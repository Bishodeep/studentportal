CREATE TABLE accommodation.student_details (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,Student_name VARCHAR(30)
	,Student_age INTEGER
	,Student_address VARCHAR(40)
	,Student_contact VARCHAR(20)
	,User_id INTEGER
	);

CREATE TABLE accommodation.user_info (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,user_name VARCHAR(30)
	,password VARCHAR(225)
	,role_id INTEGER
	);

CREATE TABLE accommodation.ROLE (
	id INTEGER AUTO_INCREMENT PRIMARY KEY
	,role_name VARCHAR(10)
	);

CREATE TABLE accommodation.room_details (
	 id INTEGER AUTO_INCREMENT PRIMARY KEY
	,type_id VARCHAR(30)
	,room_location VARCHAR(40)
	,monthly_charge INTEGER
	,room_available CHAR(1)
    ,room_description VARCHAR(255)
    ,roomtype_name VARCHAR(255)
	);
        
CREATE TABLE accommodation.room_type(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    typename varchar(30),
    available INTEGER
    );
    
    CREATE TABLE accommodation.student_room(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    student_id INTEGER,
    room_id INTEGER,
    payement_status Boolean
    );
    