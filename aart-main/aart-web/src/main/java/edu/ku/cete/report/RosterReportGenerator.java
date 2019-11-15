package edu.ku.cete.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.Educator;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.report.roster.ItiRosterReportEE;
import edu.ku.cete.domain.report.roster.RosterReport;
import edu.ku.cete.domain.report.roster.RosterReportGradeStudentData;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.PermissionUtil;

@Component
public class RosterReportGenerator extends ReportGenerator {
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RosterReportGenerator.class);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private PermissionUtil permissionUtil;
	   
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Value("${user.organization.DLM}")
	private String USER_ORG_DLM;
	
    @Autowired
	private ItiTestSessionService itiTestSessionService;
	
    @Autowired
    private RosterService rosterService;
    
	@Value("/images/dlm_logo_final_registered_312x128.png")
	private Resource dlmLogoFile;
	
	@Value("/images/plusicon.png")
	private Resource plusIconFile;
	
	@Value("/images/minusicon.png")
	private Resource minusIconFile;
	
	protected String getRosterIconPath(String iconName) throws IOException {
		File iconFile = null;
		if(iconName.equalsIgnoreCase("dlmlogo")){
			iconFile = dlmLogoFile.getFile();
		} else if(iconName.equalsIgnoreCase("plusicon")){
			iconFile = plusIconFile.getFile();
		} else if(iconName.equalsIgnoreCase("minusicon")){
			iconFile = minusIconFile.getFile();
		}
		return iconFile.toURI().toString();
	}
	
	public void generatePdf(File foFile, OutputStream out, TraxSource source) throws IOException, Exception {
		
		try {
			
			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					foFile.getCanonicalPath()));
	
			FOUserAgent foUserAgent = getFopFactory().newFOUserAgent();
			
			// Construct fop with desired output format
			Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);
	
			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
	
			// Start XSLT transformation and FOP processing
			transformer.transform(source, res);
		} 
		catch(IOException ie)
		{
			LOGGER.error("IOException in generatePdf() method.", ie);
		}
		catch(SAXException se)
		{
			LOGGER.error("SAXException in generatePdf() method.", se);
		}
		catch(TransformerException te)
		{
			LOGGER.error("TransformerException in generatePdf() method.", te);
		}
		catch(NullPointerException ne)
		{
			LOGGER.error("NullPointerException in generatePdf() method.", ne);
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void setupRosterReportPDFGeneration(RosterReport rosterReport, OutputStream out){
		try{
			XStream xstream = new XStream();
			xstream.alias("rosterReportData", RosterReport.class);
			
			TraxSource source = new TraxSource(rosterReport, xstream);
			Resource resource = resourceLoader.getResource("/templates/xslt/rosterreport.xsl");
			generatePdf(resource.getFile(), out, source);
		} 
		catch(IOException ie)
		{
			LOGGER.error("IOException in setupRosterReportPDFGeneration() method.", ie);
		}
		catch(NullPointerException ne){
			LOGGER.error("NullPointerException in setupRosterReportPDFGeneration() method.", ne);
		}
		catch(Exception ne){
			LOGGER.error("Exception in setupRosterReportPDFGeneration() method.", ne);
		}
	}
	
	public RosterReport generateRosterReport(List<Long> assessmentProgramIds, Long schoolId, Long rosterId, 
			String[] studentIds, String schoolName, String contentAreaName, String[] studentNames, 
			String rosterName, String resourcePath, Long contentAreaId) throws IOException{
		/**
		 * Check that user has roster report view permission. (note: this is the permission for DLM Alternate roster reports)
		 */
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean rosterPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_ALTERNATE_ROSTER_REPORT");
        List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
		RosterReport rosterReport = new RosterReport();
        if(schoolId != null){
        	 if(rosterPermission && allChilAndSelfOrgIds.contains(schoolId)){
        		for(Long assessmentProgramId : assessmentProgramIds) 
        		{
        			AssessmentProgram assessmentProgram = assessmentProgramService
 						.findByAssessmentProgramId(assessmentProgramId);
        			if (assessmentProgram.getProgramName().equalsIgnoreCase(USER_ORG_DLM)) {
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
						rosterReport.setDlmLogo(getRosterIconPath("dlmlogo"));
						rosterReport.setPlusLogo(getRosterIconPath("plusicon"));
						rosterReport.setMinusLogo(getRosterIconPath("minusicon"));
 						
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
        }
        return rosterReport;
	}
	
	
	
}