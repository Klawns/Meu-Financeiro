package klaus.financeirosimples.transactions.infra.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Currency;

@Component
@RequiredArgsConstructor
public class CurrencyFreaksClient {

    private final RestClient restClient;
    private final CurrencyProperties properties;

    public CurrencyFreaksResponse convert(
            Currency from,
            Currency to,
            long amount
    ) {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/convert/latest")
                        .queryParam("apikey", properties.apiKey())
                        .queryParam("from", from.getCurrencyCode())
                        .queryParam("to", to.getCurrencyCode())
                        .queryParam("amount", amount)

                        .build()

                )
                .retrieve()
                .body(CurrencyFreaksResponse.class);
    }
}
