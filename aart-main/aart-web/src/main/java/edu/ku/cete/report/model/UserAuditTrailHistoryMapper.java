package edu.ku.cete.report.model;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.ActivationTemplateAuditTrailHistory;
import edu.ku.cete.report.domain.FCSComplexityBandHistory;
import edu.ku.cete.report.domain.FirstContactSurveyAuditHistory;
import edu.ku.cete.report.domain.OrganizationAuditTrailHistory;
import edu.ku.cete.report.domain.RoleAuditTrailHistory;
import edu.ku.cete.report.domain.RosterAuditTrailHistory;
import edu.ku.cete.report.domain.StudentAuditTrailHistory;
import edu.ku.cete.report.domain.UserAuditTrailHistory;

/*sudhansu : Created for US17687 -For user audit trail
 * */
public interface UserAuditTrailHistoryMapper {
	

	int insert(UserAuditTrailHistory record);

	Date getLastJobRunDateTime();
	
	//Added during US18004 - comparing organization json string
	void insertOrganizationAuditTrail(OrganizationAuditTrailHistory organizationAuditTrailHistory);
	
	//Added during US18005 - comparing role json string
	void insertRoleAuditTrail(UserAuditTrailHistory userAuditTrailHistory);
	
	//Added during US18182- comparing Roster json string
		void insertRosterAuditTrail(RosterAuditTrailHistory rosterAuditTrailHistory);
	
	//Added during US18184 - storing student audit data 
    void insertStudent(StudentAuditTrailHistory record);
    
    void insertSurvey(FirstContactSurveyAuditHistory record);
    
  //Added during US19029 - storing ActivationEmailTamplete audit data 
    void insertActivationEmailAuditTrail(ActivationTemplateAuditTrailHistory activationTemplateAuditTrailHistory);

	List<UserAuditTrailHistory> getUserAuditTrailHistory(@Param("startDateTimes")Date startDateTimes,
			@Param("endDateTimes")Date endDateTimes,@Param("educatorId") String educatorId,@Param("emailAddress") String emailAddress,@Param("offset") int offset,@Param("limit") int limit);

	List<StudentAuditTrailHistory> getStudentAuditTrailHistory(
			@Param("startDateTimes")Date startDateTimes, @Param("endDateTimes")Date endDateTimes,@Param("stateStudentId") String stateStudentId,@Param("offset") int offset,@Param("limit") int limit);

	List<RosterAuditTrailHistory> getRosterAuditTrailHistory(
			@Param("startDateTimes")Date startDateTimes,  @Param("endDateTimes")Date endDateTimes, @Param("stateId") String stateId,@Param("schoolIds") List schoolIds,@Param("contentArea") String contentArea,@Param("educatorId") String educatorId,@Param("offset") int offset,@Param("limit") int limit);

	List<RoleAuditTrailHistory> getRoleAuditTrailHistory(
			@Param("startDateTimes")Date startDateTimes, @Param("endDateTimes")Date endDateTimes, @Param("assessmentProgram")String assessmentProgram,@Param("stateId")String stateId,@Param("groupId")String groupId,@Param("offset") int offset,@Param("limit") int limit);

	List<OrganizationAuditTrailHistory> getOrganizationAuditTrailHistory(
			@Param("startDateTimes")Date startDateTimes, @Param("endDateTimes")Date endDateTimes, @Param("filterValues") Map<String,String> filterValues,@Param("offset") int offset,@Param("limit") int limit);

	List<FirstContactSurveyAuditHistory> getFirstContactSurveyAuditHistory(
				@Param("startDateTimes")Date startDateTimes, @Param("endDateTimes")Date endDateTimes,@Param("stateStudentId") String stateStudentId,@Param("offset") int offset,@Param("limit") int limit);

	List<ActivationTemplateAuditTrailHistory> getActivationTemplateAuditTrailHistory(
			@Param("startDateTimes")Date startDateTimes, @Param("endDateTimes")Date endDateTimes, @Param("assessmentProgram")String assessmentProgram,@Param("stateId")String stateId,@Param("offset") int offset,@Param("limit") int limit);

    void insertComplexityBandHistory(FCSComplexityBandHistory fcsComplexityBandHistory);

	
}
