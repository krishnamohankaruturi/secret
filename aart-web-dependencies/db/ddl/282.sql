
--US15034
create table profileitemattrnameattrcontainerviewoptions 
(
	id bigserial NOT NULL,
	pianacid bigint NOT NULL,
	assessmentprogramid bigint NOT NULL,
	viewoption character varying(10) NOT NULL,
	createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser integer NOT NULL,
	activeflag boolean DEFAULT true,
	modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	modifieduser integer NOT NULL,
	CONSTRAINT pk_profile_item_attr_name_attr_container_view_options PRIMARY KEY (id),
	CONSTRAINT fk_pianacvo_profileitemattributenameattributecontainer FOREIGN KEY (pianacid)
		REFERENCES profileitemattributenameattributecontainer (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_pianacvo_assessmentprogram FOREIGN KEY (assessmentprogramid)
		REFERENCES assessmentprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT uk_pianac_ap UNIQUE (pianacid, assessmentprogramid)
);

CREATE OR REPLACE FUNCTION getstudentassessmentprogram(sid bigint, schoolyear bigint)
  RETURNS bigint AS
$BODY$
DECLARE
    apid bigint;
BEGIN
	
	apid = (select assessmentprogramid from student where id = sid);
	IF (apid is null) THEN
		apid = (select assessmentprogramid from orgassessmentprogram oap join assessmentprogram ap on ap.id = oap.assessmentprogramid 
		where oap.organizationid =(select id from organization_parent((select attendanceschoolid from enrollment e where e.currentschoolyear=schoolyear and e.studentid=sid limit 1)) op where op.organizationtypeid=2)
		and ap.abbreviatedname in ('KAP', 'AMP'));
	END IF;
	return apid;	
	END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;