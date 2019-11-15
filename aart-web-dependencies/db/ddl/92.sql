--US12993: Technical: Redundant data and constraint cleanup

ALTER table contentarea DROP CONSTRAINT uk_name;
DROP INDEX uk_content_area_name_idx;