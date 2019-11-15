package edu.ku.cete.configuration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mahesh
 * This class holds the attribute names of the attributes
 * mapped in the properties file
 * 
 * 
lmprofile.name=Profile
superNode=Super Node
connectionCode=Connection Code
nodesWithVariables=Nodes with Variables
connectionParameters=Connection Parameters
essentialElement=EE
ca=CA
band=Band
caStandard=CAstandard
link=link
testableSuperNode=Testable Super Node
importantNodes=Important Nodes
initialPrecursor=InitialPrecursor
distalPrecursor=DistalPrecursor
proximalPrecursor=ProximalPrecursor
target=target
successor=Successor
 * 
 */
@Component
public class LmAttributeConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(LmAttributeConfiguration.class);
	
	@Value("${lmprofile.name}")
	private String profileName;
	
	@Value("${lmattribute.superNode}")
	private String superNode;

	@Value("${lmattribute.connectionCode}")
	private String connectionCode;	
	
	@Value("${lmattribute.nodesWithVariables}")
	private String nodesWithVariables;

	@Value("${lmattribute.connectionParameters}")
	private String connectionParameters;

	@Value("${lmattribute.essentialElement}")
	private String essentialElement;

	@Value("${lmattribute.ca}")
	private String ca;
	
	@Value("${lmattribute.band}")
	private String band;
	
	@Value("${lmattribute.caStandard}")
	private String caStandard;	

	@Value("${lmattribute.link}")
	private String link;

	@Value("${lmattribute.testableSuperNode}")
	private String testableSuperNode;
	
	@Value("${lmattribute.importantNodes}")
	private String importantNodes;
	
	@Value("${lmattribute.initialPrecursor}")
	private String initialPrecursor;		
	
	@Value("${lmattribute.distalPrecursor}")
	private String distalPrecursor;	
	
	@Value("${lmattribute.proximalPrecursor}")
	private String proximalPrecursor;	
	
	@Value("${lmattribute.target}")
	private String target;
	
	@Value("${lmattribute.successor}")
	private String successor;
		
	@Value("${lmattribute.connectionParametersWithVariables}")
	private String connectionParametersWithVariables;
	

	/**
	 * @return the profileName
	 */
	public final String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public final void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the superNode
	 */
	public final String getSuperNode() {
		return superNode;
	}

	/**
	 * @param superNode the superNode to set
	 */
	public final void setSuperNode(String superNode) {
		this.superNode = superNode;
	}

	/**
	 * @return the connectionCode
	 */
	public final String getConnectionCode() {
		return connectionCode;
	}

	/**
	 * @param connectionCode the connectionCode to set
	 */
	public final void setConnectionCode(String connectionCode) {
		this.connectionCode = connectionCode;
	}

	/**
	 * @return the nodesWithVariables
	 */
	public final String getNodesWithVariables() {
		return nodesWithVariables;
	}

	/**
	 * @param nodesWithVariables the nodesWithVariables to set
	 */
	public final void setNodesWithVariables(String nodesWithVariables) {
		this.nodesWithVariables = nodesWithVariables;
	}

	/**
	 * @return the connectionParameters
	 */
	public final String getConnectionParameters() {
		return connectionParameters;
	}

	/**
	 * @param connectionParameters the connectionParameters to set
	 */
	public final void setConnectionParameters(String connectionParameters) {
		this.connectionParameters = connectionParameters;
	}

	/**
	 * @return the essentialElement
	 */
	public final String getEssentialElement() {
		return essentialElement;
	}

	/**
	 * @param essentialElement the essentialElement to set
	 */
	public final void setEssentialElement(String essentialElement) {
		this.essentialElement = essentialElement;
	}

	/**
	 * @return the ca
	 */
	public final String getCa() {
		return ca;
	}

	/**
	 * @param ca the ca to set
	 */
	public final void setCa(String ca) {
		this.ca = ca;
	}

	/**
	 * @return the band
	 */
	public final String getBand() {
		return band;
	}

	/**
	 * @param band the band to set
	 */
	public final void setBand(String band) {
		this.band = band;
	}

	/**
	 * @return the caStandard
	 */
	public final String getCaStandard() {
		return caStandard;
	}

	/**
	 * @param caStandard the caStandard to set
	 */
	public final void setCaStandard(String caStandard) {
		this.caStandard = caStandard;
	}

	/**
	 * @return the link
	 */
	public final String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public final void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the testableSuperNode
	 */
	public final String getTestableSuperNode() {
		return testableSuperNode;
	}

	/**
	 * @param testableSuperNode the testableSuperNode to set
	 */
	public final void setTestableSuperNode(String testableSuperNode) {
		this.testableSuperNode = testableSuperNode;
	}

	/**
	 * @return the importantNodes
	 */
	public final String getImportantNodes() {
		return importantNodes;
	}

	/**
	 * @param importantNodes the importantNodes to set
	 */
	public final void setImportantNodes(String importantNodes) {
		this.importantNodes = importantNodes;
	}

	/**
	 * @return the initialPrecursor
	 */
	public final String getInitialPrecursor() {
		return initialPrecursor;
	}

	/**
	 * @param initialPrecursor the initialPrecursor to set
	 */
	public final void setInitialPrecursor(String initialPrecursor) {
		this.initialPrecursor = initialPrecursor;
	}

	/**
	 * @return the distalPrecursor
	 */
	public final String getDistalPrecursor() {
		return distalPrecursor;
	}

	/**
	 * @param distalPrecursor the distalPrecursor to set
	 */
	public final void setDistalPrecursor(String distalPrecursor) {
		this.distalPrecursor = distalPrecursor;
	}

	/**
	 * @return the proximalPrecursor
	 */
	public final String getProximalPrecursor() {
		return proximalPrecursor;
	}

	/**
	 * @param proximalPrecursor the proximalPrecursor to set
	 */
	public final void setProximalPrecursor(String proximalPrecursor) {
		this.proximalPrecursor = proximalPrecursor;
	}

	/**
	 * @return the target
	 */
	public final String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public final void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the successor
	 */
	public final String getSuccessor() {
		return successor;
	}

	/**
	 * @param successor the successor to set
	 */
	public final void setSuccessor(String successor) {
		this.successor = successor;
	}
	
	/**
	 * @return the connectionParametersWithVariables
	 */
	public final String getConnectionParametersWithVariables() {
		return connectionParametersWithVariables;
	}

	/**
	 * @param connectionParametersWithVariables the connectionParametersWithVariables to set
	 */
	public final void setConnectionParametersWithVariables(String connectionParametersWithVariables) {
		this.connectionParametersWithVariables = connectionParametersWithVariables;
	}
	
	@PostConstruct
	public void initialize() {
		logger.debug("Successor "+successor);
	}
	
}
