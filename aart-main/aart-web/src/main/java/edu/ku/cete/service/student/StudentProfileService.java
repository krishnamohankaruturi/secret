package edu.ku.cete.service.student;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ku.cete.domain.ProfileAttribute;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.student.ProfileItemAttribute;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.web.ProfileItemAttributeDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

/**
 * @author m802r921
 *
 */
public interface StudentProfileService {

	/**
	 * Add the selected attribute values to the profile of the given student.
	 * 
	 * @param selectedAttributeValues
	 * @param student
	 */
	List<PersonalNeedsProfileRecord> addProfileToStudent(List<? extends StudentSchoolRelationInformation> studentSchoolRelations);
	
	/**
	 * @param stateStudentIdentifier
	 * @return
	 */
	List<StudentProfileItemAttributeDTO> getProfileByStateStudentIdentifier(Long studentId);	

	/**
	 * @param studentId
	 * @return
	 */
	List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainers(Long studentId, User user);
	
	/**
	 * @param studentId
	 * @return
	 */
	List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainersSection(Long studentId, User user);
	
	/**
	 * @param studentProfileItemAttributeData
	 * @param StudentId
	 * @return
	 */
	Boolean saveStudentProfileItemAttributes(Map<String, String> studentProfileItemAttributeData, Long StudentId) throws JsonProcessingException ;
	
	List<StudentProfileItemAttributeDTO> getStudentProfileItemAttribute(Long studentId, List<String> itemAttributeList);
	List<StudentProfileItemAttributeDTO> getStudentProfileItemContainer(Long studentId, List<String> itemAttributeList);
	
	Long countValuesInAttributeAndContainerForStudents(List<Long> studentIds,
			String containerName, String attributeName, String value, String condition);

	List<Long> getStudentIdsWithAttributeValueForStudents(List<Long> studentIds,
			String containerName, String attributeName, String value, String condition);
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test Coordination - enhance Test Information PDF functionality
     * Get count of students for large print, paper pencil and spanish prints based on their PNP data and given test session id.
	 * @param testSessionId
	 * @return
	 */
	Map<String, Long> getStudentPNPValuesByTestSession(Long testSessionId);

	List<ProfileAttribute> getStudentProfileItemAttributes(Long studentId);

	Long getPianacId(String attributeContainer, String attributeName);
	
	List<ProfileItemAttributeDTO> getAttributesWithSpecialOptionsForAssessmentPrograms(List<Long> assessmentProgramIds, Long stateId);
	
	/**
	 * This method will go through the settings that are disabled for the given assessment programs
	 * and remove those settings from the student, if the student has them selected.
	 * <br/><br/>
	 * NOTE: The <code>assessmentProgramIds</code> parameter is the list of assessment programs to KEEP, not the ones being removed.
	 * @param studentId
	 * @param assessmentProgramIds
	 * @return
	 * @throws JsonProcessingException
	 */
	Boolean removeNonAssociatedPNPSettings(Long studentId, List<Long> assessmentProgramIds, Long userId, Long stateId) throws JsonProcessingException;

	int updateStudentPNPOption(Long studentId, String containerName, String attributeName, String value, Long userId);

	void insertPNPAuditHistory(Long studentId, String beforeValueJson, String afterValueJson)
			throws JsonProcessingException;

	String determinePNPStatus(Long studentId);

	void insertOrUpdateStudentAttributes(Long studentId) throws JsonProcessingException;
	
	void removeActivateByDefaultsFromContainersWithoutSupport(Long studentId, Long userId);

	boolean checkToUnenrollTestSessionsBasedOnPNP(String assessmentProgram, long studentId,
			Map<String, String> studentProfileItemAttributeData);

	void pnpUnenrollTests(Long studentId, String assessmentProgramAbbrName, User modifyingUser);

	Map<String, String> getSelectedValuesForStudent(Long studentId);
	
	List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainersByAttributeName(Long studentId, User user,String attributeName,String viewOption);
}