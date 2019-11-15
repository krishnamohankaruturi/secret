

UPDATE frameworktype fwt SET typecode = substring((select name from frameworktype fwtinner where fwtinner.id=fwt.id), 0, 29);