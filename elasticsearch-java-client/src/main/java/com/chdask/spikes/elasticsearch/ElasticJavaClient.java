package com.chdask.spikes.elasticsearch;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * local elastic search java client
 * @author Christina Daskalaki
 *
 */
public class ElasticJavaClient {

	public static void main(String[] args) {

		String indexName = "christina-index";
		String documentType = "catalog";
		String documentId = "1"; 
		String json = "{\"name\":\"christina\"}";
		
		//Add documents
		IndexRequestBuilder indexRequestBuilder = client().prepareIndex(indexName, documentType, documentId);
		indexRequestBuilder.setSource(json);
		IndexResponse response = indexRequestBuilder.execute().actionGet();
	
		//Get document
		GetRequestBuilder getRequestBuilder = client().prepareGet(indexName, documentType, documentId);
		getRequestBuilder.setFields(new String[]{"name"});
		GetResponse response1 = getRequestBuilder.execute().actionGet();
		String name = response1.getField("name").getValue().toString();
		System.out.println(name);
	}
	
	public static Client client(){
		//Create Client
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
		TransportClient transportClient = new TransportClient(settings);
		//Default port for the transportClient is 9300 instead of 9200.
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		return (Client) transportClient;
	}
	
	public static void createIndex(Client client, String indexName){
		//Create Index and set settings and mappings
		CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();
	}
}