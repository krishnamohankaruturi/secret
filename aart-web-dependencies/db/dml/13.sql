
UPDATE fieldspecification SET rejectifempty = true WHERE fieldname = 'legalFirstName' and mappedname ='Legal_First_Name';

UPDATE fieldspecification SET rejectifempty = true WHERE fieldname = 'legalLastName' and mappedname ='Legal_Last_Name';


UPDATE fieldspecification SET rejectifinvalid = true WHERE fieldname = 'legalFirstName' and mappedname ='Legal_First_Name';

UPDATE fieldspecification SET rejectifinvalid = true WHERE fieldname = 'legalLastName' and mappedname ='Legal_Last_Name';


UPDATE fieldspecification SET showerror = true WHERE fieldname = 'legalFirstName' and mappedname ='Legal_First_Name';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'legalLastName' and mappedname ='Legal_Last_Name';


UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'legalFirstName' and mappedname ='Legal_First_Name';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'legalLastName' and mappedname ='Legal_Last_Name';


  update profileitemattribute
  set
  attributename = 'brailleMarkType',
  modifieddate = now(),
  modifieduser = (select id from aartuser where username='cetesysadmin')
  where attributename = 'needBrailleMarkType';
  
 
UPDATE fieldspecification SET fieldlength = 40 WHERE fieldname = 'stateCourseCode' AND mappedname is null;
  
  
UPDATE fieldspecification SET showerror = true WHERE fieldname = 'comprehensiveRace' and mappedname ='Comprehensive_Race';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'aypSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'createDate' and mappedname ='Create_Date';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'recordCommonId' and mappedname ='Record_Common_ID';

-- Below 3 update queries are for increasing the column legth and its faster than Alter command.	
ALTER TABLE aartuser
   ALTER COLUMN firstname TYPE character varying(80);

ALTER TABLE aartuser
   ALTER COLUMN middlename TYPE character varying(80);

ALTER TABLE aartuser
   ALTER COLUMN surname TYPE character varying(80);
  
UPDATE fieldspecification SET maximum = null WHERE fieldname = 'stateStudentIdentifier' and mappedname ='State_Student_Identifier';

--tc rules for collections that already exist.

INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            createduser, modifieduser)
    (
Select tc.id as testcollectionid,cat.id as sessionruleid,
(Select id from aartuser where username='cetesysadmin') as createduser,
(Select id from aartuser where username='cetesysadmin') as modifieduser
from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in
 ('SYSTEM_DEFINED_ENROLLMENT_TO_TEST')
 and tc.id in (Select id from testcollection)
 and tc.id not in (Select testcollectionid
 from testcollectionssessionrules tcsr,category cat
 where tcsr.sessionruleid = cat.id and cat.categorycode='SYSTEM_DEFINED_ENROLLMENT_TO_TEST')
 );
 
INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            graceperiod,
            createduser, modifieduser)
    (
Select tc.id as testcollectionid,cat.id as sessionruleid,
5400000 as graceperiod,
(Select id from aartuser where username='cetesysadmin') as createduser,
(Select id from aartuser where username='cetesysadmin') as modifieduser
from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in
 ('GRACE_PERIOD')
 and tc.id in (Select id from testcollection)
 and tc.id not in (Select testcollectionid
 from testcollectionssessionrules tcsr,category cat
 where tcsr.sessionruleid = cat.id and cat.categorycode='GRACE_PERIOD')
 ); 
	