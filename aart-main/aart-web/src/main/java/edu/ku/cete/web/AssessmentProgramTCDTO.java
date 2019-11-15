package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author vittaly
 * This contains assessment program along with test collection information.
 */
public class AssessmentProgramTCDTO {

	/**
	 * 
	 */
	private Long id;	
	/**
	 * 
	 */
	private Long testCollectionId;
	
	/**
	 * 
	 */
	private String programName;
	
	/**
	 * 
	 */
	private String name;

	/**
	 * @return
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Long getTestCollectionId() {
		return testCollectionId;
	}

	/**
	 * @param testCollectionId
	 */
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}
	
	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();
		
		//Set Id
		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set  Test Collection Id
		if(getTestCollectionId() != null) {
			cells.add(ParsingConstants.BLANK + getTestCollectionId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set  Name
		if(getName() != null) {
			cells.add(ParsingConstants.BLANK + getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
					
		return cells;
	}
	
	
	
}
