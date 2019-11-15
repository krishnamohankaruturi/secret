-- US15772: Test Sessions: Apply Operational Test Window Dates To Session

ALTER TABLE operationaltestwindow ADD column activeflag boolean NOT NULL DEFAULT true;

CREATE TABLE operationaltestwindowstestcollections
(
	operationaltestwindowid bigint NOT NULL,
	testcollectionid bigint NOT NULL,
	createduser bigint NOT NULL,
	createddate time with time zone NOT NULL DEFAULT now(),
	modifieduser integer,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	activeflag boolean NOT NULL DEFAULT true,
	CONSTRAINT ooperationaltestwindowstestcollections_pkey PRIMARY KEY (operationaltestwindowid, testcollectionid),
	CONSTRAINT ooperationaltestwindowstestcollections_operationaltestwindowid_fkey FOREIGN KEY(operationaltestwindowid)
		REFERENCES operationaltestwindow(id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT operationaltestwindowstestcollections_testcollectionid_fkey FOREIGN KEY(testcollectionid)
		REFERENCES testcollection(id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION	
);
