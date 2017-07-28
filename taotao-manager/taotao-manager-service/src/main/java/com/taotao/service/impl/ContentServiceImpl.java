package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		
		Date date = new Date();
		
		content.setCreated(date);
		content.setUpdated(date);
		
		contentMapper.insert(content);
		
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(int page, int rows) {
		
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		List<TbContent> list = contentMapper.selectByExample(example);
		
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

}
