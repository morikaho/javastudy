package raisetech.studentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.studentManagement.date.ApplicationStatus;
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
  void 受講生IDで受講生の検索が行えること() {
    Student expected = createStudent();

    final Student actual = sut.searchStudent("1");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 指定した条件で受講生の検索が行えること() {
    final Student student = createStudent();

    List <Student> expected = List.of(student);

    final List<Student> actual = sut.searchStudentsByCondition("1", "渡辺　恵子", "わたなべ", "けい",
        "unique.user1937@example.com", "東京",30 , "女");

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

    final List<StudentCourse> actual = sut.searchStudentCourseByStudentId(studentId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生IDに紐づく受講生コース詳細検索が行えること() {
    String studentId = "1";
    CourseDetail courseDetail1 = new CourseDetail(studentId, "1", "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"), 1, "受講終了");

    List<CourseDetail> expected = List.of(courseDetail1);

    final List<CourseDetail> actual = sut.searchCourseDetailsByStudentId(studentId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 複数の受講生IDに紐づく受講生コース情報検索が行えること() {
    String studentId1 = "1";
    String studentId2 = "3";

    StudentCourse studentCourse1 = new StudentCourse("1", studentId1, "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));
    StudentCourse studentCourse2 = new StudentCourse("6", studentId1, "AWSコース",
        LocalDate.parse("2024-06-01"), LocalDate.parse("2024-09-01"));
    StudentCourse studentCourse3 = new StudentCourse("3", studentId2, "デザインコース",
        LocalDate.parse("2024-03-01"), LocalDate.parse("2024-06-01"));
    List<StudentCourse> expected = List.of(studentCourse1, studentCourse2,studentCourse3);

    List<String> studentIds = List.of(studentId1,studentId2);

    final List<StudentCourse> actual = sut.searchStudentCoursesByStudentIds(studentIds);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student(null, "渡辺　恵子", "わたなべ　けいこ", "けいこ",
        "unique.user1937@example.com", "東京都", 30, "女", "", false);

    sut.insertStudent(student);

    final List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse(null, "1", "デザインコース",
        LocalDate.parse("2024-07-01"), LocalDate.parse("2024-10-01"));

    sut.insertStudentCourse(studentCourse);

    final List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 申込状況の登録が行えること() {
    ApplicationStatus applicationStatus = new ApplicationStatus();
    applicationStatus.setCourseId("6");
    applicationStatus.setApplicationStatus("仮申込");

    sut.insertApplicationStatus(applicationStatus);

    final List<CourseDetail> actual = sut.searchCourseDetailsByStudentId("1");

    assertThat(actual.get(1).getCourseId()).isEqualTo("6");
    assertThat(actual.get(1).getApplicationStatusId()).isEqualTo(6);
    assertThat(actual.get(1).getApplicationStatus()).isEqualTo("仮申込");
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
  void 受講生コース情報の更新が行えること() {
    String courseId = "1";
    String studentId = "1";
    StudentCourse expected = new StudentCourse(courseId, studentId, "デザインコース",
        LocalDate.parse("2025-01-01"), LocalDate.parse("2026-04-01"));

    sut.updateStudentCourse(expected);

    final List<StudentCourse> actual = sut.searchStudentCourseByStudentId(studentId);

    assertThat(actual.getFirst()).isEqualTo(expected);
  }

  @Test
  void 受講コース申し込み状況の更新が行えること() {
    ApplicationStatus expected = new ApplicationStatus(1, "1", "受講中");

    sut.updateApplicationStatus(expected);

    final CourseDetail first = sut.searchCourseDetailsByStudentId("1").getFirst();
    ApplicationStatus actual = new ApplicationStatus(first.getApplicationStatusId(),
        first.getCourseId(), first.getApplicationStatus());

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講コース情報の削除が行えること() {
    String courseId = "1";
    String studentId = "1";

    sut.deleteApplicationStatus(courseId);
    sut.deleteStudentCourse(courseId);

    final List<CourseDetail> actual = sut.searchCourseDetailsByStudentId(studentId);

    assertThat(actual).isEmpty();
  }

  @Test
  void 受講コース詳細の削除が行えること() {
    String courseId = "1";
    String studentId = "1";

    sut.deleteApplicationStatus(courseId);

    final List<CourseDetail> actual = sut.searchCourseDetailsByStudentId(studentId);

    assertThat(actual).isEmpty();

  private Student createStudent() {
    Student student = new Student("1", "渡辺　恵子", "わたなべ　けいこ", "けいちゃん",
        "unique.user1937@example.com", "東京都新宿区新宿", 30, "女", null, false);
    return student;
  }
}