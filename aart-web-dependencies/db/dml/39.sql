--US12619 clean up of data to remove exit rules related to required to complete test
delete from testcollectionssessionrules 
where sessionruleid = (select id from category where categorycode = 'COMPLETE_TEST') 
	or sessionruleid = (select id from category where categorycode = 'REQUIRED_TO_COMPLETE_TEST');