
update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Symbols offered in groups of 1 or 2'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_1';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Low-tech communication board(s) with 8 or fewer symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_2';
                
update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Low-tech communication board(s) with 9 or more symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_3';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Low-tech communication book with multiple pages each containing 8 or fewer symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_4';  

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Low-tech communication book with multiple pages each containing 9 or more symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_5';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Eye gaze board (eye gaze communication) with 4 or fewer symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_6';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Eye gaze board (eye gaze communication) with 5 or more symbols'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_7';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Simple voice output device (e.g., BIGmack, Step by Step, Cheap Talk, Voice-in-a-Box, Talking Picture Frame) with 9 or fewer messages or multiple messages in sequence'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_8';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Simple voice output device with 10 to 40 messages'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_9';
                                                                
update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Voice output device with levels (e.g., 6 level Voice-in-a-box, Macaw, Digivox, DAC)'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_10';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Voice output device or computer/tablet with dynamic display software (e.g., DynaVox, Mytobii, Proloquo2Go, Speaking Dynamically Pro, Vantage)'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_11';

update SurveyLabel
                set label = 'Augmentative or alternative communication: Mark all that apply-Voice output device with icon sequencing (e.g., ECO, ECO2, Springboard Lite, Vanguard)'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q45_12';
                
update surveyresponse
                set responseorder = '10'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responseorder = '1';


update surveyresponse
                set responseorder = '1'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responseorder = '3';


update surveyresponse
                set responseorder = '3'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responseorder = '2';


update surveyresponse
                set responseorder = '2'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responseorder = '10';


update surveyresponse
                set responselabel = '10'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responselabel = '1';


update surveyresponse
                set responselabel = '1'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responselabel = '3';


update surveyresponse
                set responselabel = '3'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responselabel = '2';


update surveyresponse
                set responselabel = '2'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
                where labelid=(select id from surveylabel where labelnumber ='Q153') and 
                responselabel = '10';                

                
