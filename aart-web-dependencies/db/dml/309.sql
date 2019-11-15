
UPDATE authorities
SET authority = 'DATA_EXTRACTS_QUESTAR_PREID', displayname = 'Create Questar Pre-ID Data Extract'
WHERE authority = 'DATA_EXTRACTS_QUESTAR';

update fieldspecification set fieldname='rosterName' where fieldname='courseSection' and mappedname is null;

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Exit/Clear Unenrolled', 'exitclearunenrolled', 'Exit/Clear Unenrolled',(select id from categorytype where typecode='STUDENT_TESTSECTION_STATUS'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Exit/Clear Unenrolled', 'exitclearunenrolled', 'Exit/Clear Unenrolled',(select id from categorytype where typecode='STUDENT_TEST_STATUS'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));

UPDATE categorytype SET typename='KSDE Comprehensive Race', typecode='KSDE_COMPREHENSIVE_RACE', typedescription='KSDE Comprehensive Race' WHERE typecode='COMPREHENSIVE_RACE';

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('Comprehensive Race', 'COMPREHENSIVE_RACE','Comprehensive Race','AART_ORIG_CODE',now(),(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'White' as categoryname,'1' as categorycode,'White' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'African American' as categoryname,'2' as categorycode,'African American' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'Asian' as categoryname,'4' as categorycode,'Asian' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'American Indian' as categoryname,'5' as categorycode,'American Indian' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'Alaska Native' as categoryname,'6' as categorycode,'Alaska Native' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'Two or More Races' as categoryname,'7' as categorycode,'Two or More Races' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
                (Select 'Native Hawaiian or Pacific islander' as categoryname,'8' as categorycode,'Native Hawaiian or Pacific islander' as categorydescription,
                                (select id from categorytype where typecode='COMPREHENSIVE_RACE') as categorytypeid, 'AART_ORIG_CODE' as originationcode,now() as createddate,id as createduser,
                                true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');
