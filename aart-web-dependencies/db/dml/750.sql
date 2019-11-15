--dml/750.sql

----DML-----F723- TDE set default testsperpage entry & alternate tests per page entry

DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='alttests_per_page') THEN
                 INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag) 
                 VALUES ('alttests_per_page', 'tdeaccessibility', 'alttestsperpage', '10', true);  
		END IF;
				 
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='gentests_per_page') THEN
                 INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag) 
                 VALUES ('gentests_per_page', 'tdeaccessibility', 'gentestsperpage', '3', true);  
		END IF; 
end; $BODY$; 

-- ALTER TABLE public.appconfiguration DROP CONSTRAINT if exists appconfiguration_uq;
-- ALTER TABLE public.appconfiguration    ADD CONSTRAINT  appconfiguration_uq UNIQUE(attrcode);

-- insert statement to be used if unique constraint is present on attrcode column
-- INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag) 
-- VALUES ('gentests_per_page', 'tdeaccessibility', 'gentestsperpage', '3', true)
-- ON CONFLICT(attrcode) DO UPDATE SET attrvalue='3';

-- insert statement to be used if unique constraint is present on attrcode column
-- INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag) 
-- VALUES ('alttests_per_page', 'tdeaccessibility', 'alttestsperpage', '10', true)
-- ON CONFLICT(attrcode) DO UPDATE SET attrvalue='10';

