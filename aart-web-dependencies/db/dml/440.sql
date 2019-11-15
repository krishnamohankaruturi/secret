--For /ddl/440.sql


--ScriptBees
--US16437
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Ready to Submit', 'READY_TO_SUBMIT', 'Ready to submit status for first contact', (select id from categorytype where typecode='SURVEY_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
UPDATE CATEGORY SET categoryname='Completed' where categorycode='COMPLETE';

--US16586
update surveyresponse set responselabel='1' where responsevalue='80% or more of the day in Regular Class';
update surveyresponse set responselabel='2' where responsevalue='40% - 79% of the day in Regular Class';
update surveyresponse set responselabel='3' where responsevalue='Less than 40% of the day in Regular Class';

--US16587
update surveyresponse set responselabel='1' where responsevalue='No hearing loss suspected/documented';
update surveyresponse set responselabel='2' where responsevalue='Deaf or hard of hearing';
update surveyresponse set responsevalue='Questionable hearing but testing inconclusive.' where labelid=(select id from surveylabel where labelnumber='Q19') and responseorder='4';
update surveyresponse set responselabel='3' where responsevalue='Questionable hearing but testing inconclusive.';
update surveylabel set optional='true' where labelnumber='Q19';

--Change pond
--US16479
INSERT INTO groups(organizationid, groupname, groupcode, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid, 
            roleorgtypeid)
    VALUES ((select id from organization where organizationname = 'CETE Organization'), 'Teacher: PNP Read Only', 'TEAR', FALSE, CURRENT_TIMESTAMP, TRUE, 
            (select id from aartuser where email='cete@ku.edu'), (select id from aartuser where email='cete@ku.edu'), CURRENT_TIMESTAMP, (select id from organizationtype where typename = 'School'), 
            (select id from organizationtype where typename = 'School'));

