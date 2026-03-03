drop database if exists m4_casestudy_badminton;
create database m4_casestudy_badminton;
use m4_casestudy_badminton;
create table role(
id int primary key auto_increment,
name varchar(50) not null unique
);
create table account(
id bigint primary key auto_increment,
username varchar(255) not null unique,
password varchar(255) not null,
email varchar(255) not null unique,
phone varchar(255) not null unique,
role_id int not null,
foreign key (role_id) references role(id)
);
create table users(
id bigint primary key auto_increment,
account_id bigint not null unique,
foreign key (account_id) references account(id) on delete cascade,
name varchar(255) not null
);
create table fields(
id int primary key auto_increment,
name varchar(50) not null unique,
price double,
status enum('AVAILABLE','NOT_AVAILABLE')
);
create table shift(
id int primary key auto_increment,
start_time time,
end_time time,
unique(start_time,end_time)
);
create table field_book(
id bigint primary key auto_increment,
user_id bigint not null,
field_id int not null,
shift_id int not null,
foreign key (user_id) references users(id),
foreign key (field_id) references fields(id),
foreign key (shift_id) references shift(id),
status enum('PENDING','APPROVED','CANCELED') default'PENDING',
date_book date not null,
unique (field_id,shift_id,date_book)
);
create table teacher(
id int primary key auto_increment,
name varchar(255) not null,
email varchar(255) not null unique,
phone varchar(255) not null,
account_id bigint not null unique,
foreign key (account_id) references account(id) on delete cascade
);
create table study_schedule(
id int primary key auto_increment,
teacher_id int not null,
field_id int not null,
shift_id int not null,
foreign key (teacher_id) references teacher(id),
foreign key (field_id) references fields(id),
foreign key (shift_id) references shift(id),
class_name varchar(255),
price double,
min_students int not null default 5,
max_students int not null default 10,
status_class enum('NOT_OPEN','OPEN','FULL') not null default 'NOT_OPEN',
unique(field_id,shift_id)
);
create table class_register(
id bigint primary key auto_increment,
user_id bigint not null,
schedule_id int not null,
foreign key (user_id) references users(id),
foreign key (schedule_id) references study_schedule(id) on delete cascade,
date_register datetime,
status_register enum('PENDING','APPROVED','CANCELED') not null default 'PENDING',
unique(user_id,schedule_id)
);
create table teacher_review(
id bigint primary key auto_increment,
class_register_id bigint not null unique,
foreign key (class_register_id) references class_register(id),
scores double,
review varchar(255)
);
create table student_notice(
id bigint primary key auto_increment,
class_register_id bigint not null,
foreign key (class_register_id) references class_register(id),
reason varchar(255)
);
create table teacher_notice(
id bigint primary key auto_increment,
class_register_id bigint not null,
foreign key (class_register_id) references class_register(id),
reason varchar(255)
);
create table pay(
id int primary key auto_increment,
pay_type varchar(20) not null unique
);
create table payment_register(
id bigint primary key auto_increment,
pay_id int not null,
register_id bigint not null unique,
foreign key (pay_id) references pay(id),
foreign key (register_id) references class_register(id),
status enum('UNPAID','PAID')
);
create table payment_field_book(
id bigint primary key auto_increment,
pay_id int not null,
field_book_id bigint not null unique,
foreign key (pay_id) references pay(id),
foreign key (field_book_id) references field_book(id),
status enum('UNPAID','PAID')
);

-- ROLE (3 record cố định)
insert into role(name) values
('ADMIN'),
('USER'),
('TEACHER');

-- ACCOUNT (4 admin + 3 teacher + 15 user = 22)
insert into account(username,password,email,phone,role_id) values
-- Admin
('admin_nam','namdb','admin_nam@gmail.com','0905000001',1),
('admin_huy','huyg','admin_huy@gmail.com','0905000002',1),
('admin_hang','hangle','admin_hang@gmail.com','0905000003',1),
('admin_hung','hungdinh','admin_hung@gmail.com','0905000004',1),

-- Teacher
('coach_phuc','123','phuccoach@gmail.com','0912000001',3),
('coach_khoa','123','khoacoach@gmail.com','0912000002',3),
('coach_minh','123','minhcoach@gmail.com','0912000003',3),

-- Users (15)
('annguyen','123','annguyen@gmail.com','0905123001',2),
('binhtt','123','binh_tran@gmail.com','0905123002',2),
('hoangbach','123','hoangbach@gmail.com','0905123003',2),
('lequyen','123','lequyen@gmail.com','0905123004',2),
('ducmv','123','ducvo@gmail.com','0905123005',2),
('thanhmv','123','maivanthanh@gmail.com','0905123006',2),
('hoanglong','123','gianghoang@gmail.com','0905123007',2),
('buiduc','123','ducbui@gmail.com','0905123008',2),
('khoinguyen','123','khoinguyen@gmail.com','0905123009',2),
('phamquang','123','loc.pham@gmail.com','0905123010',2),
('tranmai','123','mai.tran@gmail.com','0905123011',2),
('lengan','123','ngan.le@gmail.com','0905123012',2),
('nhatminh','123','nhat.doan@gmail.com','0905123013',2),
('vuquocph','123','phong.vu@gmail.com','0905123014',2),
('hoangthaisn','123','son.hoang@gmail.com','0905123015',2);

