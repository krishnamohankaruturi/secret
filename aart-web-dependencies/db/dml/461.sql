-- 461 dml
 
 -- 461.sql
 
-- US16818, add CPASS test types and remove "I" test type
update fieldspecification set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	allowablevalues = '{2,3,A,B,D,E,F,G,GN,H,P,2Q,AQ,AM,BQ,DQ,DM,EM,FQ,GQ,HQ,HM}'
where id in (
	select fieldspecificationid
	from fieldspecificationsrecordtypes
	where recordtypeid = (select id from category where categorycode = 'TEC_RECORD_TYPE')
)
and fieldname = 'testType';

-- deactivate "I" test type
update testtype set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where id in (
	select id from testtype where testtypecode = 'I' and assessmentid in (
		select a.id
		from assessment a
		inner join testingprogram tp on a.testingprogramid = tp.id
		inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
		where ap.abbreviatedname = 'CPASS'
	)
);

update testtypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where testtypeid in (
	select id from testtype where testtypecode = 'I' and assessmentid in (
		select a.id
		from assessment a
		inner join testingprogram tp on a.testingprogramid = tp.id
		inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
		where ap.abbreviatedname = 'CPASS'
	)
);

update contentareatesttypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where testtypesubjectareaid in (
	select id from testtypesubjectarea where activeflag = false and testtypeid in (
		select id from testtype where testtypecode = 'I' and assessmentid in (
			select a.id
			from assessment a
			inner join testingprogram tp on a.testingprogramid = tp.id
			inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
			where ap.abbreviatedname = 'CPASS'
		)
	)
);

update gradecontentareatesttypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where contentareatesttypesubjectareaid in (
	select id from contentareatesttypesubjectarea where activeflag = false and testtypesubjectareaid in (
		select id from testtypesubjectarea where activeflag = false and testtypeid in (
			select id from testtype where testtypecode = 'I' and assessmentid in (
				select a.id
				from assessment a
				inner join testingprogram tp on a.testingprogramid = tp.id
				inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
				where ap.abbreviatedname = 'CPASS'
			)
		)
	)
);

-- add new test types
DO
$BODY$
DECLARE
	newttid BIGINT;
	newttsaid BIGINT;
	-- new data is basically an array of objects: [[test type code, test type name, subject code, content area code], [...]]
	newdata TEXT[][] := ARRAY[['2Q', 'General Assessment - CCQ', 'GCTEA', 'GKS'], ['AQ', 'Comprehensive Agriculture Assessment - CCQ', 'EOPA', 'AgF&NR'],
		['AM', 'Comprehensive Agriculture Assessment - Power, Structural and Technical Module', 'EOPA', 'AgF&NR'],
		['BQ', 'Animal Systems Assessment - CCQ', 'EOPA', 'AgF&NR'], ['DQ', 'Plant Systems Assessment - CCQ', 'EOPA', 'AgF&NR'],
		['DM', 'Plant Systems Assessment - Horticulture Module', 'EOPA', 'AgF&NR'], ['EM', 'Manufacturing Production Assessment - CCQ', 'EOPA', 'Mfg'],
		['FQ', 'Design and Pre Construction Assessment - CCQ', 'EOPA', 'Arch&Const'], ['GQ', 'Comprehensive Business Assessment - CCQ', 'EOPA', 'BM&A'],
		['HQ', 'Finance Assessment - CCQ', 'EOPA', 'BM&A'],	['HM', 'Finance Assessment - Accounting Module', 'EOPA', 'BM&A']];
