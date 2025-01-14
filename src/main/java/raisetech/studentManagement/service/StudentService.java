package raisetech.studentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;
import raisetech.studentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository){
    this.repository = repository;
  }

  public List<Student> searchStudentList(int age) {
    //検索処理
    List <Student> studentList = repository.searchStudents();
    //絞り込みをする。
    //抽出したリストをコントローラーに返す。
    List<Student> filteredStudents = studentList.stream()
        .filter(student -> student.getAge() >= age && student.getAge() <= age + 9)
        .collect(Collectors.toList());

    return filteredStudents;
  }

  public List<StudentsCourses> searchStudentCourseList(String course) {
    List <StudentsCourses> studentCourseList = repository.searchStudentCourse();
    //絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<StudentsCourses> filteredStudentcourse = studentCourseList.stream()
        .filter(studentCourse -> studentCourse.getCourse().equals(course))
        .collect(Collectors.toList());

    return filteredStudentcourse;

  }
}
