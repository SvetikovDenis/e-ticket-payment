DROP DATABASE IF EXISTS ticket_payment_db;
CREATE DATABASE ticket_payment_db;
USE ticket_payment_db;

INSERT INTO payment_status VALUES
(default,"BEGIN_PROCESSING"),
(default,"IN_PROCESS"),
(default,"ERROR"),
(default,"SUCCESSFUL");


insert into ticket_payment_order values
(default ,"ORDER12345","ROUTE12345","1234TEST1234","2020-10-31","08d6d9d9-1fdd-4353-88f6-1c0dce40t9f9",1,default,default),
(default ,"ORDER123456","ROUTE123456","CLIENT123457","2020-10-12","08d6d9d9-1f33-4353-88f6-1c0dce40t9f9",1,default,default),
(default ,"ORDER123457","ROUTE123457","CLIENT123458","2020-11-05","08d6d9d9-1f20-4353-88f6-1c0dce40t9f9",1,default,default);

