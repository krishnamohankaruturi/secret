ALTER TABLE studentsassessments
	DROP CONSTRAINT if exists pk_student_assessment;

ALTER TABLE studentsassessments
	DROP CONSTRAINT if exists pk_students_assessments;	

ALTER TABLE assessment
	DROP COLUMN if exists testtypeid,
	DROP COLUMN if exists testsubjectid;

--INFO AART related table.	
	
ALTER TABLE contentarea
	ALTER COLUMN name SET NOT NULL;
	
ALTER TABLE studentsassessments 
ADD CONSTRAINT pk_students_assessments PRIMARY KEY (studentid, assessmentid, contentareaid);

ALTER TABLE category DROP CONSTRAINT if exists category_categorycode_originationcode_uk;

ALTER TABLE category
	ADD CONSTRAINT category_categorycode_uk UNIQUE (categorycode, categorytypeid);

ALTER TABLE category
	ADD CONSTRAINT category_categoryname_uk UNIQUE (categoryname, categorytypeid);

ALTER TABLE categorytype 
DROP CONSTRAINT categorytype_typecode_originationcode_uk;

ALTER TABLE categorytype 
ADD CONSTRAINT categorytype_typecode_uk UNIQUE (typecode, originationcode);

update studentsassessments set contentareaid =
(select maxid from (select name,max(id) as maxid from contentarea
group by name having count(1) > 1) max_ca)
 where contentareaid in (select id from contentarea ca where
 ca.name in (select name from contentarea group by name having count(1) > 1));

delete from contentarea where name in
(select name from (select name,max(id) from contentarea group by name having count(1) > 1)
group_ca) and
id not in (select maxid from (select name,max(id) as maxid
from contentarea group by name having count(1) > 1) group_ca);

ALTER TABLE contentarea 
ADD CONSTRAINT uk_name UNIQUE (name);

--for roster changes.
	
ALTER TABLE roster DROP CONSTRAINT state_course_id;
ALTER TABLE roster DROP CONSTRAINT state_subject_area_fk;

--change the reference to content area for state course.
update roster set statecourseid = (select id from contentarea limit 1);
update roster set statesubjectareaid = (select id from contentarea limit 1);

ALTER TABLE roster ADD CONSTRAINT state_course_fk
FOREIGN KEY (statecourseid) REFERENCES contentarea (id)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE roster ADD CONSTRAINT state_subject_area_fk
FOREIGN KEY (statesubjectareaid) REFERENCES contentarea (id)
ON UPDATE NO ACTION ON DELETE NO ACTION;

--INFO AART related tables from 7.sql

ALTER TABLE orgassessmentprogram
ADD CONSTRAINT orgassessmentprogram_org_ap_uk
UNIQUE (organizationid, assessmentprogramid);

--this is for restricting enrollments.

ALTER TABLE studentstests
ADD COLUMN teacherid bigint;

ALTER TABLE studentstests
ADD CONSTRAINT teacher_fk FOREIGN KEY (teacherid) references aartuser(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE enrollment ADD COLUMN restrictionid bigint;
ALTER TABLE enrollment ADD CONSTRAINT restriction_fk FOREIGN KEY (restrictionid)
REFERENCES restriction (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--INFO AART related tables from 8.sql

--increase the field length of abbreviated area.
ALTER TABLE contentarea
   ALTER COLUMN abbreviatedname TYPE character varying(75);
   
ALTER TABLE studentstests DROP CONSTRAINT studentstests_studentid_testid_key;
ALTER TABLE studentstests ADD CONSTRAINT uk_student_test_teacher UNIQUE (studentid, testid, testcollectionid, teacherid);

--INFO AART related tables from 9.sql

--add the status field
ALTER TABLE studentstests add column status bigint;

ALTER TABLE studentstests
ADD CONSTRAINT studentstests_status_fk FOREIGN KEY (status) 
      references category(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- INFO CB related tables. from 6.sql

ALTER TABLE assessment
DROP CONSTRAINT if exists assessment_testsubjectid_fkey;

ALTER TABLE assessment
	DROP CONSTRAINT if exists test_type_fk;
