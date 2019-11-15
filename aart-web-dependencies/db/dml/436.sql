
--/436.sql

update fieldspecification set rejectifempty = true, fieldlength=550 where fieldname = 'levelDescription' ;

update survey set status=(select id from category where categorytypeid=(select id from categorytype where typecode='SURVEY_STATUS') and categorycode='IN_PROGRESS')
where status=(select id from category where categorytypeid=(select id from categorytype where typecode='SURVEY_STATUS') and categorycode='NOT_STARTED') and activeflag is true;

 