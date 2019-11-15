-- F510: F510 Student Tracker Enhancements
INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid,  originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('EMERGENT', 'EMERGENT', 'Writing: emergent', (select id from categorytype where typecode = 'COMPLEXITY_BAND'), 'AART_ORIG_CODE', 
            now(), (select id from aartuser where username ='cetesysadmin'), true, now(),(select id from aartuser where username ='cetesysadmin'));


INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid,  originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('CONVENTIONAL', 'CONVENTIONAL', 'Writing: conventionalband', (select id from categorytype where typecode = 'COMPLEXITY_BAND'), 'AART_ORIG_CODE', 
            now(), (select id from aartuser where username ='cetesysadmin'), true, now(),(select id from aartuser where username ='cetesysadmin'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid,  originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Writing', 'WRITING', 'Writing', (select id from categorytype where typecode = 'COMPLEXITY_BAND_TYPE'), 'AART_ORIG_CODE', 
            now(), (select id from aartuser where username ='cetesysadmin'), true, now(),(select id from aartuser where username ='cetesysadmin'));

INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange)
    VALUES ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')),
    'EMERGENT', '0', 0, 1.7999);

INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange)
    VALUES ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')),
    'EMERGENT', '1', 0, 1.7999);

INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange)
    VALUES ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
    'CONVENTIONAL', '2', 1.8, 4);

INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange)
    VALUES ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
    'CONVENTIONAL', '3', 1.8, 4);

INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange)
    VALUES ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
    'CONVENTIONAL', '4', 1.8, 4);
    
update surveylabel set complexityband = true, modifieddate = now(), modifieduser = (select id from aartuser where username ='cetesysadmin') where labelnumber in ('Q500');


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 1) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 2) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'CONVENTIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 3) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 4) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 5) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 6) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));


insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'EMERGENT' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":' || (SELECT id from surveylabel where labelnumber = 'Q500') || ',"responseid":' 
     || (SELECT id FROM surveyresponse WHERE labelid = (SELECT id from surveylabel where labelnumber = 'Q500') and responseorder = 7) ||'}]}', 
     (SELECT id FROM category WHERE categorycode = 'WRITING' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));      
     
