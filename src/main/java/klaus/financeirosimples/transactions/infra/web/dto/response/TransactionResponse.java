package klaus.financeirosimples.transactions.infra.web.dto.response;

import klaus.financeirosimples.transactions.domain.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(UUID transactionId, TransactionType type, String category, MoneyResponse amount, String account, LocalDate occurredAt) {
}
