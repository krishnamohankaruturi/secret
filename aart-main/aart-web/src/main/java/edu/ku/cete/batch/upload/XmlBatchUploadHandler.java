/**
 * 
 */
package edu.ku.cete.batch.upload;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;

import edu.ku.cete.batch.upload.reader.ItemCallbackHandler;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.report.domain.BatchUploadReason;

/**
 * @author Rajendra Kumar Cherukuri
 *
 */
public class XmlBatchUploadHandler implements ItemCallbackHandler {

	final static Log logger = LogFactory.getLog(XmlBatchUploadHandler.class);
	private StepExecution stepExecution;
	private Long batchUploadId;

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


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.batch.upload.writer.ItemCallbackHandler#handleItem(java.lang
	 * .String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> processHeaders() {
		logger.debug("Processing Headers");
		Map<String, FieldSpecification> xPathMappings = (Map<String, FieldSpecification>) stepExecution.getJobExecution()
				.getExecutionContext().get("feildSpecRecords");
		Set<String> xmlXpathsKeys = xPathMappings.keySet();
		Set<String> xmlXpaths = new HashSet<String>();
		Map<String, String> mapping = new HashMap<String, String>();
		if (CollectionUtils.isEmpty(xmlXpathsKeys)) {
			throwException("No field specifications found for upload type.");
		}
		// map mapped name to domain name
		Set<String> domainFieldNames = new HashSet<String>();
		for (String fieldSpecHeading : xmlXpathsKeys) {
			FieldSpecification domainFieldName = xPathMappings.get(fieldSpecHeading);
			// Check all fields present in file. Does it required?
			if (domainFieldName == null) {
				throwException("Heading validation failed as file contains more than expected columns. Column - " + fieldSpecHeading
						+ " is not valid for this upload type");
				break;
			} else {
				domainFieldNames.add(domainFieldName.getFieldName());
				xmlXpaths.add(domainFieldName.getMappedName());
				mapping.put(domainFieldName.getFieldName(), domainFieldName.getMappedName());
			}
		}
		domainFieldNames.add("linenumber");
		logger.debug("Processing Headers completed.");
		return mapping;
	}

	@SuppressWarnings("unchecked")
	private void throwException(String message) {
		logger.error(message);
		BatchUploadReason buReason = new BatchUploadReason();
		buReason.setBatchUploadId(batchUploadId);
		buReason.setFieldName("Header");
		buReason.setReason(message);
		((CopyOnWriteArrayList<BatchUploadReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(buReason);
		throw new RuntimeException(message);
	}
}
