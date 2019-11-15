package edu.ku.cete.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.domain.BluePrintCriteriaDescription;
import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.ProfileAttribute;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.professionaldevelopment.DataDetailDto;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.report.KELPAReport;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.BluePrintMapper;
import edu.ku.cete.model.CcqScoreMapper;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.model.ItiTestSessionHistoryMapper;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StateSpecificFileDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.TestingCycleMapper;
import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.professionaldevelopment.ModuleReportMapper;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.StateSpecificFile;
import edu.ku.cete.report.model.ReportGenericMapper;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.KSDEReturnFileService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ProfileAttributeContainerService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.report.AmpDataExtractService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.ColorNameUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.util.json.JavaScriptEngineAnonymousFunctionHelper;
import edu.ku.cete.web.AccessibilityExtractDTO;
import edu.ku.cete.web.AmpExtractStudentProfileItemAttributeDTO;
import edu.ku.cete.web.AmpExtractStudentSujectSectionItemCountDTO;
import edu.ku.cete.web.AmpStudentDataExtractDTO;
import edu.ku.cete.web.BlueprintCriteriaAndEEDTO;
import edu.ku.cete.web.DLMBlueprintCoverageReportDTO;
import edu.ku.cete.web.DLMBlueprintCoverageReportStudentTestsCriteriaDTO;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO;
import edu.ku.cete.web.DLMTestStatusExtractDTO;
import edu.ku.cete.web.FCSAnswer;
import edu.ku.cete.web.FCSDataExtractDTO;
import edu.ku.cete.web.FCSHeader;
import edu.ku.cete.web.ISMARTTestAdminExtractDTO;
import edu.ku.cete.web.ITIBPCoverageExtractRostersDTO;
import edu.ku.cete.web.ITIBPCoverageRosteredStudentsDTO;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.KELPA2StudentScoreDTO;
import edu.ku.cete.web.KELPATestAdministrationDTO;
import edu.ku.cete.web.KSDEStudentTestDetailsDTO;
import edu.ku.cete.web.MonitorScoringExtractDTO;
import edu.ku.cete.web.OrganizationExtractDTO;
import edu.ku.cete.web.PNPAbridgedExtractDTO;
import edu.ku.cete.web.QuestarExtractDTO;
import edu.ku.cete.web.RosterExtractDTO;
import edu.ku.cete.web.StudentExitExtractDTO;
import edu.ku.cete.web.StudentGradesTestedDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;
import edu.ku.cete.web.StudentScoreDTO;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;
import edu.ku.cete.web.StudentTestSessionInfoDTO;
import edu.ku.cete.web.TECExtractDTO;
import edu.ku.cete.web.TestAdminPartDetails;
import edu.ku.cete.web.TestFormAssignmentsInfoDTO;
import edu.ku.cete.web.TestFormMediaResourceDTO;
import edu.ku.cete.web.TestingReadinessEnrollSubjects;
import edu.ku.cete.web.TestingReadinessExtractDTO;
import edu.ku.cete.web.UserDetailsAndRolesDTO;
import edu.ku.cete.web.UserSecurityAgreemntDTO;
import edu.ku.cete.web.ViewStudentDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DataReportServiceImpl implements DataReportService {
	
	private Logger LOGGER = LoggerFactory.getLogger(DataReportServiceImpl.class);
	
	private final String DATE_FORMAT_MM_DD_YY ="MM/dd/yy hh:mm a z";
	private final String EXTRACT_DATE_FORMAT_MM_DD_YYYY ="MM/dd/yyyy hh:mm a z";
	
	private final String MM_DD_YYYY_DATE_FORMAT = "MM-dd-yyyy";					
	
	private final String DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
	
	@Autowired
	private ModuleReportMapper moduleReportDao;
	
	@Autowired
	private ReportGenericMapper reportGenericDao;	
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private RosterDao rosterDao;
	
	@Autowired
	private GroupsService groupService;
	
	@Autowired
	private TestCollectionDao testcollectionDao;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private KSDEReturnFileService ksdeReturnFileService;
	
	@Autowired
	private UploadResultFileMapper uploadResultFileMapper;
	
	@Autowired
	CcqScoreMapper ccqScoreMapper;
	
	@Autowired
	AwsS3Service s3;
	
	@Value("classpath:reports.fop.conf.xml")
	private Resource reportConfigFile;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Value("${user.organization.DLM}")
	private String DLM_ORG;
	
	@Value("${queued.extracts.starttime}")
	private int queuedExtractStartTime;
	
	@Value("${queued.extracts.endtime}")
	private int queuedExtractEndTime;
	
	@Autowired(required = true)
	private StudentProfileService studentProfileService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AmpDataExtractService ampDataExtractService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
    private BluePrintMapper bluePrintMapper;
	
	@Autowired
	private GradeCourseDao gradeCourseDao;
	
	@Autowired
	private ContentFrameworkDetailDao contentFrameworkDao;
	
	@Autowired
	private ItiTestSessionHistoryMapper itiTestSessionHistoryMapper;
	
	@Autowired
	private DataExtractService dataExtractService;
	
	@Autowired
	private ProfileAttributeContainerService profileAttributeContainerService;
	
	@Value("${report.subject.science.name}")
	private String CONTENT_AREA_SCIENCE;
	
	@Autowired
	private StateSpecificFileDao stateSpecificFilesDao;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private AppConfigurationService appConfigurationService;

    @Value("${GRF.extract.Exit.Student}")
 	private String exitExtractGRFProcessType;
    
    @Value("${GRF.extract.SCCode}")
 	private String scCodeGRFProcessType;
    
    @Autowired
	private UploadFileService uploadFileService;
    
    @Value("${personalNeedsProfileRecordType}")
	private String uploadStudentPNPRecordType;
    
    @Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${ismart2.assessmentProgram.abbreviatedName}")
	private String ISMART_2_PROGRAM_ABBREVIATEDNAME;
	
	@Value("/templates/xslt/studentsPasswordExtract.xsl")
	private Resource studentsExtractTicketsXslFile;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private TestingCycleMapper testingCycleMapper;
	
	@Autowired
	private AssessmentProgramDao assessmentProgramDao;
	
	/**
	 * operationalTestWindowService
	 */
	@Autowired
	private OperationalTestWindowService operationalTestWindowService;
	
	
	String[] kapColumnHeaders = null;
	String[] kelpa2ColumnHeaders = null;
	
	String[] pnpColumnHeaders = { "Display - Magnification", "Display - Magnification Activate by Default",
			"Display - Magnification Setting", "Display - Overlay Color", "Display - Overlay Color Activate by Default",
			"Display - Overlay Color Code", "Display - Overlay Color Desc", "Display - Invert Color Choice",
			"Display - Invert Color Choice Activate By Default", "Display - Masking",
			"Display - Masking Activate by Default", "Display - Masking Setting", "Display - Contrast Color",
			"Display - Contrast Color Activate by Default", "Display - Contrast Color Background",
			"Display - Contrast Color Background Desc", "Display - Contrast Color Foreground",
			"Display - Contrast Color Foreground Desc", "Language - Item Translation Display",
			"Language - Item Translation Display Activate by Default", "Language - Item Translation Display Setting",
			"Language - Signing Type", "Language - Signing Type Activate by Default", "Language - Signing Type Setting",
			"Language - Braille", "Language - Braille Activate by Default", "Language - Braille File Type",
			"Language - Braille Usage", "Language - Keyword Translation Display",
			"Language - Keyword Translation Display Activate by Default",
			"Language - Keyword Translation Display Setting", "Language - Tactile",
			"Language - Tactile Activate by Default", "Language - Tactile Setting", "Auditory - Auditory Background",
			"Auditory - Auditory Background Activate by Default", "Auditory - Auditory Background Breaks",
			"Auditory - Auditory Background Additional Testing Time",
			"Auditory - Auditory Background Additional Testing Time Activate by Default",
			"Auditory - Auditory Background Additional Testing Time Multiplier setting", "Auditory - Spoken Audio",
			"Auditory - Spoken Audio Activate by Default", "Auditory - Spoken Audio Voice Source Setting",
			"Auditory - Spoken Audio Voice Read at Start", "Auditory - Spoken Audio Spoken Preference Setting",
			"Auditory - Audio Directions Only", "Auditory - Spoken Audio Subject Setting", "Auditory - Switches",
			"Auditory - Switches Activate by Default", "Auditory - Switches Scan Speed Seconds",
			"Auditory - Switches Scan Initial Delay Setting Seconds",
			"Auditory - Switches Automatic Scan Repeat Frequency",
			"Other Supports - Separate, quiet, or individual setting",
			"Other Supports - Presentation Student reads assessment aloud to self",
			"Other Supports - Presentation Student Used Translation dictionary",
			"Other Supports - Presentation Other Accommodation Used",
			"Other Supports - Response - Student dictated answers to scribe",
			"Other Supports - Response - Student used a communication device",
			"Other Supports - Response - Student signed responses",
			"Other Supports - Provided by Alternate Form - Visual Impairment",
			"Other Supports - Provided by Alternate Form - Large Print",
			"Other Supports - Provided by Alternate Form - Paper and Pencil",
			"Other Supports - Requiring Additional Tools Two Switch System",
			"Other Supports - Requiring Additional Tools Administration via iPad",
			"Other Supports - Requiring Additional Tools Adaptive equipment",
			"Other Supports - Requiring Additional Tools Individualized manipulatives",
			"Other Supports - Requiring Additional Tools Calculator",
			"Other Supports - Provided outside system - Human read aloud",
			"Other Supports - Provided outside system - Sign Intrepretation",
			"Other Supports - Provided outside system - Translation",
			"Other Supports - Provided outside system - Test admin enters responses for student",
			"Other Supports - Provided outside system - Partner assisted scanning",
			"Other Supports - Provided outside system - Student provided non-embedded accommodations as noted in IEP" };
	
	String supported = "assignedSupport";
	String activeByDefault = "activateByDefault";
	Map<String, String[]> pnpColumnCodes = new LinkedHashMap<String, String[]>();
	Set<String> independentSelectionContainers = new HashSet<String>();
	Set<String> requiresSpecialOutput = new HashSet<String>();

	public String getRootOutputDir() {
		return REPORT_PATH;
	}
	
	@Override
	public ModuleReport getModuleReportById(Long moduleReportId) {
		return moduleReportDao.selectByPrimaryKey(moduleReportId);
	}
	
	@Override
	public List<ModuleReport> getReportsByType(User user, List<Short> typeIds) {
		boolean isTeacher = user.isTeacher() || user.isProctor();
		Long organizationId = (long) 0;
		if(user!=null) organizationId = user.getContractingOrgId()== 0 ? user.getCurrentOrganizationId():user.getContractingOrgId();
		return moduleReportDao.getReportsForUserByTypes(user.getId(), organizationId, typeIds, isTeacher);
	}
	
	@Override
	public List<ModuleReport> getReportsByTypeForExitOrSC(User user, List<Short> typeIds) {
		Long organizationId = (long) 0;
		if(user!=null) organizationId = user.getContractingOrgId()== 0 ? user.getCurrentOrganizationId():user.getContractingOrgId();
		return moduleReportDao.getReportsByTypeForExitOrSC(organizationId, typeIds);
	}
	
	@Override
	public List<DataDetailDto> getReportsByTypeWithDataDictionaryDetail(
			User user, List<Short> typeIds, Long assessmentprogramid,
			Long organizationId) {		
		return moduleReportDao.getReportsForUserByTypesWithDataDictionaryDetail(user.getCurrentOrganizationId(), typeIds,assessmentprogramid,organizationId);
	}
	@Override
	public int countReportsByType(User user, List<Short> typeIds) {
		boolean isTeacher = user.isTeacher() || user.isProctor();
		return moduleReportDao.countReportsForUserByTypes(user.getId(), user.getCurrentOrganizationId(), typeIds, isTeacher);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public long generateNewExtract(User user, DataReportTypeEnum type, 
			Long moduleReportId, Long orgId, Long orgTypeId, Map<String, Object> params) throws JsonProcessingException {
		return updateModuleReportDBRecords(user, type, moduleReportId, orgId, orgTypeId, params);
	}
	
	protected Comparator<String[]> pnpComparator = new Comparator<String[]>(){
		public int compare(String[] a, String[] b) {
			int comp=0;
			// x representations:
			// 0 - state
			// 1 - district
			// 2 - school
			// 3 - first name (pnp
			// 4 - last name
			// check just represents if either string is null at each level, and is -1 or 1 based on which string is null
			int levels = 5;
			for (int x = 0; x < a.length && x < b.length && x < levels; x++) {
				int check = checkForNull(a, b, x);
				if (check != 0) {
					return check;
				}
				if(StringUtils.isNotBlank(a[x]) || StringUtils.isNotBlank(b[x]) ){
                    comp = a[x].compareTo(b[x]);
                } 
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
		
		private final int checkForNull(String[] a, String[] b, int index) {
			
			if (StringUtils.isBlank(a[index])&& StringUtils.isNotBlank(b[index]))  return -1;
			if(StringUtils.isNotBlank(a[index]) && StringUtils.isBlank(b[index])) return 1;
			/*if (a[index] == null && b[index] != null) return -1;
			if (a[index] != null && b[index] == null) return 1;*/
			return 0;
		}
	};
	
	protected Comparator<String[]> pnpSummaryComparator = new Comparator<String[]>(){
		public int compare(String[] a, String[] b) {
			int comp=0;
			// x representations:
			// 0 - state
			// 1 - district
			// 2 - school
			// check just represents if either string is null at each level, and is -1 or 1 based on which string is null
			int levels = 3;
			for (int x = 0; x < a.length && x < b.length && x < levels; x++) {
				int check = checkForNull(a, b, x);
				if (check != 0) {
					return check;
				}
				if(StringUtils.isNotBlank(a[x]) || StringUtils.isNotBlank(b[x]) ){
                    comp = a[x].compareTo(b[x]);
                } 
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
		
		private final int checkForNull(String[] a, String[] b, int index) {
			if (StringUtils.isBlank(a[index])&& StringUtils.isNotBlank(b[index]))  return -1;
			if(StringUtils.isNotBlank(a[index]) && StringUtils.isBlank(b[index])) return 1;
			return 0;
		}
	};
	
	protected Comparator<String[]> itiBlueprintCovergaeComparator = new Comparator<String[]>() {
		public int compare(String[] a , String[] b) {
			int comp=0;			
			for (int x = 1; x < a.length && x < b.length && x <= 3; x = x+2) {
				int check = checkForNull(a, b, x);
				if (check != 0) {
					return check;
				}
				if(StringUtils.isNotBlank(a[x]) || StringUtils.isNotBlank(b[x]) ){
                    comp = a[x].compareTo(b[x]);
                } 
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
		
		private final int checkForNull(String[] a, String[] b, int index) {
			if (StringUtils.isBlank(a[index])&& StringUtils.isNotBlank(b[index]))  return -1;
			if(StringUtils.isNotBlank(a[index]) && StringUtils.isBlank(b[index])) return 1;
			return 0;
		}
	}; 
	protected Comparator<DLMTestAdminMonitoringSummaryDTO> stateDlmTestAdminSummaryComparator = new Comparator<DLMTestAdminMonitoringSummaryDTO>(){
		public int compare(DLMTestAdminMonitoringSummaryDTO dto1, DLMTestAdminMonitoringSummaryDTO dto2) {
			int compare = 0;
			if (dto1.getGrade() == null && dto2.getGrade() != null) return -1;
			if (dto1.getGrade() != null && dto2.getGrade() == null) return 1;
			if (dto1.getGrade() != null && dto2.getGrade() != null) {
				if (!StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) return -1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && !StringUtils.isNumeric(dto2.getGrade())) return 1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) {
					compare = Integer.valueOf(dto1.getGrade()) - Integer.valueOf(dto2.getGrade());
				} else { // neither is numeric
					compare = dto1.getGrade().compareTo(dto2.getGrade());
				}
				if (compare != 0) return compare;
			}
			compare = dto1.getSubject().compareTo(dto2.getSubject());
			return compare;
		}
	};
	
	protected Comparator<DLMTestAdminMonitoringSummaryDTO> districtDlmTestAdminSummaryComparator = new Comparator<DLMTestAdminMonitoringSummaryDTO>(){
		public int compare(DLMTestAdminMonitoringSummaryDTO dto1, DLMTestAdminMonitoringSummaryDTO dto2) {
			int compare = 0;
			compare = dto1.getDistrictName().compareTo(dto2.getDistrictName());
			if (compare != 0) return compare;

			if (dto1.getGrade() == null && dto2.getGrade() != null) return -1;
			if (dto1.getGrade() != null && dto2.getGrade() == null) return 1;
			if (dto1.getGrade() != null && dto2.getGrade() != null) {
				if (!StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) return -1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && !StringUtils.isNumeric(dto2.getGrade())) return 1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) {
					compare = Integer.valueOf(dto1.getGrade()) - Integer.valueOf(dto2.getGrade());
				} else { // neither is numeric
					compare = dto1.getGrade().compareTo(dto2.getGrade());
				}
				if (compare != 0) return compare;
			}
			
			compare = dto1.getSubject().compareTo(dto2.getSubject());
			return compare;
		}
	};
	
	protected Comparator<DLMTestAdminMonitoringSummaryDTO> schoolDlmTestAdminSummaryComparator = new Comparator<DLMTestAdminMonitoringSummaryDTO>(){
		public int compare(DLMTestAdminMonitoringSummaryDTO dto1, DLMTestAdminMonitoringSummaryDTO dto2) {
			int compare = 0;
			compare = dto1.getDistrictName().compareTo(dto2.getDistrictName());
			if (compare != 0) return compare;
			compare = dto1.getSchoolName().compareTo(dto2.getSchoolName());
			if (compare != 0) return compare;
			
			if (dto1.getGrade() == null && dto2.getGrade() != null) return -1;
			if (dto1.getGrade() != null && dto2.getGrade() == null) return 1;
			if (dto1.getGrade() != null && dto2.getGrade() != null) {
				if (!StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) return -1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && !StringUtils.isNumeric(dto2.getGrade())) return 1;
				else if (StringUtils.isNumeric(dto1.getGrade()) && StringUtils.isNumeric(dto2.getGrade())) {
					compare = Integer.valueOf(dto1.getGrade()) - Integer.valueOf(dto2.getGrade());
				} else { // neither is numeric
					compare = dto1.getGrade().compareTo(dto2.getGrade());
				}
				if (compare != 0) return compare;
			}
			
			compare = dto1.getSubject().compareTo(dto2.getSubject());
			return compare;
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean generatePNPAbridgedExtract(Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException, ScriptException {
		ModuleReport mr = moduleReportDao.selectByPrimaryKey(moduleReportId);
		Long stateId = mr.getStateId();
		Long userId = new Long(mr.getCreatedUser());
		int schoolYear = additionalParams.get("currentSchoolYear") == null ? 0 : ((int)additionalParams.get("currentSchoolYear"));
		
		boolean includeAllStudents = Boolean.TRUE.equals((Boolean) additionalParams.get("includeAllStudents"));
		
		Groups group = groupService.getGroup(mr.getGroupId());
		boolean shouldOnlySeeRosteredStudents = Pattern.matches("(?i:teacher|proctor)", group.getGroupName());
		
		// at time of initial implementation, only one assessment program can be selected
		List<Integer> tmp_apIds = (List<Integer>) additionalParams.get("assessmentProgramIds");
		Long assessmentProgramId = new Long(tmp_apIds.get(0).intValue());
		List<Long> apIds = Arrays.asList(assessmentProgramId);
		
		List<String> headers = new ArrayList<String>();
		headers.addAll(Arrays.asList(
			"State", "District Name", "District ID", "School Name", "School ID",
			"Student Last Name", "Student First Name", "State Student Identifier", "Last Modified Time", "Last Modified By"
		));
		
		// be sure to check the child functions generatePNPAbridgedExtractCSV and generatePNPAbridgedExtractExcel for references to these columns,
		// if they're ever changed
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		if (ap != null && CommonConstants.ASSESSMENT_PROGRAM_PLTW.equals(ap.getAbbreviatedname())) {
			headers.addAll(Arrays.asList("Comprehensive Race", "Hispanic Ethnicity"));
		}
		
		String fileType = (String) additionalParams.get("fileType");
		if (CommonConstants.FILE_CSV_EXTENSION.equalsIgnoreCase(fileType)) {
			return generatePNPAbridgedExtractCSV(moduleReportId, orgId, additionalParams, typeName, mr, stateId, userId, schoolYear,
					includeAllStudents, shouldOnlySeeRosteredStudents, apIds, headers);
		} else if (CommonConstants.FILE_XLSX_EXTENSION.equalsIgnoreCase(fileType)) {
			return generatePNPAbridgedExtractExcel(moduleReportId, orgId, additionalParams, typeName, mr, stateId, userId, schoolYear,
					includeAllStudents, shouldOnlySeeRosteredStudents, apIds, headers);
		} else {
			throw new IllegalArgumentException(
				new StringBuilder()
				.append("File type passed to PNP Abridged extract was not recognized (")
				.append(String.valueOf(fileType))
				.append(")")
				.toString()
			);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean generatePNPAbridgedExtractCSV(Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName, ModuleReport moduleReport, Long stateId, Long userId, int schoolYear,
			boolean includeAllStudents, boolean shouldOnlySeeRosteredStudents, List<Long> assessmentProgramIds, List<String> headers) throws IOException, ScriptException {
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramIds.get(0));
		boolean isPLTW = ap != null && CommonConstants.ASSESSMENT_PROGRAM_PLTW.equals(ap.getAbbreviatedname());
		
		// our ObjectMapper that we'll use to parse and traverse the JSON in fieldspecs as well as massage a student's PNP JSON later 
		ObjectMapper objectMapper = new ObjectMapper();
		
		// a Map to avoid having to traverse the JSON tree again later when we want to output the columns
		Map<Long, JsonNode> fieldSpecExtractScripts = new HashMap<Long, JsonNode>();
		
		List<FieldSpecification> fieldSpecs = 
				uploadFileService.selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(assessmentProgramIds.get(0), stateId);
		
		// remove columns that don't have an extract formatting function
		for (int x = 0; x < fieldSpecs.size(); x++) {
			FieldSpecification fieldSpec = fieldSpecs.get(x);
			
			String fsJsonStr = fieldSpec.getJsonData();
			JsonNode fsJson = objectMapper.readTree(fsJsonStr);
			JsonNode fieldExtractScript = fsJson.get("extractScript");
			if (fieldExtractScript != null && StringUtils.isNotEmpty(fieldExtractScript.asText(""))) {
				LOGGER.debug("adding header " + fieldSpec.getMappedName());
				headers.add(fieldSpec.getMappedName());
				fieldSpecExtractScripts.put(fieldSpec.getId(), fieldExtractScript);
			} else {
				LOGGER.debug("\"extractScript\" was null or empty on fieldspecificationid \"" + fieldSpec.getMappedName() +
						"\" during PNP abridged extract, dynamically excluding the column from the extract");
				fieldSpecs.remove(x);
				x--;
			}
		}
		
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(headers.toArray(new String[0]));
		
		/*
		 * Brad:
		 * OK, so this next block of code isn't very straightforward.
		 * 
		 * Basically, I wanted the database to have all necessary information for input and output of the columns of this extract/upload.
		 * This led to everything being JSON. So in the FieldSpecification entries, there will be "actions",
		 * which define how the data needs to look based on an upload value,
		 * and "extractScript" which defines how the data should be transformed for the extract.
		 * 
		 * So here's the fun stuff. JavaScript (JS) functions can be executed from the JVM using a ScriptEngine, as defined below.
		 * BUT, since we want to run lots of stuff, the JS functions couldn't all have the same name or else we'd run into naming conflicts.
		 * So, to leave the JS functions in the FieldSpecification JSON anonymous, we have to set up a dummy type.
		 * The dummy type needs to have a method that takes an Object as a parameter (which will end up being our function).
		 * Then, we create an instance of the dummy type and put it into our ScriptEngine.
		 * We can then call our dummy method with the parameter as the JS function from the FieldSpecification.
		 * 
		 * The parameter to our JS function needs to be dynamic, though...it's our student's PNP data after all.
		 * So our dummy type needs a parameter variable that we can pass to the method when we want to.
		 * We create our dummy object, and use its getParameter() function as the variable to pass to the method.
		*/
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if (!(engine instanceof Invocable)) {
			LOGGER.error("js engine not supported");
			throw new UnsupportedOperationException("JavaScript engine not supported");
		}
		final Invocable invocableEngine = ((Invocable)engine);
		
		JavaScriptEngineAnonymousFunctionHelper dummyObj = new JavaScriptEngineAnonymousFunctionHelper() {
			@Override
			public Object dummyMethod(Object function) {
				try {
					return invocableEngine.invokeMethod(function, "call", function, getParameter());
				} catch (NoSuchMethodException | ScriptException e) {
					LOGGER.error("Exception when trying to call javascript function:", e);
				}
				return null;
			}
		};
		engine.put("dummyObj", dummyObj);
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(EXTRACT_DATE_FORMAT_MM_DD_YYYY, orgId);
		
		int studentsAtATime = 5000;
		int offset = 0;
		List<PNPAbridgedExtractDTO> students = null;
		while (students == null || students.size() == studentsAtATime) {
			students = dataExtractService.getActiveStudentsWithPNPEnrolledInOrg(orgId,
				shouldOnlySeeRosteredStudents, userId, studentsAtATime, offset, schoolYear, assessmentProgramIds, includeAllStudents);
			LOGGER.debug("retrieved page " + (offset/studentsAtATime) + " (" + students.size() + " records)");
			offset += students.size();
			for (PNPAbridgedExtractDTO student : students) {
				String[] line = new String[headers.size()];
				int index = 0;
				line[index++] = wrapForCSV(student.getStateName());
				line[index++] = wrapForCSV(student.getDistrictName());
				line[index++] = wrapForCSV(student.getDistrictDisplayIdentifier());
				line[index++] = wrapForCSV(student.getSchoolName());
				line[index++] = wrapForCSV(student.getSchoolDisplayIdentifier());				
				line[index++] = wrapForCSV(student.getStudentLastName());
				line[index++] = wrapForCSV(student.getStudentFirstName());
				line[index++] = wrapForCSV(student.getStateStudentIdentifier());
				line[index++] = wrapForCSV(student.getModifiedDate() == null ? "" : dateFormat.format(student.getModifiedDate()));
				line[index++] = wrapForCSV(student.getModifiedBy());
				if (isPLTW) {
					line[index++] = wrapForCSV(student.getComprehensiveRace());
					if (student.getHispanicEthnicity().trim().equalsIgnoreCase("true")) {
						line[index++] = "Yes";
					} else if (student.getHispanicEthnicity().trim().equalsIgnoreCase("false")) {
						line[index++] = "No";
					}  else if (!student.getHispanicEthnicity().trim().equalsIgnoreCase("true") && !student.getHispanicEthnicity().trim().equalsIgnoreCase("false") && !student.getHispanicEthnicity().isEmpty()) {
						line[index++] = student.getHispanicEthnicity();
					}else {
						line[index++] = "";
					}
				}
				
				if ("CUSTOM".equals(student.getProfileStatus())) {
					// Massage the PNP JSON here to get it to one top-level object instead of an array that we'd have to traverse every time.
					// Should improve efficiency for the JS functions and speed things up a bit.
					String convertedJson = convertPNPJsonArray(student.getPnpJsonText());
					
					// set the dummy parameter to the student's converted PNP JSON
					dummyObj.setParameter(convertedJson);
					
					for (FieldSpecification fieldSpec : fieldSpecs) {
						JsonNode extractScript = fieldSpecExtractScripts.get(fieldSpec.getId());
						try {
							// execute the field's JavaScript function using eval()
							Object ret = engine.eval("dummyObj.dummyMethod(" + extractScript.asText() + ")");
							line[index++] = ret != null ? ret.toString() : "";
						} catch (Exception e) {
							lines.clear(); // just do some cleanup of the potentially large data set
							LOGGER.error("Exception encountered while processing field \"" +
									fieldSpec.getMappedName() + "\" for student " + student.getStudentId(), e);
							throw e;
						}
					}
				} else {
					// make sure we insert a clean line
					for (int x = 0; x < fieldSpecs.size(); x++) {
						line[index++] = "";
					}
				}
				lines.add(line);
			}
		}
		/*
		**** End of potentially strange code ****
		*/
		
		writeReport(moduleReportId, lines, typeName);
		return true;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean generatePNPAbridgedExtractExcel(Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName, ModuleReport moduleReport, Long stateId, Long userId, int schoolYear,
			boolean includeAllStudents, boolean shouldOnlySeeRosteredStudents, List<Long> assessmentProgramIds, List<String> commonHeaders) throws IOException, ScriptException {
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramIds.get(0));
		boolean isPLTW = ap != null && CommonConstants.ASSESSMENT_PROGRAM_PLTW.equals(ap.getAbbreviatedname());
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Sheet 1");
		sheet.createFreezePane(0, 1);
		//sheet.setRandomAccessWindowSize(10); // keep only 10 rows in memory, rest are flushed to disk (this is only for instances of SXSSFSheet)
		sheet.enableLocking();
		
		// after using enableLocking(), everything is locked by default, but we just want to be able to lock cells, not functionality
		sheet.lockSelectLockedCells(false);
		sheet.lockSelectUnlockedCells(false);
		sheet.lockFormatCells(false);
		sheet.lockFormatColumns(false);
		sheet.lockFormatRows(false);
		sheet.lockInsertColumns(false);
		sheet.lockInsertRows(false);
		sheet.lockInsertHyperlinks(false);
		sheet.lockDeleteColumns(false);
		sheet.lockDeleteRows(false);
		sheet.lockSort(false);
		sheet.lockAutoFilter(false);
		sheet.lockPivotTables(false);
		sheet.lockObjects(false);
		sheet.lockScenarios(false);
		
		CellStyle lockedStyle = workbook.createCellStyle();
		lockedStyle.setLocked(true);
		CellStyle unlockedStyle = workbook.createCellStyle();
		unlockedStyle.setLocked(false);
		
		XSSFDataValidationHelper dvh = (XSSFDataValidationHelper) sheet.getDataValidationHelper();
		
		XSSFRow headerRow = sheet.createRow(0);
		headerRow.setRowStyle(lockedStyle);
		
		for (int x = 0; x < commonHeaders.size(); x++) {
			sheet.setDefaultColumnStyle(x, unlockedStyle);
			
			XSSFCell headerCell = headerRow.createCell(x);
			headerCell.setCellValue(commonHeaders.get(x));
			headerCell.setCellStyle(lockedStyle);
		}
		
		// our ObjectMapper that we'll use to parse and traverse the JSON in fieldspecs as well as massage a student's PNP JSON later 
		ObjectMapper objectMapper = new ObjectMapper();
		
		// a Map to avoid having to traverse the JSON tree again later when we want to output the columns
		Map<Long, JsonNode> fieldSpecExtractScripts = new HashMap<Long, JsonNode>();
		
		List<FieldSpecification> fieldSpecs = 
				uploadFileService.selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(assessmentProgramIds.get(0), stateId);
		
		// remove columns that don't have an extract formatting function
		for (int x = 0; x < fieldSpecs.size(); x++) {
			int colIndex = commonHeaders.size() + x;
			FieldSpecification fieldSpec = fieldSpecs.get(x);
			
			String fsJsonStr = fieldSpec.getJsonData();
			JsonNode fsJson = objectMapper.readTree(fsJsonStr);
			JsonNode fieldExtractScript = fsJson.get("extractScript");
			if (fieldExtractScript != null && StringUtils.isNotEmpty(fieldExtractScript.asText(""))) {
				fieldSpecExtractScripts.put(fieldSpec.getId(), fieldExtractScript);
				
				sheet.setDefaultColumnStyle(colIndex, unlockedStyle);
				
				XSSFCell headerCell = headerRow.createCell(colIndex, Cell.CELL_TYPE_STRING);
				headerCell.setCellValue(fieldSpec.getMappedName());
				headerCell.setCellStyle(lockedStyle);
				List<String> values = getAllowedFieldValues(fieldSpec);
				if (CollectionUtils.isNotEmpty(values)) {
					XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) dvh.createExplicitListConstraint(values.toArray(new String[0]));
					XSSFDataValidation dataValidation =
							(XSSFDataValidation) dvh.createValidation(constraint, new CellRangeAddressList(1, (int) Math.pow(2, 20) - 1, colIndex, colIndex));
					
					// for some reason "suppress" is used in this method name...which is insane
					// the method just makes the dropdown arrow appear based on the parameter (true displays it, false doesn't)
					dataValidation.setSuppressDropDownArrow(true);
					
					dataValidation.setShowErrorBox(true);
					dataValidation.setShowPromptBox(true);
					dataValidation.setEmptyCellAllowed(true);
					
					sheet.addValidationData(dataValidation);
				}
			} else {
				LOGGER.debug("\"extractScript\" was null or empty on fieldspecificationid \"" + fieldSpec.getMappedName() +
						"\" during PNP abridged extract, dynamically excluding the column from the extract");
				fieldSpecs.remove(x);
				x--;
			}
		}
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if (!(engine instanceof Invocable)) {
			LOGGER.error("js engine not supported");
			throw new UnsupportedOperationException("JavaScript engine not supported");
		}
		final Invocable invocableEngine = ((Invocable)engine);
		
		JavaScriptEngineAnonymousFunctionHelper dummyObj = new JavaScriptEngineAnonymousFunctionHelper() {
			@Override
			public Object dummyMethod(Object function) {
				try {
					return invocableEngine.invokeMethod(function, "call", function, getParameter());
				} catch (NoSuchMethodException | ScriptException e) {
					LOGGER.error("Exception when trying to call javascript function:", e);
				}
				return null;
			}
		};
		engine.put("dummyObj", dummyObj);
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(EXTRACT_DATE_FORMAT_MM_DD_YYYY, orgId);
		
		int studentsAtATime = 5000;
		int offset = 0;
		int rowIndex = 1; // start at 1, since we had the header row already
		List<PNPAbridgedExtractDTO> students = null;
		while (students == null || students.size() == studentsAtATime) {
			students = dataExtractService.getActiveStudentsWithPNPEnrolledInOrg(orgId,
				shouldOnlySeeRosteredStudents, userId, studentsAtATime, offset, schoolYear, assessmentProgramIds, includeAllStudents);
			LOGGER.debug("retrieved page " + (offset/studentsAtATime) + " (" + students.size() + " records)");
			offset += students.size();
			for (PNPAbridgedExtractDTO student : students) {
				XSSFRow row = sheet.createRow(rowIndex++);
				int colIndex = 0;
				XSSFCell cell = null; // init to null just for prettifying the next handful of compound lines
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getStateName()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getDistrictName()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getDistrictDisplayIdentifier()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getSchoolName()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getSchoolDisplayIdentifier()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getStudentLastName()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getStudentFirstName()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getStateStudentIdentifier()); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getModifiedDate() == null ? "" : dateFormat.format(student.getModifiedDate())); cell.setCellStyle(unlockedStyle);
				cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getModifiedBy()); cell.setCellStyle(unlockedStyle);
				if (isPLTW) {
					cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellValue(student.getComprehensiveRace()); cell.setCellStyle(unlockedStyle);
					cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING); cell.setCellStyle(unlockedStyle);
					if (student.getHispanicEthnicity().trim().equalsIgnoreCase("true")) {
						cell.setCellValue("Yes");
					} else if (student.getHispanicEthnicity().trim().equalsIgnoreCase("false")) {
						cell.setCellValue("No");
					} else if (!student.getHispanicEthnicity().trim().equalsIgnoreCase("true") && !student.getHispanicEthnicity().trim().equalsIgnoreCase("false") && !student.getHispanicEthnicity().isEmpty()) {
						cell.setCellValue(student.getHispanicEthnicity());
					}else {
						cell.setCellValue("");
					}
				}
				
				if ("CUSTOM".equals(student.getProfileStatus())) {
					// Massage the PNP JSON here to get it to one top-level object instead of an array that we'd have to traverse every time.
					// Should improve efficiency for the JS functions and speed things up a bit.
					String convertedJson = convertPNPJsonArray(student.getPnpJsonText());
					
					// set the dummy parameter to the student's converted PNP JSON
					dummyObj.setParameter(convertedJson);
					
					for (FieldSpecification fieldSpec : fieldSpecs) {
						JsonNode extractScript = fieldSpecExtractScripts.get(fieldSpec.getId());
						try {
							// execute the field's JavaScript function using eval()
							Object ret = engine.eval("dummyObj.dummyMethod(" + extractScript.asText() + ")");
							cell = row.createCell(colIndex++, Cell.CELL_TYPE_STRING);
							cell.setCellValue(ret != null ? ret.toString() : "");
						} catch (Exception e) {
							LOGGER.error("Exception encountered while processing field \"" +
									fieldSpec.getMappedName() + "\" for student " + student.getStudentId(), e);
							throw e;
						}
					}
				}
			}
		}
		
		for (int x = 0; x < commonHeaders.size() + fieldSpecs.size(); x++) {
			sheet.autoSizeColumn(x);
		}
		
		String fileName = getFileName(moduleReport);
		int extensionIndex = fileName.lastIndexOf('.');
		fileName = fileName.substring(0, extensionIndex) + ".xlsx"; // getFileName returns a .csv file name, so change it here
		String pathAndExtensionArray[] = fileName.split("\\.");
		File tempFile = File.createTempFile(pathAndExtensionArray[0], "." + pathAndExtensionArray[1]);
		
		String folderPath = getFolderPath(moduleReport, typeName);
		String fullFileName = FileUtil.buildFilePath(folderPath,fileName);
		
		if (moduleReport.getFileName() != null) { // the file exists...shouldn't usually happen
			fullFileName = moduleReport.getFileName();
		}
		
		FileOutputStream outputStream = new FileOutputStream(tempFile);
		workbook.write(outputStream);
		workbook.close();
		s3.synchMultipartUpload(fullFileName, tempFile);
		FileUtils.deleteQuietly(tempFile);
		
		moduleReport.setFileName(fullFileName);
		updateModuleReport(moduleReport);
		return true;
	}
	
	private List<String> getAllowedFieldValues(FieldSpecification fieldSpec) {
		List<String> allowedValues = new ArrayList<String>();
		ObjectMapper objectMapper = new ObjectMapper();
		char[] separatorsForCapitalization = {' ', '/'};
		if (StringUtils.isNotEmpty(fieldSpec.getJsonData())) {
			try {
				JsonNode root = objectMapper.readTree(fieldSpec.getJsonData());
				JsonNode actions = root.get("actions");
				Iterator<String> fieldAllowedValues = actions.fieldNames();
				boolean validValueEncountered = false;
				while (fieldAllowedValues.hasNext()) {
					String fieldAllowedValue = fieldAllowedValues.next();
					if ("VALIDVALUE".equals(fieldAllowedValue)) {
						validValueEncountered = true;
					} else if (!"EMPTY".equals(fieldAllowedValue)) {
						//LOGGER.info("fieldAllowedValue = " + fieldAllowedValue);
						allowedValues.add(WordUtils.capitalize(fieldAllowedValue, separatorsForCapitalization));
					}
				}
				// add these after any other options
				if (validValueEncountered) {
					// VALIDVALUE is mainly for numeric settings that can basically be anything, so we don't have an explicit list...
					// so 1-80?
					// 80 prevents an error in Excel that doesn't let the file open correctly.
					// Take care when increasing the max and be sure to test with Excel on Windows.
					// LibreOffice on Linux worked with 100 originally, so it's a "gotcha".
					for (int x = 1; x <= 80; x++) {
						allowedValues.add(String.valueOf(x));
					}
				}
			} catch (Exception e) {
				LOGGER.error("error:", e);
			}
		}
		return allowedValues;
	}
	
	private String convertPNPJsonArray(String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ProfileAttribute> attributes = objectMapper.readValue(json,
				objectMapper.getTypeFactory().constructCollectionType(List.class, ProfileAttribute.class));
		
		String jsonStr = "{";
		for (int x = 1; x <= attributes.size(); x++) {
			ProfileAttribute attribute = attributes.get(x - 1);
			jsonStr += objectMapper.writeValueAsString(attribute.getAttrContainer() + "-" + attribute.getAttrName());
			jsonStr += ":";
			jsonStr += objectMapper.writeValueAsString(attribute.getAttrValue());
			
			if (x < attributes.size()) {
				jsonStr += ",";
			}
		}
		jsonStr += "}";
		return jsonStr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startAccessibilityExtractGeneration(UserDetailImpl userDetails, Long moduleReportId,
			Long requestedOrgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		
		String[] staticHeaders = { "State", "District", "School", "Student Last Name", "Student First Name",
				"Student Middle Initial", "Grade Level", "Student State ID", "Student Local ID", "DLM Student",
				"Last Modified Time", "Last Modified By" };
			
		String[] userColumnHeaders = (String[]) ArrayUtils.addAll(staticHeaders, pnpColumnHeaders);
		
		User user = userDetails.getUser();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		List<Long> assessmentPrograms = (List<Long>)additionalParams.get("assessmentProgramIds");
		
		Map<Long, String[]> attendanceSchoolIdsToOrgInfo = new HashMap<Long, String[]>();
		
		Locale clientLocale = new Locale((String)additionalParams.get("clientLocale"));
		Calendar userTime = new GregorianCalendar(Calendar.getInstance(clientLocale).getTimeZone());
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(EXTRACT_DATE_FORMAT_MM_DD_YYYY, requestedOrgId);	
		
		int studentsAtATime = 10000;
		
		Organization requestedOrg = dataExtractService.getOrganization(requestedOrgId);
		List<Long> orgIds = new ArrayList<Long>();
		if (requestedOrg.getTypeCode().equalsIgnoreCase("ST")) {
			List<Organization> districts = dataExtractService.getAllChildrenByType(requestedOrgId, "DT");
			orgIds.addAll(AARTCollectionUtil.getIds(districts));
		} else {
			orgIds.add(requestedOrgId);
		}
		
		List<String[]> lines = new ArrayList<String[]>();
		
		for (Long orgId : orgIds) {
			int offset = 0;
			List<Long> studentIds = dataExtractService.getActiveStudentIdsWithPNPEnrolledInOrg(orgId,
					shouldOnlySeeRosteredStudents, user.getId(), studentsAtATime, offset, currentSchoolYear, assessmentPrograms);
			
			List<AccessibilityExtractDTO> records = new ArrayList<AccessibilityExtractDTO>();
			while (studentIds.size() > 0) {
				records.addAll(moduleReportDao.getAccessibilityReportByStudentIds(studentIds, currentSchoolYear,assessmentPrograms));
				offset += studentsAtATime;
				studentIds = dataExtractService.getActiveStudentIdsWithPNPEnrolledInOrg(orgId,
						shouldOnlySeeRosteredStudents, user.getId(), studentsAtATime, offset, currentSchoolYear, assessmentPrograms);
			}
			
			for (AccessibilityExtractDTO record : records) {
				Student student = dataExtractService.findByStudentId(record.getStudentId());
				
				List<Enrollment> enrollments = dataExtractService.getCurrentEnrollmentsByStudentId(student.getId(), orgId,
						currentSchoolYear);
				for (Enrollment enrollment : enrollments) {
					String[] row = new String[userColumnHeaders.length];
					// find org info and massage it
					Long schoolId = enrollment.getAttendanceSchoolId();
					String stateName = "";
					String districtName = "";
					String schoolName = "";
					
					// use cache, if available
					if (attendanceSchoolIdsToOrgInfo.containsKey(schoolId)) {
						String[] orgInfo = attendanceSchoolIdsToOrgInfo.get(schoolId);
						stateName = orgInfo[0];
						districtName = orgInfo[1];
						schoolName = orgInfo[2];
					} else {
						Organization school = dataExtractService.getOrganization(enrollment.getAttendanceSchoolId());
						schoolName = wrapForCSV(school.getOrganizationName());
						
						List<Organization> orgParents = dataExtractService.getAllParents(schoolId);
						for (Organization org : orgParents) {
							if (org.getOrganizationType().getTypeCode().equals("DT")) {
								districtName = wrapForCSV(org.getOrganizationName());
							} else if (org.getOrganizationType().getTypeCode().equals("ST")) {
								stateName = wrapForCSV(org.getDisplayIdentifier());
							}
						}
						// cache this, so we don't make redundant DB calls for the same info again
						attendanceSchoolIdsToOrgInfo.put(schoolId, new String[]{stateName, districtName, schoolName});
					}
					int index = 0;
					row[index++] = stateName;//0
					row[index++] = districtName;//1
					row[index++] = schoolName;//2
					
					// massage student info
					row[index++] = wrapForCSV(student.getLegalLastName());//3
					row[index++] = wrapForCSV(student.getLegalFirstName());//4
					String middleName = student.getLegalMiddleName();//5
					if (middleName != null && !middleName.isEmpty()) {
						row[index++] = wrapForCSV(middleName.substring(0, 1));
					} else {
						row[index++] = "";
					}
					row[index++]=wrapForCSV(enrollment.getCurrentGrade());
	
					row[index++] = wrapForCSV(student.getStateStudentIdentifier());//6
					row[index++] = wrapForCSV(enrollment.getLocalStudentIdentifier());//7
					
					AssessmentProgram ap = dataExtractService.findAssessmentProgramByStudentId(student.getId(), user);
					if (ap != null && ap.getProgramName() != null && ap.getProgramName().equals(DLM_ORG)) {
						row[index++] = "TRUE";
					} else {
						row[index++] = "FALSE";
					}
					
					Date lastModified = null;
					User lastModifiedUser = null;
					for (int x = 0; x < record.getAttributes().size(); x++) {
						StudentProfileItemAttributeDTO dto = record.getAttributes().get(x);
						if (lastModified == null || (dto.getModifiedDate() != null && lastModified.before(dto.getModifiedDate()))) {
							if (lastModifiedUser == null ||
									(dto.getModifiedUser() != null && !lastModifiedUser.getId().equals(dto.getModifiedUser()))) {
								lastModified = dto.getModifiedDate();
								lastModifiedUser = dataExtractService.getActiveOrInactiveUser(dto.getModifiedUser());
							}
						}
					}
					
					userTime.setTimeInMillis(lastModified.getTime()); // convert to client time zone
					//row[index++] = dateFormat.format(userTime.getTime());
					row[index++] = lastModified != null ? dateFormat.format(lastModified) : StringUtils.EMPTY;
					row[index++] = lastModifiedUser.getFirstName() + " " + lastModifiedUser.getSurName();
					
					populatePNPColumns(dateFormat,  userTime, record, row, index);
					lines.add(row);
				}
			}
		}
		Collections.sort(lines, pnpComparator);
		lines.add(0, userColumnHeaders); // cheat the sorting by adding them now
		writeReport(moduleReportId, lines,typeName);
		return true;
	}

	private void populatePNPColumns(DateFormat dateFormat,
			Calendar userTime,
			AccessibilityExtractDTO record, String[] row, int index) {
		
		// these are organized by container (IMPORTANT for output), in the order that the columns are specified above
		// use LinkedHashMap for guaranteed ordering when iterating over the key values
		if(pnpColumnCodes.size() <= 0){
			pnpColumnCodes.put("Magnification", new String[]{supported, activeByDefault, "magnification"});
			pnpColumnCodes.put("ColourOverlay", new String[]{supported, activeByDefault, "colour"});
			pnpColumnCodes.put("InvertColourChoice", new String[]{supported, activeByDefault});
			pnpColumnCodes.put("Masking", new String[]{supported, activeByDefault, "MaskingType"});
			pnpColumnCodes.put("BackgroundColour", new String[]{supported, activeByDefault, "colour"});
			pnpColumnCodes.put("ForegroundColour", new String[]{"colour"});
			pnpColumnCodes.put("itemTranslationDisplay", new String[]{supported, activeByDefault, "Language"});
			pnpColumnCodes.put("Signing", new String[]{supported, activeByDefault, "SigningType"});
			pnpColumnCodes.put("Braille", new String[]{supported, activeByDefault, "ebaeFileType", "uebFileType", "usage"});
			pnpColumnCodes.put("keywordTranslationDisplay", new String[]{supported, activeByDefault, "Language"});
			pnpColumnCodes.put("Tactile", new String[]{supported, activeByDefault, "tactileFile"});
			pnpColumnCodes.put("AuditoryBackground", new String[]{supported, activeByDefault});
			pnpColumnCodes.put("breaks", new String[]{supported}); // still in "AuditoryBackground", in the column names
			pnpColumnCodes.put("AdditionalTestingTime", new String[]{supported, activeByDefault, "TimeMultiplier"});
			pnpColumnCodes.put("Spoken", new String[]{supported, activeByDefault, "SpokenSourcePreference", "ReadAtStartPreference",
						"UserSpokenPreference", "directionsOnly", "preferenceSubject"});
			pnpColumnCodes.put("onscreenKeyboard", new String[]{supported, activeByDefault, "scanSpeed", "automaticScanInitialDelay",
						"automaticScanRepeat"});
			pnpColumnCodes.put("setting", new String[]{"separateQuiteSetting"});
			pnpColumnCodes.put("presentation", new String[]{"readsAssessmentOutLoud", "useTranslationsDictionary", "someotheraccommodation"});
			pnpColumnCodes.put("response", new String[]{"dictated", "usedCommunicationDevice", "signedResponses"});
			pnpColumnCodes.put("supportsProvidedByAlternateForm", new String[]{"visualImpairment", "largePrintBooklet", "paperAndPencil"});
			pnpColumnCodes.put("supportsRequiringAdditionalTools", new String[]{"supportsTwoSwitch","supportsAdminIpad","supportsAdaptiveEquip","supportsIndividualizedManipulatives","supportsCalculator"});
			pnpColumnCodes.put("supportsProvidedOutsideSystem", new String[]{"supportsHumanReadAloud", "supportsSignInterpretation", 
						"supportsLanguageTranslation", "supportsTestAdminEnteredResponses", 
						"supportsPartnerAssistedScanning","supportsStudentProvidedAccommodations"});
		}
		
		
		// this is a static list of containers which don't have selections
		// depend on a parent
		if(independentSelectionContainers.size() <=0){
			independentSelectionContainers.add("supportsProvidedByAlternateForm");
			independentSelectionContainers.add("supportsRequiringAdditionalTools");
			independentSelectionContainers.add("supportsProvidedOutsideSystem");
		}

		if(requiresSpecialOutput.size() <= 0 ){
			requiresSpecialOutput.add("presentation");
			requiresSpecialOutput.add("response");
			requiresSpecialOutput.add("Braille");
		}
		
		List<StudentProfileItemAttributeDTO> attributes = record.getAttributes();
		
		// populate rest of row with the data from the PNP list
		for (Map.Entry<String, String[]> entry : pnpColumnCodes.entrySet()) {
			String attributeContainerName = entry.getKey();
			String[] attributeNames = entry.getValue();
			boolean selected = false;
			boolean isParent = !independentSelectionContainers.contains(attributeContainerName);
			if (requiresSpecialOutput.contains(attributeContainerName)) {
				String[] values = processSpecialOutput(attributes, attributeContainerName, attributeNames);
				for (int x = 0; x < values.length; x++) {
					row[index++] = values[x];
				}
			} else {
				for (int x = 0; x < attributeNames.length; x++) {
					String value = generatePNPColumnValue(attributes, attributeContainerName, attributeNames[x]);
					value = wrapForCSV(value);
					if (isParent) {
						if (x > 0 && !selected && !value.contains("N/A")) { // 0 is the "supported" entry
							value = "Not Selected";
						} else if (x == 0) {
							selected = value.equals("Selected");
						}
					}
					row[index++] = value;
					if (attributeNames[x].equals("colour")) {
						String colorName = "Not Selected";
						if (value != null && value.startsWith("#")) {
							colorName = ColorNameUtil.getColorNameForHex(value);
							if (colorName.isEmpty()) {
								colorName = "Not Selected";
							}
						}
						row[index++] = colorName; // small hack to include extra color description columns after each hex code column
					}
				}
			}
		}
	}
	
	private String getPNPValue(final List<StudentProfileItemAttributeDTO> dtos, final String attributeContainerName,
			final String attributeName) {
		for (StudentProfileItemAttributeDTO dto : dtos) {
			if (dto.getAttributeContainerName().equals(attributeContainerName) && dto.getAttributeName().equals(attributeName)) {
				return dto.getSelectedValue();
			}
		}
		return null;
	}
	
	/**
	 * For use in the PNP data extract.
	 * @param dtos
	 * @param attributeContainerName
	 * @param attributeName
	 * @return
	 */
	private String generatePNPColumnValue(final List<StudentProfileItemAttributeDTO> dtos, final String attributeContainerName,
			final String attributeName) {
		for (StudentProfileItemAttributeDTO dto : dtos) {
			if (dto.getAttributeContainerName().equals(attributeContainerName) && dto.getAttributeName().equals(attributeName)) {
				String selectedValue = dto.getSelectedValue();
				String viewOption = dto.getViewOption();
				String value = selectedValue;
				if (selectedValue.equals("true")) {
					value = "Selected";
				} else if (selectedValue == null || selectedValue.equals("false") || selectedValue.isEmpty()) {
					value = "Not Selected";
				}
				
				if (viewOption != null && value.equals("Not Selected")) {
					if (viewOption.contains("disable_")) {
						// only disables certain selections in the section, but MAYBE not this certain selection
						// we actually don't want to do anything here for now, since we want to show db entries that are potentially out of date,
						// and only react with an N/A if the entire section is disabled for the whole the assessment program
					} else if (viewOption.contains("disable") || viewOption.contains("hide")) { // disable the entire section, regardless of selection
						value = "N/A";
					}
				} else if(StringUtils.isNotBlank(value)){
					if (viewOption != null && viewOption.contains("disable_")) {
						if(viewOption.toLowerCase().contains("disable_"+ value.toLowerCase())) {
							value = "N/A";
						}
					} else if (viewOption != null && (viewOption.contains("disable") || viewOption.contains("hide"))) {
						value = "N/A";
					}
				}
				return value;
			}
		}
		LOGGER.debug("Could not find attribute \"" + attributeName + "\" in attribute container \"" + attributeContainerName + "\" -- returning N/A");
		return "N/A";
	}
	
	/**
	 * Used in conjunction with {@link #processSpecialOutput(List, String, String[])} to retrieve values that don't appear
	 * in a container-to-children hierarchy, like regular accommodations.
	 * @param dtos
	 * @param attributeContainerName
	 * @return
	 */
	private StudentProfileItemAttributeDTO getPNPContainerValue(final List<StudentProfileItemAttributeDTO> dtos, final String attributeContainerName) {
		for (StudentProfileItemAttributeDTO dto : dtos) {
			if (dto.getAttributeContainerName().equals(attributeContainerName)) {
				return dto;
			}
		}
		LOGGER.debug("Could not find attribute container \"" + attributeContainerName + "\" -- returning null");
		return null;
	}
	
	/**
	 * Perform special output for certain columns of the PNP report.
	 * @param dtos
	 * @param attributeContainerName
	 * @param attributeNames
	 * @return
	 */
	private String[] processSpecialOutput(final List<StudentProfileItemAttributeDTO> dtos, final String attributeContainerName,
			final String[] attributeNames) {
		int length = "Braille".equals(attributeContainerName) ? (attributeNames.length-1) : attributeNames.length;
		String[] ret = new String[length];
		StudentProfileItemAttributeDTO dto = getPNPContainerValue(dtos, attributeContainerName);
		if (dto == null) {
			for (int x = 0; x < ret.length; x++) {
				ret[x] = "N/A";
			}
			return ret;
		}
		String viewOption = dto.getViewOption();
		String selectedValue = dto.getSelectedValue();
		int index = 0;
		if (attributeContainerName.equals("presentation")) {
			for (int x = 0; x < attributeNames.length; x++) {
				String value = "";
				String accommodationName = "";
				if (attributeNames[x].equals("readsAssessmentOutLoud")) {
					accommodationName = "assessment";
				} else if (attributeNames[x].equals("useTranslationsDictionary")) {
					accommodationName = "translations";
				} else if (attributeNames[x].equals("someotheraccommodation")) {
					accommodationName = "accommodation";
				}
				value = selectedValue.contains(accommodationName) ? "Selected" : "Not Selected";
				
				if (value.equals("Not Selected") || value.equals("")) {
					if (viewOption != null && (viewOption.contains("disable") || viewOption.contains("hide"))) {
						value = "N/A";
					}
				}
				ret[index++] = value;
			}
		} else if (attributeContainerName.equals("response")) {
			for (int x = 0; x < attributeNames.length; x++) {
				String value = "";
				String accommodationName = "";
				if (attributeNames[x].equals("dictated")) {
					accommodationName = "dictated";
				} else if (attributeNames[x].equals("usedCommunicationDevice")) {
					accommodationName = "communication";
				} else if (attributeNames[x].equals("signedResponses")) {
					accommodationName = "responses";
				}
				value = selectedValue.contains(accommodationName) ? "Selected" : "Not Selected";
				
				if (value.equals("Not Selected") || value.equals("")) {
					if (viewOption != null && (viewOption.contains("disable") || viewOption.contains("hide"))) {
						value = "N/A";
					}
				}
				ret[index++] = value;
			}
		} else if(attributeContainerName.equals("Braille")){
			boolean foundBrailleFileType = false;
			for (int x = 0; x < attributeNames.length; x++) {
				String value = generatePNPColumnValue(dtos, attributeContainerName, attributeNames[x]);				
				if (attributeNames[x].equals("assignedSupport")) {
					ret[index++] = value;
				} else if (attributeNames[x].equals("activateByDefault")) {
					ret[index++] = value;
				} else if (attributeNames[x].equals("ebaeFileType") && value != null
						&& value.equals("Selected")) {
					ret[index++] = "EBAE";
					foundBrailleFileType = true;
				} else if (attributeNames[x].equals("uebFileType") && value != null
						&& value.equals("Selected")) {
					ret[index++] = "UEB";
					foundBrailleFileType = true;
				} else if (attributeNames[x].equals("usage")) {
					ret[index++] = value;
				}
				if(!foundBrailleFileType && x == 3){
					ret[index++] = "N/A";
				} else if (value.equals("Not Selected") || value.equals("")) {
					if (viewOption != null && (viewOption.contains("disable") || viewOption.contains("hide"))) {
						value = "N/A";
					}
				}
			}
		}
		return ret;
	}
	
	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}
		return s;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void updateModuleReport(ModuleReport moduleReport) {
		if (moduleReport != null) {
			Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			moduleReport.setStatusId(completedStatusid);
			moduleReport.setModifiedDate(new Date());
			moduleReportDao.updateByPrimaryKeySelective(moduleReport);
		}
	}
	
	/**
	 * Common function to write CSV reports and update the corresponding modulereport record to complete status.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void writeReport(Long moduleReportId, List<String[]> lines,String typeName) throws IOException {
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		if(moduleReport!=null){
			writeCSV(lines, moduleReport,typeName, true);
			
			Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			moduleReport.setStatusId(completedStatusid);
			moduleReport.setModifiedDate(new Date());
			moduleReportDao.updateByPrimaryKeySelective(moduleReport);			
		} else {
			LOGGER.debug("DataReportServiceImpl : writeReport : moduleReport is NULL.");
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateReportStatus(ModuleReport moduleReport, Long completedStatusid) {

		moduleReport.setStatusId(completedStatusid);
		moduleReport.setModifiedDate(new Date());
		moduleReportDao.updateByPrimaryKeySelective(moduleReport);
	}	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateKSDEExtractStatusToComplete(Long moduleReportId) {

		Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");		
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);		
		updateReportStatus(moduleReport, completedStatusid);
	}
	
	
	private void writeExcel(List<Object[]> lines, ModuleReport moduleReport, String typeName, Date createdDate) throws IOException{
		 
	        String fileName;
	        boolean fileExists = false;
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Sheet 1");
	        
	        DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
	        
	        if(moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
	        	fileName = moduleReport.getFileName();
	        	fileExists = true;
	        } else {
	        	fileName = getFileNameExcel(moduleReport,createdDate);
	        }
	        	String folderPath = getFolderPath(moduleReport, typeName);
	    		int rowNum = 0;
	    		
	    		for (Object[] line : lines) {
	                Row row = sheet.createRow(rowNum++);
	                int colNum = 0;
	                for (Object field : line) {
	                    Cell cell = row.createCell(colNum++);
	                    if (field instanceof String) {
	                        cell.setCellValue((String) field);
	                    } else if (field instanceof Integer) {
	                        cell.setCellValue((Integer) field);
	                    }else if(field instanceof Long){
	                    	cell.setCellValue((Long) field);
	                    }
	                }
	            }

	            try {
            	String[] splitFilename = fileName.split("\\.");
            	File tempFile = File.createTempFile(folderPath + java.io.File.separator + splitFilename[0], "."+splitFilename[1]);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
	                workbook.write(outputStream);
	                workbook.close();
	                
                String key = folderPath + java.io.File.separator + fileName;
                
	                if(!fileExists) {
	        		moduleReport.setFileName(key);
		        	}
                s3.synchMultipartUpload(key, tempFile);
                FileUtils.deleteQuietly(tempFile);
	                
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	}
	
	
	
	/**
	 * Common function to write CSV data.
	 * @param lines
	 * @param moduleReport
	 * @param typeName
	 * @param uploadAndDeleteTempFileAfterWrite - Upload the file to S3 and delete the temporary file afterward. In most cases, this should be <code>true</code>.
	 * However, if appending to the file in chunks, it should be <code>false</code> until the very last execution of this method to avoid storing PII on
	 * system disk (regardless of the duration it would be on disk).
	 */
	private void writeCSV(List<String[]> lines, ModuleReport moduleReport, String typeName, boolean uploadAndDeleteTempFileAfterWrite) throws IOException {
		CSVWriter csvWriter = null;
		String fileName;
		boolean fileExists = false;
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
		
		if(moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
			fileName = moduleReport.getFileName();
			fileExists = true;
		} else {
			fileName = getFileName(moduleReport);
		}
		try {
			String folderPath = getFolderPath(moduleReport, typeName);
			String csvFile = fileExists ? fileName : FileUtil.buildFilePath(folderPath, fileName);
			
			//create a local temp file
			File tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + FileUtil.getFileNameFromPath(csvFile));
			
			// Changed to Make KELPA State Return file tab deliminated.
			if("KSDE_KELPA_STATE_REPORT".equalsIgnoreCase(type.getFileName())){
				csvWriter = new CSVWriter(new FileWriter(tempFile, true), '\t', CSVWriter.NO_QUOTE_CHARACTER);
			}
			else if("Educator_Portal_PNP".equalsIgnoreCase(type.getFileName())){
				csvWriter = new CSVWriter(new FileWriter(tempFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER);
			}else {
				csvWriter = new CSVWriter(new FileWriter(tempFile, true), ',');
			}
			csvWriter.writeAll(lines);
			csvWriter.flush();
			
			if(!fileExists) {
				moduleReport.setFileName(csvFile);
				updateModuleReport(moduleReport);
			}
			
			if (uploadAndDeleteTempFileAfterWrite) {
				//send the local temp file to s3
				s3.synchMultipartUpload(csvFile, tempFile);
				//delete the local temp file
				FileUtils.deleteQuietly(tempFile);
			}
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
			throw ex;
		} finally {
			if(csvWriter != null) {
				csvWriter.close();
			}
		}
	}
	
	private String getFileNameExcel(ModuleReport moduleReport, Date createDate) {
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
		String fileName = type.getFileName();
		Organization organization = organizationService.get(moduleReport.getOrganizationId());
		TimeZone tz = organizationService.getTimeZoneForOrganization(moduleReport.getOrganizationId());
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
		dateFormat.setTimeZone(tz);
		// Server is in GMT and need to convert to US/Central time else showing future date.
		if(createDate == null) {
			createDate = new Date();
		}
		String currentDateStr = dateFormat.format(createDate);
		String displayIdentifier = organization.getDisplayIdentifier();
		fileName += "_" + displayIdentifier + "_Uploaded_" + currentDateStr + ".xlsx";

		return fileName;
	}
	

	private String getFileName(ModuleReport moduleReport) {
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
        String fileName = type.getFileName();
        Organization organization = organizationService.get(moduleReport.getOrganizationId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
        TimeZone tz = organizationService.getTimeZoneForOrganization(moduleReport.getOrganizationId());
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
        dateFormat.setTimeZone(tz);
		String currentDateStr = dateFormat.format(new Date());
		String displayIdentifier = organization.getDisplayIdentifier();
		if (displayIdentifier != null && displayIdentifier.trim().length() > 0) {
			displayIdentifier = displayIdentifier.trim().replace(" ", "_");
		}
		if (!"Kite_Scoring_Assignments_Status_Extract".equalsIgnoreCase(type.getFileName())) {
			if ("KSDE_KELPA_STATE_REPORT".equalsIgnoreCase(type.getFileName())) {
				Organization contractingOrg = organization;
		        if (!Boolean.TRUE.equals(organization.getContractingOrganization())) {
		        	contractingOrg = organizationService.getContractingOrganization(moduleReport.getOrganizationId());
		        }
				fileName += "." + displayIdentifier +
					".StudentScores." +
					(contractingOrg.getReportYear() != 0 ? (contractingOrg.getReportYear() + ".") : "") +
					currentDateStr + ".csv";
			} else {
				fileName += "_" + displayIdentifier + "_" + moduleReport.getCreatedUser();
				fileName += "_" + currentDateStr + ".csv";
			}
		} else {
			fileName += "_" + currentDateStr + ".csv";
		}
		return fileName;
	}
	
	private String getFolderPath(ModuleReport moduleReport, String typeName)
	{
		Long orgId = moduleReport.getOrganizationId();
		Organization moduleOrg = organizationService.get(orgId);
		List<Organization> parents = organizationService.getAllParents(orgId);
		
		Map<String, String> orgMap = new HashMap<String, String>();
		orgMap.put(moduleOrg.getOrganizationType().getTypeCode(), moduleReport.getOrganizationId().toString());
		for(Organization organization: parents){
			orgMap.put(organization.getOrganizationType().getTypeCode(), organization.getId().toString());
		}
		HashSet<String> types = new LinkedHashSet<String>();
		types.add("ST");
		types.add("DT");
		types.add("SCH");
		
    	String folderName = typeName+" "+"Extract";
    	String sep = java.io.File.separator;
    	String folderPath = REPORT_PATH + sep + folderName;
    	for(String type: types){
    		if(StringUtils.isNotBlank(orgMap.get(type))){
    			folderPath = folderPath + sep + orgMap.get(type);
    		}
    	}
    	return folderPath;
	}
	
	/**
	 * This is just common code to update the existing modulereport row and insert a new one for the new report.
	 * @param user
	 * @param moduleReportId
	 * @param type
	 * 
	 * @return The new report ID.
	 * @throws JsonProcessingException 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private long updateModuleReportDBRecords(User user, DataReportTypeEnum type, Long moduleReportId,
			Long orgId, Long orgTypeId, Map<String, Object> params) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		if(params != null && !params.isEmpty()){
			jsonData = objectMapper.writeValueAsString(params);
		}
		Date currentDate = new Date();
		Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
		Date currCentralTime = currentTime.getTime();
		currentTime.set(Calendar.HOUR_OF_DAY, queuedExtractStartTime);
		Date extractQStartDate = currentTime.getTime();
		
		currentTime.set(Calendar.HOUR_OF_DAY, queuedExtractEndTime);
		Date extractQEndDate = currentTime.getTime();
		
		Long inQueueStatusId = 0L;
        Groups userRole = groupService.getGroup(user.getCurrentGroupsId());
        
        if(type.getId() == DataReportTypeEnum.KSDE_ELA_AND_MATH_RETURN_FILE.getId() || type.getId() == DataReportTypeEnum.KSDE_SOCIAL_STUDIES_RETURN_FILE.getId()
        		|| type.getId() == DataReportTypeEnum.KSDE_SCIENCE_RETURN_FILE.getId()
        		|| type.getId() == DataReportTypeEnum.KSDE_KELPA_STATE_RETURN_FILE.getId()) {
        	inQueueStatusId = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");        	
        } else {
        	inQueueStatusId = categoryDao.getCategoryId("IN_QUEUE", "PD_REPORT_STATUS");             	
        }
		
		ModuleReport moduleReport = null;
		if(moduleReportId > 0) {
			moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
			moduleReport.setActiveFlag(false);
			if(moduleReport.getStatusId().equals(inQueueStatusId)){
				moduleReport.setModifiedDate(currentDate);
				moduleReport.setModifiedUser(user.getId().intValue());
			}
			moduleReport.setJsonData(jsonData);
			moduleReportDao.updateByPrimaryKeySelective(moduleReport);
		}
        
        moduleReport = new ModuleReport();
		moduleReport.setGroupId(user.getCurrentGroupsId());
		moduleReport.setReportType(type.getName());
		moduleReport.setDescription(type.getDescription());
		Long organizationId = user.getContractingOrgId()== 0 ? user.getCurrentOrganizationId():user.getContractingOrgId();
		moduleReport.setStateId(organizationId);
		moduleReport.setStatusId(inQueueStatusId);
		moduleReport.setActiveFlag(true);
		moduleReport.setCreatedUser(user.getId().intValue());
		moduleReport.setCreatedDate(currentDate);
		moduleReport.setModifiedUser(user.getId().intValue());
		moduleReport.setModifiedDate(currentDate);
		moduleReport.setReportTypeId(type.getId());
		moduleReport.setOrganizationId(orgId);
		moduleReport.setOrganizationTypeId(orgTypeId);
		moduleReport.setJsonData(jsonData);
		if(type.getQueued().equalsIgnoreCase("true")
				&& userRole != null && userRole.getGroupCode() != null
				&& !userRole.getGroupCode().equalsIgnoreCase("SSAD")
				&& currCentralTime.before(extractQStartDate) 
				&& currCentralTime.after(extractQEndDate) ) {

			moduleReport.setStartTime(extractQStartDate);
		} else {
			moduleReport.setStartTime(currentDate);
		}
		moduleReportDao.insertSelective(moduleReport);
		
		return moduleReport.getId();
	}
	
	@Autowired(required = true)
	private StudentService studentService;
	
	protected Comparator<String[]> enrollmentComparator = new Comparator<String[]>(){
		public int compare(String[] a, String[] b) {
			int comp = 0;
			
			// index column representations (if columns change, this will need to change as well):
			// 0 - state
			// 1 - district
			// 2 - school
			// 7 - student last name
			int columnIndexes[] = {0, 1, 2, 7};
			
			// "check" just represents if either string is null at each level, and is -1 or 1 based on which string is null
			for (int x = 0; x < a.length && x < b.length && x < columnIndexes.length; x++) {
				int index = columnIndexes[x];
				int check = checkForNull(a, b, index);
				if (check != 0) {
					return check;
				}
				if(StringUtils.isNotBlank(a[index]) || StringUtils.isNotBlank(b[index]) ){
                    comp = a[index].compareTo(b[index]);
                } 
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
		
		private final int checkForNull(String[] a, String[] b, int index) {
			if (StringUtils.isBlank(a[index])&& StringUtils.isNotBlank(b[index]))  return -1;
			if(StringUtils.isNotBlank(a[index]) && StringUtils.isBlank(b[index])) return 1;
			return 0;
		}
	};
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15234 : Data extract - Enrollment
	 * This method is responsible for getting the data and putting it into the csv file for enrollment extract.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startEnrollmentExtractGeneration(UserDetailImpl userDetails, Long moduleReportId,
			Long requestedOrgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		
		Long currentUserAssessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		List<Integer> assessmentProgramsInt = (List)additionalParams.get("assessmentProgramIds");
		List<Long> assessmentPrograms = new ArrayList<Long>();
		for(Integer apId : assessmentProgramsInt) {
			assessmentPrograms.add(apId.longValue());
		}
		AssessmentProgram assessmentProgramCode = null;
		boolean isPLTW=false;
		for(int i = 0; i< assessmentPrograms.size(); i++) {
			assessmentProgramCode = assessmentProgramService.findByAssessmentProgramId(assessmentPrograms.get(i).longValue());	
			if(assessmentProgramCode.getAbbreviatedname().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW = true;
				break;
			}
		}
		/**
		 * US16424: Align data extract with upload enrollment: Added and removed columns 
		 */
		/**
		 * Prepare header row for the CSV file.
		 * 
		 * NOTE, if the columns are changed, the comparator above may have to be changed as well, to keep correct sorting.
		 */	
		
		List<String> colHeaderList = new ArrayList<String>();
		colHeaderList.add("Extract_State");
		colHeaderList.add("Extract_District");
		colHeaderList.add("Extract_School_Name");
		colHeaderList.add("Extract_Last_Modified_Time");
		colHeaderList.add("Extract_Last_Modified_By");
		if(!isPLTW){ colHeaderList.add("Accountability_District_Identifier"); }
		if(!isPLTW){ colHeaderList.add("Accountability_School_Identifier"); }
		colHeaderList.add("Attendance_District_Identifier");
		colHeaderList.add("Student_Legal_Last_Name");
		colHeaderList.add("Student_Legal_First_Name");
		colHeaderList.add("Student_Legal_Middle_Name");
		colHeaderList.add("Generation_Code");
		colHeaderList.add("Gender");
		colHeaderList.add("Date_of_Birth");
		colHeaderList.add("Current_Grade_Level");
		colHeaderList.add("Local_Student_Identifier");
		colHeaderList.add("State_Student_Identifier");
		colHeaderList.add("Current_School_Year");
		colHeaderList.add("Attendance_School_Program_Identifier");
		if(!isPLTW){ colHeaderList.add("School_Entry_Date"); }
		if(!isPLTW){ colHeaderList.add("District_Entry_Date"); }
		if(!isPLTW){ colHeaderList.add("State_Entry_Date"); }
		colHeaderList.add("Comprehensive_Race");
		if(!isPLTW){ colHeaderList.add("Primary_Disability_Code"); }
		if(!isPLTW){ colHeaderList.add("Gifted_Student"); }
		colHeaderList.add("Hispanic_Ethnicity");
		if(!isPLTW){ colHeaderList.add("First_Language"); }
		if(!isPLTW){ colHeaderList.add("ESOL_Participation_Code"); }
		colHeaderList.add("Assessment_Program_1");
		colHeaderList.add("Assessment_Program_2");
		colHeaderList.add("Assessment_Program_3");
		
		String[] columnHeaders = colHeaderList.toArray(new String[colHeaderList.size()]);
		
		
		List<String[]> lines = new ArrayList<String[]>();
		
		/**
		 * Call business method to get the list of rows for enrollments
		 */
		User user = userDetails.getUser();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		
		Organization org = dataExtractService.getOrganization(requestedOrgId);
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, requestedOrgId);	
		SimpleDateFormat entryDateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YYYY, requestedOrgId);		
		
		List<Long> orgIds = new ArrayList<Long>();
		if (org.getTypeCode().equalsIgnoreCase("ST")) {
			List<Organization> schools = dataExtractService.getAllChildrenByType(requestedOrgId, "SCH");
			orgIds.addAll(AARTCollectionUtil.getIds(schools));
		} else {
			orgIds.add(requestedOrgId);
		}
		
		for (Long orgId : orgIds) {
			
			List<ViewStudentDTO> studentInformationRecords = dataExtractService.getViewStudentInformationRecordsExtract(orgId, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear,assessmentPrograms);
			
			/**
			 * Convert the retrieved list of enrollment data inot string array so that it can be passed to the CSV maker to be written.
			 */
			if(CollectionUtils.isNotEmpty(studentInformationRecords)){
				for(ViewStudentDTO dto : studentInformationRecords){
					
					/**
					 * Venkata Krishna Jagarlamudi
					 * DE8018: US15234 Enroll extract - get errors if upload extract file after deleting first 3 columns
					 * Instead of filling the boolean values in enrollments file we are now inserting the status as 'yes' or 'no' or '' which are valid entries for 
					 * giftedstudent value in upload enrollment file.
					 */
					String isGiftedStudent = "";
					if(dto.getGiftedStudent() == null) {
						isGiftedStudent = "";
					} else if(dto.getGiftedStudent()) {
						isGiftedStudent = "Yes";
					} else if(!dto.getGiftedStudent()) {
						isGiftedStudent = "No";
					}
					
					/**
					 * US16424: Align data extract with upload enrollment 
					 */
					String [] stdAssessmentPrograms =  dto.getStudentAssessmentProgram().split(",");
					String lastModifiedUserName = "";
					//DE11886 : If record is updated through other source, display that in lastmodifieduser column, otherwise use the lastmodifieduser from DB.
					//Display source type only for KIDS.
					AssessmentProgram kapAp = dataExtractService.findByAbbreviatedName("KAP");
					
					if(dto.getSourceType() != null && !StringUtils.isEmpty(dto.getSourceType().trim()) 
							&& (kapAp != null && currentUserAssessmentProgramId != null
							&& currentUserAssessmentProgramId.equals(kapAp.getId())) 
							&& (dto.getSourceType().equals("TASC") || dto.getSourceType().equals("TEST"))){
						lastModifiedUserName = dto.getSourceType();
					}else if(dto.getEnrlModifiedUser()!=null){
						lastModifiedUserName = dto.getLastModifiedEnrlUserName();
					}
					
					if(isPLTW) {
						lines.add(new String[]{
								dto.getStateName()
								, dto.getDistrictName()
								//,(dto.getSchoolName() != null && !"".equals(dto.getSchoolName()) ? dto.getSchoolName() : dto.getAttendanceSchoolDisplayIdentifiers())
								,dto.getSchoolName()
								,(dto.getEnrlModifiedDate() != null ? dateFormat.format(dto.getEnrlModifiedDate()) : StringUtils.EMPTY)
								,(lastModifiedUserName)
			            		, (dto.getResidenceDistrictIdentifiers() == null ? "" : dto.getResidenceDistrictIdentifiers().toString())
			            		, dto.getLegalLastName()	
			            		, dto.getLegalFirstName()
			            		, dto.getLegalMiddleName()
			            		, dto.getGenerationCode()
			            		, (dto.getGender()==null ? "" : dto.getGender()+"")
			            		, (dto.getDateOfBirth()==null || "null".equalsIgnoreCase(dto.getDateOfBirth().toString())? "" : dto.getDateOfBirthStr())
			            		, dto.getGradeCourseName()
			            		, dto.getLocalStudentIdentifiers()
			            		, dto.getStateStudentIdentifier()
			            		, dto.getCurrentSchoolYears()
			            		, dto.getAttendanceSchoolDisplayIdentifiers()
			            		, dto.getComprehensiveRace()
			            		, dto.getHispanicEthnicity()
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 0 ? stdAssessmentPrograms[0]:""
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 1 ? stdAssessmentPrograms[1]:""
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 2 ? stdAssessmentPrograms[2]:""
			            		});
					}else {
						lines.add(new String[]{
								dto.getStateName()
								, dto.getDistrictName()
								//,(dto.getSchoolName() != null && !"".equals(dto.getSchoolName()) ? dto.getSchoolName() : dto.getAttendanceSchoolDisplayIdentifiers())
								,dto.getSchoolName()
								,(dto.getEnrlModifiedDate() != null ? dateFormat.format(dto.getEnrlModifiedDate()) : StringUtils.EMPTY)
								,(lastModifiedUserName)
								,dto.getAccountabilityDistrictIdentifier()
			            		, dto.getAypSchoolId()
			            		, (dto.getResidenceDistrictIdentifiers() == null ? "" : dto.getResidenceDistrictIdentifiers().toString())
			            		, dto.getLegalLastName()	
			            		, dto.getLegalFirstName()
			            		, dto.getLegalMiddleName()
			            		, dto.getGenerationCode()
			            		, (dto.getGender()==null ? "" : dto.getGender()+"")
			            		, (dto.getDateOfBirth()==null || "null".equalsIgnoreCase(dto.getDateOfBirth().toString())? "" : dto.getDateOfBirthStr())
			            		, dto.getGradeCourseName()
			            		, dto.getLocalStudentIdentifiers()
			            		, dto.getStateStudentIdentifier()
			            		, dto.getCurrentSchoolYears()
			            		, dto.getAttendanceSchoolDisplayIdentifiers()
			            		, (dto.getExtractSchoolEntryDate() != null ? entryDateFormat.format(dto.getExtractSchoolEntryDate()) : StringUtils.EMPTY)
			            		, (dto.getExtractDistrictEntryDate() != null ? entryDateFormat.format(dto.getExtractDistrictEntryDate()) : StringUtils.EMPTY)
			            		, (dto.getExtractStateEntryDate() != null ? entryDateFormat.format(dto.getExtractStateEntryDate()) : StringUtils.EMPTY)
			            		, dto.getComprehensiveRace()
			            		, dto.getPrimaryDisabilityCode()
			            		, isGiftedStudent
			            		, dto.getHispanicEthnicity()
			            		, dto.getFirstLanguageCode()
			            		, dto.getEsolParticipationCode()
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 0 ? stdAssessmentPrograms[0]:""
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 1 ? stdAssessmentPrograms[1]:""
			            		, stdAssessmentPrograms != null && stdAssessmentPrograms.length > 2 ? stdAssessmentPrograms[2]:""
			            		});
					}
					
				}
			}
		}
		Collections.sort(lines, enrollmentComparator);
		lines.add(0, columnHeaders);
		/**
		 * Write data to CSV and set flag as complete in database.
		 */
		writeReport(moduleReportId, lines,typeName);
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startUserDataExtractGeneration(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		String[] userColumnHeaders = { "State", "Legal First Name",
				"Legal Last Name", "Educator Identifier", "Email",
				"Organization ID", "Organization Level", "Organization Name",
				"District Organization ID", "User Status", "Create date" };
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		List<String> roles = groupService.getGroupNames();
		 
		boolean includeInternalUsers=(Boolean) additionalParams.get("includeInternalUsers");
		List<String> assessmentProgram = assessmentProgramService.getProgramName(orgId);	
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);
		
		String[] rowHeaders = (String[]) ArrayUtils.addAll(userColumnHeaders,
				assessmentProgram.toArray());
		 //Added during US16425 Assessment program before adding roles
		String[] rowHeader = (String[]) ArrayUtils.addAll(rowHeaders,
				roles.toArray(new String[roles.size()]));
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(rowHeader);
		List<UserDetailsAndRolesDTO> userDetailsAndRoles = dataExtractService.getUserDetailsAndRolesByOrgId(orgId,assessmentPrograms,includeInternalUsers);
		fillUserDetailsInExcelRow(userDetailsAndRoles,assessmentProgram, roles, excelRows, dateFormat);
		writeReport(moduleReportId, excelRows,typeName);
		return true;
	}

	private void fillUserDetailsInExcelRow(
			List<UserDetailsAndRolesDTO> userDetailsAndRoles, List<String> assessmentPrograms,
			List<String> roles, List<String[]> excelRows, SimpleDateFormat dateFormat) {
		if (userDetailsAndRoles != null && !userDetailsAndRoles.isEmpty()) {
			for (UserDetailsAndRolesDTO userDataExtract : userDetailsAndRoles) {
				List<String> userDetails = new ArrayList<String>();
				// This needs to be in the same order while writing to excel
				userDetails.add(userDataExtract.getState());
				userDetails.add(userDataExtract.getLegalFirstName());
				userDetails.add(userDataExtract.getLegalLasttName());
				userDetails.add(userDataExtract.getEducatorIdentifier());
				userDetails.add(userDataExtract.getEmail());
				userDetails.add(userDataExtract.getOrganizationId());
				userDetails.add(userDataExtract.getOrganizationLevel());
				userDetails.add(userDataExtract.getOrganizationName());
				userDetails.add(userDataExtract.getDistrictOrgId());
				if(userDataExtract.getActiveFlag() && StringUtils.isNotEmpty(userDataExtract.getUserStatus()) ) {
					userDetails.add(userDataExtract.getUserStatus());
				} else {
					userDetails.add("Inactive");
				}				
				userDetails.add(userDataExtract.getCreatedDate() != null ? dateFormat.format(userDataExtract.getCreatedDate()) : StringUtils.EMPTY);
				
				// Added During - US16425
				String[] assessmentProgramName = userDataExtract.getAssessmentProgram().split(",");
				List<String> userDesignatedProgramName = Arrays.asList(assessmentProgramName);
				String[] assessmentProgramData = new String[assessmentPrograms.size()];
				for(String assessmentProgram : assessmentPrograms ) {
					if(userDesignatedProgramName.contains(assessmentProgram)) {
						assessmentProgramData[assessmentPrograms.indexOf(assessmentProgram)] = "X";
					} else {
						assessmentProgramData[assessmentPrograms.indexOf(assessmentProgram)] = "";
					}
				}
				
				String[] userRoles = userDataExtract.getRoles().split(",");
				List<String> userDesignatedRoles = Arrays.asList(userRoles);
				String[] rolesData = new String[roles.size()];
				for (String role : roles) {
					if (userDesignatedRoles.contains(role)) {
						rolesData[roles.indexOf(role)] = "X";
					} else {
						rolesData[roles.indexOf(role)] = "";
					}					
				}	
				String[] userDetailsRow = (String[]) ArrayUtils.addAll(
						userDetails.toArray(new String[userDetails.size()]), assessmentProgramData);
				String[] userDetailsExcelRow = (String[]) ArrayUtils.addAll(
						userDetailsRow, rolesData);
				excelRows.add(userDetailsExcelRow);				
			}
		}
	}
	
	protected Comparator<String[]> rosterComparator = new Comparator<String[]>(){
		public int compare(String[] a, String[] b) {
			int comp = 0;
			
			// index column representations (if columns change, this will need to change as well):
			// 0 - state
			// 1 - district
			// 2 - school
			// 5 - roster name
			int columnIndexes[] = {0, 1, 2, 5};
			
			// "check" just represents if either string is null at each level, and is -1 or 1 based on which string is null
			for (int x = 0; x < a.length && x < b.length && x < columnIndexes.length; x++) {
				int index = columnIndexes[x];
				int check = checkForNull(a, b, index);
				if (check != 0) {
					return check;
				}
				if(StringUtils.isNotBlank(a[index]) || StringUtils.isNotBlank(b[index]) ){
                    comp = a[index].compareTo(b[index]);
                } 
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
		
		private final int checkForNull(String[] a, String[] b, int index) {
			if (StringUtils.isBlank(a[index])&& StringUtils.isNotBlank(b[index]))  return -1;
			if(StringUtils.isNotBlank(a[index]) && StringUtils.isBlank(b[index])) return 1;
			return 0;
		}
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startRosterExtractGeneration(UserDetailImpl userDetails, Long moduleReportId,
			Long requestedOrgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		
		List<Integer> assessmentProgramsInt = (List)additionalParams.get("assessmentProgramIds");
		List<Long> assessmentPrograms = new ArrayList<Long>();
		for(Integer apId : assessmentProgramsInt) {
			assessmentPrograms.add(apId.longValue());
		}
		AssessmentProgram assessmentProgramCode = null;
		boolean isPLTW=false;
		for(int i = 0; i< assessmentPrograms.size(); i++) {
			assessmentProgramCode = assessmentProgramService.findByAssessmentProgramId(assessmentPrograms.get(i).longValue());	
			if(assessmentProgramCode.getAbbreviatedname().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW = true;
				break;
			}
		}
		// NOTE, if the columns change, then the comparator above may have to change as well, to keep correct sorting
		List<String> colHeaderList = new ArrayList<String>();
			colHeaderList.add("Extract_State");
			colHeaderList.add("Extract_District");
			colHeaderList.add("Extract_School");
			colHeaderList.add("Extract_Last_Modified_Time");
			colHeaderList.add("Extract_Last_Modified_By");
			colHeaderList.add("Extract_Assessment_Program_1");
			colHeaderList.add("Extract_Assessment_Program_2");
			colHeaderList.add("Extract_Assessment_Program_3");
			colHeaderList.add("Extract_Assessment_Program_4");
			colHeaderList.add("Extract_Student_Grade");
			colHeaderList.add("Roster Name");
			if(isPLTW){
				colHeaderList.add("Course");
			}else{
				colHeaderList.add("Subject");
			}
			if(!isPLTW){
				colHeaderList.add("Course");
			}
			colHeaderList.add("School Identifier");
			colHeaderList.add("School Year");
			colHeaderList.add("State Student Identifier");
			colHeaderList.add("Local Student Identifier");
			colHeaderList.add("Student Legal First Name");
			colHeaderList.add("Student Legal Last Name");
			colHeaderList.add("Educator Identifier");
			colHeaderList.add("Educator First Name");
			colHeaderList.add("Educator Last Name");
			colHeaderList.add("Remove from roster"); // will always be an empty column, so don't be alarmed if we never add it
			
			String[] columnHeaders = colHeaderList.toArray(new String[colHeaderList.size()]);
		
		User user = userDetails.getUser();
		List<String[]> lines = new ArrayList<String[]>();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		 /*
		 * added for US-18883
		 */
		List<AssessmentProgram> userAssessmentPrgs = dataExtractService.getAllAssessmentProgramByUserId(user.getId());
		List<String> userAssessmentPrgscode=new ArrayList<String>();
		for(AssessmentProgram userAssessmentPrg:userAssessmentPrgs){
			userAssessmentPrgscode.add(userAssessmentPrg.getAbbreviatedname());
		}
		Organization org = dataExtractService.getOrganization(requestedOrgId);
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, requestedOrgId);
		
		List<Long> orgIds = new ArrayList<Long>();
		if (org.getTypeCode().equalsIgnoreCase("ST")) {
			List<Organization> districts = dataExtractService.getAllChildrenByType(requestedOrgId, "DT");
			orgIds.addAll(AARTCollectionUtil.getIds(districts));
		} else {
			orgIds.add(requestedOrgId);
		}
		
		for (Long orgId : orgIds) {
			List<RosterExtractDTO> dtos = dataExtractService.getRosterDataExtractForOrg(orgId, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear,assessmentPrograms);
			
			for (RosterExtractDTO dto : dtos) {
				String lastModifiedUserName = "";
				List<String> raminingassesmenProgram=new ArrayList<String>();
				String assesmenProgram1="";
				String assesmenProgram2="";
				String assesmenProgram3="";
				String assesmenProgram4="";
				String[] studentAssesmentPrograms=dto.getStudentAssesmentPrograms().split(",");
				 List<String> studentAssesmentProgramList = Arrays.asList(studentAssesmentPrograms);
				 
			if(studentAssesmentProgramList.size()>1){
					for(String stdentAssesmentprogram:studentAssesmentProgramList){
						for(String userAssesmentprogram:userAssessmentPrgscode){
							if(userAssesmentprogram.equals(stdentAssesmentprogram.trim())){
								assesmenProgram1=stdentAssesmentprogram.trim();							
								break;
							}
						}
					}				
					for(String stdentAssesmentprogram:studentAssesmentProgramList){
						if(!assesmenProgram1.equals(stdentAssesmentprogram.trim())){
							raminingassesmenProgram.add(stdentAssesmentprogram);
						}
					}
					int size=raminingassesmenProgram.size();
					if(size==1){
						assesmenProgram2=raminingassesmenProgram.get(0);
					}else if(size==2){
						assesmenProgram2=raminingassesmenProgram.get(0);
						assesmenProgram3=raminingassesmenProgram.get(1);
					}else if(size==3){
						assesmenProgram2=raminingassesmenProgram.get(0);
						assesmenProgram3=raminingassesmenProgram.get(1);
						assesmenProgram4=raminingassesmenProgram.get(2);
					}

				}else if(studentAssesmentProgramList.size()==1){
					for(String stdentAssesmentprogram:studentAssesmentProgramList){
						assesmenProgram1=stdentAssesmentprogram;
					}
				}
					
				if(dto.getModifiedUser()!=null)
				{
					lastModifiedUserName = dto.getLastModifiedEnrlUserName();
				}
				
				if(isPLTW) {
					lines.add(new String[]{
							wrapForCSV(dto.getState()),
							wrapForCSV(dto.getDistrict()),
							wrapForCSV(dto.getSchool()),
							wrapForCSV(dto.getModifiedDate() != null ? dateFormat.format(dto.getModifiedDate()) : StringUtils.EMPTY),
							wrapForCSV(lastModifiedUserName),
							wrapForCSV(assesmenProgram1),
							wrapForCSV(assesmenProgram2),
							wrapForCSV(assesmenProgram3),
							wrapForCSV(assesmenProgram4),
							wrapForCSV(dto.getStudentCurrentGrade()),
							wrapForCSV(dto.getRosterName()),
							wrapForCSV(dto.getContentAreaName()),
							wrapForCSV(dto.getSchoolIdentifier()),
							(dto.getCurrentSchoolYear() == null ? "" : String.valueOf(dto.getCurrentSchoolYear().intValue())),
							wrapForCSV(dto.getStateStudentIdentifier()),
							wrapForCSV(dto.getLocalStudentIdentifier()),
							wrapForCSV(dto.getStudentFirstName()),
							wrapForCSV(dto.getStudentLastName()),
							wrapForCSV(dto.getTeacherId()),
							wrapForCSV(dto.getTeacherFirstName()),
							wrapForCSV(dto.getTeacherLastName()),
							wrapForCSV("")
							
						});
				}else {
					lines.add(new String[]{
						wrapForCSV(dto.getState()),
						wrapForCSV(dto.getDistrict()),
						wrapForCSV(dto.getSchool()),
						wrapForCSV(dto.getModifiedDate() != null ? dateFormat.format(dto.getModifiedDate()) : StringUtils.EMPTY),
						wrapForCSV(lastModifiedUserName),
						wrapForCSV(assesmenProgram1),
						wrapForCSV(assesmenProgram2),
						wrapForCSV(assesmenProgram3),
						wrapForCSV(assesmenProgram4),
						wrapForCSV(dto.getStudentCurrentGrade()),
						wrapForCSV(dto.getRosterName()),
						wrapForCSV(dto.getContentAreaName()),
						wrapForCSV(dto.getGradeCourseName()),
						wrapForCSV(dto.getSchoolIdentifier()),
						(dto.getCurrentSchoolYear() == null ? "" : String.valueOf(dto.getCurrentSchoolYear().intValue())),
						wrapForCSV(dto.getStateStudentIdentifier()),
						wrapForCSV(dto.getLocalStudentIdentifier()),
						wrapForCSV(dto.getStudentFirstName()),
						wrapForCSV(dto.getStudentLastName()),
						wrapForCSV(dto.getTeacherId()),
						wrapForCSV(dto.getTeacherFirstName()),
						wrapForCSV(dto.getTeacherLastName()),
						wrapForCSV("")
						
					});
				}
			}
		}
		Collections.sort(lines, rosterComparator);
		lines.add(0, columnHeaders);
		writeReport(moduleReportId, lines,typeName);
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startQuestarDataExtractGeneration(UserDetailImpl userDetails, Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		
		String[] columnHeaders = {
			"District Code",
			"School Code",
			"Subject",
			"State Code",
			"Grade",
			"LName",
			"FName",
			"MName",
			"Kite Number",
			"Student ID",
			"DOB1",
			"Accommodations"
		};
		
		User user = userDetails.getUser();
		ArrayList<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		List<QuestarExtractDTO> dtos = moduleReportDao.getQuestarDataForOrg(orgId, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear,assessmentPrograms);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		for (QuestarExtractDTO dto : dtos) {
			String columnValue = "";
			List<StudentProfileItemAttributeDTO> accommodations =
					dataExtractService.selectStudentAttributesAndContainers(dto.getStudentId(), user);
			String braille = getPNPValue(accommodations, "Braille", "assignedSupport");
			String largePrint = getPNPValue(accommodations, "supportsProvidedByAlternateForm", "largePrintBooklet");
			String paperPencil = getPNPValue(accommodations, "supportsProvidedByAlternateForm", "paperAndPencil");
			String readAloudSupported = getPNPValue(accommodations, "Spoken", "assignedSupport");
			String readAloudSelection = getPNPValue(accommodations, "Spoken", "UserSpokenPreference");
			String readAloudSubject = getPNPValue(accommodations, "Spoken", "preferenceSubject");
			boolean readAloud = "true".equalsIgnoreCase(readAloudSupported) && "textandgraphics".equalsIgnoreCase(readAloudSelection);
			
			if ("true".equalsIgnoreCase(braille)) {
				columnValue = "BR";
			} else if ("true".equalsIgnoreCase(largePrint)) {
				columnValue = "L";
			} else if ("true".equalsIgnoreCase(paperPencil) || "P".equals(dto.getTestTypeCode())) {
				columnValue = "P";
			}
			
			// the student gets no entry if they just need the general assessment,
			// which empty would signify by this point
			if (!"".equals(columnValue)) {
				if (readAloud) {
					boolean addR = false;
					if (!readAloudSubject.isEmpty()) {
						if ("D74".equals(dto.getSubjectAreaCode()) || "SSCIA".equals(dto.getSubjectAreaCode())) { // Sci or science
							addR = readAloudSubject.equals("math_and_science") || readAloudSubject.equals("math_science_and_ELA");
						} else if ("SELAA".equals(dto.getSubjectAreaCode())) {
							addR = readAloudSubject.equals("math_science_and_ELA");
						}
					} else {
						addR = true;
					}
					if (addR) {
						columnValue += "-R";
					}
				}
				lines.add(new String[]{
					wrapForCSV(dto.getDistrictName()),
					wrapForCSV(dto.getSchoolName()),
					wrapForCSV(dto.getSubjectAreaName()),
					wrapForCSV(dto.getStateName()),
					wrapForCSV(dto.getGradeLevel()),
					wrapForCSV(dto.getStudentLastName()),
					wrapForCSV(dto.getStudentFirstName()),
					wrapForCSV(dto.getStudentMiddleName()),
					(dto.getStudentId() == null ? "" : String.valueOf(dto.getStudentId())),
					wrapForCSV(dto.getStateStudentIdentifier()),
					(dto.getStudentDateOfBirth() == null ? "" : dateFormat.format(dto.getStudentDateOfBirth())),
					columnValue
				});
			}
		}
		writeReport(moduleReportId, lines,typeName);
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startTECExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException {
		
		String[] columnHeaders = {
			"Extract_State",
			"Extract_District",
			"Extract_School_Name",
			"Extract_Grade",
			"Record_Type",
			"State_Student_Identifier",
			"Attendance_School_Program_Identifier",
			"Exit_Reason",
			"Exit_Date",
			"Test_Type",
			"Subject",
			"School_Year"
			
		};
		User user = userDetails.getUser();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		List<TECExtractDTO> dtos = dataExtractService.getTECExtractByOrg(orgId, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear,assessmentPrograms);
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		for (TECExtractDTO dto : dtos) {
			lines.add(new String[]{
				dto.getState(),
				dto.getDistrict(),
				dto.getSchool(),
				dto.getGrade(),
				"TEST",
				dto.getStateStudentIdentifier(),
				dto.getAttendanceSchoolIdentifier(),
				"",
				"",
				dto.getTestTypeCode(),
				dto.getSubjectCode(),
				dto.getCurrentSchoolYear() == null ? "" : dto.getCurrentSchoolYear().toString()
				
			});
		}
		writeReport(moduleReportId, lines,typeName);
		return true;
	}
	/*
     * Added during US16343 : for Extracting reports on Test media Form assign to TestCollection for quality check
     */	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startFormMediaExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName ) throws IOException {
		
		String fromDate = (String)additionalParams.get("fromDate");
		String toDate = (String)additionalParams.get("toDate");
		
		List<Long> assessmentProgramId = (List)additionalParams.get("assessmentProgramIds");
		String qcCompleteStatus = (String) additionalParams.get("qcCompleteStatus");
		String media = (String) additionalParams.get("media");
		String[] columnHeaders = {"Form Name","Form ID-Content Builder","Form ID-Educator Portal","Test Name",
								  "Test Collection","Resource File Name","Content Builder Media Name",
								  "Content Builder Media ID","Section Number","Form Last Modified Date",
								  "Form Publication Status","Testlet ID","QC Complete status flag"};
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);		
		
		List<TestFormMediaResourceDTO> testFormMediaResource = testSessionService.getTestFormMediaResource(fromDate,
				toDate,assessmentProgramId,qcCompleteStatus,media);
		
		if(CollectionUtils.isNotEmpty(testFormMediaResource)){
			
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);
			
			for(TestFormMediaResourceDTO testFormMediaResourceDTO : testFormMediaResource){
				lines.add(new String[]{
						testFormMediaResourceDTO.getFormName(),
						(testFormMediaResourceDTO.getContentBuilderFormId() == null ? "" : String.valueOf(testFormMediaResourceDTO.getContentBuilderFormId())),
						(testFormMediaResourceDTO.getEducatorPortalFormId() == null ? "" : String.valueOf(testFormMediaResourceDTO.getEducatorPortalFormId())),
						testFormMediaResourceDTO.getTestName(),
						testFormMediaResourceDTO.getTestCollection(),
						testFormMediaResourceDTO.getResourceFileName(),
						testFormMediaResourceDTO.getContentBuilderMediaName(),
						(testFormMediaResourceDTO.getContentBuilderMediaId() == null ? "" : String.valueOf(testFormMediaResourceDTO.getContentBuilderMediaId())),
						(testFormMediaResourceDTO.getSectionNumber() == null ? "" : String.valueOf(testFormMediaResourceDTO.getSectionNumber())),
						(testFormMediaResourceDTO.getLastModifiedDate() == null ? "" : dateFormat.format(testFormMediaResourceDTO.getLastModifiedDate())),
						(testFormMediaResourceDTO.getCreateDate() == null ? "" : dateFormat.format(testFormMediaResourceDTO.getCreateDate())),
						 testFormMediaResourceDTO.getTestletId(),
						(testFormMediaResourceDTO.getQcCompleteStatus() == null ? "" : String.valueOf(testFormMediaResourceDTO.getQcCompleteStatus()))
						
				});
				
			}
		}
		writeReport(moduleReportId, lines,typeName);	
		
		return true;
	}
	
	
	public HashMap <Long, OperationalTestWindow> getTestCycleOTWMap(List<TestingCycle> testCycleWindows){
		HashMap <Long, OperationalTestWindow> result = new HashMap<>();
		for(TestingCycle curTestingCycle : testCycleWindows) {
			Long curOperationalTestWindowId = curTestingCycle.getOperationalTestWindowId();
			List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowService.selectOperationalTestWindowById(curOperationalTestWindowId);
			 if(operationalTestWindowList != null && operationalTestWindowList.size() > 0) {
					result.put(curTestingCycle.getId(),operationalTestWindowList.get(0));
			 }
		}
		
		return result;
	}

	/**
	 * @author Venkata Krishna Jagarlamudi, Deb
	 * US15568: DLM Test Administration Data Extract
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMTestStatusExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		LOGGER.info("DLM report processing started at " + new Date() + " for report " + moduleReportId);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date iniToDate=null, iniFromDate=null, eoyToDate=null, eoyFromDate=null;
		
		//Code Logic pending to fetch operational testwindow 
		Organization curState = organizationDao.getContractingOrg(orgId);
		boolean isIEState = organizationService.isIEModelState(curState);
		
		List<String[]> lines = new ArrayList<String[]>();
		String[] columnHeadersinit = {
				"Subject",
				"State",
				"District",
				"School ID",
				"School Name",
				"Educator ID",
				"Educator Last Name",
				"Educator First Name",
				"Grade",
				"Student Last Name",
				"Student First Name",
				"Student State ID",
				"Student Local ID"
		};
		
				try {
					
					ArrayList<String> columnHeaderList = new ArrayList<>(Arrays.asList(columnHeadersinit));
					User user = userDetails.getUser();
					String assessmentProgramCode = (String) additionalParams.get("assessmentProgramCode");
					boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
					int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
					
					List<TestingCycle> testCycleWindows = testingCycleMapper.getTestingCyclesByStateNameSchoolYearAssessmentProgramCode(assessmentProgramCode,new Long(currentSchoolYear),curState.getOrganizationName());
					
					if(isIEState) {
						//generating dynamic column header for IE states depending on the testing cycle window
						for(TestingCycle curTestCycleWindow : testCycleWindows) {
							String testCycleName= curTestCycleWindow.getTestingCycleName();
							columnHeaderList.add(testCycleName + " Window # Testlets Not Started");
							columnHeaderList.add(testCycleName + " Window # Testlets In Progress");
							columnHeaderList.add(testCycleName + " Window # Testlets Completed");
							columnHeaderList.add(testCycleName + " Window # Testlets Required");
						}
						columnHeaderList.add("Science Spring Window # Testlets Not Started");
						columnHeaderList.add("Science Spring Window # Testlets In Progress");
						columnHeaderList.add("Science Spring Window # Testlets Completed");
						columnHeaderList.add("Science Spring Window # Testlets Required");
						
					}else {
						//For Year End states
						columnHeaderList.add("Instructional # Testlets Not Started");
						columnHeaderList.add("Instructional # Testlets In Progress");
						columnHeaderList.add("Instructional # Testlets Completed");
						columnHeaderList.add("Instructional # Testlets Required");
						columnHeaderList.add("End of Year # Testlets Not Started");
						columnHeaderList.add("End of Year # Testlets In Progress");
						columnHeaderList.add("End of Year # Testlets Completed");
					}
					columnHeaderList.add("Field Test # Testlets Completed");
					int columnHeaderLength = columnHeaderList.size();
							
					String[] columnHeaders = columnHeaderList.stream().toArray(String[] ::new);	
					lines.add(columnHeaders);
					
					if(testCycleWindows.size()>0) {
						
					HashMap <Long, OperationalTestWindow> testCycleOTWMap = getTestCycleOTWMap(testCycleWindows);
					
					
					OperationalTestWindow currentEOYOTW= operationalTestWindowService.getActiveOperationalTestWindowByStateIdAndAssessmentProgramCode(assessmentProgramCode,curState.getId());
					//year end state : Take the last OTW from the test cycle
					OperationalTestWindow otwFromTestCycle= testCycleOTWMap.get(testCycleWindows.get(testCycleWindows.size()-1).getId());
					if(!isIEState && testCycleWindows.size()>0) {						
						iniToDate = otwFromTestCycle.getExpiryDate();
						iniFromDate = otwFromTestCycle.getEffectiveDate();
					}
					
					//date1.compareTo(date2) > 0
					//We want a window which is opened after the close of the last ITI window
					if(currentEOYOTW!= null && otwFromTestCycle != null && ( otwFromTestCycle.getExpiryDate().compareTo( currentEOYOTW.getEffectiveDate() ))>0) {
						eoyToDate = currentEOYOTW.getExpiryDate();
						eoyFromDate = currentEOYOTW.getEffectiveDate();
					}else if(eoyToDate == null && eoyFromDate== null) {
						try {
							//In case we don't find the spring student tracker OTW 
							//we will update the dates manually to some old dates and in the extract we will get back 0 as results for the columns
							eoyToDate    = format.parse ( CommonConstants.DISTANT_DATE );
							eoyFromDate = format.parse ( CommonConstants.DISTANT_DATE );
						} catch (ParseException e) {
							LOGGER.error("DLM report not able to convert dates from method getOrganizationTestStatusesforIEStates");
							e.printStackTrace();
						} 
					}
					
					//All parameter set now trying to run the query to get the records back
					List<Organization> schools= dataExtractService.getAllChildrenWithParentByType(orgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
					for(Organization school : schools){
						if(isIEState) {
							// Get the OTW from the testing cycle and generate the dynamic extract.
							getOrganizationTestStatusesforIEStates(school.getId(), eoyToDate, eoyFromDate, user,
									shouldOnlySeeRosteredStudents, currentSchoolYear, lines, testCycleWindows, testCycleOTWMap,columnHeaderLength);
						}else
						{
							getOrganizationTestStatuses(school.getId(), iniToDate, iniFromDate, eoyToDate, eoyFromDate, user,
									shouldOnlySeeRosteredStudents, currentSchoolYear, lines);
						}
					}
					}
			} finally{
				writeReport(moduleReportId, lines,typeName);
				
				LOGGER.info("DLM report processing completed at " + new Date() + " for report " + moduleReportId);
			}
			return true;
	}

	private void getOrganizationTestStatuses(Long orgId, Date iniToDate, Date iniFromDate, Date eoyToDate,
			Date eoyFromDate, User user, boolean shouldOnlySeeRosteredStudents, int currentSchoolYear,
			List<String[]> lines) {
		LOGGER.info("DLM report query started at "+ new Date() + " for school : " + orgId);
		List<DLMTestStatusExtractDTO> dlmTestStatusExtract = dataExtractService.dlmTestStatusReport(orgId, shouldOnlySeeRosteredStudents, 
				user.getId(), currentSchoolYear, iniToDate, iniFromDate, eoyToDate, eoyFromDate, user.getContractingOrgId());
		LOGGER.info("DLM report query completed at "+ new Date() + " for school : " + orgId);

		if(dlmTestStatusExtract != null) {
			for(DLMTestStatusExtractDTO dlmTestStatus : dlmTestStatusExtract) {
				List<Organization> organizations = dataExtractService.getAllParents(dlmTestStatus.getAttendanceSchoolId());
				Organization school = dataExtractService.getOrganization(dlmTestStatus.getAttendanceSchoolId());
				Organization district = null;
				Organization state = null;
				for(Organization organization: organizations) {
					if(organization.getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
						district = organization;
					} else if(organization.getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
						state = organization;
					}
				}
				boolean isSocialStudies = CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SOCIAL_STUDIES.equals(dlmTestStatus.getSubjectAbbreviatedName());
				
				lines.add(new String[]{
					dlmTestStatus.getSubjectName(),
					((state != null) ? state.getOrganizationName() : ""),
					((district != null) ? district.getOrganizationName() : ""),			
					((school != null) ? school.getDisplayIdentifier() : ""),
					((school != null) ? school.getOrganizationName() : ""),
					dlmTestStatus.getEducatorId(),
					getNameWithQuotes(dlmTestStatus.getEducatorLastName()),
					getNameWithQuotes(dlmTestStatus.getEducatorFirstName()),
					dlmTestStatus.getGrade(),
					getNameWithQuotes(dlmTestStatus.getStudentLaststName()),
					getNameWithQuotes(dlmTestStatus.getStudentFirstName()),
					dlmTestStatus.getStateStudentId(),
					dlmTestStatus.getLocalStudentId(),
					// as of October 2017, DLM does not use ITI for Social Studies, so these columns will be N/A
					isSocialStudies ? "N/A" : (dlmTestStatus.getInstTestsNotStarted() + ""),
					isSocialStudies ? "N/A" : (dlmTestStatus.getInstTestsInProgress() + ""),
					isSocialStudies ? "N/A" : (dlmTestStatus.getInstTestscompleted() + ""),
					isSocialStudies ? "N/A" : getInstTestsRequired(dlmTestStatus.getStateModel()),
					dlmTestStatus.getEoyTestsNotStarted() + "",
					dlmTestStatus.getEoyTestsInProgress() + "",
					dlmTestStatus.getEoytTestscompleted() + "",
					dlmTestStatus.getNumofEEs() + "",
					dlmTestStatus.getFieldTestsCompleted() + ""
				});
			}
		}
	}
	
	
	private LinkedHashMap <String,String[]> processDLMTestStatusExtract(LinkedHashMap <String,String[]> dlmTestStatusExtractMap,
			List<DLMTestStatusExtractDTO> dlmTestStatusExtract, TestingCycle testingCycle, int columnHeaderLength, int totalTestingCycle, int curTestingCycle) {
		
		//Deb DLM test admin monitoring extract ITI redesign changes
		String[] line = null;
		for( DLMTestStatusExtractDTO curDLMTestStatus :dlmTestStatusExtract) {
			boolean isSocialStudies = CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SOCIAL_STUDIES.equals(curDLMTestStatus.getSubjectAbbreviatedName());
			boolean isScience = CommonConstants.CONTENT_AREA_ABBREVIATED_NAME_SCIENCE.equalsIgnoreCase(curDLMTestStatus.getSubjectAbbreviatedName());
			boolean isSpringCycle = CommonConstants.SPRING_TESTING_CYCLE.equalsIgnoreCase(testingCycle.getTestingCycleName());
			StringBuffer key = new StringBuffer(curDLMTestStatus.getSubjectAbbreviatedName()).append(curDLMTestStatus.getAttendanceSchoolId())
					.append(curDLMTestStatus.getEducatorId()).append(curDLMTestStatus.getStudentId()).append(curDLMTestStatus.getGradeId());
			if(dlmTestStatusExtractMap.containsKey(key.toString())) {
				//fetching from the map
				line = dlmTestStatusExtractMap.get(key.toString());
				//Updating the line
				for(int temp =0 ; temp< totalTestingCycle ; temp++) {
					int colCount = 13;
					if(temp == curTestingCycle && isSocialStudies == false && !(isSpringCycle && isScience)) {
						//If testing cycle found update it
						//Update only for ELA or MAth for spring cylce
						line[colCount++] = (curDLMTestStatus.getInstTestsNotStarted() + "");
						line[colCount++] = (curDLMTestStatus.getInstTestsInProgress() + "");
						line[colCount++] = (curDLMTestStatus.getInstTestscompleted() + "");
						line[colCount++] = getInstTestsRequired(curDLMTestStatus.getStateModel())+ "";
					}else {
						//skiping the test cycle and there are 4 columns for a test cycle
						colCount += 4;
					}
					if(isScience ) {
						//Update only if Science
						line[colCount++] = curDLMTestStatus.getEoyTestsNotStarted() + "";
						line[colCount++] = curDLMTestStatus.getEoyTestsInProgress() + "";
						line[colCount++] = curDLMTestStatus.getEoytTestscompleted() + "";
						line[colCount++] = curDLMTestStatus.getNumofEEs() + "";
					}
				}
			}else {
				//new record to add
				line = new String[columnHeaderLength];
				
				List<Organization> organizations = dataExtractService.getAllParents(curDLMTestStatus.getAttendanceSchoolId());
				Organization school = dataExtractService.getOrganization(curDLMTestStatus.getAttendanceSchoolId());
				Organization district = null;
				Organization state = null;
				for(Organization organization: organizations) {
					if(organization.getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
						district = organization;
					} else if(organization.getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
						state = organization;
					}
				}
				line[0] = curDLMTestStatus.getSubjectName();
				line[1] = ((state != null) ? state.getOrganizationName() : "");
				line[2] = ((district != null) ? district.getOrganizationName() : "");
				line[3] = ((school != null) ? school.getDisplayIdentifier() : "");
				line[4] = ((school != null) ? school.getOrganizationName() : "");
				line[5] = curDLMTestStatus.getEducatorId();
				line[6] = getNameWithQuotes(curDLMTestStatus.getEducatorLastName());
				line[7] = getNameWithQuotes(curDLMTestStatus.getEducatorFirstName());
				line[8] = curDLMTestStatus.getGrade();
				line[9] = getNameWithQuotes(curDLMTestStatus.getStudentLaststName());
				line[10] = getNameWithQuotes(curDLMTestStatus.getStudentFirstName());
				line[11] = curDLMTestStatus.getStateStudentId();
				line[12] = curDLMTestStatus.getLocalStudentId();
				
				int colCount = 13;
				// as of October 2017, DLM does not use ITI for Social Studies, so these columns will be N/A
				for(int temp =0 ; temp< totalTestingCycle ; temp++) {
					if(temp == curTestingCycle  && isSocialStudies == false && !(isSpringCycle && isScience)) {
						line[colCount++] = (curDLMTestStatus.getInstTestsNotStarted() + "");
						line[colCount++] = (curDLMTestStatus.getInstTestsInProgress() + "");
						line[colCount++] = (curDLMTestStatus.getInstTestscompleted() + "");
						line[colCount++] = getInstTestsRequired(curDLMTestStatus.getStateModel())+ "";
					}else {
						//initilize with N/A
						line[colCount++] = "N/A";
						line[colCount++] = "N/A";
						line[colCount++] = "N/A";
						line[colCount++] = "N/A";
					}
				}
				if(isScience) {
					line[colCount++] = curDLMTestStatus.getEoyTestsNotStarted() + "";
					line[colCount++] = curDLMTestStatus.getEoyTestsInProgress() + "";
					line[colCount++] = curDLMTestStatus.getEoytTestscompleted() + "";
					line[colCount++] = curDLMTestStatus.getNumofEEs() + "";
				}else {
					line[colCount++] = "N/A";
					line[colCount++] = "N/A";
					line[colCount++] = "N/A";
					line[colCount++] = "N/A";
				}
				line[colCount++] = curDLMTestStatus.getFieldTestsCompleted() + "";
			}
			dlmTestStatusExtractMap.put(key.toString(), line);
		}
		
		return dlmTestStatusExtractMap;
	}
	
	private void getOrganizationTestStatusesforIEStates(Long orgId , Date eoyToDate,
			Date eoyFromDate, User user, boolean shouldOnlySeeRosteredStudents, int currentSchoolYear,
			List<String[]> lines , List<TestingCycle> testCycleWindows, HashMap <Long, OperationalTestWindow> testCycleOTWMap, int columnHeaderLength) {
		//Deb ITI redesign 
		Date iniToDate =null ; 
		Date iniFromDate =null;

		LinkedHashMap <String,String[]> dlmTestStatusExtractMap = new LinkedHashMap<>();
		int curTestingCycleCount=0;
		for(TestingCycle curTestingCycle : testCycleWindows) {
			//Fetching records for all window one by one
			OperationalTestWindow otwFromTestCycle= testCycleOTWMap.get(curTestingCycle.getId());
			if(otwFromTestCycle!= null) {
				iniFromDate = otwFromTestCycle.getEffectiveDate();
				iniToDate = otwFromTestCycle.getExpiryDate();
				LOGGER.info("DLM report query started at "+ new Date() + " for school : " + orgId + " for testing cycle "+ curTestingCycle.getTestingCycleName());
				List<DLMTestStatusExtractDTO> tempDLMTestStatusExtract = dataExtractService.dlmTestStatusReport(orgId, shouldOnlySeeRosteredStudents, 
						user.getId(), currentSchoolYear, iniToDate, iniFromDate, eoyToDate, eoyFromDate, user.getContractingOrgId());
				if(tempDLMTestStatusExtract!=null) {
					dlmTestStatusExtractMap = processDLMTestStatusExtract(dlmTestStatusExtractMap, tempDLMTestStatusExtract, curTestingCycle,columnHeaderLength, testCycleWindows.size(),curTestingCycleCount);
				}
				LOGGER.info("DLM report query completed at "+ new Date() + " for school : " + orgId + " for testing cycle "+ curTestingCycle.getTestingCycleName());
			}
			curTestingCycleCount++;
		}
		
		if(dlmTestStatusExtractMap != null) {
			for (Map.Entry<String, String[]> entry : dlmTestStatusExtractMap.entrySet()) {
			    String key = entry.getKey();
			    String[] extractLine = entry.getValue();
			    lines.add(extractLine);
			}
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startPNPSummaryExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException {
		
		String[] columnHeaders = {
			"State",
			"District",
			"School",
			"Student Counts",
			"Display - Magnification",
			"Display - Overlay Color",
			"Display - Invert Color Choice",
			"Display - Masking",
			"Display - Contrast Color Background White Foreground Green",
			"Display - Contrast Color Background White Foreground Red",
			"Display - Contrast Color Background Black Foreground Grey",
			"Display - Contrast Color Background Black Foreground Yellow",
			"Language - Item Translation Display",
			"Language - Braille EBAE",
			"Language - Braille UEB",
			"Auditory - Auditory Background",
			"Auditory - Spoken Audio",
			"Auditory - Spoken Audio Spoken Preference Setting - Text Only",
			"Auditory - Spoken Audio Spoken Preference Setting - Text and Graphics",
			"Auditory - Spoken Audio Spoken Preference Setting - Non-Visual",
			"Auditory - Spoken Audio Spoken Preference Setting - Graphics only",
			"Auditory - Spoken Subject(s) - Math and Science Only",
			"Auditory - Spoken Subject(s) - Math, Science and ELA",
			"Auditory - Single Switches",
			"Other Supports - Separate, quiet, or individual setting",
			"Other Supports - Presentation Student reads assessment aloud to self",
			"Other Supports - Presentation Student Used Translation dictionary",
			"Other Supports - Presentation Other Accommodation Used",
			"Other Supports - Response - Student dictated answers to scribe",
			"Other Supports - Response - Student used a communication device",
			"Other Supports - Response - Student signed responses",
			"Other Supports - Provided by Alternate Form - Visual Impairment",
			"Other Supports - Provided by Alternate Form - Large Print",
			"Other Supports - Provided by Alternate Form - Paper and Pencil",
			"Other Supports - Requiring Additional Tools Two Switch System",
			"Other Supports - Requiring Additional Tools Administration via iPad",
			"Other Supports - Requiring Additional Tools Adaptive equipment",
			"Other Supports - Requiring Additional Tools Individualized manipulatives",
			"Other Supports - Requiring Additional Tools Calculator", 
			"Other Supports - Provided outside system - Human read aloud",
			"Other Supports - Provided outside system - Sign Interpretation",
			"Other Supports - Provided outside system - Translation",
			"Other Supports - Provided outside system - Test admin enters responses for student",
			"Other Supports - Provided outside system - Partner assisted scanning",
			"Other Supports - Provided outside system -Student provided non-embedded accommodations as noted in IEP"
		};
		
		String supported = "assignedSupport";
		String[][] pnpColumnCodes = {
			{"Magnification", supported, "true"},
			{"ColourOverlay", supported, "true"},
			{"InvertColourChoice", supported, "true"},
			{"Masking", supported, "true"},
			{"BackgroundColour", "colour", "#ffffff", "ForegroundColour", "colour", "#3b9e24"},
			{"BackgroundColour", "colour", "#ffffff", "ForegroundColour", "colour", "#c62424"},
			{"BackgroundColour", "colour", "#000000", "ForegroundColour", "colour", "#999999"},
			{"BackgroundColour", "colour", "#000000", "ForegroundColour", "colour", "#FEFE22"},
			{"itemTranslationDisplay", supported, "true"},
			{"Braille", "ebaeFileType", "true"},
			{"Braille", "uebFileType", "true"},
			{"AuditoryBackground", supported, "true"},
			{"Spoken", supported, "true"},
			{"Spoken", "UserSpokenPreference", "textonly"},
			{"Spoken", "UserSpokenPreference", "textandgraphics"},
			{"Spoken", "UserSpokenPreference", "nonvisual"},
			{"Spoken", "UserSpokenPreference", "graphicsonly"},
			{"Spoken", "preferenceSubject", "math_and_science"},
			{"Spoken", "preferenceSubject", "math_science_and_ELA"},
			{"onscreenKeyboard", supported, "true"},
			// the "setting" container values are all lumped into the "separateQuiteSetting" record
			{"setting", "separateQuiteSetting", "setting"},
			// the "presentation" container values are all lumped into the "someotheraccommodation" record
			{"presentation", "someotheraccommodation", "assessment"},
			{"presentation", "someotheraccommodation", "translations"},
			{"presentation", "someotheraccommodation", "accommodation"},
			// the "response" container values are all lumped into the "dictated" record
			{"response", "dictated", "dictated"},
			{"response", "dictated", "communication"},
			{"response", "dictated", "responses"},
			{"supportsProvidedByAlternateForm" , "visualImpairment", "true"},
			{"supportsProvidedByAlternateForm" , "largePrintBooklet", "true"},
			{"supportsProvidedByAlternateForm" , "paperAndPencil", "true"},
			{"supportsRequiringAdditionalTools", "supportsTwoSwitch", "true"},
			{"supportsRequiringAdditionalTools", "supportsAdminIpad", "true"},
			{"supportsRequiringAdditionalTools", "supportsAdaptiveEquip","true"},
			{"supportsRequiringAdditionalTools", "supportsIndividualizedManipulatives","true"},
			{"supportsRequiringAdditionalTools", "supportsCalculator", "true"},
			{"supportsProvidedOutsideSystem", "supportsHumanReadAloud", "true"},
			{"supportsProvidedOutsideSystem", "supportsSignInterpretation", "true"},
			{"supportsProvidedOutsideSystem", "supportsLanguageTranslation", "true"},
			{"supportsProvidedOutsideSystem", "supportsTestAdminEnteredResponses", "true"},
			{"supportsProvidedOutsideSystem", "supportsPartnerAssistedScanning", "true"},
			{"supportsProvidedOutsideSystem", "supportsStudentProvidedAccommodations", "true"},
		};
		
		
		User user = userDetails.getUser();
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		String summaryLevelTypeCode = (String)additionalParams.get("summaryLevelTypeCode");
		Long summaryLevelOrgTypeId = Long.valueOf(additionalParams.get("summaryLevelOrgTypeId").toString());
		List<Integer> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();

		boolean dlmProgramSelected = false;
		boolean isNonDLMAssessmentProgram = false;
		boolean ismartProgramSelected = false;
		boolean ismart2ProgramSelected = false;
		List<Long> alternateAssessmentProgramIds = new ArrayList<Long>();
		
		for(Integer assessmentProgram:assessmentPrograms){
			AssessmentProgram ap = dataExtractService.findByAssessmentProgramId(assessmentProgram);
			if(!ap.getAbbreviatedname().equalsIgnoreCase("DLM") && !ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())
					&& !ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())){
				isNonDLMAssessmentProgram=true;
			}
			if(ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())){
				ismartProgramSelected = true;
				alternateAssessmentProgramIds.add(ap.getId());
			}
			if(ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())){
				ismart2ProgramSelected = true;
				alternateAssessmentProgramIds.add(ap.getId());
			}
			if("DLM".equalsIgnoreCase(ap.getAbbreviatedname())){
				dlmProgramSelected = true;
				alternateAssessmentProgramIds.add(ap.getId());
			}
		}
		
		Organization org = dataExtractService.getOrganization(orgId);
		List<Long> orgIds = new ArrayList<Long>();
		if (org.getOrganizationTypeId() == summaryLevelOrgTypeId) {
			orgIds = new ArrayList<Long>();
			orgIds.add(orgId);
		} else {
			orgIds = dataExtractService.getAllChildrenIdsByType(orgId, summaryLevelTypeCode);
		}
		
		List<String[]> lines = new ArrayList<String[]>();
		//lines.add(0,columnHeaders);
		for (Long id : orgIds) {
			// get state/district/school information
			Organization o = dataExtractService.getOrganization(id);
			List<Organization> parents = dataExtractService.getAllParents(id);
			String school = "", district = "", state = "";
			Long stateId = null;
			if (summaryLevelTypeCode.equals("SCH")) {
				school = o.getOrganizationName();
				for (Organization p : parents) {
					if (p.getOrganizationType().getTypeCode().equals("DT")) {
						district = p.getOrganizationName();
					}
					if (p.getOrganizationType().getTypeCode().equals("ST")) {
						state = p.getOrganizationName();
						stateId = p.getId();
					}
				}
			} else if (summaryLevelTypeCode.equals("DT")) {
				district = o.getOrganizationName();
				for (Organization p : parents) {
					if (p.getOrganizationType().getTypeCode().equals("ST")) {
						state = p.getOrganizationName();
						stateId = p.getId();
					}
				}
			} else if (summaryLevelTypeCode.equals("ST")) {
				state = o.getOrganizationName();
				stateId = o.getId();
			}
			
			String brailleFileType = StringUtils.EMPTY;
			
			//If DLM is available in userselected programs then counts for DLM will be available  in csv 
			if(dlmProgramSelected){
				String[] line = getPnpCountByProgram("DLM", userDetails, columnHeaders, pnpColumnCodes, user,
						shouldOnlySeeRosteredStudents, currentSchoolYear, id, school, district, state, stateId);
				
				lines.add(line);
			}
			
			//If I-SMART is available in userselected programs then counts for DLM will be available  in csv
			if(ismartProgramSelected){				
				
				String[] line = getPnpCountByProgram(ISMART_PROGRAM_ABBREVIATEDNAME, userDetails, columnHeaders, pnpColumnCodes, user,
						shouldOnlySeeRosteredStudents, currentSchoolYear, id, school, district, state, stateId);
				lines.add(line);
			}
			
			//If I-SMART2 is available in userselected programs then counts for DLM will be available  in csv
			if(ismart2ProgramSelected){
				
				String[] line = getPnpCountByProgram(ISMART_2_PROGRAM_ABBREVIATEDNAME, userDetails, columnHeaders, pnpColumnCodes, user,
						shouldOnlySeeRosteredStudents, currentSchoolYear, id, school, district, state, stateId);
				
				lines.add(line);
			}
			
			//If userselected programs contains other than DLM, I-SMART, I-SMART2 then counts for General Assessment will be available  in csv
			if(isNonDLMAssessmentProgram == true){
				String[] line = new String[columnHeaders.length];
				line[0] = state;
				line[1] = district;
				line[2] = school;
				line[3] = "General Assessment";			
				int offset = 0, foundCount = 0, limit = 10000;
				long[] pnpCounts = new long[columnHeaders.length];
				while (offset == 0 ||  foundCount == limit) {				
					List<Long> nonDLMStudentIds = dataExtractService.getNonDLMStudentIdsEnrolledInOrg(userDetails, id, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear,offset,limit, assessmentPrograms, alternateAssessmentProgramIds);				
					for (int x = 0; x < columnHeaders.length - 4; x++) {
						if (nonDLMStudentIds.size() > 0) {
							pnpCounts[x] += getCountsOfPNPSummaryColumn(nonDLMStudentIds, pnpColumnCodes[x]);
						}
					}
					foundCount = nonDLMStudentIds.size();
				    offset = offset + limit;					
				}
				brailleFileType = "BOTH"; // For all other assessment programs need to show all
				for (int x = 0; x < columnHeaders.length - 4; x++) {
					decorateValue(columnHeaders, brailleFileType, line, pnpCounts, x);
				}
				lines.add(line);
			}
		}
		Collections.sort(lines, pnpSummaryComparator);
		lines.add(0, columnHeaders);
		writeCSV(lines, moduleReport,typeName, true);
		Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
		return true;
	}

	
	/**
	 * This method gets pnp counts for specific assessment program
	 * @param assessmentProgramCode
	 * @param userDetails
	 * @param columnHeaders
	 * @param pnpColumnCodes
	 * @param user
	 * @param shouldOnlySeeRosteredStudents
	 * @param currentSchoolYear
	 * @param id
	 * @param school
	 * @param district
	 * @param state
	 * @param stateId
	 * @return array of lines to write it in a csv file
	 */
	private String[] getPnpCountByProgram(String assessmentProgramCode, UserDetailImpl userDetails, String[] columnHeaders, String[][] pnpColumnCodes,
			User user, boolean shouldOnlySeeRosteredStudents, int currentSchoolYear, Long id, String school,
			String district, String state, Long stateId) {
		String brailleFileType;
		brailleFileType = profileAttributeContainerService.getPnpStateSettingsByState(assessmentProgramCode, stateId);
		
		String[] line = new String[columnHeaders.length];
		line[0] = state;
		line[1] = district;
		line[2] = school;
		line[3] = assessmentProgramCode;
		
		int offset = 0, foundCount = 0, limit = 10000;
		long[] pnpCounts = new long[columnHeaders.length];
		while (offset == 0 ||  foundCount == limit) {				
			List<Long> studentIds = dataExtractService.getAllStudentIdsByOrgIdAssessmentProgram(userDetails, id, shouldOnlySeeRosteredStudents, user.getId(), currentSchoolYear, assessmentProgramCode, offset,limit);
			
			for (int x = 0; x < columnHeaders.length - 4; x++) {
				if (studentIds.size() > 0) {
					pnpCounts[x] += getCountsOfPNPSummaryColumn(studentIds, pnpColumnCodes[x]);
				}
			}									
			foundCount = studentIds.size();
		    offset = offset + limit;					
		}	
		for (int x = 0; x < columnHeaders.length - 4; x++) {
			decorateValue(columnHeaders, brailleFileType, line, pnpCounts, x);
		}
		return line;
	}

	private void decorateValue(String[] columnHeaders, String brailleFileType, String[] line, long[] pnpCounts, int x) {
		if ((columnHeaders[x + 4].equals("Language - Braille EBAE")
				|| columnHeaders[x + 4].equals("Language - Braille UEB")) && brailleFileType.equals("NONE")) {
			line[x + 4] = "N/A";
		} else if (columnHeaders[x + 4].equals("Language - Braille UEB") && brailleFileType.equals("EBAE")) {
			line[x + 4] = "N/A";
		} else if (columnHeaders[x + 4].equals("Language - Braille EBAE") && brailleFileType.equals("UEB")) {
			line[x + 4] = "N/A";
		} else {
			line[x + 4] = String.valueOf(pnpCounts[x]);
		}
	}
	
	/**
	 * Will narrow down to the list of students that satisfy every set of 3 strings in "values"
	 * @param studentIds
	 * @param values Array of the form [container, attribute, value, container, attribute, value, ...]
	 * @return The number of students that have every condition for their PNP outlined in "values"
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Long getCountsOfPNPSummaryColumn(List<Long> studentIds, String[] values) {
		int length = values.length;
		if (length % 3 != 0) {
			LOGGER.debug("getCountsOfSpecialPNPSummaryColumn -- values[] # of conditions was not divisible by 3, "+
					"requires [container, attribute, value, container, attribute, value, ...] -- received " + values);
			return 0l;
		}
		List<Long> narrowedStudentIds = studentIds;
		int numIterations = (length / 3) - 1; // -1 since we don't want to do the last one in the loop
		String condition = "EQUAL";
		for (int x = 0; x < numIterations; x++) {
			int y = 3 * x;
			String containerName = values[y];
			String attributeName = values[y + 1];
			String attributeValue = values[y + 2];
			if ("setting".equals(containerName) || "presentation".equals(containerName) || "response".equals(containerName)) {
				condition = "LIKE";
			} else {
				condition = "EQUAL";
			}
			narrowedStudentIds = studentProfileService.getStudentIdsWithAttributeValueForStudents(
					narrowedStudentIds, containerName, attributeName, attributeValue, condition);
			if (narrowedStudentIds.size() == 0) {
				return 0l;
			}
		}
		condition = "EQUAL";
		if ("setting".equals(values[length - 3]) ||
				"presentation".equals(values[length - 3]) ||
				"response".equals(values[length - 3])) {
			condition = "LIKE";
		}
		Long count = studentProfileService.countValuesInAttributeAndContainerForStudents(
				narrowedStudentIds, values[length - 3], values[length - 2], values[length - 1], condition);
		return count;
	}
	
	/**
	 * @author Navya Kooram
	 * US15696: Data extract - KSDE Test records for System Operations
	 */
	/**
	 * Changed the method to generate on extract for TEST and TASC records.
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKSDETestAndTascRecordsExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException {
		
		
		String[] columnHeaders = {
				"Create_Date", "Record_Common_Id",  "Sequence_Order",  "Record_Type",  "State_Student_Identifier",
				"AYP_QPA_Bldg_No", "Legal_Last_Name", "Legal_First_Name", "Legal_Middle_Name", "Generation_Code",
				"Gender", "Current_Grade_Level", "Hispanic_Ethnicity", "Birth_Date", "Current_School_Year", "Attendance_Bldg_No",
				"School_Entry_Date", "District_Entry_Date", "State_Entry_Date", "Comprehensive_Race", "Primary_Exceptionality_Code", 
				"Secondary_Exceptionality_Code", "ESOL_Participation_Code", "Grouping_Math_1", "Grouping_Math_2", "Grouping_Reading_1", 
				"Grouping_Reading_2", "Grouping_Science_1", "Grouping_Science_2", "Grouping_History_1", "Grouping_History_2", "Grouping_CTE_1", 
				"Grouping_CTE_2", "Grouping_Comprehensive_Ag", "Grouping_Animal_Systems", "Grouping_Plant_Systems", "Grouping_Manufacturing_Prod", 
				"Grouping_Design_PreConstruction", "Grouping_Finance", "Grouping_Comprehensive_Business", "Grouping_ELPA21_1", "Grouping_ELPA21_2", 
				"State_Math_Assess", "State_ELA_Assess", "State_Science_Assess", "State_Hist_Gov_Assess", "General_CTE_Assess", "Comprehensive_Ag_Assess", 
				"Animal_Systems_Assess", "Plant_Systems_Assess", "Manufacturing_Prod_Assess", "Design_PreConstruction_Assess", "Finance_Assess",
				"Comprehensive_Business_Assess", "ELPA21_Assess", "Av_Communications_Assess", "Grouping_Av_Communications", "Elpa_Proctor_Id",  "Elpa_Proctor_First_Name", "Elpa_Proctor_Last_Name",
				"State_History_Proctor_ID", "State_History_Proctor_First_Name", "State_History_Proctor_Last_Name",
				"Exit_Withdrawal_Type",	"Exit_Withdrawal_Date",	"Educator_Bldg_No", "State_Subj_Area_Code", "Local_Course_Id", 
				"Course_Status", "Teacher_Identifier", "Teacher_Last_Name", "Teacher_First_Name", "Teacher_Middle_Name", "Teacher_District_Email",
				"Notes", "Email_Sent", "Email_SentTo", "Status"
			};
		
		
		String studentStateID = (String)additionalParams.get("studentStateID"); //, user.getId()
		
		List<String[]> extractRecords = new ArrayList<String[]>();
		extractRecords.add(columnHeaders);
		
		String currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).toString();
		
		List<KSDERecord> ksdeTestAndTascRecords = reportGenericDao.getKidsAndTascRecordsForStudent(studentStateID, currentSchoolYear);
		
		for (KSDERecord testAndTascRecord : ksdeTestAndTascRecords) {
			extractRecords.add(new String[]{
					wrapForCSV(testAndTascRecord.getCreateDate()),
					wrapForCSV(testAndTascRecord.getRecordCommonId()),
					wrapForCSV(testAndTascRecord.getSeqNo() == null ? StringUtils.EMPTY : testAndTascRecord.getSeqNo().toString()),
					wrapForCSV(testAndTascRecord.getRecordType()),
					wrapForCSV(testAndTascRecord.getStateStudentIdentifier()),
					wrapForCSV(testAndTascRecord.getAypSchoolIdentifier()),
					wrapForCSV(testAndTascRecord.getLegalLastName()),
					wrapForCSV(testAndTascRecord.getLegalFirstName()),
					wrapForCSV(testAndTascRecord.getLegalMiddleName()),
					wrapForCSV(testAndTascRecord.getGenerationCode()),
					wrapForCSV(testAndTascRecord.getGender()),
					wrapForCSV(testAndTascRecord.getCurrentGradeLevel()),
					wrapForCSV(testAndTascRecord.getHispanicEthnicity()),
					wrapForCSV(testAndTascRecord.getDateOfBirth()),
					wrapForCSV(testAndTascRecord.getCurrentSchoolYear()),
					wrapForCSV(testAndTascRecord.getAttendanceSchoolProgramIdentifier()),
					wrapForCSV(testAndTascRecord.getSchoolEntryDate()),
					wrapForCSV(testAndTascRecord.getDistrictEntryDate()),
					wrapForCSV(testAndTascRecord.getStateEntryDate()),
					wrapForCSV(testAndTascRecord.getComprehensiveRace()),
					wrapForCSV(testAndTascRecord.getPrimaryDisabilityCode()),
					wrapForCSV(testAndTascRecord.getGiftedCode()),
					wrapForCSV(testAndTascRecord.getEsolParticipationCode()),
					wrapForCSV(testAndTascRecord.getGroupingInd1Math()),
					wrapForCSV(testAndTascRecord.getGroupingInd2Math()),
					wrapForCSV(testAndTascRecord.getGroupingInd1ELA()),
					wrapForCSV(testAndTascRecord.getGroupingInd2ELA()),
					wrapForCSV(testAndTascRecord.getGroupingInd1Sci()),
					wrapForCSV(testAndTascRecord.getGroupingInd2Sci()),
					wrapForCSV(testAndTascRecord.getGroupingInd1HistGov()),
					wrapForCSV(testAndTascRecord.getGroupingInd2HistGov()),
					wrapForCSV(testAndTascRecord.getGroupingInd1CTE()),
					wrapForCSV(testAndTascRecord.getGroupingInd2CTE()),
					wrapForCSV(testAndTascRecord.getGroupingComprehensiveAg()),
					wrapForCSV(testAndTascRecord.getGroupingAnimalSystems()),
					wrapForCSV(testAndTascRecord.getGroupingPlantSystems()),
					wrapForCSV(testAndTascRecord.getGroupingManufacturingProd()),
					wrapForCSV(testAndTascRecord.getGroupingDesignPreConstruction()),
					wrapForCSV(testAndTascRecord.getGroupingFinance()),
					wrapForCSV(testAndTascRecord.getGroupingComprehensiveBusiness()),
					wrapForCSV(testAndTascRecord.getGroupingInd1Elpa21()),
					wrapForCSV(testAndTascRecord.getGroupingInd2Elpa21()),
					wrapForCSV(testAndTascRecord.getStateMathAssess()),
					wrapForCSV(testAndTascRecord.getStateELAAssessment()),
					wrapForCSV(testAndTascRecord.getStateSciAssessment()),
					wrapForCSV(testAndTascRecord.getStateHistGovAssessment()),
					wrapForCSV(testAndTascRecord.getGeneralCTEAssessment()),
					wrapForCSV(testAndTascRecord.getComprehensiveAgAssessment()),
					wrapForCSV(testAndTascRecord.getAnimalSystemsAssessment()),
					wrapForCSV(testAndTascRecord.getPlantSystemsAssessment()),
					wrapForCSV(testAndTascRecord.getManufacturingProdAssessment()),
					wrapForCSV(testAndTascRecord.getDesignPreConstructionAssessment()),
					wrapForCSV(testAndTascRecord.getFinanceAssessment()),
					wrapForCSV(testAndTascRecord.getComprehensiveBusinessAssessment()),
					wrapForCSV(testAndTascRecord.getElpa21Assessment()),
					wrapForCSV(testAndTascRecord.getAvCommunicationsAssessment()),
					wrapForCSV(testAndTascRecord.getGroupingAvCommunications()),
					wrapForCSV(testAndTascRecord.getElpaProctorId()),
					wrapForCSV(testAndTascRecord.getElpaProctorFirstName()),
					wrapForCSV(testAndTascRecord.getElpaProctorLastName()),					
					wrapForCSV(testAndTascRecord.getHistoryGovProctorId()),
					wrapForCSV(testAndTascRecord.getHistoryGovProctorFirstName()),
					wrapForCSV(testAndTascRecord.getHistoryGovProctorLastName()),					
					wrapForCSV(testAndTascRecord.getExitWithdrawalType()),
					wrapForCSV(testAndTascRecord.getExitWithdrawalDate()),
					wrapForCSV(testAndTascRecord.getEducatorSchoolId()),
					wrapForCSV(testAndTascRecord.getTascStateSubjectAreaCode()),
					wrapForCSV(testAndTascRecord.getTascLocalCourseId()),
					wrapForCSV(testAndTascRecord.getCourseStatus()),
					wrapForCSV(testAndTascRecord.getTascEducatorIdentifier()),
					wrapForCSV(testAndTascRecord.getTeacherLastName()),
					wrapForCSV(testAndTascRecord.getTeacherFirstName()),
					wrapForCSV(testAndTascRecord.getTeacherMiddleName()),
					wrapForCSV(testAndTascRecord.getEducatorEmailId()),
					wrapForCSV(testAndTascRecord.getReason()),
					wrapForCSV(testAndTascRecord.getEmailSent() == null ? StringUtils.EMPTY : testAndTascRecord.getEmailSent().toString()),
					wrapForCSV(testAndTascRecord.getEmailSentTo()),
					wrapForCSV(testAndTascRecord.getStatus())
				
			});
		}
		writeReport(moduleReportId, extractRecords,typeName);
		
		return true;
	}
	
	/**
	 * @author Navya Kooram
	 * US15696: Data extract - KSDE Test records for System Operations
	 */
	/**
	 * Both TEST and TASC records are now coming in same xml.
	 */
	public boolean validateKSDETestAndTascStudentStateID(UserDetailImpl userDetails, String studentStateID)
	{
		User user = userDetails.getUser();
		String currentSchoolYear = Long.toString(user.getContractingOrganization().getCurrentSchoolYear());
		
		int count = reportGenericDao.countKSDETestAndTascStudentStateID(studentStateID, currentSchoolYear);
		if(count>0)
			return true;
		return false;
	}

	@Override
	public boolean checkKAPExtract(Long organizationId){
		int countKAPForOrg = moduleReportDao.checkKAPExtract(organizationId);
		if(countKAPForOrg > 0)
			return true;
		else
			return false;
	}
	
	private String getInstTestsRequired(String stateModel) {
		if(StringUtils.isNotBlank(stateModel) && stateModel.equalsIgnoreCase("SINGLEEE")) {
			return "*";
		}
		return "N/A";
	}
	
	private String getNameWithQuotes(String name) {
		if(StringUtils.isNotBlank(name) && name.contains(",")) {
			return "\"" + name + "\"";			
		}
		return name;
	}
	
	/**
	 * @author Venkata Krishna Jagarlamudi
	 * US15630: Data extract - Test administration for KAP and AMP
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startTestAdminExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId, Map<String, Object> additionalParams,String typeName) 
			throws IOException {
		
		List<String[]> lines = new ArrayList<String[]>();
		
		User user = userDetails.getUser();
		String assesmentProgram = (String) additionalParams.get("assessmentProgramCode");
		
		Long currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).longValue();
		Locale clientLocale = new Locale(additionalParams.get("clientLocale").toString());
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		String[] columnHeaders = {				
				"State", "District", "School", "School Identifier","School Entry Date", "Grade", "Grouping 1", "Grouping 2", "Roster Name",				
				"Educator Identifier", "Educator Last Name", "Educator First Name",	"Student Last Name", "Student First Name",
				"Student Middle Name", "State Student Identifier", "Local Student Identifier","Subject","Test Session Name", 
				"Test Collection Name", "Test Status", getAbsenseReasonHeader(additionalParams),"Special Circumstance Status","Last Reactivated Date Time", "Stage",
				"Start DateTime", "End DateTime", "Ticket Sections", "Total Items", "Unanswered Items"
		};
		lines.add(columnHeaders);
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		String contractingOrgName = (String) additionalParams.get("contractingOrgName");
		
		List<Organization> schools= dataExtractService.getAllChildrenWithParentByType(orgId, "SCH");
		
		if(moduleReport!=null && schools != null && !schools.isEmpty()) {
			
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);
			
			for(int x = 0; x < schools.size(); x++){
				Organization school = schools.get(x);
				int offset = 0, foundCount = 0, limit = 10000;
				while (offset == 0 ||  foundCount == limit) {			
					List<StudentTestSessionInfoDTO> testAdminDataExtractDTOs = testSessionService.getTestAdminExtract(school.getId(), currentSchoolYear, 
							shouldOnlySeeRosteredStudents, user.getId(), assesmentProgram, offset, limit,assessmentPrograms,false);					
					foundCount = testAdminDataExtractDTOs.size();
					if(CollectionUtils.isNotEmpty(testAdminDataExtractDTOs)) {					
						fillTestAdminDetails(user, lines, clientLocale, testAdminDataExtractDTOs, contractingOrgName, dateFormat, false);		
					}
					
					writeCSV(lines, moduleReport,typeName, x >= (schools.size() - 1));
					offset = offset + limit;
					lines.clear();
				}	
			}
			
			Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			updateReportStatus(moduleReport, completedStatusid);
		}
		
		return true;
	}

	private String getAbsenseReasonHeader(Map<String, Object> additionalParams) {
		return "Special Circumstance";
	}

	/**
	 * @param lines
	 * @param clientLocale
	 * @param testAdminDataExtractDTOs
	 */
	private void fillTestAdminDetails(User user, List<String[]> lines,
			Locale clientLocale,
			List<StudentTestSessionInfoDTO> testAdminDataExtractDTOs, String contractingOrgName, SimpleDateFormat dateFormat, boolean isPltw) {
		
		for(StudentTestSessionInfoDTO testAdminInfo : testAdminDataExtractDTOs) {
			List<String> testAdminDetails = new ArrayList<String>();					
			
			Organization school = organizationDao.get(testAdminInfo.getAttendanceSchoolId());
			Organization district = organizationDao.getDistrictBySchoolOrgId(testAdminInfo.getAttendanceSchoolId());				
			
			testAdminDetails.add(contractingOrgName);
			testAdminDetails.add((district != null) ? district.getOrganizationName() : "");			
			testAdminDetails.add((school != null) ? school.getOrganizationName() : "");
			testAdminDetails.add((school != null) ? school.getDisplayIdentifier() : "");
			
			testAdminDetails.add(testAdminInfo.getSchoolEntryDate() != null ? dateFormat.format(testAdminInfo.getSchoolEntryDate()) : StringUtils.EMPTY);		
			testAdminDetails.add(testAdminInfo.getGrdae());
			if(!isPltw) {
				testAdminDetails.add(testAdminInfo.getGrouping1());
				testAdminDetails.add(testAdminInfo.getGrouping2());
			}
			testAdminDetails.add(testAdminInfo.getRosterName());			
			testAdminDetails.add(testAdminInfo.getEducatorIdentifier());
			testAdminDetails.add(getNameWithQuotes(testAdminInfo.getEducatorLastName()));
			testAdminDetails.add(getNameWithQuotes(testAdminInfo.getEducatorFirstName()));
			testAdminDetails.add(getNameWithQuotes(testAdminInfo.getStudentLastName()));
			testAdminDetails.add(getNameWithQuotes(testAdminInfo.getStudentFirstName()));
			testAdminDetails.add(testAdminInfo.getStudentMiddleName());
			testAdminDetails.add(testAdminInfo.getStateStudentIdentifier());
			if(!isPltw) {
				testAdminDetails.add(testAdminInfo.getLocalStudentIdentifier());
			}
			testAdminDetails.add(getSubjectBasedOnStatge(testAdminInfo));
			testAdminDetails.add(testAdminInfo.getTestSessionName());
			if(!isPltw) {
				testAdminDetails.add(testAdminInfo.getTestCollectionName());
			}
			testAdminDetails.add(testAdminInfo.getTestSatus());
			if(!isPltw) {
				testAdminDetails.add(testAdminInfo.getSpecialCircumstances());
				//Per US17557
				testAdminDetails.add(testAdminInfo.getSpecialCircumstanceStatus());
			}
			
			testAdminDetails.add(testAdminInfo.getLastReactivatedDate() != null ? dateFormat.format(testAdminInfo.getLastReactivatedDate()) : StringUtils.EMPTY);
			testAdminDetails.add(testAdminInfo.getStage());
			for(TestAdminPartDetails partDetails : testAdminInfo.getPartsDetails()) {
				//testAdminDetails.add(partDetails.getStatus());
				testAdminDetails.add(partDetails.getStartDate() != null ? dateFormat.format(partDetails.getStartDate()) : StringUtils.EMPTY);
				testAdminDetails.add(partDetails.getEndDate() != null ? dateFormat.format(partDetails.getEndDate()) : StringUtils.EMPTY);
				if(!isPltw) {
					testAdminDetails.add(partDetails.getTicketSections());
				}
				testAdminDetails.add(getPartItems(partDetails.getTotalItems()));
				testAdminDetails.add(getPartItems(partDetails.getOmmitedItems()));
			}
			lines.add(testAdminDetails.toArray(new String[testAdminDetails.size()]));
		}
	}

	/**
	 * @param testAdminInfo
	 * @return
	 */
	private String getSubjectBasedOnStatge(
			StudentTestSessionInfoDTO testAdminInfo) {
		if("English Language Arts".equalsIgnoreCase(testAdminInfo.getSubject()) 
				&& "Multidisciplinary".equalsIgnoreCase(testAdminInfo.getStage())){
			return "Multi-disciplinary";
		}
		return testAdminInfo.getSubject();
	}

	/**
	 * @param partDetails
	 * @return
	 */
	private String getPartItems(int numOfitems) {
		if(numOfitems == 0) {
			return StringUtils.EMPTY;
		}
		return numOfitems + StringUtils.EMPTY;
	}
	
	/**
	 * @author Venkata Krishna Jagarlamudi
	 * US15741: Data Extract - Test Tickets for KAP and AMP
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startTestTicketsExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException {
		
		List<Integer> assessmentProgramsInt = (List)additionalParams.get("assessmentProgramIds");
		List<Long> assessmentPrograms = new ArrayList<Long>();
		for(Integer apId : assessmentProgramsInt) {
			assessmentPrograms.add(apId.longValue());
		}
		AssessmentProgram assessmentProgramCode = null;
		boolean isPLTW=false;
		for(int i = 0; i< assessmentPrograms.size(); i++) {
			assessmentProgramCode = assessmentProgramService.findByAssessmentProgramId(assessmentPrograms.get(i).longValue());	
			if(assessmentProgramCode.getAbbreviatedname().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW = true;
				break;
			}
		}
		User user = userDetails.getUser();
		String assesmentProgram = (String) additionalParams.get("assessmentProgramCode");
		Long currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).longValue();
		
		List<String> columnHeadersList = new ArrayList<String>();
		columnHeadersList.add("State");
		columnHeadersList.add("District");
		columnHeadersList.add("School");
		columnHeadersList.add("School Identifier");
		if(!isPLTW) { columnHeadersList.add("Grade"); }
		columnHeadersList.add("Grouping 1");
		columnHeadersList.add("Grouping 2");
		columnHeadersList.add("Roster Name");
		columnHeadersList.add("Educator Identifier");
		columnHeadersList.add("Educator Last Name");
		columnHeadersList.add("Educator First Name");
		columnHeadersList.add("Student Last Name");
		columnHeadersList.add("Student First Name");
		columnHeadersList.add("Student Middle Name");
		columnHeadersList.add("State Student Identifier");
		columnHeadersList.add("Local Student Identifier");
		columnHeadersList.add("Student Login Username");
		columnHeadersList.add("Student Login password");
		if(isPLTW) { columnHeadersList.add("Course"); } else {columnHeadersList.add("Course"); }
		columnHeadersList.add("Test Session Name");
		columnHeadersList.add("Test Collection Name");
		columnHeadersList.add("Test Status");
		columnHeadersList.add("Part 1 Status");
		columnHeadersList.add("Part 1 Ticket Sections");
		columnHeadersList.add("Part 1 Student Login Ticket");
		columnHeadersList.add("Part 2 Status");
		columnHeadersList.add("Part 2 Ticket Sections");
		columnHeadersList.add("Part 2 Student Login Ticket");
		columnHeadersList.add("Part 3 Status");
		columnHeadersList.add("Part 3 Ticket Sections");
		columnHeadersList.add("Part 3 Student Login Ticket");
		columnHeadersList.add("Part 4 Status");
		columnHeadersList.add("Part 4 Ticket Sections");
		columnHeadersList.add("Part 4 Student Login Ticket");

		
		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]);
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		String contractingOrgName = (String) additionalParams.get("contractingOrgName");

		List<Organization> schools= dataExtractService.getAllChildrenWithParentByType(orgId, "SCH");
		
		if(moduleReport!=null && schools != null && !schools.isEmpty()) {	
			for(int x = 0; x < schools.size(); x++){
				Organization school = schools.get(x);
				int offset = 0, foundCount = 0, limit = 10000;
				while (offset == 0 || foundCount == limit) {
					List<StudentTestSessionInfoDTO> testTicketDataExtractDTOs = testSessionService.getTicketDetailsExtract(school.getId(), currentSchoolYear, 
							false, user.getId(), assesmentProgram, offset, limit, assessmentPrograms);
					
					foundCount = testTicketDataExtractDTOs.size();
					if(CollectionUtils.isNotEmpty(testTicketDataExtractDTOs)) {					
						getTestTicketsInfo(user, lines, testTicketDataExtractDTOs, contractingOrgName, assessmentPrograms);		
					}
					
					writeCSV(lines, moduleReport,typeName, x >= (schools.size() - 1));
					offset = offset + limit;
					lines.clear();
				}	
			}
			
			Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			updateReportStatus(moduleReport, completedStatusid);			
		}
		
		return true;
	}

	/**
	 * @param lines
	 * @param testTicketDataExtractDTOs
	 */
	private void getTestTicketsInfo(User user, List<String[]> lines,
			List<StudentTestSessionInfoDTO> testTicketDataExtractDTOs, String contractingOrgName, List<Long> assessmentPrograms) {
		AssessmentProgram assessmentProgramCode = null;
		boolean isPLTW=false;
		for(int i = 0; i< assessmentPrograms.size(); i++) {
			assessmentProgramCode = assessmentProgramService.findByAssessmentProgramId(assessmentPrograms.get(i));	
			if(assessmentProgramCode.getAbbreviatedname().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW = true;
				break;
			}
		}
		
		for(StudentTestSessionInfoDTO testTicketInfo : testTicketDataExtractDTOs) {
			List<String> testTicketsInfo = new ArrayList<String>();
			
			Organization school = organizationDao.get(testTicketInfo.getAttendanceSchoolId());
			Organization district = organizationDao.getDistrictBySchoolOrgId(testTicketInfo.getAttendanceSchoolId());				
			
			testTicketsInfo.add(contractingOrgName);
			testTicketsInfo.add((district != null) ? district.getOrganizationName() : "");
			testTicketsInfo.add((school != null) ? school.getOrganizationName() : "");
			testTicketsInfo.add((school != null) ? school.getDisplayIdentifier() : "");
			
			if(!isPLTW) { testTicketsInfo.add(testTicketInfo.getGrdae()); }
			testTicketsInfo.add(testTicketInfo.getGrouping1());
			testTicketsInfo.add(testTicketInfo.getGrouping2());
			testTicketsInfo.add(testTicketInfo.getRosterName());
			testTicketsInfo.add(testTicketInfo.getEducatorIdentifier());
			testTicketsInfo.add(getNameWithQuotes(testTicketInfo.getEducatorLastName()));
			testTicketsInfo.add(getNameWithQuotes(testTicketInfo.getEducatorFirstName()));
			testTicketsInfo.add(getNameWithQuotes(testTicketInfo.getStudentLastName()));
			testTicketsInfo.add(getNameWithQuotes(testTicketInfo.getStudentFirstName()));
			testTicketsInfo.add(testTicketInfo.getStudentMiddleName());
			testTicketsInfo.add(testTicketInfo.getStateStudentIdentifier());
			testTicketsInfo.add(testTicketInfo.getLocalStudentIdentifier());
			testTicketsInfo.add(testTicketInfo.getStudentLoginUserName());
			testTicketsInfo.add(testTicketInfo.getStudentLoginPassword());
			testTicketsInfo.add(getSubjectBasedOnStatge(testTicketInfo));
			testTicketsInfo.add(testTicketInfo.getTestSessionName());
			testTicketsInfo.add(testTicketInfo.getTestCollectionName());
			testTicketsInfo.add(testTicketInfo.getTestSatus());
			for(TestAdminPartDetails partDetails : testTicketInfo.getPartsDetails()) {
				testTicketsInfo.add(partDetails.getStatus());
				testTicketsInfo.add(partDetails.getTicketSections());
				testTicketsInfo.add(partDetails.getStudentLoginTicket());				
			}
			lines.add(testTicketsInfo.toArray(new String[testTicketsInfo.size()]));
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateModuleReportStatusToFailed(Long moduleReportId) {	
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		Long failedStatusid = categoryDao.getCategoryId("FAILED", "PD_REPORT_STATUS");
		moduleReport.setStatusId(failedStatusid);
		moduleReport.setModifiedDate(new Date());
		moduleReportDao.updateByPrimaryKeySelective(moduleReport);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateModuleReportStatus(ModuleReport moduleReport, Long statusId) {	
		moduleReport.setStatusId(statusId);
		moduleReport.setModifiedDate(new Date());
		return moduleReportDao.updateByPrimaryKeySelective(moduleReport);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ModuleReport getMostRecentReportByTypeId(User user,
			short typeId) {
		Long organizationId = (long) 0;
		if(user!=null) organizationId = user.getContractingOrgId()== 0 ? user.getCurrentOrganizationId():user.getContractingOrgId();
		return moduleReportDao.getMostRecentReportByTypeId((user != null ? user.getId() : null), typeId, organizationId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ModuleReport getMostRecentGRFExtractReportByTypeId(User user, short typeId) {
		Long organizationId = (long) 0;
		if(user!=null) organizationId = user.getContractingOrgId()== 0 ? user.getCurrentOrganizationId():user.getContractingOrgId();
		return moduleReportDao.getMostRecentGRFExtractReportByTypeId(typeId, organizationId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ModuleReport getMostRecentCompletedReportByTypeId(User user,
			short typeId) {		
		return moduleReportDao.getMostRecentCompletedReportByTypeId((user != null ? user.getId() : null), typeId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean generateKSDEReturnFile(UserDetailImpl userDetails,
			Long moduleReportId, List<String> subjects,
			Map<String, Object> additionalParams) throws IOException {

		List<String[]> lines = new ArrayList<String[]>();

		User user = userDetails.getUser();

		int reportYear = user.getContractingOrganization().getReportYear();
		Long currentSchoolYear = new Integer(reportYear).longValue();
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		
		String filename = createFilename(moduleReport);
		File returnFile = createFile(filename);
		CSVWriter csvWriter = new CSVWriter(new FileWriter(returnFile, true), '\t', CSVWriter.NO_QUOTE_CHARACTER);
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YYYY, user.getContractingOrgId());
		
		if (subjects.contains("SELAA") || subjects.contains("D74") || subjects.contains("SSCIA")) {
			List<Long> schoolIds = organizationDao.getAllSchoolsByOrganization(user.getContractingOrgId());
			String[] ela_math_columnHeaders = {
					"State Student Identifier",
					"Accountability School Identifier",
					"Stage 1 - Test Instance ID", "Stage 2 - Test Instance ID",
					"Stage 3 - Test Instance ID",
					"Performance - Test Instance ID", "KSDE File Code",
					"Test Grade", "Test Type Code", "Stage 1 Question Count",
					"Stage 2 Question Count", "Stage 3 Question Count",
					"Performance Stage Question Count",
					"Stage 1 Responded Count", "Stage 2 Responded Count",
					"Stage 3 Responded Count",
					"Performance Stage Responded Count", "Scale Score",
					"Performance Level", "ELA combined performance level",
					"ELA MDPT score", "ELA MDPT scorable flag",
					"KSDE SC Code - Stage 1", "KSDE SC Code - Stage 2",
					"KSDE SC Code - Stage 3", "KSDE SC Code - Performance",
					"Stage 1 - Complete", "Stage 2 - Complete",
					"Stage 3 - Complete", "Performance - Complete",
					"Computer Test", "Stage 1 Test Begin Date",
					"Stage 1 Test End Date", "Stage 2 Test Begin Date",
					"Stage 2 Test End Date", "Stage 3 Test Begin Date",
					"Stage 3 Test End Date",
					"Peformance Stage Test Begin Time",
					"Performance Test End Time", "School Year", "Student Grade"
			};
			lines.add(ela_math_columnHeaders);

			if (moduleReport != null && schoolIds != null) {
				for (int i = 0; i < schoolIds.size(); i++) {
					if (i % 10 == 0) {
						LOGGER.info("KSDE ELA and Math ReturnFile Processing School Counter:" + i);
					}
					List<KSDEStudentTestDetailsDTO> ksdeReturnFileDtos = ksdeReturnFileService.getReturnFileDetailsExtract(
							subjects, currentSchoolYear, schoolIds.get(i));
					
					if (CollectionUtils.isNotEmpty(ksdeReturnFileDtos)) {
						fillKsdeDetails(ksdeReturnFileDtos, lines, ela_math_columnHeaders.length, dateFormat);
					}
		        	for (String[] line : lines) {
		            	csvWriter.writeNext(line);
		            	csvWriter.flush();    			
		    		}
					lines.clear();
				}
				
				LOGGER.info("KSDE ELA and Math ReturnFile Processed all School records");
			} else {
				String[] ss_columnHeaders = {
						"State Student Identifier",
						"Accountability School Identifier",
						"Stage 1 - Test Instance ID",
						"Performance - Test Instance ID", "KSDE File Code",
						"Test Grade", "Test Type Code",
						"Stage 1 Question Count",
						"Performance Stage Question Count",
						"Stage 1 Responded Count",
						"Performance Stage Responded Count", "Scale Score",
						"Performance Level", "KSDE SC Code - Stage 1",
						"KSDE SC Code - Performance", "Stage 1 - Complete",
						"Performance - Complete", "Computer Test",
						"Stage 1 Test Begin Date", "Stage 1 Test End Date",
						"Peformance Stage Test Begin Time",
						"Performance Test End Time", "School Year",
						"Student Grade"
				};
				lines.add(ss_columnHeaders);

				if (moduleReport != null && schoolIds != null) {
					for (int i = 0; i < schoolIds.size(); i++) {
						if (i % 10 == 0) {
							LOGGER.info("KSDE Social studies ReturnFile Processing School Counter:" + i);
						}
						List<KSDEStudentTestDetailsDTO> ksdeReturnFileDtos = ksdeReturnFileService.getReturnFileDetailsExtract(
								subjects, currentSchoolYear, schoolIds.get(i));
						
						if (CollectionUtils.isNotEmpty(ksdeReturnFileDtos)) {
							fillKsdeDetails(ksdeReturnFileDtos, lines, ss_columnHeaders.length, dateFormat);
						}
			        	for (String[] line : lines) {
			            	csvWriter.writeNext(line);
			            	csvWriter.flush();    			
			    		}
						lines.clear();
					}
					LOGGER.info("KSDE Social studies ReturnFile Processed all School records");
				}
			}
		} else if (subjects.contains("ELP")) {
			try {
				LOGGER.info("KELPA2 return file process");
				Long orgStateId = user.getContractingOrgId();
				generateKELPAStateReturnFile(moduleReport, additionalParams, orgStateId, reportYear);
				LOGGER.info("KELPA2 return file process finished");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			List<Long> schoolIds = organizationDao.getAllSchoolsByOrganization(user.getContractingOrgId());
			String[] ss_columnHeaders = {
					"State Student Identifier",
					"Accountability School Identifier",
					"Stage 1 - Test Instance ID",
					"Performance - Test Instance ID", "KSDE File Code",
					"Test Grade", "Test Type Code", "Stage 1 Question Count",
					"Performance Stage Question Count",
					"Stage 1 Responded Count",
					"Performance Stage Responded Count", "Scale Score",
					"Performance Level", "KSDE SC Code - Stage 1",
					"KSDE SC Code - Performance", "Stage 1 - Complete",
					"Performance - Complete", "Computer Test",
					"Stage 1 Test Begin Date", "Stage 1 Test End Date",
					"Peformance Stage Test Begin Time",
					"Performance Test End Time", "School Year", "Student Grade"
			};
			lines.add(ss_columnHeaders);
			if (moduleReport != null && schoolIds != null) {
				for (int i = 0; i < schoolIds.size(); i++) {
					if (i % 10 == 0) {
						LOGGER.info("KSDE Social studies ReturnFile Processing School Counter:" + i);
					}
					List<KSDEStudentTestDetailsDTO> ksdeReturnFileDtos = ksdeReturnFileService.getReturnFileDetailsExtract(
							subjects, currentSchoolYear, schoolIds.get(i));
					
					if (CollectionUtils.isNotEmpty(ksdeReturnFileDtos)) {
						fillKsdeDetails(ksdeReturnFileDtos, lines, ss_columnHeaders.length, dateFormat);
					}
		        	for (String[] line : lines) {
		            	csvWriter.writeNext(line);
		            	csvWriter.flush();    			
		    		}
					lines.clear();
				}
				LOGGER.info("KSDE Social studies ReturnFile Processed all School records");
			}
		}
    	if(csvWriter != null) {
    		csvWriter.close();
    	}
		sendReportToS3(filename, returnFile);
		return true;
	}
	
	private String createFilename(ModuleReport moduleReport) {
        String fileName = null;
    	String folderName = "dataextracts";
		String folderPath = REPORT_PATH + java.io.File.separator + folderName;
    	if(moduleReport.getFileName()!= null){
    		fileName = moduleReport.getFileName();
    	}else{
    		fileName = folderPath + java.io.File.separator + getFileName(moduleReport);
    		moduleReport.setFileName(fileName);
    		moduleReportDao.updateByPrimaryKeySelective(moduleReport);
    	}
    	return fileName;
	}
	
	private File createFile(String fileName) throws IOException {
        File tempFile = null;
        try {
        	//split the path/filename from the extension
    		String pathAndExtensionArray[] = fileName.split("\\.");
    		//create a local temp file	
    		tempFile = File.createTempFile(pathAndExtensionArray[0], "."+pathAndExtensionArray[1]);
	   	 	} catch (IOException ex) {
	   	 		LOGGER.error("IOException Occured:", ex);
	   	 		throw ex;
	        }
        return tempFile;
	}
	
	private void sendReportToS3(String key, File tempFile) throws IOException{
		//send the local temp file to s3
		s3.synchMultipartUpload(key, tempFile);
		//delete the local temp file
		FileUtils.deleteQuietly(tempFile);
	}
	
	private void generateKELPAStateReturnFile(ModuleReport moduleReport,Map<String, Object> additionalParams,Long orgStateId, int schoolYear) throws Throwable {
		
		String[] columnHeaders = {
			"District_Identifier", "School_Identifier",
			"State_Student_Identifier", "Grade", "ATTEMPT",
			"Special_Circumstance_Code_Listening", "Special_Circumstance_Code_Reading", "Special_Circumstance_Code_Speaking", "Special_Circumstance_Code_Writing",
			"Invalid_Listening", "Invalid_Reading", "Invalid_Speaking", "Invalid_Writing",
			"Status_Listening", "Status_Reading", "Status_Speaking", "Status_Writing",
			"Scale_Score_Overall", "Proficiency_Determination",
			"Scale_Score_Listening", "Level_Listening",
			"Scale_Score_Reading", "Level_Reading",
			"Scale_Score_Speaking", "Level_Speaking",
			"Scale_Score_Writing", "Level_Writing",
			"Scale_Score_Comprehension",
			"Question_Count_Listening", "Responded_Count_Listening",
			"Question_Count_Reading", "Responded_Count_Reading",
			"Question_Count_Speaking", "Responded_Count_Speaking",
			"Question_Count_Writing", "Responded_Count_Writing",
			"Human_Scored_Question_Count_Speaking", "Non_Scorable_Codes_Speaking",
			"Human_Scored_Question_Count_Writing", "Non_Scorable_Codes_Writing"
		};
		
		List<String[]> lines = new ArrayList<String[]>();
		
		List<Long> districtIds = organizationDao.getAllChildrenIdsByType(orgStateId, "DT");
		for (Long districtId : districtIds) {
			List<KELPAReport> records = studentDao.getKELPAStateStudents(districtId, schoolYear);
			
			for (KELPAReport record : records) {
				String attempted = "Y";
				if (record.getDomainStatusListening().equalsIgnoreCase("unused") &&
						record.getDomainStatusReading().equalsIgnoreCase("unused") &&
						record.getDomainStatusSpeaking().equalsIgnoreCase("unused") &&
						record.getDomainStatusWriting().equalsIgnoreCase("unused")) {
					attempted = "N";
				}
				lines.add(new String[]{
					wrapForCSV(record.getDistrictNumber()),
					wrapForCSV(record.getSchoolNumber()),
					wrapForCSV(record.getStateStudentID()),
					wrapForCSV(record.getGrade()),
					wrapForCSV(attempted),
					wrapForCSV((StringUtils.isNotBlank(record.getStudentNotTestedCodeListening()) ? "SC" : "") + record.getStudentNotTestedCodeListening()),
					wrapForCSV((StringUtils.isNotBlank(record.getStudentNotTestedCodeReading()) ? "SC" : "") + record.getStudentNotTestedCodeReading()),
					wrapForCSV((StringUtils.isNotBlank(record.getStudentNotTestedCodeSpeaking()) ? "SC" : "") + record.getStudentNotTestedCodeSpeaking()),
					wrapForCSV((StringUtils.isNotBlank(record.getStudentNotTestedCodeWriting()) ? "SC" : "") + record.getStudentNotTestedCodeWriting()),
					"", "", "", "", // "Invalid_*" fields, which are blank for 2017
					wrapForCSV(record.getDomainStatusListening().equalsIgnoreCase("unused") ? "N" : "C"),
					wrapForCSV(record.getDomainStatusReading().equalsIgnoreCase("unused") ? "N" : "C"),
					wrapForCSV(record.getDomainStatusSpeaking().equalsIgnoreCase("unused") ? "N" : "C"),
					wrapForCSV(record.getDomainStatusWriting().equalsIgnoreCase("unused") ? "N" : "C"),
					wrapForCSV(record.getOverallScaleScore()),
					wrapForCSV((record.getOverallLevel() == null ? "" : record.getOverallLevel().toString())),
					wrapForCSV(record.getListeningScaleScore()),
					wrapForCSV(record.getListeningPerformanceLevel()),
					wrapForCSV(record.getReadingScaleScore()),
					wrapForCSV(record.getReadingPerformanceLevel()),
					wrapForCSV(record.getSpeakingScaleScore()),
					wrapForCSV(record.getSpeakingPerformanceLevel()),
					wrapForCSV(record.getWritingScaleScore()),
					wrapForCSV(record.getWritingPerformanceLevel()),
					wrapForCSV(record.getComprehensionScaleScore()),
					wrapForCSV((record.getListeningQuestionCount() == null ? "" : record.getListeningQuestionCount().toString())),
					wrapForCSV((record.getListeningResponseCount() == null ? "" : record.getListeningResponseCount().toString())),
					wrapForCSV((record.getReadingQuestionCount() == null ? "" : record.getReadingQuestionCount().toString())),
					wrapForCSV((record.getReadingResponseCount() == null ? "" : record.getReadingResponseCount().toString())),
					wrapForCSV((record.getSpeakingQuestionCount() == null ? "" : record.getSpeakingQuestionCount().toString())),
					wrapForCSV((record.getSpeakingResponseCount() == null ? "" : record.getSpeakingResponseCount().toString())),
					wrapForCSV((record.getWritingQuestionCount() == null ? "" : record.getWritingQuestionCount().toString())),
					wrapForCSV((record.getWritingResponseCount() == null ? "" : record.getWritingResponseCount().toString())),
					wrapForCSV((record.getSpeakingHumanScoredCount() == null ? "" : record.getSpeakingHumanScoredCount().toString())),
					record.getSpeakingNonScorableCodes(),
					wrapForCSV((record.getWritingHumanScoredCount() == null ? "" : record.getWritingHumanScoredCount().toString())),
					record.getWritingNonScorableCodes()
				});
			}
		}
		lines.add(0, columnHeaders);
		writeTabSV(lines, moduleReport);
	}
	
	private void writeTabSV(List<String[]> lines, ModuleReport moduleReport) throws IOException {
		CSVWriter csvWriter = null;
        String fileName = null;
        try {
        	String folderName = "dataextracts";
    		String folderPath = REPORT_PATH + java.io.File.separator + folderName;
        	if(moduleReport.getFileName()!= null){
        		fileName = moduleReport.getFileName();
        	}else{
        		fileName = folderPath + java.io.File.separator + getFileName(moduleReport);
        		moduleReport.setFileName(fileName);
        		moduleReportDao.updateByPrimaryKeySelective(moduleReport);
        	}
        	
        	//split the path/filename from the extension
    		String pathAndExtensionArray[] = fileName.split("\\.");
    		//create a local temp file
    		File tempFile = File.createTempFile(pathAndExtensionArray[0], "."+pathAndExtensionArray[1]);
        	csvWriter = new CSVWriter(new FileWriter(tempFile, true), '\t', CSVWriter.NO_QUOTE_CHARACTER);
        	
        	for (String[] line : lines) {
            	csvWriter.writeNext(line);
            	csvWriter.flush();    			
    		}
    		//send the local temp file to s3
    		s3.synchMultipartUpload(fileName, tempFile);
    		//delete the local temp file
    		FileUtils.deleteQuietly(tempFile);
   	 	} catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		throw ex;
        } finally {
        	if(csvWriter != null) {
        		csvWriter.close();
        	}
        }
		
	}

	private void fillKsdeDetails(List<KSDEStudentTestDetailsDTO> ksdeReturnFileDtos,List<String[]> lines, int columnHeadersLength, SimpleDateFormat dateFormat) {
		String ksdeDetails[] = null;
		int i=-1;
		for(KSDEStudentTestDetailsDTO ksdeReturnFileDto : ksdeReturnFileDtos) {
			ksdeDetails = new String[columnHeadersLength];
			i=-1;
			ksdeDetails[++i] = ksdeReturnFileDto.getStateStudentIdentifier();
			ksdeDetails[++i] = ksdeReturnFileDto.getAypSchoolIdentifier();
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_testId());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_testId());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_testId());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerf_testId());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getKsdeFileCode());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getTestGrade());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getTestTypeCode());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_questionCount());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_questionCount());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_questionCount());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerformanceStageQuestionCount());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_respondedCount());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_respondedCount());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_respondedCount());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerf_respondedCount());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getScaleScore());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerformanceLevel());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getElaCombinedPrfrmLevel());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getMdptScore());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getElaMdptScorableFlag());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_ksdeSCCode());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_ksdeSCCode());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_ksdeSCCode());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerf_ksdeSCCode());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1Status());
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2Status());
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3Status());
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerfStatus());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getComputerTest());
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_testBeginTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage1_testBeginTime()) : StringUtils.EMPTY);
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage1_testEndTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage1_testEndTime()) : StringUtils.EMPTY);
			if(!StringUtils.equalsIgnoreCase(ksdeReturnFileDto.getAbbrSubject(), "SS")){
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_testBeginTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage2_testBeginTime()) : StringUtils.EMPTY);
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage2_testEndTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage2_testEndTime()) : StringUtils.EMPTY);
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_testBeginTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage3_testBeginTime()) : StringUtils.EMPTY);
				ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getStage3_testEndTime() != null ? dateFormat.format(ksdeReturnFileDto.getStage3_testEndTime()) : StringUtils.EMPTY);
			}
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerf_testBeginTime() != null ? dateFormat.format(ksdeReturnFileDto.getPerf_testBeginTime()) : StringUtils.EMPTY);
			ksdeDetails[++i] = getStringValueOf(ksdeReturnFileDto.getPerf_testEndTime() != null ? dateFormat.format(ksdeReturnFileDto.getPerf_testEndTime()) : StringUtils.EMPTY);
			ksdeDetails[++i] = (String.valueOf(ksdeReturnFileDto.getScholYear()));
			ksdeDetails[++i] = (String.valueOf(ksdeReturnFileDto.getStudentGrade()));
			
			lines.add(ksdeDetails);
		}
	}
    /*
     * Added during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startTestAssignmentsExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException {	
		
		
		List<Long> assessmentProgramId = (List)additionalParams.get("assessmentProgramIds");
		String qcCompleteStatus = (String) additionalParams.get("qcCompleteStatus");
		String beginDateStr = (String) additionalParams.get("beginDate");
		String toDateStr = (String) additionalParams.get("toDate");
		
		SimpleDateFormat dateFormat = getSimpleDateFormatter(MM_DD_YYYY_DATE_FORMAT, orgId);
		
		/*SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		format.setTimeZone(gmtTime);*/
		
		String[] columnHeaders = {				
				"Form Name", "Test Name","Form ID-Content Builder", "Form ID-Educator Portal","CB Test Collection ID","CB Test Collection Name","Stage", 
				"Panel Name", "Panel ID","Form Publication Date","Form Last Modified Date", 
				"Form Accessibility Flag", "Testlet ID", "Overview Name","Overview ID", 
				"Overview Phase", "Overview Pool", "QC Complete Flag"				
		};
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		
		List<TestFormAssignmentsInfoDTO> testFormAssignmentsExtract = dataExtractService.getTestFormAssignmentsExtracts(assessmentProgramId,qcCompleteStatus,beginDateStr,toDateStr);
		
				
		for (TestFormAssignmentsInfoDTO testFormAssignmentRecord : testFormAssignmentsExtract) {
			lines.add(new String[]{
				wrapForCSV(testFormAssignmentRecord.getFormName()),
				wrapForCSV(testFormAssignmentRecord.getTestname()),
				wrapForCSV((testFormAssignmentRecord.getFormIdContentBuilder() == null ? "" : String.valueOf(testFormAssignmentRecord.getFormIdContentBuilder()))),
				wrapForCSV((testFormAssignmentRecord.getFormIdEducatorPortal() == null ? "" : String.valueOf(testFormAssignmentRecord.getFormIdEducatorPortal()))),
				wrapForCSV((testFormAssignmentRecord.getCBTestCollevtionId() == null ? "" : String.valueOf(testFormAssignmentRecord.getCBTestCollevtionId()))),
				wrapForCSV(testFormAssignmentRecord.getCBTestCollectionName()),
				wrapForCSV(testFormAssignmentRecord.getStage()),
				wrapForCSV(testFormAssignmentRecord.getPanelName()),
				wrapForCSV((testFormAssignmentRecord.getPanelId() == null ? "" : String.valueOf(testFormAssignmentRecord.getPanelId()))),
				wrapForCSV((testFormAssignmentRecord.getFormPublicationDate() == null ? "" : dateFormat.format(testFormAssignmentRecord.getFormPublicationDate()))),
				wrapForCSV((testFormAssignmentRecord.getFormLastModifiedDate() == null ? "" : dateFormat.format(testFormAssignmentRecord.getFormLastModifiedDate()))),
				wrapForCSV(testFormAssignmentRecord.getFormsAccessibilityFlagField()),
				wrapForCSV(testFormAssignmentRecord.getTestletId().trim()),
				wrapForCSV(testFormAssignmentRecord.getOverViewName()),
				wrapForCSV((testFormAssignmentRecord.getOverViewId() == null ? "" : String.valueOf(testFormAssignmentRecord.getOverViewId()))),
				wrapForCSV(testFormAssignmentRecord.getOverViewPhase()),
				wrapForCSV(testFormAssignmentRecord.getOverViewPool()),
				wrapForCSV(testFormAssignmentRecord.getqCCompleteStatusFlag()),
			});
		}
		writeReport(moduleReportId, lines,typeName);		
		return true;	
		
		
	}
	
	
	/* 
	 * US16739 : Kiran Reddy Taduru : 09/04/2015
	 * This is for deleting old extract files from the server physically which has activeflag = false in modulereport table
	 * and update the deleteflag to true for the corresponding deletedfile record in the table. For now we are not deleting
	 * records from the table
	 * @see edu.ku.cete.service.DataReportService#deleteModuleReports()
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteModuleReports(){
		
		String directoryPath = REPORT_PATH;
		String filePath = null;	
		
		List<ModuleReport> moduleReports = null;
		moduleReports = moduleReportDao.selectModuleReportsForDeletion();		
		
		if(moduleReports != null && moduleReports.size() > 0){
			LOGGER.info("moduleReports.size: "+moduleReports.size());
				for(ModuleReport moduleReport : moduleReports){		
					
					if(moduleReport!=null && moduleReport.getId()!=null){
						if(moduleReport.getFileName()!=null){
							
							LOGGER.info("moduleReportType: "+moduleReport.getReportType()+" moduleReport.getReportTypeId(): "+moduleReport.getReportTypeId()+" FilePath: "+
									moduleReport.getFileName()+"ModuleReportId: "+moduleReport.getId()+" ");
							if(moduleReport.getFileName().startsWith(directoryPath)){
								LOGGER.info("The folder path is already appended to the file name.");
								if(s3.doesObjectExist(moduleReport.getFileName())){
									s3.deleteObject(moduleReport.getFileName());
									moduleReportDao.updateModuleReportDeleteFlag(moduleReport.getId());
									LOGGER.info("ModuleReportId: "+moduleReport.getId()+" File deleted and updated for modulereportid: "+moduleReport.getId());
								}
								else
								{
									LOGGER.info("ModuleReportId: "+moduleReport.getId()+" File path not found for "+moduleReport.getId());
								}
							}
							else{
								if(moduleReport.getReportTypeId()!=null){
									LOGGER.info("ModuleReportId: "+moduleReport.getId()+" The folder path is not appended to the file name!");
									DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
									if(type!=null){
										String typeName= type.getName();
										
										if(moduleReport.getOrganizationId()!=null){
										
											String folderPath = getFolderPath(moduleReport, typeName);
											LOGGER.info("ModuleReportId: "+moduleReport.getId()+" folderPath: "+folderPath+" FileName: "+moduleReport.getFileName());
											if(folderPath!=null && moduleReport.getFileName()!=null){		
												filePath= FileUtil.buildFilePath(folderPath, moduleReport.getFileName());
												LOGGER.info("ModuleReportId: "+moduleReport.getId()+" BuiltfolderPath: "+folderPath);
												if(s3.doesObjectExist(filePath)){
													s3.deleteObject(filePath);
													moduleReportDao.updateModuleReportDeleteFlag(moduleReport.getId());
													LOGGER.info("ModuleReportId: "+moduleReport.getId()+" File deleted and updated for modulereportid: "+moduleReport.getId());
												}
												else
													LOGGER.info("ModuleReportId: "+moduleReport.getId()+" File not found "+moduleReport.getId());
											}
											else
												LOGGER.info("ModuleReportId: "+moduleReport.getId()+" Filepath or filename is null "+moduleReport.getId());
										}
										else
											LOGGER.info("ModuleReportId: "+moduleReport.getId()+" OrgId null");
									}
									else
										LOGGER.info("ModuleReportId: "+moduleReport.getId()+" type null"+moduleReport.getId());
								}
							}
						}
						else
							LOGGER.info("ModuleReportId: "+moduleReport.getId()+" Filename is null");
					}else{
					LOGGER.info("Modulereport object or id is null");
						
				}					
			}
		}
		
	}
	
	private String getStringValueOf(Object object) {

		if (object != null) {
			return String.valueOf(object);
		}
		
		return StringUtils.EMPTY;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ModuleReport getQueuedReport(Long inqueueStatusId, Long inprogressStatusId) {
		return moduleReportDao.updateAndGetQueuedModuleReport(inqueueStatusId, inprogressStatusId);
	}		

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startUserNamePasswordExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {		
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		List<Integer> assessmentPrograms = (List<Integer>)additionalParams.get("assessmentProgramIds");
		List<Long> assessmentProgramIds = (List<Long>)additionalParams.get("assessmentProgramIds");
		List<AssessmentProgram> assessmentProgramCodes = assessmentProgramService.getAssessmentProgramCodeById(assessmentProgramIds);
		boolean isGroupingRequired=false;
		List<String> selectedAssPgm = new ArrayList<String>();
		List<String> nonGroupAssPgm = new ArrayList<String>();
		nonGroupAssPgm = Arrays.asList(CommonConstants.ASSESSMENT_PROGRAM_NON_GROUP.split(CommonConstants.SEPARATOR));
		for(AssessmentProgram assProg: assessmentProgramCodes) {
			selectedAssPgm.add(assProg.getAbbreviatedname());
		}
			
		isGroupingRequired= compareList(nonGroupAssPgm,selectedAssPgm);		
		String assessmentSubjectIds = (String)additionalParams.get("assessmentSubjectIds");
		List<Map<Long, Long>> contentAreaAssessment = new ArrayList<Map<Long, Long>>();
		List<Map<Long, Long>> contentAreaAssessmentForDlmAndPltw = new ArrayList<Map<Long, Long>>();
		
		List<Integer> assessmentPgmids = new ArrayList<Integer>();
		List<Integer> assessmentPgmIdsForNonGroup = new ArrayList<Integer>();
		
		for(int i = 0; i< assessmentProgramCodes.size(); i++) {
			
			if(StringUtils.equalsIgnoreCase(assessmentProgramCodes.get(i).getAbbreviatedname(),CommonConstants.ASSESSMENT_PROGRAM_DLM) || 
					StringUtils.equalsIgnoreCase(assessmentProgramCodes.get(i).getAbbreviatedname(),CommonConstants.ASSESSMENT_PROGRAM_PLTW) || 
					StringUtils.equalsIgnoreCase(assessmentProgramCodes.get(i).getAbbreviatedname(),CommonConstants.ASSESSMENT_PROGRAM_I_SMART) || 
					StringUtils.equalsIgnoreCase(assessmentProgramCodes.get(i).getAbbreviatedname(),CommonConstants.ASSESSMENT_PROGRAM_I_SMART2)) {
				assessmentPgmIdsForNonGroup.add(assessmentProgramCodes.get(i).getId().intValue());
			}else {
				assessmentPgmids.add(assessmentProgramCodes.get(i).getId().intValue());
			}
			
			
		}
		if((assessmentSubjectIds != null) && (!StringUtils.equalsIgnoreCase(assessmentSubjectIds, "[]"))){
		    ObjectMapper om = new ObjectMapper();
			List<JsonNode> elements = om.readValue(assessmentSubjectIds, new TypeReference<List<JsonNode>>() {
			});
	
		    for(JsonNode node : elements) {		    	
		    	if((StringUtils.equalsIgnoreCase(node.findValue("apCode").asText(),CommonConstants.ASSESSMENT_PROGRAM_DLM))
		    			 || (StringUtils.equalsIgnoreCase(node.findValue("apCode").asText(),CommonConstants.ASSESSMENT_PROGRAM_PLTW))
		    			 || (StringUtils.equalsIgnoreCase(node.findValue("apCode").asText(),CommonConstants.ASSESSMENT_PROGRAM_I_SMART))
		    			 || (StringUtils.equalsIgnoreCase(node.findValue("apCode").asText(),CommonConstants.ASSESSMENT_PROGRAM_I_SMART2))) {
		    		if(node.findValue("apId") != null && node.findValue("subjectId") !=null) {
				    	Map<Long, Long> row = new HashMap<Long, Long>();
				    	row.put(node.findValue("apId").asLong(), node.findValue("subjectId").asLong());
				    	contentAreaAssessmentForDlmAndPltw.add(row);
			    	}
		    	} else {
		    		if(node.findValue("apId") != null && node.findValue("subjectId") !=null) {
				    	Map<Long, Long> row = new HashMap<Long, Long>();
				    	row.put(node.findValue("apId").asLong(), node.findValue("subjectId").asLong());
				    	contentAreaAssessment.add(row);
			    	}
		    	}	
		    }
		} else {
			contentAreaAssessment = null;
			contentAreaAssessmentForDlmAndPltw = null;
		}
		
		List<String> columnHeadersList = new ArrayList<String>();
		columnHeadersList.add("District");
		columnHeadersList.add("School");
		if(!isGroupingRequired) { columnHeadersList.add("Grade"); }
		if(isGroupingRequired) { columnHeadersList.add("Course"); } else{ columnHeadersList.add("Subject"); }
		columnHeadersList.add("Roster");
		columnHeadersList.add("Student Last Name");
		columnHeadersList.add("Student First Name");
		columnHeadersList.add("Student Middle Name");
		columnHeadersList.add("State Student Identifier");
		columnHeadersList.add("Student Login Username");
		columnHeadersList.add("Student Login Password");
		if(!isGroupingRequired) {
		columnHeadersList.add("Grouping 1");
		columnHeadersList.add("Grouping 2");
		}
		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]); 		
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		int currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).intValue();
		String  gradeAbbreviateName = (String) additionalParams.get("grade");
		Long subjectId = additionalParams.get("subjectId")!=null ? Long.valueOf(additionalParams.get("subjectId").toString()): null;
		Long rosterId = additionalParams.get("rosterId")!= null ? Long.valueOf(additionalParams.get("rosterId").toString()): null;
		int userRoleId = ((Integer) additionalParams.get("userRole")).intValue();
		Groups userGroup = groupService.getGroup(Long.valueOf(userRoleId));
		boolean isTeacher = false;
		if(StringUtils.equalsIgnoreCase(userGroup.getGroupCode(),CommonConstants.GROUP_CODE_TEACHER) || StringUtils.equalsIgnoreCase(userGroup.getGroupCode(),CommonConstants.GROUP_CODE_PROCTOR)) {
			isTeacher = true;
		}
		User user = userDetails.getUser();
/*		boolean isTeacher = user.isTeacher() || user.isProctor();*/
		
		List<ViewStudentDTO> studentInformationRecords = new ArrayList<ViewStudentDTO>();
		List<ViewStudentDTO> studentInformationRecordsForDlmAndPltw = new ArrayList<ViewStudentDTO>();
		
		if(assessmentPgmIdsForNonGroup.size()>0) {
			studentInformationRecordsForDlmAndPltw = studentService.getStudentUserNamePasswordExtractForDlmAndPltw(orgId,
					isTeacher, user.getId(), currentSchoolYear, gradeAbbreviateName, contentAreaAssessmentForDlmAndPltw, rosterId, assessmentPgmIdsForNonGroup);	
		} 
		if(assessmentPgmids.size()>0){
			studentInformationRecords = studentService.getStudentUserNamePasswordExtract(orgId,
					isTeacher, user.getId(), currentSchoolYear, gradeAbbreviateName, contentAreaAssessment, rosterId, assessmentPrograms);
		}		
		
		if(studentInformationRecordsForDlmAndPltw != null && studentInformationRecordsForDlmAndPltw.size() > 0) {
			studentInformationRecords.addAll(studentInformationRecordsForDlmAndPltw);
		}
		
		if((studentInformationRecordsForDlmAndPltw != null) && (studentInformationRecordsForDlmAndPltw.size()>0) && (studentInformationRecords != null) && (studentInformationRecords.size()>0) ) {
			Comparator<ViewStudentDTO> comparator =  new Comparator<ViewStudentDTO>() {

			@Override
			public int compare(ViewStudentDTO stud1, ViewStudentDTO stud2) {
				int compareStateName = stud1.getStateName()==null ?    (stud2.getStateName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getStateName()==null ? Integer.MAX_VALUE : stud1.getStateName().compareTo(stud2.getStateName()));
				
				int compareDistrictName =   stud1.getDistrictName()==null ?    (stud2.getDistrictName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getDistrictName()==null ? Integer.MAX_VALUE : stud1.getDistrictName().compareTo(stud2.getDistrictName()));

				int compareSchoolName     =  stud1.getSchoolName()==null ?    (stud2.getSchoolName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getSchoolName()==null ? Integer.MAX_VALUE : stud1.getSchoolName().compareTo(stud2.getSchoolName()));

				int compareGroupingIndicator1    =  stud1.getGroupingIndicator1()==null ?    (stud2.getGroupingIndicator1()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getGroupingIndicator1()==null ? Integer.MAX_VALUE : stud1.getGroupingIndicator1().compareTo(stud2.getGroupingIndicator1()));

				int compareGroupingIndicator2    = stud1.getGroupingIndicator2()==null ?    (stud2.getGroupingIndicator2()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getGroupingIndicator2()==null ? Integer.MAX_VALUE : stud1.getGroupingIndicator2().compareTo(stud2.getGroupingIndicator2()));

				int compareLegalLastName    =  stud1.getLegalLastName()==null ?    (stud2.getLegalLastName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getLegalLastName()==null ? Integer.MAX_VALUE : stud1.getLegalLastName().compareTo(stud2.getLegalLastName()));
	
				int compareLegalFirstName     =  stud1.getLegalFirstName()==null ?    (stud2.getLegalFirstName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getLegalFirstName()==null ? Integer.MAX_VALUE : stud1.getLegalFirstName().compareTo(stud2.getLegalFirstName()));						

				int compareSubjectName     =  stud1.getSubjectName()==null ?    (stud2.getSubjectName()==null ? 0 : Integer.MIN_VALUE) : 
		    		(stud2.getSubjectName()==null ? Integer.MAX_VALUE : stud1.getSubjectName().compareTo(stud2.getSubjectName()));				        
		    	
		         if(compareStateName == 0) {
		         	if(compareDistrictName == 0) {
		         		if(compareSchoolName == 0) {
		         			if(compareGroupingIndicator1 == 0) {
			         			if(compareGroupingIndicator2 == 0) {
			         				if(compareLegalLastName == 0) {
		         						return ((compareLegalFirstName == 0) ? compareSubjectName : compareLegalFirstName);
					         			} else {
					         				return compareLegalLastName;
					         			}
				         			} else {
				         				return compareGroupingIndicator2;
				         			}
			         			} else {
			         				return compareGroupingIndicator1;
			         			}
		         			} else {
		         				return compareSchoolName;
		         			}
		         		} else {
		         			return compareDistrictName;
		         		}				             
		         	}else {
		         		return compareStateName;
		         	}
		         }
			};
			
			if (comparator != null) {
				Collections.sort(studentInformationRecords, comparator);
			}
		}			
				
		if (studentInformationRecords != null && studentInformationRecords.size() > 0) {
			if(!isGroupingRequired) {
				for (ViewStudentDTO dto : studentInformationRecords) {
					lines.add(new String[] {
						wrapForCSV(dto.getDistrictName()),
						wrapForCSV(dto.getSchoolName()),
						wrapForCSV(dto.getGradeCourseName()),
						wrapForCSV(dto.getSubjectName()),
						wrapForCSV(dto.getRosterName()),
						wrapForCSV(dto.getLegalLastName()),
						wrapForCSV(dto.getLegalFirstName()),
						wrapForCSV(dto.getLegalMiddleName()),
						wrapForCSV(dto.getStateStudentIdentifier()),
						wrapForCSV(dto.getUsername()),
						wrapForCSV(dto.getPassword()),
						wrapForCSV(dto.getGroupingIndicator1()),
						wrapForCSV(dto.getGroupingIndicator2())
					});
				}
				} else {
					for (ViewStudentDTO dto : studentInformationRecords) {
						lines.add(new String[] {
							wrapForCSV(dto.getDistrictName()),
							wrapForCSV(dto.getSchoolName()),
							wrapForCSV(dto.getSubjectName()),
							wrapForCSV(dto.getRosterName()),
							wrapForCSV(dto.getLegalLastName()),
							wrapForCSV(dto.getLegalFirstName()),
							wrapForCSV(dto.getLegalMiddleName()),
							wrapForCSV(dto.getStateStudentIdentifier()),
							wrapForCSV(dto.getUsername()),
							wrapForCSV(dto.getPassword())
						});
					}
				}
		}
		String jsonData = moduleReport.getJsonData();
		try {
			JSONObject jsonObj = new JSONObject(jsonData);
			boolean csvDownload = jsonObj.getBoolean( "csvDownload" );
			boolean pdfDownload = jsonObj.getBoolean( "pdfDownload" );
			if(pdfDownload) {
				writePdf(moduleReport, studentInformationRecords, typeName);				
			}
			if(csvDownload) {
				writeReport(moduleReportId, lines, typeName);				
			} 
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return true;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startRestrictedSpecialCircumstanceCode(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws Exception {
		String[] columnHeaders = { "State", "District", "School", "Educator Last Name", "Educator First Name",
				"Student Last Name", "Student First Name", "Student Middle Name", "State Student Identifier", 
				"Assessment Program","Subject", "Test Session Name", "SC Code Description", "CEDS Code Number",
				"State Code Number", "Approval Status", "Approver Last Name", "Approver First Name", "Approval Date Time"};
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		int currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).intValue();
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		//User user = userDetails.getUser();
		List<StudentSpecialCircumstanceDTO> studentInformationRecords = dataExtractService.getStudentSpecialCircumstanceInfo(orgId,
			currentSchoolYear, assessmentPrograms);
		if (studentInformationRecords != null && studentInformationRecords.size() > 0) {
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);	
			for (StudentSpecialCircumstanceDTO dto : studentInformationRecords) {
				lines.add(new String[] {
					wrapForCSV(dto.getStateName()),
					wrapForCSV(dto.getDistrictName()),
					wrapForCSV(dto.getSchoolName()),
					wrapForCSV(dto.getEducatorLastName()),
					wrapForCSV(dto.getEducatorFirstName()),
					wrapForCSV(dto.getStudentLastName()),
					wrapForCSV(dto.getStudentFirstName()),
					wrapForCSV(dto.getStudentMiddleName()),
					wrapForCSV(dto.getStateStudentIdentifier()),
					wrapForCSV(dto.getAssessmentProgram()),
					wrapForCSV(dto.getSubjectName()),
					wrapForCSV(dto.getTestSessionName()),
					wrapForCSV(dto.getScCodeDescription()),
					wrapForCSV(dto.getCedsCodeNumber()),
					wrapForCSV(dto.getStateCodeNumber()),
					wrapForCSV(dto.getApprovalStatus()),
					wrapForCSV(dto.getApproverLastName()),
					wrapForCSV(dto.getApproverFirstName()),
					wrapForCSV(dto.getApprovalDateTime() != null ? dateFormat.format(dto.getApprovalDateTime()):"")
				});
			}
		}
		writeReport(moduleReportId, lines, typeName);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMPDStatus(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws Exception {
		String[] columnHeaders = {"State", 
				                  "DistrictName",
				                  "DistrictID",
				                  "SchoolName",
				                  "SchoolID",
				                  "Username",
				                  "Lastname",
				                  "Firstname",
				                  "Email",
				                  "Role",
				                  "UserTrainingType",
				                  "RTComplete"
				                  };
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		boolean includeInternalUsers=(Boolean) additionalParams.get("includeInternalUsers");
		Long currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).longValue();
		List<Long> organizations = (List<Long>)additionalParams.get("orgIds");
		List<DLMPDTrainingDTO> reportListItemsDtos = moduleReportDao.generateDLMPDTrainingListItems(currentSchoolYear,organizations,includeInternalUsers);
		if (reportListItemsDtos != null && reportListItemsDtos.size() > 0) {
			for (DLMPDTrainingDTO dto : reportListItemsDtos) {
				lines.add(new String[] {
					wrapForCSV(dto.getState()),
					wrapForCSV(dto.getDistrictName()!= null ? String.valueOf(dto.getDistrictName()):""),
					wrapForCSV(dto.getDistrictId()!= null ? String.valueOf(dto.getDistrictId()):""),
					wrapForCSV(dto.getSchoolName()!= null ? String.valueOf(dto.getSchoolName()):""),
					wrapForCSV(dto.getSchoolId()!= null ? String.valueOf(dto.getSchoolId()):""),
					wrapForCSV(dto.getUserName()),
					wrapForCSV(dto.getLastName()),
					wrapForCSV(dto.getFirstName()),
					wrapForCSV(dto.getEmail()),
					wrapForCSV(dto.getRole()),
					//F730
					wrapForCSV(dto.getUserTrainingType() != null ? dto.getUserTrainingType() : "New"),
					wrapForCSV(dto.getRtComplete())
				});
			}
		}
		
		writeReport(moduleReportId, lines, typeName);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMPDTrainingListExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws Exception {
		String[] columnHeaders = {
									"Username",
									"LastName", "FirstName",
									"Email", "idnumber",
									"password", "state",
									"DistrictName", "DistrictID",
									"SchoolName", "SchoolID",
									"Role", "CreateDate","UserTrainingType", 
									"RTComplete","RTCompleteDate"
								};
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		boolean includeInternalUsers=(Boolean) additionalParams.get("includeInternalUsers");
		Long currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).longValue();
		List<Long> organizations = (List<Long>)additionalParams.get("orgIds");
		List<DLMPDTrainingDTO> reportListItemsDtos = moduleReportDao.generateDLMPDTrainingManagementListItems(currentSchoolYear,organizations,includeInternalUsers);
		
		if (reportListItemsDtos != null && reportListItemsDtos.size() > 0) {
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);	
			for (DLMPDTrainingDTO dto : reportListItemsDtos) {
				lines.add(new String[] {
					wrapForCSV(dto.getUserName()),
					wrapForCSV(dto.getLastName()),
					wrapForCSV(dto.getFirstName()),
					wrapForCSV(dto.getEmail()),
					wrapForCSV(dto.getIdNumber()),
					wrapForCSV(StringUtils.EMPTY),
					wrapForCSV(dto.getState()),
					wrapForCSV(dto.getDistrictName()!= null ? String.valueOf(dto.getDistrictName()):""),
					wrapForCSV(dto.getDistrictId()!= null ? String.valueOf(dto.getDistrictId()):""),
					wrapForCSV(dto.getSchoolName()!= null ? String.valueOf(dto.getSchoolName()):""),
					wrapForCSV(dto.getSchoolId()!= null ? String.valueOf(dto.getSchoolId()):""),
					wrapForCSV(dto.getRole()),
					wrapForCSV(dto.getCreatedDate() != null ? dateFormat.format(dto.getCreatedDate()) : StringUtils.EMPTY),
					//F730
					wrapForCSV(dto.getUserTrainingType() != null ? dto.getUserTrainingType() : "New"),
					wrapForCSV(dto.getRtComplete()),
					wrapForCSV(dto.getRtCompleteDate() != null ? dateFormat.format(dto.getRtCompleteDate()) : StringUtils.EMPTY)
					
				});
			}
		}
		
		writeReport(moduleReportId, lines, typeName);
		return true;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateReportStatusToComplete(Long moduleReportId) {

		Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");		
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);		
		updateReportStatus(moduleReport, completedStatusid);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean generateAmpDataExtract(UserDetailImpl userDetails, Long moduleReportId, List<Long> operationalTestWindowIds) throws IOException {
		Set<Long>pnpStudentIds = null;
		Set<Long>totalItemStudentIds = null;
		Set<Long>answeredItemStudentIds = null;
		Set<Long>viewedItemStudentIds = null;
		Set<Long>totalIncItemStudentIds = null;
		Set<Long>viewedIncItemStudentIds = null;
		Set<Long>answeredIncItemStudentIds = null;
		Set<Long>answerCorrectIncItemStudentIds = null;
		int currentSchoolYear=2016;
		List<String[]> lines = new ArrayList<String[]>();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		try {
			prop.load(cl.getResourceAsStream("ampDataReportFields.properties"));
		    int totalColumns = Integer.parseInt(prop.getProperty("totalcolumns"));
		    String[] columnHeaders = new String[totalColumns];
		    for(int i=1; i<=totalColumns;i++)
		    {
		    	columnHeaders[i-1] = prop.getProperty(String.valueOf(i));
		    }
			lines.add(columnHeaders);
			currentSchoolYear = Integer.parseInt(prop.getProperty("currentSchoolYear"));
		}catch (IOException ex) {
			LOGGER.error("Error while reading ampDataReportFields properties file", ex);
		}
		//int currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).intValue();
		List<Long> ampTestCollections = ampDataExtractService.getAMPTestCollections(currentSchoolYear, operationalTestWindowIds);
		if(ampTestCollections == null || ampTestCollections.size() > 0){
			List<AmpStudentDataExtractDTO> ampDataRecords = ampDataExtractService.getStudentData(ampTestCollections, currentSchoolYear);
			HashMap<Long, HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>>>pnpRecords = ampDataExtractService.getStudentProfileItemAttributes(ampTestCollections, currentSchoolYear);
			if(pnpRecords!= null && pnpRecords.size() > 0){
				pnpStudentIds = pnpRecords.keySet();
			}
			
			List<StudentGradesTestedDTO> gradesTestedLst = ampDataExtractService.getStudentsGradesTested(ampTestCollections, currentSchoolYear);
			//Tested Grade Courses
			List<AmpStudentDataExtractDTO> ampDataRecordsFinal = new ArrayList<AmpStudentDataExtractDTO>();
			if (ampDataRecords != null && ampDataRecords.size() > 0) {
				for (AmpStudentDataExtractDTO dto : ampDataRecords) {
					for(StudentGradesTestedDTO stdGrdTst: gradesTestedLst){
						if((stdGrdTst.getStudentId().longValue() == dto.getStudentId().longValue()) &&
								stdGrdTst.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue()){
							if(stdGrdTst.getGradeCources()!= null && stdGrdTst.getGradeCources().size() == 1){
								AmpStudentDataExtractDTO sdto = new AmpStudentDataExtractDTO ();
								copyAmpStudentDataExtractDTO(sdto, dto);
								sdto.setGradeTested(stdGrdTst.getGradeCources().get(0).getName());
								sdto.setGradeTestedCode(stdGrdTst.getGradeCources().get(0).getAbbreviatedName());
								ampDataRecordsFinal.add(sdto);
							}else if(stdGrdTst.getGradeCources()!= null && stdGrdTst.getGradeCources().size() > 1){
								for(GradeCourse grdcrse: stdGrdTst.getGradeCources()){
									AmpStudentDataExtractDTO sdto = new AmpStudentDataExtractDTO();
									copyAmpStudentDataExtractDTO(sdto, dto);
									sdto.setGradeTested(grdcrse.getName());
									sdto.setGradeTestedCode(grdcrse.getAbbreviatedName());
									ampDataRecordsFinal.add(sdto);
								}
							}
						}
					}
				}
			}
			
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> totalItemRecords = ampDataExtractService.getStudentSubjectTotalItemsCount(ampTestCollections, currentSchoolYear);
			if(totalItemRecords!= null && totalItemRecords.size() > 0){
				totalItemStudentIds = totalItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> viewedItemRecords = ampDataExtractService.getStudentSubjectViewedItemsCount(ampTestCollections, currentSchoolYear);
			if(viewedItemRecords!= null && viewedItemRecords.size() > 0){
				viewedItemStudentIds = viewedItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> answeredItemRecords = ampDataExtractService.getStudentSubjectAnsweredItemsCount(ampTestCollections, currentSchoolYear);
			if(answeredItemRecords!= null && answeredItemRecords.size() > 0){
				answeredItemStudentIds = answeredItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> totalIncludedItemRecords = ampDataExtractService.getStudentSubjectTotalIncludedItemsCount(ampTestCollections, currentSchoolYear);
			if(totalIncludedItemRecords!= null && totalIncludedItemRecords.size() > 0){
				totalIncItemStudentIds = totalIncludedItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> viewedIncludedItemRecords = ampDataExtractService.getStudentSubjectViewedIncludedItemsCount(ampTestCollections, currentSchoolYear);
			if(viewedIncludedItemRecords!= null && viewedIncludedItemRecords.size() > 0){
				viewedIncItemStudentIds = viewedIncludedItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> answeredIncludedItemRecords = ampDataExtractService.getStudentSubjectAnsweredIncludedItemsCount(ampTestCollections, currentSchoolYear);
			if(answeredIncludedItemRecords!= null && answeredIncludedItemRecords.size() > 0){
				answeredIncItemStudentIds = answeredIncludedItemRecords.keySet();
			}
			HashMap<Long, HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>>> answeredCorrectlyIncludedItemRecords = ampDataExtractService.getStudentSubjectAnsweredIncludedItemsCorrectlyCount(ampTestCollections, currentSchoolYear);
			if(answeredCorrectlyIncludedItemRecords!= null && answeredCorrectlyIncludedItemRecords.size() > 0){
				answerCorrectIncItemStudentIds = answeredCorrectlyIncludedItemRecords.keySet();
			}
			if (ampDataRecordsFinal != null && ampDataRecordsFinal.size() > 0) {
				for (AmpStudentDataExtractDTO dto : ampDataRecordsFinal) {
					if(dto!= null && dto.getStudentId()!= null){
						//PNP fields
						if(pnpStudentIds!= null && pnpStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentProfileItemAttributeDTO>> profileAttMap = pnpRecords.get(dto.getStudentId());
							List<AmpExtractStudentProfileItemAttributeDTO> profileAttLst = profileAttMap.get("pnpProfileAttributesList");
							if(profileAttLst!= null && profileAttLst.size() > 0 ){
								for(AmpExtractStudentProfileItemAttributeDTO profileAtt : profileAttLst){
									if("assignedSupport".equalsIgnoreCase(profileAtt.getAttributeName())){
										if("Spoken".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpAuditorySpokenAudio(formatBooleanStr(profileAtt.getSelectedValue()));
											dto.setPnpAuditorySwitches(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("ColourOverlay".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpDispOverlayColor(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("InvertColourChoice".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpDispInvertColorChoice(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("Masking".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpDispMasking(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("BackgroundColour".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpDispContrastColor(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("Signing".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpLanguageSigningType(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("Braille".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpLanguageBraille(formatBooleanStr(profileAtt.getSelectedValue()));
										}else if("AuditoryBackground".equalsIgnoreCase(profileAtt.getAttributeContainerName())){
											dto.setPnpAuditoryBackground(formatBooleanStr(profileAtt.getSelectedValue()));
										}
									}else if("preferenceSubject".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpAuditorySpokenAudioSubjectSetting(profileAtt.getSelectedValue());
									}else if("separateQuiteSetting".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsSeparateQuietOrIndividualSetting(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("readsAssessmentOutLoud".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsPresentationStudentReadsAssessmentAloud(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("useTranslationsDictionary".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsPresentationStudentUsedTranslationDictio(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("dictated".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsResponseStudentDictatedAnswersToScribe(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("usedCommunicationDevice".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsResponseStudentUsedACommunicationDevice(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("signedResponses".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsResponseStudentSignedResponses(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("largePrintBooklet".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsProvidedByAlternateFormLargePrint(formatBooleanStr(profileAtt.getSelectedValue()));	
									}else if("paperAndPencil".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsProvidedByAlternateFormPaperAndPencil(formatBooleanStr(profileAtt.getSelectedValue()));
									}else if("supportsTwoSwitch".equalsIgnoreCase(profileAtt.getAttributeName())){
										dto.setPnpOtherSupportsRequiringAdditionalToolsTwoSwitchSystem(formatBooleanStr(profileAtt.getSelectedValue()));
									}
								}
							}
						}
						
					
						//Total Items
						if(totalItemStudentIds!= null && totalItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> totItemMap = totalItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> totItemCountsLst = totItemMap.get("studentSubjectSectionCountLst");
							if(totItemCountsLst!= null && totItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO totItemRec : totItemCountsLst){
									if(totItemRec.getEnrollmentId()!= null && totItemRec.getGradeCourse()!= null &&
											totItemRec.getContentAreaId()!= null && totItemRec.getStageCode()!= null &&
											totItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
											totItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())
											){
										if(totItemRec.getContentAreaId() == 3){
											if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalNumberItems(getSum(dto.getElaTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setElaStg1TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalNumberItems(getSum(dto.getElaTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setElaStg2TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalNumberItems(getSum(dto.getElaTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setElaStg3TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalNumberItems(getSum(dto.getElaTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setElaStg4TotalNumberItems(totItemRec.getTotalCount().toString());
											}
										}else if(totItemRec.getContentAreaId() == 440){
											if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalNumberItems(getSum(dto.getMathTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setMathStg1TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalNumberItems(getSum(dto.getMathTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setMathStg2TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalNumberItems(getSum(dto.getMathTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setMathStg3TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalNumberItems(getSum(dto.getMathTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setMathStg4TotalNumberItems(totItemRec.getTotalCount().toString());
											}
										}else if(totItemRec.getContentAreaId() == 441){
											if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalNumberItems(getSum(dto.getSciTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setSciStg1TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalNumberItems(getSum(dto.getSciTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setSciStg2TotalNumberItems(totItemRec.getTotalCount().toString());
											}else if(totItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalNumberItems(getSum(dto.getSciTotalNumberItems(), totItemRec.getTotalCount()));
												dto.setSciStg3TotalNumberItems(totItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						//Viewed Items
						if(viewedItemStudentIds!= null && viewedItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> viewedItemMap = viewedItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> viewedItemCountsLst = viewedItemMap.get("studentSubjectSectionCountLst");
							if(viewedItemCountsLst!= null && viewedItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO viewedItemRec : viewedItemCountsLst){
									if(viewedItemRec.getContentAreaId()!= null && viewedItemRec.getStageCode()!= null &&
										viewedItemRec.getEnrollmentId()!= null && viewedItemRec.getGradeCourse()!= null &&
										viewedItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
										viewedItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(viewedItemRec.getContentAreaId() == 3){
											if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalItemsViewed(getSum(dto.getElaTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setElaStg1ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode()) ){
												dto.setElaTotalItemsViewed(getSum(dto.getElaTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setElaStg2ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalItemsViewed(getSum(dto.getElaTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setElaStg3ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalItemsViewed(getSum(dto.getElaTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setElaStg4ItemsViewed(viewedItemRec.getTotalCount().toString());
											}
										}else if(viewedItemRec.getContentAreaId() == 440){
											if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalItemsViewed(getSum(dto.getMathTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setMathStg1ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalItemsViewed(getSum(dto.getMathTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setMathStg2ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalItemsViewed(getSum(dto.getMathTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setMathStg3ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalItemsViewed(getSum(dto.getMathTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setMathStg4ItemsViewed(viewedItemRec.getTotalCount().toString());
											}
										}else if(viewedItemRec.getContentAreaId()!= null && viewedItemRec.getContentAreaId() == 441){
											if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalItemsViewed(getSum(dto.getSciTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setSciStg1ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalItemsViewed(getSum(dto.getSciTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setSciStg2ItemsViewed(viewedItemRec.getTotalCount().toString());
											}else if(viewedItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalItemsViewed(getSum(dto.getSciTotalItemsViewed(), viewedItemRec.getTotalCount()));
												dto.setSciStg3ItemsViewed(viewedItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						//Answered Items
						if(answeredItemStudentIds!= null && answeredItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> answeredItemMap = answeredItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> answeredItemCountsLst = answeredItemMap.get("studentSubjectSectionCountLst");
							if(answeredItemCountsLst!= null && answeredItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO answeredItemRec : answeredItemCountsLst){
									if(answeredItemRec.getContentAreaId()!= null && answeredItemRec.getStageCode()!= null &&
										answeredItemRec.getEnrollmentId()!= null && answeredItemRec.getGradeCourse()!= null &&
										answeredItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
										answeredItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(answeredItemRec.getContentAreaId() == 3){
											if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalItemsAnswered(getSum(dto.getElaTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setElaStg1ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalItemsAnswered(getSum(dto.getElaTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setElaStg2ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalItemsAnswered(getSum(dto.getElaTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setElaStg3ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalItemsAnswered(getSum(dto.getElaTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setElaStg4ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}
										}else if(answeredItemRec.getContentAreaId() == 440){
											if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalItemsAnswered(getSum(dto.getMathTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setMathStg1ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalItemsAnswered(getSum(dto.getMathTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setMathStg2ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalItemsAnswered(getSum(dto.getMathTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setMathStg3ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalItemsAnswered(getSum(dto.getMathTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setMathStg4ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}
										}else if(answeredItemRec.getContentAreaId()!= null && answeredItemRec.getContentAreaId() == 441){
											if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalItemsAnswered(getSum(dto.getSciTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setSciStg1ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalItemsAnswered(getSum(dto.getSciTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setSciStg2ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}else if(answeredItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalItemsAnswered(getSum(dto.getSciTotalItemsAnswered(), answeredItemRec.getTotalCount()));
												dto.setSciStg3ItemsAnswered(answeredItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						
						//Total Included Items
						if(totalIncItemStudentIds!= null && totalIncItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> totIncItemMap = totalIncludedItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> totIncItemCountsLst = totIncItemMap.get("studentSubjectSectionCountLst");
							if(totIncItemCountsLst!= null && totIncItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO totIncItemRec : totIncItemCountsLst){
									if(totIncItemRec.getContentAreaId()!= null && totIncItemRec.getStageCode()!= null &&
											totIncItemRec.getEnrollmentId()!= null && totIncItemRec.getGradeCourse()!= null &&
											totIncItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
											totIncItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(totIncItemRec.getContentAreaId() == 3){
											if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalIncludedItems(getSum(dto.getElaTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setElaStg1IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalIncludedItems(getSum(dto.getElaTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setElaStg2IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalIncludedItems(getSum(dto.getElaTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setElaStg3IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalIncludedItems(getSum(dto.getElaTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setElaStg4IncludedItems(totIncItemRec.getTotalCount().toString());
											}
										}else if(totIncItemRec.getContentAreaId() == 440){
											if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalIncludedItems(getSum(dto.getMathTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setMathStg1IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalIncludedItems(getSum(dto.getMathTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setMathStg2IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalIncludedItems(getSum(dto.getMathTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setMathStg3IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode() .equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalIncludedItems(getSum(dto.getMathTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setMathStg4IncludedItems(totIncItemRec.getTotalCount().toString());
											}
										}else if(totIncItemRec.getContentAreaId()!= null && totIncItemRec.getContentAreaId() == 441){
											if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalIncludedItems(getSum(dto.getSciTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setSciStg1IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalIncludedItems(getSum(dto.getSciTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setSciStg2IncludedItems(totIncItemRec.getTotalCount().toString());
											}else if(totIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalIncludedItems(getSum(dto.getSciTotalIncludedItems(), totIncItemRec.getTotalCount()));
												dto.setSciStg3IncludedItems(totIncItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						
						//Included Items Viewed
						if(viewedIncItemStudentIds!= null && viewedIncItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> viewedIncItemMap = viewedIncludedItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> viewedIncItemCountsLst = viewedIncItemMap.get("studentSubjectSectionCountLst");
							if(viewedIncItemCountsLst!= null && viewedIncItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO viewedIncItemRec : viewedIncItemCountsLst){
									if(viewedIncItemRec.getContentAreaId()!= null && viewedIncItemRec.getStageCode()!= null &&
										viewedIncItemRec.getEnrollmentId()!= null && viewedIncItemRec.getGradeCourse()!= null &&
										viewedIncItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
										viewedIncItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(viewedIncItemRec.getContentAreaId() == 3){
											if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalIncludedItemsViewed(getSum(dto.getElaTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setElaStg1IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalIncludedItemsViewed(getSum(dto.getElaTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setElaStg2IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalIncludedItemsViewed(getSum(dto.getElaTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setElaStg3IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalIncludedItemsViewed(getSum(dto.getElaTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setElaStg4IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}
										}else if(viewedIncItemRec.getContentAreaId() == 440){
											if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalIncludedItemsViewed(getSum(dto.getMathTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setMathStg1IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalIncludedItemsViewed(getSum(dto.getMathTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setMathStg2IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalIncludedItemsViewed(getSum(dto.getMathTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setMathStg3IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalIncludedItemsViewed(getSum(dto.getMathTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setMathStg4IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}
										}else if(viewedIncItemRec.getContentAreaId()!= null && viewedIncItemRec.getContentAreaId() == 441){
											if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalIncludedItemsViewed(getSum(dto.getSciTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setSciStg1IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalIncludedItemsViewed(getSum(dto.getSciTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setSciStg2IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}else if(viewedIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalIncludedItemsViewed(getSum(dto.getSciTotalIncludedItemsViewed(), viewedIncItemRec.getTotalCount()));
												dto.setSciStg3IncludedItemsViewed(viewedIncItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						
						//Included Items Answered 
						if(answeredIncItemStudentIds!= null && answeredIncItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> answeredIncItemMap = answeredIncludedItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> answeredIncItemCountsLst = answeredIncItemMap.get("studentSubjectSectionCountLst");
							if(answeredIncItemCountsLst!= null && answeredIncItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO answeredIncItemRec : answeredIncItemCountsLst){
									if(answeredIncItemRec.getContentAreaId()!= null && answeredIncItemRec.getStageCode()!= null &&
										answeredIncItemRec.getEnrollmentId()!= null && answeredIncItemRec.getGradeCourse()!= null &&
										answeredIncItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
										answeredIncItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(answeredIncItemRec.getContentAreaId() == 3){
											if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalIncludedItemsAnswered(getSum(dto.getElaTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setElaStg1IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalIncludedItemsAnswered(getSum(dto.getElaTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setElaStg2IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalIncludedItemsAnswered(getSum(dto.getElaTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setElaStg3IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalIncludedItemsAnswered(getSum(dto.getElaTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setElaStg4IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}
										}else if(answeredIncItemRec.getContentAreaId() == 440){
											if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalIncludedItemsAnswered(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setMathStg1IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalIncludedItemsAnswered(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setMathStg2IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalIncludedItemsAnswered(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setMathStg3IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalIncludedItemsAnswered(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setMathStg4IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}
										}else if(answeredIncItemRec.getContentAreaId()!= null && answeredIncItemRec.getContentAreaId() == 441){
											if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalIncludedItemsAnswered(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setSciStg1IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalIncludedItemsAnswered(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setSciStg2IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}else if(answeredIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalIncludedItemsAnswered(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredIncItemRec.getTotalCount()));
												dto.setSciStg3IncludedItemsAnswered(answeredIncItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						
						//Included Items Answered correctly
						if(answerCorrectIncItemStudentIds!= null && answerCorrectIncItemStudentIds.contains(dto.getStudentId())){
							HashMap<Long,List<AmpExtractStudentSujectSectionItemCountDTO>> answeredCorrectIncItemMap = answeredCorrectlyIncludedItemRecords.get(dto.getStudentId());
							List<AmpExtractStudentSujectSectionItemCountDTO> answeredCorrectIncItemCountsLst = answeredCorrectIncItemMap.get("studentSubjectSectionCountLst");
							if(answeredCorrectIncItemCountsLst!= null && answeredCorrectIncItemCountsLst.size() > 0 ){
								for(AmpExtractStudentSujectSectionItemCountDTO answeredCorrectIncItemRec : answeredCorrectIncItemCountsLst){
									if(answeredCorrectIncItemRec.getContentAreaId()!= null && answeredCorrectIncItemRec.getStageCode()!= null &&
											answeredCorrectIncItemRec.getEnrollmentId()!= null && answeredCorrectIncItemRec.getGradeCourse()!= null &&
											answeredCorrectIncItemRec.getEnrollmentId().longValue() == dto.getEnrollmentId().longValue() &&
											answeredCorrectIncItemRec.getGradeCourse().equalsIgnoreCase(dto.getGradeTestedCode())){
										if(answeredCorrectIncItemRec.getContentAreaId() == 3){
											if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setElaTotalIncludedItemsAnsweredCorrectly(getSum(dto.getElaTotalIncludedItemsAnsweredCorrectly(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setElaStg1IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setElaTotalIncludedItemsAnsweredCorrectly(getSum(dto.getElaTotalIncludedItemsAnsweredCorrectly(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setElaStg2IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setElaTotalIncludedItemsAnsweredCorrectly(getSum(dto.getElaTotalIncludedItemsAnsweredCorrectly(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setElaStg3IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setElaTotalIncludedItemsAnsweredCorrectly(getSum(dto.getElaTotalIncludedItemsAnsweredCorrectly(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setElaStg4IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}
										}else if(answeredCorrectIncItemRec.getContentAreaId() == 440){
											if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setMathTotalIncludedItemsAnsweredCorrectly(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setMathStg1IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setMathTotalIncludedItemsAnsweredCorrectly(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setMathStg2IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setMathTotalIncludedItemsAnsweredCorrectly(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setMathStg3IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE4.getCode())){
												dto.setMathTotalIncludedItemsAnsweredCorrectly(getSum(dto.getMathTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setMathStg4IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}
										}else if(answeredCorrectIncItemRec.getContentAreaId()!= null && answeredCorrectIncItemRec.getContentAreaId() == 441){
											if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE1.getCode())){
												dto.setSciTotalIncludedItemsAnsweredCorrectly(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setSciStg1IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE2.getCode())){
												dto.setSciTotalIncludedItemsAnsweredCorrectly(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setSciStg2IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}else if(answeredCorrectIncItemRec.getStageCode().equalsIgnoreCase(StageEnum.STAGE3.getCode())){
												dto.setSciTotalIncludedItemsAnsweredCorrectly(getSum(dto.getSciTotalIncludedItemsAnswered(), answeredCorrectIncItemRec.getTotalCount()));
												dto.setSciStg3IncludedItemsAnsweredCorrectly(answeredCorrectIncItemRec.getTotalCount().toString());
											}
										}
									}
								}
							}
						}
						
					lines.add(new String[] {
							wrapForCSV(dto.getStudentStateId()),
							wrapForCSV(dto.getGradeEnrolled()),
							wrapForCSV(dto.getGradeTested()),
							wrapForCSV(dto.getAttendanceSchoolDispId()),
							wrapForCSV(dto.getDistrictDispId()),
							wrapForCSV(dto.getStudentLegalLastName()),
							wrapForCSV(dto.getStudentLegalFirstName()),
							wrapForCSV(dto.getStudentLegalMiddleName()),
							wrapForCSV(dto.getGenerationCode()),
							wrapForCSV(dto.getGender()),
							wrapForCSV(dto.getDateOfBirth()),
							wrapForCSV(dto.getComprehensiveRace()),
							wrapForCSV(dto.getPrimaryDisabilityCode()),
							wrapForCSV(dto.getGiftedStudent()),
							wrapForCSV(dto.getHispanicEthnicity()),
							wrapForCSV(dto.getFirstLanguage()),
							wrapForCSV(dto.getEsolParticipationCode()),
							wrapForCSV(dto.getPnpDispOverlayColor()),
							wrapForCSV(dto.getPnpDispInvertColorChoice()),
							wrapForCSV(dto.getPnpDispMasking()),
							wrapForCSV(dto.getPnpDispContrastColor()),
							wrapForCSV(dto.getPnpLanguageSigningType()),
							wrapForCSV(dto.getPnpLanguageBraille()),
							wrapForCSV(dto.getPnpAuditoryBackground()),
							wrapForCSV(dto.getPnpAuditorySpokenAudio()),
							wrapForCSV(dto.getPnpAuditorySpokenAudioSubjectSetting()),
							wrapForCSV(dto.getPnpAuditorySwitches()),
							wrapForCSV(dto.getPnpOtherSupportsSeparateQuietOrIndividualSetting()),
							wrapForCSV(dto.getPnpOtherSupportsPresentationStudentReadsAssessmentAloud()),
							wrapForCSV(dto.getPnpOtherSupportsPresentationStudentUsedTranslationDictio()),
							wrapForCSV(dto.getPnpOtherSupportsResponseStudentDictatedAnswersToScribe()),
							wrapForCSV(dto.getPnpOtherSupportsResponseStudentUsedACommunicationDevice()),
							wrapForCSV(dto.getPnpOtherSupportsResponseStudentSignedResponses()),
							wrapForCSV(dto.getPnpOtherSupportsProvidedByAlternateFormLargePrint()),
							wrapForCSV(dto.getPnpOtherSupportsProvidedByAlternateFormPaperAndPencil()),
							wrapForCSV(dto.getPnpOtherSupportsRequiringAdditionalToolsTwoSwitchSystem()),
							wrapForCSV(dto.getPnpOtherSupportsProvidedOutsideSystemAsiniep()),
							wrapForCSV(dto.getElaTotalNumberItems()),
							wrapForCSV(dto.getElaTotalItemsViewed()),
							wrapForCSV(dto.getElaTotalItemsAnswered()),
							wrapForCSV(dto.getElaTotalIncludedItems()),
							wrapForCSV(dto.getElaTotalIncludedItemsViewed()),
							wrapForCSV(dto.getElaTotalIncludedItemsAnswered()),
							wrapForCSV(dto.getElaTotalIncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getMathTotalNumberItems()),
							wrapForCSV(dto.getMathTotalItemsViewed()),
							wrapForCSV(dto.getMathTotalItemsAnswered()),
							wrapForCSV(dto.getMathTotalIncludedItems()),
							wrapForCSV(dto.getMathTotalIncludedItemsViewed()),
							wrapForCSV(dto.getMathTotalIncludedItemsAnswered()),
							wrapForCSV(dto.getMathTotalIncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getSciTotalNumberItems()),
							wrapForCSV(dto.getSciTotalItemsViewed()),
							wrapForCSV(dto.getSciTotalItemsAnswered()),
							wrapForCSV(dto.getSciTotalIncludedItems()),
							wrapForCSV(dto.getSciTotalIncludedItemsViewed()),
							wrapForCSV(dto.getSciTotalIncludedItemsAnswered()),
							wrapForCSV(dto.getSciTotalIncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getElaScCodeText()),
							wrapForCSV(dto.getElaScCode()),
							wrapForCSV(dto.getElaStg1InternalName()),
							wrapForCSV(dto.getElaStg2InternalName()),
							wrapForCSV(dto.getElaStg3InternalName()),
							wrapForCSV(dto.getElaStg4InternalName()),
							wrapForCSV(dto.getElaStg1Status()),
							wrapForCSV(dto.getElaStg2Status()),
							wrapForCSV(dto.getElaStg3Status()),
							wrapForCSV(dto.getElaStg4Status()),
							wrapForCSV(dto.getElaTestType()),
							wrapForCSV(dto.getElaStg1TotalNumberItems()),
							wrapForCSV(dto.getElaStg1ItemsViewed()),
							wrapForCSV(dto.getElaStg1ItemsAnswered()),
							wrapForCSV(dto.getElaStg2TotalNumberItems()),
							wrapForCSV(dto.getElaStg2ItemsViewed()),
							wrapForCSV(dto.getElaStg2ItemsAnswered()),
							wrapForCSV(dto.getElaStg3TotalNumberItems()),
							wrapForCSV(dto.getElaStg3ItemsViewed()),
							wrapForCSV(dto.getElaStg3ItemsAnswered()),
							wrapForCSV(dto.getElaStg4TotalNumberItems()),
							wrapForCSV(dto.getElaStg4ItemsViewed()),
							wrapForCSV(dto.getElaStg4ItemsAnswered()),
							wrapForCSV(dto.getMathScCodeText()),
							wrapForCSV(dto.getMathScCode()),
							wrapForCSV(dto.getMathStg1InternalName()),
							wrapForCSV(dto.getMathStg2InternalName()),
							wrapForCSV(dto.getMathStg3InternalName()),
							wrapForCSV(dto.getMathStg4InternalName()),
							wrapForCSV(dto.getMathStg1Status()),
							wrapForCSV(dto.getMathStg2Status()),
							wrapForCSV(dto.getMathStg3Status()),
							wrapForCSV(dto.getMathStg4Status()),
							wrapForCSV(dto.getMathTestType()),
							wrapForCSV(dto.getMathStg1TotalNumberItems()),
							wrapForCSV(dto.getMathStg1ItemsViewed()),
							wrapForCSV(dto.getMathStg1ItemsAnswered()),
							wrapForCSV(dto.getMathStg2TotalNumberItems()),
							wrapForCSV(dto.getMathStg2ItemsViewed()),
							wrapForCSV(dto.getMathStg2ItemsAnswered()),
							wrapForCSV(dto.getMathStg3TotalNumberItems()),
							wrapForCSV(dto.getMathStg3ItemsViewed()),
							wrapForCSV(dto.getMathStg3ItemsAnswered()),
							wrapForCSV(dto.getMathStg4TotalNumberItems()),
							wrapForCSV(dto.getMathStg4ItemsViewed()),
							wrapForCSV(dto.getMathStg4ItemsAnswered()),
							wrapForCSV(dto.getSciScCodeText()),
							wrapForCSV(dto.getSciScCode()),
							wrapForCSV(dto.getSciStg1InternalName()),
							wrapForCSV(dto.getSciStg2InternalName()),
							wrapForCSV(dto.getSciStg3InternalName()),
							wrapForCSV(dto.getSciStg1Status()),
							wrapForCSV(dto.getSciStg2Status()),
							wrapForCSV(dto.getSciStg3Status()),
							wrapForCSV(dto.getSciTestType()),
							wrapForCSV(dto.getSciStg1TotalNumberItems()),
							wrapForCSV(dto.getSciStg1ItemsViewed()),
							wrapForCSV(dto.getSciStg1ItemsAnswered()),
							wrapForCSV(dto.getSciStg2TotalNumberItems()),
							wrapForCSV(dto.getSciStg2ItemsViewed()),
							wrapForCSV(dto.getSciStg2ItemsAnswered()),
							wrapForCSV(dto.getSciStg3TotalNumberItems()),
							wrapForCSV(dto.getSciStg3ItemsViewed()),
							wrapForCSV(dto.getSciStg3ItemsAnswered()),
							wrapForCSV(dto.getElaStg1IncludedItems()),
							wrapForCSV(dto.getElaStg1IncludedItemsViewed()),
							wrapForCSV(dto.getElaStg1IncludedItemsAnswered()),
							wrapForCSV(dto.getElaStg1IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getElaStg2IncludedItems()),
							wrapForCSV(dto.getElaStg2IncludedItemsViewed()),
							wrapForCSV(dto.getElaStg2IncludedItemsAnswered()),
							wrapForCSV(dto.getElaStg2IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getElaStg3IncludedItems()),
							wrapForCSV(dto.getElaStg3IncludedItemsViewed()),
							wrapForCSV(dto.getElaStg3IncludedItemsAnswered()),
							wrapForCSV(dto.getElaStg3IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getElaStg4IncludedItems()),
							wrapForCSV(dto.getElaStg4IncludedItemsViewed()),
							wrapForCSV(dto.getElaStg4IncludedItemsAnswered()),
							wrapForCSV(dto.getElaStg4IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getMathStg1IncludedItems()),
							wrapForCSV(dto.getMathStg1IncludedItemsViewed()),
							wrapForCSV(dto.getMathStg1IncludedItemsAnswered()),
							wrapForCSV(dto.getMathStg1IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getMathStg2IncludedItems()),
							wrapForCSV(dto.getMathStg2IncludedItemsViewed()),
							wrapForCSV(dto.getMathStg2IncludedItemsAnswered()),
							wrapForCSV(dto.getMathStg2IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getMathStg3IncludedItems()),
							wrapForCSV(dto.getMathStg3IncludedItemsViewed()),
							wrapForCSV(dto.getMathStg3IncludedItemsAnswered()),
							wrapForCSV(dto.getMathStg3IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getMathStg4IncludedItems()),
							wrapForCSV(dto.getMathStg4IncludedItemsViewed()),
							wrapForCSV(dto.getMathStg4IncludedItemsAnswered()),
							wrapForCSV(dto.getMathStg4IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getSciStg1IncludedItems()),
							wrapForCSV(dto.getSciStg1IncludedItemsViewed()),
							wrapForCSV(dto.getSciStg1IncludedItemsAnswered()),
							wrapForCSV(dto.getSciStg1IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getSciStg2IncludedItems()),
							wrapForCSV(dto.getSciStg2IncludedItemsViewed()),
							wrapForCSV(dto.getSciStg2IncludedItemsAnswered()),
							wrapForCSV(dto.getSciStg2IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getSciStg3IncludedItems()),
							wrapForCSV(dto.getSciStg3IncludedItemsViewed()),
							wrapForCSV(dto.getSciStg3IncludedItemsAnswered()),
							wrapForCSV(dto.getSciStg3IncludedItemsAnsweredCorrectly()),
							wrapForCSV(dto.getElaScaleScore()),
							wrapForCSV(dto.getElaStandardError()),
							wrapForCSV(dto.getElaAchievementLevelNumber()),
							wrapForCSV(dto.getElaIncompleteFlag()),
							wrapForCSV(dto.getElaStudentReportGenerated()),
							wrapForCSV(dto.getElaAggregatesIncluded()),
							wrapForCSV(dto.getElaSubscore1ReportDispName()),
							wrapForCSV(dto.getElaSubscore1ScaleScore()),
							wrapForCSV(dto.getElaSubscore1StandardError()),
							wrapForCSV(dto.getElaSubscore2ReportDispName()),
							wrapForCSV(dto.getElaSubscore2ScaleScore()),
							wrapForCSV(dto.getElaSubscore2StandardError()),
							wrapForCSV(dto.getElaSubscore3ReportDispName()),
							wrapForCSV(dto.getElaSubscore3ScaleScore()),
							wrapForCSV(dto.getElaSubscore3StandardError()),
							wrapForCSV(dto.getElaSubscore4ReportDispName()),
							wrapForCSV(dto.getElaSubscore4ScaleScore()),
							wrapForCSV(dto.getElaSubscore4StandardError()),
							wrapForCSV(dto.getMathScaleScore()),
							wrapForCSV(dto.getMathStandardError()),
							wrapForCSV(dto.getMathAchievementLevelNumber()),
							wrapForCSV(dto.getMathIncompleteFlag()),
							wrapForCSV(dto.getMathStudentReportGenerated()),
							wrapForCSV(dto.getMathAggregatesIncluded()),
							wrapForCSV(dto.getMathSubscore1ReportDispName()),
							wrapForCSV(dto.getMathSubscore1ScaleScore()),
							wrapForCSV(dto.getMathSubscore1StandardError()),
							wrapForCSV(dto.getMathSubscore2ReportDispName()),
							wrapForCSV(dto.getMathSubscore2ScaleScore()),
							wrapForCSV(dto.getMathSubscore2StandardError()),
							wrapForCSV(dto.getSciScaleScore()),
							wrapForCSV(dto.getSciStandardError()),
							wrapForCSV(dto.getSciAchievementLevelNumber()),
							wrapForCSV(dto.getSciIncompleteFlag()),
							wrapForCSV(dto.getSciStudentReportGenerated()),
							wrapForCSV(dto.getSciAggregatesIncluded()),
							wrapForCSV(dto.getSciSubscore1ReportDispName()),
							wrapForCSV(dto.getSciSubscore1ScaleScore()),
							wrapForCSV(dto.getSciSubscore1StandardError()),
							wrapForCSV(dto.getSciSubscore2ReportDispName()),
							wrapForCSV(dto.getSciSubscore2ScaleScore()),
							wrapForCSV(dto.getSciSubscore2StandardError()),
							wrapForCSV(dto.getSciSubscore3ReportDispName()),
							wrapForCSV(dto.getSciSubscore3ScaleScore()),
							wrapForCSV(dto.getSciSubscore3StandardError()),
							wrapForCSV(dto.getSciSubscore4ReportDispName()),
							wrapForCSV(dto.getSciSubscore4ScaleScore()),
							wrapForCSV(dto.getSciSubscore4StandardError())
					});
				}
			}
			}
		}
		writeReport(moduleReportId, lines, "AMP");
		return true;
	}
	
	private String formatBooleanStr(String s){
		if(s== null){
			return "No";
		}else if("true".equalsIgnoreCase(s.trim())){
			return "Yes";
		}else{
			return "No";
		}
	}
	
	private String getSum(String s1, Long s2){
		if(s1 != null){
			return String.valueOf(Integer.parseInt(s1)+s2);
		}else{
			return String.valueOf(s2);
		}
	}
	
	void copyAmpStudentDataExtractDTO(AmpStudentDataExtractDTO sdto , AmpStudentDataExtractDTO dto){
				sdto.setStudentId(dto.getStudentId());
				sdto.setEnrollmentId(dto.getEnrollmentId());
				sdto.setStudentStateId(dto.getStudentStateId());
				sdto.setGradeEnrolled(dto.getGradeEnrolled());
				sdto.setGradeTested(dto.getGradeTested());
				sdto.setAttendanceSchoolDispId(dto.getAttendanceSchoolDispId());
				sdto.setDistrictDispId(dto.getDistrictDispId());
				sdto.setStudentLegalLastName(dto.getStudentLegalLastName());
				sdto.setStudentLegalFirstName(dto.getStudentLegalFirstName());
				sdto.setStudentLegalMiddleName(dto.getStudentLegalMiddleName());
				sdto.setGenerationCode(dto.getGenerationCode());
				sdto.setGender(dto.getGender());
				sdto.setDateOfBirth(dto.getDateOfBirth());
				sdto.setComprehensiveRace(dto.getComprehensiveRace());
				sdto.setPrimaryDisabilityCode(dto.getPrimaryDisabilityCode());
				sdto.setGiftedStudent(dto.getGiftedStudent());
				sdto.setHispanicEthnicity(dto.getHispanicEthnicity());
				sdto.setFirstLanguage(dto.getFirstLanguage());
				sdto.setEsolParticipationCode(dto.getEsolParticipationCode());
				sdto.setPnpDispOverlayColor(dto.getPnpDispOverlayColor());
				sdto.setPnpDispInvertColorChoice(dto.getPnpDispInvertColorChoice());
				sdto.setPnpDispMasking(dto.getPnpDispMasking());
				sdto.setPnpDispContrastColor(dto.getPnpDispContrastColor());
				sdto.setPnpLanguageSigningType(dto.getPnpLanguageSigningType());
				sdto.setPnpLanguageBraille(dto.getPnpLanguageBraille());
				sdto.setPnpAuditoryBackground(dto.getPnpAuditoryBackground());
				sdto.setPnpAuditorySpokenAudio(dto.getPnpAuditorySpokenAudio());
				sdto.setPnpAuditorySpokenAudioSubjectSetting(dto.getPnpAuditorySpokenAudioSubjectSetting());
				sdto.setPnpAuditorySwitches(dto.getPnpAuditorySwitches());
				sdto.setPnpOtherSupportsSeparateQuietOrIndividualSetting(dto.getPnpOtherSupportsSeparateQuietOrIndividualSetting());
				sdto.setPnpOtherSupportsPresentationStudentReadsAssessmentAloud(dto.getPnpOtherSupportsPresentationStudentReadsAssessmentAloud());
				sdto.setPnpOtherSupportsPresentationStudentUsedTranslationDictio(dto.getPnpOtherSupportsPresentationStudentUsedTranslationDictio());
				sdto.setPnpOtherSupportsResponseStudentDictatedAnswersToScribe(dto.getPnpOtherSupportsResponseStudentDictatedAnswersToScribe());
				sdto.setPnpOtherSupportsResponseStudentUsedACommunicationDevice(dto.getPnpOtherSupportsResponseStudentUsedACommunicationDevice());
				sdto.setPnpOtherSupportsResponseStudentSignedResponses(dto.getPnpOtherSupportsResponseStudentSignedResponses());
				sdto.setPnpOtherSupportsProvidedByAlternateFormLargePrint(dto.getPnpOtherSupportsProvidedByAlternateFormLargePrint());
				sdto.setPnpOtherSupportsProvidedByAlternateFormPaperAndPencil(dto.getPnpOtherSupportsProvidedByAlternateFormPaperAndPencil());
				sdto.setPnpOtherSupportsRequiringAdditionalToolsTwoSwitchSystem(dto.getPnpOtherSupportsRequiringAdditionalToolsTwoSwitchSystem());
				sdto.setPnpOtherSupportsProvidedOutsideSystemAsiniep(dto.getPnpOtherSupportsProvidedOutsideSystemAsiniep());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startSecurityAgreementExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {

		String[] userColumnHeaders = { "State", "District", "Building", "Last Name",
				"First Name", "Middle Name",
				"Security Agreement Status", "Signature Entered",
				"Signature Date"};
		boolean includeInternalUsers= false;
		if(additionalParams.get("includeInternalUsers") != null){
			includeInternalUsers = (Boolean) additionalParams.get("includeInternalUsers");
		}
		
		List<Long> assessmentPrograms = (List<Long>)additionalParams.get("assessmentProgramIds");
		
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(userColumnHeaders);
		List<Object> orgIds = (List<Object>)additionalParams.get("orgIds");
		List<Long> allOrgIds = new ArrayList<Long>();
		for(Object organizationId : orgIds){
			allOrgIds.addAll(dataExtractService.getAllChildrenOrgIds(Long.valueOf(organizationId.toString())));
			// add the organization also to the list
			allOrgIds.add(Long.valueOf(organizationId.toString()));
		}
		Long currentSchoolYear = Long.valueOf(((Integer)additionalParams.get("currentSchoolYear")).intValue());

		List<UserSecurityAgreemntDTO> userSecurityAgreementDetails = dataExtractService.getUserSecurityAgreementDetails(allOrgIds,assessmentPrograms, currentSchoolYear,includeInternalUsers);
		fillUserSecurityAgreementDetails(userSecurityAgreementDetails, excelRows, orgId);
		writeReport(moduleReportId, excelRows,typeName);
		return true;
	}

	private void fillUserSecurityAgreementDetails(List<UserSecurityAgreemntDTO> userSecurityAgreementDetails,
			List<String[]> excelRows, Long orgId) {
		
		if (userSecurityAgreementDetails != null && !userSecurityAgreementDetails.isEmpty()) {
			SimpleDateFormat dateFormat = getSimpleDateFormatter(EXTRACT_DATE_FORMAT_MM_DD_YYYY, orgId);
			for (UserSecurityAgreemntDTO userSecurityAgreementDetail : userSecurityAgreementDetails) {
				List<String> securityAgreementDetails = new ArrayList<String>();
				// This needs to be in the same order while writing to excel
				securityAgreementDetails.add(userSecurityAgreementDetail.getState());
				securityAgreementDetails.add(userSecurityAgreementDetail.getDistrict());
				securityAgreementDetails.add(userSecurityAgreementDetail.getBuilding());
				securityAgreementDetails.add(userSecurityAgreementDetail.getLastName());
				securityAgreementDetails.add(userSecurityAgreementDetail.getFirstName());
				securityAgreementDetails.add(userSecurityAgreementDetail.getMiddleName());
				securityAgreementDetails.add(userSecurityAgreementDetail.getSecurityAgreementStatus());
				securityAgreementDetails.add(userSecurityAgreementDetail.getSecurityAgreementSignatureStatus());
				Date signedDate = userSecurityAgreementDetail.getSecurityAgreementSignedOn();
				String formattedSignedDate = "";
				if(signedDate != null){
					formattedSignedDate =dateFormat.format(signedDate);							
				}
				securityAgreementDetails.add(" " + formattedSignedDate);
				
				excelRows.add(securityAgreementDetails.toArray(new String[securityAgreementDetails.size()]));				
			}
		}
	
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HashMap<String, Object> getDlmTestAdminMonitoringSummary(
			String summaryLevel, Organization state, List<Long> districtIds, List<Long> schoolIds, User user) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		boolean isIEState = organizationService.isIEModelState(state);
		
		List<DLMTestAdminMonitoringSummaryDTO> testAdminSummaryDtos = new ArrayList<DLMTestAdminMonitoringSummaryDTO>();
		if (summaryLevel == null) {
			return resultMap;
		}
		
		Long schoolYear = state.getCurrentSchoolYear();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		Long userId = user.getId();
		
		List<Long> orgIds = new ArrayList<Long>();
		if ("state".equals(summaryLevel)) {
			// orgIds doesn't need anything, the query will take care of it using the state id
		} else if ("district".equals(summaryLevel)) {
			orgIds = districtIds;
		} else if ("school".equals(summaryLevel)) {
			orgIds = schoolIds;
		} else {
			return resultMap;
		}
		List<DLMTestStatusExtractDTO> statusDtos=null;
		if(isIEState == false) {
			//F1004 : Year-end model states
			statusDtos = testcollectionDao.dlmTestAdminMonitoringSummary(summaryLevel, schoolYear,
					state.getId(), orgIds, shouldOnlySeeRosteredStudents, userId);
			populateDlmTestExtractInfoIntoCorrespondingSummaryDtos(summaryLevel, testAdminSummaryDtos, statusDtos, isIEState, null, orgIds);
		}else {
			//F1004 :  Instructionally Embedded model
			OperationalTestWindowDTO otw = operationalTestWindowService.getOpenInstructionAssessmentPlannerWindow(user.getCurrentAssessmentProgramId(), user.getContractingOrganization().getId(), null, null);
			List<TestingCycle> testCycleWindows = testingCycleMapper.getTestingCyclesByStateIdSchoolYearAssessmentProgram(user.getCurrentAssessmentProgramId(),schoolYear,state.getId());
			if(CollectionUtils.isNotEmpty(testCycleWindows)) {
				ArrayList<Long> testingCycleID = new ArrayList<Long>();
				//Defaulting the window name to Fall and Spring
				resultMap.put("testingCycleName1","Fall");
				resultMap.put("testingCycleName2","Spring");
				List<TestingCycle> activeTestCycleWindows = new ArrayList<TestingCycle>();		
				for(int cycleNo=0 ; cycleNo< testCycleWindows.size(); cycleNo++) {
					testingCycleID.add(testCycleWindows.get(cycleNo).getId());
					resultMap.put("testingCycleName"+(cycleNo+1),testCycleWindows.get(cycleNo).getTestingCycleName());
					activeTestCycleWindows.add(testCycleWindows.get(cycleNo));
					if(cycleNo==1 || otw.getOtwId().equals(testCycleWindows.get(cycleNo).getOperationalTestWindowId())) {
						//we just want 2 testing cycle Fall && Spring
						//Or the operational test Window ID == current otwID
						break;
					}
				}
				
				statusDtos = testcollectionDao.dlmTestAdminMonitoringSummaryForIEStates(summaryLevel, schoolYear,
						state.getId(), orgIds, shouldOnlySeeRosteredStudents, userId, testingCycleID);
				populateDlmTestExtractInfoIntoCorrespondingSummaryDtos(summaryLevel, testAdminSummaryDtos, statusDtos, isIEState, activeTestCycleWindows, orgIds);
			}
		} 
		
		
		Comparator<DLMTestAdminMonitoringSummaryDTO> comparator = null;
		if (summaryLevel.equals("state")) {
			comparator = stateDlmTestAdminSummaryComparator;
		} else if (summaryLevel.equals("district")) {
			comparator = districtDlmTestAdminSummaryComparator;
		} else if (summaryLevel.equals("school")) {
			comparator = schoolDlmTestAdminSummaryComparator;
		}
		
		if (comparator != null) {
			Collections.sort(testAdminSummaryDtos, comparator);
			resultMap.put("resultData",testAdminSummaryDtos);
			resultMap.put("isIEState",isIEState);
			resultMap.put("summaryLevel",summaryLevel);
		}
		
		return resultMap;
	}
	
	private void populateDlmTestExtractInfoIntoCorrespondingSummaryDtos(String summaryLevel, List<DLMTestAdminMonitoringSummaryDTO> dtos,
			List<DLMTestStatusExtractDTO> toAdd, Boolean isIEState, List<TestingCycle> testCycleWindows, List<Long> orgIds) {
		
		//creating HashSet of OrgID
		Set<Long> organizationIDWithoutResult = new HashSet(orgIds);
		
		for (DLMTestStatusExtractDTO dtoToAdd : toAdd) {
			DLMTestAdminMonitoringSummaryDTO matchingSummaryDto = null;
			for (DLMTestAdminMonitoringSummaryDTO dto : dtos) {
				if (matchDLMTestDTOs(summaryLevel, dto, dtoToAdd)) {
					matchingSummaryDto = dto;
					addDLMTestDTO(dto, dtoToAdd, isIEState, testCycleWindows);
					break;
				}
			}
			if (matchingSummaryDto == null) {
				matchingSummaryDto = new DLMTestAdminMonitoringSummaryDTO();
				addDLMTestDTO(matchingSummaryDto, dtoToAdd, isIEState, testCycleWindows);
				matchingSummaryDto.setDistrictId(dtoToAdd.getDistrictId());
				matchingSummaryDto.setDistrictIdentifier(dtoToAdd.getDistrictIdentifier());
				matchingSummaryDto.setDistrictName(dtoToAdd.getDistrictName());
				matchingSummaryDto.setSchoolId(dtoToAdd.getAttendanceSchoolId());
				matchingSummaryDto.setSchoolIdentifier(dtoToAdd.getSchoolIdentifier());
				matchingSummaryDto.setSchoolName(dtoToAdd.getSchoolName());
				matchingSummaryDto.setSubjectId(dtoToAdd.getSubjectId());
				matchingSummaryDto.setSubject(dtoToAdd.getSubjectName());
				matchingSummaryDto.setGradeId(dtoToAdd.getGradeId());
				matchingSummaryDto.setGrade(dtoToAdd.getGradeLevel() == null ? "" : dtoToAdd.getGradeLevel());
				dtos.add(matchingSummaryDto);
				if ("district".equals(summaryLevel)) {
					organizationIDWithoutResult.remove(dtoToAdd.getDistrictId());
				}else if ("school".equals(summaryLevel)) {
					organizationIDWithoutResult.remove(dtoToAdd.getAttendanceSchoolId());
				}
			}
		}
		
		//add dummy data for empty result search to display empty table in UI
		if(!"state".equals(summaryLevel) && organizationIDWithoutResult.size()>0) {
			addDummyOrganizationDataToDlmTestExtractInfo(organizationIDWithoutResult,dtos,summaryLevel);
		}
		
	}
	
	private void addDummyOrganizationDataToDlmTestExtractInfo(Set<Long> organizationIDWithoutResult, List<DLMTestAdminMonitoringSummaryDTO> dtos, String summaryLevel) {
		for(Long curOrgID : organizationIDWithoutResult) {
			Organization curOrganization = null;
			if ("school".equals(summaryLevel)){
				curOrganization = organizationDao.getParentOrgDetailsById(curOrgID);
			}else {
				curOrganization= organizationDao.get(curOrgID);
			}	
			if(curOrganization!=null) {
				DLMTestAdminMonitoringSummaryDTO newDLMTestAdminMonitoringSummaryDTO = new DLMTestAdminMonitoringSummaryDTO();
				newDLMTestAdminMonitoringSummaryDTO.setDistrictId(curOrgID);
				newDLMTestAdminMonitoringSummaryDTO.setDistrictName(curOrganization.getOrganizationName());
				dtos.add(newDLMTestAdminMonitoringSummaryDTO);
			}
		}
	}
	
	private boolean matchDLMTestDTOs(String summaryLevel, DLMTestAdminMonitoringSummaryDTO summaryDto, DLMTestStatusExtractDTO extractDto) {
		boolean matches = false;
		if (summaryLevel.equals("state")) {
			matches = summaryDto.getGradeId().longValue() == extractDto.getGradeId().longValue() &&
					summaryDto.getSubjectId().longValue() == extractDto.getSubjectId().longValue();
		} else if (summaryLevel.equals("district")) {
			matches = summaryDto.getGradeId().longValue() == extractDto.getGradeId().longValue() &&
					summaryDto.getSubjectId().longValue() == extractDto.getSubjectId().longValue() &&
					summaryDto.getDistrictId().longValue() == extractDto.getDistrictId().longValue();
		} else if (summaryLevel.equals("school")) {
			matches = summaryDto.getGradeId().longValue() == extractDto.getGradeId().longValue() &&
					summaryDto.getSubjectId().longValue() == extractDto.getSubjectId().longValue() &&
					summaryDto.getDistrictId().longValue() == extractDto.getDistrictId().longValue() &&
					summaryDto.getSchoolId().longValue() == extractDto.getAttendanceSchoolId().longValue();
		}
		return matches;
	}
	
	private void addDLMTestDTO(DLMTestAdminMonitoringSummaryDTO summaryDto, DLMTestStatusExtractDTO extractDto, Boolean isIEState, List<TestingCycle> testCycleWindows) {
		Long studentId = extractDto.getStudentId();
		
		boolean itiNoPlansCreated =
				extractDto.isItiContentAvailable() &&
				extractDto.getInstTestsNotStarted() == 0 &&
				extractDto.getInstTestsInProgress() == 0 &&
				extractDto.getInstTestscompleted() == 0;
		boolean itiNoneTaken =
				extractDto.getInstTestsNotStarted() > 0 &&
				extractDto.getInstTestscompleted() == 0;
		boolean itiOneCompleted =
				extractDto.getInstTestscompleted() == 1;
		boolean itiMoreThanOneCompleted =
				extractDto.getInstTestscompleted() > 1;
		
		
		
		boolean eoyNoneTaken =
				extractDto.getEoyTestsNotStarted() > 0 &&
				extractDto.getEoyTestsInProgress() == 0 &&
				extractDto.getEoytTestscompleted() == 0;
		boolean eoyInProgress = 
				extractDto.getEoyTestsInProgress() > 0 ||
				(
					extractDto.getEoytTestscompleted() > 0 &&
					// commented because of Wisconsin Social Studies. This would be 0 and so the value would always show 0.
					//extractDto.getNumofEEs() > 0 &&
					extractDto.getEoytTestscompleted() < extractDto.getNumofEEs()
				);
		boolean eoyAllComplete = 
				extractDto.getEoytTestscompleted() > 0 &&
				// commented because of Wisconsin Social Studies. This would be 0 and so the value would always show 0.
				//extractDto.getNumofEEs() > 0 &&
				extractDto.getEoytTestscompleted() >= extractDto.getNumofEEs();
		Boolean shouldAddEOY = false;
		Boolean shouldAddInsFirstTestingCycle = false;
		
		if(isIEState==false) {
			shouldAddInsFirstTestingCycle=true;
			//Add End of year
			shouldAddEOY=true;
			
		}else {
			//IE States verify the testing cycle name before adding 
			if(!testCycleWindows.isEmpty() && StringUtils.equals(extractDto.getTestingCycleName(), testCycleWindows.get(0).getTestingCycleName())) {
				shouldAddInsFirstTestingCycle=true;
				//Add End of year.. Only want to add once
				shouldAddEOY=true;
				
			}else if (testCycleWindows.size()>1 ) {
				//Two Testing cycle present
				if(StringUtils.equals(extractDto.getTestingCycleName(), testCycleWindows.get(1).getTestingCycleName())  || StringUtils.isEmpty(extractDto.getTestingCycleName()))
				{
					if (itiNoPlansCreated) {
						summaryDto.getStudentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow().add(studentId);
					} else if (itiNoneTaken) {
						summaryDto.getStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow().add(studentId);
					} else if (itiOneCompleted) {
						summaryDto.getStudentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow().add(studentId);
					} else if (itiMoreThanOneCompleted) {
						summaryDto.getStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow().add(studentId);
					}
				}else {
					LOGGER.info("The testing cylce name is mismatching please verify the DLMTestAdminMonitoring query "+extractDto.getTestingCycleName());
				}
			}
			if(StringUtils.isEmpty(extractDto.getTestingCycleName())) {
				//in case the testing cycle name is not present we will add it to both cycle
				shouldAddInsFirstTestingCycle=true;
			}
		}
		if(shouldAddInsFirstTestingCycle) {
			if (itiNoPlansCreated) {
				summaryDto.getStudentsWithInstructionallyEmbeddedNoPlansCreated().add(studentId);
			} else if (itiNoneTaken) {
				summaryDto.getStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken().add(studentId);
			} else if (itiOneCompleted) {
				summaryDto.getStudentsWithInstructionallyEmbeddedOnlyOneTestletCompleted().add(studentId);
			} else if (itiMoreThanOneCompleted) {
				summaryDto.getStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted().add(studentId);
			}
		}
		if(shouldAddEOY) {
			if (eoyNoneTaken) {
				summaryDto.getStudentsWithYearEndNoTestletsTaken().add(studentId);
			}
			
			if (eoyAllComplete) {
				summaryDto.getStudentsWithYearEndAllRequiredTestletsCompleted().add(studentId);
			} else if (eoyInProgress) {
				summaryDto.getStudentsWithYearEndTestingInProgress().add(studentId);
			}
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startMonitorScoringExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException  {
		
		String[] columnHeaders = {
			"State", "District", "School",
			"School Identifier", "Grade", "Roster Name",
			"Educator Identifier", "Educator Last Name", "Educator First Name",
			"Student Last Name", "Student First Name", "Student Middle Name","State Student Identifier","Subject",
			"Scoring Assignment Name","Test Collection Name","Stage",
			"Scoring Status","Qty To Score","Scored","To Be Scored"			
		};
		
		Long currentSchoolYear = (long) ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		List<Long> assessmentPrograms = (List)additionalParams.get("assessmentProgramIds");
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		
		int offset = 0, foundCount = 0, limit = 7500;
		List<MonitorScoringExtractDTO> dtos = null;
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		while (offset == 0 ||  foundCount == limit) {
			 dtos = dataExtractService.getMonitorScoringExtractByOrg(orgId, assessmentPrograms,currentSchoolYear,offset,limit);
			 offset = offset + limit;
			 foundCount = dtos.size(); 
			for (MonitorScoringExtractDTO dto : dtos) {
				if(dto.getEducatorFirstName() != null && dto.getEducatorFirstName().contains(",")){
					dto.setEducatorFirstName("\""+dto.getEducatorFirstName()+"\"");	
				}
				if(dto.getEducatorLastName() != null && dto.getEducatorLastName().contains(",")){
					dto.setEducatorLastName("\""+dto.getEducatorLastName()+"\"");	
				}
				if(dto.getStudentFirstName() != null && dto.getStudentFirstName().contains(",")){
					dto.setStudentFirstName("\""+dto.getStudentFirstName()+"\"");	
				}
				if(dto.getStudentLastName() != null && dto.getStudentLastName().contains(",")){
					dto.setStudentLastName("\""+dto.getStudentLastName()+"\"");	
				}
				if(dto.getStudentMiddleName() != null && dto.getStudentMiddleName().contains(",")){
					dto.setStudentMiddleName("\""+dto.getStudentMiddleName()+"\"");	
				}
				if(dto.getEducatorIdentifier() != null && dto.getEducatorIdentifier().contains(",")){
					dto.setEducatorIdentifier("\""+dto.getEducatorIdentifier()+"\"");	
				}
				if(dto.getStateStudentIdentifier() != null && dto.getStateStudentIdentifier().contains(",")){
					dto.setStateStudentIdentifier("\""+dto.getStateStudentIdentifier()+"\"");	
				}
				lines.add(new String[]{
					dto.getState(),
					dto.getDistrict(),
					dto.getSchool(),
					dto.getSchoolIdentifier(),dto.getGrade(),dto.getRosterName(), dto.getEducatorIdentifier(),dto.getEducatorLastName(),
					dto.getEducatorFirstName(),dto.getStudentLastName(),dto.getStudentFirstName(),dto.getStudentMiddleName(),
					dto.getStateStudentIdentifier(),dto.getSubject(),dto.getScoringAssignmentName(),dto.getTestCollectionName(),
					dto.getStage(),dto.getScoringStatus(),dto.getQtyToScore(),dto.getScored(),String.valueOf(((new Integer(dto.getQtyToScore()))-(new Integer(dto.getScored()))))
				});
			}
			writeCSV(lines, moduleReport, typeName, foundCount != limit);
			LOGGER.info("Monitor Scoring Extract Processing record counter (ModuleReportId: "+moduleReport.getId()+"): " + offset +" "+ foundCount);
			lines.clear();
			dtos.clear();
		}
		
		Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
	 return true;
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMGeneralResearchFile(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		
		// TODO Auto-generated method stub
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		Long stateId = StringUtils.isNotBlank(additionalParams.get("stateId").toString())? Long.parseLong(additionalParams.get("stateId").toString()) :null;
		Long districtId = StringUtils.isNotBlank(additionalParams.get("districtId").toString())? Long.parseLong(additionalParams.get("districtId").toString()) :null;
		Long schoolId = StringUtils.isNotBlank(additionalParams.get("schoolId").toString())? Long.parseLong(additionalParams.get("schoolId").toString()) :null;
		Long subject = StringUtils.isNotBlank(additionalParams.get("subject").toString())? Long.parseLong(additionalParams.get("subject").toString()) :null;
		Long grade = StringUtils.isNotBlank(additionalParams.get("grade").toString())? Long.parseLong(additionalParams.get("grade").toString()) :null;
		
		User user = userDetails.getUser();
		Long contractingId = user.getContractingOrgId();
		
		if(stateId == null) stateId = moduleReport.getStateId();
		String stateCode=organizationDao.getStateCodeByStateId(stateId);
		List<String> columnHeadersList = new ArrayList<String>();		
		List<String[]> lines = new ArrayList<String[]>();
		
		if(!StringUtils.equalsIgnoreCase(stateCode,"AR")){			
			columnHeadersList.add("Unique_Row_Identifier");
			columnHeadersList.add("Kite_Student_Identifier");
			columnHeadersList.add("State_Student_Identifier");
			
			if(StringUtils.equalsIgnoreCase(stateCode,"NY")){
			columnHeadersList.add("Local_Student_Identifier");
			}
			columnHeadersList.add("Accountability_School_Identifier");
			columnHeadersList.add("Accountability_District_Identifier");
			columnHeadersList.add("Current_Grade_Level");
			
			if((StringUtils.equalsIgnoreCase(stateCode,"DE")) || (StringUtils.equalsIgnoreCase(stateCode,"DC"))){
			columnHeadersList.add("Course");
			}
			columnHeadersList.add("Student_Legal_First_Name");
			columnHeadersList.add("Student_Legal_Middle_Name");
			columnHeadersList.add("Student_Legal_Last_Name");
			columnHeadersList.add("Generation_Code");
			columnHeadersList.add("Username");
			columnHeadersList.add("First_Language");
			columnHeadersList.add("Date_of_Birth");

			if(StringUtils.equalsIgnoreCase(stateCode,"AR")){
			columnHeadersList.add("StateUse");
			}			
			columnHeadersList.add("Gender");
			columnHeadersList.add("Comprehensive_Race");
			columnHeadersList.add("Hispanic_Ethnicity");
			columnHeadersList.add("Primary_Disability_Code");
			columnHeadersList.add("ESOL_Participation_Code");
			columnHeadersList.add("School_Entry_Date");		
			columnHeadersList.add("District_Entry_Date");
			columnHeadersList.add("State_Entry_Date");
			columnHeadersList.add("Attendance_School_Program_Identifier");
			columnHeadersList.add("State");			
			columnHeadersList.add("District_Code");
			columnHeadersList.add("District");
			columnHeadersList.add("School_Code");
			columnHeadersList.add("School");
			
		} else if (StringUtils.equalsIgnoreCase(stateCode,"AR")) {
			columnHeadersList.add("FirstName");
			columnHeadersList.add("MiddleName");
			columnHeadersList.add("LastName");
			columnHeadersList.add("StateUse");
			columnHeadersList.add("DOB");
			columnHeadersList.add("State_Student_Identifier");
			columnHeadersList.add("Local_Student_Identifier");			
			columnHeadersList.add("District_Code");
			columnHeadersList.add("District");
			columnHeadersList.add("School_Code");
			columnHeadersList.add("School");			
			columnHeadersList.add("Unique_Row_Identifier");
			columnHeadersList.add("Kite_Student_Identifier");			
			columnHeadersList.add("Accountability_School_Identifier");
			columnHeadersList.add("Accountability_District_Identifier");
			columnHeadersList.add("Current_Grade_Level");
			columnHeadersList.add("Generation_Code");
			columnHeadersList.add("Username");
			columnHeadersList.add("First_Language");			
			columnHeadersList.add("Gender");
			columnHeadersList.add("Comprehensive_Race");
			columnHeadersList.add("Hispanic_Ethnicity");
			columnHeadersList.add("Primary_Disability_Code");
			columnHeadersList.add("ESOL_Participation_Code");
			columnHeadersList.add("School_Entry_Date");		
			columnHeadersList.add("District_Entry_Date");
			columnHeadersList.add("State_Entry_Date");
			columnHeadersList.add("Attendance_School_Program_Identifier");
			columnHeadersList.add("State");			
		}
		
		columnHeadersList.add("Educator_First_Name");
		columnHeadersList.add("Educator_Last_Name");
		columnHeadersList.add("Educator_Username");
		columnHeadersList.add("State_Educator_Identifier");
		columnHeadersList.add("Kite_Educator_Identifier");
		if(StringUtils.equalsIgnoreCase(stateCode,"IA")){
		columnHeadersList.add("Exit_Withdrawal_Date");
		columnHeadersList.add("Exit_Withdrawal_Code");
		}
		columnHeadersList.add("Subject");
		columnHeadersList.add("Final_Band");
		columnHeadersList.add("TBD_Growth");
		columnHeadersList.add("Performance_Level");
		
		if(StringUtils.equalsIgnoreCase(stateCode,"NY")){
		columnHeadersList.add("NY_Performance_Level");
		}
		
		columnHeadersList.add("Invalidation_Code");
		
		if(StringUtils.equalsIgnoreCase(stateCode,"IA")||StringUtils.equalsIgnoreCase(stateCode,"NY")){
		columnHeadersList.add("Total_Linkage_Levels_Mastered");
		if(StringUtils.equalsIgnoreCase(stateCode,"IA")){
		columnHeadersList.add("IOWA_Linkage_Levels_Mastered");
		}
		}
		
		columnHeadersList.add("EE_1");
		columnHeadersList.add("EE_2");
		columnHeadersList.add("EE_3");
		columnHeadersList.add("EE_4");
		columnHeadersList.add("EE_5");
		columnHeadersList.add("EE_6");
		columnHeadersList.add("EE_7");
		columnHeadersList.add("EE_8");
		columnHeadersList.add("EE_9");
		columnHeadersList.add("EE_10");
		columnHeadersList.add("EE_11");
		columnHeadersList.add("EE_12");
		columnHeadersList.add("EE_13");
		columnHeadersList.add("EE_14");
		columnHeadersList.add("EE_15");
		columnHeadersList.add("EE_16");
		columnHeadersList.add("EE_17");
		columnHeadersList.add("EE_18");
		columnHeadersList.add("EE_19");
		columnHeadersList.add("EE_20");
		columnHeadersList.add("EE_21");
		columnHeadersList.add("EE_22");
		columnHeadersList.add("EE_23");
		columnHeadersList.add("EE_24");
		columnHeadersList.add("EE_25");
		columnHeadersList.add("EE_26");

		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]); 
					List<Object[]> excelRows = new ArrayList<Object[]>();
			excelRows.add(columnHeaders);
			lines.add(columnHeaders);
			int year = organizationService.get(stateId).getReportYear();
			
			if(moduleReport != null){
				int offset = 0, foundCount = 0, limit = 5000;
				List<UploadGrfFile> dlmGeneralResearchDTOs =null;
				while (offset == 0 ||  foundCount == limit) {				
					dlmGeneralResearchDTOs = dataExtractService.getDLMGeneralResearchExtract(stateId,districtId,schoolId,subject,grade,year, offset, limit);
					foundCount = dlmGeneralResearchDTOs.size();
					offset = offset + limit;
					if(CollectionUtils.isNotEmpty(dlmGeneralResearchDTOs)){						
						if(StringUtils.equalsIgnoreCase(stateCode,"AR")) {
							fillCsvDLMGeneralResearchDetails(dlmGeneralResearchDTOs,lines,stateCode);
						}else {
							fillDLMGeneralResearchDetails(dlmGeneralResearchDTOs,excelRows,stateCode);
						}
					}
					dlmGeneralResearchDTOs.clear();
				}
				GrfStateApproveAudit upload = uploadResultFileMapper.getOriginalGRFUploadAudit(stateId, year);
				if(StringUtils.equalsIgnoreCase(stateCode,"AR")) {
					writeCSVForArkansasGrf(lines, moduleReport, typeName, true, year);
					lines.clear();
				}else {
					writeExcelDlm(excelRows, moduleReport, typeName, upload==null?null:upload.getCreatedDate(),year);
					excelRows.clear();
				}
			}			
			Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			updateReportStatus(moduleReport, completedStatusid);	
		return true;
	}
	
	private void writeCSVForArkansasGrf(List<String[]> lines, ModuleReport moduleReport, String typeName, boolean uploadAndDeleteTempFileAfterWrite, int year) throws IOException {
		CSVWriter csvWriter = null;
		String fileName;
		boolean fileExists = false;
				
		if(moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
			fileName = moduleReport.getFileName();
			fileExists = true;
		} else {
			fileName = getFileName(moduleReport);
        	fileName=year+"_"+fileName;
        	fileName=fileName.replaceAll("_Uploaded", "");
		}
		try {
			String folderPath = getFolderPath(moduleReport, typeName);
			String csvFile = fileExists ? fileName : FileUtil.buildFilePath(folderPath, fileName);
			
			//create a local temp file
			File tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + FileUtil.getFileNameFromPath(csvFile));
			
			// Changed to Make KELPA State Return file Pipe deliminated.
			
				csvWriter = new CSVWriter(new FileWriter(tempFile, true), '|', CSVWriter.NO_QUOTE_CHARACTER);
				
			csvWriter.writeAll(lines);
			csvWriter.flush();
			
			if(!fileExists) {
				moduleReport.setFileName(csvFile);
				updateModuleReport(moduleReport);
			}
			
			if (uploadAndDeleteTempFileAfterWrite) {
				//send the local temp file to s3
				s3.synchMultipartUpload(csvFile, tempFile);
				//delete the local temp file
				FileUtils.deleteQuietly(tempFile);
			}
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
			throw ex;
		} finally {
			if(csvWriter != null) {
				csvWriter.close();
			}
		}
	}
	
	private void fillCsvDLMGeneralResearchDetails(
			List<UploadGrfFile> dlmGeneralResearchDTOs,
			List<String[]> lines,String stateCode) {
		if(dlmGeneralResearchDTOs != null && !dlmGeneralResearchDTOs.isEmpty()){
			for(UploadGrfFile dlmGeneralResearchDTO : dlmGeneralResearchDTOs){
				List<String> dlmGeneralResearchList = new ArrayList<String>();

				
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalFirstName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalMiddleName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalLastName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getStateUse()));			
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDateOfBirth()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getStateStudentIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLocalStudentIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getResidenceDistrictIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getDistrictName())); // mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolName()));	// mandatory			
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getExternalUniqueRowIdentifier()== null ? "":String.valueOf(dlmGeneralResearchDTO.getExternalUniqueRowIdentifier()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getStudentId()== null ? "":String.valueOf(dlmGeneralResearchDTO.getStudentId()));			
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAypSchoolIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAccountabilityDistrictIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getCurrentGradelevel()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGenerationCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getUserName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getFirstLanguage()));		
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGender()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getComprehensiveRace()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getHispanicEthnicity()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getPrimaryDisabilityCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEsolParticipationCode()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getSchoolEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDistrictEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getStateEntryDate()));					
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAttendanceSchoolProgramIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getState()));			
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorFirstName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorLastName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorUserName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getKiteEducatorIdentifier()));  // mandatory
					if(!StringUtils.isEmpty(dlmGeneralResearchDTO.getSubject()) && dlmGeneralResearchDTO.getSubject().equals("M")){
						dlmGeneralResearchList.add("Math");
					}else {
						dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSubject()));	// mandatory
					}
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getFinalBand()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSgp()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getPerformanceLevel()== null ? "":String.valueOf(dlmGeneralResearchDTO.getPerformanceLevel()));	// mandatory
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getInvalidationCode()== null ? "":String.valueOf(dlmGeneralResearchDTO.getInvalidationCode()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe1()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe1()));				
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe2()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe2()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe3()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe3()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe4()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe4()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe5()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe5()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe6()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe6()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe7()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe7()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe8()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe8()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe9()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe9()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe10()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe10()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe11()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe11()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe12()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe12()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe13()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe13()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe14()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe14()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe15()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe15()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe16()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe16()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe17()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe17()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe18()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe18()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe19()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe19()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe20()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe20()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe21()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe21()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe22()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe22()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe23()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe23()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe24()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe24()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe25()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe25()));
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe26()== null ? "":String.valueOf(dlmGeneralResearchDTO.getEe26()));
				
					lines.add(dlmGeneralResearchList.toArray(new String[dlmGeneralResearchList.size()]));
			}			
		}	
		
	}
	
	private String checkEmpty(String value1){
		
		return StringUtils.isNotBlank(value1) ? value1 : " ";
	}
	private void fillDLMGeneralResearchDetails(
			List<UploadGrfFile> dlmGeneralResearchDTOs,
			List<Object[]> excelRows,String stateCode) {
		if(dlmGeneralResearchDTOs != null && !dlmGeneralResearchDTOs.isEmpty()){
			for(UploadGrfFile dlmGeneralResearchDTO : dlmGeneralResearchDTOs){
				List<Object> dlmGeneralResearchList = new ArrayList<Object>();

				if(!StringUtils.equalsIgnoreCase(stateCode,"AR")){		
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getExternalUniqueRowIdentifier()== null ? "":dlmGeneralResearchDTO.getExternalUniqueRowIdentifier());
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getStudentId()== null ? "":dlmGeneralResearchDTO.getStudentId());	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getStateStudentIdentifier()));
					
					if(StringUtils.equalsIgnoreCase(stateCode,"NY")){
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLocalStudentIdentifier()));
					}
					
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAypSchoolIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAccountabilityDistrictIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getCurrentGradelevel()));	// mandatory
	
					if((StringUtils.equalsIgnoreCase(stateCode,"DE")) || (StringUtils.equalsIgnoreCase(stateCode,"DC"))){
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getCourse()));
					}
					
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalFirstName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalMiddleName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalLastName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGenerationCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getUserName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getFirstLanguage()));				
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDateOfBirth()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGender()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getComprehensiveRace()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getHispanicEthnicity()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getPrimaryDisabilityCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEsolParticipationCode()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getSchoolEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDistrictEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getStateEntryDate()));					
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAttendanceSchoolProgramIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getState()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getResidenceDistrictIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getDistrictName())); // mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolName()));	// mandatory
									
				} else if (StringUtils.equalsIgnoreCase(stateCode,"AR")) {
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalFirstName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalMiddleName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLegalLastName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getStateUse()));			
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDateOfBirth()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getStateStudentIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getLocalStudentIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getResidenceDistrictIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getDistrictName())); // mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolIdentifier()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSchoolName()));	// mandatory			
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getExternalUniqueRowIdentifier()== null ? "":dlmGeneralResearchDTO.getExternalUniqueRowIdentifier());
					dlmGeneralResearchList.add(dlmGeneralResearchDTO.getStudentId()== null ? "":dlmGeneralResearchDTO.getStudentId());			
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAypSchoolIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAccountabilityDistrictIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getCurrentGradelevel()));	// mandatory
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGenerationCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getUserName()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getFirstLanguage()));		
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getGender()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getComprehensiveRace()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getHispanicEthnicity()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getPrimaryDisabilityCode()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEsolParticipationCode()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getSchoolEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getDistrictEntryDate()));
					dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getStateEntryDate()));					
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getAttendanceSchoolProgramIdentifier()));
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getState()));			
				}
			
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorFirstName()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorLastName()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorUserName()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getEducatorIdentifier()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getKiteEducatorIdentifier()));  // mandatory
				
				if(StringUtils.equalsIgnoreCase(stateCode,"IA")){
				dlmGeneralResearchList.add(getConvertDate(dlmGeneralResearchDTO.getExitWithdrawalDate()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getExitWithdrawalType()));
				}
				
				
				if(!StringUtils.isEmpty(dlmGeneralResearchDTO.getSubject()) && dlmGeneralResearchDTO.getSubject().equals("M")){
					dlmGeneralResearchList.add("Math");
				}else {
					dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSubject()));	// mandatory
				}
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getFinalBand()));
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getSgp()));
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getPerformanceLevel()== null ? "":dlmGeneralResearchDTO.getPerformanceLevel());	// mandatory
				
				if(StringUtils.equalsIgnoreCase(stateCode,"NY")){
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getNyPerformanceLevel()== null ? "":dlmGeneralResearchDTO.getNyPerformanceLevel());
				}
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getInvalidationCode()== null ? "":dlmGeneralResearchDTO.getInvalidationCode());
				if(StringUtils.equalsIgnoreCase(stateCode,"IA")||StringUtils.equalsIgnoreCase(stateCode,"NY")){
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getTotalLinkageLevelsMastered()));
				if(StringUtils.equalsIgnoreCase(stateCode,"IA")){
				dlmGeneralResearchList.add(checkEmpty(dlmGeneralResearchDTO.getIowaLinkageLevelsMastered()));
				}
				}
				
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe1()== null ? "":dlmGeneralResearchDTO.getEe1());				
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe2()== null ? "":dlmGeneralResearchDTO.getEe2());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe3()== null ? "":dlmGeneralResearchDTO.getEe3());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe4()== null ? "":dlmGeneralResearchDTO.getEe4());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe5()== null ? "":dlmGeneralResearchDTO.getEe5());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe6()== null ? "":dlmGeneralResearchDTO.getEe6());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe7()== null ? "":dlmGeneralResearchDTO.getEe7());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe8()== null ? "":dlmGeneralResearchDTO.getEe8());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe9()== null ? "":dlmGeneralResearchDTO.getEe9());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe10()== null ? "":dlmGeneralResearchDTO.getEe10());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe11()== null ? "":dlmGeneralResearchDTO.getEe11());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe12()== null ? "":dlmGeneralResearchDTO.getEe12());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe13()== null ? "":dlmGeneralResearchDTO.getEe13());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe14()== null ? "":dlmGeneralResearchDTO.getEe14());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe15()== null ? "":dlmGeneralResearchDTO.getEe15());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe16()== null ? "":dlmGeneralResearchDTO.getEe16());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe17()== null ? "":dlmGeneralResearchDTO.getEe17());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe18()== null ? "":dlmGeneralResearchDTO.getEe18());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe19()== null ? "":dlmGeneralResearchDTO.getEe19());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe20()== null ? "":dlmGeneralResearchDTO.getEe20());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe21()== null ? "":dlmGeneralResearchDTO.getEe21());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe22()== null ? "":dlmGeneralResearchDTO.getEe22());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe23()== null ? "":dlmGeneralResearchDTO.getEe23());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe24()== null ? "":dlmGeneralResearchDTO.getEe24());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe25()== null ? "":dlmGeneralResearchDTO.getEe25());
				dlmGeneralResearchList.add(dlmGeneralResearchDTO.getEe26()== null ? "":dlmGeneralResearchDTO.getEe26());
				
				excelRows.add(dlmGeneralResearchList.toArray(new Object[dlmGeneralResearchList.size()]));
			}			
		}	
		
	}
	
	
	public String getConvertDate(Date dateOfBirth){
		if(dateOfBirth != null){
		SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");
		java.sql.Date dobDate = new java.sql.Date(dateOfBirth.getTime());
		
		return simpleFormat.format(dobDate);
		}
		return " ";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMIncidentExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		// TODO Auto-generated method stub
		String[] columnHeaders = {"Studentid","State_Student_Identifier","State","Student_Legal_First_Name","Student_Legal_Middle_Name",
				"Student_Legal_Last_Name","Generation_Code","Date_of_Birth","Essential_Element","Issue_Code"};
		List<Object[]> excelRows = new ArrayList<Object[]>();
		excelRows.add(columnHeaders);
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		User user = userDetails.getUser();
		int year = organizationService.get(orgId).getReportYear();
		
		Date createdDate = null;
		if(moduleReport != null){
			int offset = 0, foundCount = 0, limit = 5000;
			while (offset == 0 ||  foundCount == limit) {				
				List<UploadIncidentFile> dlmIncidentDTOs = dataExtractService.getDLMIncidentExtract(orgId, year, offset, limit);
				if(dlmIncidentDTOs.size()>0){
					fillDLMIncidentDetails(dlmIncidentDTOs,excelRows);
					if(offset == 0)
	                     createdDate = dlmIncidentDTOs.get(0).getCreatedDate();
			    }
				foundCount = dlmIncidentDTOs.size();
				offset = offset + limit;
			}
			writeExcel(excelRows, moduleReport, typeName,createdDate);
			excelRows.clear();
		}	
		
		Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
		return true;
	}
	
	
	private void fillDLMIncidentDetails(List<UploadIncidentFile> dlmIncidentDTOs,
			List<Object[]> excelRows)  {
		if(dlmIncidentDTOs != null && !dlmIncidentDTOs.isEmpty()){
			for(UploadIncidentFile dlmIncidentDTO : dlmIncidentDTOs){
				List<Object> dlmIncidentList = new ArrayList<Object>();
				dlmIncidentList.add(dlmIncidentDTO.getStudentId()== null ? "":dlmIncidentDTO.getStudentId());
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getStateStudentIdentifier()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getState()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getLegalFirstName()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getLegalMiddleName()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getLegalLastName()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getGenerationCode()));
				
				dlmIncidentList.add(getConvertDate(dlmIncidentDTO.getDateOfBirth()));
				
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getEssentialElement()));
				dlmIncidentList.add(checkEmpty(dlmIncidentDTO.getIssueCode()));
				
				excelRows.add(dlmIncidentList.toArray(new Object[dlmIncidentList.size()]));
			}			
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startDLMSpecialCircumstanceExtract(
			UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		String stateCode = organizationDao.getStateCodeByStateId(orgId);
		List<String> columnHeadersList = new ArrayList<String>();
		columnHeadersList.add("Kite_Student_Identifier");
		columnHeadersList.add("State_Student_Identifier");
		columnHeadersList.add("Student_Legal_First_Name");
		columnHeadersList.add("Student_Middle_Name");
		columnHeadersList.add("Student_Legal_Last_Name");
		columnHeadersList.add("Generation_Code");
		columnHeadersList.add("Date_of_Birth");
		columnHeadersList.add("Special_Circumstance_Code");
		if (StringUtils.equalsIgnoreCase(stateCode, "KS")) {
			columnHeadersList.add("KSDE_SC_Code");
		}
		columnHeadersList.add("Special_Circumstance_Label");
		if (!StringUtils.equalsIgnoreCase(stateCode, "KS")) {
			columnHeadersList.add("Essential_Element");
		}
		if (StringUtils.equalsIgnoreCase(stateCode, "KS")) {
			columnHeadersList.add("Assessment");
		}
		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]);
		List<Object[]> excelRows = new ArrayList<Object[]>();
		excelRows.add(columnHeaders);
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);

		User user = userDetails.getUser();
		int year = organizationService.get(orgId).getReportYear();

		Map<String, String> student08ScCodes = new HashMap<String, String>();
		Map<String, String> student39ScCodes = new HashMap<String, String>();
		Map<String, Date> studentFirstAppliedScCodes = new HashMap<String, Date>();
		List<UploadScCodeFile> allSpecialCircumstanceDTOs = new ArrayList<UploadScCodeFile>();
		Date generatedDate = new Date();
		if (moduleReport != null) {
			List<UploadScCodeFile> dlmSpecialCircumstanceDTOs = dataExtractService.getDLMSpecialCircumstanceExtract(orgId, year, stateCode);

			BatchUpload batchUpload = batchUploadService.latestGrfBatchUpload(orgId, scCodeGRFProcessType);
			if (batchUpload != null) {
				generatedDate = batchUpload.getCreatedDate();
			}
			for (UploadScCodeFile dlmSpecialCircumstance : dlmSpecialCircumstanceDTOs) {
				String key = dlmSpecialCircumstance.getStudentId() + "-"
						+ dlmSpecialCircumstance.getAssessment().replaceAll(" ", "-");
				if (dlmSpecialCircumstance.getKsdeScCode() != null) {
					if (dlmSpecialCircumstance.getKsdeScCode().equals("SC-08")
							&& StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						student08ScCodes.put(key, dlmSpecialCircumstance.getKsdeScCode());
					} else if (dlmSpecialCircumstance.getKsdeScCode().equals("SC-39")
							&& StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						student39ScCodes.put(key, dlmSpecialCircumstance.getKsdeScCode());
					} else if (!dlmSpecialCircumstance.getKsdeScCode().equals("SC-08")
							&& !dlmSpecialCircumstance.getKsdeScCode().equals("SC-39")
							&& StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						if (dlmSpecialCircumstance.getScCodeCreatedDate() != null
								&& studentFirstAppliedScCodes.containsKey(key)
								&& dlmSpecialCircumstance.getScCodeCreatedDate().getTime() < studentFirstAppliedScCodes
										.get(key).getTime()) {
							studentFirstAppliedScCodes.put(key, dlmSpecialCircumstance.getScCodeCreatedDate());
						} else {
							if (!studentFirstAppliedScCodes.containsKey(key)) {
								studentFirstAppliedScCodes.put(key, dlmSpecialCircumstance.getScCodeCreatedDate());
							}
						}
					}
				}
			}
			allSpecialCircumstanceDTOs.addAll(dlmSpecialCircumstanceDTOs);

			for (UploadScCodeFile dlmSpecialCircumstance : allSpecialCircumstanceDTOs) {
				String key = dlmSpecialCircumstance.getStudentId() + "-"
						+ dlmSpecialCircumstance.getAssessment().replaceAll(" ", "-");
				if (StringUtils.equalsIgnoreCase(stateCode, "KS")) {
					if (dlmSpecialCircumstance.getKsdeScCode().equals("SC-08") && student08ScCodes.containsKey(key)) {
						dlmSpecialCircumstance.setIncludeInExtract(true);
					} else if (!student08ScCodes.containsKey(key)
							&& (dlmSpecialCircumstance.getKsdeScCode().equals("SC-39")
									&& student39ScCodes.containsKey(key))) {
						dlmSpecialCircumstance.setIncludeInExtract(true);
					} else if (!student08ScCodes.containsKey(key) && !student39ScCodes.containsKey(key)
							&& !studentFirstAppliedScCodes.isEmpty()
							&& dlmSpecialCircumstance.getScCodeCreatedDate() != null && dlmSpecialCircumstance
									.getScCodeCreatedDate().equals(studentFirstAppliedScCodes.get(key))) {
						dlmSpecialCircumstance.setIncludeInExtract(true);
					} else {
						dlmSpecialCircumstance.setIncludeInExtract(false);
					}
				} else {
					// for non Kansas states
					dlmSpecialCircumstance.setIncludeInExtract(true);
				}

			}

			fillDLMSpecialCircumstanceDetails(allSpecialCircumstanceDTOs, excelRows, stateCode);
			int count = 0;
			if(!excelRows.isEmpty()){
				count = excelRows.size() - 1;
			}
			String fileName = writeExcelDlm(excelRows, moduleReport, typeName, generatedDate,year);
			allSpecialCircumstanceDTOs.clear();
			dlmSpecialCircumstanceDTOs.clear();
			excelRows.clear();
			updateBatchFileName(fileName, batchUpload.getId(), count);
		}
		Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
		return true;
	}

	
	private void fillDLMSpecialCircumstanceDetails(List<UploadScCodeFile> dlmSpecialCircumstanceDTOs,
			List<Object[]> excelRows, String stateCode) {
		if (dlmSpecialCircumstanceDTOs != null && !dlmSpecialCircumstanceDTOs.isEmpty()) {

			for (UploadScCodeFile dlmSpecialCircumstance : dlmSpecialCircumstanceDTOs) {
				if(dlmSpecialCircumstance.isIncludeInExtract()) {
					List<Object> dlmSpecialCircumstanceList = new ArrayList<Object>();
					dlmSpecialCircumstanceList.add(
							dlmSpecialCircumstance.getStudentId() == null ? "" : dlmSpecialCircumstance.getStudentId());
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getStateStudentIdentifier()));
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getLegalFirstName()));
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getLegalMiddleName()));
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getLegalLastName()));
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getGenerationCode()));

					dlmSpecialCircumstanceList.add(getConvertDate(dlmSpecialCircumstance.getDateOfBirth()));
					dlmSpecialCircumstanceList.add(dlmSpecialCircumstance.getSpecialCircumstanceCode() == null ? ""
							: dlmSpecialCircumstance.getSpecialCircumstanceCode());
					if (StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getKsdeScCode()));
					}
					dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getSpecialCircumstanceLabel()));
					if (!StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getEssentialElement()));
					}
					if (StringUtils.equalsIgnoreCase(stateCode, "KS")) {
						dlmSpecialCircumstanceList.add(checkEmpty(dlmSpecialCircumstance.getAssessment()));
					}
					excelRows.add(dlmSpecialCircumstanceList.toArray(new Object[dlmSpecialCircumstanceList.size()]));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKELPATestAdministrationMonitoringExtract(UserDetailImpl userDetails, Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException {
		String[] columnHeaders = { "State", "District", "School", "School Identifier", "Grade", "Grouping 1", 
				"Grouping 2", "Educator ID", "Educator Last Name", "Educator First Name", "Student Last Name",
				"Student First Name", "Student Middle Name", "State Student Identifier", "Local Student Identifier",
				"Subject", "Test Session Name", "Special Circumstance", "Special Circumstance Status",
				"Last Reactivated DateTime", "Stage", "Test Status", "Test Start DateTime", "Test End DateTime", 
				"Test Total Items", "Test Omitted Items"};
		
		List<Integer> assessmentPrograms = (List<Integer>)additionalParams.get("assessmentProgramIds");
		Long assessmentProgramId = null;
		for(Integer assessmentProgram: assessmentPrograms){
			AssessmentProgram ap = dataExtractService.findByAssessmentProgramId(assessmentProgram);
			if(CommonConstants.ASSESSMENT_PROGRAM_KELPA2.equalsIgnoreCase(ap.getAbbreviatedname())){
				assessmentProgramId = ap.getId();
			}
		}
		
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(columnHeaders);
		
		Long currentSchoolYear = Long.valueOf(((Integer)additionalParams.get("currentSchoolYear")).intValue());
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		List<Organization> schools = dataExtractService.getAllChildrenWithParentByType(orgId, "SCH");
		
		if(assessmentProgramId != null && moduleReport!=null && schools != null && !schools.isEmpty()){
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);
			
			int index=0;
			for(int x = 0; x < schools.size(); x++){
				Organization school = schools.get(x);
				int offset = 0, foundCount = 0, limit = 5000;
				if(index % 10 == 0) {
					LOGGER.info("KELPA2 Extract Processing School Counter (ModuleReportId: "+moduleReport.getId()+"):" + index);
				}
				while (offset == 0 ||  foundCount == limit) {	
					List<KELPATestAdministrationDTO> kelpaTestAdminDataExtractDTOs = dataExtractService.getKELPATestAdministrationExtract(school.getId(), currentSchoolYear, assessmentProgramId, offset, limit);
					foundCount = kelpaTestAdminDataExtractDTOs.size();
					offset = offset + limit;
					
					LOGGER.info("KELPA2 Extract Processing record counter (ModuleReportId: "+moduleReport.getId()+"): " + offset +" "+ foundCount);
					fillKELPATestAdminDataExtractDeatils(kelpaTestAdminDataExtractDTOs, excelRows, dateFormat);		
					writeCSV(excelRows, moduleReport, typeName, x >= (schools.size() - 1));
					excelRows.clear();
				}	
				index++;
			}
			
			Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
			updateReportStatus(moduleReport, completedStatusid);
		}
		return true;
	}

	private void fillKELPATestAdminDataExtractDeatils(List<KELPATestAdministrationDTO> kelpaTestAdminDataExtractDTOs,
			List<String[]> excelRows, SimpleDateFormat dateFormat) {
		
		if (kelpaTestAdminDataExtractDTOs != null && !kelpaTestAdminDataExtractDTOs.isEmpty()) {
			for (KELPATestAdministrationDTO kelpaTestAdminDataExtractdetails : kelpaTestAdminDataExtractDTOs) {
				List<String> kelpaTestAdminExtractdetails = new ArrayList<String>();

				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getState());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getDistrict());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getSchool());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getSchoolIdentifier());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getGrade());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getGrouping1());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getGrouping2());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getEducatorIdentifier());
				kelpaTestAdminExtractdetails.add(getNameWithQuotes(kelpaTestAdminDataExtractdetails.getEducatorLastName()));
				kelpaTestAdminExtractdetails.add(getNameWithQuotes(kelpaTestAdminDataExtractdetails.getEducatorFirstName()));
				kelpaTestAdminExtractdetails.add(getNameWithQuotes(kelpaTestAdminDataExtractdetails.getStudentLastName()));
				kelpaTestAdminExtractdetails.add(getNameWithQuotes(kelpaTestAdminDataExtractdetails.getStudentFirstName()));
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getStudentMiddleName());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getStateStudentIdentifier());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getLocalStudentIdentifier());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getSubject());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getTestSessionName());

				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getSpecialCircumstance());
				kelpaTestAdminExtractdetails.add(getTestStatus(kelpaTestAdminDataExtractdetails.getSpecialCircumstanceStatus()));
				
				Date reactivatedDateFormat = kelpaTestAdminDataExtractdetails.getLastReactivatedDateTime();
				String formattedReactivatedDate = "";
				if(reactivatedDateFormat!=null){
					formattedReactivatedDate = dateFormat.format(reactivatedDateFormat);
				}
				kelpaTestAdminExtractdetails.add(" " + formattedReactivatedDate);
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getStage());
				kelpaTestAdminExtractdetails.add(getTestStatus(kelpaTestAdminDataExtractdetails.getTestStatus()));
				
				Date testStartDateFormat = kelpaTestAdminDataExtractdetails.getTestStartDateTime();
				String formattedStartDate = "";
				if(testStartDateFormat!=null){
					formattedStartDate = dateFormat.format(testStartDateFormat);
				}
				kelpaTestAdminExtractdetails.add(" " + formattedStartDate);
				
				Date testEndDateFormat = kelpaTestAdminDataExtractdetails.getTestEndDateTime();
				String formattedEndDate = "";
				if(testEndDateFormat!=null){
					formattedEndDate = dateFormat.format(testEndDateFormat);
				}
				
				kelpaTestAdminExtractdetails.add(" " + formattedEndDate);
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getTestTotalItems());
				kelpaTestAdminExtractdetails.add(kelpaTestAdminDataExtractdetails.getTestOmittedItems());
				
				excelRows.add(kelpaTestAdminExtractdetails.toArray(new String[kelpaTestAdminExtractdetails.size()]));				
			}
		}
	}
	
	private String getTestStatus(String status) {
		if(status!=null){
			if(status.equalsIgnoreCase("Unused")) {
				return "Not Started";
			} else if(status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("inprogress")){
				return "In progress";
			} 
			else if(status.equalsIgnoreCase("In Progress Timed Out") || status.equalsIgnoreCase("inprogresstimedout")) {
				return "In Progress Timed Out";
			}
			else if(status.equalsIgnoreCase("Complete")) {
				return "Complete";
			}
			else if (status.equalsIgnoreCase("Pending")) {
				return "Pending";
			} else if (status.equalsIgnoreCase("PROCESS_LCS_RESPONSES")) {
				return "Processing LCS Responses";
			}
		}
		else
			return StringUtils.EMPTY;
		return status;
	}
	
	
	private void fillKapStudentScores(List<KAPStudentScoreDTO> kapStudentScores, List<String[]> lines,String[] kapColumnHeaders2,Long currentReportYear) {
		List<String> kapColumnHeaderNames = Arrays.asList(kapColumnHeaders2);	
		String studentScores[] = null;
		//int i=-1;
		List<ReportSubscores> subscores = null;
		List<OrganizationTreeDetail> orgDetails = null;
		String schoolIdentifier = StringUtils.EMPTY;
		
		String schoolName = StringUtils.EMPTY;
		String districtIdentifier = StringUtils.EMPTY;
		String districtName = StringUtils.EMPTY;
		boolean scienceOnly2015 = false;		
		for(KAPStudentScoreDTO kapStudentScoreDTO : kapStudentScores) {
			scienceOnly2015 = false;
			List<StudentScoreDTO> subjectScoresForAllYears = kapStudentScoreDTO.getStudentScores();
			if(CollectionUtils.isNotEmpty(subjectScoresForAllYears)) {		
				Collections.sort(subjectScoresForAllYears, Comparator.comparing(StudentScoreDTO::getReportYear));				
					for(StudentScoreDTO subjectScoresFor2015 : subjectScoresForAllYears) {
						if(CONTENT_AREA_SCIENCE.equalsIgnoreCase(subjectScoresFor2015.getSubjectCode())
							&& subjectScoresFor2015.getReportYear() != null && subjectScoresFor2015.getReportYear().compareTo(2015l) == 0) {
							scienceOnly2015 = true;
					} else {
						scienceOnly2015 = false;
					}
				}
			}
			if(!scienceOnly2015){		
				studentScores = new String[kapColumnHeaderNames.size()];
				//i=-1;				
				studentScores[kapColumnHeaderNames.indexOf("State_Student_Identifier")] = kapStudentScoreDTO.getStateStudentIdentifier();
				studentScores[kapColumnHeaderNames.indexOf("Student_Legal_Last_Name")] = kapStudentScoreDTO.getStudentLegalLastName();
				studentScores[kapColumnHeaderNames.indexOf("Student_Legal_First_Name")] = kapStudentScoreDTO.getStudentLegalFirstName();
				studentScores[kapColumnHeaderNames.indexOf("Student_Legal_Middle_Name")] = kapStudentScoreDTO.getStudentLegalMiddleName();
				//subject
				studentScores[kapColumnHeaderNames.indexOf("Subject")] = kapStudentScoreDTO.getSubject();
				//Current_Enrolled_District_Identifier
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_District_Identifier")] = kapStudentScoreDTO.getCurrentEnrolledDistrictIdentifier();
				//Current_Enrolled_District_Name
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_District_Name")] = kapStudentScoreDTO.getCurrentEnrolledDistrictName();
				//Current_Enrolled_AYP_School_Identifier
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_Accountability_School_Identifier")] = kapStudentScoreDTO.getCurrentEnrolledAypSchoolIdentifier();
				//Current_Enrolled_AYP_School_Name
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_Accountability_School_Name")] = kapStudentScoreDTO.getCurrentEnrolledAypSchoolName();
				//Current_Enrolled_Attendance_School_Identifier
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_Attendance_School_Identifier")] = kapStudentScoreDTO.getCurrentEnrolledATTSchoolIdentifier();
				//Current_Enrolled_Attendance_School_Name
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_Attendance_School_Name")] = kapStudentScoreDTO.getCurrentEnrolledATTSchoolName();
				//Current_Enrolled_Grade
				studentScores[kapColumnHeaderNames.indexOf("Current_Enrolled_Grade")] = kapStudentScoreDTO.getCurrentEnrolledGradeLevel();				
				String reportFlagsStr = StringUtils.EMPTY;
				if(CollectionUtils.isNotEmpty(orgDetails) && orgDetails.size() > 1){
					orgDetails.clear();
				}
				if(CollectionUtils.isNotEmpty(subjectScoresForAllYears)){
					for(StudentScoreDTO scoreDto : subjectScoresForAllYears){					
						schoolIdentifier = StringUtils.EMPTY;
						schoolName = StringUtils.EMPTY;
						districtIdentifier = StringUtils.EMPTY;
						districtName = StringUtils.EMPTY;						
						if(scoreDto.getReportYear() != null && scoreDto.getReportYear().compareTo(2015l) == 0 && !CONTENT_AREA_SCIENCE.equalsIgnoreCase(kapStudentScoreDTO.getSubjectCode())){//2015 results
							//Grade_2015
							studentScores[kapColumnHeaderNames.indexOf("Grade_2015")] = scoreDto.getGradeLevel();
							orgDetails = dataExtractService.getOrganizationDetailByStudentId(scoreDto.getStudentId(), scoreDto.getReportYear(), scoreDto.getSubjectCode(), null);							
							if(CollectionUtils.isNotEmpty(orgDetails) && orgDetails.size() > 1){
								//schoolId, districtId will be blank
								//Report_District_Name_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Name_2015")] = "Multiple";
								//Report_School_Name_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_School_Name_2015")] = "Multiple";
							}else{
								//Report_District_Identifier_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Identifier_2015")] = scoreDto.getReportDistrictIdentifier();
								//Report_District_Name_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Name_2015")] = scoreDto.getReportDistrictName();
								//Report_School_Identifier_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_School_Identifier_2015")] = scoreDto.getReportSchoolIdentifier();
								//Report_School_Name_2015
								studentScores[kapColumnHeaderNames.indexOf("Report_School_Name_2015")] = scoreDto.getReportSchoolName();
								//Scale_Score_2015
								studentScores[kapColumnHeaderNames.indexOf("Scale_Score_2015")] = getStringValueOf(scoreDto.getScaleScore());
								//Performance_Level_2015
								studentScores[kapColumnHeaderNames.indexOf("Performance_Level_2015")] = getStringValueOf(scoreDto.getPerformanceLevel());
								//Performance_Level_Name_2015
								studentScores[kapColumnHeaderNames.indexOf("Performance_Level_Name_2015")] = scoreDto.getPerformanceLevelName();
							}							
							//Report_Flags_2015
							reportFlagsStr = StringUtils.EMPTY;
							if(scoreDto.getExitStatus() != null && scoreDto.getExitStatus()){
								reportFlagsStr = "Exited";
							}
							if(scoreDto.getIncompleteStatus() != null && scoreDto.getIncompleteStatus()){
								if(reportFlagsStr.length() > 0){
									reportFlagsStr = reportFlagsStr + ", Incomplete";								
								}else{
									reportFlagsStr = " Incomplete";
								}
							}
							studentScores[kapColumnHeaderNames.indexOf("Report_Flags_2015")] = reportFlagsStr;							
						}	
						/*
						 * Added BY Debasis
						 * For Dynamic Csv  
						 * */
						for(int i=CommonConstants.KAP_EXTRACT_STARTING_YEAR;i<=currentReportYear;i++) {
							//Grade
							if(scoreDto.getReportYear() != null && scoreDto.getReportYear().compareTo(Long.valueOf(i)) == 0){
							studentScores[kapColumnHeaderNames.indexOf("Grade_"+i)] = scoreDto.getGradeLevel();						
							if(scoreDto.getTransferred() != null && scoreDto.getTransferred()){
								orgDetails = dataExtractService.getOrganizationDetailByStudentId(scoreDto.getStudentId(), scoreDto.getReportYear(), scoreDto.getSubjectCode(), scoreDto.getTransferred());
							}
							if(CollectionUtils.isNotEmpty(orgDetails) && orgDetails.size() > 1 && scoreDto.getTransferred() != null && scoreDto.getTransferred()){
								for(OrganizationTreeDetail otd : orgDetails){
									if(schoolIdentifier.length() > 0){
										schoolIdentifier = schoolIdentifier + ", " + otd.getSchoolDisplayIdentifier();
										schoolName = schoolName + ", " + otd.getSchoolName();
									}else{
										schoolIdentifier = otd.getSchoolDisplayIdentifier();
										schoolName = otd.getSchoolName();
									}
									
									
									if(StringUtils.isNotBlank(otd.getDistrictDisplayIdentifier()) && !otd.getDistrictDisplayIdentifier().equals(districtIdentifier)){
										if(districtIdentifier.length() > 0){
											districtIdentifier = districtIdentifier + ", " + otd.getDistrictDisplayIdentifier();
											districtName = districtName + ", " + otd.getDistrictName();
										}else{
											districtIdentifier = otd.getDistrictDisplayIdentifier();
											districtName = otd.getDistrictName();
										}
										
									}									
								}
								//Report_District_Identifier_
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Identifier_"+i)] = districtIdentifier;
								//Report_District_Name_
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Name_"+i)] = districtName;
								//Report_School_Identifier_
								studentScores[kapColumnHeaderNames.indexOf("Report_School_ID_"+i)] = schoolIdentifier;
								//Report_School_Name_
								studentScores[kapColumnHeaderNames.indexOf("Report_School_Name_"+i)] = schoolName;
							}else{
								//Report_District_Identifier_
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Identifier_"+i)] = scoreDto.getReportDistrictIdentifier();
								//Report_District_Name_
								studentScores[kapColumnHeaderNames.indexOf("Report_District_Name_"+i)] = scoreDto.getReportDistrictName();
								//Report_School_Identifier_
								studentScores[kapColumnHeaderNames.indexOf("Report_School_ID_"+i)] = scoreDto.getReportSchoolIdentifier();
								//Report_School_Name_
								studentScores[kapColumnHeaderNames.indexOf("Report_School_Name_"+i)] = scoreDto.getReportSchoolName();
							}						
							
							if((scoreDto.getSuppressMainScalescorePrfrmLevel() != null && !scoreDto.getSuppressMainScalescorePrfrmLevel()) || i!=CommonConstants.KAP_EXTRACT_STARTING_YEAR){
								//Scale_Score_
								studentScores[kapColumnHeaderNames.indexOf("Scale_Score_"+i)] = getStringValueOf(scoreDto.getScaleScore());
								//Performance_Level_
								studentScores[kapColumnHeaderNames.indexOf("Performance_Level_"+i)] = getStringValueOf(scoreDto.getPerformanceLevel());
								//Performance_Level_Name_
								studentScores[kapColumnHeaderNames.indexOf("Performance_Level_Name_"+i)] = scoreDto.getPerformanceLevelName();
								int KAP_EXTRACT_METAMETRIC_STARTING_YEAR = getValueFromAppConfiguration(CommonConstants.KAP_EXTRACT_METAMETRIC_STARTING_YEAR);
								if(KAP_EXTRACT_METAMETRIC_STARTING_YEAR!=0 && i >= KAP_EXTRACT_METAMETRIC_STARTING_YEAR) {
									if(StringUtils.equalsIgnoreCase(scoreDto.getSubjectCode(),"ELA") && scoreDto.getScaleScore() != null){
									studentScores[kapColumnHeaderNames.indexOf("Lexile_Score_"+i)] = scoreDto.getMetametricsMeasure();
									}
									if(StringUtils.equalsIgnoreCase(scoreDto.getSubjectCode(),"M") && scoreDto.getScaleScore() != null){
									studentScores[kapColumnHeaderNames.indexOf("Quantile_Score_"+i)] = scoreDto.getMetametricsMeasure();
									}
								}
							}
							
							//Report_Flags_
							reportFlagsStr = StringUtils.EMPTY;
							
							if(i==CommonConstants.KAP_EXTRACT_STARTING_YEAR) {
								if(scoreDto.getTransferred() != null && scoreDto.getTransferred()){							
									reportFlagsStr = "Transferred";
								}
								
								if(scoreDto.getIncompleteStatus() != null && scoreDto.getIncompleteStatus()){
									if(reportFlagsStr.length() > 0){
										reportFlagsStr = reportFlagsStr + ", Incomplete";								
									}else{
										reportFlagsStr = "Incomplete";
									}
								}	
							}
							
							
							studentScores[kapColumnHeaderNames.indexOf("Report_Flags_"+i)] = reportFlagsStr;
							
								subscores = scoreDto.getSubscores();
								if(CollectionUtils.isNotEmpty(subscores)){
									int j = kapColumnHeaderNames.indexOf("Subscore_1_Name_"+i);
									for(ReportSubscores rs : subscores){
										studentScores[j++] = rs.getSubScoreReportDisplayName();
										if(rs.getRating() != null) {
											if(rs.getRating() == 3){
												studentScores[j++] = "Exceeds";
											}else if(rs.getRating() == 2){
												studentScores[j++] = "Meets";
											}else if(rs.getRating() == 1){
												studentScores[j++] = "Below";
											}else if(rs.getRating() == -1){
												studentScores[j++] = "Insufficient Data";
											}
										}
									}
								}
								}
						
						
					}
					}
				}	
				
				lines.add(studentScores);
			}
		}
	}
	
	//Get value from app configuration table
	private int getValueFromAppConfiguration(String attrType) {
		List<AppConfiguration> attrList = appConfigurationService.selectByAttributeType(attrType);
		if (CollectionUtils.isEmpty(attrList)) {
			return 0;
		} else {
			// expecting only one value
			AppConfiguration apConfig = attrList.get(0);
			return Integer.parseInt(apConfig.getAttributeValue());
		}
	}
	
	private void writeCSVForKapScoreDE(List<String[]> lines, ModuleReport moduleReport, String typeName, List<Long> subjects) throws IOException {
       lines.size();
		CSVWriter csvWriter = null;
        String fileName;
        boolean fileExists = false;
        if(moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
        	fileName = moduleReport.getFileName();
        	fileExists = true;
        } else {
        	fileName = getKapStudentScoreFileName(moduleReport, subjects);
        }
        try {
        	String folderPath = getFolderPath(moduleReport, typeName);
        	String  csvFile= FileUtil.buildFilePath(folderPath,fileName);
        	if(fileExists) {
        		csvFile= FileUtil.buildFilePath("",fileName);
        	}  
        	//split the path/filename from the extension
    		String pathAndExtensionArray[] = csvFile.split("\\.");
    		//create a local temp file
    		File tempFile = File.createTempFile(pathAndExtensionArray[0], "."+pathAndExtensionArray[1]);
        	csvWriter = new CSVWriter(new FileWriter(tempFile, true), ',');
        	csvWriter.writeAll(lines);
        	csvWriter.flush();
        	if(!fileExists) {
        		moduleReport.setFileName(folderPath + java.io.File.separator + fileName);
            	updateModuleReport(moduleReport);
        	}
    		//send the local temp file to s3
    		s3.synchMultipartUpload(csvFile, tempFile);
    		//delete the local temp file
    		FileUtils.deleteQuietly(tempFile);        	
        } catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		throw ex;
        } finally {
        	if(csvWriter != null) {
        		csvWriter.close();
        	}
        }
	}	
	
	private String getKapStudentScoreFileName(ModuleReport moduleReport, List<Long> subjects) {
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
        String fileName = type.getFileName();
        String subjCode = null;
        if(subjects != null && subjects.size() == 1){
        	subjCode = contentAreaService.selectByPrimaryKey(Long.parseLong(String.valueOf(subjects.get(0)))).getAbbreviatedName();
        }
        
        Organization organization = organizationService.get(moduleReport.getOrganizationId());
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
        String currentDateStr = DATE_FORMAT.format(new Date());
        String displayIdentifier = organization.getDisplayIdentifier();
        if (displayIdentifier != null && displayIdentifier.trim().length() > 0) {
     	   displayIdentifier = displayIdentifier.trim().replace(" ", "_");
        }
        
        if(subjCode != null){
        	fileName += "_" + subjCode + "_" + displayIdentifier + "_" + moduleReport.getCreatedUser() + "_" + currentDateStr + ".csv";
        }else{
        	fileName += "_" + displayIdentifier + "_" + moduleReport.getCreatedUser() + "_" + currentDateStr + ".csv";
        }               
               
		return fileName;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startOrganizationSummaryExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId, Map<String, Object> additionalParams,String typeName) throws IOException {
		
		String[] columnHeaders = {"Organization ID", "Org Name", "Org Level", "Org Parent ID", "Org Parent Name", "Start Date", "End Date", 
				"Assessment Programs", "Testing Model", "Status", "Report Year" ,"Merged Schools", "Last Modified Date" , "Last Modified User"};
	 
		boolean includeInactiveOrganizations=(Boolean) additionalParams.get("includeInactiveOrganizations");
		String summaryLevelTypeCode = (String)additionalParams.get("summaryLevelTypeCode");
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(columnHeaders);
		List<OrganizationExtractDTO> organizations = dataExtractService.getOrganizationsExtractByOrg(orgId,includeInactiveOrganizations,summaryLevelTypeCode);
		fillOrganizationsInExcelRow(organizations, excelRows, orgId);
		writeReport(moduleReportId, excelRows,typeName);
		return true;
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startITIBlueprintCoverageSummaryExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		
		String[] initialColumnHeaders = {"District Identifier", "District Name", "School Identifier", "School Name", "Teacher", "Subject", "Grade", 
				"Criterion", "Criterion Description"};
		User user = userDetails.getUser();
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		boolean isTeacher = user.isTeacher() || user.isProctor();
		Organization org = dataExtractService.getOrganization(orgId);
		String summaryLevelTypeCode = (String)additionalParams.get("summaryLevelTypeCode");
		Long summaryLevelOrgTypeId = Long.valueOf(additionalParams.get("summaryLevelOrgTypeId").toString());
		String groupByTeacher = (String)additionalParams.get("groupByTeacher");
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		Long gradeId = additionalParams.get("gradeId") != null ? Long.valueOf(additionalParams.get("gradeId").toString()): null;
		Long subjectId = additionalParams.get("subjectId")!=null ? Long.valueOf(additionalParams.get("subjectId").toString()): null;
		List<Long> orgIds = new ArrayList<Long>();
		List<String[]> lines = new ArrayList<String[]>();
		DecimalFormat df = new DecimalFormat("#.##");
		if (org.getOrganizationTypeId() == summaryLevelOrgTypeId) {
			orgIds = new ArrayList<Long>();
			orgIds.add(orgId);
		} else {
			orgIds = dataExtractService.getAllChildrenIdsByType(orgId, summaryLevelTypeCode);
		}
		
		ArrayList<String> columnHeaderList = new  ArrayList<String>(Arrays.asList(initialColumnHeaders));
 		
		//Getting testing cycle for the Contracting organization, year and assessment program
		String contractingOrgName =(String)additionalParams.get("contractingOrgName");;
		String assessmentProgramCode =(String)additionalParams.get("assessmentProgramCode");
		List<TestingCycle> testCycleWindows = testingCycleMapper.getTestingCyclesByStateNameSchoolYearAssessmentProgramCode(assessmentProgramCode,new Long(currentSchoolYear),contractingOrgName);
		
		//generating dynamic column header
		for(TestingCycle curTestCycleWindow : testCycleWindows) {
			String testCycleName= curTestCycleWindow.getTestingCycleName();
			columnHeaderList.add(testCycleName + " Window # Students Testing");
			columnHeaderList.add(testCycleName + " Window % Students met");
		}
		
		String[] columnHeaders = columnHeaderList.stream().toArray(String[] ::new);
		
		for(Long oId : orgIds) {
			Organization o = dataExtractService.getOrganization(oId);
			List<Organization> parents = dataExtractService.getAllParents(oId);
			String schoolName = StringUtils.EMPTY;
			String schoolDisplayIdentifier = StringUtils.EMPTY;
			String districtName = StringUtils.EMPTY;
			String districtDisplayIdentifier = StringUtils.EMPTY;			
			
			if(StringUtils.equalsIgnoreCase("DT", summaryLevelTypeCode)) {
				districtDisplayIdentifier = o.getDisplayIdentifier();
				districtName = o.getOrganizationName();
			} else if(StringUtils.equalsIgnoreCase("SCH", summaryLevelTypeCode)) {
				schoolName = o.getOrganizationName();
				schoolDisplayIdentifier = o.getDisplayIdentifier();
				for (Organization p : parents) {
					if (p.getOrganizationType().getTypeCode().equals("DT")) {
						districtName = p.getOrganizationName();
						districtDisplayIdentifier = p.getDisplayIdentifier();
						break;
					}
				}
			}
			
			List<ITIBPCoverageExtractRostersDTO> itiBpCovergaeExtractRostersDtos = new ArrayList<ITIBPCoverageExtractRostersDTO>();
			if (((StringUtils.equalsIgnoreCase("SCH", summaryLevelTypeCode) || StringUtils.equalsIgnoreCase("DT", summaryLevelTypeCode)) 
					&& StringUtils.equalsIgnoreCase("TRUE", groupByTeacher)) || isTeacher) {
				itiBpCovergaeExtractRostersDtos = dataExtractService.getrosterDetalsGroupByTeacherForITIBP(oId, gradeId, subjectId, currentSchoolYear, isTeacher, user.getId());
			} else {
				itiBpCovergaeExtractRostersDtos = dataExtractService.getRosterDetailsForITIBluePrintExtract(oId, gradeId, subjectId, currentSchoolYear, null);
			}
			
			for(ITIBPCoverageExtractRostersDTO itiRostersInfo : itiBpCovergaeExtractRostersDtos) {
				List<BluePrintCriteriaDescription> bpCriteriasForSubAndGrade = bluePrintMapper.
						getBluePrintCriteriaDescByGradeAndSub(itiRostersInfo.getSubjectId(), itiRostersInfo.getGradeAbbrName());				
				for(BluePrintCriteriaDescription bpc: bpCriteriasForSubAndGrade) {
					String[] line = new String[columnHeaders.length];
					line[0] = districtDisplayIdentifier;
					line[1] = districtName;
					line[2] = schoolDisplayIdentifier;
					line[3] = schoolName;
					if(StringUtils.isNotEmpty(itiRostersInfo.getTeacherFirstName())
							&& StringUtils.isNotEmpty(itiRostersInfo.getTeacherLastName())) {
						line[4] = itiRostersInfo.getTeacherLastName() + "," + itiRostersInfo.getTeacherFirstName();
					} else {
						line[4] = StringUtils.EMPTY;						
					}
					line[5] = itiRostersInfo.getSubjectName();
					line[6] = itiRostersInfo.getGradeAbbrName();
					line[7] = String.valueOf(bpc.getCriteria());
					line[8] = bpc.getCriteriaText();
					////foreach testing cycle getting number of student and percentage of student met criteria
					int colCount = 9;
					for(TestingCycle curTestCycleWindow : testCycleWindows) {
						line[colCount++] = String.valueOf(itiRostersInfo.getRosteredStudentsDetailsList().size());
						line[colCount++] = df.format(getPercentOfStudentsMetCriteria(bpc.getCriteria(), bpc.getContentAreaId(), itiRostersInfo.getGradeAbbrName(), 
								itiRostersInfo.getRosteredStudentsDetailsList(), currentSchoolYear, curTestCycleWindow.getOperationalTestWindowId()));
					}
					lines.add(line);
				}				
			}
		}
		Collections.sort(lines, itiBlueprintCovergaeComparator);
		lines.add(0, columnHeaders);
		writeCSV(lines, moduleReport,typeName, true);
		Long completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
		return true;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private double getPercentOfStudentsMetCriteria(Long criteria, Long contentAreaId, String gradeCourseAbbrName,
			List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList, int currentSchoolYear, Long operationalTestWindowID) {
		double percentageOfStudentsMetCriteria = 0.00;
		int totalNumOfStudents = rosteredStudentsDetailsList.size();
		int totalNumofStudentsMetCrietria = dataExtractService.getNumberOfStudentsMetITIBPCrietria(criteria, contentAreaId, gradeCourseAbbrName, rosteredStudentsDetailsList, currentSchoolYear, operationalTestWindowID);
		if(totalNumOfStudents > 0) {
			percentageOfStudentsMetCriteria = (totalNumofStudentsMetCrietria / (double) totalNumOfStudents) * 100;
		}
		return percentageOfStudentsMetCriteria;
	}

	@Override
	public List<DLMBlueprintCoverageReportDTO> getBlueprintCoverageReport(
			Long schoolId, List<Long> teacherIds, List<Long> contentAreaIds,
			List<Long> gradeIds, Long schoolYear, Boolean groupByTeacher, Long testCycleID) {
		List<DLMBlueprintCoverageReportDTO> dtos = new ArrayList<DLMBlueprintCoverageReportDTO>();
		
		Organization school = organizationDao.get(schoolId);
		boolean byTeacher = Boolean.TRUE.equals(groupByTeacher);
		
		for (Long contentAreaId : contentAreaIds) {
			for (Long gradeId : gradeIds) {
				List<ITIBPCoverageExtractRostersDTO> itiBpCoverageExtractRostersDtos = new ArrayList<ITIBPCoverageExtractRostersDTO>();
				GradeCourse gradeCourse = gradeCourseDao.selectByPrimaryKey(gradeId);
				
				if (byTeacher) {
					for (Long teacherId : teacherIds) {
						itiBpCoverageExtractRostersDtos.addAll(rosterDao.getrosterDetalsGroupByTeacherForITIBP(schoolId, gradeId, contentAreaId,
								schoolYear.intValue(), true, teacherId));
					}
				} else {
					itiBpCoverageExtractRostersDtos = rosterDao.getRosterDetailsForITIBluePrintExtract(schoolId, gradeId, contentAreaId,
							schoolYear.intValue(), teacherIds);
				}
				
				List<BlueprintCriteriaAndEEDTO> criteriaAndEEs = 
						contentFrameworkDao.selectCriteriaAndEEsInBlueprintBySubjectAndGrade(contentAreaId, gradeCourse.getAbbreviatedName());
				
				for (ITIBPCoverageExtractRostersDTO itiRostersInfo : itiBpCoverageExtractRostersDtos) {
					DLMBlueprintCoverageReportDTO coverageReportDto = new DLMBlueprintCoverageReportDTO();
					if (byTeacher) {
						coverageReportDto.setTeacherId(itiRostersInfo.getTeacherId());
						coverageReportDto.setTeacherFirstName(itiRostersInfo.getTeacherFirstName());
						coverageReportDto.setTeacherLastName(itiRostersInfo.getTeacherLastName());
					}
					coverageReportDto.setOrgId(schoolId);
					coverageReportDto.setOrgName(school.getOrganizationName());
					coverageReportDto.setContentAreaName(itiRostersInfo.getSubjectName());
					coverageReportDto.setGradeCourseName(itiRostersInfo.getGradeName());
					coverageReportDto.setCriteriaAndEEs(criteriaAndEEs);
					
					List<Long> tmpTeacherIds = byTeacher ? Arrays.asList(coverageReportDto.getTeacherId()) : teacherIds;
					
					List<DLMBlueprintCoverageReportStudentTestsCriteriaDTO> studentTestCriteria = new ArrayList<DLMBlueprintCoverageReportStudentTestsCriteriaDTO>();
					for (ITIBPCoverageRosteredStudentsDTO studentDto : itiRostersInfo.getRosteredStudentsDetailsList()) {
						for (BlueprintCriteriaAndEEDTO bpceeDto : criteriaAndEEs) {
							DLMBlueprintCoverageReportStudentTestsCriteriaDTO testDto = new DLMBlueprintCoverageReportStudentTestsCriteriaDTO();
							
							testDto.setStudentFirstName(studentDto.getStudentFirstName());
							testDto.setStudentLastName(studentDto.getStudentLastName());
							testDto.setStudentId(studentDto.getStudentId());
							testDto.setCriteria(bpceeDto.getCriteria());
							
							List<ContentFrameworkDetail> ees = testcollectionDao.getAllStudentITIEEsForSubGradeAndCriteriaUnderTeachers(bpceeDto.getCriteria(),
									gradeCourse.getAbbreviatedName(), contentAreaId, studentDto.getStudentId(), tmpTeacherIds, schoolYear);
							
							Map<String, List<ItiTestSessionHistory>> eesToTests = new HashMap<String, List<ItiTestSessionHistory>>();
							for (ContentFrameworkDetail ee : ees) {
								List<ItiTestSessionHistory> itiTests = itiTestSessionHistoryMapper.getAllStudentITIPlansForEEUnderTeachers(bpceeDto.getCriteria(),
										gradeCourse.getAbbreviatedName(), contentAreaId, schoolId, studentDto.getStudentId(), tmpTeacherIds, schoolYear, ee.getId(),testCycleID);
								for (ItiTestSessionHistory itiTest : itiTests) {
									itiTest.setBlueprintCoverageDateStrings();
								}
								eesToTests.put(ee.getContentCode(), itiTests);
							}
							testDto.setEesToTests(eesToTests);
							
							testDto.setCompletedCriteriaStatus(testCollectionService.getStudentCriteriaStatus(
									bpceeDto.getCriteria(), contentAreaId, gradeCourse.getAbbreviatedName(), studentDto.getStudentId(), schoolYear.intValue()));
							
							studentTestCriteria.add(testDto);
						}
					}
					coverageReportDto.setStudentTestCriteria(studentTestCriteria);
					
					dtos.add(coverageReportDto);
				}
			}
		}
		
		return dtos;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ModuleReport> getQueuedReports(Long inQueueStatusId, Long inProgressStatusId, String organizationCode) {
		List<ModuleReport> moduleReports = moduleReportDao.getQueuedReports(inQueueStatusId, inProgressStatusId, organizationCode);
		return moduleReports;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int moveReportToInProgress(Long inQueueStatusId, Long inProgressStatusId, Long reportId) {
		return 	moduleReportDao.moveReportToInProgress(inQueueStatusId, inProgressStatusId, reportId);
	}

	@Override
	public List<UploadGrfFile> getDistrictForGRF(Long stateId, int reportYear) {
		return uploadResultFileMapper.getDistrictForGRF(stateId, reportYear);
	}

	@Override
	public List<UploadGrfFile> getSchoolsInDistrictForGRF(
			Long districtId, Long stateId) {
		return uploadResultFileMapper.getSchoolsInDistrictForGRF(districtId,stateId);
	}

	@Override
	public List<UploadGrfFile> getContentAreasforGRF(Long stateId,
			Long districtId, Long schoolId) {
		return uploadResultFileMapper.getContentAreasforGRF(stateId,districtId,schoolId);
	}

	@Override
	public List<UploadGrfFile> getGradeCourseByGRF(Long stateId,
			Long districtId, Long schoolId, Long contentAreaId) {
		return uploadResultFileMapper.getGradeCourseByGRF(stateId,districtId,schoolId,contentAreaId);
	}
	
	
	 private void fillOrganizationsInExcelRow(List<OrganizationExtractDTO> organizations, List<String[]> excelRows, Long orgId) {
			if (organizations != null && !organizations.isEmpty()) {
				SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YYYY, orgId);
				
				for (OrganizationExtractDTO organization : organizations) {
					List<String> organizationExcelRow = new ArrayList<String>();
					// This needs to be in the same order while writing to excel
					organizationExcelRow.add(organization.getOrgDisplayIdentifier());
					organizationExcelRow.add(organization.getOrganizationName());
					organizationExcelRow.add(organization.getOrgLevel());
					organizationExcelRow.add(organization.getParentOrgDisplayIdentifier());
					organizationExcelRow.add(organization.getParentOrganizationName());	
					organizationExcelRow.add(organization.getStartDate() == null ? "N/A" : dateFormat.format(organization.getStartDate()));
					organizationExcelRow.add(organization.getEndDate() == null ? "N/A" : dateFormat.format(organization.getEndDate()));
					if(organization.getAssessmentPrograms()!=null && !organization.getAssessmentPrograms().isEmpty())
					organizationExcelRow.add(organization.getAssessmentPrograms());
					else
					organizationExcelRow.add("");
					if(organization.getAbbreviatedProgramNames()!=null && !organization.getAbbreviatedProgramNames().isEmpty() && Arrays.asList(organization.getAbbreviatedProgramNames().split(",")).contains("DLM"))
					organizationExcelRow.add(organization.getTestingModel());
					else
					organizationExcelRow.add("");
					organizationExcelRow.add(organization.getStatus());
					organizationExcelRow.add(organization.getReportYear()>0?""+organization.getReportYear():"");
					String mergedorg = setdoubleQuoteForCsvString(organization.getMergedOrgDisplayIdentifier());
					organizationExcelRow.add(mergedorg);
					organizationExcelRow.add(organization.getLastModifiedDate() == null ? "N/A" : dateFormat.format(organization.getLastModifiedDate()));
					organizationExcelRow.add(organization.getLastModifiedName());
					excelRows.add(organizationExcelRow.toArray(new String[organizationExcelRow.size()]));				
				}
			}
		}
	 
	 public String setdoubleQuoteForCsvString(String mergedorgdisplayidentifier) {
		    String quoteText = "";
		    if (!mergedorgdisplayidentifier.isEmpty() && mergedorgdisplayidentifier.contains(",")) {
		        quoteText = "\"" + mergedorgdisplayidentifier + "\"";
		    }
		    else if(!mergedorgdisplayidentifier.isEmpty() && !mergedorgdisplayidentifier.contains(",")) {
		    	quoteText = mergedorgdisplayidentifier;
		    }
		    return quoteText;
		}

	private void fillFCSDetail(FCSDataExtractDTO fcsDetail, List<FCSAnswer> answers, List<String[]> excelRows, List<FCSHeader> dynamicHeaders, SimpleDateFormat dateFormat) {
		
		List<String> firstContactSurveyDetails = new ArrayList<String>();
		// This needs to be in the same order while writing to excel
		firstContactSurveyDetails.add(fcsDetail.getStateName());
		firstContactSurveyDetails.add(fcsDetail.getDistrictName());
		firstContactSurveyDetails.add(fcsDetail.getSchoolName());
		firstContactSurveyDetails.add(fcsDetail.getStateStudentIdentifier());
		firstContactSurveyDetails.add(fcsDetail.getStudentLastName());
		firstContactSurveyDetails.add(fcsDetail.getStudentFirstName());
		firstContactSurveyDetails.add(fcsDetail.getStudentMiddleInitial());
		firstContactSurveyDetails.add(fcsDetail.getGrade());
		firstContactSurveyDetails.add(fcsDetail.getSurveyStatus());		
		firstContactSurveyDetails.add(fcsDetail.getLastModifiedDate() != null ? dateFormat.format(fcsDetail.getLastModifiedDate()) : StringUtils.EMPTY);
		firstContactSurveyDetails.add(fcsDetail.getLastModifiedUser());
		
		// 11 static columns and then dynamic answers
		for(FCSHeader header : dynamicHeaders){
			boolean foundAnswer = false;
			String response = StringUtils.EMPTY;
			if(CollectionUtils.isNotEmpty(answers)){
				for(FCSAnswer answer : answers){
					if(answer.getLabelNumber().equals(header.getLabelNumber())){
						foundAnswer = true;
						response = answer.getResponse();
					}
					if(foundAnswer){
						break;
					}
				}
				firstContactSurveyDetails.add(response);
			}
		}
		excelRows.add(firstContactSurveyDetails.toArray(new String[firstContactSurveyDetails.size()]));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean firstContactSurveyExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		String[] userColumnHeaders = { "State", "District Name", "School Name", "State Student Identifier",
				"Student Last Name", "Student First Name", "Student Middle Initial", "Grade", "Survey Status",
				"Last Modified Date", "Last Modified User" };
		List<Long> assessmentPrograms = (List<Long>) additionalParams.get("assessmentProgramIds");
		
		User user = userDetails.getUser();
		boolean isTeacher = user.isTeacher() || user.isProctor();

		List<String[]> excelRows = new ArrayList<String[]>();
		
		// Get the dynamic rows and add to headers.
		List<FCSHeader> dynamicHeaders = dataExtractService.getDynamicFCSHeaders();
		String[] dynamicColumnHeaders = new String[dynamicHeaders.size() + 11];
		int i =0;
		for(String header : userColumnHeaders){
			dynamicColumnHeaders[i++] = header;
		}
		for(FCSHeader header: dynamicHeaders){
			dynamicColumnHeaders[i++] = header.getHeaderName();
		}
		excelRows.add(Arrays.asList(dynamicColumnHeaders).toArray(new String[dynamicColumnHeaders.length]));
		Long currentSchoolYear = Long.valueOf(((Integer) additionalParams.get("currentSchoolYear")).intValue());
		SimpleDateFormat dateFormat = getSimpleDateFormatter(EXTRACT_DATE_FORMAT_MM_DD_YYYY, orgId);
		
		List<FCSDataExtractDTO> firstContactSurveyExtractDetails = dataExtractService
				.getFirstContactSurveyDetails(orgId, assessmentPrograms, currentSchoolYear,isTeacher,user.getId());
		for(FCSDataExtractDTO row : firstContactSurveyExtractDetails){
			List<FCSAnswer> answers = dataExtractService.getFCSAnswers(row.getStudentId());
			fillFCSDetail(row, answers, excelRows, dynamicHeaders, dateFormat);
		}
		
		writeReport(moduleReportId, excelRows, typeName);
		return true;
	}

	private void fillStudentTestingReadinessExtractDetails(UserDetailImpl userDetails,
			List<TestingReadinessExtractDTO> testingReadinessDetails, Long currentSchoolYear,
			Map<String, Object> additionalParams, List<String> selectedAPs) {
		if (testingReadinessDetails != null && !testingReadinessDetails.isEmpty()) {
			List<String> cpassGeneralList = Arrays.asList("2", "2Q");
			List<String> cpassComprehensiveAgricultureList = Arrays.asList("A", "AM", "AQ");
			List<String> cpassAnimalList = Arrays.asList("B", "BQ");
			List<String> cpassPlantList = Arrays.asList("D", "DM", "DQ");
			List<String> cpassManufacturingList = Arrays.asList("E", "EM");
			List<String> cpassDesignPreConstructionList = Arrays.asList("F", "FQ");
			List<String> cpassFinanceList = Arrays.asList("H", "HM", "HQ");
			List<String> cpassCompBusinessList = Arrays.asList("G", "GQ");
			Map<String, TestingReadinessExtractDTO> studentMap = new HashMap<String, TestingReadinessExtractDTO>();

			Set<Long> studentIds = new HashSet<>();
			for (TestingReadinessExtractDTO testingReadinessDetail : testingReadinessDetails) {
				studentMap.put(testingReadinessDetail.getStudentId() + "-" + testingReadinessDetail.getSchoolId(),
						testingReadinessDetail);
				studentIds.add(testingReadinessDetail.getStudentId());
			}

			List<TestingReadinessEnrollSubjects> enrolledSubjectDetails = dataExtractService
					.getTestRecordsForExtract(studentIds, currentSchoolYear);
			for (TestingReadinessEnrollSubjects enrolledSubjectDetail : enrolledSubjectDetails) {
				TestingReadinessExtractDTO testingReadinessDetail = studentMap
						.get(enrolledSubjectDetail.getStudentId() + "-" + enrolledSubjectDetail.getSchoolId());
				if (testingReadinessDetail != null) {
					if (selectedAPs.contains("KAP") || selectedAPs.contains("DLM")) {
						if (enrolledSubjectDetail.getAssessmentProgram().equals("KAP")
								|| enrolledSubjectDetail.getAssessmentProgram().equals("DLM")) {
							if (enrolledSubjectDetail.getAssessmentProgram().equals("KAP")) {
								String subjectCode = enrolledSubjectDetail.getSubjectCode();
								if (subjectCode.equalsIgnoreCase("ELA")) {
									testingReadinessDetail.setEla(true);
								} else if (subjectCode.equalsIgnoreCase("M")) {
									testingReadinessDetail.setMath(true);
								} else if (subjectCode.equalsIgnoreCase("Sci")) {
									testingReadinessDetail.setScience(true);
								} else if (subjectCode.equalsIgnoreCase("SS")) {
									testingReadinessDetail.setHgss(true);
								}
							}

							if (!testingReadinessDetail.isEla() || !testingReadinessDetail.isMath()
									|| !testingReadinessDetail.isScience() || !testingReadinessDetail.isHgss()) {
								if (enrolledSubjectDetail.getAssessmentProgram().equals("DLM")) {
									String enrolledRosterSubject = enrolledSubjectDetail.getSubjectCode();
									if (!testingReadinessDetail.isEla() && enrolledRosterSubject.equalsIgnoreCase("ELA")) {
										testingReadinessDetail.setEla(true);
									}
									if (!testingReadinessDetail.isMath() && enrolledRosterSubject.equalsIgnoreCase("M")) {
										testingReadinessDetail.setMath(true);
									}
									if (!testingReadinessDetail.isScience() && enrolledRosterSubject.equalsIgnoreCase("Sci")) {
										testingReadinessDetail.setScience(true);
									}
									if (!testingReadinessDetail.isHgss() && enrolledRosterSubject.equalsIgnoreCase("SS")) {
										testingReadinessDetail.setHgss(true);
									}
								}
							}
						}
					}
					if (selectedAPs.contains("KELPA2")) {
						if (enrolledSubjectDetail.getAssessmentProgram().equals("KELPA2")) {
							String subjectCode = enrolledSubjectDetail.getSubjectCode();
							if (subjectCode.equalsIgnoreCase("ELP")) {
								testingReadinessDetail.setKelpa2(true);
							}
						}
					}

					if (selectedAPs.contains("CPASS")) {
						if (enrolledSubjectDetail.getAssessmentProgram().equals("CPASS")) {
							String testTypeCode = enrolledSubjectDetail.getTestTypeCode();
							if (cpassGeneralList.contains(testTypeCode)) {
								testingReadinessDetail.setCpassGeneral(true);
							} else if (cpassComprehensiveAgricultureList.contains(testTypeCode)) {
								testingReadinessDetail.setComprehensiveAg(true);
							} else if (cpassAnimalList.contains(testTypeCode)) {
								testingReadinessDetail.setAnimalSystems(true);
							} else if (cpassPlantList.contains(testTypeCode)) {
								testingReadinessDetail.setPlantSystems(true);
							} else if (cpassManufacturingList.contains(testTypeCode)) {
								testingReadinessDetail.setManufacturing(true);
							} else if (cpassDesignPreConstructionList.contains(testTypeCode)) {
								testingReadinessDetail.setDesignPreconstruction(true);
							} else if (cpassFinanceList.contains(testTypeCode)) {
								testingReadinessDetail.setFinance(true);
							} else if (cpassCompBusinessList.contains(testTypeCode)) {
								testingReadinessDetail.setCompBusiness(true);
							}
						}
					}
				}
			}
		}
	}
	
	private void fillStudentTestingReadinessExtractPNPDetails(UserDetailImpl userDetails,
			List<TestingReadinessExtractDTO> testingReadinessDetails, List<String[]> excelRows, Long currentSchoolYear,
			Map<String, Object> additionalParams, List<String> selectedAPs) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		if (testingReadinessDetails != null && !testingReadinessDetails.isEmpty()) {
			for (TestingReadinessExtractDTO testingReadinessDetail : testingReadinessDetails) {
				if (testingReadinessDetail.isEla() || testingReadinessDetail.isMath()
						|| testingReadinessDetail.isScience() || testingReadinessDetail.isHgss()
						|| testingReadinessDetail.isKelpa2() || testingReadinessDetail.isCpassGeneral()
						|| testingReadinessDetail.isComprehensiveAg() || testingReadinessDetail.isAnimalSystems()
						|| testingReadinessDetail.isPlantSystems() || testingReadinessDetail.isManufacturing()
						|| testingReadinessDetail.isDesignPreconstruction() || testingReadinessDetail.isFinance()
						|| testingReadinessDetail.isCompBusiness()) {
					
					List<String> testingReadinessDetailsRow = new ArrayList<String>();

					// This needs to be in the same order while writing to excel
					testingReadinessDetailsRow.add(testingReadinessDetail.getState());
					testingReadinessDetailsRow.add(testingReadinessDetail.getDistrictName());
					testingReadinessDetailsRow.add(testingReadinessDetail.getAttendanceDistrictIdentifier());
					testingReadinessDetailsRow.add(testingReadinessDetail.getSchool());
					testingReadinessDetailsRow.add(testingReadinessDetail.getAccountabilityDistrictIdentifier());
					testingReadinessDetailsRow.add(testingReadinessDetail.getAypSchoolIdentifier());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLastName());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStudentFirstName());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStudentMiddleInitial());
					testingReadinessDetailsRow.add(testingReadinessDetail.getGenerationCode());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStateStudentIdentifier());

					Date dateOfBirth = testingReadinessDetail.getDateOfBirth();
					String formattedDateOfBirth = "";
					if (dateOfBirth != null) {
						formattedDateOfBirth = dateFormat.format(dateOfBirth);
					}
					testingReadinessDetailsRow.add(" " + formattedDateOfBirth);
					testingReadinessDetailsRow.add(testingReadinessDetail.getGender());
					testingReadinessDetailsRow.add(testingReadinessDetail.getGrade());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLoginUsername());
					testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLoginPassword());

					if (selectedAPs.contains("KAP") || selectedAPs.contains("DLM")) {
						if (testingReadinessDetail.isEla()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isMath()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isScience()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isHgss()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}
					}

					if (selectedAPs.contains("KELPA2")) {
						if (testingReadinessDetail.isKelpa2()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}
					}

					if (selectedAPs.contains("CPASS")) {
						if (testingReadinessDetail.isCpassGeneral()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}
						if (testingReadinessDetail.isComprehensiveAg()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}
						if (testingReadinessDetail.isAnimalSystems()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isPlantSystems()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isManufacturing()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isDesignPreconstruction()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isFinance()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}

						if (testingReadinessDetail.isCompBusiness()) {
							testingReadinessDetailsRow.add("X");
						} else {
							testingReadinessDetailsRow.add("");
						}
					}

					String[] stdAssessmentPrograms = testingReadinessDetail.getStudentAssessmentProgram().split(",");

					testingReadinessDetailsRow.add(stdAssessmentPrograms != null && stdAssessmentPrograms.length > 0
							? stdAssessmentPrograms[0] : "");
					testingReadinessDetailsRow.add(stdAssessmentPrograms != null && stdAssessmentPrograms.length > 1
							? stdAssessmentPrograms[1] : "");
					testingReadinessDetailsRow.add(stdAssessmentPrograms != null && stdAssessmentPrograms.length > 2
							? stdAssessmentPrograms[2] : "");

					Locale clientLocale = new Locale((String) additionalParams.get("clientLocale"));
					Calendar userTime = new GregorianCalendar(Calendar.getInstance(clientLocale).getTimeZone());

					List<Long> studentIds = Arrays.asList(testingReadinessDetail.getStudentId());

					List<Long> assessmentProgramIds = new ArrayList<>();
					for (String ap : stdAssessmentPrograms) {
						if (StringUtils.isNotBlank(ap)) {
							assessmentProgramIds.add(assessmentProgramService.findByAbbreviatedName(ap.trim()).getId());
						}
					}
					List<AccessibilityExtractDTO> records = moduleReportDao.getAccessibilityReportByStudentIds(
							studentIds, currentSchoolYear.intValue(), assessmentProgramIds);

					AccessibilityExtractDTO record = (!records.isEmpty()) ? records.get(0) : null;
					if (record != null) {
						String[] values = new String[pnpColumnHeaders.length];
						populatePNPColumns(dateFormat, userTime, record, values, 0);
						for (String value : values) {
							testingReadinessDetailsRow.add(value);
						}
					} else {
						for (int i = 1; i <= pnpColumnHeaders.length; i++) {
							testingReadinessDetailsRow.add("");
						}
					}

					testingReadinessDetailsRow.add(testingReadinessDetail.getEnrollmentLastModified());
					testingReadinessDetailsRow.add(testingReadinessDetail.getEnrollmentLastModifiedBy());
					testingReadinessDetailsRow.add(testingReadinessDetail.getPnpLastModified());
					testingReadinessDetailsRow.add(testingReadinessDetail.getPnpLastModifiedBy());

					excelRows.add(testingReadinessDetailsRow.toArray(new String[testingReadinessDetailsRow.size()]));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean generateStudentCombinedReadinessExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		// A-O columns
		String[] staticHeaders1 = { "State", "District Name", "Attendance_District_Identifier", "School","Accountability_District_Identifier",
				"Accountability_School_Identifier", "Student Last Name", "Student First Name", "Student Middle Initial",
				"Generation Code", "State Student Identifier", "Date_of_Birth", "Gender", "Grade",
				"Student Login Username", "Student Login Password", }; 
		// P,Q,R,S columns
		String[] kapOrDlmColumns = { "ELA", "Math", "Science", "HGSS" }; 
		// T column
		String[] kelpa2Columns = { "KELPA2" }; 
		// U - AB columns
		String[] cpassColumns = { "CPASS General", "Comprehensive Ag", "Animal Systems", "Plant Systems",
				"Manufacturing", "Design Preconstruction", "Finance", "Comp Business" };

		String[] staticHeaders2 = { "Assessment_Program_1", "Assessment_Program_2", "Assessment_Program_3" };

		String[] lastColumns = { "Enrollment Last Modified", "Enrollment Last Modified By", "PNP Last Modified",
				"PNP Last Modified By" };
		Date startTime = new Date();
		// Get the user selected assessment program and join corresponding
		// columns
		List<Integer> assessmentPrograms = (List<Integer>) additionalParams.get("assessmentProgramIds");
		List<String> selectedAPs = new ArrayList<>();
		
		for (Integer apId : assessmentPrograms) {
			AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);

			if (ap != null && (ap.getAbbreviatedname().equals("KAP") || ap.getAbbreviatedname().equals("DLM"))) {
				staticHeaders1 = (String[]) ArrayUtils.addAll(staticHeaders1, kapOrDlmColumns);
				selectedAPs.add(ap.getAbbreviatedname());
			} else if (ap != null && (ap.getAbbreviatedname().equals("KELPA2"))) {
				staticHeaders1 = (String[]) ArrayUtils.addAll(staticHeaders1, kelpa2Columns);
				selectedAPs.add(ap.getAbbreviatedname());
			} else if (ap != null && (ap.getAbbreviatedname().equals("CPASS"))) {
				staticHeaders1 = (String[]) ArrayUtils.addAll(staticHeaders1, cpassColumns);
				selectedAPs.add(ap.getAbbreviatedname());
			}
		}
		staticHeaders1 = (String[]) ArrayUtils.addAll(staticHeaders1, staticHeaders2);

		String[] userColumnHeaders = (String[]) ArrayUtils.addAll(ArrayUtils.addAll(staticHeaders1, pnpColumnHeaders),
				lastColumns);

		
		List<Long> allOrgIds = new ArrayList<Long>();
		allOrgIds.addAll(dataExtractService.getAllChildrenOrgIds(Long.valueOf(orgId.toString())));
		// add the organization also to the list
		allOrgIds.add(Long.valueOf(orgId.toString()));

		Long currentSchoolYear = Long.valueOf(((Integer) additionalParams.get("currentSchoolYear")).intValue());

		boolean shouldOnlySeeRosteredStudents=false;
		List<TestingReadinessExtractDTO> testReadinessExtractDetails = dataExtractService
				.getStudentReadinessExtractDetails(allOrgIds, assessmentPrograms, currentSchoolYear,shouldOnlySeeRosteredStudents,userDetails.getUserId());
		
		
		int studentsAtATime = 1000;
		int offset = 0;
		while (offset <= testReadinessExtractDetails.size()) {
			int toIndex = ((offset + studentsAtATime) > testReadinessExtractDetails.size())
					? testReadinessExtractDetails.size() : (offset + studentsAtATime);
			fillStudentTestingReadinessExtractDetails(userDetails, testReadinessExtractDetails.subList(offset, toIndex),
					currentSchoolYear, additionalParams, selectedAPs);
			offset += studentsAtATime;
		}
		
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(userColumnHeaders);
		
		
		fillStudentTestingReadinessExtractPNPDetails(userDetails, testReadinessExtractDetails, excelRows, currentSchoolYear,
				additionalParams, selectedAPs);
		writeReport(moduleReportId, excelRows, typeName);
		return true;
	}

	
	private void fillDLMStudentExitDetails(List<StudentExitExtractDTO> dlmStudentExitDTOs,
			List<Object[]> excelRows, Long orgId)  {
		
		if(dlmStudentExitDTOs != null && !dlmStudentExitDTOs.isEmpty()){
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YYYY, orgId);
			for(StudentExitExtractDTO dlmStudentExitDTO : dlmStudentExitDTOs){
				List<Object> dlmStudentExitList = new ArrayList<Object>();
				
				dlmStudentExitList.add(dlmStudentExitDTO.getStudentId()== null ? "":dlmStudentExitDTO.getStudentId());
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getStateStudentIdentifier()));
				dlmStudentExitList.add(dlmStudentExitDTO.getStateName());
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getLegalFirstName()));
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getLegalMiddleName()));
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getLegalLastName()));
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getGenerationCode()));
				
				Date dateOfBirth = dlmStudentExitDTO.getDateOfBirth();
				String formattedDateOfBirth = "";
				if (dateOfBirth != null) {
					formattedDateOfBirth = dateFormat.format(dateOfBirth);
				}
				dlmStudentExitList.add(" " + formattedDateOfBirth);
				
				dlmStudentExitList.add(dlmStudentExitDTO.getCurrentGradeLevel()== null ? "" :dlmStudentExitDTO.getCurrentGradeLevel());
				dlmStudentExitList.add(dlmStudentExitDTO.getExitCode()== null ? "" :dlmStudentExitDTO.getExitCode());
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getExitCodeDescription()));
				dlmStudentExitList.add(dlmStudentExitDTO.getExitDate() != null ? dateFormat.format(dlmStudentExitDTO.getExitDate()) : StringUtils.EMPTY);
				dlmStudentExitList.add(checkEmpty(dlmStudentExitDTO.getSubject()));
				if(dlmStudentExitDTO.getCurrentEnrollmentStatus()==0)
				{
					dlmStudentExitList.add("Student IS NOT currently enrolled to DLM");
				}
				else
				{
					dlmStudentExitList.add("Student IS currently enrolled to DLM");
				}
				excelRows.add(dlmStudentExitList.toArray(new Object[dlmStudentExitList.size()]));
			}			
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean generateStudentExitExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		String[] columnHeaders = { "Kite_Student_Identifier", "State_Student_Identifier","State", "Student_Legal_First_Name",
				"Student_Middle_Name","Student_Legal_Last_Name", "Generation_Code", "Date_of_Birth",
				"Current_Grade_Level", "Exit Code", "Exit Code Description", "Exit Date", "Subject", "Current_Enrollment_Status"};
		List<Object[]> excelRows = new ArrayList<Object[]>();
		excelRows.add(columnHeaders);
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		User user = userDetails.getUser();
		int year = organizationService.get(orgId).getReportYear();
		int count=0;
		Date generatedDate = new Date();
		String assesmentProgram = (String) additionalParams.get("assessmentProgramCode");
		String fileName = null;
		Long assessmentProgramId = assessmentProgramDao.findAssessPgmIdByAbbreviatedName(assesmentProgram);
		Boolean isStateHaveSpecificExitCode = false;
		isStateHaveSpecificExitCode = studentDao.checkStateHaveSpecificExitCodes(orgId, assessmentProgramId, year);
		if (moduleReport != null) {
			List<StudentExitExtractDTO> dlmStudentExitDTOs = dataExtractService.getDLMStudentExitExtractForGrf(orgId, year, assesmentProgram, isStateHaveSpecificExitCode);
			BatchUpload batchUpload = batchUploadService.latestGrfBatchUpload(orgId, exitExtractGRFProcessType);
			if (batchUpload != null) {
				generatedDate = batchUpload.getCreatedDate();
			}
			fillDLMStudentExitDetails(dlmStudentExitDTOs, excelRows, orgId);
			fileName=writeExcelDlm(excelRows, moduleReport, typeName, generatedDate,year);
			if(!excelRows.isEmpty())
			{
				count=excelRows.size() - 1;
			}
			excelRows.clear();
			dlmStudentExitDTOs.clear();
			updateBatchFileName(fileName,batchUpload.getId(),count);
		}

		Long completedStatusid;
		completedStatusid = dataExtractService.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		updateReportStatus(moduleReport, completedStatusid);
		return true;
	}

	private void updateBatchFileName(String fileName,Long id,int successCount) {
		BatchUpload record = new BatchUpload();
		record.setFileName(fileName);
		record.setId(id);
		record.setStatus("COMPLETED");
		record.setFailedCount(0);
		record.setSuccessCount(successCount);
		record.setModifiedDate(new Date());
		batchUploadService.updateByPrimaryKeySelectiveBatchUpload(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StateSpecificFile getStateSpecificFileById(Long stateSpecificFileId) {
		return stateSpecificFilesDao.getStateSpecificFileById(stateSpecificFileId);
	}
	
	/**
	 * Gets timezone by specific organization id
	 * If timezone not available for requested organization, then look for parent organization's timezone
	 * @param format
	 * @param orgId
	 * @return
	 */
	private SimpleDateFormat getSimpleDateFormatter(String format, Long orgId) {
		TimeZone tz = null;	
		
		if(orgId != null){			
			tz = organizationService.getTimeZoneForOrganization(orgId);			
			if(tz == null){
				//get all parent organizations
				List<Organization> parentOrgs =  organizationService.getParentOrganizationsByOrgId(orgId);
				
				if(CollectionUtils.isNotEmpty(parentOrgs)){
					for(Organization org : parentOrgs){
						tz = organizationService.getTimeZoneForOrganization(org.getId());
						if(tz != null){
							break;
						}
					}
				}
				
			}
		}
		
		if(tz == null){
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central"); 
		}
		
		return getSimpleDateFormatter(format, tz);
	}
	
	private SimpleDateFormat getSimpleDateFormatter(String format, TimeZone tz) {
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(tz);
		return sdf;
	}
	
	/* 
	 * This method is beig used for generating ISMART test administration monitoring extract
	 * @see edu.ku.cete.service.DataReportService#startISmartTestAdministrationMonitoringExtract(edu.ku.cete.domain.user.UserDetailImpl, java.lang.Long, java.lang.Long, java.util.Map, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startISmartTestAdministrationMonitoringExtract(UserDetailImpl userDetails, Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException {
		LOGGER.debug("ISMART/2 test administration monitoring extract processing started at " + new Date() + " for report " + moduleReportId);
		List<Long> assessmentProgramIds = (List)additionalParams.get("assessmentProgramIds");
		List<String> ismartContentAreas = null;
		
		String[] columnHeaders = {
				"Subject",
				"State",
				"District",
				"School ID",
				"School Name",
				"Educator ID",
				"Educator Last Name",
				"Educator First Name",
				"Grade",
				"Student Last Name",
				"Student First Name",
				"Student State ID",
				"Student Local ID",
				"# Pilot Tests Not Started",
				"# Pilot Tests In Progress",
				"# Pilot Tests Completed",
				"# Pilot Tests Required"
		};
		
		User user = userDetails.getUser();
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		int currentSchoolYear = ((Integer)additionalParams.get("currentSchoolYear")).intValue();
		List<String[]> lines = new ArrayList<String[]>();
		lines.add(columnHeaders);
		
		if(CollectionUtils.isNotEmpty(assessmentProgramIds)) {
			List<Integer> apIds = (List<Integer>)additionalParams.get("assessmentProgramIds");
			ismartContentAreas = new ArrayList<>();
			for(int index = 0; index < apIds.size(); index++) {
				AssessmentProgram ap = dataExtractService.findByAssessmentProgramId(Long.valueOf(apIds.get(index)));
				if(ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())){
					ismartContentAreas.add(CommonConstants.ISMART_SCIENCE);
				}else if(ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(ap.getAbbreviatedname())) {
					ismartContentAreas.add(CommonConstants.ISMART_2_SCIENCE);
				}
			}
		}
		
		List<Organization> schools= dataExtractService.getAllChildrenWithParentByType(orgId, "SCH");
		
		for(Organization school : schools){
			getISmartTestStatusesByOrganization(school.getId(), user, shouldOnlySeeRosteredStudents, currentSchoolYear, lines, assessmentProgramIds, ismartContentAreas);
		}
		
		writeReport(moduleReportId, lines,typeName);
		
		LOGGER.debug("ISMART/2 test administration monitoring extract processing completed at " + new Date() + " for report " + moduleReportId);
		return true;
	}
	
	private void getISmartTestStatusesByOrganization(Long orgId, User user, boolean shouldOnlySeeRosteredStudents, int currentSchoolYear, List<String[]> lines, List<Long> assessmentProgramIds, List<String> ismartContentAreas) {
		LOGGER.info("ISMART TEST ADMIN EXTRACT query started at "+ new Date() + " for school : " + orgId);
		List<ISMARTTestAdminExtractDTO> iSmartTestStatusRecords = dataExtractService.getISmartTestStatusRecords(orgId, shouldOnlySeeRosteredStudents, 
				user.getId(), currentSchoolYear, user.getContractingOrgId(), assessmentProgramIds, ismartContentAreas);
		LOGGER.info("ISMART TEST ADMIN EXTRACT query completed at "+ new Date() + " for school : " + orgId);

		if(CollectionUtils.isNotEmpty(iSmartTestStatusRecords)) {
			for(ISMARTTestAdminExtractDTO iSmartTestStatus : iSmartTestStatusRecords) {
				List<Organization> organizations = dataExtractService.getAllParents(iSmartTestStatus.getAttendanceSchoolId());
				Organization school = dataExtractService.getOrganization(iSmartTestStatus.getAttendanceSchoolId());
				Organization district = null;
				Organization state = null;
				for(Organization organization: organizations) {
					if(organization.getOrganizationType().getTypeCode().equals("DT")) {
						district = organization;
					} else if(organization.getOrganizationType().getTypeCode().equals("ST")){
						state = organization;
					}
				}
								
				lines.add(new String[]{
					iSmartTestStatus.getSubjectName(),
					((state != null) ? state.getOrganizationName() : ""),
					((district != null) ? district.getOrganizationName() : ""),			
					((school != null) ? school.getDisplayIdentifier() : ""),
					((school != null) ? school.getOrganizationName() : ""),
					iSmartTestStatus.getEducatorId(),
					getNameWithQuotes(iSmartTestStatus.getEducatorLastName()),
					getNameWithQuotes(iSmartTestStatus.getEducatorFirstName()),
					iSmartTestStatus.getGrade(),
					getNameWithQuotes(iSmartTestStatus.getStudentLaststName()),
					getNameWithQuotes(iSmartTestStatus.getStudentFirstName()),
					iSmartTestStatus.getStateStudentId(),
					iSmartTestStatus.getLocalStudentId(),					
					iSmartTestStatus.getPilotTestsNotStarted() + "",
					iSmartTestStatus.getPilotTestsInProgress() + "",
					iSmartTestStatus.getPilotTestsCompleted() + "",
					iSmartTestStatus.getPilotTestsRequired() + ""
				});
			}
		}
	}
	/**
	 *  F841 - PLTW Extract Reports
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startPLTWTestAdministrationDataExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		List<String[]> lines = new ArrayList<String[]>();
		User user = userDetails.getUser();
		String assesmentProgram = (String) additionalParams.get("assessmentProgramCode");
		Long currentSchoolYear = ((Integer) additionalParams.get("currentSchoolYear")).longValue();
		Locale clientLocale = new Locale(additionalParams.get("clientLocale").toString());
		List<Long> assessmentPrograms = (List) additionalParams.get("assessmentProgramIds");
		boolean shouldOnlySeeRosteredStudents = user.isTeacher() || user.isProctor();
		boolean isPltw = false;
		String[] columnHeaders = { "State","District", "School", "School Identifier", "School Entry Date", "Grade",				
				"Roster Name", "Educator Identifier", "Educator Last Name",
				"Educator First Name", "Student Last Name", "Student First Name", "Student Middle Name",
				"State Student Identifier", 
				"Course", "Test Session Name","Test Status", 				
				"Last Reactivated Date Time", "Stage", "Start DateTime", "End DateTime",				
				"Total Items", "Unanswered Items" };
		lines.add(columnHeaders);
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		String contractingOrgName = (String) additionalParams.get("contractingOrgName");
		List<Organization> schools = dataExtractService.getAllChildrenWithParentByType(orgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
	if(StringUtils.equalsIgnoreCase(assesmentProgram, CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
		isPltw= true;
	}
		if (moduleReport != null && schools != null && !schools.isEmpty()) {
			SimpleDateFormat dateFormat = getSimpleDateFormatter(DATE_FORMAT_MM_DD_YY, orgId);
			for (Organization school : schools) {
				int offset = 0, foundCount = 0, limit = 10000;
				while (offset == 0 || foundCount == limit) {
					List<StudentTestSessionInfoDTO> testAdminDataExtractDTOs = testSessionService.getTestAdminExtract(
							school.getId(), currentSchoolYear, shouldOnlySeeRosteredStudents, user.getId(),
							assesmentProgram, offset, limit, assessmentPrograms,isPltw);

					foundCount = testAdminDataExtractDTOs.size();
					if (CollectionUtils.isNotEmpty(testAdminDataExtractDTOs)) {
						fillTestAdminDetails(user, lines, clientLocale, testAdminDataExtractDTOs, contractingOrgName,
								dateFormat, true);
					}
					offset = offset + limit;					
				}
			}
			writeCSV(lines, moduleReport, typeName, true);
			lines.clear();
			Long completedStatusid = dataExtractService.getCategoryId(CommonConstants.PLTW_STUDENT_REPORT_SCORING_STATUS , CommonConstants.PD_REPORT_STATUS);
			updateReportStatus(moduleReport, completedStatusid);
		}
		return true;
	}

	private void writePdf(ModuleReport moduleReport, List<ViewStudentDTO> studentInformationRecords, String typeName) {
		// TODO Auto-generated method stub
		OutputStream out = null;
        String pdfFileName;
        boolean fileExists = false;
        DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
        
        if(moduleReport.getPdfFileName() != null && moduleReport.getPdfFileName().length() > 0) {
        	pdfFileName = moduleReport.getPdfFileName();
        	fileExists = true;
        } else {
        	pdfFileName = getPdfFileName(moduleReport);
        }
        try {
        	String folderPath = getFolderPath(moduleReport, typeName);       	
        	String  pdfFile= FileUtil.buildFilePath(folderPath,pdfFileName);
        	if(fileExists) {
        		pdfFile= FileUtil.buildFilePath("",pdfFileName);
        	}        	
        	//split the path/filename from the extension
    		String pathAndExtensionArray[] = pdfFile.split("\\.");
    		//create a local temp file
    		File tempFile = File.createTempFile(pathAndExtensionArray[0], "."+pathAndExtensionArray[1]);
    		
    		// Changed to Make KELPA State Return file tab deliminated.
    		if("KSDE_KELPA_STATE_REPORT".equalsIgnoreCase(type.getFileName())){
            } else {
            	out = new java.io.FileOutputStream(tempFile);
            	setupExtractPDFGeneration(out, studentInformationRecords);
            }        	
        	if(!fileExists) {
        		moduleReport.setPdfFileName(folderPath + java.io.File.separator + pdfFileName); 
        		updateModuleReport(moduleReport);
        		}
    		s3.synchMultipartUpload(pdfFile, tempFile);
    		//delete the local temp file
    		FileUtils.deleteQuietly(tempFile);
        } catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		try {
				throw ex;
			} catch (IOException e) {
				LOGGER.error("IOException Occured:", e);
			}
        } 
		
	}
	
	private void setupExtractPDFGeneration(OutputStream out, List<ViewStudentDTO> studentInformationRecords) {
		XStream xstream = new XStream();
		xstream.alias("tsPdfDTO", ViewStudentDTO.class);
		if(CollectionUtils.isEmpty(studentInformationRecords)){
			ViewStudentDTO testSessionPdf = new ViewStudentDTO();
			studentInformationRecords.add(testSessionPdf);
		}
		TraxSource source = new TraxSource(studentInformationRecords, xstream);
		try {
			generateExtractPdf(out, studentsExtractTicketsXslFile.getFile(), source);
		} catch (IOException e) {
			LOGGER.error("IOException Occured:", e);
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
	}

	private void generateExtractPdf(OutputStream out, File foFile, TraxSource source) throws Exception {
		
		try {
			out = new BufferedOutputStream(out);	
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(foFile.getCanonicalPath()));	
			FopFactory fopFactory = FopFactory.newInstance(reportConfigFile.getFile());
			fopFactory.getImageManager().getCache().clearCache();
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,out);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(source, res);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private String getPdfFileName(ModuleReport moduleReport) {
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
        String pdfFileName = type.getFileName();
        Organization organization = organizationService.get(moduleReport.getOrganizationId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
        TimeZone tz = organizationService.getTimeZoneForOrganization(moduleReport.getOrganizationId());
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
        dateFormat.setTimeZone(tz);
		String currentDateStr = dateFormat.format(new Date());
		String displayIdentifier = organization.getDisplayIdentifier();
		if (displayIdentifier != null && displayIdentifier.trim().length() > 0) {
			displayIdentifier = displayIdentifier.trim().replace(" ", "_");
		}
		if (!CommonConstants.Kite_Scoring_Assignments_Status_Extract.equalsIgnoreCase(type.getFileName())) {
			if (CommonConstants.KSDE_KELPA_STATE_REPORT.equalsIgnoreCase(type.getFileName())) {
				Organization contractingOrg = organization;
		        if (!Boolean.TRUE.equals(organization.getContractingOrganization())) {
		        	contractingOrg = organizationService.getContractingOrganization(moduleReport.getOrganizationId());
		        }
		        pdfFileName += "." + displayIdentifier +
					".StudentScores." +
					(contractingOrg.getReportYear() != 0 ? (contractingOrg.getReportYear() + ".") : "") +
					currentDateStr + ".pdf";
			} else {
				pdfFileName += "_" + displayIdentifier + "_" + moduleReport.getCreatedUser();
				pdfFileName += "_" + currentDateStr + "."+CommonConstants.FILE_PDF_EXTENSION;
			}
		} else {
			pdfFileName += "_" + currentDateStr + "."+CommonConstants.FILE_PDF_EXTENSION;
		}
		return pdfFileName;
	}

	// Added code for implementing 675 - Kelpa2 current enrolled students features

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKELPA2StudentScoreCurrentExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		List<String[]> lines = new ArrayList<String[]>();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		Long currentOrganizationId = (additionalParams.get("currentOrganizationId") != null) ? Long.parseLong(additionalParams.get("currentOrganizationId").toString()) : null;
		List<Long> contentAreaIds = (List<Long>) additionalParams.get("contentAreaIds");
		List<String> grades = (List<String>) additionalParams.get("gradeIds");
		List<Long> schoolYears = (List<Long>) additionalParams.get("schoolYears");
		Long districtId = (additionalParams.get("districtId") != null
				&& !"".equalsIgnoreCase(additionalParams.get("districtId").toString())) ? Long.parseLong(additionalParams.get("districtId").toString()) : null;
		List<Long> schoolIds = (List<Long>) additionalParams.get("schoolIds");
		Long currentReportYear = (additionalParams.get("currentReportYear") != null	&& !"".equalsIgnoreCase(additionalParams.get("currentReportYear").toString())) ? Long.parseLong(additionalParams.get("currentReportYear").toString()) : null;
		String assessmentProgramCode = (additionalParams.get("assessmentProgramCode") != null) ? additionalParams.get("assessmentProgramCode").toString() : null;
		Organization currUserOrg = dataExtractService.getOrganization(currentOrganizationId);
		Long assessmentProgramId = (additionalParams.get("assessmentProgramId") != null	&& !"".equalsIgnoreCase(additionalParams.get("assessmentProgramId").toString())) ? Long.parseLong(additionalParams.get("assessmentProgramId").toString()) : null;
		List<String> gradeAbbreviatedNames = null;
		List<Long> orgIds = null;
		if (grades != null && grades.size() > 0) {
			gradeAbbreviatedNames = new ArrayList<String>();
			for (int i = 0; i < grades.size(); i++) {
				gradeAbbreviatedNames.add(String.valueOf(grades.get(i)));
			}
		}
		if (schoolYears == null)
			schoolYears = new ArrayList<Long>();
		schoolYears.add(currentReportYear);

		boolean isDistict = false;
		boolean isSchool = false;
		if (currUserOrg != null) {
			if (CommonConstants.ORGANIZATION_STATE_TYPE_ID.equals(currUserOrg.getOrganizationType().getTypeCode())) {
				if (districtId != null) {
					orgIds = new ArrayList<Long>();
					orgIds.add(districtId);
					isDistict = true;
				}
				if (schoolIds != null && schoolIds.size() > 0) {
					orgIds = schoolIds;
					isSchool = true;
					isDistict = false;
				}
			} else if (CommonConstants.ORGANIZATION_DISTRICT_CODE
					.equals(currUserOrg.getOrganizationType().getTypeCode())) {
				if (schoolIds != null && schoolIds.size() > 0) {
					orgIds = new ArrayList<Long>();
					orgIds = schoolIds;
					isSchool = true;
				} else {
					orgIds = new ArrayList<Long>();
					orgIds.add(currentOrganizationId);
					isDistict = true;
				}
			} else if (CommonConstants.ORGANIZATION_SCHOOL_CODE
					.equals(currUserOrg.getOrganizationType().getTypeCode())) {
				isSchool = true;
				if (schoolIds != null && schoolIds.size() > 0) {
					orgIds = new ArrayList<Long>();
					orgIds = schoolIds;
				} else {
					orgIds = new ArrayList<Long>();
					orgIds.add(currentOrganizationId);
				}
			}
		}
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
		if (moduleReport != null) {
			lines = kelpa2ScoreExtractHeaderfile(currentReportYear);
			if (assessmentProgramCode.equals(CommonConstants.ASSESSMENT_PROGRAM_KELPA2)) {
				int limit = 1000;
				int offset = 0;
				int countOfRecords = 0;
				List<KELPA2StudentScoreDTO> kelpa2StudentScores = null;
				while (offset == 0 || countOfRecords != 0) {
					kelpa2StudentScores = dataExtractService.generateKELPA2StudentScoreCurrentExtractForDistrictUser(
							orgIds, isDistict, isSchool, contentAreaIds, schoolYears, gradeAbbreviatedNames,
							currentSchoolYear, currentReportYear, offset, limit, moduleReport.getStateId(),
							CommonConstants.DATA_EXTRACT_REPORT_STUDENT, assessmentProgramId);
					offset = offset + limit;
					countOfRecords = kelpa2StudentScores.size();
					if (CollectionUtils.isNotEmpty(kelpa2StudentScores)) {
						fillKelpa2StudentScores(kelpa2StudentScores, lines, kelpa2ColumnHeaders, currentReportYear);
					}
				}
			}
			writeCSVForKelpa2ScoreDE(lines, moduleReport, typeName, contentAreaIds);
			lines.clear();
		}
		return true;
	}

	/**
	 * Generating kelpa2 dynamic column headers
	 * 
	 * @param currentReportYear
	 * @return
	 */
	public List<String[]> kelpa2ScoreExtractHeaderfile(Long currentReportYear) {
		List<String[]> lines = new ArrayList<String[]>();
		kelpa2ColumnHeaders = new String[] { CommonConstants.STATE_STUDENT_IDENTIFIER,
				CommonConstants.STUDENT_LEGAL_LAST_NAME, CommonConstants.STUDENT_LEGAL_FIRST_NAME,
				CommonConstants.STUDENT_LEGAL_MIDDLE_NAME, CommonConstants.CURRENT_ENROLLED_DISTRICT_IDENTIFIER,
				CommonConstants.CURRENT_ENROLLED_DISTRICT_NAME, CommonConstants.CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_IDENTIFIER,
				CommonConstants.CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_NAME,
				CommonConstants.CURRENT_ENROLLED_ATTENDANCE_SCHOOL_IDENTIFIER,
				CommonConstants.CURRENT_ENROLLED_ATTENDANCE_SCHOOL_NAME, CommonConstants.CURRENT_ENROLLED_GRADE };

		String kelpaStartYear = appConfigurationService.getByAttributeCode(CommonConstants.KELPA2_EXTRACT_STARTING_YEAR);

		for (int kelpa2StartYear = Integer.valueOf(kelpaStartYear); kelpa2StartYear <= currentReportYear; kelpa2StartYear++) {
			ArrayList<String> myList = new ArrayList<String>(Arrays.asList(kelpa2ColumnHeaders));
			myList.add(CommonConstants.GRADE_ + kelpa2StartYear);
			myList.add(CommonConstants.REPORT_DISTRICT_IDENTIFIER_ + kelpa2StartYear);
			myList.add(CommonConstants.REPORT_DISTRICT_NAME_ + kelpa2StartYear);
			myList.add(CommonConstants.REPORT_SCHOOL_IDENTIFIER_ + kelpa2StartYear);
			myList.add(CommonConstants.REPORT_SCHOOL_NAME_ + kelpa2StartYear);
			myList.add(CommonConstants.LISTENING_SCALE_SCORE_ + kelpa2StartYear);
			myList.add(CommonConstants.LISTENING_PERFORMANCE_LEVEL_ + kelpa2StartYear);
			myList.add(CommonConstants.READING_SCALE_SCORE_ + kelpa2StartYear);
			myList.add(CommonConstants.READING_PERFORMANCE_LEVEL_ + kelpa2StartYear);
			myList.add(CommonConstants.SPEAKING_SCALE_SCORE_ + kelpa2StartYear);
			myList.add(CommonConstants.SPEAKING_PERFORMANCE_LEVEL_ + kelpa2StartYear);
			myList.add(CommonConstants.WRITING_SCALE_SCORE_ + kelpa2StartYear);
			myList.add(CommonConstants.WRITING_PERFORMANCE_LEVEL_ + kelpa2StartYear);
			myList.add(CommonConstants.OVERALL_PROFICIENCY_LEVEL_ + kelpa2StartYear);
			kelpa2ColumnHeaders = myList.toArray(kelpa2ColumnHeaders);
		}
		lines.add(kelpa2ColumnHeaders);
		return lines;
	}

	private void fillKelpa2StudentScores(List<KELPA2StudentScoreDTO> kelpa2StudentScores, List<String[]> lines,
			String[] kelpa2DynamicColumnHeaders, Long currentReportYear) {
		List<String> kelpa2DynamicColumnHeaderName = Arrays.asList(kelpa2DynamicColumnHeaders);
		String studentScores[] = null;

		String kelpaStartYear = appConfigurationService.getByAttributeCode(CommonConstants.KELPA2_EXTRACT_STARTING_YEAR);

		for (KELPA2StudentScoreDTO kelpa2StudentScoreDTO : kelpa2StudentScores) {
			studentScores = new String[kelpa2DynamicColumnHeaderName.size()];
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.STATE_STUDENT_IDENTIFIER)] = kelpa2StudentScoreDTO.getStateStudentIdentifier();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.STUDENT_LEGAL_LAST_NAME)] = kelpa2StudentScoreDTO.getStudentLegalLastName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.STUDENT_LEGAL_FIRST_NAME)] = kelpa2StudentScoreDTO.getStudentLegalFirstName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.STUDENT_LEGAL_MIDDLE_NAME)] = kelpa2StudentScoreDTO.getStudentLegalMiddleName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_DISTRICT_IDENTIFIER)] = kelpa2StudentScoreDTO.getCurrentEnrolledDistrictIdentifier();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_DISTRICT_NAME)] = kelpa2StudentScoreDTO.getCurrentEnrolledDistrictName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_IDENTIFIER)] = kelpa2StudentScoreDTO.getCurrentEnrolledAypSchoolIdentifier();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_NAME)] = kelpa2StudentScoreDTO.getCurrentEnrolledAypSchoolName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_ATTENDANCE_SCHOOL_IDENTIFIER)] = kelpa2StudentScoreDTO.getCurrentEnrolledATTSchoolIdentifier();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_ATTENDANCE_SCHOOL_NAME)] = kelpa2StudentScoreDTO.getCurrentEnrolledATTSchoolName();
			studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.CURRENT_ENROLLED_GRADE)] = kelpa2StudentScoreDTO.getCurrentEnrolledGradeLevel();
			
			List<StudentScoreDTO> subjectScoresForAllYears = kelpa2StudentScoreDTO.getKelpa2StudentScores();
			if (CollectionUtils.isNotEmpty(subjectScoresForAllYears)) {
				for (StudentScoreDTO scoreDto : subjectScoresForAllYears) {
					for (int i = Integer.valueOf(kelpaStartYear); i <= currentReportYear; i++) {
						if (scoreDto.getReportYear() != null && scoreDto.getReportYear().compareTo(Long.valueOf(i)) == 0) {
								studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.GRADE_ + i)] = scoreDto.getGradeLevel();						
								studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.REPORT_DISTRICT_IDENTIFIER_ + i)] = scoreDto.getReportDistrictIdentifier();
								studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.REPORT_DISTRICT_NAME_ + i)] = scoreDto.getReportDistrictName();
								studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.REPORT_SCHOOL_IDENTIFIER_ + i)] = scoreDto.getReportSchoolIdentifier();
								studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.REPORT_SCHOOL_NAME_ + i)] = scoreDto.getReportSchoolName();
	
								Boolean notTestedFlag = false;
								if (CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE.equalsIgnoreCase(scoreDto.getListeningStatus()) ||
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS.equalsIgnoreCase(scoreDto.getListeningStatus()) || 
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT.equalsIgnoreCase(scoreDto.getListeningStatus())) {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.LISTENING_SCALE_SCORE_ + i)] = getStringValueOf(scoreDto.getListeningScaleScore());
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.LISTENING_PERFORMANCE_LEVEL_ + i)] = getStringValueOf(scoreDto.getListeningPerformanceLevel());
								} else {
									notTestedFlag = true;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.LISTENING_SCALE_SCORE_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.LISTENING_PERFORMANCE_LEVEL_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
								}	

								if (CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE.equalsIgnoreCase(scoreDto.getReadingStatus()) ||
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS.equalsIgnoreCase(scoreDto.getReadingStatus()) || 
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT.equalsIgnoreCase(scoreDto.getReadingStatus())) {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.READING_SCALE_SCORE_ + i)] = getStringValueOf(scoreDto.getReadingScaleScore());
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.READING_PERFORMANCE_LEVEL_ + i)] = getStringValueOf(scoreDto.getReadingPerformanceLevel());
								} else {
									notTestedFlag = true;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.READING_SCALE_SCORE_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.READING_PERFORMANCE_LEVEL_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
								}

								if (CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE.equalsIgnoreCase(scoreDto.getSpeakingStatus()) ||
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS.equalsIgnoreCase(scoreDto.getSpeakingStatus()) || 
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT.equalsIgnoreCase(scoreDto.getSpeakingStatus())) {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.SPEAKING_SCALE_SCORE_ + i)] = getStringValueOf(scoreDto.getSpeakingScaleScore());
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.SPEAKING_PERFORMANCE_LEVEL_ + i)] = getStringValueOf(scoreDto.getSpeakingPerformanceLevel());
								} else {
									notTestedFlag = true;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.SPEAKING_SCALE_SCORE_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.SPEAKING_PERFORMANCE_LEVEL_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
								}

								if (CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE.equalsIgnoreCase(scoreDto.getWritingStatus()) ||
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS.equalsIgnoreCase(scoreDto.getWritingStatus()) || 
									CommonConstants.KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT.equalsIgnoreCase(scoreDto.getWritingStatus())) {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.WRITING_SCALE_SCORE_ + i)] = getStringValueOf(scoreDto.getWritingScaleScore());
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.WRITING_PERFORMANCE_LEVEL_ + i)] = getStringValueOf(scoreDto.getWritingPerformanceLevel());
								} else {
									notTestedFlag = true;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.WRITING_SCALE_SCORE_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.WRITING_PERFORMANCE_LEVEL_+ i)] = CommonConstants.KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED;
								}

								if (notTestedFlag) {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.OVERALL_PROFICIENCY_LEVEL_ + i)] = "Incomplete Test";
								} else {
									studentScores[kelpa2DynamicColumnHeaderName.indexOf(CommonConstants.OVERALL_PROFICIENCY_LEVEL_ + i)] = getStringValueOf(scoreDto.getOverAllProficiencyLevel());
								}
						}
					}
				}
			}
			lines.add(studentScores);
		}
	}

	private void writeCSVForKelpa2ScoreDE(List<String[]> lines, ModuleReport moduleReport, String typeName,
			List<Long> subjects) throws IOException {
		lines.size();
		CSVWriter csvWriter = null;
		String fileName;
		boolean fileExists = false;
		if (moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
			fileName = moduleReport.getFileName();
			fileExists = true;
		} else {
			fileName = getKelpa2StudentScoreFileName(moduleReport, subjects);
		}
		try {
			String folderPath = getFolderPath(moduleReport, typeName);
			String csvFile = FileUtil.buildFilePath(folderPath, fileName);
			if (fileExists) {
				csvFile = FileUtil.buildFilePath("", fileName);
			}
			String pathAndExtensionArray[] = csvFile.split("\\.");
			File tempFile = File.createTempFile(pathAndExtensionArray[0], "." + pathAndExtensionArray[1]);
			csvWriter = new CSVWriter(new FileWriter(tempFile, true), ',');
			csvWriter.writeAll(lines);
			csvWriter.flush();
			if (!fileExists) {
				moduleReport.setFileName(folderPath + java.io.File.separator + fileName);
				updateModuleReport(moduleReport);
			}
			s3.synchMultipartUpload(csvFile, tempFile);
			FileUtils.deleteQuietly(tempFile);
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

	private String getKelpa2StudentScoreFileName(ModuleReport moduleReport, List<Long> subjects) {
		DataReportTypeEnum type = DataReportTypeEnum.getById(moduleReport.getReportTypeId());
		String fileName = type.getFileName();
		String subjCode = null;
		if (subjects != null && subjects.size() == 1) {
			subjCode = contentAreaService.selectByPrimaryKey(Long.parseLong(String.valueOf(subjects.get(0)))).getAbbreviatedName();
		}
		Organization organization = organizationService.get(moduleReport.getOrganizationId());
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yy_HH-mm-ss");
		String currentDateStr = now.format(DATE_FORMAT);
		String displayIdentifier = organization.getDisplayIdentifier();
		if (displayIdentifier != null && displayIdentifier.trim().length() > 0) {
			displayIdentifier = displayIdentifier.trim().replace(" ", "_");
		}

		if (subjCode != null) {
			fileName += "_" + subjCode + "_" + displayIdentifier + "_" + moduleReport.getCreatedUser() + "_"+ currentDateStr + ".csv";
		} else {
			fileName += "_" + displayIdentifier + "_" + moduleReport.getCreatedUser() + "_" + currentDateStr + ".csv";
		}
		return fileName;
	}

	public List<String[]> kapScoreExtractHeaderfile(Long currentReportYear) {		
		List<String[]> lines = new ArrayList<String[]>();	
		kapColumnHeaders = new String[] { "State_Student_Identifier", "Student_Legal_Last_Name", "Student_Legal_First_Name", "Student_Legal_Middle_Name",
					"Subject", "Current_Enrolled_District_Identifier", "Current_Enrolled_District_Name", "Current_Enrolled_Accountability_School_Identifier",
					"Current_Enrolled_Accountability_School_Name", "Current_Enrolled_Attendance_School_Identifier", "Current_Enrolled_Attendance_School_Name",
					"Current_Enrolled_Grade","Grade_2015", "Report_District_Identifier_2015", "Report_District_Name_2015", "Report_School_Identifier_2015",
					"Report_School_Name_2015", "Scale_Score_2015", "Performance_Level_2015", "Performance_Level_Name_2015", "Report_Flags_2015"};		
		for(int j =2016 ; j<=currentReportYear ;j++ ) {
			ArrayList<String> myList = new ArrayList<String>(Arrays.asList(kapColumnHeaders));
			myList.add("Grade_"+j);		
			myList.add("Report_District_Identifier_"+j);
			myList.add("Report_District_Name_"+j);
			myList.add("Report_School_ID_"+j);
			myList.add("Report_School_Name_"+j);
			myList.add("Scale_Score_"+j);
			myList.add("Performance_Level_"+j);
			myList.add("Performance_Level_Name_"+j);
			int KAP_EXTRACT_METAMETRIC_STARTING_YEAR=getValueFromAppConfiguration(CommonConstants.KAP_EXTRACT_METAMETRIC_STARTING_YEAR);
			if(KAP_EXTRACT_METAMETRIC_STARTING_YEAR!=0 && j >= KAP_EXTRACT_METAMETRIC_STARTING_YEAR) {
				myList.add("Lexile_Score_"+j);
				myList.add("Quantile_Score_"+j);
			}
			myList.add("Report_Flags_"+j);
			myList.add("Subscore_1_Name_"+j);			
			myList.add("Subscore_1_Rating_"+j);
			myList.add("Subscore_2_Name_"+j);
			myList.add("Subscore_2_Rating_"+j);
			myList.add("Subscore_3_Name_"+j);			
			myList.add("Subscore_3_Rating_"+j);			
			myList.add("Subscore_4_Name_"+j);
			myList.add("Subscore_4_Rating_"+j);
			myList.add("Subscore_5_Name_"+j);
			myList.add("Subscore_5_Rating_"+j);			
			myList.add("Subscore_6_Name_"+j);
			myList.add("Subscore_6_Rating_"+j);
			myList.add("Subscore_7_Name_"+j);
			myList.add("Subscore_7_Rating_"+j);			
			myList.add("Subscore_8_Name_"+j);
			myList.add("Subscore_8_Rating_"+j);
			myList.add("Subscore_9_Name_"+j);
			myList.add("Subscore_9_Rating_"+j);			
			myList.add("Subscore_10_Name_"+j);
			myList.add("Subscore_10_Rating_"+j);
			kapColumnHeaders = myList.toArray(kapColumnHeaders);			
		}			  
		lines.add(kapColumnHeaders);
		return lines;
	}

    /*For Current Enrolled Student*/
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKAPStudentScoreCurrentExtract(UserDetailImpl userDetails, Long moduleReportId,
			Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException {
		List<String[]> lines = new ArrayList<String[]>();
		User user = userDetails.getUser();
		Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long currentOrganizationId = (additionalParams.get("currentOrganizationId") != null) ? Long.parseLong(additionalParams.get("currentOrganizationId").toString()) : null;
		List<Long> contentAreaIds = (List<Long>) additionalParams.get("contentAreaIds");
		List<String> grades = (List<String>) additionalParams.get("gradeIds");
		 List<Long> schoolYears = (List<Long>) additionalParams.get("schoolYears");
		Long districtId = (additionalParams.get("districtId") != null && !"".equalsIgnoreCase(additionalParams.get("districtId").toString())) ? Long.parseLong(additionalParams.get("districtId").toString()) : null;
		List<Long> schoolIds = (List<Long>) additionalParams.get("schoolIds");
		Long currentReportYear = (additionalParams.get("currentReportYear") != null && !"".equalsIgnoreCase(additionalParams.get("currentReportYear").toString())) ? Long.parseLong(additionalParams.get("currentReportYear").toString()) : null;
		String assessmentProgramCode = (additionalParams.get("assessmentProgramCode") != null) ? additionalParams.get("assessmentProgramCode").toString() : null;
		Organization currUserOrg = dataExtractService.getOrganization(currentOrganizationId); 
		Long assessmentProgramId=(additionalParams.get("assessmentProgramId") != null && !"".equalsIgnoreCase(additionalParams.get("assessmentProgramId").toString())) ? Long.parseLong(additionalParams.get("assessmentProgramId").toString()) : null;
		List<String> gradeAbbreviatedNames = null;
		List<Long> orgIds = null; 		
		if(grades != null && grades.size() > 0){
			gradeAbbreviatedNames = new ArrayList<String>();
			for(int i = 0; i < grades.size(); i++){
				gradeAbbreviatedNames.add(String.valueOf(grades.get(i)));
			}
		}
		if(schoolYears == null) 
			schoolYears = new ArrayList<Long>();
		schoolYears.add(currentReportYear);
		
		boolean isDistict = false;
		boolean isSchool = false;
		if(currUserOrg != null){
			if(CommonConstants.ORGANIZATION_STATE_TYPE_ID.equals(currUserOrg.getOrganizationType().getTypeCode())){
					if(districtId != null){
						orgIds = new ArrayList<Long>();
						orgIds.add(districtId);
						isDistict = true;
					}
					if(schoolIds != null && schoolIds.size() > 0){						
						orgIds=schoolIds;
						isSchool =true;
						isDistict = false;
					}				
			}else if(CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){
				if(schoolIds != null && schoolIds.size() > 0){
					orgIds = new ArrayList<Long>();
					orgIds=schoolIds;
					isSchool = true;
				}else{
					orgIds = new ArrayList<Long>();
					orgIds.add(currentOrganizationId);
					isDistict = true;
				}				
			}else if(CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){
				isSchool = true;
				if(schoolIds != null && schoolIds.size() > 0){
					orgIds = new ArrayList<Long>();
					orgIds=schoolIds;	
				}else{
					orgIds = new ArrayList<Long>();
					orgIds.add(currentOrganizationId);
				}				
			}			
		}			
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);			
		if(moduleReport != null){
			   lines =kapScoreExtractHeaderfile(currentReportYear);
			   if(assessmentProgramCode.equals(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE)) {
			   int limit =1000;
			   int offset=0;
			   int countOfRecords=0;
			   List<KAPStudentScoreDTO> kapStudentScores = null;			   
			   Long studentTotalCount = dataExtractService.getKAPCurrentStudentCount(orgIds, isDistict, isSchool, currentSchoolYear, moduleReport.getStateId(), assessmentProgramId);  

			   while (offset == 0 ||  studentTotalCount > offset) {  
				   kapStudentScores = dataExtractService.generateKAPStudentScoreCurrentExtractForDistrictUser(orgIds, isDistict,isSchool, contentAreaIds, schoolYears, gradeAbbreviatedNames,currentSchoolYear,currentReportYear,offset,limit,moduleReport.getStateId(),CommonConstants.DATA_EXTRACT_REPORT_STUDENT,assessmentProgramId);  
				   offset=offset+limit;
			  if(CollectionUtils.isNotEmpty(kapStudentScores)) {
			    fillKapStudentScores(kapStudentScores,lines ,kapColumnHeaders,currentReportYear);
			   }		 
			   }
			   }
			   //logic end
			   writeCSVForKapScoreDE(lines, moduleReport, typeName, contentAreaIds);
			   lines.clear();
			   
			  }
		return true;
	}
	
	/*For Specific Student*/
	 
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKAPStudentScoreSpecifiedExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		List<String[]> lines = new ArrayList<String[]>();
		User user = userDetails.getUser();
		Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long currentOrganizationId = (additionalParams.get("currentOrganizationId") != null) ? Long.parseLong(additionalParams.get("currentOrganizationId").toString()) : null;
		String stateIdStudentIdentifier = (additionalParams.get("studentStateId") != null && additionalParams.get("studentStateId").toString().length() > 0) ? additionalParams.get("studentStateId").toString() : null;	
		Long currentReportYear = (additionalParams.get("currentReportYear") != null && !"".equalsIgnoreCase(additionalParams.get("currentReportYear").toString())) ? Long.parseLong(additionalParams.get("currentReportYear").toString()) : null;
		String assessmentProgramCode = (additionalParams.get("assessmentProgramCode") != null) ? additionalParams.get("assessmentProgramCode").toString() : null;
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);		
		List<Long> contentAreaIds = (List<Long>) additionalParams.get("contentAreaIds");		
		Organization currUserOrg = dataExtractService.getOrganization(currentOrganizationId); 
		Long assessmentProgramId=(additionalParams.get("assessmentProgramId") != null && !"".equalsIgnoreCase(additionalParams.get("assessmentProgramId").toString())) ? Long.parseLong(additionalParams.get("assessmentProgramId").toString()) : null;
		
		String currentUserLevel = null;
		List<Long> orgIds = null;
		boolean isState = false;
		boolean isDistict = false;
		boolean isSchool = false;
		boolean isCurrentEnrolled=false;
		
		if(currUserOrg != null){
			if(CommonConstants.ORGANIZATION_STATE_TYPE_ID.equals(currUserOrg.getOrganizationType().getTypeCode())){
				orgIds = new ArrayList<Long>();		
				orgIds.add(orgId);
				isState = true;
					}				
		else if(CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){
				currentUserLevel = CommonConstants.ORGANIZATION_DISTRICT_CODE;				
					orgIds = new ArrayList<Long>();
					orgIds.add(orgId);
					isDistict = true;
			}else if(CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){				
				currentUserLevel = CommonConstants.ORGANIZATION_SCHOOL_CODE;	
				orgIds = new ArrayList<Long>();
				orgIds.add(orgId);
				isSchool = true;
		}
		}
		int currentEnrollmentCount = dataExtractService.getEnrollmentCountBySsidOrgId(stateIdStudentIdentifier, orgId, currentSchoolYear.intValue(), currentUserLevel);
		if(currentEnrollmentCount>0)
			isCurrentEnrolled=true;
			
		if(moduleReport != null){
			lines =kapScoreExtractHeaderfile(currentReportYear);
			List<KAPStudentScoreDTO> kapStudentScores = null;	
			if(assessmentProgramCode.equals(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE)){
				kapStudentScores = dataExtractService.generateKAPStudentScoreSpecifiedExtractForDistrictUser(orgIds,isState,isDistict,isSchool, currentSchoolYear, stateIdStudentIdentifier,currentReportYear,isCurrentEnrolled,assessmentProgramId);
			
			}				
			if(CollectionUtils.isNotEmpty(kapStudentScores)) {
				fillKapStudentScores(kapStudentScores,lines ,kapColumnHeaders,currentReportYear);
			}			
			writeCSVForKapScoreDE(lines, moduleReport, typeName, contentAreaIds);
			lines.clear();			
		}	
		
		return true;
	}
/*For Tested Student*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean startKAPStudentScoreTestedExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException {
		List<String[]> lines = new ArrayList<String[]>();		
		User user = userDetails.getUser();
		Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long currentOrganizationId = (additionalParams.get("currentOrganizationId") != null) ? Long.parseLong(additionalParams.get("currentOrganizationId").toString()) : null;
		List<Long> contentAreaIds = (List<Long>) additionalParams.get("contentAreaIds");
		List<String> grades = (List<String>) additionalParams.get("gradeIds");
		List<Long> schoolYears = (List<Long>) additionalParams.get("schoolYears");
		Long districtId = (additionalParams.get("districtId") != null && !"".equalsIgnoreCase(additionalParams.get("districtId").toString())) ? Long.parseLong(additionalParams.get("districtId").toString()) : null;
		List<Long> schoolIds = (List<Long>) additionalParams.get("schoolIds");
		Long currentReportYear = (additionalParams.get("currentReportYear") != null && !"".equalsIgnoreCase(additionalParams.get("currentReportYear").toString())) ? Long.parseLong(additionalParams.get("currentReportYear").toString()) : null;
		String assessmentProgramCode = (additionalParams.get("assessmentProgramCode") != null) ? additionalParams.get("assessmentProgramCode").toString() : null;
		Long assessmentProgramId=(additionalParams.get("assessmentProgramId") != null && !"".equalsIgnoreCase(additionalParams.get("assessmentProgramId").toString())) ? Long.parseLong(additionalParams.get("assessmentProgramId").toString()) : null;
		Organization currUserOrg = dataExtractService.getOrganization(currentOrganizationId); 
		
		List<String> gradeAbbreviatedNames = null;
		List<Long> orgIds = new ArrayList<Long>(); 	
		if(grades != null && grades.size() > 0){
			gradeAbbreviatedNames = new ArrayList<String>();
			for(int i = 0; i < grades.size(); i++){
				gradeAbbreviatedNames.add(String.valueOf(grades.get(i)));
			}
		}
		if(schoolYears == null) 
			schoolYears = new ArrayList<Long>();
		schoolYears.add(currentReportYear);
		boolean isDistict = false;
		boolean isSchool = false;
		boolean isState=false;
		if(schoolIds != null && schoolIds.size() > 0){						
			orgIds=schoolIds;
		}
		
		if(currUserOrg != null){
			if(CommonConstants.ORGANIZATION_STATE_TYPE_ID.equals(currUserOrg.getOrganizationType().getTypeCode())){						
						isState = true;
			
			}else if(CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){
								
					isDistict = true;
							
			}else if(CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(currUserOrg.getOrganizationType().getTypeCode())){
				isSchool = true;
				orgIds.add(currentOrganizationId);
			}			
		}			
		ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);		
			
		if(moduleReport != null){
			lines =kapScoreExtractHeaderfile(currentReportYear);	
			   if(assessmentProgramCode.equals(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE)) {
			   int limit =1000;
			   int offset=0;
			   int countOfRecords=0;
			   List<KAPStudentScoreDTO> kapStudentScores = null;			   
			   //logic Start
			   while (offset == 0 ||  countOfRecords != 0) {  
				   kapStudentScores = dataExtractService.generateKAPStudentScoreTestedExtractForDistrictUser(orgIds,currentOrganizationId,districtId,isState,isDistict,isSchool,contentAreaIds,schoolYears,gradeAbbreviatedNames,currentSchoolYear,currentReportYear,offset,limit,CommonConstants.DATA_EXTRACT_REPORT_STUDENT,moduleReport.getStateId(),assessmentProgramId);  
			  offset=offset+limit;
			  countOfRecords=kapStudentScores.size();
			  if(CollectionUtils.isNotEmpty(kapStudentScores)) {
			    fillKapStudentScores(kapStudentScores,lines ,kapColumnHeaders,currentReportYear);
			   }		 
			   }
			   }
			   //logic end
			   writeCSVForKapScoreDE(lines, moduleReport, typeName, contentAreaIds);
			   lines.clear();
			   
			  }
		
		return true;
	}
	
	
	//Generating Hashmap of Students with PNP settings
		private HashMap<Long, PNPAbridgedExtractDTO> getActivePNPStudentSetting(Long orgId,
				boolean onlyRostered,
				Long teacherId,
				int currentSchoolYear,
				List<Long> assessmentPrograms,
				boolean includeAllStudents) {
			
			HashMap<Long, PNPAbridgedExtractDTO> resultMapping = new HashMap<Long, PNPAbridgedExtractDTO>();
			//Get all the active student with PNP setting
			List<PNPAbridgedExtractDTO> students = dataExtractService.getActiveStudentsWithPNPEnrolledInOrgPLTW(orgId,
					onlyRostered, teacherId, currentSchoolYear, assessmentPrograms, includeAllStudents);
			//creating the Student Map
			for(PNPAbridgedExtractDTO student : students) {
				resultMapping.put(student.getStudentId(), student);
			}
			
			return resultMapping;
		}
		
		
		//filling up the fields in the TestingReadinessExtractDTO
		
		private void fillStudentTestingReadinessExtractDetailsPLTW(UserDetailImpl userDetails,
				List<TestingReadinessExtractDTO> testingReadinessDetails, Long schoolYear,
				Map<String, Object> additionalParams, List<String> selectedAPs,
				boolean shouldOnlySeeRosteredStudents,List<Long> apIds, Long orgId,
				Map<Long, JsonNode> fieldSpecExtractScripts,List<FieldSpecification> fieldSpecs,
				HashMap<Long, PNPAbridgedExtractDTO>  studentsPNPMapping, SortedSet<String> dynamicCoursesName) throws IOException, ScriptException {
			
			if (testingReadinessDetails != null && !testingReadinessDetails.isEmpty()) {
				Map<String, TestingReadinessExtractDTO> studentMap = new HashMap<String, TestingReadinessExtractDTO>();
				
//				Setup for the PNP abridge will be called once per fillStudentTestingReadinessExtractDetailsPLTW call
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("JavaScript");
				if (!(engine instanceof Invocable)) {
					LOGGER.error("js engine not supported");
					throw new UnsupportedOperationException("JavaScript engine not supported");
				}
				final Invocable invocableEngine = ((Invocable)engine);
				
				JavaScriptEngineAnonymousFunctionHelper dummyObj = new JavaScriptEngineAnonymousFunctionHelper() {
					@Override
					public Object dummyMethod(Object function) {
						try {
							return invocableEngine.invokeMethod(function, "call", function, getParameter());
						} catch (NoSuchMethodException | ScriptException e) {
							LOGGER.error(e.getMessage());
							e.printStackTrace();
						}
						return null;
					}
				};
				engine.put("dummyObj", dummyObj);
//				Setup ends
				
				Set<Long> studentIds = new HashSet<>();
				for (TestingReadinessExtractDTO testingReadinessDetail : testingReadinessDetails) {
					
					//Check for PNP setting for the student from studentsPNPMapping
					HashMap<String, String> pltwPNPSetting = new HashMap<String, String>();
					if(studentsPNPMapping.containsKey(testingReadinessDetail.getStudentId())) {
						PNPAbridgedExtractDTO student = studentsPNPMapping.get(testingReadinessDetail.getStudentId());
						if ("CUSTOM".equals(student.getProfileStatus())) {
							// Massage the PNP JSON here to get it to one top-level object instead of an array that we'd have to traverse every time.
							// Should improve efficiency for the JS functions and speed things up a bit.
							String convertedJson = convertPNPJsonArray(student.getPnpJsonText());
							
							// set the dummy parameter to the student's converted PNP JSON
							dummyObj.setParameter(convertedJson);
							
							for (FieldSpecification fieldSpec : fieldSpecs) {
								JsonNode extractScript = fieldSpecExtractScripts.get(fieldSpec.getId());
								try {
									// execute the field's JavaScript function using eval()
									Object ret = engine.eval("dummyObj.dummyMethod(" + extractScript.asText() + ")");
									pltwPNPSetting.put(fieldSpec.getMappedName(), ret != null ? ret.toString() : "");
									
								} catch (Exception e) {
									e.printStackTrace();
									LOGGER.error("Exception encountered while processing field \"" +
											fieldSpec.getMappedName() + "\" for student " + student.getStudentId(), e);
									throw e;
								}
							}
							testingReadinessDetail.setPltwPNPSetting(pltwPNPSetting);
						}
					}else {
						//Nothing to do... We will handle in fillStudentTestingReadinessExtractPNPDetailsPLTW method
					}
					
					//Create the StudentMap. Tried to keep the code as close to what we have in Testing Extract
					studentMap.put(testingReadinessDetail.getStudentId() + "-" + testingReadinessDetail.getSchoolId(),
							testingReadinessDetail);
					studentIds.add(testingReadinessDetail.getStudentId());
				}
				
				HashSet<String> curEnrolledCourses;
				
				List<TestingReadinessEnrollSubjects> enrolledSubjectDetails = dataExtractService
						.getTestRecordsForExtract(studentIds, schoolYear);
				for (TestingReadinessEnrollSubjects enrolledSubjectDetail : enrolledSubjectDetails) {
					TestingReadinessExtractDTO testingReadinessDetail = studentMap
							.get(enrolledSubjectDetail.getStudentId() + "-" + enrolledSubjectDetail.getSchoolId());
					if (testingReadinessDetail != null) {
						// Will remove the additional assessment program later
						if (selectedAPs.contains(CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
							if(enrolledSubjectDetail.getSubjectName()!=null && !enrolledSubjectDetail.getSubjectName().isEmpty())
							{
								curEnrolledCourses=testingReadinessDetail.getPltwCourseEnrolled();
								curEnrolledCourses.add(enrolledSubjectDetail.getSubjectName());
								testingReadinessDetail.setPltwCourseEnrolled(curEnrolledCourses);
								dynamicCoursesName.add(enrolledSubjectDetail.getSubjectName());
							}
						}
					}
				}
				
				
			}
		}
		
		//Actual excel row fill up for PLTW
		private void fillPLTWStudentTestingReadinessExtractPNPDetailsPLTW(List<TestingReadinessExtractDTO> testingReadinessDetails, 
				List<String[]> excelRows, String[] pltw_Course_Columns, List<FieldSpecification> fieldSpecs) {
			
	 		if (CollectionUtils.isNotEmpty(testingReadinessDetails)) {
				for (TestingReadinessExtractDTO testingReadinessDetail : testingReadinessDetails) {
					if( CollectionUtils.isNotEmpty(testingReadinessDetail.getPltwCourseEnrolled())) {
						List<String> testingReadinessDetailsRow = new ArrayList<String>();
		 				// This needs to be in the same order while writing to excel
						testingReadinessDetailsRow.add(testingReadinessDetail.getState());
						testingReadinessDetailsRow.add(testingReadinessDetail.getDistrictName());
						testingReadinessDetailsRow.add(testingReadinessDetail.getAttendanceDistrictIdentifier());
						testingReadinessDetailsRow.add(testingReadinessDetail.getSchool());
						testingReadinessDetailsRow.add(testingReadinessDetail.getSchoolDisplayIdentifier());
						testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLastName());
						testingReadinessDetailsRow.add(testingReadinessDetail.getStudentFirstName());
						testingReadinessDetailsRow.add(testingReadinessDetail.getStateStudentIdentifier());
						testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLoginUsername());
						testingReadinessDetailsRow.add(testingReadinessDetail.getStudentLoginPassword());
						
						HashSet<String> getPltwCourseEnrolled=testingReadinessDetail.getPltwCourseEnrolled();
						for(String course : pltw_Course_Columns) {
							if(getPltwCourseEnrolled.contains(course)) {
								testingReadinessDetailsRow.add("X");
							}else {
								testingReadinessDetailsRow.add("");
							}
						}
						
						testingReadinessDetailsRow.add(testingReadinessDetail.getComprehensiveRace());
						testingReadinessDetailsRow.add(testingReadinessDetail.getHispanicEthnicityStr());
		 				HashMap<String, String> pltwPNPSetting = testingReadinessDetail.getPltwPNPSetting();
						if(pltwPNPSetting.isEmpty()) {
							for (FieldSpecification fieldSpec : fieldSpecs) {
								//add empty cells
								testingReadinessDetailsRow.add("");
							}
						}else {
							for (FieldSpecification fieldSpec : fieldSpecs) {
								if(pltwPNPSetting.containsKey(fieldSpec.getMappedName())) {
									testingReadinessDetailsRow.add(pltwPNPSetting.get(fieldSpec.getMappedName()));
								}else {
									testingReadinessDetailsRow.add("");
								}
							}
						}
						
						excelRows.add(testingReadinessDetailsRow.toArray(new String[testingReadinessDetailsRow.size()]));
					}
				}
			}
		}
		
		
		//PLTW Testing Readiness
		@SuppressWarnings("unchecked")
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
		public boolean generatePLTWStudentCombinedReadinessExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
				Map<String, Object> additionalParams, String typeName) throws IOException {
			// A-J columns
			String[] staticHeaders1 = { "State", "District Name", "District ID", "School Name",
					"School ID", "Student Last Name", "Student First Name",
					"State Student Identifier",	"Student Login Username", "Student Login Password", }; 
	 		String[] staticHeaders2 = { "Comprehensive_Race", "Hispanic_Ethnicity"};
	 		// Get the user selected assessment program and join corresponding
			// columns
			List<Integer> assessmentPrograms = (List<Integer>) additionalParams.get("assessmentProgramIds");
			Long assessmentProgramId = new Long(assessmentPrograms.get(0).intValue());
			List<Long> apIds = Arrays.asList(assessmentProgramId);
			
			// Dynamic PLTW Course columns
			String[] pltw_Course_Columns = null;
			List<String> selectedAPs = new ArrayList<>();
			Boolean okTOProceed =true;
			
			
			//Adding Course Header
			for (Integer apId : assessmentPrograms) {
				AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
				selectedAPs.add(ap.getAbbreviatedname());
				if(ap != null && (!ap.getAbbreviatedname().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW))) {
					//Skip extract generation if Assessment program is different
					okTOProceed=false;
				}
			}
			
			
	 //		Adding PNP setting Header
			ModuleReport mr = moduleReportDao.selectByPrimaryKey(moduleReportId);
			Long stateId = mr.getStateId();
			Long userId = new Long(mr.getCreatedUser());
			int schoolYear = additionalParams.get("currentSchoolYear") == null ? 0 : ((int)additionalParams.get("currentSchoolYear"));
			
			Groups group = groupService.getGroup(mr.getGroupId());
			boolean shouldOnlySeeRosteredStudents = Pattern.matches("(?i:teacher|proctor)", group.getGroupName());
			
			// our ObjectMapper that we'll use to parse and traverse the JSON in fieldspecs as well as massage a student's PNP JSON later 
			ObjectMapper objectMapper = new ObjectMapper();
					
			// a Map to avoid having to traverse the JSON tree again later when we want to output the columns
			Map<Long, JsonNode> fieldSpecExtractScripts = new HashMap<Long, JsonNode>();
			List<FieldSpecification> fieldSpecs = uploadFileService.selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(assessmentProgramId, stateId);
			
			List<String> headers = new ArrayList<String>();
			// remove columns that don't have an extract formatting function
			for (int x = 0; x < fieldSpecs.size(); x++) {
				FieldSpecification fieldSpec = fieldSpecs.get(x);
				
				String fsJsonStr = fieldSpec.getJsonData();
				JsonNode fsJson = objectMapper.readTree(fsJsonStr);
				JsonNode fieldExtractScript = fsJson.get("extractScript");
				if (fieldExtractScript != null && StringUtils.isNotEmpty(fieldExtractScript.asText(""))) {
					LOGGER.debug("adding header " + fieldSpec.getMappedName());
					headers.add(fieldSpec.getMappedName());
					fieldSpecExtractScripts.put(fieldSpec.getId(), fieldExtractScript);
				} else {
					LOGGER.debug("\"extractScript\" was null or empty on fieldspecificationid \"" + fieldSpec.getMappedName() +
							"\" during PNP abridged extract, dynamically excluding the column from the extract");
					fieldSpecs.remove(x);
					x--;
				}
			}

			String[] userColumnHeaders=staticHeaders1;
			List<String[]> excelRows = new ArrayList<String[]>();
			
			
			//if okToProceed is false don't generate the extract
			if(okTOProceed) {
				//get Student Map
				HashMap<Long, PNPAbridgedExtractDTO>  studentsPNPMapping = getActivePNPStudentSetting(orgId,shouldOnlySeeRosteredStudents,userId,schoolYear,apIds, false);
				
				SortedSet<String> dynamicCoursesName = new TreeSet<>();
				
				//get all the children organization
				List<Long> allOrgIds = new ArrayList<Long>();
				allOrgIds.addAll(dataExtractService.getAllChildrenOrgIds(Long.valueOf(orgId.toString())));
				// add the organization also to the list
				allOrgIds.add(Long.valueOf(orgId.toString()));
				
				
				//Getting testRedinessExtractDetails
				List<TestingReadinessExtractDTO> testReadinessExtractDetails = dataExtractService
						.getStudentReadinessExtractDetails(allOrgIds, assessmentPrograms, Long.valueOf(schoolYear),shouldOnlySeeRosteredStudents ,userId );
				
				
				int studentsAtATime = 3000;
				int offset = 0;
				while (offset <= testReadinessExtractDetails.size()) {
					int toIndex = ((offset + studentsAtATime) > testReadinessExtractDetails.size()) ? testReadinessExtractDetails.size() : (offset + studentsAtATime);			
					try {
						fillStudentTestingReadinessExtractDetailsPLTW(userDetails, testReadinessExtractDetails.subList(offset, toIndex),
								Long.valueOf(schoolYear), additionalParams, selectedAPs, 
								shouldOnlySeeRosteredStudents, apIds, orgId, fieldSpecExtractScripts, fieldSpecs, studentsPNPMapping ,dynamicCoursesName);
					} catch (ScriptException e) {
						e.printStackTrace();
					}
					offset += studentsAtATime;
				}
				
				
				//Adding course header
				pltw_Course_Columns = dynamicCoursesName.toArray(new String[0]);
				userColumnHeaders = (String[]) ArrayUtils.addAll(staticHeaders1, pltw_Course_Columns);
				
				//Adding Static 2 Header
				userColumnHeaders = (String[]) ArrayUtils.addAll(userColumnHeaders, staticHeaders2);
				
				userColumnHeaders = (String[]) ArrayUtils.addAll(userColumnHeaders, headers.toArray(new String[0]));
//				Added PNP header
				
				excelRows.add(userColumnHeaders);
				
				fillPLTWStudentTestingReadinessExtractPNPDetailsPLTW(testReadinessExtractDetails, excelRows,
							pltw_Course_Columns, fieldSpecs);
			}

			writeReport(moduleReportId, excelRows, typeName);
			return true;
		}
		
		
		public static boolean compareList(List ls1, List ls2){
		    return ls1.containsAll(ls2) ? true :false;
		     }

//Exit student file
		private String writeExcelDlm(List<Object[]> lines, ModuleReport moduleReport, String typeName, Date createdDate,int year) throws IOException{
			 
	        String fileName;
	        boolean fileExists = false;
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Sheet 1");
	           
	        if(moduleReport.getFileName() != null && moduleReport.getFileName().length() > 0) {
	        	fileName = moduleReport.getFileName();
	        	fileExists = true;
	        } else {
	        	fileName = getFileNameExcel(moduleReport,createdDate);
	        	fileName=year+"_"+fileName;
	        	fileName=fileName.replaceAll("_Uploaded", "");
	        }
	        	String folderPath = getFolderPath(moduleReport, typeName);
	    		int rowNum = 0;
	    		
	    		for (Object[] line : lines) {
	                Row row = sheet.createRow(rowNum++);
	                int colNum = 0;
	                for (Object field : line) {
	                    Cell cell = row.createCell(colNum++);
	                    if (field instanceof String) {
	                        cell.setCellValue((String) field);
	                    } else if (field instanceof Integer) {
	                        cell.setCellValue((Integer) field);
	                    }else if(field instanceof Long){
	                    	cell.setCellValue((Long) field);
	                    }
	                }
	            }

	            try {
            	String[] splitFilename = fileName.split("\\.");
            	File tempFile = File.createTempFile(folderPath + java.io.File.separator + splitFilename[0], "."+splitFilename[1]);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
	                workbook.write(outputStream);
	                workbook.close();
	                
                 String key = folderPath + java.io.File.separator + fileName;
                
	                if(!fileExists) {
	        		moduleReport.setFileName(key);
		        	}
                s3.synchMultipartUpload(key, tempFile);
                FileUtils.deleteQuietly(tempFile);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
				return fileName;

				
	}
		
}