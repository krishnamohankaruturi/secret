DO
$BODY$
DECLARE
	student_record RECORD;
	new_username_base TEXT;
	new_username TEXT;
	counter INTEGER;
	
	stateidentifiers TEXT[] := ARRAY['1019422882'];
BEGIN
	FOR i IN array_lower(stateidentifiers, 1) .. array_upper(stateidentifiers, 1) LOOP
		SELECT id, legalfirstname, legallastname
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS')
		AND statestudentidentifier = stateidentifiers[i]
		INTO student_record;
		
		IF student_record.id IS NOT NULL THEN
			SELECT 1 INTO counter;
			
			SELECT SUBSTRING(LOWER(student_record.legalfirstname) FROM 1 FOR 4) || '.' || SUBSTRING(LOWER(student_record.legallastname) FROM 1 FOR 4)
			INTO new_username_base;
			
			SELECT new_username_base INTO new_username;
			
			WHILE EXISTS (SELECT TRUE FROM student WHERE username = new_username AND id != student_record.id) LOOP
				SELECT new_username_base || '.' || counter INTO new_username;
				SELECT counter + 1 INTO counter;
			END LOOP;
			
			UPDATE student
			SET username = new_username,
			modifieddate = NOW(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
			WHERE id = student_record.id;
			
			RAISE NOTICE 'Updated % username to %', stateidentifiers[i], new_username;
		ELSE
			RAISE NOTICE 'Did not find student %, skipping...', stateidentifiers[i];
		END IF;
	END LOOP;
END;
$BODY$;