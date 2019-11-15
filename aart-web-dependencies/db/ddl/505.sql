--ddl/505.sql

 --Script from changepond
CREATE TABLE taskvariantessentialelementlinkage
(
  taskvariantid bigint,
  essentialelementlinkageid bigint,
  CONSTRAINT taskvariantessentialelementlinkage_pkey PRIMARY KEY (taskvariantid,essentialelementlinkageid),
  CONSTRAINT taskvariantessentialelementlinkage_fk1 FOREIGN KEY (essentialelementlinkageid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantessentialelementlinkage_fk2 FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Function: calculate_avg_linkage_level(bigint)
DROP FUNCTION calculate_avg_linkage_level(bigint);

CREATE OR REPLACE FUNCTION calculate_avg_linkage_level(publishedtestid bigint)
  RETURNS numeric AS
$BODY$ 

DECLARE 

	section_id bigint;
	linkage_value real;
	total real = 0;
	num_of_tasks real;
	avg_linkage real = -1;
	assessment_program text[];
	assessment_program_name text;
	content_area text;
	sections_avg real array;
	section_counter integer = 0;
	section_avg real;
	BEGIN
		RAISE INFO 'BEGIN CALCULATE AVERAGE LINKAGE LEVEL';
		assessment_program = array(select abbreviatedname from assessmentprogram where id in (select assessmentprogramid from testingprogram where id in (
							select testingprogramid from assessment where id in (
							select assessmentid from assessmentstestcollections where testcollectionid in (select testcollectionid from testcollectionstests where testid=publishedtestid)))));
		
		content_area = (select abbreviatedname from contentarea where id = (select contentareaid from test where id = publishedtestid));
							
		RAISE INFO 'CHECK ASSESSMENT PROGRAM NAME: %, TEST ID: %',  assessment_program, publishedtestid;
		
		FOREACH assessment_program_name IN ARRAY assessment_program
				LOOP
					IF ('DLM' = assessment_program_name AND ('M' = content_area OR 'ELA' = content_area OR 'Sci' = content_area)) then
						
						FOR section_id in select id from testsection where testid = publishedtestid
		
						LOOP
		
							num_of_tasks = (select count(id) from essentialelementlinkagetranslationvalues where categoryid in (
									select essentialelementlinkageid from taskvariantessentialelementlinkage where taskvariantid in (
									select taskvariantid from testsectionstaskvariants where testsectionid = section_id)));
		
							RAISE INFO 'NUMBER OF TASKS: %, TEST SECTION ID: %',   num_of_tasks, section_id;
		
							CONTINUE WHEN num_of_tasks = 0;
		
							FOR linkage_value IN select translationvalue from essentialelementlinkagetranslationvalues where categoryid in (
									select essentialelementlinkageid from taskvariantessentialelementlinkage where taskvariantid in (
									select taskvariantid from testsectionstaskvariants where testsectionid = section_id))
							LOOP
									total = total + linkage_value;
		
							END LOOP;
		
							RAISE INFO 'TOTAL LINKAGE: %, TEST SECTION ID: %',   total, section_id;
		
							sections_avg[section_counter] = total/num_of_tasks;
		
							RAISE INFO 'SECTION AVG: %, TEST SECTION ID: %',   sections_avg[section_counter], section_id;
		
							total = 0;
		
							section_counter = section_counter + 1;
		
						END LOOP;
		
						total = 0;
						IF (sections_avg IS NOT NULL) THEN
							FOREACH section_avg in array sections_avg
								LOOP
									total = total + section_avg;
			
								END LOOP;
			
							RAISE INFO 'TOTAL LINKAGE ALL SECTIONS: %, NUMBER OF SECTIONS: %', total, section_counter;
			
							avg_linkage = total/section_counter;
			
							RAISE INFO 'AVG LINKAGE LEVEL: %, TEST ID: %',   avg_linkage, publishedtestid;

							UPDATE test SET avglinkagelevel = avg_linkage
								WHERE id = publishedtestid; 
						END IF;
					END IF;
			END LOOP;
			
			RAISE INFO 'END CALCULATE AVERAGE LINKAGE LEVEL';
		RETURN cast(avg_linkage as numeric);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;