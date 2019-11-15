

--US13094 and US13096
delete from complexitybandrules;

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q511.label || ',' ||q52.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Does not read any words when presented in print or Braille (not including environmental signs or logos)') as q52,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q51_1') and responsevalue = '0% (student does not exhibit this skill)') as q511
where q52.id = q511.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q511.label || ',' || q52.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Does not read any words when presented in print or Braille (not including environmental signs or logos)') as q52,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q51_1') and responsevalue = 'None to 20% of the time') as q511
where q52.id = q511.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q511.label || ',' || q52.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Does not read any words when presented in print or Braille (not including environmental signs or logos)') as q52,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q51_1') and responsevalue = '21% to 50% of the time') as q511
where q52.id = q511.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q511.label || ',' || q52.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Does not read any words when presented in print or Braille (not including environmental signs or logos)') as q52,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q51_1') and responsevalue = '51% to 80% of the time') as q511
where q52.id = q511.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q511.label || ',' || q52.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Does not read any words when presented in print or Braille (not including environmental signs or logos)') as q52,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q51_1') and responsevalue = 'More than 80% of the time') as q511
where q52.id = q511.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q52.label) as labels from 
(select ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Reads only a few words or up to pre-primer level') as q52
) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q52.label) as labels from 
(select ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Primer to first grade level') as q52
) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q52.label) as labels from 
(select ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Above first grade level to second grade level') as q52
) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q52.label) as labels from 
(select ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Above second grade level to third grade level') as q52
) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q52.label) as labels from 
(select ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q52') and responsevalue = 'Above third grade level') as q52
) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='ELA')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q37.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'Yes') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q37') and responsevalue = 'Regularly combines 3 or more spoken words according to grammatical rules to accomplish a variety of communicative purposes (e.g., sharing complex information, asking/answering longer questions, giving directions to another person)') as q37
where q36.id = q37.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q37.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'Yes') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q37') and responsevalue = 'Usually uses 2 spoken words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering questions, and commenting)') as q37
where q36.id = q37.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q37.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'Yes') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q37') and responsevalue = 'Usually uses only 1 spoken word at a time to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting, and labeling)') as q37
where q36.id = q37.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q40.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'Yes') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q40') and responsevalue = 'Regularly combines 3 or more signed words according to grammatical rules to accomplish a variety of communicative purposes (e.g., sharing complex information, asking/answering longer questions, giving directions to another person)') as q40
where q36.id = q39.id and q39.id = q40.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q40.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'Yes') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q40') and responsevalue = 'Usually uses 2 signed words at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering brief questions, and commenting)') as q40
where q36.id = q39.id and q39.id = q40.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q40.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'Yes') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q40') and responsevalue = 'Usually uses only 1 signed word at a time to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting, and labeling)') as q40
where q36.id = q39.id and q39.id = q40.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q43.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'No') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q43') and responsevalue = 'No') as q43
where q36.id = q39.id and q39.id = q43.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q43.label || ',' || q44.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'No') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q43') and responsevalue = 'Yes') as q43,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q44') and responsevalue = 'Regularly combines 3 or more symbols according to grammatical rules to accomplish the 4 major communicative purposes (e.g., expressing needs and wants, developing social closeness, exchanging information, and fulfilling social etiquette routines)') as q44
where q36.id = q39.id and q39.id = q43.id and q43.id = q44.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q43.label || ',' || q44.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'No') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q43') and responsevalue = 'Yes') as q43,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q44') and responsevalue = 'Usually uses 2 symbols at a time to meet a variety of more complex communicative purposes (e.g., obtaining things including absent objects, social expressions beyond greetings, sharing information, directing another person''s attention, asking/answering brief questions, commenting)') as q44
where q36.id = q39.id and q39.id = q43.id and q43.id = q44.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q36.label || ',' || q39.label || ',' || q43.label || ',' || q44.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q36') and responsevalue = 'No') as q36,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q39') and responsevalue = 'No') as q39,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q43') and responsevalue = 'Yes') as q43,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q44') and responsevalue = 'Usually uses only 1 symbol to meet a limited number of simple communicative purposes (e.g., refusing/rejecting things, making choices, requesting attention, greeting)') as q44
where q36.id = q39.id and q39.id = q43.id and q43.id = q44.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='COMMUNICATION')
);
