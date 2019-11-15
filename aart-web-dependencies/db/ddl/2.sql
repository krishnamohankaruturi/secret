--AART related tables.
--original 2.sql

CREATE TABLE restriction (
	id bigserial,
	restrictionname character varying(75) DEFAULT '  '::character varying NOT NULL,
	restrictioncode character varying(75) DEFAULT '  '::character varying NOT NULL,
	restrictiondescription character varying(75) DEFAULT '  '::character varying NOT NULL,
	restrictedresourcetypeid bigint NOT NULL
);



CREATE TABLE restrictionsauthorities (
	id bigserial,
	restrictionid bigint not null,
	authorityid bigint NOT NULL,
	isparent boolean NOT NULL,
	ischild boolean NOT NULL,
	isdifferential boolean NOT NULL
);



CREATE TABLE restrictionsorganizations (
	restrictionid bigserial,
	organizationid bigint NOT NULL,
	isenforced boolean DEFAULT false NOT NULL,
	id bigserial
);

-- make it nullable so that existing rosters can remain.

ALTER TABLE roster

	ADD COLUMN restrictionid bigint;



ALTER TABLE restriction
	ADD CONSTRAINT pk_restriction PRIMARY KEY (id);



ALTER TABLE restrictionsauthorities
	ADD CONSTRAINT restrictions_authorities_pk PRIMARY KEY (id);



ALTER TABLE restrictionsorganizations
	ADD CONSTRAINT pk_restrictions_organizations PRIMARY KEY (id);



ALTER TABLE restriction
	ADD CONSTRAINT fk_restricted_resource_type_fk FOREIGN KEY (restrictedresourcetypeid) REFERENCES category(id);

ALTER TABLE restriction
	ADD CONSTRAINT uk_restriction_code UNIQUE (restrictioncode );

ALTER TABLE restrictionsauthorities
	ADD CONSTRAINT restrictions_authorities_uk UNIQUE (restrictionid, authorityid, isparent, ischild, isdifferential);



ALTER TABLE restrictionsauthorities
	ADD CONSTRAINT authority_fk FOREIGN KEY (authorityid) REFERENCES authorities(id);



ALTER TABLE restrictionsauthorities
	ADD CONSTRAINT restriction_fk FOREIGN KEY (restrictionid) REFERENCES restriction(id);



ALTER TABLE restrictionsorganizations
	ADD CONSTRAINT uk_restrictions_organizations UNIQUE (restrictionid, organizationid, isenforced);



ALTER TABLE restrictionsorganizations
	ADD CONSTRAINT organization_fk FOREIGN KEY (organizationid) REFERENCES organization(id);



ALTER TABLE restrictionsorganizations
	ADD CONSTRAINT restriction_fk FOREIGN KEY (restrictionid) REFERENCES restriction(id);



ALTER TABLE roster
	ADD CONSTRAINT restriction_fk FOREIGN KEY (restrictionid) REFERENCES restriction(id);



CREATE VIEW resource_restriction AS
	SELECT res.restrictionname AS restriction_name,
	res.restrictioncode AS restriction_code,
	res.restrictiondescription AS restriction_description,
	res.restrictedresourcetypeid AS restricted_resource_type_id,
	resorgs.id AS restrictions_organizations_id,
	resorgs.restrictionid AS restriction_id,
	resorgs.organizationid AS organization_id,
	resorgs.isenforced AS is_enforced,
	resauths.id AS restrictions_authorities_id,
	resauths.authorityid AS authority_id,
	resauths.isparent AS is_parent,
	resauths.ischild AS is_child,
	diffauths.authorityid AS differential_authority_id
	FROM restrictionsorganizations resorgs,
	restrictionsauthorities resauths,
	(restriction res LEFT JOIN restrictionsauthorities diffauths ON (((((diffauths.restrictionid = res.id)
	AND (diffauths.isparent = false)) AND (diffauths.ischild = false))
	AND (diffauths.isdifferential = true))))
	WHERE (((res.id = resorgs.restrictionid) AND (resauths.restrictionid = res.id))
	AND (resauths.isdifferential = false));