BEGIN
	FOR i IN array_lower(newdata, 1) .. array_upper(newdata, 1)
	LOOP
		SELECT nextval('testtype_id_seq') INTO newttid;
		
		INSERT INTO testtype (id, testtypecode, testtypename, assessmentid, createduser,
			createdate, modifieddate, modifieduser, accessibilityflagcode, activeflag)
		VALUES
		(newttid,
		newdata[i][1],
		newdata[i][2],
		(SELECT DISTINCT a.id
			FROM assessment a
			INNER JOIN testingprogram tp ON a.testingprogramid = tp.id
			INNER JOIN assessmentprogram ap ON tp.assessmentprogramid = ap.id
			INNER JOIN testtype tt ON a.id = tt.assessmentid
			WHERE ap.abbreviatedname = 'CPASS' AND assessmentname = 'General'),
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		NOW(),
		NOW(),
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		NULL,
		TRUE);

		SELECT nextval('testtypesubjectarea_id_seq') INTO newttsaid;

		INSERT INTO testtypesubjectarea (id, testtypeid, subjectareaid, createduser,
			createdate, modifieddate, modifieduser, activeflag, assessmentid)
		VALUES
		(newttsaid,
		newttid,
		(SELECT id FROM subjectarea WHERE subjectareacode = newdata[i][3]),
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		NOW(),
		NOW(),
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		TRUE,
		(SELECT DISTINCT a.id
			FROM assessment a
			INNER JOIN testingprogram tp ON a.testingprogramid = tp.id
			INNER JOIN assessmentprogram ap ON tp.assessmentprogramid = ap.id
			INNER JOIN testtype tt ON a.id = tt.assessmentid
			WHERE ap.abbreviatedname = 'CPASS' AND assessmentname = 'General'));

		INSERT INTO contentareatesttypesubjectarea (id, contentareaid, testtypesubjectareaid, createduser,
			createdate, modifieddate, modifieduser, activeflag)
		VALUES
		(nextval('contentareatesttypesubjectarea_id_seq'),
		(SELECT id FROM contentarea WHERE abbreviatedname = newdata[i][4]),
		newttsaid,
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		now(),
		now(),
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
		TRUE);
	END LOOP;
END;
$BODY$;


-- Associate cPass test types to proper subject areas
update testtypesubjectarea set activeflag=false
 where testtypeid=2 and subjectareaid in (select id from subjectarea where subjectareacode in ('GKS', 'GCTEA')) and activeflag=true;
 
update testtypesubjectarea set subjectareaid=(select id from subjectarea where subjectareacode='GKS')
 where subjectareaid=(select id from subjectarea where subjectareacode='GCTEA') and activeflag=true;
 
update testtypesubjectarea set subjectareaid=(select id from subjectarea where subjectareacode='AgF&NR')
 where subjectareaid=(select id from subjectarea where subjectareacode='EOPA') and activeflag=true
  and testtypeid in (select id from testtype where testtypecode in ('AM','AQ','BQ','DM','DQ'));
  
update testtypesubjectarea set subjectareaid=(select id from subjectarea where subjectareacode='Mfg')
 where subjectareaid=(select id from subjectarea where subjectareacode='EOPA') and activeflag=true
  and testtypeid in (select id from testtype where testtypecode in ('EM'));

update testtypesubjectarea set subjectareaid=(select id from subjectarea where subjectareacode='Arch&Const')
 where subjectareaid=(select id from subjectarea where subjectareacode='EOPA') and activeflag=true
  and testtypeid in (select id from testtype where testtypecode in ('FQ'));

update testtypesubjectarea set subjectareaid=(select id from subjectarea where subjectareacode='BM&A')
 where subjectareaid=(select id from subjectarea where subjectareacode='EOPA') and activeflag=true
  and testtypeid in (select id from testtype where testtypecode in ('GQ','HQ','HM'));

update testtypesubjectarea set activeflag=false 
	where subjectareaid=(select id from subjectarea where subjectareacode='EOPA') and activeflag=true;

INSERT INTO testtypesubjectarea(testtypeid, subjectareaid, createduser, createdate, modifieddate, 
            modifieduser, activeflag, assessmentid)
    VALUES (2, (select id from subjectarea where subjectareacode='ELPA21'), 12, now(), now(), 
            12, true, 26);
            
            
