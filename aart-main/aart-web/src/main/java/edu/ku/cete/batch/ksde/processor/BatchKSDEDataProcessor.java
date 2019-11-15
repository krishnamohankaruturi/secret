package edu.ku.cete.batch.ksde.processor;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.service.KidsEnrollmentService;
import edu.ku.cete.service.enrollment.TASCService;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.SourceTypeEnum;

public class BatchKSDEDataProcessor implements ItemProcessor<KidRecord, Boolean> {
	private StepExecution stepExecution;

	final static Log logger = LogFactory.getLog(BatchKSDEDataProcessor.class);

	private ContractingOrganizationTree contractingOrganizationTree;

	private Map<String, FieldSpecification> fieldSpecificationMap;

	private Long restrictionId;
	
	List<ValidateableRecord> rejectedRecords = new ArrayList<ValidateableRecord>();
	
	private UserDetailImpl userDetails;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	KidsEnrollmentService kidsEnrollmentService;

	@Autowired
	private TASCService tascService;

	@Value("${kidRecord.notProcessedCode}")
	 private String NOT_PROCESSED;
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean process(KidRecord kidRecord) throws Exception {

		if (kidRecord.getRecordType().equalsIgnoreCase("TEST") || kidRecord.getRecordType().equalsIgnoreCase("EXIT")) {
			logger.info("Inside Kids process. Processing for RecordCommonId - " + kidRecord.getRecordCommonId());
			//validateESOLFields(kidRecord);

			if (kidRecord.isDoReject() && kidRecord.getInValidDetails() != null) {
				kidRecord.setReasons(getRejectedReason(kidRecord, messageSource));
				((CopyOnWriteArrayList<String>) stepExecution.getJobExecution().getExecutionContext().get("rejectedReasons")).add(getRejectedReason(kidRecord, messageSource));
				throw new SkipBatchException("Validation failed. Skipping RecordCommonId - " + kidRecord.getRecordCommonId());
			} else {
				logger.info("KID process. Common Validation Passed for RecordCommonId - " + kidRecord.getRecordCommonId());
				kidRecord.getEnrollment().setRestrictionId(restrictionId);
				kidRecord.getEnrollment().setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
				kidRecord.setCurrentContextUserId(userDetails.getUser().getId());
				userDetails.getUser().setCurrentContextUserId(userDetails.getUser().getId());
				
				kidRecord = kidsEnrollmentService.cascadeAddOrUpdateForKids(kidRecord, contractingOrganizationTree, userDetails.getUser());
				if ((kidRecord.isDoReject() && kidRecord.getInValidDetails() != null) || 
						(kidRecord.getStatus()!= null && kidRecord.getStatus().equalsIgnoreCase(NOT_PROCESSED))) {
					
					kidRecord.setReasons(StringUtils.isEmpty(kidRecord.getReasons()) ? 
							(kidRecord.isDoReject() && kidRecord.getInValidDetails() != null) ? getRejectedReason(kidRecord, messageSource) : StringUtils.EMPTY 
									: kidRecord.getReasons()+"\n" + ((kidRecord.isDoReject() && kidRecord.getInValidDetails() != null) 
											? getRejectedReason(kidRecord, messageSource) : StringUtils.EMPTY));
					((CopyOnWriteArrayList<String>) stepExecution.getJobExecution().getExecutionContext()
							.get("rejectedReasons")).add(kidRecord.getReasons());
					throw new SkipBatchException("Validation failed. Skipping RecordCommonId - " + kidRecord.getRecordCommonId());
				}
			}
			logger.info("Completed KIDS process for RecordCommonId - " + kidRecord.getRecordCommonId());
		} else if (kidRecord.getRecordType().equalsIgnoreCase("TASC")) {
			logger.info("Inside TASC process for RecordCommonId - " + kidRecord.getRecordCommonId());
			
			List<String> errorMessages = validateFields(fieldSpecificationMap, kidRecord);
			if (CollectionUtils.isNotEmpty(errorMessages)) {
				kidRecord.setStatus(NOT_PROCESSED);
				((CopyOnWriteArrayList<String>) stepExecution.getJobExecution().getExecutionContext().get("rejectedReasons")).addAll(errorMessages);
				kidRecord.setReasons(getRejectedReason(errorMessages));				
				throw new SkipBatchException("Validation failed. Skipping RecordCommonId - " + kidRecord.getRecordCommonId());
			} else {
				logger.info("TASC process. Common Validation Passed for RecordCommonId - " + kidRecord.getRecordCommonId());				
				userDetails.getUser().setCurrentContextUserId(userDetails.getUser().getId());
				kidRecord.getEnrollment().setRestrictionId(restrictionId);
				kidRecord.getRoster().setRestrictionId(restrictionId);				

				if(kidRecord.getStateSubjectAreaCode() != null && kidRecord.getStateSubjectAreaCode().startsWith("80")){
					List<String> selfContainedSubjectCodes = new ArrayList<String>();
					selfContainedSubjectCodes.add("01");//ELA
					selfContainedSubjectCodes.add("02");//Math
					for(String stateSubjectAreaCode : selfContainedSubjectCodes){
						kidRecord.setStateSubjectAreaCode(stateSubjectAreaCode);
						tascImplCascadeAddOrUpdate(kidRecord, userDetails.getUser());
					}
				}else{
					tascImplCascadeAddOrUpdate(kidRecord, userDetails.getUser());
				}
					
				
			}
			
			logger.info("Completed TASC process for RecordCommonId - " + kidRecord.getRecordCommonId());
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private void tascImplCascadeAddOrUpdate(KidRecord kidRecord, User user) {
		String errorCustomMessage = tascService.cascadeAddOrUpdate(kidRecord, user, contractingOrganizationTree);		
		if (errorCustomMessage != null && errorCustomMessage.length() > 0) {
			((CopyOnWriteArrayList<String>) stepExecution.getJobExecution().getExecutionContext().get("rejectedReasons")).add(errorCustomMessage);	
						
			throw new SkipBatchException("Custom Validation failed. Skipping RecordCommonId - " + kidRecord.getRecordCommonId());
		}
	}

	public List<String> validateFields(Map<String, FieldSpecification> fieldSpecificationMap, KidRecord kidRecord) {
		List<String> errorReason = new ArrayList<String>();
		Map<String, String> fieldMap = getFieldSetMap(fieldSpecificationMap);
		FieldSpecification fieldSpecification = null;		

		if (kidRecord.getStateStudentIdentifier() == null
				|| kidRecord.getStateStudentIdentifier().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("stateStudentIdentifier") + " is required and no value sent.");
		}

		if (kidRecord.getLegalLastName() == null
				|| kidRecord.getLegalLastName().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("legalLastName") + " is required and no value sent.");

		} else if (kidRecord.getLegalLastName().length() > 60) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("legalLastName") + " " + kidRecord.getLegalLastName()
					+ " is not valid.");
		}

		if (kidRecord.getLegalFirstName() == null
				|| kidRecord.getLegalFirstName().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("legalFirstName") + " is required and no value sent.");
		} else if (kidRecord.getLegalFirstName().length() > 60) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("legalFirstName") + " " + kidRecord.getLegalFirstName()
					+ " is not valid.");
		}

		if (kidRecord.getLegalMiddleName() != null
				&& kidRecord.getLegalMiddleName().length() > 60) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("legalMiddleName") + kidRecord.getLegalMiddleName()
					+ " is not valid.");
		}

		if (kidRecord.getStudent().getGender() == null) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("gender") + " is required and no value sent.");
		} else {
			fieldSpecification = fieldSpecificationMap.get(fieldMap.get("gender"));
			if (fieldSpecification.getAllowableValuesArray() != null) {
				if (!Arrays.asList(fieldSpecification.getAllowableValuesArray())
						.contains(kidRecord.getStudent().getGender().toString())) {
					errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier()
							+ "]. Field:" + fieldMap.get("gender") + " "
							+ kidRecord.getStudent().getGender().toString() + " is not valid.");
				}
			}
		}

		if (kidRecord.getCurrentSchoolYear() <= 0) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("currentSchoolYear") + " is required and no value sent.");
		}

		if ((kidRecord.getAttendanceSchoolProgramIdentifier() == null || kidRecord.getAttendanceSchoolProgramIdentifier().isEmpty())
				&& (kidRecord.getAypSchoolIdentifier() == null || kidRecord.getAypSchoolIdentifier().isEmpty())) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ "Attendance or AYP building number is required and no value sent.");
		}

		if (kidRecord.getStateSubjectAreaCode() == null || kidRecord.getStateSubjectAreaCode().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("tascStateSubjectAreaCode") + " is required and no value sent.");
		} else {
			fieldSpecification = fieldSpecificationMap.get(fieldMap.get("tascStateSubjectAreaCode"));
			if (fieldSpecification.getAllowableValuesArray() != null) {
				if (!Arrays.asList(fieldSpecification.getAllowableValuesArray())
						.contains(kidRecord.getStateSubjectAreaCode().toLowerCase())) {
					//errorReason.add("Invalid state subject area code received on TASC.");
					kidRecord.setAllowableSubjectCodes(Arrays.toString(fieldSpecification.getAllowableValuesArray()));
					kidRecord.setInvalidSubjectAreaCode(true);
				}
			}
		}

		if (kidRecord.getCourseStatus() == null) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("courseStatus") + " is required and no value sent.");
		} else {
			fieldSpecification = fieldSpecificationMap.get(fieldMap.get("courseStatus"));
			if (fieldSpecification.getAllowableValuesArray() != null) {
				if (!Arrays.asList(fieldSpecification.getAllowableValuesArray())
						.contains(kidRecord.getCourseStatus().toString())) {
					errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier()
							+ "]. Field:" + fieldMap.get("courseStatus") + " " + kidRecord.getCourseStatus()
							+ " is not valid.");
				}
			}
		}

		if (kidRecord.getTascEducatorIdentifier() == null || kidRecord.getTascEducatorIdentifier().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("tascEducatorIdentifier") + " is required and no value sent.");
		}

		if (kidRecord.getTeacherLastName() == null || kidRecord.getTeacherLastName().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("teacherLastName") + " is required and no value sent.");
		}

		if (kidRecord.getTeacherFirstName() == null || kidRecord.getTeacherFirstName().isEmpty()) {
			errorReason.add("Error for student:[" + kidRecord.getStateStudentIdentifier() + "]. Field:"
					+ fieldMap.get("teacherFirstName") + " is required and no value sent.");
		}
		return errorReason;

	}

	public Map<String, String> getFieldSetMap(Map<String, FieldSpecification> fieldSpecificationMap) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		Set<String> feildSpecificationMappedNamekeys = fieldSpecificationMap.keySet();
		for (String mappedName : feildSpecificationMappedNamekeys) {
			FieldSpecification fieldSpecification = fieldSpecificationMap.get(mappedName);
			fieldMap.put(fieldSpecification.getFieldName(), mappedName.toLowerCase());
		}
		return fieldMap;
	}


	public static final String getRejectedReason(ValidateableRecord rejectedRecord, MessageSource messageSource) {
		StringWriter stringWriter = new StringWriter();
		if (rejectedRecord != null) {
			try {
				stringWriter.append(System.getProperty("line.separator"));
				for (String reasonColumn : rejectedRecord.getRejectedReason(messageSource)) {
					stringWriter.append(reasonColumn);
					stringWriter.append("\t");
				}
				stringWriter.close();
			} catch (IOException e) {
				logger.error("Error in writing to string", e);
			} catch (Exception e) {
				// if file path is null or filename is null it will come here.
				logger.error("Unknown Error in writing to string", e);
			}
		}
		return stringWriter.toString().trim();
	}

	public static final String getRejectedReason(List<String> rejectedReasons) {
		StringWriter stringWriter = new StringWriter();
		if (rejectedReasons != null) {
			try {
				stringWriter.append(System.getProperty("line.separator"));
				for (String reason : rejectedReasons) {
					stringWriter.append(reason);
					stringWriter.append("\t");
				}
				stringWriter.close();
			} catch (IOException e) {
				logger.error("Error in writing to string", e);
			} catch (Exception e) {
				// if file path is null or filename is null it will come here.
				logger.error("Unknown Error in writing to string", e);
			}
		}
		return stringWriter.toString().trim();
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public ContractingOrganizationTree getContractingOrganizationTree() {
		return contractingOrganizationTree;
	}

	public void setContractingOrganizationTree(ContractingOrganizationTree contractingOrganizationTree) {
		this.contractingOrganizationTree = contractingOrganizationTree;
	}

	public Long getRestrictionId() {
		return restrictionId;
	}

	public void setRestrictionId(Long restrictionId) {
		this.restrictionId = restrictionId;
	}

	public Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}

	public void setFieldSpecificationMap(Map<String, FieldSpecification> fieldSpecificationMap) {
		this.fieldSpecificationMap = fieldSpecificationMap;
	}

	public UserDetailImpl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetailImpl userDetails) {
		this.userDetails = userDetails;
	}
}
