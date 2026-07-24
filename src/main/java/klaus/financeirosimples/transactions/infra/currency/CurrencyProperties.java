package klaus.financeirosimples.transactions.infra.currency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "currency")
public record CurrencyProperties(String apiKey, String url) {
}
