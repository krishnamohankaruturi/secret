package edu.ku.cete.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.service.HealthCheckService;

@Controller
public class HealthCheckController extends BaseController {
	@Autowired
	private HealthCheckService healthCheckService;
	
	@RequestMapping(value = "healthCheck.htm")
	@ResponseBody
	public final String healthCheck(HttpServletRequest request, HttpServletResponse response) {
		String ret = "success";
		List<String> errors = healthCheckService.doHealthCheck();
		
		if (CollectionUtils.isEmpty(errors)) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			ret = "errors: " + errors.toString();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return ret;
	}
}
