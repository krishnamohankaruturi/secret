

--US14396 Name:  First Contact: Enhancement Removing Survey Questions


CREATE OR REPLACE FUNCTION FC_Q64_Student_Responses()
RETURNS character varying AS
$BODY$
DECLARE studentsurveyresponse_row record;
	record_counter int;
	returnmessage character varying(20) = 'Unsuccessful';
	BEGIN
		FOR studentsurveyresponse_row IN SELECT * FROM studentsurveyresponse WHERE surveyresponseid in
			(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber ='Q64') AND (responseorder = 3 OR responseorder = 4)) AND activeflag = true
		LOOP
			returnmessage = 'Successful';
			RAISE INFO '%', studentsurveyresponse_row.surveyid;

			SELECT count(*) INTO record_counter FROM studentsurveyresponse WHERE surveyresponseid = 
			(SELECT id FROM surveyresponse WHERE labelid = (SELECT id FROM surveylabel WHERE labelnumber ='Q64') AND responseorder = 2) AND surveyid = studentsurveyresponse_row.surveyid;

			--RAISE INFO '%', record_counter;
			IF (record_counter = 0) THEN
				UPDATE studentsurveyresponse SET surveyresponseid = (SELECT id FROM surveyresponse WHERE labelid = (SELECT id FROM surveylabel WHERE labelnumber ='Q64') AND responseorder = 2)
					WHERE surveyresponseid = studentsurveyresponse_row.surveyresponseid AND surveyid = studentsurveyresponse_row.surveyid;
				RAISE INFO 'In IF -  % % %', (SELECT id FROM surveyresponse WHERE labelid = (SELECT id FROM surveylabel WHERE labelnumber ='Q64') AND responseorder = 2), studentsurveyresponse_row.surveyid, studentsurveyresponse_row.surveyresponseid;
			ELSE
				DELETE FROM studentsurveyresponse WHERE surveyid = studentsurveyresponse_row.surveyid AND surveyresponseid = studentsurveyresponse_row.surveyresponseid;
				UPDATE studentsurveyresponse SET activeflag = TRUE WHERE surveyid = studentsurveyresponse_row.surveyid AND surveyresponseid = (SELECT id FROM surveyresponse WHERE labelid = (SELECT id FROM surveylabel WHERE labelnumber ='Q64') AND responseorder = 2);
				RAISE INFO 'ELSE - ';
			END IF;
		END LOOP;
		
	RETURN returnmessage;
	END;	
$BODY$
LANGUAGE 'plpgsql';



/*NO LOAD BALANCE*/select * from FC_Q64_Student_Responses();



drop function IF EXISTS FC_Q64_Student_Responses();






