package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车管理
 * <p>
 * Title: CartController
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 入云龙
 * @date 2015年8月27日上午10:52:53
 * @version 1.0
 */
@Controller
@RequestMapping
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * 添加购物车商品
	 * <p>
	 * Title: addCartItem
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		if (result.getStatus() == 200) {
			return "cart-success";
		}
		return "error/exception";
	}

	/**
	 * 展示购物车商品
	 * <p>
	 * Title: showCart
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(Model model, HttpServletRequest request) {
		List<CartItem> list = cartService.getCatItemList(request);
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping("/cart/update/num/{id}/{num}")
	@ResponseBody
	public TaotaoResult updateNum(@PathVariable Long id, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		
		TaotaoResult result = cartService.updateItemNum(id, num, request,response);
		return result;
	}
	
	@RequestMapping("/cart/delete/{id}")
	public String deleteCartItem(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		
		cartService.deleteCartItem(id, request, response);
		return "redirect:/cart/cart.html";
	}

}
