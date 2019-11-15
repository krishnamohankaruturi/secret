package edu.ku.cete.batch.dlm.auto.multiassign;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;

public class DLMMultiAssignAutoWriterContext implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long rosterId;
	private Enrollment enrollment;
	private Map<TestCollection, List<Test>> testCollectionTests;
	private List<TestSession> testSessions = new ArrayList<TestSession>();
	private ComplexityBand complexityBand;
	private GradeCourse gradeCourse;
	private Integer numberOfTestsRequired;
	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	
	public Enrollment getEnrollment() {
		return enrollment;
	}
	
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
	public Map<TestCollection, List<Test>> getTestCollectionTests() {
		return testCollectionTests;
	}
	
	public void setTestCollectionTests(
			Map<TestCollection, List<Test>> testCollectionTests) {
		this.testCollectionTests = testCollectionTests;
	}

	public List<TestSession> getTestSessions() {
		return testSessions;
	}

	public void setTestSessions(List<TestSession> testSessions) {
		this.testSessions = testSessions;
	}
	
	public ComplexityBand getComplexityBand() {
		return complexityBand;
	}

	public void setComplexityBand(ComplexityBand complexityBand) {
		this.complexityBand = complexityBand;
	}
	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	public Integer getNumberOfTestsRequired() {
		return numberOfTestsRequired;
	}

	public void setNumberOfTestsRequired(Integer numberOfTestsRequired) {
		this.numberOfTestsRequired = numberOfTestsRequired;
	}
}
