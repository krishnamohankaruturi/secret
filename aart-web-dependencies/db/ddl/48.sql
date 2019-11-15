drop function if exists get_adaptive_test(_testid bigint, _studentstestid bigint);
CREATE OR REPLACE FUNCTION get_adaptive_test(_testid bigint, _studentstestid bigint)
  RETURNS TABLE (
       test bigint,
       testname character varying(75),
       numitems integer,
       uitypecode character varying(20),
       testbegininstructions text,
       testendinstructions text,
       testexitflag boolean,
       testsectionid bigint,
       testid bigint,
       testsectionname character varying(75),
       numberoftestitems integer,
       begininstructions text,
       endinstructions text,
       helpnotes text,
       hardbreak boolean,
       testsectiontoolid bigint,
       testsectiontoolname character varying(200),
       testsectiontoolcodename character varying(30),
       testsectiontooltypeid bigint,
       navigation boolean,
       ruleid bigint,
       categoryname character varying(75),
       categorycode character varying(75),
       categorydescription character varying(150),
       categorytypeid bigint,
       taskdeliveryrulecode character varying(75),
       taskvariantid bigint,
       taskname character varying(100),
       taskstem text,
       itemdescription character varying(75),
       testlet boolean, 
       numberofresponses integer,
       shuffled boolean,
       innovativeitempackagepath character varying(250),
       taskdirections text,
       cue text,
       completion text,
       tasksectiongroupnumber integer,
       taskpositioninsection integer,
       foilid bigint,
       foiltext text,
       responseorder integer,
       correctresponse  boolean,
       responsescore integer,
       tasktypecode character varying(75),
       tasklayoutcode character varying(75),
       tasklayoutformatcode character varying(75),
       toolid bigint,
       name character varying(200),
       codename character varying(30),
       tooltypeid bigint,
       tvcgid bigint,
       tvcgexternalid bigint,
       tvcgaccesselementid character varying(75),
       tvcgspanstart integer,
       tvcgspanend integer,
       tvcgcharindexstart integer,
       tvcgcharindexend integer,
       tvcgtaskvariantid bigint,
       tvcgfoilid bigint,
       tvcgcreateuserid bigint,
       racid bigint,
       racexternalid bigint,
       raccontentgroupid bigint,
       racreadaloudtypeid bigint,
       racsynthetic boolean,
       racsyntheticpronoun character varying(2000),
       rachuman boolean,
       racdefaultorder integer,
       racalternateorder integer,
       racaccessibilityfileid bigint,
       racstarttime integer,
       racendtime integer,
       raccreateuserid bigint,
       raccreatedate timestamp without time zone,
       racmodifieddate timestamp without time zone,
       racmodifieduserid bigint,
       racoriginationcode character varying(20),
       afid bigint,
       afexternalid bigint,
       affilename character varying(200),
       affilelocation character varying(250),
       affilesize double precision,
       affiletypeid bigint,
       aftaskvariantid bigint,
       afassessmentprogramid bigint,
       afduration double precision,
       aforiginationcode character varying(20),
       stimulusvariantid bigint,
       stimuluscontent text,
       contextstimulusid bigint,
       contextstimuluscontent text,
       stimulusvariantattachmentid bigint,
       attachmentname character varying(75),
       filename character varying(75),
       filelocation character varying(75),
       filesize integer,
       filetype character varying(75),
       contextstimulusattachmentid bigint,
       contextstimulusattachmentname character varying(75),
       contextstimulusfilename character varying(75),
       contextstimulusfilelocation character varying(75),
       contextstimulusfilesize integer,
       contextstimulusfiletype character varying(75),
       tsktestletid bigint,
       testletid bigint,
       testletname character varying(100),
       testingprogramid bigint,
       contentareaid bigint,
       gradecourseid bigint,
       testletlayoutid bigint,
       questionviewid bigint,
       questionlocked boolean,
       displayviewid bigint,
       stimulusneeded boolean,
       testletquestionviewcode character varying(75),
       testletlayoutcode character varying(75),
       testletdisplayviewcode character varying(75),
       testletstimulusvariantid bigint,
       testletstimuluscontent text,
       testletstimulustitle character varying(300),
       testletgroupnumber integer,
       testletsortorder integer 
  ) AS
$BODY$
DECLARE
    subsectioncursor refcursor;
    studentadaptiveteststatusrec record;
    description character varying;
    adaptiveTestRecord record;   
    itemNos INTEGER[]; 
    itemId text; 
    subsectionIds INTEGER[]; 
    testsectioncontainerid bigint;
    subsectionid bigint; 
    subsectioncount int;
    testpartnumber integer;
    thetavalue integer;
    testinprogress boolean; 
