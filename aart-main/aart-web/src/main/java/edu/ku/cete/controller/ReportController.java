/**
 * 
 */
package edu.ku.cete.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOPException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.domain.Educator;
import edu.ku.cete.domain.ItiStudentReport;
import edu.ku.cete.domain.ItiStudentReportEE;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.MicroMap;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.report.roster.ItiRosterReportEE;
import edu.ku.cete.domain.report.roster.RosterReport;
import edu.ku.cete.domain.report.roster.RosterReportGradeStudentData;
import edu.ku.cete.domain.report.student.StudentReportDto;
import edu.ku.cete.domain.report.student.StudentRptCCArea;
import edu.ku.cete.domain.report.student.StudentRptEssentialElement;
import edu.ku.cete.domain.report.student.StudentRptLinkageLevel;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.report.GraphScoreReport;
import edu.ku.cete.report.RawScoreReport;
import edu.ku.cete.report.StudentProblemReport;
import edu.ku.cete.report.StudentResponseReport;
import edu.ku.cete.score.StudentRawScore;
import edu.ku.cete.score.util.ResponseScoringUtil;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.ReportGenerator;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.UploadSpecification;

/**
 * @author neil.howerton
 * 
 * @deprecated dead code replaced by NewReportController
 */
@Controller
public class ReportController {

	/**
	 * Logger for class.
	 */
	private Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private TestSessionService testSessionService;

	@Autowired
	private StudentsResponsesService studentsResponsesService;

	@Autowired
	private ResponseScoringUtil responseScoringUtil;

	@Autowired
	private TestCollectionService testCollectionService;

	@Autowired
	private ReportGenerator reportGenerator;

	@Autowired
	private ContentAreaService contentAreaService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UploadSpecification uploadSpecification;
	/**
	 * permissionUtil.
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
    private StudentService studentService;
	
    @Autowired
    private EnrollmentService enrollmentService;
    
	@Autowired
	private OrganizationService organizationService;
	/**
	 * sessionRulesConfiguration
	 */
	@Autowired
	private SessionRulesConfiguration sessionRulesConfiguration;

	@Value("${testsession.status.closed}")
	private String COMPLETE_CATEGORY_CODE;

	@Value("${testsession.status.type}")
	private String TEST_STATUS_TYPE;

	@Value("${email.path}")
	private String uploadPath;
	
	@Value("${mail.domain}")
	private String domain;

	@Value("${print.test.file.path}")
	private String REPORT_PATH;

	@Value("${report.assessmentprogram.name}")
	private String ASSESSMENT_PROGRAM_KAP;

	@Value("${report.subject.math.name}")
	private String CONTENT_AREA_MATH;

	@Value("${report.subject.reading.name}")
	private String CONTENT_AREA_ELA;

	/**
	 * iti TestSession Service
	 */
	@Autowired
	private ItiTestSessionService itiTestSessionService;

	/**
	 * User organization for DLM
	 */
	@Value("${user.organization.DLM}")
	private String USER_ORG_DLM;

	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;

	@Autowired
	private StudentsTestsStatusConfiguration studentsTestsStatusConfiguration;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private MicroMapService microMapService;
	/**
	 * studentReportService.
	 */
	@Autowired
	private StudentReportService studentReportService;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private RosterService rosterService;

