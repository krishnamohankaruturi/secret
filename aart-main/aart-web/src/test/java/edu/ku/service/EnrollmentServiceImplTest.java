package edu.ku.service;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class EnrollmentServiceImplTest {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OrganizationService organizationService;    
    
    /**
     * USER_NAME constant variable
     */
    private final static String USER_NAME ="coloradosysAdmin";    
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testCascadeAddOrUpdate() {
        
        EnrollmentRecord enrollmentRecord = new EnrollmentRecord();
        
        enrollmentRecord.getEnrollment().setAypSchoolIdentifier("1234567891");
        enrollmentRecord.getEnrollment().setResidenceDistrictIdentifier("12345");
        enrollmentRecord.getStudent().setLegalLastName("RTestLname1");
        enrollmentRecord.getStudent().setLegalFirstName("ETestFname5");
        enrollmentRecord.getStudent().setLegalMiddleName("ETestMname5");
        enrollmentRecord.getStudent().setGenerationCode("jr");
        enrollmentRecord.getStudent().setGender(1);
        //enrollmentRecord.getStudent().setDateOfBirth(new Date());
        enrollmentRecord.getStudent().setDateOfBirthStr("2/10/1999");
        enrollmentRecord.setCurrentGradeLevel("15");
        enrollmentRecord.setLocalStudentIdentifier("1111111114");
        enrollmentRecord.getStudent().setStateStudentIdentifier("1111111114");
        enrollmentRecord.setCurrentSchoolYearStr("2012");
        //enrollmentRecord.setFundingSchool("1234");
        enrollmentRecord.getEnrollment().setAttendanceSchoolProgramIdentifier("SFES");
        enrollmentRecord.getEnrollment().setSchoolEntryDate(new Date());
        enrollmentRecord.getEnrollment().setDistrictEntryDate(new Date());
        enrollmentRecord.getEnrollment().setDistrictEntryDate(new Date());  
        enrollmentRecord.setComprehensiveRace("10000");
        enrollmentRecord.setPrimaryDisabilityCode("AM");
        enrollmentRecord.setGiftedStudent(true);
        enrollmentRecord.setFirstLanguage("13");
        
        //Set security context for the user
        UserDetailImpl userDetailImpl = getInitializedUser();      
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken
                    (
                            "AART", userDetailImpl, userDetailImpl.getAuthorities()
                    )
                );
        
        Organization currentContext = userService.getOrganizations(userDetailImpl.getUserId()).get(0);
        ContractingOrganizationTree contractingOrganizationTree = organizationService.getTree(currentContext);
        
        //enrollmentRecord = enrollmentService.cascadeAddOrUpdate(enrollmentRecord, contractingOrganizationTree, currentContext.getId());
        //Assert.assertTrue("No enrollmentsRecord ", enrollmentRecord== null);
        

    }
    
    /**
     * @return
     */
    private final UserDetailImpl getInitializedUser() {
        User user = userService.getByUserName(USER_NAME);
        return new UserDetailImpl(user);
    }    


}
