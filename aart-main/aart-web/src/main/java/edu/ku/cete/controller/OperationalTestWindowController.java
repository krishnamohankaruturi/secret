package edu.ku.cete.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OperationalTestWindowStudentRecord;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.OperationalTestWindowMultiAssignDetail;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.validation.OperationalTestWindowValidator;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.CategoryUtil;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.json.OperationalTestWindowJSONConverter;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.web.AssessmentProgramTCDTO;

/**
 * @author vittaly
 *
 */
@Controller
public class OperationalTestWindowController {
			
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(OperationalTestWindowController.class);
	
	/**
	 * OPERATIONAL_TEST_WINDOW_JSP
	 */
	private static final String OPERATIONAL_TEST_WINDOW_JSP = "/test/newOperationalTestWindow";
	private static final String EDIT_OPERATIONAL_TEST_WINDOW_JSP ="/test/editOperationalTestWindow";
    /**
     * invalidEffectiveDatetime.
     */
    @Value("${error.operTestWindow.invalidEffectiveDatetime}")
    private String invalidEffectiveDatetime;
    
    /**
     * invalidExpirationDatetime.
     */
    @Value("${error.operTestWindow.invalidExpirationDateTime}")
    private String invalidExpirationDatetime;
    
    @Value("${error.operTestWindow.invalidDACStartDatetime}")
    private String invalidDACStartDateTime;
    
    @Value("${error.operTestWindow.invalidDACEndDateTime}")
    private String invalidDACEndDateTime;
    
	/**
	 * operationalTestWindowService
	 */
	@Autowired
	private OperationalTestWindowService operationalTestWindowService;
	
	/**
	 * operationalTestWindowValidator
	 */
	@Autowired
	private OperationalTestWindowValidator operationalTestWindowValidator;
	
	@Autowired
	private UploadSpecification uploadSpecification; 
	
	@Autowired
	private CategoryUtil categoryUtil;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UploadFileProcessor uploadFileProcessor;
	
	@Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private RecordBrowserJsonUtil recordBrowserJsonUtil;
	
	@Value("${report.upload.categorytype.code}")
	private String reportUploadCategoryTypeCode;
	
	@Value("${sessionrules.enrollment.system}")
	private String MANAGEDBY_SYSTEM; 
	
	@Value("${sessionrules.ticketed.test}")
	private String TICKETED_TEST; 

	@Value("${sessionrules.ticketofday.section}")
	private String TICKET_OF_THE_DAY;
	
	@Value("${sessionrules.ticketed.section}")
	private String TICKETED_AT_SECTION;
	
	@Value("${operationalTestWindow.testCollection}")
	private String overlappingMessageLeft;
	
	@Value("${operationalTestWindow.overlappingMessage}")
	private String overlappingMessageRight;
	
