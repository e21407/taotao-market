package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {

	EasyUIDataGridResult getItemParamList(int page, int rows);
	
	TaotaoResult getItemParamByCid(Long cid);
	
	TaotaoResult insertItemParam(Long cid, String paramData);
	
}
