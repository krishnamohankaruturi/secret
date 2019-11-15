--224.sql

--complexity band rule fixes for missing 
/*
 H4G1E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 151 
Then Band 1

 */
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
/*
H4G2E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H4G3E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 151 
Then Band 1
*/

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
/*
H4G1E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H4G1E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);


/*
H4G2E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H4G2E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H4G3E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 151 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H4G3E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 151 Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G1E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G2E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G3E1C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 133 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G1E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G1E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 143 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G2E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G2E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 144 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G3E2C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 134 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

/*
H5G3E3C5
When   Question id = 41 and response id = 127 AND
Question id = 43 and response id = 135 AND
Question id = 45 and response id = 145 AND
Question id = 46 and response id = 152 
Then Band 1
*/
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
