
import javax.management.Query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class AppTest {

	@Test
	public void testSolr() throws Exception {
		
		SolrServer solrServer = new HttpSolrServer("http://119.29.195.17:8080/solr");
		
//		SolrInputDocument document = new SolrInputDocument();
//		document.setField("id", "solrtest");
//		document.setField("item_title", "testpro");
		
		//solrServer.add(document);
		
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		
	}
	
}
