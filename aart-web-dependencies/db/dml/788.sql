--dml/788.sql

-- the following script is from the SP team
ALTER TABLE studentstests ADD COLUMN IF NOT EXISTS email1 text DEFAULT NULL;
ALTER TABLE studentstests ADD COLUMN IF NOT EXISTS email2 text DEFAULT NULL;
ALTER TABLE studentstests ADD COLUMN IF NOT EXISTS consentsurveyResponse text DEFAULT NULL;

-- SP Post Test Survey

DO $BODY$
declare
    sysadminid bigint := (select id from aartuser where username = 'cetesysadmin');
begin
    IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='post_test_consent_survey_html') THEN
         INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
         VALUES ('post_test_consent_survey_html','posttestconsentsurvey','consent_survey_html','<div id="consentSurvey" style="background:#fff;max-width:1500px;max-height:600px;overflow-x:auto;overflow-y:auto;padding:20px;box-shadow:0px 0px 1px 1px rgba(0,0,0,.2);font:verdana;font-size: 14px;margin:20px auto;"><p style="font:verdana;font-size: 14px;">Some industry employers and colleges and universities offer opportunities and information based on students&#39; completion of PLTW courses and performance on the End-of-Course (EoC) Assessment (this test). These opportunities can include internship preferences, hiring preferences, admissions preferences as well as the awarding course credit and special communication about opportunities within the industry or institution.<br/><br/>If you choose, PLTW will send additional information to you about these opportunities and how to share your information with PLTW Industry Partners and Higher Education Partners.  You may also learn about sharing your information <u>at any time</u> independent of your answer here in this test by visiting mypltw.org and choosing the Assessments tab.<br/><br/><b>Your response to this question will not affect your score or test experience in any way. </b> <br/>If you are unsure of which option to select, it is acceptable to ask your teacher or teacher for guidance. <br/><br/>Please select the appropriate option below. <br/><input type="checkbox" class="surveyCheckbox" name="consentSurveyChoice1" survey="PLTWPostTestSurvey" style="zoom:1.5;" /> A. I am under 13 and therefore may not answer.<br/><br/><input type="checkbox" class="surveyCheckbox" name="consentSurveyChoice2" survey="PLTWPostTestSurvey" style="zoom:1.5;"/> B. Yes, I agree to receive direct information from PLTW about career and higher education opportunities and the PLTW Student Score Sharing Portal electronically at my personal email address, which is: <input type="email" id="consentSurveyEmail1" style="border-color: black" size="40"></input>. I confirm that <b><u>my parent(s)/guardian(s) permit me to receive school information through this email address.</u></b><br/><br/><input type="checkbox" class="surveyCheckbox" name="consentSurveyChoice3" survey="PLTWPostTestSurvey" style="zoom:1.5;"/> C. Yes, I agree to receive direct information from PLTW about career and higher education opportunities and the PLTW Student Score Sharing Portal electronically at my personal email address, which is: <input type="email" id="consentSurveyEmail2" style="border-color: black" size="40"></input>. Further, by checking this box, I represent that <b><u>I am at least 18.</u></b><br/><br/><input type="checkbox" class="surveyCheckbox" name="consentSurveyChoice4" survey="PLTWPostTestSurvey" style="zoom:1.5;"/> D. I do not wish to answer at this time.<br/><br/><input type="checkbox" class="surveyCheckbox" name="consentSurveyChoice5" survey="PLTWPostTestSurvey" style="zoom:1.5;"/> E. No, I do not want to receive direct information from PLTW about career and higher education opportunities and the PLTW Student Score Sharing Portal.<br/></p></div>',sysadminid,now(),sysadminid,now());
    END IF;
    
    IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='consent_survey_email_warn') THEN
         INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
         VALUES ('consent_survey_email_warn','posttestconsentsurvey','email_warn','Please enter a valid email before submitting.',sysadminid,now(),sysadminid,now());
    END IF;

    IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='consent_survey_option_warn') THEN
         INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
         VALUES ('consent_survey_option_warn','posttestconsentsurvey','option_warn','Please select at least one of the available options before submitting.',sysadminid,now(),sysadminid,now());
    END IF;
end; $BODY$;

------------------end script from SP