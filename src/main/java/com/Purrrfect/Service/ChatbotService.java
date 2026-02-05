package com.Purrrfect.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatbotService {

    private static final String API_URL =
            "https://api-inference.huggingface.co/models/EleutherAI/gpt-neo-1.3B";

    // ✅ Read token from environment variable
    private final String TOKEN = System.getenv("HF_API_KEY");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ChatbotService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();

        if (TOKEN == null || TOKEN.isBlank()) {
            throw new IllegalStateException(
                    "HF_API_KEY environment variable is not set"
            );
        }
    }

    public String getChatbotResponse(String userInput) {
        try {
            String cleanedInput = userInput.trim();
            return getHuggingFaceResponse(cleanedInput);
        } catch (Exception e) {
            throw new RuntimeException("Hugging Face API error: " + e.getMessage());
        }
    }

    private String getHuggingFaceResponse(String userInput) throws Exception {
        String requestBody = String.format(
                "{\"inputs\": \"%s\"}", escapeJson(userInput)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseHuggingFaceResponse(response.getBody());
        }

        throw new RuntimeException(
                "HF API error: " + response.getStatusCode()
        );
    }

    private String parseHuggingFaceResponse(String responseBody) throws Exception {
        if (responseBody == null || responseBody.isBlank()) {
            return "Empty response from API";
        }

        JsonNode root = objectMapper.readTree(responseBody);

        if (root.isArray() && root.size() > 0) {
            JsonNode first = root.get(0);
            if (first.has("generated_text")) {
                return first.get("generated_text").asText();
            }
        }

        if (root.has("generated_text")) {
            return root.get("generated_text").asText();
        }

        if (root.has("error")) {
            throw new RuntimeException(root.get("error").asText());
        }

        return responseBody;
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
