
-- update student password to not have month names/date
--YE
select insert_lockdown_date('BIE-Choctaw',    '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('BIE-Miccosukee', '2016-03-21 00:00:00', '2016-08-01 00:00:00');

select insert_lockdown_date('CO', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('IL', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('MS', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('NH', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('NJ', '2016-03-28 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('NY', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('OK', '2016-03-14 00:00:00', '2016-08-06 00:00:00');
select insert_lockdown_date('WI', '2016-03-14 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('WV', '2016-03-21 00:00:00', '2016-08-01 00:00:00');
--IM
select insert_lockdown_date('IA', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('MO', '2016-03-21 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('ND', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('UT', '2016-03-10 00:00:00', '2016-08-01 00:00:00');
select insert_lockdown_date('VT', '2016-03-16 00:00:00', '2016-08-01 00:00:00');

--US17345
update authorities set displayname = 'View First Contact Survey Status' where authority = 'VIEW_FIRST_CONTACT_SURVEY' and objecttype = 'Student Management-First Contact';

UPDATE ONLY student
SET password = generate_student_password()
WHERE student.id in (
  select id from student where password similar to '(march|may|june|july|junk|date)[0-9]*'
);
