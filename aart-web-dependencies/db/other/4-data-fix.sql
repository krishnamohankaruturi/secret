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

--TODO Assessments will no longer have test type and test subject.
--TODO when consolidating sql get rid of these deletes.
--TODO insert assessments after adding assessment code and assessment description.

--delete all tests so that assessments could be recreated.
delete from tests;


