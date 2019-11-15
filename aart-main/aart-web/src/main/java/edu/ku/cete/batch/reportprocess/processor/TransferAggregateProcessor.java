package edu.ku.cete.batch.reportprocess.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.EnrollmentService;

public class TransferAggregateProcessor implements ItemProcessor<StudentReport,Object>
{
	final static Log logger = LogFactory.getLog(TransferAggregateProcessor.class);
	
    private StepExecution stepExecution;
    private Organization contractingOrganization;
    
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	private Long testsCompletedStatusId;
    private Long testsInProgressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
    private String contentAreaAbbreviatedName;
    
    @Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
	    
	@Override
    public StudentReport process(StudentReport studentReport) throws SkipBatchException {
		if ("KAP".equals(studentReport.getAssessmentProgramCode())) {
			
			
			/* Navya (Mar 2017): 
			 	For school year 2017, there are only atmost 2 stages of tests and NO performance tests. 
			 	But there can be any number of enrollments due to unlimited transfers.
			 	The tests could be in any stages in any of the enrollments. Need to figure out which enrollment has Stage 1 and stage 2 tests.
			 	Also, get school and district of those 2 enrollments along with the test status (status gets set in Rawscore calculation stage). 
				Aggregate to school or district based on the status of test stage level.
				Kiran : 4/3/2018, added checks for performance stage, HGSS has stage1 & performance this year 
			*/
			
			Enrollment stage1Enrollment = enrollmentService.getAttendanceSchoolAndDistrictByEnrollmentId(studentReport.getStage1EnrollmentId());
			if(stage1Enrollment != null){
				studentReport.setStage1EnrollmentId(stage1Enrollment.getId());
				studentReport.setStage1AttSchoolId(stage1Enrollment.getAttendanceSchoolId());
				studentReport.setStage1AttDistrictId(stage1Enrollment.getAttendanceSchoolDistrictId());
			}
			
			//Verify if the last enrollment only gets assigned to stage2. 
			Enrollment stage2Enrollment = null;
			if(studentReport.getStage2EnrollmentId() != null){
				stage2Enrollment = enrollmentService.getAttendanceSchoolAndDistrictByEnrollmentId(studentReport.getStage2EnrollmentId());
				if(stage2Enrollment != null){
					studentReport.setStage2EnrollmentId(stage2Enrollment.getId());
					studentReport.setStage2AttSchoolId(stage2Enrollment.getAttendanceSchoolId());
					studentReport.setStage2AttDistrictId(stage2Enrollment.getAttendanceSchoolDistrictId());
				}
			}

			Enrollment prfrmEnrollment = null;
			if(studentReport.getPrfrmStageEnrollmentId() != null){
				prfrmEnrollment = enrollmentService.getAttendanceSchoolAndDistrictByEnrollmentId(studentReport.getPrfrmStageEnrollmentId());
				if(prfrmEnrollment != null){
					studentReport.setPrfrmStageEnrollmentId(prfrmEnrollment.getId());
					studentReport.setPrfrmStageAttSchoolId(prfrmEnrollment.getAttendanceSchoolId());
					studentReport.setPrfrmStageAttDistrictId(prfrmEnrollment.getAttendanceSchoolDistrictId());
				}
			}
			
			boolean transferred = false; 
			if(studentReport.getEnrollments().size() > 1){
				transferred = true;
			}				

			//Test for subject transferred.
			List<Enrollment> allActiveEnrollmentsOfStudent = enrollmentService.allEnrollmentsWithSubjectDetailsForStudent(studentReport.getStudentId(), studentReport.getCurrentSchoolYear(), studentReport.getContentAreaId());
			for(Enrollment en: allActiveEnrollmentsOfStudent){
				if(!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName)){
					if((stage1Enrollment != null && en.getId() != stage1Enrollment.getId()) && 
							((stage2Enrollment != null && en.getId() != stage2Enrollment.getId())
								|| (stage2Enrollment == null && en.getId() != stage1Enrollment.getId()))){
						
						transferred = true;
						studentReport.getEnrollments().add(en);
					}
				}else if(CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName)){
					if((stage1Enrollment != null && en.getId() != stage1Enrollment.getId()) && 
							((prfrmEnrollment != null && en.getId() != prfrmEnrollment.getId())
								|| (prfrmEnrollment == null && en.getId() != stage1Enrollment.getId()))){
						
						transferred = true;
						studentReport.getEnrollments().add(en);
					}
				}
					
				studentReport.setCurrentEnrollmentId(en.getId());
				studentReport.setCurrentAttSchoolId(en.getAttendanceSchoolId());
				studentReport.setCurrentAttDistrictId(en.getAttendanceSchoolDistrictId());
			}
			
