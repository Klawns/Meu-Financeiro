package klaus.financeirosimples.transactions.infra.web.dto.response;


public record FinancialSummaryResponse(MoneyResponse inflow, MoneyResponse outflow, MoneyResponse balance) {
}
