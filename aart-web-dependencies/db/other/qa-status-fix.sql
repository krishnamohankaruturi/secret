ALTER TABLE test DROP COLUMN status;
ALTER TABLE test ADD COLUMN status bigint REFERENCES category(id);

update test set status = (select id from category where categorytypeid = (select id from categorytype where typecode = 'TESTSTATUS') and categorycode = 'DEPLOYED')
    where status is null 
    or status != (select id from category where categorytypeid = (select id from categorytype where typecode = 'TESTSTATUS') and categorycode = 'NOT_DEPLOYED');