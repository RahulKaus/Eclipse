Drop table Product_Offering cascade constraints;
Drop table Product_Specification cascade constraints;
Drop table Service cascade constraints;
Drop table customer cascade constraints;
Drop table Product_Attributes cascade constraints;
Drop table Service_Attributes cascade constraints;
Drop table Product_Attribute_Values cascade constraints;
Drop table Service_Attribute_Values cascade constraints;
Drop table users cascade constraints;
Drop table Exchange cascade constraints;
drop sequence hibernate_sequence;
create sequence hibernate_sequence start with 1010 increment by 1;

CREATE TABLE Product_Specification(
       ID number(8),
       Name varchar2(50),
       CONSTRAINT pk_Product_Specification PRIMARY KEY(ID)
       
);

CREATE TABLE Product_Offering(
       ID number(8),
       Name varchar2(50),
       Specification_id  number(8), 
       CONSTRAINT pk_Product_Offering PRIMARY KEY(ID),
       CONSTRAINT fk_Specification_id1 FOREIGN KEY(Specification_id) REFERENCES Product_Specification(ID) ON DELETE CASCADE
);

CREATE TABLE Service(
       Name varchar2(50),
       ID number(8),
       Specification_id  number(8), 
       CONSTRAINT pk_Service PRIMARY KEY(ID),
       CONSTRAINT fk_Specification_id2 FOREIGN KEY(Specification_id) REFERENCES Product_Specification(ID) ON DELETE CASCADE
       
);

CREATE TABLE customer(
	CustomerId number(8),
       Name varchar2(50),
       Location varchar2(50),
       CONSTRAINT pk_customer PRIMARY KEY(CustomerId)
       
);

CREATE TABLE Product_Attributes(
       ID number(8),
       Name varchar2(50),
       Type number(8),
       CONSTRAINT pk_Product_Attributes PRIMARY KEY(ID),
       CONSTRAINT fk_Product_Type FOREIGN KEY(Type) REFERENCES Product_Offering(ID) ON DELETE CASCADE
       
);

CREATE TABLE Service_Attributes(
       ID number(8),
       Name varchar2(50),
       Type number(8),
       CONSTRAINT pk_Service_Attributes PRIMARY KEY(ID),
       CONSTRAINT fk_Service_Type FOREIGN KEY(Type) REFERENCES Service(ID) ON DELETE CASCADE
       
);

CREATE TABLE Product_Attribute_Values(
       ID number(8),
       Value varchar2(50),
       CONSTRAINT pk_Attribute_value_product PRIMARY KEY(ID,Value),
       CONSTRAINT fk_ID1 FOREIGN KEY(ID) REFERENCES Product_Attributes(ID) ON DELETE CASCADE
       
);

CREATE TABLE Service_Attribute_Values(
       ID number(8),
       Value varchar2(50),
       CONSTRAINT pk_Attribute_value_service PRIMARY KEY(ID,Value),
       CONSTRAINT fk_ID2 FOREIGN KEY(ID) REFERENCES Service_Attributes(ID) ON DELETE CASCADE
       
);

CREATE TABLE users(
       ID number(2),
       name varchar2(50),
       password varchar2(20),
       CONSTRAINT pk_Attribute_users PRIMARY KEY(ID)
       
);

CREATE TABLE Exchange(
       ID number(5),
       name varchar2(20),
       maxSpeed number(20),
       maxDistance number(20),
       CONSTRAINT pk_Attribute_exchange PRIMARY KEY(ID)
       
);


insert into Product_Specification values (1,'IP ADSL PS');
insert into Product_Specification values (2,'IP ADSLType 2 PS');
insert into Product_Specification values (3,'IP BDSL PS');
insert into Product_Specification values (4,'Next Generation Ethernet Lite PS');
commit;

insert into  Product_Offering values(1,'Integrated IP ADSL PO',1);
insert into  Product_Offering values(2,'Integrated IP ADSL Type 2 PO',2);
insert into  Product_Offering values(3,'Integrated IP BDSL PO',3);
insert into  Product_Offering values(4,'Next Generation Ethernet Lite Bundle PO',4);
commit;


insert into Service values('ADSL Service' ,1,1);
insert into Service values('ADSL Type 2 Service' ,2,2);
insert into Service values('Ethernet Service' ,3,3);
insert into Service values('Ethernet Service' ,4,4);
commit;


insert into Product_Attributes values(1,'Contract Term',1);
insert into Product_Attributes values(2,'Customer Required Speed',1);
insert into Product_Attributes values(3,'Contract Term',2);
insert into Product_Attributes values(4,'Customer Required Speed',2);
insert into Product_Attributes values(5,'Contract Term',3);
insert into Product_Attributes values(6,'Customer Required Speed',3);
insert into Product_Attributes values(7,'Contract Term',4);
insert into Product_Attributes values(8,'Customer Required Speed',4);
commit;

