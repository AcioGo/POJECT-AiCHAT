package org.heyi.app.service;

import reactor.core.publisher.Flux;

/**
 * AI聊天服务接口
 * 定义与AI模型交互的流式聊天能力
 *
 * @param <T> 消息响应类型，通常为String
 * @see reactor.core.publisher.Flux
 * @since 1.0
 */
public interface ChatService {

    /**
     * 执行流式聊天对话
     *
     * @param uid     用户唯一标识符，用于会话跟踪和上下文管理
     * @param message 用户输入的聊天消息内容
     * @return 包含AI模型流式响应的Flux序列
     */
    Flux<String> useDeepseek(String uid, String message);
}