delete from post;
ALTER SEQUENCE post_id_seq RESTART WITH 10;

insert into post(id, anons, date, full_text, title, usr_id) values
(1, 'Привет', '16:24:34 21-09-2020', 'Привет, мир!', 'Russian', 1),
(2, 'hey', '18:55:12 21-09-2020', 'Hello, world!', 'English', 1),
(3, 'Witaj', '18:55:12 21-09-2020', 'Witaj, swiecie', 'Polish', 2);



