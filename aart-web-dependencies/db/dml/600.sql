--dml/600.sql
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testGenerateAdditionalTests', 'TEST_GenerateAdditionalTests',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: The information was successfully processed, and additional test sessions will be generated within 48 hours.');
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testNotGenerateAdditionalTests', 'TEST_NotGenerateAdditionalTests',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: The information was successfully processed. No additional test sessions will be generated for the student.');

    --ddl/604.sql
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testEducatorNotActivated', 'TEST_EducatorNotActivated',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: The educator must be activated in Educator Portal by using the Settings menu.');
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testScoringInprogress', 'TEST_ScoringInprogress',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'TEST: The information was successfully processed. No changes were made because scoring is underway.');
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('testEducatorNotFound', 'TEST_EducatorNotFound',(select id from categorytype where typecode= 'KIDS_EMAIL_TEMPLATES'),
    'No changes were made because the educator ID submitted is not associated with an Educator Portal user. Please add the proctor to Educator Portal and resubmit the TEST.');
    

--Scoring assignment status 
INSERT INTO categorytype(
            typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Scoring Status', 'SCORING_STATUS', 'scoring status',null, 'AART_ORIG_CODE', CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'), TRUE, CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'));

   INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('In-Progress', 'IN_PROGRESS', 'In progress status', (select id from categorytype where typecode = 'SCORING_STATUS'), 
            null, 'AART_ORIG_CODE', CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'), TRUE, CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'));

   INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Pending', 'PENDING', 'Pending status', (select id from categorytype where typecode = 'SCORING_STATUS'), 
            null, 'AART_ORIG_CODE', CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'), TRUE, CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'));

   INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('completed', 'COMPLETED', 'Completed status', (select id from categorytype where typecode = 'SCORING_STATUS'), 
            null, 'AART_ORIG_CODE', CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'), TRUE, CURRENT_TIMESTAMP, 
            (Select id from aartuser where username='cetesysadmin'));
            
--KELPA Assessment
UPDATE assessment 
SET    autoenrollmentflag = true 
WHERE  id = (SELECT id 
             FROM   assessment 
             WHERE  assessmentcode='ELL' AND testingprogramid = (SELECT id 
                                        FROM   testingprogram 
                                        WHERE programabbr = 'S' AND 
                                        assessmentprogramid = (SELECT id 
                                                                      FROM 
                                               assessmentprogram 
                                                                      WHERE 
                                               abbreviatedname = 'K-ELPA' 
                                               AND activeflag IS TRUE))); 

UPDATE subjectarea SET activeflag = true, modifieddate = now() WHERE subjectareacode='D83' AND activeflag is false;

--Test Type                                               
INSERT INTO testtype 
            (id, 
             testtypecode, 
             testtypename, 
             assessmentid, 
             createduser, 
             createdate, 
             modifieddate, 
             modifieduser, 
             accessibilityflagcode, 
             activeflag) 
VALUES      (Nextval('testtype_id_seq'), 
             '2', 
             'General - Computer/English', 
             (SELECT id 
              FROM   assessment 
              WHERE assessmentcode='ELL' AND testingprogramid = (SELECT id 
                                         FROM   testingprogram 
                                         WHERE  programabbr = 'S' AND 
                                         assessmentprogramid = (SELECT id 
                                                                       FROM 
                                                assessmentprogram 
                                                                       WHERE 
                                                abbreviatedname = 'K-ELPA' 
                                                AND activeflag IS TRUE))), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             Now(), 
             Now(), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             NULL, 
             true); 

--OTW Enrollment method type
INSERT INTO testenrollmentmethod 
            (id, 
             assessmentprogramid, 
             methodcode, 
             methodname) 
VALUES      (Nextval('testenrollmentmethod_id_seq'), 
             (SELECT id 
              FROM   assessmentprogram 
              WHERE  abbreviatedname = 'K-ELPA' 
                     AND activeflag IS TRUE), 
             'MLTSTG', 
             'Multi-stage'); 

--Test type subject area
INSERT INTO testtypesubjectarea 
            (id, 
             testtypeid, 
             subjectareaid, 
             createduser, 
             createdate, 
             modifieddate, 
             modifieduser, 
             activeflag, 
             assessmentid) 
