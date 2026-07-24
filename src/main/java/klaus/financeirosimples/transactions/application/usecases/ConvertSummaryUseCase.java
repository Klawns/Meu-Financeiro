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

        Currency targetCurrency;
        try {
            targetCurrency = Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new UnsupportedOperationException(
                    "Unsupported currency: " + currencyCode
            );
        }

        FinancialSummary summary = getSummary.execute();

        Money inflow = summary.inflow();
        Money outflow = summary.outflow();

        if (!inflow.currency().equals(targetCurrency)) {
            inflow = converter.convert(inflow, targetCurrency);
            outflow = converter.convert(outflow, targetCurrency);
        }

        Money balance = inflow.subtract(outflow);

        return new FinancialSummary(
                inflow,
                outflow,
                balance
        );
    }
}
