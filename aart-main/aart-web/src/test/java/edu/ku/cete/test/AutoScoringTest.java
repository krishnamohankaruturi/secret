package edu.ku.cete.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.ScoringAssignmentServices;

public class AutoScoringTest extends BaseTest{

	@Autowired
	ScoringAssignmentServices scoAssignmentServices;
	
	private User user;
	
	private Date date;
	
	@Before
	public void init(){
		user = new User();
		user.setId(13l);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse("20/10/2016");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRosterChange() throws Exception{
		//old roster = 507363  new roster = 507362   enrollmentid = 1097963
		
		//scoAssignmentServices.reAssignStudentsOnChangeRoster(1097963l, 507364l, 507367l, user);
		
		scoAssignmentServices.reAssignStudentsOnExitStudent(1097973l, date, user);
		System.out.println("Assignment changed");		
	}
}