	/**
	 * Render method for the roster reporting page.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "rosterReporting.htm")
	public final ModelAndView viewRosterReport() {
		ModelAndView mav = new ModelAndView("rosterReporting");

		return mav;
	}

	@RequestMapping(value = "checkForCompletedStudents.htm")
	public final @ResponseBody Integer checkForCompletedStudents(
			@RequestParam long testSessionId) {

		// US14114 Technical: Fix Test Monitor and other TDE services dependency
		// Retrieve student responses and don't change the order of below to
		// calls.
		// first we need to get the responses and then only get the count.
		// studentsResponsesService.retrieveStudentsResponses(testSessionId);
		Integer completedStudentsCount = studentReportService
				.getCompletedStudentsCount(testSessionId,
						studentsTestsStatusConfiguration
								.getClosedStudentsTestsStatusCategory().getId());

		if (completedStudentsCount > 0) {
			// check if completed students are still enrolled
			Integer count = studentReportService
					.getCompletedAndEnrolledStudentsCount(testSessionId,
							studentsTestsStatusConfiguration
									.getClosedStudentsTestsStatusCategory()
									.getId());
			if (count > 0) {
				return 1;// completed students
			} else {
				return 2;// completed students are now unenrolled - exit student
							// occurred
			}
		} else {
			return 0;

		}
	}

	@RequestMapping(value = "getNewReport.htm")
	@ResponseBody
	public final Map<String, Object> getNewReport(
			@RequestParam(value = "roster", required = false) final Long roster,
			final HttpServletResponse response, final HttpServletRequest request)
			throws Exception {
		logger.trace("Entering the getReport() method");
		Map<String, Object> parentReportMap = new HashMap<String, Object>();

		String reportCategory = request.getParameter("reportCategory");
		Long assessmentProgramId = Long.valueOf(request
				.getParameter("assessmentProgram"));
		AssessmentProgram ap = assessmentProgramService
				.findByAssessmentProgramId(assessmentProgramId);
		if (reportCategory.equals("PARENT_REPORT")
				&& ap.getProgramName().equals(ASSESSMENT_PROGRAM_KAP)) {
			Long contentAreaId = null;
			if (request.getParameter("contentArea") != null) {
				contentAreaId = Long.valueOf(request
						.getParameter("contentArea"));
			}
			// parentReportMap.
			ContentArea contentArea = contentAreaService
					.selectByPrimaryKey(contentAreaId);
			if (contentArea.getAbbreviatedName().equals(CONTENT_AREA_ELA)
					|| contentArea.getAbbreviatedName().equals(
							CONTENT_AREA_MATH)) {
				parentReportMap.put("reportType", "PARENT_REPORT");
				List<String> fileList = new ArrayList<String>();
				fileList.add("Grade 3");
				fileList.add("Grade 4");
				fileList.add("Grade 5");
				fileList.add("Grade 6");
				fileList.add("Grade 7");
				fileList.add("Grade 8");
				fileList.add("Grade 9-12");
				parentReportMap.put("files", fileList);
			} else {
				parentReportMap.put("reportType", "NOT_IMPLEMENTED_REPORT");
			}

		} else if (reportCategory.equals("STUDENT")) {
			//String stateStudentIdentifier = request.getParameter("student");
			String studentID = request.getParameter("student");
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_STUDENT_VIEW");
	        parentReportMap.put("reportType", "STUDENT_REPORT");
	        List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
	        if(request.getParameter("school") != null){
	        	 Long schoolId = Long.valueOf(request.getParameter("school"));
	        	 if(studentPermission && allChilAndSelfOrgIds.contains(schoolId)){
	 				AssessmentProgram assessmentProgram = assessmentProgramService
	 						.findByAssessmentProgramId(assessmentProgramId);
	 				if (assessmentProgram.getProgramName().equalsIgnoreCase(
	 						USER_ORG_DLM)) {
	 					if (request.getParameter("student") != null) {
	 						
	 						SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	 						StudentReportDto studentReportDto = new StudentReportDto();
	 						String studentName_StateStudID[] = request.getParameter("studentName").split("\\(ID:");
	 						studentReportDto.setStudentFirstName(studentName_StateStudID[0]);
	 						studentReportDto.setContentAreaName(request.getParameter("contentAreaName"));
	 						studentReportDto.setDistrictName(request.getParameter("districtName"));
	 						studentReportDto.setReportDate(dateFormat.format(new Date()));
	 						studentReportDto.setSchoolName(request.getParameter("schoolName"));
	 						studentReportDto.setStateName(request.getParameter("stateName"));
	 						studentReportDto.setGradeName(request.getParameter("gradeName"));
	 						studentReportDto.setStateStudentIdentifier(studentName_StateStudID[1].substring(0, studentName_StateStudID[1].length()-1));
	 						studentReportDto.setStudentId(Long.parseLong(studentID));
	 						studentReportDto.setSchoolId(schoolId);
	 						studentReportDto.setClaimsConceptualData(genereateStudentReportData(studentID));
	 						parentReportMap.put("studentReportData", studentReportDto);
	 					}
	 				}
	 	        }	 	   
	        }
		} 
		/**
		 * @author bmohanty_sta
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
		 * Build Roster Report for DLM organizations.
		 */
		else if (reportCategory.equals("ROSTER")) {
			parentReportMap.put("reportType", "ROSTER_REPORT");
			Long schoolId = Long.valueOf(request.getParameter("school"));
			Long rosterId = Long.valueOf(request.getParameter("roster"));
			Long contentAreaId = Long.valueOf(request.getParameter("contentAreaId"));
			String[] studentIds = request.getParameterValues("students[]");
			String schoolName = request.getParameter("schoolName");
			String contentAreaName = request.getParameter("contentAreaName");
			String[] studentNames = request.getParameterValues("studentNames[]");
			String rosterName = request.getParameter("rosterName");
			String resourcePath = request.getRequestURL().toString().replace(request.getServletPath(), "");
			RosterReport rosterReport = generateRosterReport(assessmentProgramId, schoolId, 
					rosterId, studentIds, schoolName, contentAreaName, studentNames
					, rosterName, resourcePath, contentAreaId);
			/**
			 * Pass final data to UI.
			 */
			parentReportMap.put("rosterReportData", rosterReport);
		} else {
			parentReportMap.put("reportType", "NOT_IMPLEMENTED_REPORT");
		}
		logger.trace("Leaving the getReport() method.");
		return parentReportMap;
	}
	
	@RequestMapping(value = "getRosterReportPDF.htm")
	public final void getRosterReportPDF(@RequestParam("schoolId") Long schoolId,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("rosterName") String rosterName,
			@RequestParam("studentIds") String studentIdsParam,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("contentAreaName") String contentAreaName,
			@RequestParam("studentNames") String studentNamesParam,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("contentAreaId") Long contentAreaId,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		
		String[] studentNames = studentNamesParam.split("--");
		String[] studentIds = studentIdsParam.split("--");
		String resourcePath = request.getRequestURL().toString().replace(request.getServletPath(), "");
		RosterReport rosterReport = generateRosterReport(assessmentProgramId, schoolId, rosterId
				, studentIds, schoolName, contentAreaName, studentNames
				, rosterName, resourcePath, contentAreaId);
		OutputStream out = response.getOutputStream();
 		response.setContentType("application/pdf");
 		String fileName = rosterName + ".pdf";
 		response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
 	    //String serverPath = request.getSession().getServletContext().getRealPath("/");
 	    String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
 	    		request.getServletContext().getContextPath() + "/";
 		try {
 			setupRosterReportPDFGeneration(rosterReport, out, serverPath);
 		} catch (Exception e) {
 			logger.error("Caught {} in getStudentReportPDF() method.", e);
 		}finally {
 			if (out != null) {
 				out.close();
 			}
 		}
	}
	
	private void setupRosterReportPDFGeneration(
			RosterReport rosterReport, OutputStream out, String serverPath)
			throws Exception {
				XStream xstream = new XStream();
				xstream.alias("rosterReportData", RosterReport.class);
				
				/*XStream xstream1 = new XStream(new DomDriver());
				xstream1.alias("rosterReportData", RosterReport.class);
				logger.debug("XML Data="+xstream1.toXML(rosterReport));*/
				
				TraxSource source = new TraxSource(rosterReport, xstream);
				Resource resource = resourceLoader.getResource("/templates/xslt/rosterreport.xsl");
				PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
	}
	
	private RosterReport generateRosterReport(Long assessmentProgramId, Long schoolId, Long rosterId, 
			String[] studentIds, String schoolName, String contentAreaName, String[] studentNames, 
			String rosterName, String resourcePath, Long contentAreaId){
		/**
		 * Check that user has roster report view permission.
		 */
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean rosterPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_ROSTER_VIEW");
        List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
		RosterReport rosterReport = new RosterReport();
        if(schoolId != null){
        	 if(rosterPermission && allChilAndSelfOrgIds.contains(schoolId)){
 				AssessmentProgram assessmentProgram = assessmentProgramService
 						.findByAssessmentProgramId(assessmentProgramId);
 				if (assessmentProgram.getProgramName().equalsIgnoreCase(
 						USER_ORG_DLM)) {
 					if (rosterId != null) {
 						
 						/**DE8996 and DE9001: Retrieve students using Student ID instead of State Student Identifier. 
 						 * This step can be eliminated by passing student IDs from the client instead of state student IDs 
 						 * so that even if any duplicate state student identifiers exist in the database, the correct student IDs will be used
 						 * In short, eliminates TooManyResultsException.
 						 **/
 						/**
 						 * Get selected students for which report to be generated.
 						 */ 
 						List<String> studentStateIdStrList = new ArrayList<String>();
 						for(String item : studentNames){
 							String studentName_StateStudID[] = item.split("\\(ID:");
 							studentStateIdStrList.add(studentName_StateStudID[1].substring(0, studentName_StateStudID[1].length()-1));
						}

 						/**
 						 * Get the student ids from given list of stundents state identifier
 						 */
 						Map<Long, String> studentsMap = new HashMap<Long, String>();
 						for(int i=0;i<studentStateIdStrList.size();i++){
 							studentsMap.put(new Long(studentIds[i]), studentStateIdStrList.get(i));
 						}
 						
 						List<Long> students = new ArrayList<Long>();
 						if(studentsMap != null && studentsMap.size()>0){
 							for(Long k : studentsMap.keySet()){
 								students.add(k);
 							}
 						}
 						
						List<String> studentNamesList = null;
						if(studentNames != null && studentNames.length>0){
							studentNamesList = new ArrayList<String>();
 							for(String stuName : studentNames){
 								studentNamesList.add(stuName);
 							} 								
						}

 						 				
 						Set<Long> studentsHavePlansSet = new HashSet<Long>();
 						
 						/**
 						 * Based on selected school, roster and list of students, get all the essential elements with corresponding data
 						 * like most recent assessed EE, all currrent instruction goal EEs etc.
 						 */
 						int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
 						List<ItiRosterReportEE> eedata = itiTestSessionService.getITIDLMEEDetailsByRosterIdAndStudents(schoolId, rosterId, students, contentAreaId, currentSchoolYear);
 						
 						/**
 						 * Build a map that contains the the above EE details list based on grade and student combination as key.
 						 */
 						Map<String, List<ItiRosterReportEE>> eedatamap = new HashMap<String, List<ItiRosterReportEE>>();	 						
 						Set<String> gradeStudents = new LinkedHashSet<String>();
 						Map<Long, String> studentNameMap = new HashMap<Long, String>();
 						if(eedata!=null && eedata.size()>0){
 							for(ItiRosterReportEE eerow : eedata){
 								eerow.setIcon(eerow.getIconBySubject(contentAreaName));
 								eerow.setEndDateTimeStr(eerow.getEndDateTimeStr());
 								gradeStudents.add(eerow.getGradeName()+"-"+eerow.getStudentId());
 								studentNameMap.put(eerow.getStudentId(), eerow.getStudentName());
 								studentsHavePlansSet.add(eerow.getStudentId());
 								String key = eerow.getGradeName()+"-"+eerow.getStudentId();
 								if(eedatamap.get(key) == null){
 									List<ItiRosterReportEE> alist = new ArrayList<ItiRosterReportEE>();
 									alist.add(eerow);
 									eedatamap.put(key, alist);
 								} else {
 									List<ItiRosterReportEE> alist = (ArrayList<ItiRosterReportEE>) eedatamap.get(key);
 									alist.add(eerow);
 									eedatamap.put(key, alist);
 								}
 							}
 						}
 						
 						/**
 						 * Get educator information to be displayed in the report. This is based on roster id.
 						 */
 						Educator educator = rosterService.getEducatorByRosterId(rosterId);
 						SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
 						rosterReport.setReportDate(dateFormat.format(new Date()));
 						rosterReport.setEducatorId(educator.getEducatorId());
 						rosterReport.setEducatorName(educator.getEducatorName());
 						rosterReport.setSchoolName(schoolName);
 						rosterReport.setContentAreaName(contentAreaName);
						rosterReport.setStudentStateIds(StringUtils.join(studentStateIdStrList,"--"));
						rosterReport.setStudentIds(StringUtils.join(studentIds,"--"));
						rosterReport.setStudentNames(StringUtils.join(studentNamesList,"--"));
						rosterReport.setRosterId(rosterId);
						rosterReport.setRosterName(rosterName);
						rosterReport.setAssessmentProgramId(assessmentProgramId);
						rosterReport.setSchoolId(schoolId);
						rosterReport.setResourcePath(resourcePath);
						rosterReport.setContentAreaId(contentAreaId);
						
 						
 						/**
 						 * Each grade and student combination will be unique for a roster. So from previously built set,
 						 * extract the grade and student information and use it to get the data from eedatamap. then populate required beans
 						 * to be displayed in UI.
 						 */
 						if(gradeStudents !=null && gradeStudents.size()>0){
 							ArrayList<RosterReportGradeStudentData> gradeStudentDataList = new ArrayList<RosterReportGradeStudentData>();
 							for(String gradeStudent : gradeStudents){
 								String gradeName = gradeStudent.split("-")[0];
 								String studentIdStr = gradeStudent.split("-")[1];
 								Long studentId = new Long(studentIdStr);
 								
 								RosterReportGradeStudentData gradeStudentData = new RosterReportGradeStudentData();
 								gradeStudentData.setGradeCourseName(gradeName);
		 						gradeStudentData.setStudentName(studentNameMap.get(studentId));
 								
		 						List<ItiRosterReportEE> eeDataList = eedatamap.get(gradeName+"-"+studentId);
		 						gradeStudentData.setEeDataList(eeDataList);
		 						gradeStudentDataList.add(gradeStudentData);
	 						}
 							rosterReport.setGradeStudentDataList(gradeStudentDataList);
 							
 							List<Long> studentsFoundList = new ArrayList<Long>();
 							if(studentsHavePlansSet != null && studentsHavePlansSet.size()>0 
 									&& students != null && students.size()>0){
 								
 								studentsFoundList.addAll(studentsHavePlansSet);
 								if(studentsFoundList != null && studentsFoundList.size()>0){
 									for(Long st : studentsFoundList){
 										studentsMap.remove(st);
 										students.remove(st);
 									}
 								}
 							}
 						} 
 						
 						if(studentsMap != null && studentsMap.size()>0){
							List<String> noPlansMessages = new ArrayList<String>(); 									
							if(studentNames != null && studentNames.length>0){
								for(Long st : students){
									String studentStId = studentsMap.get(st);
									for(String stuName : studentNames){
										if(stuName.contains(studentStId)){
											noPlansMessages.add("No instructional plans exist in Educator Portal for " + stuName + " and " + contentAreaName);
										}
									}	 										
								}	 										
							}
							if(noPlansMessages != null && noPlansMessages.size()>0){
								rosterReport.setNoPlanMessages(noPlansMessages);
							}
						}
 					}
 				}
 	        }	 	   
        }
        return rosterReport;
	}

	@RequestMapping(value = "getParentReportPDF.htm")
	public final void getParentReportPDF(@RequestParam String reportFileName,
			final HttpServletResponse response, final HttpServletRequest request) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		boolean parentReportPermission = permissionUtil.hasPermission(
				userDetails.getAuthorities(), "PERM_REPORT_PERF_PARENT_VIEW");
		if (parentReportPermission) {
			String reportPDFName = "Parent_Report_ELA_MATH_"
					+ reportFileName.replace(" ", "");
			File pdfFile = new File(REPORT_PATH + java.io.File.separator
					+ "parent_reports" + java.io.File.separator + reportPDFName
					+ ".pdf");
			if (pdfFile.exists()) {
				InputStream inputStream = null;
				try {
					response.setContentType("application/force-download");
					response.addHeader("Content-Disposition",
							"attachment; filename=" + reportPDFName + ".pdf");
					inputStream = new FileInputStream(pdfFile);
					IOUtils.copy(inputStream, response.getOutputStream());
					response.flushBuffer();
				} catch (FileNotFoundException e) {
					logger.error("Parent Report PDF file not Found. File - "
							+ reportPDFName);
					logger.error("FileNotFoundException : ",e);
				} catch (IOException e) {
					logger.error("Error occurred while downloading parent report pdf file. File - ."
							+ reportPDFName);
					logger.error("IOException : ",e);
				} finally {
					if(inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							logger.error("ignore IOException closing: ",e);
						}
					}
				}
			} else {
				logger.debug("Parent Report PDF file not Found. File - "
						+ reportPDFName);
			}
		}
	}

	/**
	 * Returns a RawScoreReport for the test session that was selected.
	 * 
	 * @param testSessionId
	 *            long
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	@RequestMapping(value = "getReport.htm")
	public final void getReport(@RequestParam long testSessionId,
			final HttpServletResponse response, final HttpServletRequest request) {
		logger.trace("Entering the getReport() method");
		logger.debug("Creating PDF Report for Test Session with id : {}",
				testSessionId);
		StringBuffer path = request.getRequestURL();
		String imagepath = path.toString();
		imagepath = imagepath.replace("getReport.htm", "");
		UserDetailImpl userDetails = null;
		List<String> reponsesCheck = new ArrayList<String>();
		List<String> responsesQuestions = new ArrayList<String>();
		List<String> responsesQuestion = new ArrayList<String>();
		List<String> testSectionNames = new ArrayList<String>();
		List<String> sectionNames = new ArrayList<String>();
		String filesPaths = "";
		int numOfGraphs = 0;
		OutputStream out = null;

		List<DefaultCategoryDataset> datasets = new ArrayList<DefaultCategoryDataset>();
		List<JFreeChart> charts = new ArrayList<JFreeChart>();
		List<Integer> responsesPerQuestion = new ArrayList<Integer>();
		// List<String> variableResponses = new ArrayList<String>();
		List<String> variableResponsesLegend = new ArrayList<String>();
		int totalNoOfQuestions = 0;
		String responses = "";
		int k = 0;
		int l = 0;
		Map<String, String> questionsResponsesMap = new TreeMap<String, String>();

		// US14114 Technical: Fix Test Monitor and other TDE services dependency
		// Retrieve student responses and don't change the order of below to
		// calls.
		// first we need to get the responses and then only get the
		// studentTestSessionDtos.
		// studentsResponsesService.retrieveStudentsResponses(testSessionId);
		userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		List<StudentTestSessionDto> studentTestSessionDtos = studentReportService
				.getTestSessionReportForPdf(testSessionId,
						studentsTestsStatusConfiguration
								.getClosedStudentsTestsStatusCategory().getId(),organizationId);
		List<StudentResponseReport> studentResponseReports = responseScoringUtil
				.getStudentResponseReport(studentTestSessionDtos);

		List<GraphScoreReport> graphScoreReportCheck = responseScoringUtil
				.createDataSetGraphs(studentTestSessionDtos);
		for (GraphScoreReport graphScoreReport : graphScoreReportCheck) {
			String correctreesponses = graphScoreReport.getCorrectResponses();
			String numOfQuestions = graphScoreReport.getNumOfResponses();
			List<Integer> testSections = graphScoreReport.getTestSectionsList();
			List<Long> taskVariantIds = graphScoreReport.getTaskVariantIds();
			testSectionNames = graphScoreReport.getTestSectionNames();
			String numberOfResponses = "";
			int noOfTestSections = testSections.size();
			String[] correctResponses = correctreesponses.split(";");
			String[] questions = numOfQuestions.split(";");
			int responseLength = questions.length;

			for (int i = 0; i < (responseLength); i++) {
				for (k = i; (k < correctResponses.length); k = k
						+ responseLength) {
					responses += correctResponses[k] + ";";
					numberOfResponses += correctResponses[k] + ";";
				}
				responsesQuestions.add(responses);
				// questionsResponsesMap.put(questions[i],responses);
				responses = "";
			}
			for (String tokens : responsesQuestions) {
				String[] questionArrCheck = tokens.split(";");
				List<String> questionArr = new ArrayList<String>();
				for (String token : questionArrCheck) {
					questionArr.add(token);
				}
				for (String token : questionArr) {
					if (!(variableResponsesLegend.contains(token))) {
						variableResponsesLegend.add(token);
					}
				}
			}
			Collections.sort(variableResponsesLegend);
			int questionNum = 0;
			int testsectionlevel = 0;
			int numtestSection = 0;
			int numSection = 0;
			int count = 10;
			int check1 = 0;
			// Constructing the datasets for each graph
			for (Integer testSection : testSections) {
				totalNoOfQuestions = totalNoOfQuestions + testSection;
			}
			totalNoOfQuestions = totalNoOfQuestions - 1;
			logger.debug("totalNoOfQuestions: " + totalNoOfQuestions);

			for (Integer testSection : testSections) {
				numtestSection = numtestSection + testSection;
				check1++;
				List<String> testSectionArr = new ArrayList<String>();

				for (int i = 0; i < testSection; i = i + count) {
					DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					if (testSection < count) {
						testSectionArr = responsesQuestions.subList(
								testsectionlevel, numtestSection);
						// logger.debug("Testsection :"+check1+
						// ": Check 2"+testsectionlevel +" : " +
						// numtestSection);
						testsectionlevel = numtestSection;
						// logger.debug("Testsection :"+check1+": Check 1"+testsectionlevel
						// +" : " + numtestSection);
						sectionNames.add(testSectionNames.get(numSection));
					} else if ((testsectionlevel + count) > numtestSection) {
						testSectionArr = responsesQuestions.subList(
								testsectionlevel, numtestSection);
						// logger.debug("Testsection :"+check1+
						// ": Check 2"+testsectionlevel +" : " +
						// numtestSection);
						testsectionlevel = numtestSection;
						sectionNames.add(testSectionNames.get(numSection));
					} else {
						testSectionArr = responsesQuestions.subList(
								testsectionlevel, testsectionlevel + count);
						// logger.debug("Testsection :"+check1+
						// ":Check 3"+testsectionlevel +" : " +
						// (testsectionlevel + count));
						testsectionlevel = testsectionlevel + count;
						sectionNames.add(testSectionNames.get(numSection));
					}
					for (String question : testSectionArr) {
						List<String> responseValOrder = new ArrayList<String>();
						List<String> responseZero = new ArrayList<String>();
						List<String> questionArr = new ArrayList<String>();
						String[] questionArrCheck = question.split(";");

						for (String token : questionArrCheck) {
							questionArr.add(token);
						}

						int responseCount = 0;
						for (String val : variableResponsesLegend) {
							// logger.debug("Occurences of"+ val +
							// " : "+java.util.Collections.frequency(questionArr,
							// val));
							int check = java.util.Collections.frequency(
									questionArr, val);
							if (questionNum <= totalNoOfQuestions) {
								if (check > 0)
									dataset.addValue(check, val,
											questions[questionNum]);
								else
									dataset.addValue(null, val,
											questions[questionNum]);
							}
							responseCount++;
						}
						questionNum++;

					}

					datasets.add(dataset);
					numOfGraphs++;
				}

				numSection++;
			}

		}

		int responsesSize = variableResponsesLegend.size();
		List<String> sectionNameAndChartNumber = new ArrayList<String>();
		String lastSectionName = sectionNames.get(0);
		int chartNumber = 0;
		// Create charts for each datasets
		for (int j = 0; j < numOfGraphs; j++) {

			JFreeChart chart = createMutipleChartPlots(datasets.get(j),
					sectionNames.get(j), responsesSize,
					studentTestSessionDtos.size());
			charts.add(chart);

			if (sectionNames.get(j).equals(lastSectionName)) {
				chartNumber++;
			} else {
				chartNumber = 1;
			}
			sectionNameAndChartNumber.add(sectionNames.get(j) + ":"
					+ chartNumber);
			lastSectionName = sectionNames.get(j);

		}

		File[] imgFiles = new File[numOfGraphs];
		// Save all the chart images generated
		for (int j = 0; j < numOfGraphs; j++) {
			String uniqueFolder = "";
			String filePath = "";
			uniqueFolder = userDetails.getUserId()
					+ ParsingConstants.INNER_DELIM + System.nanoTime()
					+ ParsingConstants.JPEG;
			filePath = uploadPath + java.io.File.separator + uniqueFolder;
			File file1 = new File(filePath);
			// Constructing the string for image file paths, sectionNames and
			// chartNumbers
			filesPaths += filePath + "|" + sectionNameAndChartNumber.get(j)
					+ ";";
			try {

				ChartUtils.saveChartAsJPEG(file1, charts.get(j), 600, 375);
				imgFiles[j] = file1;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {

			out = response.getOutputStream();

			TestSession testSession = testSessionService
					.findWithAssociationsByPrimaryKey(testSessionId);
			RawScoreReport rawScoreReport = new RawScoreReport();

			// TODO do not make 2 service calls from controller.

			// List<StudentTestSessionDto> studentTestSessionDtos =
			// studentReportService.getTestSessionReportForPdf(testSessionId);
			logger.debug("Retrieved studentTestSessionDtos {}",
					studentTestSessionDtos);

			List<StudentRawScore> rawScores = responseScoringUtil
					.computeRawScoreFromDto(studentTestSessionDtos);
			logger.debug(
					"Converted retrieved StudentTestResponses to StudentRawScores: {}",
					rawScores);

			int numQuestions = rawScores.size() > 0 ? rawScores.get(0)
					.getNumQuestions() : 0;

			List<StudentProblemReport> studentProblemReports = responseScoringUtil
					.computeStudentProblemReport(studentTestSessionDtos,
							numQuestions, imagepath);
			logger.debug(
					"Converted retrieved StudentTestResponses to studentProblemReports: {}",
					studentProblemReports);

			TestCollection testCollection = testCollectionService
					.selectByTestSession(testSession.getId());

			rawScoreReport.setStudentScores(rawScores);
			rawScoreReport.setStudentProblemReports(studentProblemReports);
			rawScoreReport.setTestName(testCollection.getName());
			rawScoreReport.setRosterName(testSession.getRoster()
					.getCourseSectionName());
			rawScoreReport
					.setSubject(testCollection.getContentArea().getName());
			rawScoreReport.setGrade(testCollection.getGradeCourse().getName());
			rawScoreReport.setGraphImage(filesPaths);
			rawScoreReport.setStudentResponseReports(studentResponseReports);
			logger.debug(filesPaths);
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			rawScoreReport.setReportName(testCollection.getContentArea()
					.getName()
					+ "_"
					+ testCollection.getGradeCourse().getName()
					+ "_"
					+ dateFormat.format(now));
			rawScoreReport.setDate(dateFormat.format(now));

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ rawScoreReport.getReportName() + ".pdf");

			logger.debug(
					"Creating report {}, passing onto ReportGenerator for processing",
					rawScoreReport);
     	    String serverPath = request.getSession().getServletContext().getRealPath("/");
			reportGenerator.generateRawScoreReport(rawScoreReport, out, imgFiles, serverPath);

		} catch (IOException e) {
			logger.error(
					"Caught IOException while trying to generate the report. Printing StackTrace: {}",
					e.getMessage());
		} catch (FOPException e) {
			logger.error(
					"Caught FOPException while trying to generate the report. Printing StackTrace: {}",
					e.getMessage());
		} catch (Exception e) {
			logger.error(
					"Caught TransformerException while trying to generate the report. Printing StackTrace: {}",
					e.getMessage());
		}

		logger.trace("Leaving the getReport() method.");
	}

	/**
	 * 
	 * @param rosterId
	 *            long
	 * @return List<TestSession>
	 */
	@RequestMapping(value = "getTestSessionsForRoster.htm")
	public final @ResponseBody List<TestSession> getEndedTestSessionsForRoster(
			@RequestParam Long rosterId) {
		logger.trace("Entering the getEndedTestSessionsForRoster() method.");
		logger.debug(
				"Entered getEndedTestSessionsForRoster with parameter rosterId: {}",
				rosterId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		boolean qcPermission = permissionUtil.hasPermission(userDetails
				.getAuthorities(), RestrictedResourceConfiguration
				.getQualityControlCompletePermission());
		logger.debug("QUALITY_CONTROL_COMPLETE_permission:" + qcPermission);
		// TestSessionExample example = new TestSessionExample();
		// TestSessionExample.Criteria criteria = example.createCriteria();

		/*
		 * This method does not currently find test sessions that are ended, but
		 * returns all test sessions for a given roster. The decision to do this
		 * was made for the November 2012 pilots because TDE was unprepared to
		 * handle ending a test session and thus removing the student from the
		 * test session. Once TDE has completed that functionality the search
		 * for only ended test sessions should be re-added.
		 */
		// Category endedStatus =
		// categoryService.selectByCategoryCodeAndType(COMPLETE_CATEGORY_CODE,
		// TEST_STATUS_TYPE);
		// criteria.andRosterIdEqualTo(rosterId);
		// criteria.andStatusIdEqualTo(endedStatus.getId());

		List<TestSession> endedSessions = null;
		Long categoryId = sessionRulesConfiguration
				.getManualEnrollmentCategory().getId();

		// logger.debug("Searching for test sessions by example {}", example);

		if (qcPermission) {
			endedSessions = testSessionService.selectByRosterAndCategory(
					rosterId, categoryId, sessionRulesConfiguration
							.getSystemEnrollmentCategory().getId());
		} else {
			endedSessions = testSessionService.selectByRosterAndCategory(
					rosterId, categoryId);
		}

		logger.debug("Found test sessions: {}", endedSessions);
		logger.trace("Returning found test sessions to client.");
		return endedSessions;
	}

	/**
	 * 
	 * @return JFreeChart
	 */

	public JFreeChart createMutipleChartPlots(DefaultCategoryDataset dataset,
			String testSectionName, int responsesSize, int numStudents) {
		logger.trace("Entering the createMutipleChart() method.");
		// final JFreeChart chart2 =
		// ChartFactory.createBarChart("Student Response","Questions","Frequency"
		// ,dataset, PlotOrientation.VERTICAL, true, true, true);

		final JFreeChart chart = ChartFactory.createBarChart(
				"Student Response : " + testSectionName, "Questions",
				"Frequency", dataset, PlotOrientation.VERTICAL, true, true,
				true);
		chart.getTitle().setFont(new Font("Tahoma", Font.PLAIN, 13));
		BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer br = (BarRenderer) plot.getRenderer();

		br.setItemMargin(0);
		CategoryAxis domain = plot.getDomainAxis();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setRange(0, numStudents + 1);
		// rangeAxis.setAutoRangeIncludesZero(false);
		domain.setLowerMargin(0.1);
		domain.setUpperMargin(0.1);
		// br.setMinimumBarLength(1.65);

		br.setSeriesPaint(0, new Color(231, 175, 61));
		// br.setSeriesPaint(1, Color.green);
		for (int i = 0; i < responsesSize; i++) {
			br.setSeriesItemLabelGenerator(
					i,
					new StandardCategoryItemLabelGenerator("{2}", NumberFormat
							.getIntegerInstance()));
			br.setSeriesItemLabelsVisible(i, true);
		}
		chart.getCategoryPlot().setRenderer(br);

		return chart;
	}

	private List<StudentRptCCArea> genereateStudentReportData(String studentID) {
    	List<ItiStudentReport> itiTestHistroyData = itiTestSessionService.selectByStudentIdAndUnUsedStatus(Long.parseLong(studentID));
		List<MicroMap> microMapData = microMapService.selectMicroMapByItiHistoryStudentId(Long.parseLong(studentID));
		Map<String, String> eeLMMacroMap = new HashMap<String, String>();
		for (MicroMap microMapRow : microMapData) {
			eeLMMacroMap.put(microMapRow.getContentFrameworkDetailId()+ microMapRow.getLinkageLabel(),microMapRow.getLinkageLevelShortDesc());
		}
		List<StudentRptCCArea> reportData = new ArrayList<StudentRptCCArea>();
		for (ItiStudentReport itiStudentReport : itiTestHistroyData) {
			StudentRptCCArea claimConceptualArea = new StudentRptCCArea();
			if(StringUtils.isNotEmpty(itiStudentReport.getClaim())) {
				claimConceptualArea.setClaim(StringUtils.split(itiStudentReport.getClaim()," - ")[0]);
			} else {
				claimConceptualArea.setClaim(StringUtils.EMPTY);
			}
			if(StringUtils.isNotEmpty(itiStudentReport.getConceptualArea())) {
				claimConceptualArea.setConceptualArea(StringUtils.split(itiStudentReport.getConceptualArea(), " - ")[1]);
			} else {
				claimConceptualArea.setConceptualArea(StringUtils.EMPTY);
			}
			List<StudentRptEssentialElement> eEssentialElementList = new ArrayList<StudentRptEssentialElement>();
			for (ItiStudentReportEE itiStudentReportEE : itiStudentReport.getItiStudentReportEE()) {
				
				StudentRptEssentialElement essentialElement = new StudentRptEssentialElement();
				String[] eElementString = itiStudentReportEE.getEssentialElement().split(" - ");
				essentialElement.setEeCode(eElementString[0]);
				essentialElement.setEeDesc(eElementString[1]);
				long eElementId = itiStudentReportEE.getEssentialElementId();
				
				StudentRptLinkageLevel initialPrecursor = new StudentRptLinkageLevel();
				initialPrecursor.setAdministered("");
				initialPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Initial Precursor"));
				initialPrecursor.setLinkageLevel("Initial Precursor");

				StudentRptLinkageLevel distalPrecursor = new StudentRptLinkageLevel();
				distalPrecursor.setAdministered("");
				distalPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Distal Precursor"));
				distalPrecursor.setLinkageLevel("Distal Precursor");
				distalPrecursor.setSessionLevel("no");
				
				StudentRptLinkageLevel proximalPrecursor = new StudentRptLinkageLevel();
				proximalPrecursor.setAdministered("");
				proximalPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Proximal Precursor"));
				proximalPrecursor.setLinkageLevel("Proximal Precursor");
				proximalPrecursor.setSessionLevel("no");
				
				StudentRptLinkageLevel target = new StudentRptLinkageLevel();
				target.setAdministered("");
				target.setDesc(eeLMMacroMap.get(eElementId + "Target"));
				target.setLinkageLevel("Target");
				target.setSessionLevel("no");

				StudentRptLinkageLevel successor = new StudentRptLinkageLevel();
				successor.setAdministered("");
				successor.setDesc(eeLMMacroMap.get(eElementId + "Successor"));
				successor.setLinkageLevel("Successor");
				successor.setSessionLevel("no");

				for (ItiTestSessionHistory itiTestSessionHistoryRow : itiStudentReportEE.getItiTestSessionHistory()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
					String administeredDate = "";
					if (itiTestSessionHistoryRow.getAdministeredDate() != null) {
						administeredDate = dateFormat.format(itiTestSessionHistoryRow.getAdministeredDate());
					}  
					if(initialPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						initialPrecursor.setSessionLevel("yes");
						initialPrecursor.setAdministered(administeredDate);
					}
					if(distalPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						distalPrecursor.setSessionLevel("yes");
						distalPrecursor.setAdministered(administeredDate);
					}
					if(proximalPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						proximalPrecursor.setSessionLevel("yes");
						proximalPrecursor.setAdministered(administeredDate);
					}
					if(target.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						target.setSessionLevel("yes");
						target.setAdministered(administeredDate);
					}
					if(successor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						successor.setSessionLevel("yes");
						successor.setAdministered(administeredDate);
					}
				}
				List<StudentRptLinkageLevel> linkageLevels = new ArrayList<StudentRptLinkageLevel>();
				linkageLevels.add(initialPrecursor);
				linkageLevels.add(distalPrecursor);
				linkageLevels.add(proximalPrecursor);
				linkageLevels.add(target);
				linkageLevels.add(successor);
				essentialElement.setLevelDetails(linkageLevels);
				eEssentialElementList.add(essentialElement);
			}
			claimConceptualArea.seteEList(eEssentialElementList);
			reportData.add(claimConceptualArea);
		}
		return reportData;

	}

	@RequestMapping(value = "getStudentReportPDF.htm")
	public final void getStudentReportPDF(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier,
			@RequestParam("studentId") String studentId,
			@RequestParam("studentName") String studentName,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("districtName") String districtName,
			@RequestParam("stateName") String stateName,
			@RequestParam("contentAreaName") String contentAreaName,
			@RequestParam("schoolId") Long schoolId,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_STUDENT_VIEW");
        if(schoolId != null){
	     List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
       	 if(studentPermission && allChilAndSelfOrgIds.contains(schoolId)){
         	SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
     		StudentReportDto studentReportDto = new StudentReportDto();
     		String[] studentNameString = request.getParameter("studentName").split(" ");
     		studentReportDto.setStudentFirstName(studentNameString[0]);
     		studentReportDto.setStudentLastName(studentNameString[1]);
     		studentReportDto.setContentAreaName(request.getParameter("contentAreaName"));
     		studentReportDto.setDistrictName(request.getParameter("districtName"));
     		studentReportDto.setReportDate(dateFormat.format(new Date()));
     		studentReportDto.setSchoolName(request.getParameter("schoolName"));
     		studentReportDto.setStateName(request.getParameter("stateName"));
     		studentReportDto.setGradeName(request.getParameter("gradeName"));
     		studentReportDto.setStateStudentIdentifier(stateStudentIdentifier);
        	//int schoolYear = enrollmentService.getContractingOrgSchoolYear(schoolId);
     		int schoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        	studentReportDto.setSchoolYear(schoolYear);
     		studentReportDto.setClaimsConceptualData(genereateStudentReportData(studentId));
     		OutputStream out = response.getOutputStream();
     		response.setContentType("application/pdf");
     		String fileName = studentName + ".pdf";
     		response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
     		try {
         	    String serverPath = request.getSession().getServletContext().getRealPath("/");
     			setupStudentReportPDFGeneration(studentReportDto, out, serverPath);
     		} catch (Exception e) {
     			logger.error("Caught {} in getStudentReportPDF() method.", e);
     		}finally {
     			if (out != null) {
     				out.close();
     			}
     		}
         }
        }
       
		
		
	
	}

	/**
	 * This method sets up the student progress report PDF.
	 * 
	 * @param studentsTests
	 *            List<StudentsTests>
	 * @param out
	 *            {@link OutputStream}
	 * @throws FOPException
	 *             FOPException
	 * @throws IOException
	 *             IOException
	 * @throws TransformerException
	 *             TransformerException
	 */
	private void setupStudentReportPDFGeneration(
			StudentReportDto studentReportDto, OutputStream out, String serverPath)
			throws Exception {
				XStream xstream = new XStream();
				xstream.alias("studentReportData", StudentReportDto.class);
				TraxSource source = new TraxSource(studentReportDto, xstream);
				Resource resource = resourceLoader.getResource("/templates/xslt/studentreport.xsl");
				PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
	}
}
