grant select on all tables in schema public to aart_reader;
grant select on all sequences in schema public to aart_reader;

grant select, insert, update, delete on all tables in schema public to aart;
--grant select, usage, update on all sequences in schema public to aart_user;

grant select, insert, update, delete on all tables in schema public to aart_user;
grant select, usage, update on all sequences in schema public to aart_user;

--grant select, insert, update, delete on all tables in schema public to etl_user;
--grant select, usage, update on all sequences in schema public to etl_user;

grant all on all functions in schema public to aart;