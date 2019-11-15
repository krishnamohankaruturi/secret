-- 679.sql dml version

update activationemailtemplate set templatename='Claim User Email Template',
	modifieddate=now(),
	modifieduser=(select id from aartuser where username = 'cetesysadmin')
	where templatename='User Activation Email Template - Active User';
	


update activationemailtemplate set emailbody='<p>We are needing your assistance to update a user account in Kite Educator Portal.</p>

<p>The following educator, [claimUserDisplayName], has a district user, [userDisplayName] who has identified 
[userFirstName] as an educator now working at [District Name] district and needing you to inactivate their account.</p>

<p>If this educator is no longer working in your district, we are needing you to remove them by inactivating their account. Please click the provided link to inactivate their current account.</p>

<p>[link]</p>

<p>Thank you,</p>

<p>Kite Educator Portal Team.</p>',
modifieddate=now(),
modifieduser=(select id from aartuser where username = 'cetesysadmin')
where emailsubject='KITE Educator Portal User Account Action Request';
