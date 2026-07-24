package klaus.financeirosimples.transactions.infra.web.dto.response;

import klaus.financeirosimples.transactions.infra.web.dto.request.MoneyDTO;

public record FinancialSummaryResponse(MoneyDTO inflow, MoneyDTO outflow, MoneyDTO balance) {
}
