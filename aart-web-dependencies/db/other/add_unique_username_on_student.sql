begin;

update student set username=statestudentidentifier||'2'
where id in
(
Select max(s.id) from student s group by s.username having count(1) > 1
);

--verify

Select count(1)||'' as count,username,max(id) from student group by username having count(1) > 1;

--this is for that one record that is still left.

update student set username=statestudentidentifier||'2'
where id in
(
Select max(s.id) from student s group by s.username having count(1) > 1
);

Select count(1)||'' as count,username,max(id) from student group by username having count(1) > 1;

ALTER TABLE student ADD CONSTRAINT uk_username UNIQUE (username);

commit;