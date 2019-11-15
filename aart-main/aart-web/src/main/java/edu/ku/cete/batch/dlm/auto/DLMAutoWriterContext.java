package edu.ku.cete.batch.dlm.auto;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;

public class DLMAutoWriterContext implements Serializable {

	private static final long serialVersionUID = 2661984652843195737L;

	private Long rosterId;
	private StudentTracker studentTracker;
	private TestCollection testCollection;
	private ComplexityBand complexityBand;
	private List<Test> tests;
	private TestSession testSession;
	private Integer currentTestNumber;
	private Integer numberOfTestsRequired;

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public StudentTracker getStudentTracker() {
		return studentTracker;
	}

	public void setStudentTracker(StudentTracker studentTracker) {
		this.studentTracker = studentTracker;
	}

	public TestCollection getTestCollection() {
		return testCollection;
	}

	public void setTestCollection(TestCollection testCollection) {
		this.testCollection = testCollection;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public ComplexityBand getComplexityBand() {
		return complexityBand;
	}

	public void setComplexityBand(ComplexityBand complexityBand) {
		this.complexityBand = complexityBand;
	}

	public TestSession getTestSession() {
		return testSession;
	}

	public void setTestSession(TestSession testSession) {
		this.testSession = testSession;
	}

	public Integer getCurrentTestNumber() {
		return currentTestNumber;
	}

	public void setCurrentTestNumber(Integer currentTestNumber) {
		this.currentTestNumber = currentTestNumber;
	}

	public Integer getNumberOfTestsRequired() {
		return numberOfTestsRequired;
	}

	public void setNumberOfTestsRequired(Integer numberOfTestsRequired) {
		this.numberOfTestsRequired = numberOfTestsRequired;
	}
}
