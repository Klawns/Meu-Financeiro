package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.transactions.application.exceptions.UnsupportedCurrencyException;
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

    public FinancialSummary execute(String targetCurrencyCode) {
        Currency targetCurrency = getTargetCurrency(targetCurrencyCode);
        FinancialSummary summary = getSummary.execute();
        Money inflow = converter.convert(summary.inflow(), targetCurrency);
        Money outflow = converter.convert(summary.outflow(), targetCurrency);

        FinancialSummary converted = new FinancialSummary(
                inflow,
                outflow,
                inflow.subtract(outflow)
        );

        log.info("Financial summary generated successfully: {}, currency={}", converted, targetCurrency);
        return converted;
    }

    private Currency getTargetCurrency(String targetCurrencyCode) {
        try {
            if (targetCurrencyCode == null || targetCurrencyCode.isBlank()) {
                throw new IllegalArgumentException("Currency code is required");
            }
            return Currency.getInstance(targetCurrencyCode.toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid currency code: {}", targetCurrencyCode);
            throw new UnsupportedCurrencyException("Unsupported currency: " + targetCurrencyCode);
        }
    }
}
