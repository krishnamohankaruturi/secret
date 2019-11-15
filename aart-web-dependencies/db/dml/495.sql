-- 495.sql 
--scripts from scriptsbees
INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Daily Access Codes', 'DAILY_ACCESS_CODES', 'Daily Access Codes', (select id from categorytype where typecode='SESSION_RULES'), 
            'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
            
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname)
                VALUES ((select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true), 'MLTASGNFT', 'Multi-assign FT');

update category set activeflag = true where categorycode = 'TICKETED_AT_TEST';

INSERT INTO authorities(authority, displayname, objecttype, createddate,createduser, activeflag, modifieddate, modifieduser)
     VALUES ('PERM_OTW_EDIT', 'Edit Operational Test Window', 'Test Management-Operational Test Window',CURRENT_TIMESTAMP,
     	(select id from aartuser where email='cete@ku.edu'), true, CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
     	