package raisetech.studentManagement.service;

import java.util.List;
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
    List<StudentCourse> studentCourse = repository.searchStudentCourseByStudentId(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 受講生詳細検索です。指定した条件に紐づく任意の受講生を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id           　受講生ID
   * @param fullName     　受講生名
   * @param furigana     　ふりがな
   * @param nickname     　ニックネーム
   * @param emailAddress 　メールアドレス
   * @param area         　住所
   * @param age          　年齢
   * @param sex          　性別
   * @return 条件に紐づく受講生詳細一覧
   */
  public List<StudentDetail> searchStudentsByCondition(String id, String fullName, String furigana,
      String nickname,
      String emailAddress, String area, Integer age, String sex) {
    final List<Student> studentList = repository.searchStudentsByCondition(id, fullName, furigana,
        nickname, emailAddress, area, age, sex);

    List<String> idList = studentList.stream()
        .map(Student::getId)
        .toList();

    final List<StudentCourse> studentCourseList = repository.searchStudentCoursesByStudentIds(
        idList);

    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param id 受講生ID
   * @return 受講生コース詳細一覧
   */
  public List<CourseDetail> searchCoursesByStudentId(String id) {
    return repository.searchCourseDetailsByStudentId(id);
  }

  /**
   * 受講生詳細の登録を行います。受講生と受講生コース情報と受講コース申し込み状況個別に登録します。
   *
   * @param studentRegistrationResult 　受講生詳細
   * @return 受講生詳細
   */
  @Transactional
  public StudentRegistrationResult registerStudent(
      StudentRegistrationResult studentRegistrationResult) {
    final Student student = studentRegistrationResult.getStudent();
    final CourseDetail courseDetail = studentRegistrationResult.getCourseDetail();

    repository.insertStudent(student);

    courseDetail.setStudentId(student.getId());
    final CourseDetail responseCourseDetail = registerCourse(courseDetail);

    studentRegistrationResult.setCourseDetail(responseCourseDetail);
    return studentRegistrationResult;
  }

  /**
   * 受講生コース詳細の登録を行います。受講生コース情報と受講コース申込状況を個別に登録します。
   *
   * @param courseDetail 　受講生コース詳細
   * @return 受講生コース詳細
   */
  @Transactional
  public CourseDetail registerCourse(CourseDetail courseDetail) {
    final StudentCourse studentCourse = courseDetail.converterToStudentCourse();
    repository.insertStudentCourse(studentCourse);

    final ApplicationStatus applicationStatus = courseDetail.converterToApplicationStatus();
    repository.insertApplicationStatus(applicationStatus);

    final CourseDetail responseCourseDetail = CourseDetail.of(studentCourse, applicationStatus);
    return responseCourseDetail;
  }

  /**
   * 受講生の更新を行います。
   *
   * @param student 　受講生
   */
  @Transactional
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }

  /**
   * 受講生コース詳細の更新を行います。受講生コース情報と受講コース申し込み状況をそれぞれ更新します。
   *
   * @param courseDetail 　受講生コース詳細
   */
  @Transactional
  public void updateCourse(CourseDetail courseDetail) {
    final StudentCourse studentCourse = courseDetail.converterToStudentCourse();
    repository.updateStudentCourse(studentCourse);

    final ApplicationStatus applicationStatus = courseDetail.converterToApplicationStatus();
    repository.updateApplicationStatus(applicationStatus);
  }

  /**
   * 受講生コース詳細の削除を行います。受講コースIDに紐づく受講生コース情報と受講コース申し込み状況を削除します。
   *
   * @param courseId 　受講生コースID
   */
  @Transactional
  public void deleteCourse(String courseId) {
    repository.deleteStudentCourse(courseId);
    repository.deleteApplicationStatus(courseId);
  }
}