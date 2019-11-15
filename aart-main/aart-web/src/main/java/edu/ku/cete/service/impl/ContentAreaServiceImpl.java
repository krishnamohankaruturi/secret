/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.ContentAreaExample;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.model.ContentAreaDao;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.service.ContentAreaService;

/**
 * @author neil.howerton
 *
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ContentAreaServiceImpl implements ContentAreaService {

	@Autowired
	private ContentAreaDao contentAreaDao;
	@Autowired
	private ContentFrameworkDetailDao contentFrameworkDetailDao;
	@Value("${aartOriginationCode}")
	private String aartOriginationCode;

	/**
	 * @return the aartOriginationCode
	 */
	public String getAartOriginationCode() {
		return aartOriginationCode;
	}

	/**
	 * @param aartOriginationCode
	 *            the aartOriginationCode to set
	 */
	public void setAartOriginationCode(String aartOriginationCode) {
		this.aartOriginationCode = aartOriginationCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#countByExample(edu.ku.cete.domain.
	 * ContentAreaExample)
	 */
	@Override
	public final int countByExample(ContentAreaExample example) {
		return contentAreaDao.countByExample(example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#deleteByExample(edu.ku.cete.domain
	 * .ContentAreaExample)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByExample(ContentAreaExample example) {
		return contentAreaDao.deleteByExample(example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByPrimaryKey(Long id) {
		return contentAreaDao.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#selectByExample(edu.ku.cete.domain
	 * .ContentAreaExample)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> selectByExample(ContentAreaExample example) {
		return contentAreaDao.selectByExample(example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea selectByPrimaryKey(Long id) {
		return contentAreaDao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#updateByExampleSelective(edu.ku.
	 * cete.domain.ContentArea, edu.ku.cete.domain.ContentAreaExample)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExampleSelective(ContentArea record, ContentAreaExample example) {
		return contentAreaDao.updateByExampleSelective(record, example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#updateByExample(edu.ku.cete.domain
	 * .ContentArea, edu.ku.cete.domain.ContentAreaExample)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExample(ContentArea record, ContentAreaExample example) {
		return contentAreaDao.updateByExample(record, example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#updateByPrimaryKeySelective(edu.ku
	 * .cete.domain.ContentArea)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKeySelective(ContentArea record) {
		return contentAreaDao.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.ContentAreaService#updateByPrimaryKey(edu.ku.cete.
	 * domain.ContentArea)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKey(ContentArea record) {
		return contentAreaDao.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#findByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea findByName(String name) {
		return contentAreaDao.findByName(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#findByAbbreviatedName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea findByAbbreviatedName(String abbreviatedName) {
		return contentAreaDao.findByAbbreviatedName(abbreviatedName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#findByAbbreviatedName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea findByNameOrAbbreviatedName(String abbreviatedName) {
		return contentAreaDao.findByNameOrAbbreviatedName(abbreviatedName);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea findByContractingOrg(Long organizationId, String contentAreaCode) {
		return contentAreaDao.findByContractingOrg(organizationId, contentAreaCode);
	}

	/**
	 * @param assessmentProgramId
	 *            long
	 * @return List<ContentArea>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> findByAssessmentProgram(long assessmentProgramId) {
		return contentAreaDao.findByAssessmentProgram(assessmentProgramId);
	}
	
	/**
	 * @param assessmentProgramId
	 *            long
	 * @return List<ContentArea>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> findByAssessmentProgramandTestingProgramId(Long assessmentProgramId, Long testingProgramId) {
		return contentAreaDao.findByAssessmentProgramandTestingProgramId(assessmentProgramId, testingProgramId);
	}
	
	/**
	 * @param assessmentId
	 *            Long
	 * @return List<ContentArea>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> findByAssessmentId(Long assessmentId) {
		return contentAreaDao.findByAssessmentId(assessmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#selectAllContentAreas()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> selectAllContentAreas() {
		return contentAreaDao.selectAllContentAreas();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#selectContentAreas()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> selectContentAreas() {
		return contentAreaDao.selectContentAreas();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> getForStudentTracker(Long organizationId) {
		return contentAreaDao.selectContentAreasForStudentTracker(organizationId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> getContentAreasForDLMMultiAssignments(List<String> contentAreaCodes) {
		return contentAreaDao.selectContentAreasForDLMMultiAssignments(contentAreaCodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.ContentAreaService#selectContentAreas()
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> findByAssessmentProgramAndTestingProgram(Long assessmentProgramId,
			Long testingProgramId) {
		return contentAreaDao.findByAssessmentProgramAndTestingProgram(assessmentProgramId, testingProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ContentArea findByTestTypeSubjectAreaAssessment(Long testTypeId, Long subjectAreaId,
			Long assessmentId) {
		return contentAreaDao.findByTestTypeSubjectAreaAssessment(testTypeId, subjectAreaId, assessmentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> getByTestTypeAssessment(Long testTypeId, Long assessmentId) {
		return contentAreaDao.findByTestTypeAssessment(testTypeId, assessmentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> getGradeCoursesUsingAssessmentProgram(Long assessmentProgramId) {
		return contentAreaDao.findGradeCoursesUsingAssessmentProgram(assessmentProgramId);
	}

	@Override
	public ContentArea findBySubjectAreaIdAndTestTypeCodeAndAssessmentId(String testTypeCode, Long subjectAreaId,
			Long assessmentId) {
		return contentAreaDao.findBySubjectAreaIdAndTestTypeCode(testTypeCode, subjectAreaId, assessmentId);
	}

	@Override
	public List<ContentArea> getContentAreasWhereReportsHaveProcessed(Map<String, Object> parameters) {
		/*
		 * String reportType= (String) parameters.get("reportType"); String
		 * assessmentProgCode = (String) parameters.get("assessmentProgCode");
		 * 
		 * if(reportType.equals("alternate_student")) reportType =
		 * reportType.replace("alternate_student","ALT_ST"); else
		 * if(reportType.equals("alternate_roster")) reportType =
		 * reportType.replace("alternate_roster","ALT_CR"); else
		 * if(reportType.equals("general_student") &&
		 * assessmentProgCode.equals("KAP")) reportType =
		 * reportType.replace("general_student","GEN_ST"); else
		 * if(reportType.equals("alternate_student_individual") &&
		 * assessmentProgCode.equals("CPASS")) reportType =
		 * reportType.replace("alternate_student_individual","CPASS_GEN_ST");
		 * else if(reportType.equals("alternate_student_individual") &&
		 * assessmentProgCode.equals("DLM")) reportType =
		 * reportType.replace("alternate_student_individual","ALT_ST_IND");
		 * 
		 * parameters.put("reportType", reportType);
		 */
		return contentAreaDao.getContentAreasWhereReportsHaveProcessed(parameters);
	}

	@Override
	public List<ContentArea> findByAssessmentProgramforTestRecord(Long assessmentProgramId) {
		return contentAreaDao.findByAssessmentProgramforTestRecord(assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> findByAssessmentProgramforAssignScorer(long assessmentProgramId ,long schoolAreaId,int currentSchoolYear) {
		return contentAreaDao.findByAssessmentProgramforAssignScorer(assessmentProgramId , schoolAreaId,currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Long findByTestTypeAssessmentContentArea(Long assessmentId, Long testTypeId, Long contentAreaId) {
		return contentAreaDao.findByTestTypeAssessmentContentArea(assessmentId, testTypeId, contentAreaId);
	}

	@Override
	public List<ContentArea> getSubjectsByGrade(Long gradeId) {
		return contentAreaDao.getSubjectsByGrade(gradeId);
	}

	@Override
	public List<ContentArea> getContententAreasByOtwId(Long operationalTestWindowId) {
		return contentAreaDao.getContententAreasByOtwId(operationalTestWindowId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getInterimSubjectNames(Long purposeId, Boolean isInterim, Long organizationId,
			Long assessmentProgramId, boolean isTeacher, boolean isUserLoggedAsPLTW,Long userId) {
		return contentAreaDao.getInterimSubjectNames(purposeId, isInterim, organizationId, assessmentProgramId,
				isTeacher, isUserLoggedAsPLTW, userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentFrameworkDetail> getInterimAlignment(Long contentAreaId, Long gradeCourseId, Boolean isInterim,
			Long purposeId, Long organizationId, Long assessmentProgramId, Long schoolYear) {
		return contentFrameworkDetailDao.selectAllInterim(contentAreaId, gradeCourseId, isInterim, purposeId,
				organizationId, assessmentProgramId, schoolYear);
	}
	
	@Override
	public List<ContentArea> getSubjectsForStudentScoreExtract() {
		return contentAreaDao.getSubjectsForStudentScoreExtract();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getItiBluePrintSubjects(long schoolYear, boolean isTeacher, Long educatorId) {
		return contentAreaDao.getItiBluePrintSubjects(schoolYear, isTeacher, educatorId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getContentAreasForResearchSurvey() {		
		return contentAreaDao.getContentAreasForResearchSurvey();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getContentAreasForISMARTResearchSurvey() {		
		return contentAreaDao.getContentAreasForISMARTResearchSurvey();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getContentAreaIdsByTestTypeAndSubjectArea(List<EnrollmentTestTypeSubjectArea> enrollmentTestTypeSubjectAreas) {
		return contentAreaDao.getContentAreaIdsByTestTypeAndSubjectArea(enrollmentTestTypeSubjectAreas);
	}

	@Override
	public List<ContentArea> getContentAreaForBundledReport(Long[] schoolIds, Long assessmentProgId, String assessmentProgCode, int schoolYear) {
		return contentAreaDao.getContentAreaForBundledReport(schoolIds, assessmentProgId, assessmentProgCode, schoolYear);
	}

	@Override
	public List<ContentArea> selectAllContentAreasDropdown(Long currentAssessmentProgramId) {
		return contentAreaDao.selectAllContentAreasDropdown(currentAssessmentProgramId);
	}

	@Override
	public List<ContentArea> findByAssessmentProgramCode(String assessmentProgramCode) {
		return contentAreaDao.findByAssessmentProgramCode(assessmentProgramCode);
	}

	@Override
	public List<ContentArea> findByAbbreviatedNamesAndAssessmentProgramCode(String[] contentAreaCodes,
			String assessmentProgramCode) {
		return contentAreaDao.findByAbbreviatedNamesAndAssessmentProgramCode(contentAreaCodes,assessmentProgramCode);

	}

	@Override
	public List<ContentArea> getSubjectsForReportGeneration(
			Long assessmentProgramId, Long schoolYear, Long stateId) {		
		return contentAreaDao.getSubjectsForReportGeneration(assessmentProgramId, schoolYear, stateId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<ContentArea> selectContentAreasForISmartAutoEnrollment(Long organizationId, String contentAreaAbbrName) {
		return contentAreaDao.selectContentAreasForISmartAutoEnrollment(organizationId, contentAreaAbbrName);
	}

	@Override
	public List<ContentArea> getSubjectsByGradeAndAssessment(String abbreviatedName, List<Long> assessmentProgramIds) {
		return contentAreaDao.getSubjectsByGradeAndAssessment(abbreviatedName, assessmentProgramIds);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<String> getCourseNameByAssesmentProgram(String assesmentProgram) {
		return contentAreaDao.getCourseNameByAssesmentProgram(assesmentProgram);
	}

	@Override
	public List<Long> getContentAreasIdForDistrictSummaryReportsHaveProcessed(Map<String, Object> params) {
		return contentAreaDao.getContentAreasIdForDistrictSummaryReportsHaveProcessed(params);
	}
}
