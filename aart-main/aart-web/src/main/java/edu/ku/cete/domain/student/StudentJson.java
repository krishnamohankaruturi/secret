package edu.ku.cete.domain.student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.util.CommonConstants;

public class StudentJson {

	private Long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String username;
	private String stateStudentIdentifier;
	private String generation;
	private Date dateOfBirth;
	private String gender;
	private String comprehensiveResponse;
	private String hispanicEthnicity;
	private String firstLanguage;
	private String primaryDisability;
	private String eSOLParticipation;
	private List<String> assessmentPrograms;

	private String exitReason;
	private Date exitDate;
	private String exitedSchool;

	private String destinationDistrict;
	private String destinationAttendanceSchool;
	private String destinationAypSchool;
	private String destinationLocalId;

    private List<StudentOrganization> studentOrganization;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getStatestudentidentifier() {
		return stateStudentIdentifier;
	}

	public void setStatestudentidentifier(String statestudentidentifier) {
		this.stateStudentIdentifier = statestudentidentifier;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getComprehensiveResponse() {
		return comprehensiveResponse;
	}

	public void setComprehensiveResponse(String comprehensiveResponse) {
		this.comprehensiveResponse = comprehensiveResponse;
	}

	public String isHispanicEthnicity() {
		return hispanicEthnicity;
	}

	public void setHispanicEthnicity(String hispanicEthnicity) {
		this.hispanicEthnicity = hispanicEthnicity;
	}

	public String getFirstLanguage() {
		return firstLanguage;
	}

	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public String getPrimaryDisability() {
		return primaryDisability;
	}

	public void setPrimaryDisability(String primaryDisability) {
		this.primaryDisability = primaryDisability;
	}

	public String geteSOLParticipation() {
		return eSOLParticipation;
	}

	public void seteSOLParticipation(String eSOLParticipation) {
		this.eSOLParticipation = eSOLParticipation;
	}

	public List<String> getAssessmentPrograms() {
		return assessmentPrograms;
	}

	public void setAssessmentPrograms(List<String> assessmentPrograms) {
		this.assessmentPrograms = assessmentPrograms;
	}

	public String getExitReason() {
		return exitReason;
	}

	public void setExitReason(String exitReason) {
		this.exitReason = exitReason;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public String getExitedSchool() {
		return exitedSchool;
	}

	public void setExitedSchool(String exitedSchool) {
		this.exitedSchool = exitedSchool;
	}	

	public String getDestinationDistrict() {
		return destinationDistrict;
	}

	public void setDestinationDistrict(String destinationDistrict) {
		this.destinationDistrict = destinationDistrict;
	}

	public String getDestinationAttendanceSchool() {
		return destinationAttendanceSchool;
	}

	public void setDestinationAttendanceSchool(
			String destinationAttendanceSchool) {
		this.destinationAttendanceSchool = destinationAttendanceSchool;
	}

	public String getDestinationAypSchool() {
		return destinationAypSchool;
	}

	public void setDestinationAypSchool(String destinationAypSchool) {
		this.destinationAypSchool = destinationAypSchool;
	}

	public String getDestinationLocalId() {
		return destinationLocalId;
	}

	public void setDestinationLocalId(String destinationLocalId) {
		this.destinationLocalId = destinationLocalId;
	}
	
	public List<StudentOrganization> getStudentOrganization() {
		return studentOrganization;
	}

	public void setStudentOrganization(List<StudentOrganization> studentOrganization) {
		this.studentOrganization = studentOrganization;
	}

	public String buildjsonString() {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			DateFormat df = new SimpleDateFormat(CommonConstants.JSON_TIME_FORMAT);
			mapper.setDateFormat(df);
			
			jsonInString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return jsonInString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	

}
