
--US15814

 insert into complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid) values
(232,'FOUNDATIONAL', 0, 0, 1.999, (select id from contentarea where abbreviatedname = 'Sci'));

insert into complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid) values
(233,'BAND_1', 1, 0, 1.999, (select id from contentarea where abbreviatedname = 'Sci'));

insert into complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid) values
(234,'BAND_2', 2, 2.0, 2.999, (select id from contentarea where abbreviatedname = 'Sci'));

insert into complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid) values
(235,'BAND_3', 3, 3.0, 4.0, (select id from contentarea where abbreviatedname = 'Sci'));