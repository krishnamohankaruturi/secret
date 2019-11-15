package edu.ku.cete.batch.dlm.st;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.util.PoolTypeEnum;
import static edu.ku.cete.util.CommonConstants.DELAWARE_ABBR_NAME;
import static edu.ku.cete.util.CommonConstants.DC_ABBR_NAME;

public class StudentTrackerProcessor implements ItemProcessor<StudentTracker, StudentTracker> {
	private Logger logger = LoggerFactory.getLogger(StudentTrackerProcessor.class);
	@Autowired
	private StudentTrackerService trackerService;
	
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private List<ComplexityBand> allBands;
	private StepExecution stepExecution;
	private String assessmentProgram;
	private Long operationalWindowId;
	private User user;
	
	@SuppressWarnings("unchecked")
	@Override
	public StudentTracker process(StudentTracker trackerStudent) throws Exception {
		logger.debug("--> StudentTracker process: "+trackerStudent);
		logger.debug("--> StudentTracker process studentId: "+trackerStudent.getStudentId() + " , OTW = " + operationalWindowId);
		trackerStudent.setOperationalWindowId(operationalWindowId);
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		String orgPoolType = StudentTrackerHelper.getPoolType(contractingOrganization, trackerStudent.getGradeCode(), contentArea.getAbbreviatedName());

		Long courseId = null;
		if((!DELAWARE_ABBR_NAME.equalsIgnoreCase(contractingOrganization.getDisplayIdentifier())
			&& !DC_ABBR_NAME.equalsIgnoreCase(contractingOrganization.getDisplayIdentifier())) 
				&& orgPoolType.equals(PoolTypeEnum.MULTIEEOFC.name())){
			courseId = trackerStudent.getCourseId();
			if((trackerStudent.getExcludeStudentCourseId() == null && courseId != null) ||
			  (trackerStudent.getExcludeStudentCourseId() != null && !trackerStudent.getExcludeStudentCourseId().equals(courseId)) ||
			  (!trackerStudent.isExcludeStudentActiveflag())){
					setIncludeErrorMessage(trackerStudent, reasons, courseId);
					return null;
			}
		}
		
		if(trackerStudent.isExclude()){
			logger.debug("process: "+trackerStudent.getStudentId()+" set to be excluded.");
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
			BatchRegistrationReason excludeReason = new BatchRegistrationReason();
			excludeReason.setStudentId(trackerStudent.getStudentId());
			excludeReason.setReason("Student has been specified in the exclude file with the Operational Test Window:" + operationalWindowId +
					", subject: " + contentArea.getAbbreviatedName() + "(" + contentArea.getId() +  "), course:" + (trackerStudent.getCourseCode() == null ? StringUtils.EMPTY:trackerStudent.getCourseCode() )
					+ "(" +  courseId + ")" );
			reasons.add(excludeReason);
			return null;
		}
		
		logger.info("StudentTracker process studentId: "+trackerStudent.getStudentId() + " , OTW = " + operationalWindowId + ", courseId =" + courseId + ",School Year =  " + contractingOrganization.getCurrentSchoolYear() + ", subject = " + trackerStudent.getContentAreaId());

		StudentTracker studentStatus = trackerService.getTrackerByStudentAndContentArea(trackerStudent.getStudentId(), trackerStudent.getContentAreaId(), 
				operationalWindowId, courseId, contractingOrganization.getCurrentSchoolYear());
		logger.debug("studentStatus...."+trackerStudent.getStudentId()+":"+studentStatus+":"+contractingOrganization.getPoolType());
		boolean process = false;
		if(studentStatus == null) {
			process = true;
		}
		else {
			trackerStudent.setId(studentStatus.getId());
			if (studentStatus.getStatus() == null 
					|| studentStatus.getStatus().equalsIgnoreCase("UNTRACKED")) {
				process = true;
			}
		}
		if(process) {
			StudentTrackerBand recommendation = null;
			if(orgPoolType.equals("SINGLEEE")) {
				recommendation = trackerService.processStudentForSingleEE(trackerStudent, contentArea, allBands, reasons, 
						orgPoolType, contractingOrganization.getCurrentSchoolYear(), operationalWindowId, user);	
			} else if(orgPoolType.equals("MULTIEEOFG") 
					|| orgPoolType.equals("MULTIEEOFC")) {
				recommendation = trackerService.processStudentForMultiEE(trackerStudent, contentArea, allBands, reasons, 
						orgPoolType, operationalWindowId, contractingOrganization, user);
			}
			trackerStudent.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
			trackerStudent.setCourseId(trackerStudent.getCourseId());
			trackerStudent.setRecommendedBand(recommendation);
			logger.debug("process: "+trackerStudent+", recommendation: "+recommendation);
		}
		
		if(trackerStudent.getRecommendedBand() != null) {
			return trackerStudent;
		} else {
			logger.debug("process: "+trackerStudent+" no band recommended or skipped as band exists. Contracting Organization Id : " + contractingOrganization.getId()  + " , Operational Test Window ID : " + operationalWindowId);
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
			return null;
		}
	}
	
	private void setIncludeErrorMessage(StudentTracker	 trackerStudent,  List<BatchRegistrationReason> reasons, Long courseId){
		logger.debug("process: "+trackerStudent.getStudentId() +" is not set to include");
		stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
		BatchRegistrationReason excludeReason = new BatchRegistrationReason();
		excludeReason.setStudentId(trackerStudent.getStudentId());
		String message = "Student is not specified in the include file with the Operational Test Window:" + operationalWindowId +
				", subject: " + contentArea.getAbbreviatedName() + "(" + contentArea.getId() +  ")";
		if(courseId != null){
			message = message + ", course:" + trackerStudent.getCourseCode() + "(" +  courseId + ")";
		}
		excludeReason.setReason(message);
		reasons.add(excludeReason);
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
	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
