--missing constraints.

ALTER TABLE assessment
   ALTER COLUMN originationcode SET NOT NULL;
ALTER TABLE assessment
   ALTER COLUMN assessmentcode SET NOT NULL;

ALTER TABLE assessmentprogram
   ALTER COLUMN originationcode SET NOT NULL;

ALTER TABLE category
   ALTER COLUMN originationcode SET NOT NULL;
   
ALTER TABLE categorytype
   ALTER COLUMN originationcode SET NOT NULL;
-- this is redundant because type code is already unique.   
ALTER TABLE categorytype DROP CONSTRAINT categorytype_typecode_uk;  

ALTER TABLE cognitivetaxonomy
   ALTER COLUMN originationcode SET NOT NULL;
   
ALTER TABLE cognitivetaxonomydimension
   ALTER COLUMN originationcode SET NOT NULL;
   
ALTER TABLE contentarea
   ALTER COLUMN originationcode SET NOT NULL;

ALTER TABLE contentframework
   ALTER COLUMN originationcode SET NOT NULL;
   
ALTER TABLE contentframeworkdetail
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE foil
   ALTER COLUMN originationcode SET NOT NULL;         
   
ALTER TABLE frameworklevel
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE frameworktype
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE gradecourse
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE stimulusvariant
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE stimulusvariantattachment
   ALTER COLUMN originationcode SET NOT NULL;   

ALTER TABLE tasklayoutformat
   ALTER COLUMN originationcode SET NOT NULL;   
   
ALTER TABLE tasktype
   ALTER COLUMN originationcode SET NOT NULL;      

ALTER TABLE taskvariant
   ALTER COLUMN originationcode SET NOT NULL;         
   
ALTER TABLE test
   ALTER COLUMN originationcode SET NOT NULL;            

ALTER TABLE testcollection
   ALTER COLUMN originationcode SET NOT NULL;            

ALTER TABLE testform
   ALTER COLUMN originationcode SET NOT NULL;            

ALTER TABLE testingprogram
   ALTER COLUMN originationcode SET NOT NULL;            

ALTER TABLE testsection
   ALTER COLUMN originationcode SET NOT NULL;            

ALTER TABLE tool
   ALTER COLUMN originationcode SET NOT NULL;            
   