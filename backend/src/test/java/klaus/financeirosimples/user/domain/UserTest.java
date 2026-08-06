package klaus.financeirosimples.user.domain;

import klaus.financeirosimples.common.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    void shouldCreateUser() {
        User testUser = createUser();
        assertEquals("teste", testUser.getUsername());
        assertEquals("teste@gmail.com", testUser.getEmail());
        assertEquals("123456", testUser.getPassword());
    }

    @Test
    void shouldThrowWhenUsernameIsTooShort() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            User testUser = new User("ab", "teste@gmail.com", "test");
        });
        assertEquals("Username too short", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPasswordIsTooShort() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> User.createUser(
                        "teste",
                        "teste@gmail.com",
                        "12345"
                )
        );

        assertEquals("Password too short", exception.getMessage());
    }

    @Test
    void shouldThrowWhenUsernameIsEmpty() {
        assertThrows(
                DomainException.class,
                () -> User.createUser(
                        "",
                        "teste@email.com",
                        "123456"
                )
        );
    }

    @Test
    void shouldThrowWhenEmailIsEmpty() {
        assertThrows(
                DomainException.class,
                () -> User.createUser(
                        "teste",
                        "",
                        "123456"
                )
        );
    }

    @Test
    void shouldThrowWhenPasswordIsEmpty() {
        assertThrows(
                DomainException.class,
                () -> User.createUser(
                        "teste",
                        "teste@gmail.com",
                        ""
                )
        );
    }

}
