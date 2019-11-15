--dml/746.sql

--F753 PNP Extract and Upload: delete very, VERY old data that probably isn't usable anymore
drop table if exists tmp_fieldspecids;
select fs.id
into temporary table tmp_fieldspecids
from fieldspecification fs
join fieldspecificationsrecordtypes fsrt on fs.id = fsrt.fieldspecificationid
join category c on fsrt.recordtypeid = c.id
join categorytype ct on c.categorytypeid = ct.id
where ct.typecode = 'CSV_RECORD_TYPE'
and c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
and fs.fieldname != 'stateStudentIdentifier';

DELETE FROM fieldspecificationsrecordtypes
WHERE fieldspecificationid IN (SELECT id FROM tmp_fieldspecids)
AND recordtypeid = (
    SELECT c.id
    FROM category c
    JOIN categorytype ct ON c.categorytypeid = ct.id
    WHERE ct.typecode = 'CSV_RECORD_TYPE'
    AND c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
);

DELETE FROM fieldspecification WHERE id IN (SELECT id FROM tmp_fieldspecids);

--F753 PNP Extract and Upload: minor helper functions (will be dropped at the end of the file)
DROP FUNCTION IF EXISTS insertfieldspecification(
    fieldname CHARACTER VARYING(75),
    allowablevalues TEXT,
    minimum BIGINT,
    maximum BIGINT,
    fieldlength INTEGER,
    rejectifempty BOOLEAN,
    rejectifinvalid BOOLEAN,
    formatregex CHARACTER VARYING(150),
    mappedname CHARACTER VARYING(120),
    showerror BOOLEAN,
    iskeyvaluepairfield BOOLEAN,
    fieldtype CHARACTER VARYING(75),
    minimumregex CHARACTER VARYING(300),
    maximumregex CHARACTER VARYING(300),
    regexmodeflags TEXT
);
CREATE OR REPLACE FUNCTION insertfieldspecification(
    fieldname CHARACTER VARYING(75),
    allowablevalues TEXT,
    minimum BIGINT,
    maximum BIGINT,
    fieldlength INTEGER,
    rejectifempty BOOLEAN,
    rejectifinvalid BOOLEAN,
    formatregex CHARACTER VARYING(150),
    mappedname CHARACTER VARYING(120),
    showerror BOOLEAN,
    iskeyvaluepairfield BOOLEAN,
    fieldtype CHARACTER VARYING(75),
    minimumregex CHARACTER VARYING(300),
    maximumregex CHARACTER VARYING(300),
    regexmodeflags TEXT
)
RETURNS TEXT AS $BODY$
DECLARE
    userid BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
    INSERT INTO fieldspecification (
        fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,
        createduser, modifieduser, iskeyvaluepairfield, fieldtype, minimumregex, maximumregex, regexmodeflags
    )
    VALUES (
        fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,
        userid, userid, iskeyvaluepairfield, fieldtype, minimumregex, maximumregex, regexmodeflags
    );
    RETURN 'success';
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 10;


DROP FUNCTION IF EXISTS insertfieldspecificationsrecordtypes(
    fieldspecid BIGINT,
    _categorycode CHARACTER VARYING(75),
    _mappedname CHARACTER VARYING(120),
    _jsondata JSONB,
    _sortorder INTEGER,
    _required BOOLEAN
);
CREATE OR REPLACE FUNCTION insertfieldspecificationsrecordtypes(
    fieldspecid BIGINT,
    _categorycode CHARACTER VARYING(75),
    _mappedname CHARACTER VARYING(120),
    _jsondata JSONB,
    _sortorder INTEGER,
    _required BOOLEAN DEFAULT TRUE
)
RETURNS TEXT AS $BODY$
DECLARE
    userid BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM fieldspecificationsrecordtypes
        WHERE fieldspecificationid = fieldspecid
        AND recordtypeid = (SELECT id FROM category WHERE categorycode = _categorycode)
    ) THEN
        INSERT INTO fieldspecificationsrecordtypes (
            fieldspecificationid,
            recordtypeid,
            createduser,
            modifieduser,
            mappedname,
            jsondata,
            required,
            sortorder
        )
        VALUES (
            fieldspecid,
            (SELECT id FROM category WHERE categorycode = _categorycode),
            userid,
            userid,
            _mappedname,
            _jsondata,
            _required,
            _sortorder
        );
    ELSE
        UPDATE fieldspecificationsrecordtypes
        SET activeflag = TRUE,
        modifieddate = now(),
        modifieduser = userid,
        mappedname = _mappedname,
        jsondata = _jsondata,
        required = _required,
        sortorder = _sortorder
        WHERE fieldspecificationid = fieldspecid
        AND recordtypeid = (SELECT id FROM category WHERE categorycode = _categorycode);
    END IF;
    RETURN 'success';
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 10;


