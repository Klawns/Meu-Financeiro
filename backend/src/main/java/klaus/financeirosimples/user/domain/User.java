package klaus.financeirosimples.user.domain;

import jakarta.persistence.*;
import klaus.financeirosimples.common.exceptions.DomainException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public User(String username, String email, String password) {
        validate(username, email, password);
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }
    public static User createUser(String username, String email, String password) {
        return new User(username, email, password);
    }
    public void changeUsername(String username) {
        this.username = username;
    }
    public void changeEmail(String email) {
        this.email = email;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    private void validate(String username, String email, String password) {
        validateEmpty(username, email, password);
        validateUsername(username);
        validatePassword(password);
    }

    private static void validatePassword(String password) {
        if (password.length() < 6) {
            log.warn("Password too short");
            throw new DomainException("Password too short");
        }
    }

    private static void validateUsername(String username) {
        if (username.length() < 3) {
            log.warn("Username too short");
            throw new DomainException("Username too short");
        }
    }

    private static void validateEmpty(String username, String email, String password) {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            log.warn("Username, email or password are empty");
            throw new DomainException("Username, email and password both required");
        }
    }
}
