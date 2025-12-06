package com.multi_ai_query.app_backend.config.openAi;

import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiResponsesApiRequest;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiResponsesApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenAiClient {

    private final WebClient openAiWebClient;

    public OpenAiResponsesApiResponse createResponse(OpenAiResponsesApiRequest request) {
        // Responses API endpoint. :contentReference[oaicite:4]{index=4}
        return openAiWebClient.post()
                .uri("/v1/responses")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAiResponsesApiResponse.class)
                .block();
    }
}

