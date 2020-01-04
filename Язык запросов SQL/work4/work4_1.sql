use skillbox;
SELECT ROW_NUMBER() OVER(ORDER BY name) no, name, age, salary FROM Teachers WHERE salary > 10000;
SELECT AVG(age) as average_age_teachers_with_salary_over_10000 FROM Teachers WHERE salary > 10000;