package klaus.financeirosimples.transactions.application.commands;

import klaus.financeirosimples.transactions.domain.TransactionType;
import klaus.financeirosimples.transactions.domain.vo.Money;

import java.time.LocalDate;

public record CreateTransactionCommand(TransactionType type, String category, Money amount, String account, LocalDate occurredAt) {
}
