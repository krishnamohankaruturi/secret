
DO 
$BODY$

BEGIN

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'assessment'  and constraint_name = 'uk_assessment_name_testing_program') then
                                -- Constraint: uk_assessment_name_testing_program
                                ALTER TABLE assessment DROP CONSTRAINT uk_assessment_name_testing_program;
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'assessment'  and constraint_name = 'uk_assessment_code_testing_program') then
                                -- Constraint: uk_assessment_code_testing_program
                                ALTER TABLE assessment DROP CONSTRAINT uk_assessment_code_testing_program;
                END IF;

                
                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'categorytype'  and constraint_name = 'uk_category_type_code') then
                                -- Constraint: uk_category_type_code
                                ALTER TABLE categorytype DROP CONSTRAINT uk_category_type_code;                   
                END IF; 
                
                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_categoryname_uk') then
                -- Constraint: category_categoryname_uk
                                ALTER TABLE category DROP CONSTRAINT category_categoryname_uk;
                END IF;

                
                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_name_uk') then
                                -- Constraint: category_name_uk
                                ALTER TABLE category DROP CONSTRAINT category_name_uk;                                     
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_code_uk') then    
                                -- Constraint: category_code_uk
                                ALTER TABLE category DROP CONSTRAINT category_code_uk;                                 
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_categoryname_uk') then  
                                -- Constraint: category_categoryname_uk
                                ALTER TABLE category DROP CONSTRAINT category_categoryname_uk;                                   
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_categorycode_uk') then
                                -- Constraint: category_categorycode_uk
                                ALTER TABLE category DROP CONSTRAINT category_categorycode_uk;                                     
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'category_categoryname_originationcode_uk') then
                                -- Constraint: category_categoryname_originationcode_uk
                                ALTER TABLE category DROP CONSTRAINT category_categoryname_originationcode_uk;                                     
                END IF; 

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'testingprogram'  and constraint_name = 'uk_testingprogram_assessmentprogram') then
                                -- Constraint: uk_testingprogram_assessmentprogram
                                ALTER TABLE testingprogram DROP CONSTRAINT uk_testingprogram_assessmentprogram;                                     
                END IF;

                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'categorycode_categorytype_originationcode_uk') then
                                                ALTER TABLE category
                                                                DROP CONSTRAINT categorycode_categorytype_originationcode_uk;                                   
                END IF;

                IF NOT EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'category'  and constraint_name = 'categorycode_categorytype_originationcode_uk') then
                                                ALTER TABLE category
                                                                ADD CONSTRAINT categorycode_categorytype_originationcode_uk UNIQUE(categorycode, originationcode, categorytypeid);                                    
                END IF;


                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'contentarea'  and constraint_name = 'uk_name') then
                                                ALTER TABLE contentarea DROP CONSTRAINT uk_name;                                   
                END IF;
                
                IF NOT EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'contentarea'  and constraint_name = 'abbreviatedname_uk') then
                                                ALTER TABLE contentarea
                                                                ADD CONSTRAINT abbreviatedname_uk UNIQUE(abbreviatedname, activeflag);                                    
                END IF;

                IF EXISTS (SELECT 0 FROM pg_class where relname = 'uk_content_area_name_idx') then
                                                DROP INDEX uk_content_area_name_idx;                                  
                END IF;

                IF NOT EXISTS (SELECT 0 FROM pg_class where relname = 'uk_content_area_name_idx') then
                                                CREATE INDEX uk_content_area_name_idx
                                                                ON contentarea USING btree
                                                                (lower(name::text) COLLATE pg_catalog."default");                                
                END IF;
                
                IF EXISTS (select constraint_name 
                   from information_schema.constraint_column_usage 
                   where table_name = 'gradecourse'  and constraint_name = 'gradecourse_code') then  
                        -- Constraint: gradecourse_code
                                                ALTER TABLE gradecourse DROP CONSTRAINT gradecourse_code;
                        ALTER TABLE gradecourse
                                                                ADD CONSTRAINT gradecourse_code_contentarea UNIQUE(abbreviatedname, contentareaid, activeflag);                                   
                END IF;

END;
$BODY$;

ALTER TABLE frameworktype ADD COLUMN typecode character varying(30);
