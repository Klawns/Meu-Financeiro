package klaus.financeirosimples.auth.infra.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import klaus.financeirosimples.auth.application.ports.JwtProvider;
import klaus.financeirosimples.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class JwtService implements JwtProvider {
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.expiration-seconds}")
    private long expiration;

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Instant generateExpirationDate() {
        return Instant.now().plus(expiration, ChronoUnit.SECONDS);
    }


    @Override
    public String generate(User user) {
        try {
           return Jwts.builder()
                   .subject(user.getId().toString())
                   .issuedAt(Date.from(Instant.now()))
                   .expiration(Date.from(generateExpirationDate()))
                   .signWith(getSigningKey())
                   .compact();
        } catch (Exception e) {
            log.error("Error generating JWT", e);
            throw new JwtGenerationException("Error generating JWT.", e);
        }
    }
}
