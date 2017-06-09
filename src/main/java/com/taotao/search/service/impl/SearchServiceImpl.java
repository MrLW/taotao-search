package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 创建查询条件
		SolrQuery query = new SolrQuery() ;
		query.setQuery(queryString) ;
		//分页处理
		query.setStart((page-1)*rows) ;
		query.setRows(rows) ;
		// 设置默认搜索域
		query.set("df", "item_title") ;
		// 设置高亮
		query.setHighlight(true) ;
		//添加搜索域
		query.addHighlightField("item_title") ;
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		query.setHighlightSimplePost("</font>");
		// 开始 执行查询
		SearchResult result = searchDao.search(query);
		//开始计算总页数
		Long recordCount = result.getRecordCount() ;
//		int pageCount =(int) (( recordCount % rows == 0) ? (recordCount / rows ): (recordCount / rows + 1)) ;
		int pageCount = (int) (recordCount / rows) ;
		if(recordCount % rows > 0 ){
			pageCount++ ;
		}
		result.setPageCount(pageCount); 
		// 设置当前页
		result.setCurPage(page);
		return result;
	}

}
