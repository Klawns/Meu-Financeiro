package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.ports.CurrencyConverter;
import klaus.financeirosimples.transactions.domain.vo.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@RequiredArgsConstructor
public class ConvertSummaryUseCase {
    private final CurrencyConverter converter;
    private final GetFinancialSummaryUseCase getSummary;


    public FinancialSummary execute(String currencyCode) {
        Currency targetCurrency = getTargetCurrency(currencyCode);

        FinancialSummary summary = getSummary.execute();

        Money inflow = convertIfNecessary(summary.inflow(), targetCurrency);
        Money outflow = convertIfNecessary(summary.outflow(), targetCurrency);

        return new FinancialSummary(
                inflow,
                outflow,
                inflow.subtract(outflow)
        );
    }

    private Currency getTargetCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new UnsupportedOperationException(
                    "Unsupported currency: " + currencyCode
            );
        }
    }

    private Money convertIfNecessary(Money money, Currency targetCurrency) {
        if (money.currency().equals(targetCurrency)) {
            return money;
        }

        return converter.convert(money, targetCurrency);
    }
}
