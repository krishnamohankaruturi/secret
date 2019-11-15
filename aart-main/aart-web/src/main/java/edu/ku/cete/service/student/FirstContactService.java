package edu.ku.cete.service.student;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.domain.student.survey.StudentSurveyResponse;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabelInfo;
import edu.ku.cete.domain.student.survey.SurveyLabel;
import edu.ku.cete.domain.student.survey.SurveyLabelPrerequisite;
import edu.ku.cete.domain.student.survey.SurveyPageStatus;
import edu.ku.cete.domain.student.survey.SurveySection;
import edu.ku.cete.domain.student.survey.SurveySectionComposite;
import edu.ku.cete.domain.upload.FirstContactRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.report.domain.DomainAuditHistory;

/**
 * @author mahesh
 *
 */
public interface FirstContactService {
	
	/**
	 * @param firstContactRecord
	 * @return
	 */
	FirstContactRecord cascadeAddOrUpdate(FirstContactRecord firstContactRecord);

	/**
	 * @param studentId
	 * @return
	 */
	Survey addSurveyIfNotPresent(FirstContactRecord firstContactRecord);

	/**
	 * @param surveyId
	 * @param globalPageNum
	 * @return
	 */
	List<StudentSurveyResponseLabelInfo> selectAllowedStudentSurveyResponseLabels(Long surveyId, Integer globalPageNum);

	/**
	 * @param studentId
	 * @return
	 */
	Survey addSurveyIfNotPresent(Long studentId);

	/**
	 * @return
	 */
	List<SurveySectionComposite> selectAllSurveySections();
	
	/**
	 * @param studentId
	 * @return {@link Boolean}
	 */
	boolean checkForExistingSurvey(Long studentId);
	
	/**
	 * @param globalPageNum
	 * @param surveyId
	 * @return
	 */
	String getSurveyPageStatus(Integer globalPageNum, Long surveyId, Long userStateId);
	
	/**
	 * @param globalPageNum
	 * @param studentId
	 */
	void insertSurveyPageStatus(Integer globalPageNum, Long studentId);
	
	/**
	 * @param globalPageNum
	 * @param surveyId
	 */
	void updateSurveyPageStatus(Integer globalPageNum, Long surveyId, boolean batch, Long userStateId);

	void updateSurveyPageStatusToNotComplete(Integer globalPageNum, Long surveyId, String status, Long userStateId);

	/**
	 * @param surveyId
	 * @param surveyResponseId
	 * @param user 
	 * @return
	 */
	void addOrUpdateStudentSurvey(Long surveyId, String labelNumber, Long surveyResponseId, String surveyResponseText, User user);

	/**
	 * @param surveyId
	 * @param labelNumber
	 */
	void updateExistingStudentSurveyResponseActiveFlag(Long surveyId, String labelNumber);
	
	/**
	 * @param surveyId
	 * @param surveyResponseId
	 */
	void insertStudentSurveyResponse(Long surveyId, Long surveyResponseId, String surveyResponseText);
	
	/**
	 * @param studentSurveyResponseId
	 */
	void updateStudentSurveyResponse(StudentSurveyResponse studentSurveyResponse, String surveyResponseText);

	/**
	 * @return
	 */
	//Collection<SurveyLabelPreRequisiteInfo> selectAllowedStudentSurveyResponseLabelInfos();
	
	/**
	 * @param surveyId
	 * @return List<Integer>
	 */
	List<Integer> getSurveyPageStatusList(Long surveyId);
	
	/**
	 * @return
	 */
	List<SurveyLabelPrerequisite> selectAllowedStudentSurveyResponseLabelInfoList(Integer globalPageNum);

	/**
	 * @param surveyId
	 * @param globalPageNum
	 * @return
	 */
	List<SurveyLabelPrerequisite> selectBySurveyAndGlobalPageNum(Long surveyId, Integer globalPageNum);

	void updateSurveyStatusToComplete(Long surveyId, User user);

	void updateFirstContactSkippedResponses(Long surveyId, String[] labelNumbers);

	Map<String, Object> getFirstContactResponses(Long studentId, Long surveyId,
			int lastPageToBeSaved,int pageNumber, String status, boolean settingsChanged, Long stateId,boolean isToggleOnCategoryChange);

	void updateStudentTrackerTestSession(Long studentId, User user);

	void addOrUpdateStudentSurvey(Long surveyId, List<StudentSurveyResponseLabel> studentSurveyResponseLabelList,
			User user, int currentPageNum);

	Survey findByStudentId(Long studentId);

	Survey findBySurveyId(Long surveyId);

	int updateSurveyStatus(Survey survey);

	List<FirstContactSettings> getFirstContactSurveySettings(Long organizationId);
	
	int updateFirstContactSurveySettings(FirstContactSettings firstContactSettings);

	List<SurveySection> getSurveyRootSections();

	List<SurveySection> getSurveySubSections();
	
	List<Integer> getActiveRootSections(Long surveyId);
	
	FirstContactSettings getFirstContactSurveySetting(Long organizationId);

	int getFirstContactActivePageCount();

	List<SurveyLabel> selectSurveyLabelsByAndCondition();

	void flipFirstContactSurveyOnOptionChange(Long surveyId, Long enforcingOption, Long lastEditedOption,
			boolean isToggleOnCategoryCange, Long userStateId);

	List<SurveyPageStatus> getSurveyPageStatus(Long surveyId);

	boolean getAllQuestionsAnsweredFlag(Integer globalPageNum, Long surveyId);
	
	FirstContactSettings getOrganizationScienceFlagSetting(Long organizationId, Long schoolYear, Boolean currentSchYr, Boolean prevSchYr);
	
	void resetSurveyStatus(Long organizationid, Long inProgressStatusId, Long completeStatusId, Long readyToSubmitId);
	
	int inactivateOtherSchoolYearRecords(Long schoolYear, Long organizationId);
	
	FirstContactSettings checkIfOrgSettingsExists(Long organizationId, Long schoolYear);
	
	int insertFirstContactSettings(FirstContactSettings firstContactSettings);
	
	void insertIntoDomainAuditHistory(Long objectId, String action, String source, String userBeforUpdate, String userAfterUpdate);

	boolean addToGroupAuditTrailHistory(DomainAuditHistory domainAuditHistory);
	
	public void toggleSurveyOnCategoryChange(FirstContactSettings persistedFcs, FirstContactSettings newFirstContactSettings);
	
	boolean hasFcsReadOnlyPermission();
	
	public Long getUserStateId();

	void toggleSurveyOnCategoryChange(List<FirstContactSettings> fcsSettings);

	List<SurveySection> getNoOfSubSections();
}
