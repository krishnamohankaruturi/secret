package edu.ku.cete.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegisteredTestSessions;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.SubjectAreaService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;

@Component("batchRegistrationProcessor")
public class BatchRegistrationProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TestTypeService testTypeService;
    
    @Autowired
    private SubjectAreaService subjectAreaService;
    
    @Autowired
    private GradeCourseService gradeCourseService;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private TestingProgramService testingProgramService;
    
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    @Autowired
    private BatchRegistrationService batchRegistrationService;
    
    @Autowired
    private ComplexityBandService complexityBandService;
    
    @Autowired
	private OrganizationService organizationService;
    
    @Value("${user.organization.DLM}")
    private String dlmOrgName;
    
    @SuppressWarnings("unchecked")
	@Async
    public Future<Map<String, Object>> startBatchRegProcess(Long assessmentProgramId,
			Long testTypeId, Long subjectAreaId, Long gradeCourseId,
			Long assessmentId, Long testingProgramId, Long contractingOrgId) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result.put("STATUS", "INPROCESS");
            //do logic
            Map<String, Integer> successAndFailureCounts = null;
    		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetails.getUser();
    		if(ap != null) {
    			if(ap.getProgramName().equals(dlmOrgName)) {
    				Date date= new Date();
    		        Timestamp submitedDate = new Timestamp(date.getTime());
    				//Insert into Batch Registration Table with status In-Progress.
    		        BatchRegistration batchRegistrationRecord = new BatchRegistration();
    				batchRegistrationRecord.setAssessmentProgram(assessmentProgramId);
    				batchRegistrationRecord.setSubmissionDate(submitedDate);
    				batchRegistrationRecord.setStatus("IN-PROGRESS");
    				batchRegistrationRecord.setSuccessCount(0);
    				batchRegistrationRecord.setFailedCount(0);
    				batchRegistrationRecord.setCreatedUser(user.getId());
    				batchRegistrationService.insertSelectiveBatchRegistration(batchRegistrationRecord);
    				Set<Long> surveyIds = complexityBandService.findSurveysForBatchAutoRegistration(contractingOrgId, assessmentProgramId, user);
    		        int successCount = 0;
    				int failedCount = 0;
    				int currentSurveyCount = surveyIds.size();
    				List<Map<String, Object>> failedRecords = new ArrayList<Map<String, Object>>();
    		    	List<Long> successTestSessions = new ArrayList<Long>();
    		    	logger.debug("Total number of survey's to process: "+currentSurveyCount);
    				for (Long surveyId : surveyIds){
    					Map<String, Object> processResult = null;
    					try {
    						logger.debug("Started processing surveyid : "+surveyId);
	    		            processResult = complexityBandService.calculateComplexityBandsForBatch(surveyId, user);
	    		            logger.debug("Completed processinsurveyid : "+surveyId);
	    		            if (null != processResult.get("success") 
	    		            		&& (Boolean) processResult.get("success")){
	    		            	successCount++;
	    		            } else {
	    		            	logger.debug("Successfully processed surveyid : "+surveyId);
	    		            	failedCount++;
	    		                failedRecords.add(processResult);
	    		            }
	    		            if(processResult.get("testsessionids") !=null) {
	    		            	successTestSessions.addAll((List<Long>) processResult.get("testsessionids"));
	    		            } 
    					} catch(Exception e) {
    						logger.error("Error while processing survey:"+surveyId, e);
    						failedCount++;
    						if(null != processResult) {
    							failedRecords.add(processResult);
    						}
    					}
    					logger.debug("Remaining survey's to process: "+ --currentSurveyCount);
    				}
    				//get batch id
    				Long batchRegistrationId = batchRegistrationRecord.getId();

    				//Update Batch Registration Table with status Completed.
    				batchRegistrationRecord.setStatus("COMPLETED");
    				batchRegistrationRecord.setSuccessCount(successCount);
    				batchRegistrationRecord.setFailedCount(failedCount);
    				batchRegistrationService.updateBatchRegistrationSelective(batchRegistrationRecord);
    				
    				//Insert failed reasons into reasons table
    				for(Map<String, Object> failedRecord : failedRecords){
    					BatchRegistrationReason batchRegistrationReasonRec = new BatchRegistrationReason();
    					batchRegistrationReasonRec.setBatchRegistrationId(batchRegistrationId);
    					if(failedRecord.get("studentid") != null)
    						batchRegistrationReasonRec.setStudentId((Long)failedRecord.get("studentid"));
    					if(failedRecord.get("reason") != null)
    						batchRegistrationReasonRec.setReason((String)failedRecord.get("reason"));
    					batchRegistrationService.insertBatchRegistrationReason(batchRegistrationReasonRec);
    				}
    				//insert testsessions created in batchregistered test session table
    				if(successTestSessions.size() > 0){
    					for(long testSessionId : successTestSessions){
    						BatchRegisteredTestSessions batchRegisteredTestSessionsRec = new BatchRegisteredTestSessions();
    						batchRegisteredTestSessionsRec.setBatchRegistrationId(batchRegistrationId);
    						batchRegisteredTestSessionsRec.setTestSessionId(testSessionId);
    						batchRegistrationService.insertBatchRegisteredTestSessions(batchRegisteredTestSessionsRec);
    					}
    				}
    				successAndFailureCounts = new HashMap<String, Integer>();
    				successAndFailureCounts.put("success", successCount);
    				successAndFailureCounts.put("failed", failedCount);
    			} else {
    				Map<String, Object> processResult = new HashMap<String, Object>();
    				
    				boolean breakDayAlaska = false;
    				TestingProgram tp = testingProgramService.getByTestingProgramId(testingProgramId);
    				
    				// If assessmentprogram == KAP && testingprogram == Formative -- invoke old batch process
    				if(null != ap && null != tp && null != ap.getAbbreviatedname() 
    						&& null != tp.getProgramAbbr() && ap.getAbbreviatedname().equalsIgnoreCase("KAP") 
    						&& tp.getProgramAbbr().equalsIgnoreCase("F")){
    					
    					breakDayAlaska = true;
    				}
    				
    				//Insert into Batch Registration Table with status In-Progress.
    				Date date= new Date();
    		        Timestamp submitedDate = new Timestamp(date.getTime());
    				BatchRegistration batchRegistrationRecord = new BatchRegistration();
    				batchRegistrationRecord.setAssessmentProgram(assessmentProgramId);
    				batchRegistrationRecord.setSubmissionDate(submitedDate);
    				batchRegistrationRecord.setStatus("IN-PROGRESS");
    				batchRegistrationRecord.setSuccessCount(0);
    				batchRegistrationRecord.setFailedCount(0);
    				batchRegistrationRecord.setAssessment(assessmentId);
    				batchRegistrationRecord.setGrade(gradeCourseId);
    				batchRegistrationRecord.setSubject(subjectAreaId);
    				batchRegistrationRecord.setTestingProgram(testingProgramId);
    				batchRegistrationRecord.setTestType(testTypeId);
    				batchRegistrationRecord.setCreatedUser(user.getId());
    				batchRegistrationService.insertSelectiveBatchRegistration(batchRegistrationRecord);
    				boolean enrollmentsFound = false;    				    				
    		    	int successCnt = 0;
    		    	int failedCnt = 0;
    				List<Map<String, Object>> failedRecords = new ArrayList<Map<String, Object>>();
    		    	List<Long> successTestSessions = new ArrayList<Long>();
    				
    				//Code for Alaska break day
    				List<Organization> orgs = organizationService.getByDisplayIdentifier("AK");
    				Organization alaskaOrg = null;
    				for (Organization org : orgs) {
    					if(org.getContractingOrganization()) {
    						alaskaOrg = org;
    						break;
    					}
    				}
    				
    				if(null == alaskaOrg) {
    					List<Map<String, Object>> failedRecord = new ArrayList<Map<String, Object>>();
    					Map<String, Object> failedRecordMap =  new HashMap<String, Object>();
    					failedRecordMap.put("success", false);
    					failedRecordMap.put("reason", "Alaska Organization not found.");
    					failedRecords.add(failedRecordMap);
    					processResult.put("faileddetails", failedRecord);
    					processResult.put("success", 0);
    					processResult.put("failed", 1);
    				} else {
	    				if (testTypeId == null || subjectAreaId == null || gradeCourseId == null) {
	        		    	
	    					// assuming kansas break day data set
	    					if(testTypeId == null) {
	    						List<TestType> testTypes = testTypeService.getTestTypeByAssessmentId(assessmentId);
	    						if(testTypes != null && !testTypes.isEmpty()) {
	    							testTypeId = testTypes.get(0).getId();
	    						}
	    					}
	    					List<GradeCourse> gradeCourses = gradeCourseService.selectGradeCourseOfKansasBreakDay();
	    					
	    					List<SubjectArea> subjectAreas = null;
	    					
	    					if(subjectAreaId == null) {
	    						//subjectAreas = subjectAreaService.getSubjectAreaByTestTypeId(testTypeId);
	    						subjectAreas = subjectAreaService.getSubjectAreasForAutoRegistration(testTypeId, assessmentId);
	        					for (SubjectArea subjectArea : subjectAreas) {
	        						for (GradeCourse gradeCourse : gradeCourses) {
	        							List<Enrollment> enrollments = null;
	        							if(breakDayAlaska) {
	        								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectArea.getId(), gradeCourse.getId(), alaskaOrg.getId());
	        							} else {
	        								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectArea.getId(), gradeCourse.getId(), contractingOrgId);
	        							}
	        							
	        							if(enrollments != null && !enrollments.isEmpty()) {
	        								enrollmentsFound = enrollmentsFound || true; 
	    	    							for (Enrollment enrollment : enrollments) {
	    	        							//Map<String, Object> loopProcessResult = processBatchRegProcess(testTypeId, subjectAreaId, gradeCourse.getId(), assessmentId, contractingOrgId);
	    	        							Map<String, Object> loopProcessResult = studentsTestsService.batchCreateAutoRegistrationTestSessions(enrollment, subjectArea.getId(), testTypeId, gradeCourse.getId(), assessmentId);
	    	        							   								
	    	        								if(loopProcessResult.containsKey("faileddetails")) {    									
	    	        									failedRecords.addAll((List<Map<String, Object>>)loopProcessResult.get("faileddetails"));
	    	        								}    								
	    	        								if(loopProcessResult.containsKey("successdetails")) {    									
	    	        									successTestSessions.addAll((List<Long>)loopProcessResult.get("successdetails"));
	    	        								}     								
	    	        								if(loopProcessResult.containsKey("success")) {
	    	        									Integer curCnt = (Integer) loopProcessResult.get("success");
	    	        									if(curCnt != null && curCnt.intValue() > 0) {
	    	        										successCnt += curCnt.intValue();
	    	        									}
	    	        								}    								
	    	        								if(loopProcessResult.containsKey("failed")) {
	    	        									Integer curCnt = (Integer) loopProcessResult.get("failed");
	    	        									if(curCnt != null && curCnt.intValue() > 0) {
	    	        										failedCnt += curCnt.intValue();
	    	        									}
	    	        								} 
	    	    							}
	    								} else {
	        								enrollmentsFound = enrollmentsFound || false; 
	    								}
	    							}
	    						}
	    					} else {
	    						for (GradeCourse gradeCourse : gradeCourses) {
	    							
        							List<Enrollment> enrollments = null;
        							if(breakDayAlaska) {
        								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourse.getId(), alaskaOrg.getId());
        							} else {
        								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourse.getId(), contractingOrgId);
        							}
	    							
	    							
	    							if(enrollments != null && !enrollments.isEmpty()) {
	    								enrollmentsFound = enrollmentsFound || true; 
		    							for (Enrollment enrollment : enrollments) {
		        							//Map<String, Object> loopProcessResult = processBatchRegProcess(testTypeId, subjectAreaId, gradeCourse.getId(), assessmentId, contractingOrgId);
		        							Map<String, Object> loopProcessResult = studentsTestsService.batchCreateAutoRegistrationTestSessions(enrollment, subjectAreaId, testTypeId, gradeCourse.getId(), assessmentId);
		        							   								
		        								if(loopProcessResult.containsKey("faileddetails")) {    									
		        									failedRecords.addAll((List<Map<String, Object>>)loopProcessResult.get("faileddetails"));
		        								}    								
		        								if(loopProcessResult.containsKey("successdetails")) {    									
		        									successTestSessions.addAll((List<Long>)loopProcessResult.get("successdetails"));
		        								}     								
		        								if(loopProcessResult.containsKey("success")) {
		        									Integer curCnt = (Integer) loopProcessResult.get("success");
		        									if(curCnt != null && curCnt.intValue() > 0) {
		        										successCnt += curCnt.intValue();
		        									}
		        								}    								
		        								if(loopProcessResult.containsKey("failed")) {
		        									Integer curCnt = (Integer) loopProcessResult.get("failed");
		        									if(curCnt != null && curCnt.intValue() > 0) {
		        										failedCnt += curCnt.intValue();
		        									}
		        								} 
		    							}
									} else {
	    								enrollmentsFound = enrollmentsFound || false; 
									}
								}
	    					}		
	    					
	    				} else {

							List<Enrollment> enrollments = null;
							if(breakDayAlaska) {
								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourseId, alaskaOrg.getId());
							} else {
								enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourseId, contractingOrgId);
							}
							
							if(enrollments != null && !enrollments.isEmpty()) {
								enrollmentsFound = enrollmentsFound || true; 
								for (Enrollment enrollment : enrollments) {
	    							Map<String, Object> loopProcessResult = studentsTestsService.batchCreateAutoRegistrationTestSessions(enrollment, subjectAreaId, testTypeId, gradeCourseId, assessmentId);
	    							   								
	    								if(loopProcessResult.containsKey("faileddetails")) {    									
	    									failedRecords.addAll((List<Map<String, Object>>)loopProcessResult.get("faileddetails"));
	    								}    								
	    								if(loopProcessResult.containsKey("successdetails")) {    									
	    									successTestSessions.addAll((List<Long>)loopProcessResult.get("successdetails"));
	    								}     								
	    								if(loopProcessResult.containsKey("success")) {
	    									Integer curCnt = (Integer) loopProcessResult.get("success");
	    									if(curCnt != null && curCnt.intValue() > 0) {
	    										successCnt += curCnt.intValue();
	    									}
	    								}    								
	    								if(loopProcessResult.containsKey("failed")) {
	    									Integer curCnt = (Integer) loopProcessResult.get("failed");
	    									if(curCnt != null && curCnt.intValue() > 0) {
	    										failedCnt += curCnt.intValue();
	    									}
	    								} 
								}
							} else {
								enrollmentsFound = enrollmentsFound || false; 
							}
	    				}
	    				
	    				if(!enrollmentsFound) {
	    					List<Map<String, Object>> failedRecord = new ArrayList<Map<String, Object>>();
	    					Map<String, Object> failedRecordMap =  new HashMap<String, Object>();
	    					failedRecordMap.put("success", false);
	    					failedRecordMap.put("reason", "No enrollements found.");
	    					failedRecords.add(failedRecordMap);
	    					processResult.put("faileddetails", failedRecord);
	    					processResult.put("success", 0);
	    					processResult.put("failed", 1);
	    				} else {
	    					processResult.put("successdetails", successTestSessions);
	    					processResult.put("faileddetails", failedRecords);
	    					processResult.put("success", successCnt);
	    					processResult.put("failed", failedCnt);
	    				}
	    				
	    				Long batchRegistrationId = batchRegistrationRecord.getId();
	    				
	    				
	    				batchRegistrationRecord.setStatus("COMPLETED");
	    				batchRegistrationRecord.setSuccessCount((Integer) processResult.get("success"));
	    				batchRegistrationRecord.setFailedCount((Integer) processResult.get("failed"));
	    				batchRegistrationService.updateBatchRegistrationSelective(batchRegistrationRecord);
						List<Map<String, Object>> thisFailedRecords = (List<Map<String, Object>>) processResult.get("faileddetails");
	    				for(Map<String, Object> failedRecord : thisFailedRecords){
	    					BatchRegistrationReason batchRegistrationReasonRec = new BatchRegistrationReason();
	    					batchRegistrationReasonRec.setBatchRegistrationId(batchRegistrationId);
	    					if(failedRecord.get("studentid") != null)
	    						batchRegistrationReasonRec.setStudentId((Long)failedRecord.get("studentid"));
	    					if(failedRecord.get("reason") != null)
	    						batchRegistrationReasonRec.setReason((String)failedRecord.get("reason"));
	    					batchRegistrationService.insertBatchRegistrationReason(batchRegistrationReasonRec);
	    				}
	    				//insert testsessions created in batchregistered test session table
	    				if(processResult.get("successdetails") != null){
	    					List<Long> thisSuccessTestSessions = (List<Long>) processResult.get("successdetails");
	        				if(successTestSessions.size() > 0){
	        					for(Long testSessionId : thisSuccessTestSessions){
	        						BatchRegisteredTestSessions batchRegisteredTestSessionsRec = new BatchRegisteredTestSessions();
	        						batchRegisteredTestSessionsRec.setBatchRegistrationId(batchRegistrationId);
	        						batchRegisteredTestSessionsRec.setTestSessionId(testSessionId);
	        						batchRegistrationService.insertBatchRegisteredTestSessions(batchRegisteredTestSessionsRec);
	        					}
	        				}
	    				}
	    				successAndFailureCounts = new HashMap<String, Integer>();
	    				successAndFailureCounts.put("success", (Integer) processResult.get("success"));
	    				successAndFailureCounts.put("failed", (Integer) processResult.get("failed"));
	    			}
    			}
    		}
            result.put("STATUS", "COMPLETED");
        } catch (Exception e) {
            logger.error("Exception", e);
            result.put("STATUS", "ERROR");
            result.put("ERRORS", "Unknown error occured.");
        }

        return new AsyncResult<Map<String, Object>>(result);
    }
    /*
    private Map<String, Object> processBatchRegProcess(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long assessmentId, Long contractingOrgId){
    	List<Enrollment> enrollments = enrollmentService.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourseId, contractingOrgId);
    	Map<String, Object> processResult = new HashMap<String, Object>();
    	processResult.put("enrollmentsFound", false);
    	
		if (enrollments != null && !enrollments.isEmpty()){
			for (Enrollment enrollment : enrollments) {
				processResult = studentsTestsService.batchCreateAutoRegistrationTestSessions(enrollment, subjectAreaId, testTypeId, gradeCourseId, assessmentId);
			}
			processResult.put("enrollmentsFound", true);
		} 
		
		return processResult;
    }*/
}
