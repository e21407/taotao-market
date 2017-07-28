package com.taotao.portal.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taotao.pojo.TbItem;

public class PortalItem extends TbItem {
	
	private Integer cartItemNum;
	
	
	public Integer getCartItemNum() {
		return cartItemNum;
	}


	public void setCartItemNum(Integer cartItemNum) {
		this.cartItemNum = cartItemNum;
	}


	@JsonIgnore
	public String[] getImages() {
		
		String images = this.getImage();
		
		if(images != null && !("".equals(images))) {
			String[] imgs = images.split(",");
			return imgs;
		}
		
		return null;
	}
}
