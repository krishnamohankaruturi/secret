package edu.ku.cete.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.feedback.FeedBack;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.FeedBackService;

@Controller
public class FeedBackController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackController.class);
	
	@Autowired
	private FeedBackService feedBackService;
	
	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "saveFeedback.htm", method = RequestMethod.POST)
	public final @ResponseBody String saveFeedback(@RequestParam(value = "fullname") String fullname,
			@RequestParam(value = "email") String email, @RequestParam(value = "feedback") String feedback,
			@RequestParam(value = "url") String url) throws Exception {
		LOGGER.debug("--> saveFeedback");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			
			// Persist in database
			FeedBack userFeedback = feedBackService.addUserFeedBack(fullname, email, feedback, url);
			emailService.sendUserFeedback(userFeedback);
			model.put("status", "success");
			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- saveFeedback");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving user feedback: " + e.getMessage(), e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

}
