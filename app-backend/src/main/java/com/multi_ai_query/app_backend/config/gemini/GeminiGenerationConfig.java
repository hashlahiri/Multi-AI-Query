package com.multi_ai_query.app_backend.config.gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiGenerationConfig {

    /**
     * Controls randomness. :contentReference[oaicite:8]{index=8}
     */
    private Double temperature;

    /**
     * The maximum number of tokens to include in a response candidate. :contentReference[oaicite:9]{index=9}
     */
    private Integer maxOutputTokens;

    private Double topP;
    private Integer topK;
}

