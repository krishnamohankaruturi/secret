

--DE5393EP: Production - Tickets are being generated with forbidden identifiers
	--updates to username field.
UPDATE student SET username = (substring(legalfirstname from 1 for LEAST(18,length(legalfirstname))) || '.'  || substring(legallastname from 1 for LEAST(18,length(legallastname))) || id), modifieddate = now()
	WHERE username = statestudentidentifier;
