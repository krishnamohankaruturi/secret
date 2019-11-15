package edu.ku.cete.batch.upload.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.ObjectError;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.report.domain.BatchUploadReason;

@SuppressWarnings("rawtypes")
public class XmlBatchUploadProcessor extends UploadItemProcessor {

	final static Log logger = LogFactory.getLog(XmlBatchUploadProcessor.class);

	XmlBatchUploadProcessor() {
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

		/**
		 * Prasanth : US16252 : To upload data file( User,Organization,roster,
		 * enrollment etc)
		 */
		if ("XML_FILE_DATA".equals(getUPLOAD_TYPE())) {

			ExecutionContext ec = getStepExecution().getJobExecution().getExecutionContext();

			ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree) ec.get("contractingOrganizationTree");
			List<Groups> groups = (List<Groups>) ec.get("groupsList");

			params.put("stateId", getStateId());
			params.put("districtId", getDistrictId());
			params.put("schoolId", getSchoolId());
			params.put("selectedOrgId", getSelectedOrgId());
			params.put("currentContext", ec.get("currentContext"));
			params.put("contractingOrganizationTree", contractingOrganizationTree);
			params.put("currentUser", ec.get("currentUser"));
			params.put("groupsList", groups);

			params.put("orderedOrgTypesByLevel", ec.get("orderedOrgTypesByLevel"));
			params.put("validVerifiedParentOrgIdentifier", ec.get("validVerifiedParentOrgIdentifier"));
			params.put("orgTypesMap", ec.get("orgTypesMap"));
			params.put("buildingUniquenessType", ec.get("buildingUniquenessType"));
			params.put("organizationDisplayIdentifiers", ec.get("organizationDisplayIdentifiers"));

			params.put("restriction", ec.get("restriction"));
			params.put("assessmentPrograms", ec.get("assessmentPrograms"));
			params.put("batchUploadRecord", ec.get("batchUploadRecord"));
		}

		Map<String, String> mappedFieldNames = (Map<String, String>) getStepExecution().getJobExecution().getExecutionContext()
				.get("mappedFieldNames");

		Map<String, Object> validationResultMap = batchUploadService.validateProcess(getBeanFieldSetMapper(), uploadFileValidator, fieldSet,
				params, mappedFieldNames);
		List<ObjectError> errorList = (List<ObjectError>) validationResultMap.get("errors");
		if (errorList.size() > 0) {
			for (ObjectError objErr : errorList) {
				BatchUploadReason batchUploadReasons = new BatchUploadReason();
				batchUploadReasons.setReason(objErr.getDefaultMessage());
				batchUploadReasons.setFieldName((String) objErr.getArguments()[1]);
				batchUploadReasons.setLine((String) objErr.getArguments()[0]);
				((CopyOnWriteArrayList<BatchUploadReason>) getStepExecution().getJobExecution().getExecutionContext().get("jobMessages"))
						.add(batchUploadReasons);
			}
			logger.debug("Stopped Processing as validation failed.");
			throw new SkipBatchException("Skipping this line as validation failed.");

		} else {
			logger.debug("completed processing batchUpload");
			return validationResultMap.get("rowDataObject");
		}
	}
}
