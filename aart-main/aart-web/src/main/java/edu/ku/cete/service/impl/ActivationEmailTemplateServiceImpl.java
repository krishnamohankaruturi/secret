package edu.ku.cete.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.domain.common.ActivationEmailTemplate;
import edu.ku.cete.domain.common.ActivationEmailTemplateState;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.ActivationEmailTemplateDao;
import edu.ku.cete.report.domain.ActivationTemplateAuditTrailHistory;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.UserAuditTrailHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.ActivationEmailTemplateService;
import edu.ku.cete.service.ActivationEmailTemplateStateService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;


@CacheConfig(cacheManager="epCacheManager")
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ActivationEmailTemplateServiceImpl implements ActivationEmailTemplateService {

    /** Generated Serial. */
    private static final long serialVersionUID = 2735963534122808411L;
    /**
     * logger.
     */
    private final Logger  logger = LoggerFactory.getLogger(ActivationEmailTemplateServiceImpl.class);

    @Autowired
    private ActivationEmailTemplateDao activationEmailTemplateDao;
    

	@Autowired
	private ActivationEmailTemplateStateService emailActivationStatesService;
	
	/**
	 * AuditTrailHistoryMapper.
	 */
	@Autowired
	private UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
	@Autowired
    private UserService userService;	
	
	private ObjectMapper mapper = new ObjectMapper();
		
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final ActivationEmailTemplate add(ActivationEmailTemplate toAdd) throws ServiceException {
        if (activationEmailTemplateDao.add(toAdd) != 1) {
            throw new ServiceException("add() returned more than one row");
        }
        return toAdd;
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final ActivationEmailTemplate get(final Long templateId) {
        return activationEmailTemplateDao.getEmailActivationDetailsByTemplateId(templateId);
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<ActivationEmailTemplate> getAllActive() {
        return activationEmailTemplateDao.getAllActive();
    }    
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final ActivationEmailTemplate getTemplateByTemplateName(ActivationEmailTemplate emailActivation) {
           return activationEmailTemplateDao.getTemplateByTemplateName(emailActivation);
     }  
        
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final ActivationEmailTemplate getTemplateByTemplateNameandTemplateId(ActivationEmailTemplate emailActivation) {
           return activationEmailTemplateDao.getTemplateByTemplateNameandTemplateId(emailActivation);
     } 
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final ActivationEmailTemplate getEmailActivationDetailsByAssesmentProgramIdandStateId(Long assessmentProgramId,Long stateId) {
           return activationEmailTemplateDao.getEmailActivationDetailsByAssesmentProgramIdandStateId(assessmentProgramId,stateId);
     }     
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final ActivationEmailTemplate getEmailActivationDetailsByTemplateId(final Long templateId) {
           return activationEmailTemplateDao.getEmailActivationDetailsByTemplateId(templateId);
     }  
      
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final ActivationEmailTemplate getDefaultEmailTemplate() {
           return activationEmailTemplateDao.getDefaultEmailTemplate();
     }     
    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateDefaultEmailTemplate(ActivationEmailTemplate emailActivation){
    	emailActivation.setAuditColumnPropertiesForUpdate();    	
		ActivationEmailTemplate afterUpdate=new ActivationEmailTemplate();
    	ActivationEmailTemplate beforeUpdate=new ActivationEmailTemplate();
    	String emailTemplateBeforUpdate = null;
		String emailTemplateAfterUpdate = null;			    			
		beforeUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(emailActivation.getId());
		emailTemplateBeforUpdate=beforeUpdate.buildJsonString();		
		int returnValue = activationEmailTemplateDao.updateDefaultEmailTemplate(emailActivation);    	
    	afterUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(emailActivation.getId());
		emailTemplateAfterUpdate=afterUpdate.buildJsonString();
		insertIntoDomainAuditHistory(afterUpdate.getId(),afterUpdate.getModifiedUser(),EventTypeEnum.UPDATE.getCode(),SourceTypeEnum.MANUAL.getCode(),emailTemplateBeforUpdate,emailTemplateAfterUpdate);
		return returnValue;
    }
    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateInactiveAllStatesByTemplateId(ActivationEmailTemplate emailActivation){
    	return activationEmailTemplateDao.updateInactiveAllStatesByTemplateId(emailActivation);
    }
        
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean updateOldTemplateStatesActiveFalse(String[] statesAlreadyTemplateIds){    
    	boolean updated=false;		
		for(int k=0;k<statesAlreadyTemplateIds.length;k++){
			if(!statesAlreadyTemplateIds[k].equals("")){
				
				List<String> list = Arrays.asList(statesAlreadyTemplateIds[k].split("_"));
				if(list.size()>0){
					Long tempId = new Long(list.get(0));
					String stateIDs = list.get(1);
					ActivationEmailTemplate afterUpdate=new ActivationEmailTemplate();
			    	ActivationEmailTemplate beforeUpdate=new ActivationEmailTemplate();
			    	String emailTemplateBeforUpdate = null;
					String emailTemplateAfterUpdate = null;
					beforeUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(tempId);
					emailTemplateBeforUpdate=beforeUpdate.buildJsonString();
					if(stateIDs.length()>0){
						List<String> stateIds = Arrays.asList(stateIDs.split(",")); 
						Long[] state =  new Long[stateIds.size()];      										
						for(int y=0;y<stateIds.size();y++){
							state[y]= new Long(stateIds.get(y));
						}
						
						
						ActivationEmailTemplate mailTemplate = new ActivationEmailTemplate();
						mailTemplate.setId(tempId);
						mailTemplate.setAllStates(false);
						mailTemplate.setAuditColumnPropertiesForUpdate();
						emailActivationStatesService.updateActiveFlagByStateIds(tempId, state,mailTemplate.getModifiedDate(),mailTemplate.getModifiedUser());
						updateInactiveAllStatesByTemplateId(mailTemplate);    									
					
					}
					 afterUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(tempId);
			    	 emailTemplateAfterUpdate=afterUpdate.buildJsonString();
			    	 insertIntoDomainAuditHistory(afterUpdate.getId(),afterUpdate.getModifiedUser(),EventTypeEnum.UPDATE.getCode(),SourceTypeEnum.MANUAL.getCode(),emailTemplateBeforUpdate,emailTemplateAfterUpdate);
			       	
				}
				
			}
		}
	
    	return updated;
    }
    
    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean createCustomEmailTemplate(Long templateId,ActivationEmailTemplate emailActivation,Long[] states){    
    	boolean templatedCreated=false;
    	ActivationEmailTemplate afterUpdate=new ActivationEmailTemplate();
    	ActivationEmailTemplate beforeUpdate=new ActivationEmailTemplate();
    	String emailTemplateBeforUpdate = null;
		String emailTemplateAfterUpdate = null;
		String action=EventTypeEnum.INSERT.getCode();
		if(templateId!=null){
		beforeUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(templateId);
		emailTemplateBeforUpdate=beforeUpdate.buildJsonString();
		}
    	ActivationEmailTemplate emailActivationCreated = addOrUpdateEmailTemplate(emailActivation);    	    	
    	if(templateId==null || templateId.longValue()==0){
    		if (states != null && states.length>0) {
    			for (Long stateId : states) {
    				ActivationEmailTemplateState emailTemplateStates = new ActivationEmailTemplateState();
    		    	emailTemplateStates.setTemplateId(emailActivationCreated.getId());
    		    	emailTemplateStates.setAssessmentProgramId(emailActivationCreated.getAssessmentProgramId());
    		    	emailTemplateStates.setStateId(stateId);
    		    	emailTemplateStates.setAuditColumnProperties();		    	
    		    	emailActivationStatesService.add(emailTemplateStates);
    			}
    		}
    		templatedCreated = true;
    	}  
    	else{
    		
    		action=EventTypeEnum.UPDATE.getCode();
    		emailActivationCreated.setAuditColumnPropertiesForUpdate();
    		 List<ActivationEmailTemplateState> emailTemplateStates = emailActivationStatesService.updateAndReturnByTemplateId(emailActivationCreated.getId(),emailActivationCreated.getModifiedDate(),emailActivationCreated.getModifiedUser());
    		 Set<Long> oldStates = new HashSet<Long>();    		 
    		 
    		 if(states.length>0){
    			for(ActivationEmailTemplateState state : emailTemplateStates){
    				oldStates.add(state.getStateId());
       		    }
    		 }
    		 
    		 if(states.length>0){
     			for(Long StateId : states){
     				if(oldStates.contains(StateId)){
     					//update
    					ActivationEmailTemplateState templateStates = new ActivationEmailTemplateState();
    					templateStates.setTemplateId(emailActivationCreated.getId());
    					templateStates.setAssessmentProgramId(emailActivationCreated.getAssessmentProgramId());
        		    	templateStates.setStateId(StateId);
        		    	templateStates.setActiveFlag(true);
        		    	templateStates.setAuditColumnPropertiesForUpdate();
        		    	emailActivationStatesService.update(templateStates);
     				}else{
     					//insert
    					ActivationEmailTemplateState templateStates = new ActivationEmailTemplateState();
    					templateStates.setTemplateId(emailActivationCreated.getId());
    					templateStates.setAssessmentProgramId(emailActivationCreated.getAssessmentProgramId());
        		    	templateStates.setStateId(StateId);
        		    	templateStates.setActiveFlag(true);
        		    	templateStates.setAuditColumnProperties();
        		    	emailActivationStatesService.add(templateStates);
     				}
        		  }
     		 }
    		 
    		 templatedCreated = true;
    	}
    	 afterUpdate=activationEmailTemplateDao.getActivationEmailForAuditById(emailActivationCreated.getId());
    	 emailTemplateAfterUpdate=afterUpdate.buildJsonString();
    	 insertIntoDomainAuditHistory(afterUpdate.getId(),afterUpdate.getModifiedUser(),action,SourceTypeEnum.MANUAL.getCode(),emailTemplateBeforUpdate,emailTemplateAfterUpdate);
       
        return templatedCreated;
    }
    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public ActivationEmailTemplate addOrUpdateEmailTemplate(ActivationEmailTemplate emailActivation) {    	
    	if(emailActivation.getId()==null){
    		emailActivation.setAuditColumnProperties();
    		activationEmailTemplateDao.add(emailActivation); 
    	}
    	else{
    		emailActivation.setAuditColumnPropertiesForUpdate();
    		activationEmailTemplateDao.update(emailActivation);
    	}    	
    	emailActivation = activationEmailTemplateDao.getEmailActivationDetailsByTemplateId(emailActivation.getId());
    	return emailActivation;
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<ActivationEmailTemplate> getAllChildrenToView(Map<String, Object> criteria, String sortByColumn, String sortType, 
    		Integer offset, Integer limitCount) {
    	criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
        return activationEmailTemplateDao.findSelectEmailActivation(criteria);
    }
    

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Integer countAllChildrenToView(Map<String, Object> filters) {
        return activationEmailTemplateDao.countSelectEmailActivation(filters);
    }
    
	@Override
	  @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToactivationEmailTemplateAuditTrailHistory(DomainAuditHistory domainAuditHistory) {
		// TODO Auto-generated method stub
		JsonNode before = null;
		JsonNode after = null;
		String templateName=new String();
		String statesImpactedIds=new String();
		String statesImpactedNames=new String();
		Long assessmentProgramId=null;
		String assessmentProgramName=new String();
		boolean isProcessed= false;
		StringBuffer sb=new StringBuffer();
		try {
			if(domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() == null){
				logger.debug("In-valid entry in Domainaudithistory table"+ domainAuditHistory.getObjectId());

			}else if(domainAuditHistory.getObjectBeforeValues() == null ){
				//To make an entry into ActivationTemplateAuditTrailHistory when email template created  
				if (SourceTypeEnum.MANUAL.getCode().equals(domainAuditHistory.getSource())){
					/**
					  * uday
					  * F424
					  * For audit history 
					  */
					//start
					after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
					assessmentProgramId=after.get("AssessmentProgramId").asLong();
					assessmentProgramName=after.get("AssessmentProgram").asText();
					templateName=after.get("TemplateName").asText();
					List<Long> stateidList = new ArrayList<Long>();
					List<String> stateNameList = new ArrayList<String>();
					if(after.get("States")!=null){
						ArrayNode stateArray = (ArrayNode) after.get("States");
						
						for (int i = 0; i < stateArray.size(); i++) {
							stateidList.add(Long.valueOf(stateArray.get(i).get("Id").asText()));
							stateNameList.add(stateArray.get(i).get("Name").asText());
						}
					}
					statesImpactedIds=stateidList.toString();
					Collections.sort(stateNameList);
					statesImpactedNames=stateNameList.toString();
					//end
					
					 
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							,"Activation Email Template Created",null,domainAuditHistory.getObjectAfterValues(),assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
				}
			}else{
				//To make an entry into ActivationTemplateAuditTrailHistory when template edited 
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				//For activation email templates audit history impacted states and assessment program 
				assessmentProgramId=after.get("AssessmentProgramId").asLong();
				assessmentProgramName=after.get("AssessmentProgram").asText();
				templateName=after.get("TemplateName").asText();
				List<Long> stateidList; 
				List<String> stateNameList;
				
				
				//Assuming always the json structure for before object and after object is same 			
				for(Iterator<Entry<String, JsonNode>> it = before.fields();it.hasNext();){
					stateidList = new ArrayList<Long>();
					stateNameList = new ArrayList<String>();
					Entry<String, JsonNode> entry = it.next();
					String key = entry.getKey();					
					//States added or removed start							
					if(!"ModifiedDate".equalsIgnoreCase(key) && !"ModifiedUser".equalsIgnoreCase(key)){
						
						if("States".equals(key)&&!before.get(key).isNull() &&!after.get(key).isNull()){

							ArrayNode beforeStateArray = (ArrayNode) before.get(key);
							ObjectMapper removemapper = new ObjectMapper();
							JsonNode removeroot = removemapper.createObjectNode();
							ArrayNode removedStates = removemapper.createArrayNode();
							List<ObjectNode> beforeStateList = new ArrayList<ObjectNode>(); 
							for (int i = 0; i < beforeStateArray.size(); i++) {
								beforeStateList.add((ObjectNode)beforeStateArray.get(i));
								//For activation email templates audit history impacted states start
								stateidList.add(Long.valueOf(beforeStateArray.get(i).get("Id").asText()));
								stateNameList.add(beforeStateArray.get(i).get("Name").asText());
								//end
							} 

							ArrayNode afterStateArray = (ArrayNode) after.get(key);
							ObjectMapper addmapper = new ObjectMapper();
							JsonNode addroot = addmapper.createObjectNode();
							ArrayNode addedStates = addmapper.createArrayNode();
							List<ObjectNode> afterStateList = new ArrayList<ObjectNode>();
							for (int i = 0; i < afterStateArray.size(); i++) {
								afterStateList.add((ObjectNode)afterStateArray.get(i));
								//For activation email templates audit history impacted states start
								if(!stateidList.contains(Long.valueOf(afterStateArray.get(i).get("Id").asText()))){
									stateidList.add(Long.valueOf(afterStateArray.get(i).get("Id").asText()));
								}
								if(!stateNameList.contains(afterStateArray.get(i).get("Name").asText())){
									stateNameList.add(afterStateArray.get(i).get("Name").asText());
								}
								//end
							}									   
							statesImpactedIds=stateidList.toString();
							Collections.sort(stateNameList);
							statesImpactedNames=stateNameList.toString();
							//Removed States
							for (ObjectNode beforeState : beforeStateList) {
								boolean isItContains=true;
								for(ObjectNode afterState : afterStateList){
									if(beforeState.get("Id").asText().equalsIgnoreCase(afterState.get("Id").asText())){
										isItContains=false;
									}
								}
								if(isItContains){
									removedStates.add(beforeState);
								}
							}
							if(removedStates!=null&& removedStates.size()>0){
							
								((ObjectNode)removeroot).set(key, removedStates);
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
										,"Activation Email Template "+key+" Removed",removemapper.writeValueAsString(removeroot),null,assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
							}
							//Added States
							for(ObjectNode afterState : afterStateList){
								boolean isItContains=true;
								for (ObjectNode beforeState : beforeStateList) {
									if(beforeState.get("Id").asText().equalsIgnoreCase(afterState.get("Id").asText())){
										isItContains=false;
									}
								}
								if(isItContains){
									addedStates.add(afterState);
								}
							}

							if(addedStates!=null&&addedStates.size()>0){
							
								((ObjectNode)addroot).set(key, addedStates);
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
										,"Activation Email Template "+key+" Added",null,addmapper.writeValueAsString(addroot),assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
							}
						}else if("States".equals(key)&&!before.get(key).isNull()&&after.get(key).isNull()){
							/**
							  * uday
							  * F424
							  * For activation email templates audit history impacted states   
							  */
							//start
							if(before.get("States")!=null){
								ArrayNode stateArray = (ArrayNode) before.get("States");
								
								for (int i = 0; i < stateArray.size(); i++) {									
									stateidList.add(Long.valueOf(stateArray.get(i).get("Id").asText()));
									stateNameList.add(stateArray.get(i).get("Name").asText());
								}
							}
							statesImpactedIds=stateidList.toString();
							Collections.sort(stateNameList);
							statesImpactedNames=stateNameList.toString();
							//end
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
									,"Activation Email Template "+key+" Removed",null,after.get(key).asText(),assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
						}else if("States".equals(key)&&!after.get(key).isNull()&& before.get(key).isNull()){
							/**
							  * uday
							  * F424
							  * For activation email templates audit history impacted states   
							  */
							//start
							if(after.get("States")!=null){
							ArrayNode stateArray = (ArrayNode) after.get("States");
							
							for (int i = 0; i < stateArray.size(); i++) {
								stateidList.add(Long.valueOf(stateArray.get(i).get("Id").asText()));
								stateNameList.add(stateArray.get(i).get("Name").asText());
							}
						}
							statesImpactedIds=stateidList.toString();
							Collections.sort(stateNameList);
							statesImpactedNames=stateNameList.toString();
						//end
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
									,"Activation Email Template "+key+" Adeed",before.get(key).asText(),null,assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
						}
						//States added or removed end
					String beforetext=before.get(key).asText();
					String aftertext=after.get(key).asText();

						if( !"States".equals(key) && !beforetext.equals(aftertext)){
							/**
							  * uday
							  * F424
							  * For activation email templates audit history impacted states  
							  */
							//start
							if(after.get("States")!=null){
								ArrayNode stateArray = (ArrayNode) before.get("States");
								
								for (int i = 0; i < stateArray.size(); i++) {
									stateidList.add(Long.valueOf(stateArray.get(i).get("Id").asText()));
									stateNameList.add(stateArray.get(i).get("Name").asText());
								}
							}
							statesImpactedIds=stateidList.toString();
							Collections.sort(stateNameList);
							statesImpactedNames=stateNameList.toString();
							//end
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
										,"Activation Email Template "+key+" Changed","{\""+key+"\":"+before.get(key)+"}","{\""+key+"\":"+after.get(key)+"}",assessmentProgramId,assessmentProgramName,statesImpactedIds,statesImpactedNames,templateName);
						}
					}


				}	

			

			}
			isProcessed= true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isProcessed= false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isProcessed= false;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in ActivationEmailaudittrail table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		}
		if (isProcessed){
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"COMPLETED");
		}
		else{
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"FAILED");
		}
		
		return isProcessed;	
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,Long domainAuditHistoryId,Long modifiedUserId,String eventName,String beforeValue,String currentValue,Long assessmentProgramId,
			String assessmentProgramName,String statesImpactedIds,String statesImpactedNames,String templateName){
		StringBuffer states=new StringBuffer();
		states.append("{");
		states.append(statesImpactedIds.substring(1, statesImpactedIds.length()-1));
		states.append("}");
		User user=userService.get(modifiedUserId);
		ActivationTemplateAuditTrailHistory activationTemplateAuditTrailHistory = new ActivationTemplateAuditTrailHistory();
		activationTemplateAuditTrailHistory.setAffectedemailtemplateid(objectId);
		activationTemplateAuditTrailHistory.setCreatedDate(new Date());
		activationTemplateAuditTrailHistory.setModifiedUser(modifiedUserId);
		activationTemplateAuditTrailHistory.setEventName(eventName);
		activationTemplateAuditTrailHistory.setBeforeValue(beforeValue);
		activationTemplateAuditTrailHistory.setCurrentValue(currentValue);
		activationTemplateAuditTrailHistory.setDomainAuditHistoryId(domainAuditHistoryId);
		activationTemplateAuditTrailHistory.setAssessmentProgramId(assessmentProgramId);
		activationTemplateAuditTrailHistory.setAssessmentProgram(assessmentProgramName);
		activationTemplateAuditTrailHistory.setStateIds(states.toString());
		activationTemplateAuditTrailHistory.setTemplateName(templateName);
		activationTemplateAuditTrailHistory.setStateNames(statesImpactedNames.substring(1, statesImpactedNames.length()-1));
		activationTemplateAuditTrailHistory.setModifiedUserName(user==null ? null:user.getUserName());
		activationTemplateAuditTrailHistory.setModifiedUserFirstName(user==null ? null:user.getFirstName());
		activationTemplateAuditTrailHistory.setModifiedUserLastName(user==null ? null:user.getSurName());
		activationTemplateAuditTrailHistory.setModifiedUserEducatorIdentifier(user==null ? null:user.getUniqueCommonIdentifier());
		userAuditTrailHistoryMapperDao.insertActivationEmailAuditTrail(activationTemplateAuditTrailHistory);
		logger.trace("value inserted in ActivationEmailaudittrail table ");
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source,String emailTemplateBeforUpdate,String emailTemplateAfterUpdate){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();

		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("ACTIVITIONEMAIL");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(action);
		domainAuditHistory.setObjectBeforeValues(emailTemplateBeforUpdate);
		domainAuditHistory.setObjectAfterValues(emailTemplateAfterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ActivationEmailTemplate> getTemplateByAssesmentAndState(
			Long assessmentprogrsmId, Long stateId) {
		// TODO Auto-generated method stub
		return activationEmailTemplateDao.getTemplateByAssesmentAndState(assessmentprogrsmId,  stateId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ActivationEmailTemplate> getTemplateForClaimUsers(String templateName) {

		return activationEmailTemplateDao.getTemplateForClaimUsers(templateName);
	}


}