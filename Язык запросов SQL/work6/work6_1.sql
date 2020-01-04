use skillbox;
SELECT Students.name as name, SUM(duration) as count_hours FROM Courses
JOIN Subscriptions ON Courses.id = Subscriptions.course_id
JOIN Students ON Students.id = Subscriptions.student_id
GROUP BY Students.id ORDER BY count_hours DESC;