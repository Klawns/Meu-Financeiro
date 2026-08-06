package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.transactions.domain.Transaction;
import jakarta.transaction.Transactional;
import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.exceptions.TransactionNotFoundException;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeleteTransactionUseCase {
    private final TransactionRepository repo;
    private final AuthenticatedUser user;

    public void execute(UUID transactionId) {
        var userId = user.id();

        repo.findById(transactionId, userId)
                        .orElseThrow(() -> {
                            log.warn("Transaction not found with id {}", transactionId);
                            return new TransactionNotFoundException("Transaction not found.");
                        });

        repo.delete(transactionId, userId);
        log.info("Transaction deleted: {}", transactionId);
    }
}
