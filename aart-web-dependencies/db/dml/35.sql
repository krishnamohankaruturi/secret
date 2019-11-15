INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('keywordTranslationDisplay', 
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
