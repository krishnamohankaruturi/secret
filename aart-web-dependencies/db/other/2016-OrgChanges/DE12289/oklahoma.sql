update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier in ('51H000105', '51H000705', '55E014984')
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set organizationtypeid = (select id from organizationtype where typecode = 'SCH'),
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '50H000105'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set displayidentifier = '51H000105',
organizationname = 'Oklahoma School for the Blind ES',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = 'H000511'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set displayidentifier = '51H000705',
organizationname = 'Oklahoma School for the Blind HS',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = 'H000512'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set activeflag = true,
organizationname = 'SANKOFA DISTRICT',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '72G004'
and id in (
  WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
    SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid =
      (SELECT id FROM organization WHERE displayidentifier = 'OK')
    UNION
    SELECT organizationrelation.organizationid, organizationrelation.parentorganizationid
    FROM organizationrelation, organization_relation as parentorganization_relation
    WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid
  )
  SELECT o.id FROM organization o WHERE o.id IN (SELECT organizationid FROM organization_relation)
);

update organization
set organizationname = 'Mooreland High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '77I002715'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set organizationname = 'Tulsa Legacy Charter School, Inc.',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '72E006976'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));