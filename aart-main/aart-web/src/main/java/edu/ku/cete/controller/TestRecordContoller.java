package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.controller.test.SetupTestSessionController;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestRecordService;

@Controller
public class TestRecordContoller {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(SetupTestSessionController.class);
	
	@Autowired
	TestRecordService testRecordService;

	@Autowired
	EnrollmentService enrollmentService;
	
	@Autowired
	OrganizationService organizationService;

	/**
	 * contentAreaService
	 */
	@Autowired
	private ContentAreaService contentAreaService;

	@RequestMapping(value = "createTestRecord.htm", method = RequestMethod.POST)
	@ResponseBody
	public final String createTestRecord(final HttpServletRequest request,@RequestParam("testSubject") String testSubjectId,
			@RequestParam("testType") String testTypeId,@RequestParam("orgChildrenIds") Long orgChildrenIds,
			@RequestParam("recordType") String recordType) {
		
	
		 String[] stateStudentId = request.getParameterValues("studentsList[stateStudentIdentifier][]");
		 String[] attendanceSchoolIdentifier = request.getParameterValues("studentsList[attendanceSchoolDisplayIdentifiers][]");
		 String[] currentSchoolYear = request.getParameterValues("studentsList[currentSchoolYears][]");
		 
		 List<TecRecord> tecRecords =new ArrayList<TecRecord>();
		 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 for (int i = 0; i < attendanceSchoolIdentifier.length; i++) {
		
			 TecRecord tecRecord = new TecRecord();
			 tecRecord.setAttendanceSchoolProgramIdentifier(attendanceSchoolIdentifier[i]);
			 tecRecord.setStateStudentIdentifier(stateStudentId[i]);
			 tecRecord.setSchoolYear(Integer.parseInt(currentSchoolYear[i]));
			 tecRecord.setTestType(testTypeId);
			 tecRecord.setSubject(testSubjectId);
			 tecRecord.setRecordType(recordType);
			 tecRecords.add(tecRecord);
		
		 }
		
		Organization org  =  organizationService.get(orgChildrenIds);		
		ContractingOrganizationTree contractingOrganizationTree = organizationService.getTree(org);
	
		try{
			if ( !tecRecords.isEmpty() && tecRecords.size() > 0) {
				for (TecRecord tecRecord : tecRecords) {					
					enrollmentService.cascadeAddOrUpdate(tecRecord, contractingOrganizationTree, userDetails.getUser());
				}
			} else {
				return "{\"result\":\"invalid data\"}";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "{\"result\":\"Failed\"}";
		}
		return "{\"result\":\"success\"}";
	}
	
	@RequestMapping(value = "getContentAreasByAssessmentProgramForTestRecord.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getContentAreasByAssessmentProgramId(final Long assessmentProgramId) {
		LOGGER.trace("Entering getContentAreasByAssessmentProgramId");

		List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgramforTestRecord(assessmentProgramId);
		Collections.sort(contentAreas, contentAreaComparator);

		LOGGER.trace("Leaving getContentAreasByAssessmentProgramId");
		return contentAreas;
	}
	
	static Comparator<ContentArea> contentAreaComparator = new Comparator<ContentArea>() {
		public int compare(ContentArea ca1, ContentArea ca2) {
			return ca1.getName().compareToIgnoreCase(ca2.getName());
		}
	};
}
