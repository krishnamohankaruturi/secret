DO 
$BODY$ 
DECLARE
    DSROW RECORD;
    ERROW RECORD;
    EROW RECORD;
    EID bigint;
    RID bigint;
    ERID bigint;
    SID bigint;
    DIDS bigint[];
    maxid bigint;
    minid bigint;
BEGIN
    FOR DSROW IN (SELECT statestudentidentifier, count(statestudentidentifier) as cnt, array_agg(s.id) as dids, 
			max(s.id) as maxid,min(s.id) as minid FROM student s 
				WHERE activeflag=true --and statestudentidentifier=<test statestudentidentifier>
				and id in (SELECT distinct studentid  FROM enrollment e  
			WHERE e.attendanceschoolid in (select id from organization_children((select id 
					from organization where organizationname='Kansas' and organizationtypeid=2)))) 
		GROUP BY statestudentidentifier  HAVING count(statestudentidentifier) > 1   ORDER BY statestudentidentifier) LOOP
	RAISE NOTICE  '%,%', DSROW.statestudentidentifier, DSROW.cnt;                                       
	DIDS :=	DSROW.dids;
	maxid := DSROW.maxid;
	FOR SID IN (select x from unnest(DSROW.dids) x) LOOP
		RAISE NOTICE  'Started %', SID; 
		IF SID != maxid THEN
			RAISE NOTICE  'SID != maxid  %,%', SID,maxid;
			FOR EROW IN (SELECT e.* from enrollment e where e.studentid=SID) LOOP
				SELECT id INTO EID FROM enrollment 
					WHERE currentschoolyear = EROW.currentschoolyear
						AND attendanceschoolid = EROW.attendanceschoolid
						AND studentid=maxid;
				IF EID IS NULL THEN
					UPDATE enrollment set studentid=maxid where id=EROW.id;
				ELSE
					--update enrollmenttesttypesubjectarea
					FOR ERROW IN (SELECT * from enrollmenttesttypesubjectarea where enrollmentid=EROW.id) LOOP
						SELECT id INTO ERID FROM enrollmenttesttypesubjectarea 
							WHERE enrollmentid=EID AND testtypeid=ERROW.testtypeid 
							AND subjectareaid=ERROW.subjectareaid;
						IF ERID IS NULL THEN
							UPDATE enrollmenttesttypesubjectarea SET enrollmentid=EID WHERE id=ERROW.id; 
						ELSE
							DELETE FROM enrollmenttesttypesubjectarea WHERE id=ERROW.id; 
						END IF;
					END LOOP;
					
					--update enrollmentsrosters
					FOR ERROW IN (SELECT * from enrollmentsrosters where enrollmentid=EROW.id) LOOP
						SELECT id INTO ERID FROM enrollmentsrosters 
							WHERE enrollmentid=EID AND rosterid=ERROW.rosterid;
						IF ERID IS NULL THEN
							UPDATE enrollmentsrosters SET enrollmentid=EID WHERE id=ERROW.id; 
						ELSE
							DELETE FROM enrollmentsrosters WHERE id=ERROW.id; 
						END IF;
					END LOOP;
					DELETE FROM enrollment WHERE id=EROW.id;
				END IF;
			END LOOP;
			
			FOR ERROW IN (SELECT * from studentstests where studentid=SID) LOOP
				SELECT id INTO ERID FROM studentstests WHERE studentid=maxid AND testsessionid=ERROW.testsessionid;
				IF ERID IS NULL THEN
					UPDATE studentstests SET studentid=maxid WHERE id= ERROW.id;
					UPDATE studentsresponses SET studentid=maxid WHERE studentstestsid=ERROW.id;
				ELSE
					DELETE FROM studentsresponses WHERE studentstestsid=ERROW.id;
					DELETE FROM studentsresponses_aart WHERE studentstestsid=ERROW.id;
					DELETE FROM studentstestsectionstasksfoils WHERE studentstestsectionsid in (select id from studentstestsections WHERE studentstestid = ERROW.id);
					DELETE FROM studentstestsectionstasks WHERE studentstestsectionsid in (select id from studentstestsections WHERE studentstestid = ERROW.id);
					DELETE FROM studentstestsections WHERE studentstestid = ERROW.id;
					DELETE FROM studentstestsections_aart WHERE studentstestid=ERROW.id;
					DELETE FROM studentstests WHERE id= ERROW.id;
				END IF;
			END LOOP;
			
			FOR ERROW IN (SELECT * from studentsassessments where studentid=SID) LOOP
				SELECT studentid INTO ERID FROM studentsassessments 
					WHERE studentid=maxid AND assessmentid=ERROW.assessmentid AND contentareaid=ERROW.contentareaid;
				IF ERID IS NULL THEN
					UPDATE studentsassessments SET studentid=maxid WHERE studentid=SID AND assessmentid=ERROW.assessmentid AND contentareaid=ERROW.contentareaid;
				ELSE
					DELETE FROM studentsassessments WHERE studentid=SID AND assessmentid=ERROW.assessmentid AND contentareaid=ERROW.contentareaid;
				END IF;
			END LOOP;
			UPDATE studentprofileitemattributevalue SET studentid=maxid, activeflag=false WHERE studentid=SID;
			DELETE FROM student where id=SID;
		END IF;--//SID!=maxid
		RAISE NOTICE  'END %', SID; 
	END LOOP;
        RAISE NOTICE  'Completed %,%,%,%', DSROW.statestudentidentifier, DSROW.cnt,DIDS,maxid; 
    END LOOP;
END;
$BODY$;