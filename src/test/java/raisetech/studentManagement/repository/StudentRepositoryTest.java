package raisetech.studentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.CourseDetail;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    final List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の検索が行えること() {
    Student expected = new Student("1", "渡辺　恵子", "わたなべ　けいこ", "けいちゃん",
        "unique.user1937@example.com", "東京都新宿区新宿", 30, "女", null, false);

    final Student actual = sut.searchStudent("1");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生のコース情報の全件検索が行えること() {
    final List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報検索が行えること() {
    String studentId = "1";
    StudentCourse studentCourse1 = new StudentCourse("1", studentId, "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));
    StudentCourse studentCourse2 = new StudentCourse("6", studentId, "AWSコース",
        LocalDate.parse("2025-06-01"), LocalDate.parse("2025-09-01"));
    List<StudentCourse> expected = List.of(studentCourse1, studentCourse2);

    final List<StudentCourse> actual = sut.searchStudentCourse(studentId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生IDに紐づく受講生コース詳細検索が行えること() {
    String studentId = "1";
    CourseDetail courseDetail1 = new CourseDetail(studentId, "1", "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"), 1, "受講終了");
    CourseDetail courseDetail2 = new CourseDetail(studentId, "6", "AWSコース",
        LocalDate.parse("2025-06-01"), LocalDate.parse("2025-09-01"), 6, "仮申込");

    List<CourseDetail> expected = List.of(courseDetail1,courseDetail2);

    final List<CourseDetail> actual = sut.searchCourseDetailsByStudentId(studentId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setFullName("渡辺　恵子");
    student.setFurigana("わたなべ　けいこ");
    student.setNickname("けいこ");
    student.setEmailAddress("unique.user1937@example.com");
    student.setArea("東京都");
    student.setAge(30);
    student.setSex("女");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    final List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourse("デザインコース");
    studentCourse.setStartDate(LocalDate.parse("2024-07-01"));
    studentCourse.setExpectedCompletionDate(LocalDate.parse("2024-10-01"));

    sut.insertStudentCourse(studentCourse);

    final List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 受講生の更新が行えること() {
    String id = "2";
    Student expected = new Student(id, "渡辺　太郎", "わたなべ　たろう", "たろう",
        "unique.user1111@example.com", "北海道", 20, "男", "", false);

    sut.updateStudent(expected);

    final Student actual = sut.searchStudent(id);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生コース情報の名前の更新が行えること() {
    String studentId = "1";
    String courseId = "1";
    StudentCourse expected = new StudentCourse(courseId, studentId, "デザインコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));

    sut.updateStudentCourse(expected);

    final List<StudentCourse> studentCourses = sut.searchStudentCourse(studentId);
    List<StudentCourse> actual = studentCourses.stream()
        .filter(course -> Objects.equals(course.getId(), courseId))
        .toList();

    assertThat(actual.getFirst()).isEqualTo(expected);
  }
}