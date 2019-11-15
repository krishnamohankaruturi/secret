package edu.ku.cete.domain.common;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.util.ParsingConstants;

/**
 *
 * @author ntipton
 *
 */
public class OrganizationDetail implements Serializable, Identifiable {

    /**
	 *  Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -1924658131846377352L;
	
	/** The id of the organizationDetail. */
    private Long id;
    /**
     * id of the organization.
     */
    private Long organizationid;/**
     * name of the organization.
     */
    private String organizationName;
    
    private Date itiStartDate;
    
    private Date itiEndDate;
    
    private Boolean activeFlag;
    
    private Date createdDate;
    
    private Date modifiedDate;
   
    private Integer createdUser;
    
    private Integer modifiedUser;
    
    private String orgJsonString;
    
    private Long testingCycleId;
    private Boolean instructionPlannerWindow;
    private String instructionPlannerDisplayName;
    private String testingCycleName;
    
    private Date windowEffectiveDate;
    private Date windowExpiryDate;
	
	public Long getOrganizationId() {
		return organizationid;
	}

	/**
	 * @param assessmentProgramId the assessmentProgramId to set
	 */
	public void setOrganizationId(Long OrganizationId) {
		this.organizationid = OrganizationId;
	}

	public OrganizationDetail(){
    	
    }
	
	public OrganizationDetail(long id){
    	this.id = id;
    }
    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param newId the id to set
     */
    public final void setId(final Long newId) {
        this.id = newId;
    }
    /**
	 * @return the organizationName.
	 */
	public final String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param orgName the organizationName to set
	 */
	public final void setOrganizationName(String orgName) {
		this.organizationName = orgName;
	}

	public Date getItiStartDate() {
		return itiStartDate;
	}

	public void setItiStartDate(Date itiStartDate) {
		this.itiStartDate = itiStartDate;
	}
	
	public Date getItiEndDate() {
		return itiEndDate;
	}

	public void setItiEndDate(Date itiEndDate) {
		this.itiEndDate = itiEndDate;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeflag) {
		this.activeFlag = activeflag;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Integer createdUser) {
		this.createdUser = createdUser;
	}

	public Integer getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Integer modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	
	public Long getId(int order) {
		return getId();
	}
	
	public String getStringIdentifier(int order) {		
		return null;
	}
	
	 public Long getTestingCycleId() {
		return testingCycleId;
	}

	public void setTestingCycleId(Long testingCycleId) {
		this.testingCycleId = testingCycleId;
	}

	public String getInstructionPlannerDisplayName() {
		return instructionPlannerDisplayName;
	}

	public void setInstructionPlannerDisplayName(String instructionPlannerDisplayName) {
		this.instructionPlannerDisplayName = instructionPlannerDisplayName;
	}

	public String getTestingCycleName() {
		return testingCycleName;
	}

	public void setTestingCycleName(String testingCycleName) {
		this.testingCycleName = testingCycleName;
	}

	public Boolean getInstructionPlannerWindow() {
		return instructionPlannerWindow;
	}

	public void setInstructionPlannerWindow(Boolean instructionPlannerWindow) {
		this.instructionPlannerWindow = instructionPlannerWindow;
	}

	 /**
		 * Method to construct the json list.
		 * 
		 * @return
		 */
		public List<String> buildJSONRow() {
			List<String> cells = new ArrayList<String>();

			if(getId() != null) {
				cells.add(ParsingConstants.BLANK + getId());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getOrganizationId() != null) {
				cells.add(ParsingConstants.BLANK + getOrganizationId());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getOrganizationName() != null) {
				String orgName = getOrganizationName();
				orgName = Character.toUpperCase(orgName.charAt(0)) + orgName.substring(1);
				cells.add(ParsingConstants.BLANK + orgName);
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			
			if(getItiStartDate() != null) {
				cells.add(ParsingConstants.BLANK + convertDateFormat(getItiStartDate(), "MM/dd/yyyy hh:mm:ss a"));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getItiEndDate() != null) {
				cells.add(ParsingConstants.BLANK + convertDateFormat(getItiEndDate(), "MM/dd/yyyy hh:mm:ss a"));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getActiveFlag() != null) {
				cells.add(ParsingConstants.BLANK + getActiveFlag());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getCreatedDate() != null) {
				cells.add(ParsingConstants.BLANK + getCreatedDate());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getCreatedUser()!= null) {
				cells.add(ParsingConstants.BLANK + getCreatedUser());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getModifiedDate() != null) {
				cells.add(ParsingConstants.BLANK + getModifiedDate());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getModifiedUser() != null) {
				cells.add(ParsingConstants.BLANK + getModifiedUser());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			
			if (getTestingCycleName() != null) {
				cells.add(ParsingConstants.BLANK + getTestingCycleName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			return cells;
		}
		
		public String convertDateFormat(Date date, String format) {
			DateFormat cstTimeZoneFormatter1 = new SimpleDateFormat(format);
			String dateString = cstTimeZoneFormatter1.format(date);
			return dateString;
		}
		
	public String buildJsonString(){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			//Create Object tree for user object
			JsonNode root = mapper.createObjectNode();
			
			((ObjectNode)root).put("OrganizationDetailId", getId());
			((ObjectNode)root).put("OrganizationId", getOrganizationId());
			((ObjectNode)root).put("OrganizationName",getOrganizationName());
			((ObjectNode)root).put("ITIStartDate",getItiStartDate() == null? " ":getItiStartDate().toString());
			((ObjectNode)root).put("ITIEndDate",getItiEndDate() == null? " ":getItiEndDate().toString());
			((ObjectNode)root).put("ModifiedDate",getModifiedDate() == null? " ":getModifiedDate().toString());
			((ObjectNode)root).put("ModifiedUser",getModifiedUser() );
			((ObjectNode)root).put("CreatedDate",getCreatedDate() == null? " ":getCreatedDate().toString());
			((ObjectNode)root).put("CreatedUser",getCreatedUser());
			((ObjectNode)root).put("ActiveFlag",getActiveFlag());
			
			
			orgJsonString = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return orgJsonString;
	}

	public Date getWindowEffectiveDate() {
		return windowEffectiveDate;
	}

	public void setWindowEffectiveDate(Date windowEffectiveDate) {
		this.windowEffectiveDate = windowEffectiveDate;
	}

	public Date getWindowExpiryDate() {
		return windowExpiryDate;
	}

	public void setWindowExpiryDate(Date windowExpiryDate) {
		this.windowExpiryDate = windowExpiryDate;
	}
}
