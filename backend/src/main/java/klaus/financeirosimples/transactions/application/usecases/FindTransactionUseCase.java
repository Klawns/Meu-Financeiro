package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.commands.TransactionPeriod;
import klaus.financeirosimples.transactions.application.exceptions.TransactionNotFoundException;
import klaus.financeirosimples.transactions.application.outputs.TransactionOutput;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.application.vo.DateRange;
import klaus.financeirosimples.transactions.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindTransactionUseCase {
    private final TransactionRepository repo;
    private final AuthenticatedUser user;

    public TransactionOutput findById(UUID transactionId) {
        Transaction transaction = repo.findById(transactionId, user.id())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found."));

        return toOutput(transaction);
    }

    public List<TransactionOutput> findAll() {
        List<Transaction> transactions = repo.findAll(user.id());
        return transactions.stream()
                .map(this::toOutput)
                .toList();
    }

    public List<TransactionOutput> findAllByDateBetween(TransactionPeriod period) {
        DateRange range = period.resolve();
        return repo.findByDateBetween(range.start(), range.end(), user.id()).stream()
                .map(this::toOutput)
                .toList();
    }

    private TransactionOutput toOutput(Transaction transaction) {
        return new TransactionOutput(
                transaction.getId(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getAccount(),
                transaction.getOccurredAt()
        );
    }
}
