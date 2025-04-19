package com.xmz.sdk.infrastructure.openai.impl;

import com.alibaba.fastjson2.JSON;
import com.xmz.sdk.infrastructure.openai.IOpenai;
import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import com.xmz.sdk.utils.ChatGLMToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 0:37
 * Description: 是你对接 ChatGLM 或兼容 OpenAI 接口模型服务的一个实现类。
 */
public class ChatGLM implements IOpenai {
    private final String apiHost;
    private final String apiKeySecret;

    public ChatGLM(String apiHost, String apiKeySecret) {
        this.apiHost = apiHost;
        this.apiKeySecret = apiKeySecret;
    }

    /**
     * 向一个类似 OpenAI 的大模型 API（如 ChatGLM、通义千问、DeepSeek 等）发起 对话请求（completions） 的方法，将请求内容发出去、拿到模型的回复，并反序列化成对象返回。
     * @param requestDTO
     * @return
     * @throws Exception
     */
    @Override
    public ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception {
        String token = ChatGLMToken.getToken(apiKeySecret);

        URL url = new URL(apiHost);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(requestDTO).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
    }


}
