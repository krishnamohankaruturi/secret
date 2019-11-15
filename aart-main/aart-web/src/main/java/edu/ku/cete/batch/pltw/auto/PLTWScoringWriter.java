package edu.ku.cete.batch.pltw.auto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.api.scoring.ClusterScoreObject;
import edu.ku.cete.domain.api.scoring.ScoringAPIObject;
import edu.ku.cete.domain.api.scoring.ScoringAPIResponse_PLTW;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.report.domain.OutgoingRequest;
import edu.ku.cete.service.OutgoingRequestService;
import edu.ku.cete.service.impl.api.APIHash;

public class PLTWScoringWriter implements ItemWriter<ScoringAPIObject> {
	
	private final static Log logger = LogFactory.getLog(PLTWScoringWriter.class);
	
	private static final List<Integer> SUCCESS_CODES = Arrays.asList(HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_ACCEPTED);
	
	private Long batchRegistrationId;
	private String scoringAPIURL;
	private String scoringAPIKey;
	private boolean validateEnrollmentsHeader;
	private StepExecution stepExecution;
	
	@Autowired
	private OutgoingRequestService outgoingRequestService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends ScoringAPIObject> objects) throws Exception {
		if (CollectionUtils.isNotEmpty(objects) && StringUtils.isNotBlank(scoringAPIURL)) {
			final int size = objects.size();
			ObjectMapper objectMapper = new ObjectMapper();
			
			final String method = RequestMethod.POST.toString();
			final String body = objectMapper.writeValueAsString(objects);
			final String hash = APIHash.SHA256.checksumToHex(body, "UTF-8");
			
			HttpURLConnection connection = (HttpURLConnection) new URL(scoringAPIURL).openConnection();
			// important to call setDoOutput() first before the other methods, because they will apparently connect implicitly?
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", MediaType.APPLICATION_JSON_VALUE);
			connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			connection.setRequestProperty("X-Api-Key", scoringAPIKey);
			connection.setRequestProperty("X-Checksum", StringUtils.lowerCase(hash));
			connection.setRequestProperty("X-Total-Count", String.valueOf(size));
			if (!validateEnrollmentsHeader) {
				connection.setRequestProperty("X-Validate-Enrollments", String.valueOf(validateEnrollmentsHeader).toUpperCase());
			}
			String headers = connection.getRequestProperties().toString();
			connection.setRequestMethod(method);
			connection.connect();
			
			OutgoingRequest outgoingRequest = new OutgoingRequest();
			outgoingRequest.setUrl(scoringAPIURL);
			outgoingRequest.setMethod(method);
			outgoingRequest.setHeaders(headers);
			outgoingRequest.setBody(body);
			outgoingRequest.setSentTime(new Date());
			outgoingRequestService.insert(outgoingRequest);
			
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			
			// just some checking
			for (ScoringAPIObject obj : objects) {
				BigDecimal computedTotalScore = new BigDecimal(0);
				BigDecimal objTotalScore = obj.getTotalRawScore();
				
				for (ClusterScoreObject cObj : obj.getClusters()) {
					computedTotalScore = computedTotalScore.add(cObj.getRawScoreEarned());
				}
				
				// if they're the not the same, log it
				if (objTotalScore.compareTo(computedTotalScore) != 0) {
					logger.warn("Outgoing request ID " + outgoingRequest.getId() + ", studentId " + obj.getStudentId() +
							", subject " + obj.getContentAreaAbbreviatedName() +
							", stage1 studentstestsid " + obj.getStage1StudentsTestsId() +
							", stage2 studentstestsid " + obj.getStage2StudentsTestsId() +
							" raw score from the database (" + objTotalScore +
							") did not match the computed one in the writer process (" + computedTotalScore + ")");
				}
			}
			
			int responseCode = connection.getResponseCode();
			
			// HttpURLConnection doesn't use the same stream when a 200-399 is encountered versus 400+,
			// so we have to adjust to avoid an IOException
			InputStream requestResponseStream =
					responseCode < HttpURLConnection.HTTP_BAD_REQUEST ?
					connection.getInputStream() : connection.getErrorStream();
			
			/* We expect the response to be something similar to the following:
				{
					"batchId": "d99406d8-1916-4fb5-82bc-7b480fa860dc",
					"message": "Received and queued 100 test results for asynchronous processing.",
					"status": 202,
					"success": true
				}
			*/
			String responseString = null;
			try {
				responseString = IOUtils.toString(requestResponseStream, "UTF-8");
			} catch (IOException ioe) {
				logger.error("Error reading request response", ioe);
			}
			
			outgoingRequest.setResponseTime(new Date());
			outgoingRequest.setResponseCode(responseCode);
			outgoingRequest.setResponse(responseString);
			outgoingRequestService.updateByPrimaryKey(outgoingRequest);
			
			try {
				ScoringAPIResponse_PLTW responseObject = objectMapper.readValue(responseString, ScoringAPIResponse_PLTW.class);
				
				int objectResponseCode = responseObject.getStatus();
				
				if (objectResponseCode != responseCode) {
					String message = "outgoing request ID " + outgoingRequest.getId() + " response codes do not match--request response code is " + responseCode +
							", but the code provided in the response body is " + objectResponseCode;
					logger.warn(message);
					writeReason(message);
				}
				
				if (!SUCCESS_CODES.contains(responseCode)) {
					String message = "outgoing request ID " + outgoingRequest.getId() + " generated an unsuccessful response (none of " + SUCCESS_CODES + ")";
					logger.warn(message);
					writeReason(message);
				}
			} catch (Exception e) {
				logger.error("error:", e);
			}
			
			for (ScoringAPIObject obj : objects) {
				if (obj.getReprocessId() != null) {
					logger.info("reprocessId " + obj.getReprocessId() + " added");
					((CopyOnWriteArrayList<Long>) stepExecution.getJobExecution().getExecutionContext().get("reprocessIds")).add(obj.getReprocessId());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		logger.debug(msg);

		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext()
				.get("jobMessages")).add(brReason);
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public String getScoringAPIURL() {
		return scoringAPIURL;
	}

	public void setScoringAPIURL(String scoringAPIURL) {
		this.scoringAPIURL = scoringAPIURL;
	}

	public String getScoringAPIKey() {
		return scoringAPIKey;
	}

	public void setScoringAPIKey(String scoringAPIKey) {
		this.scoringAPIKey = scoringAPIKey;
	}

	public boolean isValidateEnrollmentsHeader() {
		return validateEnrollmentsHeader;
	}

	public void setValidateEnrollmentsHeader(boolean validateEnrollmentsHeader) {
		this.validateEnrollmentsHeader = validateEnrollmentsHeader;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
