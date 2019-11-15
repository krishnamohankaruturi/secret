--ddl/753.sql

CREATE SEQUENCE excludedcontentcodes_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 2147483647
  START 1
  CACHE 1;
  
CREATE TABLE excludedcontentcodes
(
	id bigint NOT NULL DEFAULT nextval('excludedcontentcodes_id_seq'::regclass),
	assessmentprogramcode character varying(75),
	excludedcontentcode character varying(40),
	activeflag boolean DEFAULT true,
	createuserid bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieduserid bigint,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT excludedcontentcodes_pkey PRIMARY KEY (id)
);


-- Function: public.calculate_avg_linkage_level(bigint)

DROP FUNCTION public.calculate_avg_linkage_level(bigint);

CREATE OR REPLACE FUNCTION public.calculate_avg_linkage_level(publishedtestid bigint)
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
	sections_avg real array;
	section_counter integer = 0;
	section_avg real;
	BEGIN
		RAISE INFO 'BEGIN CALCULATE AVERAGE LINKAGE LEVEL';
		assessment_program = array(select abbreviatedname from assessmentprogram where id in (select assessmentprogramid from testingprogram where id in (
							select testingprogramid from assessment where id in (
							select assessmentid from assessmentstestcollections where testcollectionid in (select testcollectionid from testcollectionstests where testid=publishedtestid)))));
		
		RAISE INFO 'CHECK ASSESSMENT PROGRAM NAME: %, TEST ID: %',  assessment_program, publishedtestid;
		
		FOREACH assessment_program_name IN ARRAY assessment_program
				LOOP
					IF ('DLM' = assessment_program_name or 'I-SMART' = assessment_program_name) then 
						
						FOR section_id in select id from testsection where testid = publishedtestid
		
						LOOP							
									num_of_tasks = (select count(id) from essentialelementlinkagetranslationvalues where categoryid in (
											select essentialelementlinkageid from taskvariantessentialelementlinkage where taskvariantid in (
											select tstv.taskvariantid from testsectionstaskvariants tstv 
											inner join taskvariantcontentframeworkdetail tcf on tcf.taskvariantid = tstv.taskvariantid
											inner join contentframeworkdetail cf on tcf.contentframeworkdetailid = cf.id
											where cf.contentcode NOT in (
											select excludedcontentcode from excludedcontentcodes where assessmentprogramcode = assessment_program_name and activeflag = true) 
											and tcf.taskvariantid in (select taskvariantid from testsectionstaskvariants where testsectionid = section_id)
											)));
		
							RAISE INFO 'NUMBER OF TASKS: %, TEST SECTION ID: %',   num_of_tasks, section_id;
		
							CONTINUE WHEN num_of_tasks = 0;
									FOR linkage_value IN select translationvalue from essentialelementlinkagetranslationvalues where categoryid in (
											select essentialelementlinkageid from taskvariantessentialelementlinkage where taskvariantid in (select tstv.taskvariantid from testsectionstaskvariants tstv 
											inner join taskvariantcontentframeworkdetail tcf on tcf.taskvariantid = tstv.taskvariantid
											inner join contentframeworkdetail cf on tcf.contentframeworkdetailid = cf.id
											where cf.contentcode NOT in (
											select excludedcontentcode from excludedcontentcodes where assessmentprogramcode = assessment_program_name and activeflag = true) 
											and tcf.taskvariantid in (select taskvariantid from testsectionstaskvariants where testsectionid = section_id)))
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
