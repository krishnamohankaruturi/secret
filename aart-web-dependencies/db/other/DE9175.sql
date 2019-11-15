--DE 9175 Populating missing group indicator for reading and history 
DO 
$BODY$
DECLARE
     readingRecords RECORD;
     historyRecords RECORD;
     groupingInd1 character varying(50);
     groupingInd2 character varying(50);
     testTypeCode character varying(10);     
BEGIN
	RAISE NOTICE 'Get Reading enrollmenttesttypes';
	FOR readingRecords IN (
		select enttsa.*, en.studentid, st.statestudentidentifier 
		from enrollmenttesttypesubjectarea enttsa
		join enrollment en on enttsa.enrollmentid = en.id 
		join student st on en.studentid = st.id 
		where en.currentschoolyear=2015 and en.activeflag is true
			and st.stateid=51 and st.activeflag is true and enttsa.activeflag is true
			and enttsa.subjectareaid = 17 and testtypeid in (2,18,19)
			and enttsa.groupingindicator1 is null and groupingindicator2 is null
	) 
	LOOP
		--RAISE NOTICE 'enrollmenttesttypesubjectarea Id = %, enrollmentid = %, studentid = %', readingRecords.id, readingRecords.enrollmentid, readingRecords.studentid;
		--RAISE NOTICE 'groupingInd1 = %, groupingInd2 = %, testTypeCode = %', groupingInd1, groupingInd2, testTypeCode;
		groupingInd1 = null; 
		groupingInd2 = null;
		testTypeCode = null;

		select grouping_reading_1, grouping_reading_2, state_reading_assess into groupingInd1, groupingInd2, testTypeCode
			from kids_record_staging where state_student_identifier=readingRecords.statestudentidentifier 
			and state_reading_assess in ('2','3','C','c') order by id desc limit 1;			
			
		IF (groupingInd1 is not null or groupingInd2 is not null) THEN
			RAISE NOTICE 'Updating..with..groupingInd1 = %, groupingInd2 = %, testTypeCode = %', groupingInd1, groupingInd2, testTypeCode;		
			update enrollmenttesttypesubjectarea 
				set groupingindicator1=groupingInd1, groupingindicator2=groupingInd2
			where id=readingRecords.id;
		END IF;
	END LOOP;

	RAISE NOTICE 'Get history enrollmenttesttypes';
	FOR historyRecords IN (
		select enttsa.*, en.studentid, st.statestudentidentifier 
		from enrollmenttesttypesubjectarea enttsa
		join enrollment en on enttsa.enrollmentid = en.id 
		join student st on en.studentid = st.id 
		where en.currentschoolyear=2015 and en.activeflag is true
			and st.stateid=51 and st.activeflag is true and enttsa.activeflag is true
			and enttsa.subjectareaid = 20 and testtypeid in (2,18,19)
			and enttsa.groupingindicator1 is null and groupingindicator2 is null
	) 
	LOOP
		--RAISE NOTICE 'enrollmenttesttypesubjectarea Id = %, enrollmentid = %, studentid = %', historyRecords.id, historyRecords.enrollmentid, historyRecords.studentid;
		--RAISE NOTICE 'groupingInd1 = %, groupingInd2 = %, testTypeCode = %', groupingInd1, groupingInd2, testTypeCode;
		groupingInd1 = null; 
		groupingInd2 = null;
		testTypeCode = null;

		select grouping_history_1, grouping_history_2, state_history_assess into groupingInd1, groupingInd2, testTypeCode
			from kids_record_staging where state_student_identifier=historyRecords.statestudentidentifier 
			and state_history_assess in ('2','3','C','c') order by id desc limit 1;			
			
		IF (groupingInd1 is not null or groupingInd2 is not null) THEN
			RAISE NOTICE 'Updating..with..groupingInd1 = %, groupingInd2 = %, testTypeCode = %', groupingInd1, groupingInd2, testTypeCode;		
			update enrollmenttesttypesubjectarea 
				set groupingindicator1=groupingInd1, groupingindicator2=groupingInd2
			where id=historyRecords.id;
		END IF;
	END LOOP;	
END;
$BODY$;