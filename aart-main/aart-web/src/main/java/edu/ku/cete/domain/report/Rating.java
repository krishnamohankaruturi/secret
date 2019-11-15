package edu.ku.cete.domain.report;

import edu.ku.cete.report.ReportStandardError;

public class Rating implements Comparable<Rating> {
	private Long gradeID;
	private Integer rating;
	private Long ratingid;
	
	
	public Long getRatingid() {
		return ratingid;
	}
	public void setRatingid(Long ratingid) {
		this.ratingid = ratingid;
	}
	public Long getGradeID() {
		return gradeID;
	}
	public void setGradeID(Long gradeID) {
		this.gradeID = gradeID;
	}
	

	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	@Override
	public int compareTo(Rating other) {
		// TODO Auto-generated method stub
		return gradeID.compareTo(other.gradeID);
	}
	
}
