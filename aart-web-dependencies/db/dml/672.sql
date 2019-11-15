--dml/672.sql 

-- Change the order of the question to last in the series of questions for page 9
update surveylabel  set surveyorder  = 305,
modifieduser = (select id from aartuser where username='cetesysadmin'),
modifieddate = now()
where labelnumber = 'Q47';

update surveysection  set surveysectionname='Computer Access and Attention During Instruction',
modifieduser = (select id from aartuser where username='cetesysadmin'),
modifieddate = now()
where surveysectioncode='ACCESS_AND_SWITCHES';

--pooltype,statemodel
update category set categorycode = 'SINGLEEE' where categoryname = 'Single EE' 
and categorytypeid = (select id from categorytype where typecode  ='DLM_TESTING_MODEL');

update category set categorycode = 'MULTIEEOFC' where categoryname = 'Multi EE - EOC' 
and categorytypeid = (select id from categorytype where typecode  ='DLM_TESTING_MODEL'); 

update category set categorycode = 'MULTIEEOFG' where categoryname = 'Multi EE – EOG' 
and categorytypeid = (select id from categorytype where typecode  ='DLM_TESTING_MODEL');

update organization org set testingmodel = (select id from category where categorycode = org.pooltype) 
where organizationtypeid = 2 and (pooltype is not null or pooltype = ''); 

update organization org set pooltype = (select categorycode from category where id = org.testingmodel) 
where organizationtypeid = 2 and testingmodel is not null;

update testcollection set activeflag= false, modifieddate=now() where id in (
select tct.testcollectionid from testcollectionstests tct 
   join interimtest it on it.testcollectionid=tct.testcollectionid and it.currentschoolyear is null);

update category set categorycode = 'MULTIEEOFG' where categoryname = 'Multi EE - EOG' 
	and categorytypeid = (select id from categorytype where typecode  ='DLM_TESTING_MODEL');

update organization org set testingmodel = (select id from category where categorycode = org.pooltype) 
	where organizationtypeid = 2 and (pooltype is not null or pooltype = ''); 

update organization org set pooltype = (select categorycode from category where id = org.testingmodel) 
	where organizationtypeid = 2 and testingmodel is not null;
	
update organization set pooltype = 'MULTIEEOFG' , testingmodel = (select id from category where categorycode = 'MULTIEEOFG') 
 where organizationtypeid = 2 and pooltype = 'MEOG';
	
update brailleaccommodation set brailleabbreviation='EBAE' 
	where accessibilityfileid in (select id from accessibilityfile  where filetypeid =542) 
	and brailleabbreviation='UCB';