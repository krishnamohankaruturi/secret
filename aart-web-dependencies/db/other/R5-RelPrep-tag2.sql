UPDATE fieldspecification SET fieldlength = 40 WHERE fieldname = 'stateCourseCode' AND mappedname is null;  
  
UPDATE fieldspecification SET showerror = true WHERE fieldname = 'comprehensiveRace' and mappedname ='Comprehensive_Race';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'aypSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'createDate' and mappedname ='Create_Date';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'recordCommonId' and mappedname ='Record_Common_ID';