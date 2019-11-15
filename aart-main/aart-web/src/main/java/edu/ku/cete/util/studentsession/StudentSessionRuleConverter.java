/**
 * 
 */
package edu.ku.cete.util.studentsession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;

/**
 * @author mahesh
 *
 */
@Component
public class StudentSessionRuleConverter {
	
	@Autowired
	private SessionRulesConfiguration sessionRulesConfiguration;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSessionRuleConverter.class);
	/**
	 * @param studentSessionRuleCategories
	 * @return
	 */
	public final StudentSessionRule convertToStudentSessionRule(
			List<TestCollectionsSessionRules> testCollectionsSessionRulesList) {
		
		List<StudentSessionRule> studentSessionRules = new ArrayList<StudentSessionRule>();
		StudentSessionRule studentSessionRule = null;
		
		studentSessionRules = convert(testCollectionsSessionRulesList);
		
		if(studentSessionRules != null && 
				CollectionUtils.isNotEmpty(studentSessionRules)) {
			studentSessionRule = studentSessionRules.get(0);
		}
		return studentSessionRule;
	}
	/**
	 * @param studentSessionRuleCategories
	 * @return
	 */
	public final List<StudentSessionRule> convert(
			List<TestCollectionsSessionRules> testCollectionsSessionRulesList) {
		
		List<StudentSessionRule> studentSessionRules = new ArrayList<StudentSessionRule>();
		Map<Long,List<TestCollectionsSessionRules>> tcSessionRuleMap = new HashMap<Long, List<TestCollectionsSessionRules>>();
		
		//all rules for a given test collection is put.
		if(testCollectionsSessionRulesList != null
				&& CollectionUtils.isNotEmpty(testCollectionsSessionRulesList)) {

			for(TestCollectionsSessionRules testCollectionsSessionRules : testCollectionsSessionRulesList) {
				if (testCollectionsSessionRules != null) {
					//Set the rule
					testCollectionsSessionRules.setSessionRule(
							sessionRulesConfiguration.getStatus(testCollectionsSessionRules.getSessionRuleId()));
					
					//If null it should result in abrupt failure.
					//testCollectionsSessionRules.getSessionRule().getId();
					
					if (!tcSessionRuleMap.containsKey(testCollectionsSessionRules.getTestCollectionId())) {
						tcSessionRuleMap.put(
								testCollectionsSessionRules.getTestCollectionId(),new ArrayList<TestCollectionsSessionRules>());
					}
					tcSessionRuleMap.get(testCollectionsSessionRules.getTestCollectionId()).add(testCollectionsSessionRules);
				} else {
					LOGGER.debug(" testCollectionsSessionRules is null "+testCollectionsSessionRules);
				}
			}
			
			for(Long testCollectionId : tcSessionRuleMap.keySet()) {
				studentSessionRules.add(getStudentSessionRule(tcSessionRuleMap.get(testCollectionId)));
			}
			
		} else {
			LOGGER.debug(" no testCollectionsSessionRulesList found "+testCollectionsSessionRulesList);
		}
		return studentSessionRules;
	}
	
	/**
	 * Get the session rule for one test collection.
	 * 
	 * @param testCollectionsSessionRulesList
	 * @return
	 */
	public final StudentSessionRule getStudentSessionRule(
			List<TestCollectionsSessionRules> testCollectionsSessionRulesList) {
		StudentSessionRule resultStudentSessionRule = new StudentSessionRule();
		if(testCollectionsSessionRulesList != null
				&& CollectionUtils.isNotEmpty(testCollectionsSessionRulesList)) {
			for(TestCollectionsSessionRules
					testCollectionsSessionRules:testCollectionsSessionRulesList) {
				if(resultStudentSessionRule.getTestCollectionId() == null) {
					resultStudentSessionRule.setTestCollectionId(
							testCollectionsSessionRules.getTestCollectionId());
				}
				if (sessionRulesConfiguration.isGracePeriodSet(
								testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setGracePeriodSet(
						sessionRulesConfiguration.isGracePeriodSet(
								testCollectionsSessionRules.getSessionRule()));
				}
				if (sessionRulesConfiguration.isManualEnrollment(
								testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setManualEnrollment(
						sessionRulesConfiguration.isManualEnrollment(
								testCollectionsSessionRules.getSessionRule()));
				}
				if (sessionRulesConfiguration.isSystemEnrollment(
						testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setSystemDefinedEnrollment(
						sessionRulesConfiguration.isSystemEnrollment(
								testCollectionsSessionRules.getSessionRule()));
				}
				if (sessionRulesConfiguration.isTicketedAtTest(
								testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setTicketedAtTest(
						sessionRulesConfiguration.isTicketedAtTest(
								testCollectionsSessionRules.getSessionRule()));
				}
				if(sessionRulesConfiguration.isTicketedAtSection(
								testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setTicketedAtSection(
						sessionRulesConfiguration.isTicketedAtSection(
								testCollectionsSessionRules.getSessionRule()));
				}
				if (sessionRulesConfiguration.isNotRequiredToCompleteTest(
								testCollectionsSessionRules.getSessionRule())){
					resultStudentSessionRule.setNotRequiredToCompleteTest(
						sessionRulesConfiguration.isNotRequiredToCompleteTest(
								testCollectionsSessionRules.getSessionRule()));
				}
				if(resultStudentSessionRule.isGracePeriodSet() && 
						sessionRulesConfiguration.isGracePeriodSet(
								testCollectionsSessionRules.getSessionRule())
								) {
					//if the current rule is grace period then set the time out also.
					resultStudentSessionRule.setGracePeriod(
							testCollectionsSessionRules.getGracePeriod());						
					
				}

			}
		}
		return resultStudentSessionRule;
	}

}
