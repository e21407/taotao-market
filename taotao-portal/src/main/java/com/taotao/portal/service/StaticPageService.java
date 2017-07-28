package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

public interface StaticPageService {

	TaotaoResult getItemHtml(Long itemId) throws Exception;
	
}
