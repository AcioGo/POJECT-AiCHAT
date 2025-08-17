package org.heyi.request.utils;

import org.heyi.common.dataModel.dto.ServerSentEventDTO;
import org.heyi.request.properties.HttpClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class HttpClientUtil {

    @Autowired
    private  HttpClientProperties useHttpClientProperties;

    /**
     * 发送GET请求
     * @param url 请求URL
     * @param headers 请求头
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doGet(
            String url,
            Map<String, String> headers
    ) throws IOException {
        Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
        Integer readTimeout = useHttpClientProperties.getReadTimeout();
        return doGet(url, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送GET请求
     * @param url 请求URL
     * @param headers 请求头
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读取超时时间(毫秒)
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doGet(
            String url,
            Map<String, String> headers,
            Integer connectTimeout,
            Integer readTimeout
    ) throws IOException {
        return requestServer("GET", url, null, headers, "application/json", connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求(JSON格式)
     * @param url 请求URL
     * @param json JSON请求体
     * @param headers 请求头
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doPostJson(
            String url,
            String json,
            Map<String, String> headers
    ) throws IOException {
        Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
        Integer readTimeout = useHttpClientProperties.getReadTimeout();
        return doPost(url, json, headers, "application/json", connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求(表单格式)
     * @param url 请求URL
     * @param params 表单参数(key=value&key2=value2)
     * @param headers 请求头
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doPostForm(
            String url,
            String params,
            Map<String, String> headers
    ) throws IOException {
        Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
        Integer readTimeout = useHttpClientProperties.getReadTimeout();
        return doPost(url, params, headers, "application/x-www-form-urlencoded", connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @param contentType 内容类型
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读取超时时间(毫秒)
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doPost(
            String url,
            String body,
            Map<String, String> headers,
            String contentType,
            Integer connectTimeout,
            Integer readTimeout
    ) throws IOException {
        return requestServer("POST", url, body, headers, contentType, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求(JSON格式)
     * @param url 请求URL
     * @param json JSON请求体
     * @param headers 请求头
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doPutJson(
            String url,
            String json,
            Map<String, String> headers
    ) throws IOException {
        Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
        Integer readTimeout = useHttpClientProperties.getReadTimeout();
        return doPut(url, json, headers, "application/json", connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @param contentType 内容类型
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读取超时时间(毫秒)
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doPut(
            String url,
            String body,
            Map<String, String> headers,
            String contentType,
            Integer connectTimeout,
            Integer readTimeout
    ) throws IOException {
        return requestServer("PUT", url, body, headers, contentType, connectTimeout, readTimeout);
    }

    /**
     * 发送DELETE请求
     * @param url 请求URL
     * @param headers 请求头
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doDelete(
            String url,
            Map<String, String> headers
    ) throws IOException {
        Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
        Integer readTimeout = useHttpClientProperties.getReadTimeout();
        return doDelete(url, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送DELETE请求
     * @param url 请求URL
     * @param headers 请求头
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读取超时时间(毫秒)
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    public  String doDelete(
            String url,
            Map<String, String> headers,
            Integer connectTimeout,
            Integer readTimeout
    ) throws IOException {
        return requestServer("DELETE", url, null, headers, "application/json", connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP请求
     * @param method 请求方法
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @param contentType 内容类型
     * @param connectTimeout 连接超时时间(毫秒)
     * @param readTimeout 读取超时时间(毫秒)
     * @return 响应内容
     * @throws IOException 如果请求失败
     */
    private  String  requestServer(
            String method,
            String url,
            String body,
            Map<String, String> headers,
            String contentType,
            Integer connectTimeout,
            Integer readTimeout
    ) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        OutputStream outputStream = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);

            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Accept", "application/json");

            // 设置自定义请求头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 对于有请求体的方法，设置DoOutput并写入请求体
            if (body != null && !body.isEmpty()) {
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }else {
                throw new IOException("HTTP请求失败，URL: " + url);
            }
        } finally {
            // 使用工具方法关闭资源
            closeResources(reader, outputStream);
            disconnect(connection);
        }
    }

    /**
     * 建立SSE连接并监听事件
     * @param url SSE服务URL
     * @param headers 请求头
     * @param eventConsumer 事件处理器
     * @throws IOException 如果连接失败
     */
    public  void requestServerSSE(
            String url,
            Map<String, String> headers,
            Consumer<ServerSentEventDTO> eventConsumer
    ) throws IOException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            Integer connectTimeout = useHttpClientProperties.getConnectTimeout();
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            // 无限等待新事件
            connection.setReadTimeout(0);
            // 设置SSE必需的请求头
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "text/event-stream");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Connection", "keep-alive");
            // 设置自定义请求头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                ServerSentEventDTO serverSentEventDTO = new ServerSentEventDTO();

                while ((line = reader.readLine()) != null) {
                    System.out.println("line: "+line);
                    if (line.startsWith("data:")) {
                        // 处理数据行
                        String data = line.substring(5).trim();
                        serverSentEventDTO.setData(data);
                    } else if (line.startsWith("event:")) {
                        // 处理事件类型
                        String type = line.substring(6).trim();
                        serverSentEventDTO.setType(type);
                    } else if (line.startsWith("id:")) {
                        // 处理事件ID
                        String id = line.substring(3).trim();
                        serverSentEventDTO.setId(id);
                    } else if (line.startsWith("retry:")) {
                        // 处理重试时间
                        String retry = line.substring(6).trim();
                        serverSentEventDTO.setRetry(Long.parseLong(retry));
                    } else if (line.isEmpty()) {
                        // 空行表示事件结束
                        if (serverSentEventDTO.getData() != null) {
                            eventConsumer.accept(serverSentEventDTO);
                        }
                        serverSentEventDTO = new ServerSentEventDTO(); // 重置为新建事件
                    }
                }
            }else {
                throw new IOException("SSE连接失败，URL: " + url);
            }
        } finally {
            // 使用工具方法关闭资源
            closeResources(reader, inputStream);
            disconnect(connection);
        }
    }

    /**
     * 安全关闭多个资源
     * @param resources 可变参数，支持AutoCloseable类型的资源
     */
    private  void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace(); // 或使用日志记录
                }
            }
        }
    }

    /**
     * 关闭HttpURLConnection连接
     * @param connection 要关闭的连接
     */
    private  void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
