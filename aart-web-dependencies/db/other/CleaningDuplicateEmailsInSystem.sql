-- KIDS process was failed beacuse of the duplicate email ids in system. Found 7 of them in system. Going to inactivate all the accounts
-- which are inactive status and adding "_Inactive" at the end of email and username for these ones and updating the activeflag to false for these emails

update aartuser set email = email || '_Inactive', username = username || '_Inactive', activeflag = false, modifieddate = now(), modifieduser = 12, uniquecommonidentifier = uniquecommonidentifier || '_Inactive'
            where email in ('MMYaklin@bluevalleyk12.org', 'AJStevenson@bluevalleyk12.org', 'PKamlowsky@bluevalleyk12.org', 
  'Kpaxson@argonia359.org', 'PATilbury@bluevalleyk12.org', 'EABodenhamer@bluevalleyk12.org', 'Stephanie.Dietiker@d300.orgdead');
