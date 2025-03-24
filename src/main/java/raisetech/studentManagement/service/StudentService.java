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

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentRegistrationResult registerStudent(
      StudentRegistrationResult studentRegistrationResult) {
    final Student student = studentRegistrationResult.getStudent();

    repository.insertStudent(student);

    studentRegistrationResult.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, student.getId());
      repository.insertStudentCourse(studentCourse);
    });

    List<StudentCourse> studentCourseList = studentRegistrationResult.getStudentCourseList();
    List<ApplicationStatus> applicationStatusList = studentRegistrationResult.getApplicationStatusList();
    IntStream.range(0, Math.min(applicationStatusList.size(), studentCourseList.size()))
        .forEach(i -> {
          applicationStatusList.get(i).setCourseId(studentCourseList.get(i).getId());
          repository.insertApplicationStatus(applicationStatusList.get(i));
        });

    return studentRegistrationResult;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param id            受講生ID
   */
  void initStudentCourse(StudentCourse studentCourse, String id) {
    //開始日を指定して入力、終了日を一年後に自動設定
    studentCourse.setStudentId(id);
    studentCourse.setExpectedCompletionDate(studentCourse.getStartDate().plusYears(1));
  }

  /**
   * 受講生の更新を行います。 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}
