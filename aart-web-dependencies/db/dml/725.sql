--dml/725.sql

    --F479 DML -KELPA2 Student Individual Report Static Content---


    ---Report Header Intro Paragraph 2 Static Content---

    INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'PDF_STATIC_CONTENT', 'PDF_STATIC_CONTENT', 'KELPA2 Student Report Static content', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

    INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'KEPLA_ST_REPORT', 'KEPLA_P_2', 'This report shows and explains the student’s performance on the Kansas English Language Proficiency Assessment (KELPA2). The KELPA2 measures growth in English language proficiency to ensure all English learners (ELs) are prepared for academic success. This report provides performance levels on each domain tested: speaking, writing, listening, and reading, as well as an overall proficiency determination. These results are used by the teachers, the school, and the school district in planning the student’s level of support and participation in the EL program.',
    (select id from  categorytype where typecode ='PDF_STATIC_CONTENT' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));


---Report Domain Performance level Description static content---

    INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'DOMAIN_PERF_LEVEL', 'DOMAIN_PERF_LEVEL', 'KELPA2 Student Report Domain Performance Level', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

     
    INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Advanced', '5', 'Exhibits superior English language skills.',
    (select id from  categorytype where typecode ='DOMAIN_PERF_LEVEL' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

        INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Early Advanced', '4', 'Demonstrates English language skills required for engagement with grade-level academic content instruction at a level comparable to non-ELs.',
    (select id from  categorytype where typecode ='DOMAIN_PERF_LEVEL' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

        INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Intermediate', '3', 'Applies some grade-level English language skills and will benefit from EL program support.',
    (select id from  categorytype where typecode ='DOMAIN_PERF_LEVEL' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

        INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Early Intermediate', '2', 'Presents evidence of developing grade-level English language skills and will benefit from EL Program support.',
    (select id from  categorytype where typecode ='DOMAIN_PERF_LEVEL' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

        INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Beginning', '1', 'Displays few grade-level English language skills and will benefit from EL program support.',
    (select id from  categorytype where typecode ='DOMAIN_PERF_LEVEL' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));
    

---Report Domain Level Progression Text---

    INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'DOMAIN_LEVEL_PROGRESS_STATUS', 'DOMAIN_LEVEL_PROGRESS_STATUS', 'KELPA2 Student Report Progress Status Text', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

    INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Proficient', 'PF', 'Proficient',
    (select id from  categorytype where typecode ='DOMAIN_LEVEL_PROGRESS_STATUS' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( '', 'BLANK', '',
    (select id from  categorytype where typecode ='DOMAIN_LEVEL_PROGRESS_STATUS' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

     INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Progress not Demonstrated', 'PND', 'Progress Not Demonstrated',
    (select id from  categorytype where typecode ='DOMAIN_LEVEL_PROGRESS_STATUS' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

     INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Satisfactory Progress', 'SP', 'Satisfactory Progress',
    (select id from  categorytype where typecode ='DOMAIN_LEVEL_PROGRESS_STATUS' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));


----Overall Proficiency Level Description----


 INSERT INTO leveldescription(schoolyear, assessmentprogramid, subjectid, gradeid, level, 
            levelname, batchuploadid, createddate, createduser, activeflag, 
            leveldescription, descriptiontype, descriptionparagraphpagebottom, 
            testingprogramid, reportcycle, assessmentcode)
    VALUES ((SELECT extract(year FROM CURRENT_DATE)), (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) ,
    (select id from contentarea where abbreviatedname = 'ELP' and activeflag is true), '-1', 1, 'Not proficient', '-1', now(), (select id from aartuser where username = 'cetesysadmin'), true,
    'Students that are not yet proficient have not attained a level of English language skill necessary to produce, interpret, and collaborate on grade-level content-related academic tasks in English. This is indicated by attaining performance levels of 
    Beginning and Intermediate in all four domains. Students not proficient are eligible for ongoing program support.', 'Main', '', (select id from public.testingProgram where assessmentprogramid = (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) and programabbr in('S') and activeflag is true)
    , null, null);

     INSERT INTO leveldescription(schoolyear, assessmentprogramid, subjectid, gradeid, level, 
            levelname, batchuploadid, createddate, createduser, activeflag, 
            leveldescription, descriptiontype, descriptionparagraphpagebottom, 
            testingprogramid, reportcycle, assessmentcode)
    VALUES ((SELECT extract(year FROM CURRENT_DATE)), (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) ,
    (select id from contentarea where abbreviatedname = 'ELP' and activeflag is true), '-1', 2, 'Nearly proficient', '-1', now(), (select id from aartuser where username = 'cetesysadmin'), true,
    'Students are nearly proficient when they approach a level of English language skill necessary to produce, interpret, and collaborate, on grade-level content related academic tasks in English. This is indicated by attaining performance levels with above Early Intermediate that does not meet the requirements to be proficient. Nearly proficient
    students are eligible for ongoing program support.', 'Main', '', (select id from public.testingProgram where assessmentprogramid = (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) and programabbr in('S') and activeflag is true)
    , null, null);


     INSERT INTO leveldescription(schoolyear, assessmentprogramid, subjectid, gradeid, level, 
            levelname, batchuploadid, createddate, createduser, activeflag, 
            leveldescription, descriptiontype, descriptionparagraphpagebottom, 
            testingprogramid, reportcycle, assessmentcode)
    VALUES ((SELECT extract(year FROM CURRENT_DATE)), (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) ,
    (select id from contentarea where abbreviatedname = 'ELP' and activeflag is true), '-1', 3, 'Proficient', '-1', now(), (select id from aartuser where username = 'cetesysadmin'), true,
    'Students are proficient when they attain a level of English language skill necessary to independently produce, interpret, collaborate on, and succeed in gradelevel content-related academic tasks in English. This is indicated by attaining performance levels of Early Advanced or higher in all domains.', 'Main',
     '', (select id from public.testingProgram where assessmentprogramid = (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) and programabbr in('S') and activeflag is true)
    , null, null);


-- Report View Permission for Kelap Student Individual---

insert into authorities 
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
   select 'VIEW_KELPA_ASMNT_STUDENT_IND_REP','View KELPA2 Student Individual','Reports-Performance Reports',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','English Language Learner Assessments',null,1,
           ((select sortorder from authorities where authority='VIEW_CPASS_ASMNT_STUDENT_BUN_REP')+
           (select sortorder from authorities where authority='DATA_EXTRACTS_CURRENT_ENROLLMENT'))/2
   where not exists(
   select 1 from authorities where authority='VIEW_KELPA_ASMNT_STUDENT_IND_REP'
   );  

--- Report View Permission for Kelap Student Bundled---

insert into authorities 
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
   select 'VIEW_KELPA_ASMNT_STUDENT_BUN_REP','View KELPA2 Students (Bundled)','Reports-Performance Reports',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','English Language Learner Assessments',null,1,
           ((select sortorder from authorities where authority='VIEW_KELPA_ASMNT_STUDENT_IND_REP')+
           (select sortorder from authorities where authority='DATA_EXTRACTS_CURRENT_ENROLLMENT'))/2
   where not exists(
   select 1 from authorities where authority='VIEW_KELPA_ASMNT_STUDENT_BUN_REP'
   );

----Dynamic bundle report permission
   insert into authorities 
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
   select 'CREATE_KELPA_STUDENT_BUN_REP','Create KELPA2 Students (Bundled)','Reports-Performance Reports',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','English Language Learner Assessments',null,1,
           ((select sortorder from authorities where authority='VIEW_CPASS_ASMNT_STUDENT_BUN_REP')+
           (select sortorder from authorities where authority='VIEW_KELPA_ASMNT_STUDENT_IND_REP'))/2
   where not exists(
   select 1 from authorities where authority='CREATE_KELPA_ASMNT_STUDENT_BUN_REP'
   );     
   
        
  --- Report Link in Report page for Kelap Student Individual---

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
   VALUES ( 'Student (Individual)', 'KELPA_ST', 'Kelpa Student (Individual)', (select id from categorytype  where typecode ='REPORT_TYPES_UI'), null,
            null,now(), (select id from aartuser where username  ='cetesysadmin'), true,now(), 
            (select id from aartuser where username  ='cetesysadmin'));
             
  --- Report Link in Report page for Kelap Student Bundled---

            INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
   VALUES ( 'Students (Bundled)', 'KELPA_ST_ALL', 'Kelpa Students (Bundled)', (select id from categorytype  where typecode ='REPORT_TYPES_UI'), null,
            null,now(), (select id from aartuser where username  ='cetesysadmin'), true,now(), 
            (select id from aartuser where username  ='cetesysadmin'));  


--Report Access Permission for KELPA Student individual--

Select * from reportassessmentprogram_fn('KELPA2','KELPA_ST','ELP','VIEW_KELPA_ASMNT_STUDENT_IND_REP');


--Report Access Permission for KELPA Student Bundled--

Select * from reportassessmentprogram_fn('KELPA2','KELPA_ST_ALL','ELP','VIEW_KELPA_ASMNT_STUDENT_BUN_REP');


update reportassessmentprogram set readytoview = true where assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KELPA2' and activeflag is true) and subjectid in( (select id from contentarea where abbreviatedname = 'ELP' and activeflag is true));


--Set starting report year of the assessent program

update assessmentprogram set modifieddate = now(), beginreportyear = 2015 where abbreviatedname in ('KAP', 'CPASS', 'DLM');

update assessmentprogram set modifieddate = now(), beginreportyear = 2018 where abbreviatedname in ('KELPA2');

