/**
 * 
 */
package edu.ku.cete.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.professionaldevelopment.ModuleReportMapper;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.WebServiceConsumerService;
import edu.ku.cete.util.AartResource;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.KSDESubjectAreaCodeEnum;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.json.DataReportJsonConverter;

/**
 * @author m802r921
 * 
 */
@Controller
public class WebServiceController implements MessageSourceAware {
	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory
			.getLog(WebServiceController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private WebServiceConsumerService wbcService;
	
	@Autowired
	private KSDEReturnFileProcessor ksdeReturnFileProcesor;
	
	@Autowired
	private AmpExtractFileProcessor ampExtractFileProcessor;
	
	@Autowired
	private DataReportService dataReportService;
	@Autowired
	private OrganizationTypeService orgTypeService;
	@Autowired
	private ModuleReportMapper moduleReportDao;
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private ContentAreaService contentAreaService;
	@Override
	public void setMessageSource(MessageSource msgSource) {
		this.messageSource = msgSource;
	}

	/**
	 * invoke the web service.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "immediateUpload.htm", method = RequestMethod.POST)
	public final ModelAndView invokeWebservice() {
		ModelAndView mv = new ModelAndView(AartResource.WEB_SERVICE_FOLDER
				+ java.io.File.separator + AartResource.IMMEDIATE_UPLOAD);
		
		mv = wbcService.invokeWebServices(mv);
		
		return mv;
	}

	/**
	 * @param model
	 *            {@link Model}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "immediateUpload.htm", method = RequestMethod.GET)
	public final ModelAndView view(Model model) {
		return new ModelAndView(AartResource.WEB_SERVICE_FOLDER
				+ java.io.File.separator + AartResource.IMMEDIATE_UPLOAD);
	}
	
	/**
	 * Invoke the KSDE Web Service
	 * @return {@link String}
	 */
	@RequestMapping(value = "invokewebservice.htm", method = RequestMethod.POST)
	public @ResponseBody String invokeKsdWebSerive() {
		ModelAndView mv = new ModelAndView();
		mv = wbcService.invokeWebServices(mv);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
			String modelJson = mapper.writeValueAsString(mv.getModel());
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Parse exception occured while converting the object." + e.getMessage());			
			return "{\"errorFound\":\"true\"}";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="generateksdeextract.htm",method=RequestMethod.POST)
	public @ResponseBody long generateKsdeExtract(@RequestParam(value="subject") String subject) throws IOException {
		String[] subjects = StringUtils.split(subject, ",");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		OrganizationType ot = orgTypeService.getByTypeCode("ST");
		DataReportTypeEnum type = KSDESubjectAreaCodeEnum.getKSDESubjectAreaCodeBySubjectCode(subject).getDataReportType();
		long orgId = user.getContractingOrgId();
		long orgTypeId = ot.getOrganizationTypeId();
		ModuleReport moduleReport = moduleReportDao.getMostRecentReportByTypeId(user.getId(), type.getId(), orgId);
		long moduleReportId = (moduleReport != null ? moduleReport.getId() : -1);
		Map<String, Object> params = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if(moduleReport!= null && StringUtils.isNotBlank(moduleReport.getJsonData())){
			params = objectMapper.readValue(moduleReport.getJsonData(), Map.class);
		} else {
			params = new HashMap<String, Object>();
			if (subject.equalsIgnoreCase("ELP")) {
				params.put("assessmentProgramCode", "KELPA2");
			}
		}
		moduleReportId = dataReportService.generateNewExtract(user, type, moduleReportId, orgId, orgTypeId, params);
		Future<Map<String, Object>> result = ksdeReturnFileProcesor.createKSDEReturnFile(userDetails, moduleReportId, Arrays.asList(subjects),params);
		return moduleReportId;
	}
	
	@RequestMapping(value = "getKSDEExtracts.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getKSDEExtracts(@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType,
	  		@RequestParam("filters") String filters) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		ArrayList<Short> typeIds = new ArrayList<Short>();
		ArrayList<DataReportTypeEnum> types = new ArrayList<DataReportTypeEnum>();
		for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
			if (rule.getAppCode().equals("KE")) {
				typeIds.add(rule.getId());
				types.add(rule);
			}
		}
		
		int totalCount = types.size() > 0 ? dataReportService.countReportsByType(user, typeIds) : 0;
		
		int currentPage = NumericUtil.parse(page, 1);
	    int limitCount = NumericUtil.parse(limitCountStr, 5);
	    int totalPages = NumericUtil.getPageCount(totalCount, limitCount);
		JQGridJSONModel jq;
		long alternateId = -1L;
		if (totalCount > 0){
			List<ModuleReport> reports = dataReportService.getReportsByType(user, typeIds);
			// populate any type not found from DB records
			Set<Short> typesFound = new HashSet<Short>();
			for (ModuleReport report : reports) {
				typesFound.add(report.getReportTypeId());
			}
			for (DataReportTypeEnum type : types) {
				if (!typesFound.contains(type.getId())) {
					ModuleReport rep = new ModuleReport();
					rep.setId(alternateId);
					rep.setReportTypeId(type.getId());
					rep.setReportType(StringUtils.EMPTY);
					reports.add(rep);
					totalCount++;
					alternateId=alternateId-1L;
				}
			}
			// F820 - Grids Sort Order
			Collections.sort(reports, reportComparator);
			jq = DataReportJsonConverter.convertToJQGrid(reports, totalCount, currentPage, totalPages);
		} else {
			// F820 - Grids Sort Order
			Collections.sort(types, reportComparatorType);
			jq = DataReportJsonConverter.generateEmptyJQGridForTypes(types, totalCount, currentPage, totalPages);
		}
		
