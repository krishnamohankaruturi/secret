-- First Contact changes.

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q3'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),'TEXT');


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_1'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_2'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_3'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_4'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_5'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_6'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_7'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_8'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_9'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_10'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_11'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q45_12'), 1,'TRUE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q58_1'), 1,'Rank 1', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q58_2'), 1,'Rank 2', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q58_3'), 1,'Rank 3', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q58_4'), 1,'Rank 4', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),1);

UPDATE  surveylabel SET labelnumber = labelnumber ||'_TEXT' WHERE labelnumber IN ('Q63','Q65','Q154');
	 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate,
 createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q63_TEXT'), 1,'',
 	  now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),'TEXT');
	 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q65_TEXT'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),'TEXT');


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q154_TEXT'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'),'TEXT');

--4 missing questions.

insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select sr.labelid, 2 as responseorder,'Rank 2' as responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
'2' as responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1');

insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select sr.labelid, 3 as responseorder,'Rank 3' as responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
'3' as responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1' limit 1);

insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select sr.labelid, 4 as responseorder,'Rank 4' as responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
'4' as responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1' limit 1);


 insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select (Select id from surveylabel where labelnumber='Q58_2') as labelid,
 sr.responseorder,sr.responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
sr.responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1'  and sr.responseorder in (2,3,4));

 insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select (Select id from surveylabel where labelnumber='Q58_3') as labelid,
 sr.responseorder,sr.responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
sr.responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1'  and sr.responseorder in (2,3,4));

 insert into surveyresponse(labelid,responseorder,responsevalue,createduser,activeflag,modifieduser,responselabel)
(Select (Select id from surveylabel where labelnumber='Q58_4') as labelid,
 sr.responseorder,sr.responsevalue,sr.createduser,sr.activeflag,sr.modifieduser,
sr.responselabel
 from surveyresponse sr,surveylabel sl where sr.labelid=sl.id and sl.labelnumber = 'Q58_1'  and sr.responseorder in (2,3,4));

---DE3914 remove validations on fieldspecs out of scope.

delete from fieldspecificationsrecordtypes 
where fieldspecificationid in (
Select id from fieldspecification where 
mappedname like 'Q%' and mappedname not in ('Q3',
'Q13_1','Q36','Q37','Q39','Q40','Q41','Q43','Q44',
'Q47','Q49_1','Q49_2','Q49_3','Q49_4','Q49_5',
'Q49_6','Q51_1','Q51_2','Q51_3','Q51_4',
'Q51_5','Q51_6','Q51_7','Q51_8','Q52',
'Q54_1','Q54_2','Q54_3','Q54_4','Q54_5',
'Q54_6','Q54_7','Q54_8','Q54_9','Q54_10',
'Q54_11','Q54_12','Q54_13','Q56_1','Q56_2',
'Q56_3','Q56_4','Q56_5','Q56_6','Q56_7',
'Q56_8','Q142','Q146','Q60','Q62','Q64','Q62_TEXT',
'Q45_1','Q45_2','Q45_3','Q45_4','Q45_5','Q45_6',
'Q45_7','Q45_8','Q45_9','Q45_10','Q45_11','Q45_12',
'Q58_1','Q58_2','Q58_3','Q58_4',
'Q63_TEXT','Q65_TEXT','Q154_TEXT')
);
 
delete from fieldspecification where 
mappedname like 'Q%' and mappedname not in ('Q3',
'Q13_1','Q36','Q37','Q39','Q40','Q41','Q43','Q44',
'Q47','Q49_1','Q49_2','Q49_3','Q49_4','Q49_5',
'Q49_6','Q51_1','Q51_2','Q51_3','Q51_4',
'Q51_5','Q51_6','Q51_7','Q51_8','Q52',
'Q54_1','Q54_2','Q54_3','Q54_4','Q54_5',
'Q54_6','Q54_7','Q54_8','Q54_9','Q54_10',
'Q54_11','Q54_12','Q54_13','Q56_1','Q56_2',
'Q56_3','Q56_4','Q56_5','Q56_6','Q56_7',
'Q56_8','Q142','Q146','Q60','Q62','Q64','Q62_TEXT',
'Q45_1','Q45_2','Q45_3','Q45_4','Q45_5','Q45_6',
'Q45_7','Q45_8','Q45_9','Q45_10','Q45_11','Q45_12',
'Q58_1','Q58_2','Q58_3','Q58_4',
'Q63_TEXT','Q65_TEXT','Q154_TEXT');

--DE3891 Defect fix for CB

update tasklayout set layoutcode='horizontal',
modifieddate=now() where layoutname='Horizontal' and originationcode='CB';


-- DE2740, DE2741 fix.

UPDATE fieldspecification SET fieldlength = 60 WHERE fieldname in ('educatorFirstName', 'educatorLastName');
