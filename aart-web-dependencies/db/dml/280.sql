
--DE7473
update fieldspecification set formatregex='(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]|^$' where fieldname='exitDate' and mappedname is null;

update organization set schoolstartdate='08/01/2014 00:00:00', schoolenddate='07/31/2015 00:00:00' where contractingorganization=true;