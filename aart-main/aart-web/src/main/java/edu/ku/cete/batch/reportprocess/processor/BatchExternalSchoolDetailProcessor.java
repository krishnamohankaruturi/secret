package edu.ku.cete.batch.reportprocess.processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.ExternalSchoolDetailReportDTO;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.report.CpassSchoolReportGenerator;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO;
import edu.ku.cete.report.domain.StudentPrctTopicReportsDTO;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.report.AssessmentTopicService;
import edu.ku.cete.service.report.OrganizationPrctByAssessmentTopicService;
import edu.ku.cete.service.report.StudentPrctByAssessmentTopicService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class BatchExternalSchoolDetailProcessor implements ItemProcessor<Long, Object> {
	
	private final static Log logger = LogFactory .getLog(BatchExternalSchoolDetailProcessor.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long schoolYear;
	private Long stateId;
    private String assessmentCode;
	private String reportCycle;
	private Long testingProgramId;
	private List<AssessmentTopic> topics;
	private StepExecution stepExecution;
	private String assessmentName;
	private Long reportProcessId;
   
	@Autowired
	AssessmentTopicService assessmentTopicService;
	
	@Autowired
	StudentPrctByAssessmentTopicService studentPrctByAssessmentTopicService;
	
	@Autowired
	OrganizationPrctByAssessmentTopicService organizationPrctByAssessmentTopicService;

	@Autowired
	OrganizationService organizationService;
	
	@Autowired 
	CpassSchoolReportGenerator cpassschoolreportgenerator;
	
	@Override
	public OrganizationReportDetails process(Long schoolId) throws Exception {
	 logger.info("Cpass School Detail process started with schoolid - "+schoolId);
	 try{
		 if(topics.size()>0){
			 List<Long> topicsId = AARTCollectionUtil.getIds(topics); 
			 
			 List<StudentPrctTopicReportsDTO> studentDetails = studentPrctByAssessmentTopicService.getStudentDetails(schoolId, reportCycle, testingProgramId, assessmentCode, schoolYear, assessmentProgramId, topicsId);
			 
			 if(studentDetails.size() > 0){		 
				 List<OrganizationPrctTopicReportsDTO> organizationDetails = organizationPrctByAssessmentTopicService.getOrganizationDetails(schoolId, reportCycle, testingProgramId,
			             assessmentCode, schoolYear, assessmentProgramId, topicsId);			 
				 
				 if(organizationDetails.size() > 0){
					 ExternalSchoolDetailReportDTO externalSchoolDetailReportDTO = new ExternalSchoolDetailReportDTO();
					 
					 List<Organization> organizations = organizationService.getAllParents(schoolId);
					 
					 for (Organization organization : organizations) {
						if(organization.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2){
							externalSchoolDetailReportDTO.setStateName(organization.getOrganizationName());
							externalSchoolDetailReportDTO.setStateDisplayIdentifier(organization.getDisplayIdentifier());
							externalSchoolDetailReportDTO.setStateId(organization.getId());
						}else if (organization.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5){
							externalSchoolDetailReportDTO.setDistrictName(organization.getOrganizationName());
							externalSchoolDetailReportDTO.setDistrictDisplayIdentifier(organization.getDisplayIdentifier());
							externalSchoolDetailReportDTO.setDistrictId(organization.getId());
						}					
					}
					 
					 Organization school = organizationService.get(schoolId);
					 externalSchoolDetailReportDTO.setSchoolName(school.getOrganizationName());
					 externalSchoolDetailReportDTO.setSchoolDisplayIdentifier(school.getDisplayIdentifier());
					 externalSchoolDetailReportDTO.setSchoolId(school.getId());
					 externalSchoolDetailReportDTO.setSchoolYear(schoolYear);
					 externalSchoolDetailReportDTO.setTopicList(topics);
					 externalSchoolDetailReportDTO.setGradeName(topics.get(0).getGrade());
					 externalSchoolDetailReportDTO.setStudentList(studentDetails);
					 externalSchoolDetailReportDTO.setOrganizationList(organizationDetails);
					 externalSchoolDetailReportDTO.setAssessmentCode(assessmentName);
					 externalSchoolDetailReportDTO.setSubject(topics.get(0).getSubject());
					 
					 
					//call generator class
					 String path = cpassschoolreportgenerator.generateTableFile(externalSchoolDetailReportDTO);
					 
					 OrganizationReportDetails organizationReportDetails = new OrganizationReportDetails();
					 organizationReportDetails.setDetailedReportPath(path);
					 organizationReportDetails.setOrganizationId(schoolId);
					 organizationReportDetails.setReportType("CPASS_GEN_SD");
					 organizationReportDetails.setAssessmentProgramId(assessmentProgramId);
					 organizationReportDetails.setContentAreaId(topics.get(0).getContentareaId());
					 organizationReportDetails.setGradeId(topics.get(0).getGradeId());
					 organizationReportDetails.setAssessmentCode(assessmentCode);
					 organizationReportDetails.setReportCycle(reportCycle);
					 organizationReportDetails.setTestingProgramId(testingProgramId);
					 organizationReportDetails.setBatchReportProcessId(reportProcessId);
					 organizationReportDetails.setSchoolYear(schoolYear);
					 organizationReportDetails.setSchoolName(school.getOrganizationName());
					 organizationReportDetails.setDistrictName(externalSchoolDetailReportDTO.getDistrictName());
					 organizationReportDetails.setAssessmentName(assessmentName);
					 
					 return organizationReportDetails;
				 }else{
						throw new SkipBatchException("Organizations topic code not available for school ");
				 }
			 }else{
					throw new SkipBatchException("Students topic code not available for school ");
			 }	 
		 }else{
				throw new SkipBatchException("Topic code not available for school ");
		 }
	 }catch(Exception e){
		 writeReason("Report Generation Failed for "+ schoolId+" due to "+e.getMessage());
		 return null;
	 }
  }
	private void writeReason(String msg) {
		ReportProcessReason reportProcessReason = new ReportProcessReason();
		reportProcessReason.setReason(msg);
		reportProcessReason.setReportProcessId(reportProcessId);
		((CopyOnWriteArrayList<ReportProcessReason>) getStepExecution().getExecutionContext().get("stepReasons")).add(reportProcessReason);
		throw new SkipBatchException(msg);
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getAssessmentCode() {
		return assessmentCode;
	}
	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}
	public String getReportCycle() {
		return reportCycle;
	}
	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}
	public Long getTestingProgramId() {
		return testingProgramId;
	}
	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}	
	public List<AssessmentTopic> getTopics() {
		return topics;
	}
	public void setTopics(List<AssessmentTopic> topics) {
		this.topics = topics;
	}
	public Long getReportProcessId() {
		return reportProcessId;
	}
	public void setReportProcessId(Long reportProcessId) {
		this.reportProcessId = reportProcessId;
	}
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
