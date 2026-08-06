package klaus.financeirosimples.transactions.domain;

import klaus.financeirosimples.common.exceptions.DomainException;
import klaus.financeirosimples.transactions.domain.vo.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static klaus.financeirosimples.transactions.TransactionFactory.createTransaction;
import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    void shouldCreateTransaction() {
        Transaction transaction = createTransaction();

        assertEquals(TransactionType.INFLOW, transaction.getType());
        assertEquals("Salary", transaction.getCategory());
        assertEquals(10000, transaction.getAmount().amount());
        assertEquals("Nubank", transaction.getAccount());
        assertEquals(LocalDate.now(), transaction.getOccurredAt());
        assertNotNull(transaction.getUser());
    }

    @Test
    void shouldChangeCategory() {
        Transaction transaction = createTransaction();

        transaction.changeCategory("Food");

        assertEquals("Food", transaction.getCategory());
    }

    @Test
    void shouldChangeAmount() {
        Transaction transaction = createTransaction();

        transaction.changeAmount(Money.fromCents(2500));

        assertEquals(2500, transaction.getAmount().amount());
    }

    @Test
    void shouldChangeAccount() {
        Transaction transaction = createTransaction();

        transaction.changeAccount("Inter");

        assertEquals("Inter", transaction.getAccount());
    }

    @Test
    void shouldChangeOccurredAt() {
        Transaction transaction = createTransaction();

        LocalDate yesterday = LocalDate.now().minusDays(1);

        transaction.changeOccurredAt(yesterday);

        assertEquals(yesterday, transaction.getOccurredAt());
    }

    @Test
    void shouldThrowWhenCategoryIsBlank() {
        assertThrows(
                DomainException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "",
                        Money.fromCents(1000),
                        "Nubank",
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenAmountIsZero() {
        assertThrows(
                DomainException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        Money.fromCents(0),
                        "Nubank",
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenAccountIsBlank() {
        assertThrows(
                DomainException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        Money.fromCents(1000),
                        "",
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenOccurredAtIsInFuture() {
        assertThrows(
                DomainException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        Money.fromCents(1000),
                        "Nubank",
                        LocalDate.now().plusDays(1)
                )
        );
    }

    @Test
    void shouldThrowWhenCategoryIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        null,
                        Money.fromCents(1000),
                        "Nubank",
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenAmountIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        null,
                        "Nubank",
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenAccountIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        Money.fromCents(1000),
                        null,
                        LocalDate.now()
                )
        );
    }

    @Test
    void shouldThrowWhenOccurredAtIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Transaction(
                        createUser(),
                        TransactionType.INFLOW,
                        "Salary",
                        Money.fromCents(1000),
                        "Nubank",
                        null
                )
        );
    }
}