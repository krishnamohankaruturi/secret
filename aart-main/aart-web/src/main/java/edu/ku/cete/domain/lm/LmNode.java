/**
 * 
 */
package edu.ku.cete.domain.lm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.MapUtils;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author mahesh
 *
 */
public class LmNode {

	private Long nodeId;
	private String nodeKey;
	private String nodeName;
	private Set<String> profiles;
	private Map<String, String> attributeAndValues = new HashMap<String, String>();
	private LmNode() {
		
	}
	public LmNode(String nodeKey) {
		this.nodeKey=nodeKey;
	}
	public LmNode(Long nodeId) {
		this.nodeId=nodeId;
	}
	/**
	 * @return the nodeId
	 */
	public final Long getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public final void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @param nodeId2
	 */
	public void setNodeId(Integer nodeId2) {
		setNodeId(new Long (nodeId2));
	}	
	/**
	 * @return the nodeKey
	 */
	public final String getNodeKey() {
		return nodeKey;
	}
	/**
	 * @param nodeKey the nodeKey to set
	 */
	public final void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the profiles
	 */
	public final Set<String> getProfiles() {
		return profiles;
	}
	/**
	 * @param profiles the profiles to set
	 */
	public final void setProfiles(Set<String> profiles) {
		this.profiles = profiles;
	}
	/**
	 * @param profiles the profiles to set
	 */
	public final void addProfiles(String profile) {
		if(getProfiles() == null) {
			setProfiles(new HashSet<String>());
		}
		getProfiles().add(profile);
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeKey == null) ? 0 : nodeKey.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LmNode other = (LmNode) obj;
		if (nodeKey == null) {
			if (other.nodeKey != null)
				return false;
		} else if (!nodeKey.equals(other.nodeKey))
			return false;
		return true;
	}
	/**
	 * @return the attributeAndValues
	 */
	public final Map<String, String> getAttributeAndValues() {
		return attributeAndValues;
	}
	
	/**
	 * @return the attributeAndValuesForPreview
	 */
	public final Map<String, String> getAttributeAndValuesForPreview() {
		Map<String,String> attributeAndValuesForPreview = new HashMap<String, String>(attributeAndValues);
		attributeAndValuesForPreview.remove("Node Name");
		return attributeAndValuesForPreview;
	}
	
	public final List<String> getAttributeInformation(LmAttributeConfiguration lmAttributeConfiguration) {
		List<String> result = new ArrayList<String>();
		if(attributeAndValues != null && MapUtils.isNotEmpty(attributeAndValues)) {
			for(String attributeKey:attributeAndValues.keySet()) {
				//Defect fix to not to display certain attributes.
				if(!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getSuperNode()) && 
						!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getConnectionCode()) && 
								!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getImportantNodes()) && 
								!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getTestableSuperNode()) &&
								!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getNodesWithVariables()) && 
								!attributeKey.equalsIgnoreCase(lmAttributeConfiguration.getConnectionParametersWithVariables())) {
					result.add(attributeKey + ParsingConstants.EQUAL_TO + attributeAndValues.get(attributeKey));
				}
			}
		}
		return result;
	}
	
	/**
	 * @param attributeAndValues
	 */
	public void setAttributeAndValues(Map<String, String> attributeAndValues) {
		this.attributeAndValues = attributeAndValues;
	}
	
	/**
	 * @param attributeAndValues
	 */
	public void addAttributeAndValues(Map<String, String> attributeAndValues) {
		if(attributeAndValues != null && MapUtils.isNotEmpty(attributeAndValues)) {
			//INFO: if it already contains the values then append.
			this.attributeAndValues = AARTCollectionUtil.merge(
					this.attributeAndValues,attributeAndValues);
		}
	}
	
	/**
	 * @param lmAttributeConfiguration
	 * @return
	 */
	public String getEssentialElement(
			LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getEssentialElement());
	}	
	/**
	 * @return
	 */
	public String getConceptualArea(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getCa());
	}	
	/**
	 * @return
	 */
	public String getBand(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getBand());
	}
	/**
	 * @return
	 */
	public String getCAStandard(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getCaStandard());
	}
	/**
	 * @return
	 */
	public String getLink(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getLink());
	}
	/**
	 * @return
	 */
	public String getInitialPrecursor(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getInitialPrecursor());
	}	
	/**
	 * @return
	 */
	public String getDistalPrecursor(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getDistalPrecursor());
	}	
	/**
	 * @return
	 */
	public String getProximalPrecursor(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getProximalPrecursor());
	}	
	/**
	 * @return
	 */
	public String getTarget(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getTarget());
	}
	/**
	 * @return
	 */
	public String getSuccessor(LmAttributeConfiguration lmAttributeConfiguration) {
		return AARTCollectionUtil.searchKeyInMap(attributeAndValues,
				lmAttributeConfiguration.getSuccessor());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LmNode [");
		if (nodeId != null) {
			builder.append("nodeId=");
			builder.append(nodeId);
			builder.append(", ");
		}
		if (nodeKey != null) {
			builder.append("nodeKey=");
			builder.append(nodeKey);
			builder.append(", ");
		}
		if (nodeName != null) {
			builder.append("nodeName=");
			builder.append(nodeName);
			builder.append(", ");
		}
		if (profiles != null) {
			builder.append("profiles=");
			builder.append(profiles);
		}
		if(attributeAndValues != null) {
			builder.append("attributeAndValues = ");
			builder.append(attributeAndValues);
		}
		builder.append("]");
		return builder.toString();
	}






}
