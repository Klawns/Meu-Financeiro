package klaus.financeirosimples.transactions.application.outputs;

import klaus.financeirosimples.transactions.domain.TransactionType;
import klaus.financeirosimples.transactions.domain.vo.Money;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionOutput(UUID transactionId, TransactionType type, String category, Money amount, String account, LocalDate occurredAt) {
}
