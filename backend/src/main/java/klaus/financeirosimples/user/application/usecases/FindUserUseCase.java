package klaus.financeirosimples.user.application.usecases;

import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import klaus.financeirosimples.user.application.output.UserOutput;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserUseCase {
    private final UserRepository userRepository;

    public UserOutput findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserOutput(user.getUsername(), user.getEmail());
    }
}
