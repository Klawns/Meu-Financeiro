package klaus.financeirosimples.config;

import klaus.financeirosimples.transactions.infra.currency.CurrencyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(CurrencyProperties.class)
@RequiredArgsConstructor
public class HttpConfig {
    private final CurrencyProperties properties;

    @Bean
    RestClient restClient() {
        return RestClient.builder()
                .baseUrl(properties.url())
                .build();
    }
}
