--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.5
-- Dumped by pg_dump version 9.1.3
-- Started on 2012-08-27 11:27:24

--
-- TOC entry 3013 (class 0 OID 29666)
-- Dependencies: 167
-- Data for Name: aartuser; Type: TABLE DATA; Schema: public; Owner: aart
--


INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('ColoradoSysAdmin', 'Colorado', NULL, 'SysAdmin', '71b2837204867ee16bd5656e3b0d16e3c98a724e9746f92092b8ef9146a961fda0659c2b66462bfe04871c03802a228d194ec27cd9f91ba5a40345b349a05cbd', 'ColoradoSysAdmin', '100001', NULL, '㐀䲘즬਒ᩤ᫕닕鶮輺㈑햿풏﹠㬽');
INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('NebraskaSysAdmin', 'Nebraska', NULL, 'SysAdmin', 'd8a3a954516e3c88955782cf13696e73b8ea7ef1739499182154a2c115d7da7c092cdfc5b8942958d4037928d2bce1a0a08841bef96b0b6f61f6cce50783385d', 'NebraskaSysAdmin', '101001', NULL, '熷挒稻ᯱ幐雒ṗ쫗ꝧ㗢뾳伮将䶹뉰');
INSERT INTO aartuser (username, firstname, middlename, surname, password, email, uniquecommonidentifier, defaultusergroupsid, ukey) VALUES ('IowaSysAdmin', 'Iowa', NULL, 'SysAdmin', 'fd4d50cf6317245581d1cfc168faebaa02610452c70bcf3c62f2f8664c678258669118925ee55e7476853fe7ba6e84c73b6b2681a6469ee2d0df1a55be7d380b', 'IowaSysAdmin', '102001', NULL, '祰揕ꇖ⃯⿵쀲꾬랚񀠱뙬ᓷ໢武');

--
-- TOC entry 3031 (class 0 OID 30069)
-- Dependencies: 202
-- Data for Name: assessmentprogram; Type: TABLE DATA; Schema: public; Owner: aart
--
--TODO this is no longer an assessment program.This is a testing program.
INSERT INTO assessmentprogram (programname) VALUES ('Formative');
INSERT INTO assessmentprogram (programname) VALUES ('CPASS');

--
-- TOC entry 3023 (class 0 OID 29870)
-- Dependencies: 189
-- Data for Name: categorytype; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Enrollment Status', 'ENRL_STAT', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Course Enrollment Status', 'CRS_ENRL_ST', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('State Course Code', 'STATE_COURSE_CODE', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Course Enrollment Status Code', 'COURSE_ENROLLMENT_STATUS_CODE', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('SUBJECT_CODE', 'SUBJECT_CODE', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Test Type', 'TEST_TYPE_CODE', 'Type of test');
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Grade', 'GRADE_TYPE_CODE', NULL);
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Kansas Assessment Tags', 'KANSAS_ASSESSMENT_TAGS', 'State_Math_Assess,State_Reading_Assess,K8_State_Sci_Assess etc..');
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Web Service Url Type', 'KANSAS_WEB_SERVICE_CONFIG_TYPE_CODE', 'configuration like web service url , schedule interval etc.');
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('CSV Record Type', 'CSV_RECORD_TYPE', 'Possible values at this time are ENRL,SCRS and Test');
INSERT INTO categorytype (typename, typecode, typedescription) VALUES ('Web Service Record Type', 'WEB_SERVICE_RECORD_TYPE', 'Currently Kids web service from Kansas');


