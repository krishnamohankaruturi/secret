package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationContractRelation;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ProjectedTestingService;

/**
 * Added By Sudhansu 
 * Feature: f183
 * Projected Testing
 */
@SuppressWarnings("unused")
@Service
public class ProjectedTestingUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	OrganizationTypeService organizationTypeService;
	
	@Autowired
	ProjectedTestingService projectedTestingService;
	
	@Autowired
	GradeCourseService gradeCourseService;
	
	@Autowired
    GroupAuthoritiesService groupAuthoritiesService;
	
	private static final String[] columnNames = {
        "",
        "one","two","three","four","five","six","seven","eight","nine","ten",
        "eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen","twenty",
        "twentyOne","twentyTwo","twentyThree","twentyFour","twentyFive","twentySix","twentySeven","twentyEight","twentyNine",
        "thirty","thirtyOne"
    };
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		ProjectedTesting projectedTesting = (ProjectedTesting) rowData;
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long stateId = (Long) params.get("stateId");
		Long districtId = (Long) params.get("districtId");
		Long schoolId = (Long)params.get("schoolId");
		Long assessmentprogramId = (Long)params.get("assessmentProgramIdOnUI");
        String lineNumber = projectedTesting.getLineNumber();
		
		boolean validationPassed = true;
		String organizationTypeCode = "";
		
		UserDetailImpl userDetails =  (UserDetailImpl)params.get("currentUser");
		
		Long parentOrgId = (Long)params.get("selectedOrgId");
		Long organizationId  = parentOrgId;
		Organization org = null;
		UserDetailImpl currentUser = null;
		ContractingOrganizationTree contractingOrganizationTree  =null;
		boolean isInsert = true;
		
		contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
		
		//Validate AssessmentProgram
		Authorities authority = groupAuthoritiesService.getAuthorityForCsap("VIEW_PROJECTED_SCORING", userDetails.getUser().getCurrentGroupsId(), 
						stateId, userDetails.getUser().getCurrentAssessmentProgramId());
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentprogramId);
		
		if(projectedTesting.getAssessmentProgram() == null || !(projectedTesting.getAssessmentProgram().equalsIgnoreCase(assessmentProgram.getAbbreviatedname()))){
			projectedTesting.addInvalidField("assessmentProgram", projectedTesting.getAssessmentProgram(), true,InvalidTypes.NULL, " was not same with signed in Assessment Program.");
			validationPassed = false;
		}else if(authority ==null &&
				(projectedTesting.getProjectionType().equalsIgnoreCase("Scoring")||projectedTesting.getProjectionType().equalsIgnoreCase("S"))){
			projectedTesting.addInvalidField("assessmentProgram", projectedTesting.getAssessmentProgram(), true,InvalidTypes.ERROR, " is not applicable for Scoring.");
			validationPassed = false;
		}
		
		projectedTesting.setAssessmentProgramId(assessmentprogramId);
		
		//validate Projection Type(Testing and Scoring)
		if(projectedTesting.getProjectionType() == null || projectedTesting.getProjectionType().isEmpty()){
			projectedTesting.addInvalidField("projectionType", projectedTesting.getProjectionType(), true,InvalidTypes.NULL, " is not valid.");
			validationPassed = false;
			
		}else if(projectedTesting.getProjectionType().equalsIgnoreCase("Testing")||projectedTesting.getProjectionType().equalsIgnoreCase("T")){
			projectedTesting.setProjectionType("T");
		}else if(projectedTesting.getProjectionType().equalsIgnoreCase("Scoring")||projectedTesting.getProjectionType().equalsIgnoreCase("S")){
			projectedTesting.setProjectionType("S");
		}
		
		//validate Grade
		if(projectedTesting.getGrade() == null){
			projectedTesting.addInvalidField("grade", projectedTesting.getGrade(), true,InvalidTypes.NULL, " is not valid.");
			validationPassed = false;
		}else if(projectedTesting.getGrade().equalsIgnoreCase("k")){
			GradeCourse grade = gradeCourseService.getGradesForScoring(projectedTesting.getGrade());
			projectedTesting.setGradeId(grade.getId());
		}
		else{
			GradeCourse grade = gradeCourseService.getIndependentGradeByAbbreviatedName(projectedTesting.getGrade());
			projectedTesting.setGradeId(grade.getId());
		}
		
		//state validation
		//In a contracting organization tree there always only one state
		List<OrganizationContractRelation> displayIdentifiers= contractingOrganizationTree.getHighestContractingOrgs();
		
		if(projectedTesting.getState() == null || !displayIdentifiers.get(0).getOrganization().getDisplayIdentifier().contains(projectedTesting.getState())){
			projectedTesting.addInvalidField("state", projectedTesting.getState(), true,InvalidTypes.NULL, " is not valid.");
			validationPassed = false;
		}
		projectedTesting.setStateId(stateId);
		
		if(validationPassed){
			//District Validation		
	        List<Organization> district = organizationService.getDistrictInState(projectedTesting.getDistrictID(), parentOrgId);
	        if (district == null || district.isEmpty()) {
	        	projectedTesting.addInvalidField("districtID", projectedTesting.getDistrictID(), true,InvalidTypes.NULL, " is not valid.");
	        	validationPassed = false;
	        }else{
	        	projectedTesting.setDistrictId(district.get(0).getId());	        	
	        	
	        	//School Validation
	            Organization school =  null;
	            //Find the organization only from at or below the user's organization.
	            school = contractingOrganizationTree.getUserOrganizationTree()
	            			.getOrganization(projectedTesting.getSchoolID());
	            OrganizationType orgType = null;
	            
	            
	            List<Organization> schoolList = organizationService.getImmediateChildren(district.get(0).getId());
	            
	            
	            if (school == null) {
	            	projectedTesting.addInvalidField("schoolID", projectedTesting.getSchoolID(), true,InvalidTypes.NULL, " is not valid.");
	            	validationPassed = false;
	            }else{
	            	Boolean schoolExist  = false;
	            	for (Organization organization : schoolList) {
						if(projectedTesting.getSchoolID().contains(organization.getDisplayIdentifier())){
							schoolExist = true;
						}
					}
	            	
	            	if(schoolExist){
		            	orgType = organizationTypeService.get(school.getOrganizationType().getOrganizationTypeId());
		            	if(orgType != null && !"SCH".equals(orgType.getTypeCode())){
		            		projectedTesting.addInvalidField("schoolID", projectedTesting.getSchoolID(), true,InvalidTypes.NULL, " is not a school.");
		            		validationPassed = false;
		            	}else{
		            		projectedTesting.setSchoolId(school.getId());
		            	}
	            	}else{
	            		projectedTesting.addInvalidField("schoolID", projectedTesting.getSchoolID(), true,InvalidTypes.NULL, " is not a valid school for district :"+district.get(0).getOrganizationName()+" ("+district.get(0).getDisplayIdentifier()+").");
		            	validationPassed = false;
	            	}
	            }
	
	        }
	            
		}
		
		if(validationPassed){
			projectedTesting.setSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
			projectedTesting.setCurrentSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
			Date currentDate =  new Date();
			
					
				 projectedTesting.setSchoolStartDate(userDetails.getUser().getContractingOrganization().getSchoolStartDate());
				 projectedTesting.setSchoolEndDate(userDetails.getUser().getContractingOrganization().getSchoolEndDate());
				 projectedTestingService.convertColumnToDates(projectedTesting);			 
				 

					
				/*if(projectedTesting.getPreviousDate().size() > 0){
					for(Integer i: projectedTesting.getPreviousDate()){
						projectedTesting.addInvalidField(columnNames[i], projectedTesting.getMonth(), true, " For Date "+i+" is a previous date.");
	        			validationPassed = false;
					}				
				}else*/
				 if(projectedTesting.getInvalidDate().size() > 0){
					for(Integer i: projectedTesting.getInvalidDate()){
						//projectedTesting.addInvalidField(columnNames[i], projectedTesting.getMonth(), true, " For Date "+i+" is not a valid date.");
						validationErrors.rejectValue(columnNames[i], "", 
								new String[]{lineNumber, mappedFieldNames.get(columnNames[i])},
								"The date "+i+" with month "+projectedTesting.getMonth()+" is an invalid date.");
	        			validationPassed = false;
					}				
				}/*else if(projectedTesting.getWeekends().size() > 0){
					for(Integer i: projectedTesting.getWeekends()){
						//projectedTesting.addInvalidField(columnNames[i], projectedTesting.getMonth(), true, " For Date "+i+" is a weekend date.");
						validationErrors.rejectValue(columnNames[i], "", 
								new String[]{lineNumber, mappedFieldNames.get(columnNames[i])},
								"The date "+i+" with month "+projectedTesting.getMonth()+" is a week-end date.");
	        			validationPassed = false;
					}				
				}*/
			 
		}
                
        if( !validationPassed ){
        	for( InValidDetail inValidDetail  : projectedTesting.getInValidDetails() ){
        		String errMsg = new StringBuilder(inValidDetail.getInValidMessage()).toString() ;
				String fieldName = inValidDetail.getActualFieldName();
				validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
        	}
        }
        else
        {
        	projectedTesting.setCreatedUser(userDetails.getUserId());
        	projectedTesting.setModifiedUser(userDetails.getUserId());
        	
        }
		
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", projectedTesting);
		return customValidationResults;	
	}

}