--dml/713.sql

--F688
update category set activeflag = true, modifieddate = now() where categorycode = 'ASSESSMENT_TOPICS';
			