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
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(int id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(int studentId);

  @Insert(
      "INSERT INTO students (full_name, furigana, nick_name, email_address, area, age, sex, remark, is_deleted) "
          + "VALUES (#{fullName},#{furigana}, #{nickname}, #{emailAddress}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course, start_date, expected_completion_date)"
      + "VALUES (#{studentId}, #{course}, #{startDate}, #{expectedCompletionDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudentCourse(StudentsCourses studentsCourses);

  @Update(
      "UPDATE students SET full_name = #{fullName}, furigana = #{furigana}, nick_name = #{nickname},"
          + "email_address = #{emailAddress}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted =#{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course = #{course} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);
}


