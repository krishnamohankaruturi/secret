-- 467.sql

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORING_VIEW', 'Scoring', 'Test Management-Scoring',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
            
-- delete the older assignments
delete from groupauthorities where authorityid in (select id from authorities where objecttype = 'Test Management-Test Window');
-- delete the old role.
delete from  authorities where objecttype = 'Test Management-Test Window';     



-- hiding Calculator for CPASS
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsCalculator'),
(select id from assessmentprogram where abbreviatedname='CPASS'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

-- hiding Calculator for ARMM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsCalculator'),
(select id from assessmentprogram where abbreviatedname='ARMM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

-- hiding Student provided non-embedded accommodations as noted in IEP for CPASS
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsStudentProvidedAccommodations'),
(select id from assessmentprogram where abbreviatedname='CPASS'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

-- hiding Student provided non-embedded accommodations as noted in IEP for ARMM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsStudentProvidedAccommodations'),
(select id from assessmentprogram where abbreviatedname='ARMM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);