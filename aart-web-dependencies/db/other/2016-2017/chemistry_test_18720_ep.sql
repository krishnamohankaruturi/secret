delete from testjson where testid in (select id from test where externalid=18720);