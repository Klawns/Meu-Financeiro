package klaus.financeirosimples.transactions.infra.currency;

import java.math.BigDecimal;

public record CurrencyFreaksResponse(
        String date,
        String from,
        String to,
        BigDecimal rate,
        BigDecimal givenAmount,
        BigDecimal convertedAmount
) {
}
