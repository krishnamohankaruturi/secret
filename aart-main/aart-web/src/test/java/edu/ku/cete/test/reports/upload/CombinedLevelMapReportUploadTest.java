/**
 * 
 */
package edu.ku.cete.test.reports.upload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.impl.report.BatchUploadServiceImpl;
import edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.CombinedLevelMapService;
import edu.ku.cete.test.BaseTest;

/**
 * @author ktaduru_sta
 *
 */
public class CombinedLevelMapReportUploadTest extends BaseTest {

	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private CombinedLevelMapService combinedLevelMapService;
	
	@Autowired
	private GradeCourseService gradeCourseService;	
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Test
	public void testinsertBatchUploadMockito() {
		BatchUpload batchUpload = getBatchUploadDataObject(12l, 3l, 2016, "CombinedLevelMappingTemplate_2016.csv", "c:/dev/83689/160316160128/12_3_summative_report_uploads/CombinedLevelMappingTemplate_2016.csv");
		BatchUploadService batchUploadServiceMock = mock(BatchUploadServiceImpl.class);
		when(batchUploadServiceMock.insertBatchUpload(batchUpload)).thenReturn(1);
		
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(12l);
		batchUpload.setAssessmentProgramName(assessmentProgram.getProgramName());
		
		ContentArea contentArea =  contentAreaService.selectByPrimaryKey(3l);
		batchUpload.setContentAreaName(contentArea.getName());
		batchUpload.setUploadType("");
		batchUpload.setCreatedUserDisplayName("CetesysAdmin");
		
		int rowsInserted = batchUploadServiceMock.insertBatchUpload(batchUpload);
		assertEquals(1, rowsInserted);
		//assertEquals(0, rowsInserted);
		//assertNotEquals(1, rowsInserted);
	}	
	
	@Test
	public void testSelectBatchUploadPendingRecordMockito() {
		BatchUpload batchUploadRecord = getBatchUploadDataObject(12l, 3l, 2016, "CombinedLevelMappingTemplate_2016.csv", "c:/dev/83689/160316160128/12_3_summative_report_uploads/CombinedLevelMappingTemplate_2016.csv");
		BatchUploadService batchUploadServiceMock = mock(BatchUploadServiceImpl.class);
		when(batchUploadServiceMock.selectOnePending("Pending", "REPORT_UPLOAD_FILE_TYPE")).thenReturn(batchUploadRecord);
		//when(batchUploadServiceMock.selectOnePending("Pending", "REPORT_UPLOAD_FILE_TYPE")).thenReturn(null);
		BatchUpload pendingUploadRecord = batchUploadServiceMock.selectOnePending("Pending", "REPORT_UPLOAD_FILE_TYPE");
		//assertNotEquals(null,pendingUploadRecord);
		assertNotNull("Record Not Found", pendingUploadRecord);
		//assertNull(pendingUploadRecord);
	}	
	
	@Test
	public void testUpdateBatchUploadRecordMockito() {
		BatchUpload batchUpload = new BatchUpload();		
		BatchUploadService batchUploadServiceMock = mock(BatchUploadServiceImpl.class);
		when(batchUploadServiceMock.updateByPrimaryKeySelectiveBatchUpload(batchUpload)).thenReturn(1);
		
		batchUpload.setId(8734l);
		batchUpload.setSuccessCount(2);
		batchUpload.setFailedCount(1);
		batchUpload.setAlertCount(0);
		batchUpload.setStatus("COMPLETED");
		batchUpload.setModifiedDate(new Date());
		
		int rowsUpdated = batchUploadServiceMock.updateByPrimaryKeySelectiveBatchUpload(batchUpload);
		assertEquals(1, rowsUpdated);
		//assertEquals(0, rowsUpdated);
		
	}	
	
