--dml/622.sql ==> For ddl/622.sql
--US19228: Ability to exclude permission from roles
--DE14866: Should not be able to select bundled report permission for teacher

SELECT * FROM groupauthoritiesexclusion;

DELETE FROM groupauthoritiesexclusion;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_REPORT'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_GNRL_STUDENT_RPT_BUNDLED'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_BUNDLED_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_CPASS_ASMNT_STUDENT_IND_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_MDPT_RESP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_ALL_STUDENT_REPORTS'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEA'), 
	(select id from authorities where authority ilike 'VIEW_KELPA_ELP_STUDENT_SCORE'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;







WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_REPORT'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_GNRL_STUDENT_RPT_BUNDLED'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_BUNDLED_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_CPASS_ASMNT_STUDENT_IND_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_MDPT_RESP'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_ALL_STUDENT_REPORTS'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('KAP', 'K-ELPA', 'DLM', 'CPASS')
 order by ap.abbreviatedname, org.displayidentifier)
INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 SELECT (select id from groups where groupcode = 'TEAR'), 
	(select id from authorities where authority ilike 'VIEW_KELPA_ELP_STUDENT_SCORE'), 
	 orgsToExcludeList.apid,orgsToExcludeList.orgid
 FROM orgsToExcludeList;




 
	
