package edu.ku.cete.domain.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.audit.AuditableDomain;

public class Restriction extends AuditableDomain {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.restriction.id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.restriction.restriction_name
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    private String restrictionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.restriction.restriction_code
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    private String restrictionCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.restriction.restriction_description
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    private String restrictionDescription;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.restriction.restricted_resource_type_id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    private Long restrictedResourceTypeId;
    /**
     * parentAuthorityIds.
     */
    private List<Long> authorityIds = new ArrayList<Long>();    
    /**
     * parentAuthorityIds.
     */
    private List<Long> parentAuthorityIds = new ArrayList<Long>();
    /**
     * childAuthorityIds.
     */
    private List<Long> childAuthorityIds = new ArrayList<Long>();
    /**
     * differentialAuthorityIds.
     */
    private List<Long> differentialAuthorityIds =  new ArrayList<Long>();
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.restriction.id
     *
     * @return the value of public.restriction.id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.restriction.id
     *
     * @param id the value for public.restriction.id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.restriction.restriction_name
     *
     * @return the value of public.restriction.restriction_name
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public String getRestrictionName() {
        return restrictionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.restriction.restriction_name
     *
     * @param restrictionName the value for public.restriction.restriction_name
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public void setRestrictionName(String restrictionName) {
        this.restrictionName = restrictionName == null ? null : restrictionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.restriction.restriction_code
     *
     * @return the value of public.restriction.restriction_code
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public String getRestrictionCode() {
        return restrictionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.restriction.restriction_code
     *
     * @param restrictionCode the value for public.restriction.restriction_code
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public void setRestrictionCode(String restrictionCode) {
        this.restrictionCode = restrictionCode == null ? null : restrictionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.restriction.restriction_description
     *
     * @return the value of public.restriction.restriction_description
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public String getRestrictionDescription() {
        return restrictionDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.restriction.restriction_description
     *
     * @param restrictionDescription the value for public.restriction.restriction_description
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public void setRestrictionDescription(String restrictionDescription) {
        this.restrictionDescription = restrictionDescription == null ? null : restrictionDescription.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.restriction.restricted_resource_type_id
     *
     * @return the value of public.restriction.restricted_resource_type_id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public Long getRestrictedResourceTypeId() {
        return restrictedResourceTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.restriction.restricted_resource_type_id
     *
     * @param restrictedResourceTypeId the value for public.restriction.restricted_resource_type_id
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    public void setRestrictedResourceTypeId(Long restrictedResourceTypeId) {
        this.restrictedResourceTypeId = restrictedResourceTypeId;
    }

	/**
	 * @return the parentAuthorityIds
	 */
	public List<Long> getParentAuthorityIds() {
		return parentAuthorityIds;
	}

	public void addParentAuthorityId(long parentAuthorityId) {
		if (!parentAuthorityIds.contains(parentAuthorityId)) {
			parentAuthorityIds.add(parentAuthorityId);
		}
	}
	public void addParentAuthorityId(Long parentAuthorityId) {
		if (parentAuthorityId != null) {
			addParentAuthorityId(parentAuthorityId.longValue());
		}
	}
	/**
	 * @param parentAuthorityIds the parentAuthorityIds to set
	 */
	public void setParentAuthorityIds(List<Long> parentAuthorityIds) {
		this.parentAuthorityIds = parentAuthorityIds;
	}

	/**
	 * @return the childAuthorityIds
	 */
	public List<Long> getChildAuthorityIds() {
		return childAuthorityIds;
	}

	/**
	 * @param childAuthorityIds the childAuthorityIds to set
	 */
	public void setChildAuthorityIds(List<Long> childAuthorityIds) {
		this.childAuthorityIds = childAuthorityIds;
	}

	public void addChildAuthorityId(long childAuthorityId) {
		if (!childAuthorityIds.contains(childAuthorityId)) {
			childAuthorityIds.add(childAuthorityId);
		}
	}
	public void addChildAuthorityId(Long childAuthorityId) {
		if (childAuthorityId != null) {
			addChildAuthorityId(childAuthorityId.longValue());
		}
	}
	/**
	 * @return the differentialAuthorityIds
	 */
	public List<Long> getDifferentialAuthorityIds() {
		return differentialAuthorityIds;
	}
	/**
	 * @param differentialAuthorityIds the differentialAuthorityIds to set
	 */
	public void setDifferentialAuthorityIds(List<Long> differentialAuthorityIds) {
		this.differentialAuthorityIds = differentialAuthorityIds;
	}
	public void addDifferentialAuthorityId(long differentialAuthorityId) {
		if (!differentialAuthorityIds.contains(differentialAuthorityId)) {
			differentialAuthorityIds.add(differentialAuthorityId);
		}
	}
	public void addDifferentialAuthorityId(Long differentialAuthorityId) {
		if (differentialAuthorityId != null) {
			addDifferentialAuthorityId(differentialAuthorityId.longValue());
		}
	}

	/**
	 * @return the authorityIds
	 */
	public List<Long> getAuthorityIds() {
		return authorityIds;
	}

	public void addAuthorityId(long authorityId) {
		if (!authorityIds.contains(authorityId)) {
			authorityIds.add(authorityId);
		}
	}
	public void addAuthorityId(Long authorityId) {
		if (authorityId != null) {
			addAuthorityId(authorityId.longValue());
		}
	}
	/**
	 * @param authorityIds the authorityIds to set
	 */
	public void setAuthorityIds(List<Long> authorityIds) {
		this.authorityIds = authorityIds;
	}
	/**
	 * Adds the permission from the passed in restriction object.
	 * @param restriction
	 */
	public void mergeRestriction(Restriction restriction) {
		this.addParentAuthorityIds(restriction.getParentAuthorityIds());
		this.addChildAuthorityIds(restriction.getChildAuthorityIds());
		this.addAuthorityIds(restriction.getAuthorityIds());
		this.addDifferentialAuthorityIds(restriction.getDifferentialAuthorityIds());
	}

	/**
	 * @param authIds {@link List}
	 */
	private void addAuthorityIds(List<Long> authIds) {
		if (authIds != null && CollectionUtils.isNotEmpty(authIds)) {
			this.childAuthorityIds.addAll(authIds);
		}
	}

	/**
	 * @param differentialAuthIds {@link List}
	 */
	private void addDifferentialAuthorityIds(
			List<Long> differentialAuthIds) {
		if (differentialAuthIds != null && CollectionUtils.isNotEmpty(differentialAuthIds)) {
			this.childAuthorityIds.addAll(differentialAuthIds);
		}
	}

	/**
	 * @param childAuthIds {@link List}
	 */
	private void addChildAuthorityIds(List<Long> childAuthIds) {
		if (childAuthIds != null && CollectionUtils.isNotEmpty(childAuthIds)) {
			this.childAuthorityIds.addAll(childAuthIds);
		}
	}

	/**
	 * @param parentAuthIds {@link List}
	 */
	private void addParentAuthorityIds(List<Long> parentAuthIds) {
		if (parentAuthIds != null && CollectionUtils.isNotEmpty(parentAuthIds)) {
			this.parentAuthorityIds.addAll(parentAuthIds);
		}
	}
}