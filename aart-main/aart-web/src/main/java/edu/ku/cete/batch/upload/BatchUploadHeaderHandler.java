package edu.ku.cete.batch.upload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadReason;

public class BatchUploadHeaderHandler implements LineCallbackHandler {
	
	
	@Value("${scoringRecordType}")
	private String scoringRecordType;
	
	private StepExecution stepExecution;
	private Long batchUploadId;

	final static Log logger = LogFactory.getLog(BatchUploadHeaderHandler.class);

	@Override
	@SuppressWarnings("unchecked")
	public void handleLine(String line) {
		logger.debug("Heading Line is - " + line);
		String[] headNames = line.toLowerCase().split(",");
	 	Map<String, FieldSpecification> fieldSpecRecords = (Map<String, FieldSpecification>) stepExecution.getJobExecution().getExecutionContext().get("feildSpecRecords");
	 	String uploadType = (String)stepExecution.getJobExecution().getExecutionContext().get("uploadTypeCode");
		Set<String> fieldSpecHeadings =  fieldSpecRecords.keySet();
		if (CollectionUtils.isEmpty(fieldSpecHeadings)) {
			throwException("No field specifications found for upload type.");
		}
		//Added for f430 to support dynamic column upload
		if (scoringRecordType.equals(uploadType)) {
			Long documentId = (Long)stepExecution.getJobExecution().getExecutionContext().get("documentId");
			Long assessmentId = (Long)stepExecution.getJobExecution().getExecutionContext().get("assessmentProgramId");
			Long userId = (Long)stepExecution.getJobExecution().getExecutionContext().get("userId");
			
			BatchUpload ubRecord = (BatchUpload) stepExecution.getJobExecution().getExecutionContext().get("batchUploadRecord");
			
			if(documentId == null)
				throwException("DocumentId was disturbed/changed. Please download csv again");
			
			if(assessmentId == null || ubRecord.getAssessmentProgramId().longValue() != assessmentId.longValue())
				throwException("Please upload the document under the same assessment program where you have downloaded");
			
			if(userId == null || ubRecord.getCreatedUser().longValue() != userId.longValue())
				throwException("Uploaded document is not downloaded by uploaded user. Please download document for you and then upload");
		}
		
		//map mapped name to domain name 
		List<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < headNames.length; i++) {
			//trim heading from file
			headNames[i] = headNames[i].trim();
			String heading = headNames[i];
			FieldSpecification fieldName = fieldSpecRecords.get(heading);
			if (heading.equalsIgnoreCase("comment")) {
				fieldNames.add("comment");
			} else {
				if (fieldName == null) {
					logger.debug("header \"" + heading + "\" provided in file, but not in specification, ignoring");
					
					// add a dummy column placeholder to prevent problems mapping cells to the correct column
					fieldNames.add("DUMMY_HEADER_PLACEHOLDER_(" + heading + ")");
					
					// who cares about extra columns?
					/*throwException("Heading validation failed as file contains more than expected columns. Column - " +  heading + " is not valid for this upload type");
					break;*/
				} else {
					logger.debug("found header \"" + heading + "\" in uploaded file and matching field specification for uploadType: " + uploadType);
					fieldNames.add(fieldSpecRecords.get(heading).getFieldName());
				}
			}
		}
		
		Set<String> headingsFromFile = new HashSet<String>();
		Collections.addAll(headingsFromFile, headNames);
		Set<String> requiredHeadingsMissingFromFile = new HashSet<String>();
		//Check all fields present in file
		for (String fieldSpecHeading : fieldSpecHeadings) {
			String lowercaseSpecHeading = fieldSpecHeading.toLowerCase();
			FieldSpecification spec = fieldSpecRecords.get(fieldSpecHeading);
			if (!headingsFromFile.contains(lowercaseSpecHeading)) {
				if (spec.isOptional()) {
					logger.debug("column " + lowercaseSpecHeading + " was omitted from an upload of type " + uploadType + ", but is optional, so skipping exception.");
				} else {
					requiredHeadingsMissingFromFile.add(fieldSpecHeading);
				}
			}
		}
		if (!requiredHeadingsMissingFromFile.isEmpty()) {
			throwException("Heading validation failed. File is missing " + requiredHeadingsMissingFromFile);
		}
		
		// remove any headers from the current system data that we didn't find in the file,
		// to avoid errors when it tries to map the columns to POJOs later
		Set<String> tmpHeaders = new HashSet<String>(fieldSpecHeadings);
		for (String fsKey : tmpHeaders) {
			FieldSpecification fs = fieldSpecRecords.get(fsKey);
			if (!fieldNames.contains(fs.getFieldName())) {
				fieldSpecRecords.remove(fsKey);
			}
		}
		
		//moving the line number to the first of the list
		fieldNames.add(0, "linenumber");
		stepExecution.getExecutionContext().put("fieldNames", fieldNames.toArray(new String[fieldNames.size()]));
		logger.debug("Heading validation completed. " + fieldNames);
	}
	
	@SuppressWarnings("unchecked")
	private void throwException(String message){
		logger.debug(message);
		BatchUploadReason buReason = new BatchUploadReason();
		buReason.setBatchUploadId(batchUploadId);
		buReason.setFieldName("Header");
		buReason.setReason(message);
		buReason.setLine("0");
		((CopyOnWriteArrayList<BatchUploadReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(buReason);
		InvalidHeaderException exp = new InvalidHeaderException(message);
		stepExecution.addFailureException(exp);
		stepExecution.setExitStatus(ExitStatus.FAILED);
		throw exp;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchUploadId() {
		return batchUploadId;
	}

	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}
	
	
}
