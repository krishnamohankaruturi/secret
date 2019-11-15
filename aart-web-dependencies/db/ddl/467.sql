--467.sql
-- scripts from change pond
drop table if exists scoringassignmentsscorer;
drop table if exists scoringassignmentstudent;
drop table if exists scoringassignment CASCADE;

create table scoringassignment
(
	id  					bigserial NOT NULL,
	testsessionid           bigint not null,
	createddate             timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser             integer not null,
	activeflag              boolean DEFAULT true,
	constraint pk_scoringassignment primary key(id),
	CONSTRAINT testsessionid_fk FOREIGN KEY (testsessionid) REFERENCES testsession(id)
);
create table scoringassignmentstudent
(
    id						bigserial NOT NULL,
	studentstestsid			bigint not null,
    studentid				bigint not null,
    scoringassignmentid		bigint not null,
    createddate             timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser             integer not null,
    activeflag              boolean DEFAULT true,
    constraint pk_scoringassignmentstudent  primary key(id),
	CONSTRAINT studentstestsid_fk FOREIGN KEY (studentstestsid) REFERENCES studentstests  (id),
    CONSTRAINT studentid_fk FOREIGN KEY (studentid) REFERENCES student (id),
	CONSTRAINT scoringassignmentid_fk FOREIGN KEY (scoringassignmentid) REFERENCES ScoringAssignment(id)
);
create table scoringassignmentscorer
(              
    id						bigserial NOT NULL,
    scoringassignmentid		bigint not null,	
    scorerid                integer not null,
    createddate             timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser             integer not null,
    activeflag				boolean DEFAULT true,
    constraint pk_scoringassignmentscorer     primary key(id),
    CONSTRAINT scorerid_fk FOREIGN KEY (scorerid) REFERENCES aartuser (id),
	CONSTRAINT scoringassignmentid_fk FOREIGN KEY (scoringassignmentid) REFERENCES ScoringAssignment(id)
);
create table ccqscore
(
	id  				bigserial NOT NULL,
	scoringassignmentid		bigint not null,
	scoringassignmentstudentid	bigint not null,
	scoringassignmentscorerid	bigint not null,
	isscored			boolean ,
	totalscore			numeric,
	createddate                     timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser                     integer not null,
	activeflag                      boolean DEFAULT true,
	constraint pk_ccqscoreteststudent primary key(id),
	CONSTRAINT scoringassignmentid_fk FOREIGN KEY (scoringassignmentid) REFERENCES ScoringAssignment(id),
	CONSTRAINT scoringassignmentstudentid_fk FOREIGN KEY (scoringassignmentstudentid) REFERENCES ScoringAssignmentStudent (id),
	CONSTRAINT scoringassignmentscorerid_fk FOREIGN KEY (scoringassignmentscorerid) REFERENCES ScoringAssignmentScorer (id)
);
create table ccqscoreitem
(
    id					bigserial NOT NULL,
    ccqscoreid			bigint not null,
    rubriccategoryid	bigint not null,
	score				numeric,
    createddate         timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
    createduser         integer not null,
	activeflag          boolean DEFAULT true,
    constraint pk_ccqscoreteststudentrubricscore  primary key(id),
    CONSTRAINT ccqscoreid_fk FOREIGN KEY (ccqscoreid) REFERENCES ccqscore (id),
	CONSTRAINT rubriccategoryid_fk FOREIGN KEY (rubriccategoryid) REFERENCES rubriccategory(id)
);

-- already relesed to production so removing the below scripts