--for ddl/626.sql
update modulereport set reporttypeid=40 where reporttypeid=37;

--DE15061: QA - There is no Assessment Program selection list in Monitor Scoring extract report
update authorities
set authority = 'DATA_EXTRACTS_MONITOR_SCORING'
WHERE authority = 'DATA_EXTRACTS_MONITOR_ASSIGNMENT';