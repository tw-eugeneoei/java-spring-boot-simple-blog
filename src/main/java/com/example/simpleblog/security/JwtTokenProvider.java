/* (C)2022 */
package com.example.simpleblog.security;

import com.example.simpleblog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  // @Value annotation to read values from application.properties file
  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app.jwt-expiration-milliseconds}")
  private Integer jwtExpiration;

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);

    String token =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

    return token;
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public Boolean validateToken(String token) {
    // can also handle a generic exception for all
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
    }
  }
}
