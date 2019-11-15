-- US15863: Summative Report upload

INSERT INTO category (id, categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES
(
	(SELECT nextval('category_id_seq')),
	'Excluded Items',
	'EXCLUDED_ITEMS',
	'Excluded Items',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	(SELECT nextval('category_id_seq')),
	'Level Descriptions',
	'LEVEL_DESCRIPTIONS',
	'Level Descriptions',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	(SELECT nextval('category_id_seq')),
	'Miscellaneous Report Text',
	'MISCELLANEOUS_REPORT_TEXT',
	'Miscellaneous Report Text',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	(SELECT nextval('category_id_seq')),
	'Subscore Description and Report Usage',
	'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE',
	'Subscore Description and Report Usage',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	(SELECT nextval('category_id_seq')),
	'Subscore Framework Mapping',
	'SUBSCORE_FRAMEWORK_MAPPING',
	'Subscore Framework Mapping',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	(SELECT nextval('category_id_seq')),
	'Subscore Raw to Scale Score',
	'SUBSCORE_RAW_TO_SCALE_SCORE',
	'Subscore Raw to Scale Score',
	(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);
