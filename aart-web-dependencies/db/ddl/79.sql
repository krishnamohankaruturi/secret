-- US13041: Auto registration - Auto register Alternate population

update autoregistrationcriteria set assessmentid = (select id from assessment where assessmentcode = 'GL' order by modifieddate desc limit 1) 
	where assessmentid = (select id from assessment where assessmentcode = 'G');
		
		

