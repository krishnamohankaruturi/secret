package edu.ku.cete.domain;

import edu.ku.cete.domain.content.TaskVariantsFoils;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;

/**
 * @author m802r921
 * This object has the student responses and also indicates which response is correct.
 */
public class StudentsResponsesReportDto {
	private Long id;
	private String taskLayoutFormat;
	private String reportTaskLayoutFormat;
	private StudentsResponses studentsResponses;
	private TaskVariantsFoils taskVariantsFoils;
	private TestSectionsTaskVariants testSectionsTaskVariants;
	private String testSectionName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public StudentsResponses getStudentsResponses() {
		return studentsResponses;
	}

	public void setStudentsResponses(StudentsResponses studentsResponses) {
		this.studentsResponses = studentsResponses;
	}

	/**
	 * @return the taskVariantsFoils
	 */
	public TaskVariantsFoils getTaskVariantsFoils() {
		return taskVariantsFoils;
	}

	/**
	 * @param taskVariantsFoils the taskVariantsFoils to set
	 */
	public void setTaskVariantsFoils(TaskVariantsFoils taskVariantsFoils) {
		this.taskVariantsFoils = taskVariantsFoils;
	}

	/**
	 * @return the testSectionsTaskVariants
	 */
	public TestSectionsTaskVariants getTestSectionsTaskVariants() {
		return testSectionsTaskVariants;
	}

	/**
	 * @param testSectionsTaskVariants the testSectionsTaskVariants to set
	 */
	public void setTestSectionsTaskVariants(TestSectionsTaskVariants testSectionsTaskVariants) {
		this.testSectionsTaskVariants = testSectionsTaskVariants;
	}

	public String getTaskLayoutFormat() {
		return taskLayoutFormat;
	}

	public void setTaskLayoutFormat(String taskLayoutFormat) {
		this.taskLayoutFormat = taskLayoutFormat;
	}

	public String getReportTaskLayoutFormat() {
		return reportTaskLayoutFormat;
	}

	public void setReportTaskLayoutFormat(String reportTaskLayoutFormat) {
		this.reportTaskLayoutFormat = reportTaskLayoutFormat;
	}

	public String getTestSectionName() {
		return testSectionName;
	}

	public void setTestSectionName(String testSectionName) {
		this.testSectionName = testSectionName;
	}
}
