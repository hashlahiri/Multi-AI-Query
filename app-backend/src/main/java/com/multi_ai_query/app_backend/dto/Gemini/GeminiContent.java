package com.multi_ai_query.app_backend.dto.Gemini;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiContent {
    private String role; // "user" or "model" (optional for single turn)
    private List<GeminiPart> parts;
}

