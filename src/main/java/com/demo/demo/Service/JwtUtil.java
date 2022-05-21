package com.demo.demo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    //JSON Web Token (JWT) is a standard for encoding information that may be securely transmitted as a JSON object.

    private String SECRETE_KEY = "secret";

    public String extractUsername(String token){ //takes in a token and return the username
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){ //takes in a token and return the expiration date
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){ //uses a claim resolver to figure out what the claims are
        final Claims claims = extractAllClaims(token);
        return  claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){ //checks if the token is expired or not
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(UserDetails userDetails){ //pass in user details and gets back the jwt for that user (generates a token)
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims, userDetails.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject){ //sets subject (user), date etc.
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //10 hours
                .signWith(SignatureAlgorithm.HS256, SECRETE_KEY).compact(); //sign the token and pass in the secrete key
    }
    public Boolean validateToken(String token, UserDetails userDetails){ //validates the token and the user details
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
