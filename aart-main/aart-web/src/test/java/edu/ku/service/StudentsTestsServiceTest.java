/**
 * 
 */
package edu.ku.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.impl.StudentsTestsServiceImpl;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class StudentsTestsServiceTest {
	/**
	 * studentsTestsService.
	 */
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	/**
	 * Students Tests Service Implementation.
	 */

	private StudentsTestsServiceImpl studentsTestsServiceImpl;
	/**
	 * studentsTestsDao
	 */
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	
	/**
	 * studentsTestSectionsDao
	 */
	@Autowired
	private StudentsTestSectionsDao studentsTestSectionsDao;
	
	/**
	 * studentDao
	 */
	@Autowired
	private StudentDao studentDao; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		 studentsTestsServiceImpl = new StudentsTestsServiceImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#countByExample(edu.ku.cete.domain.StudentsTestsExample)}.
	 */
	//@Test
	public final void testCountByExample() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#deleteByExample(edu.ku.cete.domain.StudentsTestsExample)}.
	 */
	//@Test
	public final void testDeleteByExample() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#deleteByPrimaryKey(java.lang.Long)}.
	 */
	//@Test
	public final void testDeleteByPrimaryKey() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#insert(edu.ku.cete.domain.StudentsTests)}.
	 */
	@Test
	public final void testInsert() {
		
	Date date= new Date();
	StudentsTests StudentTest = new StudentsTests();
	int size= studentsTestsService.countByExample(null);
	StudentTest.setId(20945L);
	StudentTest.setStudentId(410151L);
	StudentTest.setTestId(7L);
	StudentTest.setTestCollectionId(1L);
	StudentTest.setStatus(84L);
	StudentTest.setTestSessionId(1487L);
	StudentTest.setCreatedDate(date);
	StudentTest.setCreatedUser(12L);
	StudentTest.setActiveFlag(null);
	StudentTest.setModifiedUser(12L);
	StudentTest.setModifiedDate(date);
	
	studentsTestsService.insert(StudentTest);
	assertTrue(size < studentsTestsService.countByExample(null));
		    
	}
	/*public final void testInsert() {
		fail("Not yet implemented"); // TODO
	}*/

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#insertSelective(edu.ku.cete.domain.StudentsTests)}.
	 */
	//@Test
	public final void testInsertSelective() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#selectByExample(edu.ku.cete.domain.StudentsTestsExample)}.
	 */
	//@Test
	public final void testSelectByExample() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#selectByPrimaryKey(java.lang.Long)}.
	 */
	//@Test
	public final void testSelectByPrimaryKey() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#updateByExampleSelective(edu.ku.cete.domain.StudentsTests, edu.ku.cete.domain.StudentsTestsExample)}.
	 */
	//@Test
	public final void testUpdateByExampleSelective() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#updateByExample(edu.ku.cete.domain.StudentsTests, edu.ku.cete.domain.StudentsTestsExample)}.
	 */
	//@Test
	public final void testUpdateByExample() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#updateByPrimaryKeySelective(edu.ku.cete.domain.StudentsTests)}.
	 */
	//@Test
	public final void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#updateByPrimaryKey(edu.ku.cete.domain.StudentsTests)}.
	 */
	//@Test
	public final void testUpdateByPrimaryKey() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#createTestSessions(java.util.List, long, edu.ku.cete.domain.TestSession, java.lang.Long)}.
	 */
	//@Test
	public final void testCreateTestSessions() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#selectByExampleAssessmentIdAndRoster(edu.ku.cete.domain.StudentsTestsExample, long, long)}.
	 */
	//@Test
	public final void testSelectByExampleAssessmentIdAndRoster() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#manageTestSessions(java.util.List, java.util.Map, java.util.Map, long, long)}.
	 */
	//@Test
	public final void testManageTestSessions() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#findByTestSession(long)}.
	 */
	//@Test
	public final void testFindByTestSession() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#findByAssessmentProgramAndRoster(long, long)}.
	 */
	//@Test
	public final void testFindByAssessmentProgramAndRoster() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#findByAssessmentIdAndRoster(java.lang.Long, java.lang.Long)}.
	 */
	//@Test
	public final void testFindByAssessmentIdAndRoster() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#updateStudentsTestsStatus(java.util.List, java.lang.Long, java.lang.Long)}.
	 */
	//@Test
	public final void testUpdateStudentsTestsStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#closeStudentsTestsStatus(java.util.List, java.lang.Long)}.
	 */
	//@Test
	public final void testCloseStudentsTestsStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.StudentsTestsServiceImpl#reactivateStudentsTestSectionsStatus(java.util.Collection)}.
	 */
	@Test
	public final void testReactivateStudentsTestsStatus() {
		List<Long> studentsTestsIdsList = new ArrayList<Long>();
		studentsTestsIdsList.add((long) 7033);
		//INFO rollback is automatic.
		studentsTestsService.reactivateStudentsTestSectionsStatus(studentsTestsIdsList, 1234L);
	}

	@Test
	public final void testEnrolledStudents() {
		List<Long> toBeEnrolledStudentIds = new ArrayList<Long>();
		toBeEnrolledStudentIds.add((long) 7033);
		toBeEnrolledStudentIds.add((long) 7034);
		//studentsTestsService.insert(toBeEnrolledStudentIds);
		
		List<Long> toBeUnEnrolledStudentIds = new ArrayList<Long>();
		toBeUnEnrolledStudentIds.add((long) 7033);
		toBeUnEnrolledStudentIds.add((long) 7034);
		boolean bResponse = studentsTestsServiceImpl.editTestSession(toBeEnrolledStudentIds,toBeUnEnrolledStudentIds, 1486L, true);
		
		assertTrue(bResponse);
		//INFO rollback is automatic.
		
	}
	@Test
	public final void testDeleteByStudentsTestsIds() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		//Test studentsTestSectionsDao.deleteByStudentsTestsIds
		studentsTestSectionsDao.deleteByStudentsTestsIds(new ArrayList<Long>() {{
		    add(14512L);
		    add(14515L);		    
		}});
		
		//Test StudentsTestsDao.deleteByStudentsTestsIds	 
		studentsTestsDao.deleteByStudentsTestsIds(new ArrayList<Long>() {{
		    add(14512L);
		    add(14515L);		    
		}},userDetails.getUserId());
		
	}
	
	@Test
	public final void testGetStudentIdsByEnrollmentRosterIds() {
		
		//Test studentsTestSectionsDao.deleteByStudentsTestsIds
		List<Long> studentIds= studentDao.getStudentIdsByEnrollmentRosterIds(new ArrayList<Long>() {{
		    add(24971L);
		    add(24972L);		    
		}});				
		
	}
	
}
