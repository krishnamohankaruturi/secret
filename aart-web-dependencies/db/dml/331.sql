--331.sql
INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Passed', 'PASSED', 'User completed the module by PASSING.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
            'AART_ORIG_CODE', now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
            true, now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Attempted', 'ATTEMPTED', 'User completed the module by NOT PASSING.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
            'AART_ORIG_CODE', now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
            true, now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'));


UPDATE usermodule set enrollmentstatusid=(select id from category where categorycode= 'PASSED' and categorytypeid=(select id from categorytype where typecode='USER_MODULE_STATUS'))
WHERE testresult is true and enrollmentstatusid=(select id from category where categorycode= 'COMPLETED' and categorytypeid=(select id from categorytype where typecode='USER_MODULE_STATUS'));

UPDATE usermodule set enrollmentstatusid=(select id from category where categorycode= 'ATTEMPTED' and categorytypeid=(select id from categorytype where typecode='USER_MODULE_STATUS'))
WHERE (testresult is NULL OR testresult is false) and enrollmentstatusid=(select id from category where categorycode= 'COMPLETED' and categorytypeid=(select id from categorytype where typecode='USER_MODULE_STATUS'));

DELETE FROM category 
where id=(select id from category where categorycode= 'COMPLETED' and categorytypeid=(select id from categorytype where typecode='USER_MODULE_STATUS'));

delete from orgassessmentprogram oap 
 where id in (select distinct oap.id from orgassessmentprogram oap
  inner join organization o on oap.organizationid=o.id
  where contractingorganization is false);

-- Populate grades of KAP batch processing  
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where cttsa.activeflag is true and ttsa.activeflag is true and tt.activeflag is true and 
	ttsa.assessmentid =(select id from assessment where assessmentcode='GL' and testingprogramid=(select id from testingprogram where programabbr='S' 
			and assessmentprogramid=(select id from assessmentprogram where abbreviatedname='KAP')))
	and tt.testtypecode='2' and ca.abbreviatedname IN ('M','ELA','Sci','SS') AND gc.abbreviatedname in ('3','4','5','6','7','8','10', '11');  