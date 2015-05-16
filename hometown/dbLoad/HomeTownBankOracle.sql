
-- Updated by RKJ: Oct. 16  MST 2007

-- Drop sequences

DROP SEQUENCE HOMETOWNBANK.seqLookup;
DROP SEQUENCE HOMETOWNBANK.seqBranch;
DROP SEQUENCE HOMETOWNBANK.seqPerson;
DROP SEQUENCE HOMETOWNBANK.seqAddress;
DROP SEQUENCE HOMETOWNBANK.seqPhone;
DROP SEQUENCE HOMETOWNBANK.seqPayee;
DROP SEQUENCE HOMETOWNBANK.seqSecurity;
DROP SEQUENCE HOMETOWNBANK.seqAccount;
DROP SEQUENCE HOMETOWNBANK.seqTransaction;
DROP SEQUENCE HOMETOWNBANK.seqBillPayment;
DROP SEQUENCE HOMETOWNBANK.seqPurchase;

-- Drop Foreign Key Constraints 

ALTER TABLE HOMETOWNBANK.BRANCH DROP CONSTRAINT BRANCHADDRESSFK; 
ALTER TABLE HOMETOWNBANK.PERSON DROP CONSTRAINT PERSONBRANCHFK;
ALTER TABLE HOMETOWNBANK.PERSON DROP CONSTRAINT PERSONADDRESSFK;
ALTER TABLE HOMETOWNBANK.PERSONPHONE DROP CONSTRAINT PERSONPHONEFK;
ALTER TABLE HOMETOWNBANK.PERSONPHONE  DROP CONSTRAINT PHONEPERSONFK;
ALTER TABLE HOMETOWNBANK.PAYEE DROP CONSTRAINT PAYEEADDRESSFK;
ALTER TABLE HOMETOWNBANK.PAYEE DROP CONSTRAINT PAYEEPHONEFK;
ALTER TABLE HOMETOWNBANK.PERSONPAYEE DROP CONSTRAINT PAYEEPERSONFK;
ALTER TABLE HOMETOWNBANK.PERSONPAYEE DROP CONSTRAINT PERSONPAYEEFK;
ALTER TABLE HOMETOWNBANK.ACCOUNT DROP CONSTRAINT ACCOUNTPERSONFK;
ALTER TABLE HOMETOWNBANK.BANKTRANSACTION DROP CONSTRAINT TRANSFRMACCTFK;
ALTER TABLE HOMETOWNBANK.BANKTRANSACTION DROP CONSTRAINT TRANSTOACCTFK;
ALTER TABLE HOMETOWNBANK.BILLPAYMENT DROP CONSTRAINT PAYMENTPAYEEFK;
ALTER TABLE HOMETOWNBANK.BILLPAYMENT DROP CONSTRAINT PAYMENTTRANSFK;
ALTER TABLE HOMETOWNBANK.PURCHASE DROP CONSTRAINT PURCHSESECURITYFK;
ALTER TABLE HOMETOWNBANK.PURCHASE DROP CONSTRAINT PURCHASEBUYFK;
ALTER TABLE HOMETOWNBANK.PURCHASE DROP CONSTRAINT PURCHASESELLFK;

-- Drop tables
DROP TABLE HOMETOWNBANK.LOOKUP CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.BRANCH CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PERSON CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.ADDRESS CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PHONE CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PERSONPHONE CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PAYEE CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.SECURITY CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PERSONPAYEE CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.BANKTRANSACTION CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.BILLPAYMENT CASCADE CONSTRAINTS;
DROP TABLE HOMETOWNBANK.PURCHASE CASCADE CONSTRAINTS;



-- Create sequences


CREATE SEQUENCE HOMETOWNBANK.seqLookup INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqBranch INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPerson INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqAddress INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPhone INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPayee INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqSecurity INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqAccount INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqTransaction INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqBillPayment INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPurchase INCREMENT BY 1 START WITH 1;




-- Create tables
CREATE TABLE HOMETOWNBANK.LOOKUP
   (LOOKUPGROUP VARCHAR2(10) NOT NULL,
    LOOKUPKEY VARCHAR2(1) NOT NULL,
    SHORTVALUE VARCHAR2(10) NOT NULL,
    LONGVALUE VARCHAR2(35) NULL);

