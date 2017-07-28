package com.taotao.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class StaticPageServiceImpl implements StaticPageService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;

	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	
	@Value("${REST_ITEM_PAEAM_URL}")
	private String REST_ITEM_PAEAM_URL;

	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Override
	public TaotaoResult getItemHtml(Long itemId) throws Exception {
		
		TbItem tbItem = itemService.getItemById(itemId);
		String itemDesc = itemService.getItemDescById(itemId);
		String itemParam = itemService.getItemParamById(itemId);
		
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("item", tbItem);
		root.put("itemDesc", itemDesc);
		root.put("itemParam", itemParam);
		
		Writer out = new FileWriter(new File(STATIC_PAGE_PATH + itemId + ".html"));
		
		template.process(root, out);
		out.flush();
		out.close();
		
		return TaotaoResult.ok();
	}

}
