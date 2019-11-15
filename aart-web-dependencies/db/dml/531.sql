-- dml/531.sql

-- US17717, adding column for paper/pencil SC code mapping
DO
$BODY$
DECLARE
	-- [description, paper pencil mapping code]
	data TEXT[][] := ARRAY[
		['Absent', '1'],
		['Student Refusal', '2'],
		['Parent Refusal', '3'],
		['Medical Waiver', '4'],
		['Invalidation', '5']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		UPDATE statespecialcircumstance
		SET paperpencilcode = data[i][2]
		WHERE specialcircumstanceid = (
			SELECT sc.id
			FROM specialcircumstance sc
			INNER JOIN statespecialcircumstance ssc ON ssc.specialcircumstanceid=sc.id 
			WHERE ssc.stateid = (SELECT id FROM organization WHERE displayidentifier = 'AK' AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST'))
			AND sc.activeflag = TRUE
			AND description = data[i][1]
		)
		AND stateid = (
			SELECT id
			FROM organization
			WHERE displayidentifier = 'AK'
			AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
		);
	END LOOP;
END;
$BODY$;