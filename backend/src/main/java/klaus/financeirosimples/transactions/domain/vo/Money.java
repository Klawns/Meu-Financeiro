package klaus.financeirosimples.transactions.domain.vo;

import jakarta.persistence.*;

@Embeddable
public record Money(
        @Column(name = "amount", nullable = false)
        long amount
) {

    public static Money fromCents(long cents) {
        return new Money(cents);
    }

    public Money add(Money other) {
        return new Money(amount + other.amount);
    }

    public Money subtract(Money other) {
        return new Money(amount - other.amount);
    }
}
