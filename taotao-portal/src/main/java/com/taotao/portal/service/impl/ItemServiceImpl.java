package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;

	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	
	@Value("${REST_ITEM_PAEAM_URL}")
	private String REST_ITEM_PAEAM_URL;
	
	@Override
	public TbItem getItemById(long itemId) {

		// 调用taotao-rest服务
		String jsonResult = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
		// 转换成taotaoresult
		TaotaoResult result = TaotaoResult.formatToPojo(jsonResult, PortalItem.class);
		
		TbItem item = (TbItem) result.getData();
		return item;
	}

	/**
	 * 取商品描述
	 * <p>
	 * Title: getItemDesc
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemDesc(long)
	 */
	@Override
	public String getItemDescById(long itemId) {

		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
		// 把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
		
		TbItemDesc itemDesc = (TbItemDesc) result.getData();
		String desc = itemDesc.getItemDesc();

		return desc;
	}

	@Override
	public String getItemParamById(long itemId) {
		// 调用taotao-rest服务，请求规格参数数据
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PAEAM_URL + itemId);
		// 转换成taotaoResult对象
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		// 请求出错，返回空串
		TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
		// 取规格参数
		String paramJson = itemParamItem.getParamData();
		// 把规格参数json格式的数据转换成java对象
		@SuppressWarnings("rawtypes")
		List<Map> paramList = JsonUtils.jsonToList(paramJson, Map.class);
		// 根据list生成html
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		strbuf.append("     <tbody>\n");

		for (@SuppressWarnings("rawtypes")
		Map param : paramList) {
			strbuf.append("          <tr>\n");
			strbuf.append("               <th class=\"tdTitle\" colspan=\"2\">" + param.get("group") + "</th>\n");
			strbuf.append("          </tr>\n");
			// 取规格项
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<Map> object = (List<Map>) param.get("params");
			for (@SuppressWarnings("rawtypes")
			Map map : object) {
				strbuf.append("          <tr>\n");
				strbuf.append("               <td class=\"tdTitle\">" + map.get("k") + "</td>\n");
				strbuf.append("               <td>" + map.get("v") + "</td>\n");
				strbuf.append("          </tr>\n");
			}
		}
		strbuf.append("     </tbody>\n");
		strbuf.append("</table>");
		return strbuf.toString();
	}

}
