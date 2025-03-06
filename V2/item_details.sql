CREATE TABLE Item_details( id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
							item_id NUMBER UNIQUE,
							description varchar2(50) ,
							issue_date DATE,
							expire_date DATE,
							CONSTRAINT fk_item FOREIGN key(item_id) REFERENCES item(id) ON DELETE cascade);

