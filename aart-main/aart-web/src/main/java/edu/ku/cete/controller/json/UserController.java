package edu.ku.cete.controller.json;

import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;

/**
 * @author nicholas
 */
@Controller(value = "json.UserController")
@RequestMapping("/JSON/user")
public class UserController {

    /** Logger for this class and subclasses. */
    private final Log logger = LogFactory.getLog(getClass());

    /** User service. */
    private UserService userService;

    /** Form validator. */
    private Validator validator;

    /**
     * Get the Service wired.
     * @param newUserService {@link UserService}
     */
    @Autowired
    public UserController(final UserService newUserService) {
        this.userService = newUserService;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        setValidator(validatorFactory.getValidator());
    }

    /**
     * @return the validator
     */
    public final Validator getValidator() {
        return validator;
    }

    /**
     * @param newValidator the validator to set
     */
    public final void setValidator(final Validator newValidator) {
        this.validator = newValidator;
    }

    /**
     * Get the user by username.
     * @param userName UserName to search
     * @return List of {@link Accommodation}
     */
    @RequestMapping(value = "/getByUserName", method = RequestMethod.GET)
    @ResponseBody
    public final User getByUserName(final String userName) {

        User user = userService.getByUserName(userName);

        if (user == null) {
            logger.debug("No user found for getByUserName()");
            return null;
        }

        return user;
    }

//    /**
//     * Alter the enabled status of the user.
//     * @param userName UserName to search.
//     * @param enabled Enabled status to set user.
//     * @return The modified {@link User}
//     */
//    @RequestMapping(value = "/setEnabled", method = RequestMethod.GET)
//    @ResponseBody
//    public final User setEnabled(final String userName, final int enabled) {
//
//        User user = userService.getByUserName(userName);
//
//        if (user == null) {
//            logger.debug("No user found for setActive()");
//            return null;
//        }
//
//        user.setEnabled(enabled);
//
//        try {
//            userService.update(user);
//            return user;
//        } catch (ServiceException e) {
//            logger.error("Could not toggle user Enable");
//            return null;
//        }
//    }
}
