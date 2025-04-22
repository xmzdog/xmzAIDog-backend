package com.onepiece.xmz.service.impl;

import com.onepiece.xmz.common.constants.Constants;
import com.onepiece.xmz.domain.po.PayOrder;
import com.onepiece.xmz.domain.req.ShopCartReq;
import com.onepiece.xmz.domain.res.PayOrderRes;
import com.onepiece.xmz.domain.vo.ProductVO;
import com.onepiece.xmz.dto.IOrderDao;
import com.onepiece.xmz.service.IOrderService;
import com.onepiece.xmz.service.rpc.ProductRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/21
 * Time: 23:42
 * Description: No Description
 */
@Slf4j
@Service
public class IOrderServiceImpl implements IOrderService {

    @Resource
    private IOrderDao orderDao;

    @Resource
    private ProductRPC productRPC;

    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) {
        // 1.查看用户是否有未支付订单或掉单订单
        PayOrder payOrder = new PayOrder();
        payOrder.setUserId(shopCartReq.getUserId());
        payOrder.setProductId(shopCartReq.getProductId());

        PayOrder unpaidOrder = orderDao.queryUnPayOrder(payOrder);



        if (null != unpaidOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unpaidOrder.getStatus())) {
            log.info("创建订单-存在，已存在未支付订单。userId:{} productId:{} orderId:{}", shopCartReq.getUserId(), shopCartReq.getProductId(), unpaidOrder.getOrderId());
            return PayOrderRes.builder()
                    .orderId(unpaidOrder.getOrderId())
                    .payUrl(unpaidOrder.getPayUrl())
                    .build();
        } else if (null != unpaidOrder && Constants.OrderStatusEnum.CREATE.getCode().equals(unpaidOrder.getStatus())) {
            log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} productId:{} orderId:{}", shopCartReq.getUserId(), shopCartReq.getProductId(), unpaidOrder.getOrderId());
            PayOrder payOrder = doPrepayOrder(unpaidOrder.getProductId(), unpaidOrder.getProductName(), unpaidOrder.getOrderId(), unpaidOrder.getTotalAmount());
            return PayOrderRes.builder()
                    .orderId(payOrder.getOrderId())
                    .payUrl(payOrder.getPayUrl())
                    .build();
        }

        // 2. 查询商品 & 创建订单
        ProductVO productVO = productRPC.queryProductByProductId(shopCartReq.getProductId());
        String orderId = RandomStringUtils.randomNumeric(16);
        orderDao.insert(PayOrder.builder()
                .userId(shopCartReq.getUserId())
                .productId(shopCartReq.getProductId())
                .productName(productVO.getProductName())
                .orderId(orderId)
                .totalAmount(productVO.getPrice())
                .orderTime(new Date())
                .status(Constants.OrderStatusEnum.CREATE.getCode())
                .build());

        // 3. 创建支付单
        PayOrder payOrder = doPrepayOrder(productVO.getProductId(), productVO.getProductName(), orderId, productVO.getPrice());

        return PayOrderRes.builder()
                .orderId(orderId)
                .payUrl(payOrder.getPayUrl())
                .build();

    }
}
