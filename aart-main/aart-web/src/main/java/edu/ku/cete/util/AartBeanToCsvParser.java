package edu.ku.cete.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.common.AartBeanToCsv;
import edu.ku.cete.web.AssessmentProgramParticipationDTO;

/**
 * Parser class that creates csv file from java beans or list of beans.
 * @author vittaly
 *
 */
public class AartBeanToCsvParser {

	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(AartBeanToCsvParser.class);
			 
	/**
	 * fileHeaders
	 */
	private String fileHeaders;	 	
	
	/**
	 * @return
	 */
	public String getFileHeaders() {
		return fileHeaders;
	}

	/**
	 * @param fileHeaders
	 */
	public void setFileHeaders(String fileHeaders) {
		this.fileHeaders = fileHeaders;
	}

	/**
	 * @param AartBeanToCsvList
	 * @param outputStreamWriter
	 * @return
	 * @throws IOException
	 */
	public CSVWriter generateCsv(List<? extends AartBeanToCsv> AartBeanToCsvList,
			OutputStreamWriter outputStreamWriter)  throws IOException{
		LOGGER.trace("Entering the generateCsv method.");		
		String objAsString = null;
		CSVWriter csvWriter = null;
		AssessmentProgramParticipationDTO assessmentProgramParticipationDTO = null;

		csvWriter = new CSVWriter(outputStreamWriter);
		csvWriter.writeNext(fileHeaders.split(CommonConstants.COMMA_DELIM));
		for (AartBeanToCsv aartBeanToCsv : AartBeanToCsvList) {
			assessmentProgramParticipationDTO = (AssessmentProgramParticipationDTO) aartBeanToCsv;
			objAsString = assessmentProgramParticipationDTO.toString();
			csvWriter.writeNext(objAsString.split(CommonConstants.ASSESSMENT_PROGRAM_PARTICIPATION_DELIM));
		}				
		LOGGER.trace("Leaving the generateCsv method.");
		return csvWriter;
	}
	
}
