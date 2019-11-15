package edu.ku.cete.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.SourceTypeEnum;


/**
 *
 * @author neil.howerton
 *
 */
@Controller
public class GrfStudentController {
    private Logger logger = LoggerFactory.getLogger(GrfStudentController.class);

    /**
     * STUDENT_RECORDS_JSP
     */
    
    private static final String EDIT_STUDENT_GRF_INFORMATION_JSP = "editStudentGrfInformation";
    
    @Autowired
    private UploadGrfFileService uploadGrfFileService;
    
    @Autowired
    private GradeCourseService gradeCourseService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService; 
    
	@RequestMapping(value = "saveStudentGrfInf.htm", method = RequestMethod.POST)
	public final @ResponseBody String saveStudentGrfInf(
			@RequestParam(value="grfStudentId",required=true) Long studentId,
			@RequestParam(value="grfReportYear",required=true) Long reportYear,
			@RequestParam(value="grfSubjectId",required=true) Long subjectId,
			@RequestParam(value="invalidate",required=true) String invalidate,
			@RequestParam(value="legalFirstName",required=true) String legalFirstName,
			@RequestParam(value="legalMiddleName",required=false) String legalMiddleName,
			@RequestParam(value="legalLastName",required=true) String legalLastName,
			@RequestParam(value="generationCode",required=false) String generationCode,
			@RequestParam(value="dateOfBirth",required=true) String dateOfBirth,
			@RequestParam(value="stateStudentIdentifier",required=true) String stateStudentIdentifier,			
			@RequestParam(value="gender",required=true) String gender,		
			@RequestParam(value="comprehensiveRace",required=true) String comprehensiveRace,
			@RequestParam(value="hispanicEthnicity",required=true) String hispanicEthnicity,			
			@RequestParam(value="firstLanguage",required=false) String firstLanguage,			
			@RequestParam(value="primaryDisabilityCode",required=true) String primaryDisabilityCode,
			@RequestParam(value="esolParticipationCode",required=true) String esolParticipationCode,			
			@RequestParam(value="currentGradelevel",required=true) String currentGradelevel,			
			@RequestParam(value="attendanceSchoolProgramIdentifier",required=true) String attendanceSchoolProgramIdentifier,			
			@RequestParam(value="aypSchoolIdentifier",required=true) String aypSchoolIdentifier,
			@RequestParam(value="schoolIdentifier",required=true) String schoolIdentifier,
			@RequestParam(value="residenceDistrictIdentifier",required=true) String residenceDistrictIdentifier,			
			@RequestParam(value="schoolEntryDate",required=true) String schoolEntryDate,
			@RequestParam(value="districtEntryDate",required=false) String districtEntryDate,
			@RequestParam(value="stateEntryDate",required=false) String stateEntryDate,
			@RequestParam(value="educatorIdentifier",required=true) String educatorIdentifier,
			@RequestParam(value="exitWithdrawalDate",required=false) String exitWithdrawalDate,
			@RequestParam(value="exitWithdrawalType",required=false) String exitWithdrawalType,
			@RequestParam(value="localStudentIdentifier",required=false) String localStudentIdentifier) {
			
			logger.info("Entered updating GRF Record for studentId : "+ studentId);
		
			UploadGrfFile uploadGrfData = new UploadGrfFile();
			uploadGrfData.setStudentId(studentId);
			uploadGrfData.setReportYear(reportYear);
			uploadGrfData.setSubjectId(subjectId != null && subjectId == 0?null:subjectId);			
			uploadGrfData.setInvalidationCode(new Long(invalidate));			
			uploadGrfData.setLegalFirstName(StringUtils.isNotBlank(legalFirstName)?legalFirstName.trim():legalFirstName);
			uploadGrfData.setLegalLastName(StringUtils.isNotBlank(legalLastName)?legalLastName.trim():legalLastName);
			uploadGrfData.setLegalMiddleName(StringUtils.isNotBlank(legalMiddleName)?legalMiddleName.trim():legalMiddleName);
			uploadGrfData.setGenerationCode(generationCode);			
			if(StringUtils.isNotBlank(dateOfBirth)) {
				uploadGrfData.setDateOfBirth(DateUtil.parse(dateOfBirth, "MM/dd/yyyy", null));
			}
			uploadGrfData.setStateStudentIdentifier(StringUtils.isNotBlank(stateStudentIdentifier)?stateStudentIdentifier.trim():stateStudentIdentifier);
			uploadGrfData.setGender(gender);
			uploadGrfData.setComprehensiveRace(comprehensiveRace);			
			uploadGrfData.setHispanicEthnicity(hispanicEthnicity);			
			if(firstLanguage.length() != 0)
				uploadGrfData.setFirstLanguage(firstLanguage);
			uploadGrfData.setPrimaryDisabilityCode(primaryDisabilityCode);
			uploadGrfData.setEsolParticipationCode(esolParticipationCode);
			uploadGrfData.setCurrentGradelevel(currentGradelevel);
			uploadGrfData.setAttendanceSchoolProgramIdentifier(StringUtils.isNotBlank(attendanceSchoolProgramIdentifier)?attendanceSchoolProgramIdentifier.trim():attendanceSchoolProgramIdentifier);
			uploadGrfData.setAypSchoolIdentifier(StringUtils.isNotBlank(aypSchoolIdentifier)?aypSchoolIdentifier.trim():aypSchoolIdentifier);
			uploadGrfData.setSchoolIdentifier(StringUtils.isNotBlank(schoolIdentifier)?schoolIdentifier.trim():schoolIdentifier);
			uploadGrfData.setResidenceDistrictIdentifier(StringUtils.isNotBlank(residenceDistrictIdentifier)?residenceDistrictIdentifier.trim():residenceDistrictIdentifier);
			if(StringUtils.isNotBlank(schoolEntryDate)) {
				uploadGrfData.setSchoolEntryDate(DateUtil.parse(schoolEntryDate, "MM/dd/yyyy", null));
			}			
			if(StringUtils.isNotBlank(districtEntryDate)) {
				uploadGrfData.setDistrictEntryDate(DateUtil.parse(districtEntryDate, "MM/dd/yyyy", null));
			}
			if(StringUtils.isNotBlank(stateEntryDate)) {
				uploadGrfData.setStateEntryDate(DateUtil.parse(stateEntryDate, "MM/dd/yyyy", null));
			}
			uploadGrfData.setEducatorIdentifier(StringUtils.isNotBlank(educatorIdentifier)?educatorIdentifier.trim():educatorIdentifier);
			if(StringUtils.isNotBlank(exitWithdrawalDate)) {
				uploadGrfData.setExitWithdrawalDate(DateUtil.parse(exitWithdrawalDate, "MM/dd/yyyy", null));
			}
			uploadGrfData.setExitWithdrawalType(exitWithdrawalType);			
			uploadGrfData.setLocalStudentIdentifier(localStudentIdentifier);
			
			
			GradeCourse gradeCourse = gradeCourseService.getByContentAreaAndAbbreviatedName(uploadGrfData.getSubjectId(), currentGradelevel);
			if(gradeCourse != null)
			  uploadGrfData.setGradeId(gradeCourse.getId());
			
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long stateId = userDetails.getUser().getContractingOrgId();
	
			try{
				String status = uploadGrfFileService.updateOnEdit(uploadGrfData);
				if(status.equals("success")){
					
					 //Insert entry in GRF audit table
					 GrfStateApproveAudit audit = new GrfStateApproveAudit();
					 audit.setActiveFlag(true);
					 audit.setStateId(stateId);
					 audit.setSchoolYear(reportYear);
					 audit.setAuditColumnProperties();
					 audit.setOperation("Updated GRF");
					 audit.setUpdatedUserId(user.getId());
					 audit.setSource(SourceTypeEnum.MANUAL.getCode());
						 
					 uploadGrfFileWriterProcessService.setGRFAuditInfo(audit);
					 
					return "{\"success\":\"true\"}";
				}else{
					return "{\"error\":\""+status+"\"}";
				}
			}
			catch(Exception e){
				logger.error("Exception updaing GRF record for studentID : "+ studentId + e.getMessage());
				e.printStackTrace();
				return "{\"error\":\"Unable to update\"}";
			}
						
	}
	
