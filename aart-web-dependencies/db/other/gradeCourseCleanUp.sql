update enrollment set currentgradelevel = null where currentgradelevel is not null and currentgradelevel not in (select id from gradecourse);

delete from category where categorytypeid in (select id from categorytype where typecode ='GRADE_TYPE_CODE');

delete from categorytype where typecode ='GRADE_TYPE_CODE';

--remove test types