/**
 * 
 */
package edu.ku.cete.domain.enrollment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.student.Student;

/**
 * @author m802r921
 * This Dto has student information and test session information if present
 * 
 */
public class StudentTestSessionDto implements Identifiable{
	
	private Student student;
	
	private StudentsTests studentsTests;
	
	private Long studentId;
	private Long studentTestId;
	
	private List<StudentsResponsesReportDto>
	studentsResponsesReportDtos = new ArrayList<StudentsResponsesReportDto>();

	private String studentsTestStatus;

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the studentsTests
	 */
	public StudentsTests getStudentsTests() {
		return studentsTests;
	}

	/**
	 * @param studentsTests the studentsTests to set
	 */
	public void setStudentsTests(StudentsTests studentsTests) {
		this.studentsTests = studentsTests;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the studentTestId
	 */
	public Long getStudentTestId() {
		return studentTestId;
	}

	/**
	 * @param studentTestId the studentTestId to set
	 */
	public void setStudentTestId(Long studentTestId) {
		this.studentTestId = studentTestId;
	}

	@Override
	public Long getId() {
		return getStudentTestId();
	}

	@Override
	public Long getId(int order) {
		return getStudentId();
	}

	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}
	
	/**
	 * @return the studentsResponsesReportDtos
	 */
	public List<StudentsResponsesReportDto> getStudentsResponsesReportDtos() {
		return studentsResponsesReportDtos;
	}

	/**
	 * @param studentsResponsesReportDtos the studentsResponsesReportDtos to set
	 */
	public void setStudentsResponsesReportDtos(
			List<StudentsResponsesReportDto> studentsResponsesReportDtos) {
		this.studentsResponsesReportDtos = studentsResponsesReportDtos;
	}

	public void setStudentsResponsesDtos(
			List<StudentsResponsesReportDto> studentsResponsesReportDtos) {
		if (studentsResponsesReportDtos != null && CollectionUtils.isNotEmpty(studentsResponsesReportDtos)) {
			//The student has responded.
			for(StudentsResponsesReportDto studentsResponsesReportDto:studentsResponsesReportDtos) {
				if(studentsResponsesReportDto != null
						&& studentsResponsesReportDto.getStudentsResponses() != null
						&& studentsResponsesReportDto.getStudentsResponses().getStudentsTestsId() != null
						&& this.getStudentTestId() != null
						&& this.getStudentTestId().equals(
								studentsResponsesReportDto.getStudentsResponses().getStudentsTestsId())) {
					//The responded student test is same as the current student test.
					this.studentsResponsesReportDtos.add(studentsResponsesReportDto);
				}
				//TODO optimize here..No need to iterate over the whole list.
			}
			studentsResponsesReportDtos.removeAll(this.studentsResponsesReportDtos);
		}
		
	}

	/**
	 * @param studentTestStatusMap
	 */
	public void setStudentTestStatus(Map<Long, Category> studentTestStatusMap,String notEnrolledStatus) {
		if(studentTestStatusMap != null && studentsTests != null
				&& studentsTests.getStatus() != null) {
			//The student is enrolled to the test.
			studentsTests.setStudentTestStatus(studentTestStatusMap.get(studentsTests.getStatus()));
			if(studentsTests.getStudentTestStatus() != null) {
				studentsTestStatus = studentsTests.getStudentTestStatus().getCategoryName();				
			}
		}
		if(studentsTestStatus == null) {
			studentsTestStatus = notEnrolledStatus;			
		}
	}
	
	/**
	 * @return
	 */
	public String getStudentsTestStatus() {
		return studentsTestStatus;
	}

	/**
	 * @param studentsTestStatus the studentsTestStatus to set
	 */
	public void setStudentsTestStatus(String studentsTestStatus) {
		this.studentsTestStatus = studentsTestStatus;
	}


}
