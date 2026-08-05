package klaus.financeirosimples.transactions.domain.vo;

import jakarta.persistence.*;
import klaus.financeirosimples.common.exceptions.DomainException;

import java.util.Currency;

@Embeddable
public record Money(

        @Column(name = "amount", nullable = false)
        long amount,

        @Column(name = "currency", nullable = false)
        String currencyCode

) {

    public static final Currency BASE_CURRENCY =
            Currency.getInstance("GBP");


    public static Money fromCents(long cents) {
        return new Money(
                cents,
                BASE_CURRENCY.getCurrencyCode()
        );
    }
    public Money subtract(Money other) {
        validateCurrency(other);

        return new Money(
                amount - other.amount(),
                currencyCode
        );
    }

    public Money add(Money other) {
        validateCurrency(other);

        return new Money(
                amount + other.amount(),
                currencyCode
        );
    }

    private void validateCurrency(Money other) {
        if (!currency().equals(other.currency())) {
            throw new DomainException("Currencies must be equal");
        }
    }

    public Currency currency() {
        return Currency.getInstance(currencyCode);
    }

}