/**
 * 
 */
package edu.ku.cete.domain.user;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author neil.howerton
 *
 */
public class UploadedUser extends ValidateableRecord implements Serializable{

    private User user;
    private long rowNum;
    private long organizationId;
    private String displayIdentifier;
    private String organizationTypeCode;
    private String state;
    private String districtName;
    private String idnumber;
    private String districtID;
    private String schoolNamme;
    private String schoolID;
    private boolean rtComplete;
    private String rtCompleteDate;
    
    /**
	 * Prasanth :  US16352 : To upload data file     
	 */
    private String lineNumber;
    private User existingUser;
    private Organization currentContext;
    private ContractingOrganizationTree contractingOrganizationTree;
    private UserDetailImpl loggedinUser; 
    
    /**
	 * Prasanth :  US16246 : to upload primary and secondary role      
	 */
    private String primaryRole;
    private String secondaryRole;
    private Groups primaryGroups;
    private Groups secondaryGroups;

    /**
	 * Prasanth :  US16239 : to upload user primary assessment program      
	 */
    private String primaryAssessmentProgram;
    private Long primaryAssessmentProgramId;
    
    @Override
    public String getIdentifier() {
        return ParsingConstants.BLANK + getId();
    }

    /**
     *
     */
    public UploadedUser() {
        user = new User();
        setRowNum(0);
    }

    /**
     *
     *@return {@link User}
     */
    public final User getUser() {
        return user;
    }

    /**
     *
     *@param user {@link User}
     */
    public final void setUser(final User user) {
        this.user = user;
    }

    /**
     * @return the id
     */
    public final Long getId() {
        return user.getId();
    }

    /**
     * @param newId the id to set
     */
    public final void setId(final Long newId) {
        this.user.setId(newId);
    }

    /**
     * @return the userId
     */
    public final String getUserName() {
        return user.getUserName();
    }

    /**
     * @param newUserName
     *            the userId to set
     */
    public final void setUserName(final String newUserName) {
        user.setUserName(newUserName);
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
        return user.getFirstName();
    }

    /**
     * @param newFirstName
     *            the firstName to set
     */
    public final void setFirstName(final String newFirstName) {
        user.setFirstName(newFirstName);
    }

    /**
     * @return the middleName
     */
    public final String getMiddleName() {
        return user.getMiddleName();
    }
    
    /**
     * @param newMiddleName the middleName to set
     */
    public final void setMiddleName(final String newMiddleName) {
        user.setMiddleName(newMiddleName);
    }

    /**
     * @return the surName
     */
    public final String getSurName() {
        return user.getSurName();
    }

    /**
     * @param newLastName
     *            the surName to set
     */
    public final void setLastName(final String newLastName) {
        user.setSurName(newLastName);
    }
    
    public final String getLastName() {
    	return user.getSurName();
    }

    /**
     *
     *@return {@link String}
     */
    public final String getEmail() {
        return user.getEmail();
    }

    /**
     *
     *@param email {@link String}
     */
    public final void setEmail(String email) {
        user.setEmail(email);
    }

    /**
     *
     *@return {@link String}
     */
    public final String getEducatorIdentifier() {
        return user.getUniqueCommonIdentifier();
    }

    /**
     *
     *@param educatorIdentifier {@link String}
     */
    public final void setEducatorIdentifier(String educatorIdentifier) {
        user.setUniqueCommonIdentifier(educatorIdentifier);
    }

    /**
     * @return the rowNum
     */
    public final long getRowNum() {
        return rowNum;
    }

    /**
     * @param rowNum the rowNum to set
     */
    public final void setRowNum(long rowNum) {
        this.rowNum = rowNum;
    }

    /**
     *
     *@return long - the organization id.
     */
    public final long getOrganizationId() {
        return organizationId;
    }

    /**
     *
     *@param organizationId long
     */
    public final void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * @return the organizationTypeCode
     */
    public final String getOrganizationTypeCode() {
        return organizationTypeCode;
    }

    /**
     * @param organizationTypeCode the organizationTypeCode to set
     */
    public final void setOrganizationTypeCode(String organizationTypeCode) {
        this.organizationTypeCode = organizationTypeCode;
    }

    /**
     * @return the displayIdentifier
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
     * @param displayIdentifier the displayIdentifier to set
     */
    public final void setDisplayIdentifier(String displayIdentifier) {
        this.displayIdentifier = displayIdentifier;
    }

    @Override
    public String toString() {
        return super.toString() + " UploadedUser [user=" + user + ", rowNum=" + rowNum
                + ", organizationId=" + organizationId
                + ", organizationTypeCode=" + organizationTypeCode + "]";
    }

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public User getExistingUser() {
		return existingUser;
	}

	public void setExistingUser(User existingUser) {
		this.existingUser = existingUser;
	}

	public Organization getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(Organization currentContext) {
		this.currentContext = currentContext;
	}

	public ContractingOrganizationTree getContractingOrganizationTree() {
		return contractingOrganizationTree;
	}

	public void setContractingOrganizationTree(
			ContractingOrganizationTree contractingOrganizationTree) {
		this.contractingOrganizationTree = contractingOrganizationTree;
	}

	public UserDetailImpl getLoggedinUser() {
		return loggedinUser;
	}

	public void setLoggedinUser(UserDetailImpl loggedinUser) {
		this.loggedinUser = loggedinUser;
	}

	public String getPrimaryRole() {
		return primaryRole;
	}

	public void setPrimaryRole(String primaryRole) {
		this.primaryRole = primaryRole;
	}

	public String getSecondaryRole() {
		return secondaryRole;
	}

	public void setSecondaryRole(String secondaryRole) {
		this.secondaryRole = secondaryRole;
	}

	public Groups getPrimaryGroups() {
		return primaryGroups;
	}

	public void setPrimaryGroups(Groups primaryGroups) {
		this.primaryGroups = primaryGroups;
	}

	public Groups getSecondaryGroups() {
		return secondaryGroups;
	}

	public void setSecondaryGroups(Groups secondaryGroups) {
		this.secondaryGroups = secondaryGroups;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolNamme() {
		return schoolNamme;
	}

	public void setSchoolNamme(String schoolNamme) {
		this.schoolNamme = schoolNamme;
	}


	public boolean isRtComplete() {
		return rtComplete;
	}

	public void setRtComplete(boolean rtComplete) {
		this.rtComplete = rtComplete;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getRtCompleteDate() {
		return rtCompleteDate;
	}

	public void setRtCompleteDate(String rtCompleteDate) {
		this.rtCompleteDate = rtCompleteDate;
	}

	public String getPrimaryAssessmentProgram() {
		return primaryAssessmentProgram;
	}

	public void setPrimaryAssessmentProgram(String primaryAssessmentProgram) {
		this.primaryAssessmentProgram = primaryAssessmentProgram;
	}

	public Long getPrimaryAssessmentProgramId() {
		return primaryAssessmentProgramId;
	}

	public void setPrimaryAssessmentProgramId(Long primaryAssessmentProgramId) {
		this.primaryAssessmentProgramId = primaryAssessmentProgramId;
	}

}
