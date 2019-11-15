
-- 341.sql

update groups set roleorgtypeid = (select id from organizationtype where typecode='BLDG') where groupname in ('Technology Director');
