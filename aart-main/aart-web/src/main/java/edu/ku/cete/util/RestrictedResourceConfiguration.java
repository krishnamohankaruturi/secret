package edu.ku.cete.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;

/**
 * @author m802r921
 *
 */
@Component
public class RestrictedResourceConfiguration {
	/**
	 * restrictedResourceTypeCode
	 */
	@Value("${restrictedResourceTypeCode}")
	private String restrictedResourceTypeCode;
	/**
	 * rosterResourceCode.
	 */
	@Value("${rosterResourceCode}")
	private String rosterResourceCode;
	/**
	 * enrollmentResourceCode.
	 */  
	@Value("${enrollmentResourceCode}")
	private String enrollmentResourceCode;
	/**
	 * rosterResourceCategory.
	 */
	private Category rosterResourceCategory;
	/**
	 * categoryService.
	 */
	@Autowired
	private CategoryService categoryService;
	/**
	 * enrollmentResourceCategory.
	 */
	private Category enrollmentResourceCategory;
	//TODO move it to properties file.
	/**
	 * PERM_ROSTER_RECORD_VIEWALL.
	 */
	private static final String PERM_ROSTER_RECORD_VIEWALL = "PERM_ROSTERRECORD_VIEWALL";
	/**
	 * PERM_ROSTER_RECORD_SEARCH.
	 */
	private static final String PERM_ROSTER_RECORD_SEARCH = "PERM_ROSTERRECORD_SEARCH";
	/**
	 * PERM_ROSTERRECORD_UPLOAD.
	 */
	private static final String PERM_ROSTERRECORD_UPLOAD = "PERM_ROSTERRECORD_UPLOAD";
	/**
	 * PERM_ROSTER_RECORD_VIEW.
	 */
	private static final String PERM_ROSTER_RECORD_VIEW = "PERM_ROSTERRECORD_VIEW";
	/**
	 * PERM_ENROLLMENTRECORD_UPLOAD.
	 */
	private static final String PERM_ENROLLMENTRECORD_UPLOAD = "PERM_ENRL_UPLOAD";
	/**
	 * PERM_ORG_VIEW.
	 */
	private static final String PERM_ORGANIZATION_VIEW = "PERM_ORG_VIEW";
	/**
	 * HIGH_STAKES.
	 */
	private static final String HIGH_STAKES_PERMISSION = "HIGH_STAKES";	
	/**
	 * HIGH_STAKES.
	 */
	private static final String HIGH_STAKES_TESTSESSION_VIEW_PERMISSION = "HIGH_STAKES_TESTSESSION_VIEW";	
	
	/**
	 * QUALITY_CONTROL_COMPLETE.
	 */
	private static final String QUALITY_CONTROL_COMPLETE_PERMISSION = "QUALITY_CONTROL_COMPLETE";
	
	private static final String RELEASE_MODULE_PERMISSION = "REL_MODULE";
	
	private static final String UNRELEASE_MODULE_PERMISSION = "UNREL_MODULE";
	
	private static final String STATE_CEU_PERMISSION = "STATE_CEU";
	
	private static final String PUBLISH_MODULE_PERMISSION = "PUB_MODULE";
	
	private static final String UNPUBLISH_MODULE_PERMISSION = "UNPUB_MODULE";
	
	private static final String EDIT_MODULE_PERMISSION = "EDIT_MODULES";
	
	private static final String VIEW_PD_ADMIN_PERMISSION = "VIEW_ADMIN";
	
	private static final String PERM_ROLE_MODIFY = "PERM_ROLE_MODIFY";
	
	private static final String HIGH_STAKES_SPL_CIRCUM_CODE_SEL = "HIGH_STAKES_SPL_CIRCUM_CODE_SEL";
	
	private static final String PERM_EXIT_ALT_STUDENT = "PERM_EXIT_ALT_STUDENT";
	
	private static final String END_TEST_SESSION_PERMISSION = "END_STUDENT_TESTSESSION";
	
	private static final String REACTIVATE_TEST_SESSION_PERMISSION = "REACTIVATE_STUDENT_TESTSESSION";
	
	private static final String VIEW_PREDICTIVE_STUDENT_SCORE = "VIEW_PREDICTIVE_STUDENT_SCORE";
	
	private static final String PERM_STUDENTRECORD_MODIFY = "PERM_STUDENTRECORD_MODIFY";
	
