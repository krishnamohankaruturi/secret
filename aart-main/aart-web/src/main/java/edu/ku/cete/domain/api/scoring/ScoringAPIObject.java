package edu.ku.cete.domain.api.scoring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoringAPIObject {
	private Long studentId;
	
	private Long stage1StudentsTestsId;
	private Long stage1TestId;
	private Date stage1StartTime;
	private Date stage1EndTime;
	
	private Long stage2StudentsTestsId;
	private Long stage2TestId;
	private Date stage2StartTime;
	private Date stage2EndTime;
	
	private Integer schoolYear;
	
	private Long enrollmentId;
	private Long classroomId;
	private Long teacherId;
	private Long schoolId;
	
	private Long contentAreaId;
	private String contentAreaAbbreviatedName;
	
	private BigDecimal totalRawScore;
	private BigDecimal totalScaleScore;
	
	private Long reprocessId;
	
	private List<ClusterScoreObject> clusters;
	
	@JsonIgnore
	public Long getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@JsonProperty("stage1InstanceId")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Long getStage1StudentsTestsId() {
		return stage1StudentsTestsId;
	}
	
	public void setStage1StudentsTestsId(Long stage1StudentsTestsId) {
		this.stage1StudentsTestsId = stage1StudentsTestsId;
	}
	
	@JsonProperty("stage1FormId")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Long getStage1TestId() {
		return stage1TestId;
	}
	
	public void setStage1TestId(Long stage1TestId) {
		this.stage1TestId = stage1TestId;
	}
	
	@JsonProperty("stage1Start")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	public Date getStage1StartTime() {
		return stage1StartTime;
	}
	
	public void setStage1StartTime(Date stage1StartTime) {
		this.stage1StartTime = stage1StartTime;
	}
	
	@JsonProperty("stage1End")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	public Date getStage1EndTime() {
		return stage1EndTime;
	}
	
	public void setStage1EndTime(Date stage1EndTime) {
		this.stage1EndTime = stage1EndTime;
	}
	
	@JsonProperty("stage2InstanceId")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Long getStage2StudentsTestsId() {
		return stage2StudentsTestsId;
	}
	
	public void setStage2StudentsTestsId(Long stage2StudentsTestsId) {
		this.stage2StudentsTestsId = stage2StudentsTestsId;
	}
	
	@JsonProperty("stage2FormId")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Long getStage2TestId() {
		return stage2TestId;
	}
	
	public void setStage2TestId(Long stage2TestId) {
		this.stage2TestId = stage2TestId;
	}
	
	@JsonProperty("stage2Start")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	public Date getStage2StartTime() {
		return stage2StartTime;
	}
	
	public void setStage2StartTime(Date stage2StartTime) {
		this.stage2StartTime = stage2StartTime;
	}
	
	@JsonProperty("stage2End")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	public Date getStage2EndTime() {
		return stage2EndTime;
	}
	
	public void setStage2EndTime(Date stage2EndTime) {
		this.stage2EndTime = stage2EndTime;
	}
	
	@JsonProperty("schoolYear")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Integer getSchoolYear() {
		return schoolYear;
	}
	
	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	@JsonProperty("enrollmentId")
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	
	@JsonProperty("classroomId")
	public Long getClassroomId() {
		return classroomId;
	}
	
	public void setClassroomId(Long classroomId) {
		this.classroomId = classroomId;
	}
	
	@JsonProperty("teacherId")
	public Long getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	@JsonProperty("siteId")
	public Long getSchoolId() {
		return schoolId;
	}
	
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
	@JsonIgnore
	public Long getContentAreaId() {
		return contentAreaId;
	}
	
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	
	@JsonProperty("courseCode")
	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}
	
	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}
	
	@JsonProperty("rawScore")
	public BigDecimal getTotalRawScore() {
		return totalRawScore;
	}
	
	public void setTotalRawScore(BigDecimal totalRawScore) {
		this.totalRawScore = totalRawScore;
	}
	
	@JsonProperty("scaledScore")
	public BigDecimal getTotalScaleScore() {
		return totalScaleScore;
	}
	
	public void setTotalScaleScore(BigDecimal totalScaleScore) {
		this.totalScaleScore = totalScaleScore;
	}
	
	@JsonIgnore
	public Long getReprocessId() {
		return reprocessId;
	}

	public void setReprocessId(Long reprocessId) {
		this.reprocessId = reprocessId;
	}

	@JsonProperty("clusters")
	public List<ClusterScoreObject> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterScoreObject> clusters) {
		this.clusters = clusters;
	}
	
	public void addCluster(ClusterScoreObject cluster) {
		if (this.clusters == null) {
			this.clusters = new ArrayList<ClusterScoreObject>();
		}
		this.clusters.add(cluster);
	}
	
}
