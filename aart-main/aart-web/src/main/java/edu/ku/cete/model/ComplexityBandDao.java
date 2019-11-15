package edu.ku.cete.model;

public class ComplexityBandDao {
	private long id;
	private String bandName;
	private String bandCode;
	private double minRange;
	private double maxRange;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBandName() {
		return bandName;
	}
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	public String getBandCode() {
		return bandCode;
	}
	public void setBandCode(String bandCode) {
		this.bandCode = bandCode;
	}
	public double getMinRange() {
		return minRange;
	}
	public void setMinRange(double minRange) {
		this.minRange = minRange;
	}
	public double getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}
		
}
