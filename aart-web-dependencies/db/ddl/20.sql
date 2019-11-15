--for improving performance on node report.
ALTER TABLE contentframeworkdetail ADD CONSTRAINT
uk_cfd_cfd_parent UNIQUE (contentcode, frameworklevelid, contentframeworkid, parentcontentframeworkdetailid);

--fixed the defect on multiple choice single select vs multiple choice multi select.

DROP VIEW if exists answered_item_summary_by_node cascade;

CREATE OR REPLACE VIEW answered_item_summary_by_node AS 
 SELECT   st.id                             AS students_tests_id            ,
          st.studentid                      AS student_id                   ,
          st.testid                         AS test_id                      ,
          st.testcollectionid               AS test_collection_id           ,
          tvln.nodecode                     AS node_key                     ,
          cfd.contentcode                   AS content_framework_detail_code,
          COUNT(DISTINCT tvf.taskvariantid) AS no_of_answered_items
 FROM     studentsresponses sr                   ,
          taskvariantsfoils tvf                  ,
          taskvariantlearningmapnode tvln        ,
          taskvariantcontentframeworkdetail tvcfd,
          contentframeworkdetail cfd             ,
          studentstests st
 WHERE    sr.studentstestsid             = st.id
 AND      tvcfd.contentframeworkdetailid = cfd.id
 AND      tvcfd.isprimary                = true
 AND      tvcfd.taskvariantid            = tvf.taskvariantid
 AND      sr.taskvariantid               = tvf.taskvariantid
 AND      tvf.taskvariantid              = tvln.taskvariantid
 AND
          (
		(
                   tvf.foilid            = sr.foilid
                )
                OR
                (
                   tvf.foilid      IS NULL
		    AND sr.response IS NOT NULL
		    AND sr.response ~~ '[%]'::text
		    AND btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
		    AND tvf.foilid = ANY (string_to_integer_array(sr.response))
                   )
          )
 GROUP BY st.id        ,
          st.studentid ,
          st.testid    ,
          tvln.nodecode,
          cfd.contentcode;

--fixed the defect on searching by category name instead of category code.
drop view if exists total_item_summary_by_node cascade;

CREATE OR REPLACE VIEW total_item_summary_by_node AS 
 SELECT   st.id               AS students_tests_id                   ,
         st.studentid        AS student_id                          ,
         st.testid           AS test_id                             ,
         st.testcollectionid AS test_collection_id                  ,
         tvln.nodecode       AS node_key                            ,
         cfd.contentcode     AS content_framework_detail_code       ,
         cfd.description     AS content_framework_detail_description,
         COUNT(1)            AS total_items_presented
FROM     studentstests st                       ,
         category ststat                        ,
         categorytype metatype,
         testsection ts                         ,
         testsectionstaskvariants tstv          ,
         taskvariantcontentframeworkdetail tvcfd,
         contentframeworkdetail cfd             ,
         taskvariantlearningmapnode tvln
WHERE    tvcfd.contentframeworkdetailid = cfd.id
AND      tvcfd.isprimary                = true
AND      tvcfd.taskvariantid            = tstv.taskvariantid
AND      st.testid                      = ts.testid
AND      tstv.testsectionid             = ts.id
AND      st.status                      = ststat.id
AND      ststat.categorycode::text     <> 'unused'::text
AND	 metatype.id			= ststat.categorytypeid
AND	 metatype.typecode		='STUDENT_TEST_STATUS'
AND      tstv.taskvariantid             = tvln.taskvariantid
GROUP BY st.id          ,
         st.studentid   ,
         st.testid      ,
         tvln.nodecode  ,
         cfd.contentcode,
         cfd.description;
         
-- correct response summary by node. seperated sqls
drop view if exists correct_response_summary_by_node cascade;
         
CREATE OR REPLACE VIEW correct_response_summary_by_node AS 
SELECT   st.id                                          AS students_tests_id            ,
         st.studentid                                   AS student_id                   ,
         st.testid                                      AS test_id                      ,
         st.testcollectionid                            AS test_collection_id           ,
         tvln.nodecode                                  AS node_key                     ,
         cfd.contentcode                                AS content_framework_detail_code,
         COUNT(DISTINCT tvf.foilid)                     AS no_of_correct_responses      ,
         SUM(NVL(tvf.responsescore::bigint))            AS total_raw_score
