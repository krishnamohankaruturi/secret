/**
 * 
 */
package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.model.validation.PasswordValidator;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.EncryptionUtil;

/**
 * @author rajender.arebelly
 * 
 */
@Controller
public class CreateAccountController extends BaseController {

	private static final String CREATE_ACCOUNT_VIEW = "createAccount";
	private final Logger logger = LoggerFactory
			.getLogger(CreateAccountController.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserOrganizationsGroupsService userOrgsGroupsService;
	/**
	 * orgAssessProgService
	 */
	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private AssessmentProgramService assessSrvc;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ReCaptcha reCaptchaService;

	 @Autowired
	   	private PasswordValidator passwordValidator;

	/**
	 * This method renders the initial create account page.
	 * 
	 * @return {@link ModelAndView}
	 */
	
	
	/*@RequestMapping("createAccount/createAccount.htm")
	public final ModelAndView viewCreateAccount() {
		logger.trace("Entering the viewCreateAccount method.");
		logger.debug(
				"Leaving the viewCreateAccount method. Routing user to view with name {}",
				CREATE_ACCOUNT_VIEW);

		ModelAndView map = new ModelAndView(CREATE_ACCOUNT_VIEW);
		map.addObject("assessmentPrograms", assessSrvc.getAll());

		return map;
	}

    @RequestMapping(value = "createAccount/saveAccount.htm", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> createAccount(@ModelAttribute User user,@RequestParam("organizationId") Long organizationId,HttpServletRequest request) {
    	Map<String , String> result=new HashMap<String, String>();
    	 String challenge = request.getParameter("recaptcha_challenge_field");
         String response = request.getParameter("recaptcha_response_field");
         String remoteAddr = request.getRemoteAddr();
          
         ReCaptchaResponse reCaptchaResponse = reCaptchaService.checkAnswer(remoteAddr, challenge, response);
          
         if(reCaptchaResponse.isValid()) {
	    	if(userService.getByEmail(user.getEmail())==null){
	    		if(user.getUniqueCommonIdentifier()!=null && user.getUniqueCommonIdentifier().isEmpty()){
	    			user.setUniqueCommonIdentifier(null);
	    		}
	    		try{
					userService.register(user,organizationId);
	    		}catch(ServiceException e){
	    			result.put("Error", "Failed to send email");
	    		}
				result.put("msg", "success");
	    	}else{
	    		result.put("msg", "DUPLICATE");
	    	}
         }else{
	    		result.put("msg", "INVALIDCAPCTHA");
	    	}
		return result;
	}
*/
	@RequestMapping(value = "createAccount/activeAccount.htm", method = RequestMethod.GET)
	public ModelAndView activateAccount(@RequestParam("auth") String auth) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("newPassword");
		try {
			String email = EncryptionUtil.decrypt(auth);
			User user = userService.getByEmail(email);
			if (user == null) {
				modelAndView.addObject("Error", "Invalid request");
			} else if (user.getActiveFlag()) {
				modelAndView.addObject("Error",
						"Your account has been activated already.");
			} else {
				modelAndView.addObject("user", user);
			}
		} catch (ServiceException e) {
			modelAndView.addObject("Error", "Invalid request");
		}
		return modelAndView;
	}


    /**
     * Changed for US-14985
     */
	@RequestMapping(value = "createAccount/newPassword.htm", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> activateAndChangePassword(@RequestParam String auth,
			@RequestParam String password, @RequestParam String cfrmPassword) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String email = EncryptionUtil.decrypt(auth);
			User user = userService.getByEmail(email);
			user.setPassword(password);
			user.setActiveFlag(true);
			passwordValidator.validate(user);
			userService.updateWithEncodedPassword(user, false, null);
			result.put("SUCCESS", "SUCCESS");
		} catch (ServiceException e) {
			result.put("ERROR", "Invalid request");
		}
		return result;
	}

	/**
	 * @param assessmentProgramId
	 *            {@link long}
	 * @return List<testingPrograms>
	 */
	@RequestMapping(value = "createAccount/getOrgsByAssessments.htm", method = RequestMethod.POST)
	private @ResponseBody
	List<OrgAssessmentProgram> getTestingProgramByAssessmentProgramId(
			final long assessmentProgramId) {

		List<OrgAssessmentProgram> assessmentOrgs = new ArrayList<OrgAssessmentProgram>();
		assessmentOrgs = orgAssessProgService
				.findByAssessmentProgramId(assessmentProgramId);

		for (OrgAssessmentProgram orgAssessmentProgram : assessmentOrgs) {
			orgAssessmentProgram.setOrganization(orgService
					.get(orgAssessmentProgram.getOrganizationId()));
		}

		return assessmentOrgs;
	}
}
