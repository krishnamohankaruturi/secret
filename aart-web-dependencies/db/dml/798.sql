-- jdoodle secrets

DO 
$BODY$ 
BEGIN
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='jdoodle_client_id') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('jdoodle_client_id','jdoodle','clientid','',12,now(),12,now());
		END IF;
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='jdoodle_client_secret') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('jdoodle_client_secret','jdoodle','clientsecret','',12,now(),12,now());
		END IF;
END; 
$BODY$;
