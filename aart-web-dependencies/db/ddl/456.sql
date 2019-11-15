--456.sql (new column in modulereport table)
--this flag is to indicate whether the physical file has been deleted from server or not
-- deleteflag=true means deleted from server

ALTER TABLE modulereport ADD COLUMN deleteflag boolean DEFAULT false;
  

