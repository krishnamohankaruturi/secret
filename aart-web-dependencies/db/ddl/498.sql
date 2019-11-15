--ddl/498.sql
create table statespecialcircumstance (
	stateid bigint, 
	specialcircumstanceid bigint, 
	requireconfirmation boolean DEFAULT false,
	activeflag boolean DEFAULT true,
	createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser integer,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	modifieduser integer,
	CONSTRAINT statespecialcircumstance_stateid_fkey FOREIGN KEY (stateid)
	REFERENCES organization (id),
	CONSTRAINT statespecialcircumstance_specialcircumstanceid_fkey FOREIGN KEY (specialcircumstanceid)
	REFERENCES specialcircumstance (id)
);

alter table studentspecialcircumstance add
	createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	add createduser integer,
	add modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	add modifieduser integer,
	add activeflag boolean DEFAULT true;
