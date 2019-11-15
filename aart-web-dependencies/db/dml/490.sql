--dml/490.sql

--script from tde team
delete from testjson;

-- allow underscore in user email
update fieldspecification set formatregex = '^[\w~`!#$%^&*_+={};:/?|-]+(\.{0,1}[\w~`!#$%^''&*_+={};:/?|-]+)*@{1}[A-Za-z0-9-_]+(\.{0,1}[A-Za-z0-9_]+-{0,1})*\.{1}[A-Za-z0-9]{2,}$' 
where id =(SELECT distinct 
 
 fieldSpec.id
       FROM public.fieldspecification fieldSpec,
     public.fieldspecificationsrecordtypes fieldSpecRel,category cat
 WHERE  
   fieldSpec.id = fieldSpecRel.fieldspecificationid
   and cat.id =  fieldSpecRel.recordtypeid
   and fieldSpecRel.activeflag is true
   and fieldSpec.fieldname = 'email'
  AND fieldSpecRel.recordtypeid = (select id as recordtypeid from
   category where categorycode='USER_RECORD_TYPE'));