FROM     studentsresponses sr                   ,
         taskvariantsfoils tvf                  ,
         taskvariantlearningmapnode tvln        ,
         taskvariantcontentframeworkdetail tvcfd,
         contentframeworkdetail cfd             ,
         studentstests st
WHERE    sr.studentstestsid             = st.id
AND      tvcfd.contentframeworkdetailid = cfd.id
AND      tvcfd.isprimary                = true
AND      tvcfd.taskvariantid            = tvf.taskvariantid
AND      sr.taskvariantid               = tvf.taskvariantid
AND      tvf.taskvariantid              = tvln.taskvariantid
AND	 	 tvf.foilid            			= sr.foilid
AND      tvf.correctresponse 			IS NOT NULL
AND      tvf.correctresponse           = true
GROUP BY st.id        ,
         st.studentid ,
         st.testid    ,
         tvln.nodecode,
         cfd.contentcode
union all
SELECT   st.id                                          AS students_tests_id            ,
         st.studentid                                   AS student_id                   ,
         st.testid                                      AS test_id                      ,
         st.testcollectionid                            AS test_collection_id           ,
         tvln.nodecode                                  AS node_key                     ,
         cfd.contentcode                                AS content_framework_detail_code,
         COUNT(DISTINCT tvf.foilid)                     AS no_of_correct_responses      ,
         SUM(NVL(tvf.responsescore::bigint))            AS total_raw_score
FROM     studentsresponses sr                   ,
         taskvariantsfoils tvf                  ,
         taskvariantlearningmapnode tvln        ,
         taskvariantcontentframeworkdetail tvcfd,
         contentframeworkdetail cfd             ,
         studentstests st
WHERE    sr.studentstestsid             = st.id
AND      tvcfd.contentframeworkdetailid = cfd.id
AND      tvcfd.isprimary                = true
AND      tvcfd.taskvariantid            = tvf.taskvariantid
AND      sr.taskvariantid               = tvf.taskvariantid
AND      tvf.taskvariantid              = tvln.taskvariantid
AND      tvf.foilid      IS NULL
AND      sr.response IS NOT NULL
AND      sr.response ~~ '[%]'::text
AND      btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
AND      tvf.foilid = ANY (string_to_integer_array(sr.response))
AND      tvf.correctresponse IS NOT NULL
AND      tvf.correctresponse           = true
GROUP BY st.id        ,
         st.studentid ,
         st.testid    ,
         tvln.nodecode,
         cfd.contentcode;
         
--seperated in_correct_response_summary_by_node        
         
drop view if exists in_correct_response_summary_by_node cascade;
         
CREATE OR REPLACE VIEW in_correct_response_summary_by_node AS        
SELECT   st.id                                             AS students_tests_id            ,
         st.studentid                                      AS student_id                   ,
         st.testid                                         AS test_id                      ,
         st.testcollectionid                               AS test_collection_id           ,
         tvln.nodecode                                     AS node_key                     ,
         cfd.contentcode                                   AS content_framework_detail_code,
         COUNT(DISTINCT tvf.foilid)                        AS no_of_in_correct_responses   ,
         SUM(NVL(tvf.responsescore::bigint))               AS total_raw_score
FROM     studentsresponses sr                   ,
         taskvariantsfoils tvf                  ,
         taskvariantlearningmapnode tvln        ,
         taskvariantcontentframeworkdetail tvcfd,
         contentframeworkdetail cfd             ,
         studentstests st
WHERE    sr.studentstestsid             = st.id
AND      tvcfd.contentframeworkdetailid = cfd.id
AND      tvcfd.isprimary                = true
AND      tvcfd.taskvariantid            = tvf.taskvariantid
AND      sr.taskvariantid               = tvf.taskvariantid
AND      tvf.taskvariantid              = tvln.taskvariantid
AND      tvf.foilid            = sr.foilid
AND
         (
                  tvf.correctresponse IS NULL
         OR       tvf.correctresponse       = false
         )
GROUP BY st.id        ,
         st.studentid ,
         st.testid    ,
         tvln.nodecode,
         cfd.contentcode
