package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ。
 * <p>
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentCourse();

  //単一検索
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchOneStudent(int id);

  @Select("SELECT student_id FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchOneStudentCourse(int id);

  @Insert(
      "INSERT INTO students (full_name, furigana, nick_name, email_address, area, age, sex, remark, is_deleted) "
          + "VALUES (#{fullName},#{furigana}, #{nickname}, #{emailAddress}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id,course,start_date,expected_completion_date)"
      + "VALUES (#{studentId}, #{course}, #{startDate}, #{expectedCompletionDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudentCourse(StudentsCourses studentsCourses);

  //更新
  @Update(
      "UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, nick_name = #{nickname},"
          + "email_address = #{emailAddress}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark} "
          + "WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course = #{course} WHERE student_id = #{studentId}")
  void updateStudentCourses(StudentsCourses studentsCourses);
}


