--dml/511.sql

--DE12184
-- To disable Breaks options in settings for KAP students.

UPDATE profileitemattrnameattrcontainerviewoptions set viewoption = 'disable' where pianacid = (SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport') and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP');


-- This query is to set back the value to false for KAP students for breaks option which is set to true.
UPDATE studentprofileitemattributevalue 
SET selectedvalue = 'false'
WHERE id IN (
    SELECT spiav.id
    FROM
        studentprofileitemattributevalue spiav
        INNER JOIN profileitemattributenameattributecontainer pianc ON spiav.profileitemattributenameattributecontainerid = pianc.id
        INNER JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
        INNER JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
        INNER JOIN profileitemattrnameattrcontainerviewoptions pianacvo ON pianc.id =pianacvo.pianacid
        INNER JOIN studentassessmentprogram sap on sap.studentid=spiav.studentid and pianacvo.assessmentprogramid=sap.assessmentprogramid
    WHERE
        piac.attributecontainer IN ('breaks')
        AND pia.attributename IN ('assignedSupport')
        AND spiav.selectedvalue = 'true'
        AND pianacvo.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP'));
        
--This query is to set back the value to false for KAP students for breaks option which is set to true.
UPDATE studentprofileitemattributevalue SET selectedvalue = 'false'
WHERE id IN (select spav.id from profileitemattributenameattributecontainer pianac
	join profileitemattribute pia  on pianac.attributenameid = pia.id 
	join studentprofileitemattributevalue spav on pianac.id = spav.profileitemattributenameattributecontainerid 
	join profileitemattrnameattrcontainerviewoptions pianacvo ON pianac.id =pianacvo.pianacid
	join studentassessmentprogram sap on sap.studentid=spav.studentid and pianacvo.assessmentprogramid=sap.assessmentprogramid
	where  pia.attributename ='paperAndPencil' and selectedvalue='true'
	AND pianacvo.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP'));