ALTER TABLE operationaltestwindow ADD COLUMN createddate time with time zone NOT NULL DEFAULT now();


CREATE OR REPLACE FUNCTION modifyDateColumns() RETURNS INTEGER AS 
$BODY$
DECLARE  
    tname             information_schema.tables.table_name%TYPE;
    tschema           information_schema.tables.table_schema%TYPE;  
    tableCount	           integer = 0;
    tableCursor CURSOR FOR  
         select table_name, table_schema from information_schema.tables where table_schema NOT IN ('pg_catalog', 'information_schema');

BEGIN
RAISE INFO '---------Enter---------------';   
OPEN tableCursor;   
	LOOP
		
		FETCH tableCursor INTO tname, tschema;  
		EXIT WHEN NOT FOUND ;  
		--RAISE INFO '%     %', table_name, table_schema;  
				
		IF tname = 'assessment' or tname = 'assessmentprogram' or tname = 'cognitivetaxonomy' or tname = 'contentframework'
		 or tname = 'cognitivetaxonomydimension' or tname = 'foil'
		 		or tname = 'contentframeworkdetail' or tname = 'contentarea'
		 		 or tname = 'testcollection' or tname='gradecourse'
		 		 or tname ='taskvariant' or tname='frameworktype'
		 		 or tname ='frameworklevel' or tname='test'
	 		 or tname ='tasklayoutformat' or tname='readaloudaccommodation'
	 		 or tname ='stimulusvariantattachment' or tname='stimulusvariant'
	 		 or tname ='tasklayoutformat' or tname='readaloudaccommodation'
	 		 or tname ='tool' or tname='testsection'
	 		 or tname ='testlet' or tname = 'tasktype'
	 		 or tname ='testingprogram' or tname = 'tasktype'
	 		 or tname ='testlet' or tname = 'tasktype'
	 		 or tname ='accessibilityfile'
	 		 or tname ='contentgroup'
		 		 
		THEN 
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN createdate TYPE timestamp with time zone';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN modifieddate TYPE timestamp with time zone '; 
			tableCount = tableCount + 1;
		ELSE if tname <> 'foilsstimulusvariants' and tname <> 'assessment_program_participation'
		 and tname <> 'studentstests' and tname  <> 'taskvariantsfoils' and tname <> 'resource_restriction'
		 and tname <> 'stimulusvariantcontentarea' and tname <> 'stimulusvariantgradecourse'
		 and tname <> 'studentpassword' and tname <> 'tasklayout'
		 and tname <> 'taskvariantcontentframeworkdetail'
		 and tname <> 'taskvariantitemusage'
		 and tname <> 'taskvariantsstimulusvariants'
		 and tname <> 'useraudit'
		 and tname <> 'testform'
		 and tname <> 'testletstimulusvariants'
		 and tname <> 'testsectionsrules'	
		 and tname <> 'ddl_version'	 
		 and tname <> 'testsectionstools'	 
		 and tname <> 'testsectionstaskvariants'	 
		 and tname <> 'userorganizationsgroups'	 
		 and tname <> 'testcollectionstests'
		 and tname <> 'userpasswordreset'
		 and tname <> 'usersorganizations'	 
		 and tname <> 'taskvariantstools'
		 and tname <> 'stimulusvarianttestingprogram'
		 and tname <> 'taskvariantstools'
		 and tname <> 'node_report'
		 and tname <> 'operationaltestwindow'
		 and tname <> 'correct_response_summary_by_node'
		 and tname <> 'in_correct_response_summary_by_node'
		 and tname <> 'answered_item_summary_by_node'
		 and tname <> 'total_item_summary_by_node'
		THEN
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN createddate TYPE timestamp with time zone';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN modifieddate TYPE timestamp with time zone'; 
			
			tableCount = tableCount + 1;		
			END IF;	
		END IF;
		 
		
	END LOOP;
CLOSE tableCursor;  
RAISE INFO '---------Exit with %--------------- ', tableCount;   
RETURN tableCount;  
EXCEPTION 
            WHEN undefined_object THEN
            RAISE NOTICE 'caught exception %', tableCount;
            RETURN tableCount;
END
$BODY$
LANGUAGE plpgsql;

select * from modifyDateColumns();

drop FUNCTION modifyDateColumns();

--US11611 refactor view.
drop view if exists total_item_summary_by_node cascade;

