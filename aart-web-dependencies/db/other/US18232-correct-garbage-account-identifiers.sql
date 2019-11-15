update aartuser
set uniquecommonidentifier = uniquecommonidentifier || '-' || id,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where uniquecommonidentifier in ('5475816417', '7558691958', '2451851945')
and username ~* (uniquecommonidentifier || '\-\d+\-\d+');