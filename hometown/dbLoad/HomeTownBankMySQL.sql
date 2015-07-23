
-- Drop Foreign Key Constraints --

ALTER TABLE hometownbank.branch DROP FOREIGN KEY branchaddressfk;
ALTER TABLE hometownbank.person DROP FOREIGN KEY personbranchfk;
ALTER TABLE hometownbank.person DROP FOREIGN KEY personaddressfk;
ALTER TABLE hometownbank.personphone DROP FOREIGN KEY personphonefk;
ALTER TABLE hometownbank.personphone  DROP FOREIGN KEY phonepersonfk;
ALTER TABLE hometownbank.payee DROP FOREIGN KEY payeeaddressfk;
ALTER TABLE hometownbank.payeephone DROP FOREIGN KEY payeephonefk;
ALTER TABLE hometownbank.payeephone  DROP FOREIGN KEY phonepayeefk;
ALTER TABLE hometownbank.payeeaccount DROP FOREIGN KEY payeepersonfk;
ALTER TABLE hometownbank.payeeaccount DROP FOREIGN KEY personpayeefk;
ALTER TABLE hometownbank.personaccount DROP FOREIGN KEY personaccountfk;
ALTER TABLE hometownbank.personaccount DROP FOREIGN KEY accountpersonfk;
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
DROP TABLE IF EXISTS hometownbank.payeeaccount CASCADE;
DROP TABLE IF EXISTS hometownbank.payeephone CASCADE;
DROP TABLE IF EXISTS hometownbank.payee CASCADE;
DROP TABLE IF EXISTS hometownbank.security CASCADE;
DROP TABLE IF EXISTS hometownbank.personphone CASCADE;
DROP TABLE IF EXISTS hometownbank.phone CASCADE;
DROP TABLE IF EXISTS hometownbank.personaccount CASCADE;
DROP TABLE IF EXISTS hometownbank.person CASCADE;
DROP TABLE IF EXISTS hometownbank.account CASCADE;
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
   street VARCHAR(50) NULL,
   city VARCHAR(20) NULL,
   state VARCHAR(2) NULL,
   zipcode VARCHAR(9) NULL) AUTO_INCREMENT=1;
     
CREATE TABLE hometownbank.phone
  (phoneid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   phonetype VARCHAR(32) NOT NULL,
   phonenumber VARCHAR(15) NOT NULL) AUTO_INCREMENT=1;
   
CREATE TABLE hometownbank.security
  (securityid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   description VARCHAR(60)  NULL,
   rate NUMERIC(10, 4) NULL,
   price NUMERIC(14, 2) NULL,
   daystomaturity NUMERIC(5,0) NULL) AUTO_INCREMENT=1;
   
CREATE TABLE hometownbank.account
  (accountid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   accountno VARCHAR(15) NOT NULL,
   balance DECIMAL(10,2) NOT NULL,
   accounttype VARCHAR(32) NOT NULL) AUTO_INCREMENT=1;
   
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
   lastname VARCHAR(32) NOT NULL,
   username VARCHAR(15) NULL,
   password VARCHAR(10) NULL,
   addressid INT NULL,
   branchid INT NULL,
   CONSTRAINT personbranchfk FOREIGN KEY (branchid)
     REFERENCES hometownbank.branch(branchid),
   CONSTRAINT personaddressfk FOREIGN KEY (addressid)
     REFERENCES hometownbank.address(addressid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.personphone
  (personid INT NOT NULL,
   phoneid INT NOT NULL,
   CONSTRAINT personphonepk PRIMARY KEY (personid, phoneid),
   CONSTRAINT personphonefk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT phonepersonfk FOREIGN KEY (phoneid)
     REFERENCES hometownbank.phone(phoneid));

CREATE TABLE hometownbank.payee
  (payeeid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   company VARCHAR(35) NOT NULL,
   addressid INT NOT NULL,
   CONSTRAINT payeeaddressfk FOREIGN KEY (addressid)
     REFERENCES hometownbank.address(addressid)) AUTO_INCREMENT=1;
     
CREATE TABLE hometownbank.payeephone
  (payeeid INT NOT NULL,
   phoneid INT NOT NULL,
   CONSTRAINT payeephonepk PRIMARY KEY (payeeid, phoneid),
   CONSTRAINT payeephonefk FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid),
   CONSTRAINT phonepayeefk FOREIGN KEY (phoneid)
     REFERENCES hometownbank.phone(phoneid));

