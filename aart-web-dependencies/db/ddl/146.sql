

--- Student test section and related tables Migrate from tde to EP

alter table studentstestsections rename column studentstestsid to studentstestid;
alter table studentstestsections rename column createdate to createddate;
alter table studentstestsections rename column testingstatusid to statusid;
alter table studentstestsections alter column statusid SET NOT NULL; 
alter table studentstestsections add column ticketno character varying(75) DEFAULT NULL::character varying;
alter table studentstestsections add column createduser integer; 
alter table studentstestsections add column modifieduser integer;
alter table studentstestsections add column activeflag boolean DEFAULT true;
--alter table studentstestsections add column statusid bigint NOT NULL;
alter table studentstestsections drop column createusername;
alter table studentstestsections drop column modifiedusername;
alter table studentstestsections drop column studentid;
alter table studentstestsections drop column testid; 
alter table studentstestsections drop column sortorder;
alter table studentstestsections drop column IF EXISTS "testSectionInstanceId";

update studentstestsections set statusid = (select c.id from category c join categorytype ct on ct.id = c.categorytypeid where ct.typecode ilike 'STUDENT_TESTSECTION_STATUS' 
        and c.categorycode ilike 'unused' ) where statusid = 1;
update studentstestsections set statusid = (select c.id from category c join categorytype ct on ct.id = c.categorytypeid where ct.typecode ilike 'STUDENT_TESTSECTION_STATUS' 
        and c.categorycode ilike 'inprogress' ) where statusid = 2;
update studentstestsections set statusid = (select c.id from category c join categorytype ct on ct.id = c.categorytypeid where ct.typecode ilike 'STUDENT_TESTSECTION_STATUS' 
        and c.categorycode ilike 'complete' ) where statusid = 3;
update studentstestsections set statusid = (select c.id from category c join categorytype ct on ct.id = c.categorytypeid where ct.typecode ilike 'STUDENT_TESTSECTION_STATUS' 
        and c.categorycode ilike 'reactivated' ) where statusid = 4;
update studentstestsections set statusid = (select c.id from category c join categorytype ct on ct.id = c.categorytypeid where ct.typecode ilike 'STUDENT_TESTSECTION_STATUS' 
        and c.categorycode ilike 'inprogresstimedout' ) where statusid = 5;
        
SELECT setval('studentstestsections_id_seq', (SELECT MAX(id) FROM studentstestsections), true);

CREATE OR REPLACE FUNCTION studenttestsectionsmerge() RETURNS INTEGER AS 
$BODY$
DECLARE  
    stid        public.studentstestsections.studentstestid%TYPE;
    tid         public.studentstestsections.testsectionid%TYPE;  
    tno		public.studentstestsections.ticketno%TYPE; 
    cd          public.studentstestsections.createddate%TYPE;
    cu          public.studentstestsections.createduser%TYPE;  
    af		public.studentstestsections.activeflag%TYPE;  
    mu		public.studentstestsections.modifieduser%TYPE;  
    md		public.studentstestsections.modifieddate%TYPE;  
    st		public.studentstestsections.statusid%TYPE;
    foundCount	integer;
    foundRec record;
    recCursor CURSOR FOR  
         select studentstestid, testsectionid,  ticketno,  createddate,  createduser,  activeflag,  modifieduser,  modifieddate,  statusid from studentstestsections_aart where studentstestid is not null and testsectionid is not null;

BEGIN
RAISE INFO '---------Enter---------------';   
OPEN recCursor;   
	LOOP
		
		FETCH recCursor INTO stid, tid, tno, cd, cu, af, mu, md, st;  
		EXIT WHEN NOT FOUND ;  
		foundCount := 0;
		FOR foundRec IN EXECUTE 'select * from studentstestsections sts1 where sts1.studentstestid = ' || stid || ' and sts1.testsectionid = ' || tid || ''
		LOOP   
		   foundCount := 1;
		   --RAISE INFO '%     %     %', foundCount, stid, tid;  
		   update studentstestsections set ticketno = tno, createddate = cd,  createduser = cu,  activeflag = af,  modifieduser = mu,  modifieddate = md,  statusid = st  where id = foundRec.id; 
		END LOOP; 
		IF foundCount != 1
		THEN 
			--RAISE INFO '%     %     %', foundCount, stid, tid; 
			insert into studentstestsections(studentstestid, testsectionid,  ticketno,  createddate,  createduser,  activeflag,  modifieduser,  modifieddate,  statusid) values (stid, tid, tno, cd, cu, af, mu, md, st);
		END IF;
		 
		
	END LOOP;
