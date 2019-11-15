
--DE6553 fix surveys with a null status

update survey set status = (select id from category 
	where categorycode='IN_PROGRESS' and categorytypeid = (select id from categorytype where typecode='SURVEY_STATUS')) where status is null;