-- create a placeholder session just to hold the stuff we're moving
INSERT INTO testsession (id, name, createduser, modifieduser, activeflag, testcollectionid)
VALUES (
	nextval('testsession_id_seq'::regclass),
	'CPASS duplicate session cleanup',
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	FALSE,
	4827
);

UPDATE studentsresponses
SET activeflag = FALSE,
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE studentstestsid IN (
	8425094, /**/ 8391784, 8391782, 8425486, 8391781, 8391783, 8391780, /**/ 8425262, /**/ 8425476
);

UPDATE studentstestsections
SET activeflag = FALSE,
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE studentstestid IN (
	8425094, /**/ 8391784, 8391782, 8425486, 8391781, 8391783, 8391780, /**/ 8425262, /**/ 8425476
);

UPDATE studentstests
SET testsessionid = (SELECT id FROM testsession WHERE name = 'CPASS duplicate session cleanup'),
activeflag = FALSE,
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE id IN (
	8425094, /**/ 8391784, 8391782, 8425486, 8391781, 8391783, 8391780, /**/ 8425262, /**/ 8425476
);

UPDATE studentstests
SET testsessionid = 2139849
WHERE id IN (8425491, 8425489, 8425488, 8425490, 8425487);

-- deactivate bad sessions
UPDATE testsession
SET activeflag = FALSE,
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE id IN (
	2149254, 2149592, 2149591, 2149590, 2149589, 2149588, 2149587, 2149389, 2149577
);