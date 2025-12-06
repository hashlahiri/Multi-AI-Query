package com.multi_ai_query.app_backend.controller;

import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiQueryRequest;
import com.multi_ai_query.app_backend.dto.OpenAi.OpenAiQueryResult;
import com.multi_ai_query.app_backend.service.OpenAiQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class OpenAiController {

    @Autowired
    private OpenAiQueryService openAiQueryService;

    @PostMapping("/query")
    public OpenAiQueryResult createRequestQueryOpenAiEndpoint(@Valid @RequestBody OpenAiQueryRequest request) {

        return openAiQueryService.createRequestQueryOpenAi(request);
    }

}

