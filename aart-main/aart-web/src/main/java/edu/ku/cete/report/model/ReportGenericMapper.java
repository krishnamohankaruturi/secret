package edu.ku.cete.report.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.ksde.kids.result.KSDERecord;

public interface ReportGenericMapper {
	
	int countKSDETestAndTascStudentStateID(
			@Param("studentStateID") String studentStateID,
			@Param("currentSchoolYear") String currentSchoolYear);

	List<KSDERecord> getKidsAndTascRecordsForStudent(
			@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("currentSchoolYear") String currentSchoolYear);
}
