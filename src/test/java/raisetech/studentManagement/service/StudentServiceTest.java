package raisetech.studentManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.studentManagement.controller.converter.StudentConverter;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧機能_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    List<StudentCourse> studentCourse = new ArrayList<>();
    String id = "100";
    student.setId(id);
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(studentCourse);

    StudentDetail expected = new StudentDetail(student, studentCourse);

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);
    assertEquals(expected, actual);
  }

  @Test
  void 指定した条件での受講生の検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    Student student = new Student("1", "渡辺　恵子", "わたなべ　けいこ", "けいちゃん",
        "unique.user1937@example.com", "東京都新宿区新宿", 30, "女", null, false);
    List<Student> studentList = List.of(student);

    StudentCourse studentCourse = new StudentCourse("1", "1", "デザインコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<String> idList = List.of("1");

    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    List<StudentDetail> expected = List.of(studentDetail);

    when(repository.searchStudentsByCondition("1", "渡辺　恵子", "わたなべ", "けい",
        "unique.user1937@example.com", "東京", 30, "女")).thenReturn(studentList);
    when(repository.searchStudentCoursesByStudentIds(idList)).thenReturn(studentCourseList);
    when(converter.convertStudentDetails(studentList, studentCourseList)).thenReturn(expected);

    final List<StudentDetail> actual = sut.searchStudentsByCondition("1", "渡辺　恵子",
        "わたなべ", "けい",
        "unique.user1937@example.com", "東京", 30, "女");

    verify(repository, times(1)).searchStudentsByCondition("1", "渡辺　恵子", "わたなべ", "けい",
        "unique.user1937@example.com", "東京", 30, "女");
    verify(repository, times(1)).searchStudentCoursesByStudentIds(idList);
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).insertStudent(student);
    verify(repository, times(1)).insertStudentCourse(studentCourse);
  }

  @Test
  void 受講生の登録_受講生コース情報の初期情報の設定が適切に行われていること() {
    String id = "100";
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();
    LocalDate now = LocalDate.now();

    sut.initStudentCourse(studentCourse, student.getId());

    assertEquals(id, studentCourse.getStudentId());
    assertEquals(now, studentCourse.getStartDate());
    assertEquals(now.plusYears(1), studentCourse.getExpectedCompletionDate());
  }

  @Test
  void 受講生の更新_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = new ArrayList<>(List.of(studentCourse));
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(studentDetail.getStudent());
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }
}