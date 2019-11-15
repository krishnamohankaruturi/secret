-- 494.sql
-- US16949:Track activity of key enrollment and roster events

alter table domainaudithistory add column objectbeforevalues json;
alter table domainaudithistory add column objectaftervalues json;

CREATE INDEX idx_domainaudithistory_objecttype ON domainaudithistory USING btree (objecttype COLLATE pg_catalog."default");
CREATE INDEX idx_domainaudithistory_objectid ON domainaudithistory USING btree (objectid);
CREATE INDEX idx_domainaudithistory_source ON domainaudithistory USING btree (source COLLATE pg_catalog."default");
CREATE INDEX idx_domainaudithistory_createduserid ON domainaudithistory USING btree (createduserid);

--changes from script bees
DROP TABLE IF EXISTS studentpnpjson;
create table studentpnpjson (
	studentid bigint not null,
	jsontext text,
	constraint studentpnpjson_pkey primary key (studentid)
);


DROP FUNCTION IF EXISTS populatestudentpnpjson();
CREATE OR REPLACE FUNCTION populatestudentpnpjson()
  RETURNS integer AS
$BODY$
DECLARE
	student RECORD;
	studentPNPJson RECORD;
	jsonText text;
	pnpAttribute text;
BEGIN
	RAISE NOTICE 'Populating student pnp json text...';
	delete from studentpnpjson;
	
    FOR student IN SELECT distinct spiav.studentid 
	FROM profileitemattribute pia 
	INNER JOIN profileItemAttributenameAttributeContainer pianc ON pia.id=pianc.attributenameid 
	INNER JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
	INNER JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid
	ORDER BY spiav.studentid 
    LOOP
        RAISE NOTICE ' Populating student pnp for % ...', student.studentId;
	jsonText = '[';
	pnpAttribute = '';
        for studentPNPJson in SELECT lower(pia.attributename) as attrname, piac.id as attrcontainerid, 
		lower(piac.attributecontainer) as attrcontainer, lower(spiav.selectedvalue) as attrvalue
		FROM profileitemattribute pia 
		INNER JOIN profileItemAttributenameAttributeContainer pianc ON pia.id=pianc.attributenameid 
		INNER JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
		INNER JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid 
		where spiav.studentid = student.studentId
        loop
		pnpAttribute = pnpAttribute || '{';
		pnpAttribute = pnpAttribute || '"attrName"' || ':"' || studentPNPJson.attrname || '",';
		pnpAttribute = pnpAttribute || '"attrContainerId"' || ':' || studentPNPJson.attrcontainerid || ',';
		pnpAttribute = pnpAttribute || '"attrContainer"' || ':"' || studentPNPJson.attrcontainer || '",';
		pnpAttribute = pnpAttribute || '"attrValue"' || ':"' || studentPNPJson.attrvalue || '"';
		pnpAttribute = pnpAttribute || '},';
        end loop;
	jsonText = jsonText || trim( trailing ',' from pnpAttribute);
        jsonText = jsonText || ']';
        -- RAISE NOTICE '%', jsonText;
        EXECUTE 'INSERT INTO studentpnpjson (studentid, jsontext) values($1, $2)' using student.studentId, jsonText;
    END LOOP;
    RAISE NOTICE 'Done Populating student pnp json text...';
    RETURN 1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;