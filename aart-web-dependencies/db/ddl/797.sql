-- new columns for codecompiler 

alter table stimulusvariant add column if not exists codecompilerlang varchar(20);
alter table stimulusvariant add column if not exists codecompilertheme varchar(20);
alter table taskvariant add column if not exists codecompilerlang varchar(20);
alter table taskvariant add column if not exists codecompilertheme varchar(20);
