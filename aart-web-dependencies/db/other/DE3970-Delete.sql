--delete enrollments that have no rosters.

delete from enrollment where id not in
(
Select min(id)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
 )
and (attendanceschoolid,studentid,currentschoolyear)
in
(
Select attendanceschoolid,studentid,currentschoolyear
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
) and not exists
(select 1 from enrollmentsrosters where enrollmentid = enrollment.id);

--Re-execute it again, for removing the ones that are 3 and above.

delete from enrollment where id not in
(
Select min(id)
 from enrollment 
 group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
 )
and (attendanceschoolid,studentid,currentschoolyear)
in
(
Select attendanceschoolid,studentid,currentschoolyear
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
) and not exists
(select 1 from enrollmentsrosters where enrollmentid = enrollment.id);

--Re-execute it again, for removing the ones that are 4 and above.

delete from enrollment where id not in
(
Select min(id)
 from enrollment 
 group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
 )
and (attendanceschoolid,studentid,currentschoolyear)
in
(
Select attendanceschoolid,studentid,currentschoolyear
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
) and not exists
(select 1 from enrollmentsrosters where enrollmentid = enrollment.id);

--see again.

Select attendanceschoolid,studentid,currentschoolyear,count(1)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1;

--select the enrollemnts that need to be removed from the rosters.
Select enrlRost.id from enrollment enrl,enrollmentsrosters enrlRost
 where 
enrlRost.enrollmentid = enrl.id and
 (enrl.attendanceschoolid,enrl.studentid,enrl.currentschoolyear) 
in
(
Select attendanceschoolid,studentid,currentschoolyear
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
) and enrl.id not in
(
Select min(id)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
);

--delete the enrollments from the roster.
delete from enrollmentsrosters where id in 
(
	Select enrlRost.id from enrollment enrl,enrollmentsrosters enrlRost
	 where 
	enrlRost.enrollmentid = enrl.id and
	 (enrl.attendanceschoolid,enrl.studentid,enrl.currentschoolyear) 
	in
	(
	Select attendanceschoolid,studentid,currentschoolyear
	 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
	) and enrl.id not in
	(
	Select min(id)
	 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
	)
);

delete from enrollmentsrosters where id in 
(
	Select enrlRost.id from enrollment enrl,enrollmentsrosters enrlRost
	 where 
	enrlRost.enrollmentid = enrl.id and
	 (enrl.attendanceschoolid,enrl.studentid,enrl.currentschoolyear) 
	in
	(
	Select attendanceschoolid,studentid,currentschoolyear
	 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
	) and enrl.id not in
	(
	Select min(id)
	 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
	)
);

--delete enrollments that have no rosters.

Select attendanceschoolid,studentid,currentschoolyear,count(1)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1;
 
delete from enrollment where id not in
(
Select min(id)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
 )
and (attendanceschoolid,studentid,currentschoolyear)
in
(
Select attendanceschoolid,studentid,currentschoolyear
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1
) and not exists
(select 1 from enrollmentsrosters where enrollmentid = enrollment.id);

--this should return 0 rows otherwise constraint would fail.
Select attendanceschoolid,studentid,currentschoolyear,count(1)
 from enrollment group by attendanceschoolid,studentid,currentschoolyear having count(1) > 1;

ALTER TABLE enrollment DROP CONSTRAINT enrollment_uk;

ALTER TABLE enrollment ADD CONSTRAINT enrollment_uk
UNIQUE (studentid , attendanceschoolid , currentschoolyear );