BEGIN
  testpartnumber := 0;
  thetavalue := 0;
  testinprogress := false; 
  FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus where studentstestid=_studentstestid and testpartcomplete=false order by testpartnumber LIMIT 1
  LOOP   
    testpartnumber := studentadaptiveteststatusrec.testpartnumber;
    testinprogress := true; 
  END LOOP; 
  --RAISE INFO 'testpartnumber %     thetavalue %      testinprogress %', testpartnumber, thetavalue, testinprogress;  
  subsectioncount :=1;
  IF NOT testinprogress THEN     
	  FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus where studentstestid=_studentstestid and testpartcomplete=true order by testpartnumber desc LIMIT 1
	      LOOP   
		testpartnumber := studentadaptiveteststatusrec.testpartnumber; 
	  END LOOP;
	  testpartnumber := testpartnumber + 1;  
	  FOR adaptiveTestRecord IN     
		     SELECT t.id as testid,
				   tp.id as testpartid,
				   tp.partnumber as testparnumber,
				   tp.selectednumberofsubsections as selectednumberofsubsections,
				   tp.administratednumberofsubsections as administratednumberofsubsections,
				   tsc.id as testsectioncontainerid,
				   tsc.sectionnumber as testsectioncontainernumber,
				   tc.constructnumber as testconstructnumber,
				   tc.thetanodevalue as testsectioncontainerthetanodevalue,
				   tscc.itemdiscriminationparametername,
				   tscc.itemdiscriminationparameterindex 
			    FROM test t
			    JOIN testpart tp ON tp.testid=t.id 
			    JOIN testsectioncontainer tsc ON tsc.id=tp.testsectioncontainerid
			    JOIN testconstruct tc ON tc.testid=t.id
			    JOIN testsectioncontainerconstruct tscc ON tscc.testconstructid = tc.id and tscc.testsectioncontainerid = tsc.id
		    WHERE t.id = _testid 
		      AND tp.partnumber = testpartnumber  
	     LOOP 
	     	      thetavalue := adaptiveTestRecord.testsectioncontainerthetanodevalue;
	      	      FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus sat where sat.studentstestid=_studentstestid and sat.testpartcomplete=true and sat.testsectioncontainernumber = testsectioncontainernumber order by testpartnumber desc LIMIT 1
	              LOOP   
	     	  	thetavalue := studentadaptiveteststatusrec.interimtheta; 
      	  	      END LOOP; 
		      --RAISE INFO 'testid %     testpartid %     testsectioncontainerid %      testcontainerthetavalue %       selectednumberofsubsections %      administratednumberofsubsections %', adaptiveTestRecord.testid, adaptiveTestRecord.testpartid, adaptiveTestRecord.testsectioncontainerid, thetavalue, adaptiveTestRecord.selectednumberofsubsections, adaptiveTestRecord.administratednumberofsubsections;  
		      subsectioncursor = get_adaptive_subsections(adaptiveTestRecord.testid , adaptiveTestRecord.testpartid, adaptiveTestRecord.testsectioncontainerid, thetavalue, adaptiveTestRecord.selectednumberofsubsections, adaptiveTestRecord.administratednumberofsubsections, _studentstestid);  
		      LOOP
			FETCH subsectioncursor INTO testsectioncontainerid,subsectionid;
			EXIT WHEN NOT FOUND;
			RAISE INFO 'testsectioncontainerid %   subsectionid %', testsectioncontainerid, subsectionid;
			subsectionIds[subsectioncount]:= subsectionid;
			subsectioncount := subsectioncount + 1;
			EXECUTE 'insert into studentsadaptivetestsections(studentstestid,testpartid,testsectioncontainerid,testsectionid)values(' || _studentstestid || ',' || adaptiveTestRecord.testpartid  || ',' || testsectioncontainerid  || ',' || subsectionid || ')';
		      END LOOP; 
		      CLOSE subsectioncursor; 
		      EXECUTE 'insert into studentadaptivetestthetastatus(studentstestid,testpartnumber,testsectioncontainernumber,initialtheta, interimtheta,testpartcomplete)values(' || _studentstestid || ',' || testpartnumber  || ',' || adaptiveTestRecord.testsectioncontainernumber || ',' || thetavalue ||',0 ,false)';		      
	     END LOOP;  	     
     ELSE
     FOR adaptiveTestRecord IN     
     		     SELECT sat.testsectionid
     			    FROM studentsadaptivetestsections sat 
     		     WHERE sat.studentstestid = _studentstestid
     		      AND sat.testpartid = (select tp.id from test t join testpart tp on t.id = tp.testid where t.id=_testid and tp.partnumber = testpartnumber)
     	     LOOP   
     		    --RAISE INFO 'InProgress testsectionid %', adaptiveTestRecord.testsectionid;      		       
     		    subsectionIds[subsectioncount]:= adaptiveTestRecord.testsectionid;
     		    subsectioncount := subsectioncount + 1; 
	     END LOOP;  
     END IF;

     RETURN QUERY SELECT t.id AS testid,
       t.testname,
       t.numitems,
       t.uitypecode,
       t.begininstructions AS testbegininstructions,
       t.endinstructions AS testendinstructions,
       CASE
           WHEN testexitquery.testexitcode IS NULL THEN FALSE
           ELSE TRUE
       END AS testexitflag,
       (select tp.id from test t join testpart tp on t.id = tp.testid where t.id=_testid and tp.partnumber = testpartnumber LIMIT 1) AS testsectionid,
       ts.testid,
       ts.testsectionname,
       ts.numberoftestitems,
       ts.begininstructions,
       ts.endinstructions,
       ts.helpnotes,
       ts.hardbreak,
       tstool.id AS testsectiontoolid,
       tstool.name AS testsectiontoolname,
       tstool.codename AS testsectiontoolcodename,
       tstool.tooltypeid AS testsectiontooltypeid,
       tsr.navigation,
       c.id AS ruleid,
       c.categoryname,
       c.categorycode,
       c.categorydescription,
       c.categorytypeid,
       deliveryrule.categorycode AS taskdeliveryrulecode,
       tv.id AS taskvariantid,
       tv.taskname,
       tv.taskstem,
       tv.itemdescription,
       tv.testlet, 
       tv.numberofresponses,
       tv.shuffled,
       tv.innovativeitempackagepath,
       tv.directions AS taskdirections,
       tv.cue,
       tv.completion,
       tstv.groupnumber AS tasksectiongroupnumber,
       tstv.taskvariantposition AS taskpositioninsection,
       f.id AS foilid,
       f.foiltext,
       tvf.responseorder,
       tvf.correctresponse,
       tvf.responsescore,
       tt.code AS tasktypecode,
       tl.layoutcode AS tasklayoutcode,
       tlf.formatcode AS tasklayoutformatcode,
       ttl.id AS toolid,
       ttl.name,
       ttl.codename,
       ttl.tooltypeid,
       tvcg.id AS tvcgid,
       tvcg.externalid AS tvcgexternalid,
       tvcg.accesselementid AS tvcgaccesselementid,
       tvcg.spanstart AS tvcgspanstart,
       tvcg.spanend AS tvcgspanend,
       tvcg.charindexstart AS tvcgcharindexstart,
       tvcg.charindexend AS tvcgcharindexend,
       tvcg.taskvariantid AS tvcgtaskvariantid,
       tvcg.foilid AS tvcgfoilid,
       tvcg.createuserid AS tvcgcreateuserid,
       rac.id AS racid,
       rac.externalid AS racexternalid,
       rac.contentgroupid AS raccontentgroupid,
       rac.readaloudtypeid AS racreadaloudtypeid,
       rac.synthetic AS racsynthetic,
       rac.syntheticpronoun AS racsyntheticpronoun,
       rac.human AS rachuman,
       rac.defaultorder AS racdefaultorder,
       rac.alternateorder AS racalternateorder,
       rac.accessibilityfileid AS racaccessibilityfileid,
       rac.starttime AS racstarttime,
       rac.endtime AS racendtime,
       rac.createuserid AS raccreateuserid,
       rac.createdate AS raccreatedate,
       rac.modifieddate AS racmodifieddate,
       rac.modifieduserid AS racmodifieduserid,
       rac.originationcode AS racoriginationcode,
       af.id AS afid,
       af.externalid AS afexternalid,
       af.filename AS affilename,
       af.filelocation AS affilelocation,
       af.filesize AS affilesize,
       af.filetypeid AS affiletypeid,
       af.taskvariantid AS aftaskvariantid,
       af.assessmentprogramid AS afassessmentprogramid,
       af.duration AS afduration,
       af.originationcode AS aforiginationcode,
       sv.id AS stimulusvariantid,
       sv.stimuluscontent,
       contextstimulus.id AS contextstimulusid,
       contextstimulus.stimuluscontent AS contextstimuluscontent,
       sva.id AS stimulusvariantattachmentid,
       sva.attachmentname,
       sva.filename,
       sva.filelocation,
       sva.filesize,
       sva.filetype,
       contextsa.id AS contextstimulusattachmentid,
       contextsa.attachmentname AS contextstimulusattachmentname,
       contextsa.filename AS contextstimulusfilename,
       contextsa.filelocation AS contextstimulusfilelocation,
       contextsa.filesize AS contextstimulusfilesize,
       contextsa.filetype AS contextstimulusfiletype,
       tstv.testletid AS tsktestletid,
       tstl.id AS testletid,
       tstl.testletname,
       tstl.testingprogramid,
       tstl.contentareaid,
       tstl.gradecourseid,
       tstl.testletlayoutid,
       tstl.questionviewid,
       tstl.questionlocked,
       tstl.displayviewid,
       tstl.stimulusneeded,
       tstlqvi.categorycode AS testletquestionviewcode,
       tstlly.categorycode AS testletlayoutcode,
       tstldvi.categorycode AS testletdisplayviewcode,
       tstl2sv.id AS testletstimulusvariantid,
       tstl2sv.stimuluscontent AS testletstimuluscontent,
       tstl2sv.stimulustitle AS testletstimulustitle,
       tstlsv.groupnumber AS testletgroupnumber,
       tstlsv.sortorder AS testletsortorder
