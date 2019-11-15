package edu.ku.cete.batch.dlm.auto.multiassign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.dlm.st.StudentTrackerHelper;
import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.PoolTypeEnum;

public class DLMMultiAssignBatchRegistrationProcessor implements ItemProcessor<Enrollment,DLMMultiAssignAutoWriterContext> {

	private final static Log logger = LogFactory .getLog(DLMMultiAssignBatchRegistrationProcessor.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private StudentTrackerService studentTrackerService;
	
	private Organization contractingOrganization;
	private ContentArea contentArea;
	private List<ComplexityBand> allBands;

	private Long multiAssignTestWindowId;
	private Integer multiAssignLimit;
	private String enrollmentMethod;
	
	private Random randomGenerator = new Random();
	
	private StepExecution stepExecution;
	
	protected Long batchRegistrationId;

	@Override
	public DLMMultiAssignAutoWriterContext process(Enrollment enrollment) throws Exception {
		DLMMultiAssignAutoWriterContext ctx = processEE(enrollment);
		if(ctx == null) {
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
		}
		return ctx;
	}

	@SuppressWarnings("unchecked")
	private DLMMultiAssignAutoWriterContext processEE(Enrollment enrollment) {
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
		Long complexityBandId = 0L;
		ComplexityBand recommendedBand=null;
		Roster roster=null;
		String gradeCode = enrollment.getCurrentGradeLevelCode();
		String contentAreaCode = contentArea.getAbbreviatedName();
		String orgPoolType = StudentTrackerHelper.getPoolType(contractingOrganization, gradeCode, contentAreaCode);
		String message = StringUtils.EMPTY;
		if(enrollmentMethod.equalsIgnoreCase("MLTASGNFT")) {
			//US19654: DLM Filed Test Assignment
			List<ContentFrameworkDetail> listOfContentCodesInFieldTests = testCollectionService
					.getContentCodesForFieldTests(multiAssignTestWindowId, orgPoolType, gradeCode, contentArea.getId());
			if(CollectionUtils.isEmpty(listOfContentCodesInFieldTests)) {
				message = StudentTrackerHelper.constructMultiAssignMessage("Unable to find the contentcodes(Field Test) for student: ", 
						enrollment, gradeCode, contentArea, contractingOrganization, multiAssignTestWindowId, orgPoolType, null, null);
				writeReason(enrollment.getStudentId(), message, reasons);
				return null;
			}
			List<String> fieldTestEEs = new ArrayList<>();
			for(ContentFrameworkDetail cfd : listOfContentCodesInFieldTests) {
				fieldTestEEs.add(cfd.getContentCode());
			}
			if(StringUtils.equalsIgnoreCase(orgPoolType, "SINGLEEE") && listOfContentCodesInFieldTests.size() > 1) {
				message = StudentTrackerHelper.constructMultiAssignMessage("More than one contentcodes(Field Test - For SIGNLEEE model it should be only one contentcode) for student: ", 
						enrollment, gradeCode, contentArea, contractingOrganization, multiAssignTestWindowId, orgPoolType, fieldTestEEs, null);
				writeReason(enrollment.getStudentId(), message, reasons);
				return null;
			}
			List<StudentTrackerBand> listOfSTBandForFieldTestEEs = studentTrackerService.getStudentTrackerBandByFieldTestEEs(enrollment.getStudent().getStudentTrackerId()
					, listOfContentCodesInFieldTests);
			StudentTrackerBand stBandForFieldTestEEs = null;
			
			if(CollectionUtils.isEmpty(listOfSTBandForFieldTestEEs)) {
				message = StudentTrackerHelper.constructMultiAssignMessage("Student is not tested for field test EEs in Student tracker. StudentId: ", enrollment, gradeCode, contentArea, 
						contractingOrganization, multiAssignTestWindowId, orgPoolType, fieldTestEEs, null);
				writeReason(enrollment.getStudentId(), message, reasons);
				return null;
			} else {
				if(listOfSTBandForFieldTestEEs.size() > 1) {
					message = StudentTrackerHelper.constructMultiAssignMessage("More than one ST test available for Student for FieldTest EEs. StudentId: ", enrollment, gradeCode, contentArea, 
							contractingOrganization, multiAssignTestWindowId, orgPoolType, fieldTestEEs, null);
					writeReason(enrollment.getStudentId(), message, reasons);
					return null;
				} else {
					stBandForFieldTestEEs = listOfSTBandForFieldTestEEs.get(0);
				}
			}
			complexityBandId = stBandForFieldTestEEs.getComplexityBandId();
			recommendedBand = getFieldTestBandById(allBands, complexityBandId, contentAreaCode);
		} else {
			if ("ELA".equals(contentAreaCode)) {
				complexityBandId = enrollment.getStudent().getFinalElaBandId();
			} else if ("M".equals(contentAreaCode)) {
				complexityBandId = enrollment.getStudent().getFinalMathBandId();
			} else if ("Sci".equals(contentAreaCode)) {
				complexityBandId = enrollment.getStudent().getFinalSciBandId();
			} else if ("SS".equals(contentAreaCode)) {
				complexityBandId = enrollment.getStudent().getCommBandId();
			} else {
				complexityBandId = enrollment.getStudent().getCommBandId();
			}
			
			recommendedBand = getBandById(allBands, complexityBandId);
			
			// This "if" was added for Oklahoma Social Studies in spring 2018,
			// SS was supposed to use a similar mapping scheme as science in Student Tracker
			if ("SS".equalsIgnoreCase(contentAreaCode)) {
				if (recommendedBand == null) {
					logger.debug("Multi-assign SS, band returned by getBandById was null for student " + enrollment.getStudentId() + ", using 0 as band code--check their comm band");
					recommendedBand = StudentTrackerHelper.getBandInContentArea(allBands, "0", contentAreaCode);
				} else {
					logger.debug("Multi-assign SS, band used for student " + enrollment.getStudentId() + " is band code " + recommendedBand.getBandCode());
					recommendedBand = StudentTrackerHelper.getBandInContentArea(allBands, recommendedBand.getBandCode(), contentAreaCode);
				}
				
				logger.debug("Multi-assign SS, band for student " + enrollment.getStudentId() +
					", bandId = " + recommendedBand.getId() +
					", bandCode = " + recommendedBand.getBandCode() +
					", band range = " + recommendedBand.getMinRange() + "-" + recommendedBand.getMaxRange());
			}
		}
		
		Double linkageLevelLowerBound = 0d;
		Double linkageLevelUpperBound = 0d;
		if(recommendedBand != null) {
			linkageLevelLowerBound = recommendedBand.getMinRange();
			linkageLevelUpperBound = recommendedBand.getMaxRange();
		}
		List<TestCollection> testCollections = null;
		GradeCourse gradeCourse = null;
		if(orgPoolType != null) {
			roster = getRoster(enrollment, orgPoolType);
			Long recommendedBandId = null;
			if(recommendedBand != null) {
				recommendedBandId = recommendedBand.getId();
			}
			if(roster == null) {
				if(PoolTypeEnum.MULTIEEOFC.name().equals(orgPoolType)) {
					message = StudentTrackerHelper.constructMultiAssignMessage("Multi Assign-No course based roster found for student: ", enrollment, gradeCode, contentArea, 
							contractingOrganization, complexityBandId, orgPoolType, null, recommendedBandId);
					writeReason(enrollment.getStudentId(), message, reasons);
				} else {
					message = StudentTrackerHelper.constructMultiAssignMessage("Multi Assign-No roster found for student: ", enrollment, gradeCode, contentArea, 
							contractingOrganization, complexityBandId, orgPoolType, null, recommendedBandId);
					writeReason(enrollment.getStudentId(), message, reasons);
				}
				return null;
			}
			gradeCourse = getGradeCourseAbbr(roster, orgPoolType, enrollment);
			testCollections = getTestCollections(roster.getId(), enrollment, linkageLevelLowerBound.floatValue(), 
					linkageLevelUpperBound.floatValue(),  gradeCourse, contentArea,orgPoolType, reasons);
			if(CollectionUtils.isEmpty(testCollections)) {
				message = StudentTrackerHelper.constructNoTestCollectionMessageForMultiAssign("Multi Assign- No Test collection found for contentpool: ", orgPoolType, gradeCourse,  
						contentArea,recommendedBandId, linkageLevelLowerBound.floatValue(), linkageLevelUpperBound.floatValue(), multiAssignTestWindowId);
				writeReason(enrollment.getStudentId(), message, reasons);
				return null;
			}
			
			for(TestCollection testCollection: testCollections) {
				List<Test> tests = getTests(enrollment.getStudentId(), testCollection, contentAreaCode);
				logger.debug(String.format("StudentId : %d, %d tests are found for testcollection id %d ", enrollment.getStudentId(), testCollection.getId(),  tests.size()));
				logger.debug("Test Collection id -" + testCollection.getId() + ", Test ids are - " + AARTCollectionUtil.getIds(tests));
				if(CollectionUtils.isEmpty(tests)){
					String cErrorMsg = String.format("No tests found with criteria - grade: %s(%d), contentarea: %s(%d), testcollections: %s(%d), operational test window id: %d.", 
							gradeCourse.getAbbreviatedName(), gradeCourse.getId(), contentAreaCode, contentArea.getId(), testCollection.getName(),testCollection.getId(), multiAssignTestWindowId);
					logger.debug(cErrorMsg);
					writeReason(enrollment.getStudentId(), cErrorMsg, reasons);
				}else{
					testCollectionTests.put(testCollection, tests);
					logger.debug(String.format("StudentId : %d, testCollectionTests Map size - %d",  enrollment.getStudentId(),testCollectionTests.size()));
				}
			}
		}
			
		DLMMultiAssignAutoWriterContext wCtx = new DLMMultiAssignAutoWriterContext();
		wCtx.setRosterId(roster.getId());
		wCtx.setEnrollment(enrollment);
		wCtx.setGradeCourse(gradeCourse);
		wCtx.setTestCollectionTests(testCollectionTests);
		wCtx.setComplexityBand(recommendedBand);
		wCtx.setNumberOfTestsRequired(multiAssignLimit);
		return wCtx;
	}
	
	private List<TestCollection> getTestCollections(Long rosterId, Enrollment enrollment, Float linkageLevelLowerBound, Float linkageLevelUpperBound, GradeCourse gradeCourse, ContentArea contentArea, String poolType, List<BatchRegistrationReason> reasons){
		List<TestCollection> testCollections = null;
		String contentAreaCode = contentArea.getAbbreviatedName();

		List<StudentsTests> existingTestSessions = studentsTestsService.getExistingMultiAssignSessions(enrollment.getStudentId(), contentArea.getId(), gradeCourse.getId(), multiAssignTestWindowId);
		
		List<Long> excludeTestCollections = new ArrayList<Long>();
		for(StudentsTests studentsTest : existingTestSessions){
			excludeTestCollections.add(studentsTest.getTestCollectionId());
		}
		logger.debug("StudentId : " + enrollment.getStudentId() + ", Exclude List :" + excludeTestCollections);
		if(excludeTestCollections.size() >= multiAssignLimit){
			String cErrorMsg = String.format("Test already assigned - grade: %s(%d), contentarea: %s(%d), studentid: %d.", 
					gradeCourse.getAbbreviatedName(), gradeCourse.getId(), contentArea.getAbbreviatedName(), contentArea.getId(), enrollment.getStudentId());
			logger.debug(cErrorMsg);
			throw new SkipBatchException(cErrorMsg);
		}else{
			testCollections = testCollectionService.getMultiAssignTestCollections(linkageLevelLowerBound, linkageLevelUpperBound,
					gradeCourse.getAbbreviatedName(), contentAreaCode, multiAssignLimit - excludeTestCollections.size(), excludeTestCollections, poolType, multiAssignTestWindowId);
		}
		
		if(CollectionUtils.isNotEmpty(testCollections)){
			int foundTc = testCollections.size() + excludeTestCollections.size();
			if(foundTc < multiAssignLimit){
					writeReason(enrollment.getStudentId(), String.format("Found only %d test collections with tests were expecting %d test collections for %s - contentpool, %s (%d) - grade, %s (%d) - contentarea, "
						+ "%f - %f - avg linage level", foundTc, multiAssignLimit, poolType, gradeCourse.getAbbreviatedName(), gradeCourse.getId(),  contentArea.getAbbreviatedName(), contentArea.getId(), linkageLevelLowerBound.floatValue()
						, linkageLevelUpperBound.floatValue()), reasons);
			}
		}
		return testCollections;
	}
	
	private List<Test> getTests(Long studentId, TestCollection testCollection, String contentAreaCode) {
		logger.debug("--> getTests" );
		
		List<Test> tests = testService.findByTestCollectionAndStatus(testCollection.getTestCollectionId(), 
        		testStatusConfiguration.getPublishedTestStatusCategory().getId());
		if(contentAreaCode.equalsIgnoreCase("ELA") || contentAreaCode.equalsIgnoreCase("M")){
			tests = studentsTestsService.dlmFilterTestsBasedOnPNP(studentId, tests);
		}
		logger.debug("<-- getTests" );
		return tests;
	}

	private GradeCourse getGradeCourseAbbr(Roster roster, String poolType, Enrollment enrollment) {
		logger.debug("returning getGradeCourseAbbr for poolType: "+poolType);
		if(PoolTypeEnum.MULTIEEOFC.name().equals(poolType)) {
			return roster.getCourse();
		} else {
			GradeCourse gradeCourse = new GradeCourse();
			gradeCourse.setId(enrollment.getCurrentGradeLevel());
			gradeCourse.setAbbreviatedName(enrollment.getCurrentGradeLevelCode());
			return gradeCourse;
		}
	}
	
	private ComplexityBand getBandById(List<ComplexityBand> allBands, Long bandId) {
		ComplexityBand complexityBand = null;
		for (ComplexityBand band : allBands) {
			if (band.getId().equals(bandId)) {
				complexityBand = band;
				break;
			}
		}
		return complexityBand;
	}
	
	//US17033
	private ComplexityBand getFieldTestBandById(List<ComplexityBand> allBands, Long bandId, String contentAreaCode) {
		ComplexityBand complexityBand = getBandById(allBands, bandId);
		String lowerBandCode = String.valueOf(new Integer(complexityBand.getBandCode()) - 1);
		String higherBandCode = String.valueOf(new Integer(complexityBand.getBandCode()) + 1);
		List<ComplexityBand> possibleComplexityBands = new ArrayList<>();
		for (ComplexityBand band : allBands) {
			boolean includeBand = StringUtils.equalsIgnoreCase("ELA", contentAreaCode) || StringUtils.equalsIgnoreCase("M", contentAreaCode)
					? band.getContentAreaCode() == null : (band.getContentAreaCode() != null && StringUtils.equalsIgnoreCase(contentAreaCode, band.getContentAreaCode()) 
						&& !StringUtils.equals(band.getBandCode(), "0"));
			if ((StringUtils.equals(lowerBandCode, band.getBandCode()) || StringUtils.equals(higherBandCode, band.getBandCode()))
					&& !(StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_EMERGENT, band.getBandName()) || StringUtils.equalsIgnoreCase(CommonConstants.COMPLEXITY_BAND_CONVENTIONAL, band.getBandName()))
					&& includeBand) {
				possibleComplexityBands.add(band);
			}
		}
		
		return possibleComplexityBands.get(randomGenerator.nextInt(possibleComplexityBands.size()));
	}
	
