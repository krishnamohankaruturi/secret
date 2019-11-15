

--US12992 Name: Technical: Add Publishing/Re-publishing scenario's 


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
	BEGIN

		-- Logic implementation for - Test taker has registered for the test but has not yet started it. We would like the test taker to get the new, updated form with all items corrected.
		FOR test_row IN SELECT unpublished.* FROM test published JOIN test unpublished ON published.externalid = unpublished.externalid 
			WHERE published.id = publishedtestid AND unpublished.id != publishedtestid
		LOOP
			returnmessage = 'Successful';
			
			--Statement to Update testsession table testid column with newly published test id 
			--RAISE INFO 'UPDATE testsession testid - %, id - %',  publishedtestid , test_row.id;
			UPDATE testsession SET testid = publishedtestid WHERE testid = test_row.id;
			
			FOR studentstests_row IN SELECT st.* FROM studentstests st JOIN category c ON st.status = c.id 
				WHERE testid = test_row.id AND c.categorycode = 'unused' AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')
			LOOP

				--Statement to Update studentstests table testid column with newly published test id 
				--RAISE INFO 'UPDATE studentstests testid - %, id - %',  publishedtestid , studentstests_row.id;
				UPDATE studentstests SET testid = publishedtestid WHERE id = studentstests_row.id;
				
				studentstestsections_id = array(select id from studentstestsections where studentstestid = studentstests_row.id ORDER BY id);

				counter = 0;
				--Logic to Update/Insert/Delete studentstestsections table based on the test section of the newly published test 
				IF (select array_length(studentstestsections_id,1)) <= (select array_length(publishedtest_testsection_id,1)) THEN
					FOREACH id_val IN ARRAY publishedtest_testsection_id
					LOOP
						counter = counter + 1;
						IF counter <= (select array_length(publishedtest_testsection_id,1)) THEN 
							--RAISE INFO '%. In IF UPDATE publishedtest_testsection_id - %, studentstestsections_id[counter] - %', counter, id_val , studentstestsections_id[counter];
							UPDATE studentstestsections SET testsectionid = id_val WHERE id = studentstestsections_id[counter];
						ELSE
							--RAISE INFO '%. In IF INSERT publishedtest_testsection_id - %, studentstests_row.id - %', counter, id_val , studentstests_row.id;
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
							--RAISE INFO '%. In ELSE UPDATE studentstestsections_id - %, publishedtest_testsection_id[counter] - %', counter, id_val , publishedtest_testsection_id[counter];
							UPDATE studentstestsections SET testsectionid = publishedtest_testsection_id[counter] WHERE id = id_val;
						ELSE
							--RAISE INFO '%. In ELSE DELETE studentstestsections_id - %', counter, id_val;
							DELETE FROM studentstestsections WHERE id = id_val;
						END IF;
						--RAISE INFO '    Outside ELSE Loop %', (select array_length(studentstestsections_id,1));
					END LOOP;
				ELSE
					
				END IF;
			END LOOP;
		END LOOP;
		RETURN returnmessage;
	END;	
$BODY$
LANGUAGE 'plpgsql';



--drop function publish_republishing_test(bigint);


