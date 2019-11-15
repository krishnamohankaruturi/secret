--ddl/739.sql

--F730 Deb: 5/9 : DLM Training Extracts Identify New vs Returning
ALTER TABLE moodleupload ADD COLUMN if not exists usertrainingtype VARCHAR(50) ;
