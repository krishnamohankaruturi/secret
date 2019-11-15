package edu.ku.cete.batch.reportprocess.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.report.TestCutScoresService;
import edu.ku.cete.util.StageEnum;

public class KelpaCalculateLevelOnScaleScore implements ItemProcessor<StudentReport,Object> {
	private StepExecution stepExecution;
	
	final static Log logger = LogFactory.getLog(KelpaCalculateLevelOnScaleScore.class);

	@Autowired
	private TestCutScoresService testCutScoresService;
	
	private Long testingProgramId;
	
	@SuppressWarnings("unchecked")
	@Override
	public StudentReport process(StudentReport studentReport) throws Exception {
		logger.trace("Inside KELPA2 Calculate Levels process....Student - " + studentReport.getStudentId());
		
		List<TestCutScores> testCutScoresData = testCutScoresService.getCutScoresBasedOnAssessmentProgramSubjectYear(
			studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(),
			studentReport.getSchoolYear(), studentReport.getGradeId(), testingProgramId);
		if (CollectionUtils.isEmpty(testCutScoresData)) {
			String msg = "No scale score value exists in TestCutScores for School Year - " + studentReport.getSchoolYear() +
					", Assessment Program - " + studentReport.getAssessmentProgramId() +
					", GradeId - " + studentReport.getGradeId() +
					", SubjectId - " + studentReport.getContentAreaId() +
					", Student - " + studentReport.getStudentId();
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, null));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			List<String> domains = Arrays.asList(
				StageEnum.READING.getDescription(),
				StageEnum.LISTENING.getDescription(),
				StageEnum.SPEAKING.getDescription(),
				StageEnum.WRITING.getDescription());
			Map<String, List<TestCutScores>> domainTestCutScores = separateTestCutScoresBasedOnDomains(domains, testCutScoresData);
			
			for (int x = 0; x < domains.size(); x++) {
				String domain = domains.get(x);
				boolean isReading = domain.equals(StageEnum.READING.getDescription());
				boolean isListening = domain.equals(StageEnum.LISTENING.getDescription());
				boolean isSpeaking = domain.equals(StageEnum.SPEAKING.getDescription());
				boolean isWriting = domain.equals(StageEnum.WRITING.getDescription());
				Long level = null;
				Long scaleScore = null;
				if (isReading) {
					scaleScore = studentReport.getReadingScaleScore();
				} else if (isListening) {
					scaleScore = studentReport.getListeningScaleScore();
				} else if (isSpeaking) {
					scaleScore = studentReport.getSpeakingScaleScore();
				} else if (isWriting) {
					scaleScore = studentReport.getWritingScaleScore();
				}
				
				List<TestCutScores> currentDomainTestCutScores = domainTestCutScores.get(domain);
				
				if (CollectionUtils.isEmpty(currentDomainTestCutScores)) {
					String msg = "Could not find any cut scores for domain " + domain + ", scale score of " + scaleScore + " for student " + studentReport.getStudentId();
					((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, domain));
					throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
				} else {
					for (TestCutScores testCutScore : currentDomainTestCutScores) {
						Long lowScore = testCutScore.getLevelLowCutScore();
						Long highScore = testCutScore.getLevelHighCutScore();
						if (lowScore <= scaleScore && scaleScore <= highScore) {
							level = testCutScore.getLevel();
							break;
						}
					}
					if (level == null) {
						String msg = "Could not find level for scale score of " + scaleScore + " for student " + studentReport.getStudentId();
						((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, domain));
						throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
					} else {
						if (isReading) {
							studentReport.setReadingLevel(level);
						} else if (isListening) {
							studentReport.setListeningLevel(level);
						} else if (isSpeaking) {
							studentReport.setSpeakingLevel(level);
						} else if (isWriting) {
							studentReport.setWritingLevel(level);
						}
					}
				}
			}
			
