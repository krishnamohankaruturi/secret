/**
 * 
 */
package edu.ku.cete.service.report;

import java.util.List;
import edu.ku.cete.domain.CombinedLevelMap;

/**
 * @author ktaduru_sta
 *
 */
public interface CombinedLevelMapService {
	Integer insertCombinedLevelMap(CombinedLevelMap combinedLevelMap);
	Integer deleteCombinedLevelMap(Long assessmentProgramId, Long contentAreaId, Long schoolYear);
	Integer deleteAllCombinedLevelMap(Long schoolYear);	
	
	List<CombinedLevelMap> getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear);
}
