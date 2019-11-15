--441.sql

ALTER TABLE testpanel RENAME inuse to activeflag;

create table domainaudithistory
(
	id			bigserial NOT NULL,
	source			character varying(25) not null,					
	objecttype		character varying(50) not null,		
	objectid		bigint	not null,
	createduserid	integer	not null,
	createddate		timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	action			character varying(25) not null
);
