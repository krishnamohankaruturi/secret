
--need to update students where received through KIDS that received TEST records with TEST Type ids = 3 to set students AssessmentProgramid
update student set assessmentprogramid=(select id from assessmentprogram where programname='Dynamic Learning Maps' limit 1)
where id in (select e.studentid from  enrollment e  inner join enrollmenttesttypesubjectarea ettsa on e.id = ettsa.enrollmentid
		inner join testtype tt on ettsa.testtypeid=tt.id
	where tt.testtypecode='3');
	
	
--need to update students where received through STCO statesubjectareaid to map to updated mapping
DO 
$BODY$ 
DECLARE
    CROW RECORD;
BEGIN
    FOR CROW IN (select distinct ca.id as ocaid, oca.contentareaid from roster r
	inner join contentarea ca on r.statesubjectareaid=ca.id
	inner join organizationcontentarea oca on ca.name=oca.organizationcontentareacode) LOOP
        RAISE NOTICE  '%,%', CROW.ocaid,CROW.contentareaid;                                       
		UPDATE roster SET statesubjectareaid=CROW.contentareaid where statesubjectareaid=CROW.ocaid;
        RAISE NOTICE  'UPDATED %,%', CROW.ocaid,CROW.contentareaid;
    END LOOP;
END;
$BODY$;

--update contractingorganization flag in organization table and need to update contractingorganizations' start and end dates
--create organization hierarchy for each contractingorganization
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    CTROW RECORD;
BEGIN
    FOR CROW IN (select distinct id from organization where organizationtypeid=2 and activeflag=true) LOOP
        RAISE NOTICE  '%', CROW.id;  
	INSERT INTO organizationhierarchy(organizationid, organizationtypeid) 
		SELECT distinct CROW.id, 1 FROM organizationhierarchy WHERE NOT EXISTS 
		(SELECT 1 from organizationhierarchy where organizationid=CROW.id and organizationtypeid=1); 
	INSERT INTO organizationhierarchy(organizationid, organizationtypeid) 
		SELECT distinct CROW.id, 2 FROM organizationhierarchy WHERE NOT EXISTS 
		(SELECT 1 from organizationhierarchy where organizationid=CROW.id and organizationtypeid=2);   
	FOR CTROW IN (select distinct organizationtypeid from organization_children(CROW.id) where organizationtypeid != 2) LOOP
		INSERT INTO organizationhierarchy(organizationid, organizationtypeid) 
			SELECT distinct CROW.id, CTROW.organizationtypeid FROM organizationhierarchy WHERE NOT EXISTS 
			(SELECT 1 from organizationhierarchy where organizationid=CROW.id and organizationtypeid=CTROW.organizationtypeid);  
	END LOOP;
        RAISE NOTICE  'CREATED orgtree %', CROW.id;
        UPDATE organization set buildinguniqueness=2,contractingorganization=true,schoolstartdate=to_date('08/13/2013', 'MM/DD/YYYY'),schoolenddate=to_date('08/13/2014', 'MM/DD/YYYY') where id=CROW.id;
        RAISE NOTICE  'UPDATED org info %', CROW.id;
    END LOOP;
END;
$BODY$;
--set null for these values for non contracting organizations
UPDATE organization set buildinguniqueness=null,schoolstartdate=null,schoolenddate=null where (contractingorganization is null or contractingorganization=false);

--prepare script to populate security agreement for non-dlm users in Kansas so that 
--system does not prompt then for agreement : execute manually by correcting statename
DO 
$BODY$ 
DECLARE
    CROW RECORD;
BEGIN
    FOR CROW IN (select distinct r.teacherid from roster r 
			where r.attendanceschoolid in (select id from 
				organization_children((select id from organization where organizationname='Kansas' and organizationtypeid=2)))
			and r.teacherid not in (select distinct r.teacherid from  enrollment e 
					inner join student s on s.id=e.studentid
					inner join enrollmentsrosters er  on er.enrollmentid=e.id
					inner join roster r on r.id=er.rosterid
					inner join assessmentprogram ap on s.assessmentprogramid=ap.id
				where ap.programname='Dynamic Learning Maps' and r.attendanceschoolid in (select id from 
				organization_children((select id from organization where organizationname='Kansas' and organizationtypeid=2))))) LOOP
        RAISE NOTICE  '%', CROW.teacherid;                                       
		INSERT INTO usersecurityagreement(aartuserid, assessmentprogramid, schoolyear, agreementelection, 
				agreementsigneddate, expiredate, signername)
		VALUES (CROW.teacherid, (select id from assessmentprogram where programname='Kansas Assessment Program' limit 1), 
				2014, false, now(), '8/10/2020', 'SYSTEM');
        RAISE NOTICE  'CREATED SAgreement %', CROW.teacherid;
    END LOOP;
END;
$BODY$;