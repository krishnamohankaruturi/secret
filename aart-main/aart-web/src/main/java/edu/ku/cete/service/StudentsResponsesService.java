/**
 * 
 */
package edu.ku.cete.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentResponseScore;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsResponsesExample;
import edu.ku.cete.web.QuestarDTO;

/**
 * @author neil.howerton
 *
 */
public interface StudentsResponsesService {

    /**
     * Returns the number of StudentsResponses that match the example.
     * @param example {@link StudentsResponsesExample}
     * @return int
     */
    int countByExample(StudentsResponsesExample example);

    /**
     * Inserts a new StudentsResponse record.
     * @param record {@link StudentsResponses}
     * @return int
     */
    StudentsResponses insert(StudentsResponses record);

    /**
     * Inserts a new StudentsResponse record with only the data that exists.
     * @param record {@link StudentsResponses}
     * @return int
     */
    StudentsResponses insertSelective(StudentsResponses record);

    /**
     * Finds all StudentsResponses records that match the example.
     * @param example {@link StudentsResponsesExample}
     * @return List<StudentsResponses>
     */
    List<StudentsResponses> selectByExample(StudentsResponsesExample example);

    /**
     * Finds a StudentsResponses record by its primary key.
     * @param id {@link Long}
     * @return {@link StudentsResponses}
     */
    StudentsResponses selectByPrimaryKey(Long id);

    /**
     * Updates all StudentsResponses records that match the example, and sets the corresponding columns only for the fields on
     * the record parameter that are set.
     * @param record {@link StudentsResponses}
     * @param example {@link StudentsResponsesExample}
     * @return int
     */
    int updateByExampleSelective(@Param("record") StudentsResponses record, @Param("example") StudentsResponsesExample example);

    /**
     * Updates all StudentsResponses records that match the example. Updates all columns.
     * @param record {@link StudentsResponses}
     * @param example {@link StudentsResponsesExample}
     * @return int
     */
    int updateByExample(@Param("record") StudentsResponses record, @Param("example") StudentsResponsesExample example);

    /**
     * Updates the StudentsResponses record that matches the primary key. Will only update columns whose values in the record
     * parameter are set.
     * @param record {@link StudentsResponses}
     * @return int
     */
    int updateByPrimaryKeySelective(StudentsResponses record);

    /**
     * Updates the StudentsResponses record that matches the primary key. Will update all columns of that record.
     * @param record {@link StudentsResponses}
     * @return int
     */
    int updateByPrimaryKey(StudentsResponses record);

    /**
     * This method retrieves student responses from TDE after a test session has been closed.
     * @param testSessionId long - the test session to retrieve the student responses for.
     * @return boolean
     */
    boolean retrieveStudentsResponses(long testSessionId);

    List<QuestarDTO> getResponsesForQuestar(Map<String, Object> criteria);
    
    int updateQuestarRequestId(Long questarRequestId, List<StudentsResponses> responses);
    
    StudentsResponses getStudentResponse(@Param("studentId") Long studentId, 
    		@Param("taskVariantId") Long taskVariantId, @Param("testId") Long testId);
    
    StudentsResponses selectQuestarResponse(Long studentId, Long studentsTestsId, Long studentsTestSectionsId, Long taskVariantId);
    
    List<StudentsResponses> findQuestarResponseByStudentTestSectionId(@Param("studentsTestSectionsId") Long studentsTestSectionsId);
    
    @MapKey(value="studentsTestSectionsId")
    HashMap<Long, List<StudentsResponses>> findQuestarResponseMapByStudentTestId(@Param("studentsTestsId") Long studentsTestsId);
    
    int updateScoreForQuestar(Long userId, Long studentsTestSectionsId, Long taskVariantId, BigDecimal score);
    
    /**
	 * Inserts or updates a record in the studentresponsescore table. This method will update the create date
	 * of the parameter as appropriate, if a previous entry with the same key is found.
	 * 
	 * @return The inserted/updated object
	 */
    StudentResponseScore addOrUpdateStudentResponseScore(StudentResponseScore record);

	StudentsResponses insertStudentResponse(StudentsResponses sr);

	List<StudentResponseScore> findStudentResponseScores(Long studentsTestSectionsId, Long taskVariantExternalId, List<Integer> raterOrders);

	int getNoOfNotResponsesMachineScoreItems(Long studentsTestId);
}
