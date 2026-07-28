package klaus.financeirosimples.auth.application.usecases;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class AuthenticatedUser {

    public UUID id() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Unauthenticated user");
        }

        return UUID.fromString(
                Objects.requireNonNull(jwt.getSubject(), "JWT without subject")
        );
    }
}