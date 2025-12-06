package com.multi_ai_query.app_backend.dto.Gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiGenerateContentResponse {
    private List<GeminiCandidate> candidates;
}

