--dml/671.sql

-- Index: idx_studentpassword_word
-- DROP INDEX idx_studentpassword_word;

CREATE INDEX idx_studentpassword_word
  ON studentpassword
  USING btree
  (word );

-- Function: generate_student_password(bigint)

-- DROP FUNCTION generate_student_password(bigint);

CREATE OR REPLACE FUNCTION generate_student_password(maxpasswordlength bigint)
  RETURNS text AS
$BODY$
DECLARE
	MAX_PASSWORD_LENGTH INTEGER := maxpasswordlength;
	password TEXT;
	chars_needed INTEGER;
	i INTEGER;
	rndnum TEXT;
BEGIN
	SELECT word
	FROM studentpassword
	WHERE char_length(word) <= MAX_PASSWORD_LENGTH
	and (word not ilike '%i%' and  word not ilike '%l%' and word not ilike '%o%')
	ORDER BY random()
	LIMIT 1
	INTO password;
	rndnum='0';
	SELECT MAX_PASSWORD_LENGTH - char_length(password) INTO chars_needed;

	 WHILE ((rndnum like '%0%' or  rndnum like '%1%') and chars_needed>0)
				 
			loop
			RAISE INFO 'Affected Students : %', rndnum;
				select get_random_number(
				(select (10 ^ (chars_needed - 1))::INTEGER), -- lowest number with # digits = chars_needed
				(select (10 ^ chars_needed)::INTEGER))::text INTO rndnum;
				
			end loop;
	
	IF chars_needed > 0 THEN
		SELECT concat(
			password,rndnum
			) -- lowest number with # digits = chars_needed + 1
		INTO password;
	END IF;

	--RAISE NOTICE 'return password', password;
	
	RETURN UPPER(password);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;


-- Function: generate_student_password(bigint)

-- DROP FUNCTION generate_student_password(bigint);

CREATE OR REPLACE FUNCTION generate_student_password(maxpasswordlength bigint)
  RETURNS text AS
$BODY$
DECLARE
	MAX_PASSWORD_LENGTH INTEGER := maxpasswordlength;
	password TEXT;
	chars_needed INTEGER;
	i INTEGER;
	rndnum TEXT;
BEGIN
	SELECT word
	FROM studentpassword
	WHERE char_length(word) <= MAX_PASSWORD_LENGTH
	and (word not ilike '%i%' and  word not ilike '%l%' and word not ilike '%o%')
	and upper(word) not in ('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','SEPT','OCT','NOV','DEC')
	ORDER BY random()
	LIMIT 1
	INTO password;
	rndnum='0';
	SELECT MAX_PASSWORD_LENGTH - char_length(password) INTO chars_needed;

	 WHILE ((rndnum like '%0%' or  rndnum like '%1%') and chars_needed>0)
				 
			loop
			RAISE INFO 'Affected Students : %', rndnum;
				select get_random_number(
				(select (10 ^ (chars_needed - 1))::INTEGER), -- lowest number with # digits = chars_needed
				(select (10 ^ chars_needed)::INTEGER))::text INTO rndnum;
				
			end loop;
	
	IF chars_needed > 0 THEN
		SELECT concat(
			password,rndnum
			) -- lowest number with # digits = chars_needed + 1
		INTO password;
	END IF;

	--RAISE NOTICE 'return password', password;
	
	RETURN UPPER(password);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
