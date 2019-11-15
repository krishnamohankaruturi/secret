--/dml/757.sql
----dml/*.sql ==> For ddl/*.sql
--US18760: UI Control to Set Academic Year Dates by State

DELETE FROM groupauthorities where authorityid = (select id FROM authorities where authority = 'PERM_ORG_EDIT');
DELETE FROM authorities where authority = 'PERM_ORG_EDIT';

DELETE FROM groupauthorities where authorityid = (select id FROM authorities where authority = 'PERM_ANNUAL_RESET');
DELETE FROM authorities where authority = 'PERM_ANNUAL_RESET';

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_ANNUAL_RESET','Annual Resets','Administrative-General', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

-- Script from scriptbees
update surveylabel set labeltype = (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional'),
	complexityband = false
where labelnumber like  'Q212\_%';

-- Update label type for radio button questions.
update surveylabel set labeltype= (select id from category where categorytypeid= (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='radiobutton') 
where labelnumber in ('Q501', 'Q502', 'Q503');

update surveylabel set complexityband = false where labelnumber in ('Q501', 'Q502', 'Q503');

update surveysection  set surveysectiondescription  = 'Language<br/>Section' where surveysectioncode='LANGUAGE';


--US18657: FCS2017: Add Science Comp Band Calculations
update surveylabel set complexityband = true where labelnumber in ('Q212_1', 'Q212_2', 'Q212_5');

update surveylabel set complexityband = false where labelnumber in ('Q212_3','Q212_4','Q212_6', 'Q212_7', 'Q212_8');

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
      values('SCI', 'SCI', 'SCI', 53, 'AART_ORIG_CODE', now(), 12, now(), 12);-- 

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'FOUNDATIONAL' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":160,"responseid":489}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":160,"responseid":490}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":160,"responseid":491}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_1' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":160,"responseid":492}]}',
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));  

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_2' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":161,"responseid":495}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));     
     
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_2' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":161,"responseid":496}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_3' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":164,"responseid":507}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values 
    ((SELECT id FROM category WHERE categorycode = 'BAND_3' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND')), 
     '{"rule":[{"label":164,"responseid":508}]}', 
     (SELECT id FROM category WHERE categorycode = 'SCI' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'COMPLEXITY_BAND_TYPE')));
     