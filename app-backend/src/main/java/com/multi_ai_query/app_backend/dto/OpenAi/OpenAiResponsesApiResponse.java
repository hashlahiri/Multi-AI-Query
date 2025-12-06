package com.multi_ai_query.app_backend.dto.OpenAi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiResponsesApiResponse {

    private String id;

    private String model;

    // Some SDKs expose output_text as a convenience aggregator. :contentReference[oaicite:3]{index=3}
    // The field may also appear in raw JSON depending on the response shape.
    @JsonProperty("output_text")
    private String outputText;

    private JsonNode output;

    private String status;

    private JsonNode error;
}
