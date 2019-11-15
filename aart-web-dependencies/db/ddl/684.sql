-- F546 Scoring ddl
ALTER TABLE scoringtestmetadata ADD COlUMN modifieduser bigint;

ALTER TABLE scoringtestmetadata ADD COlUMN modifieddate  
timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;

ALTER TABLE scoringassignmentstudent ADD column modifieduser bigint;

ALTER TABLE scoringassignmentstudent ADD column modifieddate 
timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;

ALTER TABLE scoringassignmentscorer ADD column modifieduser bigint;

ALTER TABLE scoringassignmentscorer ADD column modifieddate 
timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;

ALTER TABLE scoringassignment ADD column modifieduser bigint;

ALTER TABLE scoringassignment ADD column modifieddate 
timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;

 ALTER TABLE scoringtestmetadata 
   ADD CONSTRAINT fk_scoringtestmetadata_testid
   FOREIGN KEY (testid) 
   REFERENCES test(id);

 ALTER TABLE scoringtestmetadata 
   ADD CONSTRAINT fk_scoringtestmetadata_taskvariantid
   FOREIGN KEY (taskvariantid) 
   REFERENCES taskvariant(id);
 
ALTER TABLE studentstestscorehistory ADD  column modifieduser bigint;
ALTER TABLE studentstestscorehistory ADD  column modifieddate timestamp with time zone;
ALTER TABLE studentstestscorehistory ADD  column createduser bigint;
