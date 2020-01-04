use skillbox;
SELECT Teachers.name as name, AVG(Students.age) as average_age_students FROM Students
JOIN Subscriptions ON Students.id = Subscriptions.student_id
JOIN Courses ON Subscriptions.course_id = Courses.id
JOIN Teachers ON Courses.teacher_id = Teachers.id
GROUP BY Teachers.id ORDER BY average_age_students;