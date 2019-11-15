INSERT INTO categorytype(
            typename, typecode, typedescription, originationcode)
    VALUES ('STIMULUSFORMAT', 'STIMULUSFORMAT', 'Stimulus Format', 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('SELECT', 'SELECT', 'Select',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), -1, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('TEXT', 'TEXT', 'Text',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), 1, 'CB');


INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('AUDIO', 'AUDIO', 'Audio',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), 2, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('VIDEO', 'VIDEO', 'Video',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), 3, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('IMAGE', 'IMAGE', 'Image',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), 4, 'CB');

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode)
    VALUES ('HYPERLINK', 'HYPERLINK', 'Hyperlink',
    (select id from categorytype where originationcode = 'CB' and typecode = 'STIMULUSFORMAT'), 5, 'CB');
