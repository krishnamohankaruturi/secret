--dml/768.sql

UPDATE fieldspecificationsrecordtypes
SET jsondata = (
    '{
        "actions":{
            "EMPTY":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":""}]
            },
            "1":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            },
            "2":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            },
            "3":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            },
            "4":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            },
            "5":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            },
            "infinity":{
                "set":[{"containerName":"onscreenKeyboard", "attributeName":"automaticScanRepeat", "value":"VALUE"}]
            }
        },
        "extractScript":' || to_json(regexp_replace(
            'function(jsonStr){
                var json = JSON.parse(jsonStr);
                var support = json["onscreenkeyboard-assignedsupport"] == "true";
                var value = json["onscreenkeyboard-automaticscanrepeat"];
                return support ? value : "";
            }', '\s{2,}', '', 'g')::text)||
    '}'
)::JSONB
WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE activeflag IS TRUE AND fieldname = 'switchesAutomaticScanFrequency' ORDER BY id DESC LIMIT 1)
AND recordtypeid = (
    SELECT c.id
    FROM category c
    JOIN categorytype ct ON c.categorytypeid = ct.id
    WHERE ct.typecode = 'CSV_RECORD_TYPE'
    AND c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
);

