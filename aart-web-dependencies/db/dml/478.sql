-- dml/478.sql -- US16938
update studenttracker set schoolyear = 2015;

--DE11736
update fieldspecification set allowablevalues='{'''',yes,YES,Yes,No,NO,no}' where id in (
select id from fieldspecification fs, fieldspecificationsrecordtypes fst 
where fst.fieldspecificationid = fs.id and fst.recordtypeid in (select id from category where categorycode ='PD_TRAINING_RECORD_TYPE')
and fs.fieldname='rtComplete'
);
