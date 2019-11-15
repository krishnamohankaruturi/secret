-- TDE Accessibility

DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='zoomin') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser,modifieddate)
    			 VALUES ('zoomin','tdeaccessibility','zoomin','Zoom in',12,now(),12,now());
		END IF;
				 
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='zoomout') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('zoomout','tdeaccessibility','zoomout','Zoom out',12,now(),12,now());
		END IF; 

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ttsread') THEN
				INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			VALUES ('ttsRead','tdeaccessibility','ttsRead','Hear question read',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='ttsPlay') THEN
				INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			VALUES ('ttsPlay','tdeaccessibility','ttsPlay','Hear question read',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='arrowBackIcon') THEN
				INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			VALUES ('arrowBackIcon','tdeaccessibility','arrowBackIcon','Go back one',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='buttonsExitSign') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			VALUES ('buttonsExitSign','tdeaccessibility','buttonsExitSign','Exit Without Saving',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='arrowIcon') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			VALUES ('arrowIcon','tdeaccessibility','arrowIcon','Go foward one',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='helpOverlay') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('helpOverlay','tdeaccessibility','helpOverlay','Help for how to answer question',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='alttests_per_page') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('alttests_per_page','tdeaccessibility','alttestsperpage','10',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='alttests_per_page') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('gentests_per_page','tdeaccessibility','gentestsperpage','3',12,now(),12,now());
		END IF;

		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='alttextimg') THEN
                INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
   				VALUES ('alttextimg','tdeaccessibility','alternatetext','No Description Available',12,now(),12,now());
		END IF;
end; $BODY$; 