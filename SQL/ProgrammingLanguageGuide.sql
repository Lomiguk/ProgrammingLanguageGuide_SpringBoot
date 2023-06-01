DROP TABLE "profile" CASCADE;
DROP TABLE "subscribe" CASCADE;
DROP TABLE "article" CASCADE;
DROP TABLE "tag" CASCADE;
DROP TABLE "article_tag" CASCADE;
DROP TABLE "comment" CASCADE;

CREATE TABLE "profile" (
  "id" bigserial PRIMARY KEY,
  "email" varchar(256) NOT NULL,
  "login" varchar(80) UNIQUE NOT NULL,
  "password" bigint NOT NULL,
  "is_author" boolean DEFAULT FALSE
);

CREATE TABLE "subscribe" (
  "subscriber_id" bigint NOT NULL,
  "author_id" bigint NOT NULL,
  "subscribe_date" timestamp NOT NULL
);

CREATE TABLE "article" (
  "id" bigserial PRIMARY KEY,
  "title" varchar NOT NULL,
  "author_id" bigint NOT NULL,
  "post_date" timestamp NOT NULL,
  "content" text,
  "read_count" int DEFAULT 0
);

CREATE TABLE "tag" (
  "id" bigserial PRIMARY KEY,
  "title" varchar(20) UNIQUE NOT NULL
);

CREATE TABLE "article_tag" (
  "article_id" bigint NOT NULL,
  "tag_id" bigint NOT NULL
);

CREATE TABLE "comment" (
  "id" bigserial PRIMARY KEY,
  "author_id" bigint NOT NULL,
  "article_id" bigint NOT NULL,
  "content" text NOT NULL,
  "post_date" timestamp NOT NULL
);

ALTER TABLE "subscribe" ADD FOREIGN KEY ("subscriber_id") REFERENCES "profile" ("id");

ALTER TABLE "subscribe" ADD FOREIGN KEY ("author_id") REFERENCES "profile" ("id");

ALTER TABLE "article" ADD FOREIGN KEY ("author_id") REFERENCES "profile" ("id");

ALTER TABLE "article_tag" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

ALTER TABLE "article_tag" ADD FOREIGN KEY ("tag_id") REFERENCES "tag" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("author_id") REFERENCES "profile" ("id");

ALTER TABLE "comment" ADD FOREIGN KEY ("article_id") REFERENCES "article" ("id");

/* INSERT */
/* Tag */
INSERT INTO tag (title) VALUES ('C#');
INSERT INTO tag (title) VALUES ('Java');
INSERT INTO tag (title) VALUES ('Kotlin');
INSERT INTO tag (title) VALUES ('SQL');
INSERT INTO tag (title) VALUES ('PostgreSQL');
INSERT INTO tag (title) VALUES ('OOP');
INSERT INTO tag (title) VALUES ('Servlet');
INSERT INTO tag (title) VALUES ('Go');
INSERT INTO tag (title) VALUES ('PHP');
INSERT INTO tag (title) VALUES ('Maven');
INSERT INTO tag (title) VALUES ('Spring');
INSERT INTO tag (title) VALUES ('Spring Boot');
/* Profile */
INSERT INTO profile (email, login, password, is_author) VALUES ('base.user@mail.com', 'baseUser', 8164042005876377988, TRUE); /* password: baseUser*/
/* Article */
INSERT INTO article (title, author_id, post_date, content) VALUES ('Spring Framework?', 1, now(), 'Вы можете использовать это руководство для различных целей: Чтобы понять, что такое Spring Framework Как работают ее основные фичи: такие как внедрение зависимостей или Web MVC Это также исчерпывающий FAQ (Перечень часто задаваемых вопросов) Примечание: Статья ~ 9000 слов, вероятно, не стоит читать ее на мобильном устройстве. Добавьте ее в закладки и вернитесь позже. И даже на компьютере ешь читай этого слона по одному кусочку за раз :-). Введение Сложность экосистемы Spring Многие компании используют Spring, однако когда вы захотите узнать об этом фреймворке и перейдете на сайт spring.io, то увидите, что вселенная Spring на самом деле состоит из 21 различных активных проектов. Ой! Кроме того, если вы начали программировать с помощью Spring в последние пару лет, очень велика вероятность того, что вы перешли непосредственно к Spring Boot или Spring Data. Однако это руководство касается только одного, самого важного из этих проектов: Spring Framework. Почему? Потому что важно понимать, что Spring Framework является основой для всех других проектов. Spring Boot, Spring Data, Spring Batch — все это построено поверх Spring. Это имеет два последствия: Без надлежащего знания Spring Framework вы рано или поздно потеряетесь. Вы не будете в полной мере понимать, например. Spring Boot, несмотря на то, что, как вы думаете знание ядра Spring Framework неважно. Потраченные ~ 15 минут на чтение этого руководства, которое охватывает самые важные 80% Spring Framework, многократно окупятся в вашей профессиональной карьере. Что такое Spring Framework? Краткий ответ: По сути Spring Framework представляет собой просто контейнер внедрения зависимостей, с несколькими удобными слоями (например: доступ к базе данных, прокси, аспектно-ориентированное программирование, RPC, веб-инфраструктура MVC). Это все позволяет вам быстрее и удобнее создавать Java-приложения. Только это не очень помогает, не так ли? К счастью, есть и длинный ответ: Остальная часть этого руководства. Основы внедрения зависимостей Если вы уже знаете, что такое внедрение зависимостей, не стесняйтесь переходить прямо к следующему разделу Контейнер Spring IOC / Dependency Injection. В противном случае читайте дальше. Что такое зависимость? Представьте, что вы пишете Java класс, который позволяет вам получить доступ к таблице пользователей в вашей базе данных. Вы бы назвали эти классы DAO (объект доступа к данным) или репозитории. Итак, вы собираетесь написать класс UserDAO.');
/* Article - Tag*/
INSERT INTO article_tag VALUES (1, 2); /* Java */
INSERT INTO article_tag VALUES (1, 3); /* Kotlin */
INSERT INTO article_tag VALUES (1, 10); /* Maven */
INSERT INTO article_tag VALUES (1, 11); /* Spring */
INSERT INTO article_tag VALUES (1, 12); /* Spring Boot */
/* Comment */
INSERT INTO comment (author_id, article_id, content, post_date) VALUES (1, 1, 'Больше таких статей!', now());