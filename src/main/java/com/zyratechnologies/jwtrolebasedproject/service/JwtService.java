package com.zyratechnologies.jwtrolebasedproject.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private  String  secret;
    @Value("${jwt.expiration}")
    private  Long expiration;

    private SecretKey getKey(){
        byte keyBytes[] = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith( getKey())
                .compact();
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    public <T>T extractClaims(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token){
     return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiryTime(String token){
    return extractClaims(token,Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
       return(extractUsername(token).equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiryTime(token).before(new Date());
    }


}