VALUES      (Nextval('testtypesubjectarea_id_seq'), 
             (SELECT id 
              FROM   testtype 
              WHERE  testtypecode = '2' 
                     AND assessmentid = (SELECT id 
                                         FROM   assessment 
                                         WHERE  assessmentcode='ELL' AND testingprogramid = (SELECT id 
                                                                    FROM 
                                                testingprogram 
                                                                    WHERE 
                                                programabbr = 'S' AND
                                                assessmentprogramid = (SELECT 
                                                id 
                                                                       FROM 
                                                assessmentprogram 
                                                                       WHERE 
                                                abbreviatedname = 'K-ELPA' 
                                                AND activeflag IS TRUE)))), 
             (SELECT id 
              FROM   subjectarea 
              WHERE  subjectareacode = 'D83' 
                     AND activeflag IS TRUE), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             Now(), 
             Now(), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             true, 
             (SELECT id 
              FROM   assessment 
              WHERE  assessmentcode='ELL' AND testingprogramid = (SELECT id 
                                         FROM   testingprogram 
                                         WHERE  programabbr = 'S' AND 
                                         assessmentprogramid = (SELECT id 
                                                                       FROM 
                                                assessmentprogram 
                                                                       WHERE 
                                                abbreviatedname = 'K-ELPA' 
                                                AND activeflag IS TRUE)))); 


--content area and test type subject area associatio
INSERT INTO contentareatesttypesubjectarea 
            (id, 
             contentareaid, 
             testtypesubjectareaid, 
             createduser, 
             createdate, 
             modifieddate, 
             modifieduser, 
             activeflag, 
             stageid) 
VALUES      (Nextval('contentareatesttypesubjectarea_id_seq'), 
             (SELECT id 
              FROM   contentarea 
              WHERE  abbreviatedname = 'ELP' 
                     AND activeflag IS TRUE), 
             (SELECT id 
              FROM   testtypesubjectarea 
              WHERE  subjectareaid = (SELECT id 
                                      FROM   subjectarea 
                                      WHERE  subjectareacode = 'D83' 
                                             AND activeflag IS TRUE) 
                     AND assessmentid = (SELECT id 
                                         FROM   assessment 
                                         WHERE  assessmentcode='ELL' AND testingprogramid = (SELECT id 
                                                                    FROM 
                                                testingprogram 
                                                                    WHERE programabbr = 'S' AND
                                                assessmentprogramid = (SELECT 
                                                id 
                                                                       FROM 
                                                assessmentprogram 
                                                                       WHERE 
                                                abbreviatedname = 'K-ELPA' 
                                                AND activeflag IS TRUE)))), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             Now(), 
             Now(), 
             (SELECT id 
              FROM   aartuser 
              WHERE  username = 'cetesysadmin'), 
             true, 
             NULL);

--KELPA grades
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = 'K';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '1';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '2';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '3';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '4';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '5';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '6';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '7';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '8';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '9';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '10';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '11';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='ELP' AND gc.abbreviatedname = '12';

	
-- US19247: ITI Blueprint Coverage - load criteria descriptions
-- As we are going to manually create table and loading the data manually to table using csv in all environments going to checkin ddl and dml into old versions. As of now 600.sql is already executed in prod. So checking into it.

/**
 DROP TABLE IF EXISTS temp_ela_criteriadescription;
CREATE TEMP table temp_ela_criteriadescription(subject CHARACTER VARYING(10), grade CHARACTER VARYING(10), criteria BIGINT, criteriatext text);

\COPY temp_ela_criteriadescription FROM 'ELA_criteria_descriptions.csv' WITH (FORMAT CSV, HEADER);

INSERT INTO blueprintcriteriadescription(contentareaabbrname, gradecourseabbrname, criteria, criteriatext) 
(SELECT subject, grade, criteria, criteriatext FROM (SELECT distinct subject, grade, criteria, criteriatext FROM temp_ela_criteriadescription)asp ORDER  BY cast(NULLIF(regexp_replace(grade, E'\\D', '', 'g'), '') AS integer), criteria);


DROP TABLE IF EXISTS temp_ela_criteriadescription;

DROP TABLE IF EXISTS temp_math_criteriadescription;
CREATE TEMP table temp_math_criteriadescription(subject CHARACTER VARYING(10), grade CHARACTER VARYING(10), criteria BIGINT, criteriatext text);

\COPY temp_math_criteriadescription FROM 'Math_criteria_descriptions.csv' WITH (FORMAT CSV, HEADER);

INSERT INTO blueprintcriteriadescription(contentareaabbrname, gradecourseabbrname, criteria, criteriatext) 
(SELECT subject, grade, criteria, criteriatext FROM (SELECT distinct subject, grade, criteria, criteriatext FROM temp_math_criteriadescription)asp ORDER  BY cast(NULLIF(regexp_replace(grade, E'\\D', '', 'g'), '') AS integer), criteria);


DROP TABLE IF EXISTS temp_ela_criteriadescription;


UPDATE blueprintcriteriadescription caf SET contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = cad.contentareaabbrname), 
      gradecourseid = (SELECT id FROM gradecourse WHERE abbreviatedname = cad.gradecourseabbrname AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = cad.contentareaabbrname))
  FROM blueprintcriteriadescription cad 
  where caf.id = cad.id;
 
 **/	
	