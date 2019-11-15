--748 DDL

--Script from Rajendra

DROP INDEX IF EXISTS idx_studentpnpjson_studentid;

CREATE INDEX idx_studentpnpjson_studentid
    ON public.studentpnpjson USING btree
    (studentid ASC NULLS LAST)
    TABLESPACE pg_default;