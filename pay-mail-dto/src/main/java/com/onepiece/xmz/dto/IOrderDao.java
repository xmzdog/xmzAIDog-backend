package com.onepiece.xmz.dto;

import com.onepiece.xmz.domain.po.PayOrder;
import com.onepiece.xmz.domain.req.ShopCartReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiangmz
 * Date: 2025/4/21
 * Time: 19:49
 * Description: No Description
 */
@Mapper
public interface IOrderDao {

    void insert(PayOrder payOrder);

    PayOrder queryUnpayOrder(ShopCartReq shopCartReq) ;
}
