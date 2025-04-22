package com.onepiece.xmz.domain.req;

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
public class ShopCartReq {

    private String userId;
//    商品ID
    private String productId;
}
