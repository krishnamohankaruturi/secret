--ddl/725.sql

--F479 DDL -KELPA2 Student Individual Report Static Content---

ALTER TABLE category ALTER COLUMN categorydescription TYPE TEXT;

ALTER TABLE assessmentprogram ADD COLUMN beginreportyear bigint default null;