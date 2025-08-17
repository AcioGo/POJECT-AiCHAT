package org.heyi.interlocution.impl;

import org.heyi.interlocution.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatClient useChatClient;

    /**
     * 执行流式聊天对话
     *
     * @param uid     用户唯一标识符，用于会话跟踪和上下文管理
     * @param message 用户输入的聊天消息内容
     * @return 包含AI模型流式响应的Flux序列
     */
    @Override
    public Flux<String> useDeepseek(String uid, String message) {
        return useChatClient.prompt().user(message).stream().content();
    }
}
