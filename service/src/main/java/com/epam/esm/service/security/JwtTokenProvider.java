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
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    private String prefixForPostmanAoth2 = "Bearer ";

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
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    public boolean validateToken(String token) {
        if (Objects.isNull(token)) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (RuntimeException e) {
            throw new JwtAuthenticationException(LocalizationExceptionMessageValues.JWT_EXCEPTION.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader(authorizationHeader);
        if (Objects.isNull(tokenWithBearer) || tokenWithBearer.isEmpty()) {
            return null;
        }
        return tokenWithBearer.substring(prefixForPostmanAoth2.length());
    }

}
