package edu.ku.cete.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import au.com.bytecode.opencsv.CSVWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.UserModule;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.professionaldevelopment.Activity;
import edu.ku.cete.domain.professionaldevelopment.Module;
import edu.ku.cete.domain.professionaldevelopment.ModuleGroup;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.professionaldevelopment.ModuleState;
import edu.ku.cete.domain.professionaldevelopment.ModuleStateKey;
import edu.ku.cete.domain.professionaldevelopment.ModuleTag;
import edu.ku.cete.domain.professionaldevelopment.UserTest;
import edu.ku.cete.domain.professionaldevelopment.UserTestResponse;
import edu.ku.cete.domain.professionaldevelopment.UserTestSection;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTask;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTaskFoil;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.OrganizationTypeDao;
import edu.ku.cete.model.UserModuleDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.professionaldevelopment.ActivityDao;
import edu.ku.cete.model.professionaldevelopment.ModuleDao;
import edu.ku.cete.model.professionaldevelopment.ModuleGroupMapper;
import edu.ku.cete.model.professionaldevelopment.ModuleReportMapper;
import edu.ku.cete.model.professionaldevelopment.ModuleStateMapper;
import edu.ku.cete.model.professionaldevelopment.ModuleTagMapper;
import edu.ku.cete.model.professionaldevelopment.UserTestMapper;
import edu.ku.cete.model.professionaldevelopment.UserTestResponseMapper;
import edu.ku.cete.model.professionaldevelopment.UserTestSectionMapper;
import edu.ku.cete.model.professionaldevelopment.UserTestSectionTaskFoilMapper;
import edu.ku.cete.model.professionaldevelopment.UserTestSectionTaskMapper;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.ProfessionalDevelopmentService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.PDTrainingDetailsReportDTO;
import edu.ku.cete.web.PDTrainingStatusReportDTO;

