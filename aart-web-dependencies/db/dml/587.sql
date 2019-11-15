--dml/*.sql ==> For ddl/587.sql

update profileitemattrnameattrcontainerviewoptions set viewoption = 'disable_textonly,disable_graphicsonly' 
where pianacid = (
SELECT pianc.id FROM profileitemattribute pia
JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid
JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference')
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM');

-- Permissions for US19069
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'TOOLS_VIEW_ORGANIZATION_DATA','Tools View Organization Data','Administrative-View Tools',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'TOOLS_MERGE_SCHOOLS','Tools Merge Schools' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'TOOLS_MOVE_A_SCHOOL','Tools Move a School' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'TOOLS_DEACTIVATE_ORGANIZATION','Tools Deactivate Organization' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

-- Permission for US19086
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
('PERM_PNP_OPTIONS','Edit PNP Settings','Student Management-Access Profile (PNP)', 
(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'));

update interimtest set organizationid=o.id from organization o where o.organizationname=schoolname;