	private Roster getRoster(Enrollment enrollment, String poolType) {
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(contentArea.getId(), enrollment);
		
		List<Roster> finalRosters = new ArrayList<Roster>();
		//if MULTIEEOFC look for course roster
		if(PoolTypeEnum.MULTIEEOFC.name().equals(poolType)) {
			for(Roster roster: rosters) {
				if(roster.getStateCoursesId() != null) {
					finalRosters.add(roster);
				}
			}
		} else {
			finalRosters.addAll(rosters);
		}
		
		//check if any proctor roster exists
		for(Roster roster: finalRosters) {
			if(roster.getSourceType() != null && roster.getSourceType().equals("TEST")) {
				return roster;
			}
		}
		
		if(!finalRosters.isEmpty()) {
			//if no proctor return one of the roster
			return finalRosters.get(0);
		}
		return null;
	}
	
	private void writeReason(Long StudentId, String msg, List<BatchRegistrationReason> reasons) {
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(StudentId);
		reason.setReason(msg);
		reasons.add(reason);
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}
	
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public List<ComplexityBand> getAllBands() {
		return allBands;
	}

	public void setAllBands(List<ComplexityBand> allBands) {
		this.allBands = Collections.unmodifiableList(allBands);
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getMultiAssignTestWindowId() {
		return multiAssignTestWindowId;
	}

	public void setMultiAssignTestWindowId(Long multiAssignTestWindowId) {
		this.multiAssignTestWindowId = multiAssignTestWindowId;
	}

	public Integer getMultiAssignLimit() {
		return multiAssignLimit;
	}

	public void setMultiAssignLimit(Integer multiAssignLimit) {
		this.multiAssignLimit = multiAssignLimit;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}
}
