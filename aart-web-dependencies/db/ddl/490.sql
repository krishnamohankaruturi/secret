-- ddl/490.sql for KAP adaptive enrollments

ALTER TABLE enrollment ADD COLUMN sourcetype character varying(20);

-- audit columns on userorganizations
ALTER TABLE usersorganizations ADD COLUMN createddate timestamp with time zone;
ALTER TABLE usersorganizations ALTER COLUMN createddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE usersorganizations ADD COLUMN createduser integer;
ALTER TABLE usersorganizations ADD COLUMN modifieddate timestamp with time zone;
ALTER TABLE usersorganizations ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE usersorganizations ADD COLUMN modifieduser integer;

-- audit columns on userorganizationsgroups
ALTER TABLE userorganizationsgroups ADD COLUMN createddate timestamp with time zone;
ALTER TABLE userorganizationsgroups ALTER COLUMN createddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE userorganizationsgroups ADD COLUMN createduser integer;
ALTER TABLE userorganizationsgroups ADD COLUMN modifieddate timestamp with time zone;
ALTER TABLE userorganizationsgroups ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE userorganizationsgroups ADD COLUMN modifieduser integer;