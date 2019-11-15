package edu.ku.cete.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.common.Organization;

/**
 * @author m802r921
 * This class is specific for json serialization that's why it has meta data.
 * TODO extend the organization object. 
 */
public class OrganizationDto implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8220969014087527232L;
	/**
	 * data 
	 */
	private String data;
    /**
     * metadata 
     */
    private Metadata metadata;    
    /**
     * attr
     * This is need for record browser jstree. 
     */
    private Metadata attr;
    /**
     * children
     */
    private List<OrganizationDto> children;
    
    /**
     * if getting children then this is the parent organization id.
     * if getting parent then this is the child organization id. 
     */
    private Long relatedOrganizationId;
    
    /**
     * 
     *@return
     */
    public String getData() {
        return data;
    }
    
    /**
     * 
     *@param organization
     */
    public void setData(String data) {
        this.data = data;
    }
    
    /**
     * 
     *@return
     */
    public Metadata getMetadata() {
        return metadata;
    }
    
    /**
     * @return
     */
    public Long getId(){
    	Long result = null;
    	if (metadata != null) {
    		result = metadata.getId();
    	}
    	return result;
    }
    
    /**
     * 
     *@param metadata
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    /**
     * @return metadata
     * This is need for record browser jstree.
     */
    public Metadata getAttr() {
    	return metadata;
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
	 * @param relatedOrganization {@link Organization}
	 */
	public final void setRelatedOrganizationId(Organization relatedOrganization) {
		if (relatedOrganization != null && relatedOrganization.getRelatedOrganizationId() != null) {
			this.setRelatedOrganizationId(relatedOrganization.getRelatedOrganizationId());
		}
	}
	
	/**
     * 
     *@return children
     */
    public List<OrganizationDto> getChildren() {
        return children;
    }
        
    /**
     * Add the child with out duplicates.
     * @param childOrg
     */
    public final void addChild(OrganizationDto childOrg) {
    	if (children == null) {
    		children = new ArrayList<OrganizationDto>();
    	}
		if (childOrg != null
				&& getId() != null
				&& childOrg.getId() > 0
				&& childOrg.getRelatedOrganizationId().equals(getId())
				&& !children.contains(childOrg)
				) {
			children.add(childOrg);
		}
    }

    /**
     * Pass all the children the parent will be inspected before being set as child.
     *@param childOrgs {@link List}
     */
    public final void setChildren(List<OrganizationDto> childOrgs) {
    	if (childOrgs != null && CollectionUtils.isNotEmpty(childOrgs)) {
    		for (OrganizationDto childOrg : childOrgs) {
    			addChild(childOrg);
    		}
    	}
    }
    
    /**
     * this is for contains method to work properly
     * @return {@link Boolean}
     */
    @Override
    public final boolean equals(Object orgObj) {
    	boolean result = false;
    	if (orgObj != null
    			&& orgObj instanceof OrganizationDto) {
    		OrganizationDto orgDto = (OrganizationDto) orgObj;
    		//if the organization and related organization id match then it is the same organization.
    		if (orgDto.getId() != null
    				&& orgDto.getRelatedOrganizationId() != null
    				&& this.getId() != null
    				&& this.getRelatedOrganizationId() != null
    				&& orgDto.getId() == this.getId()
    				&& orgDto.getRelatedOrganizationId() == this.getRelatedOrganizationId()) {
    			result = true;
    		}
    	}
    	return result;
    }

}