ALTER TABLE HOMETOWNBANK.LOOKUP
  ADD CONSTRAINT LOOKUPPK PRIMARY KEY (LOOKUPGROUP,LOOKUPKEY);

CREATE TABLE HOMETOWNBANK.BRANCH
  (BRANCHID VARCHAR(32) NOT NULL,
   BRANCHNAME VARCHAR2(50) NOT NULL,
   ADDRESSID VARCHAR2(32) NULL);

ALTER TABLE HOMETOWNBANK.BRANCH
  ADD CONSTRAINT BRANCHPK PRIMARY KEY (BRANCHID);

CREATE TABLE HOMETOWNBANK.PERSON
  (PERSONID VARCHAR(32) NOT NULL,
   FIRSTNAME VARCHAR2(15) NOT NULL,
   MIDDLENAME VARCHAR2(15) NULL,
   LASTNAME VARCHAR2(15) NOT NULL,
   USERID VARCHAR2(15) NULL,
   PASSWORD VARCHAR2(10) NULL,
   ADDRESSID VARCHAR(32) NULL,
   BRANCHID VARCHAR(32) NULL);

ALTER TABLE HOMETOWNBANK.PERSON
  ADD CONSTRAINT PERSONPK PRIMARY KEY (PERSONID);

CREATE TABLE HOMETOWNBANK.ADDRESS
  (ADDRESSID VARCHAR(32) NOT NULL,
   STREET1 VARCHAR(30) NULL,
   STREET2 VARCHAR(30) NULL,
   CITY VARCHAR(20) NULL,
   STATE VARCHAR(2) NULL,
   ZIPCODE VARCHAR(9) NULL);

ALTER TABLE HOMETOWNBANK.ADDRESS
  ADD CONSTRAINT ADDRESSPK PRIMARY KEY (ADDRESSID);
  
CREATE TABLE HOMETOWNBANK.PHONE
	(PHONEID VARCHAR(32) NOT NULL,
	 PHONETYPE VARCHAR2(1) NOT NULL,
	 PHONE VARCHAR(13) NOT NULL);

ALTER TABLE HOMETOWNBANK.PHONE
  ADD CONSTRAINT PHONEPK PRIMARY KEY (PHONEID);

CREATE TABLE HOMETOWNBANK.PERSONPHONE
	(PERSONID VARCHAR2(32) NOT NULL,
     PHONEID VARCHAR2(32) NOT NULL);

ALTER TABLE HOMETOWNBANK.PERSONPHONE
  ADD CONSTRAINT PERSONPHONEPK PRIMARY KEY (PERSONID,PHONEID);

CREATE TABLE HOMETOWNBANK.PAYEE
  (PAYEEID VARCHAR2(32) NOT NULL,
   COMPANY VARCHAR2(35) NOT NULL,
   ADDRESSID VARCHAR2(32) NOT NULL,
   PHONEID VARCHAR2(32) NULL);

ALTER TABLE HOMETOWNBANK.PAYEE
  ADD CONSTRAINT PAYEEPK PRIMARY KEY (PAYEEID);


CREATE TABLE HOMETOWNBANK.PERSONPAYEE
  (PERSONPAYEEID VARCHAR2(32) NOT NULL,
   PAYEEACCOUNTNO VARCHAR2(32) NOT NULL,
   PERSONID VARCHAR2(32) NOT NULL,
   PAYEEID VARCHAR2(32) NOT NULL);

ALTER TABLE HOMETOWNBANK.PERSONPAYEE
  ADD CONSTRAINT PERSONPAYEEPK PRIMARY KEY (PERSONPAYEEID);



CREATE TABLE HOMETOWNBANK.SECURITY
  (SECURITYID VARCHAR2(32) NOT NULL,
   DESCRIPTION VARCHAR2(60)  NULL,
   RATE NUMERIC(10, 4) NULL,
   PRICE NUMERIC(14, 2) NULL,
   DAYSTOMATURITY NUMERIC(5,0) NULL);

ALTER TABLE HOMETOWNBANK.SECURITY
  ADD CONSTRAINT SECURITYPK PRIMARY KEY (SECURITYID);


CREATE TABLE HOMETOWNBANK.ACCOUNT
  (ACCOUNTID VARCHAR2(32) NOT NULL,
   ACCOUNTNO VARCHAR2(15) NOT NULL,
   BALANCE NUMBER(10,2) NOT NULL,
   ACCOUNTTYPE VARCHAR2(1) NOT NULL,
   PERSONID VARCHAR2(32) NULL);  

