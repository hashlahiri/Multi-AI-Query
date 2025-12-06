package com.multi_ai_query.app_backend.service;

import com.multi_ai_query.app_backend.config.gemini.GeminiClient;
import com.multi_ai_query.app_backend.config.gemini.GeminiGenerationConfig;
import com.multi_ai_query.app_backend.dto.Gemini.*;
import com.multi_ai_query.app_backend.config.properties.GeminiProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiQueryService {

    @Autowired
    private GeminiClient geminiClient;

    @Autowired
    private GeminiProperties properties;

    public GeminiQueryResult createGeminiTextRequest(GeminiQueryRequest req) {
        long start = System.currentTimeMillis();

        String modelToUse = StringUtils.defaultIfBlank(req.getModel(), properties.getModel());

        try {
            // Build user content
            GeminiContent userContent = GeminiContent.builder()
                    .role("user")
                    .parts(List.of(GeminiPart.builder().text(req.getPrompt()).build()))
                    .build();

            // Optional system instruction Content (text-only)
            GeminiContent system = null;
            if (StringUtils.isNotBlank(req.getSystemInstruction())) {
                system = GeminiContent.builder()
                        .parts(List.of(GeminiPart.builder().text(req.getSystemInstruction()).build()))
                        .build();
            }

            GeminiGenerationConfig genConfig = null;
            if (req.getTemperature() != null
                    || req.getMaxOutputTokens() != null
                    || req.getTopP() != null
                    || req.getTopK() != null) {

                genConfig = GeminiGenerationConfig.builder()
                        .temperature(req.getTemperature())
                        .maxOutputTokens(req.getMaxOutputTokens())
                        .topP(req.getTopP())
                        .topK(req.getTopK())
                        .build();
            }

            GeminiGenerateContentRequest apiRequest = GeminiGenerateContentRequest.builder()
                    .contents(List.of(userContent))
                    .systemInstruction(system)
//                    .generationConfig(genConfig)
                    .build();

            GeminiGenerateContentResponse apiResponse =
                    geminiClient.generateContent(modelToUse, apiRequest);

            long latency = System.currentTimeMillis() - start;

            String text = extractText(apiResponse);

            return GeminiQueryResult.builder()
                    .provider("gemini")
                    .model(modelToUse)
                    .response(text)
                    .latencyMs(latency)
                    .error(null)
                    .build();

        } catch (Exception ex) {
            long latency = System.currentTimeMillis() - start;

            return GeminiQueryResult.builder()
                    .provider("gemini")
                    .model(modelToUse)
                    .response("")
                    .latencyMs(latency)
                    .error(ex.getMessage())
                    .build();
        }
    }

    /**
     * Typical text response:
     * candidates[0].content.parts[0].text :contentReference[oaicite:11]{index=11}
     */
    private String extractText(GeminiGenerateContentResponse resp) {
        if (resp == null || resp.getCandidates() == null || resp.getCandidates().isEmpty()) {
            return "";
        }

        GeminiCandidate c = resp.getCandidates().get(0);
        if (c == null || c.getContent() == null || c.getContent().getParts() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (GeminiPart p : c.getContent().getParts()) {
            if (p != null && StringUtils.isNotBlank(p.getText())) {
                sb.append(p.getText());
            }
        }
        return sb.toString().trim();
    }
}
