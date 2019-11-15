package edu.ku.cete.domain.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;

/**
 *
 * @author mrajannan
 *
 */
public class OrganizationContractRelation extends AuditableDomain implements Serializable,Identifiable,Comparable<OrganizationContractRelation> {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 4163701807053372620L;

    private Organization organization;
    
    private Boolean isParent;
    
    private Integer currentLevel;
    
    private boolean isContractPresent = false;
    
    private Long id;
    
    private Long relatedOrganizationId;

    public OrganizationContractRelation(){
    	
    }

    public OrganizationContractRelation(long id){
    }

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public void setRelatedOrganizationId(Long relatedOrganizationId) {
		this.relatedOrganizationId = relatedOrganizationId;
	}

	/**
	 * @return the isParent
	 */
	public Boolean getIsParent() {
		return isParent;
	}

	/**
	 * @param isParent the isParent to set
	 */
	public void setIsParent(Boolean isParent) {
		if(isParent == null) {
			this.isParent = false;
		} else {
			this.isParent = isParent;
		}
	}

	/**
	 * @return the currentLevel
	 */
	public Integer getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * @return the isContractPresent
	 */
	public boolean isContractPresent() {
		return isContractPresent;
	}

	/**
	 * @param isContractPresent the isContractPresent to set
	 */
	public void setContractPresent(boolean isContractPresent) {
		this.isContractPresent = isContractPresent;
	}
	/**
	 * @param isContractPresent the isContractPresent to set
	 */
	public void setContractPresent(Boolean isContractPresent) {
		if (isContractPresent == null) {
			this.isContractPresent = false;			
		} else {
			this.isContractPresent = isContractPresent;
		}
	}

	@Override
    public final String toString() {
		return ToStringBuilder.reflectionToString(this);
    }

	@Override
	public int compareTo(OrganizationContractRelation orgContractRelation) {
		int result = 0;
		if(orgContractRelation != null) {
			result = this.getCurrentLevel().compareTo(orgContractRelation.getCurrentLevel());
		}
		return result;
	}

	@Override
	public Long getId(int order) {
		return getId();
	}
	
	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}

}
