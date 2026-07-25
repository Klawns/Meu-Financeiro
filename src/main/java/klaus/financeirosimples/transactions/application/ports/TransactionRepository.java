package klaus.financeirosimples.transactions.application.ports;

import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.transactions.domain.vo.Money;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findById(UUID transactionId, UUID userId);
    List<Transaction> findAll(UUID userId);
    void delete(UUID transactionId, UUID userId);
    Money sumInflow(UUID userId);
    Money sumOutflow(UUID userId);
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end, UUID userId);
}