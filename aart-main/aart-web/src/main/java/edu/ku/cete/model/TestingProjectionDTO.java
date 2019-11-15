package edu.ku.cete.model;

public class TestingProjectionDTO {
	private String testDate;
	private Double kap = 0d;
	private Double cpass = 0d;
	private Double dlm = 0d;
	private Double kelpa2 = 0d;
	private Double scoring = 0d;
	private Double pltw = 0d;

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public Double getKap() {
		return kap;
	}

	public void setKap(Double kap) {
		this.kap = kap;
	}

	public Double getCpass() {
		return cpass;
	}

	public void setCpass(Double cpass) {
		this.cpass = cpass;
	}

	public Double getDlm() {
		return dlm;
	}

	public void setDlm(Double dlm) {
		this.dlm = dlm;
	}

	public Double getKelpa2() {
		return kelpa2;
	}

	public void setKelpa2(Double kelpa2) {
		this.kelpa2 = kelpa2;
	}

	public Double getScoring() {
		return scoring;
	}

	public void setScoring(Double scoring) {
		this.scoring = scoring;
	}

	public Double getPltw() {
		return pltw;
	}

	public void setPltw(Double pltw) {
		this.pltw = pltw;
	}
}
