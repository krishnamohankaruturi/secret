-- TDE feature F822
DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='no_of_testlets_errorMessage_addition') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('no_of_testlets_errorMessage_addition','errorMsgForNoOfTestlets','errorMsgForNoOfTestlets','Please check back. A field test may follow in 30 minutes.',12,now(),12,now());
		END IF;
				UPDATE public.appconfiguration set attrvalue = 'Please check back. A field test may follow in 30 minutes.' where attrcode = 'no_of_testlets_errorMessage_addition';
end; $BODY$; 