package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

  @Insert("INSERT INTO students (id,full_name,furigana,nick_name,email_address,area,age,sex,remark,is_deleted)"
      + "VALUES (#{id},#{fullName},#{furigana},#{nickname},#{emailAddress},#{area},#{age},#{sex},#{remark},#{isDeleted})")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (id,student_id,course,start_date,expected_completion_date)"
      + "VALUES (#{id},#{studentId},#{course},#{startDate},#{expectedCompletionDate})")
  void insertStudentCourse(StudentsCourses studentsCourses);
}


