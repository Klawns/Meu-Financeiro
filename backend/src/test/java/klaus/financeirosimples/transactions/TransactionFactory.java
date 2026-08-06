package klaus.financeirosimples.transactions;

import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.transactions.domain.TransactionType;
import klaus.financeirosimples.transactions.domain.vo.Money;

import java.time.LocalDate;

import static klaus.financeirosimples.user.UserFactory.createUser;

public class TransactionFactory {
    public static Transaction createTransaction() {
        return new Transaction(
                createUser(),
                TransactionType.INFLOW,
                "Salary",
                Money.fromCents(10000),
                "Nubank",
                LocalDate.now()
        );
    }
}
