
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.common.AppConfiguration;


public interface AppConfigurationService {
	
    AppConfiguration selectByPrimaryKey(Long id);
 
    int deleteByPrimaryKey(Long id);

	List<AppConfiguration> selectByAttributeType(String attributeType);

	Map<String, AppConfiguration> selectIdMapByAttributeType(String attributeType);
	
	List<AppConfiguration> selectSecurityAgreementText(String currentEnvironment);

	List<AppConfiguration> selectByAttributeTypeAndAttributeValue(String attributeType, String attributeValue);

	List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramId(String attributeType, Long assessmentProgramId);

	List<String> getByAttributeType(String attributeType);
	
	String getByAttributeCode(String attributeCode);
	
	List<AppConfiguration> selectByAttributeTypeAndDefaultAssessment(String Type);

	List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramIdForHispanic(String attributeType,Long assessmentProgramId);

	List<String> getValueByAttributeType(String attributeType);

}
