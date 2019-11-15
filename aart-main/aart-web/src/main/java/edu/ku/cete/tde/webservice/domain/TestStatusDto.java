/**
 * 
 */
package edu.ku.cete.tde.webservice.domain;

/**
 * @author neil.howerton
 *
 */
public class TestStatusDto {

    /**
     * the id of the test session to update.
     */
    private long testSessionId;

    /**
     * the code for the status. Keeps tde and aart in sync.
     */
    private String testStatusCode;

    /**
     * @return the testSessionId
     */
    public final long getTestSessionId() {
        return testSessionId;
    }

    /**
     * @param testSessionId the testSessionId to set
     */
    public final void setTestSessionId(long testSessionId) {
        this.testSessionId = testSessionId;
    }

    /**
     * @return the testStatusCode
     */
    public String getTestStatusCode() {
        return testStatusCode;
    }

    /**
     * @param testStatusCode the testStatusCode to set
     */
    public void setTestStatusCode(String testStatusCode) {
        this.testStatusCode = testStatusCode;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestStatusDto [testSessionId=");
		builder.append(testSessionId);
		builder.append(", ");
		if (testStatusCode != null) {
			builder.append("testStatusCode=");
			builder.append(testStatusCode);
		}
		builder.append("]");
		return builder.toString();
	}
}
