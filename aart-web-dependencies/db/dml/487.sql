-- US16973
-- populate gradecontentareatesttypesubjectarea for new test types in CPASS auto enrollment

DO
$BODY$
DECLARE
	newttid BIGINT;
	newttsaid BIGINT;
	-- data is an array of objects: [[test type code, subject area code, grade course code, content area code], [...]]
	data TEXT[][] := ARRAY[
		['2', 'GKS', 'Comp', 'GKS'],
		['2Q', 'GKS', 'Comp', 'GKS'],
		['AM', 'AgF&NR', 'CompAg', 'AgF&NR'],
		['AQ', 'AgF&NR', 'CompAg', 'AgF&NR'],
		['BQ', 'AgF&NR', 'Asys', 'AgF&NR'],
		['DM', 'AgF&NR', 'Psys', 'AgF&NR'],
		['DQ', 'AgF&NR', 'Psys', 'AgF&NR'],
		['EM', 'Mfg', 'Prod', 'Mfg'],
		['FQ', 'Arch&Const', 'D&PreCon', 'Arch&Const'],
		['GQ', 'BM&A', 'CompBus', 'BM&A'],
		['HM', 'BM&A', 'Fin', 'BM&A'],
		['HQ', 'BM&A', 'Fin', 'BM&A']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1)
	LOOP
		insert into gradecontentareatesttypesubjectarea (contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieduser, modifieddate)
		values
		(
			(
				select cattsa.id
				from contentareatesttypesubjectarea cattsa
				inner join contentarea ca on cattsa.contentareaid = ca.id
				inner join testtypesubjectarea ttsa on cattsa.testtypesubjectareaid = ttsa.id
				inner join testtype tt on ttsa.testtypeid = tt.id
				inner join subjectarea sa on ttsa.subjectareaid = sa.id
				where ca.activeflag = true and tt.activeflag = true and sa.activeflag = true and ttsa.activeflag = true
				and tt.testtypecode = data[i][1] and sa.subjectareacode = data[i][2]
				limit 1
			),
			(select id from gradecourse where activeflag = true
				and abbreviatedname = data[i][3] and contentareaid = (select id from contentarea where activeflag = true and abbreviatedname = data[i][4]) limit 1),
			(select id from aartuser where username = 'cetesysadmin'),
			now(),
			(select id from aartuser where username = 'cetesysadmin'),
			now()
		);
	END LOOP;
END;
$BODY$;

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,categorytypeid, externalid, originationcode) VALUES ('Braille', 'BRAILLE', 'Braille', (select id from categorytype where typecode='ACCESSIBILITY_FILE'), 4, 'CB');