	/**
	 * @return the restrictedResourceTypeCode
	 */
	public String getRestrictedResourceTypeCode() {
		return restrictedResourceTypeCode;
	}
	/**
	 * @param restrictedResourceTypeCode the restrictedResourceTypeCode to set
	 */
	public void setRestrictedResourceTypeCode(String restrictedResourceTypeCode) {
		this.restrictedResourceTypeCode = restrictedResourceTypeCode;
	}
	/**
	 * @return the rosterResourceCode
	 */
	public String getRosterResourceCode() {
		return rosterResourceCode;
	}
	/**
	 * @param rosterResourceCode the rosterResourceCode to set
	 */
	public void setRosterResourceCode(String rosterResourceCode) {
		this.rosterResourceCode = rosterResourceCode;
	}
	/**
	 * @return the rosterResourceCategory
	 */
	public Category getRosterResourceCategory() {
		return rosterResourceCategory;
	}
	/**
	 * @param rosterResourceCategory the rosterResourceCategory to set
	 */
	public void setRosterResourceCategory(Category rosterResourceCategory) {
		this.rosterResourceCategory = rosterResourceCategory;
	}
	/**
	 * @return the enrollmentResourceCategory
	 */
	public Category getEnrollmentResourceCategory() {
		return enrollmentResourceCategory;
	}
	/**
	 * @param enrollmentResourceCategory the enrollmentResourceCategory to set
	 */
	public void setEnrollmentResourceCategory(Category enrollmentResourceCategory) {
		this.enrollmentResourceCategory = enrollmentResourceCategory;
	}
	/**
	 * @return the permRosterrecordViewall
	 */
	public static String getViewAllRostersPermissionCode() {
		return PERM_ROSTER_RECORD_VIEWALL;
	}
	/**
	 * @return the permRosterRecordSearch
	 */
	public static String getSearchRosterPermissionCode() {
		return PERM_ROSTER_RECORD_SEARCH;
	}
	/**
	 * @return {@link String}
	 */
	public static String getUploadRosterPermissionCode() {
		return PERM_ROSTERRECORD_UPLOAD;
	}
	/**
	 * @return the permRosterrecordViewall
	 */
	public static String getViewRostersPermissionCode() {
		return PERM_ROSTER_RECORD_VIEW;
	}
	/**
	 * @return {@link String}
	 */
	public static String getUploadEnrollmentPermissionCode() {
		return PERM_ENROLLMENTRECORD_UPLOAD;
	}
	
	public static String getAddEnrollmentPermissionCode() {
		return "PERM_STUDENTRECORD_CREATE";
	}
	
	/**
	 * @return  {@link String}
	 */
	public static String getHighStakesPermission() {
		return HIGH_STAKES_PERMISSION;
	}
	/**
	 * @return  {@link String}
	 */
	public static String getViewHighStakesTestSessionsPermission() {	
		return HIGH_STAKES_TESTSESSION_VIEW_PERMISSION;
	}
	/**
	 * @return {@link String}
	 */
	public static String getQualityControlCompletePermission() {
		return QUALITY_CONTROL_COMPLETE_PERMISSION;
	}
	/**
	 * @return {@link String}
	 */
	public static String getViewOrganizationPermissionCode() {
		return PERM_ORGANIZATION_VIEW;
	}
	
	public static String getReleaseModulePermission() {
		return RELEASE_MODULE_PERMISSION;
	}
	
	public static String getUnreleaseModulePermission() {
		return UNRELEASE_MODULE_PERMISSION;
	}
	
	public static String getEditModulePermission() {
	return EDIT_MODULE_PERMISSION;
	}
	
	public static String getStateCEUPermission() {
		return STATE_CEU_PERMISSION;
	}

	public static String getPublishModulePermission() {
		return PUBLISH_MODULE_PERMISSION;
	}
	
	public static String getUnpublishModulePermission() {
		return UNPUBLISH_MODULE_PERMISSION;
	}
	
	public static String getViewPDAdminPermission() {
		return VIEW_PD_ADMIN_PERMISSION;
	}
	public static String getPermRoleModify() {
		return PERM_ROLE_MODIFY;
	}
	
	public static String getHighStakesSplCircumstanceApprovalPermission(){
		return HIGH_STAKES_SPL_CIRCUM_CODE_SEL;
	}

	public static String getExitDLMStudentPermissionCode() {
		return PERM_EXIT_ALT_STUDENT;
	}
	
	public static String getEndTestSessionPermission() {
		return END_TEST_SESSION_PERMISSION;
	}
	public static String getReactivateTestSessionPermission() {
		return REACTIVATE_TEST_SESSION_PERMISSION;
	}
	public static String getViewPredictiveStudentScorePermission(){
		return VIEW_PREDICTIVE_STUDENT_SCORE;
	}
	
	public static String getEditStudentPermissionCode() {
		return PERM_STUDENTRECORD_MODIFY;
	}
	/**
	 * sets the restricted resource categories.
	 */
	@PostConstruct
	public final void setRestrictedResources() {
		setRosterResourceCategory(
				categoryService.selectByCategoryCodeAndType(rosterResourceCode, restrictedResourceTypeCode));
		setEnrollmentResourceCategory(categoryService.selectByCategoryCodeAndType(enrollmentResourceCode,
				restrictedResourceTypeCode));
	}

}
