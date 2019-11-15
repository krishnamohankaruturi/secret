DO
$BODY$
DECLARE
	currentgradeid BIGINT;
	currentenrollmentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['3482060711'],
		['3157446312'],
		['7489895146'],
		['4517946266'],
		['1550776436'],
		['3429447984'],
		['8193484371'],
		['7756728424'],
		['8488524889'],
		['1413471722'],
		['7913245573'],
		['1649637373'],
		['1293875627'],
		['2763066712'],
		['4814255284'],
		['8811049032'],
		['7579371529'],
		['5681197328'],
		['5128687546'],
		['1033662232'],
		['9119145675'],
		['5021736027'],
		['5601189498'],
		['9892217233'],
		['8579450233'],
		['1601510896'],
		['2884230319'],
		['3414362759'],
		['7485198017'],
		['2522682369'],
		['8537846376'],
		['4465613458'],
		['7372583829']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		RAISE NOTICE '***** Processing student with statestudentidentifier ''%'' *****', data[i][1]; 
	
		SELECT enrollmentid FROM studentreport sr
				JOIN student st ON sr.studentid=st.id AND st.stateid=51 AND sr.schoolyear=2016 
				AND st.statestudentidentifier = data[i][1] LIMIT 1
		INTO currentenrollmentid;

		SELECT id FROM gradecourse WHERE abbreviatedname = (SELECT gc.abbreviatedname FROM gradecourse gc 
			JOIN studentreport sr ON gc.id = sr.gradeid 
			JOIN student st ON sr.studentid=st.id AND st.stateid=51 WHERE gc.activeflag is true AND sr.schoolyear=2016 
			AND st.statestudentidentifier = data[i][1] LIMIT 1)
			AND assessmentprogramgradesid is not null
		INTO currentgradeid;		
		
		IF currentenrollmentid IS NOT NULL AND currentgradeid IS NOT NULL THEN
			UPDATE enrollment  SET currentgradelevel = currentgradeid,
			modifieddate = now(),
			notes = concat(notes, ' -- updated grade on enrollment as per KSDE return file scalescore issue fix')
			WHERE 
				id = currentenrollmentid
				AND currentschoolyear=2016 ;
			RAISE NOTICE 'Student ''%'' with Enrollment id: ''%'' is updated with grade id: ''%''', data[i][1], currentenrollmentid, currentgradeid;
			
		ELSE
			RAISE NOTICE 'Enrollment not found for State student identifier ''%'', skipping...', data[i][1];
		END IF;
	END LOOP;
	
	UPDATE enrollment  SET currentgradelevel = 64,
			modifieddate = now(),
			notes = concat(notes, ' -- updated grade on enrollment as per KSDE return file scalescore issue fix')
			WHERE 
				id in(1986116, 2312012)
				AND currentschoolyear=2016 ;
	
	
END;
$BODY$;

