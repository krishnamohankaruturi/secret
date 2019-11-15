DO
$BODY$
DECLARE
	student_record RECORD;
	new_username_base TEXT;
	new_username TEXT;
	counter INTEGER;
	
	stateidentifiers TEXT[] := ARRAY['2548944174',
									'5051656536',
									'5243149853',
									'5864257648',
									'1364385317',
									'2096211522',
									'1419449486',
									'4255342156',
									'1429478527',
									'8811574234',
									'8732671245',
									'6465278243',
									'4988195139',
									'2323481916',
									'4865791639',
									'6280710254',
									'3079939441',
									'4338228547',
									'2838850768',
									'7987058017',
									'9781791861',
									'1428004874',
									'5341302389',
									'3767373173',
									'2336042371',
									'4514793973',
									'1337646105',
									'7319212994',
									'4123780288',
									'7568380769',
									'4464043127'];
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