--CB related changes for R5 I3
--accessibility
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription,
originationcode)
VALUES ('Read Aloud', 'READ_ALOUD', 'Read Aloud', 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Text Only', 'TEXT_ONLY', 'Text Only', (select id from categorytype where typecode='READ_ALOUD'), 1, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Graphic Only', 'GRAPHIC_ONLY', 'Graphic Only',
(select id from categorytype where typecode='READ_ALOUD'), 2, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Text and Graphic', 'TEXT_GRAPHIC',
'Text and Graphic', (select id from categorytype where typecode='READ_ALOUD'), 3, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Non-Visual', 'NON_VISUAL', 'Non-Visual',
(select id from categorytype where typecode='READ_ALOUD'), 4, 'CB');

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode)
VALUES ('Accessiblity File', 'ACCESSIBILITY_FILE', 'Accessibility File', 'CB');

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
categorytypeid, externalid, originationcode)
VALUES ('Audio', 'AUDIO', 'Audio', (select id from categorytype where typecode='ACCESSIBILITY_FILE'), 1, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
categorytypeid, externalid, originationcode)
VALUES ('Video', 'VIDEO', 'Video', (select id from categorytype where typecode='ACCESSIBILITY_FILE'), 2, 'CB');

--test collection
INSERT INTO CATEGORYTYPE (typename, typecode,
typedescription, originationcode)
VALUES ('System Select Option', 'SYSTEM_SELECT_OPTION', 'System Select Option', 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Randomization', 'RADOMIZATION', 'Randomization',
(select id from categorytype where typecode='SYSTEM_SELECT_OPTION'), 1, 'CB');

--learning maps

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode)
VALUES ('Node Weight', 'NODE_WEIGHT', 'Node Weights', 'CB');

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
categorytypeid, externalid, originationcode)
VALUES ('Compensatory', 'COMPENSATORY', 'Compensatory',
(select id from categorytype where typecode='NODE_WEIGHT'), 1, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Inhibitor', 'INHIBITOR', 'Inhibitor',
(select id from categorytype where typecode='NODE_WEIGHT'), 3, 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid, externalid, originationcode)
VALUES ('Non-Compensatory', 'NON-COMPENSATORY', 'Non-Compensatory',
(select id from categorytype where typecode='NODE_WEIGHT'), 2, 'CB');

INSERT INTO CATEGORYTYPE (typename, typecode,
typedescription, originationcode)
VALUES ('Node Type Code', 'NODE_TYPE_CODE', 'Node Type Codes', 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid,  originationcode)
VALUES ('Target', 'TARGET', 'Target',
(select id from categorytype where typecode='NODE_TYPE_CODE'), 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid,  originationcode)
VALUES ('Precursor', 'PRECURSOR', 'Precursor',
(select id from categorytype where typecode='NODE_TYPE_CODE'), 'CB');

INSERT INTO CATEGORY (categoryname, categorycode,
categorydescription, categorytypeid,  originationcode)
VALUES ('Successor', 'SUCCESSOR', 'Successor',
(select id from categorytype where typecode='NODE_TYPE_CODE'),'CB');

update category set createduser=(select id from aartuser where username='cetesysadmin')
where createduser is null;

update category set modifieduser=(select id from aartuser where username='cetesysadmin')
where modifieduser is null;

update categorytype set createduser=(select id from aartuser where username='cetesysadmin')
where createduser is null;

update categorytype set modifieduser=(select id from aartuser where username='cetesysadmin')
where modifieduser is null;

--it needs to be added here so that the constraint violation will not happen.

ALTER TABLE CATEGORY ADD CONSTRAINT fk_category_crdusr FOREIGN KEY (createduser)
REFERENCES aartuser (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE CATEGORY ALTER column createduser set not null;

ALTER TABLE CATEGORY ADD CONSTRAINT fk_category_updusr FOREIGN KEY (modifieduser)
REFERENCES aartuser (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE CATEGORY ALTER column modifieduser set not null;

ALTER TABLE CATEGORYTYPE ADD CONSTRAINT fk_category_type_crdusr FOREIGN KEY (createduser)
REFERENCES aartuser (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE CATEGORYTYPE ALTER column modifieduser set not null;

ALTER TABLE CATEGORYTYPE ADD CONSTRAINT fk_category_type_updusr FOREIGN KEY (modifieduser)
REFERENCES aartuser (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE CATEGORYTYPE ALTER column modifieduser set not null;

INSERT INTO CATEGORY (categoryname, categorycode, 
categorydescription, categorytypeid,  originationcode, createduser, 
modifieduser)
VALUES ('Text - Other', 'TEXTOTHER', 'Text - Other',
(select id from categorytype where typecode='STIMULUSFORMAT'), 'CB',
(SELECT id FROM aartuser where username ='cetesysadmin'),
(SELECT id FROM aartuser where username ='cetesysadmin'));