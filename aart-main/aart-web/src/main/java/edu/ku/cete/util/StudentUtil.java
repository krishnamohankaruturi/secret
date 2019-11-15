/**
 *
 */
package edu.ku.cete.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentsTestsService;

/**
 * @author neil.howerton
 *
 */
@Component
public class StudentUtil {
    /**
     * The minimum length for a student password.
     */
    private static final int MIN_PWD_LEN = 5;

    /**
     * The maximum length for a student password.
     */
    private static final int MAX_PWD_LEN = 15;

    /**
     * The logger for the student util.
     */
    private final Logger logger = LoggerFactory.getLogger(StudentUtil.class);

    /**
     * The configurable student password length.
     */
    @Value("${studentpwd.length}")
    private int pwdLength;
    /**
     * The configurable student first name length.
     */
    @Value("${username.firstName.length}")
    private int usernameFirstNameLength;
    /**
     * The configurable student last name length.
     */
    @Value("${username.lastName.length}")
    private int usernameLastNameLength;
    
	@Value("${student.password.category.code}")
	private String STUDENT_PASSWORD_CATEGORY_CODE;
	
	@Value("${student.password.category.type.code}")
	private String STUDENT_PASSWORD_CATEGORY_TYPE_CODE;
	
	@Autowired
	private StudentsTestsService stService;
	
	@Autowired
	private CategoryService categoryService;
	
    /**
     * This method generated a student's username and password.
     * @param student {@link Student}
     */
    public final void generateStudentCredentials(Student student) {
        // generate the students username and password
        student.setUsername(generateUsername(student));
        int passwordLength=getSystemPasswordLength();
        student.setPassword(generateRandomWordForStudentPassWord(passwordLength));
    }
    
    // Brad: WHY IS THIS NOT JUST A STATIC METHOD??
    private int getSystemPasswordLength(){
    	 Category category=categoryService.selectByCategoryCodeAndType(STUDENT_PASSWORD_CATEGORY_CODE,STUDENT_PASSWORD_CATEGORY_TYPE_CODE);
 		return Integer.valueOf(category.getCategoryName());
    }
    /**
     * Tools Tab enhancement, Used to generate Student Password ONLY which will be later saved by the Tools Tab User
     */
    public final String generatePasswordString(){
 		int passwordLength=getSystemPasswordLength();
 		return generateRandomWordForStudentPassWord(passwordLength);
    }

    /**
     * This method generates a student's username.
     * @param student {@link Student}
     * @return String - the students generated username.
     */
    public String generateUsername(final Student student) {
    	//changed for US14180
    	//remove special characters
    	//first four digits of first name
    	//period
    	//first four digits of last name
        String username = null;
        try {
        
        	String firstName = removeSpecialCharacters(student.getLegalFirstName());
        	String lastName = removeSpecialCharacters(student.getLegalLastName());
			username = firstName.substring(	0, Math.min(usernameFirstNameLength, firstName.length()))
					+ ParsingConstants.PERIOD +
					lastName.substring(0, Math.min(usernameLastNameLength, lastName.length()));
					
			if(username == null || !StringUtils.hasText(username)) {
				logger.debug("Username is empty for student " + student);
			} else {
				username = username.toLowerCase();
			}
		} catch (RuntimeException e) {
			logger.error("Error generating username for student " , e);
		}
        return username;
    }

    public String generateRandomWord(int length) {
        int totalLength = length;
        int charsLeft = pwdLength;
        if (pwdLength < MIN_PWD_LEN) {
            logger.debug("Password length property was set too low, using system minimum.");
            totalLength = MIN_PWD_LEN;
            charsLeft = MIN_PWD_LEN;
        } else if (pwdLength > MAX_PWD_LEN) {
            logger.debug("Password length property was set too high, using system maxmimum.");
            totalLength = MAX_PWD_LEN;
            charsLeft = MAX_PWD_LEN;
        }

        logger.debug("Generating student's password.");

        //get a word from the database table used to generate student passwords.
        String pwd = stService.getRandomWord();
        charsLeft = totalLength - pwd.length();

        if (charsLeft > 0) {
            String pwdNum = RandomStringUtils.randomNumeric(charsLeft);
            logger.debug("Generated random number {}", pwdNum);
            pwd = pwd.concat(pwdNum);
        } else if (charsLeft < 0) {
            logger.debug("Word {} pulled from password table was longer than the set password length, truncating password.", pwd);
            pwd = pwd.substring(0, totalLength);
        }

        return pwd;
    }
    /**
     * This method generates a student's password using a set of commonly used words, and a random number generator.
     * @return String - the students generated password.
     */
    public String generateRandomWordForStudentPassWord(int length) {
        int totalLength = length;
        int charsLeft = length;
        if (totalLength < MIN_PWD_LEN) {
            logger.debug("Password length property was set too low, using system minimum.");
            totalLength = MIN_PWD_LEN;
            charsLeft = MIN_PWD_LEN;
        } else if (totalLength > MAX_PWD_LEN) {
            logger.debug("Password length property was set too high, using system maxmimum.");
            totalLength = MAX_PWD_LEN;
            charsLeft = MAX_PWD_LEN;
        }

        logger.debug("Generating student's password.");

        //get a word from the database table used to generate student passwords.
        String pwd = stService.getRandomWordForStudentPassword(totalLength);
        charsLeft = totalLength - pwd.length();

        if (charsLeft > 0) {
            String pwdNum = new String();
            while(pwdNum.contains("0")||pwdNum.contains("1")||StringUtils.isEmpty(pwdNum)){
            pwdNum= RandomStringUtils.randomNumeric(charsLeft);
            logger.debug("Generated random number {}", pwdNum);
            }
            pwd = pwd.concat(pwdNum);
        } else if (charsLeft < 0) {
            logger.debug("Word {} pulled from password table was longer than the set password length, truncating password.", pwd);
            pwd = pwd.substring(0, totalLength);
        }

        return pwd;
    }
    
    public static String removeSpecialCharacters(String s){
    	if(s != null) {
    		StringBuffer sb = new StringBuffer();
		    for(int i=0;i<s.length();i++) {
		        char ch = s.charAt(i);
		        if (Character.isLetter(ch))
		            sb.append(ch);
		    }
		    return sb.toString();  
    	}
    	return s;
	}
    
}
