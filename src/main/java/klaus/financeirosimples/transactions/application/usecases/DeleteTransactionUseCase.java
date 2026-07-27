package klaus.financeirosimples.transactions.application.usecases;

import jakarta.transaction.Transactional;
import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteTransactionUseCase {
    private final TransactionRepository repo;
    private final AuthenticatedUser user;

    public void execute(UUID transactionId) {
        repo.delete(transactionId, user.id());
    }
}
