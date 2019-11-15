package edu.ku.cete.batch.reportprocess.processor;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.util.StageEnum;

public class KelpaCalculateRawCutScore implements ItemProcessor<StudentReport,Object>
{
	final static Log logger = LogFactory.getLog(KelpaCalculateRawCutScore.class);
	
	private StepExecution stepExecution;
	private Long testsCompletedStatusId;
	private Long testsInProgressStatusId;
	private Long testStatusUnusedId;
	private Long testStatusPendingId;
	private Long testStatusInprogressTimedoutId;
	private Long testSectionStatusUnusedId;
	private Long testSectionStatusCompletedId;
	private Long testSectionStatusInProgressId;	
	private List<Long> rawScaleExternalTestIds;
	private Long specialCircumstanceStatusSavedId;
	private Long specialCircumstanceStatusApprovedId;	
	private String contentAreaAbbreviatedName;
	private List<ExcludedItems> excludedItemsList;
	
	@Autowired
	private ExcludedItemsService excludedItemsService;	
	
	@Autowired
	private StudentReportService studentReportService;	
	
	@Autowired
	private CategoryService categoryService;
	
	@SuppressWarnings("unchecked")
	@Override
	public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside KelpaCalculateRawCutScore process....: for student::"+studentReport.getStudentId());
		studentReport.setStatus(true);
		studentReport.setIncompleteStatus(false);
		studentReport.setListeningHasSCCode(false);
		studentReport.setSpeakingHasSCCode(false);
		studentReport.setReadingHasSCCode(false);
		studentReport.setWritingHasSCCode(false);
		
