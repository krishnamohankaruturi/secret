
--will make sure that all future test records have the default uitypecode
-- INFO CB related tables.

alter table test alter column uiTypeCode set default 'genTest';

ALTER TABLE test DROP COLUMN status;
ALTER TABLE test ADD COLUMN status bigint REFERENCES category(id);

update test set status = (select id from category where categorytypeid = (
select id from categorytype where typecode = 'TESTSTATUS') and categorycode = 'DEPLOYED')
    where status is null 
    or status != (select id from category where categorytypeid = (
    select id from categorytype where typecode = 'TESTSTATUS') and categorycode = 'NOT_DEPLOYED');
    
--identify difference between CB and AART for integration.
   
--execute missing table changes in integration.

ALTER TABLE contentframeworkdetail
   ALTER COLUMN description TYPE character varying(2000);
ALTER TABLE organization
   ALTER COLUMN organizationname TYPE character varying(100);
ALTER TABLE organization
   ALTER COLUMN displayidentifier TYPE character varying(100);
   
ALTER TABLE taskvariant DROP COLUMN itemname;

--fix on randomization.

ALTER TABLE testcollection
   ALTER COLUMN randomizationtype SET DEFAULT 'enrollment';

update testcollection set randomizationtype = 'enrollment' where
 ( randomizationtype is null or randomizationtype not in ('enrollment','login') );

 ALTER TABLE testcollection
   ALTER COLUMN randomizationtype SET NOT NULL;