
--R9
--US13203: Rubric - Scoring items with extended response task type.

CREATE TABLE rubricscore
(
id bigserial NOT NULL,
studenttestid bigint NOT NULL,
taskvariantid bigint NOT NULL,
rubricscore Decimal(10,2),
date timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
CONSTRAINT rubricscore_pkey PRIMARY KEY (id),  
      CONSTRAINT rubriccategory_taskvariantid_fk FOREIGN KEY (taskvariantid)
       REFERENCES taskvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--table change for CB
ALTER TABLE taskvariantsfoils ADD COLUMN maxfieldsize smallint;



-- US13144 Name: Technical: Performance Improvements of SQL queries 
 
create index idx_student_legalmiddlename on student(legalmiddlename);

create index idx_student_generationcode on student(generationcode);

