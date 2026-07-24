package klaus.financeirosimples.transactions.application.ports;

import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.transactions.domain.vo.Money;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);
    List<Transaction> findAllByUserId(UUID userId);
    void deleteByIdAndUserId(UUID transactionId, UUID userId);
    Money sumInflow(UUID userId);
    Money sumOutflow(UUID userId);
}