			if(stage1Enrollment != null){
				if(studentReport.getStage1TestStatus().equals(testsCompletedStatusId)){
					if(stage2Enrollment != null){ 
						 //Stage 2 not null only if it is within the same district (in same school or different school)
						if(studentReport.getStage2TestStatus().equals(testsCompletedStatusId) ||
								studentReport.getStage2TestStatus().equals(testsInProgressStatusId) || 
								studentReport.getStage2TestStatus().equals(testStatusInprogressTimedoutId) ||
								studentReport.getStage2TestStatus().equals(testStatusUnusedId)){
							if(stage1Enrollment.getId() == stage2Enrollment.getId()){
								//Report generated in one school. 
								//If current enrollment different from stage 2 then in that school as well.
								
								//Aggregated to school and district
								studentReport.setAggregateDistrictId(studentReport.getStage2AttDistrictId());	
								studentReport.setAggregateSchoolId(studentReport.getStage2AttSchoolId());
								
								if(stage2Enrollment.getId() == studentReport.getCurrentEnrollmentId()){
									//Stage2 complete and No Transfer after stage 1. 
									studentReport.setEnrollmentId(studentReport.getStage2EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage2AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage2AttDistrictId());
								}else{
									//Stage2 complete and transferred after finishing stage2 
									studentReport.setEnrollmentId(studentReport.getStage2EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage2AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage2AttDistrictId());
									
									if(studentReport.getCurrentEnrollmentId() > 0){ 
										studentReport.setEnrollmentId2(studentReport.getCurrentEnrollmentId());
										studentReport.setAttendanceSchoolId2(studentReport.getCurrentAttSchoolId());
										studentReport.setDistrictId2(studentReport.getCurrentAttDistrictId());
									}
								}
							}else if(stage1Enrollment.getId() != stage2Enrollment.getId()){
								//Stage2 complete and Transferred within district after stage 1
								//Report should be generated in both schools. 
								//If current enrollment different from stage 2 then in that school as well.
								
								//Aggregated only to district
								studentReport.setAggregateDistrictId(studentReport.getStage2AttDistrictId());	
								
								if(stage2Enrollment.getId() == studentReport.getCurrentEnrollmentId()){
									studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
									
									studentReport.setEnrollmentId2(studentReport.getStage2EnrollmentId());
									studentReport.setAttendanceSchoolId2(studentReport.getStage2AttSchoolId());
									studentReport.setDistrictId2(studentReport.getStage2AttDistrictId());
								}else{
									//Transferred within district or across district after finishing stage 2
									studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
									
									studentReport.setEnrollmentId2(studentReport.getStage2EnrollmentId());
									studentReport.setAttendanceSchoolId2(studentReport.getStage2AttSchoolId());
									studentReport.setDistrictId2(studentReport.getStage2AttDistrictId());
									
									if(studentReport.getCurrentEnrollmentId() > 0){ 
										studentReport.setEnrollmentId3(studentReport.getCurrentEnrollmentId());
										studentReport.setAttendanceSchoolId3(studentReport.getCurrentAttSchoolId());
										studentReport.setDistrictId3(studentReport.getCurrentAttDistrictId());
									}
								}
							}
								
						}
						
					}else {
							if((!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && stage2Enrollment == null)
									|| (CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && prfrmEnrollment == null)){
								//Stage 2 enrollment NULL. or performance stage is null for HGSS 
								//DISTRICT TRANSFER ==> Stage 2 not generated. But current enrollmentid and stage1 enrollmentid are different. 
								studentReport.setAggregateDistrictId(studentReport.getStage1AttDistrictId());	
								studentReport.setAggregateSchoolId(studentReport.getStage1AttSchoolId());
								
								if(studentReport.getCurrentEnrollmentId() !=0 && stage1Enrollment.getId() != studentReport.getCurrentEnrollmentId()){
									studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
									
									studentReport.setEnrollmentId2(studentReport.getCurrentEnrollmentId());
									studentReport.setAttendanceSchoolId2(studentReport.getCurrentAttSchoolId());
									studentReport.setDistrictId2(studentReport.getCurrentAttDistrictId());
								}else {
									studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
									studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
									studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
								}
								
							}else if(CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && prfrmEnrollment != null){
								determineHGSSTransferAggregation(studentReport, stage1Enrollment, prfrmEnrollment);
							}
							
							
						
					}
					
				}else if(studentReport.getStage1TestStatus().equals(testsInProgressStatusId) ||
						studentReport.getStage1TestStatus().equals(testStatusInprogressTimedoutId) ||
						studentReport.getStage1TestStatus().equals(testStatusUnusedId)){
					
					if((!CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && stage2Enrollment == null)
							|| (CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && prfrmEnrollment == null)){
						 
						studentReport.setAggregateSchoolId(studentReport.getStage1AttSchoolId());
						studentReport.setAggregateDistrictId(studentReport.getStage1AttDistrictId());
						
						if(stage1Enrollment.getId() == studentReport.getCurrentEnrollmentId()){
							studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
							studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
							studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
						}else {
							studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
							studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
							studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
							
							if(studentReport.getCurrentEnrollmentId() > 0){ 
								studentReport.setEnrollmentId2(studentReport.getCurrentEnrollmentId());
								studentReport.setAttendanceSchoolId2(studentReport.getCurrentAttSchoolId());
								studentReport.setDistrictId2(studentReport.getCurrentAttDistrictId());
							}
						}
						
					} else if(CONTENT_AREA_SS.equalsIgnoreCase(contentAreaAbbreviatedName) && prfrmEnrollment != null){
						
						determineHGSSTransferAggregation(studentReport, stage1Enrollment, prfrmEnrollment);
						
					}else {
						//Stage2Enrollment will never exist. As stage1 will have got inactivated and another stage1 created.
						//Science may have stage 1 unused status and stage2 in pending status
						studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
						studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
						studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
					}
				}
				
			}else{
				//This occurs if student is exited without taking stage1 but completed performance stage (unused stage1 will be inactivated), 
				//since HGSS stage1 & prfrm are independent of each other
				if(prfrmEnrollment != null){
					studentReport.setEnrollmentId(prfrmEnrollment.getId());
					studentReport.setAttendanceSchoolId(prfrmEnrollment.getAttendanceSchoolId());
					studentReport.setDistrictId(prfrmEnrollment.getAttendanceSchoolDistrictId());					
				}
			}
			
