package com.escambia.official.webservice.utility;

import com.escambia.official.webservice.enums.SecurityRoles;
import com.escambia.official.webservice.model.postgresql.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Component
public class JwtUtility {
    @Value("${jjwt.secret}")
    private String secret;

    @Value("${jjwt.expirationTime}")
    private Long expirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Claims getAllClaimsFromAuthorization(String tokenWithBearer) {
        var token = tokenWithBearer.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Integer getUserIdFromAuthorization(String tokenWithBearer) {
        var token = tokenWithBearer.substring(7);
        return (Integer) Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .build()
                .parseClaimsJws(token).getBody().get("userId");
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateTokenUser(UserInfo userInfo) {
        List<String> roles = new ArrayList<>();
        roles.add(SecurityRoles.USER.toString());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userInfo.getUserId());
        claims.put("role", roles);
        return doGenerateToken(claims, userInfo.getUserName());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Boolean validateTokenFromAuthorization(String tokenWithBearer) {
        var token = tokenWithBearer.substring(7);
        return !isTokenExpired(token);
    }
}
