package com.multi_ai_query.app_backend.dto.Gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiPart {
    private String text;
}

