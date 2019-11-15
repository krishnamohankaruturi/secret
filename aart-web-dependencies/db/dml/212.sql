
--dml/212.sql
UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='ST') 
	WHERE groupname in ('Consortium Assessment Program Administrator', 'PD State Admin',
						'Test Administrator', 'Test Administrator (QC Person)', 'State Assessment Administrator');

UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='DT') 
	WHERE groupname in ('District Superintendent', 'District Test Coordinator', 'District User');

UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='BLDG') 
	WHERE groupname in ('Building Test Coordinator', 'Building User', 'Test Proctor', 'Proctor', 'Principal');

UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='SCH') 
	WHERE groupname in ('DLM teacher', 'Teacher', 'PD User');
	
UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='CONS') 
	WHERE organizationtypeid is null;
