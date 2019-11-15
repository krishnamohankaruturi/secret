package edu.ku.cete.service.impl.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

import edu.ku.cete.batch.upload.validator.BatchUploadValidator;
import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.domain.PNPUploadRecord;
import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.UploadedPermissionRecord;
import edu.ku.cete.domain.UploadedScoringRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.model.StateSpecificFileDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadInfo;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.report.domain.ExternalStudentReportResults;
import edu.ku.cete.report.domain.ExternalUploadResult;
import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.report.domain.OrganizationReportResults;
import edu.ku.cete.report.domain.StateSpecificFile;
import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.report.model.BatchUploadMapper;
import edu.ku.cete.report.model.BatchUploadReasonMapper;
import edu.ku.cete.service.BatchUploadCustomValidationForAlertService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ScoringUploadCustomValidationServiceImpl;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.impl.ProjectedTestingUploadCustomValidationServiceImpl;
import edu.ku.cete.service.impl.ProjectedTestingUploadWriterProcessServiceImpl;
import edu.ku.cete.service.impl.ScoringUploadWriterProcessServiceImpl;
import edu.ku.cete.service.impl.SubScoreDefaultStageIdsCustomValidatonServiceImpl;
import edu.ku.cete.service.impl.SubScoreDefaultStageIdsWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadAssessmentTopicCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadAssessmentTopicDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadExternalOrgReportResultCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadExternalOrgReportResultDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadExternalStudentReportResultsCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadExternalStudentReportResultsDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadGrfFileDefaultCustomValidatonServiceImpl;
import edu.ku.cete.service.impl.UploadGrfFileDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadIncidentFileDefaultCustomValidatonServiceImpl;
import edu.ku.cete.service.impl.UploadIncidentFileDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadOrgPrctByAssessmentTopicCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadPermissionCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadPermissionDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadScCodeFileDefaultCustomValidatonServiceImpl;
import edu.ku.cete.service.impl.UploadScCodeFileDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.impl.UploadStudentPrctByAssessmentTopicCustomValidationServiceImpl;
import edu.ku.cete.service.impl.UploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.web.OrganizationReportsImportDTO;
import edu.ku.cete.web.StudentReportsImportDTO;

