package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		
		TbItemDescExample example = new TbItemDescExample();
		com.taotao.pojo.TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		List<TbItemDesc> list = itemDescMapper.selectByExample(example);
		
		TbItemDesc itemDesc = null;
		if(list != null && list.size() > 0) {
			itemDesc = list.get(0);
		}
		
		return itemDesc;
	}

}
