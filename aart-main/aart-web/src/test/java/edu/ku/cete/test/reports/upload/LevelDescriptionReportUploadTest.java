/**
 * 
 */
package edu.ku.cete.test.reports.upload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.impl.report.BatchUploadServiceImpl;
import edu.ku.cete.service.impl.report.LevelDescriptionServiceImpl;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.test.BaseTest;

/**
 * @author ktaduru_sta
 *
 */
public class LevelDescriptionReportUploadTest extends BaseTest {

	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Value("${levelDescription.mdpt.max.bullets}")
	private int levelDescriptionMdptMaxBullets;

	@Value("${levelDescription.main.max.bullets}")
	private int levelDescriptionMainMaxBullets;
	
	@Value("${levelDescription.mdpt.max.length}")
	private int levelDescriptionMdptMaxLength;
	
	@Value("${levelDescription.main.max.length}")
	private int levelDescriptionMainMaxLength;
	
	@Value("${levelDescriptionParagraphPageBottom.max.length}")
	private int levelDescriptionParagraphPageBottomMaxLength;
	
	@Value("${levelDescription.delimitter}")
	private String levelDescriptionDelimitter ;	
	
	@Test
	public void testinsertBatchUploadMockito() {
		BatchUpload batchUpload = getBatchUploadDataObject(12l, 3l, 2016, "Upload_level_description_success_test.csv", "c:/dev/83689/160316143950/12_3_summative_report_uploads/Upload_level_description_success_test.csv");
		BatchUploadService batchUploadServiceMock = mock(BatchUploadServiceImpl.class);
		
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(12l);
		batchUpload.setAssessmentProgramName(assessmentProgram.getProgramName());
		
		ContentArea contentArea =  contentAreaService.selectByPrimaryKey(3l);
		batchUpload.setContentAreaName(contentArea.getName());
		batchUpload.setUploadType("");
		batchUpload.setCreatedUserDisplayName("CetesysAdmin");
		
		when(batchUploadServiceMock.insertBatchUpload(batchUpload)).thenReturn(1);
		int rowsInserted = batchUploadServiceMock.insertBatchUpload(batchUpload);
		assertEquals(1, rowsInserted);
		//assertEquals(0, rowsInserted);
		//assertNotEquals(1, rowsInserted);
		
		
	}	
	
	@Test
	public void testSelectBatchUploadPendingRecordMockito() {
		BatchUpload batchUploadRecord = getBatchUploadDataObject(12l, 3l, 2016, "Upload_level_description_success_test.csv", "c:/dev/83689/160316143950/12_3_summative_report_uploads/Upload_level_description_success_test.csv");
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
		batchUpload.setSuccessCount(3);
		batchUpload.setFailedCount(0);
		batchUpload.setAlertCount(0);
		batchUpload.setStatus("COMPLETED");
		batchUpload.setModifiedDate(new Date());
		
		int rowsUpdated = batchUploadServiceMock.updateByPrimaryKeySelectiveBatchUpload(batchUpload);
		assertEquals(1, rowsUpdated);
		//assertEquals(0, rowsUpdated);
		
	}	
	
	@Test
	public void testInsertLevelDescriptionMockito() {
		String levelDescription = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		LevelDescriptionService levelDescriptionServiceMock = mock(LevelDescriptionServiceImpl.class);
		LevelDescription levelDescriptionObj = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescription, descParagraphPageBottom, 8731l);
		when(levelDescriptionServiceMock.insertSelectiveLevelDescription(levelDescriptionObj)).thenReturn(1);
		
