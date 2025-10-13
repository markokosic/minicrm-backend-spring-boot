package com.markokosic.minicrm.service;


import com.markokosic.minicrm.model.User;
import com.markokosic.minicrm.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;


@Service
public class JWTService {


    @Value("${jwt.secret}")
    private final String secretKey;

    private final UserRepository userRepository;

    public JWTService(UserRepository userRepository ){
        this.userRepository = userRepository;

		try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
           secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }

    public String generateToken(String email, Long tenantId, Long expirationInMinutes) {

        Map<String, Object> claims = new HashMap<>();
            claims.put("tenantId", tenantId);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 1000 * expirationInMinutes))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte[] keyByes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractEmail(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractTenantId(String token) {
        return extractClaim(token, claims -> claims.get("tenantId", Long.class ));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractEmail(token);
        return (!isTokenExpired(token) && userName.equals(userDetails.getUsername())  );
    }

    public boolean validateRefreshToken(String token) {
        String email = extractEmail(token);

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found")));

        if (isTokenExpired(token)) {
            return false;
        }


        return true;
    }

    public String refreshAccessToken(String refreshToken) {
        //HIER WEITER MACHEN ICH BIN GERADE IM REFRESH FLOW FÜR TOKEN
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = extractEmail(refreshToken);
        User user = userRepository.findByEmail(email).get();

        return generateToken(user.getEmail(), user.getTenantId(), 15L); // z.B. 15 Minuten
    }

    public boolean isTokenExpired(String token) {

            return extractAllClaims(token).getExpiration().before(new Date());
//        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
