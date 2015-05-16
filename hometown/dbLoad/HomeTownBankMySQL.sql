
-- Drop Foreign Key Constraints --

ALTER TABLE hometownbank.branch DROP FOREIGN KEY branchaddressfk;
ALTER TABLE hometownbank.person DROP FOREIGN KEY personbranchfk;
ALTER TABLE hometownbank.person DROP FOREIGN KEY personaddressfk;
ALTER TABLE hometownbank.personphone DROP FOREIGN KEY personphonefk;
ALTER TABLE hometownbank.personphone  DROP FOREIGN KEY phonepersonfk;
ALTER TABLE hometownbank.payee DROP FOREIGN KEY payeeaddressfk;
ALTER TABLE hometownbank.payee DROP FOREIGN KEY payeephonefk;
ALTER TABLE hometownbank.personpayee DROP FOREIGN KEY payeepersonfk;
ALTER TABLE hometownbank.personpayee DROP FOREIGN KEY personpayeefk;
ALTER TABLE hometownbank.account DROP FOREIGN KEY accountpersonfk;
ALTER TABLE hometownbank.banktransaction DROP FOREIGN KEY transfrmacctfk;
ALTER TABLE hometownbank.banktransaction DROP FOREIGN KEY transtoacctfk;
ALTER TABLE hometownbank.billpayment DROP FOREIGN KEY paymentpayeefk;
ALTER TABLE hometownbank.billpayment DROP FOREIGN KEY paymenttransfk;
ALTER TABLE hometownbank.purchase DROP FOREIGN KEY purchasesecurityfk;
ALTER TABLE hometownbank.purchase DROP FOREIGN KEY purchasebuyfk;
ALTER TABLE hometownbank.purchase DROP FOREIGN KEY purchasesellfk;
ALTER TABLE hometownbank.checkorder DROP FOREIGN KEY chkfk1;
ALTER TABLE hometownbank.checkorder DROP FOREIGN KEY chkfk2;
-- ALTER TABLE hometownbank.payments DROP FOREIGN KEY paymentfk1;
-- ALTER TABLE hometownbank.payments DROP FOREIGN KEY paymentfk2;
-- ALTER TABLE hometownbank.payments DROP FOREIGN KEY paymentfk3;

-- Drop tables --

DROP TABLE IF EXISTS hometownbank.payments CASCADE;
DROP TABLE IF EXISTS hometownbank.checkorder CASCADE;
DROP TABLE IF EXISTS hometownbank.checklookup CASCADE;
DROP TABLE IF EXISTS hometownbank.purchase CASCADE;
DROP TABLE IF EXISTS hometownbank.billpayment CASCADE;
DROP TABLE IF EXISTS hometownbank.banktransaction CASCADE;
DROP TABLE IF EXISTS hometownbank.account CASCADE;
DROP TABLE IF EXISTS hometownbank.personpayee CASCADE;
DROP TABLE IF EXISTS hometownbank.payee CASCADE;
DROP TABLE IF EXISTS hometownbank.security CASCADE;
DROP TABLE IF EXISTS hometownbank.personphone CASCADE;
DROP TABLE IF EXISTS hometownbank.phone CASCADE;
DROP TABLE IF EXISTS hometownbank.person CASCADE;
DROP TABLE IF EXISTS hometownbank.branch CASCADE;
DROP TABLE IF EXISTS hometownbank.address CASCADE;
DROP TABLE IF EXISTS hometownbank.lookup CASCADE;

#########################################################
################### CREATE DATABASE #####################
#########################################################

CREATE DATABASE IF NOT EXISTS hometownbank;

###################
## Create Tables ##
###################

CREATE TABLE hometownbank.lookup
   (lookupgroup VARCHAR(10) NOT NULL,
    lookupkey VARCHAR(1) NOT NULL,
    shortvalue VARCHAR(10) NOT NULL,
    longvalue VARCHAR(35) NULL,
    CONSTRAINT lookuppk PRIMARY KEY (lookupgroup, lookupkey));
    
