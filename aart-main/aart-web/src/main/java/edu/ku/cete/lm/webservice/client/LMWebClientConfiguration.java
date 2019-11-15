/**
 * 
 */
package edu.ku.cete.lm.webservice.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.TimerUtil;

/**
 * @author mahesh
 *
 *1. Loads the necessary configuration for connecting to LM Services.
 *2. For a given input of node ids, returns the LM nodes.
 *3. Delegates the parsing to NodeParser.
 *4. Cleans up the connections prior to IOC destroy on proper shutdown.
 *
 * This class contains the configuration parameters for LM service.
 * Returns the configured client and the params with out the node ids.
 */
@Component
public class LMWebClientConfiguration {

    @Value("${webservice.lm.serviceClassName}")
	private String serviceClassName;
    @Value("${webservice.lm.serviceMethodName}")
	private String serviceMethodName;
    @Value("${webservice.lm.serviceParameters}")
	private String serviceParameters;
    @Value("${webservice.lm.radius}")
	private Integer radius;    
    @Value("${webservice.lm.versionId}")
	private Integer versionId;     
    @Value("${webservice.lm.encoding}")
	private String encoding;
    //For connection.
    @Value("${webservice.lm.host}")
	private String host;    
    @Value("${webservice.lm.protocol}")
	private String protocol;    
    @Value("${webservice.lm.port}")
    private Integer port;
    @Value("${webservice.lm.hostUserName}")
	private String hostUserName;    
    @Value("${webservice.lm.hostPassword}")
	private String hostPassword;    
    @Value("${webservice.lm.postUrl}")
	private String postUrl;
 
	private HttpHost targetHost = null;
	private DefaultHttpClient httpClient = null;
	private AuthCache authCache = null;
	private BasicScheme basicAuth = null;
	private BasicHttpContext ctx = null;
	private HttpPost post = null;
	/**
     * logger.
     */
    private final Logger
    logger = LoggerFactory.getLogger(LMWebClientConfiguration.class);
    @Autowired
	private LmAttributeConfiguration lmAttributeConfiguration;		
    
