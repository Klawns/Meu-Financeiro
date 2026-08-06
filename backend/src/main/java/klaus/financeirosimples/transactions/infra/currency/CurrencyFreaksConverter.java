package klaus.financeirosimples.transactions.infra.currency;

import klaus.financeirosimples.transactions.application.ports.CurrencyConverter;
import klaus.financeirosimples.transactions.domain.vo.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
@RequiredArgsConstructor
public class CurrencyFreaksConverter implements CurrencyConverter {
    private static final Currency SOURCE_CURRENCY = Currency.getInstance("GBP");

    private final CurrencyFreaksClient client;

    @Override
    public Money convert(Money source, Currency target) {
        CurrencyFreaksResponse response =
                client.convert(
                        SOURCE_CURRENCY,
                        target,
                        source.amount()
                );
        return new Money(response.convertedAmount().longValue());
    }
}
