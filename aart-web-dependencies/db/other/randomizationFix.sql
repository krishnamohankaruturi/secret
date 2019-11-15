ALTER TABLE testcollection
   ALTER COLUMN randomizationtype SET DEFAULT 'enrollment';

update testcollection set randomizationtype = 'enrollment' where
 ( randomizationtype is null or randomizationtype not in ('enrollment','login') );

 ALTER TABLE testcollection
   ALTER COLUMN randomizationtype SET NOT NULL;