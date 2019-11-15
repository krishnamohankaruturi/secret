--ddl/528.sql -- US17665 : Upload LevelDescription Report enhancements for 2016

ALTER TABLE leveldescription DROP COLUMN IF EXISTS testid1, DROP COLUMN IF EXISTS testid2;

ALTER TABLE leveldescription ADD COLUMN descriptiontype character varying(20);

ALTER TABLE leveldescription ADD COLUMN descriptionparagraphpagebottom character varying(1000);