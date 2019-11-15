-- Inactivation roster as there are no active students

UPDATE enrollmentsrosters SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE enrollmentid = 2301347; 

UPDATE roster SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id IN (1063311, 1063312);