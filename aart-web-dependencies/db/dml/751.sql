--dml/751.sql

-- TDE Accessibility
DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='zoomin') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('zoomin','tdeaccessibility','zoomin','Zoom in',12,now(),12,now());
		END IF;
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='zoomout') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('zoomout','tdeaccessibility','zoomout','Zoom out',12,now(),12,now());
		END IF; 
end; $BODY$;
