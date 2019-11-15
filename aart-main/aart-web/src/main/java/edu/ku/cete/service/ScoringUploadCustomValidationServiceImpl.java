package edu.ku.cete.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.CcqScore;
import edu.ku.cete.domain.CcqScoreItem;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.RubricCategory;
import edu.ku.cete.domain.RubricReportDto;
import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.ScoringUploadDto;
import edu.ku.cete.domain.StudentsTestScore;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.UploadedScoringRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.security.GroupAuthorities;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.RubricCategoryDao;
import edu.ku.cete.model.StudentsTestScoreMapper;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * Added By Sudhansu.b
 * Feature: f430
 * Scoring Upload
 */

@SuppressWarnings("unused")
@Service
public class ScoringUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService{
    
	@Autowired
	private AssessmentProgramService assessmentProgramService;
    
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	@Autowired
	private GroupAuthoritiesService groupAuthoritiesService;
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentServices;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private CcqScoreService ccqScoreService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private RubricCategoryDao rubricCategoryDao;
	
	@Autowired
	private StudentsTestScoreMapper studentsTestScoreMapper;

	@Autowired
	private StudentService studentService;
	
	@Autowired
    private AppConfigurationService appConfigurationService;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {		
		
		Properties prop =(Properties) params.get("Properties");
		Set<String> keys = new HashSet(prop.keySet());				
	    UploadedScoringRecord record= (UploadedScoringRecord) rowData;
	    
	    for (String key : keys) {			
			if(key.contains(FieldName.SCORING_ITEMS.toString()) || key.contains(FieldName.SCORING_ITEMS_REASONS.getName())){
				record.getItems().put(key, prop.getProperty(key));
			}
		}
	    
	   BatchUpload batchUploadRecoUpload = (BatchUpload) params.get("batchUploadRecord");
	   
	   Map<String, Object> customValidationResults = new HashMap<String, Object>();
	   
	   Long assessmentprogramId = (Long)params.get("assessmentProgramIdOnUI");
       String lineNumber = prop.getProperty("linenumber");
	   boolean validationPassed = true;
	   
	   Long testId = (Long) params.get("testId");
	   
	   ScoringAssignment scoringAssignment = null;
	   
	   ContractingOrganizationTree contractingOrganizationTree  =null;
	   contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
		
	   List<ScoringUploadDto> scoringUploadDtos = null;
	   Organization school =  null;
	   UserDetailImpl currentUser = (UserDetailImpl) params.get("currentUser");
	   
	   if(!record.getStateStudentIdentifier().trim().isEmpty() ){
		   String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
		   int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
		   if(record.getStateStudentIdentifier().trim().length()>allowedLength){
			   String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
			   String errMsg = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
			   record.addInvalidField("stateStudentIdentifier", record.getStateStudentIdentifier(), true, 
					   errMsg);
			   validationPassed = false;
		   }
	   }
	   
	   //Validate documentId
	   if(record.getDocumentId().longValue() != batchUploadRecoUpload.getDocumentId().longValue()){
		   record.addInvalidField("documentId", record.getDocumentId().toString(), true,InvalidTypes.NULL, " is not valid.");
		   validationPassed = false;
	   }
	   
	   //Validate TestId		
		 if(record.getTestId().longValue() != testId.longValue()){
			 record.addInvalidField("testId", record.getTestId().toString(), true,InvalidTypes.NULL, " is not valid.");
			 validationPassed = false;
		 }
				
		//state validation
		//In a contracting organization tree there always only one state
		Set<String> displayIdentifiers= contractingOrganizationTree.getOrganizationDisplayIdentifiers();
		
		if(record.getState() == null || !currentUser.getUser().getContractingOrgDisplayIdentifier().equalsIgnoreCase((record.getState()))){
			record.addInvalidField("state", record.getState(), true,InvalidTypes.NULL, " is not same with the state from where you have downloaded.");
			validationPassed = false;
		}
		
		if(validationPassed){
			//District Validation		
	        List<Organization> district = organizationService.getDistrictInState(record.getDistrictID(), batchUploadRecoUpload.getUploadedUserOrgId());
	        if (district == null || district.isEmpty()) {
	        	record.addInvalidField("districtID", record.getDistrictID(), true,InvalidTypes.NULL, " is not valid.");
	        	validationPassed = false;
	        }else{	        	
	        	//School Validation	           
	            //Find the organization only from at or below the user's organization.
	            school = contractingOrganizationTree.getUserOrganizationTree()
	            			.getOrganization(record.getSchoolID());
	            OrganizationType orgType = null;
	            if (school == null) {
	            	record.addInvalidField("schoolID", record.getSchoolID(), true,InvalidTypes.NULL, " is not valid.");
	            	validationPassed = false;
	            }else{
	            	orgType = organizationTypeService.get(school.getOrganizationType().getOrganizationTypeId());
	            	if(orgType != null && !"SCH".equals(orgType.getTypeCode())){
	            		record.addInvalidField("schoolID", record.getSchoolID(), true,InvalidTypes.NULL, " is not a school.");
	            		validationPassed = false;
	            	}
	            }	
	        }	            
		}
		
		if(validationPassed){
			//Need to check the score all test permission
			Authorities authority = groupAuthoritiesService.getAuthorityForCsap("PERM_SCORE_ALL_TEST", batchUploadRecoUpload.getUploadedUserGroupId(),
					batchUploadRecoUpload.getStateId(), batchUploadRecoUpload.getAssessmentProgramId());
			
			Category scorinigCompleted = categoryService.selectByCategoryCodeAndType("COMPLETED","SCORING_STATUS");
			
			//Need to bring all the assignment related results....
			if(authority != null){
				scoringUploadDtos =scoringAssignmentServices.getAssignmentsForUpload(record.getAssignmentName().trim(),true,batchUploadRecoUpload.getCreatedUser());
			}else{
				scoringUploadDtos = scoringAssignmentServices.getAssignmentsForUpload(record.getAssignmentName().trim(),false,batchUploadRecoUpload.getCreatedUser());
			}
			
			if(scoringUploadDtos.size() > 0){
				 ScoringUploadDto scoringUploadDto = scoringUploadDtos.get(0);//Always we will have only one object because assessment name is unique in the system
				if(scoringUploadDto.getAttendanceSchoolId().longValue() != school.getId().longValue()){
					record.addInvalidField("schoolID", record.getSchoolID(), true,InvalidTypes.NULL, " is not map with Assignment "+record.getAssignmentName()+".");
            		validationPassed = false;
				}/*else if(!scoringUploadDto.getEducatorIdentifiers().contains(record.getEducatorIdentifier())){
					record.addInvalidField("educatorIdentifier", record.getEducatorIdentifier(), true,InvalidTypes.NULL, " is not map with Assignment "+record.getAssignmentName()+".");
            		validationPassed = false;
				}*/else if(!scoringUploadDto.getStateStudentIdentifiers().contains(record.getStateStudentIdentifier())){
					record.addInvalidField("stateStudentIdentifier", record.getStateStudentIdentifier(), true,InvalidTypes.NULL, " is not map with Assignment "+record.getAssignmentName()+".");
            		validationPassed = false;
				}
			}else{
				if(authority != null){
				    record.addInvalidField("assignmentName", record.getAssignmentName(), true,InvalidTypes.NULL, " is not correct.");
				}
				else{
					record.addInvalidField("assignmentName", record.getAssignmentName(), true,InvalidTypes.NULL, " is not mapped to uploaded user, to access this you have to get score all permission.");
				}
					
        		validationPassed = false;
			}			
		}
		
		if(validationPassed){
			Map<String,Object> itemMap = record.getItems();
			Set<String> key = new HashSet(itemMap.keySet());			
			for (String s : key) {			
				if(s.contains(FieldName.SCORING_ITEMS.toString())){
					s = s.replace(FieldName.SCORING_ITEMS.toString(), "");					
					if((StringUtils.isNotBlank((String)itemMap.get(s+FieldName.SCORING_ITEMS_REASONS.toString())) && StringUtils.isNotBlank((String)itemMap.get(FieldName.SCORING_ITEMS.toString()+s)))
							       && (!StringUtils.equalsIgnoreCase("C", (String)itemMap.get(s+FieldName.SCORING_ITEMS_REASONS.toString())) && !"HSO".equalsIgnoreCase((String) itemMap.get(s+FieldName.SCORING_ITEMS_REASONS.toString())) 
							    		   && !StringUtils.equalsIgnoreCase("C", (String)itemMap.get(FieldName.SCORING_ITEMS.toString()+s)) && Integer.parseInt(itemMap.get(FieldName.SCORING_ITEMS.toString()+s).toString()) != 0)
							 ){
						  record.addInvalidField(s+FieldName.SCORING_ITEMS_REASONS.toString(),"",true,InvalidTypes.NULL, " is not allowed with the provided score.");
	            		  validationPassed = false;						
					}
				}//if					
		   }//for
	   }//if
	   
		if(validationPassed){
			String assignmentName = prop.getProperty("assignmentName");
		    String stateStudentId = prop.getProperty("stateStudentIdentifier");
		    
		    UserDetailImpl userDetails =  (UserDetailImpl)params.get("currentUser");
		    //Get scored items based on student, teacher and assignments
		    //CcqScore ccqScore= ccqScoreService.getByEducatorIdAndStateStudentId(assignmentName, educatorId, stateStudentId);
		    
		    ScoringAssignmentStudent scoringAssignmentStudent =  scoringAssignmentServices.getStudentByNameAndStudentIdentifier(assignmentName, stateStudentId);
		    
		    //getting all non score reasons code and set to map for avoiding multiple loops 
		    List<Category> nonScoringReason = categoryService.selectByCategoryType("KELPA_NON_SCORE_REASON_CODE");
		    Map<String,Long> reasonsMap = new HashMap<String,Long>();
		    
		    for (Category category : nonScoringReason) {
				reasonsMap.put(category.getCategoryCode(), category.getId());
			}
		    
		    //Map all the item no to its appropriate taskvariantid	    
		    List<TaskVariant> taskVariantList = testService.getItemsWithPositionByTestId(testId);  
		    
		    //convert to map to avoid looping across the list multiple times
		    Map<Integer,Object> taskVariants = new HashMap<Integer,Object>();
		    
		    for (TaskVariant taskVariant : taskVariantList) {
				taskVariants.put(taskVariant.getTaskPosition(), taskVariant.getId());
			}
		    
		    List<RubricCategory> rubricCategories = null;
		    List<String> itemNos = new ArrayList<String>();
		    List<StudentsTestScore> studentsTestScoreList = new ArrayList<StudentsTestScore>();
		    StudentsTestScore studentsTestScore =null;
		    String itemValue = null;
		    String itemReason = null;
		    
		    for (String key : keys) {//Iterate all values come from csv
			if(key.contains(FieldName.SCORING_ITEMS.toString())){
				key = key.replace(FieldName.SCORING_ITEMS.toString(), "");
				if(StringUtils.isNotBlank((String) prop.get(FieldName.SCORING_ITEMS.toString()+key)) || StringUtils.isNotBlank((String)prop.get(key+FieldName.SCORING_ITEMS_REASONS.toString()))){
					String[] itemPos = key.split("--");				
					itemNos.clear();
					if(itemPos.length > 1){//To handle the cluster groups, having more than 1 items
		                for (int i = Integer.parseInt(itemPos[0]); i <= Integer.parseInt(itemPos[1]); i++) {
							itemNos.add(String.valueOf(i));
						}
				    }else{
				    	itemNos.add(itemPos[0]);	
				    }
					
					itemValue = (String) prop.get(FieldName.SCORING_ITEMS.toString()+key);
					itemReason = (String) prop.get(key+FieldName.SCORING_ITEMS_REASONS.toString());
					for (int i = 0; i < itemNos.size(); i++) {// for multiple task variant in single column e.g. 3--5					
						if(i < 1){
							rubricCategories = rubricCategoryDao.selectByTaskVariantId((Long)taskVariants.get(Integer.parseInt(itemNos.get(i))));
						}
						for (RubricCategory rubricReportDto : rubricCategories) { //Mostly there will be only rubric id for KELPA
							//New Code Begins
							  studentsTestScore = studentsTestScoreMapper.findByTaskvariantIdAndRubriType((Long)taskVariants.get(Integer.parseInt(itemNos.get(i))), rubricReportDto.getId(), scoringAssignmentStudent.getStudentsTestsId());
						
							  
							  if(studentsTestScore != null){//Update existing score						  
								  if((StringUtils.equalsIgnoreCase("C", itemValue) 
										          && StringUtils.equalsIgnoreCase("C", itemReason)) //If both the cell value is "C" so need to remove existing entry 
										  ||(StringUtils.equalsIgnoreCase("C", itemValue) 
												  && studentsTestScore.getNonscorereason() == null
												  && StringUtils.isBlank(itemReason)) //If score cell is "C" and nonscorereason is null
										  /*||(StringUtils.equalsIgnoreCase("C", itemReason)
	                                              && studentsTestScore.getScore().intValue() == 0)
	                                              && (StringUtils.isNotBlank(itemValue) && (int)Integer.parseInt(itemValue) == 0)*/){ //If nonscorereason cell is "C" and score is Zero
									  studentsTestScore.setTestId(testId);
									  record.getRemovedStudentsScores().add(studentsTestScore);
								  }else if(StringUtils.equalsIgnoreCase("C", itemValue) && studentsTestScore.getScore().intValue() != 0){
									  
									  studentsTestScore.setScore(0);
									  studentsTestScore.setScorerid(userDetails.getUser().getId());
									  studentsTestScore.setSource(SourceTypeEnum.UPLOAD.getCode());
									  studentsTestScore.setTestId(testId);
									  if(StringUtils.isNotBlank(itemReason))
									   studentsTestScore.setNonscorereason(reasonsMap.get(itemReason.toUpperCase()).longValue());
									  
									  studentsTestScoreList.add(studentsTestScore);
								  }else if (StringUtils.equalsIgnoreCase("C", itemReason) && studentsTestScore.getNonscorereason() != null){
									  
									  if(StringUtils.isNotBlank(itemValue))
									    studentsTestScore.setScore((int)Integer.parseInt(itemValue));
									  studentsTestScore.setScorerid(userDetails .getUser().getId());
									  studentsTestScore.setTestId(testId);
									  studentsTestScore.setSource(SourceTypeEnum.UPLOAD.getCode());
									  studentsTestScore.setNonscorereason(null);
									  studentsTestScoreList.add(studentsTestScore); 
								  }else if(StringUtils.isNotBlank(itemValue) && !StringUtils.equalsIgnoreCase("C", itemValue)
										  && studentsTestScore.getScore().intValue() != (int)Integer.parseInt(itemValue)){
									  
									  studentsTestScore.setScore((int)Integer.parseInt(itemValue));
									  studentsTestScore.setScorerid(userDetails.getUser().getId());
									  studentsTestScore.setSource(SourceTypeEnum.UPLOAD.getCode());
									  studentsTestScore.setTestId(testId);
									  if(StringUtils.isNotBlank(itemReason))
									   studentsTestScore.setNonscorereason(!StringUtils.equalsIgnoreCase("C", itemReason)? reasonsMap.get(prop.get(key+FieldName.SCORING_ITEMS_REASONS.toString()).toString().toUpperCase()).longValue():null);
									  
									  if(studentsTestScore.getNonscorereason() != null && reasonsMap.get("HSO").longValue() != studentsTestScore.getNonscorereason().longValue() && studentsTestScore.getScore().longValue() != 0){
										  record.addInvalidField(key+FieldName.SCORING_ITEMS_REASONS.toString(),"",true,InvalidTypes.NULL, " has been updated by another user which now requires either a clear or a change in value.");
					            		  validationPassed = false;
					            		  break;
									  }else{
									      studentsTestScoreList.add(studentsTestScore);
									  }
								  
								  }else if((studentsTestScore.getNonscorereason() == null && StringUtils.isNotBlank(itemReason) && !StringUtils.equalsIgnoreCase("C", itemReason))
										  ||(studentsTestScore.getNonscorereason() != null && StringUtils.isNotBlank(itemReason) && !StringUtils.equalsIgnoreCase("C", itemReason)
										  && studentsTestScore.getNonscorereason().longValue() != reasonsMap.get(itemReason.toUpperCase()).longValue())){
									  
									  if(StringUtils.isNotBlank(itemValue))
									   studentsTestScore.setScore(!StringUtils.equalsIgnoreCase("C", itemValue)? (int)Integer.parseInt(itemValue): 0);
									  studentsTestScore.setScorerid(userDetails .getUser().getId());
									  studentsTestScore.setTestId(testId);
									  studentsTestScore.setSource(SourceTypeEnum.UPLOAD.getCode());
									  studentsTestScore.setNonscorereason(StringUtils.isNotBlank(itemReason) ? reasonsMap.get(itemReason.toUpperCase()).longValue():null);
									  
									  if(studentsTestScore.getNonscorereason() != null && reasonsMap.get("HSO").longValue() != studentsTestScore.getNonscorereason().longValue() && studentsTestScore.getScore().longValue() != 0){
										  record.addInvalidField(key+FieldName.SCORING_ITEMS_REASONS.toString(),"",true,InvalidTypes.NULL, " has been updated by another user which now requires either a clear or a change in value.");
										  validationPassed = false;
					            		  break;
									  }else{
									     studentsTestScoreList.add(studentsTestScore);
									  }
								  }							  
							  }else{//New Entry
								  if((StringUtils.isNotBlank(itemValue) && !StringUtils.equalsIgnoreCase("C", itemValue)) 
										  || (StringUtils.isNotBlank(itemReason) && !StringUtils.equalsIgnoreCase("C", itemReason))
										   ){
									  studentsTestScore = new StudentsTestScore();								  
									  studentsTestScore.setTaskvariantid((Long)taskVariants.get(Integer.parseInt(itemNos.get(i))));
									  studentsTestScore.setRubriccategoryid(rubricReportDto.getId());
									  studentsTestScore.setStudensTsestId(scoringAssignmentStudent.getStudentsTestsId());
									  studentsTestScore.setActiveFlag(true);
									  studentsTestScore.setTestId(testId);
									  studentsTestScore.setScore(StringUtils.isNotBlank(itemValue) && !StringUtils.equalsIgnoreCase("C", itemValue)?(int)Integer.parseInt(itemValue):0);
									  studentsTestScore.setScorerid(userDetails .getUser().getId());
									  studentsTestScore.setSource(SourceTypeEnum.UPLOAD.getCode());
									  studentsTestScore.setNonscorereason(StringUtils.isNotBlank(itemReason) && !StringUtils.equalsIgnoreCase("C", itemReason)? reasonsMap.get(itemReason.toUpperCase()).longValue():null);
									  studentsTestScoreList.add(studentsTestScore);
								  }
							  }
					      }
					  }
				  }    
			   } 
		    }
		  
		    record.setStudentsScores(studentsTestScoreList);
		}
	
		
		 if( !validationPassed ){
	        	for( InValidDetail inValidDetail  : record.getInValidDetails() ){
	        		String errMsg = new StringBuilder(inValidDetail.getInValidMessage()).toString() ;
					String fieldName = inValidDetail.getActualFieldName();
					validationErrors.rejectValue("", "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
	        	}
	        }
	        else
	        {
	        	record.setCreatedUser(batchUploadRecoUpload.getCreatedUser());
	        	record.setModifiedUser(batchUploadRecoUpload.getCreatedUser());
	        	
	        }		   
			params.put("rowDataObject", record);//Required it for alert message.. 
	 		customValidationResults.put("errors", validationErrors.getAllErrors());
			customValidationResults.put("rowDataObject", record);
			return customValidationResults;	
	}
}
