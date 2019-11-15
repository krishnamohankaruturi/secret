package edu.ku.cete.domain.interim;

public class InterimPredictiveStudentScoreRange {
	private String testingCycleName;
	private String low;
	private String high;
	public String getTestingCycleName() {
		return testingCycleName;
	}
	public void setTestingCycleName(String testingCycleName) {
		this.testingCycleName = testingCycleName;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}

}
