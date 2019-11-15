delete from organizationrelation;
delete from orgassessmentprogram;
delete from groupauthorities;
delete from usergroups;
delete from groups;
delete from restrictionsorganizations;
delete from enrollmentsrosters;
delete from enrollment;
delete from studentstests;
delete from student;
delete from policy;
delete from roster;
delete from useraudit;delete from assessmentstestcollections;
delete from testcollectionstests;
delete from testcollection;
delete from test;
delete from gradecourse;
delete from contentarea;


delete from organization;
delete from aartuser;

insert into organization (organizationname, displayidentifier, organizationtypeid) values 
('Colorado', 'CO', (select id from organizationtype where typecode = 'ST')),
('Colorado Practice', 'COp', (select id from organizationtype where typecode = 'ST')),
('Kansas', 'KS', (select id from organizationtype where typecode = 'ST')),
('Kansas Practice', 'KSp', (select id from organizationtype where typecode = 'ST')),
('Mississippi', 'MS', (select id from organizationtype where typecode = 'ST')),
('Mississippi Practice', 'MSp', (select id from organizationtype where typecode = 'ST')),
('Smoke Test', 'SM', (select id from organizationtype where typecode = 'ST'))
except (select organizationname, displayidentifier, organizationtypeid from organization);

insert into groups (organizationid, groupname, defaultrole) values
	((select id from organization where displayidentifier = 'KS'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'KS'), 'System Administrator', false),
	((select id from organization where displayidentifier = 'KSp'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'KSp'), 'System Administrator', false),
	((select id from organization where displayidentifier = 'CO'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'CO'), 'System Administrator', false),
	((select id from organization where displayidentifier = 'COp'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'COp'), 'System Administrator', false),
	((select id from organization where displayidentifier = 'MS'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'MS'), 'System Administrator', false),
	((select id from organization where displayidentifier = 'MSp'), 'DEFAULT', true),
	((select id from organization where displayidentifier = 'MSp'), 'System Administrator', false)
except (select organizationid, groupname, defaultrole from groups);

insert into groupauthorities (groupid, authorityid) (select gr.id, auth.id from groups gr, authorities auth where gr.defaultrole = false)
except (select groupid, authorityid from groupauthorities);

insert into aartuser (username, firstname, surname, password, email, uniquecommonidentifier) values
('KansasSysAdmin', 'Kansas', 'SysAdmin', 'password', 'KansasSysAdmin', '101001'),
('KansasPracticeSysAdmin', 'KansasPractice', 'SysAdmin', 'password', 'KansasPracticeSysAdmin', '104001'),
('ColoradoSysAdmin', 'Colorado', 'SysAdmin', 'password', 'ColoradoSysAdmin', '102001'),
('ColoradoPracticeSysAdmin', 'ColoradoPractice', 'SysAdmin', 'password', 'ColoradoPracticeSysAdmin', '105001'),
('MississippiSysAdmin', 'Mississippi', 'SysAdmin', 'password', 'MississippiSysAdmin', '103001'),
('MississippiPracticeSysAdmin', 'MississippiPractice', 'SysAdmin', 'password', 'MississippiPracticeSysAdmin', '106001')
except (select username, firstname, surname, password, email, uniquecommonidentifier from aartuser);

