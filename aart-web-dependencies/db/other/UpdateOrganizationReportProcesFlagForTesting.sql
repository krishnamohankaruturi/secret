--Set reportprocess of the needed organizations to true.

UPDATE organization SET reportprocess = false; 

DO
$BODY$
DECLARE 
   test bigint;
   i integer;
   school_display_identifiers varchar[] := array['0875', '1756', '7620', '1287', '1756', '1718', '2652', '7143', '9311', '8195', '8308', '8305', '8288', '8287', '8284', '8279', '8346', '8332', '8312', '2518', '7542', '5904', '4662', '0482', '0870', '0874', '0859', '0808', '1262', '7332', '0794', 'KAPSCH', '3262', '3871', '0835', '0836', '0844', '8652', '8658', '8666', '2786', '0847', '1958', '1955', '3488', '4092', '4950', '2436'];
   district_display_identifiers varchar[] := array['D0233', 'D0259', 'D0475', 'D0249', 'D0259', 'D0259', 'D0290', 'D0457', 'D0233', 'D0497', 'D0500', 'D0500', 'D0500', 'D0500', 'D0500', 'D0500', 'D0500', 'D0500', 'D0500', 'D0285', 'D0473', 'Z0031', 'D0367', 'D0216', 'D0233', 'D0233', 'D0233', 'D0231', 'D0248', 'D0465', 'D0230', 'KAPDST', 'D0313', 'D0335', 'D0232', 'D0232', 'D0232', 'D0506', 'D0506', 'D0506', 'D0233', 'D0233', 'D0261', 'D0261', 'D0323', 'D0346', 'D0378', 'D0282'];
BEGIN
   FOR i IN array_lower(school_display_identifiers, 1) .. array_upper(school_display_identifiers, 1)
   LOOP
    
    UPDATE organization
	SET reportprocess = true, reportyear = 2106
	where id in (
		select id 
			from organization org
			join organizationtreedetail ord ON (ord.schoolid = org.id and ord.statedisplayidentifier='KS')
			where ord.schooldisplayidentifier =school_display_identifiers[i] and ord.districtdisplayidentifier = district_display_identifiers[i]
		UNION
		select districtid from organizationtreedetail ord where ord.districtdisplayidentifier = district_display_identifiers[i] and ord.statedisplayidentifier='KS'
	);

    RAISE NOTICE ' Updated: % % %', i, school_display_identifiers[i], district_display_identifiers[i];
    
   END LOOP;
END;
$BODY$