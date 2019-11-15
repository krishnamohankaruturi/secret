package edu.ku.cete.batch.upload.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.ObjectError;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.report.domain.BatchUploadReason;

@SuppressWarnings("rawtypes")
public class BatchUploadProcessor extends UploadItemProcessor {
	final static Log logger = LogFactory.getLog(BatchUploadProcessor.class);
	private Long testId;
	private Long documentId;
	private Long reportYear;

	@Value("${scoringRecordType}")
	private String scoringRecordType;
	
	@Value("${permissionRecordType}")
	private String permissionRecordType;

	BatchUploadProcessor() {
		setBeanFieldSetMapper(new BeanWrapperFieldSetMapper());
		getBeanFieldSetMapper().setStrict(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object process(FieldSet fieldSet) throws Exception {
		logger.debug("Started processing batchUpload");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assessmentProgramIdOnUI", getAssessmentProgramId());
		params.put("assessmentProgramCodeOnUI", getAssessmentProgramCode());
		params.put("subjectIdOnUI", getSubjectId());
		params.put("subjectCodeOnUI", getSubjectCode());
		params.put("uploadTypeCode", getUploadTypeCode());
		params.put("batchUploadId", getBatchUploadId());
		params.put("batchUploadRecord", getBatchUploadRecord());
		params.put("uploadedUserId", getUploadedUserId());
		params.put("reportYear", getReportYear());
		params.put("testingProgramIdOnUI", getTestingProgramId());
		params.put("testingProgramNameOnUI", getTestingProgramName());
		params.put("reportCycleOnUI", getReportCycle());
		params.put("createdUser", getCreatedUser());
		
		/**
		 * Prasanth : US16252 : To upload data file( User,Organization,roster,
		 * enrollment etc)
		 */
		if ("FILE_DATA".equals(getUPLOAD_TYPE())) {

			ExecutionContext ec = getStepExecution().getJobExecution().getExecutionContext();

			ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree) ec
					.get("contractingOrganizationTree");
			List<Groups> groups = (List<Groups>) ec.get("groupsList");

			params.put("stateId", getStateId());
			params.put("districtId", getDistrictId());
			params.put("schoolId", getSchoolId());
			params.put("selectedOrgId", getSelectedOrgId());
			params.put("currentContext", ec.get("currentContext"));
			params.put("contractingOrganizationTree", contractingOrganizationTree);
			params.put("currentUser", ec.get("currentUser"));
			params.put("groupsList", groups);
			params.put("testId", testId);
			params.put("documentId", documentId);
			/**
			 * US16533 : To check the user allowed roles
			 */
			params.put("allowedRoles", (List<Groups>) ec.get("allowedRoles"));

			params.put("orderedOrgTypesByLevel", ec.get("orderedOrgTypesByLevel"));
			params.put("validVerifiedParentOrgIdentifier", ec.get("validVerifiedParentOrgIdentifier"));
			params.put("orgTypesMap", ec.get("orgTypesMap"));
			params.put("buildingUniquenessType", ec.get("buildingUniquenessType"));
			params.put("organizationDisplayIdentifiers", ec.get("organizationDisplayIdentifiers"));

			params.put("restriction", ec.get("restriction"));
			params.put("grfUploadType", ec.get("grfUploadType"));
			params.put("assessmentPrograms", ec.get("assessmentPrograms"));
		}
		//Added for F885 UploadPermissions
		if(permissionRecordType.equals(getUploadTypeCode())) {
			ExecutionContext ec = getStepExecution().getJobExecution().getExecutionContext();
			params.put("dynamicRoleNameList", ec.get("dynamicRoleNameList"));
		}

		Map<String, String> mappedFieldNames = (Map<String, String>) getStepExecution().getJobExecution()
				.getExecutionContext().get("mappedFieldNames");
		
		params.put("fieldSpecRecords", (Map<String, FieldSpecification>)
				getStepExecution().getJobExecution().getExecutionContext().get("feildSpecRecords"));

		Map<String, Object> validationResultMap = batchUploadService.validateProcess(getBeanFieldSetMapper(),
				uploadFileValidator, fieldSet, params, mappedFieldNames);
		
		//Added for F671 - GRF grade change scenario
		if(validationResultMap.get("gradeChanged") != null && (boolean)validationResultMap.get("gradeChanged")){
			getStepExecution().getJobExecution().getExecutionContext().put("gradeChangeCount",
					(Integer) getStepExecution().getJobExecution().getExecutionContext().get("gradeChangeCount") + 1);
		}
		
		List<ObjectError> errorList = (List<ObjectError>) validationResultMap.get("errors");
		if (CollectionUtils.isNotEmpty(errorList)) {
			for (ObjectError objErr : errorList) {
				BatchUploadReason batchUploadReasons = new BatchUploadReason();
				batchUploadReasons.setReason(objErr.getDefaultMessage());
				batchUploadReasons.setFieldName((String) objErr.getArguments()[1]);
				batchUploadReasons.setLine((String) objErr.getArguments()[0]);
				((CopyOnWriteArrayList<BatchUploadReason>) getStepExecution().getJobExecution().getExecutionContext()
						.get("jobMessages")).add(batchUploadReasons);
			}
			logger.debug("Stopped Processing as validation failed.");
			throw new SkipBatchException("Skipping this line as validation failed.");

		} else {
			// added during US16966 - To add alert message to upload
			Map<String, Object> validationResultAlertMap = batchUploadService.validateProcessForAlertMessage(
					getBeanFieldSetMapper(), uploadFileValidator, fieldSet, params, mappedFieldNames);
			errorList = (List<ObjectError>) validationResultAlertMap.get("errors");
			if (errorList != null && errorList.size() > 0) {
				for (ObjectError objErr : errorList) {
					BatchUploadReason batchUploadReasons = new BatchUploadReason();
					batchUploadReasons.setReason(objErr.getDefaultMessage());
					batchUploadReasons.setFieldName((String) objErr.getArguments()[1]);
					batchUploadReasons.setLine((String) objErr.getArguments()[0]);
					((CopyOnWriteArrayList<BatchUploadReason>) getStepExecution().getJobExecution()
							.getExecutionContext().get("alertMessages")).add(batchUploadReasons);
				}
				getStepExecution().getJobExecution().getExecutionContext().put("alertCount",
						(Integer) getStepExecution().getJobExecution().getExecutionContext().get("alertCount") + 1);
			}

			logger.debug("completed processing batchUpload");
			// Added for f430-US19339 for scoring upload
			/*if (getUploadTypeCode().equals(scoringRecordType)) {
				return validationResultAlertMap.get("rowDataObject");
			} else {*/  // Commented as part of 570 - Because we are not using alert message service
				return validationResultMap.get("rowDataObject");
			//}
		}
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getReportYear() {
		return reportYear;
	}

	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	
}
