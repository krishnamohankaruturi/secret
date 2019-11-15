begin;

INSERT INTO restriction(
            restrictionname, restrictioncode, restrictiondescription, 
            restrictedresourcetypeid)
    VALUES ( 'Top Down Enrollment For Jeord', 'TOP_DOWN_ENROLLMENT_JEROD',
    'Organizations tied to this will not be able to do anything with enrollment', 
            (Select id from category where categoryCode = 'ENROLLMENT_RESOURCE'));

--this needs to be deleted so that it can restricted specifically. 
delete from restrictionsauthorities where isparent = true and
restrictionid = (Select id from restriction where restrictioncode = 'TOP_DOWN_ENROLLMENTS');            

--requirement
--all regions and schools with in colorado should be restricted from doing anything in
--enrollments.

update restrictionsorganizations
 set restrictionid = (select id from restriction where restrictioncode='TOP_DOWN_ENROLLMENT_JEROD')
  where
   organizationid in (
	SELECT id from organization
 where
  organizationtypeid in (select id from organizationtype where typecode in ('SCH','RG')) and
  id in (Select id from organization_children(
  (Select id from organization where displayidentifier='COLO')
  ))
   ) and
   restrictionid
   = (Select id from restriction where restrictioncode = 'TOP_DOWN_ENROLLMENTS');

commit;