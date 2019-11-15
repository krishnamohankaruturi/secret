
--DE4285 Name: First Contact questions and answers not fully shown.

update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -A) Can point to, look at, or touch things in the immediate vicinity when asked (e.g., pictures, objects, body parts)'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_1';

update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -B) Can perform simple actions, movements or activities when asked (e.g., comes to teacher''s location, gives an object to teacher or peer, locates or retrieves an object)'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_2';


update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -C) Responds appropriately in any modality (speech, sign, gestures, facial expressions) when offered a favored item that is not present or visible (e.g., “Do you want some ice cream?”)'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_3';

update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -D) Responds appropriately in any modality (speech, sign, gestures, facial expressions) to single words that are spoken or signed'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_4';

update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -E) Responds appropriately in any modality (speech, sign, gestures, facial expressions) to phrases and sentences that are spoken or signed'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_5';

update SurveyLabel
	set label = 'Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. -F) Follows 2-step directions presented verbally or through sign (e.g., gets a worksheet or journal and begins to work, distributes items needed by peers for a lesson or activity, looks at requested or desired item and then looks at location where it should go)'
	,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q49_6';

update SurveyLabel set label = 'General level of understanding instruction: Choose the highest one that applies'
	,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q60';

update surveyresponse set responsevalue = 'Usually uses 2 spoken words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering questions, and commenting)'
	,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q37') and responseorder = 2;


update surveyresponse set responsevalue = 'Usually uses 2 signed words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering brief questions, and commenting)'
	,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q40') and responseorder = 2;


update surveyresponse set responsevalue = 'Usually uses 2 signed words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering brief questions, and commenting)'
	,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q44') and responseorder = 2;


--DE4091 Name: Error found in TC86998: US12057_124_SpokenSourcePreference 
	
update fieldspecification set fieldname = 'SpokenSourcePreference', mappedname = 'SpokenSourcePreference' where fieldname = 'SpokenSourcePreference ' and mappedname = 'SpokenSourcePreference ';

update profileitemattribute set attributename = 'SpokenSourcePreference' where attributename = 'SpokenSourcePreference ';


--DE4103 Name: Error found in TC87213: US12057_339_tactileFile 

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES ('tactileFile', NULL, NULL, NULL, 30, false, false, NULL, 'tactileFile', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('tactileFile',  'tactileFile'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES ('tactileFile', now(), (select id from aartuser where username='cetesysadmin'), true, now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) VALUES ((select id from profileitemattribute where attributename ='tactileFile'), (select id from profileitemattributecontainer where attributecontainer = 'Tactile'),now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipDisplay') );

