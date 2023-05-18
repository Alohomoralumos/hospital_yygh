package com.fufu.yygh.order.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fufu.yygh.model.order.OrderInfo;
import com.fufu.yygh.model.user.UserInfo;
import com.fufu.yygh.vo.order.OrderCountQueryVo;
import com.fufu.yygh.vo.order.OrderQueryVo;

import java.util.Map;

public interface OrderService extends IService<OrderInfo> {
    Long saveOrder(String scheduleId, Long patientId);

    OrderInfo getOrder(String orderId);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    Boolean cancelOrder(Long orderId);

    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
