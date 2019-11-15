--US17753 
update modulereport set statusid=539, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id=96754 and statusid=474 and reporttypeid=4 and stateid=69352 and createduser = 158115;