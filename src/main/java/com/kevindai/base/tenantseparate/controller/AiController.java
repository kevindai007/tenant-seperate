package com.kevindai.base.tenantseparate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/ai")
public class AiController {
    private final ChatClient chatClient;
    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        ChatResponse chatResponse = chatClient.prompt().user(message).call().chatResponse();
        System.out.println(chatResponse);
        return chatResponse.getResults().getFirst().getOutput().getText();
    }

}
