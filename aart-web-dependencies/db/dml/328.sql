--update pd report data
update modulereport set reporttypeid=1 where reporttype='Training Status';

update modulereport set organizationid=stateid, 
organizationtypeid=(select id from organizationtype where typecode='ST') 
where reporttypeid=1 and stateid is not null;
