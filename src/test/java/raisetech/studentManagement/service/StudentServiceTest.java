package raisetech.studentManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.studentManagement.controller.converter.StudentConverter;
import raisetech.studentManagement.date.ApplicationStatus;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.CourseDetail;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.domain.StudentRegistrationResult;
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
    when(repository.searchStudentCourseByStudentId(id)).thenReturn(studentCourse);

    StudentDetail expected = new StudentDetail(student, studentCourse);

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourseByStudentId(id);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生コース詳細の検索_リポジトリの処理が適切に呼び出せていること() {
    String id = "1";
    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setStudentId(id);
    List<CourseDetail> expected = List.of(courseDetail);

    when(repository.searchCourseDetailsByStudentId(id)).thenReturn(expected);

    final List<CourseDetail> actual = sut.searchCoursesByStudentId(id);

    verify(repository, times(1)).searchCourseDetailsByStudentId(id);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();
    CourseDetail courseDetail = new CourseDetail();
    StudentRegistrationResult studentRegistrationResult = new StudentRegistrationResult(student,
        courseDetail);

    sut.registerStudent(studentRegistrationResult);

    verify(repository, times(1)).insertStudent(student);
  }

  @Test
  void 受講生コース詳細の登録_リポジトリの処理が適切に呼び出せていること() {
    StudentCourse studentCourse = new StudentCourse();
    ApplicationStatus applicationStatus = new ApplicationStatus();
    CourseDetail courseDetail = new CourseDetail();

    sut.registerCourse(courseDetail);

    verify(repository, times(1)).insertStudentCourse(studentCourse);
    verify(repository, times(1)).insertApplicationStatus(applicationStatus);
  }

  @Test
  void 受講生の更新_リポジトリの処理が適切に呼び出せていること() {
    Student student = new Student();

    sut.updateStudent(student);

    verify(repository, times(1)).updateStudent(student);
  }

  @Test
  void 受講生コース詳細の更新＿リポジトリの処理が適切に呼び出せていること() {
    StudentCourse studentCourse = new StudentCourse();
    ApplicationStatus applicationStatus = new ApplicationStatus();
    CourseDetail courseDetail = new CourseDetail(studentCourse.getStudentId(),
        studentCourse.getId(), studentCourse.getCourse(), studentCourse.getStartDate(),
        studentCourse.getExpectedCompletionDate(),
        applicationStatus.getId(), applicationStatus.getApplicationStatus());

    sut.updateCourse(courseDetail);

    verify(repository, times(1)).updateStudentCourse(studentCourse);
    verify(repository, times(1)).updateApplicationStatus(applicationStatus);
  }

  @Test
  void 受講生コース詳細の削除_リポジトリの処理が適切に呼び出せていること() {
    String courseId = "1";

    sut.deleteCourse(courseId);

    verify(repository, times(1)).deleteStudentCourse(courseId);
    verify(repository, times(1)).deleteApplicationStatus(courseId);
  }
}