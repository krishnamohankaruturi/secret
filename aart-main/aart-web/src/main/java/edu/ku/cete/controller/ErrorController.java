package edu.ku.cete.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.util.CommonConstants;

/**
 * 
 * @author neil.howerton
 * 
 */
@Controller
public class ErrorController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@Autowired
	private AppConfigurationService appConfigService;

	/**
	 * Generic error handler for the application. Any unhandled exception or
	 * error code is caught and redirected here.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "/generalError.htm")
	public final ModelAndView handleErrors(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		String accept = request.getHeader("accept");
		if (accept != null && accept.contains(MediaType.APPLICATION_JSON)) {
			// this means an exception was being thrown while an ajax request
			// was being processed.
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} else {
			if (SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication()
							.isAuthenticated()) {
				mav = new ModelAndView("error");
			} else {
				mav = new ModelAndView("errorNoNavigation");
			}
		}

		return mav;
	}

	/**
	 * Generic permission denied handler for the application.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "/permissionDenied.htm")
	public final ModelAndView handlePermissionDenied(
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		String accept = request.getHeader("accept");
		if (accept != null && accept.contains(MediaType.APPLICATION_JSON)) {
			// this means a permissions error occurred while an ajax request was
			// being processed.
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		} else {
			if (SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication()
							.isAuthenticated()) {
				mav = new ModelAndView("permissionDenied");
			} else {
				mav = new ModelAndView("permissionDeniedNoNavigation");
			}
		}

		return mav;
	}
	
	/**
	 * SSO Authorization Error page.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "/authError.htm")
	public final ModelAndView handleAuthError(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		String accept = request.getHeader("accept");
		
		Map<String, AppConfiguration> appConfigurationMap =  appConfigService.selectIdMapByAttributeType(CommonConstants.APP_CONFIGURATION_ATTR_TYPE_FOR_SSO_ERROR_MESSAGES);
		AppConfiguration genericError = appConfigurationMap.get(CommonConstants.APP_CONFIGURATION_SSO_GENERIC);
		
		if (accept != null && accept.contains(MediaType.APPLICATION_JSON)) {
			// this means a permissions error occurred while an ajax request was
			// being processed.
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		} else {
			mav = new ModelAndView("authErrorNoNavigation");
			mav.addObject("errorMessage", genericError.getAttributeValue());
		}

		return mav;
	}	
	
    @RequestMapping(value = "nojs.htm")
    public final String nojsView() {
    	return "nojs";
    }
}
