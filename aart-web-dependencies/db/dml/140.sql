
--changes for CB related to navigation

INSERT INTO category(id, categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, activeflag, modifieddate)
                VALUES  (nextval('category_id_seq'), 'LINEAR', 'LINEAR', 'Linear', (SELECT id FROM categorytype WHERE typecode = 'NAVIGATION' AND originationcode = 'CB'), 5, 'CB',  now(), 'TRUE', now());

INSERT INTO category(id, categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, activeflag, modifieddate)
                VALUES  (nextval('category_id_seq'), 'NONLINEAR', 'NONLINEAR', 'Non Linear', (SELECT id FROM categorytype WHERE typecode = 'NAVIGATION' AND originationcode = 'CB'), 6, 'CB',  now(), 'TRUE', now());

DO 
$BODY$
DECLARE
    TDTSROW RECORD;
    TDTSNVROW RECORD;
   exist boolean;
BEGIN
                FOR TDTSROW IN (
                                select distinct t.id as testid, t.testformatcode, ts.id as testsectionid
                                from testsectionsrules tsr
                                inner join testsection ts on tsr.testsectionid = ts.id
                                inner join test t on ts.testid = t.id
                                where navigation=true  and tsr.ruleid in (
                                                SELECT id FROM category WHERE categorytypeid = 
                                                                (SELECT id FROM categorytype WHERE typecode = 'NAVIGATION' AND originationcode = 'CB') 
                                                                and externalid in (1,2,3,4) AND originationcode = 'CB')
                                order by t.id,ts.id
                ) LOOP
                                IF (TDTSROW.testformatcode = 'ADP') THEN 
                                                DELETE from testsectionsrules where testsectionid=TDTSROW.testsectionid;
                                END IF;

                                IF (TDTSROW.testformatcode IS NULL or TDTSROW.testformatcode = 'NADP' ) THEN
                                                exist = false;
                                                FOR TDTSNVROW IN (select ruleid from testsectionsrules where testsectionid=TDTSROW.testsectionid and ruleid in (SELECT id FROM category WHERE categorytypeid = 
                                                                (SELECT id FROM categorytype WHERE typecode = 'NAVIGATION' AND originationcode = 'CB') 
                                                                and externalid in (1,2,3,4) AND originationcode = 'CB')    ) LOOP
                                                                IF (TDTSNVROW.ruleid = (SELECT id FROM category WHERE categorytypeid = (SELECT id FROM categorytype 
                                                                                                WHERE typecode = 'NAVIGATION' AND originationcode = 'CB') and externalid =4 AND originationcode = 'CB')) THEN 
                                                                                exist = true; 
                                                                END IF;
                                                END LOOP;

                                                DELETE from testsectionsrules where testsectionid=TDTSROW.testsectionid;
                                                
                                                IF(exist) THEN
                                                                INSERT INTO testsectionsrules(testsectionid, ruleid, navigation)
                                                                                VALUES  (TDTSROW.testsectionid, (SELECT id FROM category WHERE categorytypeid = (SELECT id FROM categorytype 
                                                                                                WHERE typecode = 'NAVIGATION' AND originationcode = 'CB') and externalid = 6 AND originationcode = 'CB'), 'TRUE');
                                                ELSE
                                                                INSERT INTO testsectionsrules(testsectionid, ruleid, navigation)
                                                                                VALUES  (TDTSROW.testsectionid, (SELECT id FROM category WHERE categorytypeid = (SELECT id FROM categorytype 
                                                                                                WHERE typecode = 'NAVIGATION' AND originationcode = 'CB') and externalid = 5 AND originationcode = 'CB'), 'TRUE');                                          
END IF;                 
                                END IF;
                END LOOP;

END;
$BODY$;               