CREATE TABLE hometownbank.payeeaccount
  (personid INT NOT NULL,
   payeeid INT NOT NULL,
   payeeaccountno VARCHAR(32) NOT NULL,
   CONSTRAINT payeeaccountpk PRIMARY KEY (personid, payeeid, payeeaccountno),
   CONSTRAINT personpayeefk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT payeepersonfk FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid));
     
CREATE TABLE hometownbank.personaccount
  (personid INT NOT NULL,
   accountid INT NOT NULL,
   CONSTRAINT personaccountpk PRIMARY KEY (personid, accountid),
   CONSTRAINT personaccountfk FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT accountpersonfk FOREIGN KEY (accountid)
     REFERENCES hometownbank.account(accountid));

CREATE TABLE hometownbank.banktransaction
  (transactionid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   description VARCHAR(60) NULL,
   timemade DATETIME NULL, 
   amount DECIMAL(10,2) NULL,
   transtype VARCHAR(32) NOT NULL,
   fromaccountid INT NULL,
   toaccountid INT NULL,
   frombalance DECIMAL(10,2) NULL,
   tobalance DECIMAL(10,2) NULL,
   userdescription VARCHAR(34) NULL,
   CONSTRAINT transfrmacctfk FOREIGN KEY (fromaccountid)
     REFERENCES hometownbank.account(accountid),
   CONSTRAINT transtoacctfk FOREIGN KEY (toaccountid)
     REFERENCES hometownbank.account(accountid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.billpayment
  (payeeid INT NOT NULL,
   transactionid INT NOT NULL,
   CONSTRAINT billpaymentpk PRIMARY KEY (payeeid, transactionid),
   CONSTRAINT paymentpayeefk FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid),
   CONSTRAINT paymenttransfk FOREIGN KEY (transactionid)
     REFERENCES hometownbank.banktransaction(transactionid)); 

CREATE TABLE hometownbank.purchase
  (purchaseid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   securityid INT NOT NULL,
   buytransactionid INT NOT NULL,
   selltransactionid INT NULL,
   CONSTRAINT purchasesecurityfk FOREIGN KEY (securityid)
     REFERENCES hometownbank.security(securityid),
   CONSTRAINT purchasebuyfk FOREIGN KEY (buytransactionid)
     REFERENCES hometownbank.banktransaction(transactionid),
   CONSTRAINT purchasesellfk FOREIGN KEY (selltransactionid)
     REFERENCES hometownbank.banktransaction(transactionid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.checklookup
  (checkid     VARCHAR(3)   PRIMARY KEY NOT NULL,
   checkdesc   VARCHAR(30)  NOT NULL);
  
CREATE TABLE hometownbank.checkorder
  (orderid     INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   personid     INT           NOT NULL,
   accountid    INT   NOT NULL,
   amount       DECIMAL(10,2) NOT NULL,
   checkid      VARCHAR(3)    NOT NULL,
   dateordered  DATETIME      NOT NULL,
   CONSTRAINT chkfk1 FOREIGN KEY (personid)
     REFERENCES hometownbank.person(personid),
   CONSTRAINT chkfk2 FOREIGN KEY (accountid)
     REFERENCES hometownbank.account(accountid)) AUTO_INCREMENT=1;

CREATE TABLE hometownbank.payments
  (paymentid INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
   personid INT NOT NULL,
   accountid INT NOT NULL,
   amount DECIMAL(10,2) NOT NULL,
   payeeid INT NOT NULL,
   paydate DATETIME NOT NULL,
   FOREIGN KEY (personid)
     REFERENCES hometownbank.person (personid),
   FOREIGN KEY (accountid)
     REFERENCES hometownbank.account(accountid),
   FOREIGN KEY (payeeid)
     REFERENCES hometownbank.payee(payeeid)) AUTO_INCREMENT=1;
    
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
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('585 1st St.', 'Idaho Falls', 'ID', '83402');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('77 E Main', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('183 S State', 'Rigby', 'ID', '83445');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('1455 University Ave.', 'Provo', 'UT', '84540');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('65 S. Center', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('101 Main St.', 'Chicago', 'IL', '70512');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES ('424 France Ave.', 'Paris', 'OH', '12891');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('0001 Boulder Lane', 'Neanderthal', 'PL', '11111');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('0003 Boulder Lane', 'Neanderthal', 'PL', '11111');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1001 Main Street Ave', 'Chicago', 'IL', '35578');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('Federation Apartments #26, 842 College Ave', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('825 North Yellowstone', 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('435 Woodruff', 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('7823 Alpine', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('355 2nd East 3rd South ', 'Rigby', 'ID', '83446');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1123 17th Street', 'Idaho Falls', 'ID', '83410');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('9825 Broadway', 'Boise', 'ID', '83493');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1821 Spring Hill', 'Salt Lake City', 'UT', '83561');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1352 7th South 5th East', 'Preston', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1400 7th South 5th East', 'Rexburg', 'ID', '83440');
INSERT INTO hometownbank.address (street, city, state, zipcode)
   VALUES('1400 7th South Empire', 'Death Star', 'ID', '83440');

-- PHONES: Phone numbers
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('HOME', '0010011111');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '0010012222');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('HOME', '0010011234');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '0010015678');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '0010019876');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('HOME', '2083568126');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '6105218324');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '6105218412');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2084968314');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('HOME', '2086561845');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2084961182');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2087458221');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2085213632');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2085221233');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2087456551');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2083563482');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2088351232');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('WORK', '2086567103');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '2083569999');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '2083568888');
INSERT INTO hometownbank.phone(phonetype, phonenumber)
   VALUES('CELL', '2083568887');

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
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Fred', 'F', 'Flintstone', 'flintstonef', 'pebbles', 8, 1);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Barney', 'B', 'Rubble', 'rubbleb', 'bambam', 9, 1);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Taylor', '', 'Swift', 'swifty', 'taytay13', 10, 7);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Melissa', 'J', 'Hart', 'sabrina', 'salem', 10, 6);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Jeanluc', NULL, 'Picard', 'locutus', 'wolf359', 11, 3);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Trinity', NULL, 'Trinity', NULL, NULL, 20, 3);
INSERT INTO hometownbank.person (firstname, middlename, lastname, username, password, addressid, branchid)
   VALUES('Napoleon', NULL, 'Dynamite', NULL, NULL, 20, 3); 

