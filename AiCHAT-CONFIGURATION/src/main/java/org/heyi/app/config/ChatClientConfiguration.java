package org.heyi.app.config;

import org.heyi.app.constant.ChatClientConstant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder argChatClientBuilder) {
        return argChatClientBuilder.defaultSystem(ChatClientConstant.AI_DEFAULT).build();
    }
}
