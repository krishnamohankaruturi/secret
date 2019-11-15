
-- 339.sql

update groups set roleorgtypeid = organizationtypeid;
update groups set roleorgtypeid = (select id from organizationtype where typecode='BLDG') where groupname in ('Building Principal','Building Test Coordinator','Building User');
