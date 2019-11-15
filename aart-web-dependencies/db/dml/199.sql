update organization set contractingorganization=true 
	where id in (select organizationid from orgassessmentprogram) 
		and organizationtypeid in (select id from organizationtype where typecode='ST');