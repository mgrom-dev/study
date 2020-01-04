use skillbox;
SELECT * from (SELECT name, age as "age/duration" FROM Students ORDER BY age LIMIT 3) as students
UNION ALL
SELECT * from (SELECT name, age as "age/duration" FROM Teachers ORDER BY age DESC LIMIT 3) as teachers
UNION ALL
SELECT * from (SELECT name, duration as "age/duration" FROM Courses ORDER BY duration DESC LIMIT 3) as courses;