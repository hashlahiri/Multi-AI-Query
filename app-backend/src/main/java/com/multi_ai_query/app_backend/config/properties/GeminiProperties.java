package com.multi_ai_query.app_backend.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Data
@Validated
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {

    @NotBlank
    private String apiKey;

    @NotBlank
    private String baseUrl = "https://generativelanguage.googleapis.com";

    @NotBlank
    private String model = "gemini-2.5-flash";

    private Duration connectTimeout = Duration.ofSeconds(5);
    private Duration readTimeout = Duration.ofSeconds(30);
}

