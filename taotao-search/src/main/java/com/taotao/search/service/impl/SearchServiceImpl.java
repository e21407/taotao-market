package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(queryString);
		
		solrQuery.setStart((int) ((page - 1) * rows));
		solrQuery.setRows(rows);
		
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font class=\"skcolor:ljg\">");
		solrQuery.setHighlightSimplePost("</font>");
		
		SearchResult searchResult = searchDao.search(solrQuery);
		
		long total = searchResult.getRecrdCount();
		long pageCount = total / rows;
		if (total % rows > 0) {
			pageCount++;
		}
		searchResult.setPageCount((int) pageCount);
		searchResult.setCurPage(page);
		//返回结果
		return searchResult;
	}

}
