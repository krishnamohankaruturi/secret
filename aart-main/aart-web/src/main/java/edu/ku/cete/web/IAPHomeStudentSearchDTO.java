package edu.ku.cete.web;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.student.Student;

public class IAPHomeStudentSearchDTO implements Comparable<IAPHomeStudentSearchDTO>{
	
	private Student student;
	private Boolean hasELA;
	private Boolean hasMath;
	private Boolean hasScience;
	private String grade;
	private String teacherName;
	private String studentName;
	private String studentID;
	private String studentStateIdentifier;
	private String studentUserName;
	private String studentPassword;
	private Boolean isIEModel;
	private String recordKey;
	private String gradeAbbreviatedName;
	
	Map<String,IAPStudentTestStatusDTO> subjectRecord;
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Boolean getHasELA() {
		return hasELA;
	}
	public void setHasELA(Boolean hasELA) {
		this.hasELA = hasELA;
	}
	public Boolean getHasMath() {
		return hasMath;
	}
	public void setHasMath(Boolean hasMath) {
		this.hasMath = hasMath;
	}
	public Boolean getHasScience() {
		return hasScience;
	}
	public void setHasScience(Boolean hasScience) {
		this.hasScience = hasScience;
	}
	public Map<String, IAPStudentTestStatusDTO> getSubjectRecord() {
		return subjectRecord;
	}
	public void setSubjectRecord(Map<String, IAPStudentTestStatusDTO> subjectRecord) {
		this.subjectRecord = subjectRecord;
	}
	
	public void addSubjectRecordFor(IAPStudentTestStatusDTO record) {
		if(this.subjectRecord==null) {
			this.subjectRecord = new TreeMap();;
		}
		if(!this.subjectRecord.containsKey(record.getSubjectAbbreviatedName())) {
			this.subjectRecord.put(record.getSubjectAbbreviatedName(),record);
		}
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getStudentStateIdentifier() {
		return studentStateIdentifier;
	}
	public void setStudentStateIdentifier(String studentStateIdentifier) {
		this.studentStateIdentifier = studentStateIdentifier;
	}
	public String getStudentUserName() {
		return studentUserName;
	}
	public void setStudentUserName(String studentUserName) {
		this.studentUserName = studentUserName;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	public Boolean getIsIEModel() {
		return isIEModel;
	}
	public void setIsIEModel(Boolean isIEModel) {
		this.isIEModel = isIEModel;
	}
	public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	public String getGradeAbbreviatedName() {
		return gradeAbbreviatedName;
	}
	public void setGradeAbbreviatedName(String gradeAbbreviatedName) {
		this.gradeAbbreviatedName = gradeAbbreviatedName;
	}
	@Override
	public int compareTo(IAPHomeStudentSearchDTO otherRecord) {
		
		int teacherCompare = 0;
		int gradeCompare =0;
		
		//compare Teacher
		if(!StringUtils.isEmpty(this.teacherName) && !StringUtils.isEmpty(otherRecord.teacherName))
		{
			teacherCompare = this.teacherName.compareTo(otherRecord.teacherName);
			if(teacherCompare==0) {
				//teacher name is same
				if(this.gradeAbbreviatedName !=null) {
					if(StringUtils.isNumeric(this.gradeAbbreviatedName) && StringUtils.isNumeric(otherRecord.gradeAbbreviatedName)) {
						gradeCompare = Integer.valueOf(this.gradeAbbreviatedName).compareTo(Integer.valueOf(otherRecord.gradeAbbreviatedName)); 
					}
				}
				if(gradeCompare==0) {
					//grade is equal
					if(!StringUtils.isEmpty(this.studentName) && !StringUtils.isEmpty(otherRecord.studentName)) {
						//return after comparing student name
						return this.studentName.compareTo(otherRecord.studentName);
					}
				}else {
					return gradeCompare;
				}
				
			}else {
				return teacherCompare;
			}
		}
		//By default the function will return 0 which means both the record is equal
		return 0;
	}
	
}
