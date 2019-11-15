package edu.ku.cete.domain.report;

import java.util.Date;

public class DashboardTestingSummary {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.id
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.assessmentprogram
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String assessmentProgram;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.assessmentprogramid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long assessmentProgramId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.contentarea
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String contentArea;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.contentareaid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long contentAreaId;

    private Long classroomId;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.schoolname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String schoolName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.schoolid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long schoolId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.districtname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String districtName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.districtid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long districtId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.statename
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String stateName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.stateid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long stateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countsessionscompletedtoday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countSessionsCompletedToday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countsessionscompletedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countSessionsCompletedLastSchoolDay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countsessionscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countSessionsCompletedThisYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countstudentsassignedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countStudentsAssignedThisYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countstudentscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countStudentsCompletedThisYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countreactivatedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    //added  Saikat
    private double studentsPercentCompletedThisYear;
    
    private Long countReactivatedLastSchoolDay;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.countreactivatedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Long countReactivatedThisYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.status
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.createddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dashboardtestingsummary.modifieddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    private Date modifiedDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.id
     *
     * @return the value of dashboardtestingsummary.id
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.id
     *
     * @param id the value for dashboardtestingsummary.id
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.assessmentprogram
     *
     * @return the value of dashboardtestingsummary.assessmentprogram
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getAssessmentProgram() {
        return assessmentProgram;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.assessmentprogram
     *
     * @param assessmentProgram the value for dashboardtestingsummary.assessmentprogram
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setAssessmentProgram(String assessmentProgram) {
        this.assessmentProgram = assessmentProgram == null ? null : assessmentProgram.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.assessmentprogramid
     *
     * @return the value of dashboardtestingsummary.assessmentprogramid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getAssessmentProgramId() {
        return assessmentProgramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.assessmentprogramid
     *
     * @param assessmentProgramId the value for dashboardtestingsummary.assessmentprogramid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setAssessmentProgramId(Long assessmentProgramId) {
        this.assessmentProgramId = assessmentProgramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.contentarea
     *
     * @return the value of dashboardtestingsummary.contentarea
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getContentArea() {
        return contentArea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.contentarea
     *
     * @param contentArea the value for dashboardtestingsummary.contentarea
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setContentArea(String contentArea) {
        this.contentArea = contentArea == null ? null : contentArea.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.contentareaid
     *
     * @return the value of dashboardtestingsummary.contentareaid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getContentAreaId() {
        return contentAreaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.contentareaid
     *
     * @param contentAreaId the value for dashboardtestingsummary.contentareaid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setContentAreaId(Long contentAreaId) {
        this.contentAreaId = contentAreaId;
    }

    public Long getClassroomId() {
        return classroomId;
    }
    
    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.schoolname
     *
     * @return the value of dashboardtestingsummary.schoolname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.schoolname
     *
     * @param schoolName the value for dashboardtestingsummary.schoolname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.schoolid
     *
     * @return the value of dashboardtestingsummary.schoolid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.schoolid
     *
     * @param schoolId the value for dashboardtestingsummary.schoolid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.districtname
     *
     * @return the value of dashboardtestingsummary.districtname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.districtname
     *
     * @param districtName the value for dashboardtestingsummary.districtname
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.districtid
     *
     * @return the value of dashboardtestingsummary.districtid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.districtid
     *
     * @param districtId the value for dashboardtestingsummary.districtid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.statename
     *
     * @return the value of dashboardtestingsummary.statename
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.statename
     *
     * @param stateName the value for dashboardtestingsummary.statename
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setStateName(String stateName) {
        this.stateName = stateName == null ? null : stateName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.stateid
     *
     * @return the value of dashboardtestingsummary.stateid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getStateId() {
        return stateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.stateid
     *
     * @param stateId the value for dashboardtestingsummary.stateid
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countsessionscompletedtoday
     *
     * @return the value of dashboardtestingsummary.countsessionscompletedtoday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountSessionsCompletedToday() {
        return countSessionsCompletedToday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countsessionscompletedtoday
     *
     * @param countSessionsCompletedToday the value for dashboardtestingsummary.countsessionscompletedtoday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountSessionsCompletedToday(Long countSessionsCompletedToday) {
        this.countSessionsCompletedToday = countSessionsCompletedToday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countsessionscompletedlastschoolday
     *
     * @return the value of dashboardtestingsummary.countsessionscompletedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountSessionsCompletedLastSchoolDay() {
        return countSessionsCompletedLastSchoolDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countsessionscompletedlastschoolday
     *
     * @param countSessionsCompletedLastSchoolDay the value for dashboardtestingsummary.countsessionscompletedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountSessionsCompletedLastSchoolDay(Long countSessionsCompletedLastSchoolDay) {
        this.countSessionsCompletedLastSchoolDay = countSessionsCompletedLastSchoolDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countsessionscompletedthisyear
     *
     * @return the value of dashboardtestingsummary.countsessionscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountSessionsCompletedThisYear() {
        return countSessionsCompletedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countsessionscompletedthisyear
     *
     * @param countSessionsCompletedThisYear the value for dashboardtestingsummary.countsessionscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountSessionsCompletedThisYear(Long countSessionsCompletedThisYear) {
        this.countSessionsCompletedThisYear = countSessionsCompletedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countstudentsassignedthisyear
     *
     * @return the value of dashboardtestingsummary.countstudentsassignedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountStudentsAssignedThisYear() {
        return countStudentsAssignedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countstudentsassignedthisyear
     *
     * @param countStudentsAssignedThisYear the value for dashboardtestingsummary.countstudentsassignedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountStudentsAssignedThisYear(Long countStudentsAssignedThisYear) {
        this.countStudentsAssignedThisYear = countStudentsAssignedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countstudentscompletedthisyear
     *
     * @return the value of dashboardtestingsummary.countstudentscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountStudentsCompletedThisYear() {
        return countStudentsCompletedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countstudentscompletedthisyear
     *
     * @param countStudentsCompletedThisYear the value for dashboardtestingsummary.countstudentscompletedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountStudentsCompletedThisYear(Long countStudentsCompletedThisYear) {
        this.countStudentsCompletedThisYear = countStudentsCompletedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countreactivatedlastschoolday
     *
     * @return the value of dashboardtestingsummary.countreactivatedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
//added Saikat    
    public Double getStudentsPercentCompletedThisYear() {
        return studentsPercentCompletedThisYear;
    }

    public void setStudentsPercentCompletedThisYear(Double studentsPercentCompletedThisYear) {
        this.studentsPercentCompletedThisYear = studentsPercentCompletedThisYear;
    }
    
    public Long getCountReactivatedLastSchoolDay() {
        return countReactivatedLastSchoolDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countreactivatedlastschoolday
     *
     * @param countReactivatedLastSchoolDay the value for dashboardtestingsummary.countreactivatedlastschoolday
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountReactivatedLastSchoolDay(Long countReactivatedLastSchoolDay) {
        this.countReactivatedLastSchoolDay = countReactivatedLastSchoolDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.countreactivatedthisyear
     *
     * @return the value of dashboardtestingsummary.countreactivatedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Long getCountReactivatedThisYear() {
        return countReactivatedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.countreactivatedthisyear
     *
     * @param countReactivatedThisYear the value for dashboardtestingsummary.countreactivatedthisyear
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCountReactivatedThisYear(Long countReactivatedThisYear) {
        this.countReactivatedThisYear = countReactivatedThisYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.status
     *
     * @return the value of dashboardtestingsummary.status
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.status
     *
     * @param status the value for dashboardtestingsummary.status
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.createddate
     *
     * @return the value of dashboardtestingsummary.createddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.createddate
     *
     * @param createdDate the value for dashboardtestingsummary.createddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dashboardtestingsummary.modifieddate
     *
     * @return the value of dashboardtestingsummary.modifieddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dashboardtestingsummary.modifieddate
     *
     * @param modifiedDate the value for dashboardtestingsummary.modifieddate
     *
     * @mbggenerated Thu Oct 12 09:08:11 CDT 2017
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}