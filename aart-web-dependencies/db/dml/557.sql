--dml/557.sql 
--DE13409 - changed from subscore raw to scale score to subscore raw to rating

update category set categoryname = 'Subscore Raw Score to Rating', categorycode='SUBSCORE_RAW_TO_RATING', categorydescription='Subscore Raw Score to Rating',
modifieddate=now(), modifieduser = 12
where categorycode='SUBSCORE_RAW_TO_SCALE_SCORE' and categorytypeid=(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE');

-- script bees DE13382

insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values ((SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable', now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

/*insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);*/

--DE13322
update specialcircumstance set ksdecode = '28' where specialcircumstancetype = 'Cheating' and ksdecode = '00';
update specialcircumstance set ksdecode = '07' where specialcircumstancetype ='Chronic absences' and ksdecode ='08';
