INSERT INTO students (full_name, furigana, nick_name, email_address, area, age, sex)
VALUES ('渡辺　恵子','わたなべ　けいこ','けいちゃん','unique.user1937@example.com','東京都新宿区新宿',30,'女'),
       ('田中　大輔','たなか　だいすけ','だい','fast.rabbit8924@example.com','福岡県福岡市博多区博多駅前',25,'男'),
       ('中村　聡','なかむら　さとし','なかさと','blue.sky7452@example.com','神奈川県横浜市中区桜木町',24,'男'),
       ('佐藤　智也','さとう　ともや','とも','smart.code3021@example.com','広島県広島市中区紙屋町',32,'男'),
       ('伊藤　さやか','いとう　さやか','さや','cool.star6789@example.com','埼玉県さいたま市大宮区桜木町',22,'女');

INSERT INTO students_courses (student_id, course, start_date, expected_completion_date)
VALUES (1,'JAVAコース','2024-01-01','2024-04-01'),
       (2,'AWSコース','2024-02-01','2024-05-01'),
       (3,'デザインコース','2024-03-01','2024-06-01'),
       (4,'JAVAコース','2024-04-01','2024-07-01'),
       (5,'デザインコース','2025-05-01','2025-08-01'),
       (1,'AWSコース','2025-06-01','2025-09-01');

INSERT INTO application_status (course_id, application_status)
VALUES (1,'受講終了'),
       (2,'受講終了'),
       (3,'受講終了'),
       (4,'受講中'),
       (5,'本申込');