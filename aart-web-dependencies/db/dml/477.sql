-- 477.sql
-- US16972 : Data extract - KSDE TASC records for System Operations
-- permissions for the data extracts

DO
$BODY$
BEGIN
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_TASC' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TASC does not exist. Inserting...';
		INSERT INTO authorities(
				authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES ('DATA_EXTRACTS_KSDE_TASC', 'Create KSDE TASC Records Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TEST exists. Skipping...';
	END IF;
	
END;
$BODY$;

--DE11721
 update fieldspecification  set rejectifempty = true where fieldname = 'stateStudentIdentifier' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateStudentIdentifier'  )
);

--DE11719
update fieldspecification  set fieldtype = 'Date' where fieldname = 'exitDateStr' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitDateStr'  )
);            

update fieldspecification  set fieldtype = 'Date' where fieldname = 'dateOfBirth' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'dateOfBirth'  )
);     

update fieldspecification  set fieldtype = 'Date' where fieldname = 'schoolEntryDate' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'schoolEntryDate'  )
);     
update fieldspecification  set fieldtype = 'Date' where fieldname = 'districtEntryDate' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'districtEntryDate'  )
);     

update fieldspecification  set fieldtype = 'Date' where fieldname = 'stateEntryDate' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
 fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateEntryDate'  )
);

INSERT INTO fieldspecification(fieldname, fieldlength, rejectifempty, rejectifinvalid, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
    VALUES ('email', 254, true, false, true,
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), 
            (select id from aartuser where username = 'cetesysadmin'), false);

update fieldspecificationsrecordtypes 
set fieldspecificationid=(select id from fieldspecification where mappedname is null and fieldname='email' and formatregex is null)
where fieldspecificationid=(select id from fieldspecification where mappedname is null and fieldname='email' and formatregex is not null)
and recordtypeid=(select id from category where categorycode='PD_TRAINING_RECORD_TYPE');

--US16944: Test Management and Test Coordination - remove access to test sessions from prior school year
-- update CurrentSchoolYear column of TestSession table.
update testsession set schoolyear=2016 where id in(
select id from testsession where schoolyear is null and name ilike '2016_%');

update testsession set schoolyear=2015 where id in(
select id from testsession where schoolyear is null and name ilike '2015_%');

--populate school year from enrollment
DO
$BODY$ 
DECLARE
	row_id record;
BEGIN           
	FOR row_id IN select id from testsession where schoolyear is null and activeflag is true
	LOOP 
		update testsession set schoolyear = 
				(select e.currentschoolyear 
					from enrollment e JOIN studentstests st ON st.enrollmentid = e.id 
					WHERE st.testsessionid = row_id.id
					order by st.modifieddate desc limit 1)
			where id=row_id.id;			
	END LOOP;
END;
$BODY$;

--populate school year from roster
DO
$BODY$ 
DECLARE
	row_id record;
BEGIN           
	FOR row_id IN select id from testsession where activeflag is true and schoolyear is null and rosterid is not null
	LOOP 
		update testsession set schoolyear = 
				(select rs.currentschoolyear 
					from roster rs join testsession ts on ts.rosterid = rs.id
					WHERE ts.id = row_id.id and rs.activeflag is true
					order by rs.modifieddate desc limit 1)
			where id=row_id.id;			
	END LOOP;
END;
$BODY$;


--UPDATE the SCHOOLYEAR of TestSession table based on CREATEDDATE 
-- for the remaining active sessions which are not updated with queries using modified dates of ENROLLMENT OR ROSTER 
DO
$BODY$ 
DECLARE
	row_id record;
BEGIN           
	FOR row_id IN select id from testsession where schoolyear is null 
	LOOP 
		update testsession set schoolyear = 
				(case 
				     When createddate >= '2015-08-01' Then 2016
				     When createddate < '2015-08-01' and createddate >= '2014-08-01' Then 2015
				     When createddate < '2014-08-01' and createddate >= '2013-08-01' Then 2014
				     When createddate < '2013-08-01' and createddate >= '2012-08-01' Then 2013
				     When createddate < '2012-08-01' and createddate >= '2011-08-01' Then 2012
				     When createddate < '2011-08-01' and createddate >= '2010-08-01' Then 2011
				     When createddate < '2010-08-01' and createddate >= '2009-08-01' Then 2010
				     When createddate < '2009-08-01' and createddate >= '2008-08-01' Then 2009
				     When createddate < '2008-08-01' and createddate >= '2007-08-01' Then 2008
				     When createddate < '2007-08-01' and createddate >= '2006-08-01' Then 2007
                     When createddate is null Then null
                  end) 
			where id=row_id.id;			
	END LOOP;
END;
$BODY$;