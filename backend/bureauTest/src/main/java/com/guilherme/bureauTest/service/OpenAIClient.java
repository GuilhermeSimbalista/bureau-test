package com.guilherme.bureauTest.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class OpenAIClient {
    private final WebClient webClient;

    public OpenAIClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> detectLocale(String text) {
        ChatGPTRequest request = createLocaleDetectionRequest(text);

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(response -> response.choices().get(0).message().content());
    }

    private ChatGPTRequest createLocaleDetectionRequest(String text) {
        ChatGPTMessage message = new ChatGPTMessage("user", "Detect the language of the following text: " + text);
        return new ChatGPTRequest("gpt-4", List.of(message));
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static record ChatGPTRequest(String model, List<ChatGPTMessage> messages) {
    }

    static record ChatGPTMessage(String role, String content) {
    }

    static record ChatGPTResponse(List<Choice> choices) {
    }

    static record Choice(ChatGPTMessage message) {
    }

}


