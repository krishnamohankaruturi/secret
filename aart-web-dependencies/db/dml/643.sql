-- F460 Dynamic Bundled Report

INSERT INTO batchjobschedule(
            jobname, jobrefname, initmethod, cronexpression, scheduled, 
            allowedserver)
    VALUES ('Dynamic Bundled Report scheduler', 'dynamicbundledreportscheduler', 'startbatchDynamicBundledReportProcess', '0 0/5 * * * ?', false, 'localhost');

INSERT INTO categorytype(typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Report File Size By ContentArea', 'REPORTSIZE', 'Generated PDF Report File Size', null, null, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('2', '440', 'Generated pdf size for Mathematics DLM', (select id from categorytype where typecode = 'REPORTSIZE'), 
            null, 'AART_ORIG_CODE', now(), (select id from aartuser where username = 'cetesysadmin'), true, 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('111', '3', 'Generated pdf size for ELA KAP', (select id from categorytype where typecode = 'REPORTSIZE'), 
            null, 'AART_ORIG_CODE', now(), (select id from aartuser where username = 'cetesysadmin'), true, 
            now(), (select id from aartuser where username = 'cetesysadmin'));
			
update category set categoryname  = '112' where categorycode = '440' and categorytypeid in (select id from categorytype where typecode = 'REPORTSIZE');
			
INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('112', '441', 'Generated pdf size for Science KAP', (select id from categorytype where typecode = 'REPORTSIZE'), 
            null, 'AART_ORIG_CODE', now(), (select id from aartuser where username = 'cetesysadmin'), true, 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
	VALUES ('DYNA_BUNDLE_GENERAL_ASSESSMENT', 'Create new students(bundled)- general assessment', 'Reports-Performance Reports',now(), 
		(select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
	VALUES ('DYNA_BUNDLE_ALTERNATE_ASSESSMENT', 'Create new students(bundled)- alt assessment', 'Reports-Performance Reports',now(), 
		(select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
	VALUES ('DYN_BUND_CARPATH_ASSESSMENT', 'Create new students(bundled)- car path asment', 'Reports-Performance Reports',now(), 
		(select id from aartuser where username = 'cetesysadmin'), true,now(), (select id from aartuser where username = 'cetesysadmin'));
