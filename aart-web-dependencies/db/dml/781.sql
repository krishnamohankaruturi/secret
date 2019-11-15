--DML 781 - changes to Claim User Email text

update activationemailtemplate set emailbody='<p>We need your assistance updating a user account in Kite Educator Portal.</p>
<p>[District Name] district user [userDisplayName] has identified [claimUserDisplayName] as 
an educator now working in that district and needs you to inactivate their current account.</p>
<p>If this educator is no longer working in your district, please remove them by clicking the provided link and inactivating their account.</p>
<p>[link]</p>
<p>Thank you,</p>
<p>Kite Educator Portal Team.</p>',
modifieddate=now(),
modifieduser=(select id from aartuser where username = 'cetesysadmin')
where templatename = 'Claim User Email Template';
