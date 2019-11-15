-- 278.sql

update fieldspecification set formatregex = '[a-zA-z]+([ X,.''-][a-zA-Z]+)*' where fieldname = 'legalFirstName' and mappedname is null;

update fieldspecification set formatregex = '[a-zA-z]+([ X,.''-][a-zA-Z]+)*' where fieldname = 'legalLastName' and mappedname is null;

update fieldspecification set formatregex = '[a-zA-z]+([ X,.''-][a-zA-Z]+)*' where fieldname = 'legalMiddleName' and mappedname is null;

update fieldspecification set rejectifempty = false where fieldname = 'esolProgramEntryDate';
update fieldspecification set rejectifempty = false where fieldname = 'esolProgramEndingDate';