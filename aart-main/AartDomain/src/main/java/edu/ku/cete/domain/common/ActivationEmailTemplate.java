package edu.ku.cete.domain.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.util.ParsingConstants;

/**
 *
 * @author mrajannan
 *
 */
public class ActivationEmailTemplate extends AuditableDomain implements Serializable, Identifiable {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 4163701807053372620L;

    /** The id of the email template. */
    private Long id;
    /**
     * name of the email template.
     */
    private String templateName;

    private Long assessmentProgramId;
    
    private String assessmentProgramName;
    
    private String assignedStates;
    
    private String states;
    
    private String emailSubject;
    
    private String emailBody;
    
    private Boolean includeEpLogo;
    
    private Boolean isDefault;
    
    private Boolean allStates;
    
    private Boolean activeFlag;
    
    private List<Organization> statesList;
    
    private String emailActivationJsonString;
    
    private String createdusername;
    
    private String modifiedusername;
    
    public ActivationEmailTemplate(){
    	
    }

	public ActivationEmailTemplate(long id){
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

	public String getTemplateName() {
		return templateName;
	}


	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}

	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}

	public String getAssignedStates() {
		return assignedStates;
	}

	public void setAssignedStates(String assignedStates) {
		this.assignedStates = assignedStates;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}


	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public Boolean getIncludeEpLogo() {
		return includeEpLogo;
	}


	public void setIncludeEpLogo(Boolean includeEpLogo) {
		this.includeEpLogo = includeEpLogo;
	}


	public Boolean getIsDefault() {
		return isDefault;
	}


	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}


	public Boolean getAllStates() {
		return allStates;
	}


	public void setAllStates(Boolean allStates) {
		this.allStates = allStates;
	}


	public Boolean getActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	/**
		 * Method to construct the json list for view rosters record browser.
		 * 
		 * @return
		 */
		public List<String> buildJSONRow() {
			List<String> cells = new ArrayList<String>();
			
			if(getAssessmentProgramName() != null) {				
					cells.add(ParsingConstants.BLANK + getAssessmentProgramName());				
			
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			
			if(getStates() != null) {				
				String states="";
				if(getAssignedStates()!=null && getAssignedStates().length()>0){				
					if(getStates()!=null && getStates().length()>0){
						List<String> stateList = Arrays.asList(getAssignedStates().split(", "));
						List<String> oldstateList = Arrays.asList(getStates().split(", "));
						for(String val2 : oldstateList){						
							if(!stateList.contains(val2)){
								if(states.equals("")) states = val2;
								else states = states+", "+val2;
							}
						}
					}
				cells.add(ParsingConstants.BLANK + states);	
				}
				else{
					cells.add(ParsingConstants.BLANK + getStates());		
				}
				
			
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getIsDefault() != null) {
				cells.add(ParsingConstants.BLANK + (getIsDefault() ? "Yes" : "No"));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			
			if(getTemplateName() != null) {
				cells.add(ParsingConstants.BLANK + getTemplateName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			
			if(getId() != null) {
				cells.add(ParsingConstants.BLANK + getId());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			cells.add("");
					
			return cells;
		}

	

	public List<Organization> getStatesList() {
		return statesList;
	}

	public void setStatesList(List<Organization> statesList) {
		this.statesList = statesList;
	}
	
	

	public String getCreatedusername() {
		return createdusername;
	}

	public void setCreatedusername(String createdusername) {
		this.createdusername = createdusername;
	}

	public String getModifiedusername() {
		return modifiedusername;
	}

	public void setModifiedusername(String modifiedusername) {
		this.modifiedusername = modifiedusername;
	}

	public String buildJsonString(){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			//Create Object tree for Activation Template object
			JsonNode root = mapper.createObjectNode();
			
			((ObjectNode)root).put("id", getId());
			((ObjectNode)root).put("TemplateName",getTemplateName());		
			((ObjectNode)root).put("AssessmentProgram",getAssessmentProgramName());
			/**
			  * uday
			  * F424
			  * For audit history 	
			  */
			((ObjectNode)root).put("AssessmentProgramId",getAssessmentProgramId());
			
			((ObjectNode)root).put("EmailSubject",getEmailSubject());
			((ObjectNode)root).put("EmailBody",getEmailBody()== null? "":getEmailBody());
			((ObjectNode)root).put("IncludeEpLogo",getIncludeEpLogo());
			((ObjectNode)root).put("allStates",getAllStates());
			ArrayNode stateArrayNode = mapper.createArrayNode();	
			if(getStatesList()!=null){
				for (Organization state: getStatesList()) {
					//Creating new json object for each state
					ObjectNode stateNode = mapper.createObjectNode();
					stateNode.put("Id",state.getId());	
					stateNode.put("Name", state.getOrganizationName());
					stateNode.put("DisplayIdentifier", state.getDisplayIdentifier());
					stateArrayNode.add(stateNode);			
			  }	
			}			
			((ObjectNode)root).set("States", stateArrayNode);
			((ObjectNode)root).put("CreatedUserName",getCreatedusername());					
			((ObjectNode)root).put("CreatedDate",getCreatedDate() == null? " ":getCreatedDate().toString());
			((ObjectNode)root).put("CreatedUser",getCreatedUser() == null? 0:getCreatedUser());
			((ObjectNode)root).put("ModifiedUserID",getModifiedUser());
			((ObjectNode)root).put("ModifiedUserName",getModifiedusername());
			((ObjectNode)root).put("ModifiedDate",getModifiedDate() == null? " ":getModifiedDate().toString());
			emailActivationJsonString = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return emailActivationJsonString;
	}


	@Override
	public Long getId(int order) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}	
}
