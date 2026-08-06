package klaus.financeirosimples.user;

import klaus.financeirosimples.user.domain.User;

public class UserFactory {
    public static User createUser() {
        return new User(
                "teste",
                "teste@gmail.com",
                "123456"
        );
    }
}
