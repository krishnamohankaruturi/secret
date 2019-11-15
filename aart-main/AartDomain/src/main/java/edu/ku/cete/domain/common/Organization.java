package edu.ku.cete.domain.common;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.util.ParsingConstants;

/**
 *
 * @author mrajannan
 *
 */
public class Organization extends AuditableDomain implements Serializable, Comparable, Identifiable {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 4163701807053372620L;

    /** The id of the organization. */
    private Long id;
    /**
     * name of the organization.
     */
    private String organizationName;
    /** The welcome message of the organization. */
    private String welcomeMessage;
    /** The identifier displayed to the user. */
    private String displayIdentifier;

    /**
     * TODO If this is not always needed move to Dto.
     * If getting children then this id corresponds to parent.
     * If getting parent this id corresponds to child.
     */
    private Long relatedOrganizationId;

    private OrganizationType organizationType;
    
    private Long buildingUniqueness;
    
    private Long currentSchoolYear;
    
    private Date schoolStartDate;
    
    private Date schoolEndDate;
    
    private List<String> organizationHierarchies;
    
    private String parentOrgDisplayName;
    
    private String parentOrgTypeCode;
    
    private String orgJsonString;
    private int reportYear;
    
    private Boolean activeFlag;
    
    private Boolean defaultOrg;
    private Boolean contractingOrganization;
    private Boolean expirePasswords;
    private Long expirationDateType;
    private String expirationDateTypeString;
    
    private List<Groups> groupsList;
    
    List<Organization> hierarchy;
    
    private Long assessmentProgramId;
    private String poolType;
    
    private Long operationalWindowId;
    private Date operationalWindowEffectiveDate;
    
    private boolean reportProcess;
    
    private boolean isExist;
    
    private String shortOrgName;
    
    private String parentOrganizationName;
    
    private Long testingModel;
    
    private String testingModelName;
    
    private String assessmentProgram;
        
    private String organizaionStructure;
	
    private List<Long> assessmentProgramIdList;
    
    private String parentOrgTypeName;
    
    private List<AssessmentProgram> assessmentPrograms;
    
    private Boolean isMerged;
    
    private Date operationalTestWindowExpiryDate;
    
    private String sourceorgdisplayidentifier;
    
    private String externalid;
    
    private Long timezoneid;
    
    private Date testBeginTime;
    
    private Date testEndTime;
    
    private String testDays;
    
    /**
	 * @return the poolType
	 */
	public String getPoolType() {
		return poolType;
	}

	/**
	 * @param poolType the poolType to set
	 */
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	/**
	 * @return the assessmentProgramId
	 */
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	/**
	 * @param assessmentProgramId the assessmentProgramId to set
	 */
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Organization(){
    	
    }
	
    /**
	 * @return the hierarchy
	 */
	public List<Organization> getHierarchy() {
		return hierarchy;
	}

