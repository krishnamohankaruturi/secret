
--DE7312
update fieldspecification set fieldname='giftedCode', allowablevalues='{'''',GI,Gi,gi}', fieldlength=1 where fieldname='secondaryDisabilityCode' and mappedname='Secondary_Exceptionality_Code';