
--remove regex for student names
update fieldspecification set formatregex=null where fieldname in ('legalLastName','legalFirstName','legalMiddleName') and formatregex is not null; 