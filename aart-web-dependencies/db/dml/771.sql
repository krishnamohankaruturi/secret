DO $BODY$ 
BEGIN
IF EXISTS (SELECT 1 FROM assessmentprogram WHERE abbreviatedname ='PLTW' and activeflag is true) THEN
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='bms-principlesofbiomedicalscience') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_bio','testingprogramcolor','bms-principlesofbiomedicalscience','card_bio',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='bms-humanbodysystems') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_bio','testingprogramcolor','bms-humanbodysystems','card_bio',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='bms-medicalinterventions') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_bio','testingprogramcolor','bms-medicalinterventions','card_bio',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='cs-computerscienceessentials') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_compsci','testingprogramcolor','cs-computerscienceessentials','card_compsci',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='cs-computerscienceprinciples') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_compsci','testingprogramcolor','cs-computerscienceprinciples','card_compsci',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='cs-computersciencea') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_compsci','testingprogramcolor','cs-computersciencea','card_compsci',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='cs-cybersecurity') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_compsci','testingprogramcolor','cs-cybersecurity','card_compsci',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-introductiontoengineeringdesign') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-introductiontoengineeringdesign','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-principlesofengineering') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-principlesofengineering','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-aerospaceengineering') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-aerospaceengineering','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-civilengineeringandarchitecture') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-civilengineeringandarchitecture','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-computerintegratedmanufacturing') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-computerintegratedmanufacturing','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-digitalelectronics') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-digitalelectronics','card_engg',12, now(), 12, now(), 2);
	END IF;
	IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrname='eng-environmentalsustainability') THEN
		INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
		VALUES ('card_engg','testingprogramcolor','eng-environmentalsustainability','card_engg',12, now(), 12, now(), 2);
	END IF;
    IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='testsperpage') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate, assessmentprogramid)
    			 VALUES ('testsperpage','count','testsperpage','12',12,now(),12,now(),2);
		END IF;
END IF;
END;
$BODY$;
