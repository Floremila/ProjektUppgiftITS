package se.amandaflorencia.projektuppgiftits.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Service class responsible for creating JWT tokens.
 *
 * Uses user authentication info to build the token with roles and expiration.
 */
@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    /**
     * Injects the JwtEncoder used to create the token.
     *
     * @param jwtEncoder the encoder used to sign the JWT.
     */
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a JWT for the authenticated user.
     *
     * @param authentication the current user's authentication object.
     * @return the signed JWT as a string.
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();


        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
