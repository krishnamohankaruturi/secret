-- DDL statements from Changepond - F61

--New column in organization
ALTER TABLE organization ADD COlUMN ismerged Boolean DEFAULT false;

--Adding is merged flag
CREATE OR REPLACE FUNCTION organization_children_active_or_inactive(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, contractingorganization boolean, schoolstartdate timestamp with time zone, schoolenddate timestamp with time zone, expirepasswords boolean, expirationdatetype bigint) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
        SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT o.id, o.organizationname, o.displayidentifier, o.organizationtypeid, o.welcomemessage, o.contractingorganization, o.schoolstartdate, o.schoolenddate, o.expirepasswords,  o.expirationdatetype
          from organization o where o.ismerged is false and o.id in (Select organizationid FROM organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


--Adding is merged flag

 CREATE OR REPLACE FUNCTION organization_parent_active_or_inactive(
    IN childid bigint,
    OUT id bigint,
    OUT organizationname character varying,
    OUT displayidentifier character varying,
    OUT organizationtypeid bigint,
    OUT welcomemessage character varying,
    OUT buildinguniqueness bigint,
    OUT schoolstartdate timestamp with time zone,
    OUT schoolenddate timestamp with time zone,
    OUT contractingorganization boolean,
    OUT expirepasswords boolean,
    OUT expirationdatetype bigint)
  RETURNS SETOF record AS
$BODY$
 
WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation AS parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage, buildinguniqueness, schoolstartdate, schoolenddate, contractingorganization, expirepasswords, expirationdatetype
        FROM organization WHERE  ismerged is false and id IN (SELECT parentorganizationid FROM organization_relation);
 
$BODY$
  LANGUAGE sql VOLATILE
   COST 100
  ROWS 1000;

DROP FUNCTION  IF EXISTS update_studentreport_school_district_name(text, bigint);

CREATE OR REPLACE FUNCTION update_studentreport_school_district_name(in_assessmentprogram text, in_schoolyear bigint)
  RETURNS void AS
$BODY$
DECLARE
               
               studentreport_record RECORD;
BEGIN
    
    RAISE NOTICE 'Started updating school and districtname on student reports for AssessmentProgram: % and SchoolYear: %', in_assessmentprogram, in_schoolyear;

    FOR studentreport_record IN (SELECT DISTINCT sr.attendanceschoolid, sr.districtid, sr.stateid, otd.schoolname, otd.districtname, sr.assessmentprogramid 
               FROM studentreport sr
               JOIN organizationtreedetail otd ON otd.schoolid = sr.attendanceschoolid AND otd.districtid = sr.districtid AND otd.stateid = sr.stateid 
               WHERE sr.schoolyear = in_schoolyear 
               AND sr.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = in_assessmentprogram AND activeflag is true) 
               AND sr.schoolname is null
               ORDER BY sr.districtid, sr.attendanceschoolid ) 
    LOOP
               UPDATE studentreport
                              SET schoolname = TRIM(studentreport_record.schoolname),
                                  districtname = TRIM(studentreport_record.districtname)
               WHERE attendanceschoolid = studentreport_record.attendanceschoolid
               AND districtid = studentreport_record.districtid
               AND stateid = studentreport_record.stateid
               AND schoolyear = in_schoolyear
               AND assessmentprogramid = studentreport_record.assessmentprogramid;               
               
    END LOOP; 
    RAISE NOTICE 'Update complete for AssessmentProgram: % and SchoolYear: %', in_assessmentprogram, in_schoolyear;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

DROP FUNCTION IF EXISTS update_studentreport_missing_school_district_name(in_assessmentprogram text, in_schoolyear bigint);

CREATE OR REPLACE FUNCTION update_studentreport_missing_school_district_name(in_assessmentprogram text, in_schoolyear bigint)
  RETURNS void AS
$BODY$
DECLARE
	
	studentreport_record RECORD;
BEGIN
    
    RAISE NOTICE 'Started updating school and districtname on student reports for AssessmentProgram: % and SchoolYear: %', in_assessmentprogram, in_schoolyear;

    FOR studentreport_record IN (SELECT DISTINCT sr.attendanceschoolid, org.organizationname, sr.assessmentprogramid 
	FROM studentreport sr
	JOIN organization org ON org.id = sr.attendanceschoolid	AND org.organizationtypeid in(select id from organizationtype where typecode = 'SCH')
	WHERE sr.schoolyear = in_schoolyear 
	AND sr.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = in_assessmentprogram AND activeflag is true) 
	AND sr.schoolname is null
	ORDER BY sr.attendanceschoolid ) 

    LOOP
	UPDATE studentreport
		SET schoolname = TRIM(studentreport_record.organizationname)
	WHERE attendanceschoolid = studentreport_record.attendanceschoolid
	AND schoolyear = in_schoolyear
	AND assessmentprogramid = studentreport_record.assessmentprogramid AND schoolname is null;	
	
    END LOOP; 
    FOR studentreport_record IN (SELECT DISTINCT sr.districtid, org.organizationname, sr.assessmentprogramid 
	FROM studentreport sr
	JOIN organization org ON org.id = sr.districtid	AND organizationtypeid in(select id from organizationtype where typecode = 'DT')
	WHERE sr.schoolyear = in_schoolyear 
	AND sr.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = in_assessmentprogram AND activeflag is true) 
	AND sr.districtname is null
	ORDER BY sr.districtid ) 

    LOOP
	UPDATE studentreport
		SET districtname = TRIM(studentreport_record.organizationname)
	WHERE districtid = studentreport_record.districtid
	AND schoolyear = in_schoolyear
	AND assessmentprogramid = studentreport_record.assessmentprogramid AND districtname is null;	
	
    END LOOP; 
    RAISE NOTICE 'Update complete for AssessmentProgram: % and SchoolYear: %', in_assessmentprogram, in_schoolyear;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  