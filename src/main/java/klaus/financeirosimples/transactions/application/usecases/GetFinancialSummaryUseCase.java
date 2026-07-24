package klaus.financeirosimples.transactions.application.usecases;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.common.expections.DomainException;
import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.ports.CurrencyConverter;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.domain.vo.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GetFinancialSummaryUseCase {
    private final TransactionRepository repo;
    private final AuthenticatedUser authenticatedUser;


    public FinancialSummary execute() {

        UUID userId = authenticatedUser.id();

        Money inflow = repo.sumInflow(userId);
        Money outflow = repo.sumOutflow(userId);
        Money balance = inflow.subtract(outflow);

        return new FinancialSummary(
                inflow,
                outflow,
                balance
        );
    }
}
