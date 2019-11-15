--stage/ddl/46.sql

DROP TABLE IF EXISTS outgoingrequests;

CREATE TABLE outgoingrequests (
    id SERIAL NOT NULL PRIMARY KEY,
    url TEXT NOT NULL,
    method TEXT NOT NULL,
    headers TEXT,
    body TEXT,
    responsecode INTEGER,
    response TEXT,
    senttime TIMESTAMP WITH TIME ZONE,
    responsetime TIMESTAMP WITH TIME ZONE
);

DO 
$BODY$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = 'public'
        AND table_name = 'apirequest'
        AND table_type = 'BASE TABLE'
    ) THEN
        RAISE NOTICE 'Did not find apirequest table, creating...';
        
        RAISE NOTICE 'Creating table...';
        CREATE TABLE apirequest (
            id SERIAL NOT NULL PRIMARY KEY,
            requesttype CHARACTER VARYING (20),
            endpoint TEXT NOT NULL,
            method CHARACTER VARYING (10) NOT NULL,
            headers TEXT,
            body TEXT,
            responsecode INTEGER,
            response TEXT,
            receivedtime TIMESTAMP WITH TIME ZONE,
            responsetime TIMESTAMP WITH TIME ZONE
        );
        
        CREATE INDEX apirequest_requesttype ON apirequest USING btree (requesttype);
        CREATE INDEX apirequest_endpoint ON apirequest USING btree (endpoint);
	ELSE
		RAISE NOTICE 'Found apirequest table, skipping creation...';
    END IF;
END;
$BODY$;
