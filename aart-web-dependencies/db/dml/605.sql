--ddl/605.sql
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('KELPA Non Scoring Reason','KELPA_NON_SCORE_REASON_CODE','KELPA Non Scoring reason codes' ,null ,'AART_ORIG_CODE', 
            current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));
            
INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Blank','BL' ,'The response is blank.' ,(Select id from categorytype where typecode = 'KELPA_NON_SCORE_REASON_CODE') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Insufficient','IS' , 'The response does not include enough student writing to score.' , (Select id from categorytype where typecode = 'KELPA_NON_SCORE_REASON_CODE') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Off Task', 'OT' ,'The response is unrelated to the resources and/or prompt.' , (Select id from categorytype where typecode = 'KELPA_NON_SCORE_REASON_CODE') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Response not in English','RNE' ,'The response is in a language other than English.' , (Select id from categorytype where typecode = 'KELPA_NON_SCORE_REASON_CODE') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Harm to Self or Others', 'HSO' ,'This response is harmful to self and/or others.' , (Select id from categorytype where typecode = 'KELPA_NON_SCORE_REASON_CODE') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin')); 

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled,  allowedserver)
    VALUES ('Auto Scoring Assignment Scheduler', 'autoScoringAssignmentScheduler', 'run', '0 0/15 * * * ?', false, 'localhost');
    
update groups set activeflag = false where groupcode = 'SSCO';

insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('DATA_EXTRACTS_K-ELPA_AGREEMENT','Create K-ELPA Test Administration Monitoring Extract','Reports-Data Extracts' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'));
