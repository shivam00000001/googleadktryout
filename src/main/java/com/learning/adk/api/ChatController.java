package com.learning.adk.api;

import com.learning.adk.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@Tag(name = "Agentic Chat AI", description = "Endpoints demonstrating Main Agent and Weather Sub-Agent routing.")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // Java 17 Records for clean JSON request/response bodies
    public record ChatRequest(String message) {}
    public record ChatResponse(String userPrompt, String agentReply) {}

    @PostMapping("/ask")
    @Operation(
            summary = "Send a prompt to the AI system",
            description = "Try asking 'What is 2+2?' (handled by Main Agent) vs 'What is the weather in Paris?' (delegated to Sub-Agent)."
    )
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {

        log.info("\n\n>>> INCOMING REQUEST: User asked -> '{}'", request.message());

        if (request.message() == null || request.message().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Process the message through the ADK
        String reply = chatService.processMessage(request.message());

        log.info("<<< OUTGOING RESPONSE: Agent replied -> '{}'\n\n\n", reply);

        return ResponseEntity.ok(new ChatResponse(request.message(), reply));
    }
}