package org.heyi.configuration.config;

import org.heyi.common.constant.ChatClientConstant;
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
