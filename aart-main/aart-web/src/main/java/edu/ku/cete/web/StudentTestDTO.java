package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Venkata Krishna Jagarlamudi
 * US15630: Data extract - Test administration for KAP and AMP
 * US15741: Data Extract - Test Tickets for KAP and AMP
 * Common for both extracts.
 *
 */
public class StudentTestDTO {
	
	private Long stuTestId;
	private List<StudentTestSectionsDTO> studentTestSectionDtos = new ArrayList<StudentTestSectionsDTO>();
		
	/**
	 * @return the studentTestSectionDtos
	 */
	public List<StudentTestSectionsDTO> getStudentTestSectionDtos() {
		return studentTestSectionDtos;
	}
	/**
	 * @param studentTestSectionDtos the studentTestSectionDtos to set
	 */
	public void setStudentTestSectionDtos(
			List<StudentTestSectionsDTO> studentTestSectionDtos) {
		this.studentTestSectionDtos = studentTestSectionDtos;
	}
	/**
	 * @return the stuTestId
	 */
	public Long getStuTestId() {
		return stuTestId;
	}
	/**
	 * @param stuTestId the stuTestId to set
	 */
	public void setStuTestId(Long stuTestId) {
		this.stuTestId = stuTestId;
	}
}
