
-- 271.sql

alter table ititestsessionhistory add column studentenrlrosterid bigint;

create table ititestsessionsensitivitytags(
  ititestsessionhistoryid bigint,
  sensitivitytag bigint,
  CONSTRAINT fk_itisensitivity_tagid FOREIGN KEY (sensitivitytag)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_itisensitivity_historyid FOREIGN KEY (ititestsessionhistoryid)
      REFERENCES ititestsessionhistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