--
-- TOC entry 3024 (class 0 OID 29880)
-- Dependencies: 191 3023
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Exited', 'EXIT', 'The student exited this building', (select id from categorytype where typecode = 'ENRL_STAT'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Enrolled', 'ENRL', 'The student is enrolled in this building.', (select id from categorytype where typecode = 'ENRL_STAT'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Enrolled For this course', 'ENRL_CRS', 'The student is enrolled for this course but not started it.', (select id from categorytype where typecode = 'CRS_ENRL_ST'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Began This Course', 'BEGAN_CRS', 'The student began this course', (select id from categorytype where typecode = 'CRS_ENRL_ST'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Completed This Course', 'COMPL_CRS', 'The student Completed this course', (select id from categorytype where typecode = 'CRS_ENRL_ST'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Dropped This Course', 'DROP_CRS', 'The Student dropped this course.', (select id from categorytype where typecode = 'CRS_ENRL_ST'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Enrollment', 'ENRL_RECORD_TYPE', 'This indicates that the uploaded record is Enrollment record.', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('SCRS', 'SCRS_RECORD_TYPE', 'This indicates that the uploaded record is scrs.', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Reading', 'READING', NULL, (select id from categorytype where typecode = 'STATE_COURSE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Test Record Type', 'TEST_RECORD_TYPE', 'Record type for Test in the csv', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Exit', 'EXIT_RECORD_TYPE', 'This indicates that the uploaded record is an Exit record.', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('USER', 'USER_RECORD_TYPE', NULL, (select id from categorytype where typecode = 'CSV_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('12', 'GRADE_12', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('7', 'GRADE_7', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('5', 'GRADE_5', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('6', 'GRADE_6', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('2', 'GRADE_2', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('4', 'GRADE_4', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('11', 'GRADE_11', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('3', 'GRADE_3', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('1', 'GRADE_1', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('9', 'GRADE_9', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('8', 'GRADE_8', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('10', 'GRADE_10', NULL, (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('M', 'TEST_TYPE_M', 'Test type of modified', (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('A', 'TEST_TYPE_A', 'Test type of alternate', (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('G', 'TEST_TYPE_G', 'Test type of general', (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('http://localhost:8080/AARTService/services/KIDS_WebServiceSoap12', 'KANSAS_WEB_SERVICE_URL_CODE', 'This is the url for Kansas Kids Web Service', (select id from categorytype where typecode = 'KANSAS_WEB_SERVICE_CONFIG_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('State_Reading_Assess', 'State_Reading_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('HS_State_Life_Sci_Assess', 'HS_State_Life_Sci_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('HS_State_Phys_Sci_Assess', 'HS_State_Phys_Sci_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('K8_State_Hist_Gov_Assess', 'K8_State_Hist_Gov_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('State Math Assessment', 'State_Math_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('HS_State_Hist_Gov_World', 'HS_State_Hist_Gov_World', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('HS_State_Hist_Gov_State', 'HS_State_Hist_Gov_State', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('State_Writing_Assess', 'State_Writing_Assess', 'Kansas State math assessment', (select id from categorytype where typecode = 'KANSAS_ASSESSMENT_TAGS'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Math Common Core', 'Math Common Core', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('ELA Common Core', 'ELA Common Core', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('9999999999999999', 'KANSAS_WEB_SERVICE_SCHEDULE_FREQUENCY_CODE', 'This is the schedule/ re-schedule frequency', (select id from categorytype where typecode = 'KANSAS_WEB_SERVICE_CONFIG_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Kid Record Type', 'KID_RECORD_TYPE', 'Record type for kids webservice in the webservice upload', (select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 2', 'TEST_SUBJECT_MATH 2', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH', 'TEST_SUBJECT_MATH', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 3', 'TEST_SUBJECT_MATH 3', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('S', 'TEST_TYPE_S', NULL, (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('P', 'TEST_TYPE_P', NULL, (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 4', 'TEST_SUBJECT_MATH 4', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('Y', 'TEST_TYPE_Y', NULL, (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 5', 'TEST_SUBJECT_MATH 5', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 6', 'TEST_SUBJECT_MATH 6', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 7', 'TEST_SUBJECT_MATH 7', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 8', 'TEST_SUBJECT_MATH 8', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 9', 'TEST_SUBJECT_MATH 9', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 10', 'TEST_SUBJECT_MATH 10', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 11', 'TEST_SUBJECT_MATH 11', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 12', 'TEST_SUBJECT_MATH 12', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 13', 'TEST_SUBJECT_MATH 13', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 14', 'TEST_SUBJECT_MATH 14', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 15', 'TEST_SUBJECT_MATH 15', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 1', 'TEST_SUBJECT_MATH 1', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('MATH 99', 'TEST_SUBJECT_MATH 99', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('13', 'GRADE_TYPE_13', 'Created by Application', (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('14', 'GRADE_TYPE_14', 'Created by Application', (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('15', 'GRADE_TYPE_15', 'Created by Application', (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('16', 'GRADE_TYPE_16', 'Created by Application', (select id from categorytype where typecode = 'GRADE_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('READx', 'TEST_SUBJECT_READX', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('J', 'TEST_TYPE_J', NULL, (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('N', 'TEST_TYPE_N', NULL, (select id from categorytype where typecode = 'TEST_TYPE_CODE'));
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES ('1234', 'TEST_SUBJECT_1234', NULL, (select id from categorytype where typecode = 'SUBJECT_CODE'));


--
-- TOC entry 3029 (class 0 OID 30023)
-- Dependencies: 199 3024 3031 3024
-- Data for Name: assessment; Type: TABLE DATA; Schema: public; Owner: aart

--TODO Assessments will no longer have test type and test subject.
--TODO when consolidating sql get rid of these inserts.

INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Assessment Math', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Assessment ELA', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Formative 1', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Formative 2', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Formative 3', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'Formative'), 'Formative 4', NULL);
INSERT INTO assessment (testtypeid, assessmentprogramid, assessmentname, testsubjectid)
VALUES ((select id from category where categorycode = 'TEST_TYPE_G'),
(select id from assessmentprogram where programname = 'CPASS'), 'CPASS 1', NULL);


--
-- TOC entry 3015 (class 0 OID 29695)
-- Dependencies: 172
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_VIEW', 'View Roles', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_MODIFY', 'Edit Roles', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_CREATE', 'Create Roles', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_DELETE', 'Delete Roles', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_SEARCH', 'Search Roles', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROLE_ASSIGN', 'Assign Roles To Users', 'Role');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_VIEW', 'View Organizations', 'Organization');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_CREATE', 'Create Organizations', 'Organization');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_MODIFY', 'Edit Organizations', 'Organization');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_DELETE', 'Delete Organizations', 'Organization');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_SEARCH', 'Search Organizations', 'Organization');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_VIEW', 'View Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_CREATE', 'Create Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_DELETE', 'Delete Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_MODIFY', 'Edit Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_SEARCH', 'Search Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_USER_UPLOAD', 'Upload Users', 'User');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_VIEW', 'View Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_MODIFY', 'Edit Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_CREATE', 'Create Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_DELETE', 'Delete Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_ARCHIVE', 'Archive Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STUDENTRECORD_SEARCH', 'Search Student Records', 'Student Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_VIEW', 'View Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_MODIFY', 'Edit Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_SEARCH', 'Search Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_DELETE', 'Delete Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_CREATE', 'Create Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_CREATE', 'Create Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_MODIFY', 'Edit Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_DELETE', 'Delete Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_PUBLISH', 'Publish Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_PRINT', 'Print Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_DOWNLOAD', 'Download Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TASK_VIEW', 'View Tasks', 'Task');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_VIEW', 'View Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_MODIFY', 'Edit Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_CREATE', 'Create Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_DELETE', 'Delete Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_SEARCH', 'Search Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_ARCHIVE', 'Archive Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STIMULUS_VIEW', 'View Stimulus', 'Stimulus');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STIMULUS_CREATE', 'Create Stimulus', 'Stimulus');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STIMULUS_MODIFY', 'Edit Stimulus', 'Stimulus');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STIMULUS_DELETE', 'Delete Stimulus', 'Stimulus');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_STIMULUS_SEARCH', 'Search Stimulus', 'Stimulus');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_VIEW', 'View Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_CREATE', 'Create Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_MODIFY', 'Edit Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_DELETE', 'Delete Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_PRINT', 'Print Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_PUBLISH', 'Publish Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_REPORT_DOWNLOAD', 'Download Reports', 'Report');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_VIEW', 'View Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_CREATE', 'Create Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_MODIFY', 'Edit Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_DELETE', 'Delete Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_SEARCH', 'Search Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_FORM_PUBLISH', 'Publish Forms', 'Form');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_VIEW', 'View Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_CREATE', 'Create Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_MONITOR', 'Monitor Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_MODIFY', 'Edit Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_DELETE', 'Delete Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTSESSION_SCHEDULE', 'Schedule Test Session', 'Test Session');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_VIEW', 'View Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_CREATE', 'Create Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_VALIDATE', 'Validate Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_MODIFY', 'Edit Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_DELETE', 'Delete Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_SEARCH', 'Search Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_RESET', 'Reset Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_PRINT', 'Print Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_DOWNLOAD', 'Download Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TESTTICKET_REISSUE', 'Re-Issue Test Ticket', 'Test Ticket');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_BATCH_VIEW', 'View Batch', 'Batch');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_BATCH_CREATE', 'Create Batch', 'Batch');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_BATCH_MODIFY', 'Edit Batch', 'Batch');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_BATCH_SEARCH', 'Search Batch', 'Batch');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_BATCH_EXPORT', 'Export Batch', 'Batch');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ENRL_UPLOAD', 'Upload Enrollments', 'Enrollment');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_UPLOAD', 'Upload Roster Records', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_EXIT_UPLOAD', 'Upload Exits', 'Exit');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_TEST_UPLOAD', 'Upload Tests', 'Test');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ROSTERRECORD_VIEWALL', 'View All Rosters', 'Roster Record');
INSERT INTO authorities (authority, displayname, objecttype) VALUES ('PERM_ORG_UPLOAD', 'Upload Organizations', 'Organization');


--
-- TOC entry 3022 (class 0 OID 29841)
-- Dependencies: 187
-- Data for Name: organizationtype; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('Consortia', 'CONS', 10);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('State', 'ST', 20);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('Region', 'RG', 30);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('Area', 'AR', 40);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('District', 'DT', 50);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('Building', 'BLDG', 60);
INSERT INTO organizationtype (typename, typecode, typelevel) VALUES ('School', 'SCH', 70);

--
-- TOC entry 3011 (class 0 OID 29637)
-- Dependencies: 163
-- Data for Name: organization; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('Colorado', 'COLO', (select id from organizationtype where typecode = 'ST'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Colorado', 'NRTHCOLO', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Colorado', 'EASTCOLO', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Colorado', 'STHCOLO', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Colorado', 'WESTCOLO', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Colorado High School', 'NCHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Colorado High School', 'ECHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Colorado High School', 'SCHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Colorado High School', 'WCHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('Nebraska', 'NEB', (select id from organizationtype where typecode = 'ST'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Nebraska', 'NRTHNEB', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Nebraska', 'EASTNEB', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Nebraska', 'STHNEB', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Nebraska', 'WESTNEB', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Nebraska District', 'NNEBDIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Nebraska High School', 'NNHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Nebraska High School', 'ENHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Nebraska District', 'SNEBDIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Nebraska High School', 'SNHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Nebraska High School', 'WNHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('Iowa', 'IA', (select id from organizationtype where typecode = 'ST'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Iowa', 'NRTHIA', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Iowa', 'EASTIA', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Iowa', 'STHIA', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Iowa', 'WESTIA', (select id from organizationtype where typecode = 'RG'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Iowa District', 'NIADIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Iowa High School', 'NIHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Iowa District', 'EIADIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Iowa High School', 'EIHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Iowa District', 'SIADIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Iowa High School', 'SIHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Iowa District', 'WIADIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Iowa High School', 'WIHS', (select id from organizationtype where typecode = 'SCH'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('North Colorado District', 'NCOLODIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Colorado District', 'ECOLODIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('South Colorado District', 'SCOLODIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Colorado District', 'WCOLODIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('East Nebraska District', 'ENEBDIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Nebraska District', 'WNEBDIST', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('West Central Colorado District', 'WCCD', (select id from organizationtype where typecode = 'DT'), NULL);
INSERT INTO organization (organizationname, displayidentifier, organizationtypeid, welcomemessage) VALUES ('Northwest Colorado High School', 'NWHS', (select id from organizationtype where typecode = 'SCH'), NULL);


--
-- TOC entry 3026 (class 0 OID 29930)
-- Dependencies: 195
-- Data for Name: fieldspecification; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('courseSection', NULL, NULL, NULL, 30, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalLastName', NULL, NULL, NULL, 60, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalFirstName', NULL, NULL, NULL, 60, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateCourseCode', NULL, NULL, NULL, 75, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateStudentIdentifier', NULL, 1, 99999999999999999, 10, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('educatorIdentifier', NULL, 1, 99999999999999999, 10, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateSubjectAreaCode', NULL, NULL, NULL, 10, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('localStudentIdentifier', NULL, NULL, NULL, 20, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalMiddleName', NULL, NULL, NULL, 60, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('currentGradeLevel', NULL, NULL, NULL, 2, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('attendanceSchoolProgramIdentifier', NULL, NULL, NULL, 10, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('primaryDisabilityCode', '{'''',AM,DB,DD,ED,HI,LD,MD,MR,ID,OH,OI,SL,TB,VI}', NULL, NULL, 2, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('aypSchoolIdentifier', NULL, NULL, NULL, 10, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('organizationTypeCode', NULL, NULL, NULL, 15, true, true, '^[A-Za-z]++$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('exitWithdrawalType', '{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,98,99}', NULL, NULL, 10, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('schoolIdentifier', NULL, NULL, NULL, 4, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('generationCode', '{Jr.,Jr,Sr.,Sr,II,III,IV,V,'''',jr.,jr,sr.,sr,ii,iii,iv,v,JR.,JR,SR.,SR}', NULL, NULL, 10, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('enrollmentStatusCode', '{'''',00,01,99}', NULL, NULL, NULL, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('testSubject', NULL, NULL, NULL, 10, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('testType', NULL, NULL, NULL, 2, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('currentSchoolYear', NULL, 1900, 2099, 4, true, true, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('organizationId', NULL, NULL, NULL, 10, true, true, E'^\\d++$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('email', NULL, NULL, NULL, 40, true, true, E'^[\\w~`!#$%^&*_+={};:/?|-]+(\\.{0,1}[\\w~`!#$%^&*_+={};:/?|-]+)*@{1}[A-Za-z]+(\\.{0,1}[A-Za-z]+-{0,1})*\\.{1}[A-Za-z]{2,}$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('schoolEntryDate', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('districtEntryDate', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateEntryDate', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('dateOfBirth', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('specialEdProgramEndingDate', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('exitWithdrawalDate', NULL, NULL, NULL, 10, false, false, '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('currentSchoolYear', NULL, 1900, 2099, 4, true, true, NULL, 'Current_School_Year', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('aypSchoolIdentifier', NULL, NULL, NULL, 10, false, false, NULL, 'AYP_QPA_Bldg_No', false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateStudentIdentifier', NULL, 1, 99999999999999999, 10, true, true, NULL, 'State_Student_Identifier', false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalFirstName', NULL, NULL, NULL, 60, false, false, NULL, 'Legal_First_Name', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalLastName', NULL, NULL, NULL, 60, false, false, NULL, 'Legal_Last_Name', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('legalMiddleName', NULL, NULL, NULL, 60, false, false, NULL, 'Legal_Middle_Name', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('generationCode', '{Jr.,Jr,Sr.,Sr,II,III,IV,V,'''',jr.,jr,sr.,sr,ii,iii,iv,v,JR.,JR,SR.,SR}', NULL, NULL, 10, false, false, NULL, 'Generation_Code', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('currentGradeLevel', NULL, NULL, NULL, 2, false, false, NULL, 'Current_Grade_Level', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('dateOfBirth', NULL, NULL, NULL, 10, false, false, NULL, 'Birth_Date', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('localStudentIdentifier', NULL, NULL, NULL, 20, false, false, NULL, 'Local_Student_Identifier', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('attendanceSchoolProgramIdentifier', NULL, NULL, NULL, 4, true, true, NULL, 'Attendance_Bldg_No', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('schoolEntryDate', NULL, NULL, NULL, 10, false, false, NULL, 'School_Entry_Date', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('districtEntryDate', NULL, NULL, NULL, 10, false, false, NULL, 'District_Entry_Date', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('stateEntryDate', NULL, NULL, NULL, 10, false, false, NULL, 'State_Entry_Date', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('lastName', NULL, NULL, NULL, 40, false, true, '^[A-Za-z-`]*+$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('firstName', NULL, NULL, NULL, 40, false, true, '^[A-Za-z-`]*+$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('firstLanguage', '{'''',1,2,3,4,5,6,7,8,10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45}', NULL, NULL, 2, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('fundingSchool', NULL, NULL, NULL, 4, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('fundingSchool', NULL, NULL, NULL, 4, false, false, NULL, 'Funding_Bldg_No', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('residenceDistrictIdentifier', NULL, NULL, NULL, 5, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('residenceDistrictIdentifier', NULL, NULL, NULL, 5, false, false, NULL, 'Residence_Org_No', false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('educatorFirstName', NULL, NULL, NULL, 40, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('educatorLastName', NULL, NULL, NULL, 40, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('comprehensiveRace', NULL, NULL, NULL, 5, false, false, '(0|1)(0|1)(0|1)(0|1)(0|1)', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('userIdentifier', NULL, NULL, NULL, 10, false, true, E'^\\w*+$', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('giftedStudent', NULL, NULL, NULL, 5, false, false, E'\\w*', NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('gender', '{0,1}', NULL, NULL, 1, false, false, NULL, NULL, true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('gender', '{0,1}', NULL, NULL, 1, false, false, NULL, 'Gender', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('recordCommonId', NULL, NULL, NULL, 15, false, false, NULL, 'Record_Common_Id', false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('createDate', NULL, NULL, NULL, 30, false, false, NULL, 'Create_Date', false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('firstLanguage', '{'''',1,2,3,4,5,6,7,8,10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45}', NULL, NULL, 2, false, false, NULL, 'First_Language', true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror) VALUES ('comprehensiveRace', NULL, NULL, NULL, 5, false, false, '(0|1)(0|1)(0|1)(0|1)(0|1)', 'Comprehensive_Race', true);



--
-- TOC entry 3027 (class 0 OID 29941)
-- Dependencies: 196 3026 3024
-- Data for Name: fieldspecificationsrecordtypes; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'schoolIdentifier' and mappedname is NULL), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'enrollmentStatusCode' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'courseSection' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalLastName' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalFirstName' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'educatorFirstName' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'educatorLastName' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentSchoolYear' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateCourseCode' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'educatorIdentifier' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateSubjectAreaCode' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'SCRS_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentSchoolYear' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalLastName' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalFirstName' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentGradeLevel' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'fundingSchool' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'giftedStudent' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname is null), (select id from category where categorycode = 'ENRL_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'organizationId' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'userIdentifier' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'lastName' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'firstName' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'email' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'organizationTypeCode' and mappedname is null), (select id from category where categorycode = 'USER_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentSchoolYear' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalLastName' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalFirstName' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentGradeLevel' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'fundingSchool' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'giftedStudent' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'specialEdProgramEndingDate' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'testSubject' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'testType' and mappedname is null), (select id from category where categorycode = 'TEST_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentSchoolYear' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalLastName' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalFirstName' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentGradeLevel' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'fundingSchool' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'giftedStudent' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'exitWithdrawalType' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'exitWithdrawalDate' and mappedname is null), (select id from category where categorycode = 'EXIT_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname = 'AYP_QPA_Bldg_No'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname = 'Residence_Org_No'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname = 'State_Student_Identifier'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalFirstName' and mappedname = 'Legal_First_Name'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalLastName' and mappedname = 'Legal_Last_Name'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname = 'Legal_Middle_Name'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname = 'Generation_Code'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname = 'Gender'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentGradeLevel' and mappedname = 'Current_Grade_Level'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth' and mappedname = 'Birth_Date'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname = 'Local_Student_Identifier'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'currentSchoolYear' and mappedname = 'Current_School_Year'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'fundingSchool' and mappedname = 'Funding_Bldg_No'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname = 'Attendance_Bldg_No'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname = 'School_Entry_Date'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname = 'District_Entry_Date'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname = 'State_Entry_Date'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace' and mappedname = 'Comprehensive_Race'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname = 'First_Language'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'recordCommonId' and mappedname = 'Record_Common_Id'), (select id from category where categorycode = 'KID_RECORD_TYPE'));
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid) VALUES ((select id from fieldspecification where fieldname = 'createDate' and mappedname = 'Create_Date'), (select id from category where categorycode = 'KID_RECORD_TYPE'));


--
-- TOC entry 3017 (class 0 OID 29731)
-- Dependencies: 176 3011
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO groups (organizationid, groupname, defaultrole) (select id, 'DEFAULT', true from organization) except (select organizationid, groupname, defaultrole from groups);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'COLO'), 'System Administrator', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'NEB'), 'System Administrator', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'IA'), 'System Administrator', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'COLO'), 'SysAdmin - Colorado', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'WESTCOLO'), 'Supervisor - West Colorado', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'WCOLODIST'), 'Superintendent - W Colorado Dstr', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'WCHS'), 'Principal - W Colorado HS', false);
INSERT INTO groups (organizationid, groupname, defaultrole) VALUES ((select id from organization where displayidentifier = 'WCHS'), 'Teacher - W Colorado HS', false);

--
-- TOC entry 3018 (class 0 OID 29744)
-- Dependencies: 178 3017 3015
-- Data for Name: groupauthorities; Type: TABLE DATA; Schema: public; Owner: aart
--
INSERT INTO groupauthorities (groupid, authorityid) (select gr.id, a.id from groups as gr, authorities as a where defaultrole != true);

--
-- TOC entry 3021 (class 0 OID 29836)
-- Dependencies: 185 3011 3011
-- Data for Name: organizationrelation; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NRTHCOLO'), (select id from organization where displayidentifier = 'COLO')) EXCEPT (select organizationid, parentorganizationid from organizationrelation);
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'EASTCOLO'), (select id from organization where displayidentifier = 'COLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'STHCOLO'), (select id from organization where displayidentifier = 'COLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WESTCOLO'), (select id from organization where displayidentifier = 'COLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NCOLODIST'), (select id from organization where displayidentifier = 'NRTHCOLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NCHS'), (select id from organization where displayidentifier = 'NCOLODIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'ECOLODIST'), (select id from organization where displayidentifier = 'EASTCOLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'ECHS'), (select id from organization where displayidentifier = 'ECOLODIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SCOLODIST'), (select id from organization where displayidentifier = 'STHCOLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SCHS'), (select id from organization where displayidentifier = 'SCOLODIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WCOLODIST'), (select id from organization where displayidentifier = 'WESTCOLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WCHS'), (select id from organization where displayidentifier = 'WCOLODIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NRTHNEB'), (select id from organization where displayidentifier = 'NEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'EASTNEB'), (select id from organization where displayidentifier = 'NEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'STHNEB'), (select id from organization where displayidentifier = 'NEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WESTNEB'), (select id from organization where displayidentifier = 'NEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NNEBDIST'), (select id from organization where displayidentifier = 'NRTHNEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NNHS'), (select id from organization where displayidentifier = 'NNEBDIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'ENEBDIST'), (select id from organization where displayidentifier = 'EASTNEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'ENHS'), (select id from organization where displayidentifier = 'ENEBDIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SNEBDIST'), (select id from organization where displayidentifier = 'STHNEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SNHS'), (select id from organization where displayidentifier = 'SNEBDIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WNEBDIST'), (select id from organization where displayidentifier = 'WESTNEB'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WNHS'), (select id from organization where displayidentifier = 'WNEBDIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NRTHIA'), (select id from organization where displayidentifier = 'IA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'EASTIA'), (select id from organization where displayidentifier = 'IA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'STHIA'), (select id from organization where displayidentifier = 'IA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WESTIA'), (select id from organization where displayidentifier = 'IA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NIADIST'), (select id from organization where displayidentifier = 'NRTHIA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NIHS'), (select id from organization where displayidentifier = 'NIADIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'EIADIST'), (select id from organization where displayidentifier = 'EASTIA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'EIHS'), (select id from organization where displayidentifier = 'EIADIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SIADIST'), (select id from organization where displayidentifier = 'STHIA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'SIHS'), (select id from organization where displayidentifier = 'SIADIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WIADIST'), (select id from organization where displayidentifier = 'WESTIA'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WIHS'), (select id from organization where displayidentifier = 'WIADIST'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'WCCD'), (select id from organization where displayidentifier = 'WESTCOLO'));
INSERT INTO organizationrelation (organizationid, parentorganizationid) VALUES ((select id from organization where displayidentifier = 'NWHS'), (select id from organization where displayidentifier = 'WCOLODIST'));

--
-- TOC entry 3032 (class 0 OID 30079)
-- Dependencies: 204 3011 3031
-- Data for Name: orgassessmentprogram; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) 
    (select org.id, ap.id from organization as org, assessmentprogram as ap)
        except (select organizationid, assessmentprogramid from orgassessmentprogram);


--
-- TOC entry 3019 (class 0 OID 29762)
-- Dependencies: 180 3017 3013
-- Data for Name: usergroups; Type: TABLE DATA; Schema: public; Owner: aart
--

INSERT INTO usergroups (aartuserid, groupid, status, activationno, activationnoexpirationdate) VALUES ((select id from aartuser where email = 'ColoradoSysAdmin'), (select id from groups where groupname = 'System Administrator' and organizationid = (select id from organization where displayIdentifier = 'COLO')), 2, NULL, NULL);
INSERT INTO usergroups (aartuserid, groupid, status, activationno, activationnoexpirationdate) VALUES ((select id from aartuser where email = 'NebraskaSysAdmin'), (select id from groups where groupname = 'System Administrator' and organizationid = (select id from organization where displayIdentifier = 'NEB')), 2, NULL, NULL);
INSERT INTO usergroups (aartuserid, groupid, status, activationno, activationnoexpirationdate) VALUES ((select id from aartuser where email = 'IowaSysAdmin'), (select id from groups where groupname = 'System Administrator' and organizationid = (select id from organization where displayIdentifier = 'IA')), 2, NULL, NULL);


-- Completed on 2012-08-27 11:27:45

--
-- PostgreSQL database dump complete
--
