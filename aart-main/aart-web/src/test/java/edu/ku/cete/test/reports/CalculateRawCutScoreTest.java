/**
 * 
 */
package edu.ku.cete.test.reports;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.StudentResponseScore;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.StageEnum;

/**
 * @author ktaduru_sta
 *
 */
public class CalculateRawCutScoreTest extends BaseTest {

	final static Log logger = LogFactory.getLog(CalculateRawCutScoreTest.class);
	@Autowired
	private TestService testService;
	
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	private StepExecution stepExecution;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private StudentReportService studentReportService;
	
	@Autowired
	private StudentsResponsesService studentsResponsesService;
	
	@Test
	public void testGetStudentTestScores() {
		StudentReport studentReport = getStudentResponses(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l, 5748l, null, null, null, null);
		assertTrue("No Completed/InProgress tests/responses found for student: "+studentReport.getStudentId(), studentReport.getStudentTestsScore().size() > 0);
		
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCalculateRawScore() {
		StudentReport studentReport = getStudentResponses(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l, 5748l, null, null, null, null);
		
		calculateRawScore(studentReport);
		logger.info("***** RawScore is: "+studentReport.getRawScore());
		assertTrue("Calculated RawScore should be 3", studentReport.getRawScore().compareTo(new BigDecimal(3)) == 0);
	}	
	
	@Test
	public void testCalculateRawScoreWithoutIncludingPrfrmTestRawScore() {
		StudentReport studentReport = getStudentResponses(892134l, 1093722l, "KAP", 12l, "M", 440l, "8", 149l, 5859l, null, null, null, 6233l);
		
		calculateRawScore(studentReport);
		logger.info("***** RawScore is: "+studentReport.getRawScore());
		assertTrue("Calculated RawScore should be 4", studentReport.getRawScore().compareTo(new BigDecimal(4)) == 0);
	}	
	
	@Test
	public void testCalculateRawScoreWithIncludingPrfrmTestRawScore() {
		StudentReport studentReport = getStudentResponses(892134l, 1093722l, "KAP", 12l, "M", 440l, "8", 149l, 5859l, null, null, null, 6233l);
		
		calculateRawScore(studentReport);
		logger.info("***** RawScore is: "+studentReport.getRawScore());
		assertTrue("Calculated RawScore should be 5", studentReport.getRawScore().compareTo(new BigDecimal(5)) == 0);
	}	
	
	@Test
	public void testGetRawToScaleScore() {
		List<Long> testIds = new ArrayList<Long>();
		testIds.add(5761l);
		testIds.add(5743l);
		//testIds.add(5784l);
		//testIds.add(5758l);
		
		StudentReport studentReport = getStudentReport(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l);
		studentReport.setExternalTest1Id(5761l);
		studentReport.setExternalTest2Id(5743l);
		//studentReport.setExternalTest3Id(5784l);
		//studentReport.setExternalTest4Id(5758l);
		//studentReport.setExternalTest4Id(5757l);
		//studentReport.setPerformanceTestExternalId(6233l);
		
		RawToScaleScores rawToScaleScores= rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
				studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId(), null, 18L, null);
		
		String msg = "RawToScaleScore record is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId()
						+" - Test1Id: "+studentReport.getExternalTest1Id()+" - Test2Id: "+studentReport.getExternalTest2Id()+" - Test3Id: "+studentReport.getExternalTest3Id()
						+" - Test4Id: "+studentReport.getExternalTest4Id() + " - PerformanceTestId: "+studentReport.getPerformanceTestExternalId();
		
		assertNotNull(msg, rawToScaleScores);
		
	}
	
	public StudentReport getStudentResponses(Long studentId, Long enrollmentId, String assessmentProramCode, Long assessmentProgramId, String contentAreaCode, Long contentAreaId, String grade, Long gradeCourseId,
			Long test1Id, Long test2Id, Long test3Id, Long test4Id, Long prfrmTestId) {
		
		StudentReport studentReport = getStudentReport(studentId, enrollmentId, assessmentProramCode, assessmentProgramId, contentAreaCode, contentAreaId, grade, gradeCourseId);
		
		List<Long> testsStatusIds = new ArrayList<Long>();
		List<Long> rawScaleExternalTestIds = new ArrayList<Long>();
		Long testsCompletedStatusId = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TEST_STATUS").getId();
	    Long testsInprogressStatusId = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TEST_STATUS").getId();
	    Long testsInprogressTimedoutStatusId = categoryService.selectByCategoryCodeAndType("inprogresstimedout",  "STUDENT_TEST_STATUS").getId();
	    Long testsUnusedStatusId = categoryService.selectByCategoryCodeAndType("unused",  "STUDENT_TEST_STATUS").getId();
	    Long testsPendingStatusId = categoryService.selectByCategoryCodeAndType("pending",  "STUDENT_TEST_STATUS").getId();
	    Category specialCircumstanceStatusSavedCategory = categoryService.selectByCategoryCodeAndType("SAVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Category specialCircumstanceStatusApprovedCategory = categoryService.selectByCategoryCodeAndType("APPROVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Long specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedCategory.getId();
		Long specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedCategory.getId();
		
		testsStatusIds.add(testsInprogressStatusId);
		testsStatusIds.add(testsCompletedStatusId);
		testsStatusIds.add(testsInprogressTimedoutStatusId);
		testsStatusIds.add(testsUnusedStatusId);
		testsStatusIds.add(testsPendingStatusId);
		
		if(test1Id != null){
			rawScaleExternalTestIds.add(test1Id);
		}
		if(test2Id != null){
			rawScaleExternalTestIds.add(test2Id);
		}
		if(test3Id != null){
			rawScaleExternalTestIds.add(test3Id);
		}
		if(test4Id != null){
			rawScaleExternalTestIds.add(test4Id);
		}
		if(prfrmTestId != null){
			rawScaleExternalTestIds.add(prfrmTestId);
		}
		
		List<Long> specialCircumstanceStatusIds = new ArrayList<Long>();
		specialCircumstanceStatusIds.add(specialCircumstanceStatusApprovedId);
		specialCircumstanceStatusIds.add(specialCircumstanceStatusSavedId);
		
		List<Long> enrollmentIds = new ArrayList<Long>();
		for (Enrollment e : studentReport.getEnrollments()) {
			enrollmentIds.add(e.getId());
		}
		
		List<StudentReportTestResponses> studentTestsScore = testService.getTestsScoreByStudentIdExternalTestIds(studentReport.getStudentId(),
				rawScaleExternalTestIds, enrollmentIds, testsStatusIds, specialCircumstanceStatusIds, 51l, 631l);		
		
		if(CollectionUtils.isEmpty(studentTestsScore)){
			List<StudentsResponses> stg1CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg1OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg2CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg2OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg3CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg3OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg4CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> stg4OtherstudentsResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> performanceCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
			List<StudentsResponses> performanceOtherstudentsResponses = new ArrayList<StudentsResponses>();
			
			for(StudentReportTestResponses studentReportTestResponse : studentTestsScore){
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("Stg1")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;
					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg1CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg1OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg1CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg1CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg1OtherstudentsResponses);
					}

				}
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("Stg2")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg2CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg2OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg2CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg2CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg2OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg3")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg3CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg3OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg3CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg3CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg3OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("stg4")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								stg4CompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							stg4OtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(stg4CompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(stg4CompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(stg4OtherstudentsResponses);
					}

				}
				
				if(studentReportTestResponse.getStageCode().equalsIgnoreCase("prfrm")){
					List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
					Long completedStudentTestId = null;

					for(StudentsResponses studentResponse: studentResponses){
						if(studentResponse.getStudentsTestStatus().equals(testsCompletedStatusId) ||
								studentResponse.getStudentsTestStatus().equals(testsInprogressStatusId) || 
								studentResponse.getStudentsTestStatus().equals(testsInprogressTimedoutStatusId)){
							if(completedStudentTestId == null){
								completedStudentTestId = studentResponse.getStudentsTestsId();
							}
							if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
								performanceCompleteAndInProgressStudentResponses.add(studentResponse);
							}
						}else{
							performanceOtherstudentsResponses.add(studentResponse);
						}
					}
					
					if(performanceCompleteAndInProgressStudentResponses.size() != 0){
						studentReportTestResponse.setStudentsResponses(performanceCompleteAndInProgressStudentResponses);
					}else{
						studentReportTestResponse.setStudentsResponses(performanceOtherstudentsResponses);
					}

				}
				
			}
		}			
			
		studentReport.setStudentTestsScore(studentTestsScore);
		return studentReport;
	}
	
	
	public StudentReport getStudentReport(Long studentId, Long enrollmentId, String assessmentProgramCode, Long assessmentProgramId, String subject, Long subjectId, String gradeCourse, Long gradeCourseId) {
		StudentReport studentReport = new StudentReport();
		
		studentReport.setAssessmentProgramCode(assessmentProgramCode);
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setStudentId(studentId);
		studentReport.setEnrollmentId(enrollmentId);
		studentReport.setStudentFirstName("Firstname");
		studentReport.setStudentLastName("Lastname");
		studentReport.setStateStudentIdentifier("23232323232");
		studentReport.setStateId(51L);
		studentReport.setDistrictId(361L);
		studentReport.setAttendanceSchoolName("Roosevelt Middle");
		studentReport.setAttendanceSchoolId(2890l);
		studentReport.setDistrictName("Coffeyville");
		studentReport.setDistrictDisplayIdentifier("D0445");
		studentReport.setGradeCode(gradeCourse);
		studentReport.setGradeId(gradeCourseId);
		studentReport.setContentAreaCode(subject);
		studentReport.setContentAreaId(subjectId);
		studentReport.setCurrentSchoolYear(2016L);
		studentReport.setEnrollmentActive(true);
		
		return studentReport;
	}

	@SuppressWarnings("unchecked")
	public StudentReport calculateRawScore(StudentReport studentReport) {
		//StudentReportDto studentReport = getStudentResponses(891766l, 1093142l, "KAP", 12l, "ELA", 3l, "3", 133l, 5748l, null, null, null, null);
		
		studentReport.setStatus(true);
		studentReport.setIncompleteStatus(false);
		studentReport.setStage1HasSCCode(false);
		studentReport.setStage2HasSCCode(false);
		studentReport.setStage3HasSCCode(false);
		studentReport.setStage4HasSCCode(false);
		studentReport.setPrfrmStageHasSCCode(false);
		
		List<Long> testIds = new ArrayList<Long>();
		StudentReportTestScores studentReportTestScores = new StudentReportTestScores();
		int taskVariantCount = 0;
		int responseCount = 0;	
		
		Long testsCompletedStatusId = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TEST_STATUS").getId();
	    Long testsInProgressStatusId = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TEST_STATUS").getId();
	    Long testsInProgressTimedoutStatusId = categoryService.selectByCategoryCodeAndType("inprogresstimedout",  "STUDENT_TEST_STATUS").getId();
	    Category specialCircumstanceStatusSavedCategory = categoryService.selectByCategoryCodeAndType("SAVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Category specialCircumstanceStatusApprovedCategory = categoryService.selectByCategoryCodeAndType("APPROVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Long specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedCategory.getId();
		Long specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedCategory.getId();
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		if(CollectionUtils.isEmpty(studentReportTestResponses))
		{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No test responses found for student: "+studentReport.getStudentId() ;
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
		
		BigDecimal rawCutScore = new BigDecimal(0);
		Boolean mdptScorable = null;
		Boolean scCodeStatus = false;
		
		Map<String, BigDecimal> stageTestScore = new HashMap<String, BigDecimal>();
		for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses){
			scCodeStatus = false;
			if(studentReportTestResponse.getSpecialCircumstanceId() != null && studentReportTestResponse.getScCodeStatus() != null) {
				if(studentReportTestResponse.getKsdeRestricted() != null){
					if(studentReportTestResponse.getKsdeRestricted()){
						if(specialCircumstanceStatusApprovedId.equals(studentReportTestResponse.getScCodeStatus())){
							scCodeStatus = true;
						}
					}else if(!studentReportTestResponse.getReportScore() && specialCircumstanceStatusSavedId.equals(studentReportTestResponse.getScCodeStatus())){
						scCodeStatus = true;
					}
				}
				
			}
			if(testsCompletedStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
							testsInProgressStatusId.equals(studentReportTestResponse.getStudentsTestStatus()) || 
							testsInProgressTimedoutStatusId.equals(studentReportTestResponse.getStudentsTestStatus())){
				/*Boolean historySectionsFlag = false;
				List<RawScoreSectionWeights> sectionWeightsList = studentReportService.getRawScoreSectionWeights(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReportTestResponse.getTestExternalId());
				if(sectionWeightsList.size() > 0)
					historySectionsFlag = true;*/
				
				Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
				List<StudentsResponses> studentsResponses = studentReportTestResponse.getStudentResponses();
				taskVariantCount = studentReportService.taskvariantCountByTestId(studentReportTestResponse.getTestId());
				responseCount = 0;
				
				for(StudentsResponses responses : studentsResponses){					
					//Take only responses that are from completed and inprogress, inprogress timedout tests
					if(testsCompletedStatusId.equals(responses.getStudentsTestStatus()) || testsInProgressStatusId.equals(responses.getStudentsTestStatus())
							|| testsInProgressTimedoutStatusId.equals(responses.getStudentsTestStatus())){
						if(responses.getTaskVariantExternalId() != null){
							responseCount++;
							BigDecimal currentTaskScore = new BigDecimal(0);
							
							if (Boolean.TRUE.equals(responses.getScoringNeeded())) {
								if (responses.getScore() != null) {
									/*if(historySectionsFlag)
									{
										if(responses.getSectionOrder()==1)
											currentTaskScore = responses.getScore().multiply(sectionWeightsList.get(0).getSectionWeight1());
										else if(responses.getSectionOrder()==2)
											currentTaskScore = responses.getScore().multiply(sectionWeightsList.get(0).getSectionWeight2());
										else
											currentTaskScore = responses.getScore();
									}
									else*/
										currentTaskScore = responses.getScore();
								} else {
									logger.warn("No score found in studentsresponses for studentsTestSectionsId: " + responses.getStudentsTestSectionsId() +
											", taskVariantId: " + responses.getTaskVariantId());
								}
							} else {
								// ScorePoint may have scored the item if it's performance, so we look there
								if ("KAP".equals(studentReport.getAssessmentProgramCode()) && 
										StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())) {
									Long studentsTestSectionsId = responses.getStudentsTestSectionsId();
									Long taskVariantExternalId = responses.getTaskVariantExternalId();
									List<StudentResponseScore> studentResponseScores = studentsResponsesService.findStudentResponseScores(
											studentsTestSectionsId, taskVariantExternalId, Arrays.asList(1));
									if (CollectionUtils.isEmpty(studentResponseScores)) {
										logger.debug("No studentresponsescore entries found for studentsTestSectionsId: " + studentsTestSectionsId +
												", taskVariantExternalId: " + taskVariantExternalId);
										mdptScorable = false;
									} else {
										for (StudentResponseScore srs : studentResponseScores) {
											// if any part is scorable, we keep "true", so we only need to set it once
											if ((!Boolean.TRUE.equals(mdptScorable)) && srs.getScorable() != null) {
												mdptScorable = srs.getScorable();
											}
											if (Boolean.TRUE.equals(srs.getScorable()) && srs.getScore() != null) {
												currentTaskScore = currentTaskScore.add(srs.getScore());
											}
										}
									}
								}
							}
							
							//Handle duplicate items
							BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
							if (existingScore != null){
								variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(currentTaskScore));
							} else {
								variantScoreMap.put(responses.getTaskVariantExternalId(), currentTaskScore);
							}
						} else { // the taskvariantexternalid is null, so the student didn't answer the question
							if ("KAP".equals(studentReport.getAssessmentProgramCode()) && 
									StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())) {
								// we want to keep it as true, if ANY other question was scorable
								if (!Boolean.TRUE.equals(mdptScorable)) {
									mdptScorable = false;
								}
							}
						}
					}
					
					
				}
				
				
				if(!variantScoreMap.isEmpty()){
					List<ExcludedItems> excludedItems= excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(),
							studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), null);
					if(CollectionUtils.isNotEmpty(excludedItems)){
						for(ExcludedItems eItems : excludedItems){
							variantScoreMap.remove(eItems.getTaskVariantId());
						}
					}
				}
				BigDecimal testScore = new BigDecimal(0);
				for(Long variantScore : variantScoreMap.keySet()){
					BigDecimal responseScore = variantScoreMap.get(variantScore);
					if(responseScore != null)
						testScore = testScore.add(responseScore);
				}
				
				BigDecimal tmpScore = stageTestScore.get(studentReportTestResponse.getStageCode());
				if (tmpScore == null || tmpScore.compareTo(testScore) == 1){
					stageTestScore.put(studentReportTestResponse.getStageCode(), testScore);
					if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setExternalTest1Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest1Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest1IdTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest1IdResponseCount(responseCount);
				    	studentReport.setStage1RespondedItems(responseCount);
				    	studentReport.setStage1HasSCCode(scCodeStatus);
				    	studentReport.setStage1TestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
					if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest2Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest2Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest2idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest2IdResponseCount(responseCount);
				    	studentReport.setStage2RespondedItems(responseCount);
				    	studentReport.setStage2HasSCCode(scCodeStatus);
				    	studentReport.setStage2TestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
					if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest3Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest3Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest3idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest3IdResponseCount(responseCount);
				    	studentReport.setStage3HasSCCode(scCodeStatus);
				    	studentReport.setStage3TestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
					if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setExternalTest4Id(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentTest4Id(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setTest4idTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setTest4IdResponseCount(responseCount);
				    	studentReport.setStage4HasSCCode(scCodeStatus);
				    	studentReport.setStage4TestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
					if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				    	studentReport.setPerformanceTestExternalId(studentReportTestResponse.getTestExternalId());
				    	studentReport.setStudentPerformanceTestId(studentReportTestResponse.getTestId());
				    	studentReportTestScores.setPrfrmTestTaskVariantCount(taskVariantCount);
				    	studentReportTestScores.setPrfrmTestResponseCount(responseCount);
				    	studentReport.setPrfrmStageHasSCCode(scCodeStatus);
				    	studentReport.setPrfrmTestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
				}
			}else{//Pending or Unused status
				if(stageTestScore.get(studentReportTestResponse.getStageCode()) == null) {					
					if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage1TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage1HasSCCode(scCodeStatus);
					}
					if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage2TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage2HasSCCode(scCodeStatus);
					}
					if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage3TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage3HasSCCode(scCodeStatus);
					}
					if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStage4TestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setStage4HasSCCode(scCodeStatus);
					}
					if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setPrfrmTestStatus(studentReportTestResponse.getStudentsTestStatus());
						studentReport.setPrfrmStageHasSCCode(scCodeStatus);
					}
				}
				
			}
		
		}		
		
		logger.info(" Stage_1  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE1.getCode()));
		if(stageTestScore.get(StageEnum.STAGE1.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE1.getCode()));
			studentReportTestScores.setTest1IdRawScore(rawCutScore);
		}
		logger.info(" Stage_2  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE2.getCode()));
		if(stageTestScore.get(StageEnum.STAGE2.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE2.getCode()));
			studentReportTestScores.setTest2IdRawScore(stageTestScore.get(StageEnum.STAGE2.getCode()));
		}
		logger.info(" Stage_3  score for student: "+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.STAGE3.getCode()));
		if(stageTestScore.get(StageEnum.STAGE3.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE3.getCode()));
			studentReportTestScores.setTest3IdRawScore(stageTestScore.get(StageEnum.STAGE3.getCode()));
		}
		logger.info(" Stage_4  score for student: "+studentReport.getStudentId()+" score: "+stageTestScore.get(StageEnum.STAGE4.getCode()));
		if(stageTestScore.get(StageEnum.STAGE4.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE4.getCode()));
			studentReportTestScores.setTest4IdRawScore(stageTestScore.get(StageEnum.STAGE4.getCode()));
		}
		
				
		testIds.add(studentReport.getExternalTest1Id());
		if(studentReport.getExternalTest2Id() !=null){
			testIds.add(studentReport.getExternalTest2Id());
		}
		if(studentReport.getExternalTest3Id() != null){
			testIds.add(studentReport.getExternalTest3Id());
		}	
		if(studentReport.getExternalTest4Id() != null){
			testIds.add(studentReport.getExternalTest4Id());
		}
		
		if(stageTestScore.get(StageEnum.PERFORMANCE.getCode()) != null) {
			studentReportTestScores.setPrfrmTestRawScore(stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
			RawToScaleScores rawToScaleScores= rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
																	studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
																	studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
																	studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId(), null, 18L, null);
		
			if(rawToScaleScores != null){
				logger.info(" Performance stage  score for student:"+studentReport.getStudentId()+" Score: "+stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
				if(rawToScaleScores.getPerformanceRawscoreIncludeFlag() != null){
					studentReport.setPerformanceRawscoreIncludeFlag(rawToScaleScores.getPerformanceRawscoreIncludeFlag());
					if(rawToScaleScores.getPerformanceRawscoreIncludeFlag()){
						if(rawToScaleScores.getPerformanceItemWeight() != null){
							logger.info("Performance Item Weight is: "+ rawToScaleScores.getPerformanceItemWeight()+" for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId());
							rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.PERFORMANCE.getCode()).multiply(rawToScaleScores.getPerformanceItemWeight()));
						}else{
							ReportProcessReason reportProcessReason = new ReportProcessReason();
							String msg = "PerformanceItemWeight is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
							logger.info(msg);
							reportProcessReason.setStudentId(studentReport.getStudentId());
							reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
							reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
							reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
							reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
							reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
							reportProcessReason.setReason(msg);
							reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
							((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
							throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
						}
						
					}else{
						logger.info("Assigning Performance stage score to MDPT Score....for student: "+studentReport.getStudentId()+" MDPT Score:"+stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
						studentReport.setMdptScorableFlag(mdptScorable);
						if (mdptScorable!=null && mdptScorable) {
							studentReport.setMdptScore(stageTestScore.get(StageEnum.PERFORMANCE.getCode()));
						} else {
							studentReport.setMdptScore(null);
						}
					}
					
				}
			}else{
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "RawToScaleScore record is not available for Assessment Program - " + studentReport.getAssessmentProgramId() + " Grade Id - " + studentReport.getGradeId() + " Subject Id -" + studentReport.getContentAreaId();
				logger.info(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
				reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
				reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
				reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
				reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
				reportProcessReason.setReason(msg);
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
			}
			
		}		
			
		//Do not produce score		
		if(studentReport.getExternalTest1Id() != null && testsCompletedStatusId.equals(studentReport.getStage1TestStatus()) && studentReport.getStage1RespondedItems() >= 5 && !studentReport.getStage1HasSCCode()){
			if(studentReport.getExternalTest2Id() != null && 
					(testsCompletedStatusId.equals(studentReport.getStage2TestStatus())
					|| testsInProgressStatusId.equals(studentReport.getStage2TestStatus())
					|| testsInProgressTimedoutStatusId.equals(studentReport.getStage2TestStatus())) && studentReport.getStage2RespondedItems() >= 5
					&& !studentReport.getStage2HasSCCode()
					){
				studentReport.setStatus(true);
			}else{
				studentReport.setStatus(false);
			}
		}else{
			studentReport.setStatus(false);			
		}
		
		
		if(!studentReport.getStatus()){
			studentReport.setIncompleteStatus(true);
		}
		
		studentReport.setRawScore(rawCutScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		studentReport.setStudentReportTestScores(studentReportTestScores);
		
		return studentReport;
		
	}
}