insert into Service_Attributes values(1,'Service Level',1); 
insert into Service_Attributes values(2,'IP Variant',1); 
insert into Service_Attributes values(3,'Restricted Routing Policy',1);
insert into Service_Attributes values(4,'Restricted Routing Policy v6',1);
insert into Service_Attributes values(5,'Port Speed',1);
insert into Service_Attributes values(6,'Access Speed',1);
insert into Service_Attributes values(7,'Max Allowed Cable Distance',1);
insert into Service_Attributes values(8,'Service Level',2); 
insert into Service_Attributes values(9,'IP Variant',2); 
insert into Service_Attributes values(10,'Redundant Link',2);
insert into Service_Attributes values(11,'Restricted Routing Policy',2);
insert into Service_Attributes values(12,'Restricted Routing Policy v6',2);
insert into Service_Attributes values(13,'Port Speed(CIR)',2);
insert into Service_Attributes values(14,'Access Speed',2);
insert into Service_Attributes values(15,'Max Allowed Cable Distance',2);
insert into Service_Attributes values(16,'Service Level',3); 
insert into Service_Attributes values(17,'IP Variant',3); 
insert into Service_Attributes values(18,'Restricted Routing Policy',3);
insert into Service_Attributes values(19,'Restricted Routing Policy v6',3);
insert into Service_Attributes values(20,'Routing Protocol',3);
insert into Service_Attributes values(21,'Routing Protocol v6',3);
insert into Service_Attributes values(22,'NTU Mode',3);
insert into Service_Attributes values(23,'Port Speed',3);
insert into Service_Attributes values(24,'Access Speed',3);
insert into Service_Attributes values(25,'Max Allowed Cable Distance',3);
insert into Service_Attributes values(26,'Service Level',4); 
insert into Service_Attributes values(27,'IP Variant',4); 
insert into Service_Attributes values(28,'Restricted Routing Policy',4);
insert into Service_Attributes values(29,'Restricted Routing Policy v6',4);
insert into Service_Attributes values(30,'Routing Protocol',4);
insert into Service_Attributes values(31,'Routing Protocol v6',4);
insert into Service_Attributes values(32,'NTU Mode',4);
insert into Service_Attributes values(33,'Port Speed',4);
insert into Service_Attributes values(34,'Access Speed',4);
insert into Service_Attributes values(35,'Max Allowed Cable Distance',4);
commit;


insert into Product_Attribute_Values values(1,'12');
insert into Product_Attribute_Values values(1,'24');
insert into Product_Attribute_Values values(1,'36');
insert into Product_Attribute_Values values(1,'60');
insert into Product_Attribute_Values values(2,'256');
insert into Product_Attribute_Values values(2,'512');
insert into Product_Attribute_Values values(2,'1024');
insert into Product_Attribute_Values values(2,'2048');
insert into Product_Attribute_Values values(2,'4096');
insert into Product_Attribute_Values values(2,'8192');
insert into Product_Attribute_Values values(2,'10240');
insert into Product_Attribute_Values values(3,'12');
insert into Product_Attribute_Values values(3,'24');
insert into Product_Attribute_Values values(3,'36');
insert into Product_Attribute_Values values(3,'60');
insert into Product_Attribute_Values values(4,'256');
insert into Product_Attribute_Values values(4,'512');
insert into Product_Attribute_Values values(4,'1024');
insert into Product_Attribute_Values values(4,'2048');
insert into Product_Attribute_Values values(4,'4096');
insert into Product_Attribute_Values values(4,'8192');
insert into Product_Attribute_Values values(4,'10240');
insert into Product_Attribute_Values values(5,'12');
insert into Product_Attribute_Values values(5,'24');
insert into Product_Attribute_Values values(5,'36');
insert into Product_Attribute_Values values(6,'256');
insert into Product_Attribute_Values values(6,'512');
insert into Product_Attribute_Values values(6,'1024');
insert into Product_Attribute_Values values(6,'2048');
insert into Product_Attribute_Values values(6,'4096');
insert into Product_Attribute_Values values(7,'12');
insert into Product_Attribute_Values values(7,'24');
insert into Product_Attribute_Values values(7,'36');
insert into Product_Attribute_Values values(7,'60');
insert into Product_Attribute_Values values(8,'256');
insert into Product_Attribute_Values values(8,'512');
insert into Product_Attribute_Values values(8,'1024');
insert into Product_Attribute_Values values(8,'2048');
insert into Product_Attribute_Values values(8,'4096');
insert into Product_Attribute_Values values(8,'8192');
insert into Product_Attribute_Values values(8,'10240');
commit;



