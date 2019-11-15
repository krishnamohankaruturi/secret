----FCS Survey status reset/responses clear Hardening release end
--Include this final script as well to update the Survey Status and Page Status for Required Questions.
------------------------------------------------------------------------------------------------------

--page status from COMPLETE to IN_PROGRESS
update survey set  modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') ,status = (select id from category 
    where categorycode='IN_PROGRESS' and categorytypeid = (select id from categorytype where typecode='SURVEY_STATUS')) where status=(select id from category 
    where categorycode='COMPLETE' and categorytypeid = (select id from categorytype where typecode='SURVEY_STATUS'));

--page status to in_progress
update surveypagestatus set modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),iscompleted='false' where globalpagenum in (9,11,13,14);

--detele existing responses

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q49_1','Q49_2','Q49_3', 'Q49_4','Q49_5', 'Q49_6')));

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q143','Q147','Q310')));

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q45_1','Q45_2','Q45_3','Q45_4','Q45_5','Q45_6','Q45_7','Q45_8','Q45_9','Q45_10','Q45_11','Q45_12')));

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q51_1','Q51_2','Q51_3','Q51_4','Q51_5','Q51_6','Q51_7','Q51_8')));


UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q56_1','Q56_2','Q56_3','Q56_4','Q56_5','Q56_6','Q56_7','Q56_8')));


UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q54_1','Q54_2','Q54_3','Q54_4','Q54_5','Q54_6','Q54_7','Q54_8','Q54_9','Q54_10','Q54_11','Q54_12','Q54_13')));

--removing Dependent responses

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q33_1','Q33_2','Q33_3','Q33_4','Q33_5','Q33_6','Q33_7','Q33_8','Q33_9','Q33_10','Q33_11','Q201','Q202')));

--removing Not requied label responses which changed

UPDATE studentsurveyresponse SET ACTIVEFLAG='FALSE' WHERE surveyresponseid in 
    (SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q16_1','Q17','Q19','Q20_1','Q20_2','Q20_3','Q20_4','Q20_5','Q20_6','Q20_7','Q330','Q22','Q23_1','Q23_2','Q23_3','Q429_1','Q429_2','Q429_3','Q429_4','Q429_5','Q200','Q143','Q147','Q33_1','Q33_2','Q33_3','Q33_4','Q33_5','Q33_6','Q33_7','Q33_8','Q33_9','Q33_10','Q33_11','Q151_1','Q151_2','Q151_3','Q25_1','Q25_2','Q25_3','Q25_4','Q25_5','Q210','Q211','Q300','Q310','Q400')));


--inacctivate pages from 15
UPDATE SURVEYPAGESTATUS SET modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'), ACTIVEFLAG='FALSE' WHERE globalpagenum IN (15,16,17,18,19,20);
