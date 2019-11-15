package edu.ku.cete.util;

public enum CompBandSubject {
	FINAL_ELA("final_ela"), FINAL_MATH("final_math"), FINAL_SCI("final_sci"), COMM_BAND("comm_band"),
	WRITING_BAND("writing_band");

	private String name;

	CompBandSubject(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}