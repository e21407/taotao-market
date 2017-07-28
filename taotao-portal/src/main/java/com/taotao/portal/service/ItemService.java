package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	String getItemDescById(long itemId);
	String getItemParamById(long itemId);
}
