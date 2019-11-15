package edu.ku.cete.service.report;

import edu.ku.cete.domain.report.SubscoresDescription;



public interface SubscoresDescriptionService {

	Integer deleteAllSubscoresDescription(Long schoolYear);
	
	Integer deleteSubscoresDescriptions(Long assessmentProgramId, Long contentAreaId, Long schoolYear);
	
	Integer insertSelectiveSubscoresDescription(SubscoresDescription subscoresDescription);
	
	boolean checkForDuplicateSequenceForReport(Long schoolYear, Long assessmentProgramId, Long subject, String report, String subScoreDefinitionName, Integer subScoreDisplaySequence);
	
	boolean checkForDuplicateDefinitionForReport(Long schoolYear, Long assessmentProgramId, Long subject, String report, String subScoreDefinitionName);
	
	boolean checkIfSubscoreDefinitionNameExists(Long schoolYear, Long assessmentProgramId, Long subject, String subScoreDefinitionName);
	
}