		return jq;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="generateampextract.htm",method=RequestMethod.POST)
	public @ResponseBody long generateAmpExtract(@RequestParam(value="otw") String operationalTestWindowStr) throws IOException {
		String[] operationalTestWindows = StringUtils.split(operationalTestWindowStr, ",");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		OrganizationType ot = orgTypeService.getByTypeCode("ST");
		DataReportTypeEnum type = DataReportTypeEnum.AMP_DATA_EXTRACT;
		long orgId = user.getContractingOrgId();
		long orgTypeId = ot.getOrganizationTypeId();
		ModuleReport moduleReport = moduleReportDao.getMostRecentReportByTypeId(user.getId(), type.getId(), orgId);
		long moduleReportId = (moduleReport != null ? moduleReport.getId() : -1);
		Map<String, Object> params = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if(moduleReport!= null && StringUtils.isNotBlank(moduleReport.getJsonData())){
			params = objectMapper.readValue(moduleReport.getJsonData(), Map.class);
		}
		moduleReportId = dataReportService.generateNewExtract(user, type, moduleReportId, orgId, orgTypeId, params);
		
		Future<Map<String, Object>> result = ampExtractFileProcessor.createAmpExtractFile(userDetails, moduleReportId, Arrays.asList(operationalTestWindows),params);
		return moduleReportId;
	}
	
	@RequestMapping(value = "getAmpExtracts.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getAmpExtracts(@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType,
	  		@RequestParam("filters") String filters) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		ArrayList<Short> typeIds = new ArrayList<Short>();
		ArrayList<DataReportTypeEnum> types = new ArrayList<DataReportTypeEnum>();
		for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
			if (rule.getAppCode().equals("AE")) {
				typeIds.add(rule.getId());
				types.add(rule);
			}
		}
		
		int totalCount = types.size() > 0 ? dataReportService.countReportsByType(user, typeIds) : 0;
		
		int currentPage = NumericUtil.parse(page, 1);
	    int limitCount = NumericUtil.parse(limitCountStr, 5);
	    int totalPages = NumericUtil.getPageCount(totalCount, limitCount);
		JQGridJSONModel jq;
		if (totalCount > 0){
			List<ModuleReport> reports = dataReportService.getReportsByType(user, typeIds);
			// populate any type not found from DB records
			Set<Short> typesFound = new HashSet<Short>();
			for (ModuleReport report : reports) {
				typesFound.add(report.getReportTypeId());
			}
			for (DataReportTypeEnum type : types) {
				if (!typesFound.contains(type.getId())) {
					ModuleReport rep = new ModuleReport();
					rep.setId(-1L);
					rep.setReportTypeId(type.getId());
					reports.add(rep);
					totalCount++;
				}
			}
			jq = DataReportJsonConverter.convertToJQGrid(reports, totalCount, currentPage, totalPages);
		} else {
			jq = DataReportJsonConverter.generateEmptyJQGridForTypes(types, totalCount, currentPage, totalPages);
		}
		
		return jq;
	}
	
	@RequestMapping(value = "getAllExtractStates.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getAllStates() {
		LOGGER.trace("Entering the getAllStates method.");
       
        List<Organization> states = orgService.getByTypeId(CommonConstants.ORGANIZATION_STATE_CODE, "name");
        
        LOGGER.trace("Leaving the getAllStates method.");
        return states;
    }

	@RequestMapping(value = "getExtractAssessmentProgramsByOrganization.htm", method = RequestMethod.GET)
    public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsByOrganization(@RequestParam("organizationId") Long organizationId) {
		LOGGER.trace("Entering the getAssessmentProgramsByOrganization method.");
		
		List<AssessmentProgram> assessmentPrograms = assessmentProgramService.findByOrganizationId(organizationId);
        
        LOGGER.trace("Leaving the getAssessmentProgramsByOrganization method.");
        return assessmentPrograms;
    }
	
	@RequestMapping(value = "getExtractCoursesBasedOnAssessmentProgram.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getCoursesBasedOnAssessmentProgram(@RequestParam("assessmentProgramId")  Long assessmentProgramId){
		LOGGER.trace("Entering getCoursesBasedOnAssessmentProgram");
		
		List<ContentArea> contentAreas = contentAreaService.getGradeCoursesUsingAssessmentProgram(assessmentProgramId);
		
		LOGGER.trace("Leaving getCoursesBasedOnAssessmentProgram");
		return contentAreas;
	}
	
	static Comparator<DataReportTypeEnum> reportComparatorType = new Comparator<DataReportTypeEnum>(){
		public int compare(DataReportTypeEnum r1, DataReportTypeEnum r2){
			return r1.getName().compareTo( r2.getName());
		}
	};
	
	static Comparator<ModuleReport> reportComparator = new Comparator<ModuleReport>(){
		public int compare(ModuleReport r1, ModuleReport r2){
			return r1.getReportType().compareTo( r2.getReportType());
    	}
	};
}