FROM test t
LEFT OUTER JOIN
  (SELECT t.id,
          sessionrule.categorycode AS testexitcode
   FROM test t
   INNER JOIN testcollectionstests tct ON t.id=tct.testid
   INNER JOIN testcollection tstcol ON tct.testcollectionid=tstcol.id
   LEFT OUTER JOIN testcollectionssessionrules tcsr ON tcsr.testcollectionid=tstcol.id
   INNER JOIN category sessionrule ON tcsr.sessionruleid = sessionrule.id
   AND sessionrule.categorycode='NOT_REQUIRED_TO_COMPLETE_TEST'
   WHERE t.id = _testid) AS testexitquery ON t.id=testexitquery.id
LEFT OUTER JOIN testsection ts ON t.id = ts.testid
LEFT OUTER JOIN public.testsectionstaskvariants tstv ON ts.id = tstv.testsectionid
LEFT OUTER JOIN testlet tstl ON tstl.id=tstv.testletid
LEFT OUTER JOIN testletstimulusvariants tstlsv ON tstl.id=tstlsv.testletid
LEFT OUTER JOIN stimulusvariant tstl2sv ON tstl2sv.id=tstlsv.stimulusvariantid
LEFT OUTER JOIN public.category tstlqvi ON tstl.questionviewid = tstlqvi.id
LEFT OUTER JOIN public.category tstlly ON tstl.testletlayoutid = tstlly.id
LEFT OUTER JOIN public.category tstldvi ON tstl.displayviewid = tstldvi.id
LEFT OUTER JOIN public.testsectionstools tst ON ts.id = tst.testsectionid
LEFT OUTER JOIN public.tool tstool ON tst.toolid = tstool.id
LEFT OUTER JOIN public.testsectionsrules tsr ON ts.id = tsr.testsectionid
LEFT OUTER JOIN public.category c ON tsr.ruleid = c.id
LEFT OUTER JOIN public.category deliveryrule ON ts.taskdeliveryruleid = deliveryrule.id
LEFT OUTER JOIN public.taskvariant tv ON tstv.taskvariantid = tv.id
LEFT OUTER JOIN contentgroup tvcg ON tv.id=tvcg.taskvariantid
LEFT OUTER JOIN readaloudaccommodation rac ON tvcg.id=rac.contentgroupid
LEFT OUTER JOIN accessibilityfile af ON af.id=rac.accessibilityfileid
LEFT OUTER JOIN public.tasktype tt ON tv.tasktypeid = tt.id
LEFT OUTER JOIN public.tasklayout tl ON tv.tasklayoutid = tl.id
LEFT OUTER JOIN public.tasklayoutformat tlf ON tv.tasklayoutformatid = tlf.id
LEFT OUTER JOIN taskvariantsfoils tvf ON tv.id = tvf.taskvariantid
LEFT OUTER JOIN foil f ON tvf.foilid = f.id
LEFT OUTER JOIN taskvariantstools tvt ON tv.id = tvt.taskvariantid
LEFT OUTER JOIN tool ttl ON tvt.toolid = ttl.id
LEFT OUTER JOIN taskvariantsstimulusvariants tvsv ON tv.id = tvsv.taskvariantid
LEFT OUTER JOIN stimulusvariant sv ON tvsv.stimulusvariantid = sv.id
LEFT OUTER JOIN stimulusvariant contextstimulus ON tv.contextstimulusid = contextstimulus.id
LEFT OUTER JOIN public.stimulusvariantattachment sva ON sv.id = sva.stimulusvariantid
LEFT OUTER JOIN public.stimulusvariantattachment contextsa ON contextstimulus.id = contextsa.stimulusvariantid
WHERE t.id = _testid and ts.id in (select unnest(subsectionIds))
ORDER BY tstv.groupnumber,
         tstv.taskvariantposition,
         tvf.responseorder;
 
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

ALTER FUNCTION get_adaptive_test(bigint, bigint) OWNER TO aart;