-- US16702, KIDS Test record changes
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('comprehensiveAgAssessment', '{0,1,2,3,4,C}', NULL, NULL, 1, false, true, NULL, 'Comprehensive_Ag_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'comprehensiveAgAssessment' and mappedname='Comprehensive_Ag_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Comprehensive_Ag_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('animalSystemsAssessment', '{0,1,3,C}', NULL, NULL, 1, false, true, NULL, 'Animal_Systems_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'animalSystemsAssessment' and mappedname='Animal_Systems_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Animal_Systems_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('plantSystemsAssessment', '{0,1,2,3,4,C}', NULL, NULL, 1, false, true, NULL, 'Plant_Systems_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'plantSystemsAssessment' and mappedname='Plant_Systems_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Plant_Systems_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('manufacturingProdAssessment', '{0,1,3,C}', NULL, NULL, 1, false, true, NULL, 'Manufacturing_Prod_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'manufacturingProdAssessment' and mappedname='Manufacturing_Prod_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Manufacturing_Prod_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('designPreConstructionAssessment', '{0,1,3,C}', NULL, NULL, 1, false, true, NULL, 'Design_PreConstruction_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'designPreConstructionAssessment' and mappedname='Design_PreConstruction_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Design_PreConstruction_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('financeAssessment', '{0,1,2,3,4,C}', NULL, NULL, 1, false, true, NULL, 'Finance_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'financeAssessment' and mappedname='Finance_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Finance_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('comprehensiveBusinessAssessment', '{0,1,3,C}', NULL, NULL, 1, false, true, NULL, 'Comprehensive_Business_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'comprehensiveBusinessAssessment' and mappedname='Comprehensive_Business_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Comprehensive_Business_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('elpa21Assessment', '{0,1,2,C}', NULL, NULL, 1, false, true, NULL, 'ELPA21_Assess', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'elpa21Assessment' and mappedname='ELPA21_Assess'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'ELPA21_Assess');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingComprehensiveAg', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Comprehensive_Ag', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingComprehensiveAg' and mappedname='Grouping_Comprehensive_Ag'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Comprehensive_Ag');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingAnimalSystems', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Animal_Systems', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingAnimalSystems' and mappedname='Grouping_Animal_Systems'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Animal_Systems');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingPlantSystems', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Plant_Systems', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingPlantSystems' and mappedname='Grouping_Plant_Systems'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Plant_Systems');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingManufacturingProd', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Manufacturing_Prod', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingManufacturingProd' and mappedname='Grouping_Manufacturing_Prod'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Manufacturing_Prod');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingDesignPreConstruction', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Design_PreConstruction', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingDesignPreConstruction' and mappedname='Grouping_Design_PreConstruction'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Design_PreConstruction');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingFinance', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Finance', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingFinance' and mappedname='Grouping_Finance'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Finance');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingComprehensiveBusiness', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_Comprehensive_Business', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingComprehensiveBusiness' and mappedname='Grouping_Comprehensive_Business'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_Comprehensive_Business');

INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, createdate, modifieddate, modifieduser, activeflag)
    VALUES ('ELPA21', 'ELPA 21 test', (select id from aartuser where username='cetesysadmin'), now(), now(), (select id from aartuser where username='cetesysadmin'), true);
    
