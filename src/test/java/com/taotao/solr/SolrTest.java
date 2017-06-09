package com.taotao.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
/**
 *  Solr 测试 
 * @author lw
 */
public class SolrTest {
	// 单机
	@Test
	public void testSolrSingleInsert(){
		try {
			//创建solrserver对象
			SolrServer server = new HttpSolrServer("http://192.168.154.129:8080/solr/collection1"); 
			SolrInputDocument document = new SolrInputDocument() ;
			document.addField("id", "22");
			document.addField("item_title", "title22");
			document.addField("item_sell_point", "sell_point22");
			document.addField("item_price", 22);
			document.addField("item_image", "image22");
			document.addField("item_category_name", "category_name22");
			document.addField("item_desc","desc22");
			server.add(document);
			server.commit() ;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testCloudSolr(){
		try {
			// 1、创建SolrServer对象
			CloudSolrServer server = new CloudSolrServer(
					"192.168.154.129:2181,192.168.154.129:2182,192.168.154.129:2183");
			// 1-1、设置默认collection
			server.setDefaultCollection("collection2");
			// 2、创建doc对象
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", "solrcloud01");
			doc.addField("item_title", "solrcloud test");
			// 3、添加文档
			server.add(doc);
			// 4、提交
			server.commit() ;
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
}
