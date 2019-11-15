package edu.ku.cete.batch.reportprocess.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.report.ScaleScoresBySubScoreDef;
import edu.ku.cete.report.SubScaleScoresByStudent;
import edu.ku.cete.report.SubScoreMedianScore;
import edu.ku.cete.service.report.BatchReportProcessService;

public class ReportProcessMedianBySubScoreProcessor implements ItemProcessor<ReportsMedianScore,Object>
{
    private StepExecution stepExecution;
    private Long batchReportProcessId;
    private String assessmentProgramCode;
    
    @Value("${report.minimum.required.kap}")
	private String reportMinimumRequiredKAP;
    
    @Value("${report.minimum.required.amp}")
   	private String reportMinimumRequiredAMP;
    
    @Value("${subscore.rating.insufficientData}")
	private int insufficientDataRating;
    
    @Autowired
	private BatchReportProcessService batchReportProcessService;
    
    final static Log logger = LogFactory.getLog(ReportProcessMedianBySubScoreProcessor.class);
    
	@Override
    public Map<String, Object> process(ReportsMedianScore subScoreMedianScore) throws Exception {
		logger.info("Inside Calculate School, State, District Median Score By subscore definition process....Org - " + subScoreMedianScore.getOrganizationId());
		Map<String, Object> reportsMap = new HashMap<String, Object>();
		SubScoreMedianScore studentsByOrgAndSubScoreDef = new SubScoreMedianScore();		
		
		if(subScoreMedianScore.getOrgTypeCode().equalsIgnoreCase("SCH")){
			studentsByOrgAndSubScoreDef = batchReportProcessService.getStudentsBySchoolAssmntGradeSubjectSubScoreDef(subScoreMedianScore.getOrganizationId(), 
					subScoreMedianScore.getAssessmentProgramId(),
					subScoreMedianScore.getContentAreaId(),
					subScoreMedianScore.getGradeId(),
					subScoreMedianScore.getSchoolYear());
		}else if(subScoreMedianScore.getOrgTypeCode().equalsIgnoreCase("DT")){
			studentsByOrgAndSubScoreDef = batchReportProcessService.getStudentsByDistrictAssmntGradeSubjectSubScoreDef(subScoreMedianScore.getOrganizationId(), 
					subScoreMedianScore.getAssessmentProgramId(),
					subScoreMedianScore.getContentAreaId(),
					subScoreMedianScore.getGradeId(),
					subScoreMedianScore.getSchoolYear());
		}else if(subScoreMedianScore.getOrgTypeCode().equalsIgnoreCase("ST")){
			studentsByOrgAndSubScoreDef = batchReportProcessService.getStudentsByStateAssmntGradeSubjectSubScoreDef(subScoreMedianScore.getOrganizationId(), 
					subScoreMedianScore.getAssessmentProgramId(),
					subScoreMedianScore.getContentAreaId(),
					subScoreMedianScore.getGradeId(),
					subScoreMedianScore.getSchoolYear());
		}
		List<ReportsMedianScore> reportMedianScoresBySubScoreDef = new ArrayList<ReportsMedianScore>();
		if(studentsByOrgAndSubScoreDef != null) {
			if(CollectionUtils.isNotEmpty(studentsByOrgAndSubScoreDef.getScaleScoresBySubScoreDef())) {
				List<ScaleScoresBySubScoreDef> scaleScoresBySubScoreDef = studentsByOrgAndSubScoreDef.getScaleScoresBySubScoreDef();
				for(ScaleScoresBySubScoreDef scaleScoreBySubScore: scaleScoresBySubScoreDef) {
					int countRating1 = 0;
					int countRating2 = 0;
					int countRating3 = 0;
					int countRatingMinus1 = 0;
					int totalRatingCount = 0;
					ReportsMedianScore reportMedianScore = new ReportsMedianScore();
					setReportMedianScoreDetails(subScoreMedianScore,
							scaleScoreBySubScore, reportMedianScore);
					List<SubScaleScoresByStudent> subScaleScoresByStudents  = scaleScoreBySubScore.getSubScaleScoresByStudent();
					List<Long> subScoreScaleScores = new ArrayList<Long>();
					for(SubScaleScoresByStudent subScaleScoresbyStudent : subScaleScoresByStudents) {
						if (subScoreMedianScore.getOrgTypeCode().equalsIgnoreCase("ST")) {
							if(subScaleScoresbyStudent.getSubscorescalescore() != null){
								subScoreScaleScores.add(subScaleScoresbyStudent.getSubscorescalescore());
							}
						} else {
							if (subScaleScoresbyStudent.getRating() == null) {
								logger.debug("Found null rating for studentid: " + subScaleScoresbyStudent.getStudentId());
							} else {
								totalRatingCount++;
								Integer rating = subScaleScoresbyStudent.getRating();
								if (rating == insufficientDataRating) {
									countRatingMinus1++;
								} else if (rating == 1) {
									countRating1++;
								} else if (rating == 2) {
									countRating2++;
								} else if (rating == 3) {
									countRating3++;
								}
							}
						}
					}
					if (!subScoreMedianScore.getOrgTypeCode().equalsIgnoreCase("ST")) {
						reportMedianScore.setStudentCount(totalRatingCount);
						if (totalRatingCount == 0) {
							logger.debug("Number of counted ratings is zero when calculating subscore rating for orgId: " + subScoreMedianScore.getOrganizationId() +
									", this will result in a insufficient rating for subscore " + scaleScoreBySubScore.getSubScoreDefinitionName());
							reportMedianScore.setRating(insufficientDataRating);
						} else if (countRatingMinus1 == totalRatingCount) {
							logger.debug("Number of counted ratings is equal to number of " + insufficientDataRating +
									" ratings when calculating subscore rating for orgId: " + subScoreMedianScore.getOrganizationId() +
									", this will result in a insufficient rating for subscore " + scaleScoreBySubScore.getSubScoreDefinitionName());
							reportMedianScore.setRating(insufficientDataRating);
						} else {
							reportMedianScore.setRating(calculateOverallRating(countRating1, countRating2, countRating3, (totalRatingCount - countRatingMinus1)));
						}
					}
					if(CollectionUtils.isNotEmpty(subScoreScaleScores)) {
						int studentCount = subScoreScaleScores.size();
						boolean calcMedainFlag = true;
						if(assessmentProgramCode.equalsIgnoreCase("KAP")) {
							if(studentCount < Integer.parseInt(reportMinimumRequiredKAP)) {
								calcMedainFlag = false;
							}
						}
						
						if(calcMedainFlag  && studentCount > 0) {
							reportMedianScore.setScore(batchReportProcessService.calcMedianScore(subScoreScaleScores, studentCount));
							double standardDeviation = batchReportProcessService.calcStandardDeviation(subScoreScaleScores);
							double standardError = standardDeviation / Math.sqrt(studentCount);
							// US16347 - Multiplying standard error with 1.253 
							reportMedianScore.setStandardError(BigDecimal.valueOf(standardError * 1.253));
						}
						reportMedianScore.setStudentCount(studentCount);
					}
					reportMedianScoresBySubScoreDef.add(reportMedianScore);
				}
			}
		}		
		reportsMap.put("subScoremedian", reportMedianScoresBySubScoreDef);
		logger.debug("Completed Calculate School, State, District Median Score and standard Error for Subscore definition.");
    	return reportsMap;
    }

