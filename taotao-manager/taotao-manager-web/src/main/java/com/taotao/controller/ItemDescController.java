package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;

@Controller
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/desc/page")
	@ResponseBody
	public TbItemDesc getItemDesc(@RequestParam("item-edit") Long itemId) {
		TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
		return itemDesc;
	}
	
}
