package klaus.financeirosimples.user.application.usecases;

import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import klaus.financeirosimples.user.application.output.UserOutput;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserUseCase {

    private final UserRepository userRepository;

    public UserOutput findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found. userId={}", id);
                    return new UserNotFoundException("User not found.");
                });

        return new UserOutput(
                user.getUsername(),
                user.getEmail()
        );
    }
}