ALTER TABLE HOMETOWNBANK.ACCOUNT
  ADD CONSTRAINT ACCOUNTPK PRIMARY KEY (ACCOUNTID);


CREATE TABLE HOMETOWNBANK.BANKTRANSACTION
  (TRANSACTIONID VARCHAR(32) NOT NULL,
   DESCRIPTION VARCHAR2(60) NULL,
   TIMEMADE DATE NULL, 
   AMOUNT NUMBER(10, 2) NULL,
   TRANSTYPE VARCHAR2(1) NOT NULL,
   FROMACCOUNTID VARCHAR(32) NULL,
   TOACCOUNTID VARCHAR(32) NULL,
   FROMBALANCE NUMBER(10,2) NULL,
   TOBALANCE NUMBER(10,2) NULL,
   USERDESCRIPTION VARCHAR2(34) NULL);  

ALTER TABLE HOMETOWNBANK.BANKTRANSACTION
  ADD CONSTRAINT TRANSACTIONPK PRIMARY KEY (TRANSACTIONID);

CREATE TABLE HOMETOWNBANK.BILLPAYMENT
  (PAYEEID VARCHAR2(32) NOT NULL,
   TRANSACTIONID VARCHAR(32) NOT NULL); 

ALTER TABLE HOMETOWNBANK.BILLPAYMENT
  ADD CONSTRAINT BILLPAYMENT PRIMARY KEY (TRANSACTIONID, PAYEEID);

CREATE TABLE HOMETOWNBANK.PURCHASE
  (PURCHASEID VARCHAR(32) NOT NULL,
   SECURITYID VARCHAR(32) NOT NULL,
   BUYTRANSACTIONID VARCHAR2(32) NOT NULL,
   SELLTRANSACTIONID VARCHAR2(32) NULL);

ALTER TABLE HOMETOWNBANK.PURCHASE
  ADD CONSTRAINT BUYSELLORDERPK PRIMARY KEY (PURCHASEID);  
  


-- Create foreign key constraints 
ALTER TABLE HOMETOWNBANK.BRANCH
  ADD CONSTRAINT BRANCHADDRESSFK FOREIGN KEY (ADDRESSID)
  REFERENCES HOMETOWNBANK.ADDRESS(ADDRESSID);

ALTER TABLE HOMETOWNBANK.PERSON
  ADD CONSTRAINT PERSONBRANCHFK FOREIGN KEY (BRANCHID)
  REFERENCES HOMETOWNBANK.BRANCH(BRANCHID);
  
ALTER TABLE HOMETOWNBANK.PERSON
  ADD CONSTRAINT PERSONADDRESSFK FOREIGN KEY (ADDRESSID)
  REFERENCES HOMETOWNBANK.ADDRESS(ADDRESSID); 

ALTER TABLE HOMETOWNBANK.PERSONPHONE
  ADD CONSTRAINT PERSONPHONEFK FOREIGN KEY (PERSONID)
  REFERENCES HOMETOWNBANK.PERSON(PERSONID);  
  
ALTER TABLE HOMETOWNBANK.PERSONPHONE
  ADD CONSTRAINT PHONEPERSONFK FOREIGN KEY (PHONEID)
  REFERENCES HOMETOWNBANK.PHONE(PHONEID); 
 
ALTER TABLE HOMETOWNBANK.PAYEE
  ADD CONSTRAINT PAYEEADDRESSFK FOREIGN KEY (ADDRESSID)
  REFERENCES HOMETOWNBANK.ADDRESS(ADDRESSID); 

ALTER TABLE HOMETOWNBANK.PAYEE
  ADD CONSTRAINT PAYEEPHONEFK FOREIGN KEY (PHONEID)
  REFERENCES HOMETOWNBANK.PHONE(PHONEID);

ALTER TABLE HOMETOWNBANK.PERSONPAYEE
  ADD CONSTRAINT PERSONPAYEEFK FOREIGN KEY (PERSONID)
  REFERENCES HOMETOWNBANK.PERSON(PERSONID);

ALTER TABLE HOMETOWNBANK.PERSONPAYEE
  ADD CONSTRAINT PAYEEPERSONFK FOREIGN KEY (PAYEEID)
  REFERENCES HOMETOWNBANK.PAYEE(PAYEEID);

