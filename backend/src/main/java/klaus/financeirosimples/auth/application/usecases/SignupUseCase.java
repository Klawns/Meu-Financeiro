package klaus.financeirosimples.auth.application.usecases;

import jakarta.transaction.Transactional;
import klaus.financeirosimples.auth.application.commands.SignupCommand;
import klaus.financeirosimples.user.application.exceptions.UserAlreadyExists;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupUseCase {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public void execute(SignupCommand command) {
        if (repo.existsByEmail(command.email())){
            throw new UserAlreadyExists("User already exists.");
        }

        String passwordHash = passwordEncoder.encode(command.password());

        User user = User.createUser(command.username(), command.email(), passwordHash);

        repo.save(user);
    }
}
