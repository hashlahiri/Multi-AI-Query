package com.multi_ai_query.app_backend.controller;

import com.multi_ai_query.app_backend.dto.Gemini.GeminiQueryRequest;
import com.multi_ai_query.app_backend.dto.Gemini.GeminiQueryResult;
import com.multi_ai_query.app_backend.service.GeminiQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gemini")
@RequiredArgsConstructor
public class GeminiController {

    @Autowired
    private GeminiQueryService geminiQueryService;

    @PostMapping("/query")
    public GeminiQueryResult createGeminiTextRequestEndpoint(@Valid @RequestBody GeminiQueryRequest request) {

        return geminiQueryService.createGeminiTextRequest(request);
    }
}

