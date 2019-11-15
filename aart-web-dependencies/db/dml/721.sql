--dml/721.sql
update fieldspecification set rejectifempty = true, modifieddate = now() where fieldname = 'educatorIdentifier' and rejectifempty is false and fieldlength is null;

update fieldspecification set rejectifempty = false, modifieddate = now() where fieldname = 'kiteEducatorIdentifier';