/**
 * @author UdayaKiran.j
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class BatchUploadServiceImpl implements BatchUploadService {
	
    private Logger logger = LoggerFactory.getLogger(BatchUploadServiceImpl.class);


    @Value("${excludedItemsRecordType}")
	private String excludedItemsRecordType;
	
	@Value("${rawToScaleScoresRecordType}")
	private String rawToScaleScoresRecordType;
	
	@Value("${testCutScoresRecordType}")
	private String testCutScoresRecordType;
	
	@Value("${combinedLevelMapRecordType}")
	private String combinedLevelMapRecordType;
	
	@Value("${levelDescriptionsRecordType}")
	private String levelDescriptionsRecordType;
	
	@Value("${miscellaneousReportTextRecordType}")
	private String miscellaneousReportTextRecordType;
	
	@Value("${subscoreDescriptionReportUsageRecordType}")
	private String subscoreDescriptionReportUsageRecordType;
	
	@Value("${subscoreFrameworkMappingRecordType}")
	private String subscoreFrameworkMappingRecordType;
	
	@Value("${subscoreRawToScaleScoreRecordType}")
	private String subscoreRawToScaleScoreRecordType;
	
	/** Added By Prasanth
	 *  User Story: US16352
	 *  For User Upload Type
	 */
	@Value("${userupload.userRecordType}")
	private String userUploadRecordType;
	
	@Value("${orgupload.recordType}")
	private String orgUploadRecordType;
	
	@Value("${enrollmentRecordType}")
	private String enrollmentRecordType;

	@Value("${xmlEnrollmentRecordType}")
	private String xmlEnrollmentRecordType;
	
	@Value("${xmlUnEnrollmentRecordType}")
	private String xmlUnEnrollmentRecordType;
	
	@Value("${xmlLeaRecordType}")
	private String xmlLeaRecordType;
	
	@Value("${xmlSchoolRecordType}")
	private String xmlSchoolRecordType;
	
	@Value("${xmlDeleteLeaRecordType}")
	private String xmlDeleteLeaRecordType;
	
	@Value("${xmlDeleteSchoolRecordType}")
	private String xmlDeleteSchoolRecordType;
	
	@Value("${tecXMLRecordType}")
	private String tecXMLRecordType;
	
	@Value("${xmlRosterRecordType}")
	private String xmlRosterRecordType;
	
	@Value("${scrsRecordType}")
	private String scrsRecordType;
	
	@Value("${tecRecordType}")
	private String tecRecordType;
	
	@Value("${projectedTestingRecordType}")
	private String projectedTesingRecordType;
	
	@Value("${scoringRecordType}")
	private String scoringRecordType;
	
	@Value("${subScoreDefaultStageIdRecordType}")
	private String subScoreDefaultStageIdRecordType;
	
	@Value("${studentReportsImportRecordType}")
	private String studentReportsImportRecordType;
	
	@Value("${studentSummaryReportsImportRecordType}")
	private String studentSummaryReportsImportRecordType;
	
	@Value("${schoolReportsImportRecordType}")
	private String schoolReportsImportRecordType;
	
	@Value("${classroomReportsImportRecordType}")
	private String classroomReportsImportRecordType;
	
	@Value("${schoolCsvReportsImportRecordType}")
	private String schoolCsvReportsImportRecordType;
	
	@Value("${classroomCsvReportsImportRecordType}")
	private String classroomCsvReportsImportRecordType;
	
	@Value("${uploadIncidentFileType}")
	private String uploadIncidentFileType;
	
	@Value("${uploadKansasScCodeFileType}")
	private String uploadKansasScCodeFileType;
	
	@Value("${uploadCommonScCodeFileType}")
	private String uploadCommonScCodeFileType;
	
	@Value("${uploadCommonGrfFileType}")
	private String uploadCommonGrfFileType;
	
	@Value("${uploadIowaGrfFileType}")
	private String uploadIowaGrfFileType;
	
	@Value("${uploadNewYorkGrfFileType}")
	private String uploadNewYorkGrfFileType;
	
	@Value("${uploadAssessmentTopicType}")
	private String uploadAssessmentTopicType;
	
	@Value("${uploadOrganizationScoreCaluculations}")
	private String uploadOrganizationScoreCaluculations;
	
	@Value("${uploadStudentPCTByAssessmentTopic}")
	private String uploadStudentPCTByAssessmentTopic;
	
	@Value("${uploadOrganizationPCTByAssessmentTopic}")
	private String uploadOrganizationPCTByAssessmentTopic;
	
	@Value("${uploadStudentScoreCaluculations}")
	private String uploadStudentScoreCaluculations;
	
	@Value("${cpassTestCutScoreRecordType}")
	private String cpassTestCutScoreRecordType;	
	
	@Value("${cpassLevelDiscriptionRecordType}")
	private String cpassLevelDiscriptionRecordType;
	
	@Value("${studentDcpsReportsImportRecordType}")
	private String studentDcpsReportsImportRecordType;
	
	@Value("${personalNeedsProfileRecordType}")
	private String uploadStudentPNPRecordType;
	
	@Value("${uploadDelawareGrfFileType}")
	private String uploadDelawareGrfFileType;
	
	@Value("${uploadDCGrfFileType}")
	private String uploadDCGrfFileType;
	
	@Value("${uploadArkansasGrfFileType}")
	private String uploadArkansasGrfFileType;

	@Value("${permissionRecordType}")
	private String permissionRecordType;
	
	@Autowired
	private ContentFrameworkDetailDao contentFrameworkDetailDao;
	
	@Autowired
	private SubscoresDescriptionCustomValidationServiceImpl subscoresDescriptionCustomValidationServiceImpl;
	
	@Autowired
	private SubscoreFrameworkCustomValidationServiceImpl subscoreFrameworkCustomValidationServiceImpl;
	
	@Autowired
	private BatchUploadMapper batchUploadDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private BatchUploadReasonMapper batchUploadReasonMapper;
	
	@Autowired
	private ExcludedItemCustomValidationServiceImpl excludedItemCustomValidationServiceImpl;
	
	@Autowired
	private ExcludedItemUploadWriterProcessServiceImpl excludedItemUploadWriterProcessServiceImpl;

	@Autowired
	private RawToScaleScoresCustomValidationServiceImpl rawToScaleScoresCustomValidationServiceImpl;
	
	@Autowired
	private RawToScaleScoresUploadWriterProcessServiceImpl rawToScaleScoresUploadWriterProcessServiceImpl;
	
	@Autowired
	private SubscoreRawToScaleScoresCustomValidationServiceImpl subscoreRawToScaleScoresCustomValidationServiceImpl;
	
	@Autowired
	private SubscoreRawToScaleScoresUploadWriterProcessServiceImpl subscoreRawToScaleScoresUploadWriterProcessServiceImpl;
	
	@Autowired
	private LevelDescriptionCustomValidationServiceImpl levelDescriptionCustomValidationServiceImpl;
	
	@Autowired
	private TestCutScoresCustomValidationServiceImpl testCutScoresCustomValidationServiceImpl;
	
	@Autowired
	private TestCutScoresUploadWriterProcessServiceImpl testCutScoresUploadWriterProcessServiceImpl;

	@Autowired
	private LevelDescriptionUploadWriterProcessServiceImpl levelDescriptionUploadWriterProcessServiceImpl;
	
	@Autowired
	private CombinedLevelMapCustomValidationServiceImpl combinedLevelMapCustomValidationServiceImpl;
	
	@Autowired
	private CombinedLevelMapUploadWriterProcessServiceImpl combinedLevelMapUploadWriterProcessServiceImpl;
		
	@Autowired
	private SubscoreFrameworkUploadWriterProcessServiceImpl subscoreFrameworkUploadWriterProcessServiceImpl;
	
	@Autowired
	SubscoresDescriptionUploadWriterProcessServiceImpl subscoresDescriptionUploadWriterProcessServiceImpl;
	
	@Autowired
	private UserUploadCustomValidationServiceImpl userUploadCustomValidationServiceImpl;
	
	@Autowired
	private UserUploadWriterProcessServiceImpl userUploadWriterProcessServiceImpl;
	
	@Autowired
	private OrganizationUploadCustomValidationServiceImpl organizationUploadCustomValidationServiceImpl;
	
	@Autowired
	private OrganizationUploadWriterProcessServiceImpl organizationUploadWriterProcessServiceImpl;
	
	@Autowired
	private  EnrollmentUploadCustomValidationServiceImpl enrollmentUploadCustomValidationServiceImpl;
	
	@Autowired
	private  UnEnrollmentUploadCustomValidationServiceImpl unEnrollmentUploadCustomValidationServiceImpl;
	
	@Autowired
	private  UnEnrollmentUploadWriterProcessServiceImpl unEnrollmentUploadWriterProcessServiceImpl;
	
	@Autowired
	private  OrganizationDeleteUploadCustomValidationServiceImpl organizationDeleteUploadCustomValidationServiceImpl;
	
	@Autowired
	private  OrganizationDeleteUploadWriterProcessServiceImpl organizationDeleteUploadWriterProcessServiceImpl;
	
	@Autowired
	private  EnrollmentUploadWriterProcessServiceImpl enrollmentUploadWriterProcessServiceImpl;
	
	@Autowired
	private  RosterUploadCustomValidationServiceImpl rosterUploadCustomValidationServiceImpl;
	
	@Autowired
	private  RosterUploadWriterProcessServiceImpl rosterUploadWriterProcessServiceImpl;
	
	@Autowired
	private TECUploadCustomValidationServiceImpl tecUploadCustomValidationServiceImpl;
	
	@Autowired
	private TECUploadWriterProcessServiceImpl tecUploadWriterProcessServiceImpl;
	
	@Autowired
	private ProjectedTestingUploadCustomValidationServiceImpl projectedTestingUploadCustomValidationServiceImpl;
	
	@Autowired
	private ProjectedTestingUploadWriterProcessServiceImpl projectedTestingUploadWriterProcessServiceImpl;
	
	@Autowired
	private ScoringUploadCustomValidationServiceImpl scoringUploadCustomValidationServiceImpl;
	
	@Autowired
	private ScoringUploadWriterProcessServiceImpl scoringUploadWriterProcessServiceImpl;
	
	@Autowired
	private SubScoreDefaultStageIdsCustomValidatonServiceImpl subscoreDefaultStageIdsCustomValidationServiceImpl;
	
	@Autowired
	private SubScoreDefaultStageIdsWriterProcessServiceImpl subScoreDefaultStageIdsWriterProcessServiceImpl;
	
	@Autowired
	private ExternalReportImportCustomValidationServiceImpl externalReportImportCustomValidationServiceImpl;
	
	@Autowired
	private ExternalReportImportWriter externalReportImportWriter;
	
	@Autowired
	private  UploadIncidentFileDefaultCustomValidatonServiceImpl UploadIncidentFileDefaultCustomValidatonServiceImpl;	
	
	@Autowired
	private UploadIncidentFileDefaultWriterProcessServiceImpl uploadIncidentFileWriterProcessServiceImpl;
	
	@Autowired
	private  UploadScCodeFileDefaultCustomValidatonServiceImpl UploadScCodeFileDefaultCustomValidatonServiceImpl;	
	
	@Autowired
	private UploadScCodeFileDefaultWriterProcessServiceImpl uploadScCodeFileWriterProcessServiceImpl;
	
	@Autowired
	private  UploadGrfFileDefaultCustomValidatonServiceImpl UploadGrfFileDefaultCustomValidatonServiceImpl;	
	
	@Autowired
	private UploadGrfFileDefaultWriterProcessServiceImpl uploadGrfFileWriterProcessServiceImpl;
		
	@Value("${questionInformationRecordType}")
	private String questionInformationRecordType;
	
	@Autowired
	private QuestionInformationCustomValidationServiceImpl  questionInformationCustomValidationServiceImpl;
	
	@Autowired
	private QuestionInformationUploadWriterProcessServiceImpl  questionInformationUploadWriterProcessServiceImpl;
	
	@Autowired
	private UploadAssessmentTopicCustomValidationServiceImpl  uploadAssessmentTopicCustomValidationServiceImpl;
	
	@Autowired
	private UploadAssessmentTopicDefaultWriterProcessServiceImpl  uploadAssessmentTopicWriterProcessServiceImpl;
	
	@Autowired
	private UploadExternalOrgReportResultCustomValidationServiceImpl uploadExternalOrgReportResultCustomValidationServiceImpl;
	
	@Autowired
	private UploadExternalOrgReportResultDefaultWriterProcessServiceImpl  uploadExternalOrgReportResultDefaultWriterProcessServiceImpl;
	
	@Autowired
	private UploadOrgPrctByAssessmentTopicCustomValidationServiceImpl uploadOrgPrctByAssessmentTopicCustomValidationServiceImpl;
	
	@Autowired
	private UploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl  uploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl;
	
	@Autowired
	private UploadExternalStudentReportResultsCustomValidationServiceImpl uploadExternalStudentReportResultsCustomValidationServiceImpl;
	
	@Autowired
	private UploadExternalStudentReportResultsDefaultWriterProcessServiceImpl  uploadExternalStudentReportResultsDefaultWriterProcessServiceImpl;
	
	@Autowired
	private UploadStudentPrctByAssessmentTopicCustomValidationServiceImpl uploadStudentPrctByAssessmentTopicCustomValidationServiceImpl;
	
	@Autowired
	private UploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl  uploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl;
	
	@Autowired
	private UploadPermissionCustomValidationServiceImpl uploadPermissionCustomValidationServiceImpl;
	
	@Autowired
	private UploadPermissionDefaultWriterProcessServiceImpl  uploadPermissionDefaultWriterProcessServiceImpl;
	
	
	@Autowired
	private PNPUploadCustomValidationServiceImpl pnpUploadCustomValidationServiceImpl;
	
	@Autowired
	private PNPUploadWriterProcessServiceImpl pnpUploadWriterProcessServiceImpl;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private StateSpecificFileDao stateSpecificFilesDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertBatchUpload(BatchUpload record) {
		record.setCreatedDate(new Date());
		return batchUploadDao.insert(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelectiveBatchUpload(BatchUpload record) {
		record.setCreatedDate(new Date());
		return batchUploadDao.insertSelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeyBatchUpload(BatchUpload record) {
		record.setModifiedDate(new Date());
		return batchUploadDao.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelectiveBatchUpload(BatchUpload record) {
		record.setModifiedDate(new Date());
		return batchUploadDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public BatchUpload selectByPrimaryKeyBatchUpload(long id) {
		return batchUploadDao.selectByPrimaryKey(id);
	}

	@Override
	public List<BatchUpload> selectByAssessmentProgramIdsAndFiltersBatchUpload(List<Long> assessmentProgramIds,
			String orderByColumn, String order, Integer limit, Integer offset,List<Long> typeIds) {
		return batchUploadDao.selectByAssessmentProgramIdsAndFilters(assessmentProgramIds, orderByColumn, order, limit, offset,typeIds);
	}
	
	@Override
	public int getUploadCountByAssessmentProgramIdsAndFilters(List<Long> assessmentProgramIds,List<Long> typeIds) {
		return batchUploadDao.getUploadCountByAssessmentProgramIdsAndFilters(assessmentProgramIds,typeIds);
	}
	
	@Override
	public int selectDuplicateCountBatchUpload(Long assessmentProgramId, Long contentAreaId, Long uploadTypeId, Integer schoolYear, Long testingProgramId, String reportCycle) {
		return batchUploadDao.selectDuplicateCount(assessmentProgramId, contentAreaId, uploadTypeId, schoolYear, testingProgramId, reportCycle);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updatePreviousToInactiveBatchUpload(Long assessmentProgramId,
			Long contentAreaId, Long uploadTypeId, Integer schoolYear, Long reportYear, Long stateId, Long testingProgramId, String reportCycle) {
		return batchUploadDao.updatePreviousToInactive(assessmentProgramId, contentAreaId, uploadTypeId, schoolYear,reportYear,stateId, testingProgramId, reportCycle);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> validateProcess(BeanWrapperFieldSetMapper beanFieldSetMapper, BatchUploadValidator uploadFileValidator,
				FieldSet fieldSet, Map<String, Object> params, Map<String, String> mappedFieldNames) throws BindException {
		logger.debug("Inside validateProcess");
		Map<String, Object> validateMap = new HashMap<String, Object>();
		Object domainObject = null;
		Object customValidationObj = null;
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		if(uploadTypeCode.equalsIgnoreCase(excludedItemsRecordType)){
			domainObject = new ExcludedItems();
			beanFieldSetMapper.setTargetType(ExcludedItems.class);
			customValidationObj = excludedItemCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(rawToScaleScoresRecordType)){
			domainObject = new RawToScaleScores();
			beanFieldSetMapper.setTargetType(RawToScaleScores.class);
			customValidationObj = rawToScaleScoresCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(testCutScoresRecordType) || uploadTypeCode.equalsIgnoreCase(cpassTestCutScoreRecordType)){
			domainObject = new TestCutScores();
			beanFieldSetMapper.setTargetType(TestCutScores.class);
			customValidationObj = testCutScoresCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(levelDescriptionsRecordType)||uploadTypeCode.equalsIgnoreCase(cpassLevelDiscriptionRecordType)){
			domainObject = new LevelDescription();
			beanFieldSetMapper.setTargetType(LevelDescription.class);
			customValidationObj = levelDescriptionCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(miscellaneousReportTextRecordType)){
			
		} else if(uploadTypeCode.equalsIgnoreCase(questionInformationRecordType)){
			domainObject = new QuestionInformation();
			beanFieldSetMapper.setTargetType(QuestionInformation.class);
			customValidationObj = questionInformationCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreDescriptionReportUsageRecordType)){
			domainObject = new SubscoresDescription();
			beanFieldSetMapper.setTargetType(SubscoresDescription.class);
			customValidationObj = subscoresDescriptionCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreFrameworkMappingRecordType)){
			domainObject = new SubscoreFramework();
			beanFieldSetMapper.setTargetType(SubscoreFramework.class);
			customValidationObj = subscoreFrameworkCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreRawToScaleScoreRecordType)){
			domainObject = new SubscoreRawToScaleScores();
			beanFieldSetMapper.setTargetType(SubscoreRawToScaleScores.class);
			customValidationObj = subscoreRawToScaleScoresCustomValidationServiceImpl;
			
		} else if(uploadTypeCode.equalsIgnoreCase(userUploadRecordType)){
			/** Added by Prasanth: User story :US16352: User Upload 
			 */
			domainObject = new UploadedUser();
			beanFieldSetMapper.setTargetType(UploadedUser.class);
			customValidationObj = userUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(orgUploadRecordType)){
			/** Added by Prasanth: User story :US16352: Organization Upload 
			 */
			domainObject = new UploadedOrganization();
			beanFieldSetMapper.setTargetType(UploadedOrganization.class);
			customValidationObj = organizationUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(enrollmentRecordType) ||
			uploadTypeCode.equalsIgnoreCase(xmlEnrollmentRecordType)){
			/** Added by Prasanth: User story :US16352: Enrollment Upload 
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new EnrollmentRecord();
			beanFieldSetMapper.setTargetType(EnrollmentRecord.class);
			customValidationObj = enrollmentUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(xmlUnEnrollmentRecordType)){
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new EnrollmentRecord();
			beanFieldSetMapper.setTargetType(EnrollmentRecord.class);
			customValidationObj = unEnrollmentUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(xmlLeaRecordType) || uploadTypeCode.equalsIgnoreCase(xmlSchoolRecordType)){
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
		    domainObject = new UploadedOrganization();
			beanFieldSetMapper.setTargetType(UploadedOrganization.class);
			customValidationObj = organizationUploadCustomValidationServiceImpl;
		}  else if (uploadTypeCode.equalsIgnoreCase(xmlDeleteLeaRecordType) || uploadTypeCode.equalsIgnoreCase(xmlDeleteSchoolRecordType)) {
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
		    domainObject = new UploadedOrganization();
			beanFieldSetMapper.setTargetType(UploadedOrganization.class);
			customValidationObj = organizationDeleteUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(scrsRecordType) ||
			uploadTypeCode.equalsIgnoreCase(xmlRosterRecordType)){
			/** Added by Prasanth: User story :US16352: Roster Upload 
			 */
			domainObject = new RosterRecord();
			beanFieldSetMapper.setTargetType(RosterRecord.class);
			customValidationObj = rosterUploadCustomValidationServiceImpl;
		} else if (uploadTypeCode.equalsIgnoreCase(tecRecordType)
				|| uploadTypeCode.equalsIgnoreCase(tecXMLRecordType)) {
			/** Added by Prasanth: User story :US16352: Student TEC Upload 
			 */
			domainObject = new TecRecord();
			beanFieldSetMapper.setTargetType(TecRecord.class);
			customValidationObj = tecUploadCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(combinedLevelMapRecordType)){
			domainObject = new CombinedLevelMap();
			beanFieldSetMapper.setTargetType(CombinedLevelMap.class);
			customValidationObj = combinedLevelMapCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(projectedTesingRecordType)){
			domainObject = new ProjectedTesting();
			beanFieldSetMapper.setTargetType(ProjectedTesting.class);
			customValidationObj = projectedTestingUploadCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(scoringRecordType)){
			domainObject = new UploadedScoringRecord();
			beanFieldSetMapper.setTargetType(UploadedScoringRecord.class);
			customValidationObj = scoringUploadCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if(uploadTypeCode.equalsIgnoreCase(subScoreDefaultStageIdRecordType)) {
			domainObject = new SubScoresMissingStages();
			beanFieldSetMapper.setTargetType(SubScoresMissingStages.class);
			customValidationObj = subscoreDefaultStageIdsCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if (uploadTypeCode.equalsIgnoreCase(studentReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(studentSummaryReportsImportRecordType)
				||uploadTypeCode.equalsIgnoreCase(studentDcpsReportsImportRecordType) ) {
			domainObject = new StudentReportsImportDTO();
			beanFieldSetMapper.setTargetType(StudentReportsImportDTO.class);
			customValidationObj = externalReportImportCustomValidationServiceImpl;
		}else if (uploadTypeCode.equalsIgnoreCase(schoolReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(classroomReportsImportRecordType)
				||uploadTypeCode.equalsIgnoreCase(schoolCsvReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(classroomCsvReportsImportRecordType)) {
			domainObject = new OrganizationReportsImportDTO();
			beanFieldSetMapper.setTargetType(OrganizationReportsImportDTO.class);
			customValidationObj = externalReportImportCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(uploadIncidentFileType)) {
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new UploadIncidentFile();
			beanFieldSetMapper.setTargetType(UploadIncidentFile.class);
			customValidationObj = UploadIncidentFileDefaultCustomValidatonServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if(uploadTypeCode.equalsIgnoreCase(uploadKansasScCodeFileType)||uploadTypeCode.equalsIgnoreCase(uploadCommonScCodeFileType)) { 
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new UploadScCodeFile();
			beanFieldSetMapper.setTargetType(UploadScCodeFile.class);
			customValidationObj = UploadScCodeFileDefaultCustomValidatonServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if(uploadTypeCode.equalsIgnoreCase(uploadCommonGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadIowaGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadNewYorkGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadDelawareGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadDCGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadArkansasGrfFileType)) { 
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new UploadGrfFile();
			beanFieldSetMapper.setTargetType(UploadGrfFile.class);
			customValidationObj = UploadGrfFileDefaultCustomValidatonServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if(uploadTypeCode.equalsIgnoreCase(uploadAssessmentTopicType)) { 
			/** Added by Uday: Feature :F97: CPASS reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new AssessmentTopic();
			beanFieldSetMapper.setTargetType(AssessmentTopic.class);
			customValidationObj = uploadAssessmentTopicCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}
		else if(uploadTypeCode.equalsIgnoreCase(uploadOrganizationScoreCaluculations)) { 
			/** Added by Uday: Feature :F97: CPASS reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new OrganizationReportResults();
			beanFieldSetMapper.setTargetType(OrganizationReportResults.class);
			customValidationObj = uploadExternalOrgReportResultCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}
		else if(uploadTypeCode.equalsIgnoreCase(uploadOrganizationPCTByAssessmentTopic)) { 
			/** Added by Uday: Feature :F97: CPASS reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new OrganizationPrctByAssessmentTopic();
			beanFieldSetMapper.setTargetType(OrganizationPrctByAssessmentTopic.class);
			customValidationObj = uploadOrgPrctByAssessmentTopicCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}
		else if(uploadTypeCode.equalsIgnoreCase(uploadStudentScoreCaluculations)) { 
			/** Added by Uday: Feature :F97: CPASS reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new ExternalStudentReportResults();
			beanFieldSetMapper.setTargetType(ExternalStudentReportResults.class);
			customValidationObj = uploadExternalStudentReportResultsCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		}else if(uploadTypeCode.equalsIgnoreCase(uploadStudentPCTByAssessmentTopic)) { 
			/** Added by Uday: Feature :F97: CPASS reports  
			 */
			HashMap map = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    sdf.setLenient(false);
		    map.put("java.util.Date", new org.springframework.beans.propertyeditors.CustomDateEditor(sdf,true));
		    beanFieldSetMapper.setCustomEditors(map);
			domainObject = new StudentPrctByAssessmentTopic();
			beanFieldSetMapper.setTargetType(StudentPrctByAssessmentTopic.class);
			customValidationObj = uploadStudentPrctByAssessmentTopicCustomValidationServiceImpl;			
			params.put("Properties", fieldSet.getProperties());
		} else if (uploadTypeCode.equalsIgnoreCase(uploadStudentPNPRecordType)) {
			domainObject = new PNPUploadRecord();
			beanFieldSetMapper.setTargetType(PNPUploadRecord.class);
			customValidationObj = pnpUploadCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(permissionRecordType)) {
			/** Added by Uday: Feature :F885: Permissions Upload  
			 */
			domainObject = new UploadedPermissionRecord();
			beanFieldSetMapper.setTargetType(UploadedPermissionRecord.class);
			customValidationObj = uploadPermissionCustomValidationServiceImpl;
			params.put("Properties", fieldSet.getProperties());
		}
		
		
		BeanPropertyBindingResult validationErrors = new BeanPropertyBindingResult(domainObject, "batchUpload");
		commonPrimaryValidation(validationErrors, uploadFileValidator, fieldSet);
        List<ObjectError> errorList = validationErrors.getAllErrors();
		if(errorList.size() > 0){
			logger.debug("Common validation failed." + errorList.toString());
			validateMap.put("errors", errorList);
		}else{
			logger.debug("Starting custom validation process after common validation passed.");
			beanFieldSetMapper.setDistanceLimit(4);
			validateMap = customValidation(customValidationObj, validationErrors, beanFieldSetMapper.mapFieldSet(fieldSet), params, mappedFieldNames);
			errorList = (List<ObjectError>)validateMap.get("errors");
			if(null != errorList && errorList.size() > 0)
			{
				logger.debug("Custom validation failed." + errorList.toString());
			}
		}
		return validateMap;
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> validateProcessForAlertMessage(
			BeanWrapperFieldSetMapper beanFieldSetMapper,
			BatchUploadValidator uploadFileValidator, FieldSet fieldSet,
			Map<String, Object> params, Map<String, String> mappedFieldNames)throws BindException{
		
		logger.debug("Inside validateProcessForAlertMessage");
		Map<String, Object> validateMap = new HashMap<String, Object>();
		Object domainObject = null;
		Object customValidationObj = null;
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		
		if(uploadTypeCode.equalsIgnoreCase(enrollmentRecordType) ||
			uploadTypeCode.equalsIgnoreCase(xmlEnrollmentRecordType)){			
			domainObject = new EnrollmentRecord();
			beanFieldSetMapper.setTargetType(EnrollmentRecord.class);
			customValidationObj = enrollmentUploadCustomValidationServiceImpl;
		} else if(uploadTypeCode.equalsIgnoreCase(xmlUnEnrollmentRecordType)){			
				domainObject = new EnrollmentRecord();
				beanFieldSetMapper.setTargetType(EnrollmentRecord.class);
				customValidationObj = unEnrollmentUploadCustomValidationServiceImpl;
		}else if(uploadTypeCode.equalsIgnoreCase(xmlLeaRecordType) || uploadTypeCode.equalsIgnoreCase(xmlSchoolRecordType)){			
				domainObject = new UploadedOrganization();
				beanFieldSetMapper.setTargetType(UploadedOrganization.class);
				customValidationObj = organizationUploadCustomValidationServiceImpl;
		}/*else if(uploadTypeCode.equalsIgnoreCase(scoringRecordType)){
			domainObject = new UploadedScoringRecord();
			beanFieldSetMapper.setTargetType(UploadedScoringRecord.class);
			customValidationObj = scoringUploadCustomValidationServiceImpl;			
		}*/
		BeanPropertyBindingResult validationErrors = new BeanPropertyBindingResult(domainObject, "batchUpload");	
		if(customValidationObj != null){		
			validateMap = customValidationForAlertMessage(customValidationObj, validationErrors, beanFieldSetMapper.mapFieldSet(fieldSet), params, mappedFieldNames);
		    List<ObjectError> errorList = (List<ObjectError>)validateMap.get("errors");		
			if(null != errorList && errorList.size() > 0){
				logger.warn("Alert Message." + errorList.toString());
			}
		}	
	 return validateMap;
		
	}
	
	
	//added during US16966 - To add alert message to upload
	private Map<String, Object> customValidationForAlertMessage(Object customValidationObj ,BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {

		if(customValidationObj != null){
			BatchUploadCustomValidationForAlertService batchUploadCustomValidationService = (BatchUploadCustomValidationForAlertService) customValidationObj;
			logger.debug("Invoking respective custom validation fro alert message Process for upload.");
			return batchUploadCustomValidationService.customValidationForAlert(validationErrors, rowData, params, mappedFieldNames);
		}
		return null;
	}

	@Override
	public void writeProcess(List<? extends Object> objects, String uploadTypeCode) {
		if(uploadTypeCode.equalsIgnoreCase(excludedItemsRecordType)){
			writerProcess(excludedItemUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(rawToScaleScoresRecordType)){
			writerProcess(rawToScaleScoresUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(testCutScoresRecordType)|| uploadTypeCode.equalsIgnoreCase(cpassTestCutScoreRecordType)){
			writerProcess(testCutScoresUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(levelDescriptionsRecordType)||uploadTypeCode.equalsIgnoreCase(cpassLevelDiscriptionRecordType)){
			writerProcess(levelDescriptionUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(miscellaneousReportTextRecordType)){
			
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreDescriptionReportUsageRecordType)){
			writerProcess(subscoresDescriptionUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreFrameworkMappingRecordType)){
			writerProcess(subscoreFrameworkUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(subscoreRawToScaleScoreRecordType)){
			writerProcess(subscoreRawToScaleScoresUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(userUploadRecordType)){
			writerProcess(userUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(orgUploadRecordType) ||
				uploadTypeCode.equalsIgnoreCase(xmlLeaRecordType) || 
				uploadTypeCode.equalsIgnoreCase(xmlSchoolRecordType)){
			writerProcess(organizationUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(enrollmentRecordType) || uploadTypeCode.equalsIgnoreCase(xmlEnrollmentRecordType)){
			writerProcess(enrollmentUploadWriterProcessServiceImpl, objects);
		} else if (uploadTypeCode.equalsIgnoreCase(xmlUnEnrollmentRecordType)) {
			writerProcess(unEnrollmentUploadWriterProcessServiceImpl, objects);
		} else if (uploadTypeCode.equalsIgnoreCase(xmlDeleteLeaRecordType) || uploadTypeCode.equalsIgnoreCase(xmlDeleteSchoolRecordType)) {
			writerProcess(organizationDeleteUploadWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(scrsRecordType) || uploadTypeCode.equalsIgnoreCase(xmlRosterRecordType)){
			writerProcess(rosterUploadWriterProcessServiceImpl, objects);
		} else if (uploadTypeCode.equalsIgnoreCase(tecRecordType)
				|| uploadTypeCode.equalsIgnoreCase(tecXMLRecordType)) {
			writerProcess(tecUploadWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(combinedLevelMapRecordType)){
			writerProcess(combinedLevelMapUploadWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(projectedTesingRecordType)){
			writerProcess(projectedTestingUploadWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(scoringRecordType)){
			writerProcess(scoringUploadWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equals(subScoreDefaultStageIdRecordType)){
			writerProcess(subScoreDefaultStageIdsWriterProcessServiceImpl, objects);
		}else if (uploadTypeCode.equals(studentReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(studentSummaryReportsImportRecordType)
				|| uploadTypeCode.equals(schoolReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(classroomReportsImportRecordType)
				|| uploadTypeCode.equals(schoolCsvReportsImportRecordType)||uploadTypeCode.equalsIgnoreCase(classroomCsvReportsImportRecordType)
				||uploadTypeCode.equals(studentDcpsReportsImportRecordType)){
			writerProcess(externalReportImportWriter, objects);
		}else if(uploadTypeCode.equals(uploadIncidentFileType)){
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			writerProcess(uploadIncidentFileWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadKansasScCodeFileType)||uploadTypeCode.equalsIgnoreCase(uploadCommonScCodeFileType)) { 
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			writerProcess(uploadScCodeFileWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadCommonGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadIowaGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadNewYorkGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadDelawareGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadDCGrfFileType)
				||uploadTypeCode.equalsIgnoreCase(uploadArkansasGrfFileType)) { 
			/** Added by Uday: Feature :F458: DLM reports  
			 */
			writerProcess(uploadGrfFileWriterProcessServiceImpl, objects);
		} else if(uploadTypeCode.equalsIgnoreCase(questionInformationRecordType)){			
			writerProcess(questionInformationUploadWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadAssessmentTopicType)){
			/** Added by Uday: Feature :F97: DLM reports  
			 */
			writerProcess(uploadAssessmentTopicWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadOrganizationScoreCaluculations)){
			/** Added by Uday: Feature :F97: DLM reports  
			 */
			writerProcess(uploadExternalOrgReportResultDefaultWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadStudentPCTByAssessmentTopic)){
			/** Added by Uday: Feature :F97: DLM reports  
			 */
			writerProcess(uploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl, objects);
		}else if(uploadTypeCode.equalsIgnoreCase(uploadOrganizationPCTByAssessmentTopic)){
			/** Added by Uday: Feature :F97: DLM reports  
			 */
			writerProcess(uploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl, objects);
		}
		else if(uploadTypeCode.equalsIgnoreCase(uploadStudentScoreCaluculations)){
			/** Added by Uday: Feature :F97: DLM reports  
			 */
			writerProcess(uploadExternalStudentReportResultsDefaultWriterProcessServiceImpl, objects);
		} else if (uploadTypeCode.equalsIgnoreCase(uploadStudentPNPRecordType)) {
			writerProcess(pnpUploadWriterProcessServiceImpl, objects);
		}
		else if (uploadTypeCode.equalsIgnoreCase(permissionRecordType)) {
			writerProcess(uploadPermissionDefaultWriterProcessServiceImpl, objects);
		}
	}

	private BeanPropertyBindingResult commonPrimaryValidation(BeanPropertyBindingResult validationErrors, BatchUploadValidator uploadFileValidator, FieldSet fieldSet){
        ValidationUtils.invokeValidator(uploadFileValidator, fieldSet, validationErrors);
   		return validationErrors;
	}

	public Map<String, Object> customValidation(Object customValidationObj ,BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) 
    {
		if(customValidationObj != null){
			BatchUploadCustomValidationService batchUploadCustomValidationService = (BatchUploadCustomValidationService) customValidationObj;
			logger.debug("Invoking respective custom validation Process for upload.");
			return batchUploadCustomValidationService.customValidation(validationErrors, rowData, params, mappedFieldNames);
		}
		return null;
    }
	
	public void writerProcess(Object writerProcessObj , List<? extends Object> objects) 
    {
		if(writerProcessObj != null){
			BatchUploadWriterService batchUploadWriterProcessService = (BatchUploadWriterService) writerProcessObj;
			logger.debug("Invoking respective Write Process for upload.");
			batchUploadWriterProcessService.writerProcess(objects);
		}
    }
	
	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public void insertBatchUploadReasons(List<BatchUploadReason> uploadBatchReasons) {
		for (BatchUploadReason reason : uploadBatchReasons) {
			batchUploadReasonMapper.insertSelective(reason);
		}
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public List<BatchUploadReason> findBatchUploadReasonsForId(Long batchUploadId) {
		return batchUploadReasonMapper.selectByBatchUploadId(batchUploadId);
	}
	/** Added for US16252
	 * 
	 */
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public List<BatchUploadReason> find100BatchUploadReasonsForId(Long batchUploadId) {
		return batchUploadReasonMapper.select100ByBatchUploadId(batchUploadId);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public BatchUpload selectOnePending(String status, String uploadType) {
		CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeEqualTo(uploadType);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        List<Category> categories = categoryDao.selectByCategoryAndType(categoryExample);
        
		return batchUploadDao.selectOnePending(status, categories);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public List<Long> findFrameWorkTypeForAssessmentProgram(Long assessmentProgramId, String frameworkCode) {
		return contentFrameworkDetailDao.selectFrameWorkTypeForAssessmentProgram(assessmentProgramId, frameworkCode);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public Long findContentFrameworkCodeWithLevelAssessmentGradeContentarea(Long assessmentProgramId, Long gradeId, 
			Long contentAreaId, List<Long> frameworkTypeIds, String levelTitle, String contentCode) {
		return contentFrameworkDetailDao.selectContentFrameworkCodeWithLevelAssessmentGradeContentarea(assessmentProgramId, gradeId, contentAreaId, frameworkTypeIds, levelTitle, contentCode);
	}
	/* Added for US16548*/
	@Override
	public List<BatchUploadInfo> selectByCategoryCodeBatchUpload(Long userId, String categoryCode,Long userGroupId, Long userOrgId) {
		Long uploadTypeId = categoryDao.getCategoryId(categoryCode, "CSV_RECORD_TYPE");
		return batchUploadDao.selectByCategoryCodeBatchUpload(userId,uploadTypeId,userGroupId, userOrgId);
	}
	
	@Override
	public List<BatchUpload> selectuploadResultsByAssessmentProgramIdsAndFiltersBatchUpload(
			Long assessmentProgramId,List userStates, String orderByColumn, String order,
			int limitCount, int offset){
		return batchUploadDao.selectUploadResultsByAssessmentProgramIdsAndFilters(assessmentProgramId,userStates, orderByColumn, order, limitCount, offset);
	}
	
	@Override
	public int checkForInProgressUpload(Long assessmentProgramId, Long stateId,
			Long fileTypeId, Integer reportYear,Long contentAreaId, Long testingProgramId, String reportCycle){
		return batchUploadDao.checkForInProgressUpload(assessmentProgramId, stateId, fileTypeId, reportYear, contentAreaId,  testingProgramId,  reportCycle);
	}
	
	@Override
	public BeanPropertyBindingResult externalResultsUploadCommonValidation(
			ExternalUploadResult externalUploadResult,
			BeanPropertyBindingResult validationErrors,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		String lineNumber = externalUploadResult.getLineNumber();
		Long stateIdOnUI = (Long) params.get("stateId");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		Long testingProgramIdOnUI = (Long) params.get("testingProgramIdOnUI");
		String testingProgramOnUI = (String) params.get("testingProgramNameOnUI");
		String ReportCycleOnUI = (String) params.get("reportCycleOnUI");
		Long selectedReportyear = (Long) params.get("reportYear");
		
		//validate report cycle
		if(externalUploadResult.getReportCycle() == null || !externalUploadResult.getReportCycle().equalsIgnoreCase(ReportCycleOnUI)){
			String errMsg = "Report Cycle is not matching with upload page Report Cycle selection. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);
		}else{
			//Validate Testing Program
			if(!testingProgramOnUI.equalsIgnoreCase(externalUploadResult.getTestingProgram())){
				String errMsg = "Testing Program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("testingProgram", "", new String[]{lineNumber, mappedFieldNames.get("testingProgram")}, errMsg);
			}else{
			//Validate state
			Organization stateOrg = null;
			if("All".equalsIgnoreCase(externalUploadResult.getState())){
			   stateOrg = organizationService.getByDisplayIdentifierAndType(externalUploadResult.getState().toUpperCase(),"ST");
			}else{				
			   stateOrg = organizationService.get(stateIdOnUI);
			}
			if(stateOrg == null || !externalUploadResult.getState().equalsIgnoreCase(stateOrg.getDisplayIdentifier()) 
					|| ("All".equalsIgnoreCase(externalUploadResult.getState()) && "student".equalsIgnoreCase(externalUploadResult.getUploadLevel()))){
				String errMsg = "State is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")}, errMsg);
			}else{					
				externalUploadResult.setStateInternalId(stateOrg.getId());
				//validate District
				List<Organization> districtOrgs = organizationService.getByDisplayIdentifierAndParent_ActiveOrInactive(externalUploadResult.getDistrictIdentifier(), stateOrg.getId());
				if(districtOrgs.size() == 0 && !"All".equalsIgnoreCase(externalUploadResult.getState()) 
						&& (StringUtils.isNotEmpty(externalUploadResult.getDistrictIdentifier()) || "All".equalsIgnoreCase(externalUploadResult.getDistrictIdentifier()))){
					String errMsg = "District is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("districtIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("districtIdentifier")}, errMsg);
				}else{
					externalUploadResult.setDistrictInternalId(districtOrgs.size() == 0? null: districtOrgs.get(0).getId());
					externalUploadResult.setDistrictName(districtOrgs.size() == 0? null :districtOrgs.get(0).getOrganizationName());
					//Validate School
					List<Organization> schoolOrgs= organizationService.getByDisplayIdentifierAndParent_ActiveOrInactive(externalUploadResult.getSchoolIdentifier(), externalUploadResult.getDistrictInternalId() == null ?0:externalUploadResult.getDistrictInternalId());
					if (schoolOrgs.size() == 0 && !"All".equalsIgnoreCase(externalUploadResult.getState()) 
							&& (StringUtils.isNotEmpty(externalUploadResult.getSchoolIdentifier()) || "All".equalsIgnoreCase(externalUploadResult.getSchoolIdentifier()))){
						String errMsg = "School is invalid. ";
						logger.debug(errMsg);
						validationErrors.rejectValue("schoolIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("schoolIdentifier")}, errMsg);
					}else{
						externalUploadResult.setSchoolInternalId(schoolOrgs.size() == 0? null: schoolOrgs.get(0).getId());
						externalUploadResult.setSchoolName(schoolOrgs.size() == 0? null: schoolOrgs.get(0).getOrganizationName());	
						//Validate Subject
						ContentArea subject = contentAreaService.selectByPrimaryKey(subjectId);
						if(subject == null || !subject.getAbbreviatedName().equalsIgnoreCase(externalUploadResult.getSubject())){
							String errMsg = "Subject is not matching with upload page Subject selection. ";
							logger.debug(errMsg);
							validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
						}else{
							externalUploadResult.setSubjectId(subject.getId());
							//Validate Grade
							GradeCourse gc = gradeCourseService.selectGradeByAbbreviatedNameAndContentAreaId(externalUploadResult.getGrade(), subject.getId());
							if (gc == null){
								String errMsg = "Grade is invalid. ";
								logger.debug(errMsg);
								validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
							}else{
								externalUploadResult.setGradeId(gc.getId());
								//Validate TestType
								List<TestType> testType = testTypeService.getCPASSTestTypesForReportsByTestTypeCode(testingProgramIdOnUI, externalUploadResult.getTestType());
								if(testType == null || testType.size() == 0){
									String errMsg = "TestType is invalid. ";
									logger.debug(errMsg);
									validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
								}else{
									if(!externalUploadResult.getSubject().equalsIgnoreCase(testType.get(0).getSubjectCode())
											|| !externalUploadResult.getGrade().equalsIgnoreCase(testType.get(0).getGradeCode())){
										String errMsg = "Comination of Test_Type, Subject and grade is Invalid.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
							        }else{
							        	externalUploadResult.setAssessmentName(testType.get(0).getTestTypeName());
							        	externalUploadResult.setTestingProgramInternalId(testingProgramIdOnUI);
							        	//Validate school Year
							        	if(externalUploadResult.getSchoolYear().longValue() != selectedReportyear.longValue()){
							        		String errMsg = "School Year is not matching with the selection at upload page.";
											logger.debug(errMsg);
											validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
							        	}
							        }								
							   }
						   }
					   }
				   }
			   }
		    }
		  }
		}
		return validationErrors;
	}

	@Override
	public List<BatchUpload> selectGRFProcessStatusBYStateId(
			Long assessmentProgramId, long contractingOrgId, Integer reportYear, List<String> grfProcessTypes,
			String orderByColumn, String order, int limitCount, int offset) {
		
		return batchUploadDao.selectGRFProcessStatusBYStateId(assessmentProgramId, contractingOrgId, reportYear, grfProcessTypes,
				orderByColumn, order, limitCount, offset);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int saveCustomFileData(StateSpecificFile stateSpecificFiles) {
		stateSpecificFiles.setCreatedDate(new Date());
		stateSpecificFiles.setModifiedDate(new Date());
		return stateSpecificFilesDao.insertStateSpecificFileData(stateSpecificFiles);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StateSpecificFile> getStateSpecificFileData(Long assessmentProgramId, Long stateId, String sortByColumn,
			String sortType, Integer offset, Integer limitCount, Map<String, String> recordCriteriaMap) {
		return stateSpecificFilesDao.getStateSpecificFileData(assessmentProgramId, stateId, sortByColumn, sortType,
				offset, limitCount, recordCriteriaMap);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int removeStateSpecificFile(Long stateSpecificFileId, Long modifiedUserId) {
		return stateSpecificFilesDao.removeStateSpecificFile(stateSpecificFileId, modifiedUserId, new Date());
	}
	
	@Override
	public int checkForInProgressGrfUploadOrReport(Long assessmentProgramId, Long stateId, Integer reportYear){
		return batchUploadDao.checkForInProgressGrfUploadOrReport(assessmentProgramId, stateId, reportYear);
	}

	@Override
	public void updateGrfBatchUpload(Long batchuploadid) {
		BatchUpload upload = new BatchUpload();
		upload.setId(batchuploadid);
		upload.setStatus("COMPLETED");
		upload.setFailedCount(0);
		upload.setModifiedDate(new Date());
		batchUploadDao.updateByPrimaryKeySelective(upload);
	}

	@Override
	public BatchUpload latestGrfBatchUpload(Long stateId,String grfProcessType) {
		
		return batchUploadDao.latestGrfBatchUpload(stateId,grfProcessType);
	}
	
	@Override
	public int checkForInProgressInSpecialCircumstanceAndExitedStudents(Long assessmentProgramId, Long stateId, Integer reportYear, String grfProcessType){
		return batchUploadDao.checkForInProgressInSpecialCircumstanceAndExitedStudents(assessmentProgramId, stateId, reportYear, grfProcessType);
	}
}