CREATE TABLE hometownbank.address
  (addressid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   street1 VARCHAR(30) NULL,
   street2 VARCHAR(30) NULL,
   city VARCHAR(20) NULL,
   state VARCHAR(2) NULL,
   zipcode VARCHAR(9) NULL) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.branch
  (branchid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   branchname VARCHAR(50) NOT NULL,
   addressid INT NULL,
   CONSTRAINT branchaddressfk FOREIGN KEY (addressid)
     REFERENCES hometownbank.address(addressid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.person
  (personid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   firstname VARCHAR(15) NOT NULL,
   middlename VARCHAR(15) NULL,
   lastname VARCHAR(15) NOT NULL,
   userid VARCHAR(15) NULL,
   password VARCHAR(10) NULL,
   addressid INT NULL,
   branchid INT NULL,
   CONSTRAINT personbranchfk FOREIGN KEY (branchid)
     REFERENCES hometownbank.branch(branchid),
   CONSTRAINT personaddressfk FOREIGN KEY (addressid)
     REFERENCES hometownbank.address(addressid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.phone
	(phoneid VARCHAR(32) PRIMARY KEY NOT NULL,
	 phonetype VARCHAR(1) NOT NULL,
	 phone VARCHAR(13) NOT NULL);

CREATE TABLE hometownbank.personphone
  (personid INT NOT NULL,
   phoneid VARCHAR(32) NOT NULL,
   CONSTRAINT personphonepk PRIMARY KEY (personid, phoneid),
   CONSTRAINT personphonefk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT phonepersonfk FOREIGN KEY (phoneid)
     REFERENCES hometownbank.phone(phoneid));

CREATE TABLE hometownbank.payee
  (payeeid VARCHAR(32) PRIMARY KEY NOT NULL,
   company VARCHAR(35) NOT NULL,
   addressid INT NOT NULL,
   phoneid VARCHAR(32) NULL,
   CONSTRAINT payeeaddressfk FOREIGN KEY (addressid)
     REFERENCES hometownbank.address(addressid),
   CONSTRAINT payeephonefk FOREIGN KEY (phoneid)
     REFERENCES hometownbank.phone(phoneid));

CREATE TABLE hometownbank.personpayee
  (personpayeeid VARCHAR(32) PRIMARY KEY NOT NULL,
   payeeaccountno VARCHAR(32) NOT NULL,
   personid INT NOT NULL,
   payeeid VARCHAR(32) NOT NULL,
   CONSTRAINT personpayeefk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT payeepersonfk FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid));

CREATE TABLE hometownbank.security
  (securityid VARCHAR(32) PRIMARY KEY NOT NULL,
   description VARCHAR(60)  NULL,
   rate NUMERIC(10, 4) NULL,
   price NUMERIC(14, 2) NULL,
   daystomaturity NUMERIC(5,0) NULL);
  
CREATE TABLE hometownbank.account
  (accountid VARCHAR(32) PRIMARY KEY NOT NULL,
   accountno VARCHAR(15) NOT NULL,
   balance DECIMAL(10,2) NOT NULL,
   accounttype VARCHAR(1) NOT NULL,
   personid INT NULL,
   CONSTRAINT accountpersonfk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid));

CREATE TABLE hometownbank.banktransaction
  (transactionid VARCHAR(32) PRIMARY KEY NOT NULL,
   description VARCHAR(60) NULL,
   timemade DATETIME NULL, 
   amount DECIMAL(10,2) NULL,
   transtype VARCHAR(1) NOT NULL,
   fromaccountid VARCHAR(32) NULL,
   toaccountid VARCHAR(32) NULL,
   frombalance DECIMAL(10,2) NULL,
   tobalance DECIMAL(10,2) NULL,
   userdescription VARCHAR(34) NULL,
   CONSTRAINT transfrmacctfk FOREIGN KEY (fromaccountid)
     REFERENCES hometownbank.account(accountid),
   CONSTRAINT TRANSTOACCTFK FOREIGN KEY (toaccountid)
     REFERENCES hometownbank.account(accountid));

CREATE TABLE hometownbank.billpayment
  (payeeid VARCHAR(32) NOT NULL,
   transactionid VARCHAR(32) NOT NULL,
   CONSTRAINT billpaymentpk PRIMARY KEY (transactionid, payeeid),
   CONSTRAINT paymentpayeefk FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid),
   CONSTRAINT paymenttransfk FOREIGN KEY (transactionid)
     REFERENCES hometownbank.banktransaction(transactionid)); 

CREATE TABLE hometownbank.purchase
  (purchaseid VARCHAR(32) PRIMARY KEY NOT NULL,
   securityid VARCHAR(32) NOT NULL,
   buytransactionid VARCHAR(32) NOT NULL,
   selltransactionid VARCHAR(32) NULL,
   CONSTRAINT purchasesecurityfk FOREIGN KEY (securityid)
     REFERENCES hometownbank.security(securityid),
   CONSTRAINT purchasebuyfk FOREIGN KEY (buytransactionid)
     REFERENCES hometownbank.banktransaction(transactionid),
   CONSTRAINT purchasesellfk FOREIGN KEY (selltransactionid)
     REFERENCES hometownbank.banktransaction(transactionid));

CREATE TABLE checklookup(
  checkid     VARCHAR(3)   PRIMARY KEY NOT NULL,
  checkdesc   VARCHAR(30)  NOT NULL);
  
CREATE TABLE hometownbank.checkorder(
  orderid      VARCHAR(4)    PRIMARY KEY NOT NULL,
  personid     INT           NOT NULL,
  accountid    VARCHAR(32)   NOT NULL,
  amount       DECIMAL(10,2) NOT NULL,
  checkid      VARCHAR(3)    NOT NULL,
  dateordered  DATETIME      NOT NULL,
  CONSTRAINT chkfk1 FOREIGN KEY (personid)
    REFERENCES hometownbank.person(personid),
  CONSTRAINT chkfk2 FOREIGN KEY (accountid)
    REFERENCES hometownbank.account(accountid));

CREATE TABLE hometownbank.payments(
  paymentid VARCHAR(4) PRIMARY KEY NOT NULL,
  personid INT NOT NULL,
  accountid VARCHAR(32) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  payeeid VARCHAR(32) NOT NULL,
  paydate DATETIME NOT NULL,
  FOREIGN KEY (personid)
    REFERENCES hometownbank.person (personid),
  FOREIGN KEY (accountid)
    REFERENCES hometownbank.account(accountid),
  FOREIGN KEY (payeeid)
    REFERENCES hometownbank.payee(payeeid));
    
COMMIT;

#################
## Insert Data ##
#################

-- Insert Bank account types into lookup table
INSERT INTO hometownbank.lookup
   VALUES('BKNACCTS', 'C', 'CASH', 'BANK CASH');
INSERT INTO hometownbank.lookup
   VALUES('BNKACCTS', 'S', 'SECURITIES', 'BANK SECURITIES');
INSERT INTO hometownbank.lookup
   VALUES('BNKACCTS', 'R', 'RESERVES', 'BANK RESERVES');

-- Insert personal account types into lookup table
INSERT INTO hometownbank.lookup
   VALUES('PACCTS', 'S', 'SAVINGS', 'PERSONAL SAVINGS');
INSERT INTO hometownbank.lookup
   VALUES('PACCTS', 'C', 'CHECKING', 'PERSONAL CHECKING');
INSERT INTO hometownbank.lookup
   VALUES('PACCTS', 'R', 'CREDIT', 'PERSONAL CREDIT CARD');
INSERT INTO hometownbank.lookup
   VALUES('PACCTS', 'U', 'SECURITIES', 'PERSONAL SECURITY');

-- Insert transaction types into lookup table
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'D', 'DEPOSIT', 'DEPOSIT');
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'W', 'WITHDRAWL', 'WITHDRAWL');
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'T', 'TRANSFER', 'TRANSFER FUNDS');
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'P', 'PAYMENT', 'PAY BILL');
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'B', 'BUY', 'BUY SECURITY');
INSERT INTO hometownbank.lookup
   VALUES('TRANS', 'S', 'SELL', 'SELL SECURITY');

