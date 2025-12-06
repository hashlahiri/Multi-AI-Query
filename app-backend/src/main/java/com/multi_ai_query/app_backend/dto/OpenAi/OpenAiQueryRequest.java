package com.multi_ai_query.app_backend.dto.OpenAi;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OpenAiQueryRequest {

    @NotBlank
    private String prompt;

    // optional overrides
    private String model;
    private String instructions;
    private Double temperature;
    private Integer maxOutputTokens;
}
