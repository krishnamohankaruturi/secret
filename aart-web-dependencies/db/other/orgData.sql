--this script is to be executed only once.

INSERT INTO organization (organizationname, displayidentifier, organizationtypeid)
values ('West Central Colorado District', 'WCCD', (select id from organizationtype where typecode = 'DT'));

INSERT INTO organizationrelation (organizationid, parentorganizationid)
values (currval('organization_id_seq'), (select id from organization where displayidentifier = 'WESTCOLO'));

INSERT INTO organization (organizationname, displayidentifier, organizationtypeid)
values ('West Central Colorado High School', 'WCCH', (select id from organizationtype where typecode = 'SCH')),
('West Central Colorado Middle School', 'WCCM', (select id from organizationtype where typecode = 'SCH'));

INSERT INTO organizationrelation (organizationid, parentorganizationid)
values ( (select id from organization where displayidentifier = 'WCCH') , (select id from organization where displayidentifier = 'WCCD')),
	( (select id from organization where displayidentifier = 'WCCM') , (select id from organization where displayidentifier = 'WCCD'));

INSERT INTO organization (organizationname, displayidentifier, organizationtypeid)
values ('Northwest Colorado High School', 'NWHS', (select id from organizationtype where typecode = 'SCH'));

INSERT INTO organizationrelation (organizationid, parentorganizationid)
values (currval('organization_id_seq'), (select id from organization where displayidentifier = 'WCOLODIST'));

INSERT INTO groups (organizationid, groupname, defaultrole)
(Select id, 'DEFAULT', true from organization where displayidentifier in ('WCCD', 'WCCH', 'WCCM', 'NWHS'));