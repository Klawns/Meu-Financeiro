package klaus.financeirosimples.transactions.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void shouldCreateMoneyFromCents() {
        Money money = Money.fromCents(125);

        assertEquals(125, money.amount());
    }

    @Test
    void shouldAddAndSubtractAmounts() {
        Money first = Money.fromCents(500);
        Money second = Money.fromCents(125);

        assertEquals(625, first.add(second).amount());
        assertEquals(375, first.subtract(second).amount());
    }

    @Test
    void shouldAllowZeroAndNegativeAmounts() {
        assertEquals(0, new Money(0).amount());
        assertEquals(-25, new Money(-25).amount());
    }
}
