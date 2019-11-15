--US17502

--Cleanup KS students primary disability codes
UPDATE student 
SET primarydisabilitycode = UPPER(primarydisabilitycode) 
WHERE (primarydisabilitycode ~* 'WD' OR primarydisabilitycode ~* 'ND') 
	AND stateid = 51 
	AND primarydisabilitycode is not null;

UPDATE student 
SET primarydisabilitycode = NULL 
WHERE primarydisabilitycode NOT IN ('ND','WD') 
	AND stateid = 51  
	AND primarydisabilitycode is not null 
	AND length(primarydisabilitycode) >=1;

--Cleanup Other states students primary disability codes

UPDATE student 
SET primarydisabilitycode = UPPER(primarydisabilitycode) 
WHERE lower(primarydisabilitycode) IN (select lower(categorycode) from category where categorytypeid = 
				(SELECT id FROM categorytype WHERE typecode LIKE 'PRIMARY_DISABILITY_CODES')) 
		AND stateid <> 51  
		AND primarydisabilitycode is not null;

UPDATE student 
SET primarydisabilitycode = NULL 
WHERE primarydisabilitycode 
	NOT IN (select categorycode from category where categorytypeid = 
				(select id from categorytype where typecode like 'PRIMARY_DISABILITY_CODES')) 
	AND stateid <> 51
	AND primarydisabilitycode is not null 
	AND length(primarydisabilitycode) >=1;	
	
update student set primarydisabilitycode= null where stateid is null;	
update student set primarydisabilitycode=null where stateid is not null and primarydisabilitycode='';
