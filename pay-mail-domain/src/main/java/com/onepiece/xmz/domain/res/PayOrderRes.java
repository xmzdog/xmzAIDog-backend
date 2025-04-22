package com.onepiece.xmz.domain.res;

import com.onepiece.xmz.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/21
 * Time: 20:08
 * Description: No Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRes {

    private String userId;

    private String orderId;

    private String payUrl;

    private Constants.OrderStatusEnum orderStatusEnum;
}
