package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public TaotaoResult imprtItems() throws Exception {

		List<SearchItem> list = itemMapper.getSearchItemList();
		System.out.println(list);
		System.out.println(list.size());
		int i = 0;
		for (SearchItem item : list) {

			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_desc());

			solrServer.add(document);
			System.out.println("finish..." + (++i));

		}
		System.out.println("finish before commit");
		solrServer.commit();
		System.out.println("finish after commit");
		return TaotaoResult.ok();
	}

}
