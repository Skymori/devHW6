INSERT INTO customers (customer_name, industry)
VALUES
    ('Amazon', 'Cloud computing'),
    ('Google', 'Internet'),
    ('Apple', 'Consumer electronics'),
    ('Microsoft', 'Software development'),
    ('Samsung', 'Conglomerate'),
    ('ICBC', 'Financial services'),
    ('Facebook', 'Social networking service'),
    ('Walmart', 'Retail'),
    ('Ping An', 'Financial services'),
    ('Huawei', 'Consumer electronics');

INSERT INTO companies (company_name, headquarters)
VALUES
    ('EPAM', 'Newtown'),
    ('SoftServe', 'Lviv'),
    ('Luxoft', 'Zug'),
    ('Ciklum', 'London'),
    ('NIX', 'Kharkiv');

INSERT INTO projects (project_name, project_description)
VALUES
    ('Titan', 'electric car project'),
    ('Project Hanks', 'business keys'),
    ('Kodiak', 'operating system'),
    ('Code Talkers', NULL),
    ('Project Blue Book', 'systematic study of unidentified flying objects'),
    ('X Lab', 'Software and consulting'),
    ('Project 404', NULL),
    ('Manhattan Project', NULL),
    ('Durango', 'gaming console'),
    ('Apollo', NULL),
    ('ProjectManager.com', 'management software'),
    ('Bikers Portal', NULL),
    ('College Enquiry Chat Bot', NULL),
    ('Improved Data Leakage Detection', NULL),
    ('Matrimonial Portal Project', NULL),
    ('Image Mining Project', NULL),
    ('MLM Project', NULL);

INSERT INTO customers_companies (customer_id, company_id, project_id)
VALUES
    (1, 1, 1), (1, 2, 1), (1, 2, 5),
    (2, 2, 2), (2, 2, 8), (2, 4, 12),
    (3, 1, 3), (3, 3, 3), (3, 5, 7),
    (4, 3, 10), (4, 4, 10),
    (5, 1, 11), (5, 4, 11), (5, 4, 13),
    (6, 2, 14), (6, 5, 14),
    (7, 1, 4), (7, 2, 4), (7, 5, 15),
    (8, 3, 9), (8, 4, 16),
    (9, 1, 6), (9, 5, 17),
    (10, 1, 12);

INSERT INTO developers (first_name, last_name, sex)
VALUES
    ('Rudolph', 'Hicks', 'male'),
    ('Tricia', 'Thornton', 'female'),
    ('Dianna', 'Floyd', 'female'),
    ('Delbert', 'Brock', 'male'),
    ('Fredrick', 'Cross', 'male'),
    ('Ernest', 'Jensen', 'male'),
    ('Bruce', 'Ferguson', 'male'),
    ('Stella', 'Joseph', 'female'),
    ('Curtis', 'Bass', 'male'),
    ('Marie', 'Hale', 'female'),
    ('Stephen', 'Lambert', 'male'),
    ('Micheal', 'Waters', 'male'),
    ('Vanessa', 'Alvarado', 'female'),
    ('Miranda', 'Davidson', 'female'),
    ('Alexander', 'Crawford', 'male'),
    ('Jacob', 'Mcbride', 'male'),
    ('Cecil', 'Dixon', 'male'),
    ('Woodrow', 'Carroll', 'male'),
    ('Bobbie', 'Jacobs', 'female'),
    ('Gilberto', 'Lawson', 'male'),
    ('Loretta', 'Bowers', 'female'),
    ('Delores', 'Garner', 'female'),
    ('Kyle', 'Ford', 'female'),
    ('Felix', 'Knight', 'male'),
    ('Stacy', 'Ward', 'female'),
    ('Walter', 'Scott', 'male'),
    ('Gina', 'Haynes', 'female'),
    ('Candace', 'Spencer', 'male'),
    ('Julius', 'Pratt', 'male'),
    ('Kirk', 'Clayton', 'male'),
    ('Laura', 'Goodwin', 'female'),
    ('Chelsea', 'Simpson', 'female'),
    ('Dominic', 'Perez', 'male'),
    ('Miriam', 'Lynch', 'female'),
    ('Frank', 'Grant', 'male'),
    ('Dan', 'Tran', 'male'),
    ('Abel', 'Walton', 'male'),
    ('Damon', 'Wise', 'male'),
    ('Tabitha', 'Hanson', 'male'),
    ('Teri', 'Kelly', 'female'),
    ('Alice', 'Bailey', 'female'),
    ('Edith', 'Santiago', 'female'),
    ('Rochelle', 'Goodman', 'female'),
    ('Gladys', 'Nguyen', 'female'),
    ('Pedro', 'Allen', 'male'),
    ('Mercedes', 'Rhodes', 'female'),
    ('Wilson', 'Taylor', 'male'),
    ('Monique', 'Hayes', 'female'),
    ('Wallace', 'Hammond', 'male'),
    ('Everett', 'Cannon', 'male');

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 1 AND 20
WHERE pr.project_id in (1,3,10);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 21 AND 28
WHERE pr.project_id in (2,6,7,15);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 29 AND 32
WHERE pr.project_id in (4,5,14,17);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 33 AND 45
WHERE pr.project_id in (8,9,11,16);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 46 AND 50
WHERE pr.project_id in (12,13);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 1 AND 7
WHERE pr.project_id in (9);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 40 AND 48
WHERE pr.project_id in (2,3,4,5);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 5 AND 10
WHERE pr.project_id in (17);

INSERT INTO project_developers
SELECT pr.project_id, d.developer_id
FROM projects pr
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 30 AND 35
WHERE pr.project_id in (2,3);

INSERT INTO skills (branch, skill_level)
VALUES
    ('Java', 'Junior'),
    ('Java', 'Middle'),
    ('Java', 'Senior'),
    ('C++', 'Junior'),
    ('C++', 'Middle'),
    ('C++', 'Senior'),
    ('C#', 'Junior'),
    ('C#', 'Middle'),
    ('C#', 'Senior'),
    ('JS', 'Junior'),
    ('JS', 'Middle'),
    ('JS', 'Senior');

INSERT INTO developer_skills
SELECT s.skill_id, d.developer_id
FROM skills s
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 1 AND 7
WHERE s.skill_id in (1,5);

INSERT INTO developer_skills
SELECT s.skill_id, d.developer_id
FROM skills s
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 8 AND 23
WHERE s.skill_id in (2,3,5);

INSERT INTO developer_skills
SELECT s.skill_id, d.developer_id
FROM skills s
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 24 AND 30
WHERE s.skill_id in (3,4,7,12);

INSERT INTO developer_skills
SELECT s.skill_id, d.developer_id
FROM skills s
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 31 AND 35
WHERE s.skill_id in (3,8,11);

INSERT INTO developer_skills
SELECT s.skill_id, d.developer_id
FROM skills s
         LEFT JOIN developers d
                   ON d.developer_id BETWEEN 36 AND 50
WHERE s.skill_id in (6,9,10);

ALTER TABLE developers
    ADD COLUMN salary int;

UPDATE developers
SET salary = 1000
WHERE last_name LIKE '%on';

UPDATE developers
SET salary = 3000
WHERE sex='female';

UPDATE developers
SET salary = 2500
WHERE sex<>'female' AND first_name LIKE 'W%';

UPDATE developers
SET salary = 500
WHERE developer_id BETWEEN 4 AND 6;

UPDATE developers
SET salary = 5000
WHERE salary IS NULL;

ALTER TABLE projects
    ADD COLUMN cost int;

UPDATE projects proj SET cost =
                             (	SELECT SUM (d.salary)  AS total_cost
                                  FROM projects p
                                           INNER JOIN project_developers pd ON p.project_id = pd.project_id
                                           INNER JOIN developers d ON d.developer_id=pd.developer_id
                                  WHERE p.project_id = proj.project_id
                             );

alter table projects
    add column create_date date default current_date;