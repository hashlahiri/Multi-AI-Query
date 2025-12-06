package com.multi_ai_query.app_backend.dto.Gemini;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeminiQueryResult {
    private String provider;   // "gemini"
    private String model;
    private String response;
    private long latencyMs;
    private String error;      // null if success
}

