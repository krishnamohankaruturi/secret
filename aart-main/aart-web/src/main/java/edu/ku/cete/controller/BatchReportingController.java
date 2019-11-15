package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;

@Controller
public class BatchReportingController {
	
	private Logger logger = LoggerFactory.getLogger(BatchReportingController.class);

    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private ContentAreaService contentAreaService;
    
    @Autowired
    private GradeCourseService gradeCourseService;
    
    @Autowired
    private OrganizationService organizationService;
	
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
	
	@RequestMapping(value = "getAssessmentProgramsForBatchReporting.htm", method = RequestMethod.GET)
	public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsForBatchReporting(){
		logger.trace("Entering getAssessmentProgramsForAutoRegistration");
		
		List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Organization contract = userDetails.getUser().getContractingOrganization();

		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
		assessmentPrograms.add(ap);
		
		logger.trace("Leaving getAssessmentProgramsForAutoRegistration");
		return assessmentPrograms;
	}
	
	@RequestMapping(value = "getCoursesBasedOnAssessmentProgram.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getCoursesBasedOnAssessmentProgram(@RequestParam("assessmentProgramId")  Long assessmentProgramId){
		logger.trace("Entering getCoursesBasedOnAssessmentProgram");
		List<ContentArea> contentAreas = contentAreaService.getGradeCoursesUsingAssessmentProgram(assessmentProgramId);
		logger.trace("Leaving getCoursesBasedOnAssessmentProgram");
		return contentAreas;
	}
	
	@RequestMapping(value = "getGradesBasedOnAssessmentProgramAndCourses.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradesBasedOnAssessmentProgramAndCourses(@RequestParam("assessmentProgramId")  Long assessmentProgramId, 
			@RequestParam("contentAreaId") Long contentAreaId){
		logger.trace("Entering getGradesBasedOnAssessmentProgramAndCourses");
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		List<GradeCourse> gradeCourses = null;
		
		String reportType="";
		if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
			reportType = dbCPASSStudentReportsImportReportType;
		else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
			reportType = dbDLMStudentReportsImportReportType;		
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
	    Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
		if(reportYear == null)
			reportYear = (long) user.getContractingOrganization().getReportYear();
		
		if("CPASS".equalsIgnoreCase(assessmentProgram.getAbbreviatedname()) ||"DLM".equalsIgnoreCase(assessmentProgram.getAbbreviatedname())){
			gradeCourses = gradeCourseService.getGradesUsingAssessmentProgramAndCourseForExternalReport(assessmentProgramId, contentAreaId,assessmentProgram.getAbbreviatedname(), reportType, reportYear);
		}else{		
		    gradeCourses = gradeCourseService.getGradesUsingAssessmentProgramAndCourse(assessmentProgramId, contentAreaId);
		}
		logger.trace("Leaving getGradesBasedOnAssessmentProgramAndCourses");
		return gradeCourses;
	}
	
	
	
}
