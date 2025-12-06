package com.multi_ai_query.app_backend.dto.Gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.multi_ai_query.app_backend.config.gemini.GeminiGenerationConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiGenerateContentRequest {

    /**
     * Required. The content of the current conversation. :contentReference[oaicite:5]{index=5}
     */
    private List<GeminiContent> contents;

    /**
     * Optional system instruction(s). Currently text only. :contentReference[oaicite:6]{index=6}
     */
    private GeminiContent systemInstruction;

    /**
     * Optional. Configuration options for model generation and outputs. :contentReference[oaicite:7]{index=7}
     */
    private GeminiGenerationConfig generationConfig;
}

