package klaus.financeirosimples.user.domain;

import klaus.financeirosimples.common.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    @DisplayName("Success - Should create user with valid data.")
    void shouldCreateUser() {
        User testUser = createUser();
        assertEquals("teste", testUser.getUsername());
        assertEquals("teste@gmail.com", testUser.getEmail());
        assertEquals("123456", testUser.getPassword());
    }

    @Test
    @DisplayName("Error - Should throw when username is too short.")
    void shouldThrowWhenUsernameIsTooShort() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            User testUser = new User("ab", "teste@gmail.com", "test");
        });
        assertEquals("Username too short", exception.getMessage());
    }

    @Test
    @DisplayName("Error - Should throw when password is too short.")
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
    @DisplayName("Error - Should throw when username is empty.")
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
    @DisplayName("Error - Should throw when email is empty.")
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
    @DisplayName("Error - Should throw when password is empty.")
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
