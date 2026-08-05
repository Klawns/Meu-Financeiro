package klaus.financeirosimples.auth.application.usecases;

import klaus.financeirosimples.auth.application.commands.SigninCommand;
import klaus.financeirosimples.auth.application.exceptions.InvalidCredentialsException;
import klaus.financeirosimples.auth.infra.jwt.JwtService;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigninUseCase {
    private final UserRepository repo;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;


    public String execute(SigninCommand command) {
        User user = repo.findByEmail(command.email())
                .orElseThrow(() -> {
                    log.warn("Signin failed: user not found. email={}", command.email());
                    return new InvalidCredentialsException("Invalid email or password");
                });

       if (!passwordEncoder.matches(command.password(), user.getPassword())) {
           throw new InvalidCredentialsException("Invalid email or password");
       }

        log.info("Signin successful. userId={}", user.getId());

       return jwt.generate(user);
    }
}
