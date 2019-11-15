-- Student pnp json audit column changes (part of code review).
alter table studentpnpjson add column createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;
alter table studentpnpjson add column createduser integer default 12;
alter table studentpnpjson add column activeflag boolean DEFAULT true;
alter table studentpnpjson add column modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone;
alter table studentpnpjson add column modifieduser integer default 12;

ALTER TABLE studentpnpjson
    ADD CONSTRAINT studentpnpjson_createduser_fk FOREIGN KEY (createduser) REFERENCES aartuser(id);

ALTER TABLE studentpnpjson
    ADD CONSTRAINT studentpnpjson_modifieduser_fk FOREIGN KEY (modifieduser) REFERENCES aartuser(id);
