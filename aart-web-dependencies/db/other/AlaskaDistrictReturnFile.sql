--AMP return file for each district in the state of Alaska

DO
LANGUAGE plpgsql
$$
DECLARE
    districtId integer;
    districtDisplayIdentifier  character varying(100);
BEGIN
    FOR districtId, districtDisplayIdentifier IN select org.id,org.displayidentifier from organization org where org.id in(select id from organization_children(35999)) and org.organizationtypeid = 5 order by org.organizationname

	LOOP
		EXECUTE format($x$COPY (		      
			SELECT
			st.statestudentidentifier AS Student_State_ID,
			array_to_string(array(Select distinct gc.name from studentstests sts  
				JOIN testcollection tc ON tc.id = sts.testcollectionid
				JOIN testsession tsn ON tsn.id = sts.testsessionid
			        JOIN gradecourse gc ON gc.id = tsn.gradecourseid
				where sts.studentid = st.id 
				and sts.activeflag = true and sts.enrollmentid = en.id and sts.status in (84,85,86) 
				and sts.testcollectionid in (2308,1365,1451,1380,1422,1366,1377,2301,1378,1371,1370,1421,1367,1513,1420,1428) order by gc.name ), ','  )  AS Grade_tested,				
			(select displayidentifier from organization where id=en.attendanceschoolid) as Attendance_School_ID,
			(select displayidentifier from organization where id=(select parentorganizationid from organizationrelation where organizationid=en.attendanceschoolid)) as District_Display_ID,
			st.legallastname AS Student_Legal_Last_Name,
			st.legalfirstname AS Student_Legal_First_Name,
			st.legalmiddlename AS Student_Legal_Middle_Name,
			st.generationcode AS Generation_Code,
			CASE WHEN st.gender = 0 THEN 'Female' ELSE 'Male' END AS Gender,
			st.comprehensiverace AS Comprehensive_Race,
			st.primarydisabilitycode AS Primary_Disability_Code,
			en.giftedstudent AS  Gifted_Student,
			st.hispanicethnicity AS Hispanic_Ethnicity,
			st.firstlanguage AS First_Language,
			st.esolparticipationcode AS ESOL_Participation_Code,

			(Select sc.specialcircumstancetype from studentstests sts
				JOIN testcollection tc ON tc.id = sts.testcollectionid
				JOIN studentspecialcircumstance ssc ON ssc.studenttestid = sts.id
				JOIN specialcircumstance sc ON sc.id = ssc.specialcircumstanceid and sc.activeflag=true
				where tc.contentareaid = 3 and sts.studentid = st.id 
				and sts.activeflag = true and sts.enrollmentid = en.id and sts.status in (84,85,86)
				and sts.testcollectionid in (2308,1365,1451,1380,1422,1366,1377,2301,1378,1371,1370,1421,1367,1513,1420,1428))   AS ELA_Reason_Not_Tested_Text,
			(Select sc.specialcircumstancetype from studentstests sts  
				JOIN testcollection tc ON tc.id = sts.testcollectionid
				JOIN studentspecialcircumstance ssc ON ssc.studenttestid = sts.id
				JOIN specialcircumstance sc ON sc.id = ssc.specialcircumstanceid and sc.activeflag=true
				where tc.contentareaid = 440 and sts.studentid = st.id 
				and sts.activeflag = true and sts.enrollmentid = en.id and sts.status in (84,85,86)
				and sts.testcollectionid in (2308,1365,1451,1380,1422,1366,1377,2301,1378,1371,1370,1421,1367,1513,1420,1428))  AS Math_Reason_Not_Tested_Text,
				
			-- PNP section
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'ColourOverlay') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Display_Overlay_Color, 
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'InvertColourChoice') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Display_Invert_Color_Choice,
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'Masking') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Display_Masking,
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'BackgroundColor') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Display_Contrast_Color,	
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'Signing') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Language_Signing_Type,	
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'Braille') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Language_Braille,
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'AuditoryBackground') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Auditory_Background,
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'Spoken') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Auditory_Spoken_Audio,
			(SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'preferenceSubject'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)
				as PNP_Auditory_Spoken_Audio_Subject_Setting,
			(SELECT CASE WHEN (SELECT selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattributecontainer piac ON pianac.attributecontainerid = piac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND spiav.activeflag = 't'
				WHERE studentid = st.id
				AND attributename = 'assignedSupport'
				AND attributecontainer = 'Spoken') = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Auditory_Switches,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'separateQuiteSetting'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Separate_quiet_or_individual_setting,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'readsAssessmentOutLoud'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Presentation_Student_reads_assessment_aloud_to_self,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'useTranslationsDictionary'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Presentation_Student_Used_Translation_dictionary,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'dictated'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Response_Student_dictated_answers_to_scribe,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'usedCommunicationDevice'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Response_Student_used_a_communication_device,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'signedResponses'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Response_Student_signed_responses,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'largePrintBooklet'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Provided_by_Alternate_Form_Large_Print,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'paperAndPencil'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Provided_by_Alternate_Form_Paper_and_Pencil,
			(SELECT CASE WHEN ( select selectedvalue
				FROM studentprofileitemattributevalue spiav
				INNER JOIN profileitemattributenameattributecontainer pianac ON spiav.profileitemattributenameattributecontainerid = pianac.id
				INNER JOIN profileitemattribute pia ON pianac.attributenameid = pia.id
				AND attributename = 'supportsTwoSwitch'
				AND spiav.activeflag = 't'
				WHERE studentid = st.id)  = 'true' THEN 'Yes' ELSE 'No' END) as PNP_Other_Supports_Requiring_Additional_Tools_Two_Switch_System,


				--ELA_Scale_Score
			(select scalescore from studentreport 
				where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
					and studentid= st.id and enrollmentid = en.id and status is true limit 1) AS ELA_Scale_Score,					
				--ELA_Standard_Error
			(select standarderror from studentreport 
				where contentareaid=(select id from contentarea where abbreviatedname='ELA') 
					and studentid= st.id and enrollmentid = en.id and status is true limit 1) AS ELA_Standard_Error,
				--ELA_Achievement_Level_Number
			(select level from studentreport sr, leveldescription ld 
				where sr.levelid=ld.id and sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
					and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA') limit 1) AS ELA_Achievement_Level_Number ,
				--ELA_Incomplete_Flag
			(select CASE WHEN (incompletestatus is true) THEN 'Incomplete' ELSE '' END from studentreport sr
				where sr.studentid=st.id and sr.enrollmentid = en.id
					and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA') limit 1) AS ELA_Incomplete_Flag,
				--ELA_Student_Report_Generated
			(select CASE WHEN (status is true) THEN 'Yes' ELSE 'No' END from studentreport sr
				where sr.studentid=st.id and sr.enrollmentid = en.id
				and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA') limit 1) AS ELA_Student_Report_Generated,
				
			(select CASE WHEN (status is true and aggregates is true) THEN 'Yes' ELSE 'No' END from studentreport sr
				where sr.studentid=st.id and sr.enrollmentid = en.id
				and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA') limit 1) AS ELA_Aggregates_Included,
	
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS ELA_Subscore1_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS ELA_Subscore1_Scale_Score,
		
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS ELA_Subscore1_Standard_Error,
		
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2 limit 1) AS ELA_Subscore2_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2 limit 1) AS ELA_Subscore2_Scale_Score,
		
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2 limit 1) AS ELA_Subscore2_Standard_Error,
		
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=3 limit 1) AS ELA_Subscore3_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=3 limit 1) AS ELA_Subscore3_Scale_Score,
		
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=3 limit 1) AS ELA_Subscore3_Standard_Error,
		
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=4 limit 1) AS ELA_Subscore4_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=4 limit 1) AS ELA_Subscore4_Scale_Score,
		
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='ELA')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=4 limit 1) AS ELA_Subscore4_Standard_Error,
						
			(select scalescore from studentreport 
				where contentareaid=(select id from contentarea where abbreviatedname='M') 
					and studentid= st.id and enrollmentid = en.id and status is true limit 1) AS Math_Scale_Score,							
			(select standarderror from studentreport 
				where contentareaid=(select id from contentarea where abbreviatedname='M') 
					and studentid= st.id and enrollmentid = en.id and status is true limit 1) AS Math_Standard_error,		
			(select level from studentreport sr, leveldescription ld 
			where sr.levelid=ld.id and sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M') limit 1) AS Math_Achievement_Level_Number,
		
			(select CASE WHEN (incompletestatus is true) THEN 'Incomplete' ELSE '' END from studentreport sr
			where sr.studentid=st.id and sr.enrollmentid = en.id
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M') limit 1) AS Math_Incomplete_Flag,
		
			(select CASE WHEN (status is true) THEN 'Yes' ELSE 'No' END from studentreport sr
			where sr.studentid=st.id and sr.enrollmentid = en.id
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M') limit 1) AS Math_Student_Report_Generated,

			(select CASE WHEN (status is true and aggregates is true) THEN 'Yes' ELSE 'No' END from studentreport sr
			where sr.studentid=st.id and sr.enrollmentid = en.id
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M') limit 1) AS Math_Aggregates_Included,				
					
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS Math_Subscore1_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS Math_Subscore1_Scale_Score,
		
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=1 limit 1) AS Math_Subscore1_Standard_Error,
		
			(select ssd.subscorereportdisplayname
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2 limit 1) AS Math_Subscore2_Report_Display_Name,
		
			(select rss.subscorescalescore
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2  limit 1) AS Math_Subscore2_Scale_Score,
			(select rss.subscorestandarderror
			from subscoresdescription ssd, reportsubscores rss, studentreport sr  
			where sr.studentid=st.id and sr.enrollmentid = en.id and sr.status is true
			and sr.contentareaid=(select id from contentarea where abbreviatedname='M')
			and rss.studentreportid = sr.id
			and rss.subscoredefinitionname=ssd.subscoredefinitionname
			and ssd.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP') 
			and ssd.subjectid=sr.contentareaid
			and ssd.report='Student' and ssd.subscoredisplaysequence=2 limit 1) AS Math_Subscore2_Standard_Error
			
			FROM student st
			INNER JOIN enrollment en ON (st.id = en.studentid and en.currentschoolyear = 2015
						and (en.activeflag OR ((NOT en.activeflag) AND (en.exitwithdrawaltype IS NOT NULL))))
			INNER JOIN gradecourse gc ON gc.id = en.currentgradelevel
			INNER JOIN organization og ON en.attendanceschoolid = og.id
			WHERE st.activeflag is true and en.attendanceschoolid in (select id from organization_children(%1$L)) 
			and og.reportprocess = true and st.stateid = 35999 
			and st.id in (SELECT distinct studentid from studentstests
				where testcollectionid in (2308,1365,1451,1380,1422,1366,1377,2301,1378,1371,1370,1421,1367,1513,1420,1428)) 
		) TO '/srv/pg_data/postgres/AlaskaDistrictReturnFile-%s.csv' WITH DELIMITER ',' CSV HEADER$x$, districtId, districtDisplayIdentifier);
	END LOOP;
END;
$$