-- USERS (15 records, account_id từ 8 → 22)
insert into users(account_id,name) values
(8,'Nguyễn Văn An'),
(9,'Trần Thị Bình'),
(10,'Lê Hoàng bách'),
(11,'Phạm Lệ Quyên'),
(12,'Mai Võ Đức'),
(13,'Mai Văn Thành'),
(14,'Hoàng Long Giang'),
(15,'Bùi Đức'),
(16,'Nguyễn Minh Khôi'),
(17,'Phạm Quang Lộc'),
(18,'Trần Kim Mai'),
(19,'Lê Thùy Ngân'),
(20,'Đoàn Minh Nhật'),
(21,'Vũ Quốc Phong'),
(22,'Hoàng Thái Sơn');

-- TEACHER (account_id 5,6,7)
insert into teacher(name,email,phone,account_id) values
('Nguyễn Hữu Phúc','phuc.coach@gmail.com','0912000001',5),
('Trần Minh Khoa','khoa.coach@gmail.com','0912000002',6),
('Lê Quốc Minh','minh.coach@gmail.com','0912000003',7);

-- FIELDS (6 sân)
insert into fields(name,price,status) values
('Sân A',200000,'AVAILABLE'),
('Sân B',200000,'AVAILABLE'),
('Sân C',200000,'AVAILABLE'),
('Sân D',200000,'AVAILABLE'),
('Sân E',200000,'AVAILABLE'),
('Sân F',200000,'NOT_AVAILABLE');

-- SHIFT (9 ca, mỗi ca 2 tiếng)
insert into shift(start_time,end_time) values
('05:00','07:00'),
('07:00','09:00'),
('09:00','11:00'),
('11:00','13:00'),
('13:00','15:00'),
('15:00','17:00'),
('17:00','19:00'),
('19:00','21:00'),
('21:00','23:00');

-- STUDY_SCHEDULE (2 lớp cố định)
insert into study_schedule(teacher_id,field_id,shift_id,class_name,price,min_students,max_students,status_class) values
(1,1,7,'Lớp 2-4-6',1000000,5,10,'OPEN'),
(2,2,7,'Lớp 3-5-7',2000000,5,10,'NOT_OPEN');

insert into class_register(user_id, schedule_id, date_register, status_register) values
-- Lớp 2-4-6 (shift_id = 7, field_id = 1, 5 học viên → lớp mở)
(1, 1, '2026-02-28 10:00:00', 'APPROVED'),
(2, 1, '2026-02-28 10:30:00', 'APPROVED'),
(3, 1, '2026-02-28 11:00:00', 'APPROVED'),
(4, 1, '2026-02-28 11:30:00', 'APPROVED'),
(5, 1, '2026-02-28 12:00:00', 'APPROVED'),

-- Lớp 3-5-7 (shift_id = 7, field_id = 2, 4 học viên → lớp chưa mở)
(6, 2, '2026-02-28 10:15:00', 'PENDING'),
(7, 2, '2026-02-28 10:45:00', 'PENDING'),
(8, 2, '2026-02-28 11:15:00', 'PENDING'),
(9, 2, '2026-02-28 11:45:00', 'PENDING'),

-- Thêm 2 học viên đăng ký thêm cho lớp 2-4-6
(10, 1, '2026-03-01 09:00:00', 'PENDING'),
(11, 1, '2026-03-01 09:30:00', 'PENDING'),

-- Thêm 4 học viên còn lại đăng ký lớp 3-5-7
(12, 2, '2026-03-01 10:00:00', 'PENDING'),
(13, 2, '2026-03-01 10:30:00', 'PENDING'),
(14, 2, '2026-03-02 09:00:00', 'PENDING'),
(15, 2, '2026-03-02 09:30:00', 'PENDING');

-- FIELD_BOOK (khách đặt sân, thứ tự id = user_id)
insert into field_book(user_id,field_id,shift_id,date_book,status) values
(1,1,1,'2026-03-03','APPROVED'),
(2,2,2,'2026-03-04','APPROVED'),
(3,3,3,'2026-03-05','APPROVED'),
(4,4,4,'2026-03-06','APPROVED'),
(5,5,5,'2026-03-07','APPROVED'),
(6,1,6,'2026-03-08','APPROVED'),
(7,2,7,'2026-03-09','APPROVED'),
(8,3,8,'2026-03-10','APPROVED'),
(9,4,9,'2026-03-11','PENDING'),
(10,5,1,'2026-03-12','APPROVED'),
(11,1,2,'2026-03-13','APPROVED'),
(12,2,3,'2026-03-14','APPROVED'),
(13,3,4,'2026-03-15','APPROVED'),
(14,4,5,'2026-03-16','APPROVED'),
(15,5,6,'2026-03-17','CANCELED');

-- PAY
insert into pay(pay_type) values
('CASH'),
('BANKING');

-- PAYMENT (ban đầu tất cả UNPAID)
insert into payment_register(pay_id,register_id,status) values
(1,1,'UNPAID'),
(2,2,'UNPAID'),
(1,3,'UNPAID'),
(2,4,'UNPAID'),
(1,5,'UNPAID'),
(2,6,'UNPAID'),
(1,7,'UNPAID'),
(2,8,'UNPAID'),
(1,9,'UNPAID'),
(2,10,'UNPAID'),
(1,11,'UNPAID'),
(2,12,'UNPAID'),
(1,13,'UNPAID'),
(2,14,'UNPAID'),
(1,15,'UNPAID');

insert into payment_field_book(pay_id,field_book_id,status) values
(1,1,'UNPAID'),
(2,2,'UNPAID'),
(1,3,'UNPAID'),
(2,4,'UNPAID'),
(1,5,'UNPAID'),
(2,6,'UNPAID'),
(1,7,'UNPAID'),
(2,8,'UNPAID'),
(2,10,'UNPAID'),
(1,11,'UNPAID'),
(2,12,'UNPAID'),
(1,13,'UNPAID'),
(2,14,'UNPAID');