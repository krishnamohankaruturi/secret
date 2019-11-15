--dml/481.sql
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORING_MONITORSCORES', 'Monitor CCQ Scores', 'Test Management-Scoring',CURRENT_TIMESTAMP,
	   (select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
            
            
--Disable Activate By Default (Spoken Audio) for AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Disable Read At Start for AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='ReadAtStartPreference'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
            