package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * 商品订单管理
 * <p>Title: OrderController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月27日下午4:37:03
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		List<CartItem> list = cartService.getCatItemList(request);
		model.addAttribute("cartList", list);
		//取用户信息
//		TbUser user = (TbUser) request.getAttribute("user");
//		OrderCart orderCart = orderService.getOrderCart(user.getId(), request, response);
//		//把物流信息和购物车商品列表传递给jsp
//		model.addAttribute("shippingList", orderCart.getShippingList());//未实现
//		model.addAttribute("cartList", orderCart.getItemList());
		//返回逻辑视图
		return "order-cart";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request, Model model) {
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用service创建订单
		String orderId = orderService.createOrder(orderInfo);
		
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
		
	}
}
