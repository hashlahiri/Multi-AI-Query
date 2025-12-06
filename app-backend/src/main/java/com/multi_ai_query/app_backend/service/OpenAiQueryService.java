package com.multi_ai_query.app_backend.service;

import com.multi_ai_query.app_backend.config.openAi.OpenAiClient;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiQueryRequest;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiQueryResult;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiResponsesApiRequest;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiResponsesApiResponse;
import com.multi_ai_query.app_backend.config.properties.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

@Service
@RequiredArgsConstructor
public class OpenAiQueryService {

    @Autowired
    private OpenAiClient openAiClient;

    @Autowired
    private OpenAiProperties properties;

    public OpenAiQueryResult createRequestQueryOpenAi(OpenAiQueryRequest req) {
        long start = System.currentTimeMillis();

        String modelToUse = StringUtils.defaultIfBlank(req.getModel(), properties.getModel());

        try {
            OpenAiResponsesApiRequest apiRequest = OpenAiResponsesApiRequest.builder()
                    .model(modelToUse)
                    .input(req.getPrompt())
                    .instructions(req.getInstructions())
                    .temperature(req.getTemperature())
                    .maxOutputTokens(req.getMaxOutputTokens())
                    .build();

            OpenAiResponsesApiResponse apiResponse = openAiClient.createResponse(apiRequest);

            long latency = System.currentTimeMillis() - start;

            String text = extractText(apiResponse);

            return OpenAiQueryResult.builder()
                    .provider("openai")
                    .model(apiResponse != null && StringUtils.isNotBlank(apiResponse.getModel())
                            ? apiResponse.getModel()
                            : modelToUse)
                    .response(text)
                    .latencyMs(latency)
                    .error(null)
                    .build();

        } catch (Exception ex) {
            long latency = System.currentTimeMillis() - start;

            return OpenAiQueryResult.builder()
                    .provider("openai")
                    .model(modelToUse)
                    .response("")
                    .latencyMs(latency)
                    .error(ex.getMessage())
                    .build();
        }
    }

    /**
     * Tries to be resilient against evolving response shapes.
     * - Prefer outputText when present (SDK convenience style). :contentReference[oaicite:5]{index=5}
     * - Otherwise, walk output[] -> content[] -> text.
     */
    private String extractText(OpenAiResponsesApiResponse resp) {
        if (resp == null) return "";

        if (StringUtils.isNotBlank(resp.getOutputText())) {
            return resp.getOutputText();
        }

        JsonNode output = resp.getOutput();
        if (output == null || !output.isArray()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (JsonNode item : output) {
            JsonNode content = item.get("content");
            if (content != null && content.isArray()) {
                for (JsonNode c : content) {
                    String type = c.path("type").asText();
                    // Common shapes include "output_text" containers.
                    if ("output_text".equals(type) || "text".equals(type)) {
                        String t = c.path("text").asText(null);
                        if (t != null) sb.append(t);
                    }
                }
            }
        }

        return sb.toString().toString().trim();
    }
}

