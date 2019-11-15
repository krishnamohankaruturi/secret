package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.model.common.AppConfigurationDao;
import edu.ku.cete.service.AppConfigurationService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AppConfigurationServiceImpl implements AppConfigurationService {

    @Autowired
    private AppConfigurationDao appConfigurationDao;
	
	
	@Override
	public AppConfiguration selectByPrimaryKey(Long id) {
		return appConfigurationDao.selectByPrimaryKey(id);
	}

	@Override
	public List<AppConfiguration> selectByAttributeType(String attributeType) {
		return appConfigurationDao.selectByAttributeType(attributeType);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return appConfigurationDao.deleteByPrimaryKey(id);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AppConfiguration> selectSecurityAgreementText(String currentEnvironment) {
		return appConfigurationDao.selectSecurityAgreementText(currentEnvironment);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Map<String,AppConfiguration> selectIdMapByAttributeType(String attributeType) {
	    	List<AppConfiguration> appConfigurations = selectByAttributeType(attributeType);
	    	Map<String,AppConfiguration> codeAppConfigurationMap = new HashMap<String, AppConfiguration>();
	    	if(CollectionUtils.isNotEmpty(appConfigurations)) {
	    		for(AppConfiguration appConfiguration : appConfigurations) {
	    			//no null check because they are not null columns.
	    			codeAppConfigurationMap.put(appConfiguration.getAttributeCode(), appConfiguration);
	    		}
	    	}
	        return codeAppConfigurationMap;
	    }
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AppConfiguration> selectByAttributeTypeAndAttributeValue(String attributeType, String attributeValue) {
		return appConfigurationDao.selectByAttributeTypeAndAttributeValue(attributeType, attributeValue);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramId(String attributeType, Long assessmentProgramId) {
		return appConfigurationDao.selectByAttributeTypeAndAssessmentProgramIdNew(attributeType, assessmentProgramId);
	}

	@Override
	public List<String> getByAttributeType(String attributeType) {
			return appConfigurationDao.getByAttributeType(attributeType);
	}
	
	@Override
	public String getByAttributeCode(String attributeCode) {
		return appConfigurationDao.getByAttributeCode(attributeCode);
	}

	@Override
	public List<AppConfiguration> selectByAttributeTypeAndDefaultAssessment(String Type) {
		// TODO Auto-generated method stub
		return appConfigurationDao.getByAttributeCodeAndDefaultAssessment(Type);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AppConfiguration> selectByAttributeTypeAndAssessmentProgramIdForHispanic(String attributeType, Long assessmentProgramId) {
		return appConfigurationDao.selectByAttributeTypeAndAssessmentProgramIdNew(attributeType, assessmentProgramId);
	}
	
	
	@Override
	public List<String> getValueByAttributeType(String attributeType) {
			return appConfigurationDao.getValueByAttributeType(attributeType);
	}
	
}