		int rowsInserted = levelDescriptionServiceMock.insertSelectiveLevelDescription(levelDescriptionObj);
		assertEquals(1, rowsInserted);
	}	
	
	@Test
	public void testDeleteLevelDescriptionMockito() {
		LevelDescriptionService levelDescriptionServiceMock = mock(LevelDescriptionServiceImpl.class);
		when(levelDescriptionServiceMock.deleteLevelDescriptions(12l, 3l, 2015l, 18L, null)).thenReturn(1);
		int deletedRowsCount = levelDescriptionServiceMock.deleteLevelDescriptions(12l, 3l, 2015l, 18L, null);
		assertEquals(1,deletedRowsCount);
		//assertEquals(0,deletedRowsCount);
		
	}
	
	@Test
	public void testValidateAssessmentProgram() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		String assessmentProgramCodeOnUI = "KAP";
		assertEquals("Must be assessment program specified on upload page.", assessmentProgramCodeOnUI, levelDescription.getAssessmentProgram());
	}
	
	@Test
	public void testValidateSubject() {	
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		String subjectCodeOnUI = "ELA";
		assertEquals("Must be subject selected on upload page.", subjectCodeOnUI, levelDescription.getSubject());
	}
	
	@Test
	public void testValidateGrade() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		GradeCourse gradeCourse = null;
		Long contentAreaIdOnUi = 3l; 
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(contentAreaIdOnUi, levelDescription.getGrade());
		assertNotNull("Must be a valid grade for subject specified.", gradeCourse);
		
	}
	
	@Test
	public void testLevelDescriptionTypeNotNull() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		//levelDescription.setDescriptionType(null);		
		assertNotNull("Value not specified for field Description_Type", levelDescription.getDescriptionType());
		
	}
	
	@Test
	public void testLevelDescriptionType() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		List<String> allowableValuesArray = new ArrayList<String>();
		allowableValuesArray.add("mdpt");
		allowableValuesArray.add("main");
		allowableValuesArray.add("combined");
		
		assertTrue("Value in field Description_Type is not allowed.", allowableValuesArray.contains(levelDescription.getDescriptionType().toLowerCase()));
		
	}
	
	@Test
	public void testLevelNameLength() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
				
		assertTrue("Value in field Level_Name must be less than or equal to 22 characters.", levelDescription.getLevelName().length() < 22);
		
	}
	
	@Test
	public void testNoOfBulletsInLevelDescriptionForMDPT() {
		String levelDescriptionVal = "Bullet Point 1 ~ Bulllet Point 2 ~ Bulllet Point 3 ~ Bulllet Point 4 ~ Bulllet Point 5 ~ Bulllet Point 6 ~ Bulllet Point 7";
		//levelDescriptionVal = "Bullet Point 1 ~ Bulllet Point 2 ~ Bulllet Point 3 ~ Bulllet Point 4 ~ Bulllet Point 5 ~ Bulllet Point 6 ~ Bulllet Point 7 ~ Bulllet Point 8";
		String descParagraphPageBottom = "Students who perform at this level typically ~ establish setting and characters in narratives or state";	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		List<String> levelDescBulletedList = new ArrayList<String>(Arrays.asList(levelDescription.getLevelDescription().split(levelDescriptionDelimitter)));		
		assertTrue("Value in field Level_Description has exceeded maximum number of bullet points(7).", levelDescBulletedList.size() <= levelDescriptionMdptMaxBullets);
		
	}
	
	@Test
	public void testNoOfCharactersInLevelDescriptionForMDPT() {
		String levelDescriptionVal = "Students who perform at this level typically  establish setting and characters in narratives or state  a clear main idea in informational and opinion writing  use significant descriptions and details  clarify relationships among ideas and use appropriate transitions  write readable Students who perform at this level typically  establish setting and char";
		levelDescriptionVal = "Students who perform at this level typically  establish setting and characters in narratives or state  a clear main idea in informational and opinion writing  use significant descriptions and details  clarify relationships among ideas and use appropriate transitions  write readable Students who perform at this level typically  establish";
		String descParagraphPageBottom = "Students who perform at this level typically ~ establish setting and characters in narratives or state";	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
				
		assertTrue("Value in field Level_Description has exceeded maximum number of characters(350).", !(levelDescription.getLevelDescription().trim().length() > levelDescriptionMdptMaxLength));
		
	}
	
	@Test
	public void testNoOfBulletsInLevelDescriptionForMainType() {
		String levelDescriptionVal = "Bullet Point 1 ~ Bulllet Point 2 ~ Bulllet Point 3 ~ Bulllet Point 4 ~ Bulllet Point 5 ~ Bulllet Point 6 ~ Bulllet Point 7 ~ Bulllet Point 8 ~ Bulllet Point 9 ~ Bulllet Point 10 ~ Bulllet Point 11 ~ Bulllet Point 12 ~ Bulllet Point 13 ~ Bulllet Point 14 ~ Bulllet Point 15";
		levelDescriptionVal = "Bullet Point 1 ~ Bulllet Point 2 ~ Bulllet Point 3 ~ Bulllet Point 4 ~ Bulllet Point 5 ~ Bulllet Point 6 ~ Bulllet Point 7 ~ Bulllet Point 8 ~ Bulllet Point 9 ~ Bulllet Point 10 ~ Bulllet Point 11 ~ Bulllet Point 12 ~ Bulllet Point 13 ~ Bulllet Point 14 ~ Bulllet Point 15 ~ Bulllet Point 16";
		String descParagraphPageBottom = "Students who perform at this level typically ~ establish setting and characters in narratives or state";	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "Main", levelDescriptionVal, descParagraphPageBottom, 8731l);
		List<String> levelDescBulletedList = new ArrayList<String>(Arrays.asList(levelDescription.getLevelDescription().split(levelDescriptionDelimitter)));		
		assertTrue("Value in field Level_Description has exceeded maximum number of bullet points(15).", levelDescBulletedList.size() <= levelDescriptionMainMaxBullets);
		
	}
	
	@Test
	public void testNoOfCharactersInLevelDescriptionForMainType() {
		String levelDescriptionVal = "Students who perform at this level typically  read moderately complex texts  summarize themes  identify implicit or explicit details to support an idea  determine meanings of more difficult words and complex figurative language  identify literary elements and text structures and their impact on meaning  determine point of view or purpose  Students who perform at this level typically   read moderately complex texts  summarize themes  identify implicit or explicit details to support an idea  determine meanings of more difficult words and complex figurative language  identify literary elements and text structures and their impact on meaning  determine point of view";
		levelDescriptionVal = "Students who perform at this level typically  read moderately complex texts  summarize themes  identify implicit or explicit details to support an idea  determine meanings of more difficult words and complex figurative language  identify literary elements and text structures and their impact on meaning  determine point of view or purpose  Students who perform at this level typically   read moderately complex texts  summarize themes  identify implicit or explicit details to support an idea  determine meanings of more difficult words and complex figurative language  identify literary elements and text structures and their impact on meaning  determine point of view or purpose testing max number of characters";
		String descParagraphPageBottom = "Students who perform at this level typically ~ establish setting and characters in narratives or state";	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "Main", levelDescriptionVal, descParagraphPageBottom, 8731l);
				
		assertTrue("Value in field Level_Description has exceeded maximum number of characters(710).", !(levelDescription.getLevelDescription().trim().length() > levelDescriptionMainMaxLength));
		
	}
	
	@Test
	public void testSchoolYear() {
		List<LevelDescription> LevelDescriptionList = new ArrayList<LevelDescription>();
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		
		getLevelDescriptionList(LevelDescriptionList, "KAP", 12l, "ELA", 3l, "4", 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		getLevelDescriptionList(LevelDescriptionList, "KAP", 12l, "ELA", 3l, "4", 133l, 2015l, 3l, "Level_3", "Main", levelDescriptionVal, descParagraphPageBottom, 8732l);
		assertEquals("All school years in the file are not the same school year.", LevelDescriptionList.get(0).getSchoolYear(), LevelDescriptionList.get(1).getSchoolYear());
	}
	
	@Test
	public void testSchoolYearNotNull() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		//levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, null, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		assertNotNull("Value not specified for field School_Year", levelDescription.getSchoolYear());
		
	}
	
	@Test
	public void testAssessmentProgramNotNull() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		//levelDescription.setAssessmentProgram(null);
		assertNotNull("Value not specified for field Assessment_Program", levelDescription.getAssessmentProgram());
		
	}
	
	@Test
	public void testSubjectNotNull() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		//levelDescription.setSubject(null);
		assertNotNull("Value not specified for field Subject", levelDescription.getSubject());
		
	}
	
	@Test
	public void testGradeNotNull() {
		String levelDescriptionVal = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;	
		LevelDescription levelDescription = getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescriptionVal, descParagraphPageBottom, 8731l);
		//levelDescription.setGrade(null);
		assertNotNull("Value not specified for field Grade", levelDescription.getGrade());
		
	}
	
	private LevelDescription getLevelDescriptionObject(String assessmentProgramCode, Long assessmentProgramId, Long subjectId, Long gradeCourseId, Long schoolYear, Long levelNumber, String levelName,
			String descriptionType, String levelDescriptionValue, String descParagraphPageBottom, Long batchUploadId){
		LevelDescription levelDescription = new LevelDescription();
		levelDescription.setAssessmentProgram(assessmentProgramCode);
		levelDescription.setAssessmentProgramId(assessmentProgramId);
		levelDescription.setSubject("ELA");
		levelDescription.setSubjectId(subjectId);
		levelDescription.setGradeId(gradeCourseId);
		levelDescription.setGrade("4");
		levelDescription.setSchoolYear(schoolYear);
		levelDescription.setLevel(levelNumber);
		levelDescription.setLevelName(levelName);
		levelDescription.setLevelDescription(levelDescriptionValue);
		levelDescription.setDescriptionParagraphPageBottom(descParagraphPageBottom);
		levelDescription.setDescriptionType(descriptionType);
		levelDescription.setBatchUploadId(batchUploadId);
		
		return levelDescription;
	}
	
	private List<LevelDescription> getLevelDescriptionList(List<LevelDescription> LevelDescriptionList, String assessmentProgramCode, Long assessmentProgramId, String subject, Long subjectId, String grade, Long gradeCourseId, Long schoolYear, Long levelNumber, String levelName,
			String descriptionType, String levelDescriptionValue, String descParagraphPageBottom, Long batchUploadId){
		LevelDescription levelDescription = new LevelDescription();
		levelDescription.setAssessmentProgram(assessmentProgramCode);
		levelDescription.setAssessmentProgramId(assessmentProgramId);
		levelDescription.setSubject(subject);
		levelDescription.setSubjectId(subjectId);
		levelDescription.setGradeId(gradeCourseId);
		levelDescription.setGrade(grade);
		levelDescription.setSchoolYear(schoolYear);
		levelDescription.setLevel(levelNumber);
		levelDescription.setLevelName(levelName);
		levelDescription.setLevelDescription(levelDescriptionValue);
		levelDescription.setDescriptionParagraphPageBottom(descParagraphPageBottom);
		levelDescription.setDescriptionType(descriptionType);
		levelDescription.setBatchUploadId(batchUploadId);
		LevelDescriptionList.add(levelDescription);
		
		return LevelDescriptionList;
	}
	
	private BatchUpload getBatchUploadDataObject(Long assessmentProgramId, Long contentAreaId, int schoolYear, String fileName, String filePath){
		BatchUpload batchUpload = new BatchUpload();
		
		batchUpload.setUploadTypeId(498l);
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
		int batchUploadId = batchUploadService.insertBatchUpload(getBatchUploadDataObject(12l, 3l, 2016, "Upload_level_description_success_test.csv", "c:/dev/83689/160316143950/12_3_summative_report_uploads/Upload_level_description_success_test.csv"));
		assertNotNull(batchUploadId);
	}
	
	@Test
	public void testSelectBatchUploadPendingRecord() {
		BatchUpload pendingUploadRecord = batchUploadService.selectOnePending("Pending", "REPORT_UPLOAD_FILE_TYPE");
		//assertEquals(null,pendingUploadRecord);
		assertNotEquals(null,pendingUploadRecord);
		//System.out.println(pendingUploadRecord.getId());
		//assertNotNull(pendingUploadRecord);
	}
	
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testinsertBatchUploadWithoutAssessmentProgram() {
		batchUploadService.insertBatchUpload(getBatchUploadDataObject(null, 3l, 2016, "Upload_level_description_success_test.csv", "c:/dev/83689/160316143950/12_3_summative_report_uploads/Upload_level_description_success_test.csv"));		
	}
	
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testinsertBatchUploadWithoutContentAreaId() {
		batchUploadService.insertBatchUpload(getBatchUploadDataObject(12l, null, 2016, "Upload_level_description_success_test.csv", "c:/dev/83689/160316143950/12_3_summative_report_uploads/Upload_level_description_success_test.csv"));		
	}
	
	@Test
	public void testInsertLevelDescription() {
		String levelDescription = "Students who perform at this level typically ~ establish setting and characters in narratives or state";
		String descParagraphPageBottom = null;
		int rowsInserted = levelDescriptionService.insertSelectiveLevelDescription(getLevelDescriptionObject("KAP", 12l, 3l, 133l, 2016l, 3l, "Level_3", "MDPT", levelDescription, descParagraphPageBottom, 8731l));
		assertEquals(1, rowsInserted);
	}
	
	@Test
	public void testDeleteLevelDescription() {
		int deletedRowsCount = levelDescriptionService.deleteLevelDescriptions(12l, 3l, 2015l);
		//assertEquals(1,deletedRowsCount);
		assertEquals(0,deletedRowsCount);
		
	}
	
	@Test
	public void testUpdateBatchUploadRecord() {
		BatchUpload batchUpload = new BatchUpload();
		batchUpload.setId(8734l);
		batchUpload.setSuccessCount(3);
		batchUpload.setFailedCount(0);
		batchUpload.setAlertCount(0);
		batchUpload.setStatus("COMPLETED");
		batchUpload.setModifiedDate(new Date());
		int rowsUpdated = batchUploadService.updateByPrimaryKeySelectiveBatchUpload(batchUpload);
		assertEquals(1, rowsUpdated);
		//assertEquals(0, rowsUpdated);
		
	}*/
}
