
-- moved previous contents to ddl\123.sql

--here are the contnts from ddl/126.sql, ddl/141.sql, ddl/147.sql, ddl/156.sql that went to prod for 12/20 patch


--US13095 and US13428 - Math Complexity Band
ALTER TABLE student ADD COLUMN mathbandid bigint;
ALTER TABLE student ADD CONSTRAINT mathband_student_fk FOREIGN KEY (mathbandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE student ADD COLUMN finalmathbandid bigint;
ALTER TABLE student ADD CONSTRAINT finalmathband_student_fk FOREIGN KEY (finalmathbandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
     
      
--US13916 Complexity Bands Auto Enrollment
alter table test add column avglinkagelevel real;


--US14261
create table essentialelementlinkagetranslationvalues(
 id bigserial not null,
 categoryid bigint not null,
 translationvalue real not null,
 constraint eeltv_pk primary key (id),
 constraint eeltv_category_fk foreign key (categoryid)
	references category (id) match simple
	on update no action on delete no action,
 constraint category_translationvalue_uk unique (categoryid, translationvalue)
 );
 
 CREATE OR REPLACE FUNCTION publish_republishing_test(publishedtestid bigint)
  RETURNS character varying AS
$BODY$
DECLARE test_row record;
	studentstests_row record;
	testsection_row record;
	publishedtest_testsection_id bigint[] = array(SELECT id FROM testsection WHERE testid = publishedtestid ORDER BY id);
	studentstestsections_id bigint[];
	id_val bigint;
	counter int;
	returnmessage character varying(20) = 'Unsuccessful';
	pub_testsection_row record;
	unpublishedtest_testsection_id bigint[];
	publishedtestinfo_row record;
	unpublishedtestinfo_row record;
	found character varying(20) = 'failure';	
	studentstestsections_testsectionid bigint[];
	modifiedtestsections_row  record;
	updatestudenttestmappings boolean = TRUE;
	publishedtestsectionid bigint;
	unpubtestsectionid bigint;
	unpublishedtest_taskvariant_id bigint[];
	unpublishedtest_foil_id bigint[];
	foilid_val bigint;
	pubid_val bigint;
	pubfoilid_val bigint;
	section_id bigint;
	linkage_value integer;
	total real = 0;
	num_of_tasks real;
	avg_linkage real = -1;
	assessment_program text;
	sections_avg real array;
	section_counter integer = 0;
	section_avg real;
	BEGIN

		
		FOR test_row IN SELECT unpublished.* FROM test published JOIN test unpublished ON published.externalid = unpublished.externalid 
 			WHERE published.id = publishedtestid AND unpublished.id != publishedtestid
		LOOP
 			returnmessage = 'Successful';
 			
 			--Statement to Update testsession table testid column with newly published test id 
 			RAISE INFO 'UPDATE testsession testid - %, id - %',  publishedtestid , test_row.id;
 			UPDATE testsession SET testid = publishedtestid WHERE testid = test_row.id;

 			RAISE INFO '############################################################################# UPDATE UNUSED STUDENT TESTS #############################################################################';
 			-- Logic implementation for - Test taker has registered for the test but has not yet started it. We would like the test taker to get the new, updated form with all items corrected.
 			FOR studentstests_row IN SELECT st.* FROM studentstests st JOIN category c ON st.status = c.id 
 				WHERE testid = test_row.id AND c.categorycode = 'unused' AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')
 			LOOP
 
 				--Statement to Update studentstests table testid column with newly published test id 
 				RAISE INFO 'UPDATE studentstests testid - %, id - %',  publishedtestid , studentstests_row.id;
 				UPDATE studentstests SET testid = publishedtestid WHERE id = studentstests_row.id;
 				
 				studentstestsections_id = array(select id from studentstestsections where studentstestid = studentstests_row.id ORDER BY id);
 
 				counter = 0;
 				--Logic to Update/Insert/Delete studentstestsections table based on the test section of the newly published test 
 				IF (select array_length(studentstestsections_id,1)) <= (select array_length(publishedtest_testsection_id,1)) THEN
 					FOREACH id_val IN ARRAY publishedtest_testsection_id
 					LOOP
 						counter = counter + 1;
 						IF counter <= (select array_length(publishedtest_testsection_id,1)) THEN 
 							RAISE INFO '%. In IF UPDATE publishedtest_testsection_id - %, studentstestsections_id[counter] - %', counter, id_val , studentstestsections_id[counter];
 							UPDATE studentstestsections SET testsectionid = id_val WHERE id = studentstestsections_id[counter];
 						ELSE
 							RAISE INFO '%. In IF INSERT publishedtest_testsection_id - %, studentstests_row.id - %', counter, id_val , studentstests_row.id;
 							INSERT INTO studentstestsections(studentstestid, testsectionid,statusId, createddate, createduser, activeflag, modifieduser, modifieddate) 
 								VALUES (studentstests_row.id, id_val, (SELECT id FROM category WHERE categorycode = 'unused' AND categorytypeid = 
 									(SELECT id FROM categorytype WHERE typecode = 'STUDENT_TESTSECTION_STATUS')), now(), 
 									(SELECT id FROM aartuser WHERE username='cetesysadmin'),true, (SELECT id FROM aartuser WHERE username='cetesysadmin'), now());
 						END IF;
 						--RAISE INFO '    Outside IF Loop %', (select array_length(studentstestsections_id,1));
 					END LOOP;
 				ELSIF (select array_length(studentstestsections_id,1)) > (select array_length(publishedtest_testsection_id,1)) THEN
 					FOREACH id_val IN ARRAY studentstestsections_id
 					LOOP
 						counter = counter + 1;
 						IF counter <= (select array_length(studentstestsections_id,1)) THEN
 							RAISE INFO '%. In ELSE UPDATE studentstestsections_id - %, publishedtest_testsection_id[counter] - %', counter, id_val , publishedtest_testsection_id[counter];
 							UPDATE studentstestsections SET testsectionid = publishedtest_testsection_id[counter] WHERE id = id_val;
 						ELSE
 							RAISE INFO '%. In ELSE DELETE studentstestsections_id - %', counter, id_val;
 							DELETE FROM studentstestsections WHERE id = id_val;
 						END IF;
 						--RAISE INFO '    Outside ELSE Loop %', (select array_length(studentstestsections_id,1));
 					END LOOP;
 				ELSE
 					
 				END IF;
 			END LOOP;


			RAISE INFO '############################################################################# FIND MODIFIED TEST SECTIONS #############################################################################';
			/* Start - Finding the modified test sections logic */
			
			unpublishedtest_testsection_id = array(SELECT id FROM testsection WHERE testid = test_row.id ORDER BY id);
			RAISE INFO 'Unpublished testid - %, unpublishedtest_testsection_id - %', test_row.id, unpublishedtest_testsection_id;

			-- Create a temp table with all the testsection, taskvariant,foils data for published test.
			CREATE TEMP TABLE publishedtestinfo_table AS 
				SELECT ts.testid as testid, ts.id as testsectionid, ts.externalid as testsectionexternalid, ts.testsectionname as testsectionname ,
					tv.id as taskvariantid, tv.externalid as taskvariantexternalid, tv.variantname as taskvariantname, tstv.taskvariantposition as taskvariantposition,
					tvf.foilid as taskvariantsfoilsid, tvf.externalfoilid taskvariantsfoilsexternalfoilid, tvf.responseorder as taskvariantsfoilsresponseorder						
				FROM testsectionstaskvariants tstv 
					JOIN testsection ts ON tstv.testsectionid = ts.id 
					JOIN taskvariant tv ON tstv.taskvariantid = tv.id
					JOIN taskvariantsfoils tvf ON tv.id = tvf.taskvariantid
				WHERE ts.testid = publishedtestid ORDER BY ts.testid, ts.testsectionname, tstv.taskvariantposition, tvf.responseorder;

			-- Create a temp table with all the testsection, taskvariant,foils data for unpublished test.
			CREATE TEMP TABLE unpublishedtestinfo_table AS 
				SELECT ts.testid as testid, ts.id as testsectionid, ts.externalid as testsectionexternalid, ts.testsectionname as testsectionname ,
					tv.id as taskvariantid, tv.externalid as taskvariantexternalid, tv.variantname as taskvariantname, tstv.taskvariantposition as taskvariantposition,
					tvf.foilid as taskvariantsfoilsid, tvf.externalfoilid taskvariantsfoilsexternalfoilid, tvf.responseorder as taskvariantsfoilsresponseorder						
				FROM testsectionstaskvariants tstv 
					JOIN testsection ts ON tstv.testsectionid = ts.id 
					JOIN taskvariant tv ON tstv.taskvariantid = tv.id
					JOIN taskvariantsfoils tvf ON tv.id = tvf.taskvariantid
				WHERE ts.testid = test_row.id ORDER BY ts.testid, ts.testsectionname, tstv.taskvariantposition, tvf.responseorder;

			-- Create a temp table with modified testsectionid and name.
			CREATE TEMP TABLE modifiedtestsections (testsectionid bigint, testsectionname text);

			FOR unpublishedtestinfo_row IN SELECT * FROM unpublishedtestinfo_table
			LOOP				
				FOR publishedtestinfo_row IN SELECT * FROM publishedtestinfo_table
				LOOP
					IF ( (SELECT COUNT(distinct testsectionexternalid) FROM unpublishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) =
						(SELECT COUNT(distinct testsectionexternalid) FROM publishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) AND
						publishedtestinfo_row.testsectionname = unpublishedtestinfo_row.testsectionname AND
						publishedtestinfo_row.testsectionexternalid = unpublishedtestinfo_row.testsectionexternalid AND
						(SELECT COUNT(distinct taskvariantexternalid) FROM unpublishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) =
							(SELECT COUNT(distinct taskvariantexternalid) FROM publishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) AND	
						publishedtestinfo_row.taskvariantposition = unpublishedtestinfo_row.taskvariantposition AND
						publishedtestinfo_row.taskvariantexternalid = unpublishedtestinfo_row.taskvariantexternalid AND
						(SELECT COUNT(distinct taskvariantsfoilsexternalfoilid) FROM unpublishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) =
							(SELECT COUNT(distinct taskvariantsfoilsexternalfoilid) FROM publishedtestinfo_table where testsectionname = unpublishedtestinfo_row.testsectionname) AND
						publishedtestinfo_row.taskvariantsfoilsresponseorder = unpublishedtestinfo_row.taskvariantsfoilsresponseorder AND
						publishedtestinfo_row.taskvariantsfoilsexternalfoilid = unpublishedtestinfo_row.taskvariantsfoilsexternalfoilid) THEN

						found = 'success';

					END IF;									
				END LOOP;
				
				IF(found ='failure') THEN
					IF((SELECT COUNT(*) FROM modifiedtestsections WHERE testsectionid = unpublishedtestinfo_row.testsectionid) <= 0) THEN
						INSERT INTO modifiedtestsections VALUES(unpublishedtestinfo_row.testsectionid, unpublishedtestinfo_row.testsectionname);
						RAISE INFO 'unpublishedtestinfo_row - %', unpublishedtestinfo_row;
					END IF;					
				END IF;
				found = 'failure';
				
			END LOOP;
			RAISE INFO ' modifiedtestsections count - %',  (SELECT COUNT(*) FROM modifiedtestsections);
			
			/* End - Finding the modified test sections logic */


			RAISE INFO '############################################################################# UPDATE INPROGRESS STUDENT TESTS #############################################################################';
			
			IF ((SELECT COUNT(*) FROM modifiedtestsections) > 0) THEN 
				FOR studentstests_row IN SELECT st.* FROM studentstests st JOIN category c ON st.status = c.id 
					WHERE testid = test_row.id AND c.categorycode = 'inprogress' AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')
				LOOP	 					

					RAISE INFO '   *************************** UPDATE UNUSED STUDENT TEST SECTIONS *******************************   ';
					--Below logic is to check if all the modified sections have been unused by the student.
					studentstestsections_testsectionid = array(select sts.testsectionid from studentstestsections sts JOIN category c ON sts.statusid = c.id 
						where sts.studentstestid = studentstests_row.id AND c.categorycode = 'unused' 
							AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS') ORDER BY sts.testsectionid);
					RAISE INFO 'studentstests_row.id value : %  -  studentstestsections_testsectionid : %', studentstests_row.id, studentstestsections_testsectionid;
					FOR modifiedtestsections_row IN SELECT * FROM modifiedtestsections
					LOOP
						--updatestudenttestmappings = (SELECT studentstestsections_testsectionid = ALL(modifiedtestsections_row.testsectionid));						
						updatestudenttestmappings = (SELECT studentstestsections_testsectionid @> ARRAY[modifiedtestsections_row.testsectionid]);
						EXIT WHEN updatestudenttestmappings = FALSE;
					END LOOP;					

					-- If the below condition is true then we need to change the old testsectionid's to newly published testsectionid's as student haven't started all of those test sections
					RAISE INFO '   updatestudenttestmappings value - %',  updatestudenttestmappings;
					IF (updatestudenttestmappings = TRUE) THEN

						--Get all the unused studentstestsectionsid's from studentstestsections table
						studentstestsections_id = array(select sts.id from studentstestsections sts JOIN category c ON sts.statusid = c.id 
							where sts.studentstestid = studentstests_row.id AND c.categorycode = 'unused'
								AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS') ORDER BY sts.id);
							
						--Statement to Update studentstests table testid column with newly published test id 
						RAISE INFO 'UPDATE studentstests SET testid - % WHERE id - %',  publishedtestid , studentstests_row.id;
						UPDATE studentstests SET testid = publishedtestid WHERE id = studentstests_row.id;
					
						counter = 0;
						--Logic to Update studentstestsections table based on the test section of the newly published test 												
						FOREACH id_val IN ARRAY studentstestsections_id
						LOOP
							counter = counter + 1;
							RAISE INFO '     %. id_val - %',  counter, id_val;
							publishedtestsectionid = (SELECT pub.id FROM testsection pub JOIN testsection unpub ON pub.testsectionname = unpub.testsectionname
											JOIN studentstestsections sts ON unpub.id = sts.testsectionid
										WHERE sts.id = id_val AND pub.externalid = unpub.externalid and pub.id != unpub.id ORDER BY 1 desc LIMIT 1)																;

							IF(publishedtestsectionid >  0) THEN
								RAISE INFO '       UPDATE studentstestsections SET testsectionid : % WHERE  id : %', publishedtestsectionid, id_val;
								UPDATE studentstestsections SET testsectionid = publishedtestsectionid WHERE id = id_val;
							ELSE 
								RAISE INFO '       DELETE studentstestsections WHERE id : %', id_val;
								DELETE FROM studentstestsections WHERE id = id_val;
							END IF;
						END LOOP;
					END IF; -- end of updatestudenttestmappings check

					RAISE INFO '   **************************** UPDATE COMPLETED STUDENT TEST SECTIONS *******************************   ';
					--Get all the completed studentstestsectionsid's from studentstestsections table
					studentstestsections_id = array(select sts.id from studentstestsections sts JOIN category c ON sts.statusid = c.id 
						where sts.studentstestid = studentstests_row.id AND c.categorycode = 'complete'
							AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS') ORDER BY sts.id);
					RAISE INFO '     COMPLETE STATUS studentstestsections_id : %', studentstestsections_id;	
					
				
					counter = 0;
					--Logic to Update studentstestsections table based on the test section of the newly published test 												
					FOREACH id_val IN ARRAY studentstestsections_id
					LOOP
						counter = counter + 1;
						RAISE INFO '     %. studentstestsectionsid_val - %',  counter, id_val;
						publishedtestsectionid = (SELECT pub.id FROM testsection pub JOIN testsection unpub ON pub.testsectionname = unpub.testsectionname
										JOIN studentstestsections sts ON unpub.id = sts.testsectionid
									WHERE sts.id = id_val AND pub.externalid = unpub.externalid and pub.id != unpub.id ORDER BY 1 desc LIMIT 1)																;

						IF(publishedtestsectionid >  0) THEN
							RAISE INFO '       UPDATE studentstestsections SET testsectionid : % WHERE  id : %', publishedtestsectionid, id_val;
							UPDATE studentstestsections SET testsectionid = publishedtestsectionid WHERE id = id_val;
						ELSE 
							RAISE INFO '       DELETE studentstestsections WHERE id : %', id_val;
							DELETE FROM studentstestsections WHERE id = id_val;
						END IF;

						unpubtestsectionid = (SELECT DISTINCT unpub.testsectionid FROM unpublishedtestinfo_table unpub JOIN publishedtestinfo_table pub ON
									pub.testsectionexternalid = unpub.testsectionexternalid 
									WHERE pub.testsectionid = publishedtestsectionid ORDER BY 1 desc LIMIT 1);

						RAISE INFO 'UNPUB testsectionid - %, PUB testsectionid - %',  unpubtestsectionid, publishedtestsectionid;
						unpublishedtest_taskvariant_id = array(SELECT DISTINCT taskvariantid FROM unpublishedtestinfo_table
									WHERE testsectionid = unpubtestsectionid);
						--RAISE INFO 'UNPUB taskvariant - %, PUB  taskvariant - %',  unpublishedtest_taskvariant_id, unpublishedtest_taskvariant_id;
						FOREACH id_val IN ARRAY unpublishedtest_taskvariant_id
						LOOP
							pubid_val = (SELECT pub.id FROM taskvariant pub JOIN taskvariant unpub ON pub.externalid = unpub.externalid 
								WHERE pub.externalid = unpub.externalid and pub.id != unpub.id and unpub.id = id_val ORDER BY 1 desc LIMIT 1);
								
							RAISE INFO 'UPDATE studentsresponses SET taskvariantid = %, testid = % WHERE taskvariantid = % AND studentstestsid = %', pubid_val, publishedtestid, id_val, studentstests_row.id;
							UPDATE studentsresponses SET taskvariantid = pubid_val, testid = publishedtestid WHERE taskvariantid = id_val AND studentstestsid = studentstests_row.id;
									
							unpublishedtest_foil_id = array(SELECT DISTINCT unpub.taskvariantsfoilsid FROM unpublishedtestinfo_table unpub JOIN publishedtestinfo_table pub ON
									pub.taskvariantsfoilsexternalfoilid = unpub.taskvariantsfoilsexternalfoilid 
									WHERE pub.testsectionid = publishedtestsectionid);

							FOREACH foilid_val IN ARRAY unpublishedtest_foil_id
							LOOP
								pubfoilid_val = (SELECT pub.id FROM foil pub JOIN foil unpub ON pub.externalid = unpub.externalid 
									WHERE pub.externalid = unpub.externalid and pub.id != unpub.id and unpub.id = foilid_val ORDER BY 1 desc LIMIT 1);
								RAISE INFO 'UPDATE studentsresponses SET foilid = %, testid = % WHERE foilid = % AND studentstestsid = % AND taskvariantid = %', pubfoilid_val , publishedtestid, foilid_val, studentstests_row.id, pubid_val;
								UPDATE studentsresponses SET foilid = pubfoilid_val, testid = publishedtestid WHERE foilid = foilid_val AND studentstestsid = studentstests_row.id AND taskvariantid = pubid_val;
							END LOOP;

						END LOOP;
					END LOOP;
					
				END LOOP; -- end of for loop to values into studentstests_row
			ELSE
				RAISE INFO 'In modifiedtestsections count ELSE - %', (SELECT COUNT(*) FROM modifiedtestsections);
			END IF;


			RAISE INFO '############################################################################# COPY COMPLETED STUDENT TEST ANSWERS #############################################################################';
			FOR studentstests_row IN SELECT st.* FROM studentstests st JOIN category c ON st.status = c.id 
					WHERE testid = test_row.id AND c.categorycode = 'complete' AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')
			LOOP

				--Statement to Update studentstests table testid column with newly published test id 
				RAISE INFO 'UPDATE studentstests SET testid - % WHERE id - %',  publishedtestid , studentstests_row.id;
				UPDATE studentstests SET testid = publishedtestid WHERE id = studentstests_row.id;
						
				--Get all the completed studentstestsectionsid's from studentstestsections table
				studentstestsections_id = array(select sts.id from studentstestsections sts JOIN category c ON sts.statusid = c.id 
					where sts.studentstestid = studentstests_row.id AND c.categorycode = 'complete'
						AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS') ORDER BY sts.id);
				RAISE INFO '     COMPLETE STATUS studentstestsections_id : %', studentstestsections_id;	
				
			
				counter = 0;
				--Logic to Update studentstestsections table based on the test section of the newly published test 												
				FOREACH id_val IN ARRAY studentstestsections_id
				LOOP
					counter = counter + 1;
					RAISE INFO '     %. studentstestsectionsid_val - %',  counter, id_val;
					publishedtestsectionid = (SELECT pub.id FROM testsection pub JOIN testsection unpub ON pub.testsectionname = unpub.testsectionname
									JOIN studentstestsections sts ON unpub.id = sts.testsectionid
								WHERE sts.id = id_val AND pub.externalid = unpub.externalid and pub.id != unpub.id ORDER BY 1 desc LIMIT 1)																;

					IF(publishedtestsectionid >  0) THEN
						RAISE INFO '       UPDATE studentstestsections SET testsectionid : % WHERE  id : %', publishedtestsectionid, id_val;
						UPDATE studentstestsections SET testsectionid = publishedtestsectionid WHERE id = id_val;
					ELSE 
						RAISE INFO '       DELETE studentstestsections WHERE id : %', id_val;
						DELETE FROM studentstestsections WHERE id = id_val;
					END IF;

					unpubtestsectionid = (SELECT DISTINCT unpub.testsectionid FROM unpublishedtestinfo_table unpub JOIN publishedtestinfo_table pub ON
								pub.testsectionexternalid = unpub.testsectionexternalid 
								WHERE pub.testsectionid = publishedtestsectionid ORDER BY 1 desc LIMIT 1);

					RAISE INFO 'UNPUB testsectionid - %, PUB testsectionid - %',  unpubtestsectionid, publishedtestsectionid;
					unpublishedtest_taskvariant_id = array(SELECT DISTINCT taskvariantid FROM unpublishedtestinfo_table
								WHERE testsectionid = unpubtestsectionid);
					--RAISE INFO 'UNPUB taskvariant - %, PUB  taskvariant - %',  unpublishedtest_taskvariant_id, unpublishedtest_taskvariant_id;
					FOREACH id_val IN ARRAY unpublishedtest_taskvariant_id
					LOOP
						pubid_val = (SELECT pub.id FROM taskvariant pub JOIN taskvariant unpub ON pub.externalid = unpub.externalid 
							WHERE pub.externalid = unpub.externalid and pub.id != unpub.id and unpub.id = id_val ORDER BY 1 desc LIMIT 1);
							
						RAISE INFO 'UPDATE studentsresponses SET taskvariantid = %, testid = % WHERE taskvariantid = % AND studentstestsid = %', pubid_val, publishedtestid, id_val, studentstests_row.id;
						UPDATE studentsresponses SET taskvariantid = pubid_val, testid = publishedtestid WHERE taskvariantid = id_val AND studentstestsid = studentstests_row.id;
								
						unpublishedtest_foil_id = array(SELECT DISTINCT unpub.taskvariantsfoilsid FROM unpublishedtestinfo_table unpub JOIN publishedtestinfo_table pub ON
								pub.taskvariantsfoilsexternalfoilid = unpub.taskvariantsfoilsexternalfoilid 
								WHERE pub.testsectionid = publishedtestsectionid);

						FOREACH foilid_val IN ARRAY unpublishedtest_foil_id
						LOOP
							pubfoilid_val = (SELECT pub.id FROM foil pub JOIN foil unpub ON pub.externalid = unpub.externalid 
								WHERE pub.externalid = unpub.externalid and pub.id != unpub.id and unpub.id = foilid_val ORDER BY 1 desc LIMIT 1);
							RAISE INFO 'UPDATE studentsresponses SET foilid = %, testid = % WHERE foilid = % AND studentstestsid = % AND taskvariantid = %', pubfoilid_val , publishedtestid, foilid_val, studentstests_row.id, pubid_val;
							UPDATE studentsresponses SET foilid = pubfoilid_val, testid = publishedtestid WHERE foilid = foilid_val AND studentstestsid = studentstests_row.id AND taskvariantid = pubid_val;
						END LOOP;

					END LOOP;
				
				
				END LOOP;
			END LOOP;

			DROP table publishedtestinfo_table;
			DROP table unpublishedtestinfo_table;
			DROP table modifiedtestsections;
			
		END LOOP; --end of test_row loop

		RAISE INFO 'BEGIN CALCULATE AVERAGE LINKAGE LEVEL';
		assessment_program = (select programname from assessmentprogram where id in (select assessmentprogramid from testingprogram where id in (
					select testingprogramid from assessment where id in (
					select assessmentid from assessmentstestcollections where testcollectionid in (select testcollectionid from testcollectionstests where testid=publishedtestid)))));
		RAISE INFO 'CHECK ASSESSMENT PROGRAM NAME: %, TEST ID: %',  assessment_program, publishedtestid;
		if ('Dynamic Learning Maps' = assessment_program) then
			
			FOR section_id in select id from testsection where testid = publishedtestid

			LOOP

				num_of_tasks = (select count(id) from essentialelementlinkagetranslationvalues where categoryid in (
						select essentialelementlinkageid from taskvariant where id in (
						select taskvariantid from testsectionstaskvariants where testsectionid = section_id)));
				RAISE INFO 'NUMBER OF TASKS: %, TEST SECTION ID: %',   num_of_tasks, section_id;
				CONTINUE WHEN num_of_tasks = 0;
				
				FOR linkage_value IN select translationvalue from essentialelementlinkagetranslationvalues where categoryid in (
						select essentialelementlinkageid from taskvariant where id in (
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
			
			FOREACH section_avg in array sections_avg
			LOOP
				total = total + section_avg;
			END LOOP;
			RAISE INFO 'TOTAL LINKAGE ALL SECTIONS: %, NUMBER OF SECTIONS: %', total, section_counter;
			
			avg_linkage = total/section_counter;
			
			RAISE INFO 'AVG LINKAGE LEVEL: %, TEST ID: %',   avg_linkage, publishedtestid;
			update test set avglinkagelevel=avg_linkage where id=publishedtestid;
		end if;
		
		RAISE INFO 'END CALCULATE AVERAGE LINKAGE LEVEL';
		
		RETURN returnmessage;
	END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION publish_republishing_test(bigint)
  OWNER TO aart;
GRANT EXECUTE ON FUNCTION publish_republishing_test(bigint) TO aart;
GRANT EXECUTE ON FUNCTION publish_republishing_test(bigint) TO public;
						
ALTER TABLE aartuser ADD COLUMN displayname character varying(80);