
 -- Updating the hierarchy column value: DE19270 
update groups set hierarchy = 50
where groupname in ('PD User',
'Teacher',
'State Scorer',
'Scorer',
'Teacher: PNP Read Only',
'Proctor');

 update groups set hierarchy = 41
where groupname in ('Building User');