ALTER TABLE HOMETOWNBANK.ACCOUNT
  ADD CONSTRAINT ACCOUNTPERSONFK FOREIGN KEY (PERSONID)
  REFERENCES HOMETOWNBANK.PERSON(PERSONID);

ALTER TABLE HOMETOWNBANK.BANKTRANSACTION
  ADD CONSTRAINT TRANSFRMACCTFK FOREIGN KEY (FROMACCOUNTID)
  REFERENCES HOMETOWNBANK.ACCOUNT(ACCOUNTID);
  
ALTER TABLE HOMETOWNBANK.BANKTRANSACTION
  ADD CONSTRAINT TRANSTOACCTFK FOREIGN KEY (TOACCOUNTID)
  REFERENCES HOMETOWNBANK.ACCOUNT(ACCOUNTID);  

ALTER TABLE HOMETOWNBANK.BILLPAYMENT
  ADD CONSTRAINT PAYMENTPAYEEFK FOREIGN KEY (PAYEEID)
  REFERENCES HOMETOWNBANK.PAYEE(PAYEEID);

ALTER TABLE HOMETOWNBANK.BILLPAYMENT
  ADD CONSTRAINT PAYMENTTRANSFK FOREIGN KEY (TRANSACTIONID)
  REFERENCES HOMETOWNBANK.BANKTRANSACTION(TRANSACTIONID);

ALTER TABLE HOMETOWNBANK.PURCHASE
  ADD CONSTRAINT PURCHSESECURITYFK FOREIGN KEY (SECURITYID)
    REFERENCES HOMETOWNBANK.SECURITY(SECURITYID);

ALTER TABLE HOMETOWNBANK.PURCHASE
  ADD CONSTRAINT PURCHASEBUYFK FOREIGN KEY (BUYTRANSACTIONID)
    REFERENCES HOMETOWNBANK.BANKTRANSACTION(TRANSACTIONID);

ALTER TABLE HOMETOWNBANK.PURCHASE
  ADD CONSTRAINT PURCHASESELLFK FOREIGN KEY (SELLTRANSACTIONID)
    REFERENCES HOMETOWNBANK.BANKTRANSACTION(TRANSACTIONID);

COMMIT;



-- Insert Bank account types into lookup table
INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('BKNACCTS', 'C', 'CASH', 'BANK CASH');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('BNKACCTS', 'S', 'SECURITIES', 'BANK SECURITIES');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('BNKACCTS', 'R', 'RESERVES', 'BANK RESERVES');



-- Insert personal account types into lookup table
INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PACCTS', 'S', 'SAVINGS', 'PERSONAL SAVINGS');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PACCTS', 'C', 'CHECKING', 'PERSONAL CHECKING');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PACCTS', 'R', 'CREDIT', 'PERSONAL CREDIT CARD');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PACCTS', 'U', 'SECURITIES', 'PERSONAL SECURITY');



-- Insert transaction types into lookup table
INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'D', 'DEPOSIT', 'DEPOSIT');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'W', 'WITHDRAWL', 'WITHDRAWL');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'T', 'TRANSFER', 'TRANSFER FUNDS');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'P', 'PAYMENT', 'PAY BILL');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'B', 'BUY', 'BUY SECURITY');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('TRANS', 'S', 'SELL', 'SELL SECURITY');



-- Insert phone types types into lookup table
INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PHONE', 'H', 'HOME', 'HOME PHONE');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PHONE', 'C', 'CELL', 'CELL PHONE');

INSERT INTO HOMETOWNBANK.LOOKUP
   VALUES('PHONE', 'W', 'WORK', 'WORK PHONE');



