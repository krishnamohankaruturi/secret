BEGIN;

UPDATE aartuser 
set    username=username||'_old',
       email=email||'_old',
	   uniquecommonidentifier=uniquecommonidentifier||'_old',
	   activeflag =false,
	   modifieddate =now(),
	   modifieduser =12
where id =63228;


update aartuser
set   uniquecommonidentifier ='8159867611',
	   modifieddate =now(),
	   modifieduser =12
where id =164604;

COMMIT;