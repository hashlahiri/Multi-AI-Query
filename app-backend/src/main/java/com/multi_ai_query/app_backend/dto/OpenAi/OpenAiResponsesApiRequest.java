package com.multi_ai_query.app_backend.dto.OpenAi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAiResponsesApiRequest {

    private String model;

    // The Responses API accepts input as string or array; string is simplest.
    private String input;

    private String instructions;

    private Double temperature;

    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;
}
