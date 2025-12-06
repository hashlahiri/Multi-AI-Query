package com.multi_ai_query.app_backend.dto.Gemini;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GeminiQueryRequest {

    @NotBlank
    private String prompt;

    // Optional overrides
    private String model;

    /**
     * Optional system instruction for the model (text-only).
     * The Gemini generateContent request supports a systemInstruction Content. :contentReference[oaicite:4]{index=4}
     */
    private String systemInstruction;

    // Generation config knobs
    private Double temperature;
    private Integer maxOutputTokens;
    private Double topP;
    private Integer topK;
}

