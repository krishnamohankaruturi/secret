-- changes to make the correct Lincoln Elementary School active
UPDATE organization
SET activeflag = TRUE,
displayidentifier = '216102 435',
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE id = 21059;

UPDATE organization
SET activeflag = FALSE,
modifieddate = NOW(),
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE id = 69252;