package edu.ku.cete.controller.sif;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ku.cete.domain.Alert;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.sif.SifXMLUpload;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.SifXMLFileService;
import edu.ku.cete.service.report.BatchUploadService;

@RestController
public class SifController {

	@Value("${print.test.file.path}")
	private String FILE_PATH;

	@Resource
	private Job xmlBatchUploadJob;

	@Autowired
	private BatchUploadService batchUploadService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SifXMLFileService sifXMLFileService;

	protected Random fileNameSeed = new Random();

	private Logger LOGGER = LoggerFactory.getLogger(SifController.class);

	@RequestMapping(value = "/sif/xStudents", method = RequestMethod.POST)
	public @ResponseBody String enrollStudents(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.ENRL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xStudents", method = RequestMethod.PUT)
	public @ResponseBody String updateEnrollStudents(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.ENRL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xStudents", method = RequestMethod.DELETE)
	public @ResponseBody String unEnrollStudents(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.UNENRL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xRosters", method = RequestMethod.POST)
	public @ResponseBody String uploadRosters(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.ROSTER_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);

		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xRosters", method = RequestMethod.PUT)
	public @ResponseBody String updateRosters(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.ROSTER_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/studentProgramAssociations", method = RequestMethod.POST)
	public @ResponseBody String uploadTEC(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.TEC_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/studentProgramAssociations/status")
	private void getstudentProgramAssociationsStatus(@RequestParam("requestId") Long requestId,
			final HttpServletRequest request, final HttpServletResponse response) {
		getUploadStatus(requestId, request, response);
	}

	private String uploadFile(String payLoad, String categoryCode) {
		String batchId = null;
		try {
			List<String> categoryTypes = Arrays.asList("XML_RECORD_TYPE");
			Long fileTypeId = null;

			List<Category> fileTypes = categoryService.selectByCategoryType(categoryTypes);
			for (Category fileType : fileTypes) {
				if (categoryCode.equals(fileType.getCategoryCode())) {
					fileTypeId = fileType.getId();
					break;
				}
			}

			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			Organization contractingOrganization = user.getContractingOrganization();
			Long contractingOrganizationId = contractingOrganization.getId();

			String folderPath = "sif_uploads" + File.separator + contractingOrganizationId + File.separator
					+ user.getCurrentOrganizationId() + File.separator;

			BatchUpload upload = new BatchUpload();
			upload.setUploadTypeId(fileTypeId);
			Long assessmentProgramId = user.getCurrentAssessmentProgramId();
			upload.setAssessmentProgramId(assessmentProgramId);
			upload.setContentAreaId(Long.valueOf(0));
			upload.setSubmissionDate(new Date());
			upload.setCreatedUser(user.getId());
			upload.setStatus("Pending");
			upload.setSchoolYear(contractingOrganization.getCurrentSchoolYear().intValue());
			upload.setActiveFlag(true);

			upload.setStateId(contractingOrganizationId);
			if (user.getCurrentOrganizationType().equals("DT")) {
				upload.setDistrictId(user.getCurrentOrganizationId());
			}
			if (user.getCurrentOrganizationType().equals("SCH")) {
				upload.setDistrictId(organizationService
						.getAllParentsByOrgTypeCode(user.getCurrentOrganizationId(), "DT").get(0).getId());
				upload.setSchoolId(user.getCurrentOrganizationId());
			}

			upload.setSelectedOrgId(user.getCurrentOrganizationId());
			upload.setUploadedUserOrgId(user.getCurrentOrganizationId());
			upload.setUploadedUserGroupId(user.getCurrentGroupsId());


			if (!FILE_PATH.endsWith(File.separator)) {
				folderPath = FILE_PATH + File.separator + folderPath;
			}
			String fileName = "SIF SAMPLE FILE" + ".xml";
			upload.setFileName(fileName);

			String filePath = folderPath + fileName;
			upload.setFilePath(filePath);
			upload.setDocumentId(saveFileToDatabase(payLoad, categoryCode));
			upload.setAssessmentProgramName("");
			upload.setContentAreaName("");
			upload.setUploadType(categoryCode);
			upload.setCreatedUserDisplayName(user.getDisplayName());
			batchUploadService.insertBatchUpload(upload);
			batchId = upload.getId().toString();
		} catch (Exception e) {
			LOGGER.error("error while uploading file: ", e);
		}
		return batchId;
	}

	private Long saveFileToDatabase(String payLoad, String categoryCode) {
		SifXMLUpload sif = new SifXMLUpload();
		sif.setType(categoryCode);
		sif.setXml(payLoad);
		sifXMLFileService.insert(sif);

		return sif.getId();
	}

	@RequestMapping(value = "/sif/xStudents/status")
	private void getStudentEnrolmentStatus(@RequestParam("requestId") Long requestId, final HttpServletRequest request,
			final HttpServletResponse response) {
		getUploadStatus(requestId, request, response);
	}

	@RequestMapping(value = "/sif/xRosters/status")
	private void getRosterStatus(@RequestParam("requestId") Long requestId, final HttpServletRequest request,
			final HttpServletResponse response) {
		getUploadStatus(requestId, request, response);
	}

	@RequestMapping(value = "/sif/xLeas", method = RequestMethod.POST)
	public @ResponseBody String createxLeas(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.LEA_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xLeas", method = RequestMethod.PUT)
	public @ResponseBody String updatexLeas(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.LEA_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);

		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xLeas", method = RequestMethod.DELETE)
	public @ResponseBody String deletexLeas(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.DELETE_LEA_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xLeas/status")
	private void getxLeasStatus(@RequestParam("requestId") Long requestId, final HttpServletRequest request,
			final HttpServletResponse response) {
		getUploadStatus(requestId, request, response);
	}

	@RequestMapping(value = "/sif/xSchools", method = RequestMethod.POST)
	public @ResponseBody String createxSchools(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.SCHOOL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xSchools", method = RequestMethod.PUT)
	public @ResponseBody String updatexSchools(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.SCHOOL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xSchools", method = RequestMethod.DELETE)
	public @ResponseBody String deletexSchools(@RequestBody(required = true) String payLoad) {
		String categoryCode = SifUploadType.DELETE_SCHOOL_XML_RECORD_TYPE.toString();
		String isValid= sifXMLFileService.validateXML(payLoad, categoryCode);
		if(isValid!=null)
			return isValid;
		return uploadFile(payLoad, categoryCode);
	}

	@RequestMapping(value = "/sif/xSchools/status")
	private void getxSchoolsStatus(@RequestParam("requestId") Long requestId, final HttpServletRequest request,
			final HttpServletResponse response) {
		getUploadStatus(requestId, request, response);
	}

	private String getBatchStatus(BatchUpload upload) {
		String confirmaionMessage = "";
		if (null != upload) {
			confirmaionMessage = upload.getStatus();
		} else {
			confirmaionMessage = "NOT_FOUND";
		}
		return confirmaionMessage;
	}

	private void jaxbObjectToXML(Alert alt, OutputStream out) {
		try {
			JAXBContext context = JAXBContext.newInstance(Alert.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Write to File
			m.marshal(alt, out);
		} catch (JAXBException e) {
			LOGGER.error("Error while generating xml response.", e);
		}
	}

	private void getUploadStatus(Long uploadedId, final HttpServletRequest request,
			final HttpServletResponse response) {
		// See if there are any failure reasons
		List<BatchUploadReason> reasons = batchUploadService.findBatchUploadReasonsForId(uploadedId);
		// Create alert element
		Alert alert = new Alert();
		alert.setAlertId(uploadedId);
		BatchUpload upload = batchUploadService.selectByPrimaryKeyBatchUpload(Long.valueOf(uploadedId));
		if (reasons.isEmpty()) {
			// If there are no reasons, return sucess
			alert.setLevel("INFO");
			alert.setDescription(getBatchStatus(upload));
		} else {
			alert.setLevel("ERROR");

			StringBuffer errorMessage = new StringBuffer();
			for (BatchUploadReason reason : reasons) {
				String reasonString = "";

				if (reason.getReason() != null)
					reasonString = reason.getReason();

				String errMsg = reasonString;
				errorMessage.append(errMsg);
				errorMessage.append("\n");
			}
			alert.setDescription(errorMessage.toString());
			alert.setError(errorMessage.toString());

		}
		Long sifUploadId = upload.getDocumentId();
		SifXMLUpload up = sifXMLFileService.selectByPrimaryKey(sifUploadId);
		String xml = up.getXml();
		String xmlEscape= StringEscapeUtils.unescapeXml(xml);

		alert.setBody(xmlEscape);
		response.setContentType("application/xml");
		try {
			jaxbObjectToXML(alert, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			LOGGER.error("Error while processing SIF xml ", e);
		}

	}
}