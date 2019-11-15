package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentsTestScore;

public interface StudentsTestScoreMapper {
	
	Integer getScoreByStudentsTestAndTaskvariantId(@Param("studentsTestId") Long studentsTestId,@Param("taskvariantId") Long taskVariantId,@Param("rubricCategoryId") Long rubricCategoryId);
	
	void insert(StudentsTestScore studentsTestScore);
	
	void update(StudentsTestScore studentsTestScore);
	
	void insertToHistory(StudentsTestScore studentsTestScore);

	StudentsTestScore findByTaskvariantIdAndRubriType(@Param("taskvariantId") Long taskvariantId,@Param("rubricId") Long rubricId,@Param("studentsTestsId") Long studentsTestsId);

	void removeScore(@Param("studentsTestId") Long studentsTestId,@Param("userId") Long modifiedUserId);

	void moveScoreOnChangeRoster(@Param("studentsTestId") Long studentsTestId, @Param("oldTeacherId")  Long oldTeacherId, @Param("newTeacherId") Long newTeacherId,
			                    @Param("modifiedUser") Long modifiedUser);

	void removeStudentsTestScore(@Param("id") Long id);
	
}
