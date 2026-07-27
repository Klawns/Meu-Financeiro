package klaus.financeirosimples.transactions.application.commands;

import klaus.financeirosimples.transactions.application.vo.DateRange;

import java.time.LocalDate;

public enum TransactionPeriod {
    // Padrão Strategy
    TODAY {
        @Override
        public DateRange resolve() {
            LocalDate today = LocalDate.now();
            return new DateRange(today, today);
        }
    },

    YESTERDAY {
        @Override
        public DateRange resolve() {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            return new DateRange(yesterday, yesterday);
        }
    };

    public abstract DateRange resolve();
}