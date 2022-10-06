drop table transation;
CREATE TABLE `transation` (
                    id tinyint not null auto_increment,
                    `from` varchar(500) NOT NULL,
                    `to` varchar(500) NOT NULL,
                    type boolean,
                    amount double,
                    price double,
                    status int
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;