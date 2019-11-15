package edu.ku.cete.batch.dlm.st;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.TestSpecification;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.PoolTypeEnum;
import static edu.ku.cete.util.CommonConstants.DELAWARE_ABBR_NAME;
import static edu.ku.cete.util.CommonConstants.DC_ABBR_NAME;

public class StudentTrackerHelper {
	final static Log logger = LogFactory.getLog(StudentTrackerHelper.class);
	
	private static final List<String> GRADE3TO8 = Arrays.asList(new String[]{"3","4","5","6","7","8"});
	private static final String OKLAHOMA_DISPLAY_IDENTIFIER = "OK";
	private static final List<String> GRADE10TO12 = Arrays.asList(new String[]{"10","11","12"});
	private static final List<String> GRADE9TO12 = Arrays.asList(new String[]{"9","10","11","12"});	
	
	public static String getPoolType(Organization organization, String gradeCode, String contetAreaAbbreviatedName) {
		String porgPoolType = organization.getPoolType();
		String orgDisplayIdentifier = organization.getDisplayIdentifier();
		
		//for students in grades 3-8 will use Multi EE  End of Grade
		if(contetAreaAbbreviatedName.equalsIgnoreCase("SS")){
			// OK SS is using MULTIEEOFG in 2019
			if(OKLAHOMA_DISPLAY_IDENTIFIER.equalsIgnoreCase(orgDisplayIdentifier)) {
				return PoolTypeEnum.MULTIEEOFG.name();
			}else {
				return porgPoolType;
			}			
		}
		if(PoolTypeEnum.MULTIEEOFC.name().equals(porgPoolType) && GRADE3TO8.contains(gradeCode)) {
			return PoolTypeEnum.MULTIEEOFG.name();
		}
		if(contetAreaAbbreviatedName.equalsIgnoreCase("Sci")) {
			if(DELAWARE_ABBR_NAME.equalsIgnoreCase(orgDisplayIdentifier) && GRADE10TO12.contains(gradeCode)) {
				return PoolTypeEnum.MULTIEEOFC.name();
			}
			if(DC_ABBR_NAME.equalsIgnoreCase(orgDisplayIdentifier) && GRADE9TO12.contains(gradeCode)) {
				return PoolTypeEnum.MULTIEEOFC.name();
			} 
			if(PoolTypeEnum.SINGLEEE.name().equals(porgPoolType)) {
				return PoolTypeEnum.MULTIEEOFG.name();
			}
		}
		return porgPoolType;
	}

	public static List<ComplexityBand> getBandsByContentArea(List<ComplexityBand> allBands, String contentAreaCode){
		Map<String, List<ComplexityBand>> cbByContentArea = new HashMap<String, List<ComplexityBand>>();
		for(ComplexityBand complexityBand : allBands){
			String contentArea = complexityBand.getContentAreaCode();
			if(contentArea == null)
				contentArea = "Other";
			List<ComplexityBand> bandLevelInfo = cbByContentArea.get(contentArea);
			if(bandLevelInfo == null){
				bandLevelInfo = new ArrayList<ComplexityBand>();
			}
			bandLevelInfo.add(complexityBand);
			cbByContentArea.put(contentArea, bandLevelInfo);
		}
 		List<ComplexityBand> bandsByContentArea = new ArrayList<ComplexityBand>();
		if(!cbByContentArea.containsKey(contentAreaCode)){
			bandsByContentArea =  cbByContentArea.get("Other");
		}else{
			bandsByContentArea = cbByContentArea.get(contentAreaCode);
		}
		return bandsByContentArea;
	}
	
	public static Map<String, String> getMinMaxLevels(List<ComplexityBand> allBands, String contentAreaCode){
		List<String> bandCodes = new ArrayList<String>();
		List<ComplexityBand> bandsByContentArea = getBandsByContentArea(allBands, contentAreaCode);
		for(ComplexityBand band : bandsByContentArea ){
			bandCodes.add(band.getBandCode());
		}
		Collections.sort(bandCodes);
		Map<String, String> levelMinMax = new HashMap<String, String>();
		levelMinMax.put("min", bandCodes.get(0));
		levelMinMax.put("max", bandCodes.get(bandCodes.size() - 1));
		
		if(contentAreaCode != null && (contentAreaCode.equals("Sci") || "SS".equals(contentAreaCode))){
			levelMinMax.put("min", "1");
		} 
		return levelMinMax;
	}
	
	public static ComplexityBand getBandByCode(List<ComplexityBand> allBands, String bandCode) {
		for (ComplexityBand band : allBands) {
			if (band.getBandCode().equals(bandCode)) {
				return band;
			}
		}
		return null;
	}
	
	/*
	 * Replaced getSciBand method for more flexibility
	 */
	public static ComplexityBand getBandInContentArea(List<ComplexityBand> allBands, String bandCode, String contentAreaCode) {
		for (ComplexityBand band : allBands) {
			if (band != null &&
				band.getBandCode() != null &&
				band.getContentAreaCode() != null &&
				band.getBandCode().equals(bandCode) &&
				band.getContentAreaCode().equalsIgnoreCase(contentAreaCode)) {
				return band;
			}
		}
		return null;
	}
	
	public static boolean isLatestBandWriting(List<ComplexityBand> allBands, Long bandId) {
		for(ComplexityBand band : allBands) {
			if((StringUtils.equalsIgnoreCase(band.getBandCode(), CommonConstants.COMPLEXITY_BAND_EMERGENT) 
					|| StringUtils.equalsIgnoreCase(band.getBandCode(), CommonConstants.COMPLEXITY_BAND_CONVENTIONAL)) && band.getId().equals(bandId)) {
				return true;
			}
		}
		return false;
	}
	