/**
 * @author vittaly
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProfessionalDevelopmentServiceImpl implements ProfessionalDevelopmentService {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4406886174007406635L;

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ProfessionalDevelopmentServiceImpl.class);
	
	private final String DATE_FORMAT_MM_DD_YY ="MM/dd/yy hh:mm a z";
	
	/**
	 *activityDao 
	 */
	@Autowired
	private ActivityDao activityDao;
	
	/**
	 *moduleDao 
	 */
	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private ModuleGroupMapper moduleGroupDao;
	
	/**
	 *moduleDao 
	 */
	@Autowired
	private UserModuleDao userModuleDao;
	
	@Autowired
	private ModuleTagMapper moduleTagDao;
	
	@Autowired
	private UserTestMapper userTestDao;

	@Autowired
	private UserTestSectionMapper userTestSectionDao;
	
	@Autowired
	private UserTestResponseMapper userTestResponseDao;
	
	@Autowired
	private UserTestSectionTaskMapper userTestSectionTaskDao;
	
	@Autowired
	private UserTestSectionTaskFoilMapper userTestSectionTaskFoilDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private OrganizationDao organizationDao;
	
    @Autowired
    private OrganizationTypeDao orgTypeDao;	
	
	@Autowired
	private ModuleStateMapper moduleStateDao;	
	
	@Autowired
	private ModuleReportMapper moduleReportDao;
	
	@Autowired
	private GroupsDao groupDao;	
	
	@Autowired(required = true)
	private RestTemplate restTemplate;

	@Autowired
	private TestService testService;
	
	@Autowired
	private GroupsService groupService;	
	
	@Value("${queued.extracts.starttime}")
	private int queuedExtractStartTime;
	
	@Value("${queued.extracts.endtime}")
	private int queuedExtractEndTime;
	
	@Value("${epservice.url}")
	private String epServiceURL;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.ProfessionalDevelopmentService#getActivitiesByUserID(edu.ku.cete.domain.user.User)
	 */
	public List<Activity> getActivitiesByUserID(User user) {
		return activityDao.getActivitiesByUserId(user.getId());
	}
	
	@Override
	public List<Module> getModulesForAdmin(String sortByColumn, String sortType, int offset, int limitCount){
		return moduleDao.getModulesForAdmin(sortByColumn, sortType, offset, limitCount);
	}

	@Override
	public List<Module> getModulesForStateAdmin(User user, String sortByColumn, String sortType, int offset, int limitCount){
		return moduleDao.getModulesForStateAdmin(user.getOrganization().getId(), sortByColumn, sortType, offset, limitCount);
	}

	@Override
	public int countModulesForAdmin() {
		return moduleDao.countModulesForAdmin();
	}
	
	@Override
	public int countModulesForStateAdmin(User user) {		
		return moduleDao.countModulesForStateAdmin(user.getOrganization().getId());
	}
	
	@Override
	public List<Module> getModules(Long orgId, Map<String,String> modulesCollectionsCriteriaMap,
			String sortByColumn, String sortType, Integer offset, Integer limitCount, Long userId, boolean userModulesOnly){
		return moduleDao.getModules(orgId, modulesCollectionsCriteriaMap, sortByColumn, sortType, offset, limitCount, userId, userModulesOnly);
	}
	
	@Override
	public Integer countModules(Long orgId, Map<String,String> modulesCollectionsCriteriaMap, Long userId, boolean userModulesOnly) {
		return moduleDao.countModules(orgId, modulesCollectionsCriteriaMap, userId, userModulesOnly);
	}
	
	@Override
	public Module getByModuleId(Long moduleId) {
		return getModuleDetailsById(moduleId);		
	}
	
	@Override
	public Module getModuleDetailsById(Long moduleId) {
		List<Module> modules = moduleDao.findModuleDetailsById(moduleId);
		if(modules != null && modules.size() > 0) {
			return modules.get(0);
		}
		return null;
	}
	
	public ModuleState getStateCEUByModuleUser(Long moduleId, User user) {
		
		List<Organization> orgs = organizationDao.getContractingOrganization(user.getOrganization().getId());
		
		if(CollectionUtils.isNotEmpty(orgs)) {
			Organization contractingOrg = (Organization) orgs.get(0);
			
			ModuleStateKey key = new ModuleState();
			key.setModuleId(moduleId);
			key.setStateId(contractingOrg.getId());
			
			ModuleState moduleState = moduleStateDao.selectByPrimaryKey(key);
			
			return moduleState;
		}
		
		return null;
	}
	
	@Override
	public List<Module> getModuleByName(String moduleName) {
		return moduleDao.getModuleByName(moduleName);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createModule(Module record) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		int check =  moduleDao.insert(record);
		
		if(check > 0){
			// Now insert all the groups
			if (record.getGroupIds() != null) {
				for (Long roleId : record.getGroupIds()) {
					ModuleGroup moduleGroup = new ModuleGroup();
					moduleGroup.setGroupId(roleId);
					moduleGroup.setModuleId(record.getId());
					moduleGroupDao.insert(moduleGroup);
				}
			}
			
	        if (record.getTagIds() != null) {
	            for (Long tagId : record.getTagIds()) {
	                if (null != tagId) {
	    				ModuleTag moduleTag = new ModuleTag();
	    				moduleTag.setTagId(tagId);
	    				moduleTag.setModuleId(record.getId());
	    				moduleTagDao.insert(moduleTag);
	                }
	            }
	        }
			
			flag = true;
		}else{
			
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean enrollToModule(Long moduleId, User user) {
		
		Date date = new Date();
		
		List<Organization> orgs = organizationDao.getContractingOrganization(user.getOrganization().getId());
		Organization contractingOrg = (Organization) orgs.get(0);
		
		Long enrolledStatusid = categoryDao.getCategoryId("ENROLLED", "USER_MODULE_STATUS");
		Long inProgressStatusid = categoryDao.getCategoryId("INPROGRESS", "USER_MODULE_STATUS");
		//Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "USER_MODULE_STATUS");
		Long passedStatusid = categoryDao.getCategoryId("PASSED", "USER_MODULE_STATUS");
		Long attemptedStatusid = categoryDao.getCategoryId("ATTEMPTED", "USER_MODULE_STATUS");
				
		//Verify if user is already enrolled || inprogress		
		UserModule existingModule = userModuleDao.getModuleForUser(user.getId(), moduleId);
		if(existingModule != null 
				&& (existingModule.getEnrollmentstatusid().equals(inProgressStatusid) 
						|| existingModule.getEnrollmentstatusid().equals(enrolledStatusid)
						|| existingModule.getEnrollmentstatusid().equals(passedStatusid)
						|| existingModule.getEnrollmentstatusid().equals(attemptedStatusid))) {
			return false;
		}
		
	    UserModule userModule = new UserModule();
	    userModule.setModuleId(moduleId);
	    userModule.setUserId(user.getId());
	    userModule.setEnrollmentstatusid(enrolledStatusid);
	    userModule.setStateId(contractingOrg.getId());
	    userModule.setCreatedDate(date);
	    userModule.setModifiedDate(date);
	    userModule.setCreatedUser(user.getId());
	    userModule.setModifiedUser(user.getId());
	    userModule.setActiveFlag(true);	    
	    //userModuleDao.insert(userModule);
	    userModuleDao.addOrUpdate(userModule);
	    
	    userModule = userModuleDao.getModuleForUser(user.getId(), moduleId);
	    Module module = getModuleDetailsById(moduleId);
	    
	    Long unUsedTestStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
	    Long unUsedTestSectionStatusid = categoryDao.getCategoryId("unused", "STUDENT_TESTSECTION_STATUS");
	    
	    // create usertest
	    UserTest userTest = new UserTest();
	    userTest.setUserModuleId(userModule.getId());
	    userTest.setStatusId(unUsedTestStatusid);
	    userTest.setCreatedDate(date);
	    userTest.setModifiedDate(date);
	    userTest.setActiveFlag(true);
	    
	    userTest.setTestId(module.getTestid());		    
	    userTestDao.insert(userTest);
	    
	    // create usertestsections
	    createUserTestSection(module.getTestid(), userTest, unUsedTestSectionStatusid);
	    
	    if(module.getTutorialid() != null){
		    userTest.setId(null);
		    userTest.setTestId(module.getTutorialid());
		    userTestDao.insert(userTest);
		    
		    // create usertestsection
		    createUserTestSection(module.getTutorialid(), userTest, unUsedTestSectionStatusid);
	    }
	    
	    return true;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void createUserTestSection(Long testId, UserTest userTest, Long unUsedTestSectionStatusid) {
		
	    List<TestSection> testSections = testService.findTestSectionByTest(testId);
	    for (TestSection testSection : testSections) {
		    UserTestSection userTestSection = new UserTestSection();
		    userTestSection.setUserTestId(userTest.getId());
		    userTestSection.setTestSectionId(testSection.getId());
		    userTestSection.setLastNavQNum(0);
		    userTestSection.setStatusId(unUsedTestSectionStatusid);
		    userTestSection.setCreatedDate(userTest.getCreatedDate());
		    userTestSection.setModifiedDate(userTest.getModifiedDate());
		    userTestSection.setActiveFlag(true);
		    
		    userTestSectionDao.insert(userTestSection);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateUserModuleStatus(Long userModuleStatusId, Long userModuleId) {
		
		UserModule userModule = userModuleDao.selectByPrimaryKey(userModuleId);
		userModule.setEnrollmentstatusid(userModuleStatusId);
		userModule.setModifiedDate(new Date());
		userModuleDao.updateByPrimaryKeySelective(userModule);
	}
		
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateUserTestStatus(Long userTestStatusId, Long userTestId) {
		
		UserTest userTest = userTestDao.selectByPrimaryKey(userTestId);
		userTest.setStatusId(userTestStatusId);
		userTest.setModifiedDate(new Date());
		userTestDao.updateByPrimaryKeySelective(userTest);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void updateUserTestSectionStatus(Long testSectionStatusId, Long userTestId) {
		
		userTestSectionDao.updateStatusByUserTestId(testSectionStatusId, userTestId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void removeUserTestResponses(Long userTestId) {
		userTestSectionDao.removeUserTestResponsesByUserTestId(userTestId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean unenrollToModule(Long userId, Long moduleId) {
		
		UserModule userModule = getModuleForUser(userId, moduleId);	
		if(userModule == null){
			return false;
		}
		Long statusid = categoryDao.getCategoryId("UNENROLLED", "USER_MODULE_STATUS");
		
		userModule.setEnrollmentstatusid(statusid);
		userModule.setModifiedDate(new Date());
		userModule.setActiveFlag(false);
		userModuleDao.updateByPrimaryKeySelective(userModule);
		
	    // update usertest - set active flag to flase
	    // update usertestsection - set active flag to flase
		// update usertestresponse - set active flag to flase
		userTestDao.deactivateByModuleId(userModule.getId());
		
		return true;
	}	
	
	@Override
	public UserModule getModuleForUser(Long userId, Long moduleId){
		return userModuleDao.getModuleForUser(userId,moduleId);
	}
	
	@Override
	public UserModule getUserModuleById(Long userModuleId){	
		return userModuleDao.selectByPrimaryKey(userModuleId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createActvities(Activity record) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		int check =  activityDao.insert(record);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createUserModule(UserModule userModule) {
		// TODO Auto-generated method stub
				Boolean flag = true;
				int check =  userModuleDao.insert(userModule);
				if(check > 0){
					flag = true;
				}else{
					flag = false;
				}
				return flag;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean updateUserModule(UserModule userModule) {
		Boolean flag = true;
		int check =  userModuleDao.updateByPrimaryKey(userModule);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createModuleState(ModuleState moduleState, User user) {
		
		List<Organization> orgs = organizationDao.getContractingOrganization(user.getOrganization().getId());
		
		if(CollectionUtils.isNotEmpty(orgs)) {
			Organization contractingOrg = (Organization) orgs.get(0);
			moduleState.setStateId(contractingOrg.getId());
			
			Boolean flag = true;
			int check =  moduleStateDao.insertSelective(moduleState);				
			if(check > 0){
				flag = true;
			}else{
				flag = false;
			}
			return flag;
		}
		
		return false;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void releaseModule(Long moduleId, User user) {
		
		Long statusId = categoryDao.getCategoryId("RELEASED", "MODULE_STATUS");		
		
		List<Organization> orgs = organizationDao.getContractingOrganization(user.getOrganization().getId());
		Organization contractingOrg = (Organization) orgs.get(0);					
		
		ModuleStateKey key = new ModuleState();
		key.setModuleId(moduleId);
		key.setStateId(contractingOrg.getId());
		
		ModuleState moduleState = moduleStateDao.selectByPrimaryKey(key);
		
		if(moduleState == null) {
			moduleState = new ModuleState();
			moduleState.setModuleId(moduleId);
			moduleState.setStateId(contractingOrg.getId());
			moduleState.setStatusId(statusId);
			moduleStateDao.insertSelective(moduleState);
		} else {
			moduleState.setStatusId(statusId);
			moduleStateDao.updateByPrimaryKeySelective(moduleState);
		}
	}	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean updateModuleState(ModuleState moduleState) {

		Boolean flag = true;
		int check =  moduleStateDao.updateByPrimaryKeySelective(moduleState);
		if(check > 0){			
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void unreleaseModule(Long moduleId, User user) {
		
		Date dateNow = new Date();		
		Long unreleasedStatusId = categoryDao.getCategoryId("UNRELEASED", "MODULE_STATUS");
		Long enrollmentStatusId = categoryDao.getCategoryId("ENROLLED", "USER_MODULE_STATUS");
		Long inprogressStatusId = categoryDao.getCategoryId("INPROGRESS", "USER_MODULE_STATUS");
		Long unenrolledStatusid = categoryDao.getCategoryId("UNENROLLED", "USER_MODULE_STATUS");
		
		List<Organization> orgs = organizationDao.getContractingOrganization(user.getOrganization().getId());
		Organization contractingOrg = (Organization) orgs.get(0);
		
		//Find enrolled user modules, unEnroll each module
		List<UserModule> enrolledUserModulesOfState = userModuleDao.getUserModuleByModuleStateStatus(moduleId
								, enrollmentStatusId, contractingOrg.getId());	
		List<UserModule> inprogressUserModulesOfState = userModuleDao.getUserModuleByModuleStateStatus(moduleId
				, inprogressStatusId, contractingOrg.getId());
		List<UserModule> unenrolledUserModulesOfState = userModuleDao.getUserModuleByModuleStateStatus(moduleId
				, unenrolledStatusid, contractingOrg.getId());
		
		enrolledUserModulesOfState.addAll(inprogressUserModulesOfState);
		enrolledUserModulesOfState.addAll(unenrolledUserModulesOfState);
		
		for (UserModule userModule : enrolledUserModulesOfState) {
			
			userModule.setEnrollmentstatusid(unenrolledStatusid);
			userModule.setModifiedDate(dateNow);
			userModule.setActiveFlag(false);
			userModuleDao.updateByPrimaryKeySelective(userModule);
			
		    // update usertest - set active flag to flase
		    // update usertestsection - set active flag to flase
			// update usertestresponse - set active flag to flase
			userTestDao.deactivateByModuleId(userModule.getId());
		}
		
		ModuleStateKey key = new ModuleState();
		key.setModuleId(moduleId);
		key.setStateId(contractingOrg.getId());
		
		ModuleState moduleState = moduleStateDao.selectByPrimaryKey(key);
		moduleState.setStatusId(unreleasedStatusId);

		moduleStateDao.updateByPrimaryKeySelective(moduleState);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void unpublishModule(Long moduleId, User user) {
		
		Date dateNow = new Date();
		Long unpublishStatusId = categoryDao.getCategoryId("UNPUBLISHED", "MODULE_STATUS");
		Long unreleasedStatusId = categoryDao.getCategoryId("UNRELEASED", "MODULE_STATUS");
		Long enrollmentStatusId = categoryDao.getCategoryId("ENROLLED", "USER_MODULE_STATUS");
		Long unenrolledStatusid = categoryDao.getCategoryId("UNENROLLED", "USER_MODULE_STATUS");
		Long inprogressStatusId = categoryDao.getCategoryId("INPROGRESS", "USER_MODULE_STATUS");
		
		//Find enrolled user modules, unEnroll each module
		List<UserModule> enrolledUserModules = userModuleDao.getUserModuleByModuleStateStatus(moduleId, enrollmentStatusId, null);
		List<UserModule> inprogressUserModules = userModuleDao.getUserModuleByModuleStateStatus(moduleId, inprogressStatusId, null);
		List<UserModule> unenrolledUserModules = userModuleDao.getUserModuleByModuleStateStatus(moduleId, unenrolledStatusid, null);
		
		enrolledUserModules.addAll(inprogressUserModules);		
		enrolledUserModules.addAll(unenrolledUserModules);
		
		for (UserModule userModule : enrolledUserModules) {
			
			userModule.setEnrollmentstatusid(unenrolledStatusid);
			userModule.setModifiedDate(dateNow);
			userModule.setActiveFlag(false);
			userModuleDao.updateByPrimaryKeySelective(userModule);
			
		    // update usertest - set active flag to flase
		    // update usertestsection - set active flag to flase
			// update usertestresponse - set active flag to flase
			userTestDao.deactivateByModuleId(userModule.getId());
		}
		
		//Find released states of module, unrelease from each state
		List<ModuleState> moduleStates = moduleStateDao.selectByModule(moduleId);
		
		for (ModuleState moduleState : moduleStates) {
			moduleState.setStatusId(unreleasedStatusId);
			moduleStateDao.updateByPrimaryKeySelective(moduleState);
		}
		
		// update the status of moduel to unpublished
		Module module = getModuleDetailsById(moduleId);
		module.setStatusid(unpublishStatusId);
		module.setModifieddate(dateNow);
		module.setModifieduser(user.getId());
		moduleDao.updateByPrimaryKeySelective(module);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean updateModule(Module module) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		int check =  moduleDao.updateByPrimaryKeySelective(module);
		if(check > 0){
			// remove existing groups and tags
			moduleTagDao.deleteByModule(module.getId());
			moduleGroupDao.deleteByModule(module.getId());
			if (module.getGroupIds() != null) {
				for (Long roleId : module.getGroupIds()) {
					ModuleGroup moduleGroup = new ModuleGroup();
					moduleGroup.setGroupId(roleId);
					moduleGroup.setModuleId(module.getId());
					moduleGroupDao.insert(moduleGroup);
				}
			}
			
	        if (module.getTagIds() != null) {
	            for (Long tagId : module.getTagIds()) {
	                if (null != tagId) {
	    				ModuleTag moduleTag = new ModuleTag();
	    				moduleTag.setTagId(tagId);
	    				moduleTag.setModuleId(module.getId());
	    				moduleTagDao.insert(moduleTag);
	                }
	            }
	        }			
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	@Override
	public List<Module> getModulesForPDuser(
			Map<String,String> modulesCollectionsCriteriaMap,
			String sortByColumn, String sortType, Integer offset, Integer limitCount, Long userId, User user) {
		return moduleDao.getModulesForPDuser(modulesCollectionsCriteriaMap, sortByColumn, sortType, offset, limitCount, userId, user.getOrganization().getId());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createUserTest(UserTest userTest) {

		Boolean flag = true;
		int check =  userTestDao.insert(userTest);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean updateUserTest(UserTest userTest) {

		Boolean flag = true;
		int check =  userTestDao.updateByPrimaryKeySelective(userTest);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean createUserTestSection(UserTestSection userTestSection) {

		Boolean flag = true;
		int check =  userTestSectionDao.insert(userTestSection);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Boolean updateUserTestSection(UserTestSection userTestSection) {

		Boolean flag = true;
		int check =  userTestSectionDao.updateByPrimaryKeySelective(userTestSection);
		if(check > 0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}	
	
	/**
	 * @param uri
	 *            {@link String}
	 * @param parameters
	 *            {@link String}
	 * @return servicePath {@link String}
	 */
	private String servicePath(String uri, String... parameters) {
		LOGGER.trace("Entering the servicePath method");
		LOGGER.debug("Parameters: uri: {}, parameters: {}", new Object[] { uri,
				parameters });

		StringBuilder sb = new StringBuilder(uri);

		for (String parameter : parameters) {
			sb.append("/");
			sb.append(parameter);
		}

		LOGGER.debug("Returning string: {}", sb.toString());
		LOGGER.trace("Leaving the servicePath method");
		return sb.toString();
	}
	
	//@Cacheable(value = "nonadaptivetest", key = "#testId")
	@Override
	public final String getTest(final Long testId) throws Exception {
		LOGGER.trace("Entering the getTest with testId {}", testId);

		String[] parameters = new String[] { "test", "nonadaptive",
				testId.toString() };

		String responseEntity = restTemplate.getForObject(servicePath(epServiceURL, parameters), String.class);


		LOGGER.debug("Returning test in getTestByEPRestService: {}", responseEntity);
		LOGGER.trace("Leaving the getTestByEPRestService method");

		//return mapper.writeValueAsString(test);
		return responseEntity;
	}

	//@CacheEvict(value = "nonadaptivetest", key = "#testId")
	@Override
	public String clearTestCache(final Long testId) {
		return "Ok";
	}
	
	public final String getStudentTestById(Long id) throws Exception {
		UserTest test = userTestDao.getUserTestById(id);
		
		if(test != null) {
			test.setStudentTestSections(userTestSectionDao.getNotCompletedSectionsByUserTestId(id));
		}
		//	String[] parameters = new String[] { "studenttest",
		//    "studentteststatebyid", id.toString() };

		  
		//  String responseEntity = restTemplate.getForObject(servicePath(epServiceURL, parameters), String.class);
		ObjectMapper mapper = new ObjectMapper();
		return "{\"studentTest\":"+mapper.writeValueAsString(test)+", \"lcsId\":null}";
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public String updateTestStatus(Long studentTestId,Long studentTestSectionId,String status) throws Exception {
		Date curDate = new Date();
		Long testStatusId = categoryDao.getCategoryId(status, "STUDENT_TEST_STATUS");
		Long categoryId = categoryDao.getCategoryId(status, "STUDENT_TESTSECTION_STATUS");
		UserTest studentTest = userTestDao.selectByPrimaryKey(studentTestId);
		UserTestSection studentTestSection = userTestSectionDao.selectByPrimaryKey(studentTestSectionId);
		
		studentTest.setStatusId(testStatusId);
		studentTest.setModifiedDate(curDate);
		
		studentTestSection.setStatusId(categoryId);
		studentTestSection.setModifiedDate(curDate);
		if (status.equalsIgnoreCase("inprogress")) {
			studentTest.setStartDateTime(curDate);
			studentTestSection.setStartDateTime(curDate);
		} else if (status.equalsIgnoreCase("complete")) {
			studentTest.setEndDateTime(curDate);
			studentTestSection.setEndDateTime(curDate);
		}
		
		userTestDao.updateByPrimaryKey(studentTest);
		userTestSectionDao.updateByPrimaryKey(studentTestSection);
		return "{\"status\": \"updated\"}";
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void addOrUpdateResponse(UserTestResponse data) throws Exception {
//		UserTestResponseKey key = new UserTestResponseKey();
//		key.setTaskVariantId(data.getTaskVariantId());
//		key.setUserTestSectionId(data.getUserTestSectionId());
//		UserTestResponse response = userTestResponseDao.selectByPrimaryKey(key);
//		if(response == null) {
//			data.setCreatedDate(new Date());
//			data.setModifiedDate(new Date());
//			data.setActiveFlag(true);
//			userTestResponseDao.insert(data);
//		} else {
//			response.setModifiedDate(new Date());
//			response.setFoilId(data.getFoilId());
//			response.setResponse(data.getResponse());
//			response.setScore(data.getScore());
//			userTestResponseDao.updateByPrimaryKey(response);
//		}
		
		userTestResponseDao.addOrUpdate(data);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Map<String, ? extends Object> saveTest(final Long studentTestId,
			final Long studentTestSectionId,
			final Long testSectionId, 
			final String testFormatCode,
			final String testTypeName,
			final String interimThetaValues,
			final Integer numberOfCompletedPart,
			final Integer numberOfPart,
			final String testScore,
			final String sectionScore,
			final boolean currentSectionBreak) throws Exception{
		HashMap<String,Object> toReturn = new HashMap<String,Object>(); 
		UserTest studentTest = userTestDao.selectByPrimaryKey(studentTestId); 

		if (studentTest != null && (null == testFormatCode || ("null").equals(testFormatCode) || testFormatCode.equalsIgnoreCase("NADP"))) {
			
			boolean isTestCompleted = 	userTestDao.updateSectionStatusAndGetTestStatus(studentTestId, testSectionId, sectionScore, testScore, "complete");					

			LOGGER.debug("Update test Section return - "+ isTestCompleted);
			if(!isTestCompleted) {
				toReturn.put("retPage", "test"); 
			} else { 
				// verify and if NOT a tutorial update usermodule as completed when user completes the test.
				if(!isUserTestATutorial(studentTestId)) {
					
					// find out totaltestscore
					BigDecimal totalTestScore = userTestDao.getTotalTestScore(studentTestId);
					// update earnedCEU, testresult, totaltestscore					
					UserModule userModule = userModuleDao.selectByPrimaryKey(studentTest.getUserModuleId());
					Module module = getByModuleId(userModule.getModuleId());
					ModuleStateKey moduleStateKey = new ModuleStateKey();
					moduleStateKey.setModuleId(module.getId());
					moduleStateKey.setStateId(userModule.getStateId());
					ModuleState moduleState = moduleStateDao.selectByPrimaryKey(moduleStateKey);
					
					boolean testResult = false;
					Integer earnedCEU = null;
					if(module.getPassingScore() != null && module.getPassingScore() > 0) {
						if(totalTestScore != null && (totalTestScore.compareTo(new BigDecimal(module.getPassingScore())) > -1)){
							testResult = true;
							earnedCEU = moduleState.getCeu();
						}						
					} else {
						testResult = true;
						earnedCEU = moduleState.getCeu();
					}
					
					Long passedStatusid = categoryDao.getCategoryId("PASSED", "USER_MODULE_STATUS");
					Long attemptedStatusid = categoryDao.getCategoryId("ATTEMPTED", "USER_MODULE_STATUS");
					
					if(testResult) {
						userModule.setEnrollmentstatusid(passedStatusid);
					} else {
						userModule.setEnrollmentstatusid(attemptedStatusid);
					}
					userModule.setModifiedDate(new Date());
					userModule.setEarnedCEU(earnedCEU);
					userModule.setTestResult(testResult);
					userModule.setTestFinalScore(totalTestScore);
					userModule.setTestCompletionDate(new Date());
					
					userModuleDao.updateByPrimaryKey(userModule);	
				}
				
				toReturn.put("retPage", "home");  
			}
		}
		return toReturn;
	}
	
	@Override
	public String findByTestSection(final Long userTestSectionId) throws Exception {
		List<UserTestResponse> responses = userTestResponseDao.selectByUserTestSection(userTestSectionId);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(responses);
	}
	
	@Override
	public UserTest getUserTestByModuleTestUserIds(@Param("moduleId") Long moduleId
			, @Param("testId") Long testId, @Param("userId") Long userId) throws Exception {
		return userTestDao.getUserTestByModuleTestUserIds(moduleId, testId, userId);
	}

	@Override
	public boolean isUserTestATutorial(@Param("userTestId") Long userTestId) throws Exception {
		return userTestDao.isUserTestATutorial(userTestId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveStudentsTestSectionsTasks(List<UserTestSectionTask> tasks)
			throws Exception {
		Date curDate = new Date();
		for(UserTestSectionTask t: tasks) {
			t.setCreatedDate(curDate);
			t.setModifiedDate(curDate);
			userTestSectionTaskDao.insert(t);
			
			if(t.getFoils() != null) {
				for(UserTestSectionTaskFoil f: t.getFoils()) {
					f.setCreatedDate(curDate);
					f.setModifiedDate(curDate);
					userTestSectionTaskFoilDao.insert(f);
				}
			}
		}
	}
	
	@Override
	public List<UserModule> getTranscripts(Long userId
			, Long organizationId, Map<String,String> transcriptsCriteriaMap, String sortByColumn, String sortType, int offset, int limitCount) {
		return userModuleDao.getTranscripts(userId, organizationId, transcriptsCriteriaMap, sortByColumn, sortType, offset, limitCount);
	}
	
	@Override
	public int countTranscripts(Long userId, Long organizationId, Map<String,String> transcriptsCriteriaMap) {
		
		return userModuleDao.countTranscripts(userId, organizationId, transcriptsCriteriaMap);
	}	
	
	@Override
	public List<ModuleReport> getAdminReports(User user, Boolean isStateAdmin, 
					Boolean isDistrictAdmin, List<Short> reportTypeIds) {
		
		List<ModuleReport> pdReports = moduleReportDao.getAdminModuleReports(user.getOrganization().getId(), 
				isStateAdmin, isDistrictAdmin, user.getCurrentGroupsId(), user.getId(), reportTypeIds);
		
		return pdReports;
	}
	
	@Override
	public int countAdminReports(User user, Boolean isStateAdmin, 
					Boolean isDistrictAdmin, List<Short> reportTypeIds) {

		return moduleReportDao.countAdminModuleReports(user.getOrganization().getId(), 
				isStateAdmin, isDistrictAdmin, user.getCurrentGroupsId(), user.getId(), reportTypeIds);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public long generatePDReport(User user, Boolean isStateAdmin, 
			Boolean isDistrictAdmin, Long moduleReportId, DataReportTypeEnum reportType) throws Exception {
		
		Long groupId = null;
		Long contractingOrgId = null;
		String reportFileName = reportType.getFileName();
		
		if(isDistrictAdmin){
			contractingOrgId = user.getContractingOrgId();
			reportFileName += "_" + user.getOrganization().getDisplayIdentifier() + "_" + user.getId();
			groupId = user.getCurrentGroupsId();
			
		} else if(isStateAdmin){
			contractingOrgId = user.getContractingOrgId();
			reportFileName += "_" + user.getContractingOrgDisplayIdentifier() + "_" + user.getId();
			groupId = user.getCurrentGroupsId();
			
		} else {
			reportFileName += "_" + user.getId();
			groupId = user.getCurrentGroupsId();
		}
		
		//Long inProgressStatusid = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");
		Long inQueueStatusId = categoryDao.getCategoryId("IN_QUEUE", "PD_REPORT_STATUS");
		
		// See if a report already exists. If so mark it as inactive and create a new record.
		Date currentDate = new Date();
		Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
		Date currCentralTime = currentTime.getTime();
		currentTime.set(Calendar.HOUR_OF_DAY, queuedExtractStartTime);
		Date extractQStartDate = currentTime.getTime();
		
		currentTime.set(Calendar.HOUR_OF_DAY, queuedExtractEndTime);
		Date extractQEndDate = currentTime.getTime();
		
		ModuleReport moduleReport = null;
		if(moduleReportId > 0) {
			//Shall we remove the report on the file system
			moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
			moduleReport.setActiveFlag(false);
			moduleReport.setModifiedDate(currentDate);
			moduleReport.setModifiedUser(user.getId().intValue());
			moduleReportDao.updateByPrimaryKeySelective(moduleReport);
		}
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
        String currentDateStr = DATE_FORMAT.format(currentDate);
        reportFileName += "_" + currentDateStr + ".csv";
        
        Groups userRole = groupService.getGroup(user.getCurrentGroupsId());
        
		moduleReport = new ModuleReport();
		moduleReport.setGroupId(groupId);
		moduleReport.setStateId(contractingOrgId);
		moduleReport.setStatusId(inQueueStatusId);
		moduleReport.setFileName(reportFileName);
		moduleReport.setActiveFlag(true);
		moduleReport.setCreatedUser(user.getId().intValue());
		moduleReport.setCreatedDate(currentDate);
		moduleReport.setModifiedUser(user.getId().intValue());
		moduleReport.setModifiedDate(currentDate);
		
		moduleReport.setReportTypeId(reportType.getId());
		moduleReport.setReportType(reportType.getName());
		moduleReport.setDescription(reportType.getDescription());
		
		moduleReport.setOrganizationId(user.getOrganizationId());
		moduleReport.setOrganizationTypeId(user.getOrganization().getOrganizationTypeId());
		
		if(reportType.getQueued().equalsIgnoreCase("true")
				&& currCentralTime.before(extractQStartDate) 
				&& currCentralTime.after(extractQEndDate) ) {

			moduleReport.setStartTime(extractQStartDate);
		} else {
			moduleReport.setStartTime(currentDate);
		}
		
		moduleReportDao.insertSelective(moduleReport);
		
		return moduleReport.getId();
	}
	
	public ModuleReport getModuleReportById(Long moduleReportId) {
		return moduleReportDao.selectByPrimaryKey(moduleReportId);
	}	
	
    private void writeCSVData(List<PDTrainingStatusReportDTO> trainingStatuDtos, String fileName) throws IOException {
    	
    	LOGGER.debug("Entering writeCSVData --> fileName:" + fileName);
    	CSVWriter csvWriter = null;
    	 try {
	        // create pdreports directory if it doesn't exist;
	    	File  f = new File(REPORT_PATH + java.io.File.separator + "pdreports");
	    	if(!f.exists()){
	    		f.mkdir();
	    	}
	    	
			String reportPDFName = "pdreports" + java.io.File.separator + fileName;
	    	File csvFile = new File(REPORT_PATH + java.io.File.separator   + reportPDFName);
	    	
	    	LOGGER.debug("Before converting to report data to String array");
	        csvWriter = new CSVWriter(new FileWriter(csvFile), ',');
	        List<String[]> data  = toStringArray(trainingStatuDtos);
	        LOGGER.debug("Before writing to the file...."+ reportPDFName);
	        csvWriter.writeAll(data);
	
	        LOGGER.debug("After writing to the file...."+ reportPDFName);
    	 } catch (IOException ex) {
    		 LOGGER.error("IOException Occured:", ex);
    		 throw ex;
    	 } finally {
         	if(csvWriter != null) {
         		csvWriter.close();
         	}
         }
    }
     
private void writeDLMUserDetailsCSVData(List<DLMPDTrainingDTO> trainingDetailsDtos, String fileName) throws IOException {
    	
    	LOGGER.debug("Entering writeCSVData --> fileName:" + fileName);
    	CSVWriter csvWriter = null;
    	 try {
	        // create pdreports directory if it doesn't exist;
	    	File  f = new File(REPORT_PATH + java.io.File.separator + "pdreports");
	    	if(!f.exists()){
	    		f.mkdir();
	    	}
	    	
			String reportPDFName = "pdreports" + java.io.File.separator + fileName;
	    	File csvFile = new File(REPORT_PATH + java.io.File.separator   + reportPDFName);
	    	
	    	LOGGER.debug("Before converting to report data to String array");
	        csvWriter = new CSVWriter(new FileWriter(csvFile), ',');
	        List<String[]> data  = toDlmUserDetailsStringArray(trainingDetailsDtos);
	        LOGGER.debug("Before writing to the file...."+ reportPDFName);
	        csvWriter.writeAll(data);
	
	        LOGGER.debug("After writing to the file...."+ reportPDFName);
    	 } catch (IOException ex) {
    		 LOGGER.error("IOException Occured:", ex);
    		 throw ex;
    	 } finally {
         	if(csvWriter != null) {
         		csvWriter.close();
         	}
         }
    }
private void writeDetailsCSVData(List<PDTrainingDetailsReportDTO> trainingDetailsDtos, String fileName) throws IOException {
    	
    	LOGGER.debug("Entering writeCSVData --> fileName:" + fileName);
    	CSVWriter csvWriter = null;
    	 try {
	        // create pdreports directory if it doesn't exist;
	    	File  f = new File(REPORT_PATH + java.io.File.separator + "pdreports");
	    	if(!f.exists()){
	    		f.mkdir();
	    	}
	    	
			String reportPDFName = "pdreports" + java.io.File.separator + fileName;
	    	File csvFile = new File(REPORT_PATH + java.io.File.separator   + reportPDFName);
	    	
	    	LOGGER.debug("Before converting to report data to String array");
	        csvWriter = new CSVWriter(new FileWriter(csvFile), ',');
	        List<String[]> data  = toDetailsStringArray(trainingDetailsDtos);
	        LOGGER.debug("Before writing to the file...."+ reportPDFName);
	        csvWriter.writeAll(data);
	
	        LOGGER.debug("After writing to the file...."+ reportPDFName);
    	 } catch (IOException ex) {
    		 LOGGER.error("IOException Occured:", ex);
    		 throw ex;
    	 } finally {
         	if(csvWriter != null) {
         		csvWriter.close();
         	}
         }
    }
 
    private List<String[]> toStringArray(List<PDTrainingStatusReportDTO> reportDtos) {
        List<String[]> records = new ArrayList<String[]>();
        //add header record
        records.add(new String[]{"State","District","School ID","School Name","User Status","Educator ID"
        		,"First Name","Last Name","# Required Modules Passed","# Required Modules Enrolled"
        		,"# Required Modules","# Optional Modules Passed","# Optional Modules Enrolled","# Optional Modules"});
        
        Iterator<PDTrainingStatusReportDTO> it = reportDtos.iterator();
        while(it.hasNext()){
        	PDTrainingStatusReportDTO dto = it.next();
            records.add(new String[]{dto.getState(), dto.getDistrictName(),(dto.getSchoolId() == null ? "" : dto.getSchoolId().toString())
            		, dto.getSchoolName(), dto.getUserStatus(), (dto.getEducatorId() == null ? "" : dto.getEducatorId()), dto.getEducatorFirstName()
            		, dto.getEducatorLastName(), new Long(dto.getNbrOfPassedRequiredModules()).toString()
            		, new Long(dto.getNbrOfEnrolledRequiredModules()).toString(), new Long(dto.getNbrOfRequiredModules()).toString()
            		, new Long(dto.getNbrOfPassedOptionalModules()).toString(), new Long(dto.getNbrOfEnrolledOptionalModules()).toString()
            		, new Long(dto.getNbrOfOptionalModules()).toString()});
        }
        return records;
    }
    
    private List<String[]> toDlmUserDetailsStringArray(List<DLMPDTrainingDTO> reportDtos) {
        List<String[]> records = new ArrayList<String[]>();
        
        //add header record
        records.add(new String[] {
        					"Username",
        					"LastName", "FirstName",
        					"Email", "idnumber",
        					"password", "state",
        					"DistrictName", "DistrictID",
        					"SchoolName", "SchoolID",
        					"Role", "CreateDate", 
        					"RTComplete","RTCompleteDate"});        

        Iterator<DLMPDTrainingDTO> it = reportDtos.iterator();

        while(it.hasNext()){
        	
        	DLMPDTrainingDTO dto = it.next();        	
            records.add(new String[]{
            		dto.getUserName(),
            		dto.getLastName(), dto.getFirstName(), 
            		dto.getEmail(), dto.getIdNumber(),
            		StringUtils.EMPTY, dto.getState(),
            		dto.getDistrictName(), dto.getDistrictId(),
            		dto.getSchoolName(), dto.getSchoolId(),
            		dto.getRole(), DateUtil.format(dto.getCreatedDate(), DATE_FORMAT_MM_DD_YY),
            		dto.getRtComplete(), DateUtil.format(dto.getRtCompleteDate(), DATE_FORMAT_MM_DD_YY)});
        }
        
        return records;
    }
    
    private List<String[]> toDetailsStringArray(List<PDTrainingDetailsReportDTO> reportDtos) {
        List<String[]> records = new ArrayList<String[]>();
        //add header record
        records.add(new String[]{"State","District","School ID","School Name","User Status","Educator ID"
        		,"First Name","Last Name","Module","Required","Module Status", 
        		"Training format", "Facilitated Training Date", "Facilitated Training Location", 
        		"Facilitator or Organization",	"User Name for Certification"});        

        Iterator<PDTrainingDetailsReportDTO> it = reportDtos.iterator();

        while(it.hasNext()){
        	PDTrainingDetailsReportDTO dto = it.next();
        	String trainingFormat = "";
        	String trainingDate = "";
        	String trainingLocation = "";
        	String trainingFacilitator = "";
        	String nameOfCertification = "";
        	String moduleStatus = dto.getModuleStatus();
        	
        	if(CollectionUtils.isNotEmpty(dto.getUserResponses())) {
	        	for (UserTestResponse userResponse : dto.getUserResponses()) {
	        		if(userResponse.getTaskTypeCode() != null 
	        				&& userResponse.getTaskTypeCode().equalsIgnoreCase("MC-K") && userResponse.getFoilText() != null) {
	        			if(userResponse.getFoilText().contains("FACILITATED")) {
	        				trainingFormat = "Facilitated";
	        			} else if (userResponse.getFoilText().contains("SELF-DIRECTED")) {
	        				trainingFormat = "Self-Directed";
	        			}	        			
	        		} else if(userResponse.getTaskTypeCode() != null 
	        				&& userResponse.getTaskTypeCode().equalsIgnoreCase("CR") && userResponse.getResponse() != null) {
	        			
	        			if(userResponse.getResponse().trim().length() > 0) {
		        			String[] foils = userResponse.getResponse().split("~~~");
		        			
		        			for (int j = 0; j < foils.length; j++) {
		        				if(j == 0) trainingDate = foils[0];
		        				if(j == 1) trainingLocation = foils[1];
		        				if(j == 2) trainingFacilitator = foils[2];
		        				if(j == 3) nameOfCertification = foils[3];
							}		        			
	        			}
	        		}
				}
        	}
            records.add(new String[]{dto.getState(), dto.getDistrictName(),(dto.getSchoolId() == null ? "" : dto.getSchoolId().toString())
            		, dto.getSchoolName(), dto.getUserStatus(), (dto.getEducatorId() == null ? "" : dto.getEducatorId()), dto.getEducatorFirstName()
            		, dto.getEducatorLastName(), dto.getModuleName()
            		, (dto.getRequiredFlag() != null && dto.getRequiredFlag()) ? "Required": "Optional", moduleStatus
            		, trainingFormat, trainingDate, trainingLocation, trainingFacilitator, nameOfCertification});
        }
        return records;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean startPDReportGeneration(User user, Long moduleReportId) throws IOException {
        
    	LOGGER.debug("Entering startPDReportGeneration --> moduleReportId:" + moduleReportId);
        ModuleReport moduleReport = moduleReportDao.selectByPrimaryKey(moduleReportId);
        
        Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
        
    	Long districtOrganizationId = null;
    	if(moduleReport.getOrganizationTypeId() != null){
    		
    		OrganizationType orgType = orgTypeDao.get(moduleReport.getOrganizationTypeId());
    		if(orgType != null && orgType.getTypeCode().equalsIgnoreCase("DT")){
    			
    			districtOrganizationId = moduleReport.getOrganizationId();
    		}
    	}
    	
        if(moduleReport.getReportTypeId().equals(DataReportTypeEnum.TRAINING_STATUS.getId())){
        	
            LOGGER.debug("Generating PD Training status Report for moduleReport.stateId:" + moduleReport.getStateId() + ", organizationId:" + moduleReport.getOrganizationId());
    		List<PDTrainingStatusReportDTO> reportDtos = moduleReportDao.generatePDTrainingStatusReportItems(moduleReport.getStateId(), districtOrganizationId);
    		
    		LOGGER.debug("Writing CSV data from DTO :" + moduleReport.getStateId());
    		writeCSVData(reportDtos, moduleReport.getFileName());
    		//Thread.sleep(15000L);
        } else if (moduleReport.getReportTypeId().equals(DataReportTypeEnum.TRAINING_DETAILS.getId())) {
        	
        	LOGGER.debug("Generating PD Traing Details Report for moduleReport.stateId:" + moduleReport.getStateId() + ", organizationId:" + moduleReport.getOrganizationId());
    		List<PDTrainingDetailsReportDTO> reportDtos = moduleReportDao.generatePDTrainingDetailsReportItems(moduleReport.getStateId(), districtOrganizationId);
    		
    		LOGGER.debug("Writing CSV data from DTO :" + moduleReport.getStateId());
    		writeDetailsCSVData(reportDtos, moduleReport.getFileName());	
        } 
          else if (moduleReport.getReportTypeId().equals(DataReportTypeEnum.DLM_PD_TRAINING_LIST.getId())) {
        	
        	LOGGER.debug("Generating DLM_PD_TRAINING_LIST for moduleReport.stateId:" + moduleReport.getStateId() + ", organizationId:" + moduleReport.getOrganizationId());
        	//List<DLMPDTrainingDTO> reportListItemsDtos = moduleReportDao.generateDLMPDTrainingListItems(currentSchoolYear);
    		
    		LOGGER.debug("Writing CSV data from DTO :" + moduleReport.getStateId());
    		//writeDLMUserDetailsCSVData(reportListItemsDtos, moduleReport.getFileName());
        }
		
		LOGGER.debug("Updating complete status on moduleReportId:" + moduleReportId);
		Long completedStatusid = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		moduleReport.setStatusId(completedStatusid);
		moduleReport.setModifiedDate(new Date());
		moduleReportDao.updateByPrimaryKeySelective(moduleReport);
		LOGGER.debug("Updated to complete status on moduleReportId:" + moduleReportId);
		LOGGER.debug("Exit startPDReportGeneration --> moduleReportId:" + moduleReportId);
		
		return true;
    }
}