package edu.ku.cete.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.common.PnpStateSettings;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.ProfileAttributeContainerService;

@Controller
public class PnpOptionsController {

	private Logger LOGGER = LoggerFactory.getLogger(PnpOptionsController.class);

	@Autowired
	private ProfileAttributeContainerService profileAttributeContainerService;

	@RequestMapping(value = "savePNPoptions.htm", method = RequestMethod.POST)
	public final @ResponseBody String savePnpOptions(@RequestParam("categoryId") Long categoryId,
			@RequestParam("assessmentProgram") String assessmentProgram, @RequestParam("pianacId") Long pianacId,
			@RequestParam("viewOption") String viewOption) throws IOException {
		try {

			LOGGER.trace("Entering the pnpOptions method.");

			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();

			Integer updated = profileAttributeContainerService.updatePnpOptionSettings(categoryId, assessmentProgram,
					pianacId, viewOption, user.getId());
			if (updated.equals(0)) {
				profileAttributeContainerService.insertPnpOptionSettings(categoryId, assessmentProgram, pianacId,
						viewOption, user.getId());
			}
			
			List<Long> childPianacIds = profileAttributeContainerService.getChildPnpSettingIds(pianacId);
			if(childPianacIds != null){
				for(Long childPianacId : childPianacIds){
					if(viewOption.equals("disable")){
						updated = profileAttributeContainerService.updatePnpOptionSettings(categoryId, assessmentProgram,
								childPianacId, viewOption, user.getId());
						if (updated.equals(0)) {
							profileAttributeContainerService.insertPnpOptionSettings(categoryId, assessmentProgram, childPianacId,
									viewOption, user.getId());
						}
					}
				}
			}
			
			
			// Clear all the state overrides if there is a change at assessment program level.
			profileAttributeContainerService.clearStatePnpOptionSettings(assessmentProgram, pianacId, user.getId());
			
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			model.put("status", "success");
			String modelJson = mapper.writeValueAsString(model);

			LOGGER.trace("Leaving the pnpOptions method.");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while updating pnp settings: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getPnpStateSettings.htm", method = RequestMethod.POST)
	public final @ResponseBody String getPnpStateSettings(@RequestParam("assessmentProgram") String assessmentProgram,
			@RequestParam("pianacId") Long pianacId) throws IOException {
		try {

			LOGGER.trace("Entering the getPnpStateSettings method.");

			List<PnpStateSettings> pnpStateSettings = profileAttributeContainerService
					.getPnpStateSettings(assessmentProgram, pianacId);

			List<Long> childPnpSettingIds = profileAttributeContainerService
					.getChildPnpSettingIds(pianacId);
			if(childPnpSettingIds != null && !childPnpSettingIds.isEmpty()){
				for(PnpStateSettings pnpStateSetting : pnpStateSettings){
					pnpStateSetting.setChildSettingIds(childPnpSettingIds);
					for(Long childPnpSettingId : childPnpSettingIds){
						PnpStateSettings childPnpStateSettings = profileAttributeContainerService
								.getPnpStateSettingsByState(assessmentProgram, childPnpSettingId, pnpStateSetting.getStateId());
						pnpStateSetting.getChildSettings().put(childPnpSettingId, childPnpStateSettings);
					}
				}
			}

			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			model.put("status", "success");
			model.put("pnpStateSettings", pnpStateSettings);
			String modelJson = mapper.writeValueAsString(model);

			LOGGER.trace("Leaving the getPnpStateSettings method.");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while getting pnp state settings: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "saveStatePnpOptionsOverride.htm", method = RequestMethod.POST)
	public final @ResponseBody String saveStatePnpOptionsOverride(@RequestParam("stateId") Long stateId,
			@RequestParam("assessmentProgram") String assessmentProgram, @RequestParam("pianacId") Long pianacId,
			@RequestParam("viewOption") String viewOption) throws IOException {
		try {

			LOGGER.trace("Entering the saveStatePnpOptionsOverride method.");
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();

			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			Integer updated = profileAttributeContainerService.updateStatePnpOptionSettings(stateId, assessmentProgram,
					pianacId, viewOption, user.getId());
			if (updated.equals(0)) {
				profileAttributeContainerService.insertStatePnpOptionSettings(stateId, assessmentProgram, pianacId,
						viewOption, user.getId());
			}

			model.put("status", "success");
			String modelJson = mapper.writeValueAsString(model);

			LOGGER.trace("Leaving the saveStatePnpOptionsOverride method.");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving pnp state settings: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

}
