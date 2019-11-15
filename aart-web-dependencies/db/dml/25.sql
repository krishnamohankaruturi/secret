
--US12404 Name: ENHANCEMENT: First Contact Survey - Updating some of the questions 

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-Teacher-directed instructional activities'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_1';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-Peer-directed instructional activities'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_2';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-Self-selected instructional activities'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_3';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-Computer-directed instructional activities'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_4';
