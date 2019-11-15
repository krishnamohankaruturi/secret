package edu.ku.cete.domain;

/**
 * @author m802r921
 * This object has the student responses and also indicates which response is correct.
 */
public class RubricReportDto {
	private Long rubricCategoryId;
	private Long rubricInfoId;
	private String rubricInfoDesc;
	private Integer rubricInfoScore;
	private Long rubricInfoCategoryId;
	private String categoryName;
	private Boolean selected=false;

	public Long getRubricCategoryId() {
		return rubricCategoryId;
	}
	public void setRubricCategoryId(Long rubricCategoryId) {
		this.rubricCategoryId = rubricCategoryId;
	}
	public Long getRubricInfoId() {
		return rubricInfoId;
	}
	public void setRubricInfoId(Long rubricInfoId) {
		this.rubricInfoId = rubricInfoId;
	}
	public String getRubricInfoDesc() {
		return rubricInfoDesc;
	}
	public void setRubricInfoDesc(String rubricInfoDesc) {
		this.rubricInfoDesc = rubricInfoDesc;
	}
	public Integer getRubricInfoScore() {
		return rubricInfoScore;
	}
	public void setRubricInfoScore(Integer rubricInfoScore) {
		this.rubricInfoScore = rubricInfoScore;
	}
	public Long getRubricInfoCategoryId() {
		return rubricInfoCategoryId;
	}
	public void setRubricInfoCategoryId(Long rubricInfoCategoryId) {
		this.rubricInfoCategoryId = rubricInfoCategoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
}
