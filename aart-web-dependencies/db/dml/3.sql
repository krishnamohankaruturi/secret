--Original 4.sql
--organization upload related changes.

update student set synced = false where synced is null;
alter table student alter column synced set NOT NULL;

update fieldspecification set fieldlength = 75, formatregex = E'^[A-z0-9 \\./]++$' where fieldname = 'organizationName';

update authorities set objecttype = 'Task' where objecttype = 'Tasks';

INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_UPLOAD', 'Upload Organizations', 'Organization')
    except (select authority, displayname, objecttype from authorities);

--corrected a typo in Permission.
update authorities set objecttype = 'Task' where objecttype = 'Tasks'; 

--inserting some assessment programs.

INSERT INTO assessmentprogram (programname) VALUES ('Dynamic Learning Maps');

--Below are the changes made for AART-CB integration.
--Formative is a testing program that belongs to kansas assessment program.
update assessmentprogram set programname='Kansas Assessment Program' where programname='Formative';

--insert it just incase the above update resulted in no record updates.

insert into assessmentprogram(programname) (select 'Kansas Assessment Program' where not exists
(select 1 from assessmentprogram where programname='Kansas Assessment Program')
);
--TODO insert below statement in dev
insert into assessmentprogram(programname) (select 'CPASS' where not exists
(select 1 from assessmentprogram where programname='CPASS')
);
--no need to do insert if not exists because this is a newly created table.

INSERT INTO testingprogram (programname, programdescription, assessmentprogramid)
VALUES ('Formative Testing Program', 'This is the testing program that schools use to prepare students.',
(select id from assessmentprogram where programname='Kansas Assessment Program'));
INSERT INTO testingprogram (programname, programdescription, assessmentprogramid)
VALUES ('Summative Testing Program', 'This is the high stakes testing program that schools have to administer',
(select id from assessmentprogram where programname='Kansas Assessment Program'));
INSERT INTO testingprogram (programname, programdescription, assessmentprogramid)
VALUES ('Interim Testing Program', 'This is the adaptive testing program',
(select id from assessmentprogram where programname='Kansas Assessment Program'));

--added later TODO execute in Dev.
INSERT INTO testingprogram (programname, programdescription, assessmentprogramid)
VALUES ('C Pass Testing Program 1', 'This is the CPASS testing program 1',
(select id from assessmentprogram where programname='CPASS'));
INSERT INTO testingprogram (programname, programdescription, assessmentprogramid)
VALUES ('C Pass Testing Program 2', 'This is the CPASS testing program 2',
(select id from assessmentprogram where programname='CPASS'));

INSERT INTO testingprogram(
            programname, assessmentprogramid, originationcode)
    VALUES ( 'Un Known Testing Program', (select id from assessmentprogram limit 1), 'AART_ORIG_CODE');

update assessment set testingprogramid
= (select id from testingprogram where programname = 'Un Known Testing Program');    

--this can be done only after some testing programs are create.
ALTER TABLE assessment
	ALTER COLUMN testingprogramid SET NOT NULL;
--TODO Assessments will no longer have test type and test subject.
--TODO when consolidating sql get rid of these deletes.
--TODO insert assessments after adding assessment code and assessment description.

--delete all tests so that assessments could be recreated.
--delete from tests;



--Original 5.sql

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Reentry Rules', 'REENTRY', 'Test Section Reentry Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Task Delivery Rules', 'TASKDELIVERY', 'Task Delivery Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Tools Usage Rules', 'TOOLSUSAGE', 'Tools Usage Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Navigation Rules', 'NAVIGATION', 'Navigation Rules', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Item Usage', 'ITEMUSAGE', 'Item Usage', 'CB');

INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('Test Status', 'TESTSTATUS', 'Test Status', 'CB');



INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('LOGIN_ONLY_ONCE', 'LOGIN_ONLY_ONCE',
    'Student may login to each test section only once',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('LOGIN_ANY_NUMBER', 'LOGIN_ANY_NUMBER',
    'Student may login to each section any number of times',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('ADMIN_REACTIVATE', 'ADMIN_REACTIVATE',
    'Test administrator may reactivate',
    (select id from categorytype where originationcode = 'CB' and typecode = 'REENTRY'), 
            3, 'CB');            


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('NO_REORDERING', 'NO_REORDERING', 'No re-ordering',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            1, 'CB');    

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('REVERSE_ORDER', 'REVERSE_ORDER', 'Reverse order',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            2, 'CB'); 

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('RANDOM_ORDER', 'RANDOM_ORDER', 'Random order',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TASKDELIVERY'), 
            3, 'CB'); 


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('SECTION_TOOLS', 'SECTION_TOOLS', 'Tools Defined for Section',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TOOLSUSAGE'), 1, 'CB'); 

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('TASK_TOOLS', 'TASK_TOOLS', 'Tools Defined for Tasks',
    (select id from categorytype where originationcode = 'CB'
    and typecode = 'TOOLSUSAGE'), 2, 'CB'); 


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('MUST_ANSWER_ALL', 'MUST_ANSWER_ALL',
    'Students must answer every question',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('NO_REVISIT', 'NO_REVISIT',
    'Students may not revisit a question after answering that question',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('SAME_ORDER_PRESENTED', 'SAME_ORDER_PRESENTED',
    'Students must answer items in the order presented',
    (select id from categorytype where originationcode = 'CB' and typecode = 'NAVIGATION'), 3, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('FIELD_TEST', 'FIELD_TEST', 'Field Test',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('OPERATIONAL', 'OPERATIONAL', 'Operational',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('PILOT_TEST', 'PILOT_TEST', 'Pilot Test',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 3, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('EXEMPLAR', 'EXEMPLAR', 'Exemplar',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 4, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('PRACTICE', 'PRACTICE', 'Practice',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 5, 'CB');


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('DEVELOPMENT', 'DEVELOPMENT', 'Development',
    (select id from categorytype where originationcode = 'CB' and typecode = 'ITEMUSAGE'), 6, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, originationcode)
    VALUES ('DEPLOYED', 'DEPLOYED', 'Deployed',
    (select id from categorytype where originationcode = 'CB' and typecode = 'TESTSTATUS'), 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, originationcode)
    VALUES ('NOT_DEPLOYED', 'NOT_DEPLOYED', 'Not Deployed',
    (select id from categorytype where originationcode = 'CB' and typecode = 'TESTSTATUS'),'CB');
    
--Search Tests data insert after integration.


--insert tests

INSERT INTO test (testname, numitems, externalid, createdate, modifieddate, originationcode, directions, uitypecode,
reviewtext, begininstructions, endinstructions, status)
VALUES
('one.tc1', NULL, NULL, '2012-09-13 19:40:22.672', '2012-09-13 19:40:22.672',
NULL, 'no directions', NULL, 'here is the review text', 'begin instrictions one', 'end instructions one', 'status is started');
INSERT INTO test (testname, numitems, externalid, createdate, modifieddate, originationcode, directions, uitypecode,
reviewtext, begininstructions, endinstructions, status)
VALUES
('two.tc2', NULL, NULL, '2012-09-14 10:54:58.968', '2012-09-14 10:54:58.968',
NULL, 'no directions 2', NULL, 'here is the review text 2', 'begin instructions 2', 'end instructions 2', 'status is 2.');
INSERT INTO test (testname, numitems, externalid, createdate, modifieddate, originationcode, directions, uitypecode,
reviewtext, begininstructions, endinstructions, status)
VALUES
('three.tc1', NULL, NULL, '2012-09-14 10:54:58.968', '2012-09-14 10:54:58.968',
NULL, 'no directions 3', NULL, 'here is the review text 3', 'begin instructions 3', 'end instructions 3', 'status is 3.');
INSERT INTO test (testname, numitems, externalid, createdate, modifieddate, originationcode, directions, uitypecode,
reviewtext, begininstructions, endinstructions, status)
VALUES
('four.tc2', NULL, NULL, '2012-09-14 10:54:58.968', '2012-09-14 10:54:58.968',
NULL, 'no directions 4', NULL, 'here is the review text 4', 'begin instructions 4', 'end instructions 4', 'status is 4.');
INSERT INTO test (testname, numitems, externalid, createdate, modifieddate, originationcode, directions, uitypecode,
reviewtext, begininstructions, endinstructions, status)
VALUES
('five.tc1', NULL, NULL, '2012-09-14 10:54:58.968', '2012-09-14 10:54:58.968',
NULL, 'no directions 5', NULL, 'here is the review text 5', 'begin instructions 4', 'end instructions 4', 'status is 4.');

--test collection.

INSERT INTO testcollection (name, randomizationtype,
gradecourseid,
contentareaid,
createdate, modifieddate, originationcode)
VALUES ('tc1', 'Randomize',
(Select id from gradecourse order by id asc limit 1 ),
(Select id from contentarea order by id desc limit 1),
'2012-09-13 19:43:03.875', '2012-09-13 19:43:03.875', NULL);

INSERT INTO testcollection (name, randomizationtype,
gradecourseid,
contentareaid,
createdate, modifieddate, originationcode)
VALUES ('tc2', 'Do not randomize',
(Select id from gradecourse order by id desc limit 1 ),
(Select id from contentarea order by id asc limit 1 ),
'2012-09-14 11:03:52.361', '2012-09-14 11:03:52.361', NULL);

--insert all tests into all test collection

INSERT INTO testcollectionstests(
            testcollectionid, testid)
(Select tc.id,t.id from testcollection tc,test t where (tc.id,t.id) not in (select * from testcollectionstests));

--insert into assessments table.

INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
( 'Kansas Assessment General', NULL,
(Select id from testingprogram where programname = 'Summative Testing Program'),
'2012-09-12 18:56:17.622', '2012-09-12 18:56:17.622',
NULL, 'G', 'This is the Kansas assessment for general population.');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
( 'Kansas Assessment Modified', NULL,
(Select id from testingprogram where programname = 'Formative Testing Program'),
'2012-09-12 18:56:22.511', '2012-09-12 18:56:22.511',
NULL, 'M', 'This is the kansas assessment for Formative population.');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
( 'Kansas Assessment Alternate', NULL,
(Select id from testingprogram where programname = 'Interim Testing Program'),
'2012-09-12 18:56:25.15', '2012-09-12 18:56:25.15',
NULL, 'A', 'This is the kansas assessment for Adaptive population.');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
( 'G1', NULL,
(Select id from testingprogram where programname = 'C Pass Testing Program 1'),
'2012-09-13 18:53:26.439', '2012-09-13 18:53:26.439',
NULL, 'G1', 'Created as a part of upload');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
( 'M2', NULL,
(Select id from testingprogram where programname = 'C Pass Testing Program 2'),
'2012-09-13 18:53:26.608', '2012-09-13 18:53:26.608',
NULL, 'M2', 'Created as a part of upload');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
('C Pass Assessment 1', NULL,
(Select id from testingprogram where programname = 'Formative Testing Program'),
'2012-09-14 11:22:27.993', '2012-09-14 11:22:27.993',
NULL, 'CP1', 'This is an assessment for CPASS testing program');
INSERT INTO assessment ( assessmentname, externalid, testingprogramid, createdate, modifieddate,
originationcode, assessmentcode, assessmentdescription) VALUES
('C Pass Assessment 2', NULL,
(Select id from testingprogram where programname = 'Formative Testing Program'),
'2012-09-14 11:22:27.993', '2012-09-14 11:22:27.993',
NULL, 'CP2', 'This is 2nd assessment for CPASS testing program.');

--make all test collections part of all assessments so that search tests can be verified.

insert into assessmentstestcollections
(Select a.id,tc.id from assessment a,testcollection tc
where (a.id,tc.id) not in (select * from assessmentstestcollections) );

update testcollection set name = 'tc'||id where name is null;

--update the assessment names with codes such that the codes stand for assessment,subject.
--This is necessary to correctly register the student to the assessment for the right subject.

update category
set
categorycode='STATE_ASSESSMENT,READING'
where categoryname='State_Reading_Assess';

update category
set
categorycode='HS_STATE_ASSESSMENT,LIFE_SCIENCES'
where categoryname='HS_State_Life_Sci_Assess';

update category
set
categorycode='HS_STATE_ASSESSMENT,PHYSICAL_SCIENCES'
where categoryname='HS_State_Phys_Sci_Assess';

update category
set
categorycode='K8_STATE_ASSESSMENT,GOVERNMENT_HISTORY'
where categoryname='K8_State_Hist_Gov_Assess';

update category
set
categorycode='STATE_ASSESSMENT,MATH'
where categoryname='State Math Assessment';

update category
set
categorycode='HS_STATE_ASSESSMENT,WORLD_GOVERNMENT_HISTORY'
where categoryname='HS_State_Hist_Gov_World';

update category
set
categorycode='HS_STATE_ASSESSMENT,STATE_GOVERNMENT_HISTORY'
where categoryname='HS_State_Hist_Gov_State';

update category
set
categorycode='STATE_ASSESSMENT,WRITING'
where categoryname='State_Writing_Assess';

