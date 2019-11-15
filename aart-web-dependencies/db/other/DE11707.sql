--Test Records Educator changes 
DO
$BODY$ 
DECLARE
                DUPLICATE_KIDS_USERS RECORD;
                KIDS_USER_ORGS RECORD;
                new_USER_ORG_ID BIGINT;
                INACTIVE_TEACHERS_ORGANIZATIONS RECORD;
BEGIN

    FOR INACTIVE_TEACHERS_ORGANIZATIONS IN (
                                select uo.id as usersorganizationsid, uo.organizationid, uo.aartuserid 
                                                from usersorganizations uo
                        join aartuser au on au.id = uo.aartuserid
                        where au.id in (select distinct(teacherid) from roster where sourcetype ='TEST' 
                                                                                                and createddate >= '2015-11-01' and createddate < '2015-12-01')
                                                and au.activeflag is false
     ) LOOP
                raise NOTICE 'looking for data for userid %', INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid;
                IF NOT EXISTS ( select 1 from aartuser au 
                                                  join aartuser prevau on ((au.email = prevau.email 
                                                                                or au.uniquecommonidentifier = prevau.uniquecommonidentifier) 
                                                                                and au.id <> prevau.id and prevau.activeflag is true)
                                                  join usersorganizations uo on uo.aartuserid=prevau.id                                
                                                                where uo.organizationid in (select id from organization_children(51) union select 51)
                                                                                and au.id=INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid
                                                                ) THEN
                  BEGIN
                  
                                IF NOT EXISTS (select 1 from userorganizationsgroups where userorganizationid = INACTIVE_TEACHERS_ORGANIZATIONS.usersorganizationsid) THEN
                                
                                                RAISE NOTICE 'Inserting data for userid %', INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid;
                                                INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, isdefault) 
                                                                VALUES ((select id from groups where groupcode='TEA'),3, INACTIVE_TEACHERS_ORGANIZATIONS.usersorganizationsid, true);

                                                update aartuser set activeflag=true where id=INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid and activeflag is false;
        
                                END IF; 
                  END;
                ELSE
                  BEGIN
                    FOR DUPLICATE_KIDS_USERS IN (
                                select distinct au.id as kidsuserid, prevau.id as epuserid, prevau.modifieddate
                                                from aartuser au 
                                                join aartuser prevau on ((au.email = prevau.email 
                                                                                or au.uniquecommonidentifier = prevau.uniquecommonidentifier) and au.id <> prevau.id and prevau.activeflag is true)
                                                join usersorganizations uo on uo.aartuserid=prevau.id                                                                  
                                                where uo.organizationid in (select id from organization_children(51) union select 51 ) 
                                                                and au.id = INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid order by prevau.modifieddate desc limit 1                                             
                    ) LOOP
                                    FOR KIDS_USER_ORGS IN (
                                                select * from usersorganizations where aartuserid=DUPLICATE_KIDS_USERS.kidsuserid
                                                                and organizationid not in (select organizationid from usersorganizations where aartuserid=DUPLICATE_KIDS_USERS.epuserid)
                                    ) LOOP 
                                                SELECT INTO new_USER_ORG_ID (select nextval('usersorganizations_id_seq'));
                                                
                                                INSERT INTO usersorganizations(id, aartuserid, organizationid, isdefault)
                                                                                                VALUES (new_USER_ORG_ID, DUPLICATE_KIDS_USERS.epuserid, KIDS_USER_ORGS.organizationid, false);
                                                                                                
                                                INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, isdefault) 
                                                                                                VALUES ((select id from groups where groupcode='TEA'), 3, new_USER_ORG_ID, true);                                                                                                                                         
                                    END LOOP;
                                    
                                    UPDATE roster set teacherid=DUPLICATE_KIDS_USERS.epuserid WHERE teacherid=DUPLICATE_KIDS_USERS.kidsuserid;
                    END LOOP;        
                  END;
                END IF;
    END LOOP;          
END;
$BODY$;

--TASC Records Educator changes 
DO
$BODY$ 
DECLARE
                DUPLICATE_KIDS_USERS RECORD;
                KIDS_USER_ORGS RECORD;
                new_USER_ORG_ID BIGINT;
                INACTIVE_TEACHERS_ORGANIZATIONS RECORD;
