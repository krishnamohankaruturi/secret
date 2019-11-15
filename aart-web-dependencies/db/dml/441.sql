--For /dml/441.sql
-- update for US16338
UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='activateByDefault') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='KAP');


UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='activateByDefault') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP');


UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='magnification') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='KAP');


UPDATE profileitemattrnameattrcontainerviewoptions set viewoption='enable' where pianacid=
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Magnification' and pia.attributename='magnification') and
assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP');

--Script Bees US16533
update fieldspecification  set rejectifinvalid = false where fieldname = 'firstName' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'firstName')
);

update fieldspecification  set rejectifinvalid = false where fieldname = 'lastName' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'lastName')
);

update fieldspecification  set rejectifempty = 'false' where fieldname = 'primaryRole' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'primaryRole')
);

update fieldspecification  set rejectifempty = 'false',formatregex='^[A-Z0-9]*$' where fieldname = 'primaryAssessmentProgram' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='USER_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE' ) and fieldname = 'primaryAssessmentProgram')
);