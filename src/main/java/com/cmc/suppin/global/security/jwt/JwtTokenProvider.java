package com.cmc.suppin.global.security.jwt;


import com.cmc.suppin.global.security.user.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHENTICATION_CLAIM_NAME = "roles";
    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${ACCESS_EXPIRY_SECONDS}")
    private int accessExpirySeconds;

//    @Value("${jwt.refresh-expiry-seconds}")
//    private int refreshExpirySeconds;

//    private final RedisKeyRepository redisKeyRepository;

    public String createAccessToken(UserDetailsImpl userDetails) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(accessExpirySeconds);

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .claims(
                        Map.of("id", userDetails.getId(),
                                "userId", userDetails.getUserId(),
                                AUTHENTICATION_CLAIM_NAME, authorities))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .signWith(extractSecretKey())
                .compact();
    }

//    public String createRefreshToken() {
//        Instant now = Instant.now();
//        Instant expirationTime = now.plusSeconds(refreshExpirySeconds);
//
//        return Jwts.builder()
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(expirationTime))
//                .signWith(extractSecretKey())
//                .compact();
//    }

    /**
     * 권한 체크
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = verifyAndExtractClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities = null;
        if (claims.get(AUTHENTICATION_CLAIM_NAME) != null) {
            authorities = Arrays.stream(claims.get(AUTHENTICATION_CLAIM_NAME)
                            .toString()
                            .split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        }

        UserDetailsImpl principal = UserDetailsImpl.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.get("userId", String.class))
                .password(null)
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    /**
     * 토큰 추출
     */
    public String resolveToken(String bearerToken) {
        if (hasText(bearerToken) && bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
            return bearerToken.substring(AUTHENTICATION_SCHEME.length());
        }
        return null;
    }

    /**
     * Jwt 검증 및 클레임 추출
     */
    private Claims verifyAndExtractClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(extractSecretKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public void validateAccessToken(String accessToken) {
        Jwts.parser()
                .verifyWith(extractSecretKey())
                .build()
                .parse(accessToken);
    }

    /**
     * SecretKey 추출
     */
    private SecretKey extractSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}