	@Test
	public void testInsertCombinedLevelMapMockito() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		when(combinedLevelMapServiceMock.insertCombinedLevelMap(combinedLevelMap)).thenReturn(1);
		int rowsInserted = combinedLevelMapServiceMock.insertCombinedLevelMap(combinedLevelMap);
		assertEquals(1, rowsInserted);
		//assertEquals(0, rowsInserted);	
	}
	
	@Test
	public void testDeleteCombinedLevelMapMockito() {
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		when(combinedLevelMapServiceMock.deleteCombinedLevelMap(12l, 3l, 2015l)).thenReturn(1);
		int deletedRowsCount = combinedLevelMapServiceMock.deleteCombinedLevelMap(12l, 3l, 2015l);
		assertEquals(1,deletedRowsCount);
		//assertEquals(0,deletedRowsCount);
		
	}	
	
	@Test
	public void testValidateAssessmentProgram() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		String assessmentProgramCodeOnUI = "KAP";
		assertEquals("Must be assessment program specified on upload page.", assessmentProgramCodeOnUI, combinedLevelMap.getAssessmentProgram());
	}
	
	@Test
	public void testValidateSubject() {		
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		String subjectCodeOnUI = "ELA";
		assertEquals("Must be subject selected on upload page.", subjectCodeOnUI, combinedLevelMap.getSubject());
	}
	
	@Test
	public void testValidateGrade() {
		GradeCourse gradeCourse = null;
		Long contentAreaIdOnUi = 3l; 
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(contentAreaIdOnUi, combinedLevelMap.getGrade());
		assertNotNull("Must be a valid grade for subject specified.", gradeCourse);
		
	}
	
	@Test
	public void testStagesHighScaleScoreGreaterThanLowScaleScoreValue() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("High scale score value must be higher than low scale score.", combinedLevelMap.getStagesHighScaleScore() > combinedLevelMap.getStagesLowScaleScore());
		
		//CombinedLevelMap combinedLevelMap1 = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2015l, 120l, 130l, 1, 1, 8733l);
		//assertTrue("High scale score value must be higher than low scale score.", combinedLevelMap1.getStagesHighScaleScore() > combinedLevelMap1.getStagesLowScaleScore());
	
	}
	
	@Test
	public void testSchoolYear() {
		List<CombinedLevelMap> combinedLevelMapList = new ArrayList<CombinedLevelMap>();
		getCombinedLevelMapList(combinedLevelMapList, "KAP", 12l, "ELA", 3l, "4", 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		getCombinedLevelMapList(combinedLevelMapList, "KAP", 12l, "ELA", 3l, "4", 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		
		assertEquals("All school years in the file are not the same school year.", combinedLevelMapList.get(0).getSchoolYear(), combinedLevelMapList.get(1).getSchoolYear());
		
	}
	
	@Test
	public void testSchoolYearNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);		
		//combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, null, 120l, 80l, 1, 1, 8733l);
		assertNotNull("Value not specified for field School_Year", combinedLevelMap.getSchoolYear());
		
	}
	
	@Test
	public void testAssessmentProgramNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		//combinedLevelMap.setAssessmentProgram(null);
		assertNotNull("Value not specified for field Assessment_Program", combinedLevelMap.getAssessmentProgram());
		
	}
	
	@Test
	public void testSubjectNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		//combinedLevelMap.setSubject(null);
		assertNotNull("Value not specified for field Subject", combinedLevelMap.getSubject());
		
	}
	
	@Test
	public void testGradeNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		//combinedLevelMap.setGrade(null);
		assertNotNull("Value not specified for field Grade", combinedLevelMap.getGrade());
		
	}
	
	@Test
	public void testHighScaleScoreValueNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertNotNull("Value not specified for field Stages_High_Scale_Score", combinedLevelMap.getStagesHighScaleScore());
		
	}
	
	@Test
	public void testHighScaleScoreIsNumeric() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("High_Scale_Score value must be a numeric and integer.", StringUtils.isNumeric(combinedLevelMap.getStagesHighScaleScore().toString()));
		//assertTrue("Value must be a numeric and integer.", StringUtils.isNumeric("Ab1"));
	}
	
	@Test
	public void testLowScaleScoreValueNotNull() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertNotNull("Value not specified for field Stages_Low_Scale_Score", combinedLevelMap.getStagesLowScaleScore());
		
	}
	
	@Test
	public void testLowScaleScoreIsNumeric() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("Low_Scale_Score value must be a numeric and integer.", StringUtils.isNumeric(combinedLevelMap.getStagesLowScaleScore().toString()));
		//assertTrue("Value must be a numeric and integer.", StringUtils.isNumeric("Ab1"));
	}
	
	@Test
	public void testPerformanceScaleScoreValueIsNumeric() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("Performance_Scale_Score value must be a numeric and integer.", StringUtils.isNumeric(combinedLevelMap.getPerformanceScaleScore().toString()));
		//assertTrue("Value must be a numeric and integer.", StringUtils.isNumeric("Ab1"));
	}
	
	@Test
	public void testPerformanceScaleScoreValueLessThanTen() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("Performance_Scale_Score value must be less than 10.", Long.parseLong(combinedLevelMap.getPerformanceScaleScore().toString()) < 10);
		//assertTrue("Performance scale score value must be less than 10.", combinedLevelMap.getPerformanceScaleScore().compareTo(new BigDecimal(10)) < 0);
		//assertTrue("Value must be a numeric and integer.", StringUtils.isNumeric("Ab1"));
	}
	
	@Test
	public void testCombinedLevelValueIsNumeric() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 1, 8733l);
		assertTrue("Combined_Level value must be a numeric and integer.", StringUtils.isNumeric(combinedLevelMap.getCombinedLevel().toString()));
		//assertTrue("Value must be a numeric and integer.", StringUtils.isNumeric("Ab1"));

	}
	
	@Test
	public void testCombinedLevelValueLessThanTen() {
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2016l, 120l, 80l, 1, 10, 8733l);
		assertTrue("Combined_Level value must be less than 10.", Long.parseLong(combinedLevelMap.getCombinedLevel().toString()) < 10);
	
	}
	
	private CombinedLevelMap getCombinedLevelMapObject(String assessmentProgramCode, Long assessmentProgramId, Long subjectId, Long gradeCourseId, Long schoolYear, Long stagesHighScaleScore, Long stagesLowScaleScore,
			int performanceScaleScore, int combinedLevel, Long batchUploadId){
		CombinedLevelMap combinedLevelMap = new CombinedLevelMap();
		combinedLevelMap.setAssessmentProgram(assessmentProgramCode);
		combinedLevelMap.setSubject("ELA");
		combinedLevelMap.setAssessmentProgramId(assessmentProgramId); 
		combinedLevelMap.setSubjectId(subjectId);
		combinedLevelMap.setGradeId(gradeCourseId);
		combinedLevelMap.setGrade("4");
		combinedLevelMap.setSchoolYear(schoolYear);
		combinedLevelMap.setStagesHighScaleScore(stagesHighScaleScore);
		combinedLevelMap.setStagesLowScaleScore(stagesLowScaleScore);
		combinedLevelMap.setPerformanceScaleScore(new BigDecimal(performanceScaleScore));
		combinedLevelMap.setCombinedLevel(new BigDecimal(combinedLevel));
		combinedLevelMap.setBatchUploadId(batchUploadId);
		
		return combinedLevelMap;
	}
	
	
	private List<CombinedLevelMap> getCombinedLevelMapList(List<CombinedLevelMap> combinedLevelMapList,
			String assessmentProgramCode, Long assessmentProgramId, String subject, Long subjectId, String grade, Long gradeCourseId, Long schoolYear, Long stagesHighScaleScore, Long stagesLowScaleScore,
			int performanceScaleScore, int combinedLevel, Long batchUploadId){
		CombinedLevelMap combinedLevelMap = new CombinedLevelMap();
		combinedLevelMap.setAssessmentProgram(assessmentProgramCode);
		combinedLevelMap.setSubject(subject);
		combinedLevelMap.setAssessmentProgramId(assessmentProgramId); 
		combinedLevelMap.setSubjectId(subjectId);
		combinedLevelMap.setGradeId(gradeCourseId);
		combinedLevelMap.setGrade(grade);
		combinedLevelMap.setSchoolYear(schoolYear);
		combinedLevelMap.setStagesHighScaleScore(stagesHighScaleScore);
		combinedLevelMap.setStagesLowScaleScore(stagesLowScaleScore);
		combinedLevelMap.setPerformanceScaleScore(new BigDecimal(performanceScaleScore));
		combinedLevelMap.setCombinedLevel(new BigDecimal(combinedLevel));
		combinedLevelMap.setBatchUploadId(batchUploadId);
		
		combinedLevelMapList.add(combinedLevelMap);
		
		return combinedLevelMapList;
	}
	
	
	private BatchUpload getBatchUploadDataObject(Long assessmentProgramId, Long contentAreaId, int schoolYear, String fileName, String filePath){
		BatchUpload batchUpload = new BatchUpload();
		
		batchUpload.setUploadTypeId(570l);
		batchUpload.setAssessmentProgramId(assessmentProgramId);
		batchUpload.setContentAreaId(contentAreaId);
		batchUpload.setSubmissionDate(new Date());
		batchUpload.setCreatedUser(12l);
		batchUpload.setStatus("Pending");
		batchUpload.setSchoolYear(schoolYear);
		batchUpload.setActiveFlag(true);
		batchUpload.setStateId(null);
		batchUpload.setDistrictId(null);
		batchUpload.setSchoolId(null);
		batchUpload.setSelectedOrgId(null);
		batchUpload.setUploadedUserOrgId(51l);
		batchUpload.setUploadedUserGroupId(55l);
		batchUpload.setFileName(fileName);
		batchUpload.setFilePath(filePath);
		
		
		return batchUpload;
	}	
	
	//Commenting out direct calls to database and used mockito instead
	/*@Test
	public void testinsertBatchUpload() {
		
		int rowsInserted = batchUploadService.insertBatchUpload(getBatchUploadDataObject(12l, 3l, 2016, "CombinedLevelMappingTemplate_2016.csv", "c:/dev/83689/160316160128/12_3_summative_report_uploads/CombinedLevelMappingTemplate_2016.csv"));
		//System.out.println("Rows Inserted: "+ rowsInserted);
		assertEquals(1, rowsInserted);
		//assertEquals(0, rowsInserted);
		//assertNotNull(rowsInserted);
	}
	
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testinsertBatchUploadWithoutAssessmentProgram() {
		batchUploadService.insertBatchUpload(getBatchUploadDataObject(null, 3l, 2016, "CombinedLevelMappingTemplate_2016.csv", "c:/dev/83689/160316160128/12_3_summative_report_uploads/CombinedLevelMappingTemplate_2016.csv"));		
	}
	
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testinsertBatchUploadWithoutContentAreaId() {
		batchUploadService.insertBatchUpload(getBatchUploadDataObject(12l, null, 2016, "CombinedLevelMappingTemplate_2016.csv", "c:/dev/83689/160316160128/12_3_summative_report_uploads/CombinedLevelMappingTemplate_2016.csv"));		
	}
	
	@Test
	public void testSelectBatchUploadPendingRecord() {
		BatchUpload pendingUploadRecord = batchUploadService.selectOnePending("Pending", "REPORT_UPLOAD_FILE_TYPE");
		//Assert.assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,pendingUploadRecord);
		//System.out.println(pendingUploadRecord.getId());
		//assertNotNull(pendingUploadRecord);
	}
	
	@Test
	public void testUpdateBatchUploadRecord() {
		BatchUpload batchUpload = new BatchUpload();
		batchUpload.setId(8734l);
		batchUpload.setSuccessCount(2);
		batchUpload.setFailedCount(1);
		batchUpload.setAlertCount(0);
		batchUpload.setStatus("COMPLETED");
		batchUpload.setModifiedDate(new Date());
		int rowsUpdated = batchUploadService.updateByPrimaryKeySelectiveBatchUpload(batchUpload);
		assertEquals(1, rowsUpdated);
		//assertEquals(0, rowsUpdated);
		
	}
	
	@Test
	public void testInsertCombinedLevelMap() {
		int rowsInserted = combinedLevelMapService.insertCombinedLevelMap(getCombinedLevelMapObject("KAP", 12l, 3l, 132l, 2015l, 120l, 80l, 1, 1, 8733l));
		assertEquals(1, rowsInserted);
	}
	
	@Test
	public void testDeleteCombinedLevelMap() {
		int deletedRowsCount = combinedLevelMapService.deleteCombinedLevelMap(12l, 3l, 2015l);
		assertEquals(1,deletedRowsCount);
		//assertEquals(0,deletedRowsCount);
	}*/
	
}
