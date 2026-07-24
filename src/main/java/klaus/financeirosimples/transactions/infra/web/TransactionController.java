package klaus.financeirosimples.transactions.infra.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import klaus.financeirosimples.transactions.application.commands.CreateTransactionCommand;
import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.usecases.ConvertSummaryUseCase;
import klaus.financeirosimples.transactions.application.usecases.CreateTransactionUseCase;
import klaus.financeirosimples.transactions.application.usecases.GetFinancialSummaryUseCase;
import klaus.financeirosimples.transactions.domain.vo.Money;
import klaus.financeirosimples.transactions.infra.web.dto.request.ConvertSummaryRequest;
import klaus.financeirosimples.transactions.infra.web.dto.request.CreateTransactionRequest;
import klaus.financeirosimples.transactions.infra.web.dto.request.MoneyDTO;
import klaus.financeirosimples.transactions.infra.web.dto.response.CreateTransactionResponse;
import klaus.financeirosimples.transactions.infra.web.dto.response.FinancialSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "User's Transactions Operations")
public class TransactionController {
    private final GetFinancialSummaryUseCase getFinancialSummary;
    private final ConvertSummaryUseCase convert;
    private final CreateTransactionUseCase create;

    @PostMapping

    public ResponseEntity<CreateTransactionResponse> create(@RequestBody CreateTransactionRequest request) {
        CreateTransactionCommand command = new CreateTransactionCommand(
                request.type(),
                request.category(),
                new Money(request.amount().cents(), request.amount().currency()),
                request.account(),
                request.occurredAt()
        );

        UUID transactionId = create.execute(command);
        return ResponseEntity.ok(new CreateTransactionResponse(transactionId)   );
    }

    @GetMapping("/summary")
    public ResponseEntity<FinancialSummaryResponse> getFinancialSummary() {

        FinancialSummary summary = getFinancialSummary.execute();

        return ResponseEntity.ok(
                new FinancialSummaryResponse(
                        new MoneyDTO(
                                summary.inflow().amount(),
                                summary.inflow().currency().getCurrencyCode()
                        ),
                        new MoneyDTO(
                                summary.outflow().amount(),
                                summary.outflow().currency().getCurrencyCode()
                        ),
                        new MoneyDTO(
                                summary.balance().amount(),
                                summary.balance().currency().getCurrencyCode()
                        )
                )
        );
    }

    @PostMapping("/summary/convert")
    ResponseEntity<FinancialSummaryResponse> convert(@RequestBody ConvertSummaryRequest request) {
        FinancialSummary summary = convert.execute(request.targetCurrency());
        return ResponseEntity.ok(
                new FinancialSummaryResponse(
                        new MoneyDTO(
                                summary.inflow().amount(),
                                summary.inflow().currency().getCurrencyCode()
                        ),
                        new MoneyDTO(
                                summary.outflow().amount(),
                                summary.outflow().currency().getCurrencyCode()
                        ),
                        new MoneyDTO(
                                summary.balance().amount(),
                                summary.balance().currency().getCurrencyCode()
                        )
                )
        );
    }


}