update fieldspecification set allowablevalues='{'''',ND,WD}' where id=(select id from fieldspecification where mappedname='Primary_Exceptionality_Code');
update fieldspecificationsrecordtypes set mappedname='Primary_Exceptionality_Code' 
		where fieldspecificationid=(select id from fieldspecification where mappedname='Primary_Exceptionality_Code');
--update fieldspecification set mappedname=null where id=(select id from fieldspecification where mappedname='Primary_Exceptionality_Code');

--renamed tags
update fieldspecification set mappedname = 'Grouping_Reading_1' where mappedname='Grouping_ELA_1';
update fieldspecification set mappedname = 'Grouping_Reading_2' where mappedname='Grouping_ELA_2';
update fieldspecification set mappedname = 'Grouping_History_1' where mappedname='Grouping_History_Gov_1';
update fieldspecification set mappedname = 'Grouping_History_2' where mappedname='Grouping_History_Gov_2';

update fieldspecification set mappedname = 'State_ELA_Assess' where mappedname='State_Reading_Assess';
update fieldspecification set mappedname = 'State_Hist_Gov_Assess' where mappedname='State_History_Assess';
update fieldspecification set mappedname = 'General_CTE_Assess' where mappedname='CTE_Assess';
update fieldspecification set mappedname = 'Math_DLM_Proctor_ID' where mappedname='Math_Proctor_ID';
update fieldspecification set mappedname = 'Math_DLM_Proctor_Name' where mappedname='Math_Proctor_Name';
update fieldspecification set mappedname = 'ELA_Proctor_ID' where mappedname='Reading_Proctor_ID';
update fieldspecification set mappedname = 'ELA_Proctor_Name' where mappedname='Reading_Proctor_Name';
update fieldspecification set mappedname = 'Science_DLM_Proctor_ID' where mappedname='Science_Proctor_ID';
update fieldspecification set mappedname = 'Science_DLM_Proctor_Name' where mappedname='Science_Proctor_Name';

update fieldspecification set allowablevalues='{0,1,3,C}' where id=(select id from fieldspecification where mappedname='General_CTE_Assess');

delete from fieldspecificationsrecordtypes where fieldspecificationid in( 
select fs.id from fieldspecification fs where 
fs.mappedname in (
'Birth_Date','Residence_Org_No','Local_Student_Identifier','Funding_Bldg_No','User_Field_1','User_Field_2','User_Field_3',
'USA_Entry_Date','First_Language','Grouping_Writing_1','Grouping_Writing_2','Grouping_KELPA_1','Grouping_KELPA_2',
'KELPA','K8_State_Sci_Assess','HS_State_Life_Sci_Assess','HS_State_Phys_Sci_Assess','K8_State_Hist_Gov_Assess',
'State_Writing_Assess','Grouping_HS_Life_Science_1','Grouping_HS_Life_Science_2','Grouping_HS_Phys_Science_1',
'Grouping_HS_Phys_Science_2','Grouping_HS_History_World_1','Grouping_HS_History_World_2',
'Grouping_HS_History_US_1','Grouping_HS_History_US_2'
)) and recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE');

--Adding two new disability codes for Kids(KS) into category table

--1. ND = No Disability
INSERT INTO category 
            (categoryname, 
             categorycode, 
             categorydescription, 
             categorytypeid, 
             originationcode, 
             createddate, 
             createduser, 
             activeflag, 
             modifieddate, 
             modifieduser) 
VALUES     ('No Disability', 
            'ND', 
            'No Disability', 
            (SELECT id 
             FROM   categorytype 
             WHERE  typecode = 'PRIMARY_DISABILITY_CODES'), 
            'AART', 
            Now(), 
            (SELECT id 
             FROM   aartuser 
             WHERE  username = 'cetesysadmin'), 
            true, 
            Now(), 
            (SELECT id 
             FROM   aartuser 
             WHERE  username = 'cetesysadmin')); 
		   
--2. WD = Documented Disability
INSERT INTO category 
            (categoryname, 
             categorycode, 
             categorydescription, 
             categorytypeid, 
             originationcode, 
             createddate, 
             createduser, 
             activeflag, 
             modifieddate, 
             modifieduser) 
VALUES     ('Documented Disability', 
            'WD', 
            'Documented Disability', 
            (SELECT id 
             FROM   categorytype 
             WHERE  typecode = 'PRIMARY_DISABILITY_CODES'), 
            'AART', 
            Now(), 
            (SELECT id 
             FROM   aartuser 
             WHERE  username = 'cetesysadmin'), 
            true, 
            Now(), 
            (SELECT id 
             FROM   aartuser 
             WHERE  username = 'cetesysadmin'));
       