
--R8 - Iter 1 - - file 4

--US12596 Name: First Contact Survey - Motor Capabilities- Walking and all FC stories 

CREATE OR REPLACE FUNCTION sp_update_fs_sections() RETURNS INTEGER AS
$BODY$
DECLARE
	row_data surveypagestatus.id%TYPE;
        count integer;
BEGIN
	FOR row_data IN SELECT distinct surveyid FROM surveypagestatus LOOP
		select count(*) INTO count from surveypagestatus where surveyid = row_data;
		IF count < 20 THEN
			UPDATE surveypagestatus set globalpagenum = globalpagenum + 8 where surveyid = row_data;
			FOR i IN 1..8 LOOP
				INSERT INTO surveypagestatus (iscompleted,surveyid,globalpagenum,createddate,createduser,activeflag,modifieddate,modifieduser) 
					VALUES(false,row_data,i,now(),(Select id from aartuser where username='cetesysadmin') , true, now(), (Select id from aartuser where username='cetesysadmin') );
			END LOOP;
		END IF;              
	END LOOP;
	return 1;
END;
$BODY$
LANGUAGE 'plpgsql';


select * from sp_update_fs_sections();


DROP FUNCTION IF EXISTS sp_update_fs_sections();


--CB publishing stuff.
alter table testingprogram add column highstake boolean;