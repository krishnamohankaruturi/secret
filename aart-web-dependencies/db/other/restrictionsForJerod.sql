begin;

INSERT INTO restriction(
            restrictionname, restrictioncode, restrictiondescription, 
            restrictedresourcetypeid)
    VALUES ( 'Top Down For Jeord', 'TOP_DOWN_JEROD', 'Organizations tied to this will not be able to do anything with rosters', 
            (Select id from category where categoryCode = 'ROSTER_RESOURCE'));

--this needs to be deleted so that it can restricted specifically. 
delete from restrictionsauthorities where isparent = true and
restrictionid = (Select id from restriction where restrictioncode = 'TOP_DOWN_ROSTERS');
--Colorado – COLO
--West Colorado District - WCOLODIST
--West Colorado Highschool – WCHS

--note that no authority is added to the restriction.

--Select * from organization where displayidentifier in ('COLO','WCOLODIST','WCHS');

update restrictionsorganizations
 set restrictionid = (select id from restriction where restrictioncode='TOP_DOWN_JEROD')
  where
   organizationid in (
   Select id from organization where displayidentifier in ('COLO','WCOLODIST','WCHS')
   );

commit;