create or replace view total_item_summary_by_node as
SELECT  st.id as students_tests_id,
	st.studentid                      AS student_id          ,
	st.testid as test_id,
	st.testcollectionid as test_collection_id,
                   tvln.nodecode                      AS node_key            ,
                   cfd.contentcode AS content_framework_detail_code,
                   cfd.description AS content_framework_detail_description,
                   COUNT(1)                           AS total_items_presented     
          FROM     
                   studentstests st,
                   category ststat,
                   testsection ts,                       
                   testsectionstaskvariants tstv,
                   taskvariantcontentframeworkdetail tvcfd,
                   contentframeworkdetail cfd,                   
                   taskvariantlearningmapnode tvln        
          WHERE
          	   tvcfd.contentframeworkdetailid = cfd.id and
          	   tvcfd.isprimary = true and
          	   tvcfd.taskvariantid = tstv.taskvariantid and              
		st.testid = ts.testid and
		tstv.testsectionid = ts.id and
		st.status = ststat.id and
		ststat.categoryname <> 'unused' and
		tstv.taskvariantid = tvln.taskvariantid
          GROUP BY st.id,
		   st.studentid       ,
                   st.testid          ,
                   tvln.nodecode      ,
                   cfd.contentcode,
                   cfd.description;

drop view if exists correct_response_summary_by_node;
                   
create or replace view correct_response_summary_by_node as
SELECT  st.id as students_tests_id,
	st.studentid                      AS student_id          ,
	st.testid as test_id,
		st.testcollectionid as test_collection_id	,
                   tvln.nodecode                      AS node_key            ,
                   cfd.contentcode AS content_framework_detail_code,
                   COUNT(distinct(tvf.foilid))                           AS no_of_correct_responses     ,
                   SUM(NVL(tvf.responsescore::bigint)) AS total_raw_score
          FROM     studentsresponses sr                   ,
                   taskvariantsfoils tvf                  ,
                   taskvariantlearningmapnode tvln        ,
                   taskvariantcontentframeworkdetail tvcfd,
                   contentframeworkdetail cfd,
                   studentstests st                       
          WHERE    sr.studentstestsid = st.id
          AND	   tvcfd.contentframeworkdetailid = cfd.id
          AND	   tvcfd.isprimary = true
          AND	   tvcfd.taskvariantid = tvf.taskvariantid
          AND	   sr.taskvariantid = tvf.taskvariantid
          AND	   tvf.taskvariantid = tvln.taskvariantid
          AND
                   (
                            tvf.foilid            = sr.foilid
                   OR       
			(
			tvf.foilid      IS NULL
                   AND      sr.response IS NOT NULL
                   AND      sr.response ~~ '[%]'::text
                   AND      btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
                   AND
                            (
                                     tvf.foilid = ANY (string_to_integer_array(sr.response))
                            )
                         )
                   )
          AND	    tvf.correctresponse           is not null
          AND	    tvf.correctresponse           = true
          GROUP BY st.id,
		   st.studentid       ,
                   st.testid          ,
                   tvln.nodecode      ,
                   cfd.contentcode;
                   
drop view if exists in_correct_response_summary_by_node;

 create or replace view in_correct_response_summary_by_node as
SELECT  st.id as students_tests_id,
	st.studentid                      AS student_id          ,
	st.testid as test_id,
	st.testcollectionid as test_collection_id	,
                   tvln.nodecode                      AS node_key            ,
                   cfd.contentcode AS content_framework_detail_code,
                   COUNT(distinct(tvf.foilid))         AS no_of_in_correct_responses     ,
                   SUM(NVL(tvf.responsescore::bigint)) AS total_raw_score
          FROM     studentsresponses sr                   ,
                   taskvariantsfoils tvf                  ,
                   taskvariantlearningmapnode tvln        ,
                   taskvariantcontentframeworkdetail tvcfd,
                   contentframeworkdetail cfd,                   
                   studentstests st                       
          WHERE    sr.studentstestsid = st.id
          AND	   tvcfd.contentframeworkdetailid = cfd.id
          AND	   tvcfd.isprimary = true
          AND	   tvcfd.taskvariantid = tvf.taskvariantid          
          AND	   sr.taskvariantid = tvf.taskvariantid
          AND	   tvf.taskvariantid = tvln.taskvariantid
          AND
                   (
                            tvf.foilid            = sr.foilid
                   OR       (
			tvf.foilid      IS NULL
			AND      sr.response IS NOT NULL
			AND      sr.response ~~ '[%]'::text
			AND      btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
			AND
                            (
                                     tvf.foilid = ANY (string_to_integer_array(sr.response))
                            )
                            )
                   )
          AND	    (
			tvf.correctresponse           is null
			OR
		     tvf.correctresponse           = false
		     )
          GROUP BY st.id,
		   st.studentid       ,
                   st.testid          ,
                   tvln.nodecode      ,
                   cfd.contentcode;
        
