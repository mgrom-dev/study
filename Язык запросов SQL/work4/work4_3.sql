use skillbox;
SELECT ROW_NUMBER() OVER(ORDER BY name) no, name, type, duration FROM Courses WHERE type = 'PROGRAMMING';
SELECT SUM(duration) as duration_all_courses_programming FROM Courses WHERE type = 'PROGRAMMING';