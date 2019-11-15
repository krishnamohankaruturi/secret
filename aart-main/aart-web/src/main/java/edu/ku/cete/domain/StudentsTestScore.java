package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class StudentsTestScore extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long studentsTestId;
	private Long taskvariantId;
	private Long scorerId;
	private Integer score;
	private Long nonScoreReason;
	private Long rubricCategoryId;
	private String source;
	private Long testId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudensTsestId() {
		return studentsTestId;
	}

	public void setStudensTsestId(Long studensTsestId) {
		this.studentsTestId = studensTsestId;
	}

	public Long getTaskvariantid() {
		return taskvariantId;
	}

	public void setTaskvariantid(Long taskvariantid) {
		this.taskvariantId = taskvariantid;
	}

	public Long getScorerid() {
		return scorerId;
	}

	public void setScorerid(Long scorerid) {
		this.scorerId = scorerid;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getNonscorereason() {
		return nonScoreReason;
	}

	public void setNonscorereason(Long nonscorereason) {
		this.nonScoreReason = nonscorereason;
	}

	public Long getRubriccategoryid() {
		return rubricCategoryId;
	}

	public void setRubriccategoryid(Long rubriccategoryid) {
		this.rubricCategoryId = rubriccategoryid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

}
