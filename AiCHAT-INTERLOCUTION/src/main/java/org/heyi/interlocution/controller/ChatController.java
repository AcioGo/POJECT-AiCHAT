package org.heyi.interlocution.controller;

import org.heyi.common.constant.ApiServiceConstant;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

/**
 * AI聊天服务控制器
 * 提供基于流式响应(Reactive Streams)的AI聊天接口
 */
@Controller
@ResponseBody
@RequestMapping(ApiServiceConstant.AI_MODULE)
public class ChatController {

    @Autowired
    private OllamaChatModel  useOllamaChatModel;

    @GetMapping(value = "/deepseek", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> useDeepseek(@RequestParam String message) {
        return useOllamaChatModel
                .stream(message)
                .map(content -> ServerSentEvent.builder(content).build())
                .concatWithValues(ServerSentEvent.builder("[DONE]").build());

//        return useChatClient.prompt()
//                .user(message)
//                .stream()
//                .content()
//                .map(content -> ServerSentEvent.builder(content).build())
//                .concatWithValues(ServerSentEvent.builder("[DONE]").build());
    }
}