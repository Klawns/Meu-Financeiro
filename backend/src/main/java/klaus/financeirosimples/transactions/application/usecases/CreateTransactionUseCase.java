package klaus.financeirosimples.transactions.application.usecases;

import jakarta.transaction.Transactional;
import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.commands.CreateTransactionCommand;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateTransactionUseCase {
    private final TransactionRepository repo;
    private final UserRepository userRepo;
    private final AuthenticatedUser currentUser;

    public UUID execute(CreateTransactionCommand command){
        User user = userRepo.findById(currentUser.id())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Transaction transaction = new klaus.financeirosimples.transactions.domain.Transaction(
                user,
                command.type(),
                command.category(),
                command.amount(),
                command.account(),
                command.occurredAt()
        );

        repo.save(transaction);

        return  transaction.getId();
    }
}