	/**
	 * Fail safe initialization of lmConnection
	 */
	@PostConstruct
	public void initializeLmConnection(){
		//create one instance of the target host and 
		//multiple instances of execution.HttpClient is multi threaded.
		try{
			
			logger.debug("initializeLmConnection");
			targetHost = new HttpHost(host, port, protocol);
			httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(targetHost.getHostName(), targetHost.getPort()),
					new UsernamePasswordCredentials(hostUserName, hostPassword));
			authCache = new BasicAuthCache();
			basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);
			ctx = new BasicHttpContext();
			ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
			post = new HttpPost(postUrl);
			logger.debug("initializeLmConnection Succeeded");
		}catch(Exception ex){
			logger.error("Exception in connecting to LM host",ex);
		}		
	}
	
	/**
	 * 1. Checks if the connection needs to be initialized.
	 * 2. Initializes connection if necessary.
	 */
	public void checkAndInitialize() {
		if(targetHost == null ||
				httpClient == null ||
				authCache == null ||
				basicAuth == null ||
				ctx == null ||
				post == null
				) {
			logger.warn("initializeLmConnection is necessary");
			cleanUp();
			initializeLmConnection();
		}else {
			logger.debug("initializeLmConnection is not necessary");
		}
	}

	/**
	 * 1. Cleans Lm Connections.
	 * 2. Logs an error on failure.
	 * 3. makes all connection variables as null.
	 */
	@PreDestroy
	public void cleanUp() {
		try {
			logger.debug("Spring Container is destroy! Connection clean up");
			authCache.clear();
			logger.debug("Spring Container is destroy! Cache Cleaned");
			httpClient.getConnectionManager().shutdown();
			logger.debug("Connection clean up Successfull");
		} catch (Exception e) {
			logger.error("Connection clean up Failed",e);
		} finally {
			logger.debug("Connection clean up last step");
			httpClient = null;
			targetHost = null;
			authCache = null;
			basicAuth = null;
			ctx = null;
			post = null;			
		}
	}	
	/**
	 * 1. Initializes connection if necessary.
	 * 2. creates the parameters.
	 * 3. Calls LM host.
	 * 4. Fail safe.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Set<LmNode> getNodes(Set<Long> nodeIds) {
		logger.debug("Getting nodes "+nodeIds);
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		UrlEncodedFormEntity entity = null;  
		HttpEntity nodeEntity = null;
		
		try {
			checkAndInitialize();
			params.add(new BasicNameValuePair("serviceClassName", serviceClassName));
			params.add(new BasicNameValuePair("serviceMethodName", serviceMethodName));
			params.add(new BasicNameValuePair("serviceParameters", serviceParameters));
			params.add(new BasicNameValuePair("nodeIdsString", StringUtil.convertToString(nodeIds)));
			params.add(new BasicNameValuePair("nodeKeysString", ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("radius", radius + ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("versionId", versionId + ParsingConstants.BLANK));
			logger.debug("Created call parameters for node ids "+nodeIds +" as "+params);
			entity = new UrlEncodedFormEntity(params, encoding);
			logger.debug("Created call entity for node ids "+nodeIds +" as "+entity);
			post.setEntity(entity);
			nodeEntity = getNodeEntity(post);
			lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration).getLmNodesforNodeResponse(nodeEntity.getContent());
		} catch (JsonParseException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (JsonMappingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IllegalStateException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IOException e) {
			logger.error("Exception in getting LMNodes ",e);
		}
		return lmNodes;
	}
	/**
	 * 1. Initializes connection if necessary.
	 * 2. creates the parameters.
	 * 3. Calls LM host.
	 * 4. Fail safe.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Set<LmNode> getNodesByKey(Set<String> nodeKeys) {
		logger.debug("Getting nodes "+nodeKeys);
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		UrlEncodedFormEntity entity = null;  
		HttpEntity nodeEntity = null;
		
		try {
			checkAndInitialize();
			params.add(new BasicNameValuePair("serviceClassName", serviceClassName));
			params.add(new BasicNameValuePair("serviceMethodName", serviceMethodName));
			params.add(new BasicNameValuePair("serviceParameters", serviceParameters));
			params.add(new BasicNameValuePair("nodeIdsString", ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("nodeKeysString",
					StringUtil.convertStringCollectionToString(nodeKeys)));
			params.add(new BasicNameValuePair("radius", radius + ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("versionId", versionId + ParsingConstants.BLANK));
			logger.debug("Created call parameters for node ids "+nodeKeys +" as "+params);
			entity = new UrlEncodedFormEntity(params, encoding);
			logger.debug("Created call entity for node ids "+nodeKeys +" as "+entity);
			post.setEntity(entity);
			nodeEntity = getNodeEntity(post);
			TimerUtil timerUtil = TimerUtil.getInstance();
			lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration
					).getLmNodes(nodeEntity.getContent());
			timerUtil.resetAndLog(logger, "Parsing received nodes took ");
		} catch (JsonParseException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (JsonMappingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IllegalStateException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IOException e) {
			logger.error("Exception in getting LMNodes ",e);
		}
		return lmNodes;
	}	
	
	/**
	 * 1. Initializes connection if necessary.
	 * 2. creates the parameters.
	 * 3. Calls LM host.
	 * 4. Fail safe.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Set<LmNode> getNodesByKeyNodeResponse(Set<String> nodeKeys) {
		logger.debug("Getting nodes "+nodeKeys);
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		UrlEncodedFormEntity entity = null;  
		HttpEntity nodeEntity = null;
		
		try {
			checkAndInitialize();
			params.add(new BasicNameValuePair("serviceClassName", serviceClassName));
			params.add(new BasicNameValuePair("serviceMethodName", serviceMethodName));
			params.add(new BasicNameValuePair("serviceParameters", serviceParameters));
			params.add(new BasicNameValuePair("nodeIdsString", ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("nodeKeysString",
					StringUtil.convertStringCollectionToString(nodeKeys)));
			params.add(new BasicNameValuePair("radius", radius + ParsingConstants.BLANK));
			params.add(new BasicNameValuePair("versionId", versionId + ParsingConstants.BLANK));
			logger.debug("Created call parameters for node ids "+nodeKeys +" as "+params);
			entity = new UrlEncodedFormEntity(params, encoding);
			logger.debug("Created call entity for node ids "+nodeKeys +" as "+entity);
			post.setEntity(entity);
			nodeEntity = getNodeEntity(post);
			TimerUtil timerUtil = TimerUtil.getInstance();
			lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration
					).getLmNodesforNodeResponse(nodeEntity.getContent());
			timerUtil.resetAndLog(logger, "Parsing received nodes took ");
		} catch (JsonParseException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (JsonMappingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IllegalStateException e) {
			logger.error("Exception in getting LMNodes ",e);
		} catch (IOException e) {
			logger.error("Exception in getting LMNodes ",e);
		}
		return lmNodes;
	}	
	private HttpEntity getNodeEntity(HttpRequestBase request){
		logger.debug("getNodeEntity for request "+request);
		
		TimerUtil timerUtil = TimerUtil.getInstance();
		HttpResponse resp = null;
		HttpEntity nodeEntity = null;
		try {
			resp = httpClient.execute(targetHost, request, ctx);
			logger.debug("Response is "+resp);
		} catch (ClientProtocolException e) {
			logger.error("Error in getting node entity ",e);
		} catch (IOException e) {
			logger.error("Error in getting node entity ",e);
		}
		nodeEntity = 
		resp.getEntity();
    	timerUtil.resetAndLog(logger, "getNodeEntity from LM took ");
		logger.debug("Response entity is of length" + resp.getEntity().getContentLength());
		return nodeEntity;
	}		
 }