-- Insert phone types types into lookup table
INSERT INTO hometownbank.lookup
   VALUES('PHONE', 'H', 'HOME', 'HOME PHONE');
INSERT INTO hometownbank.lookup
   VALUES('PHONE', 'C', 'CELL', 'CELL PHONE');
INSERT INTO hometownbank.lookup
   VALUES('PHONE', 'W', 'WORK', 'WORK PHONE');

-- Insert addresses for branches, persons and payees
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('585 1st St.', NULL, 'Idaho Falls', 'ID', '83402');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('77 E Main', NULL, 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('183 S State', NULL, 'Rigby', 'ID', '83445');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('1455 University Ave.', NULL, 'Provo', 'UT', '84540');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('65 S. Center', NULL, 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('101 Main St.', NULL, 'Chicago', 'IL', '70512');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES ('424 France Ave.', NULL, 'Paris', 'OH', '12891');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('0001 Boulder Lane', NULL, 'Neanderthal', 'PL', '11111');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('0003 Boulder Lane', NULL, 'Neanderthal', 'PL', '11111');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1001 Main Street Ave', NULL, 'Chicago', 'IL', '35578');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('Federation Apartments, #26', '842 College Ave', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('825 North Yellowstone', NULL, 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('435 Woodruff', NULL, 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('7823 Alpine', NULL, 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('355 2nd East 3rd South ', NULL, 'Rigby', 'ID', '83446');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1123 17th Street', NULL, 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('9825 Broadway', NULL, 'Boise', 'ID', '83493');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1821 Spring Hill', NULL, 'Salt Lake City', 'UT', '83561');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1352 7th South 5th East', NULL, 'Preston', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1400 7th South 5th East', NULL, 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street1, street2, city, state, zipcode)
   VALUES('1400 7th South Empire', NULL, 'Death Star', 'ID', '83440');

-- PHONES: Phone numbers
INSERT INTO hometownbank.phone
   VALUES('1', 'H', '0010011111');
INSERT INTO hometownbank.phone
   VALUES('2', 'W', '0010012222');
INSERT INTO hometownbank.phone
   VALUES('3','H', '0010011234');
INSERT INTO hometownbank.phone
   VALUES('4', 'C', '0010015678');
INSERT INTO hometownbank.phone
   VALUES('5', 'W', '0010019876');
INSERT INTO hometownbank.phone
   VALUES('6', 'H', '2083568126');
INSERT INTO hometownbank.phone
   VALUES('7', 'C', '6105218324');
INSERT INTO hometownbank.phone
   VALUES('8', 'C', '6105218412');
INSERT INTO hometownbank.phone
   VALUES('9', 'W', '2084968314');
INSERT INTO hometownbank.phone
   VALUES('10', 'H', '2086561845');
INSERT INTO hometownbank.phone
   VALUES('11', 'W', '2084961182');
INSERT INTO hometownbank.phone
   VALUES('12', 'W', '2087458221');
INSERT INTO hometownbank.phone
   VALUES('13', 'W', '2085213632');
INSERT INTO hometownbank.phone
   VALUES('14', 'W', '2085221233');
INSERT INTO hometownbank.phone
   VALUES('15', 'W', '2087456551');
INSERT INTO hometownbank.phone
   VALUES('16', 'W', '2083563482');
INSERT INTO hometownbank.phone
   VALUES('17', 'W', '2088351232');
INSERT INTO hometownbank.phone
   VALUES('18', 'W', '2086567103');
INSERT INTO hometownbank.phone
   VALUES('19', 'C', '2083569999');
INSERT INTO hometownbank.phone
   VALUES('20', 'C', '2083568888');
INSERT INTO hometownbank.phone
   VALUES('21', 'C', '2083568887');

-- Insert branches
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES ('US Bank - Idaho Falls', 1);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES ('US Bank - Rexburg', 2);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES ('US Bank - Rigby', 3);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES ('Beehive Credit Union - Provo', 4);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES ('Beehive Credit Union - Rexburg', 5);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES('Bank of Nauvoo', 6);
INSERT INTO hometownbank.branch (branchname, addressid)
   VALUES('Empire Bank', 7); 

-- Insert customers
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Fred', 'F', 'Flintstone', 'flintstonef', 'pebbles', 8, 1);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Barney', 'B', 'Rubble', 'rubbleb', 'bambam', 9, 1);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Taylor', '', 'Swift', 'swifty', 'taytay13', 10, 7);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Melissa', 'J', 'Hart', 'sabrina', 'salem', 10, 6);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Jeanluc', NULL, 'Picard', 'locutus', 'wolf359', 11, 3);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Trinity', NULL, 'Trinity', NULL, NULL, 20, 3);
INSERT INTO hometownbank.person (firstname, middlename, lastname, userid, password, addressid, branchid)
   VALUES('Napoleon', NULL, 'Dynamite', NULL, NULL, 20, 3); 

