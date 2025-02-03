package raisetech.studentManagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;
import raisetech.studentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudents();
  }

  public List<StudentsCourses> searchStudentCourseList() {
    return repository.searchStudentCourse();
  }

  //単一の生徒情報検索
  public StudentDetail searchOneStudent(int id) {
    StudentDetail searchOneStudent = new StudentDetail();
    searchOneStudent.setStudent(repository.searchOneStudent(id));
    searchOneStudent.setStudentsCourses(repository.searchOneStudentCourse(id));
    return searchOneStudent;
  }

  //生徒登録
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    //コース情報
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setStartDate(LocalDate.now());
      studentsCourse.setExpectedCompletionDate(LocalDate.now().plusYears(1));
      repository.insertStudentCourse(studentsCourse);
    }
  }

  @Transactional
  public void updateStudent(int id, StudentDetail studentDetail) {
    studentDetail.getStudent().setId(id);
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentDetail.getStudentsCourses().getFirst().setStudentId(id);
      repository.updateStudentCourses(studentsCourse);
    }
  }
}
