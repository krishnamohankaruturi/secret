/**
 * 
 */
package edu.ku.cete.batch.kelpa.auto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;

/**
 * @author ktaduru_sta
 *
 */
public class KELPAContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6815310594890987517L;
	private Enrollment enrollment;
	private Map<TestCollection, List<Test>> testCollectionTests;
	private List<TestSession> testSessions = new ArrayList<TestSession>();
	private Set<String> accessibilityFlags = null;
	
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

	public Set<String> getAccessibilityFlags() {
		return accessibilityFlags;
	}

	public void setAccessibilityFlags(Set<String> accessibilityFlags) {
		this.accessibilityFlags = accessibilityFlags;
	}
}
