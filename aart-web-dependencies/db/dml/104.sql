--US12993: Technical: Redundant data and constraint cleanup

--Removing duplicates for abbreviated names in gradecourse table

UPDATE gradecourse SET abbreviatedname = name where abbreviatedname IS NULL;

delete from gradecourse where id not in (select id from gradecourse where id in((select DISTINCT ON (gradecourseid) gradecourseid from testcollection) union
(select DISTINCT ON (gradecourseid) gradecourseid from contentframework) union
(select DISTINCT ON (currentgradelevel) currentgradelevel from enrollment) union
(select DISTINCT ON (gradecourseid) gradecourseid from taskvariant) union
(select DISTINCT ON (gradecourseid) gradecourseid from stimulusvariant) union
(select DISTINCT ON (gradecourseid) gradecourseid from stimulusvariantgradecourse) union
(select DISTINCT ON (gradecourseid) gradecourseid from autoregistrationcriteria) union
(select DISTINCT ON (gradecourseid) gradecourseid from testlet)));

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (trim(name), id) *
FROM  (
   SELECT DISTINCT ON (abbreviatedname) *
   FROM   gradecourse
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;


UPDATE testcollection r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE contentframework r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE taskvariant r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE stimulusvariant r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE stimulusvariantgradecourse r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse 
where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE testlet r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE autoregistrationcriteria r
SET    gradecourseid = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.gradecourseid) =  (select abbreviatedname from gradecourse where id = tmp1.id)
AND    gradecourseid IS DISTINCT FROM tmp1.id;

UPDATE enrollment r
SET    currentgradelevel = tmp1.id
FROM   tmp1
JOIN   gradecourse t1 USING (id)
WHERE  (select abbreviatedname from gradecourse where id = r.currentgradelevel) =  (select abbreviatedname from gradecourse where id = t1.id)
AND 	(select abbreviatedname from gradecourse where id = r.currentgradelevel) =  (select abbreviatedname from gradecourse where id = tmp1.id)
AND    currentgradelevel IS DISTINCT FROM tmp1.id;

DELETE FROM gradecourse where id not in (SELECT id FROM tmp1);
DROP TABLE tmp1;

--Assessment Table unique constraint

delete from assessment where id not in (select id from assessment where id in((select DISTINCT ON (assessmentid) assessmentid from assessmentstestcollections) union
(select DISTINCT ON (assessmentid) assessmentid from studentsassessments) union (select DISTINCT ON (assessmentid) assessmentid from autoregistrationcriteria) union
(select DISTINCT ON (assessmentid) assessmentid from testtype)));

ALTER TABLE assessment DROP CONSTRAINT uk_assessment_code_testing_program;

--Testing Program Cleanup

ALTER TABLE testingprogram DROP CONSTRAINT uk_testingprogram_assessmentprogram;
ALTER TABLE testingprogram ADD COLUMN activeflag boolean DEFAULT true;
UPDATE testingprogram SET programabbr = 'P' where programname = 'Practice';

ALTER TABLE testingprogram ADD CONSTRAINT uk_testingprogram_assessmentprogram UNIQUE(programabbr,assessmentprogramid,activeflag);
ALTER TABLE gradecourse ADD CONSTRAINT gradecourse_code UNIQUE(abbreviatedname,activeflag);
ALTER TABLE assessment ADD CONSTRAINT uk_assessment_code_testing_program UNIQUE (assessmentname,testingprogramid,activeflag);

--Cognitive Taxonomy

CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   select id from cognitivetaxonomy where name is not null
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;

UPDATE cognitivetaxonomydimension SET cognitivetaxonomyid = NULL where cognitivetaxonomyid not in (select id from tmp1);
UPDATE taskvariant SET cognitivetaxonomyid2 = NULL where cognitivetaxonomyid2 not in (select id from cognitivetaxonomy where id in (select id from tmp1));
UPDATE taskvariant SET cognitivetaxonomyid = NULL where cognitivetaxonomyid in (select id from tmp1);

delete from cognitivetaxonomy where id not in (select id from tmp1);

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON (name) *
   FROM   cognitivetaxonomy
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

