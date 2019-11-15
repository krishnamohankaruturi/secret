
--US14845 clean data so constraints can be added
--yes this is dml in a ddl file but must be ran before the constraints can be added
--fix old referenced data not in contentarea/gradecourse anymore
DO
$BODY$
DECLARE 
 tct1 record;

BEGIN
FOR tct1 IN (select tct.testcollectionid, tct.testid, tc.gradecourseid, tc.contentareaid 
		from test t 
		join testcollectionstests tct on t.id=tct.testid 
		join testcollection tc on tc.id = tct.testcollectionid 
		where t.contentareaid not in (select id from contentarea))
LOOP
	update test set gradecourseid = tct1.gradecourseid, contentareaid=tct1.contentareaid where id=tct1.testid;
END LOOP;

END;
$BODY$;

DO
$BODY$
DECLARE 
 tct1 record;

BEGIN
FOR tct1 IN (select tct.testcollectionid, tct.testid, tc.gradecourseid, tc.contentareaid
		from test t 
		join testcollectionstests tct on t.id=tct.testid 
		join testcollection tc on tc.id = tct.testcollectionid 
		where t.gradecourseid not in (select id from gradecourse))
LOOP
	update test set gradecourseid = tct1.gradecourseid, contentareaid=tct1.contentareaid where id=tct1.testid;
END LOOP;

END;
$BODY$;


update test set gradecourseid=null where id in (select id from test where gradecourseid not in (select id from gradecourse));


alter table test add CONSTRAINT testcollection_contentareaid_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table test add CONSTRAINT testcollection_gradecourseid_fkey FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;	