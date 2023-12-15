package com.example.ITSSJP1.config.security;

import com.example.ITSSJP1.utils.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${secret.key}")
    private String secretKey;
    @Value("${expire.time}")
    private int expireTime;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(Constant.AUTHOR)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith( SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public boolean validateToken(String token){
        return getUsername(token) != null && isExpired(token);
    }


    public boolean isExpired(String token){
        Claims claim = getClaims(token);
        return claim.getExpiration().after( new Date(System.currentTimeMillis()));
    }

    public String getUsername(String token){
        Claims claim = getClaims(token);
        return claim.getSubject();
    }

    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
