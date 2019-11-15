alter table tasksfoils add column responseorder int,
                       add column correctresponse boolean,
		       add column responsescore int,
		       drop constraint if exists tasksfoils_taskid_fkey;


alter table testsectionstasks drop constraint if exists testsectionstasks_taskid_fkey;

alter table task rename to taskvariant;					  

alter table tasksfoils rename to taskvariantsfoils; 

alter table taskvariantsfoils rename column taskid to taskvariantid;

alter table taskvariantsfoils add constraint taskvariantsfoils_taskvariantid_fkey FOREIGN KEY (taskvariantid)
						REFERENCES taskvariant (id) MATCH SIMPLE
						ON UPDATE NO ACTION ON DELETE NO ACTION;
						
						
alter table testsectionstasks rename to testsectionstaskvariants;

alter table testsectionstaskvariants rename column taskid to taskvariantid;

alter table testsectionstaskvariants add constraint taskfoils_taskvariantid_fkey FOREIGN KEY (taskvariantid)
				REFERENCES taskvariant (id) MATCH SIMPLE
				ON UPDATE NO ACTION ON DELETE NO ACTION;


alter table foil add column rationale text,
                  add column instructionalimplications text,
		  alter column foiltext type text;
				  


alter table taskvariant add column version int,
                 drop column externaltaskvariantrevisionid,
				 add column testlet boolean,
				 add column contextstimulusid bigint,
				 add column numberofresponses int,
				 add column shuffled boolean,
				 add column innovativeitempackagepath character varying(250);
				 

alter table testsection drop column instructions,
                        drop column externaltestid,
			add column begininstructions text,
			add column endinstructions text,
			add constraint testsection_toolsusageid_fkey FOREIGN KEY (toolsusageid)
			  REFERENCES category (id) MATCH SIMPLE
		                   ON UPDATE NO ACTION ON DELETE NO ACTION;
						   

						   
alter table test drop constraint test_subjectid_fkey,
				 drop constraint test_gradeid_fkey,
				 drop constraint test_gradecourseid_fkey,
				 drop constraint test_contentareaid_fkey,
				 drop constraint test_assessmentid_fkey,
				 drop column subjectid,
	             drop column gradeid,
				 drop column gradecourseid,
				 drop column contentareaid,
				 drop column assessmentid,
				 add column begininstructions text,
				 add column endinstructions text,
				 add column status character varying(75);
				
						
				 