package one.challenge.valverde.lucas.forum_hub.infra.springdoc;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("ForumHUB API (Em construção)")
                        .description("""
                                Esse é uma API Rest do ForumHUB, contendo as funcionalidades de CRUD para os posts. Mais features serão adicionadas no futuro, como acesso a informações de usuários, opções de buscas e respostas aos tópicos.
                                
                                A mesma foi criada para o desafio final do Programa Oracle Next Education da Oracle + Alura com foco em BackEnd.""")
                        .contact(new Contact()
                                .name(":Lucas Valverde")
                                .email("valverdelucas95@gmail.com"))
                        .license(new License()
                                .name("GitHub")
                                .url("https://github.com/ValverdeLucas/Challenge-ONE-ForumHUB")));
    }


}
