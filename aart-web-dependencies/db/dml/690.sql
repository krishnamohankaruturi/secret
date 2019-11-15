--dml/690.sql 

  update authorities set tabName='Interim',groupingName='View Results',level=1,sortorder= 174*100,
  modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
  where authority = 'VIEW_INT_PRED_SCHOOL_REPORT';	

  update authorities set tabName='Interim',groupingName='View Results',level=1,sortorder= 175*100,
  modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
  where authority = 'VIEW_INT_PRED_DISTRICT_REPORT';