package klaus.financeirosimples.transactions.application.outputs;

import klaus.financeirosimples.transactions.domain.vo.Money;

public record FinancialSummary(Money inflow, Money outflow, Money balance) {
}
