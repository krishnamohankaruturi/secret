package edu.ku.cete.service.report;

public interface DLMReportsService {

	public boolean checkIfIntegratedEEisWritingType(Long essentialElementId);
	
	public boolean checkIfYearEndEEisWritingType(String eeCode, Long testId);
	
	public int countTotalNumberOfScoreableItems(Long studentId, Long testId);
	
	public int countNumberOfScoreableItemsWithCorrectResponses(Long studentId, Long testId);
	
}
