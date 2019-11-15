--ddl/604.sql

-- DE14517, user upload was failing when K-ELPA was the assessment program. The old regex '^[A-Z0-9]*$' did not accept the hyphen.
-- Fix: add the hyphen to the character class in the regex.
update fieldspecification
set formatregex = '^[-A-Z0-9]*$',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = (
  select fs.id
  from fieldspecificationsrecordtypes fsrt
  join fieldspecification fs on fsrt.fieldspecificationid = fs.id
  join category c on fsrt.recordtypeid = c.id
  where c.categorycode = 'USER_RECORD_TYPE'
  and fsrt.mappedname = 'Primary_Assessment_Program'
);

--KELPA Email Template for TEST Org Mismatch
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testOrgMismatch', 'TEST_TestOrgMismatch',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: No changes were made to the student''s information because the selected assessment program is not available for the student.');

    
-- US19252: DLM - correct data in blueprint table
update blueprint set numberrequired = 2 where gradecourseid = (select id from gradecourse where abbreviatedname = '8' and contentareaid = (select id from contentarea where abbreviatedname = 'M'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'M')
       and criteria = 4 and groupnumber = 41;

DO
$BODY$
DECLARE
   grade6_id BIGINT;
   ela_contentareaid BIGINT;
   grade6_ela_required_blueprint_id BIGINT;

BEGIN

     select id from contentarea where abbreviatedname = 'ELA' INTO ela_contentareaid;
     select id from gradecourse where abbreviatedname = '6' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA') INTO  grade6_id;
     select id from blueprint where criteria=2 and groupnumber=26 and contentareaid = ela_contentareaid and  gradecourseid = grade6_id INTO grade6_ela_required_blueprint_id;
     
insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(grade6_ela_required_blueprint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.RI.6.8' limit 1), 'ELA.EE.RI.6.8', 9);

UPDATE blueprintessentialelements SET ordernumber = 10 WHERE essentialelement = 'ELA.EE.L.6.5.a' 
            AND blueprintid=grade6_ela_required_blueprint_id;

 UPDATE blueprintessentialelements SET ordernumber = 11 WHERE essentialelement = 'ELA.EE.L.6.5.b' 
            AND blueprintid=grade6_ela_required_blueprint_id;

END;
$BODY$;
    