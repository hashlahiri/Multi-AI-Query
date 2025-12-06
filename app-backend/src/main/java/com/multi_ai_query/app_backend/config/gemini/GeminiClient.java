package com.multi_ai_query.app_backend.config.gemini;

import com.multi_ai_query.app_backend.dto.Gemini.GeminiGenerateContentRequest;
import com.multi_ai_query.app_backend.dto.Gemini.GeminiGenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GeminiClient {

    private final WebClient geminiWebClient;

    public GeminiGenerateContentResponse generateContent(String model, GeminiGenerateContentRequest request) {
        // Endpoint pattern: /v1beta/models/{model}:generateContent :contentReference[oaicite:10]{index=10}
        return geminiWebClient.post()
                .uri("/v1beta/models/{model}:generateContent", model)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiGenerateContentResponse.class)
                .block();
    }
}

