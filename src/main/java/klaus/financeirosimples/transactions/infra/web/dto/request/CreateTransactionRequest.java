package klaus.financeirosimples.transactions.infra.web.dto.request;

import klaus.financeirosimples.transactions.domain.TransactionType;
import klaus.financeirosimples.transactions.domain.vo.Money;

import java.time.LocalDate;

public record CreateTransactionRequest(TransactionType type, String category, MoneyRequest amount, String account, LocalDate occurredAt) {
}
