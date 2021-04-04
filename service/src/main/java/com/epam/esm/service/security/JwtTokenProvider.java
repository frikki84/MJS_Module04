package com.epam.esm.service.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.epam.esm.service.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    public static final String JWT_EXCEPTION = "jwt_exception";

    public final UserDetailServiceImpl userDetailService;

    //заголовок запросов, который хранит токен
    @Value("${jwt.header}")
    private String authorizationHeader;
    @Value("${jwt.milliseconds}")
    private long secretMinutes;
    @Value("${jwt.secret}")
    private String secretWord;

    public JwtTokenProvider(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    //для безопасности - шифруем секретное слово
    @PostConstruct
    protected void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    public String createToken(String name, String role) {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("role", role);
        //время, когда был создан токен
        LocalDateTime now = LocalDateTime.now();
        //время, когда был завалиден
        LocalDateTime validity = now.minusMinutes(secretMinutes);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                //подписываем при помощи специального алгоритма
                .signWith(SignatureAlgorithm.ES256, secretWord)
                .compact();
    }

    public boolean validateToken(String token) {
        boolean result = false;
        if (Objects.isNull(token)) {
            return result;
        }
        try {
            //устанавливаю секретное слово и парсю токен
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            // беру тело клеймса, вытаскиваю время его создания и определяю, был ли он создан до сейчаса, т.е. он не исктек
            result = !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException(JWT_EXCEPTION);
        }
        return result;
    }

    //получение аутентификаци из токена
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //получить имя пользователя из токена
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    //нужен для контроллера: принимает HttpServletRequest и возвращает хедер запроса
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

}
