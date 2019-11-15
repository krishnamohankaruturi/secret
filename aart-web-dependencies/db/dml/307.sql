
--US15404
update taskvariant set gradecourseid=null,gradebandid=(select id from gradeband where externalid=470) where id in 
(select tv.id from taskvariant tv
inner join frameworktype ft on ft.id=tv.frameworktypeid and ft.externalid=34
inner join gradecourse gc on gc.id=tv.gradecourseid and gc.externalid=173);

update taskvariant set gradecourseid=null,gradebandid=(select id from gradeband where externalid=474) where id in 
(select tv.id from taskvariant tv
inner join frameworktype ft on ft.id=tv.frameworktypeid and ft.externalid=34
inner join gradecourse gc on gc.id=tv.gradecourseid and gc.externalid=175);

update taskvariant set gradecourseid=null,gradebandid=(select id from gradeband where externalid=473) where id in 
(select tv.id from taskvariant tv
inner join frameworktype ft on ft.id=tv.frameworktypeid and ft.externalid=35
inner join gradecourse gc on gc.id=tv.gradecourseid and gc.externalid=174);

--US15403
update contentframework set gradecourseid=null,gradebandid=(select id from gradeband where externalid=470) where externalid=165;
update contentframework set gradecourseid=null,gradebandid=(select id from gradeband where externalid=474) where externalid=171;
update contentframework set gradecourseid=null,gradebandid=(select id from gradeband where externalid=473) where externalid=166;


update test set gradecourseid=null,gradebandid=(select id from gradeband where externalid=470),gradetype=false where externalid in 
(334,3273,3425,3427,3429,3455,3456,3458,3460,3462,3466,3546,3545,3556,3549,3555,3585,3606,3607,3608,3609,3640,3643,3645,3647,3671,3708,3711,3714,3732,3728,3735,3760,3745,3843,3854,3852,300,1263,1225,1318,1397,1399,1390,1791,1812,1782,1807,1781,1776,1795,1775,1778,1792,1810,1851,1848,1802,1867,1865,1861,1857,1856,1855,1858,1859,1863,1864,1869,1799,1870,1896,1266,1903,1899,1897,1898,1901,1902,1904,1905,1906,1908,1909,1913,1910,1911,1912,1914,1915,1850,1849,1900,1224,1314,1860,1790,1862,1785,1780,1868,1787,1227,1317,1866,1400,1907,1395,1392,1916,1259,1256,1784,1805,1772,500,411,501,683,303,337,309,324,2958,2959,343,344,350,307,410,328,329,311,1497,1501,650,2206);

update test set gradecourseid=null,gradebandid=(select id from gradeband where externalid=474),gradetype=false where externalid in 
(615,651,2204,3398,3467,3518,3541,3610,3698,3701,3703,3705,3725,3727,3743,3823,3847,3723,3662,1084,1281,1280,1301,1275,1286,1325,1360,1388,1415,1408,1290,1273,1873,1428,1874,1882,1885,1920,1921,1922,1924,1925,1926,1927,1270,1295,1406,1923,1385,1872,1876,1387,1093,1879,1881,1289,1321,1327,1917,1403,1310,1319,1413,1278,1405,1267,1401,1076,1880,1268,1288,1316,1884,1875,1409,1402,1090,1065,1877,1919,1918,1283,1883,1886,1305,1307,1079,1294,1332);

update test set gradecourseid=null,gradebandid=(select id from gradeband where externalid=473),gradetype=false where externalid in 
(352,377,355,370,336,338,341,416,3481,3591,3618,3706,3733,3832,4074,4075,4079,4077,4080,4084,4088,4092,4096,4076,4086,4097,4100,4103,4108,4111,4110,4115,4113,4117,4145,4136,4148,4149,4154,4143,4140,4151,4125,4128,4134,4142,4146,4133,4137,4141,4152,4155,4147,4153,4144,4156,4150,4078,418,349,645,632,2646,2644,633,2986,353,798,835,825,826,863,856,740,831,833,850,890,897,937,876,884,873,934,887,933,900,896,905,903,910,941,1030,943,1032,996,1025,1029,1027,1012,1018,1015,1016,1031,1002,1008,1138,733,1235,1237,1231,1230,1228,1221,1218
,1208,1454,1466,1468,1456,1463,1460,1480,1478,1457,1669,992,828,736,908,1469,1936,720,1459,1483,853,1226,940,718,1144,1131,945,1472,942,713,852,1006,1935,728,1214,854,894,1470,693,851,881,1666,333,373,372,354,345);

