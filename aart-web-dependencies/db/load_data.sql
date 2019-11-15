-- SELECT currval('organization_id_seq') gets you the last inserted organization.
--this is already done under dml folder
--INSERT INTO organization (name) values ('Test Organization');
INSERT INTO policy(organizationid, "name", data) VALUES (currval('organization_id_seq'), 'welcomemessage', 'Welcome to the user in Test Orgnization');

INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0001s', 'state1', 'Jane', 'Q', 'user', 'pass0001', 's', 1);

-- These must go after user due to forgein key dependicies
INSERT INTO authorities (id, username, authority) VALUES (currval('aartuser_id_seq'), 'user0001s', 'ROLE_USER');

INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0002s', 'state2', 'Joe', 'Q', 'user', 'pass0002', 's', 1);
INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0001t', 'state3', 'Jane', 'T', 'Teacher', 'pass0001', 't', 1);
INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0002t', 'state4', 'Joe', 'T', 'Teacher', 'pass0002', 't', 1);
INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0001a', 'state5', 'Jane', 'A', 'Admin', 'pass0001', 'a', 1);
INSERT INTO aartuser (username, stateid, firstname, middlename, surname, password, usertype, enabled) values('user0002a', 'state6', 'Joe', 'A', 'Admin', 'pass0002', 'a', 1);

INSERT INTO authorities (id, username, authority) VALUES (5, 'user0001a', 'ROLE_ADMIN');
INSERT INTO authorities (id, username, authority) VALUES (5, 'user0001a', 'ROLE_USER');

