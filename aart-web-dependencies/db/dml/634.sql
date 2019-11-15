--dml For Data team
update studentassessmentprogram set createddate='2010-01-01 00:00:00+00', createduser=12, modifieddate=now(), modifieduser=12 where modifieddate is null;
update testsectionstaskvariants set createddate='2010-01-01 00:00:00+00', createduser=12, modifieddate=now(), modifieduser=12 where modifieddate is null;

--for DE15053
update ccqscore set modifieddate=now(), modifieduser=12 where modifieddate is null;
update ccqscoreitem set modifieddate=now(), modifieduser=12 where modifieddate is null;

--DE15206: EP Prod: Blueprint Coverage Report shows Conceptual Area as Partially Met, but Blueprint requirements indicate it has been met
-- DLM: Inserting the new group for ELA, GRADE-3, Criteria - 2

DO
$BODY$
DECLARE
   grade3_id BIGINT;
   ela_contentareaid BIGINT;
   grade3_ela_required_blueprint_id BIGINT;
   new_bluePrint_id BIGINT;
   db_userId BIGINT;

BEGIN
     select id from aartuser where username = 'cetesysadmin' INTO db_userId;
     select id from contentarea where abbreviatedname = 'ELA' INTO ela_contentareaid;
     select id from gradecourse where abbreviatedname = '3' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA') INTO  grade3_id;
     select id from blueprint where criteria=2 and groupnumber=4 and contentareaid = ela_contentareaid and  gradecourseid = grade3_id INTO grade3_ela_required_blueprint_id;
     
insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(grade3_ela_required_blueprint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.RL.3.4' limit 1), 'ELA.EE.RL.3.4', 3);

insert into blueprint(contentareaid, gradecourseid, criteria, groupnumber, numberrequired, createduser, createddate, modifieduser, modifieddate, activeflag, writingtestlet)
    values(ela_contentareaid, grade3_id, 2, (select max(groupnumber) + 1 from blueprint), 2, db_userId, now(), db_userId, now(), true, false) returning id INTO new_bluePrint_id;

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(new_bluePrint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.L.3.5.a' limit 1), 'ELA.EE.L.3.5.a', 1);

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(new_bluePrint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.L.3.5.c' limit 1), 'ELA.EE.L.3.5.c', 2);

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(new_bluePrint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.RI.3.4' limit 1), 'ELA.EE.RI.3.4', 3);

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(new_bluePrint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.RI.3.8' limit 1), 'ELA.EE.RI.3.8', 4);

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
            values(new_bluePrint_id, (SELECT essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'ELA.EE.RL.3.4' limit 1), 'ELA.EE.RL.3.4', 5);


END;
$BODY$;