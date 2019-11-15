/**
 * 
 */
package edu.ku.cete.domain.upload;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author neil.howerton
 *
 */
public class UploadedOrganization extends ValidateableRecord {
    private Organization organization;
    private String organizationTypeCode;
    private String parentDisplayIdentifier;
    private String contractOrgDisplayId;

    private String lineNumber; 
    /**
     * public default constructor.
     */
    public UploadedOrganization() {
        organization = new Organization();
    }

    /**
     *
     *@return {@link Organization}
     */
    public final Organization getOrganization() {
        return this.organization;
    }

    /**
     *
     *@param organization {@link Organization}
     */
    public final void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * @return {@link String}
     */
    @Override
    public final String getIdentifier() {
        if (this.getDisplayIdentifier() != null) {
            return ParsingConstants.BLANK + this.getDisplayIdentifier();
        } else {
            return ParsingConstants.BLANK;
        }
    }

    /**
     *
     *@return {@link String}
     */
    public final String getDisplayIdentifier() {
        return this.organization.getDisplayIdentifier();
    }

    /**
     *
     *@param displayIdentifier {@link String}
     */
    public final void setDisplayIdentifier(String displayIdentifier) {
        this.organization.setDisplayIdentifier(displayIdentifier);
    }

    /**
     *
     *@return {@link String}
     */
    public final String getOrganizationName() {
        return this.organization.getOrganizationName();
    }

    /**
     *
     *@param orgName {@link String}
     */
    public final void setOrganizationName(String orgName) {
        this.organization.setOrganizationName(orgName);
    }

    /**
     *
     *@return {@link OrganizationType}
     */
    public final OrganizationType getOrganizationType() {
        return this.organization.getOrganizationType();
    }

    /**
     *
     *@param organizationType {@link OrganizationType}
     */
    public final void setOrganizationType(OrganizationType organizationType) {
        this.organization.setOrganizationType(organizationType);
    }

    /**
     *
     *@return long
     */
    public final Long getParentOrganizationId() {
        return this.organization.getRelatedOrganizationId();
    }

    /**
     *
     *@param parentOrganizationId long
     */
    public final void setParentOrganizationId(long parentOrganizationId) {
        this.organization.setRelatedOrganizationId(parentOrganizationId);
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
     * @return the parentDisplayIdentifier
     */
    public final String getParentDisplayIdentifier() {
        return parentDisplayIdentifier;
    }

    /**
     * @param parentDisplayIdentifier the parentDisplayIdentifier to set
     */
    public final void setParentDisplayIdentifier(String parentDisplayIdentifier) {
        this.parentDisplayIdentifier = parentDisplayIdentifier;
    }

    /**
     * @return the contractingOrgDisplayId
     */
    public final String getContractOrgDisplayId() {
        return contractOrgDisplayId;
    }

    /**
     * @param contractingOrgDisplayId the contractingOrgDisplayIdentifier to set
     */
    public final void setContractOrgDisplayId(String contractingOrgDisplayId) {
        this.contractOrgDisplayId = contractingOrgDisplayId;
    }

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	private Long stateId;
	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}	
}
