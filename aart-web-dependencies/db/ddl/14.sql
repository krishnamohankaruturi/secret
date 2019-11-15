--25.sql publishing changes.
--testlet layout additions to AART

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Testlet Layout', 'TESTLET_LAYOUT', 'Testlet Layout', 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Horizontal - Stim Left', 'passage', 'Horizontal - Stim Left', (select id from categorytype where typecode='TESTLET_LAYOUT'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Horizontal - Stim Right', 'passage_right', 'Horizontal - Stim Right', (select id from categorytype where typecode='TESTLET_LAYOUT'),  'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Vertical', 'passage_vertical_stacked', 'Vertical', (select id from categorytype where typecode='TESTLET_LAYOUT'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Default Question View', 'DEFAULT_QUESTION_VIEW', 'Default Question View', 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('All at once', 'all_at_once', 'All at once', (select id from categorytype where typecode='DEFAULT_QUESTION_VIEW'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('One at a time', 'one_at_a_time', 'One at a time', (select id from categorytype where typecode='DEFAULT_QUESTION_VIEW'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Display View', 'DEFAULT_DISPLAY_VIEW', 'Display View', 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Questions and Passage Together', 'questions_and_passage', 'Questions and Passage Together', (select id from categorytype where typecode='DEFAULT_DISPLAY_VIEW'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Question Only', 'questions_only', 'Question Only', (select id from categorytype where typecode='DEFAULT_DISPLAY_VIEW'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)

VALUES ('Passage Only', 'passage_only', 'Passage Only', (select id from categorytype where typecode='DEFAULT_DISPLAY_VIEW'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


ALTER TABLE testlet ADD COLUMN testletlayoutid bigint;

UPDATE testlet set testletlayoutid=(SELECT id FROM category WHERE categorytypeid = (select id from categorytype where typecode = 'TESTLET_LAYOUT') and categorycode='passage');

ALTER TABLE testlet ALTER COLUMN testletlayoutid SET NOT NULL;


ALTER TABLE testlet ADD COLUMN questionviewid bigint;

UPDATE testlet set questionviewid = (SELECT id From category where categorytypeid = (select id from categorytype where typecode = 'DEFAULT_QUESTION_VIEW') and categorycode='all_at_once');

ALTER TABLE testlet ALTER COLUMN questionviewid SET NOT NULL;


ALTER TABLE testlet ADD COLUMN questionlocked boolean;

UPDATE testlet set questionlocked = false;

ALTER TABLE testlet ALTER COLUMN questionlocked SET NOT NULL;


ALTER TABLE testlet ADD COLUMN displayviewid bigint;

UPDATE testlet set displayviewid = (SELECT id From category where categorytypeid = (select id from categorytype where typecode = 'DEFAULT_DISPLAY_VIEW') and categorycode='questions_and_passage');

ALTER TABLE testlet ALTER COLUMN displayviewid SET NOT NULL;


ALTER TABLE testlet ADD COLUMN stimulusneeded boolean NOT NULL default false;

ALTER TABLE testlet ALTER COLUMN stimulusneeded drop default;

ALTER TABLE testlet ADD CONSTRAINT fk_testletlayout_id

 FOREIGN KEY (testletlayoutid) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE testlet ADD CONSTRAINT fk_questionview_id

 FOREIGN KEY (questionviewid) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE testlet ADD CONSTRAINT fk_displayview_id

 FOREIGN KEY (displayviewid) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
	
	
	