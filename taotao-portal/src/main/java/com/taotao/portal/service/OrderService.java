package com.taotao.portal.service;

import com.taotao.portal.pojo.OrderInfo;

public interface OrderService {

	//OrderCart getOrderCart(Long userId, HttpServletRequest request, HttpServletResponse response);
	String createOrder(OrderInfo orderInfo);
}
