package com.xmz.sdk;

import com.alibaba.fastjson2.JSON;
import com.xmz.sdk.model.ChatCompletionRequest;
import com.xmz.sdk.model.ChatCompletionSyncResponse;
import com.xmz.sdk.model.Model;
import com.xmz.sdk.utils.ChatGLMToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/17
 * Time: 22:06
 * Description: No Description
 */
public class OpenAICodeReview {
    public static void main(String[] args) throws Exception {
        System.out.println("测试代码评审");

        // 代码检出

        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));

        Process start = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        int exitcode = start.waitFor();
        System.out.println("Exited with code " + exitcode);
        System.out.println("评审代码"+stringBuilder.toString());

        //2. ChatGLM api 代码评审
        String log = codeReview(stringBuilder.toString());
        System.out.println("code review：" + log);


    }


    private static String codeReview(String diffCode) throws Exception {

        String apiKeySecret = "3e170b0c86004a7bbc93d456cfb2d645.7OzCVMrF8yBgRjyG";
        String token = ChatGLMToken.getToken(apiKeySecret);

        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(Model.GLM_4_FLASH.getCode());
        chatCompletionRequest.setMessages(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(new ChatCompletionRequest.Prompt("user", "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));
                add(new ChatCompletionRequest.Prompt("user", diffCode));
            }
        });

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.toJSONString(chatCompletionRequest).getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        System.out.println("评审结果：" + content.toString());

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }


}