	@Value("${operationalTestWindow.autoEnrollment}")
	private String overlappingAutoEnrollmentMessage;
	
	
	/**
     * @param errors {@link BindingResult}
     * @return {@link String}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
	@RequestMapping(value="/newOperationalTestWindow.htm", method = RequestMethod.GET)
    public final ModelAndView create(@Valid OperationalTestWindow operationalTestWindow, Errors errors) throws IOException, ServiceException {
    	ModelAndView testWindowMav = new ModelAndView(OPERATIONAL_TEST_WINDOW_JSP);
        logger.trace("Entering the create() method");
              return testWindowMav;
    }
	
	@RequestMapping(value = "/newOperationalTestWindowView.htm", method = RequestMethod.GET)
    public final ModelAndView viewStudentDetails(final @RequestParam("Id") Long testWindowId) 
    		throws NoAccessToResourceException {
		List<OperationalTestWindow> operationalTestWindow = operationalTestWindowService.selectTestViewWindowById(testWindowId);
		List<AssessmentProgramTCDTO> assessmentProgramTCDTOList = operationalTestWindowService.selectAssessmentProgramAndTCListByWindowId(testWindowId);
		
		ModelAndView mav = new ModelAndView("/test/editOperationalTestWindow");
        mav.addObject("readonly",true);
        mav.addObject("testwindow",operationalTestWindow);
        mav.addObject("testwindowcollection",assessmentProgramTCDTOList);
     	logger.debug("Leaving viewStudentDetails method");
         return mav;
    }
	
	
	@RequestMapping(value = "/editOperationalTestWindowView.htm", method = RequestMethod.GET)
    public final ModelAndView viewEditOperationalTestWindow() 
    		throws NoAccessToResourceException {
//		List<OperationalTestWindow> operationalTestWindow = operationalTestWindowService.selectTestViewWindowById(testWindowId);
//		List<AssessmentProgramTCDTO> assessmentProgramTCDTOList = operationalTestWindowService.selectAssessmentProgramAndTCListByWindowId(testWindowId);
		
		ModelAndView mav = new ModelAndView("/test/editOperationalTestWindow");
        mav.addObject("readonly",true);
 //       mav.addObject("testwindow",operationalTestWindow);
  //      mav.addObject("testwindowcollection",assessmentProgramTCDTOList);
     	logger.debug("Leaving viewStudentDetails method");
         return mav;
    }
	
	
       
    @RequestMapping(value = "getExistingWindowTestCollections.htm", method = RequestMethod.POST)
    public final @ResponseBody List<AssessmentProgramTCDTO> getExistOperationalTestCollectionData(@RequestParam("windowId") Long windowId) throws JSONException {  
    	
    	if(windowId == 0){
    		windowId = null;
    	}
    	
        List<AssessmentProgramTCDTO> windowTestCollection = operationalTestWindowService.getExistingOpertioanlWindowTestCollection(windowId);
     
        return windowTestCollection;
    }
     
    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "getExistingTestCollectionData.htm", method = RequestMethod.POST)
    public final @ResponseBody OperationalTestWindow getExistingTestCollectionData(
    		@RequestParam Long testCollectionId) {                
        logger.trace("Entering the viewManageTestSession() method");
                        
        OperationalTestWindow operationalTestWindow = operationalTestWindowService.selectTestCollectionById(
        		testCollectionId);
               
        logger.trace("Leaving the viewManageTestSession() method");
        return operationalTestWindow;
    }
    
	/**
     * Displays the Manage Test Session page.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    
    @RequestMapping(value = "addOperTestWindowContent.htm", method = RequestMethod.POST)
    public final @ResponseBody String addOperTestWindowContent(
    		@RequestParam String effectiveDatetime, @RequestParam String expirationDatetime,
    		@RequestParam Long testCollectionId, @RequestParam String windowName, 
    		@RequestParam boolean ticketingFlag,
    		@RequestParam boolean requiredToCompleteTest,
    		@RequestParam boolean suspendWindow,@RequestParam Long id) {
        logger.trace("Entering the viewManageTestSession() method");
        String result = "";
        OperationalTestWindow operationalTestWindow = new OperationalTestWindow();
        operationalTestWindow.setTestCollectionId(testCollectionId);
        operationalTestWindow.setWindowName(windowName);
        operationalTestWindow.setTicketingFlag(ticketingFlag);
        operationalTestWindow.setRequiredToCompleteTest(requiredToCompleteTest);
        operationalTestWindow.setSuspendWindow(suspendWindow);                
        operationalTestWindow.setId(id);
        
        try {
        	//if entering dates, it must be valid.
        	if(StringUtils.isNotEmpty(effectiveDatetime)
        			&& !StringUtils.isBlank(effectiveDatetime)) {
        		result= invalidEffectiveDatetime;        		
        		operationalTestWindow.setEffectiveDate(DateUtil.parseAndFail(effectiveDatetime, "MM/dd/yyyy hh:mm:ss a"));
        	}
        	if(StringUtils.isNotEmpty(expirationDatetime)
        			&& !StringUtils.isBlank(expirationDatetime)) {
        		result= invalidExpirationDatetime;
        		operationalTestWindow.setExpiryDate(DateUtil.parseAndFail(expirationDatetime, "MM/dd/yyyy hh:mm:ss a"));
        	}        	
        } catch (AartParseException ex) {
        	logger.error("Caught AartParseException in Add/Update Operational Test Window. Stacktrace: {}", ex);
        	return result;
        	 
        }
        //Validating using spring validator framework.
        BeanPropertyBindingResult errorResult = new BeanPropertyBindingResult(operationalTestWindowValidator, "operationalTestWindowValidator");	
		ValidationUtils.invokeValidator(operationalTestWindowValidator, operationalTestWindow, errorResult);		
		
		//if there are no validation errors call the service method.
		if(!errorResult.hasErrors()) {
			result = String.valueOf(operationalTestWindowService.addOrUpdateOperTestWindowData(operationalTestWindow));
		} else {
			result = errorResult.getAllErrors().get(0).getDefaultMessage();
		}
        
        logger.trace("Leaving the viewManageTestSession() method");
        return result;
    }
    
    
    @RequestMapping(value = "setOperationalTestWindowSuspendState.htm", method = RequestMethod.POST)
    public final @ResponseBody String toggleOperationalTestWindowSuspendState(@RequestParam Long id, @RequestParam boolean suspendWindow) {
    
    	String result = "";
    	OperationalTestWindow operationalTestWindow = new OperationalTestWindow();
    	operationalTestWindow.setId(id);
    	UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        operationalTestWindow.setModifiedUser(userDetails.getUserId());
        operationalTestWindow.setModifiedDate(new Date());
        operationalTestWindow.setSuspendWindow(suspendWindow);

        result = String.valueOf(operationalTestWindowService.updateOperTestWindowState(operationalTestWindow));
	
    	return result;
    }
    
    
    @RequestMapping(value = "getContentAreasWithNumberOfTestForOTWindow.htm", method = RequestMethod.POST)
    public final @ResponseBody List<OperationalTestWindowMultiAssignDetail> getContentAreasWithNumberOfTestForOTWindow(@RequestParam Long id){
    	
    	List<OperationalTestWindowMultiAssignDetail> OperationalTestWindowMultiAssignDetailList = operationalTestWindowService.selectOperationalTestWindowMultiAssignDetail(id);
    	
    	return OperationalTestWindowMultiAssignDetailList;
    	
    }
    
    @RequestMapping(value = "addOperationalTestWindowContent.htm", method = RequestMethod.POST)
    public final @ResponseBody String addOperationalTestWindowContent(
      @RequestParam String effectiveDatetime, @RequestParam String expirationDatetime,
      @RequestParam String testCollectionId, @RequestParam String windowName, 
      @RequestParam Boolean ticketingFlag,
    //  @RequestParam String ticketOfTheDaySelected,
      @RequestParam Boolean requiredToCompleteTest,
      @RequestParam Boolean suspendWindow,
      @RequestParam String managedBy,
  //    @RequestParam String randomized,
      @RequestParam Boolean gracePeroid,
      @RequestParam Integer graceTimeInMin,
      @RequestParam String windowId, 
      @RequestParam(value="multipleStateId",required=false) String multipleStateId ,
      @RequestParam Boolean testEnrollmentFlag,
      @RequestParam String testEnrollmentId,
      @RequestParam Boolean scoringWindowFlag,
      @RequestParam String scoringWindowId,
      @RequestParam String scoringWindowStartDateTime,
      @RequestParam String scoringWindowEndDateTime,
      @RequestParam Long assessmentProgramId, 
      @RequestParam(value="subjectName[]", required=false)  Long [] subjectName,
      @RequestParam(value="numOfTests[]", required=false) Integer [] numOfTests,
      @RequestParam Boolean subjectNumOfTestForOTWindowAssignedBefore,
      @RequestParam Boolean dacFlag,
      @RequestParam String dacStartTime,
      @RequestParam String dacEndTime,
      @RequestParam String dacStartDateTime,
      @RequestParam String dacEndDateTime,
      @RequestParam(value = "instructionalPlannerWindow", required = false) Boolean instructionalPlannerWindow,
      @RequestParam(value = "instructionalPlannerDisplayName", required = false) String instructionalPlannerDisplayName){
    	
	        String result = "";
	        Long[] multiStateIds = null;
	        String [] stateId =null;
	        List<Long> mulitpleStateIds = null;
	        String [] ids = testCollectionId.split(",");
	        Long Collectionids[] = new Long[ids.length];
	        for( int i=0;i<ids.length;i++){
	        	Collectionids[i] = Long.parseLong(ids[i]);
	        }
	        Long id = null;
	        if( StringUtils.isNotBlank(windowId) )
	        	id = Long.parseLong(windowId);
	       if(multipleStateId != null && !multipleStateId.isEmpty()){
	         stateId=multipleStateId.split(",");
	         multiStateIds = new Long[stateId.length];
	        for(int i=0;i<stateId.length;i++){
	        	multiStateIds[i]=Long.parseLong(stateId[i]);
	         }
	        }
	        /*boolean ticketOfTheDayFlag = false;
	        boolean ticketedAtSectionFlag = false;
	        if(ticketOfTheDaySelected != null && !ticketOfTheDaySelected.isEmpty()){
	        	if(ticketOfTheDaySelected.equals(TICKET_OF_THE_DAY)){
	        		ticketOfTheDayFlag = true;
	        	}
	        	else if(ticketOfTheDaySelected.equals(TICKETED_AT_SECTION)){
	        		ticketedAtSectionFlag = true;
	        	}
	        }*/
	        
