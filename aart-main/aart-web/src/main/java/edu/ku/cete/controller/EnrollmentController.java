package edu.ku.cete.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.TransferStudentDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.util.CategoryUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.json.EnrollmentRecordJsonConverter;
import edu.ku.cete.util.json.StudentRecordJsonConverter;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;
import edu.ku.cete.service.StudentService;


@Controller
public class EnrollmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);

	private Category enrollmentRecordType;
	
	private Category tecRecordType;
	
    @Autowired
	private CategoryUtil categoryUtil;
    
	@Autowired
	private UploadSpecification uploadSpecification;
	
	@Autowired
	private UploadFileProcessor uploadFileProcessor;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private EnrollmentService enrollmentService;
    
	@Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * resourceRestrictionService
	 */
	@Autowired
	private ResourceRestrictionService resourceRestrictionService;
    
	/**
	 * restrictedResourceConfiguration
	 */
	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;
	
	/**
	 * permissionUtil
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
	private DataExtractService dataExtractService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private StudentService studentService;
	/**
	 * @deprecated dead code
	 */
	@RequestMapping(value = "uploadEnrollment.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadEnrollment(
			MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning) 
					throws Exception {  
		try {
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			LOGGER.debug("--> uploadEnrollment");
			Long stateId = Long.parseLong(request.getParameter("stateId"));
			Long regionId = 0l;
			if(null != request.getParameter("regionId") &&  !"".equals(request.getParameter("regionId")) && request.getParameter("regionId").matches("[+-]?\\d*(\\.\\d+)?")) {
				regionId = Long.parseLong(request.getParameter("regionId"));
			}
			Long areaId = 0l;
			if(null != request.getParameter("areaId") && !"".equals(request.getParameter("areaId")) && request.getParameter("areaId").matches("[+-]?\\d*(\\.\\d+)?")) {
				areaId = Long.parseLong(request.getParameter("areaId"));
			}
			Long districtId = 0l;
			if(null != request.getParameter("districtId") && !"".equals(request.getParameter("districtId")) && request.getParameter("districtId").matches("[+-]?\\d*(\\.\\d+)?")) {
				districtId = Long.parseLong(request.getParameter("districtId"));
			}
			Long buildingId = 0l;
			if(null != request.getParameter("buildingId") && !"".equals(request.getParameter("buildingId")) && request.getParameter("buildingId").matches("[+-]?\\d*(\\.\\d+)?")) {
				buildingId = Long.parseLong(request.getParameter("buildingId"));
			}
			Long schoolId = 0l;
			if(null != request.getParameter("schoolId") && !"".equals(request.getParameter("schoolId")) && request.getParameter("schoolId").matches("[+-]?\\d*(\\.\\d+)?")) {
				schoolId = Long.parseLong(request.getParameter("schoolId"));
			}

			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
		
			File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
			LOGGER.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
			cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
         

			this.enrollmentRecordType = categoryUtil.getCSVUploadRecordTypes(
					uploadSpecification, uploadSpecification.getEnrollmentRecordType());
				
			UploadFile uploadFile = new UploadFile();
			uploadFile.setSelectedRecordTypeId(this.enrollmentRecordType.getId());
			uploadFile.setStateId(stateId);
			uploadFile.setRegionId(regionId);
			uploadFile.setAreaId(areaId);
			uploadFile.setDistrictId(districtId);
			uploadFile.setBuildingId(buildingId);
			uploadFile.setSchoolId(schoolId);
			uploadFile.setContinueOnWarning(continueOnWarning);
			uploadFile.setFile(destFile);
		
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, this.enrollmentRecordType.getCategoryName());
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Future<Map<String,Object>> futureMav = uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			//request.getSession().setAttribute("uploadEnrollmentFileProcess", futureMav);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(
					JsonInclude.Include.NON_NULL); // no more null-valued properties
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			String modelJson = mapper.writeValueAsString(model);
		
			LOGGER.debug("<-- uploadEnrollment");
			return modelJson;
		} catch(Exception e) {
			LOGGER.error("Exception ooccured while uploading enrollment file: " + e.getMessage(), e);			
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
	/**
	 * @deprecated dead code
	 */
	@RequestMapping(value = "monitorFileUploadEnrollment.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadEnrollmentFile(HttpServletRequest request, HttpServletResponse response,
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
	/**
	 * @deprecated dead code
	 */
	@RequestMapping(value = "uploadTEC.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadTEC(
			MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning) 
					throws IOException, ServiceException {                 
		try {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			LOGGER.debug("--> uploadTEC");
			Long stateId = Long.parseLong(request.getParameter("stateId"));
			Long regionId = 0l;
			if(null != request.getParameter("regionId") &&  !"".equals(request.getParameter("regionId")) && request.getParameter("regionId").matches("[+-]?\\d*(\\.\\d+)?")) {
				regionId = Long.parseLong(request.getParameter("regionId"));
			}
			Long areaId = 0l;
			if(null != request.getParameter("areaId") && !"".equals(request.getParameter("areaId")) && request.getParameter("areaId").matches("[+-]?\\d*(\\.\\d+)?")) {
				areaId = Long.parseLong(request.getParameter("areaId"));
			}
			Long districtId = 0l;
			if(null != request.getParameter("districtId") && !"".equals(request.getParameter("districtId")) && request.getParameter("districtId").matches("[+-]?\\d*(\\.\\d+)?")) {
				districtId = Long.parseLong(request.getParameter("districtId"));
			}
			Long buildingId = 0l;
			if(null != request.getParameter("buildingId") && !"".equals(request.getParameter("buildingId")) && request.getParameter("buildingId").matches("[+-]?\\d*(\\.\\d+)?")) {
				buildingId = Long.parseLong(request.getParameter("buildingId"));
			}
			Long schoolId = 0l;
			if(null != request.getParameter("schoolId") && !"".equals(request.getParameter("schoolId")) && request.getParameter("schoolId").matches("[+-]?\\d*(\\.\\d+)?")) {
				schoolId = Long.parseLong(request.getParameter("schoolId"));
			}

			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
			File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
			LOGGER.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
			cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
         
			this.tecRecordType = categoryUtil.getCSVUploadRecordTypes(uploadSpecification, uploadSpecification.getTecRecordType());
				
			UploadFile uploadFile = new UploadFile();
			uploadFile.setSelectedRecordTypeId(this.tecRecordType.getId());
			uploadFile.setStateId(stateId);
			uploadFile.setRegionId(regionId);
			uploadFile.setAreaId(areaId);
			uploadFile.setDistrictId(districtId);
			uploadFile.setBuildingId(buildingId);
			uploadFile.setSchoolId(schoolId);
			uploadFile.setContinueOnWarning(continueOnWarning);
			uploadFile.setFile(destFile);
		
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, this.tecRecordType.getCategoryName());
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Future<Map<String,Object>> futureMav = uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			//request.getSession().setAttribute("uploadTECFileProcess", futureMav);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(
					JsonInclude.Include.NON_NULL); // no more null-valued properties
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- uploadTEC");
			return modelJson;
		} catch(Exception e) {
			LOGGER.error("Exception ooccured while uploading TEC file: " + e.getMessage(), e);			
			return "{\"errorFound\":\"true\"}";
		}
	}
	
	@RequestMapping(value = "transferStudents.htm", method = RequestMethod.POST)
	public @ResponseBody String transferStudents(
			@RequestParam("transferSelectedStudentArr[]") String[] transferSelectedStudentArr,
			@RequestParam("transferStudent") String transferStudent,
			@RequestParam(value="accountabilityDistrictId",required=false) String targatedAccountabilityDistrictId,
			@RequestParam(value="accountabilityDistrictIdentifier",required=false) String targatedAccountabilityDistrictIdentifier)throws IOException, ServiceException {
		Long accountabilityDistrictId=null;
		String accountabilityDistrictIdentifier=null;
		
		if(targatedAccountabilityDistrictId !=null && !StringUtils.isEmpty(targatedAccountabilityDistrictId) ) {
			accountabilityDistrictId =Long.parseLong(targatedAccountabilityDistrictId.trim());
		}
		if(targatedAccountabilityDistrictIdentifier !=null && !StringUtils.isEmpty(targatedAccountabilityDistrictIdentifier) ) {
			accountabilityDistrictIdentifier =targatedAccountabilityDistrictIdentifier.trim();
		}
		
		try{			
			if(transferStudent != null && transferStudent.trim().length() > 0){
				transferSelectedStudentArr = new String [ 1 ];
				transferSelectedStudentArr [ 0 ] = transferStudent;				
			}
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			List<TransferStudentDTO> students = new ArrayList<TransferStudentDTO>();
			
			for (String string : transferSelectedStudentArr) {
				
				TransferStudentDTO student = mapper.readValue(string, TransferStudentDTO.class);
				students.add(student);
			}
		    
			if(students.size() > 0){			
				enrollmentService.transferStudents(students, user, accountabilityDistrictId, accountabilityDistrictIdentifier);
			}else{
				return "{\"response\" : \"nostudentselected\"}";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "{\"response\" : \"failed\"}";
		}
		
		return "{\"response\" : \"success\"}";
	}
	 @RequestMapping(value = "checkforstudentavailbility.htm", method = RequestMethod.POST) 
	    public final @ResponseBody JQGridJSONModel checkforstudentavailbility( 
			      @RequestParam("stateStudentId") String studentStateId,
			      @RequestParam("studentFname") String studentFirstName,
			      @RequestParam("studentLname") String studentLastName,
			      @RequestParam("filters") String filters,
			      @RequestParam("page") String page,
			      @RequestParam("sidx") String sortByColumn,
			      @RequestParam("sord") String sortType,
			      @RequestParam("rows") String limitCountStr) {
		 JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
	        Integer currentPage = -1;
	        Integer limitCount = -1;
	        Integer totalCount = -1;
	        currentPage = NumericUtil.parse(page, 1);
	        limitCount = NumericUtil.parse(limitCountStr,5);
	        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		 	Integer currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear().intValue();
			Long orgId=userDetails.getUser().getContractingOrganization().getId();
			 Map<String,String> studentInformationRecordCriteriaMap = constructEnrollmentCriteriaFilterCriteria(filters);
			 List<FindEnrollments> studentEnrollmentsRecords =enrollmentService.findStudentAvilbility(studentStateId, studentFirstName, studentLastName, orgId, currentSchoolYear,(currentPage-1)*limitCount,limitCount,studentInformationRecordCriteriaMap,sortByColumn,sortType);		    	
			  totalCount =enrollmentService.findStudentAvilbilityCount(studentStateId, studentFirstName, studentLastName, orgId, currentSchoolYear,studentInformationRecordCriteriaMap);		    	
			 jqGridJSONModel = EnrollmentRecordJsonConverter.convertEnrollmentJson(studentEnrollmentsRecords,totalCount,currentPage, limitCount);
	         
	    	 LOGGER.debug("Leaving the getStudentInformationRecords page.");
	         return jqGridJSONModel;	    	
	    	
	    }
	 
	 @RequestMapping(value = "findStudentEnrollment.htm", method = RequestMethod.GET) 
	    public final @ResponseBody Map<String,Object> findStudentEnrollment( 
			      @RequestParam("stateStudentId") String studentStateId) {
			
		 Map<String,Object> ssidValidationDetails = new HashMap<String,Object>();
		 	if (SecurityContextHolder.getContext().getAuthentication() != null &&
					SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
		 
			 		ssidValidationDetails = studentService.validateStateStudentId(studentStateId);			
					LOGGER.debug("Leaving the getStudentInformationRecords page.");
					
		 	}else {
		 		LOGGER.debug("User validation failed.");
		 	}
		 	return ssidValidationDetails;
	    }
	
	    private Map<String,String> constructEnrollmentCriteriaFilterCriteria(String filters) {
	    	LOGGER.debug("Entering the constructStudentRosterFilterCriteria method");
	    	Map<String,String> enrollmentCriteriaMap = new HashMap<String, String>();
	    	if(null != filters && !filters.equals("")) {
		    	RecordBrowserFilter recordBrowserFilter = null;
		    	//Parse the column filter values which the user enters on the UI record browser grid.
		        try {
		        	recordBrowserFilter = mapper.readValue(filters, 
		        			new TypeReference<RecordBrowserFilter>() {});
		        } catch(JsonParseException e) {
		        	LOGGER.error("Couldn't parse json object.", e);
		        } catch (JsonMappingException e) {
		        	LOGGER.error("Unexpected json mapping", e);
		        } catch (SecurityException e) {
		        	LOGGER.error("Unexpected exception with reflection", e);
		        } catch (IllegalArgumentException e) {
		        	LOGGER.error("Unexpected exception with reflection", e);
		        } catch (Exception e) {
		        	LOGGER.error("Unexpected error", e);
		        }
		        
		    	
		    	if(recordBrowserFilter != null) {
			    	for(RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
			    		enrollmentCriteriaMap.put(recordBrowserFilterRules.getField(), 
			    				CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);	    		
			    	}
		    	}
	    	}
	    	LOGGER.debug(""+enrollmentCriteriaMap);
	    	LOGGER.debug("Leaving the constructStudentRosterFilterCriteria method");
	    	return enrollmentCriteriaMap;
	    }
	    
	    @RequestMapping(value = "reActivateStudent.htm", method = RequestMethod.POST)
	 		public @ResponseBody String reActivateStudent(
	 				@RequestParam("legalFirstName") String legalFirstName,
	 				@RequestParam(value = "legalMiddleName", required=false) String legalMiddleName,
	 				@RequestParam("legalLastName") String legalLastName,
	 				@RequestParam(value = "generationCode", required=false) String generation,
	 				@RequestParam("gender") String gender,
	 				@RequestParam("dateOfBirth") String dobDate,
	 				@RequestParam(value = "firstLanguage", required=false) String firstLanguage,
	 				@RequestParam("comprehensiveRace") String comprehensiveRace,
	 				@RequestParam("hispanicEthnicity") String hispanicEthnicity,
	 				@RequestParam("primaryDisabilityCode") String primaryDisability,
	 				@RequestParam(value = "giftedStudentEnrollment", required=false) String giftedStudent,
	 				@RequestParam("assessmentProgramsStudent") Long[] assessmentPrograms,
	 				@RequestParam("esolParticipationCode") String esolParticipationCode,
	 				@RequestParam(value = "esolProgramEntryDate", required=false) String esolProgramEntryDate,
	 				@RequestParam(value = "usaEntryDate", required=false) String usaEntryDate,
	 				@RequestParam("enrollId") Long enrollmentId ,
	 				@RequestParam("orgDistrictId") Long orgDistrictId,
	 				@RequestParam("orgSchoolId") Long orgSchoolId,
	 				@RequestParam("gradeId") Long gradeId,
	 				@RequestParam("studentId") Long studentId,
	 				@RequestParam(value = "localStudentIdentifier", required=false) String localStudentIdentifier,
	 				@RequestParam(value = "stateEntryDateEnrollment", required=false) String stateEntryDateEnrollment,
	 				@RequestParam(value = "districtEntryDateEnrollment", required=false) String districtEntryDateEnrollment,
	 				@RequestParam("schoolEntryDateEnrollment") String schoolEntryDateEnrollment,
	 				@RequestParam(value = "accountabilitySchoolStudent", required=false) String accountabilitySchoolStudent,
	   			  	@RequestParam(value = "accountabilityDistrictStudent", required=false) String accountabilityDistrictStudent,
	   			  	@RequestParam(value = "accountabilitySchoolStudentIdentifier", required=false) String accountabilitySchoolStudentIdentifier,
	   			  	@RequestParam(value = "accountabilityDistrictStudentIdentifier", required=false) String accountabilityDistrictStudentIdentifier
	 				)throws IOException, ServiceException {
	 		
	 	    	String activateStudnet =null;
	 			try{
	 				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
	 						.getContext().getAuthentication().getPrincipal();
	 		       User user= userDetails.getUser();
	 		       Restriction restriction = getEnrollmentRestriction(userDetails);
	 		       Long userCurrentAssessmentProgramId=user.getCurrentAssessmentProgramId();
	 		       
	 		       Enrollment enrollment = new Enrollment();
 		       
	 		      if(orgDistrictId !=null && orgSchoolId!=null ){
	 		    	   Organization attDistrict = organizationService.getDistrictBySchoolOrgId(orgSchoolId);
	 		    	   if(attDistrict.getId() != null && !attDistrict.getId().equals(orgDistrictId)) {
	 		    		   return "{\"response\" : \"failed\"}";
	 		    	   }
		 		   }
	 		      
	 		      if(accountabilityDistrictStudent!=null && !accountabilityDistrictStudent.equals("")
	 		    		 && accountabilitySchoolStudent!=null && !accountabilitySchoolStudent.equals("")){
	 		    	   Organization accDistrict = organizationService.getDistrictBySchoolOrgId(new Long(accountabilitySchoolStudent));
	 		    	   if(accDistrict.getId() != null && !accDistrict.getId().equals(new Long(accountabilityDistrictStudent))) {
	 		    		   return "{\"response\" : \"failed\"}";
	 		    	   }
	 		       }
	 		    
	 		       if(StringUtils.isNotBlank(stateEntryDateEnrollment)) 
	 		       enrollment.setStateEntryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(stateEntryDateEnrollment, "US/Central",  "MM/dd/yyyy"));
	 		       
	 		       if(StringUtils.isNotBlank(districtEntryDateEnrollment)) 
	 		       enrollment.setDistrictEntryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(districtEntryDateEnrollment, "US/Central",  "MM/dd/yyyy"));
	 		       
	 		       enrollment.setSchoolEntryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(schoolEntryDateEnrollment, "US/Central",  "MM/dd/yyyy"));
	 		       
	 		       if(StringUtils.isNotBlank(localStudentIdentifier))
	 		       enrollment.setLocalStudentIdentifier(localStudentIdentifier);
	 		       
	 		       if(accountabilityDistrictStudent!=null && !accountabilityDistrictStudent.equals("")) 
	 		    	   enrollment .setAccountabilityDistrictId(new Long(accountabilityDistrictStudent));
	 		       
	 		       if(accountabilitySchoolStudent!=null && !accountabilitySchoolStudent.equals("")) 
	 		    	   enrollment .setAccountabilitySchoolId(new Long(accountabilitySchoolStudent));
	 		       
	 		       if(StringUtils.isNotBlank(accountabilityDistrictStudentIdentifier)) 
	 		       enrollment.setAccountabilityDistrictIdentifier(accountabilityDistrictStudentIdentifier);
	 		     
	 		       if(StringUtils.isNotBlank(accountabilitySchoolStudentIdentifier)) 
	 		       enrollment.setAccountabilityschoolidentifier(accountabilitySchoolStudentIdentifier);
	 		       
	 		       if(null != giftedStudent && !giftedStudent.equals(""))
	 		    	   enrollment.setGiftedStudent(Boolean.parseBoolean(giftedStudent));
	 		       
	 		       Student studentrecord = new Student();
	 		       studentrecord.setId(studentId);
	 		       studentrecord.setLegalFirstName(legalFirstName);
	 			   studentrecord.setLegalLastName(legalLastName);
	 			   
	 			   studentrecord.setLegalMiddleName(legalMiddleName);
	 			   
	 			   studentrecord.setGenerationCode(StringUtils.isNotBlank(generation)?generation:null);
	 			  
	 			   studentrecord.setGender(StringUtils.isNotEmpty(gender)?Integer.parseInt(gender):null);
	 				
	 			   studentrecord.setDateOfBirth(StringUtils.isNotBlank(dobDate)?DateUtil.parse(dobDate, "MM/dd/yyyy", null):null);
	 			  
	 			   studentrecord.setFirstLanguage(firstLanguage.length() != 0?firstLanguage:null);
	 			   studentrecord.setComprehensiveRace(comprehensiveRace);
	 			   studentrecord.setHispanicEthnicity(hispanicEthnicity);
	 			   studentrecord.setPrimaryDisabilityCode(primaryDisability);
	 			   studentrecord.setEsolParticipationCode(esolParticipationCode);
	 				studentrecord.setEsolProgramEntryDate(StringUtils.isNotBlank(esolProgramEntryDate)?new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(esolProgramEntryDate, "US/Central",  "MM/dd/yyyy")
	 							.getTime()):null);
	 				 studentrecord.setUsaEntryDate(StringUtils.isNotBlank(usaEntryDate)?new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(usaEntryDate, "US/Central",  "MM/dd/yyyy")
	 							.getTime()):null);

	 			   studentrecord.setAuditColumnPropertiesForUpdate();	 
	 			   enrollment.setStudent(studentrecord);
	 		       
	 			   activateStudnet = enrollmentService.reActivateStudent(enrollmentId,orgDistrictId,orgSchoolId,gradeId,studentId,userCurrentAssessmentProgramId,restriction.getId(), enrollment);
	 			   
	 			}catch(Exception e){
	 				e.printStackTrace();
	 				return "{\"response\" : \"failed\"}";
	 			}
	 			
	 			return activateStudnet;
	 		}
		/**
		 * @param userDetails {@link UserDetailImpl}
		 * @return {@link Restriction}
		 */
		private Restriction getEnrollmentRestriction(UserDetailImpl userDetails) {
		    //Find the restriction for what the user is trying to do on this page.
		    Restriction restriction = resourceRestrictionService.getResourceRestriction(
		            userDetails.getUser().getOrganization().getId(),
		            permissionUtil.getAuthorityId(
		                    userDetails.getUser().getAuthoritiesList(),
		                    RestrictedResourceConfiguration.getUploadEnrollmentPermissionCode()),
		                    null,
		            restrictedResourceConfiguration.getEnrollmentResourceCategory().getId());
		    //if no upload permission check for add student permission
		    if(restriction == null) {
		    	restriction = resourceRestrictionService.getResourceRestriction(
			            userDetails.getUser().getOrganization().getId(),
			            permissionUtil.getAuthorityId(
			                    userDetails.getUser().getAuthoritiesList(),
			                    RestrictedResourceConfiguration.getAddEnrollmentPermissionCode()),
			                    null,
			            restrictedResourceConfiguration.getEnrollmentResourceCategory().getId());
		    }
		    return restriction;
		}
}