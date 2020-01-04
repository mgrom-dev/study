use skillbox;
SELECT ROW_NUMBER() OVER(ORDER BY name) no, name, type, price FROM Courses WHERE type = 'DESIGN';
SELECT SUM(price) as price_off_all_courses_design FROM Courses WHERE type = 'DESIGN';