--ddl/560.sql US17974 - scriptbees

-- To facilitate the persistence requirement of default assessment program selection
ALTER TABLE userassessmentprogram ADD isdefault boolean;
ALTER TABLE userassessmentprogram add
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	add createduser integer,
	add modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	add modifieduser integer;

-- To facilitate the default behaviour of the Assessment program selection when user logged in. (In case there is no user persisted value exists)
ALTER TABLE orgassessmentprogram ADD isdefault boolean;