BEGIN

    FOR INACTIVE_TEACHERS_ORGANIZATIONS IN (
                                select uo.id as usersorganizationsid, uo.organizationid, uo.aartuserid 
                                                from usersorganizations uo
                        join aartuser au on au.id = uo.aartuserid
                        where au.id in (select distinct(teacherid) from roster where sourcetype ='TASC' 
                                                                                                and createddate >= '2015-11-07' and createddate < '2015-12-01')
                                                and au.activeflag is false
     ) LOOP
                raise NOTICE 'looking for data for userid %', INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid;
                IF NOT EXISTS ( select 1 from aartuser au 
                                                  join aartuser prevau on ((au.email = prevau.email 
                                                                                or au.uniquecommonidentifier = prevau.uniquecommonidentifier) 
                                                                                and au.id <> prevau.id and prevau.activeflag is true)
                                                  join usersorganizations uo on uo.aartuserid=prevau.id                                
                                                                where uo.organizationid in (select id from organization_children(51) union select 51)
                                                                                and au.id=INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid
                                                                ) THEN
                  BEGIN
                  
                                IF NOT EXISTS (select 1 from userorganizationsgroups where userorganizationid = INACTIVE_TEACHERS_ORGANIZATIONS.usersorganizationsid) THEN
                                
                                                RAISE NOTICE 'Inserting data for userid %', INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid;
                                                INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, isdefault) 
                                                                VALUES ((select id from groups where groupcode='TEA'),3, INACTIVE_TEACHERS_ORGANIZATIONS.usersorganizationsid, true);

                                                update aartuser set activeflag=true where id=INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid and activeflag is false;
        
                                END IF; 
                  END;
                ELSE
                  BEGIN
                    FOR DUPLICATE_KIDS_USERS IN (
                                select distinct au.id as kidsuserid, prevau.id as epuserid, prevau.modifieddate
                                                from aartuser au 
                                                join aartuser prevau on ((au.email = prevau.email 
                                                                                or au.uniquecommonidentifier = prevau.uniquecommonidentifier) and au.id <> prevau.id and prevau.activeflag is true)
                                                join usersorganizations uo on uo.aartuserid=prevau.id                                                                  
                                                where uo.organizationid in (select id from organization_children(51) union select 51 ) 
                                                                and au.id = INACTIVE_TEACHERS_ORGANIZATIONS.aartuserid order by prevau.modifieddate desc limit 1                                             
                    ) LOOP
                                    FOR KIDS_USER_ORGS IN (
                                                select * from usersorganizations where aartuserid=DUPLICATE_KIDS_USERS.kidsuserid
                                                                and organizationid not in (select organizationid from usersorganizations where aartuserid=DUPLICATE_KIDS_USERS.epuserid)
                                    ) LOOP 
                                                SELECT INTO new_USER_ORG_ID (select nextval('usersorganizations_id_seq'));
                                                
                                                INSERT INTO usersorganizations(id, aartuserid, organizationid, isdefault)
                                                                                                VALUES (new_USER_ORG_ID, DUPLICATE_KIDS_USERS.epuserid, KIDS_USER_ORGS.organizationid, false);
                                                                                                
                                                INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, isdefault) 
                                                                                                VALUES ((select id from groups where groupcode='TEA'), 3, new_USER_ORG_ID, true);                                                                                                                                         
                                    END LOOP;
                                    
                                    UPDATE roster set teacherid=DUPLICATE_KIDS_USERS.epuserid WHERE teacherid=DUPLICATE_KIDS_USERS.kidsuserid;
                    END LOOP;        
                  END;
                END IF;
    END LOOP;          
END;
$BODY$;

--Duplicate Roster cleanup
DO
$BODY$ 
DECLARE
                DUPLICATE_ROSTER_DETAILS RECORD;
                firstCreatedRosterId BIGINT;
                lastCreatedRosterId BIGINT;
                ENROLLMENT_ROSTERS RECORD;
BEGIN
    FOR DUPLICATE_ROSTER_DETAILS IN (
                select coursesectionname, teacherid, statesubjectareaid, attendanceschoolid, aypschoolid, sourcetype 
                                from roster where currentschoolyear=2016 and activeflag is true 
                                and attendanceschoolid in (select id from organization_children(51)) 
                                and aypschoolid in (select id from organization_children(51)) 
                                group by coursesectionname, teacherid, statesubjectareaid, attendanceschoolid, aypschoolid, sourcetype 
                                having count(*) > 1
) LOOP
                select max(id),min(id) into lastCreatedRosterId, firstCreatedRosterId from roster 
                                where coursesectionname=DUPLICATE_ROSTER_DETAILS.coursesectionname and teacherid=DUPLICATE_ROSTER_DETAILS.teacherid
                                and statesubjectareaid=DUPLICATE_ROSTER_DETAILS.statesubjectareaid and attendanceschoolid=DUPLICATE_ROSTER_DETAILS.attendanceschoolid 
                                and aypschoolid=DUPLICATE_ROSTER_DETAILS.aypschoolid and currentschoolyear=2016;

                RAISE NOTICE 'First created rosterid %, Last created roster %', firstCreatedRosterId, lastCreatedRosterId;
                
                    FOR ENROLLMENT_ROSTERS IN (
                                select * from enrollmentsrosters where rosterid=lastCreatedRosterId
                                                and activeflag is true 
                    ) LOOP 

                                IF EXISTS (select 1 from enrollmentsrosters where rosterid=firstCreatedRosterId and 
                                                                                                                enrollmentid = ENROLLMENT_ROSTERS.enrollmentid) THEN
                                  BEGIN
                                                UPDATE enrollmentsrosters set activeflag=true 
                                                                where rosterid=firstCreatedRosterId and enrollmentid = ENROLLMENT_ROSTERS.enrollmentid;
                                  END;
                                ELSE
                                  BEGIN
                                                UPDATE enrollmentsrosters set rosterid=firstCreatedRosterId where id=ENROLLMENT_ROSTERS.id;
                                  END;
                                END IF;
                                                                                                                                
                    END LOOP;                      

                    UPDATE enrollmentsrosters set activeflag = false where rosterid=lastCreatedRosterId;
                    UPDATE roster set activeflag = false where id=lastCreatedRosterId;
    END LOOP;
END;
$BODY$;

