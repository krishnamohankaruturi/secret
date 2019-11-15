/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.ContentAreaExample;
import edu.ku.cete.domain.test.ContentFrameworkDetail;

/**
 * @author neil.howerton
 *
 */
public interface ContentAreaService {
    /**
     *
     *@param example
     *@return
     */
    int countByExample(ContentAreaExample example);

    /**
     *
     *@param example
     *@return
     */
    int deleteByExample(ContentAreaExample example);

    /**
     *
     *@param id
     *@return
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     *@param example
     *@return
     */
    List<ContentArea> selectByExample(ContentAreaExample example);

    /**
     *
     *@param id
     *@return
     */
    ContentArea selectByPrimaryKey(Long id);

    /**
     *
     *@param record
     *@param example
     *@return
     */
    int updateByExampleSelective(@Param("record") ContentArea record, @Param("example") ContentAreaExample example);

    /**
     *
     *@param record
     *@param example
     *@return
     */
    int updateByExample(@Param("record") ContentArea record, @Param("example") ContentAreaExample example);

    /**
     *
     *@param record
     *@return
     */
    int updateByPrimaryKeySelective(ContentArea record);

    /**
     *
     *@param record
     *@return
     */
    int updateByPrimaryKey(ContentArea record);

    /**
     *
     *@param name
     *@return
     */
    ContentArea findByName(String name);

    /**
    *
    *@param abbreviatedName
    *@return
    */
   ContentArea findByAbbreviatedName(String abbreviatedName);
    
    /**
     *
     *@param assessmentProgramId
     *@return
     */
    List<ContentArea> findByAssessmentProgram(long assessmentProgramId);

    /**
    *
    *@param assessmentId long
    *@return List<ContentArea>
    */
   List<ContentArea> findByAssessmentId(Long assessmentId);
   
   /**
	 * @return
	 */
	List<ContentArea> selectAllContentAreas();
   
	/**
	 * @return
	 */
	List<ContentArea> selectContentAreas();

	ContentArea findByContractingOrg(Long organizationId, String contentAreaCode);

	ContentArea findByNameOrAbbreviatedName(String name);

	List<ContentArea> findByAssessmentProgramAndTestingProgram(
			Long assessmentProgramId, Long testingProgramId);

	ContentArea findByTestTypeSubjectAreaAssessment(Long testTypeId,
			Long subjectAreaId, Long assessmentId);

	List<ContentArea> getByTestTypeAssessment(Long testTypeId, Long assessmentId);
	
	List<ContentArea> getGradeCoursesUsingAssessmentProgram(Long assessmentProgramId);

	List<ContentArea> getForStudentTracker(Long organizationId);
	
	List<ContentArea> getContentAreasForDLMMultiAssignments(List<String> contentAreaCodes);
	
	ContentArea findBySubjectAreaIdAndTestTypeCodeAndAssessmentId(String testTypeCode, Long subjectAreaId, Long assessmentId);
	
	List<ContentArea> getContentAreasWhereReportsHaveProcessed(Map<String, Object> parameters);

	List<ContentArea> findByAssessmentProgramforTestRecord(
			Long assessmentProgramId);
	List<ContentArea> findByAssessmentProgramforAssignScorer(long assessmentProgramId, long schoolAreaId, int currentSchoolYear);

	Long findByTestTypeAssessmentContentArea(Long assessmentId, Long testTypeId, Long contentAreaId);

	List<ContentArea> getSubjectsByGrade(Long gradeId);

	List<ContentArea> getContententAreasByOtwId(Long operationalTestWindowId);
	
	List<ContentArea> getInterimSubjectNames(Long purposeId, Boolean isInterim, Long organizationId,
			Long assessmentProgramId, boolean isTeacher, boolean isUserLoggedAsPLTW,Long userId);

	List<ContentFrameworkDetail> getInterimAlignment(Long contentAreaId, Long gradeCourseId, Boolean isInterim,
			Long purposeId, Long organizationId, Long assessmentProgramId, Long schoolYear);
	
	List<ContentArea> getSubjectsForStudentScoreExtract();

	List<ContentArea> getItiBluePrintSubjects(long schoolYear, boolean isTeacher, Long educatorId);

	List<ContentArea> getContentAreasForResearchSurvey();
	
	List<ContentArea> getContentAreasForISMARTResearchSurvey();
	
	List<Long> getContentAreaIdsByTestTypeAndSubjectArea(List<EnrollmentTestTypeSubjectArea> enrollmentTestTypeSubjectAreas);

	List<ContentArea> getContentAreaForBundledReport(Long[] schoolIds, Long assessmentProgId, String assessmentProgCode, int schoolYear);

	List<ContentArea> findByAssessmentProgramandTestingProgramId(Long assessmentProgramId, Long testingProgramId);

	List<ContentArea> selectAllContentAreasDropdown(Long currentAssessmentProgramId);

	List<ContentArea> findByAssessmentProgramCode(String assessmentProgramCode);

	List<ContentArea> findByAbbreviatedNamesAndAssessmentProgramCode(String[] contentAreaCodes,
			String assessmentProgramCode);

	List<ContentArea> getSubjectsForReportGeneration(Long assessmentProgramId,
			Long schoolYear, Long stateId);
	
	List<ContentArea> selectContentAreasForISmartAutoEnrollment(Long organizationId, String contentAreaAbbrName);

	List<ContentArea> getSubjectsByGradeAndAssessment(String abbreviatedName, List<Long> assessmentProgramIds);
	
	List<String> getCourseNameByAssesmentProgram(String assesmentProgram);

	List<Long> getContentAreasIdForDistrictSummaryReportsHaveProcessed(Map<String, Object> params);	
}
