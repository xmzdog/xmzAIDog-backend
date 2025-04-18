package com.xmz.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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
    }
}