update authorities set objecttype='Student Management-Access Profile(PNP)' where authority = 'PERSONAL_NEEDS_PROFILE_UPLOAD';

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('CREATE_STUDENT_PNP', 'Create Student PNP', 'Student Management-Access Profile(PNP)',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('EDIT_STUDENT_PNP', 'Edit Student PNP', 'Student Management-Access Profile(PNP)',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_STUDENT_PNP', 'View Student PNP', 'Student Management-Access Profile(PNP)',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
            
--US16550            
UPDATE groups SET groupcode='TEA' WHERE groupname='Teacher';
UPDATE groups SET groupcode='TEAR' WHERE groupname='Teacher: PNP Read Only';
UPDATE groups SET groupcode='PRO' WHERE groupname='Test Proctor';
UPDATE groups SET groupcode='PRN' WHERE groupname='Building Principal';
UPDATE groups SET groupcode='BTC' WHERE groupname='Building Test Coordinator';
UPDATE groups SET groupcode='BUS' WHERE groupname='Building User';
UPDATE groups SET groupcode='SUP' WHERE groupname='District Superintendent';
UPDATE groups SET groupcode='DTC' WHERE groupname='District Test Coordinator';
UPDATE groups SET groupcode='DUS' WHERE groupname='District User';
UPDATE groups SET groupcode='SCO' WHERE groupname='Scorer';
UPDATE groups SET groupcode='TD' WHERE groupname='Technology Director';



--US16356: Reports - Reports UI for KAP school files of all students (bundled)
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_GNRL_STUDENT_RPT_BUNDLED','View general assessment student report (bundled)','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

--US16235: Reports - Reports UI - enhance control over whether report is listed

INSERT INTO categorytype(
            typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Report Types UI','REPORT_TYPES_UI', 'General and alternate report links for Reports UI', null, 'AART_ORIG_CODE', 
            current_timestamp, (select id from aartuser where username='cetesysadmin'), TRUE, current_timestamp,
            (select id from aartuser where username='cetesysadmin'));

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Student (Individual)', 'GEN_ST', 'General Student (Individual)' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

--US16356
insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Students (Bundled)', 'GEN_ST_ALL', 'General Students (Bundled)' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

--US16235:
insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('School Detail', 'GEN_SD', 'General School Detail' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('School Summary', 'GEN_SS', 'General School Summary' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('District Detail', 'GEN_DD', 'General District Detail' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('District Summary', 'GEN_DS', 'General District Summary' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Student', 'ALT_ST', 'Alternate Student' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Class Roster', 'ALT_CR', 'Alternate Class Roster' , (select id from categorytype where typecode = 'REPORT_TYPES_UI'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);


INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_ST' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_SD' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_SS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_DD' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_DS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));

INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_ST' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_SD' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_SS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_DD' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_DS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true));

	
--US16356:
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid) VALUES ((select id from category where categorycode = 'GEN_ST_ALL' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true));
INSERT INTO reportassessmentprogram(reporttypeid, assessmentprogramid, activeflag) VALUES ((select id from category where categorycode = 'GEN_ST_ALL' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), 
(select id from assessmentprogram where abbreviatedname='AMP' and activeflag is true), false);

--Script bees FCS changes
--US16504
update surveyresponse set responsevalue='Almost never (0% - 20% of the time)' where id=68;
update surveyresponse set responsevalue='Occasionally (21% - 50% of the time)' where id=69;
update surveyresponse set responsevalue='Frequently (51% - 80% of the time)' where id=70;
update surveyresponse set responsevalue='Consistently (More than 80% of the time)' where id=71;

update surveyresponse set responsevalue='Almost never (0% - 20% of the time)' where id=114;
update surveyresponse set responsevalue='Occasionally (21% - 50% of the time)' where id=115;
update surveyresponse set responsevalue='Frequently (51% - 80% of the time)' where id=116;
update surveyresponse set responsevalue='Consistently (More than 80% of the time)' where id=117;

update surveyresponse set responsevalue='Almost never (0% - 20% of the time)' where id=38;
update surveyresponse set responsevalue='Occasionally (21% - 50% of the time)' where id=39;
update surveyresponse set responsevalue='Frequently (51% - 80% of the time)' where id=40;
update surveyresponse set responsevalue='Consistently (More than 80% of the time)' where id=41;

--DE10617
UPDATE SURVEYLABEL SET LABEL='Hearing' WHERE LABELNUMBER='Q19';

update surveyresponse set responsevalue='Questionable hearing but testing inconclusive' 
	where labelid=(select id from surveylabel where labelnumber='Q19') and responseorder='4';
	
update surveyresponse set responsevalue = 'Homebound/hospital Environment: Includes students placed in and receiving special education in a hospital or homebound program'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where labelid= (select id from surveylabel where labelnumber='Q17') and responselabel='6';
	
--US16589
update surveyresponse set responsevalue = 'Questionable vision but testing inconclusive'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where labelid= (select id from surveylabel where labelnumber='Q22') and responselabel='3' ;
update surveyresponse set responselabel='4' where responsevalue='Questionable vision but testing inconclusive';
update surveyresponse set responselabel='3' where responsevalue='Blind or low vision, including vision that is not completely corrected with glasses or contact lenses';

--US16590
update surveyresponse set responselabel=4 where responsevalue='Uses a computer with human support (with or without assistive technology)';
update surveyresponse set responselabel=3 where responsevalue='This student has not had the opportunity to access a computer';

--US16593
update surveyresponse set responselabel = '2'
    where labelid= (select id from surveylabel where labelnumber='Q200') and responsevalue ='yes';
update surveyresponse set responselabel = '1'
    where labelid= (select id from surveylabel where labelnumber='Q200') and responsevalue ='no';
update surveyresponse set responsevalue='No' where labelid= (select id from surveylabel where labelnumber='Q200') and responsevalue ='no';
update surveyresponse set responsevalue='Yes' where labelid= (select id from surveylabel where labelnumber='Q200') and responsevalue='yes';

-- US16338
--Magnification Enable for KAP AND AMP
UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='assignedSupport') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='KAP');

UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='assignedSupport') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP');

--paperAndPencil Disable in KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='paperAndPencil'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(), (select id from aartuser where username='cetesysadmin'),
true, now(), (select id from aartuser where username='cetesysadmin'));

--large print booklet Disable in KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='largePrintBooklet'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(), (select id from aartuser where username='cetesysadmin'),
true, now(), (select id from aartuser where username='cetesysadmin'));
