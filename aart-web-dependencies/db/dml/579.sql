--dml/579.sql
update surveysection set surveysectiondescription='Language',
modifieduser = (Select id from aartuser where username='cetesysadmin'),
modifieddate = now()
where surveysectioncode = 'LANGUAGE' ;

--DE13882
update profileitemattrnameattrcontainerviewoptions set viewoption = 'enable' where pianacid = (SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='assignedSupport') and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM');

