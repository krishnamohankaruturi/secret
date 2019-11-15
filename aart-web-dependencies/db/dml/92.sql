--US12993: Technical: Redundant data and constraint cleanup

-- Contentarea table cleanup

UPDATE contentarea SET name= 'Mathematics' where abbreviatedname='M';
UPDATE contentarea SET name= 'Mathematics',abbreviatedname='M' where abbreviatedname='Math';
UPDATE contentarea SET name= 'ARMM Reading' where abbreviatedname='ARMM';
UPDATE contentarea SET name= 'English Language Arts' where abbreviatedname='ELA';

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (trim(name), id) *
FROM  (
   SELECT DISTINCT ON (name) *
   FROM   contentarea
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

delete from tmp where id not in (select id from tmp where id in((select DISTINCT ON (contentareaid) contentareaid from testcollection) union
(select DISTINCT ON (contentareaid) contentareaid from contentframework) union
(select DISTINCT ON (contentareaid) contentareaid from taskvariant) union
(select DISTINCT ON (contentareaid) contentareaid from stimulusvariant) union
(select DISTINCT ON (contentareaid) contentareaid from stimulusvariantcontentarea) union
(select DISTINCT ON (contentareaid) contentareaid from testlet) union
(select DISTINCT ON (contentareaid) contentareaid from studentsassessments) union
(select DISTINCT ON (contentareaid) contentareaid from contentareatesttypesubjectarea) union
(select DISTINCT ON (statecourseid) statecourseid from roster) union
(select DISTINCT ON (statesubjectareaid) statesubjectareaid from roster)));


UPDATE contentareatesttypesubjectarea r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE testcollection r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE contentframework r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE taskvariant r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE stimulusvariant r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;


UPDATE stimulusvariantcontentarea r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE testlet r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

UPDATE roster r
SET    statecourseid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.statecourseid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.statecourseid) =  (select name from contentarea where id = tmp.id)
AND    statecourseid IS DISTINCT FROM tmp.id;

UPDATE roster r
SET    statesubjectareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.statesubjectareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.statesubjectareaid) =  (select name from contentarea where id = tmp.id)
AND    statesubjectareaid IS DISTINCT FROM tmp.id;

UPDATE studentsassessments r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

DELETE FROM contentarea where id not in (SELECT id FROM tmp);
DROP TABLE tmp;

--Assessment table cleanup

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(id)*
   FROM   assessment
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

delete from tmp where id not in (select id from tmp where id in((select DISTINCT ON (assessmentid) assessmentid from assessmentstestcollections) union
(select DISTINCT ON (assessmentid) assessmentid from studentsassessments) union (select DISTINCT ON (assessmentid) assessmentid from autoregistrationcriteria) union
(select DISTINCT ON (assessmentid) assessmentid from testtype)));

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(assessmentname,testingprogramid) *
   FROM   tmp 
   ) sub
ORDER  BY id ASC; 

grant select,update on table tmp1 to aart_user;

DELETE FROM assessment where id not in (SELECT id FROM tmp1);
DROP table tmp1;
DROP table tmp;

-- Gradecourse table cleanup


delete from gradecourse where id not in (select id from gradecourse where id in((select DISTINCT ON (gradecourseid) gradecourseid from testcollection) union
(select DISTINCT ON (gradecourseid) gradecourseid from contentframework) union
(select DISTINCT ON (currentgradelevel) currentgradelevel from enrollment) union
(select DISTINCT ON (gradecourseid) gradecourseid from taskvariant) union
(select DISTINCT ON (gradecourseid) gradecourseid from stimulusvariant) union
(select DISTINCT ON (gradecourseid) gradecourseid from stimulusvariantgradecourse) union
(select DISTINCT ON (gradecourseid) gradecourseid from autoregistrationcriteria) union
(select DISTINCT ON (gradecourseid) gradecourseid from testlet)));

UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = 'Seventh Grade') from assessmentprogramgrades where gradecourse.name = '7' or gradecourse.name = 'Seventh' or gradecourse.name = 'Grade 7' or gradecourse.name = '7th';

UPDATE gradecourse
SET    name = 'Grade 1', gradelevel=1, abbreviatedname = '1'
WHERE  name in ('1','First','Grade 1','1st','gc1');

UPDATE gradecourse
SET    name = 'Grade 2', gradelevel=2, abbreviatedname = '2'
WHERE  name in ('2','Second','Grade 2','2nd','gc2');

UPDATE gradecourse
SET    name = 'Grade 3', gradelevel=3, abbreviatedname = '3'
WHERE  name in ('3','Third','Grade 3','3rd','gc3');

UPDATE gradecourse
SET    name = 'Grade 4', gradelevel=4, abbreviatedname = '4'
WHERE  name in ('4','Fourth','Grade 4','4th','gc4');

UPDATE gradecourse
SET    name = 'Grade 5', gradelevel=5, abbreviatedname = '5'
WHERE  name in ('5','Fifth','Grade 5','5th','gc5');

UPDATE gradecourse
SET    name = 'Grade 6', gradelevel=6, abbreviatedname = '6'
WHERE  name in ('6','Sixth','Grade 6','6th','gc6');

UPDATE gradecourse
SET    name = 'Grade 7', gradelevel=7, abbreviatedname = '7'
WHERE  name in ('7','Seventh','Grade 7','7th','gc7');

UPDATE gradecourse
SET    name = 'Grade 8', gradelevel=8, abbreviatedname = '8'
WHERE  name in ('8','Eighth','Grade 8','8th','gc8');

