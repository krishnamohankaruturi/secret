DROP VIEW users;

alter table student alter column username type character varying(40);
alter table student alter column stateid type character varying(50);

CREATE OR REPLACE VIEW users AS 
 SELECT student.username, student.password, student.enabled
   FROM student;

GRANT ALL ON TABLE users TO tde;
GRANT SELECT ON TABLE users TO tde_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE users TO tde_user;