-- Assign phones to persons
INSERT INTO hometownbank.personphone
   VALUES(1, '1');
INSERT INTO hometownbank.personphone
   VALUES(1, '2');
INSERT INTO hometownbank.personphone
   VALUES(2, '3');
INSERT INTO hometownbank.personphone
   VALUES(2, '4');
INSERT INTO hometownbank.personphone
   VALUES(2, '5');
INSERT INTO hometownbank.personphone
   VALUES(3, '6');
INSERT INTO hometownbank.personphone
   VALUES(3, '7');
INSERT INTO hometownbank.personphone
   VALUES(4, '6');
INSERT INTO hometownbank.personphone
   VALUES(4, '8');
INSERT INTO hometownbank.personphone
   VALUES(4, '9');
INSERT INTO hometownbank.personphone
   VALUES(5, '10');
INSERT INTO hometownbank.personphone
   VALUES(5, '11');
INSERT INTO hometownbank.personphone
   VALUES(6, '20');
INSERT INTO hometownbank.personphone
   VALUES(7, '20');

-- Insert personal bank accounts
INSERT INTO hometownbank.account
   VALUES('1', '1001', 5000.00, 'C', 1);
INSERT INTO hometownbank.account
   VALUES('2', '50101', 700.00, 'R', 1);
INSERT INTO hometownbank.account
   VALUES('3', '10506', 8550.00, 'S', 1);
