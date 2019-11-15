package edu.ku.cete.domain.common;

import java.io.Serializable;
import java.util.Comparator;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 *
 * @author neil.howerton
 *
 */
public class OrganizationType extends AuditableDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7084000914112413316L;
	private long organizationTypeId;
    private String typeName;
    private String typeCode;
    private int typeLevel;

    /**
     *
     *@return long
     */
    public final long getOrganizationTypeId() {
        return organizationTypeId;
    }

    /**
     *
     *@param organizationTypeId long
     */
    public final void setOrganizationTypeId(long organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    /**
     *
     *@return {@link String}
     */
    public final String getTypeName() {
        return typeName;
    }

    /**
     *
     *@param typeName {@link String}
     */
    public final void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     *@return {@link String}
     */
    public final String getTypeCode() {
        return typeCode;
    }

    /**
     *
     *@param typeCode {@link String}
     */
    public final void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     *
     *@return int
     */
    public final int getTypeLevel() {
        return typeLevel;
    }

    /**
     *
     *@param typeLevel
     */
    public final void setTypeLevel(int typeLevel) {
        this.typeLevel = typeLevel;
    }
    
    public static Comparator<OrganizationType> OrgTypeLevelComparator = new Comparator<OrganizationType>() {

    	public int compare(OrganizationType o1, OrganizationType o2) {
    		return o1.getTypeLevel() - o2.getTypeLevel();
    	}

    };
}
