select *
from faculties;
select *
from student;
select *
from faculties
where name LIKE '%7%';
select *
from faculties
where color LIKE '%red%';
select *
from student,
     faculties
where faculties.color = student.name;
