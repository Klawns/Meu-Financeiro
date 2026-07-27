package klaus.financeirosimples.transactions.infra.currency;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currency")
public record CurrencyProperties(String apiKey, String url) {
}