UPDATE cognitivetaxonomydimension r
SET    cognitivetaxonomyid = tmp.id
FROM   tmp
JOIN   cognitivetaxonomy t1 USING (id)
WHERE  (select name from cognitivetaxonomy where id = r.cognitivetaxonomyid) =  (select name from cognitivetaxonomy where id = t1.id)
AND 	(select name from cognitivetaxonomy where id = r.cognitivetaxonomyid) =  (select name from cognitivetaxonomy where id = tmp.id)
AND    cognitivetaxonomyid IS DISTINCT FROM tmp.id;

UPDATE taskvariant r
SET    cognitivetaxonomyid2 = tmp.id
FROM   tmp
JOIN   cognitivetaxonomy t1 USING (id)
WHERE  (select name from cognitivetaxonomy where id = r.cognitivetaxonomyid2) =  (select name from cognitivetaxonomy where id = t1.id)
AND 	(select name from cognitivetaxonomy where id = r.cognitivetaxonomyid2) =  (select name from cognitivetaxonomy where id = tmp.id)
AND    cognitivetaxonomyid2 IS DISTINCT FROM tmp.id;

UPDATE taskvariant r
SET    cognitivetaxonomyid = tmp.id
FROM   tmp
JOIN   cognitivetaxonomy t1 USING (id)
WHERE  (select name from cognitivetaxonomy where id = r.cognitivetaxonomyid) =  (select name from cognitivetaxonomy where id = t1.id)
AND 	(select name from cognitivetaxonomy where id = r.cognitivetaxonomyid) =  (select name from cognitivetaxonomy where id = tmp.id)
AND    cognitivetaxonomyid IS DISTINCT FROM tmp.id;

delete from cognitivetaxonomy where id not in (select id from tmp);
DROP table tmp1;
DROP table tmp;

--Cognitive Taxonomy Dimension 


CREATE TEMP TABLE tmp1 AS
SELECT DISTINCT ON (id) *
FROM  (
   select id from cognitivetaxonomydimension where name is not null
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp1 to aart_user;

UPDATE taskvariant SET cognitivetaxonomydimensionid2 = NULL where cognitivetaxonomydimensionid2 not in (select id from 

cognitivetaxonomydimension where id in (select id from tmp1));
UPDATE taskvariant SET cognitivetaxonomydimensionid = NULL where cognitivetaxonomydimensionid in (select id from tmp1);

delete from cognitivetaxonomydimension where id not in (select id from tmp1);

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT DISTINCT ON (name) *
   FROM   cognitivetaxonomydimension
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

UPDATE taskvariant r
SET    cognitivetaxonomydimensionid2 = tmp.id
FROM   tmp
JOIN   cognitivetaxonomydimension t1 USING (id)
WHERE  (select name from cognitivetaxonomydimension where id = r.cognitivetaxonomydimensionid2) =  (select name from cognitivetaxonomydimension where id = t1.id)
AND 	(select name from cognitivetaxonomydimension where id = r.cognitivetaxonomydimensionid2) =  (select name from cognitivetaxonomydimension where id = tmp.id)
AND    cognitivetaxonomydimensionid2 IS DISTINCT FROM tmp.id;

UPDATE taskvariant r
SET    cognitivetaxonomydimensionid = tmp.id
FROM   tmp
JOIN   cognitivetaxonomydimension t1 USING (id)
WHERE  (select name from cognitivetaxonomydimension where id = r.cognitivetaxonomydimensionid) =  (select name from cognitivetaxonomydimension where id = t1.id)
AND 	(select name from cognitivetaxonomydimension where id = r.cognitivetaxonomydimensionid) =  (select name from cognitivetaxonomydimension where id = tmp.id)
AND    cognitivetaxonomydimensionid IS DISTINCT FROM tmp.id;

delete from cognitivetaxonomydimension where id not in (select id from tmp);
DROP table tmp1;
DROP table tmp;

ALTER TABLE cognitivetaxonomy ADD COLUMN activeflag boolean DEFAULT true;
ALTER TABLE cognitivetaxonomy ADD CONSTRAINT cognitivetaxonomy_code UNIQUE(abbreviatedname,activeflag);

ALTER TABLE cognitivetaxonomydimension ADD COLUMN activeflag boolean DEFAULT true;
ALTER TABLE cognitivetaxonomydimension ADD CONSTRAINT cognitivetaxonomydimension_code UNIQUE(abbreviatedname,activeflag);