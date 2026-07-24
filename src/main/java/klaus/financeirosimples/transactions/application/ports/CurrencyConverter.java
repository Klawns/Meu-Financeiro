package klaus.financeirosimples.transactions.application.ports;

import klaus.financeirosimples.transactions.domain.vo.Money;

import java.util.Currency;

public interface CurrencyConverter {
    Money convert(Money source, Currency target);
}
