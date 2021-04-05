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

    private final static String PREFIX_FOR_POSTMAN_AOTH2 = "Bearer ";

    public final UserDetailServiceImpl userDetailService;

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

    public String createToken(String name) {
        Claims claims = Jwts.claims().setSubject(name);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plusMinutes(secretMinutes);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                //подписываем при помощи специального алгоритма
                .signWith(SignatureAlgorithm.HS256,  secretWord)
                .compact();
    }

    public boolean validateToken(String token) {
        if (token == null) {
            return false; }
        try {
           //устанавливаю секретное слово и парсю токен
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretWord).parseClaimsJws(token);
            // беру тело клеймса, вытаскиваю время его создания и определяю, был ли он создан до сейчас, т.е. он не исктек
            return  !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException(JWT_EXCEPTION);
        }
    }

    //получение аутентификаци из токена
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    //получить имя пользователя из токена
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    //нужен для контроллера: принимает HttpServletRequest и возвращает хедер запроса
    public String resolveToken(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader(authorizationHeader);
        if (tokenWithBearer == null || tokenWithBearer.isEmpty()) {
            return null;
        }
        return tokenWithBearer.substring(PREFIX_FOR_POSTMAN_AOTH2.length());
    }

}