insert into Service_Attribute_Values values(1,'Standard');
insert into Service_Attribute_Values values(1,'Standard Plus');
insert into Service_Attribute_Values values(1,'Urban Plus');
insert into Service_Attribute_Values values(1,'Urban Extra');
insert into Service_Attribute_Values values(2,'IPv4'); 
insert into Service_Attribute_Values values(2,'IPv6');
insert into Service_Attribute_Values values(2,'Dual Stack');
insert into Service_Attribute_Values values(3,'Yes'); 
insert into Service_Attribute_Values values(3,'No'); 
insert into Service_Attribute_Values values(4,'Yes'); 
insert into Service_Attribute_Values values(4,'No'); 
insert into Service_Attribute_Values values(5,'256 kbps');  
insert into Service_Attribute_Values values(5,'512 kbps');
insert into Service_Attribute_Values values(5,'1024 kbps');
insert into Service_Attribute_Values values(5,'2048 kbps');
insert into Service_Attribute_Values values(5,'4096 kbps');
insert into Service_Attribute_Values values(6,'256 kbps');  
insert into Service_Attribute_Values values(6,'512 kbps');
insert into Service_Attribute_Values values(6,'1024 kbps');
insert into Service_Attribute_Values values(6,'2048 kbps');
insert into Service_Attribute_Values values(6,'4096 kbps');
insert into Service_Attribute_Values values(7,'3000m');
insert into Service_Attribute_Values values(7,'2500m');
insert into Service_Attribute_Values values(7,'2000m');
insert into Service_Attribute_Values values(7,'1500m');
insert into Service_Attribute_Values values(7,'1000m');
insert into Service_Attribute_Values values(7,'750m');
insert into Service_Attribute_Values values(7,'500m');
insert into Service_Attribute_Values values(8,'Standard');
insert into Service_Attribute_Values values(8,'Standard Plus');
insert into Service_Attribute_Values values(8,'Urban Plus');
insert into Service_Attribute_Values values(8,'Urban Extra');
insert into Service_Attribute_Values values(9,'IPv4'); 
insert into Service_Attribute_Values values(9,'IPv6');
insert into Service_Attribute_Values values(9,'Dual Stack');
insert into Service_Attribute_Values values(10,'Yes'); 
insert into Service_Attribute_Values values(10,'No'); 
insert into Service_Attribute_Values values(11,'Yes'); 
insert into Service_Attribute_Values values(11,'No'); 
insert into Service_Attribute_Values values(12,'Yes'); 
insert into Service_Attribute_Values values(12,'No');
insert into Service_Attribute_Values values(13,'256 kbps');  
insert into Service_Attribute_Values values(13,'512 kbps');
insert into Service_Attribute_Values values(13,'1024 kbps');
insert into Service_Attribute_Values values(13,'2048 kbps');
insert into Service_Attribute_Values values(13,'4096 kbps');
insert into Service_Attribute_Values values(14,'256 kbps');  
insert into Service_Attribute_Values values(14,'512 kbps');
insert into Service_Attribute_Values values(14,'1024 kbps');
insert into Service_Attribute_Values values(14,'2048 kbps');
insert into Service_Attribute_Values values(14,'4096 kbps');
insert into Service_Attribute_Values values(15,'3000m');
insert into Service_Attribute_Values values(15,'2500m');
insert into Service_Attribute_Values values(15,'2000m');
insert into Service_Attribute_Values values(15,'1500m');
insert into Service_Attribute_Values values(15,'1000m');
insert into Service_Attribute_Values values(15,'750m');
insert into Service_Attribute_Values values(15,'500m'); 
insert into Service_Attribute_Values values(16,'Standard');
insert into Service_Attribute_Values values(16,'Standard Plus');
insert into Service_Attribute_Values values(16,'Urban Plus');
insert into Service_Attribute_Values values(16,'Urban Extra');
insert into Service_Attribute_Values values(17,'IPv4'); 
insert into Service_Attribute_Values values(17,'IPv6');
insert into Service_Attribute_Values values(17,'Dual Stack');
insert into Service_Attribute_Values values(18,'Yes'); 
insert into Service_Attribute_Values values(18,'No'); 
insert into Service_Attribute_Values values(19,'Yes'); 
insert into Service_Attribute_Values values(19,'No'); 
insert into Service_Attribute_Values values(20,'RIPv2');
insert into Service_Attribute_Values values(20,'RIPv1');
insert into Service_Attribute_Values values(20,'BGP');
insert into Service_Attribute_Values values(20,'OSPF');
insert into Service_Attribute_Values values(20,'Static');  
insert into Service_Attribute_Values values(20,'None');
insert into Service_Attribute_Values values(21,'RIPng');
insert into Service_Attribute_Values values(21,'BGP');
insert into Service_Attribute_Values values(21,'OSPFv3');
insert into Service_Attribute_Values values(21,'Static');
insert into Service_Attribute_Values values(22,'EFM');
insert into Service_Attribute_Values values(22,'BRE');
insert into Service_Attribute_Values values(22,'ATM');
insert into Service_Attribute_Values values(23,'256 kbps');  
insert into Service_Attribute_Values values(23,'512 kbps');
insert into Service_Attribute_Values values(23,'1024 kbps');
insert into Service_Attribute_Values values(23,'2048 kbps');
insert into Service_Attribute_Values values(23,'4096 kbps');
insert into Service_Attribute_Values values(24,'256 kbps');  
insert into Service_Attribute_Values values(24,'512 kbps');
insert into Service_Attribute_Values values(24,'1024 kbps');
insert into Service_Attribute_Values values(24,'2048 kbps');
insert into Service_Attribute_Values values(24,'4096 kbps');
insert into Service_Attribute_Values values(25,'8000m');
insert into Service_Attribute_Values values(25,'7500m');
insert into Service_Attribute_Values values(25,'7000m');
insert into Service_Attribute_Values values(25,'6500m');
insert into Service_Attribute_Values values(25,'6000m');
insert into Service_Attribute_Values values(25,'5500m');
insert into Service_Attribute_Values values(25,'5000m');
insert into Service_Attribute_Values values(26,'Standard');
insert into Service_Attribute_Values values(26,'Standard Plus');
insert into Service_Attribute_Values values(26,'Urban Plus');
insert into Service_Attribute_Values values(26,'Urban Extra');
insert into Service_Attribute_Values values(27,'IPv4'); 
insert into Service_Attribute_Values values(27,'IPv6');
insert into Service_Attribute_Values values(27,'Dual Stack');
insert into Service_Attribute_Values values(28,'Yes'); 
insert into Service_Attribute_Values values(28,'No'); 
insert into Service_Attribute_Values values(29,'Yes'); 
insert into Service_Attribute_Values values(29,'No'); 
insert into Service_Attribute_Values values(30,'RIPv2');
insert into Service_Attribute_Values values(30,'RIPv1');
insert into Service_Attribute_Values values(30,'BGP');
insert into Service_Attribute_Values values(30,'OSPF');
insert into Service_Attribute_Values values(30,'Static');  
insert into Service_Attribute_Values values(30,'None');
insert into Service_Attribute_Values values(31,'RIPng');
insert into Service_Attribute_Values values(31,'BGP');
insert into Service_Attribute_Values values(31,'OSPFv3');
insert into Service_Attribute_Values values(31,'Static');
insert into Service_Attribute_Values values(32,'EFM');
insert into Service_Attribute_Values values(32,'BRE');
insert into Service_Attribute_Values values(32,'ATM'); 
insert into Service_Attribute_Values values(33,'256 kbps');  
insert into Service_Attribute_Values values(33,'512 kbps');
insert into Service_Attribute_Values values(33,'1024 kbps');
insert into Service_Attribute_Values values(33,'2048 kbps');
insert into Service_Attribute_Values values(33,'4096 kbps');
insert into Service_Attribute_Values values(33,'8192 kbps');
insert into Service_Attribute_Values values(33,'10240 kbps');
insert into Service_Attribute_Values values(34,'256 kbps');  
insert into Service_Attribute_Values values(34,'512 kbps');
insert into Service_Attribute_Values values(34,'1024 kbps');
insert into Service_Attribute_Values values(34,'2048 kbps');
insert into Service_Attribute_Values values(34,'4096 kbps');
insert into Service_Attribute_Values values(34,'8192 kbps');
insert into Service_Attribute_Values values(34,'10240 kbps');
insert into Service_Attribute_Values values(35,'8000m');
insert into Service_Attribute_Values values(35,'7500m');
insert into Service_Attribute_Values values(35,'7000m');
insert into Service_Attribute_Values values(35,'6500m');
insert into Service_Attribute_Values values(35,'6000m');
insert into Service_Attribute_Values values(35,'5500m');
insert into Service_Attribute_Values values(35,'5000m');
commit;

insert into customer values (1,'Saddam Khan', 'Hyderabad');
insert into customer values (2,'Raj Kumar' ,'Bhubaneshwar');
commit;

insert into users values (1,'Rahul','password');
insert into users values (2,'Supriya','password');
insert into users values (3,'Disha','password');
insert into users values (4,'Abhisek','password');
insert into users values (5,'Bindu','password');
insert into users values (6,'Aakash','password');
insert into users values (7,'Asiq','password');
insert into users values (8,'Divya','password');
commit;

insert into exchange values (1,'Mysore',3000,3000);
insert into exchange values (2,'Bangalore',4096,7000);
insert into exchange values (3,'Mangalore',1024,5000);
commit;

select * from users;