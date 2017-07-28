package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_COTENT_SYNC_URL}")
	private String REST_COTENT_SYNC_URL;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		HttpClientUtil.doGet(REST_BASE_URL + REST_COTENT_SYNC_URL + content.getCategoryId());
		return result;
	}
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page, Integer rows) {
		EasyUIDataGridResult result = contentService.getContentList(page, rows);
		return result;
	}
	
}
