/**
 * 
 */
package edu.ku.cete.domain.user;

	
/**
 * @author bmohanty_sta
 *
 */
public class OrganizationRoleRequest {

	private Long organizationId;
    private Long defaultRoleId;
    private Long[] rolesIds;
    private Long state;
    private Long region;
    private Long area;
    private Long district;
    private Long building;
    private Long school;
    private Long assessmentProgramId;

    /**
     *
     */
    public OrganizationRoleRequest() {
        
    }

	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the defaultRoleId
	 */
	public Long getDefaultRoleId() {
		return defaultRoleId;
	}

	/**
	 * @param defaultRoleId the defaultRoleId to set
	 */
	public void setDefaultRoleId(Long defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	/**
	 * @return the rolesIds
	 */
	public Long[] getRolesIds() {
		return rolesIds;
	}

	/**
	 * @param rolesIds the rolesIds to set
	 */
	public void setRolesIds(Long[] rolesIds) {
		this.rolesIds = rolesIds;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * @return the region
	 */
	public Long getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Long region) {
		this.region = region;
	}

	/**
	 * @return the area
	 */
	public Long getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Long area) {
		this.area = area;
	}

	/**
	 * @return the district
	 */
	public Long getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(Long district) {
		this.district = district;
	}

	/**
	 * @return the building
	 */
	public Long getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(Long building) {
		this.building = building;
	}

	/**
	 * @return the school
	 */
	public Long getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(Long school) {
		this.school = school;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
}