			studentReport.setTransferred(transferred);
			// Check if reports will be shown in a school with active enrollment but studentreport generating in previous schools.
			
			// initial state for the first record...the writer will handle if there is a second or third school and change these accordingly
			if(studentReport.getAggregateSchoolId() != null){
				studentReport.setAggregateToSchool(Boolean.TRUE);
			}else{
				studentReport.setAggregateToSchool(Boolean.FALSE);
			}				
			
			if(studentReport.getAggregateDistrictId()!=null){
				studentReport.setAggregateToDistrict(Boolean.TRUE);
			}else{
				studentReport.setAggregateToDistrict(Boolean.FALSE);
			}
			
		} else {
			// set transferred to false, because the rules are only for KAP
			studentReport.setTransferred(Boolean.FALSE);
		}

		
		return studentReport;
    }

	
	/**
	 * HGSS will have stage1 and performance stages
	 * @param studentReport
	 * @param stage1Enrollment
	 * @param prfrmEnrollment
	 */
	private void determineHGSSTransferAggregation(StudentReport studentReport, Enrollment stage1Enrollment, Enrollment prfrmEnrollment) {
		
		if(studentReport.getPrfrmTestStatus().equals(testsCompletedStatusId) ||
				studentReport.getPrfrmTestStatus().equals(testsInProgressStatusId) || 
				studentReport.getPrfrmTestStatus().equals(testStatusInprogressTimedoutId) ||
				studentReport.getPrfrmTestStatus().equals(testStatusUnusedId)){
				
			if(stage1Enrollment.getId() == prfrmEnrollment.getId()){
				//Report generated in one school. 								
				
				//Aggregated to school and district
				studentReport.setAggregateDistrictId(studentReport.getPrfrmStageAttDistrictId());	
				studentReport.setAggregateSchoolId(studentReport.getPrfrmStageAttSchoolId());
				
				if(prfrmEnrollment.getId() == studentReport.getCurrentEnrollmentId()){
					//Prfrm stage is complete and No Transfer after stage 1. 
					studentReport.setEnrollmentId(studentReport.getPrfrmStageEnrollmentId());
					studentReport.setAttendanceSchoolId(studentReport.getPrfrmStageAttSchoolId());
					studentReport.setDistrictId(studentReport.getPrfrmStageAttDistrictId());
					
				}else{//If current enrollment is different from Performance enrollment
				
					//Transferred after finishing performance stage 
					studentReport.setEnrollmentId(studentReport.getPrfrmStageEnrollmentId());
					studentReport.setAttendanceSchoolId(studentReport.getPrfrmStageAttSchoolId());
					studentReport.setDistrictId(studentReport.getPrfrmStageAttDistrictId());
					
					if(studentReport.getCurrentEnrollmentId() > 0){ 
						studentReport.setEnrollmentId2(studentReport.getCurrentEnrollmentId());
						studentReport.setAttendanceSchoolId2(studentReport.getCurrentAttSchoolId());
						studentReport.setDistrictId2(studentReport.getCurrentAttDistrictId());
					}
				}
			}else if(stage1Enrollment.getId() != prfrmEnrollment.getId()){
				//Performance stage is in different school
				//Report should be generated in both schools. 
				//If current enrollment is different from Performance then in that school as well.
				
				//Aggregated only to district
				studentReport.setAggregateDistrictId(studentReport.getPrfrmStageAttDistrictId());	
				
				if(prfrmEnrollment.getId() == studentReport.getCurrentEnrollmentId()){
					studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
					studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
					studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
					
					studentReport.setEnrollmentId2(studentReport.getPrfrmStageEnrollmentId());
					studentReport.setAttendanceSchoolId2(studentReport.getPrfrmStageAttSchoolId());
					studentReport.setDistrictId2(studentReport.getPrfrmStageAttDistrictId());
				}else{
					//Transferred within district or across district after finishing Performance
					studentReport.setEnrollmentId(studentReport.getStage1EnrollmentId());
					studentReport.setAttendanceSchoolId(studentReport.getStage1AttSchoolId());
					studentReport.setDistrictId(studentReport.getStage1AttDistrictId());
					
					studentReport.setEnrollmentId2(studentReport.getPrfrmStageEnrollmentId());
					studentReport.setAttendanceSchoolId2(studentReport.getPrfrmStageAttSchoolId());
					studentReport.setDistrictId2(studentReport.getPrfrmStageAttDistrictId());
					
					if(studentReport.getCurrentEnrollmentId() > 0){ 
						studentReport.setEnrollmentId3(studentReport.getCurrentEnrollmentId());
						studentReport.setAttendanceSchoolId3(studentReport.getCurrentAttSchoolId());
						studentReport.setDistrictId3(studentReport.getCurrentAttDistrictId());
					}
				}
			}
				
		}
	}
	
	private Set<Long> getAttendanceSchoolIds(StudentReport studentReport) {
		LinkedHashSet<Long> attSchoolIds = new LinkedHashSet<Long>();
		for (Enrollment enrollment : studentReport.getEnrollments()) {
			if (enrollment.getAttendanceSchoolId() > 0 && !attSchoolIds.contains(enrollment.getAttendanceSchoolId())) {
				attSchoolIds.add(enrollment.getAttendanceSchoolId());
			}
			logger.info("attSchoolIds: " + attSchoolIds);
		}
		return attSchoolIds;
	}
	
	private Set<Long> getAypSchoolIds(StudentReport studentReport) {
		LinkedHashSet<Long> aypSchoolIds = new LinkedHashSet<Long>();
		for (Enrollment enrollment : studentReport.getEnrollments()) {
			logger.info("apySchoolIds: " + aypSchoolIds);
			if (enrollment.getAypSchoolId() > 0 && !aypSchoolIds.contains(enrollment.getAypSchoolId())) {
				aypSchoolIds.add(enrollment.getAypSchoolId());
			}
		}
		return aypSchoolIds;
	}
	
	/**
	 * Returns a map of each schoolId to a list containing: the school org itself
	 * and the district the school belongs to at indexes 0 and 1, respectively
	 * @param schoolIds
	 * @return
	 */
	private Map<Long, List<Organization>> getOrgsForSchools(Collection<Long> schoolIds) {
		Map<Long, List<Organization>> schoolIdToOrgsMap = new HashMap<Long, List<Organization>>();
		for (Long schoolId : schoolIds) {
			Organization school = organizationDao.get(schoolId);
			Organization district = organizationDao.getDistrictBySchoolOrgId(schoolId);
			List<Organization> orgs = new ArrayList<Organization>();
			orgs.add(school);
			orgs.add(district);
			schoolIdToOrgsMap.put(schoolId, orgs);
		}
		return schoolIdToOrgsMap;
	}
	
	private boolean isTransferWithinDistrict(StudentReport studentReport, Map<Long, List<Organization>> orgMap) {
		boolean transferredWithinDistrict = true;
		if (orgMap != null) {
			Set<Long> districtIds = new HashSet<Long>();
			for (Long schoolId : orgMap.keySet()) {
				List<Organization> orgs = orgMap.get(schoolId);
				if (orgs.size() > 1) {
					if (!districtIds.contains(orgs.get(1).getId())) {
						districtIds.add(orgs.get(1).getId());
					}
				}
			}
			transferredWithinDistrict = (districtIds.size() == 1);
		}
		return transferredWithinDistrict;
	}
	
	private List<Enrollment> findStudentEnrollmentsForAypSchool(List<Enrollment> enrollments, Long aypSchoolId) {
		List<Enrollment> enrls = new ArrayList<Enrollment>();
		for (Enrollment e : enrollments) {
			if (e.getAypSchoolId() == aypSchoolId.longValue()) {
				enrls.add(e);
			}
		}
		return enrls;
	}
	
	private List<Enrollment> findStudentEnrollmentsForAttSchool(List<Enrollment> enrollments, Long attSchoolId) {
		List<Enrollment> enrls = new ArrayList<Enrollment>();
		for (Enrollment e : enrollments) {
			if (e.getAypSchoolId() == attSchoolId.longValue()) {
				enrls.add(e);
			}
		}
		return enrls;
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(StudentReport studentReport, String msg) {
		ReportProcessReason reportProcessReason = new ReportProcessReason();
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
		//throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	public Organization getContractingOrganization() {
		return this.contractingOrganization;
	}
	
	public void setContractingOrganization(Organization organization) {
		this.contractingOrganization = organization;
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

	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}

	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}

	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}

	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}
	
	
}