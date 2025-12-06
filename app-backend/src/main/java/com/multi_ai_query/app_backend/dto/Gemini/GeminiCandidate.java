package com.multi_ai_query.app_backend.dto.Gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiCandidate {
    private GeminiContent content;
    private String finishReason;
    private Integer index;
}

