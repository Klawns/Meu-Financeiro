package klaus.financeirosimples.transactions.infra.web.mapper;

import klaus.financeirosimples.transactions.application.commands.CreateTransactionCommand;
import klaus.financeirosimples.transactions.application.outputs.FinancialSummary;

import klaus.financeirosimples.transactions.application.outputs.TransactionOutput;
import klaus.financeirosimples.transactions.infra.web.dto.request.CreateTransactionRequest;
import klaus.financeirosimples.transactions.infra.web.dto.response.FinancialSummaryResponse;
import klaus.financeirosimples.transactions.infra.web.dto.response.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MoneyHttpMapper.class)
public interface TransactionHttpMapper {

    CreateTransactionCommand toCreateTransactionCommand(CreateTransactionRequest request);

    FinancialSummaryResponse toFinancialSummaryResponse(FinancialSummary summary);

    TransactionResponse toTransactionResponse(TransactionOutput transactionOutput);

}
