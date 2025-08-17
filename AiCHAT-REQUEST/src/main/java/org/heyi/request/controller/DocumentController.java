package org.heyi.request.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.heyi.request.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;

@Controller
@ResponseBody
public class DocumentController {

    @Autowired
    private HttpClientUtil useHttpClientUtil;

    @GetMapping("/stream")
    public void stream(@RequestParam String message) {
        try {
            String encode = URLEncoder.encode(message, "UTF-8");
            useHttpClientUtil.requestServerSSE("http://127.0.0.1:8230/api-server/v1/ai-module/deepseek?message=" + encode, null, (event) -> {
                System.out.println("=====" + event);
            });
        } catch (Exception e) {
            System.out.println("------");
            e.printStackTrace();
        }
    }
}