	        if(multiStateIds != null){
	        	mulitpleStateIds = Arrays.asList(multiStateIds);
	        }
	        if( ! testEnrollmentFlag )
	        	testEnrollmentId = null;
	        
	        if(!scoringWindowFlag)
	        	scoringWindowId = null;	        
	        
	        OperationalTestWindow operationalTestWindow = new OperationalTestWindow();
	        operationalTestWindow.setTestCollectionIds(Collectionids);
	        operationalTestWindow.setWindowName(windowName);
	        //operationalTestWindow.setTicketingFlag(ticketingFlag);
	        operationalTestWindow.setRequiredToCompleteTest(requiredToCompleteTest);
	        operationalTestWindow.setSuspendWindow(suspendWindow);
	        operationalTestWindow.setAssessmentProgramId(assessmentProgramId);
	        operationalTestWindow.setManagedBySystemFlag(MANAGEDBY_SYSTEM.equals(managedBy) ? true : false);
	       // operationalTestWindow.setTicketingOfTheDayFlag(ticketOfTheDayFlag);
	        operationalTestWindow.setTicketAtSectionFlag(ticketingFlag);
	        operationalTestWindow.setGracePeriodFlag(gracePeroid);
	        operationalTestWindow.setMultipleStateIds(multiStateIds);
	        operationalTestWindow.setTestEnrollmentFlag(testEnrollmentFlag);
	        operationalTestWindow.setTestEnrollmentMethodId(testEnrollmentId != null ? Long.parseLong(testEnrollmentId) : null);
	        operationalTestWindow.setScoringWindowFlag(scoringWindowFlag);
	        operationalTestWindow.setScoringWindowMethodId(scoringWindowId != null ? Long.parseLong(scoringWindowId) : null);
	        
