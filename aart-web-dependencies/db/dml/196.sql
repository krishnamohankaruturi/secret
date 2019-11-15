
--change the grouping
update authorities set objecttype = 'Test Management-Operational Test Window' where authority='PERM_OTW_VIEW';

--update the date format validation to allow 2 digit years and dashes in or slashes
update fieldspecification set formatregex='(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]' where formatregex='(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]';