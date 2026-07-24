package klaus.financeirosimples.transactions.infra.persistence;

import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.transactions.domain.vo.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {
    private final JPATransactionRepository repo;
    @Override
    public void save(Transaction transaction) {
        repo.save(transaction);
    }

    @Override
    public Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId) {
        return repo.findByIdAndUserId(transactionId, userId);
    }

    @Override
    public List<Transaction> findAllByUserId(UUID userId) {
        return repo.findAllByUserId(userId);
    }

    @Override
    public void deleteByIdAndUserId(UUID transactionId, UUID userId) {
        repo.deleteByIdAndUserId(transactionId, userId);
    }

    @Override
    public Money sumInflow(UUID userId) {
        Long cents = repo.sumInflowByUserId(userId);
        return Money.fromCents(cents);
    }

    @Override
    public Money sumOutflow(UUID userId) {
        Long cents = repo.sumOutflowByUserId(userId);
        return Money.fromCents(cents);
    }

}
