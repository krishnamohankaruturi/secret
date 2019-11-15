-- get ajax config values from database
DO 
$BODY$ 
BEGIN
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ajax_request_timeout') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('ajax_request_timeout','ajax','ajaxtimeout','60000',12,now(),12,now());
		END IF;
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ajax_request_retry_limit') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('ajax_request_retry_limit','ajax','ajaxretrylimit','5',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='savelist_request_timeout') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('savelist_request_timeout','ajax','savelistajaxtimeout','30000',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='savelist_request_retry_limit') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('savelist_request_retry_limit','ajax','savelistajaxretrylimit','5',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='s3_request_timeout') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('s3_request_timeout','ajax','s3ajaxtimeout','10000',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='s3_request_retry_limit') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('s3_request_retry_limit','ajax','s3ajaxretrylimit','10',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ajax_start_delay') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('ajax_start_delay','ajax','ajaxstarthookdelay','200',12,now(),12,now());
		END IF;
    
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ajax_stop_delay') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('ajax_stop_delay','ajax','ajaxstopthookdelay','1000',12,now(),12,now());
		END IF;
					
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='altui_ajax_start_delay') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('altui_ajax_start_delay','ajax','altuiajaxstarthookdelay','500',12,now(),12,now());
		END IF;
END; 
$BODY$; 


