-- 463.sql ddl

create table scoringassignment
(
		id  		bigserial NOT NULL,
		testsessionid   bigint not null,
		createddate     timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
		createduser     integer not null,
		active          boolean DEFAULT true,
		constraint pk_scoringassignment primary key(id),
		CONSTRAINT testsessionid_fk FOREIGN KEY (testsessionid) REFERENCES testsession(id)
);
create table scoringassignmentstudent(
        id			bigserial NOT NULL,
        studentid		bigint not null,
        scoringassignmentid	bigint not null,
        createddate             timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
        createduser             integer not null,
        active                  boolean DEFAULT true,
        constraint pk_scoringassignmentstudent  primary key(id),
        CONSTRAINT studentid_fk FOREIGN KEY (studentid) REFERENCES student (id),
		CONSTRAINT scoringassignmentid_fk FOREIGN KEY (scoringassignmentid) REFERENCES scoringassignment(id)
);

create table scoringassignmentsscorer
(              
        id			bigserial NOT NULL,
        scoringassignmentid	bigint not null,	
        scorerid                integer not null,
        createddate             timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
        createduser             integer not null,
        active			boolean DEFAULT true,
        constraint pk_scoringassignmentsscorer     primary key(id),
        CONSTRAINT scorerid_fk FOREIGN KEY (scorerid) REFERENCES aartuser (id),
		CONSTRAINT scoringassignmentid_fk FOREIGN KEY (scoringassignmentid) REFERENCES scoringassignment(id)
);

create table exitwithoutsavetest (
studenttestsectionid bigint,
createddate timestamp without time zone DEFAULT now(),
CONSTRAINT fk_studentstestsectionid_testsectioid FOREIGN KEY (studenttestsectionid)
     REFERENCES studentstestsections (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
