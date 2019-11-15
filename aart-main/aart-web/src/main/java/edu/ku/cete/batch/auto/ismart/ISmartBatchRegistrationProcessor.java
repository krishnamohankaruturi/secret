package edu.ku.cete.batch.auto.ismart;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;

/**
 * @author Kiran Reddy Taduru
 * Jun 4, 2018 12:31:06 PM
 */
public class ISmartBatchRegistrationProcessor implements ItemProcessor<StudentRoster, ISmartAutoWriterContext> {

	private final static Log logger = LogFactory .getLog(ISmartBatchRegistrationProcessor.class);
	
	private Organization contractingOrganization;
	private ContentArea contentArea;
	private List<ComplexityBand> allBands;
	private StepExecution stepExecution;
	private Long operationalTestWindowId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private String gradeAbbrName;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	@SuppressWarnings("unchecked")
	@Override
	public ISmartAutoWriterContext process(StudentRoster studentRosterRecord) throws Exception {
		logger.debug(">>>>>> Entering ISmartBatchRegistrationProcessor::process() - StudentId: " + studentRosterRecord.getStudentId());
		
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		Map<Long, Long> gradeCoursesToGradeBand = (Map<Long, Long>) stepExecution.getExecutionContext().get("gradeCoursesToGradeBand");
		
		Long gradeBandId = gradeCoursesToGradeBand.get(studentRosterRecord.getCurrentGradeLevel());
		
		ComplexityBand complexityBand = getBandById(allBands, studentRosterRecord.getScienceBandId(), gradeBandId, contentArea.getId());
		TestCollection testCollection = getTestCollection(complexityBand, studentRosterRecord.getGradeCourse().getAbbreviatedName());
		
		if(testCollection == null) {
			writeReason(studentRosterRecord.getStudentId(), String.format("Test collection not found for assessmentProgramId - (%d), contentareaId- "
					+ "%d, gradeId-%d, studentid-%d,enrollmentid-%d", assessmentProgramId, contentArea.getId(), studentRosterRecord.getGradeCourse().getId(), studentRosterRecord.getStudentId(), studentRosterRecord.getEnrollmentId()					
					), reasons);
			return null;
		}
		
		List<Test> tests = getTests(studentRosterRecord.getStudentId(), testCollection);
		
		if(CollectionUtils.isEmpty(tests)){
			String cErrorMsg = String.format("No tests found with criteria - grade: %s(%d), contentarea: %s(%d), testcollections: %s(%d), studentid-%d,enrollmentid-%d.", 
					studentRosterRecord.getGradeCourse().getAbbreviatedName(), studentRosterRecord.getGradeCourse().getId(), contentArea.getAbbreviatedName(), contentArea.getId(), testCollection.getName(),
					testCollection.getId(), studentRosterRecord.getStudentId(), studentRosterRecord.getEnrollmentId());
			writeReason(studentRosterRecord.getStudentId(), cErrorMsg, reasons);
			return null;
		}
		
		ISmartAutoWriterContext writerContext = new ISmartAutoWriterContext();
		writerContext.setAttendanceschoolId(studentRosterRecord.getAttendanceSchoolId());
		writerContext.setAttSchDisplayIdentifer(studentRosterRecord.getAttendanceSchool().getDisplayIdentifier());
		writerContext.setEnrollmentId(studentRosterRecord.getEnrollmentId());
		writerContext.setGradeCourseAbbrName(studentRosterRecord.getGradeCourse().getAbbreviatedName());
		writerContext.setRosterId(studentRosterRecord.getRoster().getId());
		writerContext.setStudentId(studentRosterRecord.getStudentId());
		writerContext.setGradeCourseId(studentRosterRecord.getGradeCourse().getId());
		writerContext.setStatestudentidentifier(studentRosterRecord.getStudent().getStateStudentIdentifier());
		writerContext.setTestCollection(testCollection);
		writerContext.setTests(tests);
		writerContext.setContentAreaAbbrName(contentArea.getAbbreviatedName());
		writerContext.setStudentFirstName(studentRosterRecord.getStudent().getLegalFirstName());
		writerContext.setStudentLastName(studentRosterRecord.getStudent().getLegalLastName());
		writerContext.setContentAreaId(contentArea.getId());
		
		logger.debug(">>>>>> Exiting ISmartBatchRegistrationProcessor::process() - StudentId: " + studentRosterRecord.getStudentId());
		
		return writerContext;
		
	}

	private ComplexityBand getBandById(List<ComplexityBand> allBands, Long complexityBandId, Long gradeBandId, Long contentAreaId) {
		ComplexityBand enrollmentBand = null;
		//find the enrollment band based on id
		for (ComplexityBand band : allBands) {
			if (band.getId().equals(complexityBandId)){
				enrollmentBand = band;
				break;
			}
		}
		for (ComplexityBand band : allBands) {
			//find the gradebanded complexity band based on the enrollment band code and the contentareaid/gradebandid
			if (band.getBandCode().equals(enrollmentBand.getBandCode()) 
					&& (band.getContentAreaId() == null ? false : band.getContentAreaId().equals(contentAreaId)) 
					&& (band.getGradeBandId() == null ? false : band.getGradeBandId().equals(gradeBandId))) {
				return band;
			}
		}
		return null;
	}
	
	private TestCollection getTestCollection(ComplexityBand band, String gradeCourseCode){

		float linkageLevelLowerBound = band.getMinRange().floatValue();
		float linkageLevelUpperBound = band.getMaxRange().floatValue();

		List<TestCollection> testCollections = testCollectionService.getTestCollectionsForISmartBatchAuto(linkageLevelLowerBound, linkageLevelUpperBound, 
				gradeCourseCode, contentArea.getId(), operationalTestWindowId);
		
		if(!testCollections.isEmpty()) {
			return testCollections.get(0);
		}
		return null;
	}
	
	private List<Test> getTests(Long studentId, TestCollection testCollection) {
		logger.debug("--> getTests" );
		List<Test> tests = testService.gtAllQCTestsAccFlagsByTestCollectionIdAndTestStatus(testCollection.getId(), testStatusConfiguration.getPublishedTestStatusCategory().getId());
		List<Test> filteredTests = studentsTestsService.getFilteredTestsBasedOnStudentPNPForISmartBatchAuto(studentId, tests);
		logger.debug("<-- getTests" );
		return filteredTests;
	}
	
	private void writeReason(Long studentId, String msg, List<BatchRegistrationReason> reasons) {
		logger.debug("writeReason: studentid: "+studentId+", "+msg);
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(studentId);
		reason.setReason(msg);
		reasons.add(reason);
	}
	
	
	
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public List<ComplexityBand> getAllBands() {
		return allBands;
	}

	public void setAllBands(List<ComplexityBand> allBands) {
		this.allBands = allBands;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}

}
