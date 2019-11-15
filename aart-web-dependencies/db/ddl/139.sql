--- Student Response Migrate from tde to EP

alter table studentsresponses rename column taskid to taskvariantid;
alter table studentsresponses rename column foil to foilid;
alter table studentsresponses rename column createdate to createddate;
alter table studentsresponses drop column createusername;
alter table studentsresponses add column createduser integer;
alter table studentsresponses drop column modifiedusername;
alter table studentsresponses add column modifieduser integer;
alter table studentsresponses add column aciveflag boolean DEFAULT true;

delete from studentsresponses where foilid in (select foilid from (select foilid from studentsresponses except (select id from foil)) srtemp where srtemp.foilid is not null);
delete from studentsresponses where studentid in (select studentid from studentsresponses except (select id from student));
delete from studentsresponses where studentstestsid in (select studentstestsid from studentsresponses except (select id from studentstests));
delete from studentsresponses where taskvariantid in (select taskvariantid from studentsresponses except (select id from taskvariant));
delete from studentsresponses where testid in (select testid from studentsresponses except (select id from test));

alter table studentsresponses add CONSTRAINT studentsresponses_foilid_fkey FOREIGN KEY (foilid)
      REFERENCES foil (id) MATCH SIMPLE
 ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table studentsresponses add CONSTRAINT studentsresponses_studentid_fkey FOREIGN KEY (studentid)
 REFERENCES student (id) MATCH SIMPLE
 ON UPDATE NO ACTION ON DELETE NO ACTION;


alter table studentsresponses add CONSTRAINT studentsresponses_studentstestsid_fkey FOREIGN KEY (studentstestsid)
 REFERENCES studentstests (id) MATCH SIMPLE
 ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table studentsresponses add CONSTRAINT studentsresponses_taskvariantid_fkey FOREIGN KEY (taskvariantid)
 REFERENCES taskvariant (id) MATCH SIMPLE
 ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table studentsresponses add CONSTRAINT studentsresponses_testid_fkey FOREIGN KEY (testid)
  REFERENCES test (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
  
