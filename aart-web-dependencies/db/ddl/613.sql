-- ddl/613.sql


--drop table helpcontenttags;
--drop table helptags;
--drop table helpcontentcontext;
--drop table helpcontent;
--drop table helptopic;

-- Help Topic table
CREATE SEQUENCE helptopic_id_seq;

CREATE TABLE helptopic
(
  id bigint NOT NULL DEFAULT nextval('helptopic_id_seq'::regclass) ,
  name character varying(75) NOT NULL,
  description character varying(500) NOT NULL,
  activeflag boolean DEFAULT true,
  createduser bigint,
  modifieduser bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    CONSTRAINT helptopic_pkey PRIMARY KEY (id)
);

-- Help Content table
CREATE SEQUENCE helpcontent_id_seq;

CREATE TABLE helpcontent
(
  id bigint NOT NULL DEFAULT nextval('helpcontent_id_seq'::regclass),
  helptopicid bigint NOT NULL,
  content text NOT NULL,
  expiredate timestamp with time zone,
  helptitle character varying(256) NOT NULL,
  status character varying(10) NOT NULL,
  externalfile boolean DEFAULT false,
  filename character varying(250),
  activeflag boolean DEFAULT true,
  createduser bigint,
  modifieduser bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT helpcotent_pkey PRIMARY KEY (id),
  CONSTRAINT helpcotent_helptopicid_fkey FOREIGN KEY (helptopicid)
      REFERENCES helptopic (id)
  );

-- Help content context table.
CREATE SEQUENCE helpcontentcontext_id_seq;

CREATE TABLE helpcontentcontext
(
 id bigint NOT NULL DEFAULT nextval('helpcontentcontext_id_seq'::regclass),
 helpcontentid bigint NOT NULL,
 assessmentprogramid bigint NOT NULL,
 stateid bigint,
 rolesid bigint,
 activeflag boolean DEFAULT true,
 createduser bigint,
 modifieduser bigint,
 createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 CONSTRAINT helpcontentcontext_pkey PRIMARY KEY (id),
 CONSTRAINT helpcontentcontextid_assessmentprogram_fkey FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id),
 CONSTRAINT helpcontentcontext_helpcontent_fkey FOREIGN KEY (helpcontentid)
      REFERENCES helpcontent (id)
);

-- Help Tags table 
CREATE SEQUENCE helptags_id_seq;

CREATE TABLE helptags(
id bigint NOT NULL DEFAULT nextval('helptags_id_seq'::regclass),
tag character varying(75) unique,
activeflag boolean DEFAULT true,
createduser bigint,
modifieduser bigint,
CONSTRAINT helptags_pkey PRIMARY KEY (id),
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone
);

-- Help Content tags table.
CREATE SEQUENCE helpcontenttags_id_seq;

CREATE TABLE helpcontenttags
(
 id bigint NOT NULL DEFAULT nextval('helpcontenttags_id_seq'::regclass),
 helpcontentid bigint NOT NULL,
 helptagid bigint NOT NULL,
 activeflag boolean DEFAULT true,
 createduser bigint,
 modifieduser bigint,
 createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 CONSTRAINT helpcontenttags_pkey PRIMARY KEY (id),
 CONSTRAINT helpcontenttags_fkey FOREIGN KEY (helpcontentid)
      REFERENCES helpcontent (id),
 CONSTRAINT helptags_helpcontenttags_fkey FOREIGN KEY (helptagid)
      REFERENCES helptags (id)   
);


create sequence helptopicslug_id_seq;

create table helptopicslug
(
	id bigint NOT NULL DEFAULT nextval('helptopicslug_id_seq'::regclass),
 helptopicid bigint NOT NULL,
 url character varying(75) NOT NULL,
 activeflag boolean DEFAULT true,
 createduser bigint,
 modifieduser bigint,
 createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 CONSTRAINT helptopicslug_pkey PRIMARY KEY (id),
 CONSTRAINT helptopicslug_fkey FOREIGN KEY (helptopicid)
      REFERENCES helptopic (id) 
);

create sequence helpcontentslug_id_seq;

create table helpcontentslug
(
	id bigint NOT NULL DEFAULT nextval('helpcontentslug_id_seq'::regclass),
 helpcontentid bigint NOT NULL,
 url character varying(256) NOT NULL,
 activeflag boolean DEFAULT true,
 createduser bigint,
 modifieduser bigint,
 createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
 CONSTRAINT helpcontentslug_pkey PRIMARY KEY (id),
 CONSTRAINT helpcontentslug_fkey FOREIGN KEY (helpcontentid)
      REFERENCES helpcontent (id) 
);
