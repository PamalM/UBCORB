DROP TABLE roombooking;
DROP TABLE room;
DROP TABLE area;


create table area(
area_name varchar(30) identity,
PRIMARY KEY (area_name)
);

create table room(
area_id name,
room_name VARCHAR(30) identity,
PRIMARY KEY (area_name, room_name),
Foreign Key (area_name) references area(area_name)
);

create table roombooking(
booking_date date,
booking_time time,
room_name string,
area_name string,
taken bit,
Primary key (booking_date, booking_time, room_name, area_name)
Foreign Key (area_name) references area(area_name),
Foreign Key (room_name) references room(room_name)
);

