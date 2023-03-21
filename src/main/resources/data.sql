INSERT INTO candidate(id, name, email)
VALUES (101, 'Adam', 'adam@email.cz'),
       (102, 'Eva', 'eva@email.cz'),
       (103, 'Jablko', 'jablko@email.cz');

INSERT INTO technology(id, technology_name)
VALUES (301, 'Java'),
       (302, 'TypeScript'),
       (303, 'Pascal');

INSERT INTO candidate_technology (candidate_id, technology_id, rating, note)
VALUES (101, 301, 8, 'Experience in building web applications with Spring Boot'),
       (101, 302, 2, 'Starting TypeScript course.'),
       (102, 301, 5, 'Java solid knowledge.'),
       (103, 303, 6, 'Seasoned in Pascal language.');



