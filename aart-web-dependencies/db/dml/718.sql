--dml/718.sql
INSERT INTO reportassessmentprogram(
             reporttypeid, assessmentprogramid, createdate, modifieddate, 
            readytoview, activeflag, stateid, subjectid, authorityid)
    VALUES ((select id from category where categorycode = 'ALT_ST_DCPS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), (select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true), now(), now(), 
            true, true, ( select id from organization where displayidentifier = 'DE' and organizationtypeid = (select id from organizationtype where typecode = 'ST') ), null, (select id from authorities where authority = 'VIEW_ALL_STUDENT_REPORTS'));