create table scoremapping
(
originalscore numeric(6,3),
convertedscore numeric(6,3)
);

alter table studentsresponses add column originalscore numeric(6,3);

insert into scoremapping (originalscore,convertedscore) values (0.334,0.333);
insert into scoremapping (originalscore,convertedscore) values (0.501,0.5);
insert into scoremapping (originalscore,convertedscore) values (0.666,0.667);
insert into scoremapping (originalscore,convertedscore) values (0.668,0.667);
insert into scoremapping (originalscore,convertedscore) values (0.999,1);
insert into scoremapping (originalscore,convertedscore) values (1.001,1);
insert into scoremapping (originalscore,convertedscore) values (1.002,1);
insert into scoremapping (originalscore,convertedscore) values (1.332,1.333);
insert into scoremapping (originalscore,convertedscore) values (1.334,1.333);
insert into scoremapping (originalscore,convertedscore) values (2.001,2);

