
--uncomment if these are not executed
--ALTER TABLE studenttrackerband DROP CONSTRAINT fk_studenttrackerband_testsession;
--ALTER TABLE ititestsessionhistory DROP CONSTRAINT fk_ititsh_rosterid;

--ALTER TABLE ititestsessionhistory ADD CONSTRAINT fk_ititsh_rosterid  FOREIGN KEY (rosterid) REFERENCES roster(id);
--ALTER TABLE studenttrackerband ADD CONSTRAINT fk_studenttrackerband_testsession FOREIGN KEY (testsessionid) REFERENCES testsession(id);

DO
$BODY$
DECLARE
    deleteids bigint[];
    syear integer;
    StartTime timestamptz;
EndTime timestamptz;
  Delta interval;
BEGIN   
	StartTime := clock_timestamp();
	--PERFORM archivetablecount('before_count',2016);
	FOR syear IN (select unnest(ARRAY[2016])) LOOP  
		RAISE NOTICE  'processing year : %', syear;
		select ARRAY_AGG(id) into deleteids from (select id from studentstests where testsessionid in (select id from testsession where schoolyear=syear)) as stids;
					       
		RAISE NOTICE  ' Number of studentstests records to be removed : %', array_length(deleteids, 1);
		
                RAISE NOTICE  'processing at:% delete on table:ccqscoreitem',clock_timestamp();
		delete from ccqscoreitem where ccqscoreid in (select id from ccqscore where scoringassignmentstudentid in
		(select id from scoringassignmentstudent where studentstestsid =ANY(deleteids)));

		RAISE NOTICE  'processing at:% delete on table:ccqscore',clock_timestamp();
		delete from ccqscore where scoringassignmentstudentid in (select id from scoringassignmentstudent where studentstestsid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:scoringassignmentstudent',clock_timestamp();
		delete from scoringassignmentstudent where studentstestsid=ANY(deleteids); 

		RAISE NOTICE  'processing at:% delete on table:scoringassignmentscorer',clock_timestamp();
		delete from scoringassignmentscorer where scoringassignmentid in (select sa.id from scoringassignment sa 
		  inner join testsession ts on ts.id=sa.testsessionid where ts.schoolyear=syear);

		RAISE NOTICE  'processing at:% delete on table:scoringassignment',clock_timestamp();  
		delete from scoringassignment where testsessionid in (select id from testsession where schoolyear=syear);	
		
		RAISE NOTICE  'processing at:% delete on table:studentsresponses',clock_timestamp();
		delete from studentsresponses where studentstestsectionsid in (select id from studentstestsections where studentstestid=ANY(deleteids));
			
		RAISE NOTICE  'processing at:% delete on table:studentsresponsescopy',clock_timestamp();
		delete from studentsresponsescopy where studentstestsectionsid in (select id from studentstestsections where studentstestid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:studentstestsectionstasksfoils',clock_timestamp();
		delete from studentstestsectionstasksfoils where studentstestsectionsid in (select id from studentstestsections where studentstestid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:studentstestsectionstasks',clock_timestamp();
		delete from studentstestsectionstasks where studentstestsectionsid in (select id from studentstestsections where studentstestid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:studentsresponseparameters',clock_timestamp();
		delete from studentsresponseparameters where studentstestsectionsid in (select id from studentstestsections where studentstestid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:exitwithoutsavetest',clock_timestamp();
		delete from exitwithoutsavetest where studenttestsectionid in (select id from studentstestsections where studentstestid=ANY(deleteids));

		RAISE NOTICE  'processing at:% delete on table:studentstestsections',clock_timestamp();
		delete from studentstestsections where studentstestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentadaptivetestthetastatus',clock_timestamp();
		delete from studentadaptivetestthetastatus where studentstestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentadaptivetestfinaltheta',clock_timestamp();
		delete from studentadaptivetestfinaltheta where studentstestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentsadaptivetestsections',clock_timestamp();
		delete from studentsadaptivetestsections where studentstestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentspecialcircumstance',clock_timestamp();
		delete from studentspecialcircumstance where studenttestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentstestshighlighterindex',clock_timestamp();
		delete from studentstestshighlighterindex where studenttestid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentstestshistory',clock_timestamp();
		delete from studentstestshistory where studentstestsid=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studentsteststags',clock_timestamp();
		delete from studentsteststags where studenttestid=ANY(deleteids);
                
		RAISE NOTICE  'processing at:% delete on table:studentstests',clock_timestamp();
		delete from studentstests where id=ANY(deleteids);

		RAISE NOTICE  'processing at:% delete on table:studenttrackerband',clock_timestamp();
		delete from studenttrackerband where testsessionid in (	select id from testsession where schoolyear=syear);

		RAISE NOTICE  'processing at:% delete on table:testsession',clock_timestamp();
		delete from testsession where schoolyear=syear;
		RAISE NOTICE  'completed studenttests and testsession data for year : %', syear;
		
		--enrollment
		select ARRAY_AGG(id) into deleteids from (select id from enrollment where currentschoolyear=syear) as eids;

		RAISE NOTICE  'started enrollment data for year : %', syear;
		RAISE NOTICE  ' Number of enrollment records to be removed : %', array_length(deleteids, 1);
		
		delete from enrollmenttesttypesubjectarea where enrollmentid=ANY(deleteids);
		delete from enrollmentsrosters where enrollmentid=ANY(deleteids);
		delete from enrollment where id=ANY(deleteids);
		RAISE NOTICE  'completed enrollment data for year : %', syear;
		RAISE NOTICE  'started roster data for year : %', syear;
		--roster
		DELETE from ititestsessionhistory where rosterid in (select id from roster where currentschoolyear = syear);
		delete from roster where currentschoolyear = syear;
		RAISE NOTICE  'completed roster data for year : %', syear;
		RAISE NOTICE  'completed processing year : %', syear;
		
	END LOOP; 
	--PERFORM archivetablecount('after_count',2016);
	EndTime := clock_timestamp();	
  Delta := 1000 * ( extract(epoch from EndTime) - extract(epoch from StartTime) );
  RAISE NOTICE 'Duration in millisecs=%', Delta;
END;
$BODY$;