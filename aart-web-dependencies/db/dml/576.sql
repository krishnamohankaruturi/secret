--/dml/576.sql
--Temporary FC settings for previous year, keep this as temporary as we will be automating this from annual academic year resets page

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'AK' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'CO' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'IL' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'IA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'KS' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MO' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NH' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NJ' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NY' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'ND' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NC' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'OK' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'PA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'UT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'VT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'WV' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'WI' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MS' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'VA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = '123' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);


INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);


INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'BIE-Choctaw' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'BIE-Miccosukee' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MI' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCEOYST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCIMST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCYEST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'FLAT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),FALSE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2016, FALSE);

---2017 for missing states
INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = '123' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);


INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);


INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCEOYST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCIMST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'DLMQCYEST' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'FLAT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MI' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MS' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser, schoolyear, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'VA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 2017, FALSE);

