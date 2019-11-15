--

CREATE TABLE specialcircumstance (
    id bigserial NOT NULL,
    specialcircumstancetype character varying(100),
    cedscode bigint,
    ksdecode character varying(100),
    CONSTRAINT specialcircumstance_id_fk PRIMARY KEY (id)
);


INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Administration or system failure', 13835, 'None');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Catastrophic illness or accident', 13814, 'SC-08');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Cheating', 13821, 'Call KSDE');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Chronic absences', 13813, 'SC-07');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Cross-enrolled', 13833, 'SC-42');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Earlier truancy', 13812, 'SC-06');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Fire alarm', 13837, 'None');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Foreign exchange student', 13825, 'SC-32');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Home schooled for assessed subjects', 13815, 'SC-16');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Homebound', 13824, 'SC-31');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Incarcerated at adult facility', 13817, 'SC-24');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Left testing', 13832, 'SC-41');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Long-term suspension - non-special education', 13807, 'SC-01');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Non-special education student used calculator on non-calculator items', 13828, 'SC-36');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Only for writing', 13834, 'None');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Other', 9999, 'SC-39');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Other reason for ineligibility', 13830, 'SC-39');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Other reason for nonparticipation', 13831, 'SC-39');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Parent refusal', 13820, 'Call KSDE');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Psychological factors of emotional trauma', 13822, 'Call KSDE');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Reading passage read to student (IEP)', 13827, 'SC-34');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Short-term suspension - non-special education', 13808, 'SC-012');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Special detention center', 13819, 'SC-26');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Special treatment center', 13818, 'SC-25');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Student not showing adequate effort', 13823, 'Call KSDE');

 INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Student refusal', 13826, 'SC-33');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Student took this grade level assessment last year', 13816, 'SC-20');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Student used math journal (non-IEP)', 13829, 'SC-37');

 INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Suspension - special education', 13809, 'SC-03');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Teacher cheating or mis-admin', 13836, 'N/A');

INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Truancy - no paperwork filed', 13811, 'SC-05');

 INSERT INTO specialcircumstance (specialcircumstancetype, cedscode, ksdecode)
 VALUES ('Truancy - paperwork filed', 13810, 'SC-04');


CREATE TABLE studentspecialcircumstance (
    studenttestid bigint NOT NULL,
    specialcircumstanceid bigint NOT NULL
);

ALTER TABLE ONLY studentspecialcircumstance
    ADD CONSTRAINT fk_studentspecialcircumstance_studenttestid FOREIGN KEY (studenttestid) REFERENCES studentstests(id);

ALTER TABLE ONLY studentspecialcircumstance
    ADD CONSTRAINT fk_studentspecialcircumstance_specialcircumstanceid FOREIGN KEY (specialcircumstanceid) REFERENCES specialcircumstance(id);