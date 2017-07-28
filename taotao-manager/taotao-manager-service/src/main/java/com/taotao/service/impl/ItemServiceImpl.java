package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(Long itemId) {

		TbItemExample example = new TbItemExample();

		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);

		List<TbItem> list = itemMapper.selectByExample(example);

		TbItem tbItem = null;
		if (list != null && list.size() > 0) {
			tbItem = list.get(0);
		}

		return tbItem;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {

		PageHelper.startPage(page, rows);

		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) {

		long itemId = IDUtils.genItemId();

		item.setId(itemId);
		item.setStatus((byte) 1);

		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);

		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDescMapper.insert(itemDesc);

		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);

		return TaotaoResult.ok();
	}

	@Override
	public String getItemParamHtml(Long itemId) {

		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		if (list == null || list.isEmpty()) {
			return "";
		}

		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		
		@SuppressWarnings("rawtypes")
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);

		StringBuffer strbuf = new StringBuffer();

		strbuf.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		strbuf.append("    <tbody>\n");

		for (@SuppressWarnings("rawtypes") Map map : mapList) {

			strbuf.append("	    <tr>\n");
			strbuf.append("		    <th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			strbuf.append("		</tr>\n");

			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (@SuppressWarnings("rawtypes") Map map2 : mapList2) {
				strbuf.append("		<tr>\n");
				strbuf.append("		    <td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				strbuf.append("			<td>" + map2.get("v") + "</td>\n");
				strbuf.append("		</tr>\n");
			}

		}

		strbuf.append("	</tbody>\n");
		strbuf.append("</table>");

		return strbuf.toString();
	}

	@Override
	public TaotaoResult deleteItemById(Long itemId) {
		
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		itemMapper.deleteByExample(example);
		
		return TaotaoResult.ok();
	}

}
