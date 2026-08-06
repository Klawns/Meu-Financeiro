package klaus.financeirosimples.transactions.usecase;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.application.usecases.GetFinancialSummaryUseCase;
import klaus.financeirosimples.transactions.domain.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetFinancialSummaryUseCaseTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AuthenticatedUser currentUser;

    @InjectMocks
    GetFinancialSummaryUseCase useCase;

    @Test
    @DisplayName("Success - Should return financial summary.")
    void getFinancialSummary() {
        UUID  userId = UUID.randomUUID();
        when(currentUser.id()).thenReturn(userId);

        when(transactionRepository.sumInflow(userId)).thenReturn(Money.fromCents(10000));

        when(transactionRepository.sumOutflow(userId)).thenReturn(Money.fromCents(3000));

        FinancialSummary summary = useCase.execute();

        assertEquals(10000, summary.inflow().amount());
        assertEquals(3000, summary.outflow().amount());
        assertEquals(7000, summary.balance().amount());

        verify(transactionRepository).sumInflow(userId);
        verify(transactionRepository).sumOutflow(userId);
    }


}
