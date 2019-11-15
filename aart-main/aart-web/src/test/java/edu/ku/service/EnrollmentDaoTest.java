/**
 * 
 */
package edu.ku.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.ibatis.annotations.Param;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.AARTCollectionUtil;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class EnrollmentDaoTest {
	@Autowired
	private EnrollmentDao enrollmentDao;
	@Autowired
	private OrganizationService organizationService;
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
	/**
	 * Test method for {@link edu.ku.cete.service.impl.EnrollmentServiceImpl#getByCriteria(edu.ku.cete.domain.enrollment.EnrollmentExample)}.
	 */
	//@Test
	public final void getAllEnrollmentsByCriteria() {
		System.out.println("Testing getAllEnrollmentsByCriteria");
		List<Enrollment> enrollments= enrollmentDao.getByCriteria(null);
		Assert.assertNotNull("No enrollments received", enrollments);
		Assert.assertTrue("No enrollments received", enrollments.size() > 0);
		System.out.println("No Of Enrollments received "+enrollments.size());
		for(Enrollment enrollment:enrollments) {
			Assert.assertNotNull("Empty enrollments", enrollment);
		}
	}
	/**
	 * Test method for {@link edu.ku.cete.service.impl.EnrollmentServiceImpl#getBy(edu.ku.cete.domain.enrollment.EnrollmentExample)}.
	 */
	//@Test
	public final void getAllEnrollmentsByRosterCriteria() {
		System.out.println("Testing getAllEnrollmentsByRosterCriteria");
		EnrollmentExample enrollmentExample = new EnrollmentExample();
		EnrollmentExample.Criteria enrollmentCriteria = enrollmentExample.createCriteria();
		enrollmentCriteria.andRosterIdEqualTo(734);
		List<Enrollment> enrollments= enrollmentDao.getByCriteria(null);
		Assert.assertNotNull("No enrollments received", enrollments);
		Assert.assertTrue("No enrollments received", enrollments.size() > 0);
		System.out.println("No Of Enrollments received "+enrollments.size());
		for(Enrollment enrollment:enrollments) {
			Assert.assertNotNull("Empty enrollments", enrollment);
		}
	}
	/**
	 * Test method for {@link edu.ku.cete.service.impl.EnrollmentServiceImpl#getBy(edu.ku.cete.domain.enrollment.EnrollmentExample)}.
	 */
	//@Test
	public final void getAllEnrollmentsWithRoster() {
//		System.out.println("Testing getAllEnrollmentsWithRosterBy");		
//		List<StudentRoster> studentRosters= enrollmentDao.getEnrollmentWithRoster(9890L, null, false, null, 0, 0,null,null, null, null);
//		Assert.assertNotNull("No enrollments received", studentRosters);
//		Assert.assertTrue("No enrollments received", studentRosters.size() > 0);
//		System.out.println("No Of Enrollments received "+studentRosters.size());
//		for(StudentRoster studentRoster:studentRosters) {
//			Assert.assertNotNull("Empty enrollments", studentRoster);
//			Assert.assertNotNull("Empty Roster", studentRoster.getRoster().getId());
//			Assert.assertNotNull("Empty AttendanceSchoolId", studentRoster.getAttendanceSchoolId());
//			Assert.assertNotNull("Empty AttendanceSchool", studentRoster.getAttendanceSchool().getId());
//			Assert.assertNotNull("Empty Student", studentRoster.getStudent().getId());
//		}
	}
	/**
	 * Test method for {@link edu.ku.cete.service.impl.EnrollmentServiceImpl#getBy(edu.ku.cete.domain.enrollment.EnrollmentExample)}.
	 */
	//@Test
	public final void getEnrollmentsWithRosterByOrg() {
		System.out.println("getEnrollmentsWithRosterByOrg");
		
		List<Long> attendanceSchoolIds = new ArrayList<Long>();
		attendanceSchoolIds.add((long) 9379);
		attendanceSchoolIds.add((long) 9380);

		List<Long> userOrganizationIds = new ArrayList<Long>();
		userOrganizationIds.add((long) 9368);
		userOrganizationIds.add((long) 51);
		List<Organization> childOrganizations = organizationService.getAllChildren(
				userOrganizationIds,true);
		List<Long> childOrganizationIds = AARTCollectionUtil.getIds(
				childOrganizations);
		//TODO: Enable this to make working.
		/*List<TestSessionRoster> studentRosters
		= enrollmentDao.getEnrollmentWithRoster(
				attendanceSchoolIds, childOrganizationIds,
				null, true, "coursesectionname asc", 10,10);
		Assert.assertNotNull("No enrollments received", studentRosters);
		Assert.assertTrue("No enrollments received", studentRosters.size() > 0);
		Assert.assertTrue("Number of enrollments received does not match expected", studentRosters.size() == 10);
		System.out.println("No Of Enrollments received "+studentRosters.size());
		for(TestSessionRoster studentRoster:studentRosters) {
			Assert.assertNotNull("Empty enrollments", studentRoster);
			Assert.assertNotNull("Empty Roster", studentRoster.getRoster().getId());
			Assert.assertNotNull("Empty AttendanceSchoolId", studentRoster.getAttendanceSchoolId());
			Assert.assertNotNull("Empty AttendanceSchool", studentRoster.getAttendanceSchool().getId());
			Assert.assertNotNull("Empty Teacher", studentRoster.getEducator().getId());
			Assert.assertNotNull("Empty Student", studentRoster.getStudent().getId());
		}*/
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.EnrollmentServiceImpl#getBy(edu.ku.cete.domain.enrollment.EnrollmentExample)}.
	 */
	@Test
	public final void getEnrollmentsWithRosterByOrgForEducator() {
		System.out.println("getEnrollmentsWithRosterByOrg");
		
		Long educatorId = (long) 755;
		List<Long> attendanceSchoolIds = new ArrayList<Long>();
		attendanceSchoolIds.add((long) 9379);
		attendanceSchoolIds.add((long) 9380);

		List<Long> userOrganizationIds = new ArrayList<Long>();
		userOrganizationIds.add((long) 9368);
		userOrganizationIds.add((long) 51);
		List<Organization> childOrganizations = organizationService.getAllChildren(
				userOrganizationIds,true);
		List<Long> childOrganizationIds = AARTCollectionUtil.getIds(
				childOrganizations);
		//TODO: Enable this to make working.
		/*List<TestSessionRoster> studentRosters
		= enrollmentDao.getEnrollmentWithRoster(
				attendanceSchoolIds, childOrganizationIds,
				educatorId, false, "coursesectionname asc", 100,100);
		Assert.assertNotNull("No enrollments received", studentRosters);
		Assert.assertTrue("No enrollments received", studentRosters.size() > 0);
		Assert.assertTrue("Number of enrollments received does not match expected", studentRosters.size() == 100);
		System.out.println("No Of Enrollments received "+studentRosters.size());
		for(TestSessionRoster studentRoster:studentRosters) {
			Assert.assertNotNull("Empty enrollments", studentRoster);
			Assert.assertNotNull("Empty Roster", studentRoster.getRoster().getId());
			Assert.assertNotNull("Empty AttendanceSchoolId", studentRoster.getAttendanceSchoolId());
			Assert.assertNotNull("Empty AttendanceSchool", studentRoster.getAttendanceSchool().getId());
			Assert.assertNotNull("Empty Student", studentRoster.getStudent().getId());
			Assert.assertNotNull("Empty Teacher", studentRoster.getEducator().getId());
			Assert.assertNotNull("Empty State Course", studentRoster.getStateCourse().getId());
			Assert.assertNotNull("Empty Grade Course", studentRoster.getGradeCourse().getId());
			Assert.assertNotNull("Educator does not match", studentRoster.getRoster().getTeacherId().equals(educatorId));
		}*/
	}
	
}
