package edu.ku.cete.batch.upload.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.upload.validator.BatchUploadValidator;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.report.BatchUploadService;

/**
 * @author rkcherukuri
 *
 */
public abstract class UploadItemProcessor implements ItemProcessor<FieldSet, Object> {
	final static Log logger = LogFactory.getLog(UploadItemProcessor.class);
	
	private StepExecution stepExecution;
	private BeanWrapperFieldSetMapper<?> beanFieldSetMapper;
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long subjectId;
	private String subjectCode;
	private String uploadTypeCode;
	private Long batchUploadId;
	private String UPLOAD_TYPE;
	private Long stateId;
	private Long districtId;
	private Long schoolId;
	private Long selectedOrgId;
	private BatchUpload batchUploadRecord;
	private Long uploadedUserId;
	private Long testingProgramId;
	private String testingProgramName;
	private String reportCycle;
	private Long createdUser;
	
	protected BatchUploadValidator uploadFileValidator;
	
	@Autowired
	protected BatchUploadService batchUploadService;
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public BatchUploadValidator getUploadFileValidator() {
		return uploadFileValidator;
	}

	public void setUploadFileValidator(BatchUploadValidator uploadFileValidator) {
		this.uploadFileValidator = uploadFileValidator;
	}

	public BeanWrapperFieldSetMapper<?> getBeanFieldSetMapper() {
		return beanFieldSetMapper;
	}

	public void setBeanFieldSetMapper(BeanWrapperFieldSetMapper<?> beanFieldSetMapper) {
		this.beanFieldSetMapper = beanFieldSetMapper;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getUploadTypeCode() {
		return uploadTypeCode;
	}

	public void setUploadTypeCode(String uploadTypeCode) {
		this.uploadTypeCode = uploadTypeCode;
	}

	public Long getBatchUploadId() {
		return batchUploadId;
	}

	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}

	public String getUPLOAD_TYPE() {
		return UPLOAD_TYPE;
	}

	public void setUPLOAD_TYPE(String uPLOAD_TYPE) {
		UPLOAD_TYPE = uPLOAD_TYPE;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getSelectedOrgId() {
		return selectedOrgId;
	}

	public void setSelectedOrgId(Long selectedOrgId) {
		this.selectedOrgId = selectedOrgId;
	}

	public BatchUpload getBatchUploadRecord() {
		return batchUploadRecord;
	}

	public void setBatchUploadRecord(BatchUpload batchUploadRecord) {
		this.batchUploadRecord = batchUploadRecord;
	}

	public BatchUploadService getBatchUploadService() {
		return batchUploadService;
	}

	public void setBatchUploadService(BatchUploadService batchUploadService) {
		this.batchUploadService = batchUploadService;
	}

	public Long getUploadedUserId() {
		return uploadedUserId;
	}

	public void setUploadedUserId(Long uploadedUserId) {
		this.uploadedUserId = uploadedUserId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public String getTestingProgramName() {
		return testingProgramName;
	}

	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}
	
}
