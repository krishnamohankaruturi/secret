

Select * from enrollment;


Select * from category where categorytypeid=10;

Delete from category where categorytypeid=10 and categorycode not like '%GRADE%';

ALTER TABLE enrollment ADD CONSTRAINT current_grade_level_fk
 FOREIGN KEY (currentgradelevel) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

update enrollment set currentgradelevel = (select id from category where categoryname = (''||enrollment.currentgradelevel)
and categorytypeid=10 and categorycode like '%GRADE%');