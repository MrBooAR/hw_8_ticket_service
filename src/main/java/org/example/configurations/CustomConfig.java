package org.example.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    @ConditionalOnProperty(name = "my.custom.condition.enabled", havingValue = "true", matchIfMissing = false)
    public String thisIsMyFirstConditionalBean() {
        System.out.println("Conditional Bean Loaded: ThisIsMyFirstConditionalBean");
        return "ThisIsMyFirstConditionalBean";
    }
}