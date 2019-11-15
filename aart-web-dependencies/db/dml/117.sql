-- Remove Science with TDE origination code and update the rest of the tables.

CREATE TEMP TABLE tmp AS
SELECT DISTINCT ON (id) *
FROM  (
   SELECT *
   FROM   contentarea
   ) sub
ORDER  BY id ASC;

grant select,update on table tmp to aart_user;

delete from tmp where id not in (select id from tmp where id in((select DISTINCT ON (contentareaid) contentareaid from testcollection) union
(select DISTINCT ON (contentareaid) contentareaid from gradecourse) union
(select DISTINCT ON (contentareaid) contentareaid from contentframework) union
(select DISTINCT ON (contentareaid) contentareaid from taskvariant) union
(select DISTINCT ON (contentareaid) contentareaid from stimulusvariant) union
(select DISTINCT ON (contentareaid) contentareaid from stimulusvariantcontentarea) union
(select DISTINCT ON (contentareaid) contentareaid from testlet) union
(select DISTINCT ON (contentareaid) contentareaid from studentsassessments) union
(select DISTINCT ON (contentareaid) contentareaid from contentareatesttypesubjectarea) union
(select DISTINCT ON (statecourseid) statecourseid from roster) union
(select DISTINCT ON (statesubjectareaid) statesubjectareaid from roster)));

--delete from tmp where abbreviatedname='SCI' and originationcode = 'TDE';
UPDATE contentarea SET originationcode = 'CB' where abbreviatedname='SCI' and originationcode = 'TDE';

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

UPDATE gradecourse r
SET    contentareaid = tmp.id
FROM   tmp
JOIN   contentarea t1 USING (id)
WHERE  (select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = t1.id)
AND 	(select name from contentarea where id = r.contentareaid) =  (select name from contentarea where id = tmp.id)
AND    contentareaid IS DISTINCT FROM tmp.id;

DELETE FROM contentarea where id not in (SELECT id FROM tmp);
DROP TABLE tmp;