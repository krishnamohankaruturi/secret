package edu.ku.cete.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.UserModule;
import edu.ku.cete.domain.professionaldevelopment.Activity;
import edu.ku.cete.domain.professionaldevelopment.Module;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.professionaldevelopment.ModuleState;
import edu.ku.cete.domain.professionaldevelopment.UserTest;
import edu.ku.cete.domain.professionaldevelopment.UserTestResponse;
import edu.ku.cete.domain.professionaldevelopment.UserTestSection;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTask;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.DataReportTypeEnum;

/**
 * @author vittaly
 *
 */
public interface ProfessionalDevelopmentService extends Serializable {

	/**
	 * @param user
	 * @return
	 */
	List<Activity> getActivitiesByUserID(User user);
	
	/**
	 * @param string 
	 * @param offset
	 * @param limitCount
	 * @return
	 */
	List<Module> getModulesForAdmin(String sortByColumn, String sortType, int offset, int limitCount);
	
	List<Module> getModulesForStateAdmin(User user, String sortByColumn, String sortType, int offset, int limitCount);
	/**
	 * @return
	 */
	int countModulesForAdmin();

	int countModulesForStateAdmin(User user);
	/**
	 * @param orderByClause 
	 * @param offset
	 * @param limitCount
	 * @return
	 */
	List<Module> getModules(Long orgId,
			Map<String,String> modulesCollectionsCriteriaMap,
			String sortByColumn, String sortType, 
			Integer offset, 
			Integer limitCount,
			Long userId, boolean userModulesOnly);
	
	/**
	 * @return
	 */
	Integer countModules(Long orgId, Map<String,
			String> modulesCollectionsCriteriaMap, Long userId, boolean userModulesOnly);

	/**
	 * @return
	 */
	Module getByModuleId(Long moduleId);
	

	/**
	 * @return
	 */
	Boolean createModule(Module record);
	
	Boolean enrollToModule(Long ModuleId, User user);
	
	Boolean unenrollToModule(Long userId, Long moduleId);

	List<Module> getModuleByName(String moduleName);

	Boolean createActvities(Activity record);
	
	UserModule getModuleForUser(Long userId,Long moduleId);
	
	Boolean createUserModule(UserModule userModule);
	
	Boolean updateUserModule(UserModule userModule);
	
	Boolean updateModule(Module module);
	
	Boolean createUserTest(UserTest userTest);
	
	Boolean updateUserTest(UserTest userTest);	
	
	Boolean createUserTestSection(UserTestSection userTestSection);
	
	Boolean updateUserTestSection(UserTestSection userTestSection);

	List<Module> getModulesForPDuser(Map<String,String> modulesCollectionsCriteriaMap,
			String sortByColumn, String sortType, 
			Integer offset, 
			Integer limitCount,
			Long userId,
			User user);
	
	/**
	 * @param testId
	 * @return
	 */
	String getTest(Long testId) throws Exception;
	
	String clearTestCache(final Long testId);
	
	String getStudentTestById(Long id) throws Exception;
	String updateTestStatus(Long studentTestId,Long studentTestSectionId,String status) throws Exception;
	
	void addOrUpdateResponse(UserTestResponse data) throws Exception;

	Map<String, ? extends Object> saveTest(Long studentTestId,
			Long studentTestSectionId, Long testSectionId,
			String testFormatCode, String testTypeName,
			String interimThetaValues, Integer numberOfCompletedPart,
			Integer numberOfPart, String testScore, String sectionScore,
			boolean currentSectionBreak) throws Exception;

	String findByTestSection(Long userTestSectionId) throws Exception;
	
	UserTest getUserTestByModuleTestUserIds(@Param("moduleId") Long moduleId
			, @Param("testId") Long testId, @Param("userId") Long userId) throws Exception;
	
	void saveStudentsTestSectionsTasks(List<UserTestSectionTask> tasks) throws Exception;
	
	void updateUserModuleStatus(Long userModuleStatusId, Long userModuleId);
	
	void updateUserTestStatus(Long userTestStatusId, Long userTestId);
	
	void updateUserTestSectionStatus(Long userTestSectionStatusId, Long userTestId);
	
	boolean isUserTestATutorial(@Param("userTestId") Long userTestId) throws Exception;
	
	Module getModuleDetailsById(Long moduleId);
	
	ModuleState getStateCEUByModuleUser(Long moduleId, User user);
	
	Boolean updateModuleState(ModuleState moduleState);
	
	void releaseModule(Long moduleId, User user);
	void unreleaseModule(Long moduleId, User user);
	void unpublishModule(Long moduleId, User user);
	
	UserModule getUserModuleById(Long userModuleId);
	
	List<UserModule> getTranscripts(Long userId, Long organizationId
			, Map<String,String> transcriptsCriteriaMap, String sortByColumn, String sortType, int offset, int limitCount);
	
	int countTranscripts(Long userId, Long organizationId, Map<String,String> transcriptsCriteriaMap);
	
	Boolean createModuleState(ModuleState moduleState, User user);
	
	List<ModuleReport> getAdminReports(User user, Boolean isStateAdmin, Boolean isDistrictAdmin, List<Short> reportTypeIds);
	
	int countAdminReports(User user, Boolean isStateAdmin, Boolean isDistrictAdmin, List<Short> reportTypeIds);
	
	long generatePDReport(User user, Boolean isStateAdmin, 
			Boolean isDistrictAdmin, Long moduleReportId, DataReportTypeEnum reportType) throws Exception;
	
	ModuleReport getModuleReportById(Long moduleReportId);
	
	boolean startPDReportGeneration(User user, Long moduleReportId) throws IOException;
	
	void removeUserTestResponses(Long userTestId);	
	
}