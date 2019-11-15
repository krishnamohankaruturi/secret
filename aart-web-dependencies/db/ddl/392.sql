--fix for batchupload record referencing two values in category, because it wasn't using the ID, just the code

-- add the new column
ALTER TABLE batchupload ADD COLUMN uploadtypeid BIGINT;

--add the new FK reference
ALTER TABLE batchupload
	ADD CONSTRAINT batchupload_uploadtypeid_fkey FOREIGN KEY (uploadtypeid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

--move the data to the new column
UPDATE batchupload
SET uploadtypeid = (
	SELECT id
	FROM category
	WHERE categorycode = uploadtype
	AND categorytypeid = (
		SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE'
	)
	LIMIT 1
);

--drop the old column
ALTER TABLE batchupload DROP COLUMN uploadtype;
