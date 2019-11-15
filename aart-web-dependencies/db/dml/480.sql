--dml/480.sql
INSERT INTO authorities(authority,displayname,objecttype,createduser,createddate,modifieduser, modifieddate) values
	('VIEW_INTERIM_THETA_VALUES','View Interim Theta Values','Test Management-Test Session', (Select id from aartuser where username='cetesysadmin'),now(), (Select id from aartuser where username='cetesysadmin'), now());
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Test Administrator (QC Person)'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_INTERIM_THETA_VALUES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='State System Administrator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_INTERIM_THETA_VALUES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Global System Administrator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_INTERIM_THETA_VALUES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='KAP'), 'ADP', 'Adaptive');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='KAP'), 'MLTSTG', 'Multi-stage');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='DLM'), 'FXD', 'Fixed');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='DLM'), 'MLTASGN', 'Multi-assign');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='DLM'), 'STDNTTRKR', 'Student Tracker');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='AMP'), 'MLTSTG', 'Multi-stage');
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
    VALUES ((select id from assessmentprogram where abbreviatedname='CPASS'), 'MLTSTG', 'Multi-stage');
