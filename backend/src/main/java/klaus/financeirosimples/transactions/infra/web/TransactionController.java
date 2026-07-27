package klaus.financeirosimples.transactions.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import klaus.financeirosimples.transactions.application.commands.CreateTransactionCommand;
import klaus.financeirosimples.transactions.application.commands.TransactionPeriod;
import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;
import klaus.financeirosimples.transactions.application.outputs.TransactionOutput;
import klaus.financeirosimples.transactions.application.usecases.*;
import klaus.financeirosimples.transactions.infra.web.dto.request.ConvertSummaryRequest;
import klaus.financeirosimples.transactions.infra.web.dto.request.CreateTransactionRequest;
import klaus.financeirosimples.transactions.infra.web.dto.response.CreateTransactionResponse;
import klaus.financeirosimples.transactions.infra.web.dto.response.FinancialSummaryResponse;
import klaus.financeirosimples.transactions.infra.web.dto.response.TransactionResponse;
import klaus.financeirosimples.transactions.infra.web.mapper.TransactionHttpMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Operations for managing financial transactions.")
public class TransactionController {
    private final CreateTransactionUseCase create;
    private final FindTransactionUseCase find;
    private final DeleteTransactionUseCase delete;
    private final ConvertSummaryUseCase convert;
    private final GetFinancialSummaryUseCase getFinancialSummary;
    private final TransactionHttpMapper mapper;



    @PostMapping
    @Operation(
            summary = "Create transaction",
            description = "Creates a new financial transaction."
    )
    public ResponseEntity<CreateTransactionResponse> create(@RequestBody CreateTransactionRequest request) {
        CreateTransactionCommand command = mapper.toCreateTransactionCommand(request);
        UUID transactionId = create.execute(command);
        return ResponseEntity.ok(new CreateTransactionResponse(transactionId)   );
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Get financial summary",
            description = "Returns the current financial summary, including inflow, outflow and balance."
    )
    public ResponseEntity<FinancialSummaryResponse> getFinancialSummary() {
        FinancialSummary summary = getFinancialSummary.execute();
        return ResponseEntity.ok(mapper.toFinancialSummaryResponse(summary));
    }

    @Operation(
            summary = "Convert financial summary",
            description = "Converts the financial summary values to the requested currency."
    )
    @PostMapping("/summary/convert")
    ResponseEntity<FinancialSummaryResponse> convert(@RequestBody ConvertSummaryRequest request) {
        FinancialSummary summary = convert.execute(request.targetCurrency());
        return ResponseEntity.ok(mapper.toFinancialSummaryResponse(summary));
    }

    @GetMapping
    @Operation(
            summary = "List transactions",
            description = "Returns all transactions or filters them by period."
    )
    public ResponseEntity<List<TransactionResponse>> list(@RequestParam(required = false) TransactionPeriod period) {
        List<TransactionOutput> transactions = period == null ? find.findAll() : find.findAllByDateBetween(period);
        List<TransactionResponse> response = transactions
                .stream()
                .map(mapper::toTransactionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{transactionId}")
    @Operation(
            summary = "Get transaction by ID",
            description = "Returns a transaction by its identifier."
    )
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable UUID transactionId) {
        return ResponseEntity.ok(mapper.toTransactionResponse(find.findById(transactionId)));
    }

    @DeleteMapping("{transactionId}")
    @Operation(
            summary = "Delete transaction by ID",
            description = "Deletes a transaction by its identifier."
    )
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        delete.execute(transactionId);
        return ResponseEntity.ok().build();
    }
}
