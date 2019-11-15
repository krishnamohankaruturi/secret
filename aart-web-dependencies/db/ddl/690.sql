--ddl/690.sql
-- F633-Improve visibility of merged schools

CREATE TABLE organizationoperationaldetail (
    id bigserial NOT NULL,
    sourceorgid bigint,
    sourceorgdisplayidentifier character varying(100) NOT NULL,
    sourceorgname character varying(100),
    targetorgid bigint,
    activeflag boolean,
    action character varying(30),
    schoolyear bigint,
    reportyear bigint,
    createddate timestamp with time zone DEFAULT now(),
    modifieddate timestamp with time zone DEFAULT now(),
    createduser integer,
    modifieduser integer,

     CONSTRAINT mergedoperation_pkey PRIMARY KEY (id),

      CONSTRAINT fk_source_orgid FOREIGN KEY (sourceorgid)
      REFERENCES public.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

      CONSTRAINT fk_target_orgid FOREIGN KEY (targetorgid)
      REFERENCES public.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
     
      CONSTRAINT fk_org_updated_user FOREIGN KEY (modifieduser)
      REFERENCES public.aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT fk_org_created_user FOREIGN KEY (createduser)
      REFERENCES public.aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
    
    );

    CREATE INDEX idx_mergedoperational_id
	ON public.organizationoperationaldetail
	USING btree
	(id);
   CREATE INDEX idx_sourceorg_id
	ON public.organizationoperationaldetail
	USING btree
	(sourceorgid);
	
   CREATE INDEX idx_targetorg_id
	ON public.organizationoperationaldetail
	USING btree
	(targetorgid);

DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='studentsresponses' AND column_name='readableresponse'
	) THEN
		RAISE NOTICE 'readableresponse found, skipping add';
	ELSE
		ALTER TABLE studentsresponses ADD COLUMN readableresponse text;
	END IF;
END
$BODY$;