	/**
	 * @param subScoreMedianScore
	 * @param scaleScoreBySubScore
	 * @param reportMedianScore
	 */
	private void setReportMedianScoreDetails(
			ReportsMedianScore subScoreMedianScore,
			ScaleScoresBySubScoreDef scaleScoreBySubScore,
			ReportsMedianScore reportMedianScore) {
		reportMedianScore.setAssessmentProgramId(subScoreMedianScore.getAssessmentProgramId());
		reportMedianScore.setContentAreaId(subScoreMedianScore.getContentAreaId());
		reportMedianScore.setGradeId(subScoreMedianScore.getGradeId());
		reportMedianScore.setOrganizationId(subScoreMedianScore.getOrganizationId());
		reportMedianScore.setOrganizationTypeId(subScoreMedianScore.getOrganizationTypeId());
		reportMedianScore.setOrgTypeCode(subScoreMedianScore.getOrgTypeCode());
		reportMedianScore.setSchoolYear(subScoreMedianScore.getSchoolYear());
		reportMedianScore.setSubScoreDefinitionName(scaleScoreBySubScore.getSubScoreDefinitionName());
	}
	
	private Integer calculateOverallRating(int countRating1, int countRating2, int countRating3, int totalRatingCount) {
		double percentWithRating1 = (double) countRating1 / (double) totalRatingCount;
		double percentWithRating2 = (double) countRating2 / (double) totalRatingCount;
		double percentWithRating3 = (double) countRating3 / (double) totalRatingCount;
		
		// if any rating is more than 50%, use that rating
		if (percentWithRating1 > .5) {
			return 1;
		} else if (percentWithRating2 > .5) {
			return 2;
		} else if (percentWithRating3 > .5) {
			return 3;
		}
		
		// if there are multiple ratings with 50%, use the better of the two according to this:
		// 1 & 2 -> 2
		// 1 & 3 -> 2
		// 2 & 3 -> 3
		if ((percentWithRating1 == .5 && percentWithRating2 == .5) ||
				(percentWithRating1 == .5 && percentWithRating3 == .5)) {
			return 2;
		} else if (percentWithRating2 == .5 && percentWithRating3 == .5) {
			return 3;
		}
		
		// if all of them are less than 50%, use 2
		return 2;
	}
	 	
		public StepExecution getStepExecution() {
			return stepExecution;
		}

		public void setStepExecution(StepExecution stepExecution) {
			this.stepExecution = stepExecution;
		}


		public Long getBatchReportProcessId() {
			return batchReportProcessId;
		}


		public void setBatchReportProcessId(Long batchReportProcessId) {
			this.batchReportProcessId = batchReportProcessId;
		}
		
		public String getAssessmentProgramCode() {
			return assessmentProgramCode;
		}

		public void setAssessmentProgramCode(String assessmentProgramCode) {
			this.assessmentProgramCode = assessmentProgramCode;
		}
		
}