union all
SELECT   st.id                                             AS students_tests_id            ,
         st.studentid                                      AS student_id                   ,
         st.testid                                         AS test_id                      ,
         st.testcollectionid                               AS test_collection_id           ,
         tvln.nodecode                                     AS node_key                     ,
         cfd.contentcode                                   AS content_framework_detail_code,
         COUNT(DISTINCT tvf.foilid)                        AS no_of_in_correct_responses   ,
         SUM(NVL(tvf.responsescore::bigint))               AS total_raw_score
FROM     studentsresponses sr                   ,
         taskvariantsfoils tvf                  ,
         taskvariantlearningmapnode tvln        ,
         taskvariantcontentframeworkdetail tvcfd,
         contentframeworkdetail cfd             ,
         studentstests st
WHERE    sr.studentstestsid             = st.id
AND      tvcfd.contentframeworkdetailid = cfd.id
AND      tvcfd.isprimary                = true
AND      tvcfd.taskvariantid            = tvf.taskvariantid
AND      sr.taskvariantid               = tvf.taskvariantid
AND      tvf.taskvariantid              = tvln.taskvariantid
AND	 	 tvf.foilid     IS NULL
AND      sr.response 	IS NOT NULL
AND      sr.response ~~ '[%]'::text
AND      btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
AND      tvf.foilid = ANY (string_to_integer_array(sr.response))
AND
         (
                  tvf.correctresponse IS NULL
         OR       tvf.correctresponse       = false
         )
GROUP BY st.id        ,
         st.studentid ,
         st.testid    ,
         tvln.nodecode,
         cfd.contentcode;         
         
         
DROP VIEW if exists node_report cascade;
CREATE OR REPLACE VIEW node_report AS
SELECT    s.id                     AS student_id              ,
          s.statestudentidentifier AS state_student_identifier,
          s.legalfirstname         AS legal_first_name        ,
          s.legalmiddlename        AS legal_middle_name       ,
          s.legallastname          AS legal_last_name         ,
          s.comprehensiverace      AS comprehensive_race      ,
          s.gender                                            ,
          s.primarydisabilitycode AS primary_disability_code  ,
          ti_summary.node_key                                 ,
          ti_summary.students_tests_id                        ,
          ti_summary.test_id                                  ,
          t.testname                                                                           AS test_name                             ,
          t.status                                                                             AS test_status_id                        ,
          tc.id                                                                                AS test_collection_id                    ,
          tc.name                                                                              AS test_collection_name                  ,
          NVL(cr_summary.no_of_correct_responses)                                              AS no_of_correct_responses               ,
          NVL(ir_summary.no_of_in_correct_responses)                                           AS no_of_in_correct_responses            ,
          NVL(cr_summary.no_of_correct_responses) + NVL(ir_summary.no_of_in_correct_responses) AS no_of_responses                       ,
          NVL(ai_summary.no_of_answered_items)                                                 AS no_of_answered_items                  ,
          NVL(cr_summary.total_raw_score::bigint)                                              AS total_raw_score                       ,
          ti_summary.content_framework_detail_code                                                                                      ,
          ti_summary.content_framework_detail_description                                                                               ,
          ti_summary.total_items_presented
FROM      student s               ,
          test t                  ,
          testcollectionstests tct,
          testcollection tc       ,
          total_item_summary_by_node ti_summary
          LEFT JOIN correct_response_summary_by_node cr_summary
          ON        ti_summary.students_tests_id                   = cr_summary.students_tests_id
          AND       ti_summary.node_key::text                      = cr_summary.node_key::text
          AND       ti_summary.content_framework_detail_code::text = cr_summary.content_framework_detail_code::text
          LEFT JOIN in_correct_response_summary_by_node ir_summary
          ON        ti_summary.students_tests_id                   = ir_summary.students_tests_id
          AND       ti_summary.node_key::text                      = ir_summary.node_key::text
          AND       ti_summary.content_framework_detail_code::text = ir_summary.content_framework_detail_code::text
          LEFT JOIN answered_item_summary_by_node ai_summary
          ON        ti_summary.students_tests_id                   = ai_summary.students_tests_id
          AND       ti_summary.node_key::text                      = ai_summary.node_key::text
          AND       ti_summary.content_framework_detail_code::text = ai_summary.content_framework_detail_code::text
WHERE     tct.testid                                               = t.id
AND       tct.testcollectionid                                     = tc.id
AND       ti_summary.student_id                                    = s.id
AND       ti_summary.test_id                                       = t.id;         