
--US14180
BEGIN;
create or replace function fix_usernames()
returns varchar as 
$BODY$
DECLARE
	studentrecord record;
	returnmessage varchar = 'success';
BEGIN
FOR studentrecord IN select id, lower(substring(regexp_replace(st.legalfirstname, '[^a-zA-Z]+', '') from 1 for 4) || '.' || substring(regexp_replace(st.legallastname, '[^a-zA-Z]+', '') from 1 for 4)) as newusername from student st
LOOP
		update student  set username = (select studentrecord.newusername||addon(count(1)) as modifiedUsername 
		from student us 
		where us.username = studentrecord.newusername 
			or us.username like studentrecord.newusername ||'.%') where id = studentrecord.id;
END LOOP;
RETURN returnmessage;	  
END;
$BODY$
LANGUAGE 'plpgsql';

select * from fix_usernames();

drop function if exists fix_usernames();
COMMIT;