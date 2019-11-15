--update to match display identifier
update fieldspecification set formatregex=(select formatregex from fieldspecification where fieldname ='displayIdentifier')
		where fieldname='parentDisplayIdentifier';