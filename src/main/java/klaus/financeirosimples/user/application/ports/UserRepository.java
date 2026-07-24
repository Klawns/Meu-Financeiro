package klaus.financeirosimples.user.application.ports;

import klaus.financeirosimples.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    User save(User user);
    void delete(UUID id);
    boolean existsByEmail(String email);
}
