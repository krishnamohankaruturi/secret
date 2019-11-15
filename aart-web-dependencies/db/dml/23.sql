
--update abbreviated name for the ones that don't have one.

update gradecourse
 set
  abbreviatedname='A.N'||name ,
  modifieddate=now(),
  modifieduser=(Select id from aartuser where username='cetesysadmin')
  where abbreviatedname is null;

--update origination code to AART for the ones that don't have one.
  
update gradecourse
 set
  originationcode='AART_ORIG_CODE' ,
  modifieddate=now(),
  modifieduser=(Select id from aartuser where username='cetesysadmin')
  where originationcode is null;  

--update grade level for the ones that don't have gradelevel.

update gradecourse
 set
  gradelevel=name::int ,
  modifieddate=now(),
  modifieduser=(Select id from aartuser where username='cetesysadmin')
  where
   name ~  '^[0-9]+$' and
   gradelevel is null;

--US11618 Operational Test Window changes

insert into category(categoryname,categorycode,categorydescription,
categorytypeid,
originationcode,createduser,activeflag,modifieduser)
values
(
'Required To Complete Test','REQUIRED_TO_COMPLETE_TEST',
'The students are required to complete the test',
(Select id from categorytype where typecode='SESSION_RULES'),
'AART_ORIG_CODE',
(Select id from aartuser where username='cetesysadmin'),
true,
(Select id from aartuser where username='cetesysadmin')
);

insert into category(categoryname,categorycode,categorydescription,
categorytypeid,
originationcode,createduser,activeflag,modifieduser)
values
(
'Not Required To Complete Test','NOT_REQUIRED_TO_COMPLETE_TEST',
'The students are not required to complete the test',
(Select id from categorytype where typecode='SESSION_RULES'),
'AART_ORIG_CODE',
(Select id from aartuser where username='cetesysadmin'),
true,
(Select id from aartuser where username='cetesysadmin')
);


--US11178  Name: First Contact survey: communication repsonses view 

update surveylabel set sectionid = (select id from category where categorycode = 'AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION') where sectionid = (select id from category where categorycode = 'COMMUNICATION');

delete from category where categorycode = 'COMMUNICATION';

--For publishing executed in QA manually.

ALTER TABLE stimulusvariant ADD COLUMN srcack text;
ALTER TABLE testlet ALTER COLUMN questionviewid DROP NOT NULL;
ALTER TABLE testlet ALTER COLUMN questionlocked DROP NOT NULL;
ALTER TABLE testlet ALTER COLUMN displayviewid DROP NOT NULL;
 
ALTER TABLE testletstimulusvariants
  DROP CONSTRAINT testletstimulusvariants_pkey;
  
--CB publishing changes.R7-I2
--commented because of build failure.
-- looks like this was executed in QA manually. So checking in 23.sql so that it will not execute.
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Paginated', 'paginated', 'Paginated',
(select id from categorytype where typecode='TESTLET_LAYOUT'), 'CB', now(),
(select id from aartuser where username='cetesysadmin'), now(),
(select id from aartuser where username='cetesysadmin'));