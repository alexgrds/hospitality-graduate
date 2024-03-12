package com.fiap.hospitality;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Hospitality Simple", version = "0.0.1", description = "The best place to you", license = @License(name = "MIT License", url = "https://github.com/alexgrds/hospitality-graduate")),
servers = {
    @Server(url = "http://localhost:8080")
})
public class SpringDocConfigurations {
}
