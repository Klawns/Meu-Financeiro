package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.ports.CurrencyConverter;
import klaus.financeirosimples.transactions.domain.vo.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConvertSummaryUseCase {
    private final CurrencyConverter converter;
    private final GetFinancialSummaryUseCase getSummary;


    public FinancialSummary execute(String currencyCode) {
        Currency targetCurrency = getTargetCurrency(currencyCode);

        FinancialSummary summary = getSummary.execute();

        Money inflow = convertIfNecessary(summary.inflow(), targetCurrency);
        Money outflow = convertIfNecessary(summary.outflow(), targetCurrency);

        FinancialSummary converted = new FinancialSummary(
                inflow,
                outflow,
                inflow.subtract(outflow)
        );

        log.info("Financial summary generated successfully: {}, currency={}",converted, targetCurrency);
        return converted;
    }

    private Currency getTargetCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid currency code: {}", currencyCode);
            throw new UnsupportedOperationException(
                    "Unsupported currency: " + currencyCode
            );
        }
    }

    private Money convertIfNecessary(Money money, Currency targetCurrency) {
        if (money.currency().equals(targetCurrency)) {
            return money;
        }
        log.trace("Converting {} to {}", money, targetCurrency);
        return converter.convert(money, targetCurrency);
    }
}
