package klaus.financeirosimples.transactions.domain;

import jakarta.persistence.*;
import klaus.financeirosimples.transactions.domain.vo.Money;
import klaus.financeirosimples.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
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

    public Transaction(User user, TransactionType type, String category, Money amount, String account, LocalDate occurredAt) {
        this.user = user;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.account = account;
        this.occurredAt = occurredAt;
    }

    public void update(
            TransactionType type,
            String category,
            Money amount,
            String account,
            LocalDate occurredAt
    ) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.account = account;
        this.occurredAt = occurredAt;
    }

}
