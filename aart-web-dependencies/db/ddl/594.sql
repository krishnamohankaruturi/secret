-- moving organizationmanagementaudit to audit db
DROP TABLE IF EXISTS organizationmanagementaudit;

ALTER TABLE projectedtestingdate ADD COLUMN currentschoolyear BIGINT;
