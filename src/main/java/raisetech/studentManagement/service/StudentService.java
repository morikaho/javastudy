package raisetech.studentManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentManagement.controller.converter.StudentConverter;
import raisetech.studentManagement.date.ApplicationStatus;
import raisetech.studentManagement.domain.CourseDetail;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.StudentRegistrationResult;
import raisetech.studentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;


  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索です。 IDに紐づく任意の受講生を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  //受講生IDに紐づくコース詳細の検索
  public List<CourseDetail> searchCoursesByStudentId(String id) {
    return repository.searchCourseDetailsByStudentId(id);
  }

  //受講生とコース詳細登録
  @Transactional
  public StudentRegistrationResult registerStudent(
      StudentRegistrationResult studentRegistrationResult) {
    final Student student = studentRegistrationResult.getStudent();
    final CourseDetail courseDetail = studentRegistrationResult.getCourseDetail();

    repository.insertStudent(student);

    CourseDetail courseDetail1 = new CourseDetail(student.getId(), null, courseDetail.getCourse(),
        courseDetail.getStartDate(), courseDetail.getExpectedCompletionDate(), null,
        courseDetail.getApplicationStatus());

    final CourseDetail responseCourseDetail = registerCourse(courseDetail1);

    studentRegistrationResult.setCourseDetail(responseCourseDetail);
    return studentRegistrationResult;
  }

  //コース詳細登録
  @Transactional
  public CourseDetail registerCourse(CourseDetail courseDetail) {
    StudentCourse studentCourse = new StudentCourse(null, courseDetail.getStudentId(),
        courseDetail.getCourse(), courseDetail.getStartDate(),
        courseDetail.getExpectedCompletionDate());
    repository.insertStudentCourse(studentCourse);

    ApplicationStatus applicationStatus = new ApplicationStatus(null, studentCourse.getId(),
        courseDetail.getApplicationStatus());
    repository.insertApplicationStatus(applicationStatus);

    CourseDetail responseCourseDetail = new CourseDetail(studentCourse.getStudentId(),
        studentCourse.getId(), studentCourse.getCourse(), studentCourse.getStartDate(),
        studentCourse.getExpectedCompletionDate(), applicationStatus.getId(),
        applicationStatus.getApplicationStatus());
    return responseCourseDetail;
  }

  //受講生の更新
  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  //受講コース詳細の更新
  @Transactional
  public void updateCourse(CourseDetail courseDetail) {
    StudentCourse studentCourse = new StudentCourse(courseDetail.getCourseId(),
        courseDetail.getStudentId(), courseDetail.getCourse(), courseDetail.getStartDate(),
        courseDetail.getExpectedCompletionDate());
    repository.updateStudentCourse(studentCourse);

    ApplicationStatus applicationStatus = new ApplicationStatus(
        courseDetail.getApplicationStatusId(), courseDetail.getCourseId(),
        courseDetail.getApplicationStatus());
    repository.updateApplicationStatus(applicationStatus);
  }

  //受講コース詳細の削除
  @Transactional
  public void deleteCourse(String courseId){
    repository.deleteStudentCourse(courseId);
    repository.deleteApplicationStatus(courseId);
  }
}
