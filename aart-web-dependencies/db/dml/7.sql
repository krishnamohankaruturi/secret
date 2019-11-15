--fix earlier data.
update aartuser set email = 'cete@ku.edu' where username = 'CETESysAdmin';

--insert the category type, then category.

insert into categorytype (typename, typecode, typedescription, originationcode) (Select 'Student Test Status', 'STUDENT_TEST_STATUS','Student Test Status', 'TDE'
where not exists (select 1 from categorytype where typecode = 'STUDENT_TEST_STATUS') );
 
insert into category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode) 
(Select 'Complete', 'complete', 'Complete', id, 3, 'TDE'  from categorytype where originationcode = 'TDE' and typecode = 'STUDENT_TEST_STATUS'
and not exists (select 1 from category cat, categorytype catType where cat.categorytypeid = cattype.id and cat.categorycode = 'complete' and cattype.typecode = 'STUDENT_TEST_STATUS'));
 
insert into category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode) 
(Select 'In Progress', 'inprogress', 'In Progress', id, 2, 'TDE'  from categorytype where originationcode = 'TDE' and typecode = 'STUDENT_TEST_STATUS'
and not exists (select 1 from category cat, categorytype catType where cat.categorytypeid = cattype.id and cat.categorycode = 'inprogress' and cattype.typecode = 'STUDENT_TEST_STATUS'));
 
insert into category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode) 
(Select 'Unused', 'unused', 'Unused', id, 1, 'TDE'  from categorytype where originationcode = 'TDE' and typecode = 'STUDENT_TEST_STATUS'
and not exists (select 1 from category cat, categorytype catType where cat.categorytypeid = cattype.id and cat.categorycode = 'unused' and cattype.typecode = 'STUDENT_TEST_STATUS'));

--AART relted table
--Original 12.sql
update testsession set status = (select id from category where categorycode = 'unused') where status is null;

update fieldspecification set formatregex ='^[\w~`!#$%^&*_+={};:/?|-]+(\.{0,1}[\w~`!#$%^&*_+={};:/?|-]+)*@{1}[A-Za-z0-9]+(\.{0,1}[A-Za-z0-9]+-{0,1})*\.{1}[A-Za-z0-9]{2,}$'
where fieldname = 'email';