	        operationalTestWindow.setInstructionPlannerWindow(Boolean.TRUE.equals(instructionalPlannerWindow));
	        operationalTestWindow.setInstructionPlannerDisplayName(StringUtils.isEmpty(instructionalPlannerDisplayName) ? null : instructionalPlannerDisplayName);
	        
	        if(graceTimeInMin == null){
	         graceTimeInMin = 0;
	        }else{
	        	operationalTestWindow.setGracePeriodInMin((long)graceTimeInMin);
	        }
	        operationalTestWindow.setId(id);
	        try {
	         //if entering dates, it must be valid.
	         if(StringUtils.isNotEmpty(effectiveDatetime)
	           && !StringUtils.isBlank(effectiveDatetime)) {
	          result= invalidEffectiveDatetime;          
	          operationalTestWindow.setEffectiveDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(effectiveDatetime, "US/Central",  "MM/dd/yyyy hh:mm:ss a"));
	         }
	         if(StringUtils.isNotEmpty(expirationDatetime)
	           && !StringUtils.isBlank(expirationDatetime)) {
	          result= invalidExpirationDatetime;
	          operationalTestWindow.setExpiryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(expirationDatetime, "US/Central", "MM/dd/yyyy hh:mm:ss a"));
	         }  
	         if(StringUtils.isNotEmpty(scoringWindowStartDateTime)
	        	&& !StringUtils.isBlank(scoringWindowStartDateTime)){
	        	 result = invalidEffectiveDatetime;
	        	 operationalTestWindow.setScoringWindowStartDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(scoringWindowStartDateTime, "US/Central","MM/dd/yyyy hh:mm:ss a"));
	         }
	         if(StringUtils.isNotEmpty(scoringWindowEndDateTime)
	        	&& !StringUtils.isBlank(scoringWindowStartDateTime)){
	        	 result = invalidExpirationDatetime;
	        	 operationalTestWindow.setScoringWindowEndDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(scoringWindowEndDateTime, "US/Central","MM/dd/yyyy hh:mm:ss a"));
	         }        
	         
	        } catch (Exception ex) {
	         logger.error("Caught ParseException in Add/Update Operational Test Window. Stacktrace: {}", ex);
	         return result;
	        }
        operationalTestWindow.setDacFlag(dacFlag);
        if( dacFlag ){
        	//Added for DE16551 - DAC time zone issue....appending with current date instead of window start date
        	String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        	dacStartDateTime = currentDate+" "+dacStartTime;
        	dacEndDateTime = currentDate+" "+dacEndTime;
        	try {
        		if(StringUtils.isNotEmpty(dacStartDateTime)
        		           && !StringUtils.isBlank(dacStartDateTime)) {
        			result= invalidDACStartDateTime; 
        			operationalTestWindow.setDacStartTime(new java.sql.Time(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(dacStartDateTime, "US/Central", "MM/dd/yyyy hh:mm:ss a").getTime()));
				//	operationalTestWindow.setDacStartTime(new java.sql.Time(formatter.parse(dacStartTime).getTime()));
					operationalTestWindow.setDacStartDateTime(DateUtil.parseAndFail(dacStartDateTime, "MM/dd/yyyy hh:mm:ss a"));
        		}
        		if(StringUtils.isNotEmpty(dacEndDateTime)
     		           && !StringUtils.isBlank(dacEndDateTime)) {
        			result= invalidDACEndDateTime;
        			
        			operationalTestWindow.setDacEndTime(new java.sql.Time(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(dacEndDateTime, "US/Central", "MM/dd/yyyy hh:mm:ss a").getTime()));	
	        		//operationalTestWindow.setDacEndTime(new java.sql.Time(formatter.parse(dacEndTime).getTime()));
					operationalTestWindow.setDacEndDateTime(DateUtil.parseAndFail(dacEndDateTime, "MM/dd/yyyy hh:mm:ss a"));
        		}
			} catch (Exception e) {
				return result;
			} 
        }
        
        //Validating using spring validator framework.
	        BeanPropertyBindingResult errorResult = new BeanPropertyBindingResult(operationalTestWindowValidator, "operationalTestWindowValidator"); 
	        ValidationUtils.invokeValidator(operationalTestWindowValidator, operationalTestWindow, errorResult);  
        
        
        	List<Long> overLappingTestCollectionIds = operationalTestWindowService.getOverlappingTestCollectionIds(operationalTestWindow.getEffectiveDate() ,
			  			operationalTestWindow.getExpiryDate(), id, Arrays.asList(Collectionids),mulitpleStateIds);
	        if( overLappingTestCollectionIds.size() > 0){
	        	String overlapError = "";
	        	for(int s= 0; s < overLappingTestCollectionIds.size(); s++){
	        		if(s == (overLappingTestCollectionIds.size() - 1))
	        		{
	        			overlapError = overlapError+overLappingTestCollectionIds.get(s);
	        		}else
	        			overlapError = overlapError+overLappingTestCollectionIds.get(s)+","; 
	        	}
	        	result = overlappingMessageLeft+overlapError+overlappingMessageRight;
	        	return result;
	        }
	        if(testEnrollmentFlag){
        	//Validating using spring validator framework for Auto Enrollment.
        	String overLappingAutoEnrollmentMethodName = operationalTestWindowService.getOverlappingOperationalTestWindowAutoEnrollmentIds(operationalTestWindow.getEffectiveDate() ,
			  			operationalTestWindow.getExpiryDate(), id,testEnrollmentId != null ? Long.parseLong(testEnrollmentId) : null ,mulitpleStateIds);
	        if(overLappingAutoEnrollmentMethodName !=null && !overLappingAutoEnrollmentMethodName.isEmpty()){
        		result = overlappingAutoEnrollmentMessage+overLappingAutoEnrollmentMethodName+overlappingMessageRight;
	        	return result;
	        }
	    }
	        
	        
	        boolean isWindowNameExist = false;
	        isWindowNameExist = operationalTestWindowService.isTestWindowNameExist(operationalTestWindow.getWindowName(),id); 
	        if(isWindowNameExist)
	        {
	        	result = "Operational Test Window Name Exist";
	        return result;
	        }
	        
		  //if there are no validation errors call the service method.
		  if(!errorResult.hasErrors()) {
		    result = String.valueOf(operationalTestWindowService.addOrUpdateOperTestWindowData(operationalTestWindow));
		   
	    	List<OperationalTestWindowMultiAssignDetail> OperationalTestWindowMultiAssignDetailList = new ArrayList<OperationalTestWindowMultiAssignDetail>();
	    	if(subjectName != null && subjectName.length > 0 ){
	    	
	    		for(int i=0; i <= (subjectName.length - 1); i++){
					OperationalTestWindowMultiAssignDetail operationalTestWindowMultiAssignDetail = new OperationalTestWindowMultiAssignDetail();
					operationalTestWindowMultiAssignDetail.setNumberoftests(numOfTests[i]);
		    		operationalTestWindowMultiAssignDetail.setContentareaid(subjectName[i]);
		    		operationalTestWindowMultiAssignDetail.setOperationaltestwindowid(operationalTestWindow.getId());
		    		OperationalTestWindowMultiAssignDetailList.add(operationalTestWindowMultiAssignDetail);
	    		}
	    	
		    	if(windowId == null || windowId == "" || subjectNumOfTestForOTWindowAssignedBefore != true){
		    		operationalTestWindowService.deleteOperationalTestWindowMultiAssignDetail(operationalTestWindow.getId());
		    		operationalTestWindowService.addOperationalTestWindowMultiAssignDetail(OperationalTestWindowMultiAssignDetailList);
		    	}else{
		    		operationalTestWindowService.updateOperationalTestWindowMultiAssignDetail(OperationalTestWindowMultiAssignDetailList);
		    	}
	    	}else{
	    		operationalTestWindowService.deleteOperationalTestWindowMultiAssignDetail(operationalTestWindow.getId());
	    	}
	    	
		  } else {
		   result = errorResult.getAllErrors().get(0).getDefaultMessage();
		  }
        logger.trace("Leaving the addOperationalTestWindowContent() method");
        return result;
		        
    }
    
     
    
    /**
     * @param request
     * @return
     */
    
    
    
    @RequestMapping(value = "getAdminOptionData.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getAdminOptionData(@RequestParam("windowId") Long windowId) throws JSONException {  
    	
    	if(windowId == 0){
     		windowId = null;
    	}
    	
    
    	Map<String, Object> hashMap = operationalTestWindowService.getAdminOptionData(windowId);
     
        return hashMap;
    }

    @RequestMapping(value = "getServerTime.htm", method = RequestMethod.GET)
    public final @ResponseBody Date getServerTime() {
        return (new Date());
    }    
    
    /**
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15915 : Test coordination extend window for test session
     * Extend test session operation window expiry date to later date.
     * @param selectedOtwIds
     * @param newTSWEndDate
     * @return
     */
    @RequestMapping(value = "extendTestSessionWindowEndDate.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> extendTestSessionWindowEndDate(
    		@RequestParam("selectedOtwIds[]") Long[] selectedOtwIds, 
    		@RequestParam("newTSWEndDate")  String newTSWEndDate) {
        logger.trace("Entering the extendTestSessionWindowEndDate() method");
        Map<String, Object> hashMap = new HashMap<String, Object>(); 
     
        List<OperationalTestWindow> opWindows = new ArrayList<OperationalTestWindow>();
        try {
	        for(Long id : selectedOtwIds){
	            OperationalTestWindow operationalTestWindow = new OperationalTestWindow();
	            operationalTestWindow.setId(id);
	            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	            Date date = format.parse(newTSWEndDate);
	            operationalTestWindow.setExpiryDate(date);
	            UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	            operationalTestWindow.setModifiedUser(userDetails.getUserId());
	            operationalTestWindow.setModifiedDate(new Date());
	            
	            opWindows.add(operationalTestWindow);
	        }
        
        	if(StringUtils.isNotEmpty(newTSWEndDate)
        			&& !StringUtils.isBlank(newTSWEndDate)) {
        		boolean result = operationalTestWindowService.extendTestSessionWindow(opWindows);
        		if(result){
        			hashMap.put("success", true);			
        		}
        	}        	
        } catch (AartParseException ex) {
        	logger.error("Caught AartParseException in extendTestSessionWindowEndDate. Stacktrace: {}", ex);
        	return hashMap;
        	 
        } catch(Exception e){
        	logger.error("Caught Exception in extendTestSessionWindowEndDate. Stacktrace: {}", e);
        	return hashMap;
        }
        
        logger.trace("Leaving the extendTestSessionWindowEndDate() method");
        return hashMap;
    }
   
    @RequestMapping(value = "getTestWindowsByAssessmentPrograms.htm", method = RequestMethod.POST)
	public final @ResponseBody
	JQGridJSONModel getTestWindowSessionsByAssessmentProgram(
		@RequestParam("rows") String limitCountStr,
		@RequestParam("page") String page,
		@RequestParam("sidx") String sortByColumn,
		@RequestParam("sord") String sortType,
		@RequestParam("filters") String filters,
		@RequestParam("assessmentProgramId") String assessmentProgram){
		Long assessmentProgramId=null;
		
		if(!(assessmentProgram == null || assessmentProgram =="")){			
			assessmentProgramId= (long) Integer.parseInt(assessmentProgram);
		}
		
		
		logger.trace("Entering the getAutoRegistrationTestSessionsByUser page for getting results");
		Integer currentPage = -1;
		Integer limitCount = -1;
		int totalCount = -1;
		JQGridJSONModel jqGridJSONModel = null;
		UserDetailImpl userDetails = null;
		Map<String, String> testWindowCriteriaMap = null;
		//String orgType = null;
		List<OperationalTestWindowDTO> operationalTestWindow = null;
		Long userId = null;
		String orderByclause = "";

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		userId = userDetails.getUser().getId();

		
		testWindowCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
				
		TimerUtil timerUtil = TimerUtil.getInstance();

		
		  operationalTestWindow = operationalTestWindowService.selectTestWindowByAssessmentProgram(userDetails,sortByColumn,
				  sortType, (currentPage - 1) * limitCount,limitCount, testWindowCriteriaMap,
					assessmentProgramId);
		
			timerUtil.resetAndLog(logger,"Getting getAutoRegistrationTestSessionsByUser took ");

			totalCount = operationalTestWindowService.countTestWindowByAssessmentProgram(userDetails, testWindowCriteriaMap,assessmentProgramId);			
			
			
		
		timerUtil.resetAndLog(logger,
				"Counting getAutoRegistrationTestSessionsByUser took ");
				
			jqGridJSONModel = OperationalTestWindowJSONConverter.convertToTestWindowJson(operationalTestWindow, totalCount, currentPage, limitCount);
				
		
		logger.trace("Leaving the getAutoRegistrationTestSessionsByUser page.");
		return jqGridJSONModel;
	}
    
    @RequestMapping(value = "newOperationalTestWindowAPTCDTOList.htm", method = RequestMethod.POST)
    public final @ResponseBody JQGridJSONModel getassessmentProgramTestCollectionData(
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,
    		@RequestParam("filters") String filters,
    		@RequestParam("assessmentProgramId") Long assessmentProgramId,
    		@RequestParam("randomizationType") String randomizationType,
    		@RequestParam("categoryCode") String categoryCode,
    		@RequestParam("windowId") Long windowId) throws JSONException {  
    
    
    	if(assessmentProgramId == 0){
    
    		assessmentProgramId = null;
    	}
    	if(windowId == 0){
    	
    		windowId = null;
    	}
    	
    	Integer currentPage = -1;
		Integer limitCount = -1;
		int totalCount = -1;
		JQGridJSONModel jqGridJSONModel = null;
		UserDetailImpl userDetails = null;
		Map<String, String> testWindowCriteriaMap = null;
		//String orgType = null;
		List<AssessmentProgramTCDTO> assessmentProgramTCDTO = null;
		Long userId = null;
		String orderByclause = "";

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		userId = userDetails.getUser().getId();
		
		testWindowCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		TimerUtil timerUtil = TimerUtil.getInstance();
        
		assessmentProgramTCDTO = operationalTestWindowService.selectAssessmentProgramAndTCList(userDetails,sortByColumn,
				  sortType, (currentPage - 1) * limitCount,limitCount, testWindowCriteriaMap,
				  assessmentProgramId,randomizationType,categoryCode,windowId);
		
			timerUtil.resetAndLog(logger,"Getting getAutoRegistrationTestSessionsByUser took ");

			totalCount = operationalTestWindowService.countAssessmentProgramAndTCList(userDetails, testWindowCriteriaMap,assessmentProgramId,randomizationType,categoryCode,windowId);			
		
		timerUtil.resetAndLog(logger,
				"Counting getAutoRegistrationTestSessionsByUser took ");
				
		jqGridJSONModel = OperationalTestWindowJSONConverter.convertToTestCollectionGridJson(assessmentProgramTCDTO, totalCount, currentPage, limitCount);
		
		
		return jqGridJSONModel;
    }
    
    @RequestMapping(value = "getMultipleStatesOrgsForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<Long,String> getMultipleStatesOrgsForUser(
    		@RequestParam(value="assessmentProgramId")long assessmentProgramId) {
    	
        logger.trace("Entering the getMultipleStatesOrgsForUser method.");
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        Long orgId = user.getOrganization().getId();
        logger.debug("logged in users orgid: "+orgId+" org type: "+user.getOrganization().getOrganizationType().getTypeCode());
        Map<Long,String> operationTestWindowStates = operationalTestWindowService.getOperationalTestWindowMultipleStateByUserIdAndAssessmentProgramId(assessmentProgramId, userId);
        logger.trace("Leaving the getMultipleStatesOrgsForUser method.");
        return operationTestWindowStates;
    }
    
    @RequestMapping(value = "getMultipleStatesForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody 	List<OperationalTestWindow> getMultipleStatesForUser(
    		@RequestParam(value="assessmentProgramId")long assessmentProgramId) {
    	
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        List<OperationalTestWindow> operationTestWindowStates = operationalTestWindowService.getMultipleStateByUserIdAndAssessmentProgramId(assessmentProgramId, user.getId());        
        return operationTestWindowStates;
    }
    
    @RequestMapping(value = "getTestEnrollmentMethod.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<Long , String> getTestEnrollmentMethod(
    		@RequestParam(value="assessmentProgramId")Long assessmentProgramId) {
    	
        logger.trace("Entering the getTestEnrollmentMethod method.");
        Map<Long ,String> testEnrollmentMethod =operationalTestWindowService.getOperationalTestWindowTestEnrollmentMethod(assessmentProgramId); 
        logger.trace("Leaving the getTestEnrollmentMethod method.");
        return testEnrollmentMethod;
    }
    
    @RequestMapping(value="getScoringAssignmentMethod.htm",method=RequestMethod.GET)
    public final @ResponseBody Map<Long, String> getScoringAssignmentMethod(
    		@RequestParam(value="assessmentProgramId") Long assessmentProgramId){
    	logger.trace("Entering the getScoringAssignmentMethod method.");
    	Map<Long,String> scoringAssignmentMethod = operationalTestWindowService.getOperationalTestWindowScoringAssignmentMethod(assessmentProgramId);
    	logger.trace("Leaving the getScoringAssignmentMethod method.");
    	return scoringAssignmentMethod;
    }    

    @RequestMapping(value = "getOperationalTestWindowSelectedStatesForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<Long,String> getSeletedStatesOrgsForUser(
    		@RequestParam(value="operationalTestWindowId")long operationalTestWindowId) {
    	
        logger.trace("Entering the getSeletedStatesOrgsForUser method.");
        Map<Long,String> operationTestWindowSelectedStates = operationalTestWindowService.getOperationalTestWindowSelectedState(operationalTestWindowId);      
        logger.trace("Leaving the getSeletedStatesOrgsForUser method.");
        return operationTestWindowSelectedStates;
    }

    @RequestMapping(value = "uploadIncludeExclude.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadIncludeExclude(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning,
			@RequestParam(value="operationalWindowId", required=true) String operationalWindowId) throws IOException, ServiceException {    
    	try {
    		// Parse the file uploaded
    		Iterator<String> itr =  request.getFileNames();
    		MultipartFile mpf = request.getFile(itr.next());
    		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
    		File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
    		logger.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
    		cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
         
    		Category includeExcludeStudentRecordType = categoryUtil.getCSVUploadRecordTypes(uploadSpecification, uploadSpecification.getIncludeExcludeRecordType());
    		logger.debug("userRecordType ID", includeExcludeStudentRecordType.getId());
    		
    		// Initialize the upload entity with available information
    		UploadFile uploadFile = new UploadFile();
    		uploadFile.setSelectedRecordTypeId(includeExcludeStudentRecordType.getId());
    		uploadFile.setRosterUpload(0);
			uploadFile.setContinueOnWarning(continueOnWarning);
			uploadFile.setFile(destFile);
			uploadFile.setOperationalWindowId(Long.parseLong(operationalWindowId));
			
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, reportUploadCategoryTypeCode);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			// Get in progress category type
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			
			// Insert into uploadfile table
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Future<Map<String,Object>> futureMav = uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			request.getSession().setAttribute("includeExcludeStudentFileProcess", futureMav);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
    	} catch(Exception e) {
			logger.error("Exception ooccured while uploading include/exclude file: " + e.getMessage());			
			return "{\"errorFound\":\"true\"}";
		}
	}
    
    private UploadFileRecord buildUploadFileRecord(User user, String fileName, Long statusId) {
		UploadFileRecord uploadFileRecord = new UploadFileRecord();
		uploadFileRecord.setFileName(fileName);
		uploadFileRecord.setCreatedUser(user.getId());
		uploadFileRecord.setCreatedDate(new Date());
		uploadFileRecord.setModifiedUser(user.getId());
		uploadFileRecord.setModifiedDate(new Date());
		uploadFileRecord.setStatusId(statusId);
		return uploadFileRecord;
	}
	
	@RequestMapping(value = "monitorUploadIncludeExcludeFile.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadIncludeExcludeFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());
		Category status = categoryService.selectByPrimaryKey(uploadFileRecord.getStatusId());
		
		if (status.getCategoryCode().equals("COMPLETED") || status.getCategoryCode().equals("FAILED")) {
			return uploadFileRecord.getJsonData();
		}
		return "{\"uploadFileStatus\": \"IN_PROGRESS\"}";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downloadFailedIncludeExcludeStudents.htm", method = RequestMethod.GET)
	public void  downloadFailedIncludeExcludeStudents(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		
		logger.debug("uploadFileRecordId "+uploadFileRecordId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());

		String jsonData = uploadFileRecord.getJsonData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);

		Map<String,Object> invalidRecordMap = objectMapper.readValue(jsonData, Map.class);
		
		ArrayList<Map<String, Object>> invalidRecords = (ArrayList<Map<String, Object>>) invalidRecordMap.get("inValidRecords");
		
		List<OperationalTestWindowStudentRecord> failedRecords = new ArrayList<OperationalTestWindowStudentRecord>();
		for(Map<String, Object> invalidRecord: invalidRecords){
			OperationalTestWindowStudentRecord operationalTestWindowStudentRecord = new OperationalTestWindowStudentRecord();
			List<Object> invalidDetails = (List<Object>) invalidRecord.get("inValidDetails");
			String exclude = (String)invalidRecord.get("exclude");
			String subject = (String)invalidRecord.get("subject");
			String course = (String)invalidRecord.get("course");
			String stateStudentIdentifier = (String)invalidRecord.get("stateStudentIdentifier");
			String attendanceSchoolProgramIdentifier = (String)invalidRecord.get("attendanceSchoolProgramIdentifier");
			String organization = (String)invalidRecord.get("organization");
			String failedReason = (String)invalidRecord.get("failedReason");
			
			operationalTestWindowStudentRecord.setExclude(exclude);
			operationalTestWindowStudentRecord.setSubject(subject);
			operationalTestWindowStudentRecord.setCourse(course);
			operationalTestWindowStudentRecord.setStateStudentIdentifier(stateStudentIdentifier);
			operationalTestWindowStudentRecord.setAttendanceSchoolProgramIdentifier(attendanceSchoolProgramIdentifier);
			operationalTestWindowStudentRecord.setOrganization(organization);
			
			StringBuilder failedReasons = new StringBuilder();
			for(Object value : invalidDetails){
				Map<String, Object> invalidDetail = (Map<String, Object>)value;
				String fieldName = (String) invalidDetail.get("fieldName");
				boolean isRejected = (boolean) invalidDetail.get("rejectRecord");
				if(!isRejected || (!"Roster enrollment".equalsIgnoreCase(fieldName))){
					failedReasons.append(fieldName).append(" : ");
					failedReasons.append((String)invalidDetail.get("inValidMessage")).append("\n");
				} else {
					failedReasons.append(fieldName).append(" : ");
					failedReasons.append((String)invalidDetail.get("reason")).append("\n");
				}
			}
			if(null != failedReason){
				failedReasons.append(failedReason);
			}
			operationalTestWindowStudentRecord.setFailedReason(failedReasons.toString());
			failedRecords.add(operationalTestWindowStudentRecord);	
		}
		writeIncludeExcludeStudentInvalidData(failedRecords, response, "IncludeExclude_Results_CSV_Failed_Records.csv");
	}
	
	private void writeIncludeExcludeStudentInvalidData(List<OperationalTestWindowStudentRecord> trainingDetailsDtos, HttpServletResponse response, String fileName) throws IOException {
    	CSVWriter csvWriter = null;
    	 try {

			response.setContentType("application/force-download");
    		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
    		
	        csvWriter = new CSVWriter(response.getWriter(), ',');
	        List<String[]> data  = getIncludeExcludeStudentFailedRecordsData(trainingDetailsDtos);
	        csvWriter.writeAll(data);
	
    	 } catch (IOException ex) {
    		 throw ex;
    	 } finally {
         	if(csvWriter != null) {
         		csvWriter.close();
         	}
         }
    }
	
	private List<String[]> getIncludeExcludeStudentFailedRecordsData(List<OperationalTestWindowStudentRecord> reportDtos) {
	    List<String[]> records = new ArrayList<String[]>();
	    //add header record
	    records.add(new String[]{"Include/Exclude","Subject", "Course","State Student Identifier",
	    				"Attendance School Program Identifier","Organization", "Failure Reason"});        
	
	    Iterator<OperationalTestWindowStudentRecord> it = reportDtos.iterator();
	    while(it.hasNext()){
	    	OperationalTestWindowStudentRecord dto = it.next();        	
	        records.add(new String[]{dto.getExclude(), dto.getSubject(),
	        		dto.getCourse(),dto.getStateStudentIdentifier(),
	        		dto.getAttendanceSchoolProgramIdentifier(),dto.getOrganization(),dto.getFailedReason()});
	    }
	    return records;
	}
}
