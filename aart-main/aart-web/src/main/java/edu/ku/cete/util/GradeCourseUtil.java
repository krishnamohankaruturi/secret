package edu.ku.cete.util;

import java.util.Date;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.model.GradeCourseDao;

public class GradeCourseUtil {

	
	//TODO find another solution to this...this is hacky
    public static void populateDefaultGradeCourseValues(GradeCourse gradeCourse, GradeCourseDao gradeCourseDao) {
    	Long assessmentProgramGradesId = gradeCourseDao.findAssessmentProgramGradesIDByCode(gradeCourse.getAbbreviatedName());
    	Date today = new Date();
		gradeCourse.setActiveFlag(true);
		gradeCourse.setCreateDate(today);
		gradeCourse.setModifiedDate(today);
		gradeCourse.setAssessmentProgramGradesId(assessmentProgramGradesId);
		if (gradeCourse.getAbbreviatedName().equals("00")){
			gradeCourse.setGradeLevel(-5);
			gradeCourse.setName("Birth - 2 years old");
			gradeCourse.setOrdinality(0L);
		}else if (gradeCourse.getAbbreviatedName().equals("01")){
			gradeCourse.setGradeLevel(-4);
			gradeCourse.setName("3-Yr-Old Preschooler");
			gradeCourse.setOrdinality(1L);
		}else if (gradeCourse.getAbbreviatedName().equals("02")){
			gradeCourse.setGradeLevel(-3);
			gradeCourse.setName("4-Yr-Old Preschooler ");
			gradeCourse.setOrdinality(2L);
		}else if (gradeCourse.getAbbreviatedName().equals("03")){
			gradeCourse.setGradeLevel(-2);
			gradeCourse.setName("5-Yr-Old and Older Preschooler");
			gradeCourse.setOrdinality(3L);
		}else if (gradeCourse.getAbbreviatedName().equals("04")){
			gradeCourse.setGradeLevel(-1);
			gradeCourse.setName("Four-Year-Old At-Risk");
			gradeCourse.setOrdinality(4L);
		}else if (gradeCourse.getAbbreviatedName().equals("05")){
			gradeCourse.setGradeLevel(0);
			gradeCourse.setName("Kindergarten");
			gradeCourse.setOrdinality(5L);
		}else if (gradeCourse.getAbbreviatedName().equals("06")){
			gradeCourse.setGradeLevel(1);
			gradeCourse.setName("First Grade");
			gradeCourse.setOrdinality(6L);
		}else if (gradeCourse.getAbbreviatedName().equals("07")){
			gradeCourse.setGradeLevel(2);
			gradeCourse.setName("Second Grade");
			gradeCourse.setOrdinality(7L);
		}else if (gradeCourse.getAbbreviatedName().equals("08")){
			gradeCourse.setGradeLevel(3);
			gradeCourse.setName("Third Grade");
			gradeCourse.setOrdinality(8L);
		}else if (gradeCourse.getAbbreviatedName().equals("09")){
			gradeCourse.setGradeLevel(4);
			gradeCourse.setName("Fourth Grade");
			gradeCourse.setOrdinality(9L);
			gradeCourse.setAssessmentProgramGradesId(10L);
		}else if (gradeCourse.getAbbreviatedName().equals("10")){
			gradeCourse.setGradeLevel(5);
			gradeCourse.setName("Fifth Grade");
			gradeCourse.setOrdinality(10L);
		}else if (gradeCourse.getAbbreviatedName().equals("11")){
			gradeCourse.setGradeLevel(6);
			gradeCourse.setName("Sixth Grade");
			gradeCourse.setOrdinality(11L);
		}else  if (gradeCourse.getAbbreviatedName().equals("12")){
			gradeCourse.setGradeLevel(7);
			gradeCourse.setName("Seventh Grade");
			gradeCourse.setOrdinality(12L);
		}else if (gradeCourse.getAbbreviatedName().equals("13")){
			gradeCourse.setGradeLevel(8);
			gradeCourse.setName("Eighth Grade");
			gradeCourse.setOrdinality(13L);
		}else if (gradeCourse.getAbbreviatedName().equals("14")){
			gradeCourse.setGradeLevel(9);
			gradeCourse.setName("Ninth Grade");
			gradeCourse.setOrdinality(14L);
		}else if (gradeCourse.getAbbreviatedName().equals("15")){
			gradeCourse.setGradeLevel(10);
			gradeCourse.setName("Tenth Grade");
			gradeCourse.setOrdinality(15L);
		}else if (gradeCourse.getAbbreviatedName().equals("16")){
			gradeCourse.setGradeLevel(11);
			gradeCourse.setName("Eleventh Grade");
			gradeCourse.setOrdinality(16L);
		}else if (gradeCourse.getAbbreviatedName().equals("17")){
			gradeCourse.setGradeLevel(12);
			gradeCourse.setName("Twelfth Grade");
			gradeCourse.setOrdinality(17L);
		}else if (gradeCourse.getAbbreviatedName().equals("18")){
			gradeCourse.setGradeLevel(null);
			gradeCourse.setName("Not Graded");
			gradeCourse.setOrdinality(18L);
		}
		
	}
}
