package edu.ku.cete.model.validation;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;

/**
 * Added for US-14985
 */
@Component
public class PasswordValidator {

  
    /**
     * UserDao holder.
     */

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${password.categoryCode}")
    private String typeCode;

 	 private  final static String PASSWORD_PATTERN_SMALL = "(?=.*[a-z]).{8,32}";
 	 private static final String PASSWORD_PATTERN_CAPS = "(?=.*[A-Z]).{8,32}";
	 private  final static String PASSWORD_PATTERN_NUMBER = "(?=.*[0-9]).{8,32}";
	 private static final String PASSWORD_PATTERN_SPECIAL_CHAR = "(?=.*[^A-Za-z0-9]).{8,32}";
	
    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
   
    public boolean validate(User user) throws ServiceException {
    	ArrayList<Category> userPasswordPolicyList = userService.getPasswordPolicyRules(typeCode);
    	String userPassword = user.getPassword();
    
    	String encodedPassword = passwordEncoder.encodePassword(user.getPassword(),
              user.getSalt());

    	/**
    	 * This method is for checking paasword pttern and checking password histpry of last one year
    	 * or as per given value.
    	 */
    	for (Category category : userPasswordPolicyList) {
    		if(category.getCategoryCode().equals("PASS_CAPS_LETTER") && category.getActiveFlag())
    		{
    			if(!Pattern.compile(PASSWORD_PATTERN_CAPS).matcher(userPassword).matches())
    			{
    				 throw new ServiceException(
    						 "{\"error\":\"Password doesn't contain Capital letter.Please provide a captial alphabatic letter\"}");
    			}
    		}
    		else if(category.getCategoryCode().equals("PASS_SMALL_LETTER") && category.getActiveFlag())
    		{
    			if(!Pattern.compile(PASSWORD_PATTERN_SMALL).matcher(userPassword).matches())
    			{
    				 throw new ServiceException(
    						 "{\"error\":\"Password doesn't contain small letter.Please provide a small alphabetic letter.\"}");
    			}
    		}
    		else if(category.getCategoryCode().equals("PASS_NUMBER") && category.getActiveFlag())
    		{
    			if(!Pattern.compile(PASSWORD_PATTERN_NUMBER).matcher(userPassword).matches())
    			{
    				 throw new ServiceException(
    						 "{\"error\":\"Password doesn't contain number. Please give atleast one numeric letter.\"}");
    			}
    		}
    		else if(category.getCategoryCode().equals("PASS_SPECIAL_CHAR") && category.getActiveFlag())
    		{
    			if(! Pattern.compile(PASSWORD_PATTERN_SPECIAL_CHAR).matcher(userPassword).matches())
    			{
    				 throw new ServiceException(
    						 "{\"error\":\"Password doesn't caontain any special Character. Please provide a special Character.\"}");
    			}
    		}
    		else if(category.getCategoryCode().equals("PASS_MIN_LENGTH") && category.getActiveFlag())
    		{
    			if(userPassword.length() < Integer.parseInt(category.getCategoryName()))
    			{
    	    		  throw new ServiceException(
    	    				  "{\"error\":\"Password length is less than expected length\"}");
    	    	}
       		}
    		else if(category.getCategoryCode().equals("PASS_MAX_LENGTH") && category.getActiveFlag())
    		{
    			if(userPassword.length() > Integer.parseInt(category.getCategoryName()))
    	    	{
    	    		throw new ServiceException(
    	    				 "{\"error\":\"Password length exceeded maximum length\"}");
    	    	}
    		}
    		else if(category.getCategoryCode().equals("PASS_HISTORY_CHK") && category.getActiveFlag())
    		{
    			 ArrayList<User> userPasswordList = userService.getPastYearPasswordHistory(user.getId(),Integer.parseInt(category.getCategoryName()));
    			 if (userPasswordList != null && userPasswordList.size() > 0) {
    	                for (User anUserPasswordList : userPasswordList) {
    	                    if (encodedPassword.equals(anUserPasswordList.getPassword())) {
    	                        throw new ServiceException(
    	                        		 "{\"error\":\"Password Not updated. Password already used last year. Choose a different password.\"}");
    	                    }
    	                }
    	            }
    		}
		}
     	return true;
    }	
}