	/**
	 * @param hierarchy the hierarchy to set
	 */
	public void setHierarchy(List<Organization> hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Organization(long id){
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

	/**
     * @return the welcomeMessage
     */
    public final String getWelcomeMessage() {
        return welcomeMessage;
    }
    /**
     * @param welcome
     *          the welcome message of the organization to set
     */
    public final void setWelcomeMessage(String welcome) {
        this.welcomeMessage = welcome;
    }

    /**
     * @return {@link String}
     */
    public final String getDisplayIdentifier() {
    	if(displayIdentifier != null &&
    			StringUtils.isNotEmpty(displayIdentifier)) {
    		return displayIdentifier.toUpperCase();
    	} else {
    		return null;
    	}
	}

	/**
	 * @param displayId {@link String}
	 */
	public final void setDisplayIdentifier(String displayId) {
		this.displayIdentifier = displayId;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	@Override
    public final String toString() {
		return ToStringBuilder.reflectionToString(this);
    }

	/**
	 *
	 *@return {@link OrganizationType}
	 */
	public final OrganizationType getOrganizationType() {
	    return this.organizationType;
	}

	/**
	 * 
	 *@param organizationType
	 */
	public final void setOrganizationType(OrganizationType organizationType) {
	    this.organizationType = organizationType;
	}

	/**
	 * 
	 *@return
	 */
	public final long getOrganizationTypeId() {
		if(this.organizationType!=null)
			return this.organizationType.getOrganizationTypeId();
		return -1;
	}

	/**
	 * 
	 *@param organizationTypeId
	 */
	public final void setOrganizationTypeId(long organizationTypeId) {
	    if (this.organizationType == null) {
	        this.organizationType = new OrganizationType();
	    }

	    this.organizationType.setOrganizationTypeId(organizationTypeId);
	}

	/**
	 * TODO Move To Dto or use association.
	 *@return
	 */
	public final String getTypeName() {
		if(this.organizationType!=null)
			return this.organizationType.getTypeName();
		else 
			return "";
	}

	/**
	 * TODO Move To Dto or use association.
	 *@param typeName
	 */
	public final void setTypeName(String typeName) {
	    if (this.organizationType == null) {
	        this.organizationType = new OrganizationType();
	    }
	    this.organizationType.setTypeName(typeName);
	}

	/**
	 * TODO Move To Dto or use association.
	 *@return
	 */
	public final String getTypeCode() {
		if(this.organizationType!=null)
			return this.organizationType.getTypeCode();
		else
			return "";
	}

	/**
	 * TODO Move To Dto or use association.
	 *@param typeCodeL
	 */
	public final void setTypeCode(String typeCode) {
	    if (this.organizationType == null) {
            this.organizationType = new OrganizationType();
        }
	    this.organizationType.setTypeCode(typeCode);
	}

	/**
	 * 
	 *@return
	 */
	public final int getTypeLevel() {
		if(this.organizationType!=null)
			return this.organizationType.getTypeLevel();
		else 
			return -1;
	}

	/**
	 * 
	 *@param typeLevel
	 */
	public final void setTypeLevel(int typeLevel) {
	    if (this.organizationType == null) {
            this.organizationType = new OrganizationType();
        }
	    this.organizationType.setTypeLevel(typeLevel);
	}

    /**
     * @return the relatedOrganizationId
     */
    public Long getRelatedOrganizationId() {
        return relatedOrganizationId;
    }

    /**
     * @param relatedOrganizationId the relatedOrganizationId to set
     */
    public void setRelatedOrganizationId(Long parentOrganizationId) {
        this.relatedOrganizationId = parentOrganizationId;
    }

	public Long getId(int order) {
		return getId();
	}
	
	public String getStringIdentifier(int order) {		
		return null;
	}

	public Long getBuildingUniqueness() {
		return buildingUniqueness;
	}

	public void setBuildingUniqueness(Long buildingUniqueness) {
		this.buildingUniqueness = buildingUniqueness;
	}

	public Date getSchoolStartDate() {
		return schoolStartDate;
	}

	public void setSchoolStartDate(Date schoolStartDate) {
		this.schoolStartDate = schoolStartDate;
	}

	public Date getSchoolEndDate() {
		return schoolEndDate;
	}

	public void setSchoolEndDate(Date schoolEndDate) {
		this.schoolEndDate = schoolEndDate;
	}

	public List<String> getOrganizationHierarchies() {
		return organizationHierarchies;
	}

	public void setOrganizationHierarchies(List<String> organizationHierarchies) {
		this.organizationHierarchies = organizationHierarchies;
	}

	public String getParentOrgDisplayName() {
		return parentOrgDisplayName;
	}

	public void setParentOrgDisplayName(String parentOrgDisplayName) {
		this.parentOrgDisplayName = parentOrgDisplayName;
	}
	
	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	
	
	//end
	/**
		 * Method to construct the json list for view rosters record browser.
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

			if(getDisplayIdentifier() != null) {
				cells.add(ParsingConstants.BLANK + getDisplayIdentifier());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getOrganizationName() != null) {
				cells.add(ParsingConstants.BLANK + getOrganizationName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getTypeCode() != null) {
				cells.add(ParsingConstants.BLANK + getTypeCode());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}

			if(getParentOrgDisplayName() != null) {
				cells.add(ParsingConstants.BLANK + getParentOrgDisplayName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getParentOrgTypeCode() != null) {
				cells.add(ParsingConstants.BLANK + getParentOrgTypeCode());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getContractingOrganization() != null) {
				cells.add(ParsingConstants.BLANK + (getContractingOrganization() ? "Yes" : "No"));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getSchoolStartDate()!= null) {
				cells.add(ParsingConstants.BLANK + new SimpleDateFormat("MM/dd/yyyy").format(getSchoolStartDate()));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getSchoolEndDate() != null) {
				cells.add(ParsingConstants.BLANK + new SimpleDateFormat("MM/dd/yyyy").format(getSchoolEndDate()));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			/*if(getExpirePasswords() != null) {
				cells.add(ParsingConstants.BLANK + (getExpirePasswords() ? "Yes" : "No"));
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getExpirationDateTypeString() != null) {
				cells.add(ParsingConstants.BLANK + getExpirationDateTypeString());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}*/

			if(getParentOrganizationName() != null) {
				cells.add(ParsingConstants.BLANK + getParentOrganizationName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getAssessmentProgram() != null && !getAssessmentProgram().equals("")) {
				cells.add(ParsingConstants.BLANK + getAssessmentProgram());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getTestingModelName() != null) {
				cells.add(ParsingConstants.BLANK + getTestingModelName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getMergedorgdisplayidentifier() != null) {
				cells.add(ParsingConstants.BLANK + getMergedorgdisplayidentifier());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
			if(getTestBeginTime()!= null) {
				cells.add(ParsingConstants.BLANK + new SimpleDateFormat("hh:mm a").format(getTestBeginTime()));
			} else {
				cells.add(ParsingConstants.BLANK);
			}
			if(getTestEndTime() != null) {
				cells.add(ParsingConstants.BLANK + new SimpleDateFormat("hh:mm a").format(getTestEndTime()));
			} else {
				cells.add(ParsingConstants.BLANK);
			}
			if(getTestDays() != null) {
				cells.add(ParsingConstants.BLANK + getTestDays());
			} else {
				cells.add(ParsingConstants.BLANK);
			}
			
			cells.add("");
					
			return cells;
		}

	public Boolean getDefaultOrg() {
		return defaultOrg;
	}

	public void setDefaultOrg(Boolean defaultOrg) {
		this.defaultOrg = defaultOrg;
	}

	public List<Groups> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(List<Groups> groupsList) {
		this.groupsList = groupsList;
	}

	public String getParentOrgTypeCode() {
		return parentOrgTypeCode;
	}

	public void setParentOrgTypeCode(String parentOrgTypeCode) {
		this.parentOrgTypeCode = parentOrgTypeCode;
	}

	public Boolean getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Boolean contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public Boolean getExpirePasswords() {
		return expirePasswords;
	}

	public void setExpirePasswords(Boolean expirePasswords) {
		this.expirePasswords = expirePasswords;
	}

	public Long getExpirationDateType() {
		return expirationDateType;
	}

	public void setExpirationDateType(Long expirationDateType) {
		this.expirationDateType = expirationDateType;
	}

	public String getExpirationDateTypeString() {
		return expirationDateTypeString;
	}

	public void setExpirationDateTypeString(String expirationDateTypeString) {
		this.expirationDateTypeString = expirationDateTypeString;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	public boolean isReportProcess() {
		return reportProcess;
	}

	public void setReportProcess(boolean reportProcess) {
		this.reportProcess = reportProcess;
	}

	public int getReportYear() {
		return reportYear;
	}

	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}

	public String getShortOrgName() {
		return shortOrgName;
	}
	
	public Date getOperationalWindowEffectiveDate() {
		return operationalWindowEffectiveDate;
	}
	
	public void setOperationalWindowEffectiveDate(Date operationalWindowEffectiveDate) {
		this.operationalWindowEffectiveDate = operationalWindowEffectiveDate;
	}

	public void setShortOrgName(String shortOrgName) {
		this.shortOrgName = shortOrgName;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public Long getTestingModel() {
		return testingModel;
	}

	public void setTestingModel(Long testingModel) {
		this.testingModel = testingModel;
	}
	
	public String getTestingModelName() {
		return testingModelName;
	}

	public void setTestingModelName(String testingModelName) {
		this.testingModelName = testingModelName;
	}

	public String getOrganizaionStructure() {
		return organizaionStructure;
	}

	public void setOrganizaionStructure(String organizaionStructure) {
		this.organizaionStructure = organizaionStructure;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/*
	 * sudhansu : Added during US18004 - organization audit story 
	 */
	public String buildJsonString(){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			//Create Object tree for user object
			JsonNode root = mapper.createObjectNode();
			
			((ObjectNode)root).put("OrganizationId", getId());
			((ObjectNode)root).put("OrganizationName",getOrganizationName());
			((ObjectNode)root).put("DisplayIdentifier",getDisplayIdentifier());
			((ObjectNode)root).put("OrganizationTypeId",getOrganizationTypeId());
			((ObjectNode)root).put("ModifiedDate",getModifiedDate() == null? " ":getModifiedDate().toString());
			((ObjectNode)root).put("ModifiedUser",getModifiedUser());
			((ObjectNode)root).put("CreatedDate",getCreatedDate() == null? " ":getCreatedDate().toString());
			((ObjectNode)root).put("CreatedUser",getCreatedUser());
			((ObjectNode)root).put("SchoolStartDate",getSchoolStartDate() == null? " ":getSchoolStartDate().toString());
			((ObjectNode)root).put("SchoolEndDate",getSchoolEndDate() == null? " ":getSchoolEndDate().toString());
			((ObjectNode)root).put("ContractingOrganization",getContractingOrganization());
			((ObjectNode)root).put("ExpirePassword",getExpirePasswords());
			((ObjectNode)root).put("ExpirationDateType",getExpirationDateTypeString());
			((ObjectNode)root).put("PoolType",getPoolType());
			((ObjectNode)root).put("SchoolYear",getCurrentSchoolYear());
			((ObjectNode)root).put("ReportYear",getReportYear());
			((ObjectNode)root).put("ReportProcess",isReportProcess());
			((ObjectNode)root).put("TestingModel",getTestingModelName());
			((ObjectNode)root).put("AssessmentPrograms",getAssessmentProgram());  
			((ObjectNode)root).put("mergedorgdisplayidentifier",getMergedorgdisplayidentifier());
			((ObjectNode)root).put("TestBeginTime",getTestBeginTime() == null? " ":getTestBeginTime().toString());
			((ObjectNode)root).put("TestEndTime",getTestEndTime() == null? " ":getTestEndTime().toString());
			((ObjectNode)root).put("TestDays",getTestDays());
			
			orgJsonString = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return orgJsonString;
	}
	  @Override
	   public int hashCode() {
	       return new HashCodeBuilder(17, 31). 
	           append(this.getOrganizationName()).
	           toHashCode();
	   }

	   @Override
	   public boolean equals(Object obj) {
	      if (!(obj instanceof Organization))
	           return false;
	       if (obj == this)
	           return true;

	       Organization rhs = (Organization) obj;
	       return new EqualsBuilder().
	           append(this.getOrganizationName(), rhs.getOrganizationName()).
	           isEquals();
	   }

	@Override
	public int compareTo(Object rhs) {
		 if (!(rhs instanceof Organization))
	           return -1;
		 Organization o= (Organization) rhs;
		return this.getOrganizationName().compareTo(o.getOrganizationName());
	}

	public List<Long> getAssessmentProgramIdList() {
		return assessmentProgramIdList;
	}

	public void setAssessmentProgramIdList(List<Long> assessmentProgramIdList) {
		this.assessmentProgramIdList = assessmentProgramIdList;
	}

	public String getParentOrgTypeName() {
		return parentOrgTypeName;
	}

	public void setParentOrgTypeName(String parentOrgTypeName) {
		this.parentOrgTypeName = parentOrgTypeName;
	}

	public List<AssessmentProgram> getAssessmentPrograms() {
		return assessmentPrograms;
	}

	public void setAssessmentPrograms(List<AssessmentProgram> assessmentPrograms) {
		this.assessmentPrograms = assessmentPrograms;
	}

	public Boolean getIsMerged() {
		return isMerged;
	}

	public void setIsMerged(Boolean isMerged) {
		this.isMerged = isMerged;
	}

	public Date getOperationalTestWindowExpiryDate() {
		return operationalTestWindowExpiryDate;
	}

	public void setOperationalTestWindowExpiryDate(Date operationalTestWindowExpiryDate) {
		this.operationalTestWindowExpiryDate = operationalTestWindowExpiryDate;
	}
	
	public String getMergedorgdisplayidentifier() {
		return sourceorgdisplayidentifier;
	}

	public void setMergedorgdisplayidentifier(String sourceorgdisplayidentifier) {
		this.sourceorgdisplayidentifier = sourceorgdisplayidentifier;
	}
	
	public String getExternalid() {
		return this.externalid;
	}

	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	
	public Long geTimezoneID() {
		return this.timezoneid;
	}

	public void setTimeZone(Long timezoneid) {
		this.timezoneid = timezoneid;
	}	
	//Saikat Added
		
	public Date getTestBeginTime() {
		return testBeginTime;
	}

	public void setTestBeginTime(Date testBeginTime) {
		this.testBeginTime = testBeginTime;
	}

	public Date getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(Date testEndTime) {
		this.testEndTime = testEndTime;
	}
		
	public String getTestDays() {
		return testDays;
	}

	public void setTestDays(String testDays) {
		this.testDays = testDays;
	}
		
}
