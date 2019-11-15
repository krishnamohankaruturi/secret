--dml/479.sql
--Business Management and Administration
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='BM&A' and activeflag is true), '32');
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='BM&A' and activeflag is true), '33');

--Agriculture, Food and Natural Resources
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='AgF&NR' and activeflag is true), '37');

--Architecture & Construction
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='Arch&Const' and activeflag is true), '38');

--Manufacturing
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='Mfg' and activeflag is true), '39');

--modify fieldspecification with allowable subject codes
update fieldspecification set allowablevalues ='{01,02,03,04,12,13,17,18,32,33,37,38,39,51,52,53,54,81,82,83,84}', fieldlength=2 where fieldname ='tascStateSubjectAreaCode';