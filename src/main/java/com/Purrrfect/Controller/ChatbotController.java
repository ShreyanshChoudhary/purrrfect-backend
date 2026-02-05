package com.Purrrfect.Controller;

import com.Purrrfect.Service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    // Add this simple test endpoint first
    @GetMapping("/simple")
    public String simpleTest() {
        return "Simple test working! 🐕";
    }

    // Add detailed health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            // Test if service is available
            String testResponse = chatbotService.getChatbotResponse("test");
            return ResponseEntity.ok("Chatbot service is running! Test: " + testResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Health check failed: " + e.getMessage());
        }
    }

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ChatResponse("Empty message"));
            }

            String response = chatbotService.getChatbotResponse(request.getMessage());
            return ResponseEntity.ok(new ChatResponse(response));
        } catch (Exception e) {
            // Return the raw error message
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("API Error: " + e.getMessage()));
        }
    }

    // Request/Response DTOs
    public static class ChatRequest {
        private String message;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public static class ChatResponse {
        private String response;

        public ChatResponse(String response) { this.response = response; }
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
    }
}