--For /dml/418.sql

delete from groupauthorities where authorityid IN (
(select id from authorities where authority ='VIEW_PROFESSIONAL_DEVELOPMENT'), 
(select id from authorities where authority ='VIEW_MODULES'), 
(select id from authorities where authority ='ENROLL_MODULE'))
and groupid NOT IN (select id from groups where groupname ='PD Admin');

update userorganizationsgroups set status=2 where groupid=(select id from groups where groupname='Global System Administrator');

update userpasswordreset set requesttype='ACTIVATION';

INSERT INTO userpasswordreset (aart_user_id, password, requesttype )
	SELECT id, password, 'HISTORY' FROM aartuser where activeflag = true;

INSERT INTO categorytype(
            typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('Password Policy','PASSWORD_POLICY', 'This is used for password policy', null, 'AART_ORIG_CODE', 
            current_timestamp, (select id from aartuser where username='cetesysadmin'), TRUE, current_timestamp,
            (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Caps Letter', 'PASS_CAPS_LETTER','Conatins Caps letter', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Small Letter', 'PASS_SMALL_LETTER','Conatins Small letter', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Number', 'PASS_NUMBER','Conatins Numeric', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Special Characters', 'PASS_SPECIAL_CHAR','Conatins Special Character', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('150', 'PASS_WARNING','Password change warning after 150 dyas', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('180', 'PASS_EXPIRATION','Password Expiartion alert after 180 dyas', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('8', 'PASS_MIN_LENGTH','Password minimum length', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('32', 'PASS_MAX_LENGTH','Password maximum length', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('365', 'PASS_HISTORY_CHK','Check last one year password history', (select id from categorytype where typecode='PASSWORD_POLICY' ), 
            null, 'AART_ORIG_CODE',current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

