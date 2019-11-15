
--DE5170
INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('hispanicEthnicity',  'Hispanic_Ethnicity'));