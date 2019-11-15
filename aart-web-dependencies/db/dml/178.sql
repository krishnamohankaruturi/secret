DO 
$BODY$
DECLARE 
	users record;
	userorg record;
	dorgcount bigint;
	dgroupcount bigint;
BEGIN
    FOR users IN select id from aartuser where activeflag=true order by id
    LOOP
	RAISE INFO '   **************************** checking userid :  % ' , users.id;
				
        select count(isdefault) into dorgcount from usersorganizations where aartuserid=users.id;
        RAISE INFO '   **************************** dorgcount:   %' , dorgcount;
        IF dorgcount = 0 THEN
		update usersorganizations set isdefault=true where 
			id=(select id from usersorganizations where aartuserid=users.id LIMIT 1);
		RAISE INFO '   **************************** updated user:   %' , users.id;
        END IF;

        FOR userorg IN select id from usersorganizations where aartuserid=users.id
		LOOP
		RAISE INFO '   **************************** userorg: %  ', userorg.id;
		select count(isdefault) into dgroupcount from userorganizationsgroups
				where userorganizationid=userorg.id;
		RAISE INFO '   **************************** dgroupcount:  % ' , dgroupcount;
		IF dgroupcount = 0 THEN
			update userorganizationsgroups set isdefault=true where 
				id=(select id from userorganizationsgroups where userorganizationid=userorg.id LIMIT 1);
			RAISE INFO '   **************************** updated:  % ' ,  userorg.id;
		END IF;
        END LOOP;
        RAISE INFO '   **************************** done userid :  % ' , users.id;
    END LOOP;
END;
$BODY$;