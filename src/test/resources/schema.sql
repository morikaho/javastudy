CREATE TABLE IF NOT EXISTS students
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    nick_name VARCHAR(50),
    email_address VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark TEXT,
    is_deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course VARCHAR(50) NOT NULL,
    start_date DATE,
    expected_completion_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(id)
);