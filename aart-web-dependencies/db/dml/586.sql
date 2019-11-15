--dml/*.sql ==> For ddl/586.sql
--US19159: Emails Related to Processing KIDS for 2017

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
  VALUES ('KIDS Email Scheduler', 'sendKIDSEmailsJobRunScheduler', 'run', '0 0 23 * * ?', FALSE, 'localhost');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('08/15/2015', 'KIDS_EMAIL_SCHEDULER_DATE',(select id from categorytype where typecode= 'WEB_SERVICE_RECORD_TYPE'),
    'This is the date until which summarized emails must be sent to BTC/DTCs of the KIDS records processed');

  
INSERT INTO categorytype (typename, typecode,typedescription, createduser, activeflag, modifieduser)
	VALUES ('KIDS_EMAIL_TEMPLATES', 'KIDS_EMAIL_TEMPLATES', 'KIDS_EMAIL_TEMPLATES',
	(Select id from aartuser where username='cetesysadmin'), true, 
	(Select id from aartuser where username='cetesysadmin'));

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('kidsEmailSubject', 'KIDS_Email_Subject',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
	'KIDS Email Subject');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testTSCreated', 'TEST_TSCreated',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: Additional test sessions will be generated for the student.');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testTSNotCreated', 'TEST_TSNotCreated',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: No additional test sessions will be generated for the student.');
    
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testDLMStudent', 'TEST_DLMStudent',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: No changes were made because the selected assessment program is not available for the student.');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testStudentExited', 'TEST_StudentExited',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: No changes were made to the student because the student has been exited from the school by a previously received TEST submission.');
    
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('exitOrgMismatch', 'EXIT_OrgMismatch',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'EXIT: The student is enrolled in a different school');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('tascMultipleEmailIds', 'TASC_MultipleEmailIds',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TASC: The email address received does not match the one on file for the educator.');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('tascMultipleEducatorIds', 'TASC_MultipleEducatorIds',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TASC: The educator ID received does not match the one on file for the educator');
