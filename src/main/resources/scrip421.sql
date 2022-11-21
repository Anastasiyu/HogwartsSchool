ALTER TABLE student
    ADD CHECK ( age >= 16 );
ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
ALTER TABLE student
    ADD CONSTRAINT unique_name UNIQUE (name) ,
ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT unique_name_and_color UNIQUE (name, color);