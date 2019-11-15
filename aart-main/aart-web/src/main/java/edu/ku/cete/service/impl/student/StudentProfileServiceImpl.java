/**
 * 
 */
package edu.ku.cete.service.impl.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ProfileAttribute;
import edu.ku.cete.domain.StudentPNPAttributes;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.PnpAssessmentKeyComparator;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentProfileItemAttributeValue;
import edu.ku.cete.domain.student.StudentProfileItemAttributeValueExample;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.student.ProfileAttributeDao;
import edu.ku.cete.model.student.StudentProfileItemAttributeValueDao;
import edu.ku.cete.report.domain.StudentPNPAuditHistory;
import edu.ku.cete.report.model.StudentPNPAuditHistoryDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.web.ProfileItemAttributeDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

/**
 * @author mahesh
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentProfileServiceImpl implements StudentProfileService {

	/**
	 * profileAttributeDao.
	 */
	@Autowired
	private ProfileAttributeDao profileAttributeDao;
	/**
     * logger.
     */
    private final Logger
    logger = LoggerFactory.getLogger(StudentProfileServiceImpl.class);	
	
	/**
	 * studentProfileItemAttributeValueDao.
	 */
	@Autowired
	private StudentProfileItemAttributeValueDao studentProfileItemAttributeValueDao;
	
	@Autowired
	private StudentPNPAuditHistoryDao studentPNPAuditHistoryDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private StudentTrackerService studentTrackerService;	
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;	
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentServices;
	
	@Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${ismart2.assessmentProgram.abbreviatedName}")
	private String ISMART_2_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${pltw.assessmentProgram.abbreviatedName}")
	private String PLTW_PROGRAM_ABBREVIATEDNAME;
	
	private final PnpAssessmentKeyComparator PNP_HIERARCHY_COMPARATOR = new PnpAssessmentKeyComparator();
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.student.StudentProfileService#addProfileToStudent(java.util.Map, edu.ku.cete.domain.common.Student)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<PersonalNeedsProfileRecord> addProfileToStudent(List<? extends StudentSchoolRelationInformation> studentSchoolRelations) {
		
		List<PersonalNeedsProfileRecord> personalNeedsProfileRecordsList = new ArrayList<PersonalNeedsProfileRecord>();		
        List<ProfileItemAttributeDTO> profileItemAttributesDTOList = null;
        Map<String,ProfileItemAttributeDTO> profileItemAttributeMap = new HashMap<String, ProfileItemAttributeDTO>();		
		PersonalNeedsProfileRecord personalNeedsProfileRecord = null;
		StudentProfileItemAttributeValue studentProfileItemAttributeValue = null;
		StudentProfileItemAttributeValueExample studentProfileItemAttributeValueExample = null;
		int updated = 0;
		String profileItemAttributeMappKey = "";
		
		//Get all attribute names, nick names and container names before iterating thru PNP records
		profileItemAttributesDTOList = profileAttributeDao.selectAllAttributeNamesAndNicknames();			
		
        logger.debug("Personal needs profile record profileItemAttributes - " + profileItemAttributesDTOList);

        //TODO: Do it in a post construct
        //Populate map with attributeNames and attributeNicknames as keys and corresponding objects as values.
		for(ProfileItemAttributeDTO profileItemAttributesDTO : profileItemAttributesDTOList) {
        	profileItemAttributeMap.put(profileItemAttributesDTO.getAttributeName().toLowerCase() +
        			profileItemAttributesDTO.getAttributeContainerName().toLowerCase(), profileItemAttributesDTO);
        	if(profileItemAttributesDTO.getAttributeNickname() != null && 
        			StringUtils.isNotEmpty(profileItemAttributesDTO.getAttributeNickname())) {
        		profileItemAttributeMap.put(profileItemAttributesDTO.getAttributeNickname().toLowerCase() + 
        			profileItemAttributesDTO.getAttributeContainerName().toLowerCase(), profileItemAttributesDTO);
        	}
        }
        logger.debug("Personal needs profile record profileItemAttributeMap - " + profileItemAttributeMap);

        //loop thru each PNP record.
        for(StudentSchoolRelationInformation studentSchoolRelation : studentSchoolRelations) {
        	profileItemAttributeMappKey= "";
        	personalNeedsProfileRecord = (PersonalNeedsProfileRecord) studentSchoolRelation;
        	if(personalNeedsProfileRecord.getAttributeName() != null &&
        			personalNeedsProfileRecord.getAttributeContainer() != null) {
        		profileItemAttributeMappKey = personalNeedsProfileRecord.getAttributeName().toLowerCase() + 
	        			personalNeedsProfileRecord.getAttributeContainer().toLowerCase();
        	}
			//Reject the records for which student-organization relationship failed.
        	if(!personalNeedsProfileRecord.isDoReject()) {
				
				if(StringUtils.isNotEmpty(profileItemAttributeMappKey) && 
						profileItemAttributeMap.get(profileItemAttributeMappKey) != null) {
					
					studentProfileItemAttributeValue = new StudentProfileItemAttributeValue();
					
					studentProfileItemAttributeValue
							.setProfileItemAttributenameAttributeContainerId(profileItemAttributeMap
									.get(profileItemAttributeMappKey).getId());
					studentProfileItemAttributeValue
							.setSelectedValue(personalNeedsProfileRecord.getAttributeValue());
					studentProfileItemAttributeValue.setStudentId(personalNeedsProfileRecord.getStudent().getId());
					
					//find and update existing selected attribute value if exists.
					studentProfileItemAttributeValueExample = 
							new StudentProfileItemAttributeValueExample();
					StudentProfileItemAttributeValueExample.Criteria studentProfileExampleCriteria = 
							studentProfileItemAttributeValueExample.createCriteria();
					studentProfileExampleCriteria.andStudentIdEqualTo(personalNeedsProfileRecord.getStudent()
							.getId());
					studentProfileExampleCriteria
							.andProfileItemAttributenameAttributeContainerIdEqualTo(profileItemAttributeMap
									.get(profileItemAttributeMappKey).getId());
					studentProfileItemAttributeValue
							.setAuditColumnPropertiesForUpdate();
					
			        logger.debug("About to update " + studentProfileItemAttributeValue);
					updated = studentProfileItemAttributeValueDao
							.updateByExample(studentProfileItemAttributeValue,
									studentProfileItemAttributeValueExample);
			        logger.debug("Updated " + studentProfileItemAttributeValue);
			
					if (updated < 1) {
						//it needs to be inserted.
				        logger.debug("About to insert " + studentProfileItemAttributeValue);
						studentProfileItemAttributeValue.setAuditColumnProperties();
						studentProfileItemAttributeValueDao
								.insert(studentProfileItemAttributeValue);
				        logger.debug("inserted " + studentProfileItemAttributeValue);					
				        personalNeedsProfileRecord.setCreated(true);
					}
				} else {
					studentSchoolRelation.addInvalidField(
							FieldName.ATTRIBUTE_NAME_ATTRIBUTE_CONTAINER + ParsingConstants.BLANK,
							personalNeedsProfileRecord
							.getAttributeName()
							+ ParsingConstants.OUTER_DELIM
							+ personalNeedsProfileRecord
							.getAttributeContainer(),
							true, InvalidTypes.NOT_FOUND);
				}
			}
			personalNeedsProfileRecordsList.add(personalNeedsProfileRecord);
        }
        
		return personalNeedsProfileRecordsList;				
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentProfileItemAttributeDTO> getProfileByStateStudentIdentifier(Long studentId) {
		
		return studentProfileItemAttributeValueDao.selectByStateStudentIdentifier(studentId);
			
	}
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.student.StudentProfileService#selectAllAttributeNamesAndContainers()
	 */
	
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainers(Long studentId, User user) {

		List<StudentProfileItemAttributeDTO> studentProfileItemAttributesDTOList = null;

		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		//Get all attribute names, container names for a particular student along with all attributes/containers from dictionary.
		studentProfileItemAttributesDTOList = studentProfileItemAttributeValueDao.selectAllAttributesDataAndStudentSelection(studentId, currentSchoolYear,user.getCurrentAssessmentProgramId());

		return studentProfileItemAttributesDTOList;
	}
    
    /* (non-Javadoc)
	 * @see edu.ku.cete.service.student.StudentProfileService#selectAllAttributeNamesAndContainers()
	 */
	
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainersSection(Long studentId, User user) {

		List<StudentProfileItemAttributeDTO> studentProfileItemAttributesDTOList = null;

		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		//Get all attribute names, container names for a particular student along with all attributes/containers from dictionary.
		studentProfileItemAttributesDTOList = studentProfileItemAttributeValueDao.selectStudentAttributesAndContainers(studentId, currentSchoolYear,user.getCurrentAssessmentProgramId());

		return studentProfileItemAttributesDTOList;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.student.StudentProfileService#saveStudentProfileItemAttributes(java.util.Map, java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public Boolean saveStudentProfileItemAttributes(Map<String, String> studentProfileItemAttributeData, Long studentId) throws JsonProcessingException {
		String beforeValueJson = studentProfileItemAttributeValueDao.getStudentValueJson(studentId);
		StudentProfileItemAttributeValue studentProfileItemAttributeValue = null;
		StudentProfileItemAttributeValueExample studentProfileItemAttributeValueExample = null;
		int updated = 0;
		int inserted = 0;
		Boolean success = true;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		AssessmentProgram userAssessmentProgram = assessmentProgramService.findByStudentId(studentId, user);
		String assessmentProgram = "";
		if(userAssessmentProgram!=null){
			if(userAssessmentProgram.getAbbreviatedname().equalsIgnoreCase("KAP") || userAssessmentProgram.getAbbreviatedname().equalsIgnoreCase("AMP") 
					|| userAssessmentProgram.getAbbreviatedname().equalsIgnoreCase("DLM") 
					|| PLTW_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(userAssessmentProgram.getAbbreviatedname())
					|| ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())
					|| ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())){
				assessmentProgram = userAssessmentProgram.getAbbreviatedname();
				if(ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())
						|| ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())){
					assessmentProgram = user.getCurrentAssessmentProgramName();
				}
				if(checkToUnenrollTestSessionsBasedOnPNP(assessmentProgram, studentId, studentProfileItemAttributeData)){
					pnpUnenrollTests(studentId, assessmentProgram, user);
				}
			}
		}
		
		boolean hasprofilestatus=false;
		
		for (Map.Entry<String, String> studentProfileItemAttributeEntry : studentProfileItemAttributeData.entrySet()) {
			if(!studentProfileItemAttributeEntry.getKey().equals("undefined")){
			studentProfileItemAttributeValue = new StudentProfileItemAttributeValue();
			if(studentProfileItemAttributeEntry.getKey() != null && studentProfileItemAttributeEntry.getKey().equals("undefined")){
				logger.error("AP: "+ assessmentProgram +" studentProfileItemAttributeData : " + studentProfileItemAttributeData);
			}
			studentProfileItemAttributeValue
					.setProfileItemAttributenameAttributeContainerId(Long.parseLong(studentProfileItemAttributeEntry.getKey()));
			studentProfileItemAttributeValue
					.setSelectedValue(studentProfileItemAttributeEntry.getValue());
			studentProfileItemAttributeValue.setStudentId(studentId);
			
			//find and update existing selected attribute value if exists.
			studentProfileItemAttributeValueExample = 
					new StudentProfileItemAttributeValueExample();
			StudentProfileItemAttributeValueExample.Criteria studentProfileExampleCriteria = 
					studentProfileItemAttributeValueExample.createCriteria();
	        
			studentProfileExampleCriteria.andStudentIdEqualTo(studentId);
			studentProfileExampleCriteria
					.andProfileItemAttributenameAttributeContainerIdEqualTo(Long.parseLong(studentProfileItemAttributeEntry.getKey()));
			studentProfileItemAttributeValue
					.setAuditColumnPropertiesForUpdate();
			
			
			if("true".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
					|| "setting".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
							|| "dictated".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
									|| "communication".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
											|| "responses".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
													|| "assessment".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
															|| "translations".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
																	|| "accommodation".equalsIgnoreCase(studentProfileItemAttributeValue.getSelectedValue())
							
			){
				hasprofilestatus = true;
			} else {
				
			}
	        logger.debug("About to update " + studentProfileItemAttributeValue);
			updated = studentProfileItemAttributeValueDao
					.updateByExample(studentProfileItemAttributeValue,
							studentProfileItemAttributeValueExample);
	        logger.debug("Updated " + studentProfileItemAttributeValue);
	
			if (updated < 1) {
				//it needs to be inserted.
		        logger.debug("About to insert " + studentProfileItemAttributeValue);
				studentProfileItemAttributeValue.setAuditColumnProperties();
				inserted = studentProfileItemAttributeValueDao
						.insert(studentProfileItemAttributeValue);
		        logger.debug("inserted " + studentProfileItemAttributeValue);					
		        
			}
			
			if(success && (updated > 0 || inserted > 0))
				success = true;
			else 
				success = false;
		}
		}
		insertOrUpdateStudentAttributes(studentId);
		String afterValueJson = studentProfileItemAttributeValueDao.getStudentValueJson(studentId);
		insertPNPAuditHistory(studentId,beforeValueJson,afterValueJson);
		
		/**
		 * Biyatpragyan Mohanty (bmohanty_sta@ku.edu) : US15537 : Technical: Fix PNP to store status like custom/no setting 
		 * and modify view student queries a/c application to get status directly from stored field
		 * After PNP data has been stored, update student record with pnp status as "CUSTOM", the default value is "NO SETTINGS".
		 */
		String status = "NO SETTINGS";
		if(hasprofilestatus) {
			status = "CUSTOM";			
		}
		int studentUpdated = studentDao.updateProfileStatusByStudentId(studentId, status,
				studentProfileItemAttributeValue.getModifiedDate(), studentProfileItemAttributeValue.getModifiedUser());
		
		return success && studentUpdated > 0;
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void pnpUnenrollTests(Long studentId, String assessmentProgramAbbrName, User modifyingUser) {
		List<TestSession> testSessions = testSessionService.selectTestSessionByStudentIdAndSourceWithActiveOTW("BATCHAUTO", studentId, assessmentProgramAbbrName);
		for(TestSession testSession : testSessions){
			boolean removeTest = testSession.getName() == null ? true : !testSession.getName().startsWith("Predictive_");
			if (removeTest) {
				/*
				 * Calling the method only for KAP
				 */
				/*
				 * Code change is for PRod Defect - 17013 where scoring assignments is not deactivated
				 * when the test session is deactivated on PNP change
				 * Inside the reassign code we are checking for only KAP HGSS TESTS FOR NOW
				 */
				if ("KAP".equalsIgnoreCase(assessmentProgramAbbrName)) {
					List<StudentsTests> sts = studentsTestsService
							.selectKapSSByTestSessionIdAndStudentId(testSession.getId(), studentId);
					for (StudentsTests st : sts) {
						scoringAssignmentServices.deleteScoringAssignment(st, modifyingUser);
					}
				}

				studentsTestsService.pnpUnenrollStudentsTests(testSession.getId(), studentId, modifyingUser);
				testSessionService.pnpUnenrollTestSession(testSession.getId(), modifyingUser);
				if("DLM".equalsIgnoreCase(assessmentProgramAbbrName)) {
					studentTrackerService.clearTestSessionByStudentIdAndTestSessionId(studentId, testSession.getId(), modifyingUser.getId());
				}
			}
		}
	}
	
	@Override
	public void insertOrUpdateStudentAttributes(Long studentId) throws JsonProcessingException {
		if (studentId != null) {
			StudentPNPAttributes studentPNPAttributes = getAttributes(studentId);
			boolean exists = studentProfileItemAttributeValueDao.existsStudentPNPAttributes(studentPNPAttributes);
			if(exists) {
				studentPNPAttributes.setAuditColumnPropertiesForUpdate();
				studentProfileItemAttributeValueDao.updateStudentPNPAttributes(studentPNPAttributes);
			} else {
				studentPNPAttributes.setAuditColumnProperties();
				studentProfileItemAttributeValueDao.insertStudentPNPAttributes(studentPNPAttributes);
			}
		}
	}

	@Override
	public List<ProfileAttribute> getStudentProfileItemAttributes(Long studentId) {
		return studentProfileItemAttributeValueDao.getStudentPNPAttributes(studentId);
	} 
	
	@Override
	public List<StudentProfileItemAttributeDTO> getStudentProfileItemAttribute(Long studentId, List<String> itemAttributeList) {
		return studentProfileItemAttributeValueDao.selectStudentProfileItemAttribute(studentId, itemAttributeList);
	} 
	
	@Override
	public List<StudentProfileItemAttributeDTO> getStudentProfileItemContainer(Long studentId, List<String> itemAttributeList) {
		return studentProfileItemAttributeValueDao.selectStudentProfileItemContainer(studentId, itemAttributeList);
	}

	@Override
	public Long countValuesInAttributeAndContainerForStudents(
			List<Long> studentIds, String containerName, String attributeName,
			String value, String condition) {
		return studentProfileItemAttributeValueDao.countValuesInAttributeAndContainerForStudentIds(
				studentIds, containerName, attributeName, value, condition);
	}
	
	@Override
	public List<Long> getStudentIdsWithAttributeValueForStudents(
			List<Long> studentIds, String containerName, String attributeName,
			String value, String condition) {
		return studentProfileItemAttributeValueDao.getStudentIdsWithAttributeValueForStudents(
				studentIds, containerName, attributeName, value, condition);
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test Coordination - enhance Test Information PDF functionality
     * Get count of students for large print, paper pencil and spanish prints based on their PNP data and given test session id.
	 */
	@Override
	public Map<String, Long> getStudentPNPValuesByTestSession(Long testSessionId){
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("largePrintBooklet");
		itemAttributeList.add("paperAndPencil");
		itemAttributeList.add("Language");
		itemAttributeList.add("assignedSupport");
		
		Map<String, Long> dmap = new HashMap<String, Long>(); 
		List<String> data = studentProfileItemAttributeValueDao.selectStudentPNPValuesByTestSession(testSessionId, itemAttributeList);
		
		Set<Long> largePrintSet = new HashSet<Long>();
		Set<Long> paperpencilSet = new HashSet<Long>();
		Set<Long> languageSet = new HashSet<Long>();
		
		if(data!=null && data.size()>0){
			for(String str : data){
				String[] strr = str.split(",");
				if("largePrintBooklet".equals(strr[1]) && "true".equalsIgnoreCase(strr[2])){
					largePrintSet.add(new Long(strr[0]));
				} else if("paperAndPencil".equals(strr[1]) && "true".equalsIgnoreCase(strr[2])){
					paperpencilSet.add(new Long(strr[0]));
				} else if("assignedSupport".equals(strr[1]) && "true".equalsIgnoreCase(strr[2])){
					languageSet.add(new Long(strr[0]));
				}else if("Language".equals(strr[1]) && !"spa".equalsIgnoreCase(strr[2])){
					Long val = new Long(strr[0]);
					if(languageSet.contains(val)){
						languageSet.remove(val);
					}
				} 
			}
		}
		
		dmap.put("largePrintBooklet", new Long(largePrintSet.size()));
		dmap.put("paperAndPencil", new Long(paperpencilSet.size()));
		dmap.put("Language", new Long(languageSet.size()));
		
		return dmap;
	}
	
	@Override
	public boolean checkToUnenrollTestSessionsBasedOnPNP(String assessmentProgram, long studentId, Map<String, String> studentProfileItemAttributeData){
		boolean checkResult = false;
 		List<String> itemAttributeList = new ArrayList<String>();
		List<StudentProfileItemAttributeDTO> pnpAttribuites = new ArrayList<StudentProfileItemAttributeDTO>();
		if(assessmentProgram.equals("KAP")){
			itemAttributeList.add("onscreenKeyboard"); //Single Switches 
			itemAttributeList.add("Spoken");
			itemAttributeList.add("Braille");
			itemAttributeList.add("Signing");
			itemAttributeList.add("keywordTranslationDisplay");
			pnpAttribuites = getStudentProfileItemAttribute(studentId, itemAttributeList);
			itemAttributeList.clear();
			itemAttributeList.add("supportsTwoSwitch");//Two switch system
			itemAttributeList.add("Language");//Language
			itemAttributeList.add("SigningType");
			itemAttributeList.add("UserSpokenPreference");		
			pnpAttribuites.addAll(getStudentProfileItemContainer(studentId, itemAttributeList));
		} else if(assessmentProgram.equals("AMP")){
			itemAttributeList.add("onscreenKeyboard");
			itemAttributeList.add("Spoken");
			itemAttributeList.add("Signing");
			pnpAttribuites = getStudentProfileItemAttribute(studentId, itemAttributeList);
			itemAttributeList.clear();
			itemAttributeList.add("supportsTwoSwitch");
			itemAttributeList.add("SigningType");
			itemAttributeList.add("preferenceSubject");			
			pnpAttribuites.addAll(getStudentProfileItemContainer(studentId, itemAttributeList));
		} else if(assessmentProgram.equals("DLM")){
			itemAttributeList.add("onscreenKeyboard");
			itemAttributeList.add("Spoken");
			itemAttributeList.add("Braille");
			pnpAttribuites = getStudentProfileItemAttribute(studentId, itemAttributeList);
			itemAttributeList.clear();
			itemAttributeList.add("visualImpairment");//Alternate Form - Visual Impairment
			pnpAttribuites.addAll(getStudentProfileItemContainer(studentId, itemAttributeList));
		}else if(ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(assessmentProgram)
				|| ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(assessmentProgram)){
			itemAttributeList.add("onscreenKeyboard");
			itemAttributeList.add("Spoken");
			pnpAttribuites = getStudentProfileItemAttribute(studentId, itemAttributeList);
			itemAttributeList.clear();
			itemAttributeList.add("visualImpairment");//Alternate Form - Visual Impairment
			pnpAttribuites.addAll(getStudentProfileItemContainer(studentId, itemAttributeList));
			
		}else if(PLTW_PROGRAM_ABBREVIATEDNAME.equals(assessmentProgram)) {
			//TODO 08/30/2018 - This needs to be updated once we get the finalized settings, for now using KAP settings
			itemAttributeList.add("onscreenKeyboard"); //Single Switches 
			itemAttributeList.add("Spoken");
			itemAttributeList.add("Braille");
			itemAttributeList.add("Signing");
			itemAttributeList.add("keywordTranslationDisplay");
			pnpAttribuites = getStudentProfileItemAttribute(studentId, itemAttributeList);
			itemAttributeList.clear();
			itemAttributeList.add("supportsTwoSwitch");//Two switch system
			itemAttributeList.add("Language");//Language
			itemAttributeList.add("SigningType");
			itemAttributeList.add("UserSpokenPreference");		
			pnpAttribuites.addAll(getStudentProfileItemContainer(studentId, itemAttributeList));
		}
		
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			String newItemValue = studentProfileItemAttributeData.get(Long.toString(pnpAttribute.getAttributeNameAttributeContainerId()));
			if(newItemValue == null || newItemValue.length() == 0)
				newItemValue = "false";
			String existingItemValue = "";
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0){
				if(pnpAttribute.getAttributeName().equalsIgnoreCase("Language") || pnpAttribute.getAttributeName().equalsIgnoreCase("SigningType")){
					existingItemValue = newItemValue;
				}else{
					existingItemValue = "false";
				}
			}
			else
				existingItemValue = pnpAttribute.getSelectedValue();
			
			if(!newItemValue.equalsIgnoreCase(existingItemValue)){
				checkResult = true;
				break;
			}
		}
		return checkResult;
	}

	@Override
	public Long getPianacId(String attributeContainer, String attributeName) {
		return profileAttributeDao.selectPianacId(attributeContainer, attributeName);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private StudentPNPAttributes getAttributes(Long studentId) throws JsonProcessingException {
		List<ProfileAttribute> profileAttributes = getStudentProfileItemAttributes(studentId);
		StudentPNPAttributes studentPNPAttributes = new StudentPNPAttributes(studentId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		if(profileAttributes != null && !profileAttributes.isEmpty()){
			studentPNPAttributes.setJsonText(objectMapper.writeValueAsString(profileAttributes));
		}
		return studentPNPAttributes;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ProfileItemAttributeDTO> getAttributesWithSpecialOptionsForAssessmentPrograms(List<Long> assessmentProgramIds, Long stateId) {
		return profileAttributeDao.selectHiddenOrDisabledOptionsForAssessmentPrograms(assessmentProgramIds, stateId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean removeNonAssociatedPNPSettings(Long studentId, List<Long> assessmentProgramIds, Long userId, Long stateId) throws JsonProcessingException {
		List<StudentProfileItemAttributeDTO> existingRecords = getProfileByStateStudentIdentifier(studentId);
		List<ProfileItemAttributeDTO> attributesToRemove =
				getAttributesWithSpecialOptionsForAssessmentProgramsByHierarchy(assessmentProgramIds, stateId);

		if (CollectionUtils.isNotEmpty(existingRecords) && CollectionUtils.isNotEmpty(attributesToRemove)) {
			Date now = new Date();
			for (ProfileItemAttributeDTO attributeToRemove : attributesToRemove) {
				String viewOption = attributeToRemove.getViewOption();
				StudentProfileItemAttributeDTO existingRecord = searchForPianac(existingRecords, attributeToRemove.getId());
				if (existingRecord != null) {
					String selectedValue = existingRecord.getSelectedValue();
					
					String newSelectedValue = "";
					boolean update = false;
					
					if (viewOption != null) {
						// these are special cases
						if (viewOption.contains("disable_") || viewOption.contains("hide_")) {
							newSelectedValue = selectedValue;
							String[] options = viewOption.split(",");
							for (int x = 0; x < options.length; x++) {
								String[] option = options[x].split("_");
								if (selectedValue.contains(option[1])) {
									newSelectedValue = newSelectedValue
											.replaceAll(option[1], "")
											.replaceAll(",,", ",")
											.replaceAll("^,", "")
											.replaceAll(",$", "");
									update = true;
								}
							}
						} else if (!selectedValue.equalsIgnoreCase(attributeToRemove.getNonSelectedValue())) {
							update = true;
							newSelectedValue = attributeToRemove.getNonSelectedValue();
						}
					}
					
					if (update) {
						StudentProfileItemAttributeValue spiav = new StudentProfileItemAttributeValue();
						spiav.setId(existingRecord.getId());
						spiav.setSelectedValue(newSelectedValue);
						spiav.setModifiedUser(userId);
						spiav.setModifiedDate(now);
						
						studentProfileItemAttributeValueDao.updateByPrimaryKeySelective(spiav);
					}
				}
			}
			insertOrUpdateStudentAttributes(studentId);
			
			String pnpStatus = determinePNPStatus(studentId);
			studentDao.updateProfileStatusByStudentId(studentId, pnpStatus, now, userId);
		}
		return true;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	protected List<ProfileItemAttributeDTO> getAttributesWithSpecialOptionsForAssessmentProgramsByHierarchy(List<Long> assessmentProgramIds, Long stateId) {
		List<AssessmentProgram> aps = new ArrayList<AssessmentProgram>(assessmentProgramIds.size());
		for (Long apId : assessmentProgramIds) {
			AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
			aps.add(ap);
		}
		
		final Comparator<AssessmentProgram> hierarchyComparator = new Comparator<AssessmentProgram>(){
			public int compare(AssessmentProgram a, AssessmentProgram b) {
				return PNP_HIERARCHY_COMPARATOR.compare(a.getAbbreviatedname(), b.getAbbreviatedname());
			}
		};
		
		// reverse sort order from hierarchyComparator, because it gives it to us in priority order
		Collections.sort(aps, hierarchyComparator);
		Collections.reverse(aps);
		
		List<ProfileItemAttributeDTO> attributesToRemove = new ArrayList<ProfileItemAttributeDTO>();
		boolean populatedOnce = false;
		for (int a = 0; a < aps.size(); a++) {
			AssessmentProgram ap = aps.get(a);
			List<ProfileItemAttributeDTO> tmpAttributesToRemove =
					getAttributesWithSpecialOptionsForAssessmentPrograms(Arrays.asList(ap.getId()), stateId);
			logger.debug(ap.getAbbreviatedname() + " retrieved " + tmpAttributesToRemove.size() + " records");
			for (ProfileItemAttributeDTO attr : tmpAttributesToRemove) {
				logger.debug(attr.getAttributeContainerName() + "/" + attr.getAttributeName());
			}
			if (!populatedOnce && CollectionUtils.isEmpty(attributesToRemove)) {
				attributesToRemove.addAll(tmpAttributesToRemove);
				populatedOnce = true;
			} else {
				// these next 2 loops are probably not the most efficient way to do it, but right now I can't think of a better way
				// **NOTE**: we can't use .contains() in the following loops, because it uses Object.equals() instead of the one specified with @Override.
				
				// first, remove any attributes that were specified in a lower AP, but NOT in a higher AP
				// this means they were disabled in a lower-priority AP, but the higher-priority AP has it enabled
				for (int at = 0; at < attributesToRemove.size(); at++) {
					ProfileItemAttributeDTO attr = attributesToRemove.get(at);
					
					boolean foundInHigherLevel = false;
					for (int t = 0; t < tmpAttributesToRemove.size() && !foundInHigherLevel; t++) {
						ProfileItemAttributeDTO tmpAttr = tmpAttributesToRemove.get(t);
						if (attr.equals(tmpAttr)) {
							foundInHigherLevel = true;
						}
					}
					if (!foundInHigherLevel) {
						logger.debug("found " + attr.getAttributeContainerName() + "/" + attr.getAttributeName() +
								" at a lower level, but not in the higher-priority level for " + ap.getAbbreviatedname() + " - removing");
						attributesToRemove.remove(at--);
					}
				}
				
				// second, add attributes that are specified in the higher AP, but not necessarily in the lower AP
				for (int t = 0; t < tmpAttributesToRemove.size(); t++) {
					ProfileItemAttributeDTO tmpAttr = tmpAttributesToRemove.get(t);
					boolean found = false;
					for (int at = 0; at < attributesToRemove.size() && !found; at++) {
						if (attributesToRemove.get(at).equals(tmpAttr)) {
							found = true;
						}
					}
					if (!found) {
						attributesToRemove.add(tmpAttr);
					}
				}
			}
		}
		
		return attributesToRemove;
	}
	
	@Override
	public void insertPNPAuditHistory(Long studentId,String beforeValueJson,String afterValueJson) throws JsonProcessingException {
		try {
			
			StudentPNPAuditHistory studentPNPAuditHistory = new StudentPNPAuditHistory(studentId);
			ObjectMapper mapper = new ObjectMapper();
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Student students = studentDao.getStudentDetailsById(studentId);
			String stateName = studentDao.getStudentStateName(studentId);
			studentPNPAuditHistory.setEventName("Edit PNP");
			

			Map<String, JsonNode> beforeMap = new HashMap<>();
			if(beforeValueJson != null){
				JsonNode beforeValues = mapper.readTree(beforeValueJson);
				for(JsonNode node : beforeValues){
					beforeMap.put(node.get("attrContainerId").toString() + node.get("attrName").toString() , node);
				}
			}
			JsonNode afterValues = mapper.readTree(afterValueJson);
			Map<String, JsonNode> afterMap = new HashMap<>();
			for(JsonNode node : afterValues){
				afterMap.put(node.get("attrContainerId").toString() + node.get("attrName").toString() , node);
			}
			
			List<JsonNode> modifiedBeforeAttributes = new ArrayList<>();
			List<JsonNode> modifiedAfterAttributes = new ArrayList<>();
			for(String key : afterMap.keySet()){
				if(beforeMap.get(key) == null){
					modifiedAfterAttributes.add(afterMap.get(key));
				} else if(!beforeMap.get(key).equals(afterMap.get(key))){
					modifiedBeforeAttributes.add(beforeMap.get(key));
					modifiedAfterAttributes.add(afterMap.get(key));
				}
			}

			studentPNPAuditHistory.setBeforeValue(modifiedBeforeAttributes.toString());
			studentPNPAuditHistory.setCurrentValue(modifiedAfterAttributes.toString());
			studentPNPAuditHistory.setLoggedInUserFirstName(user.getFirstName());
			studentPNPAuditHistory.setLoggedInUserLastName(user.getSurName());
			studentPNPAuditHistory.setState(stateName);
			studentPNPAuditHistory.setStateStudentIdentifier(students.getStateStudentIdentifier());
			studentPNPAuditHistory.setStudentFirstName(students.getLegalFirstName());
			studentPNPAuditHistory.setStudentLastName(students.getLegalLastName());
			studentPNPAuditHistory.setAuditColumnProperties();
			studentPNPAuditHistory.setStudentId(studentId);
			studentPNPAuditHistory.setModifiedUser(user==null ? null:user.getId());
			studentPNPAuditHistory.setModifiedUserName(user==null ? null:user.getUserName());
			studentPNPAuditHistory.setModifiedUserFirstName(user==null ? null:user.getFirstName());
			studentPNPAuditHistory.setModifiedUserLastName(user==null ? null:user.getSurName());
			studentPNPAuditHistory.setModifiedUserEducatorIdentifier(user==null ? null:user.getUniqueCommonIdentifier());
			studentPNPAuditHistoryDao.insertStudentPNPAttributesHistory(studentPNPAuditHistory);
		} catch (IOException e) {
			// not a candidate to throw & handle the exception
			logger.error("Error while processing PNP audit history", e);
		}
	}
	
	
	private StudentProfileItemAttributeDTO searchForPianac(List<StudentProfileItemAttributeDTO> dtos, Long pianacId) {
		if (CollectionUtils.isNotEmpty(dtos)) { 
			for (StudentProfileItemAttributeDTO dto : dtos) {
				if (dto.getAttributeNameAttributeContainerId().equals(pianacId)) {
					return dto;
				}
			}
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String determinePNPStatus(Long studentId) {
		List<String> triggersForPNP = Arrays.asList("true", "setting", "dictated", "communication", "responses", "assessment", "translations", "accommodation");
		List<StudentProfileItemAttributeDTO> existingRecords = getProfileByStateStudentIdentifier(studentId);
		for (StudentProfileItemAttributeDTO dto : existingRecords) {
			for (String trigger : triggersForPNP) {
				if (dto.getSelectedValue() != null && dto.getSelectedValue().contains(trigger)) {
					return "CUSTOM";
				}
			}
		}
		return "NO SETTINGS";
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStudentPNPOption(Long studentId, String containerName, String attributeName, String value, Long userId) {
		return studentProfileItemAttributeValueDao.updateStudentPNPOption(studentId, containerName, attributeName, value, userId);
	}

	@Override
	public void removeActivateByDefaultsFromContainersWithoutSupport(Long studentId, Long userId) {
		studentProfileItemAttributeValueDao.removeActivateByDefaultsFromContainersWithoutSupport(studentId, userId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, String> getSelectedValuesForStudent(Long studentId) {
		StudentProfileItemAttributeValueExample ex = new StudentProfileItemAttributeValueExample();
		ex.createCriteria().andStudentIdEqualTo(studentId).andActiveFlagEqualTo(Boolean.TRUE);
		
		List<StudentProfileItemAttributeValue> spiavs = studentProfileItemAttributeValueDao.selectByExample(ex);
		Map<String, String> ret = new HashMap<String, String>();
		
		for (StudentProfileItemAttributeValue spiav : spiavs) {
			ret.put(Long.toString(spiav.getprofileItemAttributenameAttributeContainerId()), spiav.getSelectedValue());
		}
		
		return ret;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainersByAttributeName(Long studentId, User user,String attributeName,String viewOption) {
		List<StudentProfileItemAttributeDTO> studentProfileItemAttributesDTOList = null;

		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		studentProfileItemAttributesDTOList = studentProfileItemAttributeValueDao.selectStudentAttributesAndContainersByAttributeName(studentId, currentSchoolYear,user.getCurrentAssessmentProgramId(),attributeName,viewOption);

		return studentProfileItemAttributesDTOList;
	}
}
