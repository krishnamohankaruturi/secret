package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.TestForm;
import edu.ku.cete.web.TestFormAssignmentsInfoDTO;

/**
 *
 * @author neil.howerton
 *
 */
public interface TestFormDao {

    /**
     *
     *@param testId long
     *@return List<TestForm>
     */
    List<TestForm> findByTest(@Param("testId")long testId);

    /**
     *
     *@return List<TestForm>
     */
    List<TestForm> getAll();
    
    /*
     * Added during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
     */    
	List<TestFormAssignmentsInfoDTO> getTestFormAssignmentsExtracts(@Param("assessmentPrograms")
			List<Long> assessmentPrograms,@Param("qcCompleteStatus") String qcCompleteStatus,@Param("beginDate") String beginDate,
			@Param("toDate") String toDate);

}
