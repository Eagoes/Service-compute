DROP TABLE IF EXISTS `test`;
create table `test` (`role_id` int, `role` VARCHAR(255), PRIMARY KEY (`role_id`));

create table if not exists `nouser`(`name` varchar(255));