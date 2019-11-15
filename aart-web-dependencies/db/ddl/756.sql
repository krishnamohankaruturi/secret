-- ddl/756.sql

-- F848 DDL
DO
$BODY$
DECLARE
    tablenames TEXT[] := ARRAY[
        'appconfiguration', 'student', 'enrollmentsrosters', 'aartuser', 'organization'
    ]::TEXT[];
    columnnames TEXT[] := ARRAY[
        'assessmentprogramid', 'externalid', 'externalid', 'externalid', 'externalid'
    ]::TEXT[];
    columntypes TEXT[] := ARRAY[
        'BIGINT', 'CHARACTER VARYING(30)', 'BIGINT', 'CHARACTER VARYING(30)', 'CHARACTER VARYING(30)'
    ]::TEXT[];
    columnadditionals TEXT[] := ARRAY[
        '', '', '', '', ''
    ]::TEXT[];
    
    stmt TEXT := '';
BEGIN
    IF NOT (
        array_length(tablenames, 1) = array_length(columnnames, 1) AND
        array_length(tablenames, 1) = array_length(columntypes, 1) AND
        array_length(tablenames, 1) = array_length(columnadditionals, 1)
    ) THEN
        RAISE EXCEPTION 'Array lengths do not all match.' USING HINT = 'Make sure all of your data is accounted for.';
    END IF;
    
    -- loop through our column additions
    FOR i IN 1 .. array_length(tablenames, 1) LOOP
        IF NOT EXISTS (
            SELECT column_name
            FROM information_schema.columns
            WHERE table_name = tablenames[i]
            AND column_name = columnnames[i]
        ) THEN
        	SELECT 'ALTER TABLE ' || tablenames[i] || ' ADD COLUMN ' || columnnames[i] || ' ' || columntypes[i] || ' ' || columnadditionals[i] INTO stmt;
            RAISE NOTICE 'Did not find column "%.%", executing %', tablenames[i], columnnames[i], stmt;
            EXECUTE stmt;
        ELSE
            RAISE NOTICE 'Found column "%.%", skipping ALTER TABLE...', tablenames[i], columnnames[i];
        END IF;
    END LOOP;
    
    -- add our foreign key constraint if necessary
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE contype = 'f'
        AND conname = 'assessmentprogramid_appconfiguration_fk'
    ) THEN
        RAISE NOTICE 'Did not find foreign key constraint for appconfiguration.assessmentprogramid, adding...';
        ALTER TABLE appconfiguration
            ADD CONSTRAINT assessmentprogramid_appconfiguration_fk
            FOREIGN KEY (assessmentprogramid)
            REFERENCES assessmentprogram (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION;
    ELSE
        RAISE NOTICE 'Found foreign key constraint for appconfiguration.assessmentprogramid, skipping ADD CONSTRAINT';
    END IF;
    
END;

$BODY$;

CREATE TABLE IF NOT EXISTS public.usercontentareas(
	id bigserial NOT NULL,
	userid bigint NOT NULL,
	contentareaid bigint NOT NULL,
	activeflag boolean DEFAULT true,
	createduser bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieduser bigint,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time ZONE,
	CONSTRAINT pk_usercontentareas PRIMARY KEY (id),
	CONSTRAINT usercontentareas_userid_fk FOREIGN KEY (userid)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT usercontentareas_contentareaid_fk FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT usercontentareas_createduser_fk FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT usercontentareas_modifieduser_fk FOREIGN KEY (modifieduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.apidashboarderror(
	id bigserial NOT NULL,
	recordtype varchar(20) NOT NULL,
	requesttype varchar(20) NOT NULL,
	externalid varchar(30),
	name varchar(170),
	stateid bigint,
	districtid bigint,
	schoolid bigint,
	classroomid bigint,
	message text,
	createduser bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieduser bigint,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time ZONE,
	CONSTRAINT pk_apidashboarderror PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.organizationaddress(
	id bigserial NOT NULL,
	orgid bigint NOT NULL,
	orgaddress1 varchar(100),
	orgaddress2 varchar(100),
	city varchar(100),
	state varchar(10),
	zip varchar(5),
	createduser bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieduser bigint,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time ZONE,
	CONSTRAINT pk_organizationaddress PRIMARY KEY (id),
	CONSTRAINT organizationaddress_id_fk FOREIGN KEY (orgid)
		REFERENCES organization (id) MATCH SIMPLE 
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.timezonezipcodes
( id bigserial NOT NULL,
  zipcode  character varying(5) unique,
  timezoneid  bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL, 
  CONSTRAINT timezonezipcodes_pkey PRIMARY KEY (id),
  CONSTRAINT fk_timezonezipcodes_created_user FOREIGN KEY (createduser)
      REFERENCES public.aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_timezonezipcodes_updated_user FOREIGN KEY (modifieduser)
      REFERENCES public.aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT timezonezipcodes_timezone_fk FOREIGN KEY (timezoneid)
      REFERENCES public.category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
