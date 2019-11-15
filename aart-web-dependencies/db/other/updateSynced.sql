begin;

update student set synced=true where synced is false;

commit;