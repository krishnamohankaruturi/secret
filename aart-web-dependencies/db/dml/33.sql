
--DE4194

update SurveyLabel
                set label = 'Choose the highest statement that describes the student''s expressive communication with speech'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q37';


update SurveyLabel
                set label = 'Does the student use speech to meet expressive communication needs?'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q36';
                

update SurveyLabel
                set label = 'Hearing:Select the types of corrective aid/assitance: Mark all that apply-No Hearing aid,cochlear implant,or other hearing assistance'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q132_1';

update SurveyLabel
                set label = 'Hearing:Select the types of corrective aid/assitance: Mark all that apply-Uses unilateral hearing aid'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q132_2';

                
update SurveyLabel
                set label = 'Hearing:Select the types of corrective aid/assitance: Mark all that apply-Uses bilateral hearing aid'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q132_3';

update SurveyLabel
                set label = 'Hearing:Select the types of corrective aid/assitance: Mark all that apply-Has cochlear implant'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q132_4';                

                


                

                