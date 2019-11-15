--dml/.sql

-- PNP Upload Race/Ethnicity (F855, F856)
DO
$BODY$
DECLARE
    pnpuploadcategorycode CHARACTER VARYING (75) := 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE';
    userid BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
    
    INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname, required, jsondata, sortorder)
    VALUES (
        (SELECT id FROM fieldspecification WHERE fieldname = 'comprehensiveRace' AND allowablevalues = '{'''',1,2,4,5,6,7,8}' LIMIT 1),
        (SELECT id FROM category WHERE categorycode = pnpuploadcategorycode LIMIT 1),
        userid,
        userid,
        'Comprehensive Race',
        FALSE,
        NULL,
        95
    );
    
    INSERT INTO fieldspecification (fieldname, rejectifempty, rejectifinvalid, formatregex, mappedname,
        showerror, createduser, modifieduser, regexmodeflags, iskeyvaluepairfield
    )
    VALUES (
        'hispanicEthnicity',
        FALSE,
        TRUE,
        '^$|yes|no|true|false',
        'Hispanic Ethnicity',
        TRUE,
        userid,
        userid,
        'i',
        FALSE
    );
    
    INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname, required, jsondata, sortorder)
    VALUES (
        (SELECT id FROM fieldspecification WHERE fieldname = 'hispanicEthnicity' ORDER BY id DESC LIMIT 1), -- most recent inserted record, since there were more
        (SELECT id FROM category WHERE categorycode = pnpuploadcategorycode LIMIT 1),
        userid,
        userid,
        'Hispanic Ethnicity',
        FALSE,
        NULL,
        96
    );
END;
$BODY$;