			studentReport.setLevel(calculateOverallLevel(studentReport));
		}
		
		logger.trace("Completed KELPA2 Calculate Levels.");
		return studentReport;
	}
	
	/** Separates lumped TestCutScore data into each domain:<br>
	 * <pre>
	 * "Reading" -> list of reading cut scores,
	 * "Listening" -> list of listening cut scores,
	 * ...</pre>
	 * Important note at the time of initial development (May 2017): Unlike most other places in the reporting code,
	 * the domains used here are the StageEnum descriptions, not codes, since the testcutscores table's domain column comes
	 * directly from the upload.
	 * 
	 * @param domains
	 * @param testCutScores
	 * @return Map described above
	 */
	private Map<String, List<TestCutScores>> separateTestCutScoresBasedOnDomains(List<String> domains, List<TestCutScores> testCutScores) {
		Map<String, List<TestCutScores>> domainTestCutScores = new HashMap<String, List<TestCutScores>>();
		
		for (String domain : domains) {
			List<TestCutScores> currentDomainTestCutScores = new ArrayList<TestCutScores>();
			for (TestCutScores testCutScore : testCutScores) {
				if (domain.equals(testCutScore.getDomain())) {
					currentDomainTestCutScores.add(testCutScore);
				}
			}
			domainTestCutScores.put(domain, currentDomainTestCutScores);
		}
		
		return domainTestCutScores;
	}
	
	/**
	 * Calculate the Overall Level for a student report.
	 * <ul>
	 *   <li>in 2017, the overall level was calculated the same way the domain levels were--from the corresponding scale score (overall, in that case)</li>
	 *   <li>in 2018 (addition of this method), the overall level is calculated by looking at the rest of the domains and reacting accordingly:
	 *     <ul>
	 *       <li>if all domain levels are less than or equal to 2: level 1</li>
	 *       <li>if all domain levels are greater than or equal to 4: level 3</li>
	 *       <li>anything else: level 2</li>
	 *     </ul>
	 *   </li>
	 * </ul>
	 * @param studentReport The report to use in calculations
	 * @return The overall level
	 */
	private Long calculateOverallLevel(StudentReport studentReport) {
		if (studentReport == null) return null;
		
		List<Long> calculatedLevels = Arrays.asList(
			studentReport.getReadingLevel(),
			studentReport.getListeningLevel(),
			studentReport.getSpeakingLevel(),
			studentReport.getWritingLevel());
		boolean allLevelsLTE2 = true;
		boolean allLevelsGTE4 = true;
		
		for (int x = 0; x < calculatedLevels.size(); x++) {
			Long currentLevel = calculatedLevels.get(x);
			logger.debug("overall level calculation for student " + studentReport.getStudentId() + " -- currentLevel = " + currentLevel);
			if (currentLevel != null) {
				allLevelsLTE2 &= currentLevel <= 2;
				allLevelsGTE4 &= currentLevel >= 4;
			}
		}
		
		Long overallLevel = null;
		if (allLevelsLTE2) {
			overallLevel = 1L; // lowest
		} else if (allLevelsGTE4) {
			overallLevel = 3L; // highest
		} else {
			overallLevel = 2L; // everything else
		}
		
		logger.debug("overall level calculation for student " + studentReport.getStudentId() + "-- overall level calculated = " + overallLevel);
		return overallLevel;
	}
	
	private ReportProcessReason buildReason(StudentReport studentReport, String msg, String domain) {
		ReportProcessReason reason = new ReportProcessReason();
		
		if (StageEnum.READING.getDescription().equals(domain)) {
			reason.setTestId1(studentReport.getExternalTest1Id());
		} else if (StageEnum.LISTENING.getDescription().equals(domain)) {
			reason.setTestId1(studentReport.getExternalTest2Id());
		} else if (StageEnum.SPEAKING.getDescription().equals(domain)) {
			reason.setTestId1(studentReport.getExternalTest3Id());
		} else if (StageEnum.WRITING.getDescription().equals(domain)) {
			reason.setTestId1(studentReport.getExternalTest4Id());
		} else if ("Comprehension".equals(domain)) {
			reason.setTestId1(studentReport.getExternalTest1Id());
			reason.setTestId2(studentReport.getExternalTest2Id());
		}
		
		reason.setReason(msg);
		reason.setStudentId(studentReport.getStudentId());
		reason.setReportProcessId(studentReport.getBatchReportProcessId());
		
		return reason;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}
}
