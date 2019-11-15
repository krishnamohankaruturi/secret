--US17785: EP : Prod - Update student State ID - High

UPDATE student 
SET statestudentidentifier = '7994179953'
WHERE id = (SELECT id FROM student WHERE statestudentidentifier = '5187161329' AND stateid = 9634);