insert into usergroups (aartuserid, groupid) values
((select id from aartuser where uniquecommonidentifier = '101001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'KS' and gr.defaultrole = false)),
((select id from aartuser where uniquecommonidentifier = '102001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'CO' and gr.defaultrole = false)),
((select id from aartuser where uniquecommonidentifier = '103001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'MS' and gr.defaultrole = false)),
((select id from aartuser where uniquecommonidentifier = '104001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'KSp' and gr.defaultrole = false)),
((select id from aartuser where uniquecommonidentifier = '105001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'COp' and gr.defaultrole = false)),
((select id from aartuser where uniquecommonidentifier = '106001'), (select gr.id from groups gr, organization org where gr.organizationid = org.id and org.displayidentifier = 'MSp' and gr.defaultrole = false))
except (select aartuserid, groupid from usergroups);

insert into assessmentprogram (programname) values
('Kansas Formative'), ('cPASS'), ('DLM'), ('ARMM')
except (select programname from assessmentprogram);

insert into testingprogram (programname, programdescription, assessmentprogramid) values
('Kansas Formative', 'Kansas Formative', (select id from assessmentprogram where programname = 'Kansas Formative')),
('cPASS testing program', 'cPASS testing program', (select id from assessmentprogram where programname = 'cPASS')),
('DLM testing program', 'DLM testing program', (select id from assessmentprogram where programname = 'DLM')),
('ARMM testing program', 'ARMM testing program', (select id from assessmentprogram where programname = 'ARMM'))
except (select programname, programdescription, assessmentprogramid from testingprogram);

insert into assessment (assessmentname, testingprogramid, assessmentcode, assessmentdescription) values
('Kansas Formative', (select id from testingprogram where programname = 'Kansas Formative'), 'Kansas Formative', 'Kansas Formative'),
('cPASS Assessment', (select id from testingprogram where programname = 'cPASS testing program'), 'cPASS Assessment', 'cPASS Assessment'),
('DLM Assessment', (select id from testingprogram where programname = 'DLM testing program'), 'DLM Assessment', 'DLM Assessment'),
('ARMM Assessment', (select id from testingprogram where programname = 'ARMM testing program'), 'ARMM Assessment', 'ARMM Assessment')
except (select assessmentname, testingprogramid, assessmentcode, assessmentdescription from assessment);

insert into contentarea (name, abbreviatedname) values
('Math', 'Math'), ('ELA', 'ELA'), ('General', 'General'), ('Comprehensive Agriculture (2013)','Comprehensive Agriculture (2013)'), ('Animal Systems (2013)', 'Animal Systems (2013)'),
('Plant Systems (2013)', 'Plant Systems (2013)'), ('ARMM', 'ARMM')
except (select name, abbreviatedname from contentarea);

insert into gradecourse (name, gradelevel) values
('4', 4), ('7', 7)
except (select name, gradelevel from gradecourse);

insert into testcollection (name, randomizationtype, gradecourseid, contentareaid) values
('Patterns Defined By a Rule', 'enrollment', (select id from gradecourse where name = '4'), (select id from contentarea where name = 'Math')),
('Probability', 'enrollment', (select id from gradecourse where name = '7'), (select id from contentarea where name = 'Math')),
('It''s a Corgi Picnic!', 'enrollment', (select id from gradecourse where name = '4'), (select id from contentarea where name = 'ELA')),
('I am Joe''s Spleen', 'enrollment', (select id from gradecourse where name = '7'), (select id from contentarea where name = 'ELA')),
('ARMM', 'enrollment', null, (select id from contentarea where name = 'ARMM')),
('Math Pilot 4th', 'enrollment', (select id from gradecourse where name = '4'), (select id from contentarea where name = 'Math')),
('Math Pilot 7th', 'enrollment', (select id from gradecourse where name = '7'), (select id from contentarea where name = 'Math')),
('ELA Pilot 4th', 'enrollment', (select id from gradecourse where name = '4'), (select id from contentarea where name = 'ELA')),
('ELA Pilot 7th', 'enrollment', (select id from gradecourse where name = '7'), (select id from contentarea where name = 'ELA')),
('General', 'enrollment', null , (select id from contentarea where name = 'General')),
('Comprehensive Agriculture', 'enrollment', null, (select id from contentarea where name = 'Comprehensive Agriculture (2013)')),
('Animal Systems', 'enrollment', null, (select id from contentarea where name = 'Animal Systems (2013)')),
('Plant Systems', 'enrollment', null, (select id from contentarea where name = 'Plant Systems (2013)'))
except (select name, randomizationtype, gradecourseid, contentareaid from testcollection);

insert into assessmentstestcollections (assessmentid, testcollectionid) values
((select id from assessment where assessmentname = 'Kansas Formative'), (select id from testcollection where name = 'Patterns Defined By a Rule')),
((select id from assessment where assessmentname = 'Kansas Formative'), (select id from testcollection where name = 'Probability')),
((select id from assessment where assessmentname = 'Kansas Formative'), (select id from testcollection where name = 'It''s a Corgi Picnic!')),
((select id from assessment where assessmentname = 'Kansas Formative'), (select id from testcollection where name = 'I am Joe''s Spleen')),
((select id from assessment where assessmentname = 'ARMM Assessment'), (select id from testcollection where name = 'ARMM')),
((select id from assessment where assessmentname = 'DLM Assessment'), (select id from testcollection where name = 'Math Pilot 4th')),
((select id from assessment where assessmentname = 'DLM Assessment'), (select id from testcollection where name = 'Math Pilot 7th')),
((select id from assessment where assessmentname = 'DLM Assessment'), (select id from testcollection where name = 'ELA Pilot 4th')),
((select id from assessment where assessmentname = 'DLM Assessment'), (select id from testcollection where name = 'ELA Pilot 7th')),
((select id from assessment where assessmentname = 'cPASS Assessment'), (select id from testcollection where name = 'General')),
((select id from assessment where assessmentname = 'cPASS Assessment'), (select id from testcollection where name = 'Comprehensive Agriculture')),
((select id from assessment where assessmentname = 'cPASS Assessment'), (select id from testcollection where name = 'Animal Systems')),
((select id from assessment where assessmentname = 'cPASS Assessment'), (select id from testcollection where name = 'Plant Systems'))
except (select assessmentid, testcollectionid from assessmentstestcollections);

insert into test (testname, numitems, status) values
('Patterns Defined By a Rule', 25, 65),
('Probability', 25, 65),
('It''s a Corgi Picnic!', 25, 65),
('I am Joe''s Spleen', 25, 65),
('ARMM', 25, 65),
('Math Pilot 4th', 25, 65),
('Math Pilot 7th', 25, 65),
('ELA Pilot 4th', 25, 65),
('ELA Pilot 7th', 25, 65),
('AARM', 25, 65),
('General', 25, 65),
('Comprehensive Agriculture', 25, 65),
('Animal Systems', 25, 65),
('Plant Systems', 25, 65)
except (select testname, numitems, status from test);

insert into testcollectionstests (testcollectionid, testid) values
((select id from testcollection where name = 'Patterns Defined By a Rule'), (select id from test where testname = 'Patterns Defined By a Rule')),
((select id from testcollection where name = 'Probability'), (select id from test where testname = 'Probability')),
((select id from testcollection where name = 'It''s a Corgi Picnic!'), (select id from test where testname = 'It''s a Corgi Picnic!')),
((select id from testcollection where name = 'I am Joe''s Spleen'), (select id from test where testname = 'I am Joe''s Spleen')),
((select id from testcollection where name = 'Math Pilot 4th'), (select id from test where testname = 'Math Pilot 4th')),
((select id from testcollection where name = 'Math Pilot 7th'), (select id from test where testname = 'Math Pilot 7th')),
((select id from testcollection where name = 'ELA Pilot 4th'), (select id from test where testname = 'ELA Pilot 4th')),
((select id from testcollection where name = 'ELA Pilot 7th'), (select id from test where testname = 'ELA Pilot 7th')),
((select id from testcollection where name = 'ARMM'), (select id from test where testname = 'ARMM')),
((select id from testcollection where name = 'General'), (select id from test where testname = 'General')),
((select id from testcollection where name = 'Comprehensive Agriculture'), (select id from test where testname = 'Comprehensive Agriculture')),
((select id from testcollection where name = 'Animal Systems'), (select id from test where testname = 'Animal Systems')),
((select id from testcollection where name = 'Plant Systems'), (select id from test where testname = 'Plant Systems'))
except (select testcollectionid, testid from testcollectionstests);