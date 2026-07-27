package klaus.financeirosimples.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Meu Financeiro").version("1.0").description("Meu Financeiro API Documentation"))
                .components(new Components()
                        .addSecuritySchemes(
                                "access-token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                                        .name("access-token")
                        ))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("access-token")
                );
    }
}