	public static String constructCommonMessage(String message, StudentTracker trackerStudent, String poolType, 
			Long operationalTestWindowId, Organization contractOrganization, String errorMessage, Long bandId, String gcCode, Long testSpecId) {
		StringBuilder str = new StringBuilder();
		str.append(message).append(trackerStudent.getStudentId());
		if(trackerStudent.getEnrollment() != null) {
			str.append(", enrollment: ").append(trackerStudent.getEnrollment().getId());
		}
		str.append(", contentarea: ").append(trackerStudent.getContentAreaId());
		if(trackerStudent.getGradeCourseId() != null) {
			str.append(", grdeId: ").append(trackerStudent.getGradeCourseId());
		}
		Long complexityBandId = (trackerStudent.getRecommendedBand() != null)?trackerStudent.getRecommendedBand().getComplexityBandId():null;
		if(complexityBandId != null) {
			str.append(", complexity band id: ").append(complexityBandId);
		}
		str.append(", poolType:").append(poolType);
		str.append(", operationalWindowId: ").append(operationalTestWindowId);
		if(contractOrganization != null) {
			str.append(", contracting org: ").append(contractOrganization.getId())
				.append("(").append(contractOrganization.getDisplayIdentifier()).append(")");
		}
		if(StringUtils.isNotEmpty(errorMessage)) {
			str.append(", cause: ").append(errorMessage);
		}
		if(bandId != null) {
			str.append(", bandId: ").append(bandId);
		}
		if(StringUtils.isNotEmpty(gcCode)) {
			str.append(", course: ").append(gcCode);
		}
		if(testSpecId != null) {
			str.append(", testSpecId: ").append(testSpecId);
		}
		return str.toString();
	}
	
	public static String constructMessage(String message, StudentTracker trackerStudent, Long operationalTestWindowId, 
			String surveyStatus,List<String> testspecs) {
		StringBuilder str = new StringBuilder();
		str.append(message).append(trackerStudent.getStudentId());
		str.append(", operationalWindowId: ").append(operationalTestWindowId);
		if(StringUtils.isNotEmpty(surveyStatus)) {
			str.append(", survey status: ").append(surveyStatus);
		}
		if(trackerStudent.getId() != null) {
			str.append(", tracker: ").append(trackerStudent.getId());
		}
		if(testspecs != null) {
			str.append(", Test Overviews: ").append(testspecs);
		}		
		return str.toString();
	}
	
	public static String constructContentCodeMessage(String message, TestSpecification testSpec, int groupNumber) {
		StringBuilder str = new StringBuilder();
		str.append(message).append("test specification: ").append(testSpec.getSpecificationName());
		str.append(" test specification id: ").append(testSpec.getId());
		str.append(", with group number: ").append(groupNumber);
		return str.toString();
	}
	
	public static String constructNoTestCollectionMessage(String message, String poolType, String gradeCode, 
			ContentArea contentArea, List<String> contentCodes, int groupNumber, Long bandId, TestSpecification testSpec, Long testCollectionId) {
		StringBuilder str = new StringBuilder();		
		str.append(message);
		str.append(" for content pool:").append(poolType);
		str.append(", grade: ").append(gradeCode);
		str.append(", content area: ").append(contentArea.getAbbreviatedName()).append("(").append(contentArea.getId()).append(")");
		str.append(", content codes: ").append(contentCodes);
		if(testCollectionId != null) {
			str.append(", test collectionid: ").append(testCollectionId);
		}
		str.append(", group: ").append(groupNumber);
		str.append(", complexity band id: ").append(bandId);
		str.append(", test overview id: ").append(testSpec.getSpecificationName()).append("(").append(testSpec.getId()).append(")");
		return str.toString();
	}

	public static String constructMultiAssignMessage(String message, Enrollment enrollment, String gradeCode,
			ContentArea contentArea, Organization contractingOrganization, Long multiAssignTestWindowId,
			String orgPoolType, List<String> fieldTestEEs, Long recommendedBandId) {
		StringBuilder str = new StringBuilder();
		str.append(message).append(enrollment.getStudentId());
		str.append(", enrollment: ").append(enrollment.getId());
		str.append(", grade: ").append(gradeCode);
		str.append(", contentarea: ").append(contentArea.getId());
		str.append(", organization: ").append(contractingOrganization.getId())
			.append("(").append(contractingOrganization.getDisplayIdentifier()).append(")");
		str.append(", operational test window id : ").append(multiAssignTestWindowId);
		str.append(", poolType: ").append(orgPoolType);
		if(CollectionUtils.isNotEmpty(fieldTestEEs)) {
			str.append(" ,FieldTest EEs: ").append(fieldTestEEs);
		}
		if(recommendedBandId != null && recommendedBandId != 0l) {
			str.append(", complexity band id: ").append(recommendedBandId);
		}
		return str.toString();
	}

	public static String constructNoTestCollectionMessageForMultiAssign(String message, String orgPoolType,
			GradeCourse gradeCourse, ContentArea contentArea, Long recommendedBandId, float linkageLevelLowerBound,
			float linkageLevelUpperBound, Long multiAssignTestWindowId) {
		StringBuilder str = new StringBuilder();
		str.append(message).append(orgPoolType);
		str.append(", grade: ").append(gradeCourse.getAbbreviatedName())
			.append("(").append(gradeCourse.getId()).append(")");
		str.append(", contentarea: ").append(contentArea.getAbbreviatedName())
			.append("(").append(contentArea.getId()).append(")");
		str.append(", complexity band id: ").append(recommendedBandId);
		str.append(", avg linage levels: ").append(linkageLevelLowerBound).append("-").append(linkageLevelUpperBound);
		str.append(", operational test window id: ").append(multiAssignTestWindowId);
		return str.toString();
	}
	
	
}
