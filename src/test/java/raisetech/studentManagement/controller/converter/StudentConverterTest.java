package raisetech.studentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void setUp() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること() {
    final Student student = createStudent();

    StudentCourse course = new StudentCourse("100", "100", "JAVAコース", LocalDate.now(),
        LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡した時に紐づかない受講生コース情報は除外されること() {
    final Student student = createStudent();

    StudentCourse course = new StudentCourse("100", "101", "JAVAコース", LocalDate.now(),
        LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(course);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();
  }

  private Student createStudent() {
    Student student = new Student("100", "渡辺　恵子", "わたなべ　けいこ", "けいこ",
        "unique.user1937@example.com", "東京都", 30, "女", "特になし", false);
    return student;
  }
}