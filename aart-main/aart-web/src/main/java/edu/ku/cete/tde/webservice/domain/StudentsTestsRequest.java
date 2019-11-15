package edu.ku.cete.tde.webservice.domain;

import java.util.List;


public class StudentsTestsRequest {

    private String testName;
    
    private List<Long> testingProgramIds;
    
    private TDEStudentsTests studentsTests;
    
    public StudentsTestsRequest(TDEStudentsTests studentsTests, String testName, List<Long> testingProgramIds) {
        this.studentsTests = studentsTests;
        this.testName = testName;
        this.testingProgramIds = testingProgramIds;
    }
    
    public Long getId(){
        return this.studentsTests.getId();
    }
    
    public Long getStudentId(){
        return this.studentsTests.getStudentId();
    }
    
    public Long getTestId(){
        return this.studentsTests.getTestId();
    }
    
    public Long getTestCollectionId(){
        return this.studentsTests.getTestCollectionId();
    }
    
    public Long getTestingStatusId(){
        return this.studentsTests.getStatus();
    }
    
    public Long getTestSessionId(){
        return this.studentsTests.getTestSessionId();
    }
    
    public String getTestName(){
        return this.testName;
    }
    
    public List<Long> getTestTypeIds(){
        return this.testingProgramIds;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentsTestsRequest [");
		if (testName != null) {
			builder.append("testName=");
			builder.append(testName);
			builder.append(", ");
		}
		if (testingProgramIds != null) {
			builder.append("testingProgramIds=");
			builder.append(testingProgramIds);
			builder.append(", ");
		}
		if (studentsTests != null) {
			builder.append("studentsTests=");
			builder.append(studentsTests);
		}
		builder.append("]");
		return builder.toString();
	}
    
}
