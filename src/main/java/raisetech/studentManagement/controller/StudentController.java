package raisetech.studentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;
import raisetech.studentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  //年代を、クエリ文字列で受け取った文字列で検索し表示する。
  @GetMapping("/studentList")
  public List<Student> getStudentList(@RequestParam int age) {
    return service.searchStudentList(age);
  }

  //コース情報を検索
  @GetMapping("/studentCourseList")
  public List<StudentsCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}