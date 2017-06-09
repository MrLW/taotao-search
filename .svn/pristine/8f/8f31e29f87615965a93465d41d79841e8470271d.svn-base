package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJSample {

	@Test
	public void test() throws Exception {
		// 创建SolrJ服务对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.154.129:8080/solr/collection1");
		// 创建SolrDocument对象
		SolrInputDocument document = new SolrInputDocument() ;
		document.addField("id", "solrtest01");
		document.addField("item_title", "标题");
		// 添加document对象到服务中
		solrServer.add(document) ;
		// 提交
		solrServer.commit(); 
	}
}
