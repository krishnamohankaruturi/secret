package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.property.ValidateableRecord;

/**
 * Added By Sudhansu Feature: f183 Projected Testing
 */
public class ProjectedTesting extends ValidateableRecord implements Serializable {

	private static final long serialVersionUID = 3766549228200862055L;
	private String assessmentProgram;
	private String state;
	private String districtID;
	private String schoolID;
	private String districtName;
	private String schoolName;
	private String month;

	private Long assessmentProgramId;
	private Long stateId;
	private Long districtId;
	private Long schoolId;

	private String one;
	private String two;
	private String three;
	private String four;
	private String five;
	private String six;
	private String seven;
	private String eight;
	private String nine;
	private String ten;

	private String eleven;
	private String twelve;
	private String thirteen;
	private String fourteen;
	private String fifteen;
	private String sixteen;
	private String seventeen;
	private String eighteen;
	private String nineteen;
	private String twenty;

	private String twentyOne;
	private String twentyTwo;
	private String twentyThree;
	private String twentyFour;
	private String twentyFive;
	private String twentySix;
	private String twentySeven;
	private String twentyEight;
	private String twentyNine;

	private String thirty;
	private String thirtyOne;

	private String lineNumber;

	private Long schoolYear;

	private Date schoolStartDate;

	private Date schoolEndDate;

	private Long currentSchoolYear;

	private List<Integer> previousDate = new ArrayList<Integer>();
	private List<Date> actualDates = new ArrayList<Date>();
	private List<Integer> weekends = new ArrayList<Integer>();
	private List<Integer> invalidDate = new ArrayList<Integer>();

	private Long totalCount;

	/**
	 * Feature: F605 Projected Scoring
	 */
	private String projectionType;
	private String grade;
	private String gradeName;
	private Long gradeId;
	private Date modifiedDate;
	private String modifiedBy;

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(String projectionType) {
		this.projectionType = projectionType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	public List<Integer> getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(List<Integer> previousDate) {
		this.previousDate = previousDate;
	}

	public Date getSchoolStartDate() {
		return schoolStartDate;
	}

	public void setSchoolStartDate(Date schoolStartDate) {
		this.schoolStartDate = schoolStartDate;
	}

	public Date getSchoolEndDate() {
		return schoolEndDate;
	}

	public void setSchoolEndDate(Date schoolEndDate) {
		this.schoolEndDate = schoolEndDate;
	}

	public List<Integer> getWeekends() {
		return weekends;
	}

	public void setWeekends(List<Integer> weekends) {
		this.weekends = weekends;
	}

	public List<Integer> getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(List<Integer> invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public List<Date> getActualDates() {
		return actualDates;
	}

	public void setActualDates(List<Date> actualDates) {
		this.actualDates = actualDates;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getNine() {
		return nine;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getEleven() {
		return eleven;
	}

	public void setEleven(String eleven) {
		this.eleven = eleven;
	}

	public String getTwelve() {
		return twelve;
	}

	public void setTwelve(String twelve) {
		this.twelve = twelve;
	}

	public String getThirteen() {
		return thirteen;
	}

	public void setThirteen(String thirteen) {
		this.thirteen = thirteen;
	}

	public String getFourteen() {
		return fourteen;
	}

	public void setFourteen(String fourteen) {
		this.fourteen = fourteen;
	}

	public String getFifteen() {
		return fifteen;
	}

	public void setFifteen(String fifteen) {
		this.fifteen = fifteen;
	}

	public String getSixteen() {
		return sixteen;
	}

	public void setSixteen(String sixteen) {
		this.sixteen = sixteen;
	}

	public String getSeventeen() {
		return seventeen;
	}

	public void setSeventeen(String seventeen) {
		this.seventeen = seventeen;
	}

	public String getEighteen() {
		return eighteen;
	}

	public void setEighteen(String eighteen) {
		this.eighteen = eighteen;
	}

	public String getNineteen() {
		return nineteen;
	}

	public void setNineteen(String nineteen) {
		this.nineteen = nineteen;
	}

	public String getTwenty() {
		return twenty;
	}

	public void setTwenty(String twenty) {
		this.twenty = twenty;
	}

	public String getTwentyOne() {
		return twentyOne;
	}

	public void setTwentyOne(String twentyOne) {
		this.twentyOne = twentyOne;
	}

	public String getTwentyTwo() {
		return twentyTwo;
	}

	public void setTwentyTwo(String twentyTwo) {
		this.twentyTwo = twentyTwo;
	}

	public String getTwentyThree() {
		return twentyThree;
	}

	public void setTwentyThree(String twentyThree) {
		this.twentyThree = twentyThree;
	}

	public String getTwentyFour() {
		return twentyFour;
	}

	public void setTwentyFour(String twentyFour) {
		this.twentyFour = twentyFour;
	}

	public String getTwentyFive() {
		return twentyFive;
	}

	public void setTwentyFive(String twentyFive) {
		this.twentyFive = twentyFive;
	}

	public String getTwentySix() {
		return twentySix;
	}

	public void setTwentySix(String twentySix) {
		this.twentySix = twentySix;
	}

	public String getTwentySeven() {
		return twentySeven;
	}

	public void setTwentySeven(String twentySeven) {
		this.twentySeven = twentySeven;
	}

	public String getTwentyEight() {
		return twentyEight;
	}

	public void setTwentyEight(String twentyEight) {
		this.twentyEight = twentyEight;
	}

	public String getTwentyNine() {
		return twentyNine;
	}

	public void setTwentyNine(String twentyNine) {
		this.twentyNine = twentyNine;
	}

	public String getThirty() {
		return thirty;
	}

	public void setThirty(String thirty) {
		this.thirty = thirty;
	}

	public String getThirtyOne() {
		return thirtyOne;
	}

	public void setThirtyOne(String thirtyOne) {
		this.thirtyOne = thirtyOne;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
