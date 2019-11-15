/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.GradeCourseExample;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.service.GradeCourseService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public  class GradeCourseServiceImpl implements GradeCourseService {
	@Autowired
	private GradeCourseDao gradeCourseDao;

	@Value("${aartOriginationCode}")
	private String aartOriginationCode;

	@Override
	public final int countByExample(GradeCourseExample example) {
		return gradeCourseDao.countByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByExample(GradeCourseExample example) {
		return gradeCourseDao.deleteByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByPrimaryKey(Long id) {
		return gradeCourseDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> selectByExample(GradeCourseExample example) {
		return gradeCourseDao.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final GradeCourse selectByPrimaryKey(Long id) {
		return gradeCourseDao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExampleSelective(GradeCourse record, GradeCourseExample example) {
		return gradeCourseDao.updateByExampleSelective(record, example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExample(GradeCourse record, GradeCourseExample example) {
		return gradeCourseDao.updateByExample(record, example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKeySelective(GradeCourse record) {
		return gradeCourseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKey(GradeCourse record) {
		return gradeCourseDao.updateByPrimaryKey(record);
	}

	/**
	 * @param assessmentProgramId
	 *            long
	 * @return List<GradeCourse>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> findByAssessmentProgram(long assessmentProgramId) {
		return gradeCourseDao.findByAssessmentProgram(assessmentProgramId);
	}

	/**
	 *
	 * @param gradeCourse
	 *            {@link GradeCourse}
	 * @return GradeCourse - the GradeCourse referenced by the give code.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final GradeCourse getWebServiceGradeCourseByCode(GradeCourse gradeCourse) {
		return gradeCourseDao.findByGradeCode(gradeCourse.getAbbreviatedName());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getAllIndependentGrades() {
		return gradeCourseDao.findAllIndependentGrades();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final GradeCourse getIndependentGradeByAbbreviatedName(String abbreviatedName) {
		return gradeCourseDao.findIndependentGradeByAbbreviatedName(abbreviatedName);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final GradeCourse getByContentAreaAndAbbreviatedName(Long contentAreaId, String abbreviatedName) {
		return gradeCourseDao.findByContentAreaAndAbbreviatedName(contentAreaId, abbreviatedName);
	}

	/**
	 * @param assessmentId
	 *            Long
	 * @return List<GradeCourse>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> findByAssessmentId(Long assessmentId) {
		return gradeCourseDao.findByAssessmentId(assessmentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> selectAllGradeCourses() {
		return gradeCourseDao.selectAllGradeCourses();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> selectGradeCourseByContentAreaId(Long contentAreaId) {
		return gradeCourseDao.selectGradeCourseByContentAreaId(contentAreaId);
	}

	@Override
	public List<GradeCourse> selectGradeCourseByTestTypeIdSubjectAreaId(Long subjectAreaId, Long testTypeId) {
		return gradeCourseDao.selectGradeCourseByTestTypeIdSubjectAreaId(subjectAreaId, testTypeId);
	}

	@Override
	public List<GradeCourse> selectGradeCourseOfKansasBreakDay() {
		return gradeCourseDao.selectGradeCourseOfKansasBreakDay();
	}

	@Override
	public List<GradeCourse> getGradeCourseForAuto(Long contentAreaId, Long testTypeId, Long assessmentId) {
		return gradeCourseDao.findByTestTypeAndContentArea(contentAreaId, testTypeId, assessmentId);
	}

	@Override
	public List<GradeCourse> getGradeCourseByContentAreaIdForTestCoordination(Long assessmentProgramId, Long testingProgramId, Long contentAreaId) {
		return gradeCourseDao.selectGradeCourseByContentAreaIdForTestCoordination(assessmentProgramId, testingProgramId, contentAreaId);
	}

	@Override
	public List<GradeCourse> getGradeCourseByContentAreaIdForTestManagement(Long assessmentProgramId, Long testingProgramId, Long contentAreaId) {
		return gradeCourseDao.selectGradeCourseByContentAreaIdForTestManagement(assessmentProgramId, testingProgramId, contentAreaId);
	}

	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor
	 * Add Roster manually page Get all courses that are related a content area,
	 * This is achieved by getting the gradecourse data that has course column
	 * as true i.e. they are only courses.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getCoursesByContentArea(Long contentAreaId) {
		return gradeCourseDao.findCoursesByContentArea(contentAreaId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getGradesUsingAssessmentProgramAndCourse(Long assessmentProgramId,
			Long contentAreaId) {
		return gradeCourseDao.findGradesByContentAreaAndAssessmentProgram(assessmentProgramId, contentAreaId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getGradesUsingAssessmentProgramAndCourseForExternalReport(Long assessmentProgramId,
			Long contentAreaId, String assessmentCode, String reportType, Long reportYear) {
		return gradeCourseDao.getGradesUsingAssessmentProgramAndCourseForExternalReport(assessmentProgramId,
				contentAreaId, assessmentCode, reportType, reportYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getGradesWhereReportsHaveProcessed(Map<String, Object> parameters) {
		return gradeCourseDao.getGradesWhereReportsHaveProcessed(parameters);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> selectOrgGradeCourses(Long schoolId) {
		return gradeCourseDao.selectOrgGradeCourses(schoolId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getCoursesListByOtwId(Long operationalTestWindowId) {
		return gradeCourseDao.getCoursesListByOtwId(operationalTestWindowId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getDistinctGradesByAssessmentPrgmId(Long assessmentProgramId) {
		return gradeCourseDao.getDistinctGradesByAssessmentPrgmId(assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getDistinctGradesByAssessmentPrgmIdBanded(Long assessmentProgramId) {
		return gradeCourseDao.getDistinctGradesByAssessmentPrgmIdBanded(assessmentProgramId);
	}
	
	@Override
	public List<GradeCourse> getGradeCourse() {
		return gradeCourseDao.getGradeCourse();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getGradeCourseInterim(Long contentAreaId,Long purpose,Boolean isInterim, Long organizationId,Long assessmentProgramId) {
		return gradeCourseDao.getGradeCourseInterim(contentAreaId,purpose,isInterim,organizationId,assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getGradesForStudentScoreExtract(List<Long> contentAreaId) {
		return gradeCourseDao.findGradesForStudentScoreExtract(contentAreaId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getGradesForItiBPCoverageExtract(Long contentAreaId, Long narrowOrgId, 
			Long schoolYear, boolean isTeacher, Long educatorId) {		
		return gradeCourseDao.getGradesForItiBPCoverageExtract(contentAreaId, narrowOrgId, schoolYear, isTeacher, educatorId);
	}

	@Override
	public List<GradeCourse> getGradesForBundledReporting(Long districtId, Long[] schoolIds, Long[] subjectIds,
			Long assessmentProgId, String assessmentProgCode, int schoolYear, String reportTypeCode) {
		// TODO Auto-generated method stub
		return gradeCourseDao.getGradesForBundledReporting( districtId, schoolIds, subjectIds,
				assessmentProgId, assessmentProgCode, schoolYear, reportTypeCode);
	}

	@Override
	public GradeCourse findByContentAreaAbbreviatedNameAndGradeCourseAbbreviatedName(
			String contentAreaAbbreviatedName, String gradeCourseAbbreviatedName) {
		// TODO Auto-generated method stub
		return gradeCourseDao.findByContentAreaAbbreviatedNameAndGradeCourseAbbreviatedName( contentAreaAbbreviatedName, gradeCourseAbbreviatedName);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public GradeCourse selectGradeByAbbreviatedNameAndContentAreaId(String abbreviatedName, Long contentAreaId){
		return gradeCourseDao.selectGradeByAbbreviatedNameAndContentAreaId(abbreviatedName, contentAreaId);
	}
	
	@Override
	public List<GradeCourse> getGradesForDynamicStudentSummaryBundledReport(Long districtId, Long[] schoolIds,
			Long assessmentProgId, String assessmentProgCode, int schoolYear) {
		return gradeCourseDao.getGradesForDynamicStudentSummaryBundledReport( districtId, schoolIds, assessmentProgId, assessmentProgCode, schoolYear);
	}

	@Override
	public List<GradeCourse> findGradeByAbbreviatedName(String currentGradelevel) {
		return gradeCourseDao.findGradeByAbbreviatedName(currentGradelevel);
	}

	@Override
	public List<GradeCourse> selectAllGradeCoursesDropdown(Long currentAssessmentProgramId) {
		return gradeCourseDao.selectAllGradeCoursesDropdown(currentAssessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final GradeCourse getGradesForScoring(String abbreviatedName) {
		return gradeCourseDao.getGradesForScoring(abbreviatedName);
	}

	@Override
	public List<GradeCourse> getGradesForReportGeneration(Long subjectId,
			Long assessmentProgramId, Long schoolyear, Long stateId) {
		return gradeCourseDao.getGradesForReportGeneration(subjectId, assessmentProgramId, schoolyear, stateId);
	}

	@Override
	public List<GradeCourse> selectGradeByContentAreaId(Long subjectId) {
		return gradeCourseDao.findByContentAreaId(subjectId);
	}
	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<GradeCourse> getGradeBandsByContentAreaIdAndOTWId(Long operationalTestWindowId, Long contentAreaId){
		return gradeCourseDao.getGradeBandsByContentAreaIdAndOTWId(operationalTestWindowId, contentAreaId);
	}

	@Override
	public Map<Long, Long> getGradeCourseToGradeBandMap(Long operationalTestWindowId, Long contentAreaId){
		Assert.notNull(operationalTestWindowId, "The operational test window id must not be null.");
		Assert.notNull(contentAreaId, "The content area id must not be null.");
		List<GradeCourse> list = gradeCourseDao.getGradeCourseToGradeBandMap(operationalTestWindowId, contentAreaId);
		Map<Long, Long> gradeCourseToGradeBandMap = new HashMap<Long, Long>();
		for (GradeCourse gc : list) {
			gradeCourseToGradeBandMap.put(gc.getId(), gc.getGradeBandId());
		}
		return gradeCourseToGradeBandMap;
	}

	@Override
	public GradeCourse getGradeCourseByEnrollmentsRostersId(Long enrollmentsRostersId) {
		return gradeCourseDao.getByEnrollmentsRostersId(enrollmentsRostersId);
	}

	@Override
	public List<GradeCourse> getGradeBandsBySchoolIDAndAssesmentProgrammIDAndYear(Long schoolID,
			Long currentAssessmentProgramId, Long schoolYear, Long teacherID) {
//		Grade for only ('M', 'ELA', 'Sci')
//		The method is written for the grade drop down in the IAP home search.  
		return gradeCourseDao.getGradeBandsBySchoolIDAndAssesmentProgrammIDAndYear(schoolID,currentAssessmentProgramId,schoolYear,teacherID);
	}
}

