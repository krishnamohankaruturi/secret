package edu.ku.cete.batch.reportprocess.writer;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.ReportTestLevelSubscores;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.SubscoreFrameworkService;

 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchReportProcessScoreCalcWriter implements ItemWriter<StudentReport>
{
	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
 
    @Autowired
 	private SubscoreFrameworkService subscoreFrameworkService;
	
    @Value("${subscore.rating.insufficientData}")
	private int insufficientDataRating;
    
    private Long testingProgramId;
    
    @Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
    
    private String contentAreaAbbreviatedName;
    
    private String enableTroubleShooting;
    
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		if(!studentReportList.isEmpty()){
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside BatchReportProcessWriter ....writing for Student Id - " + studentReport.getStudentId());

				/* This part of code block is not needed for 2017 year
				// this is needed because of the way multiple studentreport rows will be inserted...
				// we have to hold the initial flag states, since if the data SHOULD be aggregated, but aggregate to school/district is false for that specific record,
				// then THAT record's flags need to be false, but the next studentreport record could be aggregated to school/district
				// the initial states indicate what should happen if the record is to be aggregated
				
				Boolean initialStateAggregateMainScorePerformanceLevel = studentReport.getAggregateMainScalescorePrfrmLevel();
				Boolean initialStateAggregateCombinedLevel = studentReport.getAggregateCombinedLevel();
				Boolean initialStateAggregateMdptScore = studentReport.getAggregateMdptScore();
				
				setNonOrgAggregateFlags(studentReport, initialStateAggregateMainScorePerformanceLevel,
						initialStateAggregateCombinedLevel, initialStateAggregateMdptScore);*/
				
				boolean aggregateToSchool = studentReport.getAggregateToSchool();
				boolean aggregateToDistrict = studentReport.getAggregateToDistrict();
					
				boolean alreadyAggregatedToSchool = false;
				boolean alreadyAggregatedToDistrict = false; 
				
				if (Boolean.FALSE.equals(studentReport.getStatus())) {
					studentReport.setAggregateToDistrict(false);
					studentReport.setAggregateToSchool(false);
				}
				else{
					if(studentReport.getAggregateDistrictId() != null && studentReport.getAggregateDistrictId().equals(studentReport.getDistrictId())){
						studentReport.setAggregateToDistrict(aggregateToDistrict);
						alreadyAggregatedToDistrict = true;
					}
					if(studentReport.getAggregateSchoolId() != null && studentReport.getAggregateSchoolId().equals(studentReport.getAttendanceSchoolId())){
						studentReport.setAggregateToSchool(aggregateToSchool);
						alreadyAggregatedToSchool = true;
					}
				}
				studentReport.setExitStatus(determineExitStatus(studentReport));
				writeReportToDb(studentReport);
				
				if (studentReport.getAttendanceSchoolId2() != null) {
					studentReport.setId(null);
					studentReport.setAttendanceSchoolId(studentReport.getAttendanceSchoolId2());
					studentReport.setDistrictId(studentReport.getDistrictId2());
					studentReport.setEnrollmentId(studentReport.getEnrollmentId2());
					
					aggregateToSchool = Boolean.TRUE.equals(studentReport.getStatus()) 
										&& (!alreadyAggregatedToSchool) 
										&& (studentReport.getAggregateSchoolId() != null && studentReport.getAggregateSchoolId() == studentReport.getAttendanceSchoolId());
					
					aggregateToDistrict = Boolean.TRUE.equals(studentReport.getStatus())
										  && (!alreadyAggregatedToDistrict)
										  && (studentReport.getAggregateDistrictId() != null && studentReport.getAggregateDistrictId() == studentReport.getDistrictId());
					
					studentReport.setAggregateToSchool(aggregateToSchool);
					studentReport.setAggregateToDistrict(aggregateToDistrict);
					
					studentReport.setExitStatus(determineExitStatus(studentReport));
					writeReportToDb(studentReport);
				}
				
				if (studentReport.getAttendanceSchoolId3() != null) {
					studentReport.setId(null);
					studentReport.setAttendanceSchoolId(studentReport.getAttendanceSchoolId3());
					studentReport.setDistrictId(studentReport.getDistrictId3());
					studentReport.setEnrollmentId(studentReport.getEnrollmentId3());
					
					aggregateToSchool = Boolean.TRUE.equals(studentReport.getStatus()) 
										&& (!alreadyAggregatedToSchool) 
										&& (studentReport.getAggregateSchoolId() != null && studentReport.getAggregateSchoolId() == studentReport.getAttendanceSchoolId());
					
					aggregateToDistrict = Boolean.TRUE.equals(studentReport.getStatus()) 
											&& (!alreadyAggregatedToDistrict) 
											&& (studentReport.getAggregateDistrictId() != null && studentReport.getAggregateDistrictId() == studentReport.getDistrictId());
					
					studentReport.setAggregateToSchool(aggregateToSchool);
					studentReport.setAggregateToDistrict(aggregateToDistrict);
					
					studentReport.setExitStatus(Boolean.FALSE);
					writeReportToDb(studentReport);
				}
				
				logger.debug("Completed BatchReportProcessWriter for Student Id - " + studentReport.getStudentId());
			}
		}
	}
	
	private void setNonOrgAggregateFlags(StudentReport studentReport, Boolean initialStateAggregateMainScorePerformanceLevel,
			Boolean initialStateAggregateCombinedLevel, Boolean initialStateAggregateMdptScore) {
		boolean aggregateToOrg = Boolean.TRUE.equals(studentReport.getAggregateToSchool()) || Boolean.TRUE.equals(studentReport.getAggregateToDistrict());
		
		if (!aggregateToOrg) {
			if (studentReport.getAggregateMainScalescorePrfrmLevel() != null && studentReport.getAggregateMainScalescorePrfrmLevel()) {
				studentReport.setAggregateMainScalescorePrfrmLevel(false);
				studentReport.setAggregateCombinedLevel(false);
			}
			if (studentReport.getAggregateMdptScore() != null && studentReport.getAggregateMdptScore()) {
				studentReport.setAggregateMdptScore(false);
				studentReport.setAggregateCombinedLevel(false);
			}
		} else {
			studentReport.setAggregateMainScalescorePrfrmLevel(initialStateAggregateMainScorePerformanceLevel);
			studentReport.setAggregateCombinedLevel(initialStateAggregateCombinedLevel);
			studentReport.setAggregateMdptScore(initialStateAggregateMdptScore);
		}
	}
	
	private Boolean determineExitStatus(StudentReport studentReport) {
		Long enrollmentId = studentReport.getEnrollmentId();
		if (enrollmentId != null) {
			for (Enrollment enrl : studentReport.getEnrollments()) {
				if (enrollmentId.equals(enrl.getId())) {
					return enrl.getExitWithdrawalDate() != null;
				}
			}
		}
		return false;
	}
	
	private void writeReportToDb(StudentReport studentReport) {
		studentReport.setBatchReportProcessId(batchReportProcessId);
		batchReportProcessService.insertSelectiveStudentReport(studentReport);
		
		if(studentReport.getIsProcessBySpecificStudentId() != null && studentReport.getIsProcessBySpecificStudentId()){
			studentReport.setTestingProgramId(testingProgramId);
			batchReportProcessService.updateStudentReportReprocessByStudentId(studentReport);
		}
		
		if(!"FALSE".equalsIgnoreCase(enableTroubleShooting)){
			if (studentReport.getStudentReportTestScores() != null) {
				studentReport.getStudentReportTestScores().setStudentReportId(studentReport.getId());
				batchReportProcessService.insertSelectiveStudentReportTestScores(studentReport.getStudentReportTestScores());
			}
		}		
		
		List<ReportSubscores> subscores = studentReport.getSubscoreBuckets();
		List<String> subscoreDefinitionNames = new ArrayList<String>();
		
		if(subscores!=null)
		{
			for(ReportSubscores subscore : subscores){
				subscore.setStudentReportId(studentReport.getId());
				subscoreDefinitionNames.add(subscore.getSubscoreDefinitionName());
				logger.debug("studentReportId: "+studentReport.getId()+"; SubscoreDef: "+subscore.getSubscoreDefinitionName()); 
				batchReportProcessService.insertSelectiveReportSubscores(subscore);
			}
			logger.debug("Subscores inserted for Student Id - " + studentReport.getStudentId() + " - StudentReportId: "+ studentReport.getId());
		}
		
		if(!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && studentReport.getStatus() != null && studentReport.getStatus()){
			List<String> missingSubscoreDefinitions = subscoreFrameworkService.getMissingSubscoreDefinitions(studentReport.getCurrentSchoolYear(), 
					studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
			
			if(missingSubscoreDefinitions != null && missingSubscoreDefinitions.size() > 0){
				ReportSubscores missingSubscore = null;
				BigDecimal subscoreRawscore = new BigDecimal(0);
				for(String subscoreDefintion : missingSubscoreDefinitions){
					if(!subscoreDefinitionNames.contains(subscoreDefintion)){
						missingSubscore = new ReportSubscores();
						missingSubscore.setStudentId(studentReport.getStudentId());
						missingSubscore.setStudentReportId(studentReport.getId());
						missingSubscore.setSubscoreDefinitionName(subscoreDefintion);
						missingSubscore.setSubscoreRawScore(subscoreRawscore);
						missingSubscore.setRating(insufficientDataRating);
						logger.debug("studentReportId: "+studentReport.getId()+"; SubscoreDef: "+missingSubscore.getSubscoreDefinitionName()); 
						batchReportProcessService.insertSelectiveReportSubscores(missingSubscore);
					}				
				}
				logger.debug("Missing subscores inserted for Student Id - " + studentReport.getStudentId() + " - StudentReportId: "+ studentReport.getId());
			}
		}		
		
		if(!"FALSE".equalsIgnoreCase(enableTroubleShooting)){
			List<ReportSubscores> testLevelSubscores = studentReport.getTestLevelSubscoreBuckets();
			if(testLevelSubscores != null){
				ReportTestLevelSubscores testLevelSubscore = null;
				for(ReportSubscores subscore : testLevelSubscores){
					testLevelSubscore = new ReportTestLevelSubscores();
					testLevelSubscore.setStudentReportId(studentReport.getId());
					testLevelSubscore.setTestId(subscore.getTestId());
					testLevelSubscore.setStudentId(studentReport.getStudentId());
					testLevelSubscore.setSubscoreDefinitionName(subscore.getSubscoreDefinitionName());
					testLevelSubscore.setTotalItems(subscore.getTotalItems());
					testLevelSubscore.setItemsResponded(subscore.getItemsResponded());
					testLevelSubscore.setSubscoreRawScore(subscore.getSubscoreRawScore());
					testLevelSubscore.setCreatedDate(subscore.getCreatedDate());
					
					logger.debug("studentReportId: "+studentReport.getId()+"; TestId: "+subscore.getTestId()+"; SubscoreDef: "+subscore.getSubscoreDefinitionName()); 
					batchReportProcessService.insertSelectiveTestLevelSubscore(testLevelSubscore);
				}
				logger.debug("TestLevel Subscores inserted for Student Id - " + studentReport.getStudentId() + " - StudentReportId: "+ studentReport.getId());
			}
		}
		
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}

	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}

	public String getEnableTroubleShooting() {
		return enableTroubleShooting;
	}

	public void setEnableTroubleShooting(String enableTroubleShooting) {
		this.enableTroubleShooting = enableTroubleShooting;
	}
	
	 
	

}