UPDATE gradecourse
SET    name = 'Grade 9', gradelevel=9, abbreviatedname = '9'
WHERE  name in ('9','Ninth','Grade 9','9th','gc9');

UPDATE gradecourse
SET    name = 'Grade 10', gradelevel=10, abbreviatedname = '10'
WHERE  name in ('10','Tenth','Grade 10','10th','gc10');

UPDATE gradecourse
SET    name = 'Grade 11', gradelevel=11, abbreviatedname = '11'
WHERE  name in ('11','Eleventh','Grade 11','11th','gc11');

UPDATE gradecourse
SET    name = 'Grade 12', gradelevel=12, abbreviatedname = '12'
WHERE  name in ('12','Twelfth','Grade 12','12th','gc12');


CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(name) *
   FROM  gradecourse
   ) sub
ORDER  BY id ASC; 

grant select,update on table tmp1 to aart_user;

UPDATE testcollection r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE contentframework r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE taskvariant r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE stimulusvariant r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE stimulusvariantgradecourse r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE testlet r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE autoregistrationcriteria r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.gradecourseid) =  (select name from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE enrollment r
SET    currentgradelevel = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select name from gradecourse where id = r.currentgradelevel) =  (select name from gradecourse where id = t1.id)
AND 	(select name from gradecourse where id = r.currentgradelevel) =  (select name from gradecourse where id = tmp1.id)
AND    currentgradelevel IS DISTINCT FROM tmp1.id;

DELETE FROM gradecourse where id not in (SELECT id FROM tmp1);
DROP table tmp1;


-- Testing program table cleanup

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(programname,assessmentprogramid)*
   FROM   testingprogram
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

delete from tmp where id not in (select id from tmp where id in((select DISTINCT ON (testingprogramid) testingprogramid from assessment) union
(select DISTINCT ON (testingprogramid) testingprogramid from autoregistrationcriteria) union
(select DISTINCT ON (testingprogramid) testingprogramid from stimulusvariant) union
(select DISTINCT ON (testingprogramid) testingprogramid from stimulusvarianttestingprogram) union
(select DISTINCT ON (testingprogramid) testingprogramid from taskvariant) union
(select DISTINCT ON (testingprogramid) testingprogramid from testlet)));

DELETE FROM testingprogram where id not in (SELECT id FROM tmp);
DROP table tmp;

-- Frameworktype table cleanup

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(id)*
   FROM   frameworktype
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

delete from tmp where id not in (select id from tmp where id in((select DISTINCT ON (frameworktypeid) frameworktypeid from contentframework) union
(select DISTINCT ON (frameworktypeid) frameworktypeid from frameworklevel) union
(select DISTINCT ON (frameworktypeid) frameworktypeid from taskvariant)));

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(name) *
   FROM   tmp 
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;

UPDATE frameworklevel r
SET    frameworktypeid = tmp1.id
FROM   tmp1
JOIN   frameworktype t1 USING (id)
WHERE  (select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = t1.id)
AND 	(select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = tmp1.id)
AND    frameworktypeid IS DISTINCT FROM tmp1.id;

UPDATE contentframework r
SET    frameworktypeid = tmp1.id
FROM   tmp1
JOIN   frameworktype t1 USING (id)
WHERE  (select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = t1.id)
AND 	(select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = tmp1.id)
AND    frameworktypeid IS DISTINCT FROM tmp1.id;

UPDATE taskvariant r
SET    frameworktypeid = tmp1.id
FROM   tmp1
JOIN   frameworktype t1 USING (id)
WHERE  (select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = t1.id)
AND 	(select name from frameworktype where id = r.frameworktypeid) =  (select name from frameworktype where id = tmp1.id)
AND    frameworktypeid IS DISTINCT FROM tmp1.id;


DELETE FROM frameworktype where id not in (SELECT id FROM tmp1);
DROP table tmp;
DROP table tmp1;

-- Frameworklevel table cleanup

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(title,frameworktypeid)*
   FROM   frameworklevel
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

DELETE FROM frameworklevel where id not in (SELECT id FROM tmp);
DROP table tmp;


-- Tasklayout table

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(layoutname,tasktypeid) *
   FROM   tasklayout 
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;

UPDATE taskvariant r
SET    tasklayoutid = tmp1.id
FROM   tmp1
JOIN   tasklayout t1 USING (id)
WHERE  (select layoutname from tasklayout where id = r.tasklayoutid) =  (select layoutname from tasklayout where id = t1.id)
AND 	(select layoutname from tasklayout where id = r.tasklayoutid) =  (select layoutname from tasklayout where id = tmp1.id)
AND    tasklayoutid IS DISTINCT FROM tmp1.id;

DELETE FROM tasklayout where id not in (SELECT id FROM tmp1);
DROP table tmp1;

-- Tasklayoutformat table

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON(formatname) *
   FROM   tasklayoutformat 
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;

UPDATE taskvariant r
SET    tasklayoutformatid = tmp1.id
FROM   tmp1
JOIN   tasklayoutformat t1 USING (id)
WHERE  (select formatname from tasklayoutformat where id = r.tasklayoutformatid) =  (select formatname from tasklayoutformat where id = t1.id)
AND 	(select formatname from tasklayoutformat where id = r.tasklayoutformatid) =  (select formatname from tasklayoutformat where id = tmp1.id)
AND    tasklayoutformatid IS DISTINCT FROM tmp1.id;

DELETE FROM tasklayoutformat where id not in (SELECT id FROM tmp1);
