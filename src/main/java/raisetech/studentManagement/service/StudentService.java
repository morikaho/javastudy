package raisetech.studentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentManagement.controller.converter.StudentConverter;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;
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
   * 受講生一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索です。 IDに紐づく任意の受講生を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
    return new StudentDetail(student, studentsCourses);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setStartDate(LocalDate.now());
      studentsCourse.setExpectedCompletionDate(LocalDate.now().plusYears(1));
      repository.insertStudentCourse(studentsCourse);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}