CLOSE recCursor;  
RAISE INFO '---------Exit --------------- ';   
RETURN 1;  
END
$BODY$
LANGUAGE plpgsql;

/*NO LOAD BALANCE*/ select * from studenttestsectionsmerge();

DROP FUNCTION IF EXISTS studenttestsectionsmerge();
        
delete from studentstestsectionstasksfoils where studentstestsectionsid in (select id from studentstestsections where studentstestid in (select studentstestid from studentstestsections except (select id from studentstests)));
delete from studentstestsectionstasks where studentstestsectionsid in (select id from studentstestsections where studentstestid in (select studentstestid from studentstestsections except (select id from studentstests)));
delete from studentstestsections where studentstestid in (select studentstestid from studentstestsections except (select id from studentstests));
delete from studentstestsections where testsectionid in (select testsectionid from studentstestsections except (select id from testsection));
delete from studentstestsections where statusid in (select statusid from studentstestsections except (select id from category));


alter table studentstestsections add CONSTRAINT fk_studentstestsections_status FOREIGN KEY (statusid)
      REFERENCES category (id) MATCH SIMPLE  ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsections add CONSTRAINT fk_studentstestsections_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsections add CONSTRAINT fk_studentstestsections_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsections add CONSTRAINT fk_studentstestsections_testsectionid FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsections add CONSTRAINT fk_studentstestsections_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

delete from studentsresponses where studentstestsectionsid in (select st1.id FROM studentstestsections st1 join studentstestsections st2 on st1.studentstestid = st2.studentstestid AND st1.testsectionid = st2.testsectionid where st1.id < st2.id);
delete from studentstestsectionstasksfoils where studentstestsectionsid in (select st1.id FROM studentstestsections st1 join studentstestsections st2 on st1.studentstestid = st2.studentstestid AND st1.testsectionid = st2.testsectionid where st1.id < st2.id);
delete from studentstestsectionstasks where studentstestsectionsid in (select st1.id FROM studentstestsections st1 join studentstestsections st2 on st1.studentstestid = st2.studentstestid AND st1.testsectionid = st2.testsectionid where st1.id < st2.id);
DELETE FROM studentstestsections st1 USING studentstestsections st2 WHERE st1.studentstestid = st2.studentstestid AND st1.testsectionid = st2.testsectionid AND st1.id < st2.id;
alter table studentstestsections add CONSTRAINT ukey_studentstestsections UNIQUE (studentstestid , testsectionid );

--- studentstestsectionstasksfoils

alter table studentstestsectionstasksfoils drop CONSTRAINT studentstestsectionstasksfoils_pkey;
alter table studentstestsectionstasksfoils drop  CONSTRAINT studentstestsectionstasksfoils_studentstestsectionstasks_fkey;
            
alter table studentstestsectionstasksfoils drop column testsessionid;
alter table studentstestsectionstasksfoils drop column testsectionid;

--- studentstestsectionstasks

alter table studentstestsectionstasks drop CONSTRAINT studentstestsectionstasks_pkey;
alter table studentstestsectionstasks drop  CONSTRAINT studentstestsectionstasks_studentstestsectionsid_fkey;
            
alter table studentstestsectionstasks drop column testsessionid;
alter table studentstestsectionstasks drop column testsectionid;


alter table studentstestsectionstasks add CONSTRAINT studentstestsectionstasks_pkey PRIMARY KEY (studentstestsectionsid , taskid);
alter table studentstestsectionstasks add  CONSTRAINT studentstestsectionstasks_studentstestsectionsid_fkey FOREIGN KEY (studentstestsectionsid)
      REFERENCES studentstestsections (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsectionstasks add  CONSTRAINT studentstestsectionstasks_taskid_fkey FOREIGN KEY (taskid)
      REFERENCES taskvariant (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
      
alter table studentstestsectionstasksfoils add CONSTRAINT studentstestsectionstasksfoils_pkey PRIMARY KEY (studentstestsectionsid , taskid, foilid);
alter table studentstestsectionstasksfoils add  CONSTRAINT studentstestsectionstasksfoils_studentstestsectionstasks_fkey FOREIGN KEY (studentstestsectionsid)
      REFERENCES studentstestsections (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsectionstasksfoils add  CONSTRAINT studentstestsectionstasksfoils_taskid_fkey FOREIGN KEY (taskid)
      REFERENCES taskvariant (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table studentstestsectionstasksfoils add  CONSTRAINT studentstestsectionstasksfoils_foilid_fkey FOREIGN KEY (foilid)
      REFERENCES foil (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
      