-- Assign phones to persons
INSERT INTO hometownbank.personphone
   VALUES(1, 1);
INSERT INTO hometownbank.personphone
   VALUES(1, 2);
INSERT INTO hometownbank.personphone
   VALUES(2, 3);
INSERT INTO hometownbank.personphone
   VALUES(2, 4);
INSERT INTO hometownbank.personphone
   VALUES(2, 5);
INSERT INTO hometownbank.personphone
   VALUES(3, 6);
INSERT INTO hometownbank.personphone
   VALUES(3, 7);
INSERT INTO hometownbank.personphone
   VALUES(4, 12);
INSERT INTO hometownbank.personphone
   VALUES(4, 8);
INSERT INTO hometownbank.personphone
   VALUES(4, 9);
INSERT INTO hometownbank.personphone
   VALUES(5, 10);
INSERT INTO hometownbank.personphone
   VALUES(5, 11);
INSERT INTO hometownbank.personphone
   VALUES(6, 20);
INSERT INTO hometownbank.personphone
   VALUES(7, 20);

-- Insert personal bank accounts
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('1001', 5000.00, 'CHECKING');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('50101', 700.00, 'CREDIT');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('10506', 8550.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('70112', 1000.00, 'SECURITY');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('1002', 10000.00, 'CHECKING');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('50103', 1000.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('10608', 8050.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('7492', 30000.00, 'SECURITY');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('2001', 10000.00, 'CHECKING');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('50301', 1000.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('13712', 20000.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('18032', 60000.00, 'SECURITY');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('18721', 30000.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('15099', 400.00, 'CHECKING');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('18700', 30000.00, 'SAVINGS');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('15000', 400.00, 'CHECKING');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('18701', 30000.00, 'CREDIT');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('15093', 400.00, 'CREDIT');

-- Insert bank's accounts
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('1', 10000000.00, 'H');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('2', 10000000.00, 'Y');
INSERT INTO hometownbank.account(accountno, balance, accounttype)
   VALUES('3', 90000000.00, 'R');
   
INSERT INTO hometownbank.personaccount
   VALUES(1, 1);
INSERT INTO hometownbank.personaccount
   VALUES(1, 2);
INSERT INTO hometownbank.personaccount
   VALUES(1, 3);
INSERT INTO hometownbank.personaccount
   VALUES(1, 4);

-- Insert payees
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('BPA ELECTRIC', 12);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('WESTERN GAS', 13);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('QWEST', 14);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('US CELL PHONE', 15);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('SMARTMART', 16);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('GOOD FOODS', 17);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('OPAPO CO', 18);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('DARTH VADER', 21);
INSERT INTO hometownbank.payee(company, addressid)
   VALUES('UNCLE RICO', 19);
   
-- Insert payee phone numbers
INSERT INTO hometownbank.payeephone
   VALUES(1, 15);
INSERT INTO hometownbank.payeephone
   VALUES(2, 16);
INSERT INTO hometownbank.payeephone
   VALUES(3, 16);
INSERT INTO hometownbank.payeephone
   VALUES(4, 15);
INSERT INTO hometownbank.payeephone
   VALUES(5, 17);
INSERT INTO hometownbank.payeephone
   VALUES(6, 17);
INSERT INTO hometownbank.payeephone
   VALUES(7, 17);
INSERT INTO hometownbank.payeephone
   VALUES(8, 18);
INSERT INTO hometownbank.payeephone
   VALUES(9, 18);

-- Assing payees to customers
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 1, '8014B');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 2, '793135');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 3, '775313');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 4, '83715712');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(2, 1, '8012A');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(2, 2, '43901215');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(2, 3, '2340981');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(2, 4, '23417234');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(3, 1, '8012A');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(3, 2, '43901215');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(3, 3, '55901842');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(3, 4, '23417234');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(4, 5, '57313591');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(4, 7, '47793223');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(5, 3, '21561242');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(5, 6, '88235721');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 8, '21561242');
INSERT INTO hometownbank.payeeaccount(personid, payeeid, payeeaccountno)
   VALUES(1, 9, '88235721');

-- Insert securities
INSERT INTO hometownbank.security (description, rate, price, daystomaturity)
   VALUES('6 Month CD', 0.025, 200.00, 178); 
INSERT INTO hometownbank.security (description, rate, price, daystomaturity)
   VALUES('1 Year CD', 0.029, 500.00, 356); 
INSERT INTO hometownbank.security (description, rate, price, daystomaturity)
   VALUES('5 Year CD', 0.035, 1000.00, 1780); 
INSERT INTO hometownbank.security (description, rate, price, daystomaturity)
   VALUES('10 Year CD', 0.045, 5000.00, 3560); 

-- Insert Checks
INSERT INTO checklookup VALUES ('1', 'Dolphins');
INSERT INTO checklookup VALUES ('2', 'Skittles');
INSERT INTO checklookup VALUES ('3', 'Trains');
INSERT INTO checklookup VALUES ('4', 'Disney');
INSERT INTO checklookup VALUES ('5', 'Boats');
INSERT INTO checklookup VALUES ('6', 'Cars');

-- Insert Payments
INSERT INTO hometownbank.payments (personid, accountid, amount, payeeid, paydate)
   VALUES(1, 1, 600, 2, '2001-12-20');
INSERT INTO hometownbank.payments (personid, accountid, amount, payeeid, paydate)
   VALUES(1, 3, 1000, 3, '2001-12-21');
INSERT INTO hometownbank.payments (personid, accountid, amount, payeeid, paydate)
   VALUES(1, 2, 50, 2, '2001-12-22');
INSERT INTO hometownbank.payments (personid, accountid, amount, payeeid, paydate)
   VALUES(1, 1, 100, 2, '2001-12-23');

-- Insert Transactions
INSERT INTO hometownbank.banktransaction
   (description, timemade, amount, transtype, fromaccountid, toaccountid, frombalance, tobalance, userdescription)
   VALUES('Transfer', '2008-12-23', 5.00, 'TRANSFER', 1, 3, 2975, 6998.02, 'Chef Ramsey');
INSERT INTO hometownbank.banktransaction
   (description, timemade, amount, transtype, fromaccountid, toaccountid, frombalance, tobalance, userdescription)
   VALUES('Billpay to WESTERN GAS', '2008-12-24', 10.00, 'BILL_PAYMENT', 1, NULL, 2965, NULL, NULL);
INSERT INTO hometownbank.banktransaction
   (description, timemade, amount, transtype, fromaccountid, toaccountid, frombalance, tobalance, userdescription)
   VALUES('Checks Ordered', '2008-12-25', 9.99, 'BILL_PAYMENT', 1, NULL, 2955.01, NULL, NULL);
INSERT INTO hometownbank.banktransaction
   (description, timemade, amount, transtype, fromaccountid, toaccountid, frombalance, tobalance, userdescription)
   VALUES('Transfer', '2008-12-26', 10.00, 'TRANSFER', 3, 1, 6988.02, 2965.01, 'Going to the party');

COMMIT; 