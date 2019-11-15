--469.sql
ALTER TABLE fieldspecification ALTER COLUMN mappedname TYPE varchar(120);
ALTER TABLE fieldspecificationsrecordtypes ALTER COLUMN mappedname TYPE varchar(120);

ALTER TABLE modulereport ADD COLUMN jsondata text;