INSERT INTO hometownbank.account
   VALUES('4', '70112', 1000.00, 'U', 1);
INSERT INTO hometownbank.account
   VALUES('5', '1002', 10000.00, 'C', 2);
INSERT INTO hometownbank.account
   VALUES('6', '50103', 1000.00, 'S', 2);
INSERT INTO hometownbank.account
   VALUES('7', '10608', 8050.00, 'S', 2);
INSERT INTO hometownbank.account
   VALUES('8', '7492', 30000.00, 'U', 2);
INSERT INTO hometownbank.account
   VALUES('9', '2001', 10000.00, 'C', 3);
INSERT INTO hometownbank.account
   VALUES('10', '50301', 1000.00, 'S', 3);
INSERT INTO hometownbank.account
   VALUES('11', '13712', 20000.00, 'S', 4);
INSERT INTO hometownbank.account
   VALUES('12', '18032', 60000.00, 'U', 4);
INSERT INTO hometownbank.account
   VALUES('14', '18721', 30000.00, 'S', 5);
INSERT INTO hometownbank.account
   VALUES('15', '15099', 400.00, 'C', 5);
INSERT INTO hometownbank.account
   VALUES('16', '18700', 30000.00, 'S', 6);
INSERT INTO hometownbank.account
   VALUES('17', '15000', 400.00, 'C', 6);
INSERT INTO hometownbank.account
   VALUES('18', '18701', 30000.00, 'R', 6);
INSERT INTO hometownbank.account
   VALUES('19', '15093', 400.00, 'R', 7);

-- Insert bank's accounts
INSERT INTO hometownbank.account
   VALUES('20', '1', 10000000.00, 'H', NULL);
INSERT INTO hometownbank.account
   VALUES('21', '2', 10000000.00, 'Y', NULL);
INSERT INTO hometownbank.account
   VALUES('22', '3', 90000000.00, 'R', NULL);

-- Insert payees
INSERT INTO hometownbank.payee
   VALUES('1', 'BPA ELECTRIC', 12, '12');
INSERT INTO hometownbank.payee
   VALUES('2', 'WESTERN GAS', 13, '13');
INSERT INTO hometownbank.payee
   VALUES('3', 'QWEST', '14', 14);
INSERT INTO hometownbank.payee
   VALUES('4', 'US CELL PHONE', 15, '15');
INSERT INTO hometownbank.payee
   VALUES('5', 'SMARTMART', 16, '16');
INSERT INTO hometownbank.payee
   VALUES('6', 'GOOD FOODS', 17, '17');
