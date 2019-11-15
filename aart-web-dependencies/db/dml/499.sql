--dml/499.sql
-- To enable Breaks options in settings for KAP students.
UPDATE profileitemattrnameattrcontainerviewoptions set viewoption = 'enable' where pianacid = (
SELECT pianc.id FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport') 
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP');