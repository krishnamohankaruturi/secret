--dml/671.sql 
--dml/670.sql 

INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'Student Password', 'STUDENT_PASSWORD', 'Student password', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( '5', 'STUDENT_PASSWORD_LENGTH', 'Student password length.',
    (select id from  categorytype where typecode ='STUDENT_PASSWORD' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));
