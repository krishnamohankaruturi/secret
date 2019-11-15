-- 580.sql
-- DE13975:FCS - Science - Final band is not getting calculated
DELETE FROM complexitybandrules WHERE  complexitybandtypeid = (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE'));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'FOUNDATIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_1') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_1') and responseorder = 1) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_1') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_1') and responseorder = 2) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_1') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_1') and responseorder = 3) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_1') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_1') and responseorder = 4) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_2' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_2') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_2') and responseorder = 3) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));     

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_2' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_2') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_2') and responseorder = 4) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));     

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_3' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_5') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_5') and responseorder = 3) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));           

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_3' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q212_5') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q212_5') and responseorder = 4) ||'}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

--changes from scriptbees to correct FCS alignment issue
update surveysection set surveysectiondescription='<br/>Language<br/>',
modifieduser = (Select id from aartuser where username='cetesysadmin'),
modifieddate = now()
where surveysectioncode = 'LANGUAGE';