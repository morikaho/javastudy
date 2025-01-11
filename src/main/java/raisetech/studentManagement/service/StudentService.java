package raisetech.studentManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository){
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //検索処理
    List <Student> studentList = repository.searchStudents();
    //絞り込みをする。年齢が30代の人のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<Student> filteredStudents = studentList.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
        .collect(Collectors.toList());

    return filteredStudents;
  }

  public List<StudentCourse> searchStudentCourseList() {
    List <StudentCourse> studentCourseList = repository.searchStudentCourse();
    //絞り込み検索で「Javaコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<StudentCourse> filteredStudentcourse = studentCourseList.stream()
        .filter(studentCourse -> studentCourse.getCourse().equals("JAVAコース"))
        .collect(Collectors.toList());

    return filteredStudentcourse;

  }
}
