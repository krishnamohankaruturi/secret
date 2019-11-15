-- dml/607.sql

select repopulateActiveNos();

-- Create A new test specification by name “Instructional-Interim”
insert into testspecification (specificationname,activeflag) values ('Instructional-Interim',true);
insert into testspecstatementofpurpose (testspecificationid,statementofpurposeid,activeflag)
values ((select id from testspecification where specificationname ilike 'instructional-interim' and activeflag is true)
,(select id from category where categoryname ilike 'instructional' and activeflag is true), true);

-- This script is for changing existing testspecifications in interim to the one created above. This will solve the existing problem in pdn.
 
update test set testspecificationid= (select id from testspecification where specificationname 
ilike 'instructional-interim' and activeflag is true limit 1) where is_interim_test is true;

update  category set categoryname = 'Completed' where categorytypeid in (select id from categorytype where typecode = 'SCORING_STATUS') and categorycode = 'COMPLETED';