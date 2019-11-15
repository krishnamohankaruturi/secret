--dml/803.sql
update linkagelevelsortorder set levelname = displayname where levelname is Null;
update linkagelevelsortorder set displayname = 'Initial'where displayname='Initial Precursor' and contentareaid=(select id from contentarea where abbreviatedname = 'Sci');
update linkagelevelsortorder set displayname = 'Precursor' where displayname='Proximal Precursor' and contentareaid=(select id from contentarea where abbreviatedname = 'Sci');

