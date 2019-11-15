/**
 * 
 */
package edu.ku.cete.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.lm.webservice.client.LMNodeParser;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author mahesh
 *
 */
public class TestLMService {
	private HttpHost targetHost = null;
	private DefaultHttpClient httpClient = null;
	private AuthCache authCache = null;
	private BasicScheme basicAuth = null;
	private BasicHttpContext ctx = null;
	private HttpPost post = null;
	private UrlEncodedFormEntity entity = null;
	@Autowired
	private LmAttributeConfiguration lmAttributeConfiguration;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
			System.out.println(">>>SET UP>>>>");
			//Config:host , port, protocol
			targetHost = new HttpHost("lm1.qa.cete.us", 80, "http");
			httpClient = new DefaultHttpClient();
			//Config:username, password
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(targetHost.getHostName(), targetHost.getPort()),
					new UsernamePasswordCredentials("lm_web1@ku.edu", "test"));
			// Create AuthCache instance
			authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local
			// auth cache
			basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);

			// Add AuthCache to the execution context
			ctx = new BasicHttpContext();
			ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

			//Config: postUrl
			post = new HttpPost("/tunnel-web/secure/json");
			lmAttributeConfiguration = new LmAttributeConfiguration();
			lmAttributeConfiguration.setProfileName("Profile");
			lmAttributeConfiguration.setBand("Band");
			lmAttributeConfiguration.setSuperNode("Super Node");
			lmAttributeConfiguration.setConnectionCode("Connection Code");
			lmAttributeConfiguration.setNodesWithVariables("Nodes with Variables");
			lmAttributeConfiguration.setConnectionParameters("Connection Parameters");
			lmAttributeConfiguration.setEssentialElement("EE");
			lmAttributeConfiguration.setCa("CA");
			lmAttributeConfiguration.setCaStandard("CAstandard");
			lmAttributeConfiguration.setConnectionParameters("Connection Parameters");
			lmAttributeConfiguration.setLink("link");
			lmAttributeConfiguration.setTestableSuperNode("Testable Super Node");
			lmAttributeConfiguration.setImportantNodes("Important Nodes");
			lmAttributeConfiguration.setInitialPrecursor("InitialPrecursor");
			lmAttributeConfiguration.setTestableSuperNode("Testable Super Node");
			lmAttributeConfiguration.setDistalPrecursor("DistalPrecursor");
			lmAttributeConfiguration.setProximalPrecursor("ProximalPrecursor");		
			lmAttributeConfiguration.setTarget("target");
			lmAttributeConfiguration.setSuccessor("Successor");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println(">>>TEAR DOWN");
		httpClient.getConnectionManager().shutdown();
		httpClient = null;
		targetHost = null;
		authCache = null;
		basicAuth = null;
		ctx = null;
		post = null;
		entity = null;		
	}

	//@Test
	public void testPrintEntity() throws IOException {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Set<Long> nodeIds = new HashSet<Long>();
			nodeIds.add((long) 17797);
			nodeIds.add((long) 17798);
			Set<String> nodeKeys = new HashSet<String>();
			//nodeKeys.add("ELA-10");
			nodeKeys.add("ELA-29");
			params.add(new BasicNameValuePair("serviceClassName", "edu.ku.cete.lmeditor.service.NodeServiceUtil"));
			params.add(new BasicNameValuePair("serviceMethodName", "getMapDetails"));
			params.add(new BasicNameValuePair("serviceParameters", "[nodeIdsString,nodeKeysString,radius,versionId]"));

			params.add(new BasicNameValuePair("nodeIdsString", StringUtil.convertToString(nodeIds)));
			params.add(new BasicNameValuePair("nodeKeysString", StringUtil.convertStringCollectionToString(nodeKeys)));
			params.add(new BasicNameValuePair("radius", "0"));
			params.add(new BasicNameValuePair("versionId", "-1"));

			try {
				entity = new UrlEncodedFormEntity(params, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			post.setEntity(entity);
			HttpEntity nodeEntity = getNodeEntity(post);
			Assert.notNull(nodeEntity,"Node Entity is empty");
			nodeEntity.writeTo(System.out);
	}
	//@Test
	public void fetchNode() throws JsonParseException, JsonMappingException, IllegalStateException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Long> nodeIds = new HashSet<Long>();
		nodeIds.add((long) 17797);
		nodeIds.add((long) 17798);
		Set<String> nodeKeys = new HashSet<String>();
		nodeKeys.add("ELA-10");
		nodeKeys.add("ELA-123");
		nodeKeys.add("F-4");
		params.add(new BasicNameValuePair("serviceClassName", "edu.ku.cete.lmeditor.service.NodeServiceUtil"));
		params.add(new BasicNameValuePair("serviceMethodName", "getMapDetails"));
		params.add(new BasicNameValuePair("serviceParameters", "[nodeIdsString,nodeKeysString,radius,versionId]"));

		params.add(new BasicNameValuePair("nodeIdsString", StringUtil.convertToString(nodeIds)));
		params.add(new BasicNameValuePair("nodeKeysString", StringUtil.convertStringCollectionToString(nodeKeys)));
		params.add(new BasicNameValuePair("radius", "0"));
		params.add(new BasicNameValuePair("versionId", "-1"));

		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		post.setEntity(entity);
		HttpEntity nodeEntity = getNodeEntity(post);
		Assert.notNull(nodeEntity,"Node Entity is not received");
		Set<LmNode> lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration).getLmNodes(nodeEntity.getContent());
		Assert.notEmpty(lmNodes,"No Lm Nodes received");
		Assert.isTrue(lmNodes.size() == (nodeIds.size() + nodeKeys.size()) ,"No Lm Nodes received"+lmNodes.size());
		System.out.println("Lm Nodes are "+lmNodes);
	}
	@Test
	public void fetchNodeWithAttributes() throws JsonParseException, JsonMappingException, IllegalStateException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> nodeKeys = new HashSet<String>();
		nodeKeys.add("M-2549");
		params.add(new BasicNameValuePair("serviceClassName", "edu.ku.cete.lmeditor.service.NodeServiceUtil"));
		params.add(new BasicNameValuePair("serviceMethodName", "getMapDetails"));
		params.add(new BasicNameValuePair("serviceParameters", "[nodeIdsString,nodeKeysString,radius,versionId]"));

		params.add(new BasicNameValuePair("nodeIdsString", ParsingConstants.BLANK));
		params.add(new BasicNameValuePair("nodeKeysString", StringUtil.convertStringCollectionToString(nodeKeys)));
		params.add(new BasicNameValuePair("radius", "0"));
		params.add(new BasicNameValuePair("versionId", "-1"));

		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		post.setEntity(entity);
		HttpEntity nodeEntity = getNodeEntity(post);
		Assert.notNull(nodeEntity,"Node Entity is not received");
		Set<LmNode> lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration).getLmNodes(nodeEntity.getContent());
		LmNode[] lmNodeArray = new LmNode[1];
		Assert.notEmpty(lmNodes,"No Lm Nodes received");
		Assert.isTrue(lmNodes.size() == (nodeKeys.size()) ,"No Lm Nodes received"+lmNodes.size());
		Assert.noNullElements(lmNodes.toArray() ,"No Lm Nodes received"+lmNodes.size());
		lmNodeArray = lmNodes.toArray(lmNodeArray); 
		Assert.notNull(lmNodeArray[0],"No Lm Nodes received"+lmNodes.size());
		Assert.notNull(lmNodeArray[0].getAttributeAndValues(),"No Lm Node Attributes received"+lmNodes.size());
		Assert.notNull(lmNodeArray[0].getAttributeAndValues().keySet(),"No Lm Node Attributes received"+lmNodes.size());
		Assert.isTrue(lmNodeArray[0].getConceptualArea(lmAttributeConfiguration).equals("ca"),
				"Conceptual Area incorrect"+lmNodeArray[0].getConceptualArea(lmAttributeConfiguration));
		System.out.println("Lm Nodes attributes are "+lmNodeArray[0].getAttributeAndValues());
	}
	private HttpEntity getNodeEntity(HttpRequestBase request){

		HttpResponse resp = null;
		HttpEntity nodeEntity = null;
		try {
			resp = httpClient.execute(targetHost, request, ctx);
			nodeEntity = resp.getEntity();
			//nodeEntity.writeTo(System.out);
			System.out.println("Response is "+resp);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeEntity;
	}	
}
