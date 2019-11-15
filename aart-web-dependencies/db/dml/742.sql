----DML-----F770- TDE default homepagetitle entry

DO $BODY$
begin
       IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrvalue='Kite Student Portal') THEN
                 INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag) 
                 VALUES ('homepage_title_message', 'messages', 'homepagetitle', 'Kite Student Portal', true);  
       END IF; 
end; $BODY$;
