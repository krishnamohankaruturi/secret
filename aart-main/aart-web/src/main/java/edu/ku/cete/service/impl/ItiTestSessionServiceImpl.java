package edu.ku.cete.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.ItiMCLog;
import edu.ku.cete.domain.ItiMCLogExample;
import edu.ku.cete.domain.ItiStudentReport;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.ItiTestSessionHistoryExample;
import edu.ku.cete.domain.ItiTestSessionResourceInfo;
import edu.ku.cete.domain.ItiTestSessionSensitivityTags;
import edu.ku.cete.domain.ItiTestSessionSensitivityTagsExample;
import edu.ku.cete.domain.SensitivityTag;
import edu.ku.cete.domain.SensitivityTagExample;
import edu.ku.cete.domain.StudentItemInfoForMC;
import edu.ku.cete.domain.StudentNodeProbability;
import edu.ku.cete.domain.StudentNodeProbabilityExample;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.GradeCourseExample;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.report.roster.ItiRosterReportEE;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.model.ItiMCLogMapper;
import edu.ku.cete.model.ItiTestSessionHistoryMapper;
import edu.ku.cete.model.ItiTestSessionSensitivityTagsMapper;
import edu.ku.cete.model.ReadingEssentialelementsMapper;
import edu.ku.cete.model.SensitivityTagMapper;
import edu.ku.cete.model.StudentNodeProbabilityMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.ContentFrameworkService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.ComplexityBandEnum;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StudentUtil;
import edu.ku.cete.web.IAPContentFramework;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ItiTestSessionServiceImpl implements ItiTestSessionService {

	 @Autowired
	 private ItiTestSessionHistoryMapper itiTestSessionHistoryMapper;
	 
	 @Autowired
	 private ItiTestSessionSensitivityTagsMapper itiTestSessionSensitivityTagsMapper;
	 
	 @Autowired
	 private SensitivityTagMapper sensitivityTagMapper;
	 
	 @Autowired
	 private ItiMCLogMapper ItiMCLogMapper;
	 
	 @Autowired
	 private StudentNodeProbabilityMapper studentNodeProbabilityMapper;
	 
	 @Autowired
	 private TestSessionService testSessionService;
	 
	 @Autowired
	 private StudentService studentService;
	 /**
	 * studentsTestsService
	 */
	 @Autowired
	 private StudentsTestsService studentsTestsService;
	    
	 @Autowired
	 private StudentProfileService studentProfileService;
	 
	 @Autowired
	 private ReadingEssentialelementsMapper readingEssentialelementsMapper;
	  
	@Value("${nfs.url}")
	private String MEDIA_PATH;
	 
	 /**
	 * TEST_SESSION_STATUS_UNUSED
	 */
	 @Value("${testsession.status.pending}")
	 private String TEST_SESSION_STATUS_PENDING;
	    
	 /**
	 * TEST_SESSION_STATUS_TYPE
	 */
	 @Value("${testsession.status.type}")
	 private String TEST_SESSION_STATUS_TYPE;
	    
	 /**
	 * Source Type
	 */
	 @Value("${source.type}")
	 private String SOURCE_TYPE;
	    
	 /**
	     * ITI Source
	 */
	 @Value("${source.iti.code}")
	 private String SOURCE_CODE_ITI;
	 
	 @Value("${testsession.status.closed}")
	 private String completeStatusType;
	 
	 /**
	 * TEST_SESSION_STATUS_UNUSED
	 */
	 @Value("${testsession.status.unused}")
	 private String TEST_SESSION_STATUS_UNUSED;
	 
	 /**
	  * TEST_SESSION_STATUS_ITICANCEL
	  */
	 @Value("${testsession.status.iticancel}")
	 private String TEST_SESSION_STATUS_ITICANCEL;
	 
	 /**
	     * The Data Access Object for testSession.
	 */
	 @Autowired
	 private TestSessionDao testSessionDao;
	    
	 /**
	     * Code value for the category type table that represents the test session status type.
	 */
	 @Value("${testsession.status.type}")
	 private String testStatusType;
	 
	 /**
	     * Category service used for finding the category that represents a closed test session.
	 */
	 @Autowired
	 private CategoryService categoryService;
	 
	 @Autowired
	 private StudentsTestsDao studentsTestsDao;
	 
	 @Autowired
	 private ContentAreaService contentAreaService;
	 
	 @Autowired
	 private TestCollectionService testCollectionService;
	 
	 @Autowired
	 private ContentFrameworkService contentFrameworkService;
	 
    /**
     * logger.
     */
    private Logger logger = LoggerFactory.getLogger(ItiTestSessionServiceImpl.class);

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionResourceInfo> getTestResources(long testSessionId) {
		List<ItiTestSessionResourceInfo> records = itiTestSessionHistoryMapper.selectResourceByTestSessionId(testSessionId);
	    return records;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentItemInfoForMC> getStudentItemInfoForMC(Date fromDate, Date toDate) {
		List<StudentItemInfoForMC> records = itiTestSessionHistoryMapper.selectStudentItemInfoForMC(fromDate, toDate);
	    return records;
	} 
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionSensitivityTags> getItiHistorySensitivityTags(long itiTestSessionHistoryId) {
		ItiTestSessionSensitivityTagsExample itiTestSessionSensitivityTagsExample = new ItiTestSessionSensitivityTagsExample();
		ItiTestSessionSensitivityTagsExample.Criteria criteria = itiTestSessionSensitivityTagsExample.createCriteria();
		criteria.andItiTestSessionHistoryIdEqualTo(itiTestSessionHistoryId);
		List<ItiTestSessionSensitivityTags> records = itiTestSessionSensitivityTagsMapper.selectByExample(itiTestSessionSensitivityTagsExample);
	    return records;
	} 
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> selectTestidSensitivityTag(List<Long> testIds) {
		List<ItiTestSessionHistory> records = itiTestSessionHistoryMapper.selectTestidSensitivityTag(testIds);
	    return records;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> filterTestIdsOnSensitivityTags(List<Test> tests, List<Long> sensitivityTags){
		List<Test> filteredTests = new ArrayList<Test>();
		List<Long> testIds = AARTCollectionUtil.getIds(tests);
		if(!testIds.isEmpty()){
			List<ItiTestSessionHistory> records = selectTestidSensitivityTag(testIds);
			for(ItiTestSessionHistory itiTestSessionHistory : records){
				boolean filterCheck = true;
				if(itiTestSessionHistory.getSensitivityTags().length() != 0){
					String[] sensitivityTagsOnTestlet = itiTestSessionHistory.getSensitivityTags().split(",");
					if(sensitivityTagsOnTestlet.length >= 1 ){
						for(String tag : sensitivityTagsOnTestlet){
							if(!sensitivityTags.contains(Long.valueOf(tag))){
								filterCheck = false;
								break;
							}
						}
					}
				}
				if(filterCheck){
					for(Test test : tests){
						if(test.getId().equals(itiTestSessionHistory.getTestId())){
							filteredTests.add(test);
							break;
						}
					}
				}
			}
		}
		return filteredTests;
    }
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SensitivityTag> getSensitivityTags(long contentareaId, String essentialElement, String gradeCourseAbbrName){
		if(isReadingEE(essentialElement, gradeCourseAbbrName)) {
			SensitivityTagExample example = new SensitivityTagExample();
			SensitivityTagExample.Criteria criteria = example.createCriteria();
			criteria.andContentareaIdEqualTo(contentareaId);
			example.setOrderByClause("name");
			return sensitivityTagMapper.selectByExample(example);
		}
		return null;
	}
		
	private boolean isReadingEE(String essentialElement, String gradeCourseAbbrName) {
		return readingEssentialelementsMapper.isReadingEE(essentialElement, gradeCourseAbbrName);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int addItiMClog(ItiMCLog itiMcLog) {
		return ItiMCLogMapper.insertSelective(itiMcLog);
	} 
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int addStudentNodeProbability(StudentNodeProbability studentNodeProbability) {
		int result=0;
		try{
			result = studentNodeProbabilityMapper.insertSelective(studentNodeProbability);
		}catch (DuplicateKeyException e) {
	 		logger.debug("studentNodeProbabilityMapper.duplicate exception. Ignore this as update will be next action.");
	 		result = -1;
		}
		return result;
	} 
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStudentNodeProbability(StudentNodeProbability studentNodeProbability) {
		StudentNodeProbabilityExample example = new StudentNodeProbabilityExample();
		StudentNodeProbabilityExample.Criteria criteria = example.createCriteria();
		criteria.andNodeIdEqualTo(studentNodeProbability.getNodeId());
		criteria.andStudentIdEqualTo(studentNodeProbability.getStudentId());
		int result = studentNodeProbabilityMapper.updateByExampleSelective(studentNodeProbability, example);  
		return result;
	} 
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateSelectiveItiMClog(ItiMCLog itiMCLog, ItiMCLogExample itiMCLogExample) {
		return ItiMCLogMapper.updateByExampleSelective(itiMCLog, itiMCLogExample);
	} 
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> filterTestIdsOnPNP(List<Long> testIds, String pnpAttribute) {
		return itiTestSessionHistoryMapper.selectTestidOnPNP(testIds, pnpAttribute);
	} 
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> processDLMStudentsToTest(String testCollectionName, long testCollectionId, 
	    		long rosterId, long studentEnrlRosterId, String action, String source, String linkageLevel, String[] sensitivityTags, 
	    		long studentId, String levelDesc, String eElement, String claim, String conceptualArea, long essentialelementid){
	    	List<Long> enrollmentRosterIds = new ArrayList<Long>();
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	boolean isValid = true;
	    	String resourceLoc = "";
	    	TestSession testSession = new TestSession();
	    	Category sessionStatus = null;
	    	Student studentDetails = studentService.findById(studentId);
	    	List<TestSession> previousTestSession = testSessionService.selectTestSessionByStudentIdTestCollectionSource(studentId, "ITI", testCollectionId);
	    	String sessionName = "DLM-"+StudentUtil.removeSpecialCharacters(studentDetails.getLegalLastName()) + StudentUtil.removeSpecialCharacters(studentDetails.getLegalFirstName()) + "-" + studentId + "-" + 
	    			StudentUtil.removeSpecialCharacters(testCollectionName);
	    	if(previousTestSession.size() >= 1){
	    		sessionName = sessionName + "_" + (previousTestSession.size() + 1);
	    	}
	        testSession.setName(sessionName);
	        testSession.setTestCollectionId(testCollectionId);
	        testSession.setRosterId(rosterId);
	        if(source.equalsIgnoreCase("iti")){
	        	testSession.setSource(SourceTypeEnum.ITI.getCode());
	        }
	        Category pendingStatus = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_PENDING, TEST_SESSION_STATUS_TYPE);
	        if(action.equalsIgnoreCase("save")){
	        	sessionStatus = pendingStatus;
	        }else{
	        	sessionStatus = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
	        }
	        testSession.setStatus(sessionStatus.getId());
	        enrollmentRosterIds.add(studentEnrlRosterId);
	        Long testId = null;
	        List<Long> sensitivityTagsIds = AARTCollectionUtil.getListAsLongType(sensitivityTags);
	        if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 
	        		&& testCollectionId > 0) {
	        	if(StringUtils.isNotEmpty(checkDuplicateTestsSessionExists(studentId,linkageLevel,rosterId, essentialelementid))){
	        		map.put("duplicateKey", true);
	                isValid = false;
	        	}else{
	        		ItiTestSessionHistory itiTestSessionHistoryRecord = new ItiTestSessionHistory();
	         	    itiTestSessionHistoryRecord.setModifiedDate(new Date());
	         	    itiTestSessionHistoryRecord.setTestCollectionId(testCollectionId);
	         	    itiTestSessionHistoryRecord.setTestCollectionName(testCollectionName);
	         	    itiTestSessionHistoryRecord.setRosterId(rosterId);
	         	    itiTestSessionHistoryRecord.setStudentId(studentId);
	         	    itiTestSessionHistoryRecord.setStudentEnrlRosterId(studentEnrlRosterId);
	         	    itiTestSessionHistoryRecord.setName(sessionName);
	         	    itiTestSessionHistoryRecord.setLinkageLevel(linkageLevel);
	         	    itiTestSessionHistoryRecord.setLevelDescription(levelDesc);
	         	    itiTestSessionHistoryRecord.setEssentialElement(eElement);
	         	    itiTestSessionHistoryRecord.setClaim(claim);
	         	    itiTestSessionHistoryRecord.setConceptualArea(conceptualArea);
	         	    itiTestSessionHistoryRecord.setEssentialElementId(essentialelementid);;
	                if(action.equalsIgnoreCase("save")){
	             	    itiTestSessionHistoryRecord.setSavedDate(new Date());
	             	    itiTestSessionHistoryRecord.setStatus(sessionStatus.getId());
	             	    addOrUpdateSaveHistory(itiTestSessionHistoryRecord, pendingStatus.getId(), sensitivityTagsIds);
	                } else{
	                		try {
                				//Passed the state the student belongs to in order to find the test collection associated with the state OTW
                				isValid = studentsTestsService.createTestSessions(enrollmentRosterIds, testCollectionId, testId, testSession, sensitivityTagsIds, studentDetails.getStateId());
                				if(isValid){
	    			         	    itiTestSessionHistoryRecord.setConfirmDate(new Date());
	    			         	    itiTestSessionHistoryRecord.setStatus(sessionStatus.getId());
	    			         	    itiTestSessionHistoryRecord.setTestSessionId(testSession.getId());
	    			         	    //add/update iti history
	    			         	    addOrUpdateSaveHistory(itiTestSessionHistoryRecord,  pendingStatus.getId(), sensitivityTagsIds);
	    			         	    //get Resources
	    			         	    List<ItiTestSessionResourceInfo> testResourceInfo = getTestResources(testSession.getId());
	    			         	    logger.debug("Created Test Session - " + testSession.getId() + " - length of get pdf - " + testResourceInfo.size());
	    			         	    for(ItiTestSessionResourceInfo resourceInfo : testResourceInfo){
	    			         	    	logger.debug("File Loc = " + resourceInfo.getFileLocation());
	    			         	    	logger.debug("File Type = " + resourceInfo.getFileType());
	    			         	    	if(resourceInfo.getFileType().equalsIgnoreCase("pdf")){
	    			         	    		resourceLoc = MEDIA_PATH + resourceInfo.getFileLocation().replaceFirst("/", "");
	    			         	    		break;
	    			         	    	}
	    			         	    }
	    						}else{
	    							map.put("nocontent", true);
	    						}
	    					} catch (DuplicateTestSessionNameException e) {
	    						map.put("duplicateKey", true);
	    			            isValid = false;
	    					}
	                	
	                }	
	        	}
	        }
	        map.put("valid", isValid);
	        map.put("resource", resourceLoc);
	        logger.trace("Leaving the assignStudentsToTest() method.");
	        return map;
	    }
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> finishPlan(Long studentId, Long contentAreaId, String gradeCourseCode, Long rosterId, Long itiId, boolean assignTestlet) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		ItiTestSessionHistory iti = selectByPrimaryKey(itiId);
		if (iti != null && iti.getStudentId().equals(studentId) && iti.getRosterId().equals(rosterId)) {
			Date now = new Date();
			Category status = null;
			if (assignTestlet) {
				status = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_COMPLETED_WITH_TESTLET, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
				iti.setConfirmDate(now);
				
				List<TestCollection> testCollections = getEligibleTestCollectionsForEE(
						studentId, rosterId, contentAreaId, gradeCourseCode, iti.getEssentialElementId(), iti.getLinkageLevel());
				
				if (CollectionUtils.isNotEmpty(testCollections)) {
					iti.setTestCollectionId(testCollections.get(0).getId());
					iti.setTestCollectionName(testCollections.get(0).getName());
					Map<String,Object> assignMap = assignIAPTestlet(testCollections.get(0).getName(), testCollections.get(0).getId(), rosterId,
							iti.getStudentEnrlRosterId(), iti.getLinkageLevel(), studentId, contentAreaId,
							iti.getLevelDescription(), iti.getEssentialElement(), iti.getClaim(), iti.getConceptualArea(), iti.getEssentialElementId());
					
					if (Boolean.TRUE.equals(assignMap.get("valid"))) {
						iti.setTestSessionId((Long) assignMap.get("testSessionId"));
						ret.putAll(assignMap);
						ret.put("success", true);
					} else {
						// TODO put error here
					}
				} else {
					// TODO put error here
				}
			} else {
				status = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_COMPLETED_WITH_NO_TESTLET, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
			}
			
			iti.setStatus(status.getId());
			iti.setModifiedDate(now);
			iti.setModifiedUser(userDetails.getUser().getId());
			updateByPrimaryKeySelective(iti);
		} else {
			// TODO put error here
		}
		
		return ret;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	/*
	 * This method was basically copied from the above method processDLMStudentsToTest, with minor changes to not do certain things,
	 * like mess with the ititestsessionhistory record, since the caller is the one to do that already
	 */
	public Map<String, Object> assignIAPTestlet(String testCollectionName, long testCollectionId, 
    		long rosterId, long studentEnrlRosterId, String linkageLevel, long studentId,
    		Long contentAreaId, String levelDesc, String eElement, String claim, String conceptualArea, long essentialelementid){
    	List<Long> enrollmentRosterIds = new ArrayList<Long>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	boolean isValid = true;
    	String resourceLoc = "";
    	TestSession testSession = new TestSession();
    	Category sessionStatus = null;
    	Student studentDetails = studentService.findById(studentId);
    	List<TestSession> previousTestSession = testSessionService.selectTestSessionByStudentIdTestCollectionSource(studentId, "ITI", testCollectionId);
    	String sessionName = "DLM-"+StudentUtil.removeSpecialCharacters(studentDetails.getLegalLastName()) + StudentUtil.removeSpecialCharacters(studentDetails.getLegalFirstName()) + "-" + studentId + "-" + 
    			StudentUtil.removeSpecialCharacters(testCollectionName);
    	if(previousTestSession.size() >= 1){
    		sessionName = sessionName + "_" + (previousTestSession.size() + 1);
    	}
        testSession.setName(sessionName);
        testSession.setTestCollectionId(testCollectionId);
        testSession.setRosterId(rosterId);
    	testSession.setSource(SourceTypeEnum.ITI.getCode());
    	
        sessionStatus = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
        testSession.setStatus(sessionStatus.getId());
        
        enrollmentRosterIds.add(studentEnrlRosterId);
        Long testId = null;
        
        List<SensitivityTag> sensitivityTagsForStudent = sensitivityTagMapper.selectByStudentIdAndContentAreaId(studentId, contentAreaId);
        List<Long> sensitivityTagsIds = new ArrayList<Long>(sensitivityTagsForStudent.size());
        for (SensitivityTag tag : sensitivityTagsForStudent) {
        	sensitivityTagsIds.add(tag.getId());
        }
        
        if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 && testCollectionId > 0) {
        	if (StringUtils.isNotEmpty(checkDuplicateTestsSessionExists(studentId,linkageLevel,rosterId, essentialelementid))) {
        		map.put("duplicateKey", true);
        		isValid = false;
        	} else {
        		try {
        			//Passed the state the student belongs to in order to find the test collection associated with the state OTW
        			isValid = studentsTestsService.createTestSessions(enrollmentRosterIds, testCollectionId, testId, testSession, sensitivityTagsIds, studentDetails.getStateId());
        			if (isValid) {
        				map.put("testSessionId", testSession.getId());
        				
        				//get Resources
        				List<ItiTestSessionResourceInfo> testResourceInfo = getTestResources(testSession.getId());
        				logger.debug("Created Test Session - " + testSession.getId() + " - length of get pdf - " + testResourceInfo.size());
        				for (ItiTestSessionResourceInfo resourceInfo : testResourceInfo) {
        					logger.debug("File Loc = " + resourceInfo.getFileLocation());
        					logger.debug("File Type = " + resourceInfo.getFileType());
        					if(resourceInfo.getFileType().equalsIgnoreCase("pdf")){
        						resourceLoc = MEDIA_PATH + resourceInfo.getFileLocation().replaceFirst("/", "");
        						break;
        					}
        				}
        			} else {
        				map.put("nocontent", true);
        			}
        		} catch (DuplicateTestSessionNameException e) {
        			map.put("duplicateKey", true);
        			isValid = false;
        		}
        	}
        }
        map.put("valid", isValid);
        map.put("resource", resourceLoc);
        logger.trace("Leaving the assignStudentsToTest() method.");
        return map;
    }
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addItiTestSessionHistory(Long testSessionId, String essentialElement, String linkageLevel, String levelDesc) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
	    ItiTestSessionHistory itiTestSessionHistoryRecord = new ItiTestSessionHistory();
	    itiTestSessionHistoryRecord.setLinkageLevel(linkageLevel);
	    itiTestSessionHistoryRecord.setLevelDescription(levelDesc);
	    itiTestSessionHistoryRecord.setEssentialElement(essentialElement);
	    itiTestSessionHistoryRecord.setCreatedUser(user.getId());
	    itiTestSessionHistoryRecord.setSavedDate(new Date());
	    itiTestSessionHistoryRecord.setModifiedDate(new Date());
	    itiTestSessionHistoryRecord.setCreatedDate(new Date());
	    int result = itiTestSessionHistoryMapper.insert(itiTestSessionHistoryRecord);
		return (result >= 1);
	}
     
     
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String checkDuplicateTestsSessionExists(long studentId, String level, long rosterId, long essentialElementId) {
		return  (itiTestSessionHistoryMapper.selectITITestSessionForALevelOtherThanComplete(studentId, rosterId, level, essentialElementId, SourceTypeEnum.ITI.getCode()));
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addOrUpdateSaveHistory(ItiTestSessionHistory record, long pendingStatus, List<Long> sensitivityTags) {
		long itiTestSessionHistoryId = 0;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		ItiTestSessionHistoryExample example = new ItiTestSessionHistoryExample();
		ItiTestSessionHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andStudentIdEqualTo(record.getStudentId());
		criteria.andRosterIdEqualTo(record.getRosterId());
		criteria.andStatusEqualTo(pendingStatus);
		criteria.andActiveFlagEqualTo(true);
		criteria.andTestCollectionIdEqualTo(record.getTestCollectionId());
		criteria.andEssentialElementEqualTo(record.getEssentialElement());		
		record.setModifiedDate(new Date());
		record.setModifiedUser(user.getId());
		record.setActiveFlag(true);
		List<ItiTestSessionHistory> historyDetails =  itiTestSessionHistoryMapper.selectByExample(example);
		if (historyDetails.size() > 0){
			itiTestSessionHistoryId = historyDetails.get(0).getId();
			record.setId(itiTestSessionHistoryId);
		    itiTestSessionHistoryMapper.updateByPrimaryKeySelective(record);
		}else{
			record.setCreatedDate(new Date());
			record.setCreatedUser(user.getId());
			itiTestSessionHistoryMapper.insertSelective(record);
			itiTestSessionHistoryId = record.getId();
		}
		if(!sensitivityTags.isEmpty() && itiTestSessionHistoryId > 0)
			addOrUpdateItiSensitivityTags(itiTestSessionHistoryId, sensitivityTags );

		return itiTestSessionHistoryId > 0;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ItiTestSessionHistory selectByPrimaryKey(Long id) {
		return itiTestSessionHistoryMapper.selectByPrimaryKey(id);
	}
     
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean confirmHistory(ItiTestSessionHistory record) {
		ItiTestSessionHistoryExample example = new ItiTestSessionHistoryExample();
		ItiTestSessionHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andStudentIdEqualTo(record.getStudentId());
		criteria.andRosterIdEqualTo(record.getRosterId());
		criteria.andTestCollectionIdEqualTo(record.getTestCollectionId());
	    long result = itiTestSessionHistoryMapper.updateByExampleSelective(record, example);
		if(result == 0){
			result = itiTestSessionHistoryMapper.insertSelective(record);
		}
	    
	    return (result >= 1);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getStudentHistory(long studentId, long rosterId) {
		List<ItiTestSessionHistory> records = itiTestSessionHistoryMapper.selectByStudentIdRosterIdWithStatus(studentId, rosterId);
	    return records;
	}
	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int addOrUpdateItiSensitivityTags(long itiTestSessionHistoryId, List<Long> sensitivityTags) {
		ItiTestSessionSensitivityTagsExample example = new ItiTestSessionSensitivityTagsExample();
		ItiTestSessionSensitivityTagsExample.Criteria criteria = example.createCriteria();
		criteria.andItiTestSessionHistoryIdEqualTo(itiTestSessionHistoryId);
		itiTestSessionSensitivityTagsMapper.deleteByExample(example);
		return itiTestSessionSensitivityTagsMapper.insertList(itiTestSessionHistoryId, sensitivityTags);
	} 
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ItiTestSessionHistory selectHistoryTagsByItiSessionHistoryId(long itiTestSessionHistoryId) {
		ItiTestSessionHistory records = itiTestSessionHistoryMapper.selectHistoryTagsByItiSessionHistoryId(itiTestSessionHistoryId);
	    return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiStudentReport> selectByStudentIdAndUnUsedStatus(Long studentId) {
		return itiTestSessionHistoryMapper.selectByStudentIdAndUnUsedStatus(studentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiStudentReport> selectByStudentIdAndUnUsedStatusAndSubject(Long studentId, Long subjectId, int currentSchoolYear, Long testCycleID) {
		return itiTestSessionHistoryMapper.selectByStudentIdAndUnUsedStatusAndSubject(studentId, subjectId, currentSchoolYear, testCycleID);
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get needed data to build the Roster Report for DLM organizations.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiRosterReportEE> getITIDLMEEDetailsByRosterIdAndStudents(Long userSelectedOrganizationId, 
			Long rosterId, List<Long> studentIds, Long contentAreaId, int currentSchoolYear) {
		
		return itiTestSessionHistoryMapper.selectITIDLMEEDetailsByRosterIdAndStudents(userSelectedOrganizationId, 
				rosterId, studentIds, contentAreaId, currentSchoolYear);
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int inactivatePendingITISession(long itiTestSessionHistoryId, long userId) {
		ItiTestSessionHistory record = new ItiTestSessionHistory();
		record.setModifiedDate(new Date());
		record.setModifiedUser(userId);
		record.setActiveFlag(false);
		record.setId(itiTestSessionHistoryId);
	    return itiTestSessionHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int cancelITIHistoryAndStudentTests(long itiTestSessionHistoryId,
			long testSessionId, long studentId) {
		Category itiCancel = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_ITICANCEL, TEST_SESSION_STATUS_TYPE);		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StudentsTests studentsTests = new StudentsTests();
		studentsTests.setActiveFlag(false);
		studentsTests.setStatus(itiCancel.getId());
		studentsTests.setModifiedDate(new Date());
		studentsTests.setModifiedUser(userDetails.getUser().getId());		
		StudentsTestsExample example = new StudentsTestsExample();
        StudentsTestsExample.Criteria criteria = example.createCriteria();
        criteria.andTestSessionIdEqualTo(testSessionId);
        criteria.andStudentIdEqualTo(studentId);
        int numOfStuTestsUpdated = studentsTestsDao.updateByExampleSelective(studentsTests, example);
        logger.debug(numOfStuTestsUpdated + " got updated with status ITICancel for the studentId - " + studentId + " and testsessionId - " + testSessionId + " combination" );        
		ItiTestSessionHistory record = new ItiTestSessionHistory();
		record.setModifiedDate(new Date());
		record.setModifiedUser(userDetails.getUser().getId());
		record.setActiveFlag(false);
		record.setId(itiTestSessionHistoryId);
		record.setStatus(itiCancel.getId());
		return itiTestSessionHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void unenrollITIPlansByStudentAndTestSessionId(Long studentId, String unEnrollCategroyCode, 
			Long testSessionId,Long modifiedUserId) {		
		Category unEnrollStatus = categoryService.selectByCategoryCodeAndType(unEnrollCategroyCode, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
		itiTestSessionHistoryMapper.unEnrollITIPlansByStudentAndTestsessionId(unEnrollStatus.getId(), modifiedUserId, studentId,testSessionId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void unEnrollPendingITIsByEnrollmentId(Long enrollmentId, String unEnrollStatusCode) {
		Category unEnrollStatus = categoryService.selectByCategoryCodeAndType(unEnrollStatusCode, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		itiTestSessionHistoryMapper.unEnrollPendingITIsByEnrollmentId(unEnrollStatus.getId(), enrollmentId, userDetails.getUser().getId());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void unEnrollPendingITIsByEnrlAndRosterId(Long enrollmentId,
			Long rosterId, String rosterUnEnrollCode, Long modifiedUserId) {
		Category rosterUnEnrollStatus = categoryService.selectByCategoryCodeAndType(rosterUnEnrollCode, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
		itiTestSessionHistoryMapper.unEnrollPendingITIsByEnrlAndRosterId(rosterUnEnrollStatus.getId(), enrollmentId, rosterId, modifiedUserId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferITIsToNewRosterByEnrlAndOldRosterId(List<ItiTestSessionHistory> itiPlansOfTheOldRosterList, Long enrollmentId, Long newRosterId, Long modifiedUserId){
		for(ItiTestSessionHistory itiPlansOfTheOldRoster : itiPlansOfTheOldRosterList){
			itiTestSessionHistoryMapper.transferITIsToNewRosterByEnrlAndOldRosterId(itiPlansOfTheOldRoster.getId(), enrollmentId, newRosterId, modifiedUserId);
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getUnenrolledITIsByEnrlAndRosterId(Long enrollmentId, Long rosterId) {
		return itiTestSessionHistoryMapper.getUnenrolledITIsByEnrlAndRosterId(enrollmentId, rosterId);
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getPendingITIsByEnrlAndRosterId(Long enrollmentId, Long rosterId) {
		return itiTestSessionHistoryMapper.getPendingITIsByEnrlAndRosterId(enrollmentId, rosterId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getITIPlansExceptPendingUsingEnrlAndRosterId(Long enrollmentId, Long rosterId){
		return itiTestSessionHistoryMapper.getITIPlansExceptPendingUsingEnrlAndRosterId(enrollmentId, rosterId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findInactivatedITIPlansByStudentAndTestSession(String inactivationType, Long studentId, List<Long> inactivatedTestSessionIds) {
		return itiTestSessionHistoryMapper.findInactivatedITIPlansByStudentAndTestSession(inactivationType, studentId, inactivatedTestSessionIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findInactivatedPendingITIPlansByStudent(String inactivationType, Long studentId, Long gradeCourseId) {
		return itiTestSessionHistoryMapper.findInactivatedPendingITIPlansByStudent(inactivationType, studentId, gradeCourseId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findITIPlansByStudentAndTestSessionForDeactivation(Long studentId, List<Long> testSessionIds) {
		return itiTestSessionHistoryMapper.findITIPlansByStudentAndTestSessionForDeactivation(studentId, testSessionIds);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findPendingITIPlansByStudentForDeactivation(Long studentId, Long oldGradeLevel) {
		return itiTestSessionHistoryMapper.findPendingITIPlansByStudentForDeactivation(studentId, oldGradeLevel);
	}
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateWithStatus(List<Long> itiPlanIds, String newStatusPrefix, Long modifiedUserId) {
    	for (Long itiPlanId : itiPlanIds) {
    		itiTestSessionHistoryMapper.deactivateByPrimaryKeyWithStatus(itiPlanId, newStatusPrefix, modifiedUserId);
    	}
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateForGradeChange(List<Long> itiPlanIds, Long modifiedUserId) {
    	deactivateWithStatus(itiPlanIds, TestSessionServiceImpl.GRADE_CHANGE_INACTIVATED_PREFIX, modifiedUserId);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void reactivateForGradeChange(List<Long> itiPlanIdsForDeactivation, Long modifiedUserId) {
    	for (Long itiPlanIdForDeactivation : itiPlanIdsForDeactivation) {
    		itiTestSessionHistoryMapper.reactivateByPrimaryKeyForGradeChange(itiPlanIdForDeactivation, modifiedUserId);
    	}
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public ItiTestSessionHistory selectITISessionHistoryByStudentIdAndTestSessionId(Long studentId, Long testSessionId) {
    	return itiTestSessionHistoryMapper.selectITISessionHistoryByStudentIdAndTestSessionId(studentId, testSessionId);
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ItiTestSessionHistory getMostRecentlyCompletedNonWringITITestSession(Long studentId, Long contentAreaId,
			String gradeCourseAbbrName, Long currentSchoolYear) {
		return itiTestSessionHistoryMapper.getMostRecentlyCompletedNonWringITITestSession(studentId, contentAreaId, gradeCourseAbbrName, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getForIAP(Long studentId, Long rosterId, Long enrollmentsRostersId, Long operationalTestWindowId) {
		return itiTestSessionHistoryMapper.getForIAP(studentId, rosterId, enrollmentsRostersId, operationalTestWindowId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionHistory> getForIAPByContentFrameworkDetailIdAndLinkageLevel(
			Long studentId, Long rosterId, Long enrollmentsRostersId, Long operationalTestWindowId, Long contentFrameworkDetailId, String linkageLevel) {
		return itiTestSessionHistoryMapper.getForIAPByContentFrameworkDetailIdAndLinkageLevel(
				studentId, rosterId, enrollmentsRostersId, operationalTestWindowId, contentFrameworkDetailId, linkageLevel);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ItiTestSessionHistory insertITISelective(Long studentId, Long enrollmentsRostersId, Long rosterId, IAPContentFramework iapCF,
			String linkageLevel, String linkageLevelDesc, Long otwId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
		Student student = studentService.findById(studentId);
		
		Category status = categoryService.selectByCategoryCodeAndType("STARTED", "IAP_STATUS");
		
		ItiTestSessionHistory iti = new ItiTestSessionHistory();
		String essentialElementCodewithDesc = iapCF.getEE().getContentCode() +" - "+ iapCF.getEE().getDescription();
		iti.setStudentId(student.getId());
		iti.setRosterId(rosterId);
		iti.setStudentEnrlRosterId(enrollmentsRostersId);
		iti.setName("DLM-" +
			StudentUtil.removeSpecialCharacters(student.getLegalLastName()) +
			StudentUtil.removeSpecialCharacters(student.getLegalFirstName()) +
			"-" + studentId + "-" +
			iapCF.getEE().getContentCode() + "-" +
			sdf.format(now));
		iti.setStatus(status.getId());
		iti.setSavedDate(now);
		iti.setEssentialElementId(iapCF.getEE().getId());
		iti.setEssentialElement(essentialElementCodewithDesc);
		iti.setLinkageLevel(linkageLevel);
		iti.setLevelDescription(linkageLevelDesc);
		iti.setClaim(iapCF.getClaimDescription());
		iti.setConceptualArea(iapCF.getConceptualAreaDescription());
		iti.setOperationalTestWindowId(otwId);
		iti.setCreatedUser(userDetails.getUser().getId());
		itiTestSessionHistoryMapper.insertSelective(iti);
		return iti;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelective(ItiTestSessionHistory record) {
		return itiTestSessionHistoryMapper.updateByPrimaryKey(record);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollection> getEligibleTestCollectionsForEE(Long studentId, Long rosterId, Long contentAreaId, String gradeCourseCode, Long contentFrameworkDetailId, String linkageLevel) {
		ContentArea contentArea = contentAreaService.selectByPrimaryKey(contentAreaId);
		ContentFrameworkDetail ee = contentFrameworkService.getContentFrameworkDetailById(contentFrameworkDetailId);
    	Float linkageLevelLowerBound = null;
    	Float linkageLevelUpperBound = null;
    	Long complexityBandId = null;
    	if (ComplexityBandEnum.getByLinkageLevel(linkageLevel, contentArea.getAbbreviatedName()) != null) {
    		complexityBandId = ComplexityBandEnum.getByLinkageLevel(linkageLevel, contentArea.getAbbreviatedName()).getBandId();
    	}
    	if (complexityBandId != null) {
    		ComplexityBandDao complexityBandDTO = testCollectionService.getComplexityBandById(complexityBandId);
    		if(complexityBandDTO != null) {
    			linkageLevelLowerBound = (float) complexityBandDTO.getMinRange();
    			linkageLevelUpperBound = (float) complexityBandDTO.getMaxRange();
    		}
    	}    	
		List<TestCollection> testCollections = null;
		String existingPlan = checkDuplicateTestsSessionExists(studentId, linkageLevel, rosterId, contentFrameworkDetailId);
		if (StringUtils.isEmpty(existingPlan)) {
			List<String> itemAttributeList = new ArrayList<String>();
			itemAttributeList.add("Braille");
			itemAttributeList.add("Spoken");
			List<StudentProfileItemAttributeDTO> studentProfileAll = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
			StudentProfileItemAttributeDTO studentProfileBraille = null;
			StudentProfileItemAttributeDTO studentProfileSpoken = null;
			for (StudentProfileItemAttributeDTO profile : studentProfileAll) {
				if(profile.getAttributeName().equals("Braille")){
					studentProfileBraille = profile;
				}
				if(profile.getAttributeName().equals("Spoken")){
					studentProfileSpoken = profile;
				}
			}
 			List<String> eElementList = Arrays.asList(ee.getContentCode());
			if (studentProfileBraille != null && StringUtils.equalsIgnoreCase(studentProfileBraille.getSelectedValue(), "true")) {
				// get braille collections
		    	testCollections = testCollectionService.findMatchingTestCollectionsIti(
		    			linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, 1, null, eElementList, "braille");
			}
			if (CollectionUtils.isEmpty(testCollections)) {
				// get visual impairment collections
				itemAttributeList.clear();
				itemAttributeList.add("visualImpairment");
				List<StudentProfileItemAttributeDTO> studentProfileVI = studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList);
				if (CollectionUtils.isNotEmpty(studentProfileVI) && StringUtils.equalsIgnoreCase(studentProfileVI.get(0).getSelectedValue(), "true")) {
					testCollections = testCollectionService.findMatchingTestCollectionsIti(
							linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, 1, null, eElementList, "visual_impairment");
				}
			}
			if (CollectionUtils.isEmpty(testCollections) && studentProfileSpoken != null && StringUtils.equalsIgnoreCase(studentProfileSpoken.getSelectedValue(), "true")) {
				// get spoken collections
				testCollections = testCollectionService.findMatchingTestCollectionsIti(
						linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, 1, null, eElementList, "read_aloud");
			}
			if (CollectionUtils.isEmpty(testCollections)) {
				// get general collections
				testCollections = testCollectionService.findMatchingTestCollectionsIti(
						linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, 1, null, eElementList, null);			
			}
		}
		return testCollections;
    }
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int endTestletForITI(ItiTestSessionHistory iti) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		int count = itiTestSessionHistoryMapper.deactivateStudentsTestsByITIId(iti.getId(), userDetails.getUser().getId());
		
		if (count != 0) {
			Category status = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_CANCELED, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
			iti.setModifiedDate(new Date());
			iti.setModifiedUser(userDetails.getUser().getId());
			iti.setStatus(status.getId());
			count *= updateByPrimaryKeySelective(iti);
		}
		
		return count;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ItiTestSessionResourceInfo> getTestResourceByTestSessionHistoryId(ItiTestSessionHistory iti) {
		List<ItiTestSessionResourceInfo> records = new ArrayList<>();
		if(iti!=null) {
			records= itiTestSessionHistoryMapper.selectResourceByTestSessionHistoryId(iti.getId());
		}	
	    return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTestResourceInfo> getBrailleResourceByTestSessionHistoryId(ItiTestSessionHistory iti) {
		List<StudentTestResourceInfo> records = new ArrayList<>();
		if(iti!=null) {
			records= itiTestSessionHistoryMapper.getBrailleResourceByTestSessionHistoryId(iti.getId());
		}	
	    return records;
	}
}
