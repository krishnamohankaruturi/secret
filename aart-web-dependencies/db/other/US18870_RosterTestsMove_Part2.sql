-- State ID: 1860833 - correct educator: 681316 , Move MATH and ELA tests to this educator and cleanup testsessions

UPDATE enrollmentsrosters SET activeflag = true, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  in (14472834, 13825836);
	    
UPDATE enrollmentsrosters SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  in (14542640, 14542617);
	    
UPDATE testsession SET rosterid = 917047, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  in (2743923, 3710856) ;
	    
UPDATE testsession SET rosterid = 917048, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  in (888790) ;
	    
UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  in (2361608, 2361458) ;