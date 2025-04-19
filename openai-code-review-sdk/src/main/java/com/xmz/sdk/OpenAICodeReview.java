package com.xmz.sdk;

import com.alibaba.fastjson2.JSON;
import com.xmz.sdk.infrastructure.git.GitCommand;
import com.xmz.sdk.infrastructure.openai.IOpenai;
import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;
import com.xmz.sdk.infrastructure.openai.impl.ChatGLM;
import com.xmz.sdk.infrastructure.weixin.WeiXin;
import com.xmz.sdk.infrastructure.weixin.dto.TemplateMessageDTO;
import com.xmz.sdk.model.Model;
import com.xmz.sdk.service.impl.OpenAiCodeReviewServiceImpl;
import com.xmz.sdk.utils.ChatGLMToken;
import com.xmz.sdk.utils.WXAccessTokenUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/17
 * Time: 22:06
 * Description: No Description
 */
public class OpenAICodeReview {

    private static final Logger logger = LoggerFactory.getLogger(OpenAICodeReview.class);

    // 配置配置
    private String weixin_appid = "wxf4a59accd4e400ef";
    private String weixin_secret = "0138ff87eb1fe818b8355bbd4ce58b36";
    private String weixin_touser = "oEti17SAVHCKZiXwsADODuViZFtU";
    private String weixin_template_id = "AHELTkCLGj6S2egwu4zfubJdaAeSbVftoLEiRzxu3-I";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private String chatglm_apiKeySecret = "3e170b0c86004a7bbc93d456cfb2d645.7OzCVMrF8yBgRjyG";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        /**
         * 项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );



        IOpenai openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        OpenAiCodeReviewServiceImpl openAiCodeReviewService = new OpenAiCodeReviewServiceImpl(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }



//    public static void main(String[] args) throws Exception {
//        System.out.println("openai 代码评审，测试执行");
//
//        String token = System.getenv("GITHUB_TOKEN");
//        if (null == token || token.isEmpty()) {
//            throw new RuntimeException("token is null");
//        }
//
//        // 代码检出
//        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
//        processBuilder.directory(new File("."));
//
//        Process start = processBuilder.start();
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
//
//        String line;
//        StringBuilder stringBuilder = new StringBuilder();
//        while ((line = bufferedReader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//
//        int exitcode = start.waitFor();
//        System.out.println("Exited with code " + exitcode);
//        System.out.println("评审代码"+stringBuilder.toString());
//
//        //2. ChatGLM api 代码评审
//        String log = codeReview(stringBuilder.toString());
//        System.out.println("code review：" + log);
//
//        System.out.println("=======================开始输出评审日志========================================");
//        // 3. 写入评审日志
//        String logUrl = writeLog(token, log);
//        System.out.println("writeLog：" + logUrl);
//
//        // 4. 消息通知
//        System.out.println("pushMessage：" + logUrl);
//        pushMessage(logUrl);
//
//
//    }

//    private static void pushMessage(String logUrl) {
//        String accessToken = WXAccessTokenUtils.getAccessToken();
//        System.out.println(accessToken);
//
//        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
//        templateMessageDTO.put("project", "big-market");
//        templateMessageDTO.put("review", logUrl);
//        templateMessageDTO.put("model", Model.GLM_4.toString());
//        templateMessageDTO.setUrl(logUrl);
//
//        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken);
//        sendPostRequest(url, JSON.toJSONString(templateMessageDTO));
//    }
//
//    private static void sendPostRequest(String urlString, String jsonBody) {
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setDoOutput(true);
//
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
//                String response = scanner.useDelimiter("\\A").next();
//                System.out.println(response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    private static String codeReview(String diffCode) throws Exception {
//
//        String apiKeySecret = "3e170b0c86004a7bbc93d456cfb2d645.7OzCVMrF8yBgRjyG";
//        String token = ChatGLMToken.getToken(apiKeySecret);
//
//        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Authorization", "Bearer " + token);
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        connection.setDoOutput(true);
//
//        ChatCompletionRequestDTO chatCompletionRequestDTO = new ChatCompletionRequestDTO();
//        chatCompletionRequestDTO.setModel(Model.GLM_4_FLASH.getCode());
//        chatCompletionRequestDTO.setMessages(new ArrayList<ChatCompletionRequestDTO.Prompt>() {
//            private static final long serialVersionUID = -7988151926241837899L;
//            {
//                add(new ChatCompletionRequestDTO.Prompt("user", "你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码如下:"));
//                add(new ChatCompletionRequestDTO.Prompt("user", diffCode));
//            }
//        });
//
//        try (OutputStream os = connection.getOutputStream()) {
//            byte[] input = JSON.toJSONString(chatCompletionRequestDTO).getBytes(StandardCharsets.UTF_8);
//            os.write(input);
//        }
//
//        int responseCode = connection.getResponseCode();
//        System.out.println(responseCode);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//
//        StringBuilder content = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//
//        in.close();
//        connection.disconnect();
//
//        System.out.println("评审结果：" + content.toString());
//
//        ChatCompletionSyncResponseDTO response = JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
//        return response.getChoices().get(0).getMessage().getContent();
//    }
//
//    private static String writeLog(String token, String log) throws Exception {
//        System.out.println("============================== 进入 writeLog =======================================");
//        Git git = Git.cloneRepository()
//                .setURI("https://github.com/xmzdog/openai-code-review-log.git")
//                .setDirectory(new File("repo"))
//                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""))
//                .call();
//
//        String dateFolderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        File dateFolder = new File("repo/" + dateFolderName);
//        if (!dateFolder.exists()) {
//            dateFolder.mkdirs();
//        }
//
//        String fileName = generateRandomString(12) + ".md";
//        File newFile = new File(dateFolder, fileName);
//        try (FileWriter writer = new FileWriter(newFile)) {
//            writer.write(log);
//        }
//        git.add().addFilepattern(dateFolderName + "/" + fileName).call();
//        git.commit().setMessage("Add new file via GitHub Actions").call();
//        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, "")).call();
//
//        System.out.println("Changes have been pushed to the repository.");
//
//        return "https://github.com/xmzdog/openai-code-review-log/blob/master/" + dateFolderName + "/" + fileName;
//    }
//
//    private static String generateRandomString(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return sb.toString();
//    }



}
