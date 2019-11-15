
--R8 - Iter 2 

--US12393 Name: First Contact Survey - Submit Survey Status 

CREATE OR REPLACE FUNCTION sp_update_survey_status() RETURNS INTEGER AS
$BODY$
DECLARE
	row_data survey.id%TYPE;
        count integer;
        status boolean;
        
BEGIN
	 
	FOR row_data IN SELECT distinct id FROM survey LOOP
		select count(distinct iscompleted) into count from surveypagestatus where surveyid = row_data;
		
		IF count = 2 THEN
			 
			UPDATE survey set status=(select c.id from category c ,categorytype ct where ct.typecode='SURVEY_STATUS' 
			and ct.id=c.categorytypeid and c.categorycode='IN_PROGRESS') where id = row_data;
		ELSE IF count=0 THEN
			UPDATE survey set status=(select c.id from category c ,categorytype ct where ct.typecode='SURVEY_STATUS'
			 and ct.id=c.categorytypeid and c.categorycode='NOT_STARTED') where id = row_data;
		  
		   ELSE 
			select distinct iscompleted into status   from surveypagestatus where surveyid = row_data;
			IF status = true THEN
			UPDATE survey set status=(select c.id from category c ,categorytype ct where ct.typecode='SURVEY_STATUS'
			 and ct.id=c.categorytypeid and c.categorycode='COMPLETE') where id = row_data;
			ELSE 
			UPDATE survey set status=(select c.id from category c ,categorytype ct where ct.typecode='SURVEY_STATUS'
			 and ct.id=c.categorytypeid and c.categorycode='NOT_STARTED') where id = row_data;
			 END IF;	
		 END IF;
		   	
		END IF;              
	END LOOP;
	return 1;
END;
$BODY$
LANGUAGE 'plpgsql';

select * from sp_update_survey_status();

DROP FUNCTION IF EXISTS sp_update_survey_status();
