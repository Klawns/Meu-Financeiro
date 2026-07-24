package klaus.financeirosimples.auth.application.ports;

import klaus.financeirosimples.user.domain.User;

public interface JwtProvider {
    String generate(User user);
}
