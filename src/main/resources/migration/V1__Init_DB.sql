CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE "reflection-task".users
(
    id      uuid              NOT NULL default uuid_generate_v4(),
    name    character varying NOT NULL,
    surname character varying NOT NULL,
    email   character varying NOT NULL,
    age     integer           NOT NULL,
    primary key (id)
);

INSERT INTO "reflection-task".users
VALUES ('d00285fd-4911-4f57-89aa-40740c0acfab', 'Peter', 'Parker', 'peterr@gmail.com', 25);
INSERT INTO "reflection-task".users
VALUES ('4ecb41ed-92fb-414e-962c-45734f40103f', 'Alan', 'Wake', 'allallan@gmail.com', 23);
INSERT INTO "reflection-task".users
VALUES ('c4d0668f-05b9-41cf-812f-c8eafffe459c', 'Mike', 'Tyson', 'asfdg@mail.ru', 20);
INSERT INTO "reflection-task".users
VALUES ('a9cab548-1381-4fcc-b2e0-86834b9d17e6', 'Some', 'Human', 'human@gmail.com', 19);
INSERT INTO "reflection-task".users
VALUES ('1bbfdbf6-dc0d-4443-9e5d-3c3325d51490', 'John', 'Johnson', 'johnson@gmail.com', 26);




