
--Name: ititestsessionhistory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 

CREATE TABLE public.ititestsessionhistory
(
   archid bigint NOT NULL,   
   studentid bigint, 
   rosterid bigint, 
   roster_archid bigint NOT NULL, 
   name character varying(200), 
   status bigint, 
   testid bigint, 
   testcollectionid bigint, 
   testcollectionname character varying(200), 
   saveddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   confirmdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   essentialelement text, 
   linkagelevel text, 
   leveldescription text, 
   createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   createduser bigint, 
   modifieduser bigint, 
   testsessionid bigint, 
   studentenrlrosterid bigint, 
   claim text, 
   conceptualarea text, 
   essentialelementid bigint, 
   activeflag boolean DEFAULT true, 
   arch_createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   arch_modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone, 
   sensitivityflags text, 
   CONSTRAINT ititestsessionhistory_pkey PRIMARY KEY (archid), 
   CONSTRAINT fk_ititestsessionhistory_roster_archid FOREIGN KEY (roster_archid) REFERENCES roster (archid) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;

--Name: ititestsessionhistory_archid; Type: SEQUENCE; Schema: public; Owner: postgres; Tablespace: 
CREATE SEQUENCE ititestsessionhistory_archid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ititestsessionhistory ADD COLUMN ititestsessionhistoryid BIGINT NOT NULL;


ALTER TABLE ititestsessionhistory RENAME COLUMN sensitivityflags TO sensitivitytags;


ALTER TABLE enrollmentsrosters DROP CONSTRAINT enrollmentsrosters_roster_archidfk;


ALTER TABLE enrollmentsrosters ADD  FOREIGN KEY(roster_archid) REFERENCES roster(archid);


ALTER TABLE studentstests ALTER COLUMN contentarea_name DROP NOT NULL;