ALTER TABLE contentframeworkdetail
   ALTER COLUMN description TYPE character varying(2000);
ALTER TABLE organization
   ALTER COLUMN organizationname TYPE character varying(100);
ALTER TABLE organization
   ALTER COLUMN displayidentifier TYPE character varying(100);
   
ALTER TABLE taskvariant DROP COLUMN itemname;
