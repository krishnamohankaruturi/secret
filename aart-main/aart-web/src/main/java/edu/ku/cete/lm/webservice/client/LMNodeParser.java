/**
 * 
 */
package edu.ku.cete.lm.webservice.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.lm.LmNode;

/**
 * Parses from content to LMnodes.
 * @author mahesh
 *
 */
public class LMNodeParser {
    /**
     * logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(LMNodeParser.class);		
    /**
     * lmAttributeConfiguration.
     */
    private LmAttributeConfiguration lmAttributeConfiguration;
	/**
	 * mapper.
	 */
	private static final ObjectMapper mapper = new ObjectMapper();//can be shared globally.
	
	private LMNodeParser(LmAttributeConfiguration lmAttributeConfiguration2) {
		this.lmAttributeConfiguration = lmAttributeConfiguration2;
	}

	public static LMNodeParser getInstance(LmAttributeConfiguration lmAttributeConfiguration) {
		return new LMNodeParser(lmAttributeConfiguration);
	}

	
	/**
	 * Parses from content to a list of LMnodes with only the needed parameters.
	 * @param inputStream
	 * @param nodeIds
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Set<LmNode> getLmNodes(InputStream inputStream)
			throws JsonParseException, JsonMappingException, IOException {
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		
		Map<String, Object> userData = mapper.readValue(inputStream, Map.class);
		ArrayList<LinkedHashMap> nodes = (ArrayList<LinkedHashMap>) userData
				.get("nodes");
		int i = 0;
		for (LinkedHashMap node : nodes) {
			i++;
			Long nodeId = new Long((Integer) node.get("nodeId"));
			logger.debug("Node Id #" + i + "=" + nodeId);
				LmNode lmNode = new LmNode(nodeId);
				lmNode.setNodeId(nodeId);
				String nodeName = (String) node.get("nodeName");
				lmNode.setNodeName(nodeName);
				logger.debug("Node Name #" + i + "="
						+ nodeName);
				String nodeKey = (String) node.get("nodeKey");
				logger.debug("Node Key #" + i + "=" + nodeKey);
				lmNode.setNodeKey(nodeKey);
				ArrayList<LinkedHashMap> attributes = (ArrayList<LinkedHashMap>) node
						.get("attributes");
				Set<String> profileAttributes = new HashSet<String>();
				int j = 0;
				for (LinkedHashMap attributeMap : attributes) {
					Map<String,String> attributeAndValues = new HashMap<String, String>();
					j++;
					Integer attributeId = (Integer) attributeMap
							.get("attributeId");
					if (attributeId.intValue() > -1) {
						String attributeTypeName = (String) attributeMap
								.get("attributeTypeName");
						
						if(!attributeTypeName.equalsIgnoreCase(lmAttributeConfiguration.getCa()) && 
								!attributeTypeName.equalsIgnoreCase(lmAttributeConfiguration.getCaStandard()) && 
								!attributeTypeName.equalsIgnoreCase(lmAttributeConfiguration.getBand()) &&
									!attributeTypeName.equalsIgnoreCase(lmAttributeConfiguration.getEssentialElement())) {
							if (StringUtils.hasText(attributeTypeName)
									&& attributeTypeName.contains(lmAttributeConfiguration.getProfileName())) {
								
								attributeTypeName = attributeTypeName.replaceAll(
										lmAttributeConfiguration.getProfileName(), "");
								attributeTypeName = attributeTypeName.replaceAll(
										":", "");
								attributeTypeName = attributeTypeName.trim();
								Integer attributeTypeValue = Integer
										.parseInt((String) attributeMap
												.get("attributeValue"));
								if (attributeTypeValue == 0) {
									logger.debug("profile attribute is "
											+ attributeTypeName + " has value "
											+ attributeTypeValue);
									profileAttributes.add(attributeTypeName);
								}
							}
							
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getSuperNode(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getConnectionCode(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getNodesWithVariables(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getConnectionParameters(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getEssentialElement(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getCa(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getBand(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getCaStandard(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getLink(), attributeMap);
	
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getTestableSuperNode(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getImportantNodes(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getInitialPrecursor(), attributeMap);
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getDistalPrecursor(), attributeMap);	
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getProximalPrecursor(), attributeMap);	
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getTarget(), attributeMap);	
							attributeAndValues = addToMap(attributeAndValues,
									lmAttributeConfiguration.getSuccessor(), attributeMap);	
							
							lmNode.addAttributeAndValues(attributeAndValues);
						}
					}
				}
				lmNode.setProfiles(profileAttributes);
				logger.debug("Added LMNode" + lmNode);
				lmNodes.add(lmNode);
		}
		return lmNodes;
	}
	/**
	 * Parses from content to a list of LMnodes with only the needed parameters.
	 * @param inputStream
	 * @param nodeIds
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Set<LmNode> getLmNodesforNodeResponse(InputStream inputStream)
			throws JsonParseException, JsonMappingException, IOException {
		Set<LmNode> lmNodes = new HashSet<LmNode>();
		
		Map<String, Object> userData = mapper.readValue(inputStream, Map.class);
		ArrayList<LinkedHashMap> nodes = (ArrayList<LinkedHashMap>) userData
				.get("nodes");
		int i = 0;
		for (LinkedHashMap node : nodes) {
			i++;
			Long nodeId = new Long((Integer) node.get("nodeId"));
			logger.debug("Node Id #" + i + "=" + nodeId);
				LmNode lmNode = new LmNode(nodeId);
				lmNode.setNodeId(nodeId);
				String nodeName = (String) node.get("nodeName");
				lmNode.setNodeName(nodeName);
				logger.debug("Node Name #" + i + "="
						+ nodeName);
				String nodeKey = (String) node.get("nodeKey");
				logger.debug("Node Key #" + i + "=" + nodeKey);
				lmNode.setNodeKey(nodeKey);
				ArrayList<LinkedHashMap> attributes = (ArrayList<LinkedHashMap>) node
						.get("attributes");
				Set<String> profileAttributes = new HashSet<String>();
				int j = 0;
				for (LinkedHashMap attributeMap : attributes) {
					Map<String,String> attributeAndValues = new HashMap<String, String>();
					j++;
					Integer attributeId = (Integer) attributeMap
							.get("attributeId");
					if (attributeId.intValue() > -1) {
						String attributeTypeName = (String) attributeMap
								.get("attributeTypeName");
						
						
						if (StringUtils.hasText(attributeTypeName)
								&& attributeTypeName.contains(lmAttributeConfiguration.getProfileName())) {
							
							attributeTypeName = attributeTypeName.replaceAll(
									lmAttributeConfiguration.getProfileName(), "");
							attributeTypeName = attributeTypeName.replaceAll(
									":", "");
							attributeTypeName = attributeTypeName.trim();
							Integer attributeTypeValue = Integer
									.parseInt((String) attributeMap
											.get("attributeValue"));
							if (attributeTypeValue == 0) {
								logger.debug("profile attribute is "
										+ attributeTypeName + " has value "
										+ attributeTypeValue);
								profileAttributes.add(attributeTypeName);
							}
						}
						
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getSuperNode(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getConnectionCode(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getNodesWithVariables(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getConnectionParameters(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getEssentialElement(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getCa(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getBand(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getCaStandard(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getLink(), attributeMap);

						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getTestableSuperNode(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getImportantNodes(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getInitialPrecursor(), attributeMap);
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getDistalPrecursor(), attributeMap);	
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getProximalPrecursor(), attributeMap);	
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getTarget(), attributeMap);	
						attributeAndValues = addToMap(attributeAndValues,
								lmAttributeConfiguration.getSuccessor(), attributeMap);	
						
						lmNode.addAttributeAndValues(attributeAndValues);
					
					}
				}
				lmNode.setProfiles(profileAttributes);
				logger.debug("Added LMNode" + lmNode);
				lmNodes.add(lmNode);
		}
		return lmNodes;
	}
	/**
	 * @param attributeAndValues
	 * @param attributeTypeName
	 * @param attributeMap
	 * @return
	 */
	private static Map<String, String> addToMap(
			Map<String, String> attributeAndValues,
			String attributeTypeName, LinkedHashMap attributeMap) {
		String attributeTypeNameValue = null;
		if(attributeAndValues != null
				&& attributeMap != null
				&& attributeTypeName != null
				&& StringUtils.hasText(attributeTypeName)) {
			attributeTypeNameValue = getAttributeTypeNameValue(attributeTypeName, attributeMap);
			if(attributeTypeNameValue != null && StringUtils.hasText(attributeTypeNameValue)) {
				attributeAndValues.put(attributeTypeNameValue,
						getAttributeValue(attributeMap));
			}
		}
		return attributeAndValues;
	}

