package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.domain.validation.FieldSpecificationExample;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.ksde.kids.result.KidsByDateInputParameter;
import edu.ku.cete.ksde.kids.result.KidsData;
import edu.ku.cete.ksde.rosters.result.RosterByDateInputParameter;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.service.CategoryService;

/**
 * @author m802r921
 *
 */
public class WebServiceHelper {
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(WebServiceHelper.class);
	/**
	 * initiate web service.
	 * Do not move these to a common class as there will be multiple sources.
	 * @param webServiceUrl {@link Category}
	 * @return {@link KIDSWebServiceSoap}
	 */
	public static final KIDSWebServiceSoap getWebServiceClient(final Category webServiceUrl) {
		KIDSWebServiceSoap client = null;
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

			//factory.getInInterceptors().add(new LoggingInInterceptor());
			//factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.setServiceClass(KIDSWebServiceSoap.class);
			factory.setAddress(webServiceUrl.getCategoryName());
			client = (KIDSWebServiceSoap) factory.create();
		} catch (Exception e) {
			LOGGER.error("Webservice client Initialization failed.", e);
		}
		return client;
	}
	
	/**
	 * @param categoryService {@link CategoryService}
	 * @param kansasAssessmentTags {@link String}
	 * @return {@link Map}
	 */
	public static final Map<String, Category>
	getKansasAssessments(CategoryService categoryService,
			String kansasAssessmentTags) {
		Map<String, Category> kansasAssessmentInputNames = new HashMap<String, Category>();
		List<Category> kansasAssessmentCodeCategories = categoryService.selectByCategoryType(
				kansasAssessmentTags);
		if (CollectionUtils.isEmpty(kansasAssessmentCodeCategories)) {
			LOGGER.debug("No categories of Kansas Assessments");
		} else {
			for (Category kansasAssessmentCode:kansasAssessmentCodeCategories) {
				kansasAssessmentInputNames.put(kansasAssessmentCode.getCategoryName(), kansasAssessmentCode);
			}
		}
		return kansasAssessmentInputNames;
	}
	/**
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param kidsByDateInputParameterMap {@link Map}
	 * @param categoryService {@link CategoryService}
	 * @param fieldSpecificationDao {@link FieldSpecificationDao}
	 * @return {@link KidsByDateInputParameter}
	 */
	public static final KidsByDateInputParameter getKidsByDateInputParameter(
			WebServiceInputConfiguration webServiceInputConfiguration,
			Map<String, Category> kidsByDateInputParameterMap,
			CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		//if any of the parameter is null the failure will be
		//caught in the controller/scheduler of the web service.
		KidsByDateInputParameter kidsByDateInputParameter
		= new KidsByDateInputParameter();

		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceStartTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceEndTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceSchoolYear());

 		//strFromDate,strToDate,currentSchoolYear
 		Map<String, FieldSpecification> fieldSpecificationMap
 		= WebServiceHelper.getFieldSpecificationMap(kidsByDateInputParameterMap.get(
 				webServiceInputConfiguration.getKidsByDateInputParameterCode()),
 				fieldSpecificationDao);
 		for (String webServiceInputParameterCode:webServiceInputParameterCodes) {
 			FieldSpecification fieldSpec = fieldSpecificationMap.get(webServiceInputParameterCode);
 			String categoryName
 			= kidsByDateInputParameterMap.get(webServiceInputParameterCode).getCategoryName();
 			fieldSpec.validate(kidsByDateInputParameter, categoryName);
 		}
 		return kidsByDateInputParameter;
	}

	/**
	 * update the start and end time so that it will take effect the next time.
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param kidsByDateInputParameterMap {@link Map}
	 * @param categoryService {@link CategoryService}
	 * @param kidsByDateInputParameter {@link KidsByDateInputParameter}
	 * @return
	 */
	public static final void updateKidsByDateInputParameter(
			WebServiceInputConfiguration webServiceInputConfiguration,
			Map<String, Category> kidsByDateInputParameterMap,
			CategoryService categoryService,
			KidsByDateInputParameter kidsByDateInputParameter) {
		//change the start time.
		kidsByDateInputParameterMap.get(webServiceInputConfiguration.getKansasWebServiceStartTime()).
		setCategoryName(kidsByDateInputParameter.getStrFrom());
		//change the end time
		kidsByDateInputParameterMap.get(webServiceInputConfiguration.getKansasWebServiceEndTime()).
		setCategoryName(kidsByDateInputParameter.getStrTo());

		categoryService.updateByPrimaryKey(kidsByDateInputParameterMap.get(
				webServiceInputConfiguration.getKansasWebServiceEndTime()));
		categoryService.updateByPrimaryKey(kidsByDateInputParameterMap.get(
				webServiceInputConfiguration.getKansasWebServiceStartTime()));

	}

	/**
	 * Returns the map of categories for getting the kidsByDateInputParameter.
	 *
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param webServiceRecordTypeCode {@link String}
	 * @param categoryService {@link CategoryService}
	 * @param fieldSpecificationDao {@link FieldSpecificationDao}
	 * @return {@link Map}
	 */
	public static final Map<String, Category> getKidsByDateInputParameterMap(
			WebServiceInputConfiguration webServiceInputConfiguration,
			String webServiceRecordTypeCode, CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceStartTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceEndTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceSchoolYear());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKidsByDateInputParameterCode());
 		return categoryService.selectByCategoryTypeAndCategoryCodes(webServiceRecordTypeCode,
				webServiceInputParameterCodes);
	}	
	
	/**
	 * @param uploadSpecification
	 * @param kansasWebServiceConfigTypeCode
	 * @param categoryService
	 * @param fieldSpecificationDao
	 * @return
	 */
	public static final Map<String, Category> getKidsByDateFrequencyMap(
			UploadSpecification uploadSpecification,
			String kansasWebServiceConfigTypeCode, CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				uploadSpecification.getKansasWebServiceScheduleFrequencyCode());
		webServiceInputParameterCodes.add(
				uploadSpecification.getKansasWebServiceScheduleFrequencyDelta());
		 		
		return categoryService.selectByCategoryTypeAndCategoryCodes(kansasWebServiceConfigTypeCode,
				webServiceInputParameterCodes);
	}
	
	/**
	 * initiate un-marshaller.
	 * When uploading from different sources, this will no longer be initialization.
	 * @return {@link XStream}
	 */
	public static final XStream getUnMarshaller() {
		XStream xStream = null;
        try {        	
			xStream = new XStream(new StaxDriver());
			xStream.alias("KIDS_Record", KidRecord.class);
			xStream.alias("KIDS_Data", KidsData.class);
		} catch (Exception e) {
			LOGGER.error("Unmarshaller Initialization failed.", e);
		}
        return xStream;
	}
	/**
	 * set kid fields specification.Retrieved every time to prevent any lock on the collection.
	 * returns mapped name , fieldspec combination.
	 * @param recordType {@link Category}
	 * @param fieldSpecificationDao {@link FieldSpecificationDao}
	 * @return {@link Map}
	 */
	public static Map<String, FieldSpecification>
	getFieldSpecificationMap(Category recordType, FieldSpecificationDao fieldSpecificationDao) {
		Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
		FieldSpecificationExample fieldSpecificationExample = new FieldSpecificationExample();
		if (recordType == null) {
			return fieldSpecificationMap;
		}
		fieldSpecificationExample.createCriteria().andRecordTypeIdEqualTo(recordType.getId());
		List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.selectByExample(fieldSpecificationExample);
		if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
			for (FieldSpecification fieldSpecification : fieldSpecifications) {				
				fieldSpecificationMap.put(fieldSpecification.getMappedName(), fieldSpecification);
			}
		}
		LOGGER.debug("Received field specification map "+ fieldSpecificationMap.size());
		return fieldSpecificationMap;
	}
	
	public static Map<String, FieldSpecification>
	selectFieldSpecificationMap(Category recordType, FieldSpecificationDao fieldSpecificationDao) {
		Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
		FieldSpecificationExample fieldSpecificationExample = new FieldSpecificationExample();
		if (recordType == null) {
			return fieldSpecificationMap;
		}
		fieldSpecificationExample.createCriteria().andRecordTypeIdEqualTo(recordType.getId());
		List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.getFieldSpecificationMap(fieldSpecificationExample);
		if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
			for (FieldSpecification fieldSpecification : fieldSpecifications) {				
				fieldSpecificationMap.put(fieldSpecification.getMappedName(), fieldSpecification);
			}
		}
		LOGGER.debug("Received field specification map "+ fieldSpecificationMap.size());
		return fieldSpecificationMap;
	}
	
	/**
	 * @return {@link Map}
	 */
	public static final Map<String, Object> getInitializedMessages() {
        Map<String, Object> messages = new HashMap<String, Object>();
        messages.put("startingTimeStamp",
        		(new Date()).toString());
        messages.put("endingTimeStamp",
        		(new Date()).toString());
        //initialize to blank, just in case web service config parameters are not present.
        messages.put("fileOrigination",
        		ParsingConstants.UNKNOWN);
        messages.put("recordsCreatedCount", ParsingConstants.UNKNOWN);
        messages.put("recordsUpdatedCount", ParsingConstants.UNKNOWN);
        messages.put("recordsRejectedCount", ParsingConstants.UNKNOWN);
        messages.put("timeTaken", ParsingConstants.UNKNOWN);
        messages.put("recordPullStartTime",
        		ParsingConstants.BLANK);
        messages.put("recordPullEndTime",
        		ParsingConstants.BLANK);
        messages.put("fileReferenceMessage",
        		ParsingConstants.BLANK);
        return messages;
	}
	
	/**
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param rosterByDateInputParameterMap {@link Map}
	 * @param categoryService {@link CategoryService}
	 * @param fieldSpecificationDao {@link FieldSpecificationDao}
	 * @return {@link RosterByDateInputParameter}
	 */
	public static final RosterByDateInputParameter getRosterByDateInputParameter(
			WebServiceInputConfiguration webServiceInputConfiguration,
			Map<String, Category> rosterByDateInputParameterMap,
			CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		//if any of the parameter is null the failure will be
		//caught in the controller/scheduler of the web service.
		RosterByDateInputParameter rosterByDateInputParameter
		= new RosterByDateInputParameter();

		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getRosterWebServiceStartTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getRosterWebServiceEndTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceSchoolYear());

 		//strFromDate,strToDate,currentSchoolYear
 		Map<String, FieldSpecification> fieldSpecificationMap
 		= WebServiceHelper.getFieldSpecificationMap(rosterByDateInputParameterMap.get( 				
 				webServiceInputConfiguration.getRosterByDateInputParameterCode()),
 				fieldSpecificationDao);
 		for (String webServiceInputParameterCode:webServiceInputParameterCodes) {
 			FieldSpecification fieldSpec = fieldSpecificationMap.get(webServiceInputParameterCode);
 			String categoryName
 			= rosterByDateInputParameterMap.get(webServiceInputParameterCode).getCategoryName();
 			fieldSpec.validate(rosterByDateInputParameter, categoryName);
 		}
 		return rosterByDateInputParameter;
	}
	
	/**
	 * Returns the map of categories for getting the rosterByDateInputParameter.
	 *
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param webServiceRecordTypeCode {@link String}
	 * @param categoryService {@link CategoryService}
	 * @param fieldSpecificationDao {@link FieldSpecificationDao}
	 * @return {@link Map}
	 */
	public static final Map<String, Category> getRosterByDateInputParameterMap(
			WebServiceInputConfiguration webServiceInputConfiguration,
			String webServiceRecordTypeCode, CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getRosterWebServiceStartTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getRosterWebServiceEndTime());
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getKansasWebServiceSchoolYear());		
		webServiceInputParameterCodes.add(
				webServiceInputConfiguration.getRosterByDateInputParameterCode());
 		return categoryService.selectByCategoryTypeAndCategoryCodes(webServiceRecordTypeCode,
				webServiceInputParameterCodes);
	}
	
	/**
	 * @param uploadSpecification
	 * @param kansasWebServiceConfigTypeCode
	 * @param categoryService
	 * @param fieldSpecificationDao
	 * @return
	 */
	public static final Map<String, Category> getRosterByDateFrequencyMap(
			UploadSpecification uploadSpecification,
			String kansasWebServiceConfigTypeCode, CategoryService categoryService,
			FieldSpecificationDao fieldSpecificationDao) {
		List<String> webServiceInputParameterCodes = new ArrayList<String>();
		webServiceInputParameterCodes.add(
				uploadSpecification.getKansasWebServiceScheduleFrequencyCode());
		webServiceInputParameterCodes.add(
				uploadSpecification.getKansasWebServiceScheduleFrequencyDelta());
		 		
		return categoryService.selectByCategoryTypeAndCategoryCodes(kansasWebServiceConfigTypeCode,
				webServiceInputParameterCodes);
	}
	
	/**
	 * update the start and end time so that it will take effect the next time.
	 * @param webServiceInputConfiguration {@link WebServiceInputConfiguration}
	 * @param rostersByDateInputParameterMap {@link Map}
	 * @param categoryService {@link CategoryService}
	 * @param rostersByDateInputParameter {@link RostersByDateInputParameter}
	 * @return
	 */
	public static final void updateRostersByDateInputParameter(
			WebServiceInputConfiguration webServiceInputConfiguration,
			Map<String, Category> rostersByDateInputParameterMap,
			CategoryService categoryService,
			RosterByDateInputParameter rostersByDateInputParameter) {
		//change the start time.
		rostersByDateInputParameterMap.get(webServiceInputConfiguration.getRosterWebServiceStartTime()).
		setCategoryName(rostersByDateInputParameter.getStrRosterFromDate());
		//change the end time
		rostersByDateInputParameterMap.get(webServiceInputConfiguration.getRosterWebServiceEndTime()).
		setCategoryName(rostersByDateInputParameter.getStrRosterToDate());

		categoryService.updateByPrimaryKey(rostersByDateInputParameterMap.get(
				webServiceInputConfiguration.getRosterWebServiceEndTime()));
		categoryService.updateByPrimaryKey(rostersByDateInputParameterMap.get(
				webServiceInputConfiguration.getRosterWebServiceStartTime()));

	}
	
	public static void validateESOLFields(KidsData kidsData) {
		for(KidRecord kid : kidsData.getKids()){
			if (kid.getRecordType().equalsIgnoreCase("TEST")){
				String participationCode = kid.getEsolParticipationCode();
				String firstLang = kid.getFirstLanguage();
				Date esolEntry = kid.getEsolProgramEntryDate();
				Date esolEnding = kid.getEsolProgramEndingDate();
				Date usaEntry = kid.getUsaEntryDate();
				if (participationCode == null || participationCode.isEmpty()){
					kid.addInvalidField("ESOL Participation Code ",
							ParsingConstants.BLANK+participationCode, 
							false,
							InvalidTypes.EMPTY);
				}else{
					if (participationCode.equalsIgnoreCase("0")){
						if (firstLang != null && !firstLang.isEmpty()){
							if (!firstLang.equalsIgnoreCase("0")){
								kid.addInvalidField("First Language ",
										ParsingConstants.BLANK+firstLang, 
										false,
										InvalidTypes.IN_VALID);
							}
						}else{
							kid.addInvalidField("First Language ",
									ParsingConstants.BLANK+firstLang, 
									false,
									InvalidTypes.EMPTY);
						}
						if (esolEntry != null){
							kid.addInvalidField("ESOL Entry Date ",
									ParsingConstants.BLANK+esolEntry, 
									false,
									InvalidTypes.NOT_ALLOWED);
						}
						if (usaEntry != null){
							kid.addInvalidField("USA Entry Date ",
									ParsingConstants.BLANK+usaEntry, 
									false,
									InvalidTypes.NOT_ALLOWED);
						}
					}else if (participationCode.equalsIgnoreCase("1") 
							|| participationCode.equalsIgnoreCase("2") 
							|| participationCode.equalsIgnoreCase("3")
							|| participationCode.equalsIgnoreCase("5") 
							|| participationCode.equalsIgnoreCase("6")
							|| participationCode.equalsIgnoreCase("7")
							|| participationCode.equalsIgnoreCase("8")){
						if (esolEntry == null){
							kid.addInvalidField("ESOL Entry Date ",
									ParsingConstants.BLANK+esolEntry, 
									false,
									InvalidTypes.EMPTY);
						}
						if (usaEntry == null){
							kid.addInvalidField("USA Entry Date ",
									ParsingConstants.BLANK+usaEntry, 
									false,
									InvalidTypes.EMPTY);
						}
						if (firstLang == null || firstLang.isEmpty()){
							kid.addInvalidField("First Language ",
									ParsingConstants.BLANK+firstLang, 
									false,
									InvalidTypes.EMPTY);
						}else{
							if (firstLang.equalsIgnoreCase("0")){
								kid.addInvalidField("First Language ",
										ParsingConstants.BLANK+firstLang, 
										false,
										InvalidTypes.IN_VALID);
							}
						}
						if (participationCode.equalsIgnoreCase("7") || participationCode.equalsIgnoreCase("8")){
							if (esolEnding == null){
								kid.addInvalidField("ESOL Ending Date ",
										ParsingConstants.BLANK+esolEnding, 
										false,
										InvalidTypes.EMPTY);
							}
						}else{
							if (esolEnding != null){
								kid.addInvalidField("ESOL Ending Date ",
										ParsingConstants.BLANK+esolEnding, 
										false,
										InvalidTypes.NOT_ALLOWED);
							}
						}
					}
				}
			}else if (kid.getRecordType().equalsIgnoreCase("EXIT")){
				if (kid.getEsolParticipationCode() == null || kid.getEsolParticipationCode().isEmpty()){
					kid.addInvalidField("ESOL Participation Code ",
							ParsingConstants.BLANK+kid.getStudent().getEsolParticipationCode(),
							false,
							InvalidTypes.EMPTY);
				}
			}
		}
		
	}
}