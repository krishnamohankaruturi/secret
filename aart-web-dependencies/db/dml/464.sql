--dml/464.sql 
             
-- Update allowable codes value to accept new codes ND and WD while uploading student enrollment file             
UPDATE fieldspecification 
SET    allowablevalues = '{'''',AM,DB,DD,ED,HI,LD,MD,MR,ID,OH,OI,SL,TB,VI,ND,WD}' 
WHERE  fieldname = 'primaryDisabilityCode' 
       AND id = (SELECT fieldspecificationid 
                 FROM   fieldspecificationsrecordtypes fs 
                 WHERE  recordtypeid = (SELECT c.id 
                                        FROM   category c 
                                               INNER JOIN categorytype ct 
                                                       ON 
                                               c.categorytypeid = ct.id 
                                        WHERE 
                        c.categorycode = 'ENRL_RECORD_TYPE' 
                        AND ct.typecode = 'CSV_RECORD_TYPE') 
                        AND fieldspecificationid = 
                            (SELECT id 
                             FROM   fieldspecification 
                             WHERE  fs.fieldspecificationid = id 
                                    AND fs.recordtypeid = 
                                        (SELECT c.id 
                                         FROM 
                                        category c 
                                        INNER JOIN categorytype 
                                                   ct 
                                                ON 
                                        c.categorytypeid = ct.id 
                                                WHERE 
                                        c.categorycode = 
                                        'ENRL_RECORD_TYPE' 
                                        AND 
                            ct.typecode = 'CSV_RECORD_TYPE') 
                                    AND fieldname = 
                                        'primaryDisabilityCode') 
                ); 