

select 'a' similar to '^[A-Za-z]++$';

select 'a1' similar to '\w*';

select 'a' similar to ']';

select '123' similar to '^\d';

select '11111' similar to '(0|1)(0|1)(0|1)(0|1)(0|1)';

"^\d*+$"

^[a-zA-Z_0-9]$
^[a-zA-Z_0-9]+$

select * from fieldspecification where lower(fieldname) like '%date%';

select '02/19/1981' similar to '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]';


select 'a1' similar to '\w*';

select 'a123' similar to '\w*';

select '123' similar to '\w*';

select 'abc' similar to '\w*';

select 'a$' similar to '\w*';

select '1!' similar to '\w*';

select '@' similar to '\w*';

select '#' similar to '\w*';

select '%' similar to '\w*';

select '^' similar to '\w*';

select '&' similar to '\w*';

select '*' similar to '\w*';

select '(' similar to '\w*';