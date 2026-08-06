package klaus.financeirosimples.transactions.domain;

import jakarta.persistence.*;
import klaus.financeirosimples.common.exceptions.DomainException;
import klaus.financeirosimples.transactions.domain.vo.Money;
import klaus.financeirosimples.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "transactions", indexes = {@Index(name = "idx_transactions_user_id", columnList = "user_id")})
@NoArgsConstructor
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String category;

    @Embedded
    private Money amount;

    private String account;

    @Column(name = "occurred_at")
    private LocalDate occurredAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Transaction(
            User user,
            TransactionType type,
            String category,
            Money amount,
            String account,
            LocalDate occurredAt
    ) {
        this.user = Objects.requireNonNull(user);
        this.type = Objects.requireNonNull(type);
        this.category = Objects.requireNonNull(category);
        this.amount = Objects.requireNonNull(amount);
        this.account = Objects.requireNonNull(account);
        this.occurredAt = Objects.requireNonNull(occurredAt);

        validate(
                this.category,
                this.amount,
                this.account,
                this.occurredAt
        );
    }

    public void changeCategory(String category) {
        Objects.requireNonNull(category);
        validateCategory(category);
        this.category = category;
    }

    public void changeAmount(Money amount) {
        Objects.requireNonNull(amount);
        validateAmount(amount);
        this.amount = amount;
    }

    public void changeAccount(String account) {
        Objects.requireNonNull(account);
        validateAccount(account);
        this.account = account;
    }

    public void changeOccurredAt(LocalDate occurredAt) {
        Objects.requireNonNull(occurredAt);
        validateOccurredAt(occurredAt);
        this.occurredAt = occurredAt;
    }

    private void validate(
            String category,
            Money amount,
            String account,
            LocalDate occurredAt
    ) {
        validateCategory(category);
        validateAmount(amount);
        validateAccount(account);
        validateOccurredAt(occurredAt);
    }

    private static void validateCategory(String category) {
        if (category.isBlank()) {
            log.warn("Category is required");
            throw new DomainException("Category is required");
        }
    }

    private static void validateAmount(Money amount) {
        if (amount.amount() <= 0) {
            log.warn("Transaction amount must be greater than zero");
            throw new DomainException("Transaction amount must be greater than zero");
        }
    }

    private static void validateAccount(String account) {
        if (account.isBlank()) {
            log.warn("Account is required");
            throw new DomainException("Account is required");
        }
    }

    private static void validateOccurredAt(LocalDate occurredAt) {
        if (occurredAt.isAfter(LocalDate.now())) {
            log.warn("Transaction date cannot be in the future");
            throw new DomainException("Transaction date cannot be in the future");
        }
    }
}
