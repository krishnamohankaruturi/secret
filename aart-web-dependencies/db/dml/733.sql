--dml/733.sql

INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'Troubleshoot reporting', 'TROUBLESHOOT_REPORTING', 'Write testlevel information to database for troubleshooting purposes', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
		categoryname, categorycode, categorydescription, categorytypeid, 
		externalid, originationcode, createddate, createduser, activeflag, 
		modifieddate, modifieduser)
VALUES ( 'FALSE', 'ENABLE_TROUBLESHOOTING_QUERIES', 'Enable troubleshooting and Write testlevel detailed information to database for each student to figure out issues',
(select id from  categorytype where typecode ='TROUBLESHOOT_REPORTING' ),null, null, 
now(), (select id from aartuser where username = 'cetesysadmin'), true,
now(), (select id from aartuser where username = 'cetesysadmin'));


update suppressedlevel set activeflag = false, modifieddate = now()
where activeflag = true 
and contentareaid in (
	select id from contentarea where abbreviatedname in ('ELA','M') and activeflag is true
)
and gradeCourseid in (
	select id from gradecourse  where contentareaid in (
		select id from contentarea where abbreviatedname in ('ELA','M') and activeflag is true
	) and abbreviatedname in ('3','10') and activeflag is true
);