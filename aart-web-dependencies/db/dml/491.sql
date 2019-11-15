-- adding stageid data to contentareatesttypesubjectarea

DO
$BODY$
DECLARE
	newttid BIGINT;
	newttsaid BIGINT;
	-- data is an array of objects: [[test type code, stage code], [...]]
	data TEXT[][] := ARRAY[
		['2', 'Stg1'],
		['2Q', 'Prfrm'],
		['A', 'Stg1'],
		['AM', 'Stg2'],
		['AQ', 'Prfrm'],
		['B', 'Stg1'],
		['BQ', 'Prfrm'],
		['D', 'Stg1'],
		['DM', 'Stg2'],
		['DQ', 'Prfrm'],
		['E', 'Stg1'],
		['EM', 'Prfrm'],
		['F', 'Stg1'],
		['FQ', 'Prfrm'],
		['G', 'Stg1'],
		['GQ', 'Prfrm'],
		['H', 'Stg1'],
		['HM', 'Stg2'],
		['HQ', 'Prfrm']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1)
	LOOP
		UPDATE contentareatesttypesubjectarea
		SET stageid = (SELECT id FROM stage WHERE code = data[i][2])
		WHERE id =
			(SELECT cattsa.id
			FROM contentareatesttypesubjectarea cattsa
			INNER JOIN contentarea ca ON cattsa.contentareaid = ca.id
			INNER JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id
			INNER JOIN testtype tt ON ttsa.testtypeid = tt.id
			INNER JOIN subjectarea sa ON ttsa.subjectareaid = sa.id
			WHERE cattsa.activeflag = true AND ca.activeflag = true AND ttsa.activeflag = true AND tt.activeflag = true AND sa.activeflag = true
			AND tt.testtypecode = data[i][1]
			AND ttsa.assessmentid =
				(SELECT a.id
				FROM assessment a
				INNER JOIN testingprogram tp ON a.testingprogramid = tp.id
				INNER JOIN assessmentprogram ap ON tp.assessmentprogramid = ap.id
				WHERE ap.abbreviatedname = 'CPASS' AND a.assessmentname = 'General' AND tp.highstake = true));
	END LOOP;
END;
$BODY$;

--DE12165 from change pond
update fieldspecification set allowablevalues = '{Jr.,Jr,Sr.,Sr,II,III,IV,V,VI,VII,VIII,IX,'''',jr.,jr,sr.,sr,ii,iii,iv,v,vi,vii,viii,ix,JR.,JR,SR.,SR}'
where id in (SELECT distinct  
 fieldSpec.id
       FROM public.fieldspecification fieldSpec,
     public.fieldspecificationsrecordtypes fieldSpecRel,category cat
 WHERE  
   fieldSpec.id = fieldSpecRel.fieldspecificationid
   and cat.id =  fieldSpecRel.recordtypeid
   and fieldSpecRel.activeflag is true
   and fieldSpec.fieldname = 'generationCode');
   
--SIF updates
-- student stateProvinceId
update fieldspecification set mappedname='stateProvinceId' where fieldname='stateStudentIdentifier' and mappedname='@refId';

update fieldspecificationsrecordtypes set mappedname='stateProvinceId' where mappedname='@refId';

-- student schoolRefId
update fieldspecification set mappedname='enrollment/schoolRefId' 
where fieldname='attendanceSchoolProgramIdentifier' 
and mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier';

update fieldspecificationsrecordtypes set mappedname='enrollment/schoolRefId' 
where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier';

-- Gifted student
update fieldspecification set mappedname ='giftedTalented'
where fieldname='giftedStudent' and 
mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent';

update fieldspecificationsrecordtypes set mappedname =  'giftedTalented'
where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent';

-- Student disability code
update fieldspecification set mappedname = 'disability/primaryDisability/code'
where fieldname='primaryDisabilityCode' and mappedname = 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode';

update fieldspecificationsrecordtypes set mappedname = 'disability/primaryDisability/code'
where mappedname = 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode';

-- student date of birth
update fieldspecification set fieldname = 'dateOfBirth' where mappedname = 'demographics/birthDate';

--modify fieldspecification with allowable subject codes
update fieldspecification set allowablevalues ='{01,02,03,04,12,13,17,18,32,33,37,38,39,51,52,53,54,80,81,82,83,84}', fieldlength=2 where fieldname ='tascStateSubjectAreaCode';

--Added dateOfBirth as a valid field 
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, mappedname,createduser,modifieduser) 
VALUES ((select id from  fieldspecification where fieldname ='dateOfBirth' and mappedname='Birth_Date'), 
(select id from category where categorycode = 'KID_RECORD_TYPE'), 'Birth_Date', 
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));


