--change null and numbers reporttasklayoutformat to letters
update taskvariant set reporttasklayoutformatid = ( select id from tasklayoutformat where formatcode='letters' LIMIT 1);
	