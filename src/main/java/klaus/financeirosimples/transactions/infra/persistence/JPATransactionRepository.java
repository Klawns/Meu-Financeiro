package klaus.financeirosimples.transactions.infra.persistence;

import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.transactions.domain.vo.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPATransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);
    List<Transaction> findAllByUserId(UUID userId);
    void deleteByIdAndUserId(UUID transactionId, UUID userId);
    List<Transaction> findAllByOccurredAtBetweenAndUserId(LocalDate start, LocalDate end, UUID userId);

    @Query("""
        SELECT COALESCE(SUM(t.amount.amount), 0)
        FROM Transaction t
        WHERE t.user.id = :userId
        AND t.type = 'INFLOW'
    """)
    Long sumInflowByUserId(UUID userId);


    @Query("""
        SELECT COALESCE(SUM(t.amount.amount), 0)
        FROM Transaction t
        WHERE t.user.id = :userId
        AND t.type = 'OUTFLOW'
    """)
    Long sumOutflowByUserId(UUID userId);
}
