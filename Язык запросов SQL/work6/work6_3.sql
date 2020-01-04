use skillbox;
SELECT Courses.type as type_course, AVG(Teachers.salary) as average_salary FROM Teachers
JOIN Courses ON Teachers.id = Courses.teacher_id
GROUP BY Courses.type ORDER BY average_salary DESC;