drop view if exists answered_item_summary_by_node;
                   
create or replace view answered_item_summary_by_node as
SELECT  st.id as students_tests_id,
	st.studentid                      AS student_id          ,
	st.testid as test_id,
		st.testcollectionid as test_collection_id	,
                   tvln.nodecode                      AS node_key            ,
                   cfd.contentcode AS content_framework_detail_code,
                   COUNT(distinct(tvf.taskvariantid)) AS no_of_answered_items
          FROM     studentsresponses sr                   ,
                   taskvariantsfoils tvf                  ,
                   taskvariantlearningmapnode tvln        ,
                   taskvariantcontentframeworkdetail tvcfd,
                   contentframeworkdetail cfd,
                   studentstests st                       
          WHERE    sr.studentstestsid = st.id
          AND	   tvcfd.contentframeworkdetailid = cfd.id
          AND	   tvcfd.isprimary = true
          AND	   tvcfd.taskvariantid = tvf.taskvariantid
          AND	   sr.taskvariantid = tvf.taskvariantid
          AND	   tvf.taskvariantid = tvln.taskvariantid
          AND
                   (
                            tvf.foilid      = sr.foilid
                   OR       tvf.foilid      IS NULL
                   AND      sr.response IS NOT NULL
                   AND      sr.response ~~ '[%]'::text
                   AND      btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text)
                   AND
                            (
                                     tvf.foilid = ANY (string_to_integer_array(sr.response))
                            )
                   )
          GROUP BY st.id,
		   st.studentid       ,
                   st.testid          ,
                   tvln.nodecode      ,
                   cfd.contentcode;
                   
drop view if exists node_report;
                   
CREATE OR REPLACE VIEW node_report AS 
 SELECT   s.id                     AS student_id              ,
          s.statestudentidentifier AS state_student_identifier,
          s.legalfirstname         AS legal_first_name        ,
          s.legalmiddlename        AS legal_middle_name       ,
          s.legallastname          AS legal_last_name         ,
          s.comprehensiverace      AS comprehensive_race      ,
          s.gender                                            ,
          s.primarydisabilitycode AS primary_disability_code  ,
          ti_summary.node_key                                 ,
          ti_summary.students_tests_id      AS students_tests_id             ,
          ti_summary.test_id       AS test_id                 ,
          t.testname AS test_name                                  ,
          t.status   AS test_status_id                             ,
          tc.id      AS test_collection_id                         ,
          tc.name    AS test_collection_name              ,
          NVL(cr_summary.no_of_correct_responses::bigint) as no_of_correct_responses,--changed
          NVL(ir_summary.no_of_in_correct_responses::bigint) as no_of_in_correct_responses ,--added
          --summary.correct_response                                  ,removed--
          (NVL(cr_summary.no_of_correct_responses::bigint)+NVL(ir_summary.no_of_in_correct_responses::bigint)) as no_of_Responses,--added
          NVL(ai_summary.no_of_answered_items::bigint) as no_of_answered_items,
          NVL(cr_summary.total_raw_score::bigint) as total_raw_score,
          ti_summary.content_framework_detail_code                        ,
          ti_summary.content_framework_detail_description                 ,
          ti_summary.total_items_presented
from
	student s,
	test t,
	testcollectionstests tct,
	testcollection tc,
        total_item_summary_by_node ti_summary
         left join correct_response_summary_by_node cr_summary
		on (
			ti_summary.students_tests_id = cr_summary.students_tests_id and 
			ti_summary.node_key = cr_summary.node_key and 
			ti_summary.content_framework_detail_code = cr_summary.content_framework_detail_code 
		)
         left join in_correct_response_summary_by_node ir_summary
		on (
			ti_summary.students_tests_id = ir_summary.students_tests_id and 
			ti_summary.node_key = ir_summary.node_key and 
			ti_summary.content_framework_detail_code = ir_summary.content_framework_detail_code 
		)
	left join answered_item_summary_by_node ai_summary
		on (
			ti_summary.students_tests_id = ai_summary.students_tests_id and 
			ti_summary.node_key = ai_summary.node_key and 
			ti_summary.content_framework_detail_code = ai_summary.content_framework_detail_code 
		)	
where
	tct.testid = t.id and
	tct.testcollectionid = tc.id and
	ti_summary.student_id = s.id and
	ti_summary.test_id = t.id;