INSERT INTO hometownbank.payee
   VALUES('7', 'OPAPO CO', 18, '18');
INSERT INTO hometownbank.payee
   VALUES('8', 'DARTH VADER', 21, '21');
INSERT INTO hometownbank.payee
   VALUES('9', 'UNCLE RICO', 19, '19');

-- Assing payees to customers
INSERT INTO hometownbank.personpayee
   VALUES('1', '8014B', 1, '1');
INSERT INTO hometownbank.personpayee
   VALUES('2', '793135', 1, '2');
INSERT INTO hometownbank.personpayee
   VALUES('3', '775313', 1, '3');
INSERT INTO hometownbank.personpayee
   VALUES('4', '83715712', 1, '4');
INSERT INTO hometownbank.personpayee
   VALUES('5', '8012A', 2, '1');
INSERT INTO hometownbank.personpayee
   VALUES('6', '43901215', 2, '2');
INSERT INTO hometownbank.personpayee
   VALUES('7', '2340981', 2, '3');
INSERT INTO hometownbank.personpayee
   VALUES('8', '23417234', 2, '4');
INSERT INTO hometownbank.personpayee
   VALUES('9', '8012A', 3, '1');
INSERT INTO hometownbank.personpayee
   VALUES('10', '43901215', 3, '2');
INSERT INTO hometownbank.personpayee
   VALUES('11', '55901842', 3, '3');
INSERT INTO hometownbank.personpayee
   VALUES('12', '23417234', 3, '4');
INSERT INTO hometownbank.personpayee
   VALUES('13', '57313591', 4, '5');
INSERT INTO hometownbank.personpayee
   VALUES('14', '47793223', 4, '7');
INSERT INTO hometownbank.personpayee
   VALUES('15', '21561242', 5, '3');
INSERT INTO hometownbank.personpayee
   VALUES('16', '88235721', 5, '6');
INSERT INTO hometownbank.personpayee
   VALUES('17', '21561242', 1, '8');
INSERT INTO hometownbank.personpayee
   VALUES('18', '88235721', 1, '9');

-- Insert securities
INSERT INTO hometownbank.security 
   VALUES('1', '6 Month CD', 0.025, 200.00, 178); 
INSERT INTO hometownbank.security 
   VALUES('2', '1 Year CD', 0.029, 500.00, 356); 
INSERT INTO hometownbank.security 
   VALUES('3', '5 Year CD', 0.035, 1000.00, 1780); 
INSERT INTO hometownbank.security 
   VALUES('4', '10 Year CD', 0.045, 5000.00, 3560); 

-- Insert Checks
INSERT INTO checklookup VALUES ('1', 'Dolphins');
INSERT INTO checklookup VALUES ('2', 'Skittles');
INSERT INTO checklookup VALUES ('3', 'Trains');
INSERT INTO checklookup VALUES ('4', 'Disney');
INSERT INTO checklookup VALUES ('5', 'Boats');
INSERT INTO checklookup VALUES ('6', 'Cars');

-- Insert Payments
INSERT INTO hometownbank.payments VALUES(1, 1, 1, 600, 2, '2001-12-20');
INSERT INTO hometownbank.payments VALUES(2, 1, 3, 1000, 3, '2001-12-21');
INSERT INTO hometownbank.payments VALUES(3, 1, 2, 50, 2, '2001-12-22');
INSERT INTO hometownbank.payments VALUES(4, 1, 1, 100, 2, '2001-12-23');

-- Insert Transactions
INSERT INTO hometownbank.banktransaction VALUES(
 2, 'Transfer', '2008-12-23', 5.00, 'T', 1, 3, 2975, 6998.02, 'Chef Ramsey');
INSERT INTO hometownbank.banktransaction VALUES(
 3, 'Billpay to WESTERN GAS', '2008-12-24', 10.00, 'P', 1, NULL, 2965, NULL, NULL);
INSERT INTO hometownbank.banktransaction VALUES(
 4, 'Checks Ordered', '2008-12-25', 9.99, 'P', 1, NULL, 2955.01, NULL, NULL);
INSERT INTO hometownbank.banktransaction VALUES(
 5, 'Transfer', '2008-12-26', 10.00, 'T', 3, 1, 6988.02, 2965.01, 'Going to the party');

COMMIT; 