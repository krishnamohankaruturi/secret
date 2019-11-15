update fieldspecification set formatregex ='^[\w~`!#$%^&*_+={};:/?|-]+(\.{0,1}[\w~`!#$%^&*_+={};:/?|-]+)*@{1}[A-Za-z0-9]+(\.{0,1}[A-Za-z0-9]+-{0,1})*\.{1}[A-Za-z0-9]{2,}$'
where fieldname = 'email';