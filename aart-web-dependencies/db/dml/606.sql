-- dml/606.sql

-- Fix for DE14572, MR primary disability code still being incorrectly considered valid
update fieldspecification
set allowablevalues = '{AM,DB,DD,ED,HI,LD,MD,ID,OH,OI,SL,TB,VI,ND,WD,EI,DA}',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (
	select distinct fs.id
	from fieldspecification fs
	join fieldspecificationsrecordtypes fsrt on fs.id = fsrt.fieldspecificationid
	join category c on fsrt.recordtypeid = c.id
	where fs.fieldname = 'primaryDisabilityCode'
	and c.categorycode = 'ENRL_RECORD_TYPE'
);