	/**
	 * This is to differentiate similarly named attributes like CA and CAStandard 
	 * @param attributeTypeName
	 * @param attributeMap
	 * @return
	 */
	private static String getAttributeTypeNameValueByExactMatch(String attributeTypeName,
			LinkedHashMap attributeMap) {
		String attributeTypeNameValue = (String) attributeMap
				.get("attributeTypeName");
		String requiredAttributeTypeNameValue = null;
		if (attributeMap != null && StringUtils.hasText(attributeTypeName)
				&& attributeTypeNameValue.equalsIgnoreCase(attributeTypeName)
				&& attributeTypeNameValue.indexOf(attributeTypeName) >= 0) {
			requiredAttributeTypeNameValue = attributeTypeNameValue;
		}
		return requiredAttributeTypeNameValue;
	}	
	
	/**
	 * @param attributeTypeName
	 * @param attributeMap
	 * @return
	 */
	private static String getAttributeTypeNameValue(String attributeTypeName,
			LinkedHashMap attributeMap) {
		
		String attributeTypeNameValue = (String) attributeMap
				.get("attributeTypeName");
		String requiredAttributeTypeNameValue
		= getAttributeTypeNameValueByExactMatch(
				attributeTypeNameValue, attributeMap);
		
		if (requiredAttributeTypeNameValue == null
				&& attributeMap != null && StringUtils.hasText(attributeTypeName)
				&& attributeTypeNameValue.contains(attributeTypeName)
				&& attributeTypeNameValue.indexOf(attributeTypeName) >= 0) {
			requiredAttributeTypeNameValue = attributeTypeNameValue;
		}
		return requiredAttributeTypeNameValue;
	}
	private static String getAttributeValue(LinkedHashMap attributeMap) {
		String attributeTypeValue = null;
		if (attributeMap != null) {
			attributeTypeValue = (String) attributeMap
					.get("attributeValue");
		}
		return attributeTypeValue;
	}
}
