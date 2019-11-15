/**
 * 
 */
package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.report.SubscoresDescriptionService;

/**
 * @author n466k239
 *
 */
public class SubscoresDescriptionCustomValidationServiceImplTest {

	final static Log logger = LogFactory.getLog(SubscoresDescriptionCustomValidationServiceImplTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSchoolYear() {
		SubscoresDescription subscoresDescription1 = getSubscoresDescriptionDataObject(2015L, 37L, 3L, "Student", "Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		SubscoresDescription subscoresDescription2 = getSubscoresDescriptionDataObject(2016L, 37L, 3L, "Student", "Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		assertNotEquals("All school years in the file are not the same school year.", subscoresDescription1.getSchoolYear(), subscoresDescription2.getSchoolYear());
	}
	
	@Test
	public void testSchoolYearNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(null, 37L, 3L, "Student","Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		assertNull("Value not specified for field School_Year", subscoresDescription.getSchoolYear());
		
	}
	
	@Test
	public void testAssessmentProgramNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, null, 3L, "Student", 
				"Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		assertNotNull("Value not specified for field Assessment_Program", subscoresDescription.getAssessmentProgram());
		
	}
	
	@Test
	public void testSubjectNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 37L, null, "Student", 
				"Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", "");//levelDescription.setSubject(null);
		assertNull("Value not specified for field Subject", subscoresDescription.getSubjectId());
		
	}
	
	
	@Test
	public void validateAssessmentProgram() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 3L, "Student", "Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		String assessmentProgramCodeOnUI = "AMP";
		assertEquals("Assessment program is invalid", assessmentProgramCodeOnUI, subscoresDescription.getAssessmentProgram());
	}
		
	@Test
	public void validateSubject() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 440L, "Student", "Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		String subjectIdOnUI = "ELA";
		assertEquals("Subject is invalid", subjectIdOnUI, subscoresDescription.getSubject());
	}
	
	@Test
	public void testSectionLineBelowNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 440L, "Student", "Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, null, null);
		assertNull("Value not specified for field Section_Line_Below", subscoresDescription.getSectionLineBelow());
	}
	
	@Test
	public void testSubscoreDefinitionNameNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 440L, "Student", null, "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes", null);
		assertNull("Value not specified for field SubscoreDefinitionName", subscoresDescription.getSubscoreDefinitionName());
	}
	
	@Test
	public void testSubscoreReportDisplayNameNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 440L, "Student", "Claim_3_all", null, "Claim 3 questions are all about reading", 3, "Yes", null);
		assertNull("Value not specified for field SubscoreReportDisplayName", subscoresDescription.getSubscoreReportDisplayName());
	}
	
	@Test
	public void testSubscoreReportDescriptionNotNull() {
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 12L, 3L, "Student", "Claim_3_all", "Claim 3: Reading", null, 3, "Yes", null);
		assertNull("Value not specified for field SubscoreReportDescription", subscoresDescription.getSubscoreReportDescription());
	}
	
	@Test
	public void testSubscoreDisplaySequenceNotNull() {
		SubscoresDescription subscoresDescription = new SubscoresDescription();

		subscoresDescription.setLineNumber("2");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(12L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_3_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 3: Reading");
		subscoresDescription.setSubscoreReportDescription("Claim 3 questions are all about reading");
		subscoresDescription.setSubscoreDisplaySequence(null);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded("Yes");
		assertNull("Value not specified for field Section_Line_Below", subscoresDescription.getSubscoreDisplaySequence());
	}
	
	private SubscoresDescription getSubscoresDescriptionDataObject(Long schoolYear, Long assessmentProgramId, Long subjectId, String report,
			String subscoreDefinitionName, String subscoreReportDisplayName, String subscoreReportDescription, int subscoreDisplaySequence, 
			String sectionLineBelow, String indentNeeded){
		SubscoresDescription subscoresDescription = new SubscoresDescription();
		
		subscoresDescription.setLineNumber("2");
		subscoresDescription.setSchoolYear(schoolYear);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(assessmentProgramId);
		subscoresDescription.setSubjectId(subjectId);
		subscoresDescription.setSubject("ELA");
		
		subscoresDescription.setReport(report);
		subscoresDescription.setSubscoreDefinitionName(subscoreDefinitionName);
		subscoresDescription.setSubscoreReportDisplayName(subscoreReportDisplayName);
		subscoresDescription.setSubscoreReportDescription(subscoreReportDescription);
		subscoresDescription.setSubscoreDisplaySequence(subscoreDisplaySequence);
		subscoresDescription.setSectionLineBelow(sectionLineBelow);
		subscoresDescription.setIndentNeeded(indentNeeded);
		
		return subscoresDescription;
	}
	
	
	@Test
	public void integrationTestCases() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "AMP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 37L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<SubscoresDescription> subscoresDescriptionList =  new ArrayList<SubscoresDescription>();
		
		
		SubscoresDescription subscoresDescription = new SubscoresDescription();
		//Valid - watch sectionline -yES
		subscoresDescription.setLineNumber("2");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_1_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 1: Reading");
		subscoresDescription.setSubscoreReportDescription("Claim 1 questions are all about reading");
		subscoresDescription.setSubscoreDisplaySequence(1);
		subscoresDescription.setSectionLineBelow("yES");
		subscoresDescription.setIndentNeeded("Yes");
		subscoresDescriptionList.add(subscoresDescription);
		
		//duplicate subscoredefname
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("3");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_2_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 2: Writing");
		subscoresDescription.setSubscoreReportDescription("Claim 2 is very different.");
		subscoresDescription.setSubscoreDisplaySequence(4);
		subscoresDescription.setSectionLineBelow("No");
		subscoresDescription.setIndentNeeded(null);
		subscoresDescriptionList.add(subscoresDescription);
		
		//Valid case
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("4");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_3_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 3: Listening");
		subscoresDescription.setSubscoreReportDescription("Claim 3  questions are all about listening.");
		subscoresDescription.setSubscoreDisplaySequence(2);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded("");
		subscoresDescriptionList.add(subscoresDescription);
		
		//duplicate sequence
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("5");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_1_Rpt_Group_1");
		subscoresDescription.setSubscoreReportDisplayName("Claim 1: Reading Literary Texts");
		subscoresDescription.setSubscoreReportDescription("Claim 1 Literary Text is reading passages that are fiction");
		subscoresDescription.setSubscoreDisplaySequence(2);
		subscoresDescription.setSectionLineBelow("No");
		subscoresDescription.setIndentNeeded("yES");
		subscoresDescriptionList.add(subscoresDescription);
		
		//Valid case
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("6");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_1_Rpt_Group_2");
		subscoresDescription.setSubscoreReportDisplayName("Claim 2: Reading Literary Texts");
		subscoresDescription.setSubscoreReportDescription("Claim 2 Literary Text is reading passages that are fiction");
		subscoresDescription.setSubscoreDisplaySequence(3);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded("No");
		subscoresDescriptionList.add(subscoresDescription);
	
		//Invalid section_line_below
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("7");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("School");
		subscoresDescription.setSubscoreDefinitionName("Claim_1_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 1: Reading");
		subscoresDescription.setSubscoreReportDescription("Claim 1 questions are all about reading");
		subscoresDescription.setSubscoreDisplaySequence(1);
		subscoresDescription.setSectionLineBelow("asdf");
		subscoresDescription.setIndentNeeded("Yes");
		subscoresDescriptionList.add(subscoresDescription);

		//subscore definition name does not exist
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("8");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("School");
		subscoresDescription.setSubscoreDefinitionName("asdfsdf");
		subscoresDescription.setSubscoreReportDisplayName("Claim 2: Writing");
		subscoresDescription.setSubscoreReportDescription("Claim 2 This is writing writing writing");
		subscoresDescription.setSubscoreDisplaySequence(2);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded("asdf");
		subscoresDescriptionList.add(subscoresDescription);

		//Null section number
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("9");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("School");
		subscoresDescription.setSubscoreDefinitionName("Claim_3_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 3: Listening");
		subscoresDescription.setSubscoreReportDescription("Claim 3 questions are all about listening.");
		subscoresDescription.setSubscoreDisplaySequence(3);
		subscoresDescription.setSectionLineBelow(null);
		subscoresDescription.setIndentNeeded(null);
		subscoresDescriptionList.add(subscoresDescription);

		//Wrong subject
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("10");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		//subscoresDescription.setSubjectId(440L);
		subscoresDescription.setSubject("M");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_1_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 1: Reading");
		subscoresDescription.setSubscoreReportDescription("Claim 1 questions are all about reading etc etc etc");
		subscoresDescription.setSubscoreDisplaySequence(1);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded(null);
		subscoresDescriptionList.add(subscoresDescription);

		//wrong year
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("11");
		subscoresDescription.setSchoolYear(2015L);
		subscoresDescription.setAssessmentProgram("AMP");
		subscoresDescription.setAssessmentProgramId(37L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_2_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 2: Writing");
		subscoresDescription.setSubscoreReportDescription("Claim 2 is very different");
		subscoresDescription.setSubscoreDisplaySequence(1);
		subscoresDescription.setSectionLineBelow("No");
		subscoresDescription.setIndentNeeded(null);
		subscoresDescriptionList.add(subscoresDescription);

		//wrong assessment program for AMP
		subscoresDescription = new SubscoresDescription();
		subscoresDescription.setLineNumber("12");
		subscoresDescription.setSchoolYear(2016L);
		subscoresDescription.setAssessmentProgram("KAP");
		//subscoresDescription.setAssessmentProgramId(12L);
		subscoresDescription.setSubjectId(3L);
		subscoresDescription.setSubject("ELA");
		subscoresDescription.setReport("Student");
		subscoresDescription.setSubscoreDefinitionName("Claim_3_all");
		subscoresDescription.setSubscoreReportDisplayName("Claim 3: Listening");
		subscoresDescription.setSubscoreReportDescription("Claim 3 questions are all about listening.");
		subscoresDescription.setSubscoreDisplaySequence(3);
		subscoresDescription.setSectionLineBelow("Yes");
		subscoresDescription.setIndentNeeded(null);
		subscoresDescriptionList.add(subscoresDescription);
		
		
	}
	
	private void testCustomValidation(Map<String, Object> params, List<SubscoresDescription> subscoresDescriptionList) {
		//fail("Not yet implemented"); // TODO
		logger.debug("Started custom validation for Subscores Description and Usage Batch Upload");
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		
		for(SubscoresDescription subscoresDescription : subscoresDescriptionList){
			
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = subscoresDescription.getLineNumber();
		boolean validationPassed = true;
		
		if(!subscoresDescription.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationPassed = false;
		}else{
			//validate subject
			if(!subscoresDescription.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg = "Subject is invalid.";
				logger.debug(errMsg);
				validationPassed = false;
			}
			else
			{
				boolean subscoreDefinitionNameExists = subscoresDescriptionServiceMock.checkIfSubscoreDefinitionNameExists(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getSubscoreDefinitionName());
				if(!subscoreDefinitionNameExists){
					String errMsg="Subscore definition name ["+subscoresDescription.getSubscoreDefinitionName()+"] does not exist for Assessment Program, Subject";
					logger.debug(errMsg);
					validationPassed = false;
				}
				else
				{
					boolean duplicateDefinitionName = subscoresDescriptionServiceMock.checkForDuplicateDefinitionForReport(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getReport(), subscoresDescription.getSubscoreDefinitionName());
					if(duplicateDefinitionName){
						String errMsg="More than 1 Subscore definition names for the report ["+subscoresDescription.getReport()+"]" +lineNumber;
						logger.debug(errMsg);
						validationPassed = false;
					}
					
					boolean duplicateSequenceForReport = subscoresDescriptionServiceMock.checkForDuplicateSequenceForReport(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getReport(), subscoresDescription.getSubscoreDefinitionName(), subscoresDescription.getSubscoreDisplaySequence());
					if(duplicateSequenceForReport){
						String errMsg="More than 1 Subscore definition names have the same value for display sequence for report ["+subscoresDescription.getReport()+"] "+lineNumber ;
						logger.debug(errMsg);
						validationPassed = false;
					}
					//This is actually covered in common validations. Adding it here also for extra validation
					if(subscoresDescription.getSectionLineBelow()==null || subscoresDescription.getSectionLineBelow().isEmpty())
					{
						String errMsg = "Section_Line_Below is empty."+lineNumber;
						logger.debug(errMsg);
						validationPassed = false;
					}
				}
			}
		}
		
		if(validationPassed){ 
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			subscoresDescription.setAssessmentProgramId(assessmentProgramId); 
			subscoresDescription.setSubjectId(subjectId);
			//Although the CSV file has Yes/No values, the database must store boolean true/false values
			if ("yes".contains(subscoresDescription.getSectionLineBelow().trim().toLowerCase())) {
				subscoresDescription.setSectionLineBelowFlag(Boolean.TRUE);
			}
			else if ("no".contains(subscoresDescription.getSectionLineBelow().trim().toLowerCase())) {
				subscoresDescription.setSectionLineBelowFlag(Boolean.FALSE);
			}
			
			
			//Although the CSV file has Yes/No values, the database must store boolean true/false values
			if(subscoresDescription.getIndentNeeded()==null || subscoresDescription.getIndentNeeded().isEmpty())
			{
				subscoresDescription.setIndentNeededFlag(Boolean.FALSE);
			}
			else if ("yes".contains(subscoresDescription.getIndentNeeded().trim().toLowerCase())) {
				subscoresDescription.setIndentNeededFlag(Boolean.TRUE);
			}
			else if ("no".contains(subscoresDescription.getIndentNeeded().trim().toLowerCase())) {
				subscoresDescription.setIndentNeededFlag(Boolean.FALSE);
			}
			logger.debug("Completed validation completed.");
	
		}
		}
	
	}
	
	
	

}
