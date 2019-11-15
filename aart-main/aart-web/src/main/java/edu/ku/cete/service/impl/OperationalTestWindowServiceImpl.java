package edu.ku.cete.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.OperationalTestWindowMultiAssignDetail;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesKey;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.DailyAccessCodeMapper;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.studentsession.TestCollectionsSessionRulesService;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;
import edu.ku.cete.web.AssessmentProgramTCDTO;
import edu.ku.cete.web.IAPContentFramework;

/**
 * @author vittaly
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OperationalTestWindowServiceImpl implements OperationalTestWindowService {
	private Logger logger = LoggerFactory.getLogger(OperationalTestWindowServiceImpl.class);
    /**
     * operationalTestWindowDao
     */
    @Autowired
    private OperationalTestWindowDao operationalTestWindowDao;
    
    /**
     * sessionRulesConfiguration
     */
    @Autowired
    private SessionRulesConfiguration sessionRulesConfiguration;
    
    /**
     * testCollectionsSessionRulesService
     */
    @Autowired
    private TestCollectionsSessionRulesService testCollectionsSessionRulesService;
    
    /**
     * testCollectionService.
     */
    @Autowired
    private TestCollectionService testCollectionService;      
    
    /**
     * testCollectionsSessionRulesDao.
     */
    @Autowired
    private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
    
    @Autowired
    private DailyAccessCodeMapper dacDao;
    
    /**
     * studentSessionRuleConverter.
     */
    @Autowired
    private StudentSessionRuleConverter studentSessionRuleConverter;
    
    @Autowired
    private ContentFrameworkDetailDao cfDao;
    
    @Value("${DoNotRandomize}")
    private String doNotRandomize;    
    
	/**
	 * @param operationalTestWindow
	 * @return int
	 */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int addOrUpdateOperTestWindowData(OperationalTestWindow operationalTestWindow) {
    	logger.debug("Entering the addOrUpdateOperTestWindowData() method");
		
    	Date now = new Date();
		operationalTestWindow.setModifiedDate(now);
		int result = 0;
		Long Id = operationalTestWindow.getId();
		//List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowDao.selectTestCollectionById( operationalTestWindow.getTestCollectionId());
		//There will always be only one window set for a particular testcollection.
		//if(CollectionUtils.isNotEmpty(operationalTestWindowList) && operationalTestWindowList.size() > 0) {
		if(Id != null && Id != 0){
			//operationalTestWindow.setId(operationalTestWindowList.get(0).getId());
			result = operationalTestWindowDao.updateOperTestWindowByPrimaryKey(operationalTestWindow);
			updateOperTestWindowTestCollectionData(operationalTestWindow);
			updateOperTestWindowTestStateData(operationalTestWindow);
		} else {
			result = operationalTestWindowDao.addOperTestWindowData(operationalTestWindow);
			addOperTestWindowTestCollectionData(operationalTestWindow);
			addOperTestWindowStateData(operationalTestWindow);
		}
		
	 //   Long[]  ids = operationalTestWindow.getTestCollectionIds();
		insertTestCollectionsSessionRulesFlags(operationalTestWindow,operationalTestWindow.getId());
		
		logger.debug("Leaving the addOrUpdateOperTestWindowData() method");		
		return result;
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateOperTestWindowState(OperationalTestWindow operationalTestWindow) {
    	logger.debug("Entering the addOrUpdateOperTestWindowData() method");
		
    	Date now = new Date();
		operationalTestWindow.setModifiedDate(now);
		int result = 0;
		Long Id = operationalTestWindow.getId();
		//List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowDao.selectTestCollectionById( operationalTestWindow.getTestCollectionId());
		//There will always be only one window set for a particular testcollection.
		//if(CollectionUtils.isNotEmpty(operationalTestWindowList) && operationalTestWindowList.size() > 0) {
		if(Id != null && Id != 0){
			//operationalTestWindow.setId(operationalTestWindowList.get(0).getId());
			result = operationalTestWindowDao.suspendOperTestWindowByPrimaryKey(operationalTestWindow);
			//updateOperTestWindowTestCollectionData(operationalTestWindow);
			//updateOperTestWindowTestStateData(operationalTestWindow);
		
		}
		
	 //   Long[]  ids = operationalTestWindow.getTestCollectionIds();
		//insertTestCollectionsSessionRulesFlags(operationalTestWindow,operationalTestWindow.getId());
		
		logger.debug("Leaving the addOrUpdateOperTestWindowData() method");		
		return result;
	}
   
    public List<OperationalTestWindowMultiAssignDetail> selectOperationalTestWindowMultiAssignDetail(Long id){
		List<OperationalTestWindowMultiAssignDetail> selectOperationalTestWindowMultiAssignDetail = operationalTestWindowDao.selectOperationalTestWindowMultiAssignDetailList(id);
		
		return selectOperationalTestWindowMultiAssignDetail;
    	
    }
    
    @Override
    public OperationalTestWindowMultiAssignDetail getOTWMultiAssignDetail( Long operationalTestWindowId,  Long contentAreaId) {
		OperationalTestWindowMultiAssignDetail detail = operationalTestWindowDao.selectOTWMultiAssignDetail(operationalTestWindowId, contentAreaId);
		
		return detail;
    }
   
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateOperationalTestWindowMultiAssignDetail(List<OperationalTestWindowMultiAssignDetail> operationalTestWindowMultiAssignDetailList){
    	for (OperationalTestWindowMultiAssignDetail operationalTestWindowMultiAssignDetail : operationalTestWindowMultiAssignDetailList) {
			operationalTestWindowDao.updateOperationalTestWindowMultiAssignDetail(operationalTestWindowMultiAssignDetail);
		}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void deleteOperationalTestWindowMultiAssignDetail(Long windowId){
    	operationalTestWindowDao.deleteOperationalTestWindowMultiAssignDetail(windowId);
    }
    
   @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT) 
   public void addOperationalTestWindowMultiAssignDetail(List<OperationalTestWindowMultiAssignDetail> operationalTestWindowMultiAssignDetailList){
		for (OperationalTestWindowMultiAssignDetail operationalTestWindowMultiAssignDetail : operationalTestWindowMultiAssignDetailList) {
			operationalTestWindowDao.addOperationalTestWindowMultiAssignDetail(operationalTestWindowMultiAssignDetail);
		}
   }
   
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
   private void updateOperTestWindowTestStateData(
			OperationalTestWindow operationalTestWindow) {
    	
    	operationalTestWindowDao.deleteOperTestWindowStateData(operationalTestWindow.getId());
    	logger.debug("record removed deleteOperTestWindowStateData() method");    	
    	addOperTestWindowStateData(operationalTestWindow);
    	logger.debug("record removed addOperTestWindowData() method");
    }

	/**
	 * @param operationalTestWindow
	 */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addOperTestWindowTestCollectionData(
			OperationalTestWindow operationalTestWindow) {
    	Long[] collectionId = operationalTestWindow.getTestCollectionIds();
    	for(int i=0;i<collectionId.length;i++){
    		operationalTestWindow.setTestCollectionId(collectionId[i]);
    		operationalTestWindowDao.addOperTestWindowTestCollectionData(operationalTestWindow);
    	}

	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addOperTestWindowStateData(
			OperationalTestWindow operationalTestWindow) {
    	Long[] stateIds = operationalTestWindow.getMultipleStateIds();
    	if(stateIds !=null){
    	for(int i=0;i<stateIds.length;i++){
    		operationalTestWindow.setMultipleStateId(stateIds[i]);
    		operationalTestWindowDao.addOperTestWindowStateData(operationalTestWindow);
    		}
    	}
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
 	private void updateOperTestWindowTestCollectionData(
 			OperationalTestWindow operationalTestWindow) {
     	/*Long[] collectionId = operationalTestWindow.getTestCollectionIds();
     	for(int i=0;i<collectionId.length;i++){
     		operationalTestWindow.setTestCollectionId(collectionId[i]);
     		List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowDao.selectTestCollectionById(operationalTestWindow.getTestCollectionId());
     		
     		for (Object value : operationalTestWindowList) {
     		}
     		
     		operationalTestWindowDao.addOperTestWindowTestCollectionData(operationalTestWindow);
     	//	operationalTestWindowDao.updateOperTestWindowByPrimaryKey(operationalTestWindow);
}*/     	
    	Long testWindowId = operationalTestWindow.getId();
    	
    	operationalTestWindowDao.deleteBYWindowId(testWindowId);
    	
    	Long[] collectionId = operationalTestWindow.getTestCollectionIds();    	
    	List<Long> list = Arrays.asList(collectionId);
        Set<Long> set = new HashSet<Long>(list);        
        Long[] result = new Long[set.size()];
        set.toArray(result);    	
    	
    	for(int i=0;i<result.length;i++){
    		operationalTestWindow.setTestCollectionId(result[i]);
    		operationalTestWindowDao.addOperTestWindowTestCollectionData(operationalTestWindow);
    	}

    	
 	}
	
	/**
	 * @param testCollectionId
	 * @return OperationalTestWindow
	 */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OperationalTestWindow selectTestCollectionById(Long testCollectionId) {
    	logger.debug("Entering the selectTestCollectionById() method");
    	
		OperationalTestWindow operationalTestWindow = new OperationalTestWindow();	
		TestCollection testCollection = null;
		
        List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowDao.selectTestCollectionById(testCollectionId);		
        
        //Get the student testCollectionsSessionRules for a specific testCollectionId
        StudentSessionRule studentSessionRule = getStudentSessionRule(testCollectionId);      
		
        if(operationalTestWindowList != null && operationalTestWindowList.size() > 0) {
			operationalTestWindow = operationalTestWindowList.get(0);

			//Set the ticketingFlag based on testcollectionssessionrules table data for a specific testCollection	    	
            if(studentSessionRule.isTicketedAtSection()) {
                operationalTestWindow.setTicketingFlag(true);
            } else {
                operationalTestWindow.setTicketingFlag(false);
            }
		}
		
        if(studentSessionRule.isSystemDefinedEnrollment()) {
            operationalTestWindow.setManagedBySystemFlag(true);
            // Get the testCollection for randomizationtype
            testCollection = testCollectionService.selectByPrimaryKey(testCollectionId);
            if(doNotRandomize.equals(testCollection.getRandomizationType())) {
                operationalTestWindow.setRadomizationAtLoginFlag(true);
            } else {
                operationalTestWindow.setRadomizationAtLoginFlag(false);
            }            
        } else {
            operationalTestWindow.setManagedBySystemFlag(false);
        }		

        if(studentSessionRule.isNotRequiredToCompleteTest()) {
        	operationalTestWindow.setRequiredToCompleteTest(false);
        } else {
        	operationalTestWindow.setRequiredToCompleteTest(true);
        }
        
		logger.debug("Leaving the selectTestCollectionById() method");
		return operationalTestWindow;
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OperationalTestWindow> selectTestViewWindowById(Long testWindowId) {
		OperationalTestWindow operationalTestWindow = new OperationalTestWindow();
        List<OperationalTestWindow> operationalTestWindowList = operationalTestWindowDao.selectTestViewWindowById(testWindowId);		
        /*Long testCollectionId = operationalTestWindowList.get(0).getTestCollectionId();
        
        //Get the student testCollectionsSessionRules for a specific testCollectionId
        StudentSessionRule studentSessionRule = getStudentSessionRule(testCollectionId);   */   
		
        if(operationalTestWindowList != null && operationalTestWindowList.size() > 0) {
			operationalTestWindow = operationalTestWindowList.get(0);
			operationalTestWindow.getTestCollectionId();
			//Set the ticketingFlag based on testcollectionssessionrules table data for a specific testCollection	    	
           /* if(studentSessionRule.isTicketedAtSection()) {
                operationalTestWindow.setTicketingFlag(true);
            } else {
                operationalTestWindow.setTicketingFlag(false);
            }*/
		}
		
      /*  if(studentSessionRule.isSystemDefinedEnrollment()) {
            operationalTestWindow.setManagedBySystemFlag(true);
            // Get the testCollection for randomizationtype
            testCollection = testCollectionService.selectByPrimaryKey(testCollectionId);
            if(doNotRandomize.equals(testCollection.getRandomizationType())) {
                operationalTestWindow.setRadomizationAtLoginFlag(true);
            } else {
                operationalTestWindow.setRadomizationAtLoginFlag(false);
            }            
        } else {
            operationalTestWindow.setManagedBySystemFlag(false);
        }		

        if(studentSessionRule.isNotRequiredToCompleteTest()) {
        	operationalTestWindow.setRequiredToCompleteTest(false);
        } else {
        	operationalTestWindow.setRequiredToCompleteTest(true);
        }*/
		logger.debug("Leaving the selectTestCollectionById() method");
		return operationalTestWindowDao.selectTestViewWindowById(testWindowId);
	}

	  
    
    
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void insertTestCollectionsSessionRulesTest(OperationalTestWindow operationalTestWindow, Long testCollectionId) {
    	logger.debug("Entering the insertTestCollectionsSessionRules() method");
    	
    	//Refactored
    	operationalTestWindow.setTestCollectionId(testCollectionId);
    
    	
        List<TestCollectionsSessionRules> testCollectionsSessionRulesList
        	= testCollectionsSessionRulesDao.selectByTestCollection(operationalTestWindow.getTestCollectionId());
        
        StudentSessionRule existingStudentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(
				testCollectionsSessionRulesList);
    	
		TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
		testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
		testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSectionTicketedCategory().getId());
		
    	//If no record exists for this testCollectionId and ticketingFlag is set to "On", 
    	//then insert a row into the testcollectionssessionrules table 
    	if(!existingStudentSessionRule.isTicketedAtSection() && operationalTestWindow.getTicketingFlag()) {
    		testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;

    	} else if(existingStudentSessionRule.isTicketedAtSection() && !operationalTestWindow.getTicketingFlag()) {
    		//TODO Since its relationship table,is that ok to delete the records? 
        	//otherwise if the records do exists and
        	//ticketingFlag is set to "Off" then delete the existing row from testcollectionssessionrules table.
    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
    	}    

		testCollectionsSessionRules  = new TestCollectionsSessionRules();
		testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
		testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getNotRequiredToCompleteCategory().getId());
    	if(!existingStudentSessionRule.isNotRequiredToCompleteTest() && !operationalTestWindow.isRequiredToCompleteTest()) {
        	//If no record exists for this testCollectionId and testExitFlag is set to required to not complete test, 
        	//then insert a row into the testcollectionssessionrules table 
    		
    		TestCollectionsSessionRulesKey testCollectionsSessionRulesKey = new TestCollectionsSessionRulesKey();
    		
    		testCollectionsSessionRulesKey.setTestCollectionId(testCollectionsSessionRules.getTestCollectionId());
    		testCollectionsSessionRulesKey.setSessionRuleId(testCollectionsSessionRules.getSessionRuleId());
    		
    		TestCollectionsSessionRules  existingTestCollectionsSessionRules  = testCollectionsSessionRulesService.selectByPrimaryKey(testCollectionsSessionRulesKey);
    		if(existingTestCollectionsSessionRules == null) {
    			testCollectionsSessionRules.setAuditColumnProperties();
    			testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		} else {
    			testCollectionsSessionRules.setAuditColumnPropertiesForUpdate();
    			testCollectionsSessionRulesService.updateByPrimaryKeySelective(testCollectionsSessionRules);
    		}
    	} else if(existingStudentSessionRule.isNotRequiredToCompleteTest()
    			&& operationalTestWindow.isRequiredToCompleteTest()) {
        	//otherwise if the records do exists and
        	//testExitFlag is set to "Off" then delete the existing row from testcollectionssessionrules table.
    		//TODO Since its relationship table,is that ok to delete the records? 
    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    	} else if (operationalTestWindow.getTicketingFlag()) {
    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    	}
    	
    	logger.debug("Leaving the insertTestCollectionsSessionRules() method");
    }
    
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void insertTestCollectionsSessionRulesFlags(OperationalTestWindow operationalTestWindow, long testCollectionId) {
    	logger.debug("Entering the insertTestCollectionsSessionRulesFlags() method");
    	 
    	operationalTestWindow.setTestCollectionId(testCollectionId);
    	List<TestCollectionsSessionRules> testCollectionsSessionRulesList
     		= testCollectionsSessionRulesDao.selectByOperationalTestWindowId(operationalTestWindow.getTestCollectionId());

   	 
   	 	
   	 	if(testCollectionsSessionRulesList == null || testCollectionsSessionRulesList.size()==0) {
   	 		insertInOperationalTestWindowSessionRule(operationalTestWindow);
    	}
    	else {
    		testCollectionsSessionRulesDao.deleteByOperationalWindowId(testCollectionId);
    		insertInOperationalTestWindowSessionRule(operationalTestWindow);
    	}
    	     	 
    }
    
    private void insertInOperationalTestWindowSessionRule(OperationalTestWindow operationalTestWindow){
     
    	
    	
    	
    	if( operationalTestWindow.getTicketingFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getTestTicketedCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
    	if( operationalTestWindow.isTicketAtSectionFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSectionTicketedCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
    	if( operationalTestWindow.isTicketingOfTheDayFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getTestTicketOfTheDayCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
    	if( operationalTestWindow.isDacFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getDailyAccessCodeCategory().getId());
			testCollectionsSessionRules.setDacStartTime(operationalTestWindow.getDacStartTime());
			testCollectionsSessionRules.setDacEndTime(operationalTestWindow.getDacEndTime());
			testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
    	if(!operationalTestWindow.isRequiredToCompleteTest()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getNotRequiredToCompleteCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    		
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
		if(operationalTestWindow.isGracePeriodFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getGracePeriodCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    	
			testCollectionsSessionRules.setGracePeriod(operationalTestWindow.getGracePeriodInMin());
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}
		if(operationalTestWindow.isManagedBySystemFlag()){
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSystemEnrollmentCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    	
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}	
		else{
			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
	   	 	testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
	   	
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getManualEnrollmentCategory().getId());
			testCollectionsSessionRules.setAuditColumnProperties();    	
    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
        }
		/*else{			
			testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSectionTicketedCategory().getId());
			testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    		testCollectionsSessionRules = null;
		}*/
    }
    
     /**
     * This method inserts/deletes data from testcollectionssessionrules table based ticketingFlag
     * @param operationalTestWindow
     * @return
     */
    		
   		@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
        private void insertTestCollectionsSessionRules(OperationalTestWindow operationalTestWindow, long testCollectionId) {
    	    	logger.debug("Entering the insertTestCollectionsSessionRules() method");
    	    	operationalTestWindow.setTestCollectionId(testCollectionId);
    	    	//Refactored
    	        List<TestCollectionsSessionRules> testCollectionsSessionRulesList
    	        	= testCollectionsSessionRulesDao.selectByTestCollection(operationalTestWindow.getTestCollectionId());
    			StudentSessionRule existingStudentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(
    					testCollectionsSessionRulesList);
    	    	
    			TestCollectionsSessionRules testCollectionsSessionRules  = new TestCollectionsSessionRules();
    			testCollectionsSessionRules.setTestCollectionId(operationalTestWindow.getTestCollectionId());
    			if( operationalTestWindow.getTicketingFlag()){
    				testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getTestTicketedCategory().getId());
    	    		testCollectionsSessionRules.setAuditColumnProperties();    		
    	    		testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    	    		testCollectionsSessionRules = null;
    	    		
    			}	
    			else{
    				testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSectionTicketedCategory().getId());
    	    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    	    		testCollectionsSessionRules = null;
    			}
    			
    			testCollectionsSessionRules  = new TestCollectionsSessionRules();
    			testCollectionsSessionRules.setTestCollectionId(
    					operationalTestWindow.getTestCollectionId());
    			
    			if( operationalTestWindow.isManagedBySystemFlag() ){
    				testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getSystemEnrollmentCategory().getId());
    			}	
    			else {
    				testCollectionsSessionRules.setSessionRuleId(sessionRulesConfiguration.getManualEnrollmentCategory().getId());
    			}
    			
    	    	if((existingStudentSessionRule == null || !existingStudentSessionRule.isNotRequiredToCompleteTest() )
    	    			&& !operationalTestWindow.isRequiredToCompleteTest()) {
    	        	//If no record exists for this testCollectionId and testExitFlag is set to required to not complete test, 
    	        	//then insert a row into the testcollectionssessionrules table 
    	    		
    	    		TestCollectionsSessionRulesKey testCollectionsSessionRulesKey = new TestCollectionsSessionRulesKey();
    	    		
    	    		testCollectionsSessionRulesKey.setTestCollectionId(testCollectionsSessionRules.getTestCollectionId());
    	    		testCollectionsSessionRulesKey.setSessionRuleId(testCollectionsSessionRules.getSessionRuleId());
    	    		
    	    		TestCollectionsSessionRules  existingTestCollectionsSessionRules  = testCollectionsSessionRulesService.selectByPrimaryKey(testCollectionsSessionRulesKey);
    	    		if(existingTestCollectionsSessionRules == null) {
    	    			testCollectionsSessionRules.setAuditColumnProperties();
    	    			testCollectionsSessionRulesService.insert(testCollectionsSessionRules);
    	    		} else {
    	    			testCollectionsSessionRules.setAuditColumnPropertiesForUpdate();
    	    			testCollectionsSessionRulesService.updateByPrimaryKeySelective(testCollectionsSessionRules);
    	    		}
    	    	} else if((existingStudentSessionRule == null || existingStudentSessionRule.isNotRequiredToCompleteTest())
    	    			&& operationalTestWindow.isRequiredToCompleteTest()) {
    	        	//otherwise if the records do exists and
    	        	//testExitFlag is set to "Off" then delete the existing row from testcollectionssessionrules table.
    	    		//TODO Since its relationship table,is that ok to delete the records? 
    	    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    	    	} else if (operationalTestWindow.getTicketingFlag()) {
    	    		testCollectionsSessionRulesService.deleteByPrimaryKey(testCollectionsSessionRules);
    	    	}
    	    	
    	    	logger.debug("Leaving the insertTestCollectionsSessionRules() method");
    	    }
     		
    		
    /**
     * Selects testcollectionssessionrules table based on the primarykey values passed.
     * @param operationalTestWindow
     * @return
     */
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private TestCollectionsSessionRules getTestCollectionsSessionRules(OperationalTestWindow operationalTestWindow) {
    	logger.debug("Inside getTestCollectionsSessionRules() method");
    	
    	TestCollectionsSessionRulesKey testCollectionsSessionRulesKey = new TestCollectionsSessionRulesKey();        	    		
        testCollectionsSessionRulesKey.setTestCollectionId(operationalTestWindow.getTestCollectionId());
        testCollectionsSessionRulesKey.setSessionRuleId(sessionRulesConfiguration.getSectionTicketedCategory().getId());
        //TODO why is the find here ?	        
        return  testCollectionsSessionRulesService.selectByPrimaryKey(testCollectionsSessionRulesKey);        	        
    }

    /**
     * @param testCollectionId
     */
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private StudentSessionRule getStudentSessionRule(Long testCollectionId) {
        List<TestCollectionsSessionRules> testCollectionsSesionsRulesList = 
                                            testCollectionsSessionRulesDao.selectByTestCollection(testCollectionId);
        
        return studentSessionRuleConverter.convertToStudentSessionRule(testCollectionsSesionsRulesList);
    }    
    
    /**
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15915 : Test coordination extend window for test session
     * Extend test session operation window expiry date to later date.
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor=Exception.class)
	public boolean extendTestSessionWindow (
			List<OperationalTestWindow> operationalTestWindows) throws Exception {
    	boolean flag = true;
    	for(OperationalTestWindow opw : operationalTestWindows){
    		int result = operationalTestWindowDao.extendTestSessionWindow(opw);
    		if(result<=0){
    			flag = false;
    			throw new Exception("Cannot update Operation Test Window");
    		}
    	}
		return flag;
	}
    
    @Override
	public List<OperationalTestWindow> selectByAssessmentProgramAndHighStakes(Long id, boolean highStakes) {
		return operationalTestWindowDao.selectByAssessmentProgramAndHighStakes(id, highStakes);
	}
	/* Added for US 16553 */
    @Override
	public List<OperationalTestWindowDTO> selectTestWindowByAssessmentProgram(
			UserDetailImpl userDetails, String sortByColumn, String sortType,
			int i, Integer limitCount,
			Map<String, String> testWindowCriteriaMap, Long assessmentProgramId) {
		Long userId = userDetails.getUserId();
		Long organizationId = userDetails.getUser().getContractingOrganization().getId();
		List<OperationalTestWindowDTO> selectTestByAssessmentProgram = operationalTestWindowDao.getSelectTestWindowByAssessmentProgram(userId,sortByColumn,sortType,i,limitCount,testWindowCriteriaMap,assessmentProgramId,organizationId);
		return selectTestByAssessmentProgram;
	}

	@Override
	public int countTestWindowByAssessmentProgram(UserDetailImpl userDetails,
			Map<String, String> testWindowCriteriaMap, Long assessmentProgramId) {
		Long userId = userDetails.getUserId();
		Long organizationId = userDetails.getUser().getContractingOrganization().getId();
		return operationalTestWindowDao.countSelectTestWindowByAssessmentProgram(userId,testWindowCriteriaMap,assessmentProgramId,organizationId);
	}
	@Override
	public List<AssessmentProgramTCDTO> getExistingOpertioanlWindowTestCollection(
			Long windowId) {
		return operationalTestWindowDao.existOperationalWindowTestCollection(windowId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCListByWindowId(Long testWindowId) {
	    logger.debug("Inside selectAssessmentProgramAndTCListByWindowId() method");

		return operationalTestWindowDao.selectAssessmentProgramAndTCListByWindowId(testWindowId);		
	}

	@Override
	public List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCList(
			UserDetailImpl userDetails, String sortByColumn, String sortType,
			int i, Integer limitCount,
			Map<String, String> testWindowCriteriaMap,
			Long assessmentProgramId, String randomizationType,
			String categoryCode, Long windowId) {
		Long userId = userDetails.getUserId();
		
		return operationalTestWindowDao.selectAssessmentProgramAndTCList(userId,sortByColumn,sortType,i,limitCount,testWindowCriteriaMap,assessmentProgramId,randomizationType,categoryCode,windowId);
	}

	@Override
	public int countAssessmentProgramAndTCList(UserDetailImpl userDetails,
			Map<String, String> testWindowCriteriaMap,
			Long assessmentProgramId, String randomizationType,
			String categoryCode, Long windowId) {
		Long userId = userDetails.getUserId();
		return operationalTestWindowDao.countAssessmentProgramAndTCList(userId,testWindowCriteriaMap,assessmentProgramId,randomizationType,categoryCode,windowId);
	}

	@Override
	public List<Long> getOverlappingTestCollectionIds(
			Date beginTimeStamp, Date endTimeStamp,
			Long windowId,List<Long>  testCollectionIds,List<Long> multiStateIds) {
		return operationalTestWindowDao.selectOverlappingTestCollectionIds(beginTimeStamp, endTimeStamp, windowId,testCollectionIds,multiStateIds);
	}
	
	@Override
	public String getOverlappingOperationalTestWindowAutoEnrollmentIds(
			Date beginTimeStamp, Date endTimeStamp,
			Long windowId,Long testEnrollmentId,List<Long> multiStateIds) {
		// TODO Auto-generated method stub
		
		OperationalTestWindow  operationalTestWindow = operationalTestWindowDao.selectOverlappingOperationalTestWindowAutoEnrollmentIds(beginTimeStamp, endTimeStamp, windowId,testEnrollmentId,multiStateIds);
		return operationalTestWindow!=null ? operationalTestWindow.getTestEnrollmentMethodName() : "";
	}
	
	//sudhansu - Changed for OTW performance problem
	
	@Override
	public Map<String,Object> getAdminOptionData(Long windowId){
		
		Map<String,Object> adminOptionData = new HashMap<String, Object>();
		
		OperationalTestWindow  operationalTestWindow = null;
		
		List<OperationalTestWindow> windows = operationalTestWindowDao.selectTestWindowById(windowId);
		if(!windows.isEmpty()) {
			operationalTestWindow = windows.get(0);
		}
		List<TestCollectionsSessionRules> testCollectionsSessionRulesList=testCollectionsSessionRulesDao.selectSessionRulesWithCatagoryCode(windowId);
		//List<String> categoryCodes=testCollectionsSessionRulesDao.selectCategoryCodeByOperationalTestWindowId(windowId);
			 
		 /*for (String categoryCode : categoryCodes) {
			if("TICKETED_AT_TEST".equals(categoryCode)){
				adminOptionData.put("ticketingFlag", true);
			}
			if("DAILY_ACCESS_CODES".equals(categoryCode)){
				adminOptionData.put("dacFlag", true);
			}
			
			if("TICKET_OF_THE_DAY".equals(categoryCode) || "TICKETED_AT_SECTION".equals(categoryCode)){
				adminOptionData.put("ticketingFlag", true);
				adminOptionData.put("ticketing", categoryCode);
				
			}
			if("GRACE_PERIOD".equals(categoryCode)){
				adminOptionData.put("gracePeriod", true);
			}
			if("NOT_REQUIRED_TO_COMPLETE_TEST".equals(categoryCode)){
				adminOptionData.put("testExit", true);
			}
		}*/
			 
		 for (TestCollectionsSessionRules testCollectionsSessionRule : testCollectionsSessionRulesList) {
			 
			 if("TICKETED_AT_TEST".equals(testCollectionsSessionRule.getCategoryCode())){
					adminOptionData.put("ticketingFlag", true);
			 }else if("DAILY_ACCESS_CODES".equals(testCollectionsSessionRule.getCategoryCode())){
					adminOptionData.put("dacFlag", true);
			 }else if("TICKET_OF_THE_DAY".equals(testCollectionsSessionRule.getCategoryCode()) || "TICKETED_AT_SECTION".equals(testCollectionsSessionRule.getCategoryCode())){
					adminOptionData.put("ticketingFlag", true);
					adminOptionData.put("ticketing", testCollectionsSessionRule.getCategoryCode());					
			 }else if("GRACE_PERIOD".equals(testCollectionsSessionRule.getCategoryCode())){
					adminOptionData.put("gracePeriod", true);
			 }else if("NOT_REQUIRED_TO_COMPLETE_TEST".equals(testCollectionsSessionRule.getCategoryCode())){
					adminOptionData.put("testExit", true);
			 }
			 
			 Date startDacDate = null;
			 Date endDacDate = null;
		  	try {
		  		 if(testCollectionsSessionRule.getDacStartTime() != null){
			  		String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
				   	String startingTime = new SimpleDateFormat("hh:mm:ss a").format(testCollectionsSessionRule.getDacStartTime());
					String endTime = new SimpleDateFormat("hh:mm:ss a").format(testCollectionsSessionRule.getDacEndTime());
				   	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"); 
					startDacDate = (Date)formatter.parse(currentDate+" "+ startingTime);
					endDacDate = (Date)formatter.parse(currentDate+" "+ endTime);
		  		 }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   			 
			 
			 if(testCollectionsSessionRule.getGracePeriod() != null){
				 adminOptionData.put("graceTime", testCollectionsSessionRule.getGracePeriod()); 
			 }
			 if(testCollectionsSessionRule.getDacStartTime() != null){
				 adminOptionData.put("dacStartTime", DateUtil.convertDatetoSpecificTimeZoneStringFormat(startDacDate,"US/Central", "hh:mm:ss a"));
			 }
			 if(testCollectionsSessionRule.getDacEndTime() != null){
				 adminOptionData.put("dacEndTime", DateUtil.convertDatetoSpecificTimeZoneStringFormat(endDacDate, "US/Central", "hh:mm:ss a")); 
			 }
		}	 
			 
		return adminOptionData;
	}

	@Override
	public boolean isTestWindowNameExist(String windowName, Long id) {
		
		return operationalTestWindowDao.isTestWidowNameExist(windowName,id) > 0;
	}
	
	@Override
	public Map<Long,String> getOperationalTestWindowMultipleStateByUserIdAndAssessmentProgramId(
			Long assessmentProgramId, Long userId) {
		
		Map<Long,String> operationTestWindowStateIds= new HashMap<Long,String>();
		List<OperationalTestWindow> operationTestWindowMultipleStates= operationalTestWindowDao.getOperationalTestWindowsMultipleState(assessmentProgramId,userId);
		for(OperationalTestWindow operationTestWindowState:operationTestWindowMultipleStates){
			operationTestWindowStateIds.put(operationTestWindowState.getMultipleStateId(),operationTestWindowState.getStateName());
		}
		return operationTestWindowStateIds;
	}
	
	@Override
	public List<OperationalTestWindow> getMultipleStateByUserIdAndAssessmentProgramId(
			Long assessmentProgramId, Long userId) {
		List<OperationalTestWindow> operationTestWindowMultipleStates= operationalTestWindowDao.getMultipleStateByUserIdAndAssessmentProgramId(assessmentProgramId,userId);
		return operationTestWindowMultipleStates;
	}
	
	@Override
	public Map<Long, String> getOperationalTestWindowTestEnrollmentMethod(
			Long assessmentProgramId) {
		
		Map<Long,String> testEnrollmentMethod = new HashMap<Long,String>();
		List<OperationalTestWindow> operationTestWindowTestEnrollmentMethod = operationalTestWindowDao.getOperationalTestWindowsTestEnrollmentMethod(assessmentProgramId);
		for(OperationalTestWindow operationTestWindowTestEnrollment:operationTestWindowTestEnrollmentMethod){
			testEnrollmentMethod.put(operationTestWindowTestEnrollment.getTestEnrollmentMethodId(),operationTestWindowTestEnrollment.getTestEnrollmentMethodName());
		}
		return testEnrollmentMethod;
	}
	
	@Override
	public Map<Long, String> getOperationalTestWindowScoringAssignmentMethod(
			Long assessmentProgramId) {
		// TODO Auto-generated method stub
		Map<Long,String> scoringAssignmentMethod = new HashMap<Long,String>();
		List<OperationalTestWindow> operationalTestWindowScoringWindowMethod = operationalTestWindowDao.getOperationalTestWindowScoringWindowMethod(assessmentProgramId);
		for(OperationalTestWindow operationalTestWindowScoringWindow : operationalTestWindowScoringWindowMethod){
			scoringAssignmentMethod.put(operationalTestWindowScoringWindow.getScoringWindowMethodId(), operationalTestWindowScoringWindow.getScoringWindowMethodName());
		}		
		return scoringAssignmentMethod;
	}
	
	@Override
	public Map<Long, String> getOperationalTestWindowSelectedState(
			Long operationalTestWindowId) {
		
		Map<Long,String> operationTestWindowSeletedStateIds= new HashMap<Long,String>();
		List<OperationalTestWindow> operationTestWindowSelectedStates= operationalTestWindowDao.getOperationalTestWindowsSelectedState(operationalTestWindowId);
		for(OperationalTestWindow operationTestWindowSelectedState:operationTestWindowSelectedStates){
			operationTestWindowSeletedStateIds.put(operationTestWindowSelectedState.getMultipleStateId(),operationTestWindowSelectedState.getStateName());
		}
		return operationTestWindowSeletedStateIds;	
	}
	
	@Override
	public List<DailyAccessCode> getDacTestStagesByWindow(Long operationalTestWindowId) {
		return operationalTestWindowDao.findDacTestStagesByWindow(operationalTestWindowId);
	}
	
	@Override
	public List<DailyAccessCode> getDacTestStagesByGradeBand(Long operationalTestWindowId) {
		return operationalTestWindowDao.findDacTestStagesByGradeBand(operationalTestWindowId);
	}
	
	@Override
	public List<DailyAccessCode> getExistingAccessCodes(DailyAccessCode criteria) {
		return dacDao.findExistingAccessCodes(criteria);
	}
	
	@Override
	public OperationalTestWindowDTO getOpenInstructionAssessmentPlannerWindow(Long assessmentProgramId, Long stateId, Long contentAreaId, Long otwId) {
		return operationalTestWindowDao.getOpenInstructionAssessmentPlannerWindow(assessmentProgramId, stateId, contentAreaId, otwId);
	}
	
	@Override
	public List<Long> getStateIdsForOTWId(Long operationalTestWindowId) {
		return operationalTestWindowDao.getStateIdsForOTWId(operationalTestWindowId);
	}
	
	@Override
	public List<IAPContentFramework> getIAPContentFrameworkForWindow(Long operationalTestWindowId, String contentAreaAbbreviatedName, String gradeCourseAbbreviatedName) {
		return cfDao.getIAPContentFrameworkForWindow(operationalTestWindowId, contentAreaAbbreviatedName, gradeCourseAbbreviatedName);
	}
	
	@Override
	public OperationalTestWindow getActiveOperationalTestWindowByStateIdAndAssessmentProgramCode(
			String assessmentProgramCode, Long stateId) {
		return operationalTestWindowDao.getLatestActiveOperationalTestWindowForStudentTracker(assessmentProgramCode, stateId);
	}

	@Override
	public List<OperationalTestWindow> selectOperationalTestWindowById(Long operationalTestWindowId) {
		
		return operationalTestWindowDao.selectTestWindowById(operationalTestWindowId);
	}
	
	@Override
	public List<OperationalTestWindowDTO> getPreviousInstructionPlannerWindowsByStateAssessmentProgramAndSchoolYear(
			Long stateId, Long assessmentProgramId, int schoolYear) {
		return operationalTestWindowDao.getPreviousInstructionPlannerWindowsByStateAssessmentProgramAndSchoolYear(stateId, assessmentProgramId, schoolYear);
	}
}
