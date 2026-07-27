package klaus.financeirosimples.user.infra.persistence;

import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository repo;

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repo.findById(id);
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
