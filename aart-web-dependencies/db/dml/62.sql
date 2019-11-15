
--update all task variants that have no label set for the report task layout format to letters format
update taskvariant set reporttasklayoutformatid = ( select id from tasklayoutformat where formatcode='letters' LIMIT 1)
	where reporttasklayoutformatid in (select id from tasklayoutformat where formatcode='nolabel');