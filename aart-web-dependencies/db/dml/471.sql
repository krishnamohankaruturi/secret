-- data corrections
update gradecourse 
	set contentareaid = (select id from contentarea where name = 'General Knowledge and Skills'  and abbreviatedname = 'G') 
	where name = 'Research' and contentareaid is null;

-- moving state scorer to school level	
update groups 
	set organizationtypeid=(select id from organizationtype where typecode='SCH' and activeflag is true),
	roleorgtypeid=(select id from organizationtype where typecode='SCH' and activeflag is true) 
where groupcode='SSCO';
