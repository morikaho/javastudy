package raisetech.studentManagement.service;

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

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    Random random = new Random();

    //ランダムなstudentのid
    String randomStudentId = String.valueOf(random.nextInt(100000000));
    // 生徒情報
    studentDetail.getStudent().setId(randomStudentId);
    studentDetail.getStudent().setDeleted(false);
    repository.insertStudent(studentDetail.getStudent());
    //コース情報
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(randomStudentId);
      studentsCourses.setId(String.valueOf(random.nextInt(100000000)));
      repository.insertStudentCourse(studentsCourses);
    }

  }
}
