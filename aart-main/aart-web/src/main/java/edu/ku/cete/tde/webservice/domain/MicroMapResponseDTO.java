package edu.ku.cete.tde.webservice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author vittaly
 * DTO object to hold micromap response data from LM.
 *
 */
public class MicroMapResponseDTO {

	
	/**
	 * microMapId
	 */
	private Long micromapid;
	
	/**
	 * microMapName
	 */
	private String micromapname;
	
	/**
	 * associatedEE 
	 */
	private String associatedee;
	
	/**
	 * nodeId
	 */
	private Long nodeid;
	
	/**
	 * LinkageLabel
	 */
	private String linkagelabel;
	
	/**
	 * nodeKey
	 */
	private String nodekey;
	
	/**
	 * nodeName
	 */
	private String nodename;
	/**
	 * linkagelevelshortdesc
	 */
	private String linkagelevelshortdesc;
	/**
	 * linkagelevellongdesc
	 */
	private String linkagelevellongdesc;
	
	/**
	 * nodedescription
	 */
	private String nodedescription;
		
	/**
	 * versionid
	 */
	private Long versionid;
	
	/**
	 * versionname
	 */
	private String versionname;
	
	/**
	 * versionnumber
	 */
	private Float versionnumber;

	/**
	 * @return
	 */
	public Long getMicromapid() {
		return micromapid;
	}

	/**
	 * @param micromapid
	 */
	public void setMicromapid(Long micromapid) {
		this.micromapid = micromapid;
	}

	/**
	 * @return
	 */
	public String getMicromapname() {
		return micromapname;
	}

	/**
	 * @param micromapname
	 */
	public void setMicromapname(String micromapname) {
		this.micromapname = micromapname;
	}

	/**
	 * @return
	 */
	public String getAssociatedee() {
		return associatedee;
	}

	/**
	 * @param associatedee
	 */
	public void setAssociatedee(String associatedee) {
		this.associatedee = associatedee;
	}

	/**
	 * @return
	 */
	public Long getNodeid() {
		return nodeid;
	}

	/**
	 * @param nodeid
	 */
	public void setNodeid(Long nodeid) {
		this.nodeid = nodeid;
	}

	/**
	 * @return
	 */
	public String getLinkagelabel() {
		return linkagelabel;
	}

	/**
	 * @param linkagelabel
	 */
	public void setLinkagelabel(String linkagelabel) {
		this.linkagelabel = linkagelabel;
	}

	/**
	 * @return
	 */
	public String getNodekey() {
		return nodekey;
	}

	/**
	 * @param nodekey
	 */
	public void setNodekey(String nodekey) {
		this.nodekey = nodekey;
	}

	/**
	 * @return
	 */
	public String getNodename() {
		return nodename;
	}

	/**
	 * @param nodename
	 */
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	/**
	 * LinkageLevelShortDesc
	 */
	//private String LinkageLevelShortDesc;
	
	/**
	 * LinkageLevelLongDesc
	 */
	//private String LinkageLevelLongDesc;
	
	private String testavailabile;
	
	private String actualLinkageLevel;
	
	/**
	 * @return
	 */
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();
		
		if(this.getMicromapid() != null) {			
			cells.add(String.valueOf(this.getNodeid()));
		} else  {			
			cells.add(ParsingConstants.NOT_AVAILABLE);
		} 
		if(this.getTestavailabile() != null) {
			cells.add(this.getTestavailabile());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Setting Linkagelabel value		
		if(this.getLinkagelabel() != null) {			
			cells.add(this.getLinkagelabel());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if (this.getLinkagelevelshortdesc() != null){
			cells.add(this.getLinkagelevelshortdesc());
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if (this.getLinkagelevellongdesc() != null){
			cells.add(this.getLinkagelevellongdesc());
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(this.getActualLinkageLevel() != null) {
			cells.add(this.getActualLinkageLevel());
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		cells.add(ParsingConstants.NOT_AVAILABLE);
		
		return cells;
	}

	public String getLinkagelevelshortdesc() {
		return linkagelevelshortdesc;
	}

	public void setLinkagelevelshortdesc(String linkagelevelshortdesc) {
		this.linkagelevelshortdesc = linkagelevelshortdesc;
	}

	public String getLinkagelevellongdesc() {
		return linkagelevellongdesc;
	}

	public void setLinkagelevellongdesc(String linkagelevellongdesc) {
		this.linkagelevellongdesc = linkagelevellongdesc;
	}

	public String getNodedescription() {
		return nodedescription;
	}

	public void setNodedescription(String nodedescription) {
		this.nodedescription = nodedescription;
	}
	
	public Long getVersionid() {
		return versionid;
	}

	public void setVersionid(Long versionid) {
		this.versionid = versionid;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public Float getVersionnumber() {
		return versionnumber;
	}

	public void setVersionnumber(Float versionnumber) {
		this.versionnumber = versionnumber;
	}

	public String getTestavailabile() {
		return testavailabile;
	}

	public void setTestavailabile(String testavailabile) {
		this.testavailabile = testavailabile;
	}

	/**
	 * @return the actualLinkageLevel
	 */
	public String getActualLinkageLevel() {
		return actualLinkageLevel;
	}

	/**
	 * @param actualLinkageLevel the actualLinkageLevel to set
	 */
	public void setActualLinkageLevel(String actualLinkageLevel) {
		this.actualLinkageLevel = actualLinkageLevel;
	}		
}
