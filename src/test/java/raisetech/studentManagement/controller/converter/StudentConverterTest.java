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
  void 受講生に紐づく受講コースが正しくマッピングされること() {
    String id1 = "100";
    String id2 = "101";
    Student student1 = new Student(id1, "渡辺　恵子", "わたなべ　けいこ", "けいこ",
        "unique.user1937@example.com", "東京都", 30, "女", "特になし", false);
    Student student2 = new Student(id2, "山田 太郎", "やまだ たろう", "やまたろ",
        "yamada@example.com", "福岡県", 30, "男", "特になし", false);

    StudentCourse course1 = new StudentCourse("100", id1, "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));
    StudentCourse course2 = new StudentCourse("101", id2, "Rubyコース",
        LocalDate.parse("2024-03-01"), LocalDate.parse("2024-07-01"));

    List<StudentDetail> actual = sut.convertStudentDetails(List.of(student1, student2),
        List.of(course1, course2));

    assertThat(actual).hasSize(2);
    assertThat(actual).extracting(StudentDetail::getStudent)
        .containsExactly(student1, student2);
    assertThat(actual.get(0).getStudentCourseList()).containsExactly(course1);
    assertThat(actual.get(1).getStudentCourseList()).containsExactly(course2);
  }
}