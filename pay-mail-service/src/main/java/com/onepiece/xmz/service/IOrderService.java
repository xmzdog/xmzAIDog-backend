package com.onepiece.xmz.service;

import com.onepiece.xmz.domain.req.ShopCartReq;
import com.onepiece.xmz.domain.res.PayOrderRes;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/21
 * Time: 20:07
 * Description: 订单服务接口
 */
public interface IOrderService {

    PayOrderRes createOrder(ShopCartReq shopCartReq);
}
