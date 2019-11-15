--ddl/710.sql

ALTER TABLE subscoresdescription ALTER COLUMN subscorereportdescription TYPE text;

ALTER TABLE studentreport ADD COLUMN createduser bigint DEFAULT 12, 
		ADD COLUMN modifieduser bigint DEFAULT 12, 
		ADD COLUMN createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
		ADD COLUMN modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;
		
ALTER TABLE testlet ADD COLUMN gradebandid bigint;		