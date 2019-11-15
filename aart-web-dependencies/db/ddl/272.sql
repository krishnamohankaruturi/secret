
--PD changes to capture more data points
ALTER TABLE usermodule ADD COLUMN testfinalscore numeric(6,3);
ALTER TABLE usermodule ADD COLUMN testresult boolean;
ALTER TABLE usermodule ADD COLUMN testcompletiondate timestamp with time zone;
ALTER TABLE usermodule ADD COLUMN earnedceu integer;