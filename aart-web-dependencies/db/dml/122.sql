

--Below contents are moved here from dml/126.sql, dml/147.sql, dml/150.sql, dml/156.sql due to Prod tag changes



--US13916 Complexity Bands Auto Enrollment
update category set categoryname='0' where categorycode='FOUNDATIONAL';
update category set categoryname='1' where categorycode='BAND_1';
update category set categoryname='2' where categorycode='BAND_2';
update category set categoryname='3' where categorycode='BAND_3';

--US14261

 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='IP' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 0 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='IDP' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 0.5 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='DP' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 1 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='DPP' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 1.5 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='PP' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 2 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='PPT' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 2.5 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='TA' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 3 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='TS' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 3.5 );
 insert into essentialelementlinkagetranslationvalues  (categoryid, translationvalue)
values ((select id from category where categorycode='S' and categorytypeid = (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE')), 4 );

--US13474
--(H5 or H4) + G5 = Band3
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q547.label || ',' ||q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547
where q547.id = q548.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q547.label || ',' ||q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547
where q547.id = q548.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H5 or H4) + G4 + E5 = Band3
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + (G4 or G5) + E5 = Band3
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_3'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H5 or H4) + G4 + (E4 or E3 or E2 or E1) = Band2

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + (G4 or G5) + (E4 or E3 or E2 or E1) = Band2
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '51% to 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'More than 80% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H5 or H4) + (G3 or G2 or G1) + (E5 or E4) = Band2

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + G3 + (E5 or E4) = Band2
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
--(H1 or H2 or H3) + (G1 or G2) + (E5 or E4) = Band2 229

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_2'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q545.label || ',' ||q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545
where q547.id = q548.id and q545.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + G3 + (E1 or E2 or E3) + C5 = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H4 or H5) + (G1 or G2 or G3) + (E4 or E5) + C5 = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '51% to 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'More than 80% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
--(H1 or H2 or H3) + (G1 or G2) + E3 + C5 = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
--(H4 or H5) + (G1 or G2 or G3) + (E1 or E2 or E3) + (C1 or C2 or C3 or C4) = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '51% to 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'More than 80% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + G3 + (E1 or E2 or E3) + (C1 or C2 or C3 or C4) = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '21% to 50% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + (G1 or G2) + E3 + (C1 or C2 or C3 or C4) = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '21% to 50% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);

--(H1 or H2 or H3) + (G1 or G2) + (E1 or E2) + (C3 or C4 or C5) = BAND_1
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '21% to 50% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '51% to 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='BAND_1'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'More than 80% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
--(H1 or H2 or H3) + (G1 or G2) + (E1 or E2) + (C1 or C2) = FOUNDATIONAL
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '0% (student does not exhibit this skill)') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = 'None to 20% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = '0% (student does not exhibit this skill)') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = '0% (student does not exhibit this skill)') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = '0% (student does not exhibit this skill)') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);
insert into complexitybandrules (complexitybandid, rule, complexitybandtypeid) values (
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND') and categorycode='FOUNDATIONAL'),
(select 
('{"rule":[' || rule_table.labels || ']}') as rule from 
(select (q543.label || ',' || q545.label || ',' || q547.label || ',' || q548.label) as labels from 
(select 1 as id , ('{"label":'|| labelid ||',"responseid":'|| id || '}') as label   from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_8') and responsevalue = '21% to 50% of the time') as q548,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_7') and responsevalue = 'None to 20% of the time') as q547,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_5') and responsevalue = 'None to 20% of the time') as q545,
(select 1 as id, ('{"label":'|| labelid ||',"responseid":' || id || '}') as label from surveyresponse where labelid = (select id as labelid from surveylabel where labelnumber = 'Q54_3') and responsevalue = 'None to 20% of the time') as q543
where q547.id = q548.id and q545.id = q547.id and q543.id = q547.id) rule_table),
(select id from category where categorytypeid = (select id from categorytype where typecode='COMPLEXITY_BAND_TYPE') and categorycode='MATH')
);       


--US14157 First Contact: Proposed Changes for Computer Access Switches and Modify Q60 and 65 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_1'), 4,'0', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 0);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_2'), 4,'0', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 0);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_3'), 4,'0', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 0);	

	 
	 
update surveylabel set label='General level of understanding instruction: Choose the highest one that applies <font size = "5" color = "red" >*</font>' where labelnumber='Q60';



update SurveyLabel
	set label = ''
        ,modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') where labelnumber='Q65_TEXT';
        
UPDATE aartuser SET displayname =  firstname || ' ' || surname;        