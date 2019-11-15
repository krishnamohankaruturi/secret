--dml/560.sql script from scriptbees team
update userassessmentprogram set isdefault = false;

-- Update AP as KAP for Kansas state
update orgassessmentprogram set isdefault = true where organizationid =
(select id from organization where displayidentifier = 'KS') and assessmentprogramid =
(select id from assessmentprogram where abbreviatedname ='KAP');
 
update orgassessmentprogram set isdefault = false where organizationid =
(select id from organization where displayidentifier = 'KS') and assessmentprogramid in
(select id from assessmentprogram where abbreviatedname !='KAP');
 
-- Update AP as DLM for other than Kansas States
update orgassessmentprogram set isdefault = true where organizationid in
(select id from organization where displayidentifier != 'KS') and assessmentprogramid in
(select id from assessmentprogram where abbreviatedname ='DLM');
 
update orgassessmentprogram set isdefault = false where organizationid in
(select id from organization where displayidentifier != 'KS') and assessmentprogramid in
(select id from assessmentprogram where abbreviatedname !='DLM');

update userassessmentprogram set createduser = (select id from aartuser where username='cetesysadmin'),
modifieduser = (select id from aartuser where username='cetesysadmin'),
createddate = now(),
modifieddate = now();