-- Insert addresses for branches, persons and payees
INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('1',  '585 1st St.', NULL, 'Idaho Falls', 'ID', '83402');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('2',  '77 E Main', NULL, 'Rexburg', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('3',  '183 S State', NULL, 'Rigby', 'ID', '83445');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('4',  '1455 University Ave.', NULL, 'Provo', 'UT', '84540');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('5',  '65 S. Center', NULL, 'Rexburg', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('6',  '101 Main St.', NULL, 'Nauvoo', 'IL', '70512');

INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES ('7',  '424 Nephi Ave.', NULL, 'Kirtland', 'OH', '12891');
   
INSERT INTO HOMETOWNBANK.ADDRESS
   VALUES('8', '0001 Boulder Lane', NULL, 'Neanderthal', 'PL', '11111');
   
INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('9', '0003 Boulder Lane', NULL, 'Neanderthal', 'PL', '11111');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('10', '1001 Main Street', NULL, 'Nauvoo', 'IL', '35578');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('11', 'Celestial Apartments, #26', '842 College Ave', 'Rexburg', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('12', '825 North Yellowstone', NULL, 'Idaho Falls', 'ID', '83410');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('13', '435 Woodruff', NULL, 'Idaho Falls', 'ID', '83410');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('14', '7823 Alpine', NULL, 'Rexburg', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('15', '355 2nd East 3rd South ', NULL, 'Rigby', 'ID', '83446');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('16', '1123 17th Street', NULL, 'Idaho Falls', 'ID', '83410');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('17', '9825 Broadway', NULL, 'Boise', 'ID', '83493');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('18', '1821 Spring Hill', NULL, 'Salt Lake City', 'UT', '83561');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('19', '1352 7th South 5th East', NULL, 'Preston', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('20', '1400 7th South 5th East', NULL, 'Rexburg', 'ID', '83440');

INSERT INTO HOMETOWNBANK.ADDRESS  
   VALUES('21', '1400 7th South Empire', NULL, 'Death Star', 'ID', '83440');


  
-- PHONES: Phone numbers
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('1', 'H', '0010011111');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('2', 'W', '0010012222'); 
		  
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('3','H', '0010011234');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('4', 'C', '0010015678');	 

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('5', 'W', '0010019876');	 
		  
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('6', 'H', '2083568126');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('7', 'C', '6105218324');   

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('8', 'C', '6105218412');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('9', 'W', '2084968314');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('10', 'H', '2086561845');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('11', 'W', '2084961182');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('12', 'W', '2087458221');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('13', 'W', '2085213632');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('14', 'W', '2085221233');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('15', 'W', '2087456551');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('16', 'W', '2083563482');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('17', 'W', '2088351232');
 
INSERT INTO HOMETOWNBANK.PHONE
   VALUES('18', 'W', '2086567103');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('19', 'C', '2083569999');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('20', 'C', '2083568888');

INSERT INTO HOMETOWNBANK.PHONE
   VALUES('21', 'C', '2083568887');



-- Insert branches
INSERT INTO HOMETOWNBANK.BRANCH
   VALUES ('1', 'US Bank - Idaho Falls', '1');

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES ('2', 'US Bank - Rexburg', '2');

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES ('3', 'US Bank - Rigby', '3');

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES ('4', 'Beehive Credit Union - Provo', '4');

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES ('5', 'Beehive Credit Union - Rexburg', '5');

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES('6', 'Bank of Nauvoo', '6');    

INSERT INTO HOMETOWNBANK.BRANCH
   VALUES('7', 'Bank of Kirtland','7'); 



-- Insert customers
INSERT INTO HOMETOWNBANK.PERSON
   VALUES('1', 'Fred', 'F', 'Flintstone', 'flintstonef', 'pebbles', '8','1');
   
INSERT INTO HOMETOWNBANK.PERSON
   VALUES('2', 'Barney', 'B', 'Rubble', 'rubbleb', 'bambam', '9', '1'); 
    
INSERT INTO HOMETOWNBANK.PERSON
   VALUES('3', 'Joseph', '', 'Smith', 'smithj', 'kirtland', '10', '7');
   
INSERT INTO HOMETOWNBANK.PERSON
   VALUES('4', 'Emma', NULL, 'Smith', 'smithe', 'nauvoo', '10', '6');
     
INSERT INTO HOMETOWNBANK.PERSON
   VALUES('5', 'Richard', NULL, 'Isa', 'asdf', 'asdf', '11', '3');

INSERT INTO HOMETOWNBANK.PERSON
   VALUES('6', 'Mike', NULL, 'White', '', '', '20', '3');

INSERT INTO HOMETOWNBANK.PERSON
   VALUES('7', 'Joyous', NULL, 'White', '', '', '20', '3'); 



-- Assign phones to persons
INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('1', '1');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('1', '2');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('2', '3');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('2', '4');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('2', '5');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('3', '6');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('3', '7');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('4', '6');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('4', '8');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('4', '9');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('5', '10');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('5', '11');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('6', '20');

INSERT INTO HOMETOWNBANK.PERSONPHONE
   VALUES('7', '20');



-- Insert personal bank accounts
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('1', '1001', 5000.00, 'C', '1');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('2', '50101', 700.00, 'R', '1');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('3', '10506', 8550.00, 'S', '1');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('4', '70112', 1000.00, 'U', '1');

INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('5', '1002', 10000.00, 'C', '2');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('6', '50103', 1000.00, 'S', '2');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('7', '10608', 8050.00, 'S', '2');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('8', '7492', 30000.00, 'U', '2');
        
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('9', '2001', 10000.00, 'C', '3');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('10', '50301', 1000.00, 'S', '3');    
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('11', '13712', 20000.00, 'S', '4');
   
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('12', '18032', 60000.00, 'U', '4');

INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('14', '18721', 30000.00, 'S', '5');    
  
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('15', '15099', 400.00, 'C', '5'); 

INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('16', '18700', 30000.00, 'S', '6');    
  
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('17', '15000', 400.00, 'C', '6');
 
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('18', '18701', 30000.00, 'R', '6');    
  
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('19', '15093', 400.00, 'R', '7');



-- Insert bank's accounts
INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('16', '1', 10000000.00, 'H', NULL);

INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('17', '2', 10000000.00, 'Y', NULL);

INSERT INTO HOMETOWNBANK.ACCOUNT
   VALUES('18', '3', 90000000.00, 'R', NULL);



-- Insert payees
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('1', 'BPA ELECTRIC', '12', '12');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('2', 'WESTERN GAS', '13', '13');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('3', 'QWEST', '14', '14');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('4', 'US CELL PHONE', '15', '15');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('5', 'SMARTMART', '16', '16');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('6', 'GOOD FOODS', '17', '17');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('7', 'OPAPO CO', '18', '18');

INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('8', 'DARTH VADER', '21', '21');
   
INSERT INTO HOMETOWNBANK.PAYEE
   VALUES('9', 'UNCLE RICO', '19', '19');



-- Assing payees to customers
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('1', '8014B', 1', '1');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('2', '793135', '1', '2');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('3', '775313', '1', '3');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('4', '83715712','1', '4');

INSERT INTO hometownbank.PERSONPAYEE
   VALUES('5', '8012A', '2', '1');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('6', '43901215', '2', '2');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('7', '2340981', '2', '3');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('8', '23417234', '2', '4');

INSERT INTO hometownbank.PERSONPAYEE
   VALUES('9', '8012A', '3', '1');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('10', '43901215', '3', '2');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('11', '55901842', '3', '3');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('12', '23417234', '3', '4');

INSERT INTO hometownbank.PERSONPAYEE
   VALUES('13', '57313591', '4', '5');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('14', '47793223', '4', '7');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('15', '21561242', '5', '3');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('16', '88235721', '5', '6');

INSERT INTO hometownbank.PERSONPAYEE
   VALUES('17', '21561242', '1', '21');
       
INSERT INTO hometownbank.PERSONPAYEE
   VALUES('18', '88235721', '1', '19');



-- Insert securities
INSERT INTO HOMETOWNBANK.SECURITY 
   VALUES('1', '6 Month CD', 0.025, 200.00, 178); 

INSERT INTO HOMETOWNBANK.SECURITY 
   VALUES('2', '1 Year CD', 0.029, 500.00, 356); 

INSERT INTO HOMETOWNBANK.SECURITY 
   VALUES('3', '5 Year CD', 0.035, 1000.00, 1780); 

INSERT INTO HOMETOWNBANK.SECURITY 
   VALUES('4', '10 Year CD', 0.045, 5000.00, 3560); 

------------------------------------------------------------------------

drop sequence hometownbank.seqcheckorder;
drop sequence hometownbank.seqPersonPayee;
drop sequence hometownbank.seqAddress;
drop sequence hometownbank.seqPhone;
drop sequence hometownbank.seqPayee;
drop sequence hometownbank.seqPayments;
drop table hometownbank.checkorder cascade constraints;
drop table hometownbank.checkLookup cascade constraints;
drop table hometownbank.payments cascade constraints;
ALTER TABLE HOMETOWNBANK.banktransaction drop column tobalance;
ALTER TABLE HOMETOWNBANK.banktransaction drop column frombalance;

CREATE SEQUENCE HOMETOWNBANK.seqcheckOrder INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPersonPayee INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqAddress INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPhone INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPayee INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE HOMETOWNBANK.seqPayments INCREMENT BY 1 START WITH 1;

Create table checkLookup(
  checkId     VARCHAR2(3)   NOT NULL
, checkDesc   Varchar2(30)  NOT NULL);

ALTER TABLE HOMETOWNBANK.checkLookup ADD CONSTRAINT checkLookuppk PRIMARY KEY (checkId);

INSERT INTO checkLookup VALUES ('1', 'Dolphins');
INSERT INTO checkLookup VALUES ('2', 'Skittles');
INSERT INTO checkLookup VALUES ('3', 'Trains');
INSERT INTO checkLookup VALUES ('4', 'Disney');
INSERT INTO checkLookup VALUES ('5', 'Boats');
INSERT INTO checkLookup VALUES ('6', 'Cars');

create table checkOrder(
  orderId      VARCHAR2(3)  NOT NULL
, personId     VARCHAR2(3)  NOT NULL
, accountId    VARCHAR2(3)  NOT NULL
, amount       NUMBER(10,2) NOT NULL
, checkId      VARCHAR2(3)  NOT NULL
, dateOrdered  DATE         NOT NULL);

ALTER TABLE HOMETOWNBANK.checkOrder ADD CONSTRAINT checkpk PRIMARY KEY (orderId);

ALTER TABLE HOMETOWNBANK.checkOrder ADD CONSTRAINT fk1 FOREIGN KEY (personId)
  REFERENCES HOMETOWNBANK.person(personId);

ALTER TABLE HOMETOWNBANK.checkOrder ADD CONSTRAINT fk2 FOREIGN KEY (accountId)
  REFERENCES HOMETOWNBANK.account(accountId);

ALTER TABLE HOMETOWNBANK.banktransaction ADD (frombalance  NUMBER(10,2) NULL);
ALTER TABLE HOMETOWNBANK.banktransaction ADD (tobalance    NUMBER(10,2) NULL);

CREATE TABLE HOMETOWNBANK.payments(
  paymentId     VARCHAR2(4)  NOT NULL
, personId      VARCHAR2(4)  NOT NULL
, accountId     VARCHAR2(4)  NOT NULL
, payeeId       VARCHAR2(4)  NOT NULL
, amount        NUMBER(10,2) NOT NULL
, paydate       DATE         NOT NULL);     

ALTER TABLE HOMETOWNBANK.payments ADD CONSTRAINT paymentpk PRIMARY KEY (paymentId);

ALTER TABLE HOMETOWNBANK.payments ADD CONSTRAINT paymentfk1 FOREIGN KEY (accountId)
  REFERENCES HOMETOWNBANK.account(accountId);

ALTER TABLE HOMETOWNBANK.payments ADD CONSTRAINT paymentfk2 FOREIGN KEY (payeeId)
  REFERENCES HOMETOWNBANK.payee(payeeId);

ALTER TABLE HOMETOWNBANK.payments ADD CONSTRAINT paymentfk3 FOREIGN KEY (personId)
  REFERENCES HOMETOWNBANK.person(personId);

INSERT INTO HOMETOWNBANK.payments VALUES(
 1, 1, 1, 2, 600, '23-DEC-20');

INSERT INTO HOMETOWNBANK.payments VALUES(
 2, 1, 3, 3, 1000, '23-DEC-09');

INSERT INTO HOMETOWNBANK.payments VALUES(
 3, 1, 2, 2, 50, '12-JAN-10');

INSERT INTO HOMETOWNBANK.payments VALUES(
 4, 1, 1, 2, 100, '29-OCT-09');

INSERT INTO HOMETOWNBANK.banktransaction VALUES(
 2, 'Transfer', '07-NOV-08', 5.00, 'T', 1, 3, 2975, 6998.02, 'Chef Ramsey');

INSERT INTO HOMETOWNBANK.banktransaction VALUES(
 3, 'Billpay to WESTERN GAS', '08-NOV-08', 10.00, 'P', 1, NULL, 2965, NULL, NULL);

INSERT INTO HOMETOWNBANK.banktransaction VALUES(
 4, 'Checks Ordered', '09-NOV-08', 9.99, 'P', 1, NULL, 2955.01, NULL, NULL);

INSERT INTO HOMETOWNBANK.banktransaction VALUES(
 5, 'Transfer', '10-NOV-08', 10.00, 'T', 3, 1, 6988.02, 2965.01, 'Going to the party');

COMMIT; 