		StudentReportTestScores studentReportTestScores = new StudentReportTestScores();
		int taskVariantCount = 0;
		int responseCount = 0;
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		if (CollectionUtils.isEmpty(studentReportTestResponses))
		{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No test responses found for student: "+studentReport.getStudentId() ;
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
		
		List<ExcludedItems> excludedItems = null;
		if(CollectionUtils.isEmpty(excludedItemsList)){
			excludedItems = excludedItemsService.getByAssessmentProgramContentAreaGrade(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(),
					studentReport.getCurrentSchoolYear());
		}else{
			excludedItems = getExcludedItemsList();
		}
		
		Boolean scCodeStatus = false;
		
		Map<String, BigDecimal> stageTestScore = new HashMap<String, BigDecimal>();
		for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses) {
			boolean isListening = StageEnum.LISTENING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode());
			boolean isSpeaking = StageEnum.SPEAKING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode());
			boolean isReading = StageEnum.READING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode());
			boolean isWriting = StageEnum.WRITING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode());
			scCodeStatus = false;
			
			if (studentReportTestResponse.getSpecialCircumstanceId() != null && studentReportTestResponse.getScCodeStatus() != null) {
				if (studentReportTestResponse.getKsdeRestricted() != null) {
					if (studentReportTestResponse.getKsdeRestricted()) {
						if (specialCircumstanceStatusApprovedId.equals(studentReportTestResponse.getScCodeStatus())) {
							scCodeStatus = true;
						}
					} else if (!Boolean.TRUE.equals(studentReportTestResponse.getReportScore()) && specialCircumstanceStatusSavedId.equals(studentReportTestResponse.getScCodeStatus())) {
						scCodeStatus = true;
					}
				}
			}
			
			Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
			List<StudentsResponses> studentsResponses = studentReportTestResponse.getStudentResponses();
			taskVariantCount = studentReportService.taskvariantCountByTestId(studentReportTestResponse.getTestId());
			responseCount = 0;
			
			// testlet ids
			Set<Long> clusteredScoringTestlets = new HashSet<Long>();
			
			for(StudentsResponses responses : studentsResponses) {
				boolean process = true;
				if(CollectionUtils.isNotEmpty(excludedItems)){
					for (ExcludedItems item : excludedItems) {
						if (responses.getTaskVariantExternalId().longValue() == item.getTaskVariantId().longValue()) {
							process = false;
							taskVariantCount--;
							break;
						}
					}
				}
				
				if (!process) {
					continue;
				}
				// Some KELPA2 tests (Speaking, for example) could not have a response, but still have a score.
				// Here, we check the studentstestsectionsid since in the query, that comes off of the studentsresponses record.
				// If none was found, this null check does not pass, so a response is not counted
				boolean responseExists = responses.getStudentsTestSectionsId() != null;
				if (responseExists) {
					responseCount++;
				}
				BigDecimal currentTaskScore = new BigDecimal(0);
				
				if (responseExists && Boolean.TRUE.equals(responses.getScoringNeeded())) {
					// machine-scored item
					if (responseExists) {
						if (responses.getScore() != null) {
							currentTaskScore = responses.getScore();
						} else {
							logger.warn("No score found in studentsresponses for studentsTestSectionsId: " + responses.getStudentsTestSectionsId() +
									", taskVariantId: " + responses.getTaskVariantId());
						}
					} else {
						logger.warn("No response found in studentsresponses for studentsTestsId: " + studentReportTestResponse.getStudentsTestsId() +
								", taskVariantId: " + responses.getTaskVariantId() + ", domain: " + studentReportTestResponse.getStageCode());
					}
				} else {
					// human-scored item
					// if there's no score, we don't really have much to do, so skip score addition
					if (responses.getCcqScore() != null) {
						currentTaskScore = responses.getCcqScore();
						if (responses.getCcqNonScoringReason() != null) {
							// As of 2017, if an HSO is received, the score still counts.
							// So if the code received was not HSO, the score for the item is 0.
							Category nonScoreReason = categoryService.selectByPrimaryKey(responses.getCcqNonScoringReason());
							if (!nonScoreReason.getCategoryCode().equalsIgnoreCase("HSO")) {
								currentTaskScore = new BigDecimal(0);
								logger.debug("Found HSO non-score reason for studentsTestsId: " + studentReportTestResponse.getStudentsTestsId() +
										", taskVariantId: " + responses.getTaskVariantId() + ", domain: " + studentReportTestResponse.getStageCode());
							}
						}
					}
				}
				
				// this keeps us from adding duplicate scores from the same cluster
				// we basically just pick one at random, assuming not all of them are excluded items...in which case, none would be considered
				boolean score = !Boolean.TRUE.equals(responses.getClusterScoring()) || (
					Boolean.TRUE.equals(responses.getClusterScoring()) &&
					responses.getTestletId() != null && 
					!clusteredScoringTestlets.contains(responses.getTestletId()));
				if (score) {
					clusteredScoringTestlets.add(responses.getTestletId());
					
					//Handle duplicate items
					BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
					if (existingScore != null) {
						variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(currentTaskScore));
					} else {
						variantScoreMap.put(responses.getTaskVariantExternalId(), currentTaskScore);
					}
					
					logger.debug("adding task variant id " + responses.getTaskVariantId() + " (testlet id " + responses.getTestletId() +
							") into score calcs for student " + studentReport.getStudentId() + " " + studentReportTestResponse.getStageCode());
				} else {
					logger.debug("cluster scoring not triggered on testlet id " + responses.getTestletId() + ", skipping task variant id " +
							responses.getTaskVariantId() + " for student " + studentReport.getStudentId() + " " + studentReportTestResponse.getStageCode());
				}
			}
			
			BigDecimal testScore = new BigDecimal(0);
			for(Long variantScore : variantScoreMap.keySet()) {
				BigDecimal responseScore = variantScoreMap.get(variantScore);
				if (responseScore != null) {
					testScore = testScore.add(responseScore);
				}
			}
			
			stageTestScore.put(studentReportTestResponse.getStageCode(), testScore);
			if (isReading) {
				studentReport.setExternalTest1Id(studentReportTestResponse.getTestExternalId());
				studentReport.setStudentTest1Id(studentReportTestResponse.getTestId());
				studentReportTestScores.setTest1IdTaskVariantCount(taskVariantCount);
		    	studentReportTestScores.setTest1IdResponseCount(responseCount);
				studentReport.setReadingHasSCCode(scCodeStatus);
				studentReport.setReadingTestStatus(studentReportTestResponse.getStudentsTestStatus());
			}
			if (isListening) {
				studentReport.setExternalTest2Id(studentReportTestResponse.getTestExternalId());
				studentReport.setStudentTest2Id(studentReportTestResponse.getTestId());
				studentReportTestScores.setTest2idTaskVariantCount(taskVariantCount);
		    	studentReportTestScores.setTest2IdResponseCount(responseCount);
				studentReport.setListeningHasSCCode(scCodeStatus);
				studentReport.setListeningTestStatus(studentReportTestResponse.getStudentsTestStatus());
			}
			if (isSpeaking) {
				studentReport.setExternalTest3Id(studentReportTestResponse.getTestExternalId());
				studentReport.setStudentTest3Id(studentReportTestResponse.getTestId());
				studentReportTestScores.setTest3idTaskVariantCount(taskVariantCount);
		    	studentReportTestScores.setTest3IdResponseCount(responseCount);
				studentReport.setSpeakingHasSCCode(scCodeStatus);
				studentReport.setSpeakingTestStatus(studentReportTestResponse.getStudentsTestStatus());
			}
			if (isWriting) {
				studentReport.setExternalTest4Id(studentReportTestResponse.getTestExternalId());
				studentReport.setStudentTest4Id(studentReportTestResponse.getTestId());
				studentReportTestScores.setTest4idTaskVariantCount(taskVariantCount);
		    	studentReportTestScores.setTest4IdResponseCount(responseCount);
				studentReport.setWritingHasSCCode(scCodeStatus);
				studentReport.setWritingTestStatus(studentReportTestResponse.getStudentsTestStatus());
			}
		}
		
		BigDecimal readingScore = stageTestScore.get(StageEnum.READING.getCode());
		BigDecimal listeningScore = stageTestScore.get(StageEnum.LISTENING.getCode());
		BigDecimal speakingScore = stageTestScore.get(StageEnum.SPEAKING.getCode());
		BigDecimal writingScore = stageTestScore.get(StageEnum.WRITING.getCode());
		
		BigDecimal comprehensionRawScore = new BigDecimal(0);
		BigDecimal overallRawScore = new BigDecimal(0);
		
		if (readingScore != null) {
			comprehensionRawScore = comprehensionRawScore.add(readingScore);
			overallRawScore = overallRawScore.add(readingScore);
			studentReportTestScores.setTest1IdRawScore(readingScore);
			studentReport.setReadingRawScore(readingScore);
		}
		if (listeningScore != null) {
			comprehensionRawScore = comprehensionRawScore.add(listeningScore);
			overallRawScore = overallRawScore.add(listeningScore);
			studentReportTestScores.setTest2IdRawScore(listeningScore);
			studentReport.setListeningRawScore(listeningScore);
		}
		if (speakingScore != null) {
			overallRawScore = overallRawScore.add(speakingScore);
			studentReportTestScores.setTest3IdRawScore(speakingScore);
			studentReport.setSpeakingRawScore(speakingScore);
		}
		if (writingScore != null) {
			overallRawScore = overallRawScore.add(writingScore);
			studentReportTestScores.setTest4IdRawScore(writingScore);
			studentReport.setWritingRawScore(writingScore);
		}
		
		studentReport.setComprehensionRawScore(comprehensionRawScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		studentReport.setRawScore(overallRawScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		studentReport.setStudentReportTestScores(studentReportTestScores);
		logger.debug("Completed KelpaCalculateRawCutScore....: for student::"+studentReport.getStudentId());
		return studentReport;
	}
		
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}

	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}


	public Long getTestsInProgressStatusId() {
		return testsInProgressStatusId;
	}


	public void setTestsInProgressStatusId(Long testsInProgressStatusId) {
		this.testsInProgressStatusId = testsInProgressStatusId;
	}


	public Long getTestStatusUnusedId() {
		return testStatusUnusedId;
	}


	public void setTestStatusUnusedId(Long testStatusUnusedId) {
		this.testStatusUnusedId = testStatusUnusedId;
	}
	
	public Long getTestStatusPendingId() {
		return testStatusPendingId;
	}


	public void setTestStatusPendingId(Long testStatusPendingId) {
		this.testStatusPendingId = testStatusPendingId;
	}


	public Long getTestSectionStatusUnusedId() {
		return testSectionStatusUnusedId;
	}


	public void setTestSectionStatusUnusedId(Long testSectionStatusUnusedId) {
		this.testSectionStatusUnusedId = testSectionStatusUnusedId;
	}

	public Long getTestSectionStatusCompletedId() {
		return testSectionStatusCompletedId;
	}

	public void setTestSectionStatusCompletedId(Long testSectionStatusCompletedId) {
		this.testSectionStatusCompletedId = testSectionStatusCompletedId;
	}

	public Long getTestSectionStatusInProgressId() {
		return testSectionStatusInProgressId;
	}

	public void setTestSectionStatusInProgressId(Long testSectionStatusInProgressId) {
		this.testSectionStatusInProgressId = testSectionStatusInProgressId;
	}

	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}

	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
	}

	public Long getSpecialCircumstanceStatusSavedId() {
		return specialCircumstanceStatusSavedId;
	}

	public void setSpecialCircumstanceStatusSavedId(Long specialCircumstanceStatusSavedId) {
		this.specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedId;
	}

	public Long getSpecialCircumstanceStatusApprovedId() {
		return specialCircumstanceStatusApprovedId;
	}

	public void setSpecialCircumstanceStatusApprovedId(Long specialCircumstanceStatusApprovedId) {
		this.specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedId;
	}

	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}

	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}

	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}

	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}


	public List<ExcludedItems> getExcludedItemsList() {
		return excludedItemsList;
	}


	public void setExcludedItemsList(List<ExcludedItems> excludedItemsList) {
		this.excludedItemsList = excludedItemsList;
	}
}
