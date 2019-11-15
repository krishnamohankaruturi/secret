package edu.ku.cete.domain.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import edu.ku.cete.domain.common.HexBinary;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.student.StudentProfileService;

/**
 * @author vittaly
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration()
public class PNPUploadTest {

	 /**
	 *enrollmentService
	 */
	@Autowired
	private EnrollmentService enrollmentService;
	
	 /**
	 *studentProfileService 
	 */
	@Autowired
	private StudentProfileService studentProfileService;
	 
	 /**
	 *userService 
	 */
	@Autowired
	private UserService userService;
	 
	 /**
	 *organizationService 
	 */
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * USER_NAME constant variable
	 */
	private final static String USER_NAME ="coloradosysAdmin";
	
	/**
	 * @return
	 */
	private final UserDetailImpl getInitializedUser() {
		User user = userService.getByUserName(USER_NAME);
		return new UserDetailImpl(user);
	}
	 		
	/**
	 * @throws IOException
	 * This test checks and creates the personalNeedsProfileRecord if its not present
	 * and if already exists then that record would get updated.
	 */
	@Test
	 public void insertOrUpdatePersonalNeedsProfile() throws IOException {		
		
		List<PersonalNeedsProfileRecord> personalNeedsProfileRecordList = new ArrayList<PersonalNeedsProfileRecord>(); 
		List<? extends StudentSchoolRelationInformation> studentSchoolRelations = null;
		Student student = new Student();
		PersonalNeedsProfileRecord personalNeedsProfileRecord = new PersonalNeedsProfileRecord();
				        
		//Set security context for the user
        UserDetailImpl userDetailImpl = getInitializedUser();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailImpl,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Mock data for the test
		student.setStateStudentIdentifier("1005");		
		
		/*personalNeedsProfileRecord.setAssessmentNeedBackgroundColor((new HexBinary("9933fb")));
		personalNeedsProfileRecord.setAssessmentNeedBrailleDotPressure(new Double(0));
		personalNeedsProfileRecord.setAssessmentNeedBrailleMarkType("bold");
		personalNeedsProfileRecord.setAssessmentNeedBrailleStatusCellType("left");
		personalNeedsProfileRecord.setResponsibleSchoolIdentifier("WCHS");
		personalNeedsProfileRecord.setStudent(student);	
		personalNeedsProfileRecord.setStudentId(new Long(5));
		personalNeedsProfileRecordList.add(personalNeedsProfileRecord);*/
		
		//Check if enrollment already exists
		//studentSchoolRelations = enrollmentService.verifyEnrollments(personalNeedsProfileRecordList);
		assert studentSchoolRelations != null;
		
		//Add or update profile record.
		//student = studentProfileService.addProfileToStudent(personalNeedsProfileRecord.getSelectedAttributeValues(),student);		
		assert student != null;
	}
	
}
