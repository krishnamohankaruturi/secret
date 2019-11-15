--DE17656
update authorities set displayname = 'Create DLM/I-SMART First Contact Survey Extract', 
                       modifieddate = now(),
        modifieduser = (select id from aartuser where username='cetesysadmin')
 where authority = 'DATA_EXTRACTS_FCS_REPORT';
 
update authorities set displayname = 'Create I-SMART/2 Test Administration Monitoring Extract', 
                       labelname = 'I-SMART-Specific Extracts',
        modifieddate = now(),
        modifieduser = (select id from aartuser where username='cetesysadmin') 
 where authority = 'DATA_EXTRACTS_ISMART_TEST_ADMIN';
 
--DE17662
update activationemailtemplate set emailsubject = replace(emailsubject,'KITE','Kite');
update activationemailtemplate set emailbody = replace(emailbody,'KITE','Kite');
update activationemailtemplate set emailbody = replace(emailbody,'Kite.','Kite Educator Portal.') where emailbody ilike '%Kite.%';
