use skillbox;
SELECT name, students_count, duration, price FROM Courses WHERE duration > 10 AND students_count > 4 ORDER BY price DESC LIMIT 5;