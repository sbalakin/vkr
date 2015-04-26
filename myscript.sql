/*
Created		3/31/2015
Modified		4/27/2015
Project		
Model		
Company		
Author		
Version		
Database		mySQL 5 
*/



SET SESSION CHARACTER_SET_RESULTS =utf8;

Create table animal (
	pk_type_animal Int UNSIGNED NOT NULL,
	name Char(50),
	gender Smallint,
	color Char(20),
	health_status Text,
	type Char(50),
	weight Float,
	breed Char(50),
	relationship_with_human Text,
	age Int,
	description Text,
	sterilized Int,
	pk_animal Int UNSIGNED NOT NULL AUTO_INCREMENT,
	is_were_owner Smallint,
	Index AI_pk_animal (pk_animal),
 Primary Key (pk_animal)) ENGINE = MyISAM;

Create table shelter (
	name Char(50),
	telephone Int,
	address Text,
	seat Int,
	free_seat Int,
	site Text,
	email Text,
	description Text,
	pk_shelter Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_shelter (pk_shelter),
 Primary Key (pk_shelter)) ENGINE = MyISAM;

Create table sponsor (
	name Char(50),
	address Text,
	telephone Int,
	site Text,
	email Text,
	description Text,
	pk_sponsor Int UNSIGNED NOT NULL AUTO_INCREMENT,
	is_organization Smallint,
	Index AI_pk_sponsor (pk_sponsor),
 Primary Key (pk_sponsor)) ENGINE = MyISAM;

Create table profit (
	amount Int,
	date_receive Char(20),
	description Text,
	pk_profit Int UNSIGNED NOT NULL AUTO_INCREMENT,
	pk_sponsor Int UNSIGNED NOT NULL,
	pk_support_type Int UNSIGNED NOT NULL,
	Index AI_pk_profit (pk_profit),
 Primary Key (pk_profit)) ENGINE = MyISAM;

Create table staff (
	name Char(50),
	career Char(50),
	telephone Int,
	date_of_birth Date,
	address Text,
	description Text,
	pk_staff Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_staff (pk_staff),
 Primary Key (pk_staff)) ENGINE = MyISAM;

Create table volunteer (
	name Char(50),
	career Char(50),
	telephone Int,
	date_of_birth Char(20),
	address Text,
	description Text,
	pk_volunteer Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_volunteer (pk_volunteer),
 Primary Key (pk_volunteer)) ENGINE = MyISAM;

Create table support_type (
	title Char(50),
	pk_support_type Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_support_type (pk_support_type),
 Primary Key (pk_support_type)) ENGINE = MyISAM;

Create table expense (
	product Char(50),
	price Int,
	organization Char(50),
	date_use Char(20),
	description Text,
	pk_expense Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_expense (pk_expense),
 Primary Key (pk_expense)) ENGINE = MyISAM;

Create table owner (
	name Char(50),
	telephone Int,
	address Text,
	amount_of_animal Int,
	pk_owner Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_owner (pk_owner),
 Primary Key (pk_owner)) ENGINE = MyISAM;

Create table translation (
	pk_translation Int UNSIGNED NOT NULL AUTO_INCREMENT,
	pk_owner_temp_first Int,
	pk_owner_first Int,
	pk_shelter_first Int,
	in_date Date,
	out_date Date,
	pk_animal Int UNSIGNED NOT NULL,
	pk_temp_owner Int UNSIGNED NOT NULL,
	pk_shelter Int UNSIGNED NOT NULL,
	pk_owner Int UNSIGNED NOT NULL,
	Index AI_pk_translation (pk_translation),
 Primary Key (pk_translation)) ENGINE = MyISAM;

Create table temporary_owner (
	name Char(50),
	telephone Int,
	address Text,
	amount_of_animal Int,
	pk_temp_owner Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_temp_owner (pk_temp_owner),
 Primary Key (pk_temp_owner)) ENGINE = MyISAM;

Create table type_animal (
	title Char(50),
	pk_type_animal Int UNSIGNED NOT NULL AUTO_INCREMENT,
	Index AI_pk_type_animal (pk_type_animal),
 Primary Key (pk_type_animal)) ENGINE = MyISAM;












Alter table translation add Foreign Key (pk_animal) references animal (pk_animal) on delete  restrict on update  restrict;
Alter table translation add Foreign Key (pk_shelter) references shelter (pk_shelter) on delete  restrict on update  restrict;
Alter table profit add Foreign Key (pk_sponsor) references sponsor (pk_sponsor) on delete  restrict on update  restrict;
Alter table profit add Foreign Key (pk_support_type) references support_type (pk_support_type) on delete  restrict on update  restrict;
Alter table translation add Foreign Key (pk_owner) references owner (pk_owner) on delete  restrict on update  restrict;
Alter table translation add Foreign Key (pk_temp_owner) references temporary_owner (pk_temp_owner) on delete  restrict on update  restrict;
Alter table animal add Foreign Key (pk_type_animal) references type_animal (pk_type_animal) on delete  restrict on update  restrict;















/* Users permissions */