    @RequestMapping(value = "editStudentGrf.htm")
    public final ModelAndView editStudentGrf(
    		@RequestParam(value="assessmentProgramId",required=true) Long assessmentProgramId,
			@RequestParam(value="stateId",required=true) Long stateId,
			@RequestParam(value="reportYear",required=true) Long reportYear
    		) {
    	
    	ModelAndView mav = new ModelAndView(EDIT_STUDENT_GRF_INFORMATION_JSP);  
    	
    	List<ContentArea>  subjectLists = uploadGrfFileService.getDistinctSubjectNamesFromGRF(stateId, reportYear, assessmentProgramId);
  
    	mav.addObject("subjectLists", subjectLists);    
    	return mav;
    }
    
    @RequestMapping(value = "editStudentGrfInformation.htm")
    public final ModelAndView editStudentGrfInformation(
    		@RequestParam(value="assessmentProgramId",required=true) Long assessmentProgramId,
			@RequestParam(value="stateId",required=true) Long stateId,
			@RequestParam(value="reportYear",required=true) Long reportYear,
    		@RequestParam(value="subjectId",required=false) Long subjectId,
    		@RequestParam(value="stateStudentIdentifier",required=false) String stateStudentIdentifier,
    		@RequestParam(value="uniqueRowIdentifier",required=false) Long uniqueRowIdentifier) {
    	
    	ModelAndView mav = new ModelAndView(EDIT_STUDENT_GRF_INFORMATION_JSP);
    	
    	HashMap<String, Object> searchCriteriaMap = new HashMap<String, Object>();
    	searchCriteriaMap.put("uniqueRowIdentifier", uniqueRowIdentifier);
    	searchCriteriaMap.put("stateStudentIdentifier", StringUtils.isNotBlank(stateStudentIdentifier)?stateStudentIdentifier.trim():stateStudentIdentifier);
    	searchCriteriaMap.put("subjectId", subjectId);
    	
    	UploadGrfFile studentGrfData = uploadGrfFileService.getStudentGrfDataByStudentandSubjectId(stateId, reportYear, assessmentProgramId, subjectId, uniqueRowIdentifier, stateStudentIdentifier);
    	    	
    	List<ContentArea>  subjectLists = uploadGrfFileService.getDistinctSubjectNamesFromGRF(stateId, reportYear, assessmentProgramId);

    	List<Category> languages = categoryService.selectByCategoryType("FIRST_LANGUAGE");
    	List<Category> comprehensiveRaces = categoryService.selectByCategoryType("COMPREHENSIVE_RACE");
    	List<Category> primaryDisabilities = categoryService.selectByCategoryType("PRIMARY_DISABILITY_CODES");
    	
    	List<GradeCourse> currentGrade = gradeCourseService.getAllIndependentGrades();
    		
       	Collections.sort(languages, categoryCodeComparator);
    	Collections.sort(comprehensiveRaces, categoryCodeComparator);
    	Collections.sort(primaryDisabilities, categoryCodeComparator);
    	
    	Map<String, String> languageMap = new LinkedHashMap<String, String>();
    	Map<String, String> comprehensiveRaceMap = new LinkedHashMap<String, String>();
    	Map<String, String> primaryDisabilitiesMap = new LinkedHashMap<String, String>();
    	Map<String, String> currenrGradeMap = new LinkedHashMap<String, String>();
   	

    	for (Category cat: languages) {
    		languageMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : comprehensiveRaces) {
    		comprehensiveRaceMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : primaryDisabilities) {
    		primaryDisabilitiesMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (GradeCourse grade: currentGrade) {
    		currenrGradeMap.put(grade.getAbbreviatedName(), grade.getName());
    	}
      	
    	mav.addObject("searchCriteriaMap", searchCriteriaMap);
    	mav.addObject("studentGrf", studentGrfData);
    	mav.addObject("subjectLists", subjectLists);    	
    	mav.addObject("languageOptions", languageMap);    	
    	mav.addObject("comprehensiveRaceOptions", comprehensiveRaceMap);
    	mav.addObject("primaryDisabilityOptions", primaryDisabilitiesMap);
     	mav.addObject("currentGradeOptions",currenrGradeMap);
     	     	 
    	return mav;
    }
    
	protected Comparator<Category> categoryCodeComparator = new Comparator<Category>(){
		
		public int compare(Category dto1, Category dto2) {
			// Check for the numeric values.
			if (dto1.getCategoryCode().matches("[0-9]+") && dto2.getCategoryCode().matches("[0-9]+")) {
				return Integer.valueOf(dto1.getCategoryCode() ).compareTo(
						Integer.valueOf(dto2.getCategoryCode() ));
			} else {
				return dto1.getCategoryCode().compareTo(dto2.getCategoryCode() );
			}
		}		
	};
}
