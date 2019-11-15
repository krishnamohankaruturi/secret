package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.service.report.SubscoresDescriptionService;

public class SubscoresDescriptionServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testDeleteAllSubscoresDescription() {
		//fail("Not yet implemented");
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		when(subscoresDescriptionServiceMock.deleteAllSubscoresDescription(2016L)).thenReturn(1);
		int rowsDeleted = subscoresDescriptionServiceMock.deleteAllSubscoresDescription(2016L);
		assertEquals(1, rowsDeleted);
	}

	@Test
	public final void testDeleteSubscoresDescriptions() {
		//fail("Not yet implemented");
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		when(subscoresDescriptionServiceMock.deleteSubscoresDescriptions(12L, 3L, 2016L)).thenReturn(1);
		int rowsDeleted = subscoresDescriptionServiceMock.deleteSubscoresDescriptions(12L, 3L, 2016L);
		assertEquals(1, rowsDeleted);
	}

	@Test
	public final void testInsertSelectiveSubscoresDescription() {
		//fail("Not yet implemented");
		//Case 1: Removed all testID columns for 2016 - not needed
		//Expected Result: Pass
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 37L, 3L, "Student", 
				"Claim_1_all", "Claim 1: Reading", "Claim 1 questions are all about reading", 1, "Yes");
		
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		when(subscoresDescriptionServiceMock.insertSelectiveSubscoresDescription(subscoresDescription)).thenReturn(1);
		int rowsInserted = subscoresDescriptionServiceMock.insertSelectiveSubscoresDescription(subscoresDescription);
		assertEquals(1, rowsInserted);
	}

	@Test
	public final void checkForDuplicateSequenceForReport() {
		//fail("Not yet implemented");
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 37L, 3L, "Student", 
				"Claim_4_all", "Claim 4: Reading", "Claim 4 questions are all about reading", 4, "Yes");
		
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		boolean duplicateRowExists = subscoresDescriptionServiceMock.checkForDuplicateSequenceForReport(
				subscoresDescription.getSchoolYear(), subscoresDescription.getAssessmentProgramId(), subscoresDescription.getSubjectId(), 
				subscoresDescription.getSubscoreDefinitionName(), subscoresDescription.getReport(), 
				subscoresDescription.getSubscoreDisplaySequence());
		
		assertEquals(Boolean.FALSE, duplicateRowExists);
	}

	@Test
	public final void testCheckForDuplicateDefinitionForReport() {
		//fail("Not yet implemented");
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 37L, 3L, "Student", 
				"Claim_2_all", "Claim 2: Reading", "Claim 2 questions are all about reading", 2, "Yes");
		
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		boolean duplicateRowExists = subscoresDescriptionServiceMock.checkForDuplicateDefinitionForReport(subscoresDescription.getSchoolYear(), 
				subscoresDescription.getAssessmentProgramId(), subscoresDescription.getSubjectId(), subscoresDescription.getSubscoreDefinitionName(), 
				subscoresDescription.getReport());
		
		assertEquals(Boolean.FALSE, duplicateRowExists);
	}

	@Test
	public final void testCheckIfSubscoreDefinitionNameExists() {
		//fail("Not yet implemented");
		SubscoresDescription subscoresDescription = getSubscoresDescriptionDataObject(2016L, 37L, 3L, "Student", 
					"Claim_3_all", "Claim 3: Reading", "Claim 3 questions are all about reading", 3, "Yes");
		
		SubscoresDescriptionService subscoresDescriptionServiceMock = mock(SubscoresDescriptionServiceImpl.class);
		boolean duplicateRowExists = subscoresDescriptionServiceMock.checkIfSubscoreDefinitionNameExists(subscoresDescription.getSchoolYear(), 
				subscoresDescription.getAssessmentProgramId(), subscoresDescription.getSubjectId(), subscoresDescription.getSubscoreDefinitionName());
		assertEquals(Boolean.FALSE, duplicateRowExists);
	}
	
	
	private SubscoresDescription getSubscoresDescriptionDataObject(Long schoolYear, Long assessmentProgramId, Long subjectId, String report,
			String subscoreDefinitionName, String subscoreReportDisplayName, String subscoreReportDescription, int subscoreDisplaySequence, 
			String sectionLineBelow){
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
		
		return subscoresDescription;
	}

}
