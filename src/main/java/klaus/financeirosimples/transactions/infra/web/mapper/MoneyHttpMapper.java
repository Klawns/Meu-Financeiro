package klaus.financeirosimples.transactions.infra.web.mapper;

import klaus.financeirosimples.transactions.domain.vo.Money;
import klaus.financeirosimples.transactions.infra.web.dto.request.MoneyRequest;
import klaus.financeirosimples.transactions.infra.web.dto.response.MoneyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyHttpMapper {
    default Money toMoney(MoneyRequest moneyRequest) {
        return Money.fromCents(moneyRequest.cents());
   }

   default MoneyResponse toMoneyResponse(Money money) {
        return new MoneyResponse(
                money.amount(),
                money.currencyCode()
        );
   }
}
