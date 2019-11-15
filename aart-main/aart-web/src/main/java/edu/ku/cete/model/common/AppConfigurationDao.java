package edu.ku.cete.model.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.AppConfiguration;

public interface AppConfigurationDao {
	
    AppConfiguration selectByPrimaryKey(Long id);
       
    List<AppConfiguration> selectByAttributeType(String attributeType);

    int deleteByPrimaryKey(Long id);
    
    List<AppConfiguration> selectSecurityAgreementText(
    		@Param("currentEnvironment") String currentEnvironment
    		);
    
    List<AppConfiguration> selectByAttributeTypeAndAttributeValue(
		@Param("attributeType") String attributeType,
		@Param("attributeValue") String attributeValue
	);

	List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramId(
		@Param("attributeType") String attributeType,
		@Param("assessmentProgramId") Long assessmentProgramId
	);

	List<String> getByAttributeType(@Param("attributeType") String attributeType);

	String getByAttributeCode(@Param("attributeCode") String attributeCode);

	List<AppConfiguration> getByAttributeCodeAndDefaultAssessment(String type);
	
	List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramIdNew(
			@Param("attributeType") String attributeType,
			@Param("assessmentProgramId") Long assessmentProgramId
		);
		
	List<String> getValueByAttributeType(@Param("attributeType") String attributeType);	
}