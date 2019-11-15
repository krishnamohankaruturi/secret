
--changes for CB
UPDATE test set interimthetaestmodeltypecode='uni_dimensional' where interimthetaestmodeltypecode='uni_dimentional';
UPDATE test set interimthetaestmodeltypecode='multi_dimensional' where interimthetaestmodeltypecode='multi_dimentional';

update taskvariant set deliveryformattype = 'stand_alone' where testlet = false;
update taskvariant set deliveryformattype = 'testlet' where testlet = true;