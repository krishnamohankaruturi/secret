package edu.ku.cete.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.util.AartBeanToCsvParser;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.web.AssessmentProgramParticipationDTO;

/**
 * Controller class to handle requests from assessmentProgramParticipation.jsp
 * for Organization level monitor student participation numbers (CSV file).
 * @author vittaly
 *
 */
@Controller
public class  AssessmentProgramParticipationController {

	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(AssessmentProgramParticipationController.class);
	
	/**
	 * ASSESSMENT_PROGRAM_PARTICIPATION_JSP jsp value
	 */
	private static final String ASSESSMENT_PROGRAM_PARTICIPATION_JSP = "assessmentProgramParticipation";
	
	/**
	 * assessmentProgramService
	 */
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	/**
	 * fileHeaders
	 */
	@Value("${assessment.program.participation.csv.headers}")
	 private String fileHeaders;
	
	/**
	 * @param model
	 *            {@link Model}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "getAssessmentProgramParticipation.htm", method = RequestMethod.GET)
	public final ModelAndView viewAssessmentProgramParticipation(Model model) {
		 LOGGER.trace("Entering the viewAssessmentProgramParticipation method.");
		return new ModelAndView(ASSESSMENT_PROGRAM_PARTICIPATION_JSP);
	}
	
	/**
	 * Method that handles request from assessmentProgramParticipation.jsp and
	 * responds with a csv file after getting the values from db.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "assessmentProgramParticipation.htm", method = RequestMethod.POST)
	public final ModelAndView downloadFile(final HttpServletRequest request, 
			final HttpServletResponse response) throws IOException {
		LOGGER.trace("Entering the downloadFile method.");
		OutputStream out = null;		
		OutputStream bufferedOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		CSVWriter csvWriter = null;
		List<AssessmentProgramParticipationDTO> assessmentProgramParticipationDTOList = null;
		AartBeanToCsvParser aartBeanToCsvParser = null;
		
		//Get Organization level monitor student participation numbers from db.
		assessmentProgramParticipationDTOList = assessmentProgramService.selectAllAssessmentProgramParticipation();		
		aartBeanToCsvParser = new AartBeanToCsvParser();		
		
		try {
			//Create a csv file for the user to download.
			out= response.getOutputStream();
			response.setContentType(CommonConstants.RESPONSE_CONTENT_TYPE_CSV);
			response.setHeader(CommonConstants.RESPONSE_HEADER_CONTENT_DISPOSITION, 
					CommonConstants.RESPONSE_HEADER_ATTACHMENT_FILENAME_STRING + CommonConstants.RESPONSE_HEADER_ATTACHMENT_FILENAME);
			bufferedOutputStream = new BufferedOutputStream(out);   
			outputStreamWriter = new OutputStreamWriter(bufferedOutputStream); 
			aartBeanToCsvParser.setFileHeaders(fileHeaders);
			csvWriter = aartBeanToCsvParser.generateCsv(assessmentProgramParticipationDTOList, outputStreamWriter);         		
		} catch (IOException e) {
			LOGGER.trace("Exception while generating csv file.");
		} finally {
			csvWriter.close();
			bufferedOutputStream.close();
			outputStreamWriter.close();
		}
		LOGGER.trace("Leaving the downloadFile method.");		
		return null;		
	}
	
}
