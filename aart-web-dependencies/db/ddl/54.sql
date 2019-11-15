
-- US12104 : Resolution (DE33570) Error found in TC70635: US10906_144_DataValidationCurrentGradeLevelValidValues
CREATE OR REPLACE FUNCTION update_gradecourseassesments() RETURNS INTEGER AS
$BODY$
DECLARE
	row_id record;
	--namecheck refcursor;
BEGIN
	
	FOR row_id IN SELECT * FROM assessmentprogramgrades
	LOOP 
	IF (row_id.gradename = 'First Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '1' or gradecourse.name = 'First' or gradecourse.name = 'Grade 1';	
			--RAISE INFO 'value : %',row_id.name;
	
		END IF;
	IF (row_id.gradename = 'Second Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '2' or gradecourse.name = 'Second' or gradecourse.name = 'Grade 2';
	
		END IF;
	IF (row_id.gradename = 'Third Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '3' or gradecourse.name = 'Third' or gradecourse.name = 'Grade 3';
		END IF;
	IF (row_id.gradename = 'Fourth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '4' or gradecourse.name = 'Fourth' or gradecourse.name = 'Grade 4';
		END IF;
	IF (row_id.gradename = 'Fifth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '5' or gradecourse.name = 'Fifth' or gradecourse.name = 'Grade 5';
		END IF;
	IF (row_id.gradename = 'Sixth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '6' or gradecourse.name = 'Sixth' or gradecourse.name = 'Grade 6';
		END IF;
	IF (row_id.gradename = 'Seventh Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '7' or gradecourse.name = 'Seventh' or gradecourse.name = 'Grade 7';
		END IF;
	IF (row_id.gradename = 'Eighth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '8' or gradecourse.name = 'Eighth' or gradecourse.name = 'Grade 8';
		END IF;
	IF (row_id.gradename = 'Ninth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '9' or gradecourse.name = 'Ninth' or gradecourse.name = 'Grade 9';
		END IF;
	IF (row_id.gradename = 'Tenth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '10' or gradecourse.name = 'Tenth' or gradecourse.name = 'Grade 10';
		END IF;
	IF (row_id.gradename = 'Eleventh Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '11' or gradecourse.name = 'Eleventh' or gradecourse.name = 'Grade 11';
		END IF;
	IF (row_id.gradename = 'Twelfth Grade') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '12' or gradecourse.name = 'Twelfth' or gradecourse.name = 'Grade 12';
		END IF;
	IF (row_id.gradename = 'Not Graded') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = 'Not Graded';
		END IF;
	IF (row_id.gradename = 'Birth–2 years old') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = 'Birth–2 years old';
		END IF;
	IF (row_id.gradename = '3-Yr-Old Preschooler') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '3-Yr-Old Preschooler';
		END IF;
	IF (row_id.gradename = '4-Yr-Old Preschooler') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '4-Yr-Old Preschooler';
		END IF;
	IF (row_id.gradename = '5-Yr-Old and Older Preschooler') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = '5-Yr-Old and Older Preschooler';
		END IF;
	IF (row_id.gradename = 'Four-Year-Old At-Risk') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = 'Four-Year-Old At-Risk';
		END IF;
	IF (row_id.gradename = 'Kindergarten') THEN
			UPDATE gradecourse set assessmentprogramgradesid = (select id from assessmentprogramgrades where gradename = row_id.gradename) from assessmentprogramgrades where gradecourse.name = 'Kindergarten';
		END IF;
	END LOOP;
	return 1;
END;
$BODY$
LANGUAGE 'plpgsql';


select * from update_gradecourseassesments();


DROP FUNCTION IF EXISTS update_gradecourseassesments();


