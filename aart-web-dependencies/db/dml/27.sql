--DE4285 Name: First Contact questions and answers not fully shown.

update surveyresponse set responsevalue = 'Usually uses 2 symbols at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering brief questions, commenting)'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q44') and responseorder = 2;


update SurveyLabel
                set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -C) Responds appropriately in any modality (speech, sign, gestures, facial expressions) when offered a favored item that is not present or visible (e.g., "Do you want some ice cream?")'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_3';

update SurveyLabel
                set label = 'If yes, please describe the health or personal care concerns that interfere with instruction or assessment for this student'
                ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q65_TEXT';
