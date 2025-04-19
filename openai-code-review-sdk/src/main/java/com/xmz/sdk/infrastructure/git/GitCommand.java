package com.xmz.sdk.infrastructure.git;


import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 0:19
 * Description: git 服务
 */
public class GitCommand {

    private final Logger logger = LoggerFactory.getLogger(GitCommand.class);

    private final String githubReviewLogURL;

    private final String githubToken;

    private final String project;

    private final String branch;

    private final String author;

    private final String message;

    public GitCommand(String githubReviewLogURL, String githubToken, String project, String branch, String author, String message) {
        this.githubReviewLogURL = githubReviewLogURL;
        this.githubToken = githubToken;
        this.project = project;
        this.branch = branch;
        this.author = author;
        this.message = message;
    }

    /**
     * git 用于获取当前 Git 仓库最近一次提交的代码变更（diff）内
     *
     * @return git log -1 --pretty=format:%H   # 获取最新提交的 commit hash
     * git diff <前一次提交> <本次提交>   # 获取这两次提交之间的代码改动
     */
    public String diff() throws IOException, InterruptedException {
        // openai.itedus.cn  创建 ProcessBuilder 执行 Git 命令：获取最近一次提交的 commit hash
        ProcessBuilder logProcessBuilder = new ProcessBuilder("git", "log", "-1", "--pretty=format:%H");
        logProcessBuilder.directory(new File("."));  // 设置当前目录为 Git 仓库根目录
        Process logProcess = logProcessBuilder.start(); // 启动进程执行 git log

        BufferedReader logReader = new BufferedReader(new InputStreamReader(logProcess.getInputStream()));
        String latestCommitHash = logReader.readLine();   // 读取最近一次提交的 hash 值
        logReader.close();
        logProcess.waitFor();   // 等待 git log 命令执行完成

        // 创建 ProcessBuilder 执行 Git 命令：对比上一次提交和当前提交之间的差异
        ProcessBuilder diffProcessBuilder = new ProcessBuilder("git", "diff", latestCommitHash + "^", latestCommitHash);
        diffProcessBuilder.directory(new File("."));
        Process diffProcess = diffProcessBuilder.start();

        // 读取 git diff 输出的内容（即代码的改动）
        StringBuilder diffCode = new StringBuilder();
        BufferedReader diffReader = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));
        String line;
        while ((line = diffReader.readLine()) != null) {
            diffCode.append(line).append("\n");
        }
        diffReader.close();

        // 等待 git diff 命令执行完成，并检查是否成功
        int exitCode = diffProcess.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to get diff, exit code:" + exitCode);
        }

        return diffCode.toString();
    }

    /**
     * 将推荐内容（如 AI Code Review 的结果）保存为一个 .md 文件，然后提交并推送到 GitHub 仓库，最后返回这个文件在 GitHub 上的访问链接。
     *
     * @param recommend
     * @return
     * @throws Exception
     */
    public String commitAndPush(String recommend) throws Exception {
        Git git = Git.cloneRepository()
                .setURI(githubReviewLogURL + ".git")
                .setDirectory(new File("repo"))
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubToken, ""))
                .call();

        // 创建分支
        String dateFolderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateFolder = new File("repo/" + dateFolderName); // 创建以日期为名的子文件夹，例如 repo/2025-04-19
        if (!dateFolder.exists()) {
            dateFolder.mkdirs();
        }

        // 构造唯一的 Markdown 文件名：项目-分支-作者-时间戳-4位随机数字.md
        String fileName = project + "-" + branch + "-" + author + System.currentTimeMillis() + "-" + RandomStringUtils.randomNumeric(4) + ".md";
        File newFile = new File(dateFolder, fileName);
        // 写入推荐内容（如 AI 分析结果）到 Markdown 文件
        try (FileWriter writer = new FileWriter(newFile)) {
            writer.write(recommend);
        }

        // // 使用 JGit 添加文件并提交
        git.add().addFilepattern(dateFolderName + "/" + fileName).call();
        git.commit().setMessage("add code review new file" + fileName).call();
        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubToken, "")).call();

        logger.info("openai-code-review git commit and push done! {}", fileName);

        return githubReviewLogURL + "/blob/master/" + dateFolderName + "/" + fileName;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getBranch() {
        return branch;
    }

    public String getProject() {
        return project;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public String getGithubReviewLogURL() {
        return githubReviewLogURL;
    }
}
