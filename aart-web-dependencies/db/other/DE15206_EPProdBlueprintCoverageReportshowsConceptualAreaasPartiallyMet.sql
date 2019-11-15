-- Correcting the blueprint table for Grade-10 Math to align with the DLM blueprint requirements.
UPDATE blueprint SET numberrequired = 2, modifieddate = now()
     WHERE gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname= '10') 
	 AND contentareaid IN (SELECT id FROM contentarea WHERE abbreviatedname = 'M')
	 AND groupnumber in (47,48);
     

INSERT INTO public.blueprintessentialelements(
	blueprintid, essentialelementid, essentialelement, ordernumber)
	VALUES ((SELECT id FROM blueprint WHERE gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname= '10') 
	 AND contentareaid IN (SELECT id FROM contentarea WHERE abbreviatedname = 'M')
	 AND groupnumber = 47), (SELECT distinct essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'M.EE.HS.S.CP.1-5'),
		'M.EE.HS.S.CP.1-5', 4);
	
	
INSERT INTO public.blueprintessentialelements(
	blueprintid, essentialelementid, essentialelement, ordernumber)
	VALUES ((SELECT id FROM blueprint WHERE gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname= '10') 
	 AND contentareaid IN (SELECT id FROM contentarea WHERE abbreviatedname = 'M')
	 AND groupnumber = 47), (SELECT distinct essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'M.EE.HS.G.CO.4-5'), 
			'M.EE.HS.G.CO.4-5', 5);

INSERT INTO public.blueprintessentialelements(
	blueprintid, essentialelementid, essentialelement, ordernumber)
	VALUES ((SELECT id FROM blueprint WHERE gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname= '10') 
	 AND contentareaid IN (SELECT id FROM contentarea WHERE abbreviatedname = 'M')
	 AND groupnumber = 48), (SELECT distinct essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'M.EE.HS.S.CP.1-5'), 
			'M.EE.HS.S.CP.1-5', 5);
	
	
INSERT INTO public.blueprintessentialelements(
	blueprintid, essentialelementid, essentialelement, ordernumber)
	VALUES ((SELECT id FROM blueprint WHERE gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname= '10') 
	 AND contentareaid IN (SELECT id FROM contentarea WHERE abbreviatedname = 'M')
	 AND groupnumber = 48), (SELECT distinct essentialelementid FROM blueprintessentialelements WHERE essentialelement = 'M.EE.HS.G.CO.4-5'), 
		'M.EE.HS.G.CO.4-5', 6);
		