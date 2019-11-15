
--US14309
update authorities set objecttype='Administrative-Organization', modifieddate=now() where objecttype='Organization';
update authorities set objecttype='Administrative-User', modifieddate=now() where objecttype='User';
update authorities set objecttype='Administrative-Role', modifieddate=now() where objecttype='Role';
update authorities set objecttype='Professional Development-Professional Development', modifieddate=now() where objecttype='Professional Development';
update authorities set objecttype='Reports-DLM', modifieddate=now() where objecttype='DLM';
update authorities set objecttype='Reports-Summative Reports', modifieddate=now() where objecttype='Report' and displayname='Summative Reports Upload';
update authorities set objecttype='Reports-Performance Reports', modifieddate=now() where objecttype='Report' and displayname like 'View Performance%';
update authorities set objecttype='Reports-Formative Reports', displayname='Roster Reporting', modifieddate=now() where objecttype='Report' and displayname='Download Reports';
update authorities set objecttype='Student Management-Enrollment', modifieddate=now() where objecttype='Enrollment';
update authorities set objecttype='Student Management-Roster Record', modifieddate=now() where objecttype='Roster Record';
update authorities set objecttype='Student Management-Student Record', modifieddate=now() where objecttype='Student Record';
update authorities set objecttype='Student Management-First Contact', modifieddate=now() where objecttype='First Contact';
update authorities set objecttype='Student Management-Personal Needs Profile', modifieddate=now() where objecttype='Personal Needs';
update authorities set objecttype='Test Management-Batch Process', modifieddate=now() where objecttype='Batch';
update authorities set objecttype='Test Management-High Stakes', modifieddate=now() where objecttype='High Stakes';
update authorities set objecttype='Test Management-Test Session', modifieddate=now() where objecttype='Test Session';
update authorities set objecttype='Test Management-Quality Control', modifieddate=now() where objecttype='Quality Control';
update authorities set objecttype='Test Management-Ticketing', modifieddate=now() where objecttype='Test Ticket';