DROP FUNCTION IF EXISTS getfieldspec(
    uploadtype CHARACTER VARYING(75),
    fieldname CHARACTER VARYING(75)
);
CREATE OR REPLACE FUNCTION getfieldspec(
    uploadtype CHARACTER VARYING(75),
    _fieldname CHARACTER VARYING(75)
)
RETURNS SETOF fieldspecification AS $BODY$
BEGIN
    RETURN QUERY
		SELECT fs.*
	    FROM fieldspecification fs
	    JOIN fieldspecificationsrecordtypes fsrt ON fs.id = fsrt.fieldspecificationid AND fsrt.activeflag IS TRUE
	    JOIN category c ON fsrt.recordtypeid = c.id
	    JOIN categorytype ct ON c.categorytypeid = ct.id
	    WHERE fs.activeflag IS TRUE
	    AND ct.typecode = 'CSV_RECORD_TYPE'
	    AND c.categorycode = uploadtype
	    AND fs.fieldname = _fieldname
	    LIMIT 1;
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 10;




--F753 PNP Extract and Upload: deactivate existing upload specifications
UPDATE fieldspecificationsrecordtypes
SET activeflag = FALSE,
modifieddate = now(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE recordtypeid = (
    SELECT c.id
    FROM category c
    JOIN categorytype ct ON c.categorytypeid = ct.id
    WHERE ct.typecode = 'CSV_RECORD_TYPE'
    AND c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
);

--F753 PNP Extract and Upload: modify permissions
DO
$BODY$
DECLARE
    userid BIGINT := (SELECT id FROM aartuser where username = 'cetesysadmin');
BEGIN
    UPDATE authorities
    SET modifieddate = now(),
    modifieduser = userid,
    displayname = 'Create Student PNP Data Extract (Original)'
    WHERE authority = 'DATA_EXTRACTS_PNP';
	
	IF NOT EXISTS (
	    SELECT id
	    FROM authorities
	    WHERE authority = 'DATA_EXTRACTS_PNP_ABRIDGED'
	) THEN
	    INSERT INTO authorities (
	        authority,
	        displayname,
	        objecttype,
	        createduser,
	        modifieduser,
	        tabname,
	        groupingname,
	        labelname,
	        level,
	        sortorder
        )
	    SELECT
	        'DATA_EXTRACTS_PNP_ABRIDGED',
	        'Create Student PNP Data Extract (Abridged)',
	        objecttype,
	        userid,
	        userid,
	        tabname,
	        groupingname,
	        labelname,
	        level,
	        (sortorder + (SELECT sortorder FROM authorities WHERE authority = 'DATA_EXTRACTS_PNP_SUMMARY')) / 2
	    FROM authorities
	    WHERE authority = 'DATA_EXTRACTS_PNP';
	END IF;
	
	UPDATE authorities
    SET activeflag = TRUE,
    modifieddate = now(),
    modifieduser = userid,
    tabname = 'Settings',
    groupingname = 'Students',
    labelname = 'Access Profile (PNP)',
    sortorder = 4050,
    level = 1
    WHERE authority = 'PERSONAL_NEEDS_PROFILE_UPLOAD';
END;
$BODY$;


--F753 PNP Extract and Upload: populate new upload/extract specifications
DO
$BODY$
DECLARE
    pnpuploadcategorycode CHARACTER VARYING (75) := 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE';
    userid BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
BEGIN
    
    PERFORM insertfieldspecification(
        'state', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'State', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'state' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'State', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'districtName', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'District Name', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'districtName' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'District Name', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'districtIdentifier', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'District ID', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'districtIdentifier' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'District ID', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'schoolName', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'School Name', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'schoolName' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'School Name', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'schoolIdentifier', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'School ID', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'schoolIdentifier' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'School ID', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'studentLastName', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'Student Last Name', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'studentLastName' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Student Last Name', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'studentFirstName', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'Student First Name', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'studentFirstName' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Student First Name', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM getfieldspec('ENRL_RECORD_TYPE', 'stateStudentIdentifier')), -- fieldspecid
        'PERSONAL_NEEDS_PROFILE_RECORD_TYPE', -- categorycode
        'State Student Identifier', -- mappedname
        NULL, -- jsondata,
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        TRUE -- required
    );
    
    PERFORM insertfieldspecification(
        'lastModifiedTime', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'Last Modified Time', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'lastModifiedTime' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Last Modified Time', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'lastModifiedBy', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        FALSE, -- rejectifinvalid
        '', -- formatregex
        'Last Modified By', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        '' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'lastModifiedBy' order by id desc limit 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Last Modified By', -- mappedname
        NULL, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    -- Magnification
    --------------------------
    PERFORM insertfieldspecification(
        'magnification', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|((2|3|4|5)x)', -- formatregex
        'Magnification - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'magnification' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Magnification', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Magnification", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Magnification", "attributeName":"magnification", "value":""},
                        {"containerName":"Magnification", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "2x":{
                    "set":[
                        {"containerName":"Magnification", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Magnification", "attributeName":"magnification", "value":"2x"}
                    ]
                },
                "3x":{
                    "set":[
                        {"containerName":"Magnification", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Magnification", "attributeName":"magnification", "value":"3x"}
                    ]
                },
                "4x":{
                    "set":[
                        {"containerName":"Magnification", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Magnification", "attributeName":"magnification", "value":"4x"}
                    ]
                },
                "5x":{
                    "set":[
                        {"containerName":"Magnification", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Magnification", "attributeName":"magnification", "value":"5x"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["magnification-assignedsupport"] == "true" ? json["magnification-magnification"] : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'magnificationActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Magnification - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'magnificationActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- category
        'Magnification - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Magnification", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Magnification", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Magnification", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["magnification-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["magnification-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Overlay Color
    --------------------------
    PERFORM insertfieldspecification(
        'overlayColor', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|White|Blue|Yellow|Purple|Pink|Green', -- formatregex
        'Overlay Color', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'overlayColor' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Overlay Color', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"ColourOverlay", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":""}
                    ]
                },
                "white":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#ffffff"}
                    ]
                },
                "blue":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#87cffd"}
                    ]
                },
                "yellow":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#f5f2a4"}
                    ]
                },
                "purple":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#c5c5c5"}
                    ]
                },
                "pink":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#f2c5c5"}
                    ]
                },
                "green":{
                    "set":[
                        {"containerName":"ColourOverlay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ColourOverlay", "attributeName":"colour", "value":"#bef9c3"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["colouroverlay-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "#ffffff":"White",
                        "#87cffd":"Blue",
                        "#f5f2a4":"Yellow",
                        "#c5c5c5":"Purple",
                        "#f2c5c5":"Pink",
                        "#bef9c3":"Green"
                    };
                    return map[json["colouroverlay-colour"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'overlayColorActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Overlay Color - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'overlayColorActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Overlay Color - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"ColourOverlay", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"ColourOverlay", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"ColourOverlay", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["colouroverlay-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["colouroverlay-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Invert Color Choice
    --------------------------
    PERFORM insertfieldspecification(
        'invertColorChoice', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Invert Color Choice', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'invertColorChoice' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Invert Color Choice', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"assignedSupport", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"assignedSupport", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"assignedSupport", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["invertcolourchoice-assignedsupport"] == "true" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'invertColorChoiceActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Invert Color Choice - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'invertColorChoiceActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Invert Color Choice - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"InvertColourChoice", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["invertcolourchoice-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["invertcolourchoice-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Masking
    --------------------------
    PERFORM insertfieldspecification(
        'masking', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|(custom|answer) masking', -- formatregex
        'Masking', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'masking' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Masking', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Masking", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Masking", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"Masking", "attributeName":"MaskingType", "value":""}
                    ]
                },
                "custom masking":{
                    "set":[
                        {"containerName":"Masking", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Masking", "attributeName":"MaskingType", "value":"custommask"}
                    ]
                },
                "answer masking":{
                    "set":[
                        {"containerName":"Masking", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Masking", "attributeName":"MaskingType", "value":"answermask"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var map = {
                        "answermask":"Answer Masking",
                        "custommask":"Custom Masking"
                    };
                    var support = json["masking-assignedsupport"] == "true";
                    if (!support) return "";
                    return map[json["masking-maskingtype"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'maskingActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Masking - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'maskingActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Masking - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Masking", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Masking", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Masking", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["masking-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["masking-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Contrast Color
    --------------------------
    PERFORM insertfieldspecification(
        'contrastColor', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|((Grey|Yellow)/Black)|((Red|Green)/White)', -- formatregex
        'Contrast Color', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'contrastColor' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Contrast Color', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"BackgroundColour", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"BackgroundColour", "attributeName":"colour", "value":""},
                        {"containerName":"ForegroundColour", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"ForegroundColour", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"ForegroundColour", "attributeName":"colour", "value":""}
                    ]
                },
                "grey/black":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"BackgroundColour", "attributeName":"colour", "value":"#000000"},
                        {"containerName":"ForegroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ForegroundColour", "attributeName":"colour", "value":"#999999"}
                    ]
                },
                "yellow/black":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"BackgroundColour", "attributeName":"colour", "value":"#000000"},
                        {"containerName":"ForegroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ForegroundColour", "attributeName":"colour", "value":"#fefe22"}
                    ]
                },
                "red/white":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"BackgroundColour", "attributeName":"colour", "value":"#ffffff"},
                        {"containerName":"ForegroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ForegroundColour", "attributeName":"colour", "value":"#c62424"}
                    ]
                },
                "green/white":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"BackgroundColour", "attributeName":"colour", "value":"#ffffff"},
                        {"containerName":"ForegroundColour", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"ForegroundColour", "attributeName":"colour", "value":"#3b9e24"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var fg = {
                        "#999999":"Grey",
                        "#efee79":"Yellow", /* OLD YELLOW COLOR ONLY FOR MAPPING PURPOSES */
                        "#fefe22":"Yellow",
                        "#c62424":"Red",
                        "#3b9e24":"Green"
                    };
                    var bg = {
                        "#ffffff":"White",
                        "#000000":"Black"
                    };
                    var support = json["foregroundcolour-assignedsupport"] == "true" && json["backgroundcolour-assignedsupport"] == "true";
                    if (!support) return "";
                    return fg[json["foregroundcolour-colour"]] + "/" + bg[json["backgroundcolour-colour"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'contrastColorActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Contrast Color - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'contrastColorActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Contrast Color - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"ForegroundColour", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "no":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"ForegroundColour", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "yes":{
                    "set":[
                        {"containerName":"BackgroundColour", "attributeName":"activateByDefault", "value":"true"},
                        {"containerName":"ForegroundColour", "attributeName":"activateByDefault", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["backgroundcolour-assignedsupport"] == "true" && json["foregroundcolour-assignedsupport"] == "true";
                    if (!support) return "";
                    var fgByDefault = json["foregroundcolour-activatebydefault"] == "true";
                    var bgByDefault = json["backgroundcolour-activatebydefault"] == "true";
                    return (fgByDefault && bgByDefault) ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Signing Type
    --------------------------
    PERFORM insertfieldspecification(
        'signingType', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|asl|signed english', -- formatregex
        'Signing Type - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'signingType' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Signing Type', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Signing", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"Signing", "attributeName":"SigningType", "value":""}
                    ]
                },
                "asl":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Signing", "attributeName":"SigningType", "value":"asl"}
                    ]
                },
                "signed english":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Signing", "attributeName":"SigningType", "value":"SignedEnglish"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["signing-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "asl":"ASL",
                        "SignedEnglish":"Signed English",
                        "signedenglish":"Signed English",
                        "null":""
                    };
                    return map[json["signing-signingtype"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'signingTypeActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Signing Type - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'signingTypeActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Signing Type - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "no":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "yes":{
                    "set":[
                        {"containerName":"Signing", "attributeName":"activateByDefault", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["signing-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["signing-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Braille
    --------------------------
    PERFORM insertfieldspecification(
        'braille', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|ebae|ueb', -- formatregex
        'Braille', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'braille' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Braille', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Braille", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "ebae":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Braille", "attributeName":"ebaeFileType", "value":"true"},
                        {"containerName":"Braille", "attributeName":"uebFileType", "value":"false"}
                    ]
                },
                "ueb":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Braille", "attributeName":"ebaeFileType", "value":"false"},
                        {"containerName":"Braille", "attributeName":"uebFileType", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["braille-assignedsupport"] == "true";
                    if (!support) return "";
                    var ebae = json["braille-ebaefiletype"] == "true";
                    var ueb = json["braille-uebfiletype"] == "true";
                    if (ebae) return "EBAE";
                    if (ueb) return "UEB";
                    return "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'brailleUsage', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|preferred|required|prohibited|(optionally use)', -- formatregex
        'Braille Usage', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'brailleUsage' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Braille Usage', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"usage", "value":"null"}
                    ]
                },
                "preferred":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"usage", "value":"preferred"}
                    ]
                },
                "required":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"usage", "value":"required"}
                    ]
                },
                "prohibited":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"usage", "value":"prohibited"}
                    ]
                },
                "optionally use":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"usage", "value":"optionallyUse"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["braille-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "preferred":"Preferred",
                        "required":"Required",
                        "prohibited":"Prohibited",
                        "optionallyUse":"Optionally Use"
                    };
                    return map[json["braille-usage"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'brailleActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Braille - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'brailleActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Braille - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "no":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "yes":{
                    "set":[
                        {"containerName":"Braille", "attributeName":"activateByDefault", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["braille-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["braille-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Keyword Translation
    --------------------------
    PERFORM insertfieldspecification(
        'keywordTranslation', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|spanish|english|german|vietnamese', -- formatregex
        'Keyword Translation', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'keywordTranslation' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Keyword Translation', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"keywordTranslationDisplay", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "spanish":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"keywordTranslationDisplay", "attributeName":"Language", "value":"spa"}
                    ]
                },
                "english":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"keywordTranslationDisplay", "attributeName":"Language", "value":"eng"}
                    ]
                },
                "german":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"keywordTranslationDisplay", "attributeName":"Language", "value":"ger"}
                    ]
                },
                "vietnamese":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"keywordTranslationDisplay", "attributeName":"Language", "value":"vie"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["keywordtranslationdisplay-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "spa":"Spanish",
                        "eng":"English",
                        "ger":"German",
                        "vie":"Vietnamese",
                        "null":""
                    };
                    return map[json["keywordtranslationdisplay-language"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'keywordTranslationActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Keyword Translation - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'keywordTranslationActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Keyword Translation - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "no":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "yes":{
                    "set":[
                        {"containerName":"keywordTranslationDisplay", "attributeName":"activateByDefault", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["keywordtranslationdisplay-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["keywordtranslationdisplay-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Tactile
    --------------------------
    PERFORM insertfieldspecification(
        'tactile', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|(audio file)|(audio text)|(braille text)', -- formatregex
        'Tactile', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'tactile' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Tactile', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Tactile", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"Tactile", "attributeName":"tactileFile", "value":"null"}
                    ]
                },
                "audio file":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Tactile", "attributeName":"tactileFile", "value":"audioFile"}
                    ]
                },
                "audio text":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Tactile", "attributeName":"tactileFile", "value":"audioText"}
                    ]
                },
                "braille text":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Tactile", "attributeName":"tactileFile", "value":"brailleText"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["keywordtranslationdisplay-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "audioFile":"Audio File",
                        "audioText":"Audio Text",
                        "brailleText":"Braille Text",
                        "null":""
                    };
                    return map[json["tactile-tactilefile"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'tactileActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Tactile - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'tactileActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Tactile - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "no":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"activateByDefault", "value":"false"}
                    ]
                },
                "yes":{
                    "set":[
                        {"containerName":"Tactile", "attributeName":"activateByDefault", "value":"true"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["tactile-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["tactile-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Auditory Background
    --------------------------
    PERFORM insertfieldspecification(
        'auditoryBackground', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Auditory Background', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'auditoryBackground' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Auditory Background', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"assignedSupport", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"assignedSupport", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"assignedSupport", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["auditorybackground-assignedsupport"] == "true" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'auditoryBackgroundActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Auditory Background - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'auditoryBackgroundActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Auditory Background - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"AuditoryBackground", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["auditorybackground-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["auditorybackground-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Breaks
    --------------------------
    PERFORM insertfieldspecification(
        'breaks', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Breaks', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'breaks' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Breaks', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Breaks", "attributeName":"assignedSupport", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Breaks", "attributeName":"assignedSupport", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Breaks", "attributeName":"assignedSupport", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["breaks-assignedsupport"] == "true" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Additional Testing Time
    --------------------------
    PERFORM insertfieldspecification(
        'additionalTestingTime', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|unlimited|(\d+(\.\d+)?)', -- formatregex
        'Additional Testing Time', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'additionalTestingTime' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Additional Testing Time', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"AdditionalTestingTime", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"AdditionalTestingTime", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"AdditionalTestingTime", "attributeName":"TimeMultiplier", "value":""}
                    ]
                },
                "VALIDVALUE":{
                    "set":[
                        {"containerName":"AdditionalTestingTime", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"AdditionalTestingTime", "attributeName":"TimeMultiplier", "value":"VALUE"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["additionaltestingtime-assignedsupport"] == "true";
                    var value = json["additionaltestingtime-timemultiplier"];
                    return support ? (value.charAt(0).toUpperCase() + value.slice(1)) : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'additionalTestingTimeActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Additional Testing Time - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'additionalTestingTimeActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Additional Testing Time - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"AdditionalTestingTime", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"AdditionalTestingTime", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"AdditionalTestingTime", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["additionaltestingtime-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["additionaltestingtime-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Spoken
    --------------------------
    PERFORM insertfieldspecification(
        'spokenAudio', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|human|synthetic', -- formatregex
        'Spoken Audio', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'spokenAudio' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Spoken Audio', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"Spoken", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"Spoken", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"Spoken", "attributeName":"SpokenSourcePreference", "value":""},
                        {"containerName":"Spoken", "attributeName":"directionsOnly", "value":"false"},
                        {"containerName":"Spoken", "attributeName":"ReadAtStartPreference", "value":"false"},
                        {"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":""}
                    ]
                },
                "human":{
                    "set":[
                        {"containerName":"Spoken", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Spoken", "attributeName":"SpokenSourcePreference", "value":"human"}
                    ]
                },
                "synthetic":{
                    "set":[
                        {"containerName":"Spoken", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"Spoken", "attributeName":"SpokenSourcePreference", "value":"synthetic"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["spoken-assignedsupport"] == "true";
                    var value = json["spoken-spokensourcepreference"];
                    return support ? (value.charAt(0).toUpperCase() + value.slice(1)) : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'spokenAudioActivateByDefault', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Spoken Audio - Activate by Default', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'spokenAudioActivateByDefault' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Spoken Audio - Activate by Default', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Spoken", "attributeName":"activateByDefault", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Spoken", "attributeName":"activateByDefault", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Spoken", "attributeName":"activateByDefault", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["spoken-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["spoken-activatebydefault"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'spokenAudioReadAtStart', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Spoken Audio - Read At Start', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'spokenAudioReadAtStart' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Spoken Audio - Read At Start', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Spoken", "attributeName":"ReadAtStartPreference", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Spoken", "attributeName":"ReadAtStartPreference", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Spoken", "attributeName":"ReadAtStartPreference", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["spoken-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["spoken-readatstartpreference"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'spokenAudioSpokenPreferences', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|(text\s?only)|(text and graphics)|nonvisual', -- formatregex
        'Spoken Audio - Spoken Preferences', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'spokenAudioSpokenPreferences' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Spoken Audio - Spoken Preferences', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":""}]
                },
                "textonly":{
                    "set":[{"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":"textonly"}]
                },
                "text only":{
                    "set":[{"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":"textonly"}]
                },
                "text and graphics":{
                    "set":[{"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":"textandgraphics"}]
                },
                "nonvisual":{
                    "set":[{"containerName":"Spoken", "attributeName":"UserSpokenPreference", "value":"nonvisual"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["spoken-assignedsupport"] == "true";
                    if (!support) return "";
                    var map = {
                        "textonly":"Text Only",
                        "textandgraphics":"Text and Graphics",
                        "nonvisual":"Nonvisual"
                    };
                    return map[json["spoken-userspokenpreference"]];
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'spokenAudioDirectionsOnly', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Spoken Audio - Directions Only', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'spokenAudioDirectionsOnly' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Spoken Audio - Directions Only', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"Spoken", "attributeName":"directionsOnly", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"Spoken", "attributeName":"directionsOnly", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"Spoken", "attributeName":"directionsOnly", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["spoken-assignedsupport"] == "true";
                    if (!support) return "";
                    return json["spoken-directionsonly"] == "true" ? "Yes" : "No";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Switches
    --------------------------
    PERFORM insertfieldspecification(
        'switchesScanSpeed', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|\d+', -- formatregex
        'Switches - Scan Speed', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'switchesScanSpeed' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Switches - Scan Speed', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[
                        {"containerName":"onscreenKeyboard", "attributeName":"assignedSupport", "value":"false"},
                        {"containerName":"onscreenKeyboard", "attributeName":"activateByDefault", "value":"false"},
                        {"containerName":"onscreenKeyboard", "attributeName":"scanSpeed", "value":""}
                    ]
                },
                "VALIDVALUE":{
                    "set":[
                        {"containerName":"onscreenKeyboard", "attributeName":"assignedSupport", "value":"true"},
                        {"containerName":"onscreenKeyboard", "attributeName":"scanSpeed", "value":"VALUE"}
                    ]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["onscreenkeyboard-assignedsupport"] == "true";
                    return support ? json["onscreenkeyboard-scanspeed"] : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'switchesAutomaticScanInitialDelay', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|(manual override)|\d+', -- formatregex
        'Switches - Automatic Scan - Initial Delay', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'switchesAutomaticScanInitialDelay' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Switches - Automatic Scan - Initial Delay', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanInitialDelay", "value":""}]
                },
                "manual override":{
                    "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanInitialDelay", "value":"manual"}]
                },
                "VALIDVALUE":{
                    "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanInitialDelay", "value":"VALUE"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["onscreenkeyboard-assignedsupport"] == "true";
                    var value = json["onscreenkeyboard-automaticscaninitialdelay"];
                    if (value == "manual") value = "Manual Override";
                    return support ? value : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    PERFORM insertfieldspecification(
        'switchesAutomaticScanFrequency', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|infinity|1|2|3|4|5', -- formatregex
        'Switches - Automatic Scan - Frequency', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'switchesAutomaticScanFrequency' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Switches - Automatic Scan - Frequency', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":""}]
                },
                "VALIDVALUE":{
                    "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    var support = json["onscreenkeyboard-assignedsupport"] == "true";
                    var value = json["onscreenkeyboard-automaticscanrepeat"];
                    return support ? (value.charAt(0).toUpperCase() + value.slice(1)) : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Separate, Quiet, or Individual Setting
    --------------------------
    PERFORM insertfieldspecification(
        'separateQuietOrIndividual', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Other Supports - Separate, quiet, or individual', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'separateQuietOrIndividual' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Other Supports - Separate, quiet, or individual', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"setting", "attributeName":"separateQuiteSetting", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"setting", "attributeName":"separateQuiteSetting", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"setting", "attributeName":"separateQuiteSetting", "value":"setting"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["setting-separatequitesetting"] == "setting" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Two Switch System
    --------------------------
    PERFORM insertfieldspecification(
        'twoSwitchSystem', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Supports Requiring Additional Tools - Two Switch System', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'twoSwitchSystem' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Supports Requiring Additional Tools - Two Switch System', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"supportsRequiringAdditionalTools", "attributeName":"supportsTwoSwitch", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"supportsRequiringAdditionalTools", "attributeName":"supportsTwoSwitch", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"supportsRequiringAdditionalTools", "attributeName":"supportsTwoSwitch", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["supportsrequiringadditionaltools-supportstwoswitch"] == "true" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
    
    --------------------------
    
    --------------------------
    -- Sign Interpretation
    --------------------------
    PERFORM insertfieldspecification(
        'signInterpretation', -- fieldname
        NULL, -- allowablevalues
        NULL, -- minimum
        NULL, -- maximum
        NULL, -- fieldlength
        FALSE, -- rejectifempty
        TRUE, -- rejectifinvalid
        '^$|yes|no', -- formatregex
        'Provided Outside System - Sign Interpretation', -- mappedname
        TRUE, -- showerror
        FALSE, -- iskeyvaluepairfield
        'String', -- fieldtype
        NULL, -- minimumregex
        NULL, -- maximumregex
        'i' -- regexmodeflags
    );
    
    PERFORM insertfieldspecificationsrecordtypes(
        (SELECT id FROM fieldspecification WHERE fieldname = 'signInterpretation' ORDER BY id DESC LIMIT 1), -- fieldspecid
        pnpuploadcategorycode, -- categorycode
        'Provided Outside System - Sign Interpretation', -- mappedname
        ('{
            "actions":{
                "EMPTY":{
                    "set":[{"containerName":"supportsProvidedOutsideSystem", "attributeName":"supportsSignInterpretation", "value":"false"}]
                },
                "no":{
                    "set":[{"containerName":"supportsProvidedOutsideSystem", "attributeName":"supportsSignInterpretation", "value":"false"}]
                },
                "yes":{
                    "set":[{"containerName":"supportsProvidedOutsideSystem", "attributeName":"supportsSignInterpretation", "value":"true"}]
                }
            },
            "extractScript":' || to_json(regexp_replace(
                'function(jsonStr){
                    var json = JSON.parse(jsonStr);
                    return json["supportsprovidedoutsidesystem-supportssigninterpretation"] == "true" ? "Yes" : "";
                }', '\s{2,}', '', 'g')::text)||
        '}')::JSONB, -- jsondata
        (select coalesce(max(sortorder), 0) + 10 from fieldspecificationsrecordtypes), -- sortorder
        FALSE -- required
    );
END;
$BODY$;


--F753 PNP Extract and Upload: dropping minor helper functions
DROP FUNCTION IF EXISTS insertfieldspecification(
    fieldname CHARACTER VARYING(75),
    allowablevalues TEXT,
    minimum BIGINT,
    maximum BIGINT,
    fieldlength INTEGER,
    rejectifempty BOOLEAN,
    rejectifinvalid BOOLEAN,
    formatregex CHARACTER VARYING(150),
    mappedname CHARACTER VARYING(120),
    showerror BOOLEAN,
    iskeyvaluepairfield BOOLEAN,
    fieldtype CHARACTER VARYING(75),
    minimumregex CHARACTER VARYING(300),
    maximumregex CHARACTER VARYING(300),
    regexmodeflags TEXT
);
DROP FUNCTION IF EXISTS insertfieldspecificationsrecordtypes(
    fieldspecid BIGINT,
    _categorycode CHARACTER VARYING(75),
    _mappedname CHARACTER VARYING(120),
    _jsondata JSONB,
    _sortorder INTEGER,
    _required BOOLEAN
);
DROP FUNCTION IF EXISTS getfieldspec(
    uploadtype CHARACTER VARYING(75),
    fieldname CHARACTER VARYING(75)
);


