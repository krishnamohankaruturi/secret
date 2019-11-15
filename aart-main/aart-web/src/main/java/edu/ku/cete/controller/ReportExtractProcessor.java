package edu.ku.cete.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.util.DataReportTypeEnum;

@Component("reportExtractProcessor")
public class ReportExtractProcessor {

	private Logger LOGGER = LoggerFactory.getLogger(ReportExtractProcessor.class);

	@Autowired
	private DataReportService dataReportService;

	/*
	 * Changed during US16344 : for Extracting reports on TEst Form assign to
	 * TestCollection for quality check
	 */
	@Async
	public Future<Map<String, Object>> startExtract(UserDetailImpl userDetails, Long moduleReportId,
			DataReportTypeEnum type, Long orgId, Map<String, Object> additionalParams) {
		LOGGER.debug("--> startExtract");
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			result.put("STATUS", "INPROGRESS");
			String typeName = type.getName();
			switch (type.getId()) {
			case 2: // Enrollment
				dataReportService.startEnrollmentExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 3: // Accessibility Profile
				dataReportService.startAccessibilityExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 4: // Roster
				dataReportService.startRosterExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 5: // Users
				dataReportService.startUserDataExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 6: // Questar
				dataReportService.startQuestarDataExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 7: // TEC
				dataReportService.startTECExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 8: // DLM_TEST_STATUS
				dataReportService.startDLMTestStatusExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 10: // PNP_SUMMARY
				dataReportService.startPNPSummaryExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 11: // KSDE Test And TASC Records
				dataReportService.startKSDETestAndTascRecordsExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 13: // Test administration Record for KAP and AMP
				dataReportService.startTestAdminExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 14: // Test Tickets for KAP and AMP
				dataReportService.startTestTicketsExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 18: // DLM PD Training List
				// professionalDevelopmentService.startPDReportGeneration(userDetails.getUser(),
				// moduleReportId);
				dataReportService.startDLMPDTrainingListExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 19: // Test Form Assignments to Test Collections
				dataReportService.startTestAssignmentsExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 20: // Test Media Resource
				dataReportService.startFormMediaExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			// US16972 : start KSDE TASC Records extract based on recordId
			case 24:
				/*
				 * dataReportService.startTASCRecordsExtract(userDetails, moduleReportId, orgId,
				 * additionalParams,typeName);
				 */
				break;
			case 25: // Student UserName Password
				dataReportService.startUserNamePasswordExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 26: // Restricted Special Circumstance Code
				dataReportService.startRestrictedSpecialCircumstanceCode(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 34: // DLM_PD_TRAINING_STATUS(Training Status)
				dataReportService.startDLMPDStatus(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 35: // Security Agreement Completion
				dataReportService.startSecurityAgreementExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 36: // KELPA2 Test Administration Monitoring
				dataReportService.startKELPATestAdministrationMonitoringExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 37: // Monitor Scoring
				dataReportService.startMonitorScoringExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 38: // KAP Student Scores Current Students
				dataReportService.startKAPStudentScoreCurrentExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 39: // DLM Blueprint Coverage Summary
				dataReportService.startITIBlueprintCoverageSummaryExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 41: // DLM_GENERAL_FILE
				dataReportService.startDLMGeneralResearchFile(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 42: // DLM Special Circumstances File Extract
				dataReportService.startDLMSpecialCircumstanceExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 43: // DLM Incident File Extract
				dataReportService.startDLMIncidentExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 44: // Organization Extract
				dataReportService.startOrganizationSummaryExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 45: // first contact survey Extract
				dataReportService.firstContactSurveyExtractGeneration(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 46: // Testing Readiness Extract
				dataReportService.generateStudentCombinedReadinessExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 47: // Exit Student Extract
				dataReportService.generateStudentExitExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;
			case 48: // PNP Abridged Extract
           		dataReportService.generatePNPAbridgedExtract(moduleReportId, orgId, additionalParams,typeName);
        		break;
			case 50: //ISMART and ISMART2 Test Administration Monitoring Extract
          		dataReportService.startISmartTestAdministrationMonitoringExtract(userDetails, moduleReportId, orgId, additionalParams,typeName);
           		break;
			case 51: //KAP Student Scores Specified Student
          		dataReportService.startKAPStudentScoreSpecifiedExtract(userDetails, moduleReportId, orgId, additionalParams,typeName);
           		break;
			case 52: //KAP Student Scores Tested Students
          		dataReportService.startKAPStudentScoreTestedExtract(userDetails, moduleReportId, orgId, additionalParams,typeName);
           		break;
			case 53://PLTW Test Administration Data Extract
                dataReportService.startPLTWTestAdministrationDataExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
                break;
			case 54: // PLTW Testing Readiness Extract
				dataReportService.generatePLTWStudentCombinedReadinessExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break; 
			case 56: 
				dataReportService.startKELPA2StudentScoreCurrentExtract(userDetails, moduleReportId, orgId, additionalParams, typeName);
				break;	
				
			default:
				break;
			}
			result.put("STATUS", "COMPLETED");
		} catch (IOException e) {
			result.put("STATUS", "ERROR");
			result.put("ERRORS", "IOException occured.");
			dataReportService.updateModuleReportStatusToFailed(moduleReportId);
			LOGGER.error("startExtract IOException:", e);
		} catch (Exception e) {
			result.put("STATUS", "ERROR");
			result.put("ERRORS", "Unknown error occured.");
			dataReportService.updateModuleReportStatusToFailed(moduleReportId);
			LOGGER.error("startExtract Exception:", e);
		}
		
		LOGGER.debug("<-- startExtract");
		return new AsyncResult<Map<String, Object>>(result);
	}
}