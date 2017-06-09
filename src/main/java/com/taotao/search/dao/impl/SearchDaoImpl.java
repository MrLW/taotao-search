package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired 
	private SolrServer solrServer ;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//进行查询
		QueryResponse response = solrServer.query(query);
		//求结果
		SolrDocumentList documentList = response.getResults();
		List<SearchItem> itemList = new ArrayList<>() ;
		for (SolrDocument doc : documentList) {
			SearchItem item = new SearchItem() ;
			item.setCategory_name((String) doc.get("item_category_name"));
			item.setId((String) doc.get("id"));
			item.setImage((String) doc.get("item_image"));
			item.setPrice((Long) doc.get("item_price"));
			item.setSell_point((String) doc.get("item_sell_point"));
			//取出高亮
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(doc.get("id")).get("item_title");
			String itemTitle = "" ;
			// 如果高亮不为Null
			if(list != null && list.size() > 0 ){
				itemTitle = list.get(0) ;
			}else{
				itemTitle = (String) doc.get("item_title") ;
			}
			item.setTitle(itemTitle);
			//添加到列表
			itemList.add(item) ;
		}
		// 创建SearchResult 这里只有两个变量，当前页和总页数在service中处理
		SearchResult searchResult = new SearchResult() ;
		searchResult.setRecordCount(documentList.getNumFound());
		searchResult.setItemList(itemList);
		return searchResult;
	}

}
