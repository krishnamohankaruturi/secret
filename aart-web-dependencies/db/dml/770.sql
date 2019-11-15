--dml/770.sql

DO
$BODY$
DECLARE
    pnpuploadcategorycode CHARACTER VARYING (75) := 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE';
    userid BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
    
    DELETE FROM fieldspecificationsrecordtypes
    WHERE activeflag IS TRUE
    AND recordtypeid = (SELECT id FROM category WHERE categorycode = pnpuploadcategorycode LIMIT 1)
    AND fieldspecificationid IN (SELECT id FROM fieldspecification WHERE fieldname = 'comprehensiveRace');
    
    INSERT INTO fieldspecification (fieldname, rejectifempty, rejectifinvalid, allowablevalues, mappedname,
        showerror, createduser, modifieduser, regexmodeflags, iskeyvaluepairfield
    )
    VALUES (
        'comprehensiveRace',
        FALSE,
        TRUE,
        '{'''',1,2,4,5,6,7,8}',
        'Comprehensive Race',
        TRUE,
        userid,
        userid,
        NULL,
        FALSE
    );
    
    INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname, required, jsondata, sortorder)
    VALUES (
        (SELECT id FROM fieldspecification WHERE fieldname = 'comprehensiveRace' ORDER BY id DESC LIMIT 1), -- most recent inserted record, since there are more
        (SELECT id FROM category WHERE categorycode = pnpuploadcategorycode LIMIT 1),
        userid,
        userid,
        'Comprehensive Race',
        FALSE,
        NULL,
        105
    );
    
    UPDATE fieldspecificationsrecordtypes
    SET sortorder = 106
    WHERE sortorder = 96
    AND recordtypeid = (SELECT id FROM category WHERE categorycode = pnpuploadcategorycode LIMIT 1)
    AND fieldspecificationid IN (SELECT id FROM fieldspecification WHERE fieldname = 'hispanicEthnicity');
END;
$BODY$;