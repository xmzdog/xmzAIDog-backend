package com.xmz.sdk.infrastructure.openai;

import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import com.xmz.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/20
 * Time: 0:35
 * Description: No Description
 */
public interface IOpenai {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
