--DE6628 remove the grade course id from gradebanded test collections
update testcollection set gradecourseid=null where gradebandid is not null and gradecourseid is not null;