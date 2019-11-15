alter table categorytype add column externalid bigint,
						 add column originationcode character varying(20),
						 --drop constraint uk_category_type_code,
						 add constraint categorytype_typecode_originationcode_uk UNIQUE (typecode, originationcode);
						 
						 
alter table category add column externalid bigint,
						 add column originationcode character varying(20),
						 --drop constraint category_code_uk,
						 --drop constraint category_name_uk,
						 add constraint category_categorycode_originationcode_uk UNIQUE (categorycode, originationcode, categorytypeid),
						 add constraint category_categoryname_originationcode_uk UNIQUE (categoryname, originationcode, categorytypeid);

