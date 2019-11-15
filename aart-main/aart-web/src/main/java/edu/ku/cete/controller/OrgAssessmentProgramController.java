/**
 *
 */
package edu.ku.cete.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.web.AssessmentProgramDto;

/**
 * @author neil.howerton
 *
 */
@Controller
public class OrgAssessmentProgramController {

	private static final String JSP_PAGE = "orgAssessmentsSetup";

	private final Logger logger = LoggerFactory.getLogger(OrgAssessmentProgramController.class);

	@Autowired
	private OrgAssessmentProgramService orgAssessmentProgramService;

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private GroupsDao groupsDao;

	/**
	 *
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "manageOrgAssessmentPrograms.htm")
	public final ModelAndView viewOrgAssessments() {
		ModelAndView mav = new ModelAndView(JSP_PAGE);
		mav.addObject("organizations", orgService.getAll());

		mav.addObject("assessmentPrograms", assessmentProgramService.getAll());

		mav.addObject("orgAssessmentPrograms", orgAssessmentProgramService.getAllWithAssociations());

		logger.trace("Directing user to the manageOrgAssessmentPrograms page.");
		return mav;
	}

	/**
	 *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	@RequestMapping(value = "addOrgAssessmentPrograms.htm", method = RequestMethod.POST)
	public final void addOrgAssessmentPrograms(HttpServletRequest request, HttpServletResponse response) {
		String[] organizationIds = request.getParameterValues("organizations");
		String[] apIds = request.getParameterValues("assessmentPrograms");

		logger.debug("Adding assessment program with ids : {} to organizations with ids : {}", apIds, organizationIds);

		// create the orgAssessmentPrograms
		if (organizationIds != null && apIds != null) {
			for (int i = 0; i < organizationIds.length; i++) {
				for (int j = 0; j < apIds.length; j++) {
					OrgAssessmentProgram orgAp = new OrgAssessmentProgram();
					try {
						orgAp.setAssessmentProgramId(Long.parseLong(apIds[j]));
						orgAp.setOrganizationId(Long.parseLong(organizationIds[i]));

						OrgAssessmentProgram orgAssessmentProgram = orgAssessmentProgramService
								.findByOrganizationAndAssessmentProgram(orgAp.getOrganizationId(),
										orgAp.getAssessmentProgramId());

						if (orgAssessmentProgram == null) {
							logger.debug(
									"Creating orgAssessmentProgram with assessmentProgramId = {} and organizationId = {}",
									orgAp.getAssessmentProgramId(), orgAp.getOrganizationId());
							orgAssessmentProgramService.insert(orgAp);
						}
					} catch (NumberFormatException e) {
						// log the exception
						logger.error(
								"Caught NumberFormatException, parameters passed in contained non-numeric value(s)");
					}
				}
			}
		}

		try {
			response.sendRedirect("manageOrgAssessmentPrograms.htm");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	@RequestMapping(value = "deleteOrgAssessmentProgram.htm")
	public final void deleteOrgAssessmentProgram(HttpServletRequest request, HttpServletResponse response) {
		String orgAssessmentProgramId = request.getParameter("orgAssessmentProgramId");

		if (StringUtils.isNotEmpty(orgAssessmentProgramId)) {
			Long orgApId = Long.parseLong(orgAssessmentProgramId);
			if (orgApId > 0) {
				logger.debug("Deleting orgAssessmentProgram record with id = {}", orgApId);
				orgAssessmentProgramService.deleteByPrimaryKey(orgApId);
			}
		}

		try {
			response.sendRedirect("manageOrgAssessmentPrograms.htm");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "getOrgAssessmentPrograms.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getOrgAssessmentPrograms(
			@RequestParam(value="editRoles",required=false) Boolean editRoles) {
		logger.trace("Entering the getAssessmentPrograms() method");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization current = user.getOrganization();
		Boolean isEditCall = Boolean.FALSE;
		Groups group = groupsDao.getGroup(userDetails.getUser().getCurrentGroupsId());
		
		if(editRoles==null)
		{
			editRoles=Boolean.FALSE;
		}
		isEditCall = editRoles && (group.getGroupCode().equalsIgnoreCase("SSAD"));

		// TODO check contract organization tree to get APs
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

		if (current != null) {
			if (current.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS") ||isEditCall) {
				List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
				for (AssessmentProgram ap : aps) {
					AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
					assessmentProgramDto.setAssessmentProgram(ap);
					assessmentPrograms.add(assessmentProgramDto);
				}
			} else {
				List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService
						.findByOrganizationId(current.getId());
				for (OrgAssessmentProgram oap : orgAssessProgs) {
					AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
					assessmentProgramDto.setAssessmentProgram(oap.getAssessmentProgram());
					assessmentPrograms.add(assessmentProgramDto);
				}
			}
		}

		logger.trace("Leaving the getAssessmentPrograms() method");
		return assessmentPrograms;
	}

	@RequestMapping(value = "getCurrentUserAndOrgAssessmentPrograms.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getUserIdandOrganizationId() {
		logger.trace("Entering the getAssessmentPrograms() method");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Organization contract = userDetails.getUser().getContractingOrganization();

		// TODO check contract organization tree to get APs
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

		if (contract != null) {
			List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findByUserIdAndOrganizationId(
					user.getId(), contract.getId(), userDetails.getUser().getCurrentOrganizationId(),
					user.getCurrentGroupsId());
			Set<AssessmentProgram> aps = new HashSet<AssessmentProgram>();
			for (OrgAssessmentProgram oap : orgAssessProgs) {
				oap.getAssessmentProgram().setIsDefault(oap.getIsDefault());
				aps.add(oap.getAssessmentProgram());
			}

			for (AssessmentProgram ap : aps) {
				AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
				assessmentProgramDto.setAssessmentProgram(ap);
				assessmentPrograms.add(assessmentProgramDto);
			}

		}

		logger.trace("Leaving the getAssessmentPrograms() method");
		return assessmentPrograms;
	}

	@RequestMapping(value = "getAssessmentProgramIdByOrganizationRoles.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getAssessmentProgramIdOrganization(
			@RequestParam(value = "organizationId", required = false) Long organizationId,
			@RequestParam(value = "organizationIdList[]", required = false) List<Long> organizationIdList,
			@RequestParam(value = "isEdit", required = false) Boolean forEdit) throws Exception {
		logger.trace("Entering the getAssessmentPrograms() method");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Boolean isEditCall = Boolean.FALSE;
		String contract = userDetails.getUser().getCurrentOrganizationType();
		Groups group = groupsDao.getGroup(userDetails.getUser().getCurrentGroupsId());
		Set<AssessmentProgramDto> assessmentPrograms = new HashSet<AssessmentProgramDto>();
		List<AssessmentProgramDto> retList = new ArrayList<AssessmentProgramDto>();

		if (organizationId == null && organizationIdList == null) {
			throw new Exception("No Organization Received From User");
		}
		if (organizationId != null && organizationIdList == null) {
			organizationIdList = new ArrayList<Long>();
			organizationIdList.add(organizationId);
		}
		if (forEdit == null) {
			forEdit = false;
		}
		isEditCall = forEdit && (group.getGroupCode().equalsIgnoreCase("SSAD"));

		// TODO check contract organization tree to get APs
		if (contract.equalsIgnoreCase("CONS") || isEditCall) {
			for (Long orgId : organizationIdList) {
				if (orgId != null) {
					List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findByOrganizationId(orgId);
					for (OrgAssessmentProgram oap : orgAssessProgs) {
						AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
						assessmentProgramDto.setAssessmentProgram(oap.getAssessmentProgram());
						assessmentPrograms.add(assessmentProgramDto);
					}
				}
			}
		} else {
			AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
			AssessmentProgram ap = assessmentProgramService
					.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
			assessmentProgramDto.setAssessmentProgram(ap);
			assessmentPrograms.add(assessmentProgramDto);

		}
		logger.trace("Leaving the getAssessmentPrograms() method");
		retList.addAll(assessmentPrograms);
		return retList;
	}

	@RequestMapping(value = "getAssessmentProgramsByUserSelected.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getAssessmentProgramsByUser() {
		logger.trace("Entering the getAssessmentPrograms() method");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
		AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
		assessmentProgramDto.setAssessmentProgram(ap);
		assessmentPrograms.add(assessmentProgramDto);

		logger.trace("Leaving the getAssessmentPrograms() method");
		return assessmentPrograms;
	}

	@RequestMapping(value = "getAssessmentProgramIdByOrganizationOnly.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getAssessmentProgramsByOrgId(
			@RequestParam("organizationId") Long organizationId) {
		List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findByOrganizationId(organizationId);
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
		Set<AssessmentProgram> aps = new HashSet<AssessmentProgram>();
		for (OrgAssessmentProgram oap : orgAssessProgs) {
			aps.add(oap.getAssessmentProgram());
		}
		for (AssessmentProgram ap : aps) {
			AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
			assessmentProgramDto.setAssessmentProgram(ap);
			assessmentPrograms.add(assessmentProgramDto);
		}
		return assessmentPrograms;
	}

	@RequestMapping(value = "getAssessmentProgramIdByOrganizations.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getAssessmentProgramsByOrgIds(
			@RequestParam("organizationIds[]") Long[] organizationIds) {

		List<Long> orgIds = new ArrayList<Long>();
		for (Long organizations : organizationIds) {
			orgIds.add(organizations);
		}
		List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findByOrganizationIds(orgIds);
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
		Set<AssessmentProgram> aps = new HashSet<AssessmentProgram>();
		for (OrgAssessmentProgram oap : orgAssessProgs) {
			aps.add(oap.getAssessmentProgram());
		}
		for (AssessmentProgram ap : aps) {
			AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
			assessmentProgramDto.setAssessmentProgram(ap);
			assessmentPrograms.add(assessmentProgramDto);
		}
		return assessmentPrograms;
	}

	@RequestMapping(value = "getExtractReportAssessmentPrograms.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgramDto> getExtractReportAssessmentPrograms(
			@RequestParam("extractTypeId") Short extractTypeId) {
		logger.trace("Entering the getExtractReportAssessmentPrograms() method");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Organization contractOrg = userDetails.getUser().getContractingOrganization();

		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

		if (contractOrg != null) {
			String permCode = DataReportTypeEnum.getById(extractTypeId).getPermissionCode();
			permCode = permCode.contains("DATA_EXTRACTS_") ? permCode : "DATA_EXTRACTS_" + permCode;
			List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.getExtractReportAssessmentPrograms(
					user.getId(), contractOrg.getId(), user.getCurrentOrganizationId(), user.getCurrentGroupsId(),
					user.getCurrentAssessmentProgramId(), permCode);
			Set<AssessmentProgram> aps = new HashSet<AssessmentProgram>();
			for (OrgAssessmentProgram oap : orgAssessProgs) {
				oap.getAssessmentProgram().setIsDefault(oap.getIsDefault());
				aps.add(oap.getAssessmentProgram());
			}

			for (AssessmentProgram ap : aps) {
				AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
				assessmentProgramDto.setAssessmentProgram(ap);
				assessmentPrograms.add(assessmentProgramDto);
			}

		}

		logger.trace("Leaving the getExtractReportAssessmentPrograms() method");
		return assessmentPrograms;
	}

}
