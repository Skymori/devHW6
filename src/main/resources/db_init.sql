CREATE TABLE customers
(
    customer_id   SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    industry      VARCHAR(100)
);

CREATE TABLE companies
(
    company_id   SERIAL PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    headquarters VARCHAR(100)
);

CREATE TABLE projects
(
    project_id          SERIAL PRIMARY KEY,
    project_name        VARCHAR(100) NOT NULL,
    project_description VARCHAR(100)
);

CREATE TABLE customers_companies
(
    customer_id int NOT NULL,
    company_id  int NOT NULL,
    project_id  int NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES companies (company_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (project_id) ON DELETE CASCADE,
    UNIQUE (customer_id, company_id, project_id)
);

CREATE
TYPE sex AS ENUM ('male', 'female');
CREATE TABLE developers
(
    developer_id SERIAL PRIMARY KEY,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    sex sex
);

CREATE TABLE project_developers
(
    project_id   int NOT NULL,
    developer_id int NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (project_id) ON DELETE CASCADE,
    FOREIGN KEY (developer_id) REFERENCES developers (developer_id) ON DELETE CASCADE,
    UNIQUE (project_id, developer_id)
);

CREATE
TYPE level AS ENUM ('Junior', 'Middle', 'Senior');
CREATE
TYPE language AS ENUM ('Java', 'C++', 'C#', 'JS');
CREATE TABLE skills
(skill_id SERIAL PRIMARY KEY,
branch language,
skill_level level);

CREATE TABLE developer_skills
(skill_id int NOT NULL,
developer_id int NOT NULL,
FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE,
FOREIGN KEY (developer_id) REFERENCES developers(developer_id) ON DELETE CASCADE,
UNIQUE (skill_id, developer_id));
