SELECT s.name, s.age, f.name
FROM student s
         LEFT JOIN faculty f on f.id = s.faculty_id;

SELECT s.student_id
FROM avatar s
         INNER JOIN avatar a on a.id = s.student_id;

SELECT s.name, s.age
FROM student s
         INNER JOIN avatar a on a.id = s.avatar_id;




