-- US16835, reset student passwords outside of QC states

-- function to get a number such that (start_int <= number < end_int)
CREATE OR REPLACE FUNCTION get_random_number(INTEGER, INTEGER) RETURNS INTEGER AS $$
DECLARE
	start_int ALIAS FOR $1;
	end_int ALIAS FOR $2;
BEGIN
	RETURN trunc(random() * (end_int - start_int) + start_int);
END;
$$ LANGUAGE 'plpgsql' STRICT;

-- function to generate a password using the same strategy as EP
CREATE OR REPLACE FUNCTION generate_student_password() RETURNS TEXT AS $$
DECLARE
	MAX_PASSWORD_LENGTH INTEGER := 5;
	password TEXT;
	chars_needed INTEGER;
	i INTEGER;
BEGIN
	SELECT word
	FROM studentpassword
	WHERE char_length(word) <= MAX_PASSWORD_LENGTH
	ORDER BY random()
	LIMIT 1
	INTO password;

	SELECT MAX_PASSWORD_LENGTH - char_length(password) INTO chars_needed;
	
	IF chars_needed > 0 THEN
		SELECT concat(
			password,
			(select get_random_number(
				(select (10 ^ (chars_needed - 1))::INTEGER), -- lowest number with # digits = chars_needed
				(select (10 ^ chars_needed)::INTEGER)))) -- lowest number with # digits = chars_needed + 1
		INTO password;
	END IF;

	RETURN password;
END;
$$ LANGUAGE 'plpgsql' STRICT;

UPDATE ONLY student
SET password = generate_student_password()
FROM studentassessmentprogram sap
INNER JOIN assessmentprogram ap ON sap.assessmentprogramid = ap.id
WHERE student.activeflag = TRUE AND sap.activeflag = TRUE AND ap.activeflag = TRUE
AND sap.studentid = student.id
AND ap.abbreviatedname = 'AMP' -- specific assessment program
AND student.stateid NOT IN ( -- specific states
	SELECT id
	FROM organization
	WHERE organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
	AND organizationname ~* 'QC'
	AND activeflag = TRUE
)
AND (SELECT COUNT(assessmentprogramid) FROM studentassessmentprogram WHERE activeflag = TRUE AND studentid = student.id) = 1;

DROP FUNCTION get_random_number(INTEGER, INTEGER);
